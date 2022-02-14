package com.strategos.riesgos.procesos.controllers;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;
import com.itextpdf.text.pdf.parser.Path;
import com.itextpdf.text.pdf.parser.clipper.Paths;
import com.strategos.riesgos.models.procesos.entity.ProcesoCaracterizacion;
import com.strategos.riesgos.models.procesos.entity.Procesos;
import com.strategos.riesgos.models.procesos.services.IProcesoCaracterizacionService;
import com.strategos.riesgos.models.procesos.services.IProcesosService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/procesos")
public class ProcesoCaracterizacionRestController {
	@Autowired
	private IProcesoCaracterizacionService caracterizacionService;
	
	@Autowired
	private IProcesosService procesoService;	
	
	private final Logger log = LoggerFactory.getLogger(ProcesoCaracterizacionRestController.class);
	
	@GetMapping("/caracterizacion")
	public List<ProcesoCaracterizacion> index (){
		return caracterizacionService.findAll ();
	}
	
	@GetMapping("/caracterizacion/page/{page}")
	public Page<ProcesoCaracterizacion> index (@PathVariable Integer page){
		Pageable pageable= PageRequest.of(page, 4);
		return caracterizacionService.findAll(pageable);
	}
	
	
	@GetMapping("/caracterizacion/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		ProcesoCaracterizacion caracterizacion=null;
		Map<String, Object> response = new HashMap<>();
		
		try { 
			caracterizacion= caracterizacionService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(caracterizacion == null) {
		  response.put("mensaje", "La caracterización ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
		  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ProcesoCaracterizacion>(caracterizacion, HttpStatus.OK); 		
	}
	
	@PostMapping("/caracterizacion/{procesoId}")
	public ResponseEntity<?> create(@Valid @RequestBody ProcesoCaracterizacion caracterizacion, BindingResult result, @PathVariable Long procesoId) {
		
		ProcesoCaracterizacion caracterizacionNew= null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try { 
			
			Procesos proceso=procesoService.findById(procesoId);
			caracterizacion.setProceso(proceso);
			caracterizacionNew= caracterizacionService.save(caracterizacion);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La caracterización ha sido creada con éxito!");
		response.put("producto", caracterizacionNew);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@PutMapping("/caracterizacion/{id}")
	public ResponseEntity<?>  update(@Valid @RequestBody ProcesoCaracterizacion caracterizacion, BindingResult result, @PathVariable Long id) {
		ProcesoCaracterizacion caracterizacionActual= caracterizacionService.findById(id);
		ProcesoCaracterizacion caracterizacionUpdated = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(caracterizacionActual == null) {
			  response.put("mensaje", "Error, no se pudo editar, la caracterizacion ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try{
			
			caracterizacionActual.setProcedimientoCodigo(caracterizacion.getProcedimientoCodigo());
			caracterizacionActual.setProcedimientoNombre(caracterizacion.getProcedimientoNombre());
			caracterizacionActual.setProcedimientoObjetivo(caracterizacion.getProcedimientoObjetivo());
			caracterizacionActual.setProcedimientoDocumento(caracterizacion.getProcedimientoDocumento());
			
			caracterizacionUpdated=caracterizacionService.save(caracterizacionActual);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la caracterización en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La caracterización ha sido actualizada con éxito!");
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/caracterizacion/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try{
			
			caracterizacionService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar la caracterización en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La caracterizaci�n ha sido eliminada con éxito!");
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
	}
	
	
}
