package com.strategos.riesgos.ejercicios.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.strategos.riesgos.model.ejercicios.entity.EjercicioRiesgo;
import com.strategos.riesgos.model.ejercicios.entity.FactoresRiesgo;
import com.strategos.riesgos.models.ejercicios.services.IEjercicioService;
import com.strategos.riesgos.models.procesos.entity.ProcesoCaracterizacion;


@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/ejercicios")
public class EjercicioRestController {
	@Autowired
	private IEjercicioService ejercicioRiesgoService;
	
	private final Logger log = LoggerFactory.getLogger(EjercicioRestController.class);
	
	@GetMapping("/ejercicio/proceso/{id}")
	public List<EjercicioRiesgo> index (@PathVariable Long id){
		List<EjercicioRiesgo> ejercicios = new ArrayList();
		List<EjercicioRiesgo> ejerciciosFinal = new ArrayList();
		ejercicios=ejercicioRiesgoService.findAll ();
		for(EjercicioRiesgo eje: ejercicios) {
			if(eje.getProcesoPadreId()== id) {
				ejerciciosFinal.add(eje);
			}
		}
		return ejerciciosFinal;
	}
	
	@GetMapping("/ejercicio/page/{id}/{page}")
	public Page<EjercicioRiesgo> index (@PathVariable Long id, @PathVariable Integer page){
		Pageable pageable= PageRequest.of(page, 4);
		return ejercicioRiesgoService.findAllByProcesoPadreId(id, pageable);
	}
	
	
	@GetMapping("/ejercicio/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		EjercicioRiesgo ejercicioRiesgo=null;
		Map<String, Object> response = new HashMap<>();
		
		try { 
			ejercicioRiesgo= ejercicioRiesgoService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(ejercicioRiesgo == null) {
		  response.put("mensaje", "El ejercicio ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
		  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<EjercicioRiesgo>(ejercicioRiesgo, HttpStatus.OK); 		
	}
	
	@PostMapping("/ejercicio")
	public ResponseEntity<?> create(@Valid @RequestBody EjercicioRiesgo ejercicioRiesgo, BindingResult result) {
		
		EjercicioRiesgo ejercicioNew= null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try { 
			    
			ejercicioNew= ejercicioRiesgoService.save(ejercicioRiesgo);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "EL ejercicio ha sido creado con éxito!");
		response.put("ejercicio", ejercicioNew);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@PutMapping("/ejercicio/{id}")
	public ResponseEntity<?>  update(@Valid @RequestBody EjercicioRiesgo ejercicioRiesgo, BindingResult result, @PathVariable Long id) {
		EjercicioRiesgo ejercicioActual= ejercicioRiesgoService.findById(id);
		EjercicioRiesgo ejercicioUpdated = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(ejercicioActual == null) {
			  response.put("mensaje", "Error, no se pudo editar, el ejercicio ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try{
			
			ejercicioActual.setEjercicioDescripcion(ejercicioRiesgo.getEjercicioDescripcion());
			ejercicioActual.setEjercicioEstatus(ejercicioRiesgo.getEjercicioEstatus());
			ejercicioActual.setFechaEjercicio(ejercicioRiesgo.getFechaEjercicio());
			ejercicioActual.setProcesoPadreId(ejercicioRiesgo.getProcesoPadreId());
					
			
			ejercicioUpdated=ejercicioRiesgoService.save(ejercicioActual);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el ejercicio en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El ejercicio ha sido actualizado con éxito!");
		response.put("ejercicio", ejercicioUpdated);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/ejercicio/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try{
			
			ejercicioRiesgoService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el ejercicio en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El ejercicio ha sido eliminada con éxito!");
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
	}

	@GetMapping("/ejercicio/factor/{id}")
	public List<FactoresRiesgo> obtenerFactores( @PathVariable Long id){
		List<FactoresRiesgo> factores= ejercicioRiesgoService.findById(id).getFactorRiesgo();
		return factores;
	}
}
