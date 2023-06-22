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
import com.strategos.nueva.bancoproyecto.strategos.model.SerieTiempoStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.SerieTiempoService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class SerieTiempoRestController {
	
	@Autowired
	private SerieTiempoService serieTiempoService;
	
	//Servicios Rest tabla - serietiempo
	
		private final Logger log = LoggerFactory.getLogger(SerieTiempoRestController.class);
		
		//servicio que trae la lista de serietiempo
		@GetMapping("/serietiempo")
		public List<SerieTiempoStrategos> index (){
			return serieTiempoService.findAll();
		}
			
		//servicio que muestra un serietiempo
		@GetMapping("/serietiempo/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			SerieTiempoStrategos serieTiempoId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				serieTiempoId= serieTiempoService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(serieTiempoId == null) {
			  response.put("mensaje", "La serie tiempo Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<SerieTiempoStrategos>(serieTiempoId, HttpStatus.OK); 		
		}
		
		//servicio que crea un serietiempo
		@PostMapping("/serietiempo")
		public ResponseEntity<?> create(@Valid @RequestBody SerieTiempoStrategos serieTiempoN, BindingResult result) {
			
			SerieTiempoStrategos serieTiempoNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				serieTiempoNew= serieTiempoService.save(serieTiempoN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La serie tiempo ha sido creado con Exito!");
			response.put("serietiempo", serieTiempoNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un serietiempo
		@PutMapping("/serietiempo/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody SerieTiempoStrategos serietiempo, BindingResult result, @PathVariable Long id) {
			SerieTiempoStrategos serieTiempoActual= serieTiempoService.findById(id);
			SerieTiempoStrategos serieTiempoUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(serieTiempoActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el serie tiempo ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				
			
				serieTiempoActual.setNombre(serietiempo.getNombre());
				serieTiempoActual.setFija(serietiempo.getFija());
				serieTiempoActual.setIdentificador(serietiempo.getIdentificador());
				serieTiempoActual.setOculta(serietiempo.getOculta());
				serieTiempoActual.setPreseleccionada(serietiempo.getPreseleccionada());
			
																			
				serieTiempoUpdated=serieTiempoService.save(serieTiempoActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar la serie tiempo en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La serie tiempo ha sido actualizado con Exito!");
			response.put("serietiempo", serieTiempoUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina las serietiempo
		@DeleteMapping("/serietiempo/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				serieTiempoService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar la serie tiempo en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La serie tiempo ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
