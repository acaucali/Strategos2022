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
import com.strategos.nueva.bancoproyecto.strategos.model.SerieIndicadorStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.SerieIndicadorService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class SerieIndicadorRestController {
	
	@Autowired
	private SerieIndicadorService serieIndicadorService;
	
	//Servicios Rest tabla - serieindicador
	
		private final Logger log = LoggerFactory.getLogger(SerieIndicadorRestController.class);
		
		//servicio que trae la lista de serieindicador
		@GetMapping("/serieindicador")
		public List<SerieIndicadorStrategos> index (){
			return serieIndicadorService.findAll();
		}
			
		//servicio que muestra un serieindicador
		@GetMapping("/serieindicador/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			SerieIndicadorStrategos serieIndicadorId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				serieIndicadorId= serieIndicadorService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(serieIndicadorId == null) {
			  response.put("mensaje", "La serie indicador Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<SerieIndicadorStrategos>(serieIndicadorId, HttpStatus.OK); 		
		}
		
		//servicio que crea un serieindicador
		@PostMapping("/serieindicador")
		public ResponseEntity<?> create(@Valid @RequestBody SerieIndicadorStrategos serieindicadorN, BindingResult result) {
			
			SerieIndicadorStrategos serieindicadorNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				serieindicadorNew= serieIndicadorService.save(serieindicadorN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La serie indicador ha sido creado con Exito!");
			response.put("serieindicador", serieindicadorNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un serieindicador
		@PutMapping("/serieindicador/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody SerieIndicadorStrategos serieindicador, BindingResult result, @PathVariable Long id) {
			SerieIndicadorStrategos serieindicadorActual= serieIndicadorService.findById(id);
			SerieIndicadorStrategos serieindicadorUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(serieindicadorActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el serie indicador ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				
				serieindicadorActual.setFechaBloqueo(serieindicador.getFechaBloqueo());
				serieindicadorActual.setNaturaleza(serieindicador.getNaturaleza());
				
																			
				serieindicadorUpdated=serieIndicadorService.save(serieindicadorActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar la serie indicador en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La serie indicador ha sido actualizado con Exito!");
			response.put("serieindicador", serieindicadorUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina las serieindicador
		@DeleteMapping("/serieindicador/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				serieIndicadorService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar la serie indicador en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La serie indicador ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
