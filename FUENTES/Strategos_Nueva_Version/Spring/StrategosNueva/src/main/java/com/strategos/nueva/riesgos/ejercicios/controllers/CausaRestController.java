package com.strategos.nueva.riesgos.ejercicios.controllers;

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
import com.strategos.nueva.riesgos.ejercicios.model.FactoresRiesgo;
import com.strategos.nueva.riesgos.ejercicios.model.RiCausaRiesgo;
import com.strategos.nueva.riesgos.ejercicios.service.CausaService;
import com.strategos.nueva.riesgos.ejercicios.service.FactoresService;



@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/ejercicios")
public class CausaRestController {
	@Autowired
	private CausaService causaService;
	
	@Autowired
	private FactoresService factorService;
	
	private final Logger log = LoggerFactory.getLogger(CausaRestController.class);
	
	@GetMapping("/causa")
	public List<RiCausaRiesgo> index (){
		return causaService.findAll ();
	}
	
	
	
	@GetMapping("/causa/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		RiCausaRiesgo causasRiesgo=null;
		Map<String, Object> response = new HashMap<>();
		
		try { 
			causasRiesgo= causaService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(causasRiesgo == null) {
		  response.put("mensaje", "La causa ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
		  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RiCausaRiesgo>(causasRiesgo, HttpStatus.OK); 		
	}
	
	@PostMapping("/causa/{factorId}")
	public ResponseEntity<?> create(@Valid @RequestBody RiCausaRiesgo causaRiesgo, BindingResult result, @PathVariable Long factorId) {
		
		RiCausaRiesgo causaNew= null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try { 
			
			FactoresRiesgo factor=factorService.findById(factorId);
			causaRiesgo.setFactor(factor);
			causaNew= causaService.save(causaRiesgo);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La causa ha sido creada con éxito!");
		response.put("causa", causaNew);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@PutMapping("/causa/{id}")
	public ResponseEntity<?>  update(@Valid @RequestBody RiCausaRiesgo causasRiesgo, BindingResult result, @PathVariable Long id) {
		RiCausaRiesgo causaActual= causaService.findById(id);
		RiCausaRiesgo causaUpdated = null;
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
			causaActual.setCausa(causasRiesgo.getCausa());
			causaActual.setDescripcionCausa(causasRiesgo.getDescripcionCausa());
			causaActual.setProbabilidadId(causasRiesgo.getProbabilidadId());
					
			
			causaUpdated=causaService.save(causaActual);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la causa en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La causa ha sido actualizado con éxito!");
		response.put("causa", causaUpdated);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/causa/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try{
			
			causaService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar la causa en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La causa ha sido eliminada con éxito!");
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
	}

	@GetMapping("/causa/grafico")
	public List<String> obtenerCausas(){
		return causaService.findByCausa2();
	}
	
}
