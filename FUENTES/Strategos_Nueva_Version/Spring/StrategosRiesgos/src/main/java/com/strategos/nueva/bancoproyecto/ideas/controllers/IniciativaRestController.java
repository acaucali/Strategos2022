package com.strategos.nueva.bancoproyecto.ideas.controllers;

import java.util.ArrayList;
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

import com.strategos.nueva.bancoproyecto.ideas.model.Iniciativa;
import com.strategos.nueva.bancoproyecto.ideas.model.IniciativaPerspectiva;
import com.strategos.nueva.bancoproyecto.ideas.service.IniciativaPerspectivaService;
import com.strategos.nueva.bancoproyecto.ideas.service.IniciativaService;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorPerspectiva;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorPerspectivaPk;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.PerspectivaStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.PerspectivaService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class IniciativaRestController {
	
	@Autowired
	private IniciativaService iniciativaService;
	
	@Autowired
	private PerspectivaService perspectivaService;
	
	@Autowired
	private IniciativaPerspectivaService iniciativaPerspectivaService;
	
	//Servicios Rest tabla - iniciativa 
	
		private final Logger log = LoggerFactory.getLogger(IniciativaRestController.class);
		
		//servicio que trae la lista de iniciativa 
		@GetMapping("/iniciativa")
		public List<Iniciativa> index (){
			return iniciativaService.findAll();
		}
			
		//servicio que muestra un criterio 
		@GetMapping("/iniciativa/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			Iniciativa iniciativaId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				iniciativaId= iniciativaService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(iniciativaId == null) {
			  response.put("mensaje", "La iniciativa Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Iniciativa>(iniciativaId, HttpStatus.OK); 		
		}
		
		
		@GetMapping("/iniciativa/perspectiva/{id}")
		public List<Iniciativa> indexPerspectiva(@PathVariable Long id){
					
			List<Iniciativa> iniciativas = new ArrayList<Iniciativa>();
			List<IniciativaPerspectiva> relaciones = new ArrayList<IniciativaPerspectiva>();
			
			relaciones = iniciativaPerspectivaService.findByPerspectiva(id);
			
			for(IniciativaPerspectiva indi: relaciones) {
				
				Iniciativa ind = iniciativaService.findById(indi.getIniciativaId());
				iniciativas.add(ind);
			}
										
			return iniciativas;
		}
		
		//servicio que crea un iniciativa 
		@PostMapping("/iniciativa/{perspectivaId}")
		public ResponseEntity<?> create(@Valid @RequestBody Iniciativa iniciativaN, BindingResult result, @PathVariable Long perspectivaId) {
			
			Iniciativa iniciativaNew= null;
			
			PerspectivaStrategos perspectiva = new PerspectivaStrategos();
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				iniciativaNew= iniciativaService.save(iniciativaN);
				
				IniciativaPerspectiva iniciativaPerspectiva = new IniciativaPerspectiva();
				
				
				iniciativaPerspectiva.setIniciativaId(iniciativaNew.getIniciativaId());
				iniciativaPerspectiva.setPerspectivaId(perspectivaId);
				
			
				iniciativaPerspectivaService.save(iniciativaPerspectiva);
				
				

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La iniciativa ha sido creado con Exito!");
			response.put("iniciativa", iniciativaNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un criterio 
		@PutMapping("/iniciativa/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody Iniciativa iniciativa, BindingResult result, @PathVariable Long id) {
			Iniciativa iniciativaActual= iniciativaService.findById(id);
			Iniciativa iniciativaUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(iniciativaActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, la iniciativa ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
							
				iniciativaActual.setDescripcion(iniciativa.getDescripcion());
				iniciativaActual.setFrecuencia(iniciativa.getFrecuencia());
				iniciativaActual.setNombreIniciativa(iniciativa.getNombreIniciativa());
				iniciativaActual.setZonaAmarilla(iniciativa.getZonaAmarilla());
				iniciativaActual.setZonaVerde(iniciativa.getZonaVerde());
				
																			
				iniciativaUpdated=iniciativaService.save(iniciativaActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar la tarifa en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La iniciativa ha sido actualizado con Exito!");
			response.put("iniciativa", iniciativaUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina el criterio 
		@DeleteMapping("/iniciativa/{id}/{perspectivaId}")
		public ResponseEntity<?> delete(@PathVariable Long id, @PathVariable Long perspectivaId) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				IniciativaPerspectiva iniPers= iniciativaPerspectivaService.findByPerspectivaIniciativa(perspectivaId, id);
			
				iniciativaPerspectivaService.delete(iniPers.getIniciativaPerspectivaId());
				
				iniciativaService.delete(id);
				
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el criterio en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El criterio ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
