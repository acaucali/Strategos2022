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
import com.strategos.nueva.bancoproyecto.strategos.model.ClaseIndicadoresStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.ClaseIndicadorService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class ClaseIndicadorRestController {
	
	@Autowired
	private ClaseIndicadorService claseService;
	
	//Servicios Rest tabla - clase
	
		private final Logger log = LoggerFactory.getLogger(ClaseIndicadorRestController.class);
		
		//servicio que trae la lista de clase
		@GetMapping("/clase")
		public List<ClaseIndicadoresStrategos> index (){
			return claseService.findAll();
		}
			
		//servicio que muestra un clase
		@GetMapping("/clase/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			ClaseIndicadoresStrategos claseId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				claseId= claseService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(claseId == null) {
			  response.put("mensaje", "La clase Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<ClaseIndicadoresStrategos>(claseId, HttpStatus.OK); 		
		}
		
		//servicio que crea un metodologia
		@PostMapping("/clase")
		public ResponseEntity<?> create(@Valid @RequestBody ClaseIndicadoresStrategos claseN, BindingResult result) {
			
			ClaseIndicadoresStrategos claseNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				claseNew= claseService.save(claseN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La clase ha sido creado con Exito!");
			response.put("clase", claseNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un clase
		@PutMapping("/clase/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody ClaseIndicadoresStrategos clase, BindingResult result, @PathVariable Long id) {
			ClaseIndicadoresStrategos claseActual= claseService.findById(id);
			ClaseIndicadoresStrategos claseUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(claseActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el clase ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				claseActual.setDescripcion(clase.getDescripcion());
				claseActual.setEnlaceParcial(clase.getEnlaceParcial());
				claseActual.setModificado(clase.getModificado());
				claseActual.setModificadoId(clase.getModificadoId());
				claseActual.setNombre(clase.getNombre());
				claseActual.setOrganizacionId(clase.getOrganizacionId());
				claseActual.setPadreId(clase.getPadreId());
				claseActual.setTipo(clase.getTipo());
				claseActual.setVisible(clase.getVisible());
							
																			
				claseUpdated=claseService.save(claseActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar la clase en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La clase ha sido actualizado con Exito!");
			response.put("clase", claseUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina las clase
		@DeleteMapping("/clase/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				claseService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar la clase en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La clase ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
