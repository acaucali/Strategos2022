package com.strategos.nueva.riesgos.controllers;

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
import com.strategos.nueva.riesgos.model.TipoImpacto;
import com.strategos.nueva.riesgos.service.TipoImpactoService;



@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/tablas")
public class TipoImpactoRestController {
	@Autowired
	private TipoImpactoService tipoImpactoService;
	
	private final Logger log = LoggerFactory.getLogger(TipoImpactoRestController.class);
	
	@GetMapping("/tipoimpacto")
	public List<TipoImpacto> index (){
		return tipoImpactoService.findAll ();
	}
	
	@GetMapping("/tipoimpacto/mapa")
	public List<TipoImpacto> indexPuntaje (){
		return tipoImpactoService.findAllByPuntaje();
	}

	
	@GetMapping("/tipoimpacto/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		TipoImpacto tipoImpacto=null;
		Map<String, Object> response = new HashMap<>();
		
		try { 
			tipoImpacto= tipoImpactoService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(tipoImpacto == null) {
		  response.put("mensaje", "El tipo de impacto ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
		  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<TipoImpacto>(tipoImpacto, HttpStatus.OK); 		
	}
	
	@PostMapping("/tipoimpacto")
	public ResponseEntity<?> create(@Valid @RequestBody TipoImpacto tipoImpacto, BindingResult result) {
		
		TipoImpacto tipoImpactoNew= null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try { 
			
			tipoImpactoNew= tipoImpactoService.save(tipoImpacto);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "EL tipo de impacto ha sido creado con éxito!");
		response.put("impacto", tipoImpactoNew);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@PutMapping("/tipoimpacto/{id}")
	public ResponseEntity<?>  update(@Valid @RequestBody TipoImpacto tipoImpacto, BindingResult result, @PathVariable Long id) {
		TipoImpacto tipoImpactoActual= tipoImpactoService.findById(id);
		TipoImpacto tipoImpactoUpdated = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(tipoImpactoActual == null) {
			  response.put("mensaje", "Error, no se pudo editar, el tipo de impacto ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try{
			
			tipoImpactoActual.setTipoImpacto(tipoImpacto.getTipoImpacto());
			tipoImpactoActual.setTipoImpactoDescripcion(tipoImpacto.getTipoImpactoDescripcion());
			tipoImpactoActual.setTipoImpactoPuntaje(tipoImpacto.getTipoImpactoPuntaje());
			
			
			tipoImpactoUpdated=tipoImpactoService.save(tipoImpactoActual);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el tipo de impacto en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El tipo de impacto ha sido actualizado con éxito!");
		response.put("impacto", tipoImpactoUpdated);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/tipoimpacto/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try{
			
			tipoImpactoService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el tipo de impacto en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El tipo de impacto ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
	}
	
	@GetMapping("/tipoimpacto/pdf")
	public @ResponseBody ResponseEntity<InputStreamResource> exportToPDF() throws IOException {
		List<TipoImpacto> impactos = tipoImpactoService.findAll();
	    ByteArrayInputStream bis = createReport(impactos);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "inline; filename=tiposimpacto.pdf");


	    return ResponseEntity
	            .ok()
	            .headers(headers)
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(new InputStreamResource(bis));
	}
	 
	
	public static ByteArrayInputStream createReport(List<TipoImpacto> impactos) {

		
	    Document document = new Document();
	    ByteArrayOutputStream out = new ByteArrayOutputStream();

	    try {

	    	PdfWriter.getInstance(document, out);
	        document.open();		
    		
    		document.addTitle("Reporte de Tipos de impacto");
    		Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
    		Paragraph p=new Paragraph("Listado de Tipos de Impacto", font);
    		p.setAlignment(Element.ALIGN_CENTER);
    		document.add(p);
    		document.add( Chunk.NEWLINE );
    		
    		PdfPTable table = new PdfPTable(3);

    		addTableHeader(table);
    		
    		for(TipoImpacto cal: impactos) {
    			addRows(table, cal);
    		}
    		
    		document.add(table);            
            document.close();
	    	
	    

	    } catch (DocumentException ex) {

	        System.out.println("error");
	    }

	    return new ByteArrayInputStream(out.toByteArray());
	}

	

	private static void addRows(PdfPTable table, TipoImpacto imp) {
		
		Integer puntaje=imp.getTipoImpactoPuntaje();
			
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
	    Stream.of(imp.getTipoImpacto(), puntaje.toString(), imp.getTipoImpactoDescripcion())
	      .forEach(columnas -> {
	        PdfPCell header = new PdfPCell();
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnas, font));
	        table.addCell(header);
	    });
		
	}

	private static void addTableHeader(PdfPTable table) {
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
	    Stream.of("Tipo Impacto", "Puntaje", "Descripción")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.BLUE);
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnTitle, font));
	        table.addCell(header);
	    });
	}
}
