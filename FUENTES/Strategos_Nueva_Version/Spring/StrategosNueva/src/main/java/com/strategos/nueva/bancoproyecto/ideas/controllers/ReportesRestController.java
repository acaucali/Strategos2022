package com.strategos.nueva.bancoproyecto.ideas.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.strategos.nueva.bancoproyecto.ideas.model.EstatusIdeas;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.TiposObjetivos;
import com.strategos.nueva.bancoproyecto.ideas.model.TiposPropuestas;
import com.strategos.nueva.bancoproyecto.ideas.service.EstatusIdeaService;
import com.strategos.nueva.bancoproyecto.ideas.service.IdeasProyectosService;
import com.strategos.nueva.bancoproyecto.ideas.service.TiposObjetivosService;
import com.strategos.nueva.bancoproyecto.ideas.service.TiposPropuestasService;
import com.strategos.nueva.bancoproyecto.strategos.model.OrganizacionesStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;
import com.strategos.nueva.bancoproyectos.model.util.ExportExcelResumido;

@CrossOrigin(origins= {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/strategos/bancoproyectos")
public class ReportesRestController {

	@Autowired
	private IdeasProyectosService ideasProyectosService;
	@Autowired
	private EstatusIdeaService estatusService;
	@Autowired
	private TiposObjetivosService objetivosService;
	@Autowired
	private TiposPropuestasService propuestasService;
	@Autowired
	private OrganizacionService organizacionService;
	
	//Servicios Rest tabla - idea
	private final Logger log = LoggerFactory.getLogger(IdeasProyectosRestController.class);
	
	
	 @GetMapping("/idea/excel/{ideaId}")
	    public void exportToExcel(HttpServletResponse response, @PathVariable Long ideaId) throws IOException {
		 
		 IdeasProyectos idea = ideasProyectosService.findById(ideaId);
		 
	        response.setContentType("application/octet-stream");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=idea_" + currentDateTime + ".xlsx";
	        response.setHeader(headerKey, headerValue);
	         
	        ExportExcelResumido excelExporter = new ExportExcelResumido(idea);	
	        excelExporter.exportDetalle(response);    
	 } 
	
	 
	 @GetMapping("/idea/excel/resumido/{orgId}")
	    public void exportToExcelResumido(HttpServletResponse response, @PathVariable Long orgId) throws IOException {
		 
		 	
		 
	        response.setContentType("application/octet-stream");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=idea_" + currentDateTime + ".xlsx";
	        response.setHeader(headerKey, headerValue);
	        
	        List<IdeasProyectos> ideasOrg = ideasProyectosService.findAll(); 
			List<IdeasProyectos> ideasFin = new ArrayList();
			ByteArrayInputStream bis;
			
			if(orgId == 0) { // todas las organizaciones
				ExportExcelResumido excelExporter = new ExportExcelResumido(ideasOrg);	         
		        excelExporter.export(response); 
			}else {
				for(IdeasProyectos ide: ideasOrg) {
					if(ide.getDependenciaId() == orgId) {
						ideasFin.add(ide);
					}				
				}
				ExportExcelResumido excelExporter = new ExportExcelResumido(ideasFin);	         
		        excelExporter.export(response); 
			}
			  
	 } 
	
	
	

	@GetMapping("/idea/pdf/{ideaId}") //ideas detalle
	public @ResponseBody ResponseEntity<InputStreamResource> exportToPDF(@PathVariable Long ideaId) throws IOException {
		
		IdeasProyectos idea = ideasProyectosService.findById(ideaId);
				
		
	    ByteArrayInputStream bis = createReportDetalle(idea);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "inline; filename=ideasDetalle.pdf");


	    return ResponseEntity
	            .ok()
	            .headers(headers)
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(new InputStreamResource(bis));
	}
	
	@GetMapping("/idea/pdf/resumido/{orgId}")
	public @ResponseBody ResponseEntity<InputStreamResource> exportToPDFResumido(@PathVariable Long orgId) throws IOException {
		
		List<IdeasProyectos> ideasOrg = ideasProyectosService.findAll(); 
		List<IdeasProyectos> ideasFin = new ArrayList();
		ByteArrayInputStream bis;
		
		if(orgId == 0) { // todas las organizaciones
			bis = createReport(ideasOrg);
		}else {
			for(IdeasProyectos ide: ideasOrg) {
				if(ide.getDependenciaId() == orgId) {
					ideasFin.add(ide);
				}				
			}
			bis = createReport(ideasFin);
		}
		
	   

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "inline; filename=ideas.pdf");


	    return ResponseEntity
	            .ok()
	            .headers(headers)
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(new InputStreamResource(bis));
	  }
	
	
	public  ByteArrayInputStream createReport(List<IdeasProyectos> ideas) {

		
	    Document document = new Document();
	    ByteArrayOutputStream out = new ByteArrayOutputStream();

	    try {

	    	PdfWriter.getInstance(document, out);
	        document.open();		
    		
    		document.addTitle("Reporte ideas resumido");
    		Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
    		Paragraph p=new Paragraph("Listado de ideas", font);
    		p.setAlignment(Element.ALIGN_CENTER);
    		document.add(p);
    		document.add( Chunk.NEWLINE );
    		
    		PdfPTable table = new PdfPTable(5);

    		addTableHeader(table);  
    		
    	    		
    		for(IdeasProyectos ide: ideas) {
    			addRows(table, ide);
    		}
    		
    		
    		document.add(table);            
            document.close();
	    	
	    

	    } catch (DocumentException ex) {

	        System.out.println("error");
	    }

	    return new ByteArrayInputStream(out.toByteArray());
	}
	
	
	public ByteArrayInputStream createReportDetalle(IdeasProyectos idea) {

		
	    Document document = new Document();
	    ByteArrayOutputStream out = new ByteArrayOutputStream();

	    try {

	    	PdfWriter.getInstance(document, out);
	        document.open();		
    		
    		document.addTitle("Reporte de ideas detalle");
    		Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
    		Paragraph p=new Paragraph("Idea:"+ idea.getNombreIdea(), font);
    		p.setAlignment(Element.ALIGN_CENTER);
    		document.add(p);
    		document.add( Chunk.NEWLINE );
    		
    		PdfPTable table = new PdfPTable(7);

    		addTableHeaderDetalle(table);
    		addRowsDetalle(table, idea);
    		
    		document.add(table);    
    		 
    		document.add( Chunk.NEWLINE );
    		
    		PdfPTable table2 = new PdfPTable(7);

    		addTableHeaderDetalle2(table2);
    		addRowsDetalle2(table2, idea);
    		
    		document.add(table2);  
    		
    		document.add( Chunk.NEWLINE );
    		
    		PdfPTable table3 = new PdfPTable(7);

    		addTableHeaderDetalle3(table3);
    		addRowsDetalle3(table3, idea);
    		
    		document.add(table3);  
    		
    		
    		
            document.close();
	    	
	    

	    } catch (DocumentException ex) {

	        System.out.println("error");
	    }

	    return new ByteArrayInputStream(out.toByteArray());
	}

	

	public void addTableHeader(PdfPTable table) {
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
	    Stream.of("Año", "Idea Proyecto", "Dependencia", "Tipo Propuesta", "Estatus")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.BLUE);
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnTitle, font));
	        table.addCell(header);
	    });
	}
	
	public void addTableHeaderDetalle(PdfPTable table) {
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
	    Stream.of("Nombre Idea", "Descripcion Idea", "Tipo Propuesta", "Impacto", "Problematica", "Poblacion", "Focalizacion")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.BLUE);
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnTitle, font));
	        table.addCell(header);
	    });
	}
	
	public void addTableHeaderDetalle2(PdfPTable table) {
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
	    Stream.of("Alineacion Plan", "Financiacion", "Dependencia Participantes", "Dependencia Persona", "Persona Encargada", "Datos Contacto", "Dependencia")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.BLUE);
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnTitle, font));
	        table.addCell(header);
	    });
	}

	public void addTableHeaderDetalle3(PdfPTable table) {
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);
	    Stream.of("Proyectos Ejecutados", "Capacidad Tecnica", "Fecha Idea", "Año Formulacion", "Estatus", "Historico", "Observaciones")
	      .forEach(columnTitle -> {
	        PdfPCell header = new PdfPCell();
	        header.setBackgroundColor(BaseColor.BLUE);
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnTitle, font));
	        table.addCell(header);
	    });
	}
	


	public void addRows(PdfPTable table, IdeasProyectos idea) {
		
		OrganizacionesStrategos org = organizacionService.findById(idea.getDependenciaId());
		TiposPropuestas tip = propuestasService.findById(idea.getTipoPropuestaId());
		EstatusIdeas est = estatusService.findById(idea.getEstatusIdeaId());
			
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
	    Stream.of(idea.getAnioFormulacion(),idea.getNombreIdea(), org.getNombre(), tip.getTipoPropuesta(), est.getEstatus())
	      .forEach(columnas -> {
	        PdfPCell header = new PdfPCell();
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnas, font));
	        table.addCell(header);
	    });
		
	}
	
	public void addRowsDetalle(PdfPTable table, IdeasProyectos idea) {
		
		TiposPropuestas tip = propuestasService.findById(idea.getTipoPropuestaId());
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
	    Stream.of(idea.getNombreIdea() , idea.getDescripcionIdea() ,tip.getTipoPropuesta() , idea.getImpacto() ,idea.getProblematica(), idea.getPoblacion(), idea.getFocalizacion())
	      .forEach(columnas -> {
	        PdfPCell header = new PdfPCell();
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnas, font));
	        table.addCell(header);
	    });
		
	}
	
	public void addRowsDetalle2(PdfPTable table, IdeasProyectos idea) {
		
		TiposObjetivos tip = objetivosService.findById(idea.getTipoObjetivoId());
		OrganizacionesStrategos org = organizacionService.findById(idea.getDependenciaId());
	
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
	    Stream.of(tip.getDescripcionObjetivo() ,idea.getFinanciacion(), idea.getDependenciasParticipantes(), idea.getDependenciaPersona(), idea.getPersonaEncargada() ,idea.getPersonaContactoDatos()
	    		,org.getNombre())
	      .forEach(columnas -> {
	        PdfPCell header = new PdfPCell();
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnas, font));
	        table.addCell(header);
	    });
		
	}

	public void addRowsDetalle3(PdfPTable table, IdeasProyectos idea) {
		
		EstatusIdeas est = estatusService.findById(idea.getEstatusIdeaId());
		SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = formateadorFecha.format(idea.getFechaIdea());
		String historico ="";
		if(idea.getHistorico()) {
			historico="Si";
		}else {
			historico="No";
		}
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
	    Stream.of(idea.getProyectosEjecutados(), idea.getCapacidadTecnica(), fecha, idea.getAnioFormulacion(), est.getEstatus(), historico, idea.getObservaciones())
	      .forEach(columnas -> {
	        PdfPCell header = new PdfPCell();
	        header.setBorderWidth(1);
	        header.setPhrase(new Phrase(columnas, font));
	        table.addCell(header);
	    });
		
	}
	
}
