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

import com.strategos.nueva.bancoproyecto.ideas.model.TipoPoblacion;
import com.strategos.nueva.bancoproyecto.ideas.service.TipoPoblacionService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class TipoPoblacionRestController {
	
	@Autowired
	private TipoPoblacionService tipoPoblacionService;
	
	//Servicios Rest tabla - tipopoblacion
	
		private final Logger log = LoggerFactory.getLogger(TipoPoblacionRestController.class);
		
		//servicio que trae la lista de tipopoblacion
		@GetMapping("/tipopoblacion")
		public List<TipoPoblacion> index (){
			return tipoPoblacionService.findAll();
		}
			
		//servicio que muestra un estatus
		@GetMapping("/tipopoblacion/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			TipoPoblacion poblacionId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				poblacionId= tipoPoblacionService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(poblacionId == null) {
			  response.put("mensaje", "El tipo poblacion Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<TipoPoblacion>(poblacionId, HttpStatus.OK); 		
		}
		
		//servicio que crea un tipopoblacion
		@PostMapping("/tipopoblacion")
		public ResponseEntity<?> create(@Valid @RequestBody TipoPoblacion poblacionN, BindingResult result) {
			
			TipoPoblacion poblacionNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				poblacionNew= tipoPoblacionService.save(poblacionN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El tipo poblacion ha sido creado con Exito!");
			response.put("tipopoblacion", poblacionNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un tipopoblacion
		@PutMapping("/tipopoblacion/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody TipoPoblacion poblacion, BindingResult result, @PathVariable Long id) {
			TipoPoblacion poblacionActual= tipoPoblacionService.findById(id);
			TipoPoblacion poblacionUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(poblacionActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el tipo poblacion ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
						
				poblacionActual.setPoblacion(poblacion.getPoblacion());
																						
				poblacionUpdated=tipoPoblacionService.save(poblacionActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar el tipo poblacion en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El tipo poblacion ha sido actualizado con Exito!");
			response.put("tipopoblacion", poblacionUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina el tipopoblacion
		@DeleteMapping("/tipopoblacion/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				tipoPoblacionService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el tipo poblacion en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El tipo poblacion ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
