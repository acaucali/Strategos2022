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

import com.strategos.nueva.bancoproyecto.ideas.model.IniciativaPerspectiva;
import com.strategos.nueva.bancoproyecto.ideas.service.IniciativaPerspectivaService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class IniciativaPerspectivaRestController {
	
	@Autowired
	private IniciativaPerspectivaService iniciativaPerspectivaService;
	
	//Servicios Rest tabla - criterio 
	
		private final Logger log = LoggerFactory.getLogger(IniciativaPerspectivaRestController.class);
		
		//servicio que trae la lista de iniciativaperspectiva
		@GetMapping("/iniciativaperspectiva")
		public List<IniciativaPerspectiva> index (){
			return iniciativaPerspectivaService.findAll();
		}
			
		//servicio que muestra un iniciativaperspectiva 
		@GetMapping("/iniciativaperspectiva/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			IniciativaPerspectiva iniciativaperspectivaId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				iniciativaperspectivaId= iniciativaPerspectivaService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(iniciativaperspectivaId == null) {
			  response.put("mensaje", "La iniciativa perspectiva Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<IniciativaPerspectiva>(iniciativaperspectivaId, HttpStatus.OK); 		
		}
		
		//servicio que crea un iniciativaperspectiva 
		@PostMapping("/iniciativaperspectiva")
		public ResponseEntity<?> create(@Valid @RequestBody IniciativaPerspectiva iniciativaperspectivaN, BindingResult result) {
			
			IniciativaPerspectiva iniciativaperspectivaNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				iniciativaperspectivaNew= iniciativaPerspectivaService.save(iniciativaperspectivaN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La iniciativa perspectiva ha sido creado con Exito!");
			response.put("iniciativaperspectiva", iniciativaperspectivaNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un iniciativaperspectiva 
		@PutMapping("/iniciativaperspectiva/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody IniciativaPerspectiva iniciativaperspectiva, BindingResult result, @PathVariable Long id) {
			IniciativaPerspectiva iniciativaperspectivaActual= iniciativaPerspectivaService.findById(id);
			IniciativaPerspectiva iniciativaperspectivaUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(iniciativaperspectivaActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el iniciativa perspectiva ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
							
				iniciativaperspectivaActual.setIniciativaId(iniciativaperspectiva.getIniciativaId());
				iniciativaperspectivaActual.setPerspectivaId(iniciativaperspectiva.getPerspectivaId());
																			
				iniciativaperspectivaUpdated=iniciativaPerspectivaService.save(iniciativaperspectivaActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar la iniciativa perspectiva en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El iniciativa perspectiva ha sido actualizado con Exito!");
			response.put("iniciativaperspectiva", iniciativaperspectivaUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina el iniciativaperspectiva 
		@DeleteMapping("/iniciativaperspectiva/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				iniciativaPerspectivaService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar iniciativa perspectiva en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El iniciativa perspectiva ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
