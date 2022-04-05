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

import com.strategos.nueva.bancoproyecto.ideas.model.CriteriosEvaluacion;
import com.strategos.nueva.bancoproyecto.ideas.service.CriteriosEvaluacionService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class CriteriosEvaluacionRestController {
	
	@Autowired
	private CriteriosEvaluacionService criteriosEvaluacionService;
	
	//Servicios Rest tabla - criterio 
	
		private final Logger log = LoggerFactory.getLogger(CriteriosEvaluacionRestController.class);
		
		//servicio que trae la lista de criterio 
		@GetMapping("/criterios")
		public List<CriteriosEvaluacion> index (){
			return criteriosEvaluacionService.findAll();
		}
			
		//servicio que muestra un criterio 
		@GetMapping("/criterios/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			CriteriosEvaluacion criterioId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				criterioId= criteriosEvaluacionService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(criterioId == null) {
			  response.put("mensaje", "El criterio Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<CriteriosEvaluacion>(criterioId, HttpStatus.OK); 		
		}
		
		//servicio que crea un criterio 
		@PostMapping("/criterios")
		public ResponseEntity<?> create(@Valid @RequestBody CriteriosEvaluacion criterioaN, BindingResult result) {
			
			CriteriosEvaluacion criterioNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				criterioNew= criteriosEvaluacionService.save(criterioaN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El criterio ha sido creado con Exito!");
			response.put("criterio", criterioNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un criterio 
		@PutMapping("/criterios/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody CriteriosEvaluacion criterio, BindingResult result, @PathVariable Long id) {
			CriteriosEvaluacion criterioActual= criteriosEvaluacionService.findById(id);
			CriteriosEvaluacion criterioUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(criterioActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el criterio ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
							
				criterioActual.setControl(criterio.getControl());
				criterioActual.setPeso(criterio.getPeso());
																			
				criterioUpdated=criteriosEvaluacionService.save(criterioActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar la tarifa en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El criterio ha sido actualizado con Exito!");
			response.put("criterio", criterioUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina el criterio 
		@DeleteMapping("/criterios/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				criteriosEvaluacionService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el criterio en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El criterio ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
