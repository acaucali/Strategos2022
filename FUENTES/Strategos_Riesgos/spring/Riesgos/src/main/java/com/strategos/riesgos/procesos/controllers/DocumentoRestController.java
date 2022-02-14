package com.strategos.riesgos.procesos.controllers;

import java.io.ByteArrayInputStream;
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

import com.strategos.riesgos.models.procesos.entity.Documento;
import com.strategos.riesgos.models.procesos.entity.ProcesoCaracterizacion;
import com.strategos.riesgos.models.procesos.entity.Procesos;
import com.strategos.riesgos.models.procesos.services.IDocumentoService;
import com.strategos.riesgos.models.procesos.services.IProcesoCaracterizacionService;
import com.strategos.riesgos.models.procesos.services.IProcesosService;
import com.strategos.riesgos.models.tablas.entity.CalificacionesRiesgo;

@CrossOrigin(origins= {"http://localhost:4200", "*" })
@RestController
@RequestMapping("/api/documentos")
public class DocumentoRestController {
	
	@Autowired
	private IDocumentoService documentoService;	
	
	@Autowired
	private IProcesoCaracterizacionService caracterizacionService;
	
	@Autowired
	private IProcesosService procesoService;	
	
		
	private final Logger log = LoggerFactory.getLogger(DocumentoRestController.class);
	
	@GetMapping("/documento/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		
		Documento documento=null;
		Map<String, Object> response = new HashMap<>();
		
		try { 
			documento= documentoService.findById(id);
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(documento == null) {
		  response.put("mensaje", "El documento ID: ".concat(id.toString().concat(" no existe en la base de datos!"))); 	
		  return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Documento>(documento, HttpStatus.OK); 		
	}
	
	
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		try{
			Documento doc= documentoService.findById(id);
			
			String ruta=doc.getDocumentoRuta();
			File folder = new File(ruta);
			deleteFolder(folder);
			documentoService.delete(id);
			
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al eliminar el documento en la base de datos!");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El documento ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>> (response,HttpStatus.OK);
	}
	
	@PostMapping("/upload")
	public ResponseEntity<?> subirDocumento(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id, @RequestParam("tipo") Long tipo){
		
		Map<String, Object> response = new HashMap<>();
		Documento doc = new Documento();
		Documento docNew = new Documento();
		if(!archivo.isEmpty()) {
			
			Date date = new Date();
		    SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
		    
		    File folder = new File("C:/Vision/Documentos/"+"D_"+hourdateFormat.format(date));
		    String ruta = "C:/Vision/Documentos/"+"D_"+hourdateFormat.format(date);
		    String nombre = archivo.getOriginalFilename();
		    Path path = Paths.get(ruta).resolve(nombre).toAbsolutePath();
 
		    folder.mkdirs();
		    if(folder.exists()){
		    	
		    	try {
		    		Files.copy(archivo.getInputStream(), path);
		    	}catch(IOException e){
		    		response.put("mensaje", "Error al subir el archivo al servidor!");
					response.put("error", e.getStackTrace());
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		    	}
		    	
		    	doc.setDocumentoOrigenId(id);
				doc.setDocumentoTipo(tipo.byteValue());
				doc.setDocumentoNombre(archivo.getOriginalFilename());
				doc.setDocumentoRuta(ruta);
				
				docNew=documentoService.save(doc);  
				
		    } 
			
		
		}
		response.put("mensaje", "El documento ha sido creado con éxito!");
		response.put("documento", docNew);
		return new ResponseEntity<Map<String, Object>> (response ,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/download/{id}")
	public @ResponseBody ResponseEntity<InputStreamResource> descargar(@PathVariable Long id) throws IOException {
		
		Documento doc= documentoService.findById(id);
		File file = new File(doc.getDocumentoRuta()+"/"+doc.getDocumentoNombre());
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
	
	/*
	@GetMapping("/download3")
	   public void downloadFile3(HttpServletResponse resonse) throws IOException {
	      File file = new File(FILE_PATH);

	      resonse.setContentType("application/pdf");
	      resonse.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
	      BufferedInputStream inStrem = new BufferedInputStream(new FileInputStream(file));
	      BufferedOutputStream outStream = new BufferedOutputStream(resonse.getOutputStream());
	      
	      byte[] buffer = new byte[1024];
	      int bytesRead = 0;
	      while ((bytesRead = inStrem.read(buffer)) != -1) {
	        outStream.write(buffer, 0, bytesRead);
	      }
	      outStream.flush();
	      inStrem.close();
	   }
	*/

}
