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
import com.strategos.riesgos.model.ejercicios.entity.ControlesRiesgo;
import com.strategos.riesgos.model.ejercicios.entity.FactoresRiesgo;
import com.strategos.riesgos.models.ejercicios.services.IControlesService;
import com.strategos.riesgos.models.ejercicios.services.IFactoresService;


@CrossOrigin(origins= {"http://localhost:4200", "*" })
@RestController
@RequestMapping("/api/ejercicios")
public class ControlesriesgoRestController {
	@Autowired
	private IControlesService controlesRiesgoService;
	
	@Autowired
	private IFactoresService factorService;
	
	private final Logger log = LoggerFactory.getLogger(ControlesriesgoRestController.class);
	
	@GetMapping("/control")
	public List<ControlesRiesgo> index (){
		return controlesRiesgoService.findAll ();
	}
	
	@GetMapping("/control/page/{page}")
	public Page<ControlesRiesgo> index (@PathVariable Integer page){
		Pageable pageable= PageRequest.of(page, 10);
		return controlesRiesgoService.findAll(pageable);
	}
	
	
	@GetMapping("/control/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		ControlesRiesgo controlRiesgo=null;
		Map<String, Object> response = new HashMap<>();
		
		try { 
			controlRiesgo= controlesRiesgoService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(controlRiesgo == null) {
		  response.put("mensaje", "El control ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
		  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ControlesRiesgo>(controlRiesgo, HttpStatus.OK); 		
	}
	
	@PostMapping("/control/{factorId}")
	public ResponseEntity<?> create(@Valid @RequestBody ControlesRiesgo controlRiesgo, BindingResult result, @PathVariable Long factorId) {
		
		ControlesRiesgo controlNew= null;
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
			controlRiesgo.setFactor(factor);
			controlNew= controlesRiesgoService.save(controlRiesgo);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El control ha sido creado con éxito!");
		response.put("control", controlNew);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@PutMapping("/control/{id}")
	public ResponseEntity<?>  update(@Valid @RequestBody ControlesRiesgo controlRiesgo, BindingResult result, @PathVariable Long id) {
		ControlesRiesgo controlActual= controlesRiesgoService.findById(id);
		ControlesRiesgo controlUpdated = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(controlActual == null) {
			  response.put("mensaje", "Error, no se pudo editar, el control ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try{
			controlActual.setControl(controlRiesgo.getControl());
			controlActual.setDescripcionControl(controlRiesgo.getDescripcionControl());
			controlActual.setEfectividadId(controlRiesgo.getEfectividadId());
				
			
			controlUpdated=controlesRiesgoService.save(controlActual);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar la causa en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El control ha sido actualizado con éxito!");
		response.put("control", controlUpdated);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/control/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try{
			
			controlesRiesgoService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el error en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El error ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
	}

	
}
