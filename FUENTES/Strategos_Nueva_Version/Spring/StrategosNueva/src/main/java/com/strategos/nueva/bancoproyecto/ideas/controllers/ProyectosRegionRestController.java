package com.strategos.nueva.bancoproyecto.ideas.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosRegion;
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosRegionService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")

public class ProyectosRegionRestController {

	@Autowired
	private ProyectosRegionService proyectosRegionService;
	
	@GetMapping("/proyectosRegion")
	public List<ProyectosRegion> index (){
		return proyectosRegionService.findAll();
	}
	
	@GetMapping("/proyectosRegion/{id}")
	public ResponseEntity<?> show (@PathVariable Long id){
		ProyectosRegion proyectoRegionId = null;
		Map<String, Object> response = new HashMap<>();
		try {
			proyectoRegionId = proyectosRegionService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if(proyectoRegionId == null) {
			response.put("mensaje", "El ProyectoRegion id: ".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ProyectosRegion>(proyectoRegionId, HttpStatus.OK);
	}
	
	@DeleteMapping("/proyectosRegion/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		
		try{
			
			proyectosRegionService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el tipo poblacion en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El tipo poblacion ha sido eliminado con Exito!");
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
	}
}
