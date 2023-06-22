package com.strategos.nueva.bancoproyecto.strategos.controllers;

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

import com.strategos.nueva.bancoproyecto.ideas.service.TiposPropuestasService;
import com.strategos.nueva.bancoproyecto.strategos.model.UnidadStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.UnidadService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class UnidadRestController {
	
	@Autowired
	private UnidadService unidadService;
	
	//Servicios Rest tabla - unidad
	
		private final Logger log = LoggerFactory.getLogger(UnidadRestController.class);
		
		//servicio que trae la lista de unidad
		@GetMapping("/unidad")
		public List<UnidadStrategos> index (){
			return unidadService.findAll();
		}
			
		//servicio que muestra un unidad
		@GetMapping("/unidad/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			UnidadStrategos unidadId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				unidadId= unidadService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(unidadId == null) {
			  response.put("mensaje", "La unidad Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<UnidadStrategos>(unidadId, HttpStatus.OK); 		
		}
		
		//servicio que crea un unidad
		@PostMapping("/unidad")
		public ResponseEntity<?> create(@Valid @RequestBody UnidadStrategos unidadN, BindingResult result) {
			
			UnidadStrategos unidadNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				unidadNew= unidadService.save(unidadN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La unidad ha sido creado con Exito!");
			response.put("unidad", unidadNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un unidad
		@PutMapping("/unidad/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody UnidadStrategos unidad, BindingResult result, @PathVariable Long id) {
			UnidadStrategos unidadActual= unidadService.findById(id);
			UnidadStrategos unidadUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(unidadActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, la unidad ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
								
				unidadActual.setNombre(unidad.getNombre());
				unidadActual.setTipo(unidad.getTipo());			
																			
				unidadUpdated=unidadService.save(unidadActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar la unidad en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La unidad ha sido actualizado con Exito!");
			response.put("unidad", unidadUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina la unidad
		@DeleteMapping("/unidad/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				unidadService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar la unidad en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La unidad ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
