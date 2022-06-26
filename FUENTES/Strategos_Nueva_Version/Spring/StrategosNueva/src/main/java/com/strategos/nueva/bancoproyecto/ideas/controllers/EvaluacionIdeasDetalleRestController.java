package com.strategos.nueva.bancoproyecto.ideas.controllers;

import java.util.ArrayList;
import java.util.Collections;
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

import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasEvaluadas;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyecto.ideas.service.EvaluacionIdeasDetalleService;
import com.strategos.nueva.bancoproyecto.ideas.service.IdeasEvaluadasService;
import com.strategos.nueva.bancoproyecto.ideas.service.IdeasProyectosService;
import com.strategos.nueva.bancoproyectos.model.util.DatoIdea;
import com.strategos.nueva.bancoproyectos.model.util.DatoMedicion;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class EvaluacionIdeasDetalleRestController {
	
	@Autowired
	private EvaluacionIdeasDetalleService evaluacionDetalleService;
	
	@Autowired
	private IdeasEvaluadasService ideasEvaluadasService;
	
	@Autowired
	private IdeasProyectosService ideasProyectosService;
	
	//Servicios Rest tabla - evaluacion detalle
	
		private final Logger log = LoggerFactory.getLogger(EvaluacionIdeasDetalleRestController.class);
		
		//servicio que trae la lista de evaluacion 
		@GetMapping("/evaluaciondetalle")
		public List<EvaluacionIdeasDetalle> index (){
			return evaluacionDetalleService.findAll();
		}
		
		//servicio que trae la lista de evaluacion 
		@GetMapping("/evaluaciondetalle/ideasevaluadas/{evaId}")
		public List<Long> getIdeasEvaluadas(@PathVariable Long evaId){
			
			List<IdeasEvaluadas> ideasEvaluadas = new ArrayList<IdeasEvaluadas>();
			ideasEvaluadas = ideasEvaluadasService.findAllByEvaluacionId(evaId);
			List<Long> ids = new ArrayList<Long>();
			
			List <String> ideas =ordenarIdeas(ideasEvaluadas);
									
			for(String idea: ideas) {
				String carId = idea.substring(idea.indexOf("-") + 1, idea.length());				
				ids.add(new Long(carId));								
			}
			
			return ids;
		}
				//servicio que trae la lista de evaluacion 
		@GetMapping("/evaluaciondetalle/encabezados/{evaId}")
		public List<DatoIdea> getEncabezados(@PathVariable Long evaId){
			
			List<DatoIdea> datos = new ArrayList<DatoIdea>();
			List<EvaluacionIdeasDetalle> evaluacionesDetalle = evaluacionDetalleService.findAllByEvaluacionId(evaId);
			List<IdeasEvaluadas> ideasEvaluadas = new ArrayList<IdeasEvaluadas>();			
			ideasEvaluadas = ideasEvaluadasService.findAllByEvaluacionId(evaId);
			
			String cadenaTitulo ="";
			
			for(IdeasEvaluadas ide: ideasEvaluadas) {
				
				IdeasProyectos idea = ideasProyectosService.findById(ide.getIdeaId());
				
				DatoIdea dato = new DatoIdea();
				dato.setCampo("idea");
				dato.setValor("Nombre Idea");
				dato.setTamanio("300");
				
				datos.add(dato);
								
				for(EvaluacionIdeasDetalle eva: evaluacionesDetalle) {
					if(eva.getIdeaId().equals(ide.getIdeaId())) {
						
						dato = new DatoIdea();
						dato.setCampo("criterio");
						dato.setValor(eva.getCriterio()+" ("+eva.getPeso()+" %)");
						dato.setTamanio("150");
						
						datos.add(dato);
					}
				}
				
				dato = new DatoIdea();
				dato.setCampo("total");
				dato.setValor("Puntaje Total");
				dato.setTamanio("80");
				
				datos.add(dato);
				
				return datos;
				
			}	
			
			return datos;
		}
		
		@GetMapping("/evaluaciondetalle/datosmediciones/{evaId}")
		public List<DatoMedicion> getDatosMediciones(@PathVariable Long evaId){
			
			List<DatoMedicion> datos = new ArrayList<DatoMedicion>();
			List<EvaluacionIdeasDetalle> evaluacionesDetalle = evaluacionDetalleService.findAllByEvaluacionId(evaId);
			List<IdeasEvaluadas> ideasEvaluadas = new ArrayList<IdeasEvaluadas>();		
			List<Long> ids = new ArrayList<Long>();
			ideasEvaluadas = ideasEvaluadasService.findAllByEvaluacionId(evaId);
			
			List <String> ideas =ordenarIdeas(ideasEvaluadas);
									
			for(String idea: ideas) {
				String carId = idea.substring(idea.indexOf("-") + 1, idea.length());				
				ids.add(new Long(carId));								
			}
			
			for(Long id: ids) {
				IdeasProyectos idea = ideasProyectosService.findById(id);
				
				DatoMedicion dato = new DatoMedicion();
				dato.setCampo("idea");
				dato.setValor(idea.getNombreIdea());
				dato.setTamanio("300");
				dato.setPeso("0");
				dato.setId(idea.getIdeaId());
				dato.setIdeaId(idea.getIdeaId());
				
				datos.add(dato);
								
				for(EvaluacionIdeasDetalle eva: evaluacionesDetalle) {
					if(eva.getIdeaId().equals(id)) {
						
						dato = new DatoMedicion();
						dato.setCampo("criterio");
						if(eva.getValorEvaluado() != null) {
							dato.setValor(""+eva.getValorEvaluado());
						}else {
							dato.setValor("");
						}
						
						dato.setTamanio("150");
						Double peso = (eva.getPeso()/100);
						dato.setPeso(""+peso);
						dato.setId(eva.getEvaluacionDetalleId());
						dato.setIdeaId(idea.getIdeaId());
						
						datos.add(dato);
					}
				}
				
				dato = new DatoMedicion();
				dato.setCampo("total");
				if(idea.getValorUltimaEvaluacion() != null) {
					dato.setValor(""+idea.getValorUltimaEvaluacion());
				}else {
					dato.setValor("");
				}
				
				dato.setTamanio("80");
				dato.setPeso("0");
				dato.setId(evaId);
				dato.setIdeaId(idea.getIdeaId());
				
				datos.add(dato);
			}
			
			return datos;
		}
		
		//servicio que trae la lista de evaluacion 
		@GetMapping("/evaluaciondetalle/datos/{evaId}")
		public List<DatoIdea> getDatos(@PathVariable Long evaId){
			
			List<DatoIdea> datos = new ArrayList<DatoIdea>();
			List<EvaluacionIdeasDetalle> evaluacionesDetalle = evaluacionDetalleService.findAllByEvaluacionId(evaId);
			List<IdeasEvaluadas> ideasEvaluadas = new ArrayList<IdeasEvaluadas>();			
			ideasEvaluadas = ideasEvaluadasService.findAllByEvaluacionId(evaId);
			
			String cadenaTitulo ="";
			
			for(IdeasEvaluadas ide: ideasEvaluadas) {
				
				IdeasProyectos idea = ideasProyectosService.findById(ide.getIdeaId());
				
				String cadena =""+idea.getNombreIdea()+",";
				for(EvaluacionIdeasDetalle eva: evaluacionesDetalle) {
					if(eva.getIdeaId().equals(ide.getIdeaId())) {
						
						cadena += eva.getCriterio()+" " + eva.getPeso()+","; 
					}
				}
				
				
			}			
			
			return datos;
		}
		
		
			
		//servicio que muestra un evaluacion
		@GetMapping("/evaluaciondetalle/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			EvaluacionIdeasDetalle evaluacionId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				evaluacionId= evaluacionDetalleService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(evaluacionId == null) {
			  response.put("mensaje", "El detalle de la evaluación Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<EvaluacionIdeasDetalle>(evaluacionId, HttpStatus.OK); 		
		}
		
		//servicio que crea un evaluacion
		@PostMapping("/evaluaciondetalle")
		public ResponseEntity<?> create(@Valid @RequestBody EvaluacionIdeasDetalle evaluacionN, BindingResult result) {
			
			EvaluacionIdeasDetalle evaluacionNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				evaluacionNew= evaluacionDetalleService.save(evaluacionN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El detalle evaluacion ha sido creado con Exito!");
			response.put("evaluaciondetalle", evaluacionNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un evaluacion 
		@PutMapping("/evaluaciondetalle/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody EvaluacionIdeasDetalle evaluacion, BindingResult result, @PathVariable Long id) {
			EvaluacionIdeasDetalle evaluacionActual= evaluacionDetalleService.findById(id);
			EvaluacionIdeasDetalle evaluacionUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(evaluacionActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el detalle evaluacion ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				evaluacionActual.setCriterio(evaluacion.getCriterio());
				evaluacionActual.setIdeaId(evaluacion.getIdeaId());
				evaluacionActual.setPeso(evaluacion.getPeso());
																			
				evaluacionUpdated=evaluacionDetalleService.save(evaluacionActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar el detalle evaluacion en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El detalle evaluacion ha sido actualizado con Exito!");
			response.put("evaluaciondetalle", evaluacionUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina la evaluacion
		@DeleteMapping("/evaluaciondetalle/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				evaluacionDetalleService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el detalle evaluacion en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El detalle evaluacion ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}
		
		//servicio que crea un evaluacion
		@PutMapping("/evaluaciondetalle/mediciones/{id}")
		public ResponseEntity<?> registrarMedicion(@Valid @RequestBody List<DatoMedicion> mediciones, BindingResult result, @PathVariable Long id) {
					
			List<IdeasEvaluadas> ideasEvaluadas = new ArrayList<IdeasEvaluadas>();	
			Map<String, Object> response = new HashMap<>();
			
			ideasEvaluadas = ideasEvaluadasService.findAllByEvaluacionId(id);
			
			if(result.hasErrors()) {
						
				List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
						
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
					
			try {
				
				for(IdeasEvaluadas ide: ideasEvaluadas) {
					
					for(DatoMedicion dato: mediciones) {
						
						if(dato.getIdeaId().equals(ide.getIdeaId())) {
							if(dato.getCampo().equals("criterio")) {
								EvaluacionIdeasDetalle detalle = evaluacionDetalleService.findById(dato.getId());
								detalle.setValorEvaluado(Double.parseDouble(dato.getValor()));
								evaluacionDetalleService.save(detalle);
							}else if(dato.getCampo().equals("total")) {
								IdeasProyectos idea = ideasProyectosService.findById(dato.getIdeaId());
								idea.setFechaUltimaEvaluacion(new Date());
								idea.setValorUltimaEvaluacion(Double.parseDouble(dato.getValor()));
								ideasProyectosService.save(idea);
							}
						}
					}	
				}
								
							
						
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "Los datos de evaluación se han registrado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		

	public List<String> ordenarIdeas(List<IdeasEvaluadas> ideasEvaluadas){
		List<String> puntajes = new ArrayList<>();
		
		for(IdeasEvaluadas ide: ideasEvaluadas) {
			IdeasProyectos idea = ideasProyectosService.findById(ide.getIdeaId());
			if(idea.getValorUltimaEvaluacion() != null) {
				puntajes.add(idea.getValorUltimaEvaluacion().toString()+"-"+idea.getIdeaId());
			}else {
				puntajes.add("0"+"-"+idea.getIdeaId());
			}
			
		}		
		
		System.out.println("Before Sorting: "+ puntajes);   
		
		Collections.sort(puntajes, Collections.reverseOrder()); 
				
		System.out.println("After Sorting: "+ puntajes);   
		
		return puntajes;
	}
		

}
