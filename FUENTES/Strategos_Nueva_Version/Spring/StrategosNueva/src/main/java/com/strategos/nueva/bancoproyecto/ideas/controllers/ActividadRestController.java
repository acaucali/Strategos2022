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

import com.strategos.nueva.bancoproyecto.ideas.model.Actividad;
import com.strategos.nueva.bancoproyecto.ideas.model.Iniciativa;
import com.strategos.nueva.bancoproyecto.ideas.service.ActividadService;
import com.strategos.nueva.bancoproyecto.ideas.service.IniciativaService;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorTareaPk;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorTareaStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.PerspectivaStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorService;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorTareaService;
import com.strategos.nueva.bancoproyecto.strategos.service.PerspectivaService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class ActividadRestController {
	
	@Autowired
	private ActividadService actividadService;
	
	@Autowired
	private IndicadorService indicadorService;

	@Autowired
	private PerspectivaService perspectivaService;
	
	@Autowired
	private IniciativaService iniciativaService;
	
	@Autowired
	private IndicadorTareaService indicadorTareaService;
	
	//Servicios Rest tabla - actividad 
	
		private final Logger log = LoggerFactory.getLogger(ActividadRestController.class);
		
		//servicio que trae la lista de actividad 
		@GetMapping("/actividad")
		public List<Actividad> index (){
			return actividadService.findAll();
		}
		
		@GetMapping("/actividad/tarea/{proyectoId}")
		public List<Actividad> indexTarea (@PathVariable Long proyectoId){
			return actividadService.findAllByProyectoId(proyectoId);
		}
			
		//servicio que muestra un actividad 
		@GetMapping("/actividad/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			Actividad actividadId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				actividadId= actividadService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(actividadId == null) {
			  response.put("mensaje", "La actividad Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Actividad>(actividadId, HttpStatus.OK); 		
		}
		
		//servicio que crea un actividad 
		@PostMapping("/actividad/{id}/{perspectiva}/{orgId}")
		public ResponseEntity<?> create(@PathVariable Long id, @PathVariable Long perspectiva, @PathVariable Long orgId, @Valid @RequestBody Actividad actividadN,  BindingResult result) {
			
			Actividad actividadNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				actividadN.setProyectoId(id);
				actividadNew= actividadService.save(actividadN);
				
				PerspectivaStrategos perspectivaN = perspectivaService.findById(perspectiva);
				
				IndicadorStrategos indicador = new IndicadorStrategos();
				
				indicador.setNombre(actividadN.getNombreActividad());
				indicador.setNaturaleza((byte) 0);
				indicador.setFrecuencia((byte) 3);
				indicador.setNombreCorto(actividadN.getNombreActividad());
				indicador.setPrioridad((byte) 0);
				indicador.setMostrarEnArbol((byte) 1);
				indicador.setClaseId(perspectivaN.getClaseId());
				indicador.setAlertaMetaZonaVerde(actividadN.getZonaVerde());
				indicador.setAlertaMetaZonaAmarilla(actividadN.getZonaAmarilla());
				indicador.setOrganizacionId(orgId);
				indicador.setUnidadId(actividadN.getUnidadId());
				
				
				indicadorService.save(indicador);
				
				IndicadorTareaStrategos indicadorTarea = new IndicadorTareaStrategos();
				
				IndicadorTareaPk indicadorTareaPk = new IndicadorTareaPk();
				
				indicadorTareaPk.setActividadId(actividadNew.getActividadId());
				indicadorTareaPk.setIndicadorId(indicador.getIndicadorId());
				
				indicadorTarea.setPk(indicadorTareaPk);
				
				indicadorTareaService.save(indicadorTarea);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La tarea ha sido creado con Exito!");
			response.put("actividad", actividadNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un actividad 
		@PutMapping("/actividad/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody Actividad actividad, BindingResult result, @PathVariable Long id) {
			Actividad actividadActual= actividadService.findById(id);
			Actividad actividadUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(actividadActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, la actividad ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
						
				actividadActual.setAlerta(actividad.getAlerta());
				actividadActual.setDescripcion(actividad.getDescripcion());
				actividadActual.setNombreActividad(actividad.getNombreActividad());
				actividadActual.setResponsableId(actividad.getResponsableId());
				actividadActual.setZonaAmarilla(actividad.getZonaAmarilla());
				actividadActual.setZonaVerde(actividad.getZonaVerde());
				actividadActual.setFechaCulminacion(actividad.getFechaCulminacion());
				actividadActual.setFechaInicio(actividad.getFechaInicio());
				actividadActual.setPeso(actividad.getPeso());
				actividadActual.setCompletado(actividad.getCompletado());
				actividadActual.setProgramado(actividad.getProgramado());
				actividadActual.setUltimaMedicion(actividad.getUltimaMedicion());
				actividadActual.setUnidadId(actividad.getUnidadId());
																			
				actividadUpdated=actividadService.save(actividadActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar la actividad en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La actividad ha sido actualizado con Exito!");
			response.put("actividad", actividadUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina el actividad 
		@DeleteMapping("/actividad/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				actividadService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar la actividad en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La actividad ha sido eliminada con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
