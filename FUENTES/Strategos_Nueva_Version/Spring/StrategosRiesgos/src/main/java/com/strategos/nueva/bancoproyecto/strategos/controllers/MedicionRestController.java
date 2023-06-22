package com.strategos.nueva.bancoproyecto.strategos.controllers;

import java.text.DecimalFormat;
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

import com.strategos.nueva.bancoproyecto.ideas.model.Actividad;
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasEvaluadas;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.Iniciativa;
import com.strategos.nueva.bancoproyecto.ideas.model.Presupuesto;
import com.strategos.nueva.bancoproyecto.ideas.model.PresupuestoDatos;
import com.strategos.nueva.bancoproyecto.ideas.service.ActividadService;
import com.strategos.nueva.bancoproyecto.ideas.service.IniciativaService;
import com.strategos.nueva.bancoproyecto.ideas.service.PresupuestoDatosService;
import com.strategos.nueva.bancoproyecto.ideas.service.PresupuestoService;
import com.strategos.nueva.bancoproyecto.ideas.service.TiposPropuestasService;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorPerspectiva;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorTareaStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.MedicionPkStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.MedicionStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.PerspectivaStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorPerspectivaService;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorService;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorTareaService;
import com.strategos.nueva.bancoproyecto.strategos.service.MedicionService;
import com.strategos.nueva.bancoproyecto.strategos.service.PerspectivaService;
import com.strategos.nueva.bancoproyectos.model.util.DatoIdea;
import com.strategos.nueva.bancoproyectos.model.util.DatoMedicion;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class MedicionRestController {

	@Autowired
	private MedicionService medicionService;

	@Autowired
	private IndicadorPerspectivaService indicadorPerspectivaService;

	@Autowired
	private IndicadorService indicadorService;
	
	@Autowired
	private IniciativaService iniciativaService;

	@Autowired
	private PerspectivaService perspectivaService;

	@Autowired
	private ActividadService actividadService;

	@Autowired
	private IndicadorTareaService indicadorTareaService;
	
	@Autowired
	private PresupuestoService presupuestoService;
	
	@Autowired
	private PresupuestoDatosService presupuestoDatosService;


	// Servicios Rest tabla - medicion

	private final Logger log = LoggerFactory.getLogger(MedicionRestController.class);

	// servicio que trae la lista de medicion
	@GetMapping("/medicion")
	public List<MedicionStrategos> index() {
		return medicionService.findAll();
	}

	@GetMapping("/medicion/encabezados/{objId}/{frecuencia}/{anio}/{perIni}/{perFin}")
	public List<DatoIdea> getEncabezados(@PathVariable Long objId, @PathVariable Integer frecuencia,
			@PathVariable Integer anio, @PathVariable Integer perIni, @PathVariable Integer perFin) {

		List<DatoIdea> datos = new ArrayList<DatoIdea>();

		List<IndicadorStrategos> indicadores = new ArrayList<IndicadorStrategos>();
		List<MedicionStrategos> mediciones = new ArrayList<MedicionStrategos>();

		List<IndicadorPerspectiva> relaciones = new ArrayList<IndicadorPerspectiva>();

		relaciones = indicadorPerspectivaService.findByPerspectiva(objId);

		for (IndicadorPerspectiva indi : relaciones) {

			IndicadorStrategos ind = indicadorService.findById(indi.getPk().getIndicadorId());
			indicadores.add(ind);
		}

		DatoIdea dato = new DatoIdea();
		dato.setCampo("indicador");
		dato.setValor("Indicador");
		dato.setTamanio("400");

		datos.add(dato);

		dato = new DatoIdea();
		dato.setCampo("series");
		dato.setValor("Serie");
		dato.setTamanio("50");

		datos.add(dato);

		// indicadores

		String cadenaTitulo = "";

		switch (frecuencia) {

		case 1: {// Mensual

			for (int x = perIni; x <= perFin; x++) {

				dato = new DatoIdea();
				dato.setCampo("periodo");
				dato.setValor(obtenerMes(x));
				dato.setTamanio("50");

				datos.add(dato);

			}

			break;
		}

		case 2: {// Trimestral

			for (int x = perIni; x <= perFin; x++) {

				dato = new DatoIdea();
				dato.setCampo("periodo");
				dato.setValor(obtenerPeriodo(x));
				dato.setTamanio("50");

				datos.add(dato);

			}

			break;
		}

		default: {
			System.out.println("Opcion incorrecta");
		}
		}

		return datos;
	}

	// encabezados tarea
	@GetMapping("/medicion/encabezados/tarea/{actId}/{frecuencia}/{anio}/{perIni}/{perFin}")
	public List<DatoIdea> getEncabezadosTarea(@PathVariable Long actId, @PathVariable Integer frecuencia,
			@PathVariable Integer anio, @PathVariable Integer perIni, @PathVariable Integer perFin) {

		List<DatoIdea> datos = new ArrayList<DatoIdea>();

		List<IndicadorStrategos> indicadores = new ArrayList<IndicadorStrategos>();
		List<MedicionStrategos> mediciones = new ArrayList<MedicionStrategos>();

		List<Actividad> actividades = actividadService.findAllByProyectoId(actId);

		for (Actividad act : actividades) {

			List<IndicadorTareaStrategos> relaciones = new ArrayList<IndicadorTareaStrategos>();

			relaciones = indicadorTareaService.findByTarea(act.getActividadId());

			for (IndicadorTareaStrategos indi : relaciones) {
				IndicadorStrategos ind = indicadorService.findById(indi.getPk().getIndicadorId());
				indicadores.add(ind);
			}
		}

		DatoIdea dato = new DatoIdea();
		dato.setCampo("indicador");
		dato.setValor("Indicador");
		dato.setTamanio("400");

		datos.add(dato);

		dato = new DatoIdea();
		dato.setCampo("series");
		dato.setValor("Serie");
		dato.setTamanio("50");

		datos.add(dato);

		// indicadores

		String cadenaTitulo = "";

		switch (frecuencia) {

		case 1: {// Mensual

			for (int x = perIni; x <= perFin; x++) {

				dato = new DatoIdea();
				dato.setCampo("periodo");
				dato.setValor(obtenerMes(x));
				dato.setTamanio("50");

				datos.add(dato);

			}

			break;
		}

		case 2: {// Trimestral

			for (int x = perIni; x <= perFin; x++) {

				dato = new DatoIdea();
				dato.setCampo("periodo");
				dato.setValor(obtenerPeriodo(x));
				dato.setTamanio("50");

				datos.add(dato);

			}

			break;
		}

		default: {
			System.out.println("Opcion incorrecta");
		}
		}

		return datos;
	}

	// encabezados tarea
	@GetMapping("/medicion/encabezados/presupuesto")
	public List<DatoIdea> getEncabezadosPresupuesto() {

		List<DatoIdea> datos = new ArrayList<DatoIdea>();

		List<Presupuesto> presupuestos = presupuestoService.findAll();

		DatoIdea dato = new DatoIdea();
		dato.setCampo("periodo");
		dato.setValor("Periodo");
		dato.setTamanio("50");

		datos.add(dato);

		dato = new DatoIdea();
		dato.setCampo("series");
		dato.setValor("Serie");
		dato.setTamanio("50");

		datos.add(dato);

		// presupuestos

		for (Presupuesto pre : presupuestos) {
			
			dato = new DatoIdea();
			dato.setCampo("presupuesto");
			dato.setValor(pre.getPresupuesto());
			dato.setTamanio("100");

			datos.add(dato);
		}
		

		return datos;
	}

	@GetMapping("/medicion/ids/{objId}/{serie}")
	public List<Long> getIds(@PathVariable Long objId, @PathVariable Integer serie) {

		List<Long> ids = new ArrayList<Long>();
		List<IndicadorStrategos> indicadores = new ArrayList<IndicadorStrategos>();
		List<IndicadorPerspectiva> relaciones = new ArrayList<IndicadorPerspectiva>();

		relaciones = indicadorPerspectivaService.findByPerspectiva(objId);

		for (IndicadorPerspectiva indi : relaciones) {

			IndicadorStrategos ind = indicadorService.findById(indi.getPk().getIndicadorId());
			indicadores.add(ind);
		}

		for (IndicadorStrategos ind : indicadores) {

			ids.add(ind.getIndicadorId());

		}

		return ids;
	}
	
	
	@GetMapping("/medicion/periodos/grafico/{anio}/{perIni}/{perFin}")
	public List<String> getPeriodos(@PathVariable Integer anio, @PathVariable Integer perIni, @PathVariable Integer perFin) {

		List<String> periodos = new ArrayList<String>();

		for(int x=perIni; x<=perFin; x++) {
			
			String per= x+"-"+anio;
			periodos.add(per);
			
		}
		
		return periodos;
	}
	
	
	@GetMapping("/medicion/real/grafico/{ind}/{anio}/{perIni}/{perFin}")//0
	public List<String> getRealGrafico(@PathVariable Long ind, @PathVariable Integer anio, @PathVariable Integer perIni, @PathVariable Integer perFin) {

		List<String> datos = new ArrayList<String>();
		
		List<MedicionStrategos> mediciones = new ArrayList<MedicionStrategos>();

		for(int x=perIni; x<=perFin; x++) {
			
			String dato = "";
			
			mediciones = medicionService.findByPeriodos(ind, (long) 0, anio, x, x);
			if (mediciones.size() > 0) {
				for (MedicionStrategos med : mediciones) {
					dato = med.getValor().toString();
					datos.add(dato);
				}
			}else {
				datos.add(dato);
			}
						
		}
		
		return datos;
	}
	
	@GetMapping("/medicion/meta/grafico/{ind}/{anio}/{perIni}/{perFin}")//1
	public List<String> getMetaGrafico(@PathVariable Long ind, @PathVariable Integer anio, @PathVariable Integer perIni, @PathVariable Integer perFin) {

		List<String> datos = new ArrayList<String>();
		
		List<MedicionStrategos> mediciones = new ArrayList<MedicionStrategos>();

		for(int x=perIni; x<=perFin; x++) {
			
			String dato = "";
			
			mediciones = medicionService.findByPeriodos(ind, (long) 1, anio, x, x);
			if (mediciones.size() > 0) {
				for (MedicionStrategos med : mediciones) {
					dato = med.getValor().toString();
					datos.add(dato);
				}
			}else {
				datos.add(dato);
			}
						
		}
		
		return datos;
	}
	
	

	// ids indicadores

	@GetMapping("/medicion/ids/tareas/{actId}/{serie}")
	public List<Long> getIdsTareas(@PathVariable Long actId, @PathVariable Integer serie) {

		List<Long> ids = new ArrayList<Long>();
		List<IndicadorStrategos> indicadores = new ArrayList<IndicadorStrategos>();
		List<IndicadorTareaStrategos> relaciones = new ArrayList<IndicadorTareaStrategos>();

		List<Actividad> actividades = actividadService.findAllByProyectoId(actId);

		for (Actividad act : actividades) {

			relaciones = indicadorTareaService.findByTarea(act.getActividadId());

			for (IndicadorTareaStrategos indi : relaciones) {
				IndicadorStrategos ind = indicadorService.findById(indi.getPk().getIndicadorId());
				indicadores.add(ind);
			}
		}

		for (IndicadorStrategos ind : indicadores) {

			ids.add(ind.getIndicadorId());

		}

		return ids;
	}

	// tarea medicion

	@GetMapping("/medicion/mediciones/tarea/{actId}/{anio}/{perIni}/{perFin}/{serie}")
	public List<DatoMedicion> getDatosMedicionesTarea(@PathVariable Long actId, @PathVariable Integer anio,
			@PathVariable Integer perIni, @PathVariable Integer perFin, @PathVariable Integer serie) {

		List<DatoMedicion> datos = new ArrayList<DatoMedicion>();

		List<IndicadorStrategos> indicadores = new ArrayList<IndicadorStrategos>();
		List<MedicionStrategos> medicionesReal = new ArrayList<MedicionStrategos>();
		List<MedicionStrategos> medicionesMeta = new ArrayList<MedicionStrategos>();

		List<Actividad> actividades = actividadService.findAllByProyectoId(actId);

		for (Actividad act : actividades) {

			List<IndicadorTareaStrategos> relaciones = new ArrayList<IndicadorTareaStrategos>();

			relaciones = indicadorTareaService.findByTarea(act.getActividadId());

			for (IndicadorTareaStrategos indi : relaciones) {
				IndicadorStrategos ind = indicadorService.findById(indi.getPk().getIndicadorId());
				indicadores.add(ind);
			}
		}

		// peso se tomara como periodo

		for (IndicadorStrategos ind : indicadores) {

			DatoMedicion dato = new DatoMedicion();
			dato.setCampo("indicador");
			dato.setValor(ind.getNombre());
			dato.setTamanio("400");
			dato.setPeso("0");
			dato.setId(ind.getIndicadorId());
			dato.setIdeaId(ind.getIndicadorId());
			dato.setTipo("indicador");

			datos.add(dato);

			if (serie == 1) {// real

				dato = new DatoMedicion();
				dato.setCampo("series");
				dato.setValor("Real");
				dato.setTamanio("50");
				dato.setPeso("0");
				dato.setId((long) 0);
				dato.setIdeaId(ind.getIndicadorId());
				dato.setTipo("real");

				datos.add(dato);
				
				for (int x = perIni; x <= perFin; x++) {
					
					medicionesReal = medicionService.findByPeriodos(ind.getIndicadorId(), (long) 0, anio, x, x);
					if (medicionesReal.size() > 0) {
						for (MedicionStrategos med : medicionesReal) {
							dato = new DatoMedicion();
							dato.setCampo("medicion");
				
							dato.setValor(String.format("%.0f", med.getValor()));
							dato.setTamanio("50");
							dato.setPeso(anio + "-" + med.getMedicionPk().getPeriodo());
							dato.setId(med.getMedicionPk().getSerieId());
							dato.setIdeaId(ind.getIndicadorId());
							dato.setTipo("real");

							datos.add(dato);
						}
					}else {
						dato = new DatoMedicion();
						dato.setCampo("medicion");
						dato.setValor("");
						dato.setTamanio("50");
						dato.setPeso(anio + "-" + x);
						dato.setId((long) 0);
						dato.setIdeaId(ind.getIndicadorId());
						dato.setTipo("real");

						datos.add(dato);
					}
					
				}
				
				
			} else if (serie == 2) {// meta

				dato = new DatoMedicion();
				dato.setCampo("series");
				dato.setValor("Meta");
				dato.setTamanio("50");
				dato.setPeso("0");
				dato.setId((long) 1);
				dato.setIdeaId(ind.getIndicadorId());
				dato.setTipo("meta");

				datos.add(dato);
				
				
				for (int x = perIni; x <= perFin; x++) {
					
					
					medicionesMeta = medicionService.findByPeriodos(ind.getIndicadorId(), (long) 1, anio, x, x);
					if (medicionesMeta.size() > 0) {
						for (MedicionStrategos med : medicionesMeta) {
							dato = new DatoMedicion();
							dato.setCampo("medicion");
							dato.setValor(String.format("%.0f", med.getValor()));
							dato.setTamanio("50");
							dato.setPeso(anio + "-" + med.getMedicionPk().getPeriodo());
							dato.setId(med.getMedicionPk().getSerieId());
							dato.setIdeaId(ind.getIndicadorId());
							dato.setTipo("meta");

							datos.add(dato);
						}
					}else {
						dato = new DatoMedicion();
						dato.setCampo("medicion");
						dato.setValor("");
						dato.setTamanio("50");
						dato.setPeso(anio + "-" + x);
						dato.setId((long) 1);
						dato.setIdeaId(ind.getIndicadorId());
						dato.setTipo("meta");

						datos.add(dato);
					}
					
					
					
				}
				
			}

		}

		return datos;
	}

	// medicon indicador

	@GetMapping("/medicion/mediciones/{objId}/{anio}/{perIni}/{perFin}/{serie}")
	public List<DatoMedicion> getDatosMediciones(@PathVariable Long objId, @PathVariable Integer anio,
			@PathVariable Integer perIni, @PathVariable Integer perFin, @PathVariable Integer serie) {

		List<DatoMedicion> datos = new ArrayList<DatoMedicion>();

		List<IndicadorStrategos> indicadores = new ArrayList<IndicadorStrategos>();
		List<MedicionStrategos> medicionesReal = new ArrayList<MedicionStrategos>();
		List<MedicionStrategos> medicionesMeta = new ArrayList<MedicionStrategos>();

		List<IndicadorPerspectiva> relaciones = new ArrayList<IndicadorPerspectiva>();

		relaciones = indicadorPerspectivaService.findByPerspectiva(objId);

		for (IndicadorPerspectiva indi : relaciones) {

			IndicadorStrategos ind = indicadorService.findById(indi.getPk().getIndicadorId());
			indicadores.add(ind);
		}

		// peso se tomara como periodo

		for (IndicadorStrategos ind : indicadores) {

			DatoMedicion dato = new DatoMedicion();
			dato.setCampo("indicador");
			dato.setValor(ind.getNombre());
			dato.setTamanio("400");
			dato.setPeso("0");
			dato.setId(ind.getIndicadorId());
			dato.setIdeaId(ind.getIndicadorId());
			dato.setTipo("indicador");

			datos.add(dato);

			if (serie == 1) {// real

				dato = new DatoMedicion();
				dato.setCampo("series");
				dato.setValor("Real");
				dato.setTamanio("50");
				dato.setPeso("0");
				dato.setId((long) 0);
				dato.setIdeaId(ind.getIndicadorId());
				dato.setTipo("real");

				datos.add(dato);

				for (int x = perIni; x <= perFin; x++) {
					
					medicionesReal = medicionService.findByPeriodos(ind.getIndicadorId(), (long) 0, anio, x, x);
					if (medicionesReal.size() > 0) {
						for (MedicionStrategos med : medicionesReal) {
							dato = new DatoMedicion();
							dato.setCampo("medicion");
							dato.setValor(String.format("%.0f", med.getValor()));
							dato.setTamanio("50");
							dato.setPeso(anio + "-" + med.getMedicionPk().getPeriodo());
							dato.setId(med.getMedicionPk().getSerieId());
							dato.setIdeaId(ind.getIndicadorId());
							dato.setTipo("real");

							datos.add(dato);
						}
					}else {
						dato = new DatoMedicion();
						dato.setCampo("medicion");
						dato.setValor("");
						dato.setTamanio("50");
						dato.setPeso(anio + "-" + x);
						dato.setId((long) 0);
						dato.setIdeaId(ind.getIndicadorId());
						dato.setTipo("real");

						datos.add(dato);
					}
					
				}

			} else if (serie == 2) {// meta

				dato = new DatoMedicion();
				dato.setCampo("series");
				dato.setValor("Meta");
				dato.setTamanio("50");
				dato.setPeso("0");
				dato.setId((long) 1);
				dato.setIdeaId(ind.getIndicadorId());
				dato.setTipo("meta");

				datos.add(dato);

				for (int x = perIni; x <= perFin; x++) {
					
					
					medicionesMeta = medicionService.findByPeriodos(ind.getIndicadorId(), (long) 1, anio, x, x);
					if (medicionesMeta.size() > 0) {
						for (MedicionStrategos med : medicionesMeta) {
							dato = new DatoMedicion();
							dato.setCampo("medicion");
							dato.setValor(String.format("%.0f", med.getValor()));
							dato.setTamanio("50");
							dato.setPeso(anio + "-" + med.getMedicionPk().getPeriodo());
							dato.setId(med.getMedicionPk().getSerieId());
							dato.setIdeaId(ind.getIndicadorId());
							dato.setTipo("meta");

							datos.add(dato);
						}
					}else {
						dato = new DatoMedicion();
						dato.setCampo("medicion");
						dato.setValor("");
						dato.setTamanio("50");
						dato.setPeso(anio + "-" + x);
						dato.setId((long) 1);
						dato.setIdeaId(ind.getIndicadorId());
						dato.setTipo("meta");

						datos.add(dato);
					}
					
					
					
				}
				
				
			}

		}

		return datos;
	}
	
	
	@GetMapping("/medicion/mediciones/presupuesto/{tarId}/{anio}/{perIni}/{perFin}/{serie}")
	public List<DatoMedicion> getMedicionesPresupuestos(@PathVariable Long tarId, @PathVariable Integer anio,
			@PathVariable Integer perIni, @PathVariable Integer perFin, @PathVariable Integer serie) {

		List<DatoMedicion> datos = new ArrayList<DatoMedicion>();

		List<Presupuesto> presupuestos = presupuestoService.findAll();
	
		List<PresupuestoDatos> medicionesReal = new ArrayList<PresupuestoDatos>();
		List<PresupuestoDatos> medicionesMeta = new ArrayList<PresupuestoDatos>();

			
		
		//buscar periodos
		
		for(int x=perIni; x<=perFin; x++) {
			
			DatoMedicion dato = new DatoMedicion();
			dato.setCampo("periodo");
			dato.setValor(""+anio+"-"+x);
			dato.setTamanio("50");
			dato.setPeso(""+x);
			dato.setId(tarId);
			dato.setIdeaId(tarId);
			dato.setTipo("periodo");

			datos.add(dato);
			
			// datos mediciones
			
			if (serie == 1) {//real
			
				dato = new DatoMedicion();
				dato.setCampo("series");
				dato.setValor("Real");
				dato.setTamanio("50");
				dato.setPeso(""+x);
				dato.setId((long) 0);
				dato.setIdeaId(tarId);
				dato.setTipo("real");

				datos.add(dato);
				
				medicionesReal = presupuestoDatosService.findByPeriodos(tarId, (long)0, anio, x, x);	
				
				if (medicionesReal.size() > 0) {
					
					for (PresupuestoDatos pre : medicionesReal) {
						dato = new DatoMedicion();
						dato.setCampo("medicion");
						dato.setValor(String.format("%.0f", pre.getValor()));
						dato.setTamanio("50");
						dato.setPeso(""+x);
						dato.setId(pre.getSerieId());
						dato.setIdeaId(tarId);
						dato.setTipo(""+pre.getPresupuestoDatoId());

						datos.add(dato);
					}
					
				}else {
					for (Presupuesto pre : presupuestos) {
						dato = new DatoMedicion();
						dato.setCampo("medicion");
						dato.setValor("");
						dato.setTamanio("50");
						dato.setPeso(""+x);
						dato.setId((long) 0);
						dato.setIdeaId(tarId);
						dato.setTipo(""+pre.getPresupuestoId());

						datos.add(dato);
					}
				}
				
			}else if (serie == 2) {//meta
				
				dato = new DatoMedicion();
				dato.setCampo("series");
				dato.setValor("Meta");
				dato.setTamanio("50");
				dato.setPeso(""+x);
				dato.setId((long) 1);
				dato.setIdeaId(tarId);
				dato.setTipo("meta");

				datos.add(dato);
				
				medicionesMeta = presupuestoDatosService.findByPeriodos(tarId, (long)1, anio, x, x);
				
				if (medicionesMeta.size() > 0) {
					
					for (PresupuestoDatos pre : medicionesMeta) {
						dato = new DatoMedicion();
						dato.setCampo("medicion");
						dato.setValor(String.format("%.0f", pre.getValor()));
						dato.setTamanio("50");
						dato.setPeso(""+x);
						dato.setId(pre.getSerieId());
						dato.setIdeaId(tarId);
						dato.setTipo(""+pre.getPresupuestoDatoId());

						datos.add(dato);
					}
					
				}else {
					for (Presupuesto pre : presupuestos) {
						dato = new DatoMedicion();
						dato.setCampo("medicion");
						dato.setValor("");
						dato.setTamanio("50");
						dato.setPeso(""+x);
						dato.setId((long) 1);
						dato.setIdeaId(tarId);
						dato.setTipo(""+pre.getPresupuestoId());

						datos.add(dato);
					}
				}
			}
			
		}
		
	

		return datos;
	}
	
	

	@GetMapping("/medicion/indicadores/{objId}/{serie}")
	public List<DatoIdea> getIndicadores(@PathVariable Long objId, @PathVariable Integer serie) {
		List<DatoIdea> datos = new ArrayList<DatoIdea>();

		return datos;
	}

	// servicio que muestra un medicion
	@GetMapping("/medicion/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		MedicionStrategos medicionId = null;
		Map<String, Object> response = new HashMap<>();

		try {
			medicionId = medicionService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (medicionId == null) {
			response.put("mensaje", "La medicion Id: ".concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MedicionStrategos>(medicionId, HttpStatus.OK);
	}

	// servicio que crea un medicion
	@PostMapping("/medicion/{objId}")
	public ResponseEntity<?> create(@Valid @RequestBody List<DatoMedicion> mediciones, @PathVariable Long objId,
			BindingResult result) {

		List<IndicadorStrategos> indicadores = new ArrayList<IndicadorStrategos>();
		List<IndicadorPerspectiva> relaciones = new ArrayList<IndicadorPerspectiva>();
		MedicionStrategos medicion = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "Campo: " + err.getField() + " " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {

			if (mediciones.size() > 0) {
				for (DatoMedicion dat : mediciones) {

					MedicionStrategos medicionNew = new MedicionStrategos();
					MedicionPkStrategos medicionPk = new MedicionPkStrategos();

					if (dat.getCampo().equals("medicion")) {

						int index = 0;
						index = dat.getPeso().indexOf("-");
						String anio = dat.getPeso().substring(0, index);
						String periodo = dat.getPeso().substring(index + 1, dat.getPeso().length());

						medicionPk.setAno(Integer.parseInt(anio));
						medicionPk.setIndicadorId(dat.getIdeaId());
						medicionPk.setPeriodo(Integer.parseInt(periodo));
						medicionPk.setSerieId(dat.getId());

						medicionNew.setMedicionPk(medicionPk);
						medicionNew.setValor(Double.parseDouble(dat.getValor()));
						medicionNew.setProtegido(0);
						medicionService.save(medicionNew);

						IndicadorStrategos ind = indicadorService.findById(dat.getIdeaId());
						ind.setFechaUltimaMedicion(dat.getPeso());

						if (dat.getId() == 0) {// real
							ind.setUltimaMedicion(Double.parseDouble(dat.getValor()));
						} else if (dat.getId() == 1) {// meta
							ind.setUltimoProgramado(Double.parseDouble(dat.getValor()));
						}

						indicadorService.save(ind);

					}

				}

				relaciones = indicadorPerspectivaService.findByPerspectiva(objId);

				for (IndicadorPerspectiva indi : relaciones) {

					IndicadorStrategos ind = indicadorService.findById(indi.getPk().getIndicadorId());
					indicadores.add(ind);
				}

				Double promedio = 0.0;

				for (IndicadorStrategos ind : indicadores) {
					ind.setAlerta(CalcularAlerta(ind));

					ind.setPorcentajeCumplimiento(CalcularLogro(ind));
					indicadorService.save(ind);
					promedio = promedio + ind.getPorcentajeCumplimiento();
				}

				PerspectivaStrategos per = perspectivaService.findById(objId);

				per.setUltimaMedicionParcial(promedio / indicadores.size());

				if (per.getUltimaMedicionParcial() >= 100) {
					per.setAlertaParcial((byte) 1);
				} else {
					per.setAlertaParcial((byte) 3);
				}

				perspectivaService.save(per);

			}

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La medicion ha sido creado con Exito!");
		response.put("medicion", medicion);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	
	@PostMapping("/medicion/tarea/{actId}")
	public ResponseEntity<?> createTarea(@Valid @RequestBody List<DatoMedicion> mediciones, @PathVariable Long actId,
			BindingResult result) {

		List<IndicadorStrategos> indicadores = new ArrayList<IndicadorStrategos>();
		List<IndicadorTareaStrategos> relaciones = new ArrayList<IndicadorTareaStrategos>();
		MedicionStrategos medicion = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "Campo: " + err.getField() + " " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {

			if (mediciones.size() > 0) {
				for (DatoMedicion dat : mediciones) {

					MedicionStrategos medicionNew = new MedicionStrategos();
					MedicionPkStrategos medicionPk = new MedicionPkStrategos();

					if (dat.getCampo().equals("medicion")) {

						int index = 0;
						index = dat.getPeso().indexOf("-");
						String anio = dat.getPeso().substring(0, index);
						String periodo = dat.getPeso().substring(index + 1, dat.getPeso().length());

						medicionPk.setAno(Integer.parseInt(anio));
						medicionPk.setIndicadorId(dat.getIdeaId());
						medicionPk.setPeriodo(Integer.parseInt(periodo));
						medicionPk.setSerieId(dat.getId());

						medicionNew.setMedicionPk(medicionPk);
						medicionNew.setValor(Double.parseDouble(dat.getValor()));
						medicionNew.setProtegido(0);
						medicionService.save(medicionNew);

						IndicadorStrategos ind = indicadorService.findById(dat.getIdeaId());
						ind.setFechaUltimaMedicion(dat.getPeso());

						if (dat.getId() == 0) {// real
							ind.setUltimaMedicion(Double.parseDouble(dat.getValor()));
						} else if (dat.getId() == 1) {// meta
							ind.setUltimoProgramado(Double.parseDouble(dat.getValor()));
						}

						indicadorService.save(ind);

					}

				}
				
				
				//consultar tarea
				
				
				List<Actividad> actividades = actividadService.findAllByProyectoId(actId);

				Double promedio = 0.0;
				Double promedioMeta = 0.0;
				Double promedioReal = 0.0;
				String fecha ="";
				Boolean noPeso =false;
				
				for (Actividad act : actividades) {					
					

					relaciones = new ArrayList<IndicadorTareaStrategos>();

					relaciones = indicadorTareaService.findByTarea(act.getActividadId());

					for (IndicadorTareaStrategos indi : relaciones) {
						IndicadorStrategos ind = indicadorService.findById(indi.getPk().getIndicadorId());
						ind.setAlerta(CalcularAlerta(ind));

						ind.setPorcentajeCumplimiento(CalcularLogro(ind));
						fecha = ind.getFechaUltimaMedicion();
						indicadorService.save(ind);
						promedio = promedio + ind.getPorcentajeCumplimiento();
						
						act.setUltimaMedicion(ind.getUltimaMedicion());
						act.setCompletado(ind.getUltimaMedicion());
						act.setProgramado(ind.getUltimoProgramado());
						act.setAlerta(ind.getAlerta());
						
						actividadService.save(act);
						
						if(act.getPeso() == null || act.getPeso() == 0) {
							
							Double calculo = ((ind.getUltimaMedicion())/actividades.size());
							Double calculoMeta = ((ind.getUltimoProgramado())/actividades.size());
							
							
							
							
							promedioReal = (promedioReal + calculo);
							promedioMeta = (promedioMeta + calculoMeta);
							
							
							
						}else {
							
							Double calculo = ((ind.getUltimaMedicion() * act.getPeso())/100);
							Double calculoMeta = ((ind.getUltimoProgramado() * act.getPeso())/100);
							
							promedioReal = promedioReal + calculo;
							promedioMeta = promedioMeta + calculoMeta;
							
						}
						
						
				
						
					}
												
					
					
				}			
				
				
				Iniciativa iniciativa =  iniciativaService.findById(actId);	
				
				
				
				iniciativa.setUltimaMedicion(promedioReal);
				iniciativa.setUltimoProgramado(promedioMeta);
				iniciativa.setFechaUltimaMedicion(fecha);
				
				Byte alerta = 0;// 1-verde, 2-amarillo, 3-rojo
				Double alertaVerde = 0.0;
				Double alertaAmarilla = 0.0;

				if (iniciativa.getUltimaMedicion() != null && iniciativa.getUltimoProgramado() != null) {

					if (iniciativa.getZonaVerde() != null && iniciativa.getZonaAmarilla() != null) {

						if (iniciativa.getUltimaMedicion() > iniciativa.getUltimoProgramado()) {
							alerta = 1;
						} else {
							alerta = 3;
						}

					} else {
						if (iniciativa.getZonaVerde() != null) {

							Double verde = (iniciativa.getZonaVerde() / 100);
							Double valorResta = iniciativa.getUltimoProgramado() * verde;

							alertaVerde = iniciativa.getUltimoProgramado() - valorResta;
						}
						if (iniciativa.getZonaAmarilla() != null) {

							Double amarillo = (iniciativa.getZonaAmarilla() / 100);
							Double valorResta = iniciativa.getUltimoProgramado() * amarillo;

							alertaAmarilla = alertaVerde - valorResta;
						}

						if (iniciativa.getUltimaMedicion() >= alertaVerde) {
							alerta = 1;
						} else if (iniciativa.getUltimaMedicion() >= alertaAmarilla && iniciativa.getUltimaMedicion() < alertaVerde) {
							alerta = 2;
						} else if (iniciativa.getUltimaMedicion() < alertaAmarilla) {
							alerta = 3;
						}

					}

				}
				
				iniciativa.setAlerta(alerta);
				
				iniciativaService.save(iniciativa);
				
				

			}

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La medicion ha sido creado con Exito!");
		response.put("medicion", medicion);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/medicion/presupuesto/{anio}")
	public ResponseEntity<?> createPresupuesto(@Valid @RequestBody List<DatoMedicion> mediciones, @PathVariable Integer anio,
			BindingResult result) {

		List<IndicadorStrategos> indicadores = new ArrayList<IndicadorStrategos>();
		List<IndicadorTareaStrategos> relaciones = new ArrayList<IndicadorTareaStrategos>();
		MedicionStrategos medicion = null;

		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "Campo: " + err.getField() + " " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {

			if (mediciones.size() > 0) {
				for (DatoMedicion dat : mediciones) {
					
					PresupuestoDatos presupuestoDato = new PresupuestoDatos();
					
					if (dat.getCampo().equals("medicion")) {
						presupuestoDato.setAno(anio);
						presupuestoDato.setPeriodo(Integer.parseInt(dat.getPeso()));
						presupuestoDato.setSerieId(dat.getId());
						presupuestoDato.setTareaId(dat.getIdeaId());
						presupuestoDato.setValor(Double.parseDouble(dat.getValor()));
						
						String presupuesto = "";
						Presupuesto pre= presupuestoService.findById(Long.parseLong(dat.getTipo()));
						if(pre != null) {
							presupuesto = pre.getPresupuesto();
						}
						
						presupuestoDato.setPresupuesto(presupuesto);
						
						presupuestoDatosService.save(presupuestoDato);
						
					}
					
					
				}
				
		
			}

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El presupuesto ha sido creado con Exito!");
		response.put("medicion", medicion);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	// servicio que actualiza un medicion
	@PutMapping("/medicion/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody MedicionStrategos medicion, BindingResult result,
			@PathVariable Long id) {
		MedicionStrategos medicionActual = medicionService.findById(id);
		MedicionStrategos medicionUpdated = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "Campo: " + err.getField() + " " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		if (medicionActual == null) {
			response.put("mensaje", "Error, no se pudo editar, la medicion ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			medicionActual.setValor(medicion.getValor());

			medicionUpdated = medicionService.save(medicionActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la medicion en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La medicion ha sido actualizado con Exito!");
		response.put("medicion", medicionUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// servicio que elimina las medicion
	@DeleteMapping("/medicion/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {

			medicionService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la medicion en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La medicion ha sido eliminado con Exito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	public String obtenerMes(Integer periodo) {
		String mes = "";

		switch (periodo) {
		case 1: {
			mes = "Enero";
			break;
		}
		case 2: {
			mes = "Febrero";
			break;
		}
		case 3: {
			mes = "Marzo";
			break;
		}
		case 4: {
			mes = "Abril";
			break;
		}
		case 5: {
			mes = "Mayo";
			break;
		}
		case 6: {
			mes = "Junio";
			break;
		}
		case 7: {
			mes = "Julio";
			break;
		}
		case 8: {
			mes = "Agosto";
			break;
		}
		case 9: {
			mes = "Septiembre";
			break;
		}
		case 10: {
			mes = "Octubre";
			break;
		}
		case 11: {
			mes = "Noviembre";
			break;
		}
		case 12: {
			mes = "Diciembre";
			break;
		}

		default: {
			System.out.println("Opcion incorrecta");
		}
		}

		return mes;
	}

	public String obtenerPeriodo(Integer periodo) {
		String mes = "";

		switch (periodo) {
		case 1: {
			mes = "Trimestre 1";
			break;
		}
		case 2: {
			mes = "Trimestre 2";
			break;
		}
		case 3: {
			mes = "Trimestre 3";
			break;
		}
		case 4: {
			mes = "Trimestre 4";
			break;
		}

		default: {
			System.out.println("Opcion incorrecta");
		}
		}

		return mes;
	}

	public Byte CalcularAlerta(IndicadorStrategos ind) {

		Byte alerta = 0;// 1-verde, 2-amarillo, 3-rojo
		Double alertaVerde = 0.0;
		Double alertaAmarilla = 0.0;

		if (ind.getUltimaMedicion() != null && ind.getUltimoProgramado() != null) {

			if (ind.getAlertaMetaZonaVerde() != null && ind.getAlertaIndicadorIdZonaAmarilla() != null) {

				if (ind.getUltimaMedicion() > ind.getUltimoProgramado()) {
					alerta = 1;
				} else {
					alerta = 3;
				}

			} else {
				if (ind.getAlertaMetaZonaVerde() != null) {

					Double verde = (ind.getAlertaMetaZonaVerde() / 100);
					Double valorResta = ind.getUltimoProgramado() * verde;

					alertaVerde = ind.getUltimoProgramado() - valorResta;
				}
				if (ind.getAlertaMetaZonaAmarilla() != null) {

					Double amarillo = (ind.getAlertaMetaZonaAmarilla() / 100);
					Double valorResta = ind.getUltimoProgramado() * amarillo;

					alertaAmarilla = alertaVerde - valorResta;
				}

				if (ind.getUltimaMedicion() >= alertaVerde) {
					alerta = 1;
				} else if (ind.getUltimaMedicion() >= alertaAmarilla && ind.getUltimaMedicion() < alertaVerde) {
					alerta = 2;
				} else if (ind.getUltimaMedicion() < alertaAmarilla) {
					alerta = 3;
				}

			}

		}

		return alerta;
	}

	public Double CalcularLogro(IndicadorStrategos ind) {

		Double logro = 0.0;// 1-verde, 2-amarillo, 3-rojo
		Double alertaVerde = 0.0;
		Double alertaAmarilla = 0.0;

		if (ind.getUltimaMedicion() != null && ind.getUltimoProgramado() != null) {

			if (ind.getUltimaMedicion() > ind.getUltimoProgramado()) {
				logro = 100.0;
			} else if (ind.getUltimoProgramado() < 0) {
				logro = 0.0;
			} else {
				logro = (ind.getUltimaMedicion() / ind.getUltimoProgramado()) * 100;
			}

		}

		return logro;
	}

}
