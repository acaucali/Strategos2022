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
import com.strategos.riesgos.models.tablas.entity.CausaRiesgo;
import com.strategos.riesgos.models.tablas.entity.CausaRiesgo;
import com.strategos.riesgos.models.tablas.services.ICausaRiesgoService;

@CrossOrigin(origins= {"http://localhost:4200", "*" })
@RestController
@RequestMapping("/api/tablas")
public class CausaRiesgoRestController {
	@Autowired
	private ICausaRiesgoService causaRiesgoService;
	
	private final Logger log = LoggerFactory.getLogger(CausaRiesgoRestController.class);
	
	@GetMapping("/causariesgo")
	public List<CausaRiesgo> index (){
		return causaRiesgoService.findAll ();
	}
	
	@GetMapping("/causariesgo/page/{page}")
	public Page<CausaRiesgo> index (@PathVariable Integer page){
		Pageable pageable= PageRequest.of(page, 10);
		return causaRiesgoService.findAll(pageable);
	}
	
	
	@GetMapping("/causariesgo/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		CausaRiesgo causasRiesgo=null;
		Map<String, Object> response = new HashMap<>();
		
		try { 
			causasRiesgo= causaRiesgoService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(causasRiesgo == null) {
		  response.put("mensaje", "La causa ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
		  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<CausaRiesgo>(causasRiesgo, HttpStatus.OK); 		
	}
	
	@PostMapping("/causariesgo")
	public ResponseEntity<?> create(@Valid @RequestBody CausaRiesgo causaRiesgo, BindingResult result) {
		
		CausaRiesgo causaNew= null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try { 
			
			causaNew= causaRiesgoService.save(causaRiesgo);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La causa ha sido creada con éxito!");
		response.put("causa", causaNew);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@PutMapping("/causariesgo/{id}")
	public ResponseEntity<?>  update(@Valid @RequestBody CausaRiesgo causasRiesgo, BindingResult result, @PathVariable Long id) {
		CausaRiesgo causaActual= causaRiesgoService.findById(id);
		CausaRiesgo causaUpdated = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(causaActual == null) {
			  response.put("mensaje", "Error, no se pudo editar, la causa ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try{
			
			causaActual.setCausaRiesgo(causasRiesgo.getCausaRiesgo());
			causaActual.setCausaRiesgoDescripcion(causasRiesgo.getCausaRiesgoDescripcion());			
			
			causaUpdated=causaRiesgoService.save(causaActual);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la causa en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La causa ha sido actualizado con éxito!");
		response.put("causa", causaUpdated);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/causariesgo/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try{
			
			causaRiesgoService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar la causa en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La causa ha sido eliminada con éxito!");
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
	}

	@GetMapping("/causariesgo/pdf")
	public @ResponseBody ResponseEntity<InputStreamResource> exportToPDF() throws IOException {
		List<CausaRiesgo> causas = causaRiesgoService.findAll();
	    ByteArrayInputStream bis = createReport(causas);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "inline; filename=causas.pdf");


	    return ResponseEntity
	            .ok()
	            .headers(headers)
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(new InputStreamResource(bis));
	  }
	 
	
	public static ByteArrayInputStream createReport(List<CausaRiesgo> causas) {

		
	    Document document = new Document();
	    ByteArrayOutputStream out = new ByteArrayOutputStream();

	    try {

	    	PdfWriter.getInstance(document, out);
	        document.open();		
    		
    		document.addTitle("Reporte de causas");
    		Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
    		Paragraph p=new Paragraph("Listado de Causas", font);
    		p.setAlignment(Element.ALIGN_CENTER);
    		document.add(p);
    		document.add( Chunk.NEWLINE );
    		
    		PdfPTable table = new PdfPTable(2);

    		addTableHeader(table);
    		
    		for(CausaRiesgo cal: causas) {
    			addRows(table, cal);
    		}
    		
    		document.add(table);            
            document.close();
	    	
	    

	    } catch (DocumentException ex) {

	        System.out.println("error");
	    }

	    return new ByteArrayInputStream(out.toByteArray());
	}

	

	private static void addRows(PdfPTable table, CausaRiesgo cau) {
		
		
			
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
	    Stream.of(cau.getCausaRiesgo(), cau.getCausaRiesgoDescripcion())
	      .forEach(columnas -> {
	        PdfPCell header = new PdfPCell();
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnas, font));
	        table.addCell(header);
	    });
		
	}

	private static void addTableHeader(PdfPTable table) {
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
	    Stream.of("Causa", "Descripción")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.BLUE);
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnTitle, font));
	        table.addCell(header);
	    });
	}

}
