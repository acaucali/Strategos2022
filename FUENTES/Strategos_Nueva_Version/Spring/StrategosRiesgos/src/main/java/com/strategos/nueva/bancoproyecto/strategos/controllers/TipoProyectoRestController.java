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
import com.strategos.nueva.bancoproyecto.strategos.model.TipoProyectoStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.TipoProyectoService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class TipoProyectoRestController {
	
	@Autowired
	private TipoProyectoService tipoProyectoService;
	
	//Servicios Rest tabla - tipo proyecto
	
		private final Logger log = LoggerFactory.getLogger(TipoProyectoRestController.class);
		
		//servicio que trae la lista de tipo proyecto
		@GetMapping("/tipoproyecto")
		public List<TipoProyectoStrategos> index (){
			return tipoProyectoService.findAll();
		}
			
		//servicio que muestra un tipo proyecto
		@GetMapping("/tipoproyecto/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			TipoProyectoStrategos tipoproyectoId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				tipoproyectoId= tipoProyectoService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(tipoproyectoId == null) {
			  response.put("mensaje", "El tipo proyecto Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<TipoProyectoStrategos>(tipoproyectoId, HttpStatus.OK); 		
		}
		
		//servicio que crea un tipo proyecto
		@PostMapping("/tipoproyecto")
		public ResponseEntity<?> create(@Valid @RequestBody TipoProyectoStrategos tipoproyectoN, BindingResult result) {
			
			TipoProyectoStrategos tipoproyectoNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				tipoproyectoNew= tipoProyectoService.save(tipoproyectoN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El tipo proyecto ha sido creado con Exito!");
			response.put("tipoproyecto", tipoproyectoNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un tipo proyecto
		@PutMapping("/tipoproyecto/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody TipoProyectoStrategos tipoproyecto, BindingResult result, @PathVariable Long id) {
			TipoProyectoStrategos tipoproyectoActual= tipoProyectoService.findById(id);
			TipoProyectoStrategos tipoproyectoUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(tipoproyectoActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el tipo proyecto ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				tipoproyectoActual.setNombre(tipoproyecto.getNombre());
					
																			
				tipoproyectoUpdated=tipoProyectoService.save(tipoproyectoActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar el tipo proyecto en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El tipo proyecto ha sido actualizado con Exito!");
			response.put("tipoproyecto", tipoproyectoUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina las tipo proyecto
		@DeleteMapping("/tipoproyecto/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				tipoProyectoService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el tipo proyecto en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El tipo proyecto ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
