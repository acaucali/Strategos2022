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

import com.strategos.nueva.bancoproyecto.ideas.model.TiposObjetivos;
import com.strategos.nueva.bancoproyecto.ideas.service.TiposObjetivosService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class TiposObjetivosRestController {
	
	@Autowired
	private TiposObjetivosService tiposObjetivosService;
	
	//Servicios Rest tabla - estatus 
	
		private final Logger log = LoggerFactory.getLogger(TiposObjetivosRestController.class);
		
		//servicio que trae la lista de tipos objetivos
		@GetMapping("/tiposobjetivo")
		public List<TiposObjetivos> index (){
			return tiposObjetivosService.findAll();
		}
			
		//servicio que muestra un tipos objetivos
		@GetMapping("/tiposobjetivo/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			TiposObjetivos tiposObjetivoId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				tiposObjetivoId= tiposObjetivosService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(tiposObjetivoId == null) {
			  response.put("mensaje", "El tipo objetivo Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<TiposObjetivos>(tiposObjetivoId, HttpStatus.OK); 		
		}
		
		//servicio que crea un tipos objetivos
		@PostMapping("/tiposobjetivo")
		public ResponseEntity<?> create(@Valid @RequestBody TiposObjetivos tipoObjetivoN, BindingResult result) {
			
			TiposObjetivos tipoObjetivoNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				tipoObjetivoNew= tiposObjetivosService.save(tipoObjetivoN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El tipo objetivo ha sido creado con Exito!");
			response.put("tipoobjetivo", tipoObjetivoNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un tipos objetivos
		@PutMapping("/tiposobjetivo/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody TiposObjetivos tipoObjetivo, BindingResult result, @PathVariable Long id) {
			TiposObjetivos tipoObjetivoActual= tiposObjetivosService.findById(id);
			TiposObjetivos tipoObjetivoUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(tipoObjetivoActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el tipo objetivo ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
						
				tipoObjetivoActual.setDescripcionObjetivo(tipoObjetivo.getDescripcionObjetivo());
				tipoObjetivoActual.setIdea(tipoObjetivo.getIdea());
				
																			
				tipoObjetivoUpdated=tiposObjetivosService.save(tipoObjetivoActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar el estatus proyecto en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El tipo objetivo ha sido actualizado con Exito!");
			response.put("tipoobjetivo", tipoObjetivoUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina el tipos objetivos
		@DeleteMapping("/tiposobjetivo/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				tiposObjetivosService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el tipo objetivo en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El tipo objetivo ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
