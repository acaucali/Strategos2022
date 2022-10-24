package com.strategos.nueva.bancoproyecto.strategos.controllers;

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

import com.strategos.nueva.bancoproyecto.ideas.service.TiposPropuestasService;
import com.strategos.nueva.bancoproyecto.strategos.model.UsuarioStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.UsuarioService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class UsuarioRestController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	//Servicios Rest tabla - usuario
	
		private final Logger log = LoggerFactory.getLogger(UsuarioRestController.class);
		
		//servicio que trae la lista de usuario
		@GetMapping("/usuario")
		public List<UsuarioStrategos> index (){
			return usuarioService.findAll();
		}
			
		//servicio que muestra un usuario
		@GetMapping("/usuario/{id}")
		public ResponseEntity<?> show(@PathVariable Long id) {
			
			UsuarioStrategos claseId=null;
			Map<String, Object> response = new HashMap<>();
			
			try { 
				claseId= usuarioService.findById(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar la consulta en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			if(claseId == null) {
			  response.put("mensaje", "El usuario Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
			  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<UsuarioStrategos>(claseId, HttpStatus.OK); 		
		}
		
		//servicio que crea un usuario
		@PostMapping("/usuario")
		public ResponseEntity<?> create(@Valid @RequestBody UsuarioStrategos usuarioN, BindingResult result) {
			
			UsuarioStrategos usuarioNew= null;
			
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			try { 
				
				usuarioNew= usuarioService.save(usuarioN);

			}catch(DataAccessException e) {
				response.put("mensaje", "Error al realizar el insert en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El usuario ha sido creado con Exito!");
			response.put("usuario", usuarioNew);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que actualiza un clase
		@PutMapping("/usuario/{id}")
		public ResponseEntity<?>  update(@Valid @RequestBody UsuarioStrategos usuario, BindingResult result, @PathVariable Long id) {
			UsuarioStrategos usuarioActual= usuarioService.findById(id);
			UsuarioStrategos usuarioUpdated = null;
			Map<String, Object> response = new HashMap<>();
			
			if(result.hasErrors()) {
				
				List<String> errors= result.getFieldErrors().stream().map(err ->
					"Campo: "+err.getField()+" "+err.getDefaultMessage()
				).collect(Collectors.toList());
				
				response.put("errors", errors);
			    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			
			if(usuarioActual == null) {
				  response.put("mensaje", "Error, no se pudo editar, el usuario ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
				  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			try{
				
				
				usuarioActual.setBloqueado(usuario.getBloqueado());
				usuarioActual.setDeshabilitado(usuario.getDeshabilitado());
				usuarioActual.setEstatus(usuario.getEstatus());
				usuarioActual.setForzarCambiarpwd(usuario.getForzarCambiarpwd());
				usuarioActual.setFullName(usuario.getFullName());
				usuarioActual.setInstancia(usuario.getInstancia());
				usuarioActual.setIsAdmin(usuario.getIsAdmin());
				usuarioActual.setIsConnected(usuario.getIsConnected());
				usuarioActual.setIsSystem(usuario.getIsSystem());
				usuarioActual.setModificado(usuario.getModificado());
				usuarioActual.setModificadoId(usuario.getModificadoId());
				usuarioActual.setPwd(usuario.getPwd());
				usuarioActual.setUltimaModifPwd(usuario.getUltimaModifPwd());
											
																			
				usuarioUpdated=usuarioService.save(usuarioActual);
			
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al actualizar el usuario en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El usuario ha sido actualizado con Exito!");
			response.put("usuario", usuarioUpdated);
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
		}
		
		//servicio que elimina las usuario
		@DeleteMapping("/usuario/{id}")
		public ResponseEntity<?> delete(@PathVariable Long id) {
			
			Map<String, Object> response = new HashMap<>();
			
			try{
				
				usuarioService.delete(id);
			}catch(DataAccessException e) {
				response.put("mensaje", "Error al eliminar el usuario en la base de datos!");
				response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("mensaje", "El usuario ha sido eliminado con Exito!");
			return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
		}

}
