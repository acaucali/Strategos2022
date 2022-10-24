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

import com.strategos.nueva.bancoproyecto.ideas.model.IniciativaPlan;
import com.strategos.nueva.bancoproyecto.ideas.service.IniciativaPlanService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class IniciativaPlanRestController {
	
	@Autowired
	private IniciativaPlanService iniciativaPlanService;
	
	//Servicios Rest tabla - criterio 
	
		private final Logger log = LoggerFactory.getLogger(IniciativaPlanRestController.class);
		
		//servicio que trae la lista de iniciativaplan 
		@GetMapping("/iniciativaplan")
		public List<IniciativaPlan> index (){
			return iniciativaPlanService.findAll();
		}
			
		//servicio que muestra un iniciativaplan 
		@GetMapping("/iniciativaplan/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			IniciativaPlan iniciativaplanId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				iniciativaplanId= iniciativaPlanService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(iniciativaplanId == null) {
			  response.put("mensaje", "El iniciativa plan Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<IniciativaPlan>(iniciativaplanId, HttpStatus.OK); 		
		}
		
		//servicio que crea un iniciativaplan 
		@PostMapping("/iniciativaplan")
		public ResponseEntity<?> create(@Valid @RequestBody IniciativaPlan iniciativaplanN, BindingResult result) {
			
			IniciativaPlan iniciativaplanNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				iniciativaplanNew= iniciativaPlanService.save(iniciativaplanN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La iniciativa plan ha sido creado con Exito!");
			response.put("iniciativaplan", iniciativaplanNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un iniciativaplan 
		@PutMapping("/iniciativaplan/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody IniciativaPlan iniciativaplan, BindingResult result, @PathVariable Long id) {
			IniciativaPlan iniciativaplanActual= iniciativaPlanService.findById(id);
			IniciativaPlan iniciativaplanUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(iniciativaplanActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el criterio ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				iniciativaplanActual.setIniciativaId(iniciativaplan.getIniciativaId());
				iniciativaplanActual.setPlanId(iniciativaplan.getPlanId());
							
																							
				iniciativaplanUpdated=iniciativaPlanService.save(iniciativaplanActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar la tarifa en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La iniciativa plan ha sido actualizado con Exito!");
			response.put("iniciativaplan", iniciativaplanUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina el iniciativaplan 
		@DeleteMapping("/iniciativaplan/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				iniciativaPlanService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar la iniciativa plan en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La iniciativa plan ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
