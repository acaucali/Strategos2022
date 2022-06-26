package com.strategos.nueva.bancoproyecto.ideas.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeas;
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasEvaluadas;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.TiposObjetivos;
import com.strategos.nueva.bancoproyecto.ideas.model.TiposPropuestas;
import com.strategos.nueva.bancoproyecto.ideas.service.EstatusIdeaService;
import com.strategos.nueva.bancoproyecto.ideas.service.EvaluacionIdeasDetalleService;
import com.strategos.nueva.bancoproyecto.ideas.service.EvaluacionIdeasService;
import com.strategos.nueva.bancoproyecto.ideas.service.IdeasEvaluadasService;
import com.strategos.nueva.bancoproyecto.ideas.service.IdeasProyectosService;
import com.strategos.nueva.bancoproyecto.ideas.service.TiposObjetivosService;
import com.strategos.nueva.bancoproyecto.ideas.service.TiposPropuestasService;
import com.strategos.nueva.bancoproyecto.strategos.model.OrganizacionesStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;
import com.strategos.nueva.bancoproyectos.model.util.DatoIdea;


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
	@Autowired
	private EvaluacionIdeasDetalleService evaluacionDetalleService;
	@Autowired
	private IdeasEvaluadasService ideasEvaluadasService;
	@Autowired
	private EvaluacionIdeasService evaluacionService;
	
	
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
				 
				 ideasFin = ideasProyectosService.findAllByDependenciaId(orgId); 
				 
				 bis = createReportXls(ideasFin);
			 }
			  

		    HttpHeaders headers = new HttpHeaders();
		    headers.add("Content-Disposition", "attachment; filename=ideasDetalle.xls");

		    return ResponseEntity
		            .ok()
		            .headers(headers)
		            .body(new InputStreamResource(bis));
	}
	
	@GetMapping("/idea/evaluaciondetalle/excel/{evaluacionId}") //ideas detalle
	public @ResponseBody ResponseEntity<InputStreamResource> exportXlsDetalle(@PathVariable Long evaluacionId) throws IOException {
			
			List<EvaluacionIdeasDetalle> evaluacionesDetalle = evaluacionDetalleService.findAllByEvaluacionId(evaluacionId);
			List<IdeasEvaluadas> ideasEvaluadas = new ArrayList<IdeasEvaluadas>();			
			ideasEvaluadas = ideasEvaluadasService.findAllByEvaluacionId(evaluacionId);
			ByteArrayInputStream bis;
					 
			bis = createReportEvaluacionXls(ideasEvaluadas, evaluacionesDetalle, evaluacionId);			

		    HttpHeaders headers = new HttpHeaders();
		    headers.add("Content-Disposition", "attachment; filename=evalucacionIdeasDetalle.xls");

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
			ideasFin = ideasProyectosService.findAllByDependenciaId(orgId); 
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
    		Paragraph p=new Paragraph("Listado resumido de Ideas de Proyecto", font);
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
	
	public  ByteArrayInputStream createReportEvaluacionXls(List<IdeasEvaluadas> ideasEvaluadas, List<EvaluacionIdeasDetalle> evaluacionesDetalle, Long evaluacionId) throws IOException{

		int columns = 0;
		
		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			
			Sheet sheet = workbook.createSheet("Evaluación Ideas Detalle");
			
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
            celdaTitle.setCellValue("Listado Detallado de Evaluaciones");
            celdaTitle.setCellStyle(titleCellStyle);
            
            
            EvaluacionIdeas evaluacion = evaluacionService.findById(evaluacionId);
            
            Row titleRow2 = sheet.createRow(2);
            Cell celdaTitle2 = titleRow2.createCell(0);
            celdaTitle2.setCellValue("Fecha Evaluación");
            celdaTitle2.setCellStyle(titleCellStyle);
            
            SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");
            Cell celdaTitle21 = titleRow2.createCell(1);
            celdaTitle21.setCellValue(formateadorFecha.format(evaluacion.getFechaEvaluacion()));
            
            Row titleRow3 = sheet.createRow(3);
            Cell celdaTitle3 = titleRow3.createCell(0);
            celdaTitle3.setCellValue("Descripción de evaluación");
            celdaTitle3.setCellStyle(titleCellStyle);
            
            Cell celdaTitle31 = titleRow3.createCell(1);
            celdaTitle31.setCellValue(evaluacion.getObservacion());

            
            
            Row headerRow = sheet.createRow(4);
            
            int col = 0;
            Cell cell;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Nombre Idea");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Dependencia");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            //ciclo que busca los encabezados
            for(IdeasEvaluadas ide: ideasEvaluadas) {
				
				IdeasProyectos idea = ideasProyectosService.findById(ide.getIdeaId());
					
				for(EvaluacionIdeasDetalle eva: evaluacionesDetalle) {
					if(eva.getIdeaId().equals(ide.getIdeaId())) {
						
						cell = headerRow.createCell(col);
			            cell.setCellValue(eva.getCriterio()+" ("+eva.getPeso()+" %)");
			            cell.setCellStyle(headerCellStyle);
			            col++;
					}
				}
												
				break;				
			}	 
                      
            cell = headerRow.createCell(col);
            cell.setCellValue("Puntaje Total");
            cell.setCellStyle(headerCellStyle);
            col++;
        
            int row=6;
                        
 
			List<Long> ids = new ArrayList<Long>();			
			List <String> ideas =ordenarIdeas(ideasEvaluadas);
									
			for(String idea: ideas) {
				String carId = idea.substring(idea.indexOf("-") + 1, idea.length());				
				ids.add(new Long(carId));								
			}
			
			for(Long id: ids) {
				
				int colu = 0;
    			
            	Row headerRow2 = sheet.createRow(row);
				
            	IdeasProyectos idea = ideasProyectosService.findById(id);
				
				Cell celda = headerRow2.createCell(colu);
            	celda.setCellValue(idea.getNombreIdea());            	
            	colu++;
            	
            	celda = headerRow2.createCell(colu);
            	celda.setCellValue(idea.getOrganizacion());
            	colu++;
            	          	
					
				for(EvaluacionIdeasDetalle eva: evaluacionesDetalle) {
					if(eva.getIdeaId().equals(id)) {
						
						cell = headerRow2.createCell(colu);
			            cell.setCellValue(eva.getValorEvaluado());
			            colu++;
					}
				}
				
				celda = headerRow2.createCell(colu);
            	celda.setCellValue(idea.getValorUltimaEvaluacion());
            	colu++;
												
				row++;					
				
			}
            
                                              
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
		}
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
            cell.setCellValue("Nombre Iniciativa / Propuesta");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Descripción Propuesta");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Tipo Propuesta");
            cell.setCellStyle(headerCellStyle);
            col++; 
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Impacto Estimado");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Problemática / Necesidad");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Poblacion");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Focalización de la Población");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Alineacion Plan");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Posibles Fuentes Financiación");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Dependencias Participantes");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Profesional Responsable");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Teléfono Contacto");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Correo electronico");
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
            cell.setCellValue("Fecha Radicación");
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
            cell.setCellValue("Fecha Estatus");
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
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Objetivo General");
            cell.setCellStyle(headerCellStyle);
            col++;
            
            cell = headerRow.createCell(col);
            cell.setCellValue("Duración Total");
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
                            	
            	celda = headerRow2.createCell(10);
            	celda.setCellValue(ide.getPersonaEncargada());
            	
            	celda = headerRow2.createCell(11);
            	celda.setCellValue(ide.getContactoTelefono());
            	
            	celda = headerRow2.createCell(12);
            	celda.setCellValue(ide.getContactoEmail());
            	
            	            	
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
            	
            	
            	if(ide.getFechaRadicacion() != null) {
            		celda = headerRow2.createCell(16);
                	celda.setCellValue(ide.getFechaRadicacion().toString());
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
            	
            	if(ide.getFechaEstatus() != null) {
            		celda = headerRow2.createCell(19);
                	celda.setCellValue(ide.getFechaEstatus().toString());
            	}else {
            		celda = headerRow2.createCell(19);
                	celda.setCellValue("");
            	}
            	
            	
            	if(ide.getHistorico() != null) {
            		celda = headerRow2.createCell(20);
            		if(ide.getHistorico()) {
            			celda.setCellValue("Si");
            		}else {
            			celda.setCellValue("No");
            		}
            	}else {
            		celda = headerRow2.createCell(20);
                	celda.setCellValue("");
            	}
            	
            	if(ide.getObservaciones() != null) {
            		celda = headerRow2.createCell(21);
                	celda.setCellValue(ide.getObservaciones());
            	}else {
            		celda = headerRow2.createCell(21);
                	celda.setCellValue("");
            	}
            	
            	if(ide.getObjetivoGeneral() != null) {
            		celda = headerRow2.createCell(22);
                	celda.setCellValue(ide.getObjetivoGeneral());
            	}else {
            		celda = headerRow2.createCell(22);
                	celda.setCellValue("");
            	}
            	
            	if(ide.getDuracionTotal() != null) {
            		celda = headerRow2.createCell(23);
                	celda.setCellValue(ide.getDuracionTotal());
            	}else {
            		celda = headerRow2.createCell(23);
                	celda.setCellValue("");
            	}
            	
            	
            	row++;
			}
            
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
		}
	}
	
	
	public ByteArrayInputStream createReportDetalle(IdeasProyectos idea) {

		TiposPropuestas tip = propuestasService.findById(idea.getTipoPropuestaId());
		TiposObjetivos tipo = objetivosService.findById(idea.getTipoObjetivoId());
		OrganizacionesStrategos org = organizacionService.findById(idea.getDependenciaId());

		EstatusIdeas est = estatusService.findById(idea.getEstatusIdeaId());
		SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");
		String fecha = formateadorFecha.format(idea.getFechaRadicacion());
		String fechaEstatus = formateadorFecha.format(idea.getFechaEstatus());
		String historico ="";
		if(idea.getHistorico() != null && idea.getHistorico()) {
			historico="Si";
		}else {
			historico="No";
		}
		
	    Document document = new Document();
	    ByteArrayOutputStream out = new ByteArrayOutputStream();

	    try {

	    	PdfWriter.getInstance(document, out);
	        document.open();		
    		
    		document.addTitle("Reporte de ideas detalle");
    		Font font = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
    		Paragraph p=new Paragraph("Nombre Iniciativa / Propuesta: "+ idea.getNombreIdea(), font);
    		p.setAlignment(Element.ALIGN_CENTER);
    		document.add(p);
    		document.add( Chunk.NEWLINE );
    		
    		PdfPTable table = new PdfPTable(2);
    		
    		Font fontCelda = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
    		
    		PdfPCell celda = new PdfPCell();
    		
    		celda.setPhrase(new Phrase("Descripción Propuesta", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(idea.getDescripcionIdea());
    		
    		celda.setPhrase(new Phrase("Tipo Propuesta", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(tip.getTipoPropuesta());
    		
    		celda.setPhrase(new Phrase("Impacto Estimado", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(idea.getImpacto());
    		
    		celda.setPhrase(new Phrase("Problemática / Necesidad", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(idea.getProblematica());
    		
    		celda.setPhrase(new Phrase("Poblacion", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(idea.getPoblacion());
    		
    		celda.setPhrase(new Phrase("Focalización de la Población", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(idea.getFocalizacion());
    		
    		celda.setPhrase(new Phrase("Alineacion Plan", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(tipo.getDescripcionObjetivo());
    		
    		celda.setPhrase(new Phrase("Posibles Fuentes Financiación", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(idea.getFinanciacion());
    		
    		celda.setPhrase(new Phrase("Dependencia Participantes", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(idea.getDependenciasParticipantes());
    		
    		celda.setPhrase(new Phrase("Profesional Responsable", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(idea.getPersonaEncargada());
    		
    		celda.setPhrase(new Phrase("Teléfono Contacto", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(idea.getContactoTelefono());
    		
    		celda.setPhrase(new Phrase("Correo electronico", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(idea.getContactoEmail());
    		
    		celda.setPhrase(new Phrase("Dependencia", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(org.getNombre());
    		
    		celda.setPhrase(new Phrase("Proyectos Ejecutados", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(idea.getProyectosEjecutados());
    		
    		celda.setPhrase(new Phrase("Capacidad Tecnica", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(idea.getCapacidadTecnica());
    		
    		celda.setPhrase(new Phrase("Fecha Radicación", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(fecha);
    		
    		celda.setPhrase(new Phrase("Año Formulacion", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(idea.getAnioFormulacion());
    		
    		celda.setPhrase(new Phrase("Estatus", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(est.getEstatus());
    		
    		celda.setPhrase(new Phrase("Fecha Estatus", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(fechaEstatus);
    		
    		celda.setPhrase(new Phrase("Historico", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(historico);
    		
    		celda.setPhrase(new Phrase("Observaciones", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(idea.getObservaciones());
    		
    		celda.setPhrase(new Phrase("Objetivo General", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(idea.getObjetivoGeneral());
    		
    		celda.setPhrase(new Phrase("Duración Total", fontCelda));   		
    		table.addCell(celda);
    		table.addCell(idea.getDuracionTotal());
    		
    		document.add(table);        		 
    		document.add( Chunk.NEWLINE );
    		   		    		
    		
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
	
	public List<String> ordenarIdeas(List<IdeasEvaluadas> ideasEvaluadas){
		List<String> puntajes = new ArrayList<>();
		
		for(IdeasEvaluadas ide: ideasEvaluadas) {
			IdeasProyectos idea = ideasProyectosService.findById(ide.getIdeaId());
			if(idea.getValorUltimaEvaluacion() != null) {
				puntajes.add(idea.getValorUltimaEvaluacion().toString()+"-"+idea.getIdeaId());
			}else {
				puntajes.add("0"+"-"+idea.getIdeaId());
			}
			
		}		
		
		System.out.println("Before Sorting: "+ puntajes);   
		
		Collections.sort(puntajes, Collections.reverseOrder()); 
				
		System.out.println("After Sorting: "+ puntajes);   
		
		return puntajes;
	}
	
	
}
