package com.strategos.nueva.riesgos.ejercicios.controllers;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.strategos.nueva.riesgos.ejercicios.model.ControlesRiesgo;
import com.strategos.nueva.riesgos.ejercicios.model.EfectoRiesgo;
import com.strategos.nueva.riesgos.ejercicios.model.EjercicioRiesgo;
import com.strategos.nueva.riesgos.ejercicios.model.FactoresRiesgo;
import com.strategos.nueva.riesgos.ejercicios.model.RiCausaRiesgo;
import com.strategos.nueva.riesgos.ejercicios.service.CausaService;
import com.strategos.nueva.riesgos.ejercicios.service.EjercicioService;
import com.strategos.nueva.riesgos.ejercicios.service.FactoresService;
import com.strategos.nueva.riesgos.model.CalificacionesRiesgo;
import com.strategos.nueva.riesgos.model.Probabilidad;
import com.strategos.nueva.riesgos.model.TipoImpacto;
import com.strategos.nueva.riesgos.procesos.util.MapaCalor;
import com.strategos.nueva.riesgos.service.CalificacionesRiesgoService;
import com.strategos.nueva.riesgos.service.CausaRiesgoService;
import com.strategos.nueva.riesgos.service.ProbabilidadService;
import com.strategos.nueva.riesgos.service.TipoImpactoService;
import com.strategos.nueva.riesgos.service.TipoRiesgoService;


@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/ejercicios") 
public class FactoresRestController {
	@Autowired
	private FactoresService factoresService;
	
	@Autowired
	private EjercicioService ejercicioService;
	
	@Autowired
	private CausaRiesgoService causaRiesgoService;
	
	@Autowired
	private CausaService causaService;
	
	@Autowired
	private TipoRiesgoService tipoRiesgoService;
	
	@Autowired
	private TipoImpactoService tipoImpactoService;
	
	@Autowired
	private ProbabilidadService probabilidadService;
	
	@Autowired
	private CalificacionesRiesgoService calificacionService;
	
	
	
	private final Logger log = LoggerFactory.getLogger(FactoresRestController.class);
	
	@GetMapping("/factor")
	public List<FactoresRiesgo> index (){
		return factoresService.findAll ();
	}
		
	
	@GetMapping("/factor/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		FactoresRiesgo factorRiesgo=null;
		Map<String, Object> response = new HashMap<>();
		
		try { 
			factorRiesgo= factoresService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(factorRiesgo == null) {
		  response.put("mensaje", "EL factor ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
		  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<FactoresRiesgo>(factorRiesgo, HttpStatus.OK); 		
	}
	
	@PostMapping("/factor/{ejercicioId}/{procesoId}")
	public ResponseEntity<?> create(@Valid @RequestBody FactoresRiesgo factorRiesgo, BindingResult result, @PathVariable Long ejercicioId,  @PathVariable Long procesoId) {
		
		FactoresRiesgo factorNew= null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try { 
			
			EjercicioRiesgo eje=ejercicioService.findById(ejercicioId); 
			factorRiesgo.setEjercicio(eje);
			factorRiesgo.setProcesoId(procesoId);
			factorNew= factoresService.save(factorRiesgo);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El factor ha sido creado con éxito!");
		response.put("factor", factorNew);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@PutMapping("/factor/{id}")
	public ResponseEntity<?>  update(@Valid @RequestBody FactoresRiesgo factorRiesgo, BindingResult result, @PathVariable Long id) {
		FactoresRiesgo factorActual= factoresService.findById(id);
		FactoresRiesgo factorUpdated = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(factorActual == null) {
			  response.put("mensaje", "Error, no se pudo editar, el factor ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try{
			factorActual.setControlId(factorRiesgo.getControlId());
			factorActual.setDescripcionFactor(factorRiesgo.getDescripcionFactor());
			factorActual.setEjercicio(factorRiesgo.getEjercicio());
			factorActual.setHistorico(factorRiesgo.getHistorico());
			factorActual.setImpacto(factorRiesgo.getImpacto());
			factorActual.setProbabilidad(factorRiesgo.getProbabilidad());
			factorActual.setRespuestaId(factorRiesgo.getRespuestaId());
			factorActual.setRiesgoResidual(factorRiesgo.getRiesgoResidual());
			factorActual.setSeveridad(factorRiesgo.getSeveridad());
			factorActual.setTipoRiesgoId(factorRiesgo.getTipoRiesgoId());		
			factorActual.setEstatus(factorRiesgo.getEstatus());	
			factorActual.setFactorRiesgo(factorRiesgo.getFactorRiesgo());
			factorActual.setResponsable(factorRiesgo.getResponsable());
			factorActual.setAlerta(factorRiesgo.getAlerta());
			factorActual.setProcesoId(factorRiesgo.getProcesoId());
			factorUpdated=factoresService.save(factorActual);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el factor en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El factor ha sido actualizado con éxito!");
		response.put("factor", factorUpdated);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/factor/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try{
			
			factoresService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el factor en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "EL factor ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
	}

	@GetMapping("/factor/causas/{id}")
	public List<RiCausaRiesgo> obtenerCausas( @PathVariable Long id){
		List<RiCausaRiesgo> causas= factoresService.findById(id).getCausaRiesgo();
		return causas;
	}
	
	@GetMapping("/factor/efectos/{id}")
	public List<EfectoRiesgo> obtenerEfectos( @PathVariable Long id){
		List<EfectoRiesgo> efectos= factoresService.findById(id).getEfectoRiesgo();
		return efectos;
	}
	
	@GetMapping("/factor/controles/{id}")
	public List<ControlesRiesgo> obtenerControles( @PathVariable Long id){
		List<ControlesRiesgo> controles= factoresService.findById(id).getControlesRiesgo();
		return controles;
	}
	
	@GetMapping("/factor/proceso/{procesoId}")
	public List<FactoresRiesgo> obtenerFactoresProceso( @PathVariable Long procesoId){
		List<FactoresRiesgo> factores= factoresService.findAllByProcesoId(procesoId);
		return factores;
	}
	
	@GetMapping("/factor/impacto/{ejercicioId}")
	public List<Integer> obtenerFactoresImpacto( @PathVariable Long ejercicioId){
		List<Integer> resultados = new ArrayList<>();
		List<FactoresRiesgo> factores= ejercicioService.findById(ejercicioId).getFactorRiesgo();
		List<TipoImpacto> impactos = tipoImpactoService.findAll();
				
		for(TipoImpacto imp: impactos) {
			resultados.add(numeroFactoresImpacto(imp.getTipoImpactoPuntaje(), factores));
		}
		
		return resultados;
	}
	
	@GetMapping("/factor/probabilidad/{ejercicioId}")
	public List<Integer> obtenerFactoresProbabilidad( @PathVariable Long ejercicioId){
		List<Integer> resultados = new ArrayList<>();
		List<FactoresRiesgo> factores= ejercicioService.findById(ejercicioId).getFactorRiesgo();
		List<Probabilidad> probabilidades = probabilidadService.findAll();
		
		for(Probabilidad pro: probabilidades) {
			resultados.add(numeroFactoresProbabilidad(pro.getProbabilidadPuntaje(), factores));
		}
		
		return resultados;
	}
	
	@GetMapping("/factor/severidad/{severidad}")
	public List<FactoresRiesgo> obtenerFactoresSeveridad( @PathVariable int severidad){
		List<FactoresRiesgo> factores= factoresService.findAllBySeveridad(severidad);
		return factores;
	}
	
	@GetMapping("/factor/causas-factor/{ejercicioId}")
	public List<Integer> obtenerFactoresCausa( @PathVariable Long ejercicioId){
		
		List<Integer> resultados = new ArrayList<>();
		List<FactoresRiesgo> factores= ejercicioService.findById(ejercicioId).getFactorRiesgo();
		List<String> causas = causaService.findByCausa2();
		
		for(String cau: causas) {
			resultados.add(numeroFactoresCausa(cau, factores));
		}
		
		return resultados;
	}
	
	@GetMapping("/factor/mapa-calor/{ejercicioId}")
	public List<MapaCalor> generarMapa( @PathVariable Long ejercicioId){
		List<MapaCalor> datosMapa = new ArrayList<>();
		List<FactoresRiesgo> factores= ejercicioService.findById(ejercicioId).getFactorRiesgo();
		List<Probabilidad> probabilidades = probabilidadService.findAllByPuntaje();
		List<TipoImpacto> impactos = tipoImpactoService.findAllByPuntaje();
		
		
		
		for(Probabilidad pro: probabilidades) {
			
			for(TipoImpacto imp: impactos) {
				MapaCalor mapa = new MapaCalor();
				mapa.setProbabilidad(pro.getProbabilidadPuntaje());
				System.out.println(pro.getProbabilidadPuntaje());
				mapa.setImpacto(imp.getTipoImpactoPuntaje());
				System.out.println(imp.getTipoImpactoPuntaje());
				int severidad=pro.getProbabilidadPuntaje()*imp.getTipoImpactoPuntaje();
				mapa.setSeveridad(severidad);
				mapa.setFactores(buscarFactoresSeveridad(factores, imp.getTipoImpactoPuntaje(), pro.getProbabilidadPuntaje()));
				mapa.setCantidadId(mapa.getFactores().size());
				mapa.setColor(obtenerColor(severidad));
				datosMapa.add(mapa);
				
			}			
		}
		
		
		return datosMapa;
	}
	
	@GetMapping("/factor/pdf/{ejercicioId}")
	public @ResponseBody ResponseEntity<InputStreamResource> exportToPDF(@PathVariable Long ejercicioId) throws IOException {
		
		EjercicioRiesgo eje=ejercicioService.findById(ejercicioId);
		
		List<FactoresRiesgo> factores = eje.getFactorRiesgo();
	    ByteArrayInputStream bis = createReport(factores);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "inline; filename=factores.pdf");


	    return ResponseEntity
	            .ok()
	            .headers(headers)
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(new InputStreamResource(bis));
	  }
	 
	
	public static ByteArrayInputStream createReport(List<FactoresRiesgo> factores) {

		
	    Document document = new Document();
	    ByteArrayOutputStream out = new ByteArrayOutputStream();

	    try {

	    	PdfWriter.getInstance(document, out);
	        document.open();		
    		
    		document.addTitle("Reporte de factores");
    		Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
    		Paragraph p=new Paragraph("Listado de  Factores", font);
    		p.setAlignment(Element.ALIGN_CENTER);
    		document.add(p);
    		document.add( Chunk.NEWLINE );
    		
    		PdfPTable table = new PdfPTable(11);

    		addTableHeader(table);
    		
    		for(FactoresRiesgo fac: factores) {
    			addRows(table, fac);
    		}
    		
    		document.add(table);            
            document.close();
	    	
	    

	    } catch (DocumentException ex) {

	        System.out.println("error");
	    }

	    return new ByteArrayInputStream(out.toByteArray());
	}

	

	private static void addRows(PdfPTable table, FactoresRiesgo fac) {
		
	
		String tipo ="";
		
		String respuesta ="";
		
		String estatus=null;
		if(fac.getEstatus() ==0) {
			estatus= "Proceso";
		}else if(fac.getEstatus() ==1){
			estatus= "Mitigado";
		}
		Integer riesgoIn =fac.getSeveridad();
		Integer riesgoRe =fac.getRiesgoResidual();
		Integer impacto =fac.getImpacto() ;
		Integer efectividad =fac.getControlId();
		Integer probabilidad =fac.getProbabilidad();
		
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
	    Stream.of(fac.getFactorRiesgo(), fac.getDescripcionFactor(), estatus, tipo, respuesta, fac.getResponsable(),
	    		 probabilidad.toString(), impacto.toString(), efectividad.toString(), riesgoIn.toString(), riesgoRe.toString())
	      .forEach(columnas -> {
	        PdfPCell header = new PdfPCell();
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase((String) columnas, font));
	        table.addCell(header);
	    });
		
	}

	private static void addTableHeader(PdfPTable table) {
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
	    Stream.of("Factor", "Descripci�n", "Estatus", "Tipo Riesgo", "Respuesta", "Responsable", "Probabilidad",
	    		"Impacto", "Efectividad", "Riesgo Inherente", "Riesgo Residual")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.BLUE);
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnTitle, font));
	        table.addCell(header);
	    });
	}
	
	
	private int numeroFactoresImpacto(int impacto, List<FactoresRiesgo> factores) {
		int numero =0;
		for(FactoresRiesgo fac: factores) {
			if(fac.getImpacto() == impacto) {
				numero ++;
			}
		}
		return numero;
	}
	
	private int numeroFactoresProbabilidad(int probabilidad, List<FactoresRiesgo> factores) {
		int numero =0;
		for(FactoresRiesgo fac: factores) {
			if(fac.getProbabilidad() == probabilidad) {
				numero ++;
			}
		}
		return numero;
	}
	
	private int numeroFactoresCausa(String causa, List<FactoresRiesgo> factores) {
		int numero =0;
		for(FactoresRiesgo fac: factores) {
			List<RiCausaRiesgo> causas = fac.getCausaRiesgo(); 
			
			for(RiCausaRiesgo cau: causas) {
				if(cau.getCausa().equals(causa)) {
					numero ++;
				}
			}
		}
		return numero;
	}
	
	private List<FactoresRiesgo> buscarFactoresSeveridad(List<FactoresRiesgo> factores, int impacto, int probabilidad){
		List<FactoresRiesgo> arreglo = new ArrayList<>();
		
		for(FactoresRiesgo fac: factores) {
			if(fac.getProbabilidad() == probabilidad && fac.getImpacto() == impacto) {
				arreglo.add(fac);
			}
		}
		
		return arreglo;
	}
	
	private String obtenerColor(int severidad) {
		String color="";
				
		List<CalificacionesRiesgo> calificaciones  = calificacionService.findAll();
		
		for(CalificacionesRiesgo cal: calificaciones) {
			if(severidad >=cal.getCalificacionesRiesgoMinimo() && severidad<=cal.getCalificacionesRiesgoMaximo()) {
				color=cal.getCalificacionesRiesgoColor();
			}
		}
		
		return color;
	}
	
}
 