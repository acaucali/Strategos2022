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
import com.strategos.nueva.bancoproyecto.strategos.model.MetodologiaStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.MetodologiaService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class MetodologiaRestController {
	
	@Autowired
	private MetodologiaService metodologiaService;
	
	//Servicios Rest tabla - organizaciones
	
		private final Logger log = LoggerFactory.getLogger(MetodologiaRestController.class);
		
		//servicio que trae la lista de metodologia
		@GetMapping("/metodologia")
		public List<MetodologiaStrategos> index (){
			return metodologiaService.findAll();
		}
			
		//servicio que muestra un metodologia
		@GetMapping("/metodologia/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			MetodologiaStrategos metodologiaId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				metodologiaId= metodologiaService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(metodologiaId == null) {
			  response.put("mensaje", "La metodologia Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<MetodologiaStrategos>(metodologiaId, HttpStatus.OK); 		
		}
		
		//servicio que crea un metodologia
		@PostMapping("/metodologia")
		public ResponseEntity<?> create(@Valid @RequestBody MetodologiaStrategos metodologiaN, BindingResult result) {
			
			MetodologiaStrategos metodologiaNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				metodologiaNew= metodologiaService.save(metodologiaN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La metodologia ha sido creado con Exito!");
			response.put("metodologia", metodologiaNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un metodologia
		@PutMapping("/metodologia/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody MetodologiaStrategos metodologia, BindingResult result, @PathVariable Long id) {
			MetodologiaStrategos metodologiaActual= metodologiaService.findById(id);
			MetodologiaStrategos metodologiaUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(metodologiaActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el metodologia ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				
				metodologiaActual.setCreado(metodologia.getCreado());
				metodologiaActual.setCreadoId(metodologia.getCreadoId());
				metodologiaActual.setModificado(metodologia.getModificado());
				metodologiaActual.setModificadoId(metodologia.getModificadoId());
				metodologiaActual.setNombre(metodologia.getNombre());
				metodologiaActual.setDescripcion(metodologia.getDescripcion());
				metodologiaActual.setNombreActividad(metodologia.getNombreActividad());
				metodologiaActual.setNombreIndicador(metodologia.getNombreIndicador());
				metodologiaActual.setNombreIniciativa(metodologia.getNombreIniciativa());
				metodologiaActual.setTipo(metodologia.getTipo());
			
																			
				metodologiaUpdated=metodologiaService.save(metodologiaActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar la metodologia en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La metodologia ha sido actualizado con Exito!");
			response.put("metodologia", metodologiaUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina las metodologia
		@DeleteMapping("/metodologia/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				metodologiaService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar la metodologia en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La metodologia ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
