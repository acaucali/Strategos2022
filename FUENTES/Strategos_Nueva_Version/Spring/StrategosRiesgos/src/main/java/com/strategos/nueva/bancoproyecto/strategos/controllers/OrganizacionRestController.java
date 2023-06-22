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
import com.strategos.nueva.bancoproyecto.strategos.model.OrganizacionesStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class OrganizacionRestController {
	
	@Autowired
	private OrganizacionService organizacionesService;
	
	//Servicios Rest tabla - organizaciones
	
		private final Logger log = LoggerFactory.getLogger(OrganizacionRestController.class);
		
		//servicio que trae la lista de organizaciones
		@GetMapping("/organizacion")
		public List<OrganizacionesStrategos> index (){
			return organizacionesService.findAll();
		}
			
		//servicio que muestra un organizaciones
		@GetMapping("/organizacion/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			OrganizacionesStrategos organizacionesId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				organizacionesId= organizacionesService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(organizacionesId == null) {
			  response.put("mensaje", "La organizacion Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<OrganizacionesStrategos>(organizacionesId, HttpStatus.OK); 		
		}
		
		//servicio que crea un organizaciones
		@PostMapping("/organizacion")
		public ResponseEntity<?> create(@Valid @RequestBody OrganizacionesStrategos organizacionesN, BindingResult result) {
			
			OrganizacionesStrategos organizacionesNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				organizacionesNew= organizacionesService.save(organizacionesN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La organizacion ha sido creado con Exito!");
			response.put("organizacion", organizacionesNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un organizaciones
		@PutMapping("/organizacion/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody OrganizacionesStrategos organizacion, BindingResult result, @PathVariable Long id) {
			OrganizacionesStrategos organizacionActual= organizacionesService.findById(id);
			OrganizacionesStrategos organizacionUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(organizacionActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el organizacion ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				organizacionActual.setAlertaIniciativaZa(organizacion.getAlertaIniciativaZa());
				organizacionActual.setAlertaIniciativaZv(organizacion.getAlertaIniciativaZv());
				organizacionActual.setAlertaMetaN1(organizacion.getAlertaMetaN1());
				organizacionActual.setAlertaMetaN2(organizacion.getAlertaMetaN2());
				organizacionActual.setAlertaMinMax(organizacion.getAlertaMinMax());
				organizacionActual.setCreado(organizacion.getCreado());
				organizacionActual.setCreadoId(organizacion.getCreadoId());
				organizacionActual.setDireccion(organizacion.getDireccion());
				organizacionActual.setEnlaceParcial(organizacion.getEnlaceParcial());
				organizacionActual.setFax(organizacion.getFax());
				organizacionActual.setMesCierre(organizacion.getMesCierre());
				organizacionActual.setModificado(organizacion.getModificado());
				organizacionActual.setModificadoId(organizacion.getModificadoId());
				organizacionActual.setNombre(organizacion.getNombre());
				organizacionActual.setOrganizacionId(organizacion.getOrganizacionId());
				organizacionActual.setPadreId(organizacion.getPadreId());
				organizacionActual.setReadOnly(organizacion.getReadOnly());
				organizacionActual.setRif(organizacion.getRif());
				organizacionActual.setSubclase(organizacion.getSubclase());
				organizacionActual.setTelefono(organizacion.getTelefono());
				organizacionActual.setVisible(organizacion.getVisible());
			
																			
				organizacionUpdated=organizacionesService.save(organizacionActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar la organizacion en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La organizacion ha sido actualizado con Exito!");
			response.put("organizacion", organizacionUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina las organizaciones
		@DeleteMapping("/organizacion/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				organizacionesService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar la organizacion en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La organizacion ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
