package com.strategos.nueva.bancoproyecto.ideas.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.strategos.nueva.bancoproyecto.ideas.model.Proyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosDocumentosAnexos;
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosDocumentosAnexosService;
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosService;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class ProyectosDocumentosAnexosRestController {
	
	@Autowired
	private ProyectosDocumentosAnexosService proyectosDocumentosService;
	
	@Autowired
	private ProyectosService proyectosService;
	
	private final Logger log = LoggerFactory.getLogger(ProyectosDocumentosAnexosRestController.class);
	
	@GetMapping("/documentoProyecto/{id}")
	public ResponseEntity<?> show(@PathVariable Long id){
		ProyectosDocumentosAnexos documento=null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			documento = proyectosDocumentosService.findById(id);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProyectosDocumentosAnexos>(documento, HttpStatus.OK);
	}

	@DeleteMapping("/documentoProyecto/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Proyectos proyectoId = proyectosService.findById(id);
			proyectoId.setDocumentoId(null);
			
			proyectosService.save(proyectoId);
			
			ProyectosDocumentosAnexos doc = proyectosDocumentosService.findById(id);
			
			String ruta = doc.getDocumentoRuta();
			File folder = new File(ruta);
			deleteFolder(folder);
			proyectosDocumentosService.delete(id);
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el documento en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El documento ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/documentoProyecto/upload")
	public ResponseEntity<?> subirDocumento(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id, @RequestParam("titulo") String titulo, @RequestParam("descripcion") String descripcion){
		
		Map<String, Object> response = new HashMap<>();
		ProyectosDocumentosAnexos doc = new ProyectosDocumentosAnexos();
		ProyectosDocumentosAnexos docNew = new ProyectosDocumentosAnexos();
		
		if(!archivo.isEmpty()) {
			Date date = new Date();
		    SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
		    
		    File folder = new File("C:/Vision/Documentos/"+"D_"+hourdateFormat.format(date));
		    String nombre = archivo.getOriginalFilename();
		    String ruta = "C:/Vision/Documentos/"+"D_"+hourdateFormat.format(date);
		    
		    Path path = Paths.get(ruta).resolve(nombre).toAbsolutePath();
 
		    folder.mkdirs();
		    
		    if(folder.exists()) {
		    	try {
		    		Files.copy(archivo.getInputStream(), path);
		    	}catch(IOException e){
		    		response.put("mensaje", "Error al subir el archivo al servidor!");
					response.put("error", e.getStackTrace());
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		    	}
		    	
		    	doc.setDescripcion(descripcion);
		    	doc.setDocumentoRuta(ruta+"/"+nombre);
		    	doc.setProyectoId(id);
		    	doc.setTituloDocumento(titulo);
		    	
		    	docNew = proyectosDocumentosService.save(doc);
		    	
		    	Proyectos proyectoId = proyectosService.findById(id);
		    	proyectoId.setDocumentoId(docNew.getDocumentoId());
		    	
		    	proyectosService.save(proyectoId);
		    }
		}
		response.put("mensaje", "El documento ha sido creado con éxito!");
		response.put("documento", docNew);
		return new ResponseEntity<Map<String, Object>> (response ,HttpStatus.CREATED);
	}
	
	@GetMapping("/documentoProyecto/download/{id}")
	public @ResponseBody ResponseEntity<InputStreamResource> descargar(@PathVariable Long id) throws IOException {
		
		ProyectosDocumentosAnexos doc = proyectosDocumentosService.findById(id);
		File file = new File(doc.getDocumentoRuta());
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		
		return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION,
	                  "attachment;filename=" + file.getName()) 
	            .contentLength(file.length())
	            .body(resource);
	}
	
	private void deleteFolder(File fileDel) {
        if(fileDel.isDirectory()){            
            
            if(fileDel.list().length == 0)
                fileDel.delete();
            else{                
               for (String temp : fileDel.list()) {
                   File fileDelete = new File(fileDel, temp);
                   //recursive delete
                   deleteFolder(fileDelete);
               }
               //check the directory again, if empty then delete it
               if(fileDel.list().length==0)
                   fileDel.delete();              
            }

        }else{
            
            //if file, then delete it
            fileDel.delete();            
        }
    }
}
