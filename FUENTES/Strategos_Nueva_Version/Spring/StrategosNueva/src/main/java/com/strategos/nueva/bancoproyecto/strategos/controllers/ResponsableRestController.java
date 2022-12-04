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
import com.strategos.nueva.bancoproyecto.strategos.model.Responsable;
import com.strategos.nueva.bancoproyecto.strategos.service.ResponsableService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class ResponsableRestController {
	
	@Autowired
	private ResponsableService responsableService;
	
	//Servicios Rest tabla - organizaciones
	
		private final Logger log = LoggerFactory.getLogger(ResponsableRestController.class);
		
		//servicio que trae la lista de responsable
		@GetMapping("/responsable")
		public List<Responsable> index (){
			return responsableService.findAll();
		}
			
		//servicio que muestra un responsable
		@GetMapping("/responsable/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			Responsable responsableId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				responsableId= responsableService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(responsableId == null) {
			  response.put("mensaje", "El responsable Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Responsable>(responsableId, HttpStatus.OK); 		
		}
		
		//servicio que crea un responsable
		@PostMapping("/responsable")
		public ResponseEntity<?> create(@Valid @RequestBody Responsable responsableN, BindingResult result) {
			
			Responsable responsableNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				responsableNew= responsableService.save(responsableN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El responsable ha sido creado con Exito!");
			response.put("responsable", responsableNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un responsable
		@PutMapping("/responsable/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody Responsable responsable, BindingResult result, @PathVariable Long id) {
			Responsable responsableActual= responsableService.findById(id);
			Responsable responsableUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(responsableActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el responsable ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				responsableActual.setCargo(responsable.getCargo());
				responsableActual.setEmail(responsable.getEmail());
				responsableActual.setEsGrupo(responsable.getEsGrupo());
				responsableActual.setNombre(responsable.getNombre());
				responsableActual.setNotas(responsable.getNotas());
				responsableActual.setOrganizacionId(responsable.getOrganizacionId());
				responsableActual.setTipo(responsable.getTipo());
				responsableActual.setUbicacion(responsable.getUbicacion());
				responsableActual.setUsuarioId(responsable.getUsuarioId());
				
			
																			
				responsableUpdated=responsableService.save(responsableActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar el responsable en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El responsable ha sido actualizado con Exito!");
			response.put("responsable", responsableUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina las responsable
		@DeleteMapping("/responsable/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				responsableService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el responsable en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El responsable ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
