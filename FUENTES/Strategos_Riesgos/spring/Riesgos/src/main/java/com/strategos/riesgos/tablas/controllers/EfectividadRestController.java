package com.strategos.riesgos.tablas.controllers;

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
import com.strategos.riesgos.models.tablas.entity.EfectividadControles;
import com.strategos.riesgos.models.tablas.services.IEfectividadService;

@CrossOrigin(origins= {"http://localhost:4200", "*" })
@RestController
@RequestMapping("/api/tablas")
public class EfectividadRestController {
	@Autowired
	private IEfectividadService efectividadService;
	
	private final Logger log = LoggerFactory.getLogger(EfectividadRestController.class);
	
	@GetMapping("/efectividad")
	public List<EfectividadControles> index (){
		return efectividadService.findAll ();
	}
	
	@GetMapping("/efectividad/page/{page}")
	public Page<EfectividadControles> index (@PathVariable Integer page){
		Pageable pageable= PageRequest.of(page, 10);
		return efectividadService.findAll(pageable);
	}
	
	
	@GetMapping("/efectividad/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		EfectividadControles efectividad=null;
		Map<String, Object> response = new HashMap<>();
		
		try { 
			efectividad= efectividadService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(efectividad == null) {
		  response.put("mensaje", "La efectividad ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
		  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<EfectividadControles>(efectividad, HttpStatus.OK); 		
	}
	
	@PostMapping("/efectividad")
	public ResponseEntity<?> create(@Valid @RequestBody EfectividadControles efectividad, BindingResult result) {
		
		EfectividadControles efectividadNew= null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try { 
			
			efectividadNew= efectividadService.save(efectividad);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La efectividad ha sido creado con éxito!");
		response.put("efectividad", efectividadNew);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@PutMapping("/efectividad/{id}")
	public ResponseEntity<?>  update(@Valid @RequestBody EfectividadControles efectividad, BindingResult result, @PathVariable Long id) {
		EfectividadControles efectividadActual= efectividadService.findById(id);
		EfectividadControles efectividadUpdated = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(efectividadActual == null) {
			  response.put("mensaje", "Error, no se pudo editar, la efectividad ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try{
			
			efectividadActual.setEfectividad(efectividad.getEfectividad());
			efectividadActual.setEfectividadPuntaje(efectividad.getEfectividadPuntaje());			
			
			efectividadUpdated=efectividadService.save(efectividadActual);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la efectividad en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La efectividad ha sido actualizado con éxito!");
		response.put("causa", efectividadUpdated);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/efectividad/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try{
			
			efectividadService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar la efectividad en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La efectividad ha sido eliminada con éxito!");
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
	}

	@GetMapping("/efectividad/pdf")
	public @ResponseBody ResponseEntity<InputStreamResource> exportToPDF() throws IOException {
		List<EfectividadControles> efectividades = efectividadService.findAll();
	    ByteArrayInputStream bis = createReport(efectividades);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "inline; filename=efectividades.pdf");


	    return ResponseEntity
	            .ok()
	            .headers(headers)
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(new InputStreamResource(bis));
	  }
	 
	
	public static ByteArrayInputStream createReport(List<EfectividadControles> efectividades) {

		
	    Document document = new Document();
	    ByteArrayOutputStream out = new ByteArrayOutputStream();

	    try {

	    	PdfWriter.getInstance(document, out);
	        document.open();		
    		
    		document.addTitle("Reporte de efectividades");
    		Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
    		Paragraph p=new Paragraph("Listado de Efectividades", font);
    		p.setAlignment(Element.ALIGN_CENTER);
    		document.add(p);
    		document.add( Chunk.NEWLINE );
    		
    		PdfPTable table = new PdfPTable(2);

    		addTableHeader(table);
    		
    		for(EfectividadControles efe: efectividades) {
    			addRows(table, efe);
    		}
    		
    		document.add(table);            
            document.close();
	    	
	    

	    } catch (DocumentException ex) {

	        System.out.println("error");
	    }

	    return new ByteArrayInputStream(out.toByteArray());
	}

	

	private static void addRows(PdfPTable table, EfectividadControles efe) {
		
		Integer puntaje =efe.getEfectividadPuntaje();
			
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
	    Stream.of(efe.getEfectividad(), puntaje.toString())
	      .forEach(columnas -> {
	        PdfPCell header = new PdfPCell();
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnas, font));
	        table.addCell(header);
	    });
		
	}

	private static void addTableHeader(PdfPTable table) {
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
	    Stream.of("Efectividad", "Puntaje")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.BLUE);
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnTitle, font));
	        table.addCell(header);
	    });
	}

}
