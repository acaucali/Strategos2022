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

import com.strategos.nueva.bancoproyecto.ideas.model.TiposPropuestas;
import com.strategos.nueva.bancoproyecto.ideas.service.TiposPropuestasService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class TiposPropuestasRestController {
	
	@Autowired
	private TiposPropuestasService tiposPropuestasService;
	
	//Servicios Rest tabla - tipos propuestas 
	
		private final Logger log = LoggerFactory.getLogger(TiposPropuestasRestController.class);
		
		//servicio que trae la lista de tipos propuestas
		@GetMapping("/tipopropuesta")
		public List<TiposPropuestas> index (){
			return tiposPropuestasService.findAll();
		}
			
		//servicio que muestra un tipos propuestas
		@GetMapping("/tipopropuesta/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			TiposPropuestas tiposPropuestasId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				tiposPropuestasId= tiposPropuestasService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(tiposPropuestasId == null) {
			  response.put("mensaje", "El tipo propuesta Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<TiposPropuestas>(tiposPropuestasId, HttpStatus.OK); 		
		}
		
		//servicio que crea un tipos propuestas
		@PostMapping("/tipopropuesta")
		public ResponseEntity<?> create(@Valid @RequestBody TiposPropuestas tiposPropuestasN, BindingResult result) {
			
			TiposPropuestas tipoPropuestasNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				tipoPropuestasNew= tiposPropuestasService.save(tiposPropuestasN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El tipo propuestas ha sido creado con Exito!");
			response.put("tipopropuesta", tipoPropuestasNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un tipos propuestas
		@PutMapping("/tipopropuesta/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody TiposPropuestas tiposPropuestas, BindingResult result, @PathVariable Long id) {
			TiposPropuestas tiposPropuestasActual= tiposPropuestasService.findById(id);
			TiposPropuestas tiposPropuestasUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(tiposPropuestasActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el tipo propuesta ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
						
				tiposPropuestasActual.setTipoPropuesta(tiposPropuestas.getTipoPropuesta());
				
																			
				tiposPropuestasUpdated=tiposPropuestasService.save(tiposPropuestasActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar el tipo propuesta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El tipo propuesta ha sido actualizado con Exito!");
			response.put("tipopropuesta", tiposPropuestasUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina el tipos propuestas
		@DeleteMapping("/tipopropuesta/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				tiposPropuestasService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el tipo propuesta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El tipo propuesta ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
