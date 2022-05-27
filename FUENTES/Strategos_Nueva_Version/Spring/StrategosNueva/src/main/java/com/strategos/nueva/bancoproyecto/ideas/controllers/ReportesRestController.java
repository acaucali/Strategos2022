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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
	
		
	 @GetMapping("/idea/excel/{orgId}") //ideas detalle
		public @ResponseBody ResponseEntity<InputStreamResource> exportToXls(@PathVariable Long orgId) throws IOException {
			
		 	List<IdeasProyectos> ideasOrg = ideasProyectosService.findAll(); 
			List<IdeasProyectos> ideasFin = new ArrayList();
			ByteArrayInputStream bis;
			
			 if(orgId == 0) {
				 bis = createReportXls(ideasOrg);
			 }else {
				 for(IdeasProyectos ide: ideasOrg) {
					if(ide.getDependenciaId() == orgId) {
						ideasFin.add(ide);
					}				
				 }
				 bis = createReportXls(ideasFin);
			 }
			  

		    HttpHeaders headers = new HttpHeaders();
		    headers.add("Content-Disposition", "attachment; filename=ideasDetalle.xls");

		    return ResponseEntity
		            .ok()
		            .headers(headers)
		            .body(new InputStreamResource(bis));
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
	
	public  ByteArrayInputStream createReportXls(List<IdeasProyectos> ideas) throws IOException{

		int columns = 0;
		
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			
			Sheet sheet = workbook.createSheet("Ideas");
			
			org.apache.poi.ss.usermodel.Font headerFont = (org.apache.poi.ss.usermodel.Font) workbook.createFont();
            ((org.apache.poi.ss.usermodel.Font) headerFont).setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
            headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            // Header Row
            org.apache.poi.ss.usermodel.Font titleFont = (org.apache.poi.ss.usermodel.Font) workbook.createFont();
            ((org.apache.poi.ss.usermodel.Font) titleFont).setBold(true);
            
            CellStyle titleCellStyle = workbook.createCellStyle();
            titleCellStyle.setFont(titleFont);
            
            Row titleRow = sheet.createRow(0);
            Cell celdaTitle = titleRow.createCell(0);
            celdaTitle.setCellValue("Listado de Ideas");
            celdaTitle.setCellStyle(titleCellStyle);
            
            Row headerRow = sheet.createRow(2);
            
            int col = 0;
            Cell cell;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Nombre Idea");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Descripcion Idea");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Tipo Propuesta");
            cell.setCellStyle(headerCellStyle);
            col++; 
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Impacto");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Problematica");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Poblacion");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Focalizacion");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Alineacion Plan");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Financiacion");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Dependencia Participantes");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Dependencia Persona");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Persona Encargada");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Datos Contacto");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Dependencia");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Proyectos Ejecutados");
            cell.setCellStyle(headerCellStyle);
            col++;
                        
            cell = headerRow.createCell(col);
            cell.setCellValue("Capacidad Tecnica");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Fecha Idea");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Año Formulacion");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Estatus");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Historico");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Observaciones");
            cell.setCellStyle(headerCellStyle);
            col++;
        
            int row=3;
            int colu = 0;
            
            
            
            for(IdeasProyectos ide: ideas) {
            	
            	Row headerRow2 = sheet.createRow(row);
            	
            	Cell celda = headerRow2.createCell(0);
            	celda.setCellValue(ide.getNombreIdea());
            	
            	celda = headerRow2.createCell(1);
            	celda.setCellValue(ide.getDescripcionIdea());
            	
            	if(ide.getTipoPropuestaId() != null) {
            		TiposPropuestas tipo = propuestasService.findById(ide.getTipoPropuestaId());
            		celda = headerRow2.createCell(2);
                	celda.setCellValue(tipo.getTipoPropuesta());
            	}else {
            		celda = headerRow2.createCell(2);
                	celda.setCellValue("");
            	}            	
            	
            	celda = headerRow2.createCell(3);
            	celda.setCellValue(ide.getImpacto());
            	
            	celda = headerRow2.createCell(4);
            	celda.setCellValue(ide.getProblematica());
            	
            	celda = headerRow2.createCell(5);
            	celda.setCellValue(ide.getPoblacion());
            	
            	celda = headerRow2.createCell(6);
            	celda.setCellValue(ide.getFocalizacion());
            	
            	if(ide.getTipoObjetivoId() != null) {
            		TiposObjetivos tip = objetivosService.findById(ide.getTipoObjetivoId());
            		celda = headerRow2.createCell(7);
                	celda.setCellValue(tip.getDescripcionObjetivo());
            	}else {
            		celda = headerRow2.createCell(7);
                	celda.setCellValue("");
            	}
            	
            	
            	celda = headerRow2.createCell(8);
            	celda.setCellValue(ide.getFinanciacion());
            	
            	celda = headerRow2.createCell(9);
            	celda.setCellValue(ide.getDependenciasParticipantes());
            	
            	if(ide.getDependenciaPersona() != null) {
            		OrganizacionesStrategos org = organizacionService.findById(ide.getDependenciaPersona());
            		celda = headerRow2.createCell(10);
                	celda.setCellValue(org.getNombre());
        
            	}else {
            		celda = headerRow2.createCell(10);
                	celda.setCellValue("");
            	}
            	
            	celda = headerRow2.createCell(11);
            	celda.setCellValue(ide.getPersonaEncargada());
            	
            	celda = headerRow2.createCell(12);
            	celda.setCellValue(ide.getPersonaContactoDatos());
            	
            	if(ide.getDependenciaId() != null) {
            		OrganizacionesStrategos org = organizacionService.findById(ide.getDependenciaId());
            		celda = headerRow2.createCell(13);
                	celda.setCellValue(org.getNombre());
            	}else {
            		celda = headerRow2.createCell(13);
                	celda.setCellValue("");
            	}
            	
            	
            	celda = headerRow2.createCell(14);
            	celda.setCellValue(ide.getProyectosEjecutados());
            	
            	if(ide.getCapacidadTecnica() != null) {
            		celda = headerRow2.createCell(15);
                	celda.setCellValue(ide.getCapacidadTecnica());
            	}else {
            		celda = headerRow2.createCell(15);
                	celda.setCellValue("");
            	}
            	
            	
            	if(ide.getFechaIdea() != null) {
            		celda = headerRow2.createCell(16);
                	celda.setCellValue(ide.getFechaIdea().toString());
            	}else {
            		celda = headerRow2.createCell(16);
                	celda.setCellValue("");
            	}
            	
            	
            	celda = headerRow2.createCell(17);
            	celda.setCellValue(ide.getAnioFormulacion());
            	
            	if(ide.getEstatusIdeaId() != null) {
            		celda = headerRow2.createCell(18);
            		EstatusIdeas est = estatusService.findById(ide.getEstatusIdeaId());
                	celda.setCellValue(est.getEstatus());
            	}else {
            		celda = headerRow2.createCell(18);
                	celda.setCellValue("");           		
            	}
            	
            	
            	if(ide.getHistorico() != null) {
            		celda = headerRow2.createCell(19);
            		if(ide.getHistorico()) {
            			celda.setCellValue("Si");
            		}else {
            			celda.setCellValue("No");
            		}
            	}else {
            		celda = headerRow2.createCell(19);
                	celda.setCellValue("");
            	}
            	
            	if(ide.getObservaciones() != null) {
            		celda = headerRow2.createCell(20);
                	celda.setCellValue(ide.getObservaciones());
            	}else {
            		celda = headerRow2.createCell(20);
                	celda.setCellValue("");
            	}
            	
            	
            	row++;
			}
            
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
		}
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
	    Stream.of(tip.getDescripcionObjetivo() ,idea.getFinanciacion(), idea.getDependenciasParticipantes(), "", idea.getPersonaEncargada() ,idea.getPersonaContactoDatos()
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
		if(idea.getHistorico() != null && idea.getHistorico()) {
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
