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
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorTareaStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorTareaService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class IndicadorTareaRestController {
	
	@Autowired
	private IndicadorTareaService indicadorTareaService;
	
	//Servicios Rest tabla - organizaciones
	
		private final Logger log = LoggerFactory.getLogger(IndicadorTareaRestController.class);
		
		//servicio que trae la lista de indicador
		@GetMapping("/indicadortarea")
		public List<IndicadorTareaStrategos> index (){
			return indicadorTareaService.findAll();
		}
			
		//servicio que muestra un indicador
		@GetMapping("/indicadortarea/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			IndicadorTareaStrategos indicadorTareaId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				indicadorTareaId= indicadorTareaService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(indicadorTareaId == null) {
			  response.put("mensaje", "El indicador Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<IndicadorTareaStrategos>(indicadorTareaId, HttpStatus.OK); 		
		}
		
		//servicio que crea un indicador
		@PostMapping("/indicadortarea")
		public ResponseEntity<?> create(@Valid @RequestBody IndicadorTareaStrategos indicadorN, BindingResult result) {
			
			IndicadorTareaStrategos indicadorNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				indicadorNew= indicadorTareaService.save(indicadorN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El indicador ha sido creado con Exito!");
			response.put("indicador", indicadorNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un indicador
		@PutMapping("/indicadortarea/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody IndicadorTareaStrategos indicador, BindingResult result, @PathVariable Long id) {
			IndicadorTareaStrategos indicadorActual= indicadorTareaService.findById(id);
			IndicadorTareaStrategos indicadorUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(indicadorActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el indicador ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				
				indicadorActual.setPeso(indicador.getPeso());
																							
				indicadorUpdated=indicadorTareaService.save(indicadorActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar el indicador en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El indicador ha sido actualizado con Exito!");
			response.put("indicador", indicadorUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina las indicador
		@DeleteMapping("/indicadortarea/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				indicadorTareaService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el indicador en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El indicador ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
