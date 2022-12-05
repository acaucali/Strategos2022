package com.strategos.nueva.bancoproyecto.ideas.controllers;

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

import com.strategos.nueva.bancoproyecto.ideas.model.Departamentos;
import com.strategos.nueva.bancoproyecto.ideas.model.EstatusIdeas;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.Proyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosPlan;
import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosPoblacion;
import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosRegion;
import com.strategos.nueva.bancoproyecto.ideas.model.TipoPoblacion;
import com.strategos.nueva.bancoproyecto.ideas.model.TiposPropuestas;
import com.strategos.nueva.bancoproyecto.ideas.service.DepartamentosService;
import com.strategos.nueva.bancoproyecto.ideas.service.EstatusIdeaService;
import com.strategos.nueva.bancoproyecto.ideas.service.IdeasProyectosService;
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosPlanService;
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosPoblacionService;
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosRegionService;
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosService;
import com.strategos.nueva.bancoproyecto.ideas.service.TipoPoblacionService;
import com.strategos.nueva.bancoproyecto.strategos.model.ClaseIndicadoresStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.IniciativaEstatusStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.OrganizacionesStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.PerspectivaStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.PlanStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.TipoProyectoStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.ClaseIndicadorService;
import com.strategos.nueva.bancoproyecto.strategos.service.IniciativaEstatusService;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;
import com.strategos.nueva.bancoproyecto.strategos.service.PerspectivaService;
import com.strategos.nueva.bancoproyecto.strategos.service.PlanService;
import com.strategos.nueva.bancoproyecto.strategos.service.TipoProyectoService;
import com.strategos.nueva.bancoproyectos.model.util.FIltroIdea;


@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class ProyectoRestController {
	
	@Autowired
	private ProyectosService proyectoService;
	
	@Autowired
	private IdeasProyectosService ideasProyectosService;
	
	@Autowired
	private OrganizacionService organizacionService;
	
	@Autowired
	private ClaseIndicadorService claseService;
	
	@Autowired
	private IniciativaEstatusService estatusService;
	
	@Autowired
	private EstatusIdeaService estatusIdeaService;
	
	@Autowired
	private PlanService planService;
	
	@Autowired
	private PerspectivaService perspectivaService;
	
	@Autowired
	private TipoProyectoService tipoService;
	
	@Autowired
	private ProyectosPoblacionService proyectosPoblacionService;
	
	@Autowired
	private TipoPoblacionService tipoPoblacionService;

	@Autowired
	private ProyectosPlanService proyectoPlanService;
	
	@Autowired
	private ProyectosRegionService proyectoRegionService;
	
	@Autowired
	private DepartamentosService departamentosService;
	
	//Servicios Rest tabla - proyecto
	
		private final Logger log = LoggerFactory.getLogger(ProyectoRestController.class);
		
		//servicio que trae la lista de proyecto
		@GetMapping("/proyecto")
		public List<Proyectos> index (){
			return proyectoService.findAll();
		}
		
		//servicio que trae la lista de idea
		@GetMapping("/proyecto/filtro/{orgId}/{tipoId}/{estatusId}/{anio}/{historico}")
		public List<Proyectos> indexFiltro(@PathVariable String orgId, @PathVariable String tipoId, @PathVariable String estatusId, @PathVariable String anio, @PathVariable String historico){
					
					FIltroIdea filtro = new FIltroIdea();
					
					Long org;
					Long tipId;
					Long estId;
					String ani;
					Boolean histor;
					
					List<Proyectos> proyectosFin = new ArrayList();
					
					if(!orgId.equals("undefined")) {
						if(!orgId.equals("0")) {
							org = Long.parseLong(orgId);
							filtro.setOrganizacionId(org);
						}
						
					}
					if(!tipoId.equals("undefined")) {
						if(!tipoId.equals("0")) {
							tipId = Long.parseLong(tipoId);
							filtro.setTipoId(tipId);
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
					
					proyectosFin = proyectoService.queryFiltros(filtro);
					
					System.out.println(" "+orgId + tipoId+ estatusId+ anio+ historico);
							
					return proyectosFin;
		}
		
		//servicio que trae la lista de proyectos por organizacion
		@GetMapping("/proyecto/org/{id}")
		public List<Proyectos> index(@PathVariable Long id){
					
			List<Proyectos> proyectosOrg = proyectoService.findAllByDependenciaId(id); 								
			return proyectosOrg;
		}
		
		//servicio que trae la lista de proyectos por estatus y organizacion
		@GetMapping("/proyecto/orgestatus/{orgId}/{estatusId}")
		public List<Proyectos> indexEstatusOrg(@PathVariable Long orgId ,@PathVariable Long estatusId){
							
			List<Proyectos> proyectosOrg = proyectoService.findAllByDependenciaIdAndEstatusId(orgId, estatusId); 								
			return proyectosOrg;
		}
				
				
		//servicio que trae la lista de proyectos por estatus
		@GetMapping("/proyecto/estatus/{estatusId}")
		public List<Proyectos> indexEstatus(@PathVariable Long estatusId){
							
			List<Proyectos> proyectosOrg = proyectoService.findAllByEstatusId(estatusId); 								
			return proyectosOrg;
		}
		
		//servicio que trae la lista de proyectos por estatus y organizacion
		@GetMapping("/proyecto/orgtipo/{orgId}/{isPreproyecto}")
		public List<Proyectos> indexTipoOrg(@PathVariable Long orgId ,@PathVariable Boolean isPreproyecto){
									
			List<Proyectos> proyectosOrg = proyectoService.findAllByDependenciaIdAndIsPreproyecto(orgId, isPreproyecto); 								
			return proyectosOrg;
		}
						
						
		//servicio que trae la lista de proyectos por estatus
		@GetMapping("/proyecto/tipo/{isPreproyecto}")
		public List<Proyectos> indexTipo(@PathVariable Boolean isPreproyecto){
									
			List<Proyectos> proyectosOrg = proyectoService.findAllByIsPreproyecto(isPreproyecto); 								
			return proyectosOrg;
		}
		
		//servicio que trae la lista de poblaciones
		@GetMapping("/proyecto/poblacion/{id}")
		public List<TipoPoblacion> indexPoblaciones(@PathVariable Long id){
			
			List<TipoPoblacion> poblacionesPro = new ArrayList<TipoPoblacion>();
			List<ProyectosPoblacion> proyectos = proyectosPoblacionService.findAllByProyectoId(id);
			
			for(ProyectosPoblacion pro: proyectos) {
				TipoPoblacion poblacion= tipoPoblacionService.findById(pro.getPoblacionId());
				poblacionesPro.add(poblacion);
			}
											
			return poblacionesPro;
		}
		
		//servicio que trae la lista de departamentos
		@GetMapping("/proyecto/departamento/{id}")
		public List<Departamentos> indexDepartamentos(@PathVariable Long id){
			List<Departamentos> departamentos = new ArrayList<Departamentos>();
			List<ProyectosRegion> proyectos = proyectoRegionService.findAllByDepartamentoId(id);
			
			for(ProyectosRegion pro: proyectos) {
				Departamentos departamento = departamentosService.findById(pro.getDepartamentoId());
				departamentos.add(departamento);
			}
			
			return departamentos;
		}
		
		@GetMapping("/proyecto/proyectosRegion/{id}")
		public List<ProyectosRegion> indexProyectosRegion(@PathVariable Long id){
			List<ProyectosRegion> proyectos = proyectoRegionService.findAllByProyectoId(id);
			
			return proyectos;
		}	
			
		//servicio que muestra un proyecto
		@GetMapping("/proyecto/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			Proyectos proyectoId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				proyectoId= proyectoService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(proyectoId == null) {
			  response.put("mensaje", "El proyecto Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Proyectos>(proyectoId, HttpStatus.OK); 		
		}
		
		//servicio que crea un proyecto
		@PostMapping("/proyecto")
		public ResponseEntity<?> create(@Valid @RequestBody Proyectos proyectoN, BindingResult result) {
			
			List<TipoPoblacion> proyectosPoblacion = new ArrayList();
			List<ProyectosRegion> proyectosRegion = new ArrayList();
			Proyectos proyectoNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				//sin iniciar
				//crear estatus preproyecto
				//cambiar idea a preproyecto
				
				if(proyectoN.getDependenciaId() != null) {
					OrganizacionesStrategos org = organizacionService.findById(proyectoN.getDependenciaId());
					proyectoN.setOrganizacion(org.getNombre());
				}
				if(proyectoN.getTipoProyectoId() != null) {
					TipoProyectoStrategos tipo = tipoService.findById(proyectoN.getTipoProyectoId());
					proyectoN.setTipo(tipo.getNombre());
				}
				if(proyectoN.getEstatusId() != null) {
					IniciativaEstatusStrategos est = estatusService.findById(proyectoN.getEstatusId());
					proyectoN.setEstatus(est.getNombre());
					proyectoN.setFechaEstatus(new Date());
				}
				proyectoN.setHistorico(false);
				proyectoN.setIsPreproyecto(true);
				
				
				IdeasProyectos idea = ideasProyectosService.findById(proyectoN.getIdeaId());
				
				if(idea.getEstatusIdeaId() != null) {
					EstatusIdeas est = estatusIdeaService.findById(new Long(6));
					idea.setEstatus(est.getEstatus());
					idea.setFechaEstatus(new Date());
				}
				
				ideasProyectosService.save(idea);
				
				proyectosPoblacion = proyectoN.getPoblaciones();
				
				proyectoNew= proyectoService.save(proyectoN);	
				
				for(TipoPoblacion tip: proyectosPoblacion) {
					ProyectosPoblacion proyectoPoblacion = new ProyectosPoblacion();
					proyectoPoblacion.setProyectoId(proyectoNew.getProyectoId());
					proyectoPoblacion.setPoblacionId(tip.getTipoPoblacionId());
					proyectosPoblacionService.save(proyectoPoblacion);
				}
				
				proyectosRegion = proyectoN.getDepartamentos();			
				
				if(proyectosRegion != null) {
				
					for(ProyectosRegion dep : proyectosRegion) {
						ProyectosRegion proyectoRegion = new ProyectosRegion();
						proyectoRegion.setProyectoId(proyectoNew.getProyectoId());
						proyectoRegion.setDepartamentoId(dep.getDepartamentoId());
						proyectoRegion.setMunicipioId(dep.getMunicipioId());						
						proyectoRegionService.save(proyectoRegion);
					}
				}
				

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El proyecto ha sido creado con Exito!");
			response.put("proyecto", proyectoNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un proyecto
		@PutMapping("/proyecto/{id}/{tipo}")
		public ResponseEntity<?>  update(@Valid @RequestBody Proyectos proyecto, BindingResult result, @PathVariable Long id, @PathVariable Byte tipo) {
			Proyectos proyectoActual= proyectoService.findById(id);
			Proyectos proyectoUpdated = null;
			List<TipoPoblacion> proyectosPoblacion = new ArrayList();
			List<ProyectosRegion> proyectosRegion = new ArrayList();
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(proyectoActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el proyecto ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
							
				if(tipo == 1) {// preproyecto
					proyectoActual.setAlcance(proyecto.getAlcance()); //idea
					proyectoActual.setAnioFormulacion(proyecto.getAnioFormulacion());//idea
					proyectoActual.setAntecedentes(proyecto.getAntecedentes());
					proyectoActual.setCobertura(proyecto.getCobertura());
					proyectoActual.setCodigoBdp(proyecto.getCodigoBdp());
					proyectoActual.setContactoEmail(proyecto.getContactoEmail());//idea
					proyectoActual.setContactoTelefono(proyecto.getContactoTelefono());//idea
					proyectoActual.setCostoEstimado(proyecto.getCostoEstimado());
					proyectoActual.setDependenciaId(proyecto.getDependenciaId());//idea
					proyectoActual.setDependenciaLider(proyecto.getDependenciaLider());//idea
					proyectoActual.setDependenciasEstrategicas(proyecto.getDependenciasEstrategicas());
					proyectoActual.setDuracion(proyecto.getDuracion()); //>0
					proyectoActual.setEstatusId(proyecto.getEstatusId());
					proyectoActual.setFechaEstatus(proyecto.getFechaEstatus());
					proyectoActual.setFechaFormulacion(proyecto.getFechaFormulacion());//idea
					proyectoActual.setFechaRadicacion(proyecto.getFechaRadicacion());
					proyectoActual.setFinanciacion(proyecto.getFinanciacion());//idea
					proyectoActual.setHistorico(proyecto.getHistorico());
					proyectoActual.setJustificacion(proyecto.getJustificacion());
					proyectoActual.setMetodologiaId(proyecto.getMetodologiaId());//idea
					proyectoActual.setNombreProyecto(proyecto.getNombreProyecto());//idea
					proyectoActual.setObjetivoGeneral(proyecto.getObjetivoGeneral());//idea
					proyectoActual.setPertinencia(proyecto.getPertinencia());
					proyectoActual.setProblematica(proyecto.getProblematica());//idea
					proyectoActual.setProfesionalResponsable(proyecto.getProfesionalResponsable());//idea
					proyectoActual.setIdeaId(proyecto.getIdeaId());//idea
					proyectoActual.setRolesParticipantes(proyecto.getRolesParticipantes());
					proyectoActual.setSociosEstrategicos(proyecto.getSociosEstrategicos());
					proyectoActual.setTipoObjetivoId(proyecto.getTipoObjetivoId());//idea
					proyectoActual.setTipoProyectoId(proyecto.getTipoProyectoId());
					
					proyectoActual.setIsPreproyecto(true);
					
					if(proyectoActual.getEstatusId() != null) {
						IniciativaEstatusStrategos est = estatusService.findById(proyectoActual.getEstatusId());
						proyectoActual.setEstatus(est.getNombre());
						proyectoActual.setFechaEstatus(new Date());
					}
					
					List<ProyectosPoblacion> proyectos = proyectosPoblacionService.findAllByProyectoId(id);
					
					for(ProyectosPoblacion pro: proyectos) {
						proyectosPoblacionService.delete(pro.getProyectoPoblacionId());
					}
					//proyectoService.delete(id);
					
					proyectosPoblacion = proyecto.getPoblaciones();					
									
					for(TipoPoblacion tip: proyectosPoblacion) {
						ProyectosPoblacion proyectoPoblacion = new ProyectosPoblacion();
						proyectoPoblacion.setProyectoId(proyectoUpdated.getProyectoId());
						proyectoPoblacion.setPoblacionId(tip.getTipoPoblacionId());
						proyectosPoblacionService.save(proyectoPoblacion);
					}
					
					List<ProyectosRegion> proyectosReg = proyectoRegionService.findAllByDepartamentoId(id);
					
					for(ProyectosRegion pro : proyectosReg) {
						proyectoRegionService.delete(pro.getProyectoRegionId());
					}
					
					proyectosRegion = proyecto.getDepartamentos();
					
					if(proyectosRegion != null) {
						for(ProyectosRegion dep : proyectosRegion) {
							ProyectosRegion proyectoRegion = new ProyectosRegion();
							proyectoRegion.setProyectoId(proyecto.getProyectoId());
							proyectoRegion.setDepartamentoId(dep.getDepartamentoId());
							proyectoRegion.setMunicipioId(dep.getMunicipioId());
							proyectoRegion.setDepartamentoNombre(dep.getDepartamentoNombre());
							proyectoRegion.setMunicipioNombre(dep.getMunicipioNombre());
							proyectoRegionService.save(proyectoRegion);
						}
					}
					
					proyectoUpdated=proyectoService.save(proyectoActual);
					
				}else {//proyecto
					
					proyectoActual.setAlcance(proyecto.getAlcance()); //idea
					proyectoActual.setAnioFormulacion(proyecto.getAnioFormulacion());//idea
					proyectoActual.setAntecedentes(proyecto.getAntecedentes());
					proyectoActual.setCobertura(proyecto.getCobertura());
					proyectoActual.setCodigoBdp(proyecto.getCodigoBdp());
					proyectoActual.setContactoEmail(proyecto.getContactoEmail());//idea
					proyectoActual.setContactoTelefono(proyecto.getContactoTelefono());//idea
					proyectoActual.setCostoEstimado(proyecto.getCostoEstimado());
					proyectoActual.setDependenciaId(proyecto.getDependenciaId());//idea
					proyectoActual.setDependenciaLider(proyecto.getDependenciaLider());//idea
					proyectoActual.setDependenciasEstrategicas(proyecto.getDependenciasEstrategicas());
					proyectoActual.setDuracion(proyecto.getDuracion()); //>0
					proyectoActual.setEstatusId(proyecto.getEstatusId());
					proyectoActual.setFechaEstatus(proyecto.getFechaEstatus());
					proyectoActual.setFechaFormulacion(proyecto.getFechaFormulacion());//idea
					proyectoActual.setFechaRadicacion(proyecto.getFechaRadicacion());
					proyectoActual.setFinanciacion(proyecto.getFinanciacion());//idea
					proyectoActual.setHistorico(proyecto.getHistorico());
					proyectoActual.setJustificacion(proyecto.getJustificacion());
					proyectoActual.setMetodologiaId(proyecto.getMetodologiaId());//idea
					proyectoActual.setNombreProyecto(proyecto.getNombreProyecto());//idea
					proyectoActual.setObjetivoGeneral(proyecto.getObjetivoGeneral());//idea
					proyectoActual.setPertinencia(proyecto.getPertinencia());
					proyectoActual.setProblematica(proyecto.getProblematica());//idea
					proyectoActual.setProfesionalResponsable(proyecto.getProfesionalResponsable());//idea
					proyectoActual.setIdeaId(proyecto.getIdeaId());//idea
					proyectoActual.setRolesParticipantes(proyecto.getRolesParticipantes());
					proyectoActual.setSociosEstrategicos(proyecto.getSociosEstrategicos());
					proyectoActual.setTipoObjetivoId(proyecto.getTipoObjetivoId());//idea
					proyectoActual.setTipoProyectoId(proyecto.getTipoProyectoId());
					proyectoActual.setFrecuencia(proyecto.getFrecuencia());
					proyectoActual.setIsPreproyecto(false);
					
					proyectoActual.setCooperante(proyecto.getCooperante());
					proyectoActual.setDependenciasConvenio(proyecto.getDependenciasConvenio());
					proyectoActual.setNumeroConvenio(proyecto.getNumeroConvenio());
					proyectoActual.setFechaInicioConvenio(proyecto.getFechaInicioConvenio());
					proyectoActual.setFechaCulminacionConvenio(proyecto.getFechaCulminacionConvenio());
					proyectoActual.setNombreOperador(proyecto.getNombreOperador());
					proyectoActual.setContactoEmailOperador(proyecto.getContactoEmailOperador());
					proyectoActual.setContactoTelefonoOperador(proyecto.getContactoTelefonoOperador());
					proyectoActual.setRecursosAsignados(proyecto.getRecursosAsignados());
					proyectoActual.setProrrogas(proyecto.getProrrogas());
										
					if(proyectoActual.getEstatusId() != null) {
						System.out.print("\n\n" + proyectoActual.getEstatusId());
						IniciativaEstatusStrategos est = estatusService.findById(proyectoActual.getEstatusId());
						System.out.print("\n\n" + est);
						proyectoActual.setEstatus(est.getNombre());
						proyectoActual.setFechaEstatus(new Date());
					}
					
					/*
					List<ProyectosPoblacion> proyectos = proyectosPoblacionService.findAllByProyectoId(id);
					
					for(ProyectosPoblacion pro: proyectos) {
						proyectosPoblacionService.delete(pro.getProyectoPoblacionId());
					}
					//proyectoService.delete(id);
					
					
					proyectosPoblacion = proyecto.getPoblaciones();					
									
					for(TipoPoblacion tip: proyectosPoblacion) {
						ProyectosPoblacion proyectoPoblacion = new ProyectosPoblacion();
						proyectoPoblacion.setProyectoId(proyecto.getProyectoId());
						proyectoPoblacion.setPoblacionId(tip.getTipoPoblacionId());
						proyectosPoblacionService.save(proyectoPoblacion);
					}
					*/
					
					proyectosRegion = proyecto.getDepartamentos();
					
					if(proyectosRegion != null) {
						for(ProyectosRegion dep : proyectosRegion) {
							if(dep.getProyectoRegionId() == null) {
								ProyectosRegion proyectoRegion = new ProyectosRegion();
								proyectoRegion.setProyectoId(proyecto.getProyectoId());
								proyectoRegion.setDepartamentoId(dep.getDepartamentoId());
								proyectoRegion.setMunicipioId(dep.getMunicipioId());
								proyectoRegion.setDepartamentoNombre(dep.getDepartamentoNombre());
								proyectoRegion.setMunicipioNombre(dep.getMunicipioNombre());
								proyectoRegionService.save(proyectoRegion);
							}
						}
					}
					
					proyectoUpdated=proyectoService.save(proyectoActual);
					
					
					
					ProyectosPlan proyectoPlanAct = proyectoPlanService.findAllByProyectoId(id);		
					
					if(proyectoPlanAct == null) {
						
						// creacion del plan 
						
						PlanStrategos plan = new PlanStrategos();
						
						plan.setActivo((byte) 1);
						plan.setAnoFinal(Integer.parseInt(proyecto.getAnioFormulacion()));
						plan.setAnoInicial(Integer.parseInt(proyecto.getAnioFormulacion()));
						plan.setNombre(proyecto.getNombreProyecto());
						plan.setTipo((byte) 1);
						plan.setRevision((byte) 0);
						plan.setMetodologiaId(proyecto.getMetodologiaId());
						plan.setOrganizacionId(proyecto.getDependenciaId());
						ClaseIndicadoresStrategos clase = claseService.findByClaseRaiz(proyecto.getDependenciaId(), (byte) 0); 
						
						plan.setClaseId(clase.getClaseId());
						
						planService.save(plan);
						
						
						ProyectosPlan proyectoPlan = new ProyectosPlan();
						
						proyectoPlan.setPlanId(plan.getPlanId());
						proyectoPlan.setProyectoId(proyecto.getProyectoId());
						
						proyectoPlanService.save(proyectoPlan);
						
						PerspectivaStrategos perspectiva = new PerspectivaStrategos();
					
						perspectiva.setClaseId(plan.getClaseId());
						perspectiva.setPlanId(plan.getPlanId());
						perspectiva.setNombre(plan.getNombre());
						perspectiva.setFrecuencia(proyecto.getFrecuencia());
						perspectiva.setTipo(plan.getTipo());
						perspectiva.setTipoCalculo((byte) 1);
						perspectiva.setCreado(new Date());
						
						
						perspectivaService.save(perspectiva);
						
					}
					
					
					
				}
				
				
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar el proyecto en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El proyecto ha sido actualizado con Exito!");
			response.put("proyecto", proyectoUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina el proyecto
		@DeleteMapping("/proyecto/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				Proyectos proyecto = proyectoService.findById(id);
				
				if(proyecto.getIdeaId() != null) {
					IdeasProyectos idea = ideasProyectosService.findById(proyecto.getIdeaId());
					
					idea.setEstatusIdeaId((long) 2);
					
					if(idea.getEstatusIdeaId() != null) {
						EstatusIdeas est = estatusIdeaService.findById(idea.getEstatusIdeaId());
						idea.setEstatus(est.getEstatus());
						idea.setFechaEstatus(new Date());
					}
					
					ideasProyectosService.save(idea);
				}
				
				
				List<ProyectosPoblacion> proyectos = proyectosPoblacionService.findAllByProyectoId(id);
				
				for(ProyectosPoblacion pro: proyectos) {
					proyectosPoblacionService.delete(pro.getProyectoPoblacionId());
				}
				proyectoService.delete(id);
								
				
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el proyecto en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El proyecto ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
