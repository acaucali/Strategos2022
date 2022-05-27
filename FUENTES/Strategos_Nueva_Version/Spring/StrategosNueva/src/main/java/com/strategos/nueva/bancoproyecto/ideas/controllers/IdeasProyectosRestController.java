package com.strategos.nueva.bancoproyecto.ideas.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.strategos.nueva.bancoproyecto.ideas.model.EstatusIdeas;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.TiposPropuestas;
import com.strategos.nueva.bancoproyecto.ideas.service.EstatusIdeaService;
import com.strategos.nueva.bancoproyecto.ideas.service.IdeasProyectosService;
import com.strategos.nueva.bancoproyecto.ideas.service.TiposObjetivosService;
import com.strategos.nueva.bancoproyecto.ideas.service.TiposPropuestasService;
import com.strategos.nueva.bancoproyecto.strategos.model.OrganizacionesStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;
import com.strategos.nueva.bancoproyectos.model.util.FIltroIdea;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class IdeasProyectosRestController {
	
	@Autowired
	private IdeasProyectosService ideasProyectosService;
	@Autowired
	private EstatusIdeaService estatusService;
	@Autowired
	private TiposObjetivosService objetivosService;
	@Autowired
	private TiposPropuestasService propuestasService;
	@Autowired
	private OrganizacionService organizacionService;
	
	//Servicios Rest tabla - idea
	
		private final Logger log = LoggerFactory.getLogger(IdeasProyectosRestController.class);
		
		//servicio que trae la lista de idea
		@GetMapping("/idea")
		public List<IdeasProyectos> index (){
			return ideasProyectosService.findAll();
		}
		
		//servicio que trae la lista de idea
		@GetMapping("/idea/filtro/{orgId}/{propuestaId}/{estatusId}/{anio}/{historico}")
		public List<IdeasProyectos> indexFiltro(@PathVariable String orgId, @PathVariable String propuestaId, @PathVariable String estatusId, @PathVariable String anio, @PathVariable String historico){
			
			FIltroIdea filtro = new FIltroIdea();
			
			Long org;
			Long propId;
			Long estId;
			String ani;
			Boolean histor;
			
			List<IdeasProyectos> ideasFin = new ArrayList();
			
			if(!orgId.equals("undefined")) {
				if(!orgId.equals("0")) {
					org = Long.parseLong(orgId);
					filtro.setOrganizacionId(org);
				}
				
			}
			if(!propuestaId.equals("undefined")) {
				if(!propuestaId.equals("0")) {
					propId = Long.parseLong(propuestaId);
					filtro.setPropuestaId(propId);;
				}
				
			}
			if(!estatusId.equals("undefined")) {
				if(!estatusId.equals("0")) {
					estId = Long.parseLong(estatusId);
					filtro.setEstatusId(estId);
				}
				
			}
			if(!anio.equals("undefined")) {
				if(!anio.equals("0")) {
					ani = anio;
					filtro.setAnio(ani);
				}		
				
			}
			if(!historico.equals("undefined") ) {
				histor = Boolean.getBoolean(historico);
				filtro.setHistorico(histor);
			}
			
			ideasFin = ideasProyectosService.queryFiltros(filtro);
			
			System.out.println(" "+orgId + propuestaId+ estatusId+ anio+ historico);
					
			return ideasFin;
		}
		
		//servicio que trae la lista de idea
		@GetMapping("/idea/org/{id}")
		public List<IdeasProyectos> index(@PathVariable Long id){
			
			List<IdeasProyectos> ideasOrg = ideasProyectosService.findAll(); 
			List<IdeasProyectos> ideasFin = new ArrayList();
			
			for(IdeasProyectos ide: ideasOrg) {
				if(ide.getDependenciaId() == id) {
					ideasFin.add(ide);
				}				
			}
			
			return ideasFin;
		}
				
		//servicio que muestra un idea
		@GetMapping("/idea/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			IdeasProyectos ideasProyectosId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				ideasProyectosId= ideasProyectosService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(ideasProyectosId == null) {
			  response.put("mensaje", "La idea proyecto Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<IdeasProyectos>(ideasProyectosId, HttpStatus.OK); 		
		}
		
		//servicio que crea un idea
		@PostMapping("/idea")
		public ResponseEntity<?> create(@Valid @RequestBody IdeasProyectos ideasProyectoN, BindingResult result) {
			
			IdeasProyectos ideasProyectosNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				if(ideasProyectoN.getDependenciaId() != null) {
					OrganizacionesStrategos org = organizacionService.findById(ideasProyectoN.getDependenciaId());
					ideasProyectoN.setOrganizacion(org.getNombre());
				}
				if(ideasProyectoN.getTipoPropuestaId() != null) {
					TiposPropuestas tipo = propuestasService.findById(ideasProyectoN.getTipoPropuestaId());
					ideasProyectoN.setPropuesta(tipo.getTipoPropuesta());
				}
				if(ideasProyectoN.getEstatusIdeaId() != null) {
					EstatusIdeas est = estatusService.findById(ideasProyectoN.getEstatusIdeaId());
					ideasProyectoN.setEstatus(est.getEstatus());
				}
				ideasProyectoN.setHistorico(false);
				ideasProyectosNew= ideasProyectosService.save(ideasProyectoN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La idea proyectos ha sido creado con Exito!");
			response.put("ideaproyectos", ideasProyectosNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un idea
		@PutMapping("/idea/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody IdeasProyectos ideasProyectos, BindingResult result, @PathVariable Long id) {
			IdeasProyectos ideasProyectosActual= ideasProyectosService.findById(id);
			IdeasProyectos ideasProyectosUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(ideasProyectosActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, la idea proyecto ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				ideasProyectosActual.setDependenciasParticipantes(ideasProyectos.getDependenciasParticipantes());
				ideasProyectosActual.setAnioFormulacion(ideasProyectos.getAnioFormulacion());
				ideasProyectosActual.setCapacidadTecnica(ideasProyectos.getCapacidadTecnica());
				ideasProyectosActual.setDependenciaId(ideasProyectos.getDependenciaId());
				ideasProyectosActual.setDescripcionIdea(ideasProyectos.getDescripcionIdea());
				
				ideasProyectosActual.setDependenciaPersona(ideasProyectos.getDependenciaPersona());
				ideasProyectosActual.setFechaEstatus(ideasProyectos.getFechaEstatus());
				ideasProyectosActual.setFechaIdea(ideasProyectos.getFechaIdea());
				ideasProyectosActual.setFechaUltimaEvaluacion(ideasProyectos.getFechaUltimaEvaluacion());
				ideasProyectosActual.setFinanciacion(ideasProyectos.getFinanciacion());
				ideasProyectosActual.setFocalizacion(ideasProyectos.getFocalizacion());
				ideasProyectosActual.setHistorico(ideasProyectos.getHistorico());
				ideasProyectosActual.setImpacto(ideasProyectos.getImpacto());
				ideasProyectosActual.setNombreIdea(ideasProyectos.getNombreIdea());
				ideasProyectosActual.setObservaciones(ideasProyectos.getObservaciones());
				ideasProyectosActual.setPersonaContactoDatos(ideasProyectos.getPersonaContactoDatos());
				ideasProyectosActual.setPoblacion(ideasProyectos.getPoblacion());
				ideasProyectosActual.setProblematica(ideasProyectos.getProblematica());
				ideasProyectosActual.setProyectosEjecutados(ideasProyectos.getProyectosEjecutados());
				
				ideasProyectosActual.setValorUltimaEvaluacion(ideasProyectos.getValorUltimaEvaluacion());				
				ideasProyectosActual.setAnioFormulacion(ideasProyectos.getAnioFormulacion());
				ideasProyectosActual.setEstatusIdeaId(ideasProyectos.getEstatusIdeaId());
				ideasProyectosActual.setDocumentoId(ideasProyectos.getDocumentoId());
				ideasProyectosActual.setTipoObjetivoId(ideasProyectos.getTipoObjetivoId());
				ideasProyectosActual.setTipoPropuestaId(ideasProyectos.getTipoPropuestaId());
				
				if(ideasProyectos.getDependenciaId() != null) {
					OrganizacionesStrategos org = organizacionService.findById(ideasProyectos.getDependenciaId());
					ideasProyectosActual.setOrganizacion(org.getNombre());
				}
				if(ideasProyectos.getTipoPropuestaId() != null) {
					TiposPropuestas tipo = propuestasService.findById(ideasProyectos.getTipoPropuestaId());
					ideasProyectosActual.setPropuesta(tipo.getTipoPropuesta());
				}
				if(ideasProyectos.getEstatusIdeaId() != null) {
					EstatusIdeas est = estatusService.findById(ideasProyectos.getEstatusIdeaId());
					ideasProyectosActual.setEstatus(est.getEstatus());
				}
																			
				ideasProyectosUpdated=ideasProyectosService.save(ideasProyectosActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar la idea proyecto en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La idea proyecto ha sido actualizado con Exito!");
			response.put("ideasproyectos", ideasProyectosUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina el idea
		@DeleteMapping("/idea/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				ideasProyectosService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar la idea proyecto en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La idea proyecto ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}
		
		
	

		
}
