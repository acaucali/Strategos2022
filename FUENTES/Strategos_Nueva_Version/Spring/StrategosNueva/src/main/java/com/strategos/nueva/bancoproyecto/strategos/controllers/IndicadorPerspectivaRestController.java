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
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorPerspectiva;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorPerspectivaService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class IndicadorPerspectivaRestController {
	
	@Autowired
	private IndicadorPerspectivaService indicadorPerspectivaService;
	
	//Servicios Rest tabla - organizaciones
	
		private final Logger log = LoggerFactory.getLogger(IndicadorPerspectivaRestController.class);
		
		//servicio que trae la lista de indicador
		@GetMapping("/indicadorperspectiva")
		public List<IndicadorPerspectiva> index (){
			return indicadorPerspectivaService.findAll();
		}
			
		//servicio que muestra un indicador
		@GetMapping("/indicadorperspectiva/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			IndicadorPerspectiva indicadorId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				indicadorId= indicadorPerspectivaService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(indicadorId == null) {
			  response.put("mensaje", "El indicador Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<IndicadorPerspectiva>(indicadorId, HttpStatus.OK); 		
		}
		
		//servicio que crea un indicador
		@PostMapping("/indicadorperspectiva")
		public ResponseEntity<?> create(@Valid @RequestBody IndicadorPerspectiva indicadorN, BindingResult result) {
			
			IndicadorPerspectiva indicadorNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				indicadorNew= indicadorPerspectivaService.save(indicadorN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El indicador ha sido creado con Exito!");
			response.put("indicador", indicadorNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un indicador
		@PutMapping("/indicadorperspectiva/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody IndicadorPerspectiva indicador, BindingResult result, @PathVariable Long id) {
			IndicadorPerspectiva indicadorActual= indicadorPerspectivaService.findById(id);
			IndicadorPerspectiva indicadorUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(indicadorActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el indicador ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				
				indicadorActual.setPeso(indicador.getPeso());
																							
				indicadorUpdated=indicadorPerspectivaService.save(indicadorActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar el indicador en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El indicador ha sido actualizado con Exito!");
			response.put("indicador", indicadorUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina las indicador
		@DeleteMapping("/indicadorperspectiva/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				indicadorPerspectivaService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el indicador en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El indicador ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
