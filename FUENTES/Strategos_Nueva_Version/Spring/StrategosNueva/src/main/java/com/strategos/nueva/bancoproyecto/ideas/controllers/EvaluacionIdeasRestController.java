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

import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeas;
import com.strategos.nueva.bancoproyecto.ideas.service.EvaluacionIdeasService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class EvaluacionIdeasRestController {
	
	@Autowired
	private EvaluacionIdeasService evaluacionService;
	
	//Servicios Rest tabla - evaluacion 
	
		private final Logger log = LoggerFactory.getLogger(EvaluacionIdeasRestController.class);
		
		//servicio que trae la lista de evaluacion 
		@GetMapping("/evaluacion")
		public List<EvaluacionIdeas> index (){
			return evaluacionService.findAll();
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
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				evaluacionNew= evaluacionService.save(evaluacionN);

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
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				evaluacionService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar la evaluacion en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La evaluacion ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
