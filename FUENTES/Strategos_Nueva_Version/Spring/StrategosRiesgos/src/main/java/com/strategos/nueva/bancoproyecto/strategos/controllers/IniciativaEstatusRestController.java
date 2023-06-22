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
import com.strategos.nueva.bancoproyecto.strategos.model.IniciativaEstatusStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.IniciativaEstatusService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class IniciativaEstatusRestController {
	
	@Autowired
	private IniciativaEstatusService iniciativaEstatusService;
	
	//Servicios Rest tabla - iniciativa estatus
	
		private final Logger log = LoggerFactory.getLogger(IniciativaEstatusRestController.class);
		
		//servicio que trae la lista de iniciativa estatus
		@GetMapping("/iniciativaestatus")
		public List<IniciativaEstatusStrategos> index (){
			return iniciativaEstatusService.findAll();
		}
			
		//servicio que muestra un iniciativa estatus
		@GetMapping("/iniciativaestatus/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			IniciativaEstatusStrategos estatusId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				estatusId= iniciativaEstatusService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(estatusId == null) {
			  response.put("mensaje", "El estatus de la iniciativa Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<IniciativaEstatusStrategos>(estatusId, HttpStatus.OK); 		
		}
		
		//servicio que crea un iniciativa estatus
		@PostMapping("/iniciativaestatus")
		public ResponseEntity<?> create(@Valid @RequestBody IniciativaEstatusStrategos iniciativaN, BindingResult result) {
			
			IniciativaEstatusStrategos iniciativaestNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				iniciativaestNew= iniciativaEstatusService.save(iniciativaN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El estatus de la iniciativa ha sido creado con Exito!");
			response.put("iniciativa", iniciativaestNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un iniciativa estatus
		@PutMapping("/iniciativaestatus/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody IniciativaEstatusStrategos estatus, BindingResult result, @PathVariable Long id) {
			IniciativaEstatusStrategos estatusActual= iniciativaEstatusService.findById(id);
			IniciativaEstatusStrategos estatusUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(estatusActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el estatus de la iniciativa ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				
				estatusActual.setNombre(estatus.getNombre());
				estatusActual.setBloquearIndicadores(estatus.getBloquearIndicadores());
				estatusActual.setBloquearMedicion(estatus.getBloquearMedicion());
				estatusActual.setPorcentajeFinal(estatus.getPorcentajeFinal());
				estatusActual.setPorcentajeInicial(estatus.getPorcentajeInicial());
				estatusActual.setSistema(estatus.getSistema());
							
																			
				estatusUpdated=iniciativaEstatusService.save(estatusActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar el estatus de la iniciativa en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El estatus de la iniciativa ha sido actualizado con Exito!");
			response.put("estatus", estatusUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina las iniciativa estatus
		@DeleteMapping("/iniciativaestatus/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				iniciativaEstatusService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el estatus de la iniciativa en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El estatus de la iniciativa ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
