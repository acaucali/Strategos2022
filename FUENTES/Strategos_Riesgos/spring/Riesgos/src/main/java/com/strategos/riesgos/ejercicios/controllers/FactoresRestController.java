package com.strategos.riesgos.ejercicios.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import com.strategos.riesgos.model.ejercicios.entity.CausaRiesgo;
import com.strategos.riesgos.model.ejercicios.entity.ControlesRiesgo;
import com.strategos.riesgos.model.ejercicios.entity.EfectoRiesgo;
import com.strategos.riesgos.model.ejercicios.entity.EjercicioRiesgo;
import com.strategos.riesgos.model.ejercicios.entity.FactoresRiesgo;
import com.strategos.riesgos.models.ejercicios.services.IEjercicioService;
import com.strategos.riesgos.models.ejercicios.services.IFactoresService;
import com.strategos.riesgos.models.procesos.entity.ProcesoCaracterizacion;
import com.strategos.riesgos.models.procesos.entity.ProcesoProducto;
import com.strategos.riesgos.models.procesos.services.IProcesosService;
import com.strategos.riesgos.models.tablas.entity.CalificacionesRiesgo;
import com.strategos.riesgos.models.tablas.entity.TipoRiesgo;
import com.strategos.riesgos.models.tablas.services.ICausaRiesgoService;
import com.strategos.riesgos.models.tablas.services.ITipoRiesgoService;
import com.strategos.riesgos.tablas.controllers.TipoRiesgoRestController;

@CrossOrigin(origins= {"http://localhost:4200", "*" })
@RestController
@RequestMapping("/api/ejercicios") 
public class FactoresRestController {
	@Autowired
	private IFactoresService factoresService;
	
	@Autowired
	private IEjercicioService ejercicioService;
	
	@Autowired
	private static ICausaRiesgoService causaRiesgoService;
	
	@Autowired
	private static ITipoRiesgoService tipoRiesgoService;
	
	
	
	
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
	public List<CausaRiesgo> obtenerCausas( @PathVariable Long id){
		List<CausaRiesgo> causas= factoresService.findById(id).getCausaRiesgo();
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
		
	
		Long tipoId = new Long(fac.getTipoRiesgoId());
		TipoRiesgo tip = tipoRiesgoService.findById(tipoId);
		String tipo= tip.getTipoRiesgo();
		
		Long respuestaId = new Long(fac.getRespuestaId());
		com.strategos.riesgos.models.tablas.entity.CausaRiesgo cau= causaRiesgoService.findById(respuestaId);
		String respuesta= cau.getCausaRiesgo();
		
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
	    		 probabilidad, impacto, efectividad, riesgoIn, riesgoRe)
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
	
}
 