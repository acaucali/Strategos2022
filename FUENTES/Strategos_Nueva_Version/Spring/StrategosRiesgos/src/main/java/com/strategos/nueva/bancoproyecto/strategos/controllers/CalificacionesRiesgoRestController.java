package com.strategos.nueva.bancoproyecto.strategos.controllers;

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
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.strategos.nueva.bancoproyecto.ideas.service.TiposPropuestasService;
import com.strategos.nueva.bancoproyecto.strategos.model.CalificacionesRiesgo;
import com.strategos.nueva.bancoproyecto.strategos.model.ClaseIndicadoresStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.CalificacionesRiesgoService;
import com.strategos.nueva.bancoproyecto.strategos.service.ClaseIndicadorService;

import java.io.ByteArrayInputStream;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.Document;
import java.io.ByteArrayOutputStream;
import org.springframework.http.HttpHeaders;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Paragraph;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class CalificacionesRiesgoRestController {
	
	@Autowired
	private CalificacionesRiesgoService calificacionesRiesgoService;
	
	private final Logger log = LoggerFactory.getLogger(CalificacionesRiesgoRestController.class);
	
	@GetMapping("/calificacionesriesgo")
	public List<CalificacionesRiesgo> index (){
		return calificacionesRiesgoService.findAll ();
	}
	
	
	@GetMapping("/calificacionesriesgo/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		CalificacionesRiesgo calificacionesRiesgo=null;
		Map<String, Object> response = new HashMap<>();
		
		try { 
			calificacionesRiesgo= calificacionesRiesgoService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(calificacionesRiesgo == null) {
		  response.put("mensaje", "La calificación ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
		  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CalificacionesRiesgo>(calificacionesRiesgo, HttpStatus.OK); 		
	}
	
	@PostMapping("/calificacionesriesgo")
	public ResponseEntity<?> create(@Valid @RequestBody CalificacionesRiesgo calificacionesRiesgo, BindingResult result) {
		
		CalificacionesRiesgo calificacionNew= null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try { 
			
			calificacionNew= calificacionesRiesgoService.save(calificacionesRiesgo);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La calificación ha sido creada con éxito!");
		response.put("calificacion", calificacionNew);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@PutMapping("/calificacionesriesgo/{id}")
	public ResponseEntity<?>  update(@Valid @RequestBody CalificacionesRiesgo calificacionesRiesgo, BindingResult result, @PathVariable Long id) {
		CalificacionesRiesgo calificacionActual= calificacionesRiesgoService.findById(id);
		CalificacionesRiesgo calificacionUpdated = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(calificacionActual == null) {
			  response.put("mensaje", "Error, no se pudo editar, la calificación ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try{
			
			calificacionActual.setCalificacionesRiesgo(calificacionesRiesgo.getCalificacionesRiesgo());
			calificacionActual.setCalificacionesRiesgoAccion(calificacionesRiesgo.getCalificacionesRiesgoAccion());
			calificacionActual.setCalificacionesRiesgoColor(calificacionesRiesgo.getCalificacionesRiesgoColor());
			calificacionActual.setCalificacionesRiesgoMaximo(calificacionesRiesgo.getCalificacionesRiesgoMaximo());
			calificacionActual.setCalificacionesRiesgoMinimo(calificacionesRiesgo.getCalificacionesRiesgoMinimo());
			
			
			calificacionUpdated=calificacionesRiesgoService.save(calificacionActual);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la calificación en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La calificación ha sido actualizado con éxito!");
		response.put("calificacion", calificacionUpdated);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/calificacionesriesgo/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try{
			
			calificacionesRiesgoService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar la calificación en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La calificación ha sido eliminada con éxito!");
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
	}
	
	
	
	@GetMapping("/calificacionesriesgo/pdf")
	public @ResponseBody ResponseEntity<InputStreamResource> exportToPDF() throws IOException {
		List<CalificacionesRiesgo> calificaciones = calificacionesRiesgoService.findAll();
	    ByteArrayInputStream bis = createReport(calificaciones);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "inline; filename=calificaciones.pdf");


	    return ResponseEntity
	            .ok()
	            .headers(headers)
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(new InputStreamResource(bis));
	  }
	 
	
	public static ByteArrayInputStream createReport(List<CalificacionesRiesgo> calificaciones) {

		
	    Document document = new Document();
	    ByteArrayOutputStream out = new ByteArrayOutputStream();

	    try {

	    	PdfWriter.getInstance(document, out);
	        document.open();		
    		
    		document.addTitle("Reporte de calificaciones");
    		Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
    		Paragraph p=new Paragraph("Listado de Calificaciones", font);
    		p.setAlignment(Element.ALIGN_CENTER);
    		document.add(p);
    		document.add( Chunk.NEWLINE );
    		
    		PdfPTable table = new PdfPTable(5);

    		addTableHeader(table);
    		
    		for(CalificacionesRiesgo cal: calificaciones) {
    			addRows(table, cal);
    		}
    		
    		document.add(table);            
            document.close();
	    	
	    

	    } catch (DocumentException ex) {

	        System.out.println("error");
	    }

	    return new ByteArrayInputStream(out.toByteArray());
	}

	

	private static void addRows(PdfPTable table, CalificacionesRiesgo cal) {
		
		
		Integer min =cal.getCalificacionesRiesgoMinimo();
		Integer max =cal.getCalificacionesRiesgoMaximo();	
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
	    Stream.of(cal.getCalificacionesRiesgo(),min.toString(),max.toString(),cal.getCalificacionesRiesgoColor(),cal.getCalificacionesRiesgoAccion())
	      .forEach(columnas -> {
	        PdfPCell header = new PdfPCell();
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnas, font));
	        table.addCell(header);
	    });
		
	}

	private static void addTableHeader(PdfPTable table) {
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
	    Stream.of("Calificación", "Rango mínimo", "Rango máximo", "Color", "Acción a tomar")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.BLUE);
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnTitle, font));
	        table.addCell(header);
	    });
	}
	
	
	
}
