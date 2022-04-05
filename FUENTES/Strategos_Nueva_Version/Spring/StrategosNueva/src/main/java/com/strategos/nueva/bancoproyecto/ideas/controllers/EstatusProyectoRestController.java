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

import com.strategos.nueva.bancoproyecto.ideas.model.EstatusProyecto;
import com.strategos.nueva.bancoproyecto.ideas.service.EstatusProyectoService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class EstatusProyectoRestController {
	
	@Autowired
	private EstatusProyectoService estatusProyectoService;
	
	//Servicios Rest tabla - estatus 
	
		private final Logger log = LoggerFactory.getLogger(EstatusProyectoRestController.class);
		
		//servicio que trae la lista de estatus
		@GetMapping("/estatusproyecto")
		public List<EstatusProyecto> index (){
			return estatusProyectoService.findAll();
		}
			
		//servicio que muestra un estatus
		@GetMapping("/estatusproyecto/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			EstatusProyecto estatusId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				estatusId= estatusProyectoService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(estatusId == null) {
			  response.put("mensaje", "El estatus proyecto Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<EstatusProyecto>(estatusId, HttpStatus.OK); 		
		}
		
		//servicio que crea un estatus
		@PostMapping("/estatusproyecto")
		public ResponseEntity<?> create(@Valid @RequestBody EstatusProyecto estatusProN, BindingResult result) {
			
			EstatusProyecto estatusProNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				estatusProNew= estatusProyectoService.save(estatusProN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El estatus proyecto ha sido creado con Exito!");
			response.put("estatuspro", estatusProNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un estatus
		@PutMapping("/estatusproyecto/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody EstatusProyecto estatusPro, BindingResult result, @PathVariable Long id) {
			EstatusProyecto estatusProActual= estatusProyectoService.findById(id);
			EstatusProyecto estatusProUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(estatusProActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el estatus proyecto ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
											
				estatusProActual.setEstatus(estatusPro.getEstatus());
				
																			
				estatusProUpdated=estatusProyectoService.save(estatusProActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar el estatus proyecto en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El estatus proyecto ha sido actualizado con Exito!");
			response.put("estatuspro", estatusProUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina el estatus
		@DeleteMapping("/estatusproyecto/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				estatusProyectoService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el estatus proyecto en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El estatus proyecto ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
