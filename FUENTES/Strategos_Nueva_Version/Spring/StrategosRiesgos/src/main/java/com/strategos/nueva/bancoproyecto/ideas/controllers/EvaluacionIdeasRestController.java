package com.strategos.nueva.bancoproyecto.ideas.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.strategos.nueva.bancoproyecto.ideas.model.CriteriosEvaluacion;
import com.strategos.nueva.bancoproyecto.ideas.model.EstatusIdeas;
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeas;
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasEvaluadas;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyecto.ideas.service.CriteriosEvaluacionService;
import com.strategos.nueva.bancoproyecto.ideas.service.EstatusIdeaService;
import com.strategos.nueva.bancoproyecto.ideas.service.EvaluacionIdeasDetalleService;
import com.strategos.nueva.bancoproyecto.ideas.service.EvaluacionIdeasService;
import com.strategos.nueva.bancoproyecto.ideas.service.IdeasEvaluadasService;
import com.strategos.nueva.bancoproyecto.ideas.service.IdeasProyectosService;
import com.strategos.nueva.bancoproyectos.model.util.DatoMedicion;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class EvaluacionIdeasRestController {
	
	@Autowired
	private EvaluacionIdeasService evaluacionService;
	
	@Autowired
	private IdeasEvaluadasService ideasEvaluadasService;
	
	@Autowired
	private IdeasProyectosService ideasProyectosService;
	
	@Autowired
	private EvaluacionIdeasDetalleService evaluacionDetalleService;
	
	@Autowired
	private CriteriosEvaluacionService criteriosService;
	
	@Autowired
	private EstatusIdeaService estatusService;
	
	
	//Servicios Rest tabla - evaluacion 
	
		private final Logger log = LoggerFactory.getLogger(EvaluacionIdeasRestController.class);
		
		//servicio que trae la lista de evaluacion 
		@GetMapping("/evaluacion")
		public List<EvaluacionIdeas> index (){
			return evaluacionService.findAll();
		}
		
		
		
		//servicio que trae la lista de evaluacion 
		@GetMapping("/evaluacion/ideas/{id}")
		public List<IdeasProyectos> getIdeas(@PathVariable Long id){
			
			List<IdeasProyectos> ideasProyectos = new ArrayList<IdeasProyectos>();
			List<IdeasEvaluadas> ideasEvaluadas = new ArrayList<IdeasEvaluadas>();			
			ideasEvaluadas = ideasEvaluadasService.findAllByEvaluacionId(id);
			
			for(IdeasEvaluadas ide: ideasEvaluadas) {
				IdeasProyectos idea = ideasProyectosService.findById(ide.getIdeaId());
				ideasProyectos.add(idea);
			}
												
			return ideasProyectos;
		}
			
		//servicio que muestra un evaluacion
		@GetMapping("/evaluacion/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			EvaluacionIdeas evaluacionId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				evaluacionId= evaluacionService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(evaluacionId == null) {
			  response.put("mensaje", "La evaluacion Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<EvaluacionIdeas>(evaluacionId, HttpStatus.OK); 		
		}
		
		//servicio que crea un evaluacion
		@PostMapping("/evaluacion")
		public ResponseEntity<?> create(@Valid @RequestBody EvaluacionIdeas evaluacionN, BindingResult result) {
			
			EvaluacionIdeas evaluacionNew= null;
			List<IdeasProyectos> ideas = new ArrayList<IdeasProyectos>();
			List<CriteriosEvaluacion> criterios = new ArrayList<CriteriosEvaluacion>();
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				criterios = criteriosService.findAll();		
				
				evaluacionNew= evaluacionService.save(evaluacionN);
				
				ideas = evaluacionN.getIdeas();
				
				for(IdeasProyectos ide: ideas) {
													
					
					IdeasEvaluadas ideaEva = new IdeasEvaluadas();
					
					ideaEva.setEvaluacionId(evaluacionNew.getEvaluacionId());
					ideaEva.setIdeaId(ide.getIdeaId()); 
					
					ideasEvaluadasService.save(ideaEva);
					
					for(CriteriosEvaluacion cri: criterios) {
						
						EvaluacionIdeasDetalle evaluacionDetalle = new EvaluacionIdeasDetalle();	
						
						evaluacionDetalle.setEvaluacionId(evaluacionNew.getEvaluacionId());
						evaluacionDetalle.setCriterio(cri.getControl());
						evaluacionDetalle.setIdeaId(ide.getIdeaId());
						evaluacionDetalle.setPeso(cri.getPeso());
						
						evaluacionDetalleService.save(evaluacionDetalle);
						
					}
					
					ide.setEstatusIdeaId((long) 2);
					
					if(ide.getEstatusIdeaId() != null) {
						EstatusIdeas est = estatusService.findById(ide.getEstatusIdeaId());
						ide.setEstatus(est.getEstatus());
						ide.setFechaEstatus(new Date());
					}
					
					ideasProyectosService.save(ide);
					
				}

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La evaluacion ha sido creado con Exito!");
			response.put("evaluacion", evaluacionNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un evaluacion
		@PutMapping("/evaluacion/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody EvaluacionIdeas evaluacion, BindingResult result, @PathVariable Long id) {
			
			List<IdeasProyectos> ideasEvaluacionAntes = new ArrayList<IdeasProyectos>();	
			List<IdeasProyectos> ideasEvaluacion = new ArrayList<IdeasProyectos>();
			List<CriteriosEvaluacion> criterios = new ArrayList<CriteriosEvaluacion>();
			
			EvaluacionIdeas evaluacionActual= evaluacionService.findById(id);
			EvaluacionIdeas evaluacionUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(evaluacionActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, la evaluacion ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				List<IdeasEvaluadas> ideasEvaluadas = new ArrayList<IdeasEvaluadas>();			
				ideasEvaluadas = ideasEvaluadasService.findAllByEvaluacionId(id);
				
				for(IdeasEvaluadas ide: ideasEvaluadas) {
					IdeasProyectos idea = ideasProyectosService.findById(ide.getIdeaId());
					ideasEvaluacionAntes.add(idea);
				}
				
				ideasEvaluacion = evaluacion.getIdeas();
				
				for(IdeasProyectos ideaActual: ideasEvaluacion) {
					
					Boolean existe = existeIdeas(ideasEvaluacionAntes, ideaActual);
					
					if(!existe) {
						criterios = criteriosService.findAll();
						
						IdeasEvaluadas ideaEva = new IdeasEvaluadas();
						
						ideaEva.setEvaluacionId(id);
						ideaEva.setIdeaId(ideaActual.getIdeaId()); 
						
						ideasEvaluadasService.save(ideaEva);
						
						for(CriteriosEvaluacion cri: criterios) {
							
							EvaluacionIdeasDetalle evaluacionDetalle = new EvaluacionIdeasDetalle();	
							
							evaluacionDetalle.setEvaluacionId(id);
							evaluacionDetalle.setCriterio(cri.getControl());
							evaluacionDetalle.setIdeaId(ideaActual.getIdeaId());
							evaluacionDetalle.setPeso(cri.getPeso());
							
							evaluacionDetalleService.save(evaluacionDetalle);
							
						}
						
						ideaActual.setEstatusIdeaId((long) 2);
						
						if(ideaActual.getEstatusIdeaId() != null) {
							EstatusIdeas est = estatusService.findById(ideaActual.getEstatusIdeaId());
							ideaActual.setEstatus(est.getEstatus());
							ideaActual.setFechaEstatus(new Date());
						}
						
						ideasProyectosService.save(ideaActual);
					}
					
				}
				
							
				evaluacionActual.setFechaEvaluacion(evaluacion.getFechaEvaluacion());
				evaluacionActual.setObservacion(evaluacion.getObservacion());
																						
				evaluacionUpdated=evaluacionService.save(evaluacionActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar la evaluacion en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La evaluacion ha sido actualizado con Exito!");
			response.put("evaluacion", evaluacionUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina el evaluacion
		@DeleteMapping("/evaluacion/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			List<EvaluacionIdeasDetalle> detallesEvaluacion = new ArrayList<EvaluacionIdeasDetalle>();
			List<IdeasEvaluadas> ideasEvaluadas = new ArrayList<IdeasEvaluadas>();
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				ideasEvaluadas = ideasEvaluadasService.findAllByEvaluacionId(id);
				
				for(IdeasEvaluadas ide: ideasEvaluadas) {
					detallesEvaluacion = evaluacionDetalleService.findAllByEvaluacionId(ide.getEvaluacionId());
					
					for(EvaluacionIdeasDetalle eva: detallesEvaluacion) {
						evaluacionDetalleService.delete(eva.getEvaluacionDetalleId());
					}
					
					ideasEvaluadasService.delete(ide.getIdeaEvaluadaId());
				}
				
				
				evaluacionService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar la evaluacion en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La evaluacion ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

		
		
		
		public Boolean existeIdeas(List<IdeasProyectos> ideas, IdeasProyectos ideaActual) {
			
			Boolean existe = false;
			
			for(IdeasProyectos idea: ideas) {
				if(idea.getIdeaId().equals(ideaActual.getIdeaId())){
					existe = true;
				}
			}
			
			return existe;
		}
}
