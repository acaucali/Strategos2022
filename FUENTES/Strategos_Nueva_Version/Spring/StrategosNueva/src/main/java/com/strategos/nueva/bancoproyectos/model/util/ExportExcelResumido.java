package com.strategos.nueva.bancoproyectos.model.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

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

public class ExportExcelResumido {
	
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
	
	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<IdeasProyectos> listProyectos;
    private IdeasProyectos ideaProyecto;
     
    public ExportExcelResumido(List<IdeasProyectos> listProyectos) {
        this.listProyectos = listProyectos;
        workbook = new XSSFWorkbook();
    }
 
    public ExportExcelResumido(IdeasProyectos ideaProyecto) {
        this.ideaProyecto = ideaProyecto;
        workbook = new XSSFWorkbook();
    }
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Ideas");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Año", style);      
        createCell(row, 1, "Idea Proyecto", style);       
        createCell(row, 2, "Dependencia", style);    
        createCell(row, 3, "Tipo Propuesta", style);
        createCell(row, 4, "Estatus", style);
         
    }
     
    private void writeHeaderLineDetalle() {
        sheet = workbook.createSheet("Ideas");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Nombre Idea", style);      
        createCell(row, 1, "Descripcion Idea", style);       
        createCell(row, 2, "Tipo Propuesta", style);    
        createCell(row, 3, "Impacto", style);
        createCell(row, 4, "Problematica", style);
        createCell(row, 5, "Poblacion", style);
        createCell(row, 6, "Focalizacion", style);
        createCell(row, 7, "Alineacion Plan", style);
        createCell(row, 8, "Financiacion", style);
        createCell(row, 9, "Dependencia Participantes", style);
        createCell(row, 10, "Dependencia Persona", style);
        createCell(row, 11, "Persona Encargada", style);
        createCell(row, 12, "Datos Contacto", style);
        createCell(row, 13, "Dependencia", style);
        createCell(row, 14, "Proyectos Ejecutados", style);
        createCell(row, 15, "Capacidad Tecnica", style);
        createCell(row, 16, "Fecha Idea", style);
        createCell(row, 17, "Año Formulacion", style);
        createCell(row, 18, "Estatus", style);
        createCell(row, 19, "Historico", style);
        createCell(row, 20, "Observaciones", style);
         
    }
    
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLinesDetalle() {
    	
    	
    	
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            
            Long orgId = ideaProyecto.getDependenciaId();
            Long proId = ideaProyecto.getTipoPropuestaId();
            Long estId = ideaProyecto.getEstatusIdeaId();
            Long objId = ideaProyecto.getTipoObjetivoId();
            
            OrganizacionesStrategos org = organizacionService.findById(orgId);
    		TiposPropuestas tip = propuestasService.findById(proId);
    		EstatusIdeas est = estatusService.findById(estId);
    		TiposObjetivos tipo = objetivosService.findById(objId);
    		SimpleDateFormat formateadorFecha = new SimpleDateFormat("dd/MM/yyyy");
    		String fecha = formateadorFecha.format(ideaProyecto.getFechaIdea());
    		String historico ="";
    		if(ideaProyecto.getHistorico()) {
    			historico="Si";
    		}else {
    			historico="No";
    		}
            
            createCell(row, columnCount++, ideaProyecto.getNombreIdea(), style);
            createCell(row, columnCount++, ideaProyecto.getDescripcionIdea(), style);
            createCell(row, columnCount++, tip.getTipoPropuesta(), style);
            createCell(row, columnCount++, ideaProyecto.getImpacto(), style);
            createCell(row, columnCount++, ideaProyecto.getProblematica(), style);
            createCell(row, columnCount++, ideaProyecto.getPoblacion(), style);
            createCell(row, columnCount++, ideaProyecto.getFocalizacion(), style);
            createCell(row, columnCount++, tipo.getDescripcionObjetivo(), style);
            createCell(row, columnCount++, ideaProyecto.getFinanciacion(), style);
            createCell(row, columnCount++, ideaProyecto.getDependenciasParticipantes(), style);
            createCell(row, columnCount++, ideaProyecto.getDependenciaPersona(), style);
            createCell(row, columnCount++, ideaProyecto.getPersonaEncargada(), style);
            createCell(row, columnCount++, ideaProyecto.getPersonaContactoDatos(), style);
            createCell(row, columnCount++, ideaProyecto.getProyectosEjecutados(), style);
            createCell(row, columnCount++, ideaProyecto.getCapacidadTecnica(), style);
            createCell(row, columnCount++, fecha, style);
            createCell(row, columnCount++, ideaProyecto.getAnioFormulacion(), style);
            createCell(row, columnCount++, est.getEstatus(), style);
            createCell(row, columnCount++, historico, style);
            createCell(row, columnCount++, ideaProyecto.getObservaciones(), style);
             
       
    }
    
    private void writeDataLines() {
    	
    	
    	
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        for (IdeasProyectos idea : listProyectos) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            
            OrganizacionesStrategos org = organizacionService.findById(idea.getDependenciaId());
    		TiposPropuestas tip = propuestasService.findById(idea.getTipoPropuestaId());
    		EstatusIdeas est = estatusService.findById(idea.getEstatusIdeaId());
            
            createCell(row, columnCount++, idea.getAnioFormulacion(), style);
            createCell(row, columnCount++, idea.getNombreIdea(), style);
            createCell(row, columnCount++, org.getNombre(), style);
            createCell(row, columnCount++, tip.getTipoPropuesta(), style);
            createCell(row, columnCount++, est.getEstatus(), style);
             
        }
    }
     
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
    
    public void exportDetalle(HttpServletResponse response) throws IOException {
        writeHeaderLineDetalle();
        writeDataLinesDetalle();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
	
}
