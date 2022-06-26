package com.strategos.nueva.bancoproyecto.ideas.controllers;

import java.util.ArrayList;
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

import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.Proyectos;
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosService;
import com.strategos.nueva.bancoproyectos.model.util.FIltroIdea;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class ProyectoRestController {
	
	@Autowired
	private ProyectosService proyectoService;
	
	//Servicios Rest tabla - proyecto
	
		private final Logger log = LoggerFactory.getLogger(ProyectoRestController.class);
		
		//servicio que trae la lista de proyecto
		@GetMapping("/proyecto")
		public List<Proyectos> index (){
			return proyectoService.findAll();
		}
		
		//servicio que trae la lista de idea
		@GetMapping("/proyecto/filtro/{orgId}/{tipoId}/{estatusId}/{anio}/{historico}")
		public List<Proyectos> indexFiltro(@PathVariable String orgId, @PathVariable String tipoId, @PathVariable String estatusId, @PathVariable String anio, @PathVariable String historico){
					
					FIltroIdea filtro = new FIltroIdea();
					
					Long org;
					Long tipId;
					Long estId;
					String ani;
					Boolean histor;
					
					List<Proyectos> proyectosFin = new ArrayList();
					
					if(!orgId.equals("undefined")) {
						if(!orgId.equals("0")) {
							org = Long.parseLong(orgId);
							filtro.setOrganizacionId(org);
						}
						
					}
					if(!tipoId.equals("undefined")) {
						if(!tipoId.equals("0")) {
							tipId = Long.parseLong(tipoId);
							filtro.setPropuestaId(tipId);;
						}
						
					}
					if(!estatusId.equals("undefined")) {
						if(!estatusId.equals("0")) {
							estId = Long.parseLong(estatusId);
							filtro.setEstatusId(estId);
						}
						
					}
					if(!anio.equals("undefined")) {
						if(!anio.equals("0")) {
							ani = anio;
							filtro.setAnio(ani);
						}		
						
					}
					if(!historico.equals("undefined") ) {
						histor = Boolean.getBoolean(historico);
						filtro.setHistorico(histor);
					}
					
					proyectosFin = proyectoService.queryFiltros(filtro);
					
					System.out.println(" "+orgId + tipoId+ estatusId+ anio+ historico);
							
					return proyectosFin;
		}
		
		//servicio que trae la lista de idea
		@GetMapping("/proyecto/org/{id}")
		public List<Proyectos> index(@PathVariable Long id){
					
			List<Proyectos> proyectosOrg = proyectoService.findAllByDependenciaId(id); 								
			return proyectosOrg;
		}
			
		//servicio que muestra un proyecto
		@GetMapping("/proyecto/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			Proyectos proyectoId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				proyectoId= proyectoService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(proyectoId == null) {
			  response.put("mensaje", "El proyecto Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Proyectos>(proyectoId, HttpStatus.OK); 		
		}
		
		//servicio que crea un proyecto
		@PostMapping("/proyecto")
		public ResponseEntity<?> create(@Valid @RequestBody Proyectos proyectoN, BindingResult result) {
			
			Proyectos proyectoNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				//sin iniciar
				//crear estatus preproyecto
				//cambiar idea a preproyecto
				proyectoNew= proyectoService.save(proyectoN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El proyecto ha sido creado con Exito!");
			response.put("proyecto", proyectoNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un proyecto
		@PutMapping("/proyecto/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody Proyectos proyecto, BindingResult result, @PathVariable Long id) {
			Proyectos proyectoActual= proyectoService.findById(id);
			Proyectos proyectoUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(proyectoActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el proyecto ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
							
				proyectoActual.setAlcance(proyecto.getAlcance()); //idea
				proyectoActual.setAnioFormulacion(proyecto.getAnioFormulacion());//idea
				proyectoActual.setAntecedentes(proyecto.getAntecedentes());
				proyectoActual.setCobertura(proyecto.getCobertura());
				proyectoActual.setCodigoBdp(proyecto.getCodigoBdp());
				proyectoActual.setContactoEmail(proyecto.getContactoEmail());//idea
				proyectoActual.setContactoTelefono(proyecto.getContactoTelefono());//idea
				proyectoActual.setCostoEstimado(proyecto.getCostoEstimado());
				proyectoActual.setDependenciaId(proyecto.getDependenciaId());//idea
				proyectoActual.setDependenciaLider(proyecto.getDependenciaLider());//idea
				proyectoActual.setDependenciasEstrategicas(proyecto.getDependenciasEstrategicas());
				proyectoActual.setDuracion(proyecto.getDuracion()); //>0
				proyectoActual.setEstatusId(proyecto.getEstatusId());
				proyectoActual.setFechaEstatus(proyecto.getFechaEstatus());
				proyectoActual.setFechaFormulacion(proyecto.getFechaFormulacion());//idea
				proyectoActual.setFechaRadicacion(proyecto.getFechaRadicacion());
				proyectoActual.setFinanciacion(proyecto.getFinanciacion());//idea
				proyectoActual.setHistorico(proyecto.getHistorico());
				proyectoActual.setJustificacion(proyecto.getJustificacion());
				proyectoActual.setMetodologiaId(proyecto.getMetodologiaId());//idea
				proyectoActual.setNombreProyecto(proyecto.getNombreProyecto());//idea
				proyectoActual.setObjetivoGeneral(proyecto.getObjetivoGeneral());//idea
				proyectoActual.setPertinencia(proyecto.getPertinencia());
				proyectoActual.setProblematica(proyecto.getProblematica());//idea
				proyectoActual.setProfesionalResponsable(proyecto.getProfesionalResponsable());//idea
				proyectoActual.setIdeaId(proyecto.getIdeaId());//idea
				proyectoActual.setRolesParticipantes(proyecto.getRolesParticipantes());
				proyectoActual.setSociosEstrategicos(proyecto.getSociosEstrategicos());
				proyectoActual.setTipoObjetivoId(proyecto.getTipoObjetivoId());//idea
				proyectoActual.setTipoProyectoId(proyecto.getTipoProyectoId());
																			
				proyectoUpdated=proyectoService.save(proyectoActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar el proyecto en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El proyecto ha sido actualizado con Exito!");
			response.put("proyecto", proyectoUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina el proyecto
		@DeleteMapping("/proyecto/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				proyectoService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el proyecto en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El proyecto ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
