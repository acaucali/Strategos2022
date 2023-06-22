package com.strategos.nueva.riesgos.procesos.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.strategos.nueva.riesgos.procesos.model.ProcesoCaracterizacion;
import com.strategos.nueva.riesgos.procesos.model.ProcesoProducto;
import com.strategos.nueva.riesgos.procesos.model.Procesos;
import com.strategos.nueva.riesgos.procesos.service.ProcesosService;
import com.strategos.nueva.riesgos.procesos.util.Arbol;
import com.strategos.nueva.riesgos.procesos.util.Nodo;


@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/procesos")
public class ProcesoRestController {
	
	@Autowired
	private ProcesosService procesoService;
	
	private final Logger log = LoggerFactory.getLogger(ProcesoRestController.class);
	
	@GetMapping("/proceso")
	public List<Procesos> index (){
		return procesoService.findAll ();
	}
	
	@GetMapping("/proceso/arbol")
	public List<Arbol> getArbol(){
		
		List<Procesos> procesos= procesoService.findAll ();
		ArrayList<Arbol> arboles = new ArrayList();
		if(procesos !=null) {
			List<Nodo<Procesos>> nodos = construirArbol(procesos, (int) 0);
			
		
			for(Nodo nod : nodos) {
				Arbol arbol = new Arbol();
				arbol.setText(nod.getData().getProcesoNombre());
				arbol.setId(nod.getData().getProcesosId().intValue());
				if(nod.getChildren() != null) {
					arbol.setItems(convertirArbol(nod.getChildren()));
				}
				arboles.add(arbol);
			}
		}else {
			return null;
		}
		
		return arboles;
	}
		
	public ArrayList<Arbol> convertirArbol(List<Nodo<Procesos>> nodos) {
		ArrayList<Arbol> arboles = new ArrayList();
		for(Nodo nod: nodos) {
			Arbol arbol = new Arbol();
			arbol.setText(nod.getData().getProcesoNombre());
			arbol.setId(nod.getData().getProcesosId().intValue());
			if(nod.getChildren() != null) {
				arbol.setItems(convertirArbol(nod.getChildren()));
			}
			arboles.add(arbol);
		}
		
		return arboles;
	}
	
	public ArrayList<Nodo<Procesos>> construirArbol(List<Procesos> procesos, int padreId) {
		
		ArrayList<Nodo<Procesos>> arbol = new ArrayList();
		ArrayList<Nodo<Procesos>> hijos = new ArrayList();
		Nodo<Procesos> nodo= new Nodo(new Procesos());
		
		for(Procesos pro: procesos) {
			nodo = new Nodo(pro);
			
			if(nodo.getData().getProcesoPadreId() == 0) {
				nodo.setParent(0);
			}
			
			if(nodo.getData().getProcesoPadreId() == padreId) {
				hijos = construirArbol(procesos, pro.getProcesosId().intValue());
				if(hijos != null) {
					nodo.setData(pro);
					nodo.addChildren(hijos);
				}
				arbol.add(nodo);
			}
			
			
		}
		if(arbol.size() ==0) {
			return null;	
		}
		
		return arbol;
		
	}
	
	
	@GetMapping("/proceso/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Procesos Proceso=null;
		Map<String, Object> response = new HashMap<>();
		
		try { 
			Proceso= procesoService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(Proceso == null) {
		  response.put("mensaje", "El proceso ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
		  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Procesos>(Proceso, HttpStatus.OK); 		
	}
	
	
	

	@PostMapping("/proceso")
	public ResponseEntity<?> create(@Valid @RequestBody Procesos proceso, BindingResult result) {
		
		Procesos procesoNew= null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try { 
			
			procesoNew= procesoService.save(proceso);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El proceso ha sido creado con éxito!");
		response.put("proceso", procesoNew);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	
	@PutMapping("/proceso/{id}")
	public ResponseEntity<?>  update(@Valid @RequestBody Procesos proceso, BindingResult result, @PathVariable Long id) {
		Procesos procesoActual= procesoService.findById(id);
		Procesos procesoUpdated = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if(procesoActual == null) {
			  response.put("mensaje", "Error, no se pudo editar, el proceso ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try{
			
			procesoActual.setProcesoCodigo(proceso.getProcesoCodigo());
			procesoActual.setProcesoDocumento(proceso.getProcesoDocumento());
			procesoActual.setProcesoNombre(proceso.getProcesoNombre());
			procesoActual.setProcesoTipo(proceso.getProcesoTipo());
			procesoActual.setResponsable(proceso.getResponsable());
			procesoActual.setDescripcion(proceso.getDescripcion());
			procesoActual.setProcesoDocumento(proceso.getProcesoDocumento());
			procesoUpdated=procesoService.save(procesoActual);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el proceso en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El proceso ha sido actualizado con éxito!");
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/proceso/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try{
			
			procesoService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el proceso en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El proceso ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
	}


	@GetMapping("/proceso/procedimiento/{id}")
	public List<ProcesoCaracterizacion> obtenerProcedimientos( @PathVariable Long id){
		List<ProcesoCaracterizacion> procedimientos= procesoService.findById(id).getProcesoCaracterizacion();
		return procedimientos;
	}
	
	@GetMapping("/proceso/producto/{id}")
	public List<ProcesoProducto> obtenerProductos( @PathVariable Long id){
		List<ProcesoProducto> productos= procesoService.findById(id).getProcesoProducto();
		return productos;
	}
	
}
