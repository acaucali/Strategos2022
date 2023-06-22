package com.strategos.nueva.bancoproyecto.ideas.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RestController;

import com.strategos.nueva.bancoproyecto.ideas.model.Departamentos;
import com.strategos.nueva.bancoproyecto.ideas.service.DepartamentosService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")

public class DepartamentoRestController {

	@Autowired
	private DepartamentosService departamentoService;
	
	private final Logger log = LoggerFactory.getLogger(DepartamentoRestController.class);	
	
	@GetMapping("/departamento")
	public List<Departamentos> index (){
		return departamentoService.findAll();
	}
	
	@GetMapping("/departamento/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		Departamentos departamentoId=null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			departamentoId = departamentoService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if(departamentoId == null) {
			response.put("mensaje", "El departamento id: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Departamentos>(departamentoId, HttpStatus.OK);
		
	}
	
	@PostMapping("/departamentos")
	public ResponseEntity<?> create(@Valid @RequestBody Departamentos departamento, BindingResult result){
		
		Departamentos departamentoNew= null;
		
		Map<String, Object> response=new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors= result.getFieldErrors().stream().map(err -> 
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			departamentoNew = departamentoService.save(departamentoNew);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El tipo poblacion ha sido creado con Exito!");
		response.put("tipopoblacion", departamentoNew);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@PutMapping("/departamento/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Departamentos departamento, BindingResult result, @PathVariable Long id){
		Departamentos departamentoActual = departamentoService.findById(id);
		Departamentos departamentoUpdate = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(departamentoActual == null) {
			response.put("mensaje", "Error, no se pudo editar, el tipo poblacion ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			departamentoActual.setDepartamentoNombre(departamento.getDepartamentoNombre());
			departamentoUpdate = departamentoService.save(departamentoActual);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el tipo poblacion en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El tipo poblacion ha sido actualizado con Exito!");
		response.put("tipopoblacion", departamentoUpdate);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/departamento/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		
		try{
			
			departamentoService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el tipo poblacion en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El tipo poblacion ha sido eliminado con Exito!");
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
	}
}
