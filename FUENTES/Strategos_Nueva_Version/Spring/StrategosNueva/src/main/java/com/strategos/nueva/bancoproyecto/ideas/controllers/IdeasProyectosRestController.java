package com.strategos.nueva.bancoproyecto.ideas.controllers;

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
import com.strategos.nueva.bancoproyecto.ideas.service.IdeasProyectosService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class IdeasProyectosRestController {
	
	@Autowired
	private IdeasProyectosService ideasProyectosService;
	
	//Servicios Rest tabla - idea
	
		private final Logger log = LoggerFactory.getLogger(IdeasProyectosRestController.class);
		
		//servicio que trae la lista de idea
		@GetMapping("/idea")
		public List<IdeasProyectos> index (){
			return ideasProyectosService.findAll();
		}
			
		//servicio que muestra un idea
		@GetMapping("/idea/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			IdeasProyectos ideasProyectosId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				ideasProyectosId= ideasProyectosService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(ideasProyectosId == null) {
			  response.put("mensaje", "La idea proyecto Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<IdeasProyectos>(ideasProyectosId, HttpStatus.OK); 		
		}
		
		//servicio que crea un idea
		@PostMapping("/idea")
		public ResponseEntity<?> create(@Valid @RequestBody IdeasProyectos ideasProyectoN, BindingResult result) {
			
			IdeasProyectos ideasProyectosNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				ideasProyectosNew= ideasProyectosService.save(ideasProyectoN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La idea proyectos ha sido creado con Exito!");
			response.put("ideaproyectos", ideasProyectosNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un idea
		@PutMapping("/idea/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody IdeasProyectos ideasProyectos, BindingResult result, @PathVariable Long id) {
			IdeasProyectos ideasProyectosActual= ideasProyectosService.findById(id);
			IdeasProyectos ideasProyectosUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(ideasProyectosActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, la idea proyecto ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				ideasProyectosActual.setAlineacionPlan(ideasProyectos.getAlineacionPlan());
				ideasProyectosActual.setAnioFormulacion(ideasProyectos.getAnioFormulacion());
				ideasProyectosActual.setCapacidadTecnica(ideasProyectos.getCapacidadTecnica());
				ideasProyectosActual.setDependenciaId(ideasProyectos.getDependenciaId());
				ideasProyectosActual.setDescripcionIdea(ideasProyectos.getDescripcionIdea());
				ideasProyectosActual.setDocumentos(ideasProyectos.getDocumentos());
				ideasProyectosActual.setEstatus(ideasProyectos.getEstatus());
				ideasProyectosActual.setEstatusIdea(ideasProyectos.getEstatusIdea());
				ideasProyectosActual.setFechaEstatus(ideasProyectos.getFechaEstatus());
				ideasProyectosActual.setFechaIdea(ideasProyectos.getFechaIdea());
				ideasProyectosActual.setFechaUltimaEvaluacion(ideasProyectos.getFechaUltimaEvaluacion());
				ideasProyectosActual.setFinanciacion(ideasProyectos.getFinanciacion());
				ideasProyectosActual.setFocalizacion(ideasProyectos.getFocalizacion());
				ideasProyectosActual.setHistorico(ideasProyectos.getHistorico());
				ideasProyectosActual.setImpacto(ideasProyectos.getImpacto());
				ideasProyectosActual.setNombreIdea(ideasProyectos.getNombreIdea());
				ideasProyectosActual.setObservaciones(ideasProyectos.getObservaciones());
				ideasProyectosActual.setPersonaContactoDatos(ideasProyectos.getPersonaContactoDatos());
				ideasProyectosActual.setPoblacion(ideasProyectos.getPoblacion());
				ideasProyectosActual.setProblematica(ideasProyectos.getProblematica());
				ideasProyectosActual.setProyectosEjecutados(ideasProyectos.getProyectosEjecutados());
				ideasProyectosActual.setTipoObjetivo(ideasProyectos.getTipoObjetivo());
				ideasProyectosActual.setTipoPropuesta(ideasProyectos.getTipoPropuesta());
				ideasProyectosActual.setValorUltimaEvaluacion(ideasProyectos.getValorUltimaEvaluacion());				
				
				
																			
				ideasProyectosUpdated=ideasProyectosService.save(ideasProyectosActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar la idea proyecto en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La idea proyecto ha sido actualizado con Exito!");
			response.put("ideasproyectos", ideasProyectosUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina el idea
		@DeleteMapping("/idea/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				ideasProyectosService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar la idea proyecto en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La idea proyecto ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
