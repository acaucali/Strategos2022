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

import com.strategos.nueva.bancoproyecto.ideas.model.Proyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosPlan;
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosPlanService;
import com.strategos.nueva.bancoproyecto.ideas.service.TiposPropuestasService;
import com.strategos.nueva.bancoproyecto.strategos.model.PlanStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.PlanService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class PlanRestController {
	
	@Autowired
	private PlanService planService;
	@Autowired
	private ProyectosPlanService proyectoPlanService;
	
	//Servicios Rest tabla - plan
	
		private final Logger log = LoggerFactory.getLogger(PlanRestController.class);
		
		//servicio que trae la lista de plan
		@GetMapping("/plan")
		public List<PlanStrategos> index (){
			return planService.findAll();
		}
		
		@GetMapping("/plan/proyecto/{id}")
		public ProyectosPlan indexPlan(@PathVariable Long id){					
			ProyectosPlan proyectoPlan = (ProyectosPlan) proyectoPlanService.findAllByProyectoId(id); 				
			return proyectoPlan;
		}
			
		//servicio que muestra un plan
		@GetMapping("/plan/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			PlanStrategos planId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				planId= planService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(planId == null) {
			  response.put("mensaje", "La plan Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<PlanStrategos>(planId, HttpStatus.OK); 		
		}
		
		//servicio que crea un plan
		@PostMapping("/plan")
		public ResponseEntity<?> create(@Valid @RequestBody PlanStrategos planN, BindingResult result) {
			
			PlanStrategos planNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				planNew= planService.save(planN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El plan ha sido creado con Exito!");
			response.put("plan", planNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un plan
		@PutMapping("/plan/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody PlanStrategos plan, BindingResult result, @PathVariable Long id) {
			PlanStrategos planActual= planService.findById(id);
			PlanStrategos planUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(planActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el plan ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				planActual.setActivo(plan.getActivo());
				planActual.setAlerta(plan.getAlerta());
				planActual.setAnoFinal(plan.getAnoFinal());
				planActual.setAnoInicial(plan.getAnoInicial());
				planActual.setClaseId(plan.getClaseId());
				planActual.setClaseIdIndicadoresTotales(plan.getClaseIdIndicadoresTotales());
				planActual.setEsquema(plan.getEsquema());
				planActual.setFechaUltimaMedicion(plan.getFechaUltimaMedicion());
				planActual.setIndTotalImputacionId(plan.getIndTotalImputacionId());
				planActual.setIndTotalIniciativaId(plan.getIndTotalIniciativaId());
				planActual.setMetodologiaId(plan.getMetodologiaId());
				planActual.setNlAnoIndicadorId(plan.getNlAnoIndicadorId());
				planActual.setNlParIndicadorId(plan.getNlParIndicadorId());
				planActual.setNombre(plan.getNombre());
				planActual.setOrganizacionId(plan.getOrganizacionId());
				planActual.setPadreId(plan.getPadreId());
				planActual.setPlanImpactaId(plan.getPlanImpactaId());
				planActual.setRevision(plan.getRevision());
				planActual.setTipo(plan.getTipo());
				planActual.setUltimaMedicionAnual(plan.getUltimaMedicionAnual());
				planActual.setUltimaMedicionParcial(plan.getUltimaMedicionParcial());
			
																			
				planUpdated=planService.save(planActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar el plan en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El plan ha sido actualizado con Exito!");
			response.put("plan", planUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina las plan
		@DeleteMapping("/plan/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				planService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el plan en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "el plan ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
