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

import com.strategos.nueva.bancoproyecto.ideas.model.IdeasDocumentosAnexos;
import com.strategos.nueva.bancoproyecto.ideas.service.IdeasDocumentosAnexosService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class IdeasDocumentosAnexosRestController {
	
	@Autowired
	private IdeasDocumentosAnexosService ideasDocumentosService;
	
	//Servicios Rest tabla - documento
	
		private final Logger log = LoggerFactory.getLogger(IdeasDocumentosAnexosRestController.class);
		
		//servicio que trae la lista de documento
		@GetMapping("/documento")
		public List<IdeasDocumentosAnexos> index (){
			return ideasDocumentosService.findAll();
		}
			
		//servicio que muestra un documento
		@GetMapping("/documento/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			IdeasDocumentosAnexos ideasDocumentosId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				ideasDocumentosId= ideasDocumentosService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(ideasDocumentosId == null) {
			  response.put("mensaje", "El documento ideas Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<IdeasDocumentosAnexos>(ideasDocumentosId, HttpStatus.OK); 		
		}
		
		//servicio que crea un documento
		@PostMapping("/documento")
		public ResponseEntity<?> create(@Valid @RequestBody IdeasDocumentosAnexos ideasDocumentosN, BindingResult result) {
			
			IdeasDocumentosAnexos ideasDocumentosNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				ideasDocumentosNew= ideasDocumentosService.save(ideasDocumentosN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El documento ideas ha sido creado con Exito!");
			response.put("ideasdocumentos", ideasDocumentosNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un documento
		@PutMapping("/documento/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody IdeasDocumentosAnexos ideasDocumentos, BindingResult result, @PathVariable Long id) {
			IdeasDocumentosAnexos ideasDocumentosActual= ideasDocumentosService.findById(id);
			IdeasDocumentosAnexos ideasDocumentosUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(ideasDocumentosActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el documento idea ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
											
				ideasDocumentosActual.setIdea(ideasDocumentos.getIdea());
				ideasDocumentosActual.setTituloDocumento(ideasDocumentos.getTituloDocumento());
				ideasDocumentosActual.setDocumento(ideasDocumentos.getDocumento());
																			
				ideasDocumentosUpdated=ideasDocumentosService.save(ideasDocumentosActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar el documento idea en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El documento idea ha sido actualizado con Exito!");
			response.put("ideasdocumentos", ideasDocumentosUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina el documento
		@DeleteMapping("/documento/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				ideasDocumentosService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el documento idea en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El documento idea ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
