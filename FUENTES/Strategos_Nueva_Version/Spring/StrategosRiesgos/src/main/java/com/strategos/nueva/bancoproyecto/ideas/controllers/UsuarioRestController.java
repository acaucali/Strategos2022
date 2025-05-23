package com.strategos.nueva.bancoproyecto.ideas.controllers;

import java.util.Date;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.strategos.nueva.bancoproyecto.ideas.model.EstatusIdeas;
import com.strategos.nueva.bancoproyecto.ideas.model.Roles;
import com.strategos.nueva.bancoproyecto.ideas.model.Usuario;
import com.strategos.nueva.bancoproyecto.ideas.service.EstatusIdeaService;
import com.strategos.nueva.bancoproyecto.ideas.service.RolesService;
import com.strategos.nueva.bancoproyecto.ideas.service.UsuariosService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class UsuarioRestController {
	

	@Autowired
	private UsuariosService usuarioService;
	
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private final Logger log = LoggerFactory.getLogger(UsuarioRestController.class);
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("/usuarios")
	public List<Usuario> index (){
		return usuarioService.findAll();
	}
	
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/usuarios/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Usuario usuario=null;
		Map<String, Object> response = new HashMap<>();
		
		try { 
			usuario= usuarioService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(usuario == null) {
		  response.put("mensaje", "El usuario Id: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
		  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK); 		
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/usuarios")
	public ResponseEntity<?> create(@Valid @RequestBody Usuario usuarioN, BindingResult result) {
		
		Usuario usuarioNew= null;
		
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			
			List<String> errors= result.getFieldErrors().stream().map(err ->
				"Campo: "+err.getField()+" "+err.getDefaultMessage()
			).collect(Collectors.toList());
			
			response.put("errors", errors);
		    return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		try { 
			 
			Byte tipo = usuarioN.getTipo();
			
			//contrasena 
			
			usuarioN.setPwd(encriptarContrasena(usuarioN.getPass()));
				
			
			Roles rol = new Roles();
			
			//busca el rol
			
			switch(tipo) {
			
				case 0:
					rol = rolesService.findById((long) 1);
					
					break;
				case 1:
					rol = rolesService.findById((long) 3);
						
					break;
				case 2:
					rol = rolesService.findById((long) 2);
					break;
				case 3: 
					rol = rolesService.findById((long) 4);
					break;
			}
			
			//inserta el rol
			if(rol != null) {
				usuarioN.addRol(rol);
			}
			
			usuarioNew= usuarioService.save(usuarioN);

		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario ha sido creado con exito!");
		response.put("usuario", usuarioNew);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<?>  update(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {
		Usuario usuarioActual= usuarioService.findById(id);
		Usuario usuarioUpdated = null;
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
			
			
			usuarioActual.setPwd(encriptarContrasena(usuario.getPass()));
			usuarioActual.setHabilitado(usuario.getHabilitado());
			usuarioActual.setEstatus(usuario.getEstatus());
			usuarioActual.setFullName(usuario.getFullName());
			usuarioActual.setIsAdmin(usuario.getIsAdmin());
			usuarioActual.setModificado(new Date());
			usuarioActual.setPass(usuario.getPass());
			usuarioActual.setTipo(usuario.getTipo());
						
			usuarioUpdated=usuarioService.save(usuarioActual);
		
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al actualizar el usuario en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario ha sido actualizado con exito!");
		response.put("usuario", usuarioUpdated);
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.CREATED);
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try{
			
			usuarioService.delete(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el usuario en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario ha sido eliminado con exito!");
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
	}
	
	public String encriptarContrasena(String contrasena) {
		
		String passwordBcrypt = passwordEncoder.encode(contrasena);
		
		return passwordBcrypt;
		
	}
	
}
