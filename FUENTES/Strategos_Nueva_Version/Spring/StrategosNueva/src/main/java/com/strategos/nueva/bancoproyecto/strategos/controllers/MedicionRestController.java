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

import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasEvaluadas;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyecto.ideas.service.TiposPropuestasService;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorPerspectiva;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.MedicionStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorPerspectivaService;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorService;
import com.strategos.nueva.bancoproyecto.strategos.service.MedicionService;
import com.strategos.nueva.bancoproyectos.model.util.DatoIdea;
import com.strategos.nueva.bancoproyectos.model.util.DatoMedicion;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class MedicionRestController {
	
	
	@Autowired
	private MedicionService medicionService;

	@Autowired
	private IndicadorPerspectivaService indicadorPerspectivaService;
	
	@Autowired
	private IndicadorService indicadorService;
	
	//Servicios Rest tabla - medicion
	
		private final Logger log = LoggerFactory.getLogger(MedicionRestController.class);
		
		//servicio que trae la lista de medicion
		@GetMapping("/medicion")
		public List<MedicionStrategos> index (){
			return medicionService.findAll();
		}
		
		@GetMapping("/medicion/encabezados/{objId}/{frecuencia}/{anio}/{perIni}/{perFin}")
		public List<DatoIdea> getEncabezados(@PathVariable Long objId, @PathVariable Integer frecuencia, @PathVariable Integer anio,
				@PathVariable Integer perIni, @PathVariable Integer perFin){
			
			List<DatoIdea> datos = new ArrayList<DatoIdea>();
			
			List<IndicadorStrategos> indicadores = new ArrayList<IndicadorStrategos>();
			List<MedicionStrategos> mediciones = new ArrayList<MedicionStrategos>();	
			
			
			List<IndicadorPerspectiva> relaciones = new ArrayList<IndicadorPerspectiva>();
			
			relaciones = indicadorPerspectivaService.findByPerspectiva(objId);
			
			for(IndicadorPerspectiva indi: relaciones) {
				
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
			
			//indicadores
			
			
			String cadenaTitulo ="";
			
			switch(frecuencia){
			
				case 1:{//Mensual
					
					for(int x=perIni; x<=perFin; x++) {
						
						dato = new DatoIdea();
						dato.setCampo("periodo");
						dato.setValor(obtenerMes(x));
						dato.setTamanio("50");
						
						datos.add(dato);
						
					}
					
					
					break;
				}
					
				case 2:{//Trimestral
					
					for(int x=perIni; x<=perFin; x++) {
						
						dato = new DatoIdea();
						dato.setCampo("periodo");
						dato.setValor(obtenerPeriodo(x));
						dato.setTamanio("50");
						
						datos.add(dato);
						
					}
					
					
					
					break;
				}
					
				default:{
					System.out.println("Opcion incorrecta");
				}
			}
			
						
			return datos;
		}
		
		@GetMapping("/medicion/mediciones/{objId}/{anio}/{perIni}/{perFin}/{serie}")
		public List<DatoMedicion> getDatosMediciones(@PathVariable Long objId, @PathVariable Integer anio,
				@PathVariable Integer perIni, @PathVariable Integer perFin, @PathVariable Integer serie){
			
			List<DatoMedicion> datos = new ArrayList<DatoMedicion>();
			
			List<IndicadorStrategos> indicadores = new ArrayList<IndicadorStrategos>();
			List<MedicionStrategos> medicionesReal = new ArrayList<MedicionStrategos>();	
			List<MedicionStrategos> medicionesMeta = new ArrayList<MedicionStrategos>();
			
			List<IndicadorPerspectiva> relaciones = new ArrayList<IndicadorPerspectiva>();
			
			relaciones = indicadorPerspectivaService.findByPerspectiva(objId);
			
			for(IndicadorPerspectiva indi: relaciones) {
				
				IndicadorStrategos ind = indicadorService.findById(indi.getPk().getIndicadorId());
				indicadores.add(ind);
			}
			
			//peso se tomara como periodo
			
			for (IndicadorStrategos ind: indicadores) {
				
				
				DatoMedicion dato = new DatoMedicion();
				dato.setCampo("indicador");
				dato.setValor(ind.getNombre());
				dato.setTamanio("400");
				dato.setPeso("0");
				dato.setId(ind.getIndicadorId());
				dato.setIdeaId(ind.getIndicadorId());
				dato.setTipo("tr");
				
				datos.add(dato);
								
				if(serie == 1) {//real
					
					dato = new DatoMedicion();
					dato.setCampo("series");
					dato.setValor("Real");
					dato.setTamanio("50");
					dato.setPeso("0");
					dato.setId((long) 0);
					dato.setIdeaId(ind.getIndicadorId());
					dato.setTipo("td");
					
					datos.add(dato);					
				
					//buscar mediciones
					medicionesReal = medicionService.findByPeriodos(ind.getIndicadorId(), (long) 0, anio, perIni, perFin);
					if(medicionesReal.size() >0) {
						for(MedicionStrategos med: medicionesReal) {
							dato = new DatoMedicion();
							dato.setCampo("medicion");
							dato.setValor(""+med.getValor());
							dato.setTamanio("50");
							dato.setPeso(anio+"-"+med.getMedicionPk().getPeriodo());
							dato.setId(med.getMedicionPk().getSerieId());
							dato.setIdeaId(ind.getIndicadorId());
							dato.setTipo("td");
							
							datos.add(dato);
						}
					}else {
						for(int x=perIni; x<=perFin; x++) {
							dato = new DatoMedicion();
							dato.setCampo("medicion");
							dato.setValor("");
							dato.setTamanio("50");
							dato.setPeso(anio+"-"+x);
							dato.setId((long) 0);
							dato.setIdeaId(ind.getIndicadorId());
							dato.setTipo("td");
							
							datos.add(dato);
						}
					}
				}else if(serie == 2) {//meta
					
					dato = new DatoMedicion();
					dato.setCampo("series");
					dato.setValor("Meta");
					dato.setTamanio("50");
					dato.setPeso("0");
					dato.setId((long) 1);
					dato.setIdeaId(ind.getIndicadorId());
					dato.setTipo("td");
					
					datos.add(dato);
					
					medicionesMeta = medicionService.findByPeriodos(ind.getIndicadorId(), (long) 1, anio, perIni, perFin);
					if(medicionesMeta.size() >0) {
						for(MedicionStrategos med: medicionesMeta) {
							dato = new DatoMedicion();
							dato.setCampo("medicion");
							dato.setValor(""+med.getValor());
							dato.setTamanio("50");
							dato.setPeso(anio+"-"+med.getMedicionPk().getPeriodo());
							dato.setId(med.getMedicionPk().getSerieId());
							dato.setIdeaId(ind.getIndicadorId());
							dato.setTipo("td");
							
							datos.add(dato);
						}
					}else {
						for(int x=perIni; x<=perFin; x++) {
							dato = new DatoMedicion();
							dato.setCampo("medicion");
							dato.setValor("");
							dato.setTamanio("50");
							dato.setPeso(anio+"-"+x);
							dato.setId((long) 1);
							dato.setIdeaId(ind.getIndicadorId());
							dato.setTipo("td");
							
							datos.add(dato);
						}
					}
				}else if(serie == 3) {//los 2
					
					dato = new DatoMedicion();
					dato.setCampo("series");
					dato.setValor("Real");
					dato.setTamanio("50");
					dato.setPeso("0");
					dato.setId((long) 0);
					dato.setIdeaId(ind.getIndicadorId());
					dato.setTipo("td");
					
					datos.add(dato);
					
					medicionesReal = medicionService.findByPeriodos(ind.getIndicadorId(), (long) 0, anio, perIni, perFin);
					if(medicionesReal.size() >0) {
						for(MedicionStrategos med: medicionesReal) {
							dato = new DatoMedicion();
							dato.setCampo("medicion");
							dato.setValor(""+med.getValor());
							dato.setTamanio("50");
							dato.setPeso(anio+"-"+med.getMedicionPk().getPeriodo());
							dato.setId(med.getMedicionPk().getSerieId());
							dato.setIdeaId(ind.getIndicadorId());
							dato.setTipo("td");
							
							datos.add(dato);
						}
					}else {
						for(int x=perIni; x<=perFin; x++) {
							dato = new DatoMedicion();
							dato.setCampo("medicion");
							dato.setValor("");
							dato.setTamanio("50");
							dato.setPeso(anio+"-"+x);
							dato.setId((long) 0);
							dato.setIdeaId(ind.getIndicadorId());
							dato.setTipo("td");
							
							datos.add(dato);
						}
					}
			
					//segundo registro
					
					dato = new DatoMedicion();
					dato.setCampo("indicador");
					dato.setValor(ind.getNombre());
					dato.setTamanio("400");
					dato.setPeso("0");
					dato.setId(ind.getIndicadorId());
					dato.setIdeaId(ind.getIndicadorId());
					dato.setTipo("tr");
					
					datos.add(dato);
					
					
					dato = new DatoMedicion();
					dato.setCampo("series");
					dato.setValor("Meta");
					dato.setTamanio("50");
					dato.setPeso("0");
					dato.setId((long) 1);
					dato.setIdeaId(ind.getIndicadorId());
					dato.setTipo("td");
					
					datos.add(dato);
					
					medicionesMeta = medicionService.findByPeriodos(ind.getIndicadorId(), (long) 1, anio, perIni, perFin);
					if(medicionesMeta.size() >0) {
						for(MedicionStrategos med: medicionesMeta) {
							dato = new DatoMedicion();
							dato.setCampo("medicion");
							dato.setValor(""+med.getValor());
							dato.setTamanio("50");
							dato.setPeso(anio+"-"+med.getMedicionPk().getPeriodo());
							dato.setId(med.getMedicionPk().getSerieId());
							dato.setIdeaId(ind.getIndicadorId());
							dato.setTipo("td");
							
							datos.add(dato);
						}
					}else {
						for(int x=perIni; x<=perFin; x++) {
							dato = new DatoMedicion();
							dato.setCampo("medicion");
							dato.setValor("");
							dato.setTamanio("50");
							dato.setPeso(anio+"-"+x);
							dato.setId((long) 1);
							dato.setIdeaId(ind.getIndicadorId());
							dato.setTipo("td");
							
							datos.add(dato);
						}
					}
				}
				
			}
				
					
						
			return datos;
		}
			
		

		@GetMapping("/medicion/indicadores/{objId}/{serie}")
		public List<DatoIdea> getIndicadores(@PathVariable Long objId, @PathVariable Integer serie){
			List<DatoIdea> datos = new ArrayList<DatoIdea>();
			
			return datos;
		}
		
		//servicio que muestra un medicion
		@GetMapping("/medicion/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			MedicionStrategos medicionId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				medicionId= medicionService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(medicionId == null) {
			  response.put("mensaje", "La medicion Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<MedicionStrategos>(medicionId, HttpStatus.OK); 		
		}
		
		//servicio que crea un medicion
		@PostMapping("/medicion")
		public ResponseEntity<?> create(@Valid @RequestBody MedicionStrategos medicionN, BindingResult result) {
			
			MedicionStrategos medicionNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				medicionNew= medicionService.save(medicionN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La medicion ha sido creado con Exito!");
			response.put("medicion", medicionNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un medicion
		@PutMapping("/medicion/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody MedicionStrategos medicion, BindingResult result, @PathVariable Long id) {
			MedicionStrategos medicionActual= medicionService.findById(id);
			MedicionStrategos medicionUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(medicionActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, la medicion ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
								
				medicionActual.setValor(medicion.getValor());
				
				
																			
				medicionUpdated=medicionService.save(medicionActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar la medicion en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La medicion ha sido actualizado con Exito!");
			response.put("medicion", medicionUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina las medicion
		@DeleteMapping("/medicion/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				medicionService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar la medicion en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "La medicion ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}
		
		public String obtenerMes(Integer periodo) {
			String mes="";
			
			switch(periodo) {
				case 1:{
					mes="Enero";
					break;
				}
				case 2:{
					mes="Febrero";
					break;
				}
				case 3:{
					mes="Marzo";
					break;
				}
				case 4:{
					mes="Abril";
					break;
				}
				case 5:{
					mes="Mayo";
					break;
				}
				case 6:{
					mes="Junio";
					break;
				}
				case 7:{
					mes="Julio";
					break;
				}
				case 8:{
					mes="Agosto";
					break;
				}
				case 9:{
					mes="Septiembre";
					break;
				}
				case 10:{
					mes="Octubre";
					break;
				}
				case 11:{
					mes="Noviembre";
					break;
				}
				case 12:{
					mes="Diciembre";
					break;
				}
				
				default:{
					System.out.println("Opcion incorrecta");
				}
			}
			
			return mes;
		}
		
		public String obtenerPeriodo(Integer periodo) {
			String mes="";
			
			switch(periodo) {
				case 1:{
					mes="Trimestre 1";
					break;
				}
				case 2:{
					mes="Trimestre 2";
					break;
				}
				case 3:{
					mes="Trimestre 3";
					break;
				}
				case 4:{
					mes="Trimestre 4";
					break;
				}
								
				default:{
					System.out.println("Opcion incorrecta");
				}
			}
			
			return mes;
		}
		
		
		
		
		

}
