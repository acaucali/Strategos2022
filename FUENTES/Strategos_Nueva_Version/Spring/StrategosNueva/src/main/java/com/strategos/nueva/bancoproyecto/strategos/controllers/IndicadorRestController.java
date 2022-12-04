package com.strategos.nueva.bancoproyecto.strategos.controllers;

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

import com.strategos.nueva.bancoproyecto.ideas.model.Proyectos;
import com.strategos.nueva.bancoproyecto.ideas.service.TiposPropuestasService;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorPerspectiva;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorPerspectivaPk;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.PerspectivaStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.UnidadStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorPerspectivaService;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorService;
import com.strategos.nueva.bancoproyecto.strategos.service.PerspectivaService;
import com.strategos.nueva.bancoproyecto.strategos.service.UnidadService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class IndicadorRestController {
	
	@Autowired
	private IndicadorService indicadorService;
	
	@Autowired
	private PerspectivaService perspectivaService;
	
	@Autowired
	private UnidadService unidadService;
	
	@Autowired
	private IndicadorPerspectivaService indicadorPerspectivaService;
	
	//Servicios Rest tabla - organizaciones
	
		private final Logger log = LoggerFactory.getLogger(IndicadorRestController.class);
		
		//servicio que trae la lista de indicador
		@GetMapping("/indicador")
		public List<IndicadorStrategos> index (){
			return indicadorService.findAll();
		}
			
		//servicio que muestra un indicador
		@GetMapping("/indicador/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			IndicadorStrategos indicadorId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				indicadorId= indicadorService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(indicadorId == null) {
			  response.put("mensaje", "El indicador Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<IndicadorStrategos>(indicadorId, HttpStatus.OK); 		
		}
		
		@GetMapping("/indicador/perspectiva/{id}")
		public List<IndicadorStrategos> indexPerspectiva(@PathVariable Long id){
					
			List<IndicadorStrategos> indicadores = new ArrayList<IndicadorStrategos>();
			List<IndicadorPerspectiva> relaciones = new ArrayList<IndicadorPerspectiva>();
			
			relaciones = indicadorPerspectivaService.findByPerspectiva(id);
			
			for(IndicadorPerspectiva indi: relaciones) {
				
				IndicadorStrategos ind = indicadorService.findById(indi.getPk().getIndicadorId());
				indicadores.add(ind);
			}
										
			return indicadores;
		}
		
		//servicio que crea un indicador
		@PostMapping("/indicador/{perspectivaId}")
		public ResponseEntity<?> create(@Valid @RequestBody IndicadorStrategos indicadorN, BindingResult result, @PathVariable Long perspectivaId) {
			
			IndicadorStrategos indicadorNew= null;
			
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
				
				perspectiva = perspectivaService.findById(perspectivaId);
				
				indicadorN.setClaseId(perspectiva.getClaseId());
				
				if(indicadorN.getUnidadId() != null) {
					UnidadStrategos und = unidadService.findById(indicadorN.getUnidadId());
					indicadorN.setNombreUnidad(und.getNombre());
				}
				
				
				indicadorNew= indicadorService.save(indicadorN);
				
				IndicadorPerspectiva indicadorPerspectiva = new IndicadorPerspectiva();
				IndicadorPerspectivaPk indicadorPerspectivaPk = new IndicadorPerspectivaPk();
				
				indicadorPerspectiva.setPk(indicadorPerspectivaPk);
				
				indicadorPerspectiva.getPk().setPerspectivaId(perspectivaId);
				indicadorPerspectiva.getPk().setIndicadorId(indicadorNew.getIndicadorId());
				
				indicadorPerspectivaService.save(indicadorPerspectiva);
				

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
		@PutMapping("/indicador/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody IndicadorStrategos indicador, BindingResult result, @PathVariable Long id) {
			IndicadorStrategos indicadorActual= indicadorService.findById(id);
			IndicadorStrategos indicadorUpdated = null;
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
				
				indicadorActual.setAlerta(indicador.getAlerta());
				indicadorActual.setAlertaIndicadorIdZonaVerde(indicador.getAlertaIndicadorIdZonaVerde());
				indicadorActual.setAlertaIndicadorIdZonaAmarilla(indicador.getAlertaIndicadorIdZonaAmarilla());
				indicadorActual.setAlertaMetaZonaAmarilla(indicador.getAlertaMetaZonaAmarilla());
				indicadorActual.setAlertaMetaZonaVerde(indicador.getAlertaMetaZonaVerde());
				indicadorActual.setAlertaMinMax(indicador.getAlertaMinMax());
				indicadorActual.setAlertaTipoZonaAmarilla(indicador.getAlertaTipoZonaAmarilla());
				indicadorActual.setAlertaTipoZonaVerde(indicador.getAlertaTipoZonaVerde());
				indicadorActual.setAlertaValorVariableZonaAmarilla(indicador.getAlertaValorVariableZonaAmarilla());
				indicadorActual.setAlertaValorVariableZonaVerde(indicador.getAlertaValorVariableZonaVerde());
				indicadorActual.setAsignarInventario(indicador.getAsignarInventario());
				indicadorActual.setCaracteristica(indicador.getCaracteristica());
				indicadorActual.setClaseId(indicador.getClaseId());
				indicadorActual.setCodigoEnlace(indicador.getCodigoEnlace());
				indicadorActual.setComportamiento(indicador.getComportamiento());
				indicadorActual.setCorte(indicador.getCorte());
				indicadorActual.setDescripcion(indicador.getDescripcion());
				indicadorActual.setEnlaceParcial(indicador.getEnlaceParcial());
				indicadorActual.setFechaBloqueoEjecutado(indicador.getFechaBloqueoEjecutado());
				indicadorActual.setFechaBloqueoMeta(indicador.getFechaBloqueoMeta());
				indicadorActual.setFechaUltimaMedicion(indicador.getFechaUltimaMedicion());
				indicadorActual.setFrecuencia(indicador.getFrecuencia());
				indicadorActual.setFuente(indicador.getFuente());
				indicadorActual.setGuia(indicador.getGuia());
				indicadorActual.setIndicadorAsociadoId(indicador.getIndicadorAsociadoId());
				indicadorActual.setIndicadorAsociadoRevision(indicador.getIndicadorAsociadoRevision());
				indicadorActual.setIndicadorAsociadoTipo(indicador.getIndicadorAsociadoTipo());
				indicadorActual.setMostrarEnArbol(indicador.getMostrarEnArbol());
				indicadorActual.setMultidimensional(indicador.getMultidimensional());
				indicadorActual.setNaturaleza(indicador.getNaturaleza());
				indicadorActual.setNombre(indicador.getNombre());
				indicadorActual.setNombreCorto(indicador.getNombreCorto());
				indicadorActual.setNumeroDecimales(indicador.getNumeroDecimales());
				indicadorActual.setOrden(indicador.getOrden());
				indicadorActual.setOrganizacionId(indicador.getOrganizacionId());
				indicadorActual.setParametroInferiorIndicadorId(indicador.getParametroInferiorIndicadorId());
				indicadorActual.setParametroInferiorValorFijo(indicador.getParametroInferiorValorFijo());
				indicadorActual.setParametroSuperiorIndicadorId(indicador.getParametroSuperiorIndicadorId());
				indicadorActual.setParametroSuperiorValorFijo(indicador.getParametroSuperiorValorFijo());
				indicadorActual.setPrioridad(indicador.getPrioridad());
				indicadorActual.setResponsableCargarEjecutadoId(indicador.getResponsableCargarEjecutadoId());
				indicadorActual.setResponsableCargarMetaId(indicador.getResponsableCargarMetaId());
				indicadorActual.setResponsableFijarMetaId(indicador.getResponsableFijarMetaId());
				indicadorActual.setResponsableLograrMetaId(indicador.getResponsableLograrMetaId());
				indicadorActual.setResponsableSeguimientoId(indicador.getResponsableSeguimientoId());
				indicadorActual.setResponsableNotificacionId(indicador.getResponsableNotificacionId());
				indicadorActual.setSoloLectura(indicador.getSoloLectura());
				indicadorActual.setTipoCargaMedicion(indicador.getTipoCargaMedicion());
				indicadorActual.setTipoFuncion(indicador.getTipoFuncion());
				indicadorActual.setTipoSumaMedicion(indicador.getTipoSumaMedicion());
				indicadorActual.setUltimaMedicion(indicador.getUltimaMedicion());
				indicadorActual.setUltimoProgramado(indicador.getUltimoProgramado());
				indicadorActual.setUnidadId(indicador.getUnidadId());
				indicadorActual.setUrl(indicador.getUrl());
				indicadorActual.setValorInicialCero(indicador.getValorInicialCero());
				indicadorActual.setPorcentajeCumplimiento(indicador.getPorcentajeCumplimiento());
				indicadorActual.setNombreUnidad(indicador.getNombreUnidad());
				indicadorUpdated=indicadorService.save(indicadorActual);
			
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
		@DeleteMapping("/indicador/{id}/{perspectivaId}")
		public ResponseEntity<?> delete(@PathVariable Long id, @PathVariable Long perspectivaId) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				indicadorPerspectivaService.deleteIndicadorPerspectiva(perspectivaId, id);
				
				indicadorService.delete(id);
				
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el indicador en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El indicador ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
