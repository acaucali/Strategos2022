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

import com.strategos.nueva.bancoproyecto.ideas.model.IndicadorIniciativa;
import com.strategos.nueva.bancoproyecto.ideas.service.IndicadorIniciativaService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class IndicadorIniciativaRestController {
	
	@Autowired
	private IndicadorIniciativaService indicadorIniciativaService;
	
	//Servicios Rest tabla - indicadoriniciativa
	
		private final Logger log = LoggerFactory.getLogger(IndicadorIniciativaRestController.class);
		
		//servicio que trae la lista de indicadoriniciativa 
		@GetMapping("/indicadoriniciativa")
		public List<IndicadorIniciativa> index (){
			return indicadorIniciativaService.findAll();
		}
			
		//servicio que muestra un indicadoriniciativa
		@GetMapping("/indicadoriniciativa/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			IndicadorIniciativa criterioId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				criterioId= indicadorIniciativaService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(criterioId == null) {
			  response.put("mensaje", "El indicador iniciativa Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<IndicadorIniciativa>(criterioId, HttpStatus.OK); 		
		}
		
		//servicio que crea un indicadoriniciativa
		@PostMapping("/indicadoriniciativa")
		public ResponseEntity<?> create(@Valid @RequestBody IndicadorIniciativa indicadorN, BindingResult result) {
			
			IndicadorIniciativa indicadorNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				indicadorNew= indicadorIniciativaService.save(indicadorN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El indicador iniciativa ha sido creado con Exito!");
			response.put("indicadoriniciativa", indicadorNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un indicadoriniciativa
		@PutMapping("/indicadoriniciativa/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody IndicadorIniciativa indicadoriniciativa, BindingResult result, @PathVariable Long id) {
			IndicadorIniciativa indicadoriniciativaActual= indicadorIniciativaService.findById(id);
			IndicadorIniciativa indicadoriniciativaUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(indicadoriniciativaActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el indicador iniciativa ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				indicadoriniciativaActual.setIndicadorId(indicadoriniciativa.getIndicadorId());
				indicadoriniciativaActual.setIniciativaId(indicadoriniciativa.getIniciativaId());
																						
				indicadoriniciativaUpdated=indicadorIniciativaService.save(indicadoriniciativaActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar la tarifa en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El indicador iniciativa ha sido actualizado con Exito!");
			response.put("indicadoriniciativa", indicadoriniciativaUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina el indicadoriniciativa 
		@DeleteMapping("/indicadoriniciativa/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				indicadorIniciativaService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el indicador iniciativa en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El indicador iniciativa ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
