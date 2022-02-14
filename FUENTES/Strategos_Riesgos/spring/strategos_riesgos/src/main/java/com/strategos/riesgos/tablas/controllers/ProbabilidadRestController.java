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
import com.strategos.riesgos.models.tablas.entity.Probabilidad;
import com.strategos.riesgos.models.tablas.entity.Probabilidad;
import com.strategos.riesgos.models.tablas.services.IProbabilidadService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/tablas")
public class ProbabilidadRestController {
	@Autowired
	private IProbabilidadService probabilidadService;
	
	private final Logger log = LoggerFactory.getLogger(ProbabilidadRestController.class);
	
	@GetMapping("/probabilidad")
	public List<Probabilidad> index (){
		return probabilidadService.findAll ();
	}
	
	@GetMapping("/probabilidad/mapa")
	public List<Probabilidad> indexPuntaje (){
		return probabilidadService.findAllByPuntaje();
	}
	
	@GetMapping("/probabilidad/page/{page}")
	public Page<Probabilidad> index (@PathVariable Integer page){
		Pageable pageable= PageRequest.of(page, 10);
		return probabilidadService.findAll(pageable);
	}
	
	
	@GetMapping("/probabilidad/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Probabilidad probabilidad=null;
		Map<String, Object> response = new HashMap<>();
		
		try { 
			probabilidad= probabilidadService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(probabilidad == null) {
		  response.put("mensaje", "La probabilidad ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
		  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Probabilidad>(probabilidad, HttpStatus.OK); 		
	}
	
	@PostMapping("/probabilidad")
	public ResponseEntity<?> create(@Valid @RequestBody Probabilidad probabilidad, BindingResult result) {
		
		Probabilidad probabilidadNew= null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try { 
			
			probabilidadNew= probabilidadService.save(probabilidad);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La probabilidad ha sido creada con éxito!");
		response.put("probabilidad", probabilidadNew);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@PutMapping("/probabilidad/{id}")
	public ResponseEntity<?>  update(@Valid @RequestBody Probabilidad probabilidad, BindingResult result, @PathVariable Long id) {
		Probabilidad probabilidadActual= probabilidadService.findById(id);
		Probabilidad probabilidadUpdated = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(probabilidadActual == null) {
			  response.put("mensaje", "Error, no se pudo editar, la probabilidad ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try{
			
			probabilidadActual.setProbabilidadDescripcion(probabilidad.getProbabilidadDescripcion());
			probabilidadActual.setProbabilidadNombre(probabilidad.getProbabilidadNombre());
			probabilidadActual.setProbabilidadPuntaje(probabilidad.getProbabilidadPuntaje());
			
					
			
			probabilidadUpdated=probabilidadService.save(probabilidadActual);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la probabilidad en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La probabilidad ha sido actualizado con éxito!");
		response.put("probabilidad", probabilidadUpdated);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/probabilidad/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try{
			
			probabilidadService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar la probabilidad en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La probabilidad ha sido eliminada con éxito!");
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
	}
	
	@GetMapping("/probabilidad/pdf")
	public @ResponseBody ResponseEntity<InputStreamResource> exportToPDF() throws IOException {
		List<Probabilidad> probabilidades = probabilidadService.findAll();
	    ByteArrayInputStream bis = createReport(probabilidades);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "inline; filename=probabilidad.pdf");


	    return ResponseEntity
	            .ok()
	            .headers(headers)
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(new InputStreamResource(bis));
	}
	 
	
	public static ByteArrayInputStream createReport(List<Probabilidad> probabilidades) {

		
	    Document document = new Document();
	    ByteArrayOutputStream out = new ByteArrayOutputStream();

	    try {

	    	PdfWriter.getInstance(document, out);
	        document.open();		
    		
    		document.addTitle("Reporte de probabilidades");
    		Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
    		Paragraph p=new Paragraph("Listado de Probabilidades", font);
    		p.setAlignment(Element.ALIGN_CENTER);
    		document.add(p);
    		document.add( Chunk.NEWLINE );
    		
    		PdfPTable table = new PdfPTable(3);

    		addTableHeader(table);
    		
    		for(Probabilidad cal: probabilidades) {
    			addRows(table, cal);
    		}
    		
    		document.add(table);            
            document.close();
	    	
	    

	    } catch (DocumentException ex) {

	        System.out.println("error");
	    }

	    return new ByteArrayInputStream(out.toByteArray());
	}

	

	private static void addRows(PdfPTable table, Probabilidad pro) {
		
		Integer puntaje=pro.getProbabilidadPuntaje();
			
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
	    Stream.of(pro.getProbabilidadNombre(), puntaje.toString(), pro.getProbabilidadDescripcion())
	      .forEach(columnas -> {
	        PdfPCell header = new PdfPCell();
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnas, font));
	        table.addCell(header);
	    });
		
	}

	private static void addTableHeader(PdfPTable table) {
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
	    Stream.of("Probabilidad", "Puntaje", "Descripción")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.BLUE);
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnTitle, font));
	        table.addCell(header);
	    });
	}
}
