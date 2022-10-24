package com.strategos.nueva.bancoproyecto.strategos.controllers;

import java.util.ArrayList;
import java.util.Date;
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
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosService;
import com.strategos.nueva.bancoproyecto.ideas.service.TiposPropuestasService;
import com.strategos.nueva.bancoproyecto.strategos.model.PerspectivaStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.PlanStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.PerspectivaService;
import com.strategos.nueva.bancoproyecto.strategos.service.PlanService;
import com.strategos.nueva.bancoproyectos.model.util.Arbol;
import com.strategos.nueva.bancoproyectos.model.util.Nodo;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class PerspectivaRestController {
	
	@Autowired
	private PerspectivaService perspectivaService;
	@Autowired
	private PlanService planService;
	@Autowired
	private ProyectosService proyectoService;
	
	//Servicios Rest tabla - organizaciones
	
		private final Logger log = LoggerFactory.getLogger(PerspectivaRestController.class);
		
		//servicio que trae la lista de perspectiva
		@GetMapping("/perspectiva")
		public List<PerspectivaStrategos> index (){
			return perspectivaService.findAll();
		}
			
		//servicio que muestra un perspectiva
		@GetMapping("/perspectiva/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			PerspectivaStrategos perspectivaId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				perspectivaId= perspectivaService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(perspectivaId == null) {
			  response.put("mensaje", "La perspectiva Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<PerspectivaStrategos>(perspectivaId, HttpStatus.OK); 		
		}
		
		//servicio que crea un perspectiva
		@PostMapping("/perspectiva/{planId}/{proyectoId}")
		public ResponseEntity<?> create(@Valid @RequestBody PerspectivaStrategos perspectivaN, BindingResult result, @PathVariable Long planId, @PathVariable Long proyectoId) {
			
			PerspectivaStrategos perspectivaNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				PlanStrategos plan = planService.findById(planId);
				
				Proyectos proyecto = proyectoService.findById(proyectoId);
				
				perspectivaN.setClaseId(plan.getClaseId());
				perspectivaN.setPlanId(plan.getPlanId());
				perspectivaN.setTipo(plan.getTipo());
				perspectivaN.setFrecuencia(proyecto.getFrecuencia());
																
				perspectivaN.setTipoCalculo((byte) 1);
				perspectivaN.setCreado(new Date());
								
				perspectivaNew= perspectivaService.save(perspectivaN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La perspectiva ha sido creado con Exito!");
			response.put("perspectiva", perspectivaNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un perspectiva
		@PutMapping("/perspectiva/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody PerspectivaStrategos perspectiva, BindingResult result, @PathVariable Long id) {
			PerspectivaStrategos perspectivaActual= perspectivaService.findById(id);
			PerspectivaStrategos perspectivaUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(perspectivaActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el perspectiva ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
								
				perspectivaActual.setAlertaAnual(perspectiva.getAlertaAnual());
				perspectivaActual.setAlertaParcial(perspectiva.getAlertaParcial());
				perspectivaActual.setAno(perspectiva.getAno());
				perspectivaActual.setClaseId(perspectiva.getClaseId());
				perspectivaActual.setDescripcion(perspectiva.getDescripcion());
				perspectivaActual.setFechaUltimaMedicion(perspectiva.getFechaUltimaMedicion());
				perspectivaActual.setFrecuencia(perspectiva.getFrecuencia());
				perspectivaActual.setModificado(perspectiva.getModificado());
				perspectivaActual.setModificadoId(perspectiva.getModificadoId());
				perspectivaActual.setNlAnoIndicadorId(perspectiva.getNlAnoIndicadorId());
				perspectivaActual.setNlParIndicadorId(perspectiva.getNlParIndicadorId());
				perspectivaActual.setNombre(perspectiva.getNombre());
				perspectivaActual.setOrden(perspectiva.getOrden());
				perspectivaActual.setPadreId(perspectiva.getPadreId());
				perspectivaActual.setPlanId(perspectiva.getPlanId());
				perspectivaActual.setResponsableId(perspectiva.getResponsableId());
				perspectivaActual.setTipo(perspectiva.getTipo());
				perspectivaActual.setTipoCalculo(perspectiva.getTipoCalculo());
				perspectivaActual.setTitulo(perspectiva.getTitulo());
				perspectivaActual.setUltimaMedicionAnual(perspectiva.getUltimaMedicionAnual());
				perspectivaActual.setUltimaMedicionParcial(perspectiva.getUltimaMedicionParcial());
																			
				perspectivaUpdated=perspectivaService.save(perspectivaActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar la metodologia en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La perspectiva ha sido actualizado con Exito!");
			response.put("perspectiva", perspectivaUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina las perspectiva
		@DeleteMapping("/perspectiva/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {

			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				perspectivaService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar la perspectiva en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La perspectiva ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}
		
		@GetMapping("/perspectiva/arbol/{planId}")
		public List<Arbol> getArbol(@PathVariable Long planId){
			
			PerspectivaStrategos perspectiva = new PerspectivaStrategos();
			List<PerspectivaStrategos> perspectivas= perspectivaService.findAllByPlanId(planId);
			ArrayList<Arbol> arboles = new ArrayList();
			if(perspectivas !=null) {
				
				
				for(PerspectivaStrategos pers: perspectivas) {
					if(pers.getPadreId() == null) {
						perspectiva = pers;
					}
				}
					
				
				
				List<Nodo<PerspectivaStrategos>> nodos = construirArbol(perspectivas, perspectiva.getPerspectivaId().intValue());
				
				Nodo<PerspectivaStrategos> nodo= new Nodo(new PerspectivaStrategos());
				nodo.setData(perspectiva);
				nodo.setParent(0);
				
				if(nodos != null && nodos.size() >0) {
					nodo.addChildren((ArrayList<Nodo<PerspectivaStrategos>>) nodos);
				}
				
				
				ArrayList<Nodo<PerspectivaStrategos>> arbolNodo = new ArrayList();
				
				arbolNodo.add(nodo);// padres
				
				/*
				if(nodos != null && nodos.size() >0) {
					for(Nodo nodArbol: nodos) {
						arbolNodo.add(nodArbol);// padres
					}
				}
				*/
				
			
				for(Nodo nod : arbolNodo) {
					Arbol arbol = new Arbol();
					arbol.setText(nod.getData().getNombre());
					arbol.setId(nod.getData().getPerspectivaId().intValue());
					arbol.setValor(nod.getData().getUltimaMedicionAnual());
					if(nod.getChildren() != null) {
						arbol.setItems(convertirArbol(nod.getChildren()));
					}
					arboles.add(arbol);
				}
			}else {
				return null;
			}
			
			return arboles;
		}
			
		public ArrayList<Arbol> convertirArbol(List<Nodo<PerspectivaStrategos>> nodos) {
			ArrayList<Arbol> arboles = new ArrayList();
			for(Nodo nod: nodos) {
				Arbol arbol = new Arbol();
				arbol.setText(nod.getData().getNombre());
				arbol.setId(nod.getData().getPerspectivaId().intValue());
				arbol.setValor(nod.getData().getUltimaMedicionAnual());
				if(nod.getChildren() != null) {
					arbol.setItems(convertirArbol(nod.getChildren()));
				}
				arboles.add(arbol);
			}
			
			return arboles;
		}
		
		public ArrayList<Nodo<PerspectivaStrategos>> construirArbol(List<PerspectivaStrategos> perspectivas, int padreId) {
			
			ArrayList<Nodo<PerspectivaStrategos>> arbol = new ArrayList();
			ArrayList<Nodo<PerspectivaStrategos>> hijos = new ArrayList();
			Nodo<PerspectivaStrategos> nodo= new Nodo(new PerspectivaStrategos());
			
			for(PerspectivaStrategos per: perspectivas) {
				nodo = new Nodo(per);
				
				if(nodo.getData().getPadreId() == null) {
					nodo.setParent(0);					
				} 
					
				if(nodo.getData().getPadreId() != null && nodo.getData().getPadreId() == padreId) {
					hijos = construirArbol(perspectivas, per.getPerspectivaId().intValue());
					if(hijos != null) {
						nodo.setData(per);
						nodo.addChildren(hijos);
					}
					arbol.add(nodo);
				}
								
				
			}
			if(arbol.size() ==0) {
				return null;	
			}
			
			return arbol;
			
		}

}
