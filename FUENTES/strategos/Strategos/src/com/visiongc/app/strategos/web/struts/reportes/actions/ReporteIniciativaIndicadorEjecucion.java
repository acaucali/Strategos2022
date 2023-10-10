package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.IndicadorIniciativa;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.servicio.strategos.model.util.Frecuencia;

public class ReporteIniciativaIndicadorEjecucion extends VgcAction {

	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
		navBar.agregarUrl(url, nombre);
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		String alcance = (request.getParameter("alcance"));
		String iniciativaId = (request.getParameter("iniciativaId"));
		String tipo = (request.getParameter("tipo"));
		String estatus = (request.getParameter("estatus"));
		String ano = (request.getParameter("ano"));
		int estatusId = Integer.parseInt(estatus);
		String todos = (request.getParameter("todos"));
		String mesIni =(request.getParameter("mesIni"));
		String mesFin =(request.getParameter("mesFin"));
		String anio = (request.getParameter("anoInicial"));
		//String acumular = (request.getParameter("acumularTrimestre"));
		String avance = (request.getParameter("avance"));

		Calendar fecha = Calendar.getInstance();
        int anoTemp = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;

		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;

		StrategosIniciativasService iniciativaservice = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
		StrategosPryActividadesService actividadservice = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();

		List<OrganizacionStrategos> organizaciones = organizacionservice.getOrganizaciones(0, 0, "organizacionId", "ASC", true, filtros).getLista();

		
		if (request.getParameter("alcance").equals("1")) {// iniciativa seleccionada
			
			int x=1;
			int numeroCelda=3;


			String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";
			Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();

			
			Iniciativa iniciativa = (Iniciativa) iniciativaservice.load(Iniciativa.class, new Long(iniciativaId));
			
			if(iniciativa != null) {
								
				// buscar indicadores
				
				List<IndicadorIniciativa> indicadores = iniciativaservice.getIndicadoresIniciativa(iniciativa.getIniciativaId());
				List<Long> indicadoresFisico = new ArrayList<Long>();
				List<Long> indicadoresPresupuesto = new ArrayList<Long>();
				List<Long> indicadoresAvance = new ArrayList<Long>();
					
				
				for (Iterator<IndicadorIniciativa> i = indicadores.iterator(); i.hasNext();) {
					IndicadorIniciativa ind = (IndicadorIniciativa) i.next();
					
					if(ind.getTipo().equals(TipoFuncionIndicador.getTipoFuncionPresupuesto())) {
						indicadoresPresupuesto.add(ind.getPk().getIndicadorId());
					}else {
						indicadoresFisico.add(ind.getPk().getIndicadorId());
					}
													
				}
				
				
				HSSFWorkbook workbook = new HSSFWorkbook();
		        HSSFSheet sheet = workbook.createSheet();
		        workbook.setSheetName(0, "Hoja excel");


				CellStyle headerStyle = workbook.createCellStyle();
		        Font font = workbook.createFont();
		        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		        headerStyle.setFont(font);
		        
		        Font font2 = workbook.createFont();
		        font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
		        font2.setColor(IndexedColors.WHITE.getIndex());

		        CellStyle style = workbook.createCellStyle();
		       
		        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		        
		        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        style.setFont(font);
		        style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		        style.setFillBackgroundColor(IndexedColors.PALE_BLUE.index);
		        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		        
		        CellStyle style1 = workbook.createCellStyle();
			       
		        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
		        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		        
		        style1.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        style1.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        style1.setRightBorderColor(IndexedColors.BLACK.getIndex());
		        style1.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        style1.setFillForegroundColor(IndexedColors.WHITE.index);
		        style1.setFillBackgroundColor(IndexedColors.WHITE.index);
		        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		        
		        CellStyle style3 = workbook.createCellStyle();
			       
		        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		        
		        style3.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		        style3.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		        style3.setRightBorderColor(IndexedColors.BLACK.getIndex());
		        style3.setTopBorderColor(IndexedColors.BLACK.getIndex());
		        style3.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
		        style3.setFillBackgroundColor(IndexedColors.ROYAL_BLUE.getIndex());
		        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		        style3.setFont(font2);
		        
		        CellStyle style2 = workbook.createCellStyle();
		        style2.setFont(font);
		        

		        HSSFRow headerRow = sheet.createRow(1);

		        String header = "Reporte Indicadores Iniciativa";
		        HSSFCell cell = headerRow.createCell(0);
		        cell.setCellStyle(style);
		        cell.setCellValue(header);


		        HSSFRow OrgRow = sheet.createRow(3);
		        HSSFCell cellOrg = OrgRow.createCell(0);
		        cellOrg.setCellStyle(style);
		        cellOrg.setCellValue("Organización");		        
		        
		    			
		        		        
		        OrganizacionStrategos org = (OrganizacionStrategos)organizacionservice.load(OrganizacionStrategos.class,  ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());

				if(org != null) {
					 String nombre = org.getNombre();
				     HSSFCell cellOrg1 = OrgRow.createCell(1);
				     cellOrg1.setCellStyle(style1);
				     cellOrg1.setCellValue(nombre);
				}

				HSSFRow IniRow = sheet.createRow(5);
				
				HSSFCell cellIni = IniRow.createCell(0);
		        cellIni.setCellStyle(style);
		        cellIni.setCellValue("Proyecto - Iniciativa");	
				
		        HSSFCell cellIni1 = IniRow.createCell(1);
		        cellIni1.setCellStyle(style1);
		        cellIni1.setCellValue(iniciativa.getNombre());	
		        
				
				
		        HSSFRow FechasRow = sheet.createRow(6);
		        
		        HSSFCell cellFec = FechasRow.createCell(0);
		        cellFec.setCellStyle(style);
		        cellFec.setCellValue("Año Formulación");	
				
		        HSSFCell cellFec1 = FechasRow.createCell(1);
		        cellFec1.setCellStyle(style1);
		        cellFec1.setCellValue(iniciativa.getAnioFormulacion());
		        
		     	        
		        
		        
		        HSSFRow ResRow = sheet.createRow(7);
		        
		        HSSFCell cellRes = ResRow.createCell(0);
		        cellRes.setCellStyle(style);
		        cellRes.setCellValue("Responsable");	
				
		        HSSFCell cellRes1 = ResRow.createCell(1);
		        cellRes1.setCellStyle(style1);
		        cellRes1.setCellValue(iniciativa.getResponsableProyecto());
		        
		
		        		        
		        HSSFRow IndRow = sheet.createRow(10); 
		        HSSFCell cellIndEj = IndRow.createCell(0);
		        cellIndEj.setCellStyle(style2);
		        cellIndEj.setCellValue("Ejecución Física");
		  
		        
		        // cabeceras de los periodos
		        
		        int mesI=new Integer(mesIni);
		        int mesF=new Integer(mesFin);
		        
		        
		        if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaTrimestral())) {
		        	
		        	 //cabeceras del mes
			        HSSFRow CabRow = sheet.createRow(11);
			        int numCab =2;
			        for(int c= mesI; c<=mesF; c++) {
			        	
			        	if(c == 3) {
			        		
			        		HSSFCell cellCab = CabRow.createCell(numCab);
				        	cellCab.setCellStyle(style3);
				        	cellCab.setCellValue(obtenerPeriodo(1));
		    	        	
				        	numCab = numCab +3;
			        		
			        	}else if(c == 6) {
			        		
			        		HSSFCell cellCab = CabRow.createCell(numCab);
				        	cellCab.setCellStyle(style3);
				        	cellCab.setCellValue(obtenerPeriodo(2));
		    	        	
				        	numCab = numCab +3;
			        		
			        	}else if(c == 9) {
			        		
			        		HSSFCell cellCab = CabRow.createCell(numCab);
				        	cellCab.setCellStyle(style3);
				        	cellCab.setCellValue(obtenerPeriodo(3));
		    	        	
				        	numCab = numCab +3;
			        		
			        	}else if(c == 12) {
			        		
			        		HSSFCell cellCab = CabRow.createCell(numCab);
				        	cellCab.setCellStyle(style3);
				        	cellCab.setCellValue(obtenerPeriodo(4));
		    	        	
				        	numCab = numCab +3;
			        		
			        	}
			        		        	
			        }
			        
			        HSSFRow CabPRow = sheet.createRow(12);
			        
			        HSSFCell cellCa1 = CabPRow.createCell(0);
			        cellCa1.setCellStyle(style);
			        cellCa1.setCellValue("Indicador");	
			        
			        HSSFCell cellCa2 = CabPRow.createCell(1);
			        cellCa2.setCellStyle(style);
			        cellCa2.setCellValue("Unidad");	
			        
			        int num =2;
			        for(int c= mesI; c<=mesF; c++) {
			        	
			        	if(c == 3) {
			        		
			        		HSSFCell cellCabp1 = CabPRow.createCell(num);
				        	cellCabp1.setCellStyle(style);
				        	cellCabp1.setCellValue("Ejecutado");
				        	num++;
				        	
				        	HSSFCell cellCabp2 = CabPRow.createCell(num);
				        	cellCabp2.setCellStyle(style);
				        	cellCabp2.setCellValue("Programado");
				        	num++;
				        	
				        	HSSFCell cellCabp3 = CabPRow.createCell(num);
				        	cellCabp3.setCellStyle(style);
				        	cellCabp3.setCellValue("% Eficacia");
				        	num++;
			        		
			        	}else if(c == 6) {
			        		
			        		HSSFCell cellCabp1 = CabPRow.createCell(num);
				        	cellCabp1.setCellStyle(style);
				        	cellCabp1.setCellValue("Ejecutado");
				        	num++;
				        	
				        	HSSFCell cellCabp2 = CabPRow.createCell(num);
				        	cellCabp2.setCellStyle(style);
				        	cellCabp2.setCellValue("Programado");
				        	num++;
				        	
				        	HSSFCell cellCabp3 = CabPRow.createCell(num);
				        	cellCabp3.setCellStyle(style);
				        	cellCabp3.setCellValue("% Eficacia");
				        	num++;
			        		
			        	}else if(c == 9) {
			        		
			        		HSSFCell cellCabp1 = CabPRow.createCell(num);
				        	cellCabp1.setCellStyle(style);
				        	cellCabp1.setCellValue("Ejecutado");
				        	num++;
				        	
				        	HSSFCell cellCabp2 = CabPRow.createCell(num);
				        	cellCabp2.setCellStyle(style);
				        	cellCabp2.setCellValue("Programado");
				        	num++;
				        	
				        	HSSFCell cellCabp3 = CabPRow.createCell(num);
				        	cellCabp3.setCellStyle(style);
				        	cellCabp3.setCellValue("% Eficacia");
				        	num++;
			        		
			        	}else if(c == 12) {
			        		
			        		HSSFCell cellCabp1 = CabPRow.createCell(num);
				        	cellCabp1.setCellStyle(style);
				        	cellCabp1.setCellValue("Ejecutado");
				        	num++;
				        	
				        	HSSFCell cellCabp2 = CabPRow.createCell(num);
				        	cellCabp2.setCellStyle(style);
				        	cellCabp2.setCellValue("Programado");
				        	num++;
				        	
				        	HSSFCell cellCabp3 = CabPRow.createCell(num);
				        	cellCabp3.setCellStyle(style);
				        	cellCabp3.setCellValue("% Eficacia");
				        	num++;
			        		
			        	}
			        	
			        	
			        
			        }
		        	
		        }else if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaMensual())) {
		        	
		        	 //cabeceras del mes
			        HSSFRow CabRow = sheet.createRow(11);
			        int numCab =2;
			        for(int c= mesI; c<=mesF; c++) {
			        	
			        	HSSFCell cellCab = CabRow.createCell(numCab);
			        	cellCab.setCellStyle(style3);
			        	cellCab.setCellValue(obtenerMes(c));
	    	        	
			        	numCab = numCab +3;		        	
			        }
			        
			        HSSFRow CabPRow = sheet.createRow(12);
			        
			        HSSFCell cellCa1 = CabPRow.createCell(0);
			        cellCa1.setCellStyle(style);
			        cellCa1.setCellValue("Indicador");	
			        
			        HSSFCell cellCa2 = CabPRow.createCell(1);
			        cellCa2.setCellStyle(style);
			        cellCa2.setCellValue("Unidad");	
			        
			        int num =2;
			        for(int c= mesI; c<=mesF; c++) {
			        	
			        	HSSFCell cellCabp1 = CabPRow.createCell(num);
			        	cellCabp1.setCellStyle(style);
			        	cellCabp1.setCellValue("Ejecutado");
			        	num++;
			        	
			        	HSSFCell cellCabp2 = CabPRow.createCell(num);
			        	cellCabp2.setCellStyle(style);
			        	cellCabp2.setCellValue("Programado");
			        	num++;
			        	
			        	HSSFCell cellCabp3 = CabPRow.createCell(num);
			        	cellCabp3.setCellStyle(style);
			        	cellCabp3.setCellValue("% Eficacia");
			        	num++;
			        
			        }
		        	
		        }
		        
		       
		        
		        // datos
		        
		        
		       	
		        
		        //datos indicadores ejecucion fisica
		        
		        int row = 13;		        
		        
		        for (Iterator<Long> iter = indicadoresFisico.iterator(); iter.hasNext();) {
		        	
		        	Long indicadorId = iter.next();
		        	int cel = 0;
		        	HSSFRow Row = sheet.createRow(row); 
		        	
		        	Indicador indicador = (Indicador) iniciativaservice.load(Indicador.class, new Long(indicadorId));
		        	
		        	if(indicador != null) {
		        		
		        		HSSFCell cellInd = Row.createCell(cel);
		        		cellInd.setCellStyle(style);
		        		cellInd.setCellValue(indicador.getNombre());
		        		cel++;
		        		
		        		String unidad ="";
		        		if(indicador.getUnidadId() != null) {
		        			UnidadMedida uni = (UnidadMedida) iniciativaservice.load(UnidadMedida.class, indicador.getUnidadId());
		        			if(uni != null) {
		        				unidad = uni.getNombre();
		        			}
		        		}
		        		
		        		HSSFCell cellInd1 = Row.createCell(cel);
		        		cellInd1.setCellStyle(style1);		        		
		        		cellInd1.setCellValue(unidad);
		        		cel++;
		        		
		        		for(int y= mesI; y<=mesF; y++) {
		        	    	
		        			//obtener mediciones
		        			
		        			Double real=0.0;
		        			Double meta=0.0;
		        			Double porcentaje =0.0;
		        			
		        			
		        			if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaMensual())) {
		        				
		        				real = obtenerMedicion(y, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
			        			
			        			HSSFCell cellInd2 = Row.createCell(cel);
				        		cellInd2.setCellStyle(style1);
				        		cellInd2.setCellValue(real);
				        		cel++;
				        		
				        		meta = obtenerMedicion(y, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
				        		
				        		HSSFCell cellInd3 = Row.createCell(cel);
				        		cellInd3.setCellStyle(style1);
				        		cellInd3.setCellValue(meta);
				        		cel++;
				        		
				        		if(real >0 && meta >0) {
			        				porcentaje = ((real/meta)*100);
			        			}
				        		
				        		HSSFCell cellInd4 = Row.createCell(cel);
				        		cellInd4.setCellStyle(style1);
				        		cellInd4.setCellValue(porcentaje);
				        		cel++;
			        					  
		        				
		        			}else  if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaTrimestral())) {
		        				
		        				if(y == 3) {
		        					
		        					real = obtenerMedicion(1, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
				        			
				        			HSSFCell cellInd2 = Row.createCell(cel);
					        		cellInd2.setCellStyle(style1);
					        		cellInd2.setCellValue(real);
					        		cel++;
					        		
					        		meta = obtenerMedicion(1, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
					        		
					        		HSSFCell cellInd3 = Row.createCell(cel);
					        		cellInd3.setCellStyle(style1);
					        		cellInd3.setCellValue(meta);
					        		cel++;
					        		
					        		if(real >0 && meta >0) {
				        				porcentaje = ((real/meta)*100);
				        			}
					        		
					        		HSSFCell cellInd4 = Row.createCell(cel);
					        		cellInd4.setCellStyle(style1);
					        		cellInd4.setCellValue(porcentaje);
					        		cel++;
		        					
		        				}else if(y == 6) {
		        					
		        					real = obtenerMedicion(2, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
				        			
				        			HSSFCell cellInd2 = Row.createCell(cel);
					        		cellInd2.setCellStyle(style1);
					        		cellInd2.setCellValue(real);
					        		cel++;
					        		
					        		meta = obtenerMedicion(2, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
					        		
					        		HSSFCell cellInd3 = Row.createCell(cel);
					        		cellInd3.setCellStyle(style1);
					        		cellInd3.setCellValue(meta);
					        		cel++;
					        		
					        		if(real >0 && meta >0) {
				        				porcentaje = ((real/meta)*100);
				        			}
					        		
					        		HSSFCell cellInd4 = Row.createCell(cel);
					        		cellInd4.setCellStyle(style1);
					        		cellInd4.setCellValue(porcentaje);
					        		cel++;
		        					
		        				}else if(y == 9) {
		        					
		        					real = obtenerMedicion(3, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
				        			
				        			HSSFCell cellInd2 = Row.createCell(cel);
					        		cellInd2.setCellStyle(style1);
					        		cellInd2.setCellValue(real);
					        		cel++;
					        		
					        		meta = obtenerMedicion(3, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
					        		
					        		HSSFCell cellInd3 = Row.createCell(cel);
					        		cellInd3.setCellStyle(style1);
					        		cellInd3.setCellValue(meta);
					        		cel++;
					        		
					        		if(real >0 && meta >0) {
				        				porcentaje = ((real/meta)*100);
				        			}
					        		
					        		HSSFCell cellInd4 = Row.createCell(cel);
					        		cellInd4.setCellStyle(style1);
					        		cellInd4.setCellValue(porcentaje);
					        		cel++;
		        					
		        				}else if(y == 12) {
		        					
		        					real = obtenerMedicion(4, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
				        			
				        			HSSFCell cellInd2 = Row.createCell(cel);
					        		cellInd2.setCellStyle(style1);
					        		cellInd2.setCellValue(real);
					        		cel++;
					        		
					        		meta = obtenerMedicion(4, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
					        		
					        		HSSFCell cellInd3 = Row.createCell(cel);
					        		cellInd3.setCellStyle(style1);
					        		cellInd3.setCellValue(meta);
					        		cel++;
					        		
					        		if(real >0 && meta >0) {
				        				porcentaje = ((real/meta)*100);
				        			}
					        		
					        		HSSFCell cellInd4 = Row.createCell(cel);
					        		cellInd4.setCellStyle(style1);
					        		cellInd4.setCellValue(porcentaje);
					        		cel++;
		        					
		        				}
		        				
		        			}
		        			
		        			
		        			      					        			        	
				        		        	
				        }
		        	}        
			        
			        row++;
		        }
		        
		        row++;
		        HSSFRow IndPreRow = sheet.createRow(row);      
		        
		        HSSFCell cellIndPre = IndPreRow.createCell(0);
		        cellIndPre.setCellStyle(style2);
		        cellIndPre.setCellValue("Ejecución Financiera");
		        
		        row++;
		        row++;
		        
		        
		        if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaTrimestral())) {
		        	
		        	 //cabeceras del mes
			        HSSFRow Cab1Row = sheet.createRow(row);
			        int numCab1 =2;
			        for(int c= mesI; c<=mesF; c++) {
			        	
			        	if(c == 3) {
			        		
			        		HSSFCell cellCab = Cab1Row.createCell(numCab1);
				        	cellCab.setCellStyle(style3);
				        	cellCab.setCellValue(obtenerPeriodo(1));
		    	        	
				        	numCab1 = numCab1 +3;
			        		
			        	}else if(c == 6) {
			        		
			        		HSSFCell cellCab = Cab1Row.createCell(numCab1);
				        	cellCab.setCellStyle(style3);
				        	cellCab.setCellValue(obtenerPeriodo(2));
		    	        	
				        	numCab1 = numCab1 +3;
			        		
			        	}else if(c == 9) {
			        		
			        		HSSFCell cellCab = Cab1Row.createCell(numCab1);
				        	cellCab.setCellStyle(style3);
				        	cellCab.setCellValue(obtenerPeriodo(3));
		    	        	
				        	numCab1 = numCab1 +3;
			        		
			        	}else if(c == 12) {
			        		
			        		HSSFCell cellCab = Cab1Row.createCell(numCab1);
				        	cellCab.setCellStyle(style3);
				        	cellCab.setCellValue(obtenerPeriodo(4));
		    	        	
				        	numCab1 = numCab1 +3;
			        		
			        	}
			        		        	
			        }
			        
			        row++;
			        
			        HSSFRow CabP1Row = sheet.createRow(row);
			        
			        HSSFCell cellCa1 = CabP1Row.createCell(0);
			        cellCa1.setCellStyle(style);
			        cellCa1.setCellValue("Indicador");	
			        
			        HSSFCell cellCa2 = CabP1Row.createCell(1);
			        cellCa2.setCellStyle(style);
			        cellCa2.setCellValue("Unidad");	
			        
			        int num1 =2;
			        for(int c= mesI; c<=mesF; c++) {
			        	
			        	if(c == 3) {
			        		
			        		HSSFCell cellCabp1 = CabP1Row.createCell(num1);
				        	cellCabp1.setCellStyle(style);
				        	cellCabp1.setCellValue("Ejecutado");
				        	num1++;
				        	
				        	HSSFCell cellCabp2 = CabP1Row.createCell(num1);
				        	cellCabp2.setCellStyle(style);
				        	cellCabp2.setCellValue("Programado");
				        	num1++;
				        	
				        	HSSFCell cellCabp3 = CabP1Row.createCell(num1);
				        	cellCabp3.setCellStyle(style);
				        	cellCabp3.setCellValue("% Eficacia");
				        	num1++;
			        		
			        	}else if(c == 6) {
			        		
			        		HSSFCell cellCabp1 = CabP1Row.createCell(num1);
				        	cellCabp1.setCellStyle(style);
				        	cellCabp1.setCellValue("Ejecutado");
				        	num1++;
				        	
				        	HSSFCell cellCabp2 = CabP1Row.createCell(num1);
				        	cellCabp2.setCellStyle(style);
				        	cellCabp2.setCellValue("Programado");
				        	num1++;
				        	
				        	HSSFCell cellCabp3 = CabP1Row.createCell(num1);
				        	cellCabp3.setCellStyle(style);
				        	cellCabp3.setCellValue("% Eficacia");
				        	num1++;
			        		
			        	}else if(c == 9) {
			        		
			        		HSSFCell cellCabp1 = CabP1Row.createCell(num1);
				        	cellCabp1.setCellStyle(style);
				        	cellCabp1.setCellValue("Ejecutado");
				        	num1++;
				        	
				        	HSSFCell cellCabp2 = CabP1Row.createCell(num1);
				        	cellCabp2.setCellStyle(style);
				        	cellCabp2.setCellValue("Programado");
				        	num1++;
				        	
				        	HSSFCell cellCabp3 = CabP1Row.createCell(num1);
				        	cellCabp3.setCellStyle(style);
				        	cellCabp3.setCellValue("% Eficacia");
				        	num1++;
			        		
			        	}else if(c == 12) {
			        		
			        		HSSFCell cellCabp1 = CabP1Row.createCell(num1);
				        	cellCabp1.setCellStyle(style);
				        	cellCabp1.setCellValue("Ejecutado");
				        	num1++;
				        	
				        	HSSFCell cellCabp2 = CabP1Row.createCell(num1);
				        	cellCabp2.setCellStyle(style);
				        	cellCabp2.setCellValue("Programado");
				        	num1++;
				        	
				        	HSSFCell cellCabp3 = CabP1Row.createCell(num1);
				        	cellCabp3.setCellStyle(style);
				        	cellCabp3.setCellValue("% Eficacia");
				        	num1++;
			        		
			        	}
			        	
			        	
			        
			        }
			        
			        row++;
		        	
		        }else if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaMensual())) {
		        	
		        	 //cabeceras del mes

			        HSSFRow Cab1Row = sheet.createRow(row);
			        int numCab1 =2;
			        for(int c= mesI; c<=mesF; c++) {
			        	
			        	HSSFCell cellCab = Cab1Row.createCell(numCab1);
			        	cellCab.setCellStyle(style3);
			        	cellCab.setCellValue(obtenerMes(c));
	    	        	
			        	numCab1 = numCab1 +3;		        	
			        }
			        
			        row++;
			        
			        HSSFRow CabP1Row = sheet.createRow(row);
			        
			        HSSFCell cell1Ca1 = CabP1Row.createCell(0);
			        cell1Ca1.setCellStyle(style);
			        cell1Ca1.setCellValue("Indicador");	
			        
			        HSSFCell cell1Ca2 = CabP1Row.createCell(1);
			        cell1Ca2.setCellStyle(style);
			        cell1Ca2.setCellValue("Unidad");	
			        
			        int num1 =2;
			        for(int c= mesI; c<=mesF; c++) {
			        	
			        	HSSFCell cellCabp1 = CabP1Row.createCell(num1);
			        	cellCabp1.setCellStyle(style);
			        	cellCabp1.setCellValue("Ejecutado");
			        	num1++;
			        	
			        	HSSFCell cellCabp2 = CabP1Row.createCell(num1);
			        	cellCabp2.setCellStyle(style);
			        	cellCabp2.setCellValue("Programado");
			        	num1++;
			        	
			        	HSSFCell cellCabp3 = CabP1Row.createCell(num1);
			        	cellCabp3.setCellStyle(style);
			        	cellCabp3.setCellValue("% Eficacia");
			        	num1++;
			        
			        }
			        
			        row++;
		        	
		        }
		        
		       
		        
		      //datos indicadores ejecucion presupuesto
		        
		        for (Iterator<Long> iterP = indicadoresPresupuesto.iterator(); iterP.hasNext();) {
		        	
		        	Long indicadorId = iterP.next();
		        	int cel = 0;
		        	HSSFRow Row = sheet.createRow(row); 
		        	
		        	Indicador indicador = (Indicador) iniciativaservice.load(Indicador.class, new Long(indicadorId));
		        	
		        	if(indicador != null) {
		        		
		        		HSSFCell cellInd = Row.createCell(cel);
		        		cellInd.setCellStyle(style);
		        		cellInd.setCellValue(indicador.getNombre());
		        		cel++;
		        		
		        		String unidad ="";
		        		if(indicador.getUnidadId() != null) {
		        			UnidadMedida uni = (UnidadMedida) iniciativaservice.load(UnidadMedida.class, indicador.getUnidadId());
		        			if(uni != null) {
		        				unidad = uni.getNombre();
		        			}
		        		}
		        		
		        		HSSFCell cellInd1 = Row.createCell(cel);
		        		cellInd1.setCellStyle(style1);		        		
		        		cellInd1.setCellValue(unidad);
		        		cel++;
		        		
		        		for(int y= mesI; y<=mesF; y++) {
		        	    	
		        			//obtener mediciones
		        			
		        			Double real=0.0;
		        			Double meta=0.0;
		        			Double porcentaje =0.0;
		        			
		        			
		        			if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaMensual())) {
		        				
		        				real = obtenerMedicion(y, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
			        			
			        			HSSFCell cellInd2 = Row.createCell(cel);
				        		cellInd2.setCellStyle(style1);
				        		cellInd2.setCellValue(real);
				        		cel++;
				        		
				        		meta = obtenerMedicion(y, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
				        		
				        		HSSFCell cellInd3 = Row.createCell(cel);
				        		cellInd3.setCellStyle(style1);
				        		cellInd3.setCellValue(meta);
				        		cel++;
				        		
				        		if(real >0 && meta >0) {
			        				porcentaje = ((real/meta)*100);
			        			}
				        		
				        		HSSFCell cellInd4 = Row.createCell(cel);
				        		cellInd4.setCellStyle(style1);
				        		cellInd4.setCellValue(porcentaje);
				        		cel++;
			        					  
		        				
		        			}else  if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaTrimestral())) {
		        				
		        				if(y == 3) {
		        					
		        					real = obtenerMedicion(1, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
				        			
				        			HSSFCell cellInd2 = Row.createCell(cel);
					        		cellInd2.setCellStyle(style1);
					        		cellInd2.setCellValue(real);
					        		cel++;
					        		
					        		meta = obtenerMedicion(1, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
					        		
					        		HSSFCell cellInd3 = Row.createCell(cel);
					        		cellInd3.setCellStyle(style1);
					        		cellInd3.setCellValue(meta);
					        		cel++;
					        		
					        		if(real >0 && meta >0) {
				        				porcentaje = ((real/meta)*100);
				        			}
					        		
					        		HSSFCell cellInd4 = Row.createCell(cel);
					        		cellInd4.setCellStyle(style1);
					        		cellInd4.setCellValue(porcentaje);
					        		cel++;
		        					
		        				}else if(y == 6) {
		        					
		        					real = obtenerMedicion(2, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
				        			
				        			HSSFCell cellInd2 = Row.createCell(cel);
					        		cellInd2.setCellStyle(style1);
					        		cellInd2.setCellValue(real);
					        		cel++;
					        		
					        		meta = obtenerMedicion(2, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
					        		
					        		HSSFCell cellInd3 = Row.createCell(cel);
					        		cellInd3.setCellStyle(style1);
					        		cellInd3.setCellValue(meta);
					        		cel++;
					        		
					        		if(real >0 && meta >0) {
				        				porcentaje = ((real/meta)*100);
				        			}
					        		
					        		HSSFCell cellInd4 = Row.createCell(cel);
					        		cellInd4.setCellStyle(style1);
					        		cellInd4.setCellValue(porcentaje);
					        		cel++;
		        					
		        				}else if(y == 9) {
		        					
		        					real = obtenerMedicion(3, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
				        			
				        			HSSFCell cellInd2 = Row.createCell(cel);
					        		cellInd2.setCellStyle(style1);
					        		cellInd2.setCellValue(real);
					        		cel++;
					        		
					        		meta = obtenerMedicion(3, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
					        		
					        		HSSFCell cellInd3 = Row.createCell(cel);
					        		cellInd3.setCellStyle(style1);
					        		cellInd3.setCellValue(meta);
					        		cel++;
					        		
					        		if(real >0 && meta >0) {
				        				porcentaje = ((real/meta)*100);
				        			}
					        		
					        		HSSFCell cellInd4 = Row.createCell(cel);
					        		cellInd4.setCellStyle(style1);
					        		cellInd4.setCellValue(porcentaje);
					        		cel++;
		        					
		        				}else if(y == 12) {
		        					
		        					real = obtenerMedicion(4, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
				        			
				        			HSSFCell cellInd2 = Row.createCell(cel);
					        		cellInd2.setCellStyle(style1);
					        		cellInd2.setCellValue(real);
					        		cel++;
					        		
					        		meta = obtenerMedicion(4, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
					        		
					        		HSSFCell cellInd3 = Row.createCell(cel);
					        		cellInd3.setCellStyle(style1);
					        		cellInd3.setCellValue(meta);
					        		cel++;
					        		
					        		if(real >0 && meta >0) {
				        				porcentaje = ((real/meta)*100);
				        			}
					        		
					        		HSSFCell cellInd4 = Row.createCell(cel);
					        		cellInd4.setCellStyle(style1);
					        		cellInd4.setCellValue(porcentaje);
					        		cel++;
		        					
		        				}
		        				
		        			}
		        			
		        			
		        			      					        			        	
				        		        	
				        }
		        	}        
			        
			        row++;
		        	
		        }
		        
		        if(avance.equals("true")) { // mostrar indicadores de las actividades
		        	
		        	
			        
			        if(iniciativa.getProyectoId() != null) {
			        	
			        	row++;
				        HSSFRow IndPreARow = sheet.createRow(row); 
				     
				        HSSFCell cellIndAPre = IndPreARow.createCell(0);
				        cellIndAPre.setCellStyle(style2);
				        cellIndAPre.setCellValue("Avance Actividades");
				        
				        
				        row++;
				        row++;
				        
				        
				        if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaTrimestral())) {
				        	
				        	 //cabeceras del mes
					        HSSFRow Cab2Row = sheet.createRow(row);
					        int numCab2 =2;
					        for(int c= mesI; c<=mesF; c++) {
					        	
					        	if(c == 3) {
					        		
					        		HSSFCell cellCab = Cab2Row.createCell(numCab2);
						        	cellCab.setCellStyle(style3);
						        	cellCab.setCellValue(obtenerPeriodo(1));
				    	        	
						        	numCab2 = numCab2 +3;
					        		
					        	}else if(c == 6) {
					        		
					        		HSSFCell cellCab = Cab2Row.createCell(numCab2);
						        	cellCab.setCellStyle(style3);
						        	cellCab.setCellValue(obtenerPeriodo(2));
				    	        	
						        	numCab2 = numCab2 +3;
					        		
					        	}else if(c == 9) {
					        		
					        		HSSFCell cellCab = Cab2Row.createCell(numCab2);
						        	cellCab.setCellStyle(style3);
						        	cellCab.setCellValue(obtenerPeriodo(3));
				    	        	
						        	numCab2 = numCab2 +3;
					        		
					        	}else if(c == 12) {
					        		
					        		HSSFCell cellCab = Cab2Row.createCell(numCab2);
						        	cellCab.setCellStyle(style3);
						        	cellCab.setCellValue(obtenerPeriodo(4));
				    	        	
						        	numCab2 = numCab2 +3;
					        		
					        	}
					        		        	
					        }
					        
					        row++;
					        
					        HSSFRow CabP2Row = sheet.createRow(row);
					        
					        HSSFCell cellCa1 = CabP2Row.createCell(0);
					        cellCa1.setCellStyle(style);
					        cellCa1.setCellValue("Indicador");	
					        
					        HSSFCell cellCa2 = CabP2Row.createCell(1);
					        cellCa2.setCellStyle(style);
					        cellCa2.setCellValue("Unidad");	
					        
					        int num2 =2;
					        for(int c= mesI; c<=mesF; c++) {
					        	
					        	if(c == 3) {
					        		
					        		HSSFCell cellCabp1 = CabP2Row.createCell(num2);
						        	cellCabp1.setCellStyle(style);
						        	cellCabp1.setCellValue("Ejecutado");
						        	num2++;
						        	
						        	HSSFCell cellCabp2 = CabP2Row.createCell(num2);
						        	cellCabp2.setCellStyle(style);
						        	cellCabp2.setCellValue("Programado");
						        	num2++;
						        	
						        	HSSFCell cellCabp3 = CabP2Row.createCell(num2);
						        	cellCabp3.setCellStyle(style);
						        	cellCabp3.setCellValue("% Eficacia");
						        	num2++;
					        		
					        	}else if(c == 6) {
					        		
					        		HSSFCell cellCabp1 = CabP2Row.createCell(num2);
						        	cellCabp1.setCellStyle(style);
						        	cellCabp1.setCellValue("Ejecutado");
						        	num2++;
						        	
						        	HSSFCell cellCabp2 = CabP2Row.createCell(num2);
						        	cellCabp2.setCellStyle(style);
						        	cellCabp2.setCellValue("Programado");
						        	num2++;
						        	
						        	HSSFCell cellCabp3 = CabP2Row.createCell(num2);
						        	cellCabp3.setCellStyle(style);
						        	cellCabp3.setCellValue("% Eficacia");
						        	num2++;
					        		
					        	}else if(c == 9) {
					        		
					        		HSSFCell cellCabp1 = CabP2Row.createCell(num2);
						        	cellCabp1.setCellStyle(style);
						        	cellCabp1.setCellValue("Ejecutado");
						        	num2++;
						        	
						        	HSSFCell cellCabp2 = CabP2Row.createCell(num2);
						        	cellCabp2.setCellStyle(style);
						        	cellCabp2.setCellValue("Programado");
						        	num2++;
						        	
						        	HSSFCell cellCabp3 = CabP2Row.createCell(num2);
						        	cellCabp3.setCellStyle(style);
						        	cellCabp3.setCellValue("% Eficacia");
						        	num2++;
					        		
					        	}else if(c == 12) {
					        		
					        		HSSFCell cellCabp1 = CabP2Row.createCell(num2);
						        	cellCabp1.setCellStyle(style);
						        	cellCabp1.setCellValue("Ejecutado");
						        	num2++;
						        	
						        	HSSFCell cellCabp2 = CabP2Row.createCell(num2);
						        	cellCabp2.setCellStyle(style);
						        	cellCabp2.setCellValue("Programado");
						        	num2++;
						        	
						        	HSSFCell cellCabp3 = CabP2Row.createCell(num2);
						        	cellCabp3.setCellStyle(style);
						        	cellCabp3.setCellValue("% Eficacia");
						        	num2++;
					        		
					        	}
					        	
					        	
					        
					        }
					        
					        row++;
				        	
				        }else if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaMensual())) {
				        	
				        	 //cabeceras del mes

				        	HSSFRow Cab2Row = sheet.createRow(row);
					        int numCab2 =2;
					        for(int c= mesI; c<=mesF; c++) {
					        	
					        	HSSFCell cellCab = Cab2Row.createCell(numCab2);
					        	cellCab.setCellStyle(style);
					        	cellCab.setCellValue(obtenerMes(c));
			    	        	
					        	numCab2 = numCab2 +3;		        	
					        }
					        
					        row++;
					        
					        HSSFRow CabP2Row = sheet.createRow(row);
					        
					        HSSFCell cell2Ca1 = CabP2Row.createCell(0);
					        cell2Ca1.setCellStyle(style);
					        cell2Ca1.setCellValue("Indicador");	
					        
					        HSSFCell cell2Ca2 = CabP2Row.createCell(1);
					        cell2Ca2.setCellStyle(style);
					        cell2Ca2.setCellValue("Unidad");	
					        
					        int num2 =2;
					        for(int c= mesI; c<=mesF; c++) {
					        	
					        	HSSFCell cellCabp1 = CabP2Row.createCell(num2);
					        	cellCabp1.setCellStyle(style);
					        	cellCabp1.setCellValue("Ejecutado");
					        	num2++;
					        	
					        	HSSFCell cellCabp2 = CabP2Row.createCell(num2);
					        	cellCabp2.setCellStyle(style);
					        	cellCabp2.setCellValue("Programado");
					        	num2++;
					        	
					        	HSSFCell cellCabp3 = CabP2Row.createCell(num2);
					        	cellCabp3.setCellStyle(style);
					        	cellCabp3.setCellValue("% Eficacia");
					        	num2++;
					        
					        }
				        	
				        }
				        
				        
				        
				        
				        
				        row++;
			        	
			        	List<PryActividad> actividades =actividadservice.getActividades(iniciativa.getProyectoId());
			        	
			        	 for (Iterator<PryActividad> iterA = actividades.iterator(); iterA.hasNext();) {
			        		 
			        		 PryActividad actividad = iterA.next();
			        		 
			        		 indicadoresAvance.add(actividad.getIndicadorId());
			        		 
			        	 }
			        	 
			        	 //datos avance
			        	 
			        	 for (Iterator<Long> iterI = indicadoresAvance.iterator(); iterI.hasNext();) {
					        	
					        	Long indicadorId = iterI.next();
					        	int cel = 0;
					        	HSSFRow Row = sheet.createRow(row); 
					        	
					        	Indicador indicador = (Indicador) iniciativaservice.load(Indicador.class, new Long(indicadorId));
					        	
					        	if(indicador != null) {
					        		
					        		HSSFCell cellInd = Row.createCell(cel);
					        		cellInd.setCellStyle(style);
					        		cellInd.setCellValue(indicador.getNombre());
					        		cel++;
					        		
					        		String unidad ="";
					        		if(indicador.getUnidadId() != null) {
					        			UnidadMedida uni = (UnidadMedida) iniciativaservice.load(UnidadMedida.class, indicador.getUnidadId());
					        			if(uni != null) {
					        				unidad = uni.getNombre();
					        			}
					        		}
					        		
					        		HSSFCell cellInd1 = Row.createCell(cel);
					        		cellInd1.setCellStyle(style1);		        		
					        		cellInd1.setCellValue(unidad);
					        		cel++;
					        		
					        		for(int y= mesI; y<=mesF; y++) {
					        	    	
					        			//obtener mediciones
					        			
					        			Double real=0.0;
					        			Double meta=0.0;
					        			Double porcentaje =0.0;
					        			
					        			
					        			real = obtenerMedicion(y, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
					        			
					        			HSSFCell cellInd2 = Row.createCell(cel);
						        		cellInd2.setCellStyle(style1);
						        		cellInd2.setCellValue(real);
						        		cel++;
						        		
						        		meta = obtenerMedicion(y, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
						        		
						        		HSSFCell cellInd3 = Row.createCell(cel);
						        		cellInd3.setCellStyle(style1);
						        		cellInd3.setCellValue(meta);
						        		cel++;
						        		
						        		if(real >0 && meta >0) {
					        				porcentaje = ((real/meta)*100);
					        			}
						        		
						        		HSSFCell cellInd4 = Row.createCell(cel);
						        		cellInd4.setCellStyle(style1);
						        		cellInd4.setCellValue(porcentaje);
						        		cel++;
					        			        	
							        		        	
							        }
					        	}        
						        
						        row++;
					        }
			        	 
			        	 
			        }
			        
			        
		        }
		        
		        

				 for(int y=0; y<50; y++) {
			        	
			        	sheet.autoSizeColumn(y);
			     }
		        // crea el archivo excel

		        HSSFRow dataRow = sheet.createRow(numeroCelda+1);

		        Date date = new Date();
		        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");


		        String archivo="IniciativasIndicador_"+hourdateFormat.format(date)+".xls";

		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition","attachment;filename="+archivo);

		        ServletOutputStream file  = response.getOutputStream();

		        workbook.write(file);
		        file.close();
				
				
			}
			
			
			
		}else {// todas las iniciativas
			
			OrganizacionStrategos org = (OrganizacionStrategos)organizacionservice.load(OrganizacionStrategos.class,  ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
			
			
			 filtros.put("organizacionId", org.getOrganizacionId().toString());
				if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
					filtros.put("historicoDate", "IS NULL");
				else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
					filtros.put("historicoDate", "IS NOT NULL");
				if (reporte.getFiltro().getNombre() != null)
					filtros.put("nombre", reporte.getFiltro().getNombre());
				if(!tipo.equals("0")) {
					filtros.put("tipoId", tipo);
				}
				if(todos.equals("false")) {
					filtros.put("anio", ano);
				}
				

		        List<Iniciativa> iniciativas = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();

		    	if (iniciativas.size() > 0){
		    		
		    		HSSFWorkbook workbook = new HSSFWorkbook();
			        HSSFSheet sheet = workbook.createSheet();
			        workbook.setSheetName(0, "Hoja excel");
			        
			        int numero =0;

			        CellStyle headerStyle = workbook.createCellStyle();
			        Font font = workbook.createFont();
			        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			        headerStyle.setFont(font);

			        Font font2 = workbook.createFont();
			        font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
			        font2.setColor(IndexedColors.WHITE.getIndex());

			        CellStyle style = workbook.createCellStyle();
			       
			        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			        
			        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
			        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
			        style.setFont(font);
			        style.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
			        style.setFillBackgroundColor(IndexedColors.PALE_BLUE.index);
			        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			        
			        CellStyle style1 = workbook.createCellStyle();
				       
			        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
			        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
			        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			        
			        style1.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			        style1.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			        style1.setRightBorderColor(IndexedColors.BLACK.getIndex());
			        style1.setTopBorderColor(IndexedColors.BLACK.getIndex());
			        style1.setFillForegroundColor(IndexedColors.WHITE.index);
			        style1.setFillBackgroundColor(IndexedColors.WHITE.index);
			        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			        
			        CellStyle style3 = workbook.createCellStyle();
				       
			        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
			        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
			        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			        
			        style3.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			        style3.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			        style3.setRightBorderColor(IndexedColors.BLACK.getIndex());
			        style3.setTopBorderColor(IndexedColors.BLACK.getIndex());
			        style3.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
			        style3.setFillBackgroundColor(IndexedColors.ROYAL_BLUE.getIndex());
			        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			        style3.setFont(font2);
			        
			        CellStyle style2 = workbook.createCellStyle();
			        style2.setFont(font);
			        
			        HSSFRow headerRow = sheet.createRow(numero);

			        String header = "Reporte Indicadores Iniciativa";
			        HSSFCell cell = headerRow.createCell(0);
			        cell.setCellStyle(style);
			        cell.setCellValue(header);

			        numero++;
			        numero++;
			       

					for (Iterator<Iniciativa> iter1 = iniciativas.iterator(); iter1.hasNext();){
						Iniciativa iniciativa = iter1.next();
						
						if(iniciativa != null) {
							
							// buscar indicadores
							
							List<IndicadorIniciativa> indicadores = iniciativaservice.getIndicadoresIniciativa(iniciativa.getIniciativaId());
							List<Long> indicadoresFisico = new ArrayList<Long>();
							List<Long> indicadoresPresupuesto = new ArrayList<Long>();
							List<Long> indicadoresAvance = new ArrayList<Long>();
								
							
							for (Iterator<IndicadorIniciativa> i = indicadores.iterator(); i.hasNext();) {
								IndicadorIniciativa ind = (IndicadorIniciativa) i.next();
								
								if(ind.getTipo().equals(TipoFuncionIndicador.getTipoFuncionPresupuesto())) {
									indicadoresPresupuesto.add(ind.getPk().getIndicadorId());
								}else {
									indicadoresFisico.add(ind.getPk().getIndicadorId());
								}
																
							}
							
							numero++;
							
							 HSSFRow OrgRow = sheet.createRow(numero);
						     HSSFCell cellOrg1 = OrgRow.createCell(0);
						     cellOrg1.setCellStyle(style);
						     cellOrg1.setCellValue("Organización");
												      
							if(org != null) {
								 String nombre = org.getNombre();
							     HSSFCell cellOrg = OrgRow.createCell(1);
							     cellOrg.setCellStyle(style1);
							     cellOrg.setCellValue(nombre);
							}
							
							numero++;
							
							
							HSSFRow IniRow = sheet.createRow(numero);
							
							HSSFCell cellIni = IniRow.createCell(0);
					        cellIni.setCellStyle(style);
					        cellIni.setCellValue("Proyecto - Iniciativa");	
							
					        HSSFCell cellIni1 = IniRow.createCell(1);
					        cellIni1.setCellStyle(style1);
					        cellIni1.setCellValue(iniciativa.getNombre());	
					        
					        numero++;
							
							
					        HSSFRow FechasRow = sheet.createRow(numero);
					        
					        HSSFCell cellFec = FechasRow.createCell(0);
					        cellFec.setCellStyle(style);
					        cellFec.setCellValue("Año Formulación");	
							
					        HSSFCell cellFec1 = FechasRow.createCell(1);
					        cellFec1.setCellStyle(style1);
					        cellFec1.setCellValue(iniciativa.getAnioFormulacion());
					        
					        numero++;     
					        
					        
					        HSSFRow ResRow = sheet.createRow(numero);
					        
					        HSSFCell cellRes = ResRow.createCell(0);
					        cellRes.setCellStyle(style);
					        cellRes.setCellValue("Responsable");	
							
					        HSSFCell cellRes1 = ResRow.createCell(1);
					        cellRes1.setCellStyle(style1);
					        cellRes1.setCellValue(iniciativa.getResponsableProyecto());
					        numero++;
					
					        		        
					        HSSFRow IndRow = sheet.createRow(numero); 
					        HSSFCell cellIndEj = IndRow.createCell(0);
					        cellIndEj.setCellStyle(style2);
					        cellIndEj.setCellValue("Ejecución Física");
					        numero++;
					        
					        // cabeceras de los periodos
					        
					        int mesI=new Integer(mesIni);
					        int mesF=new Integer(mesFin);
					        
					        
					        
					        if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaTrimestral())) {
					        	
					        	 //cabeceras del mes
						        HSSFRow CabRow = sheet.createRow(numero);
						        int numCab =2;
						        for(int c= mesI; c<=mesF; c++) {
						        	
						        	if(c == 3) {
						        		
						        		HSSFCell cellCab = CabRow.createCell(numCab);
							        	cellCab.setCellStyle(style3);
							        	cellCab.setCellValue(obtenerPeriodo(1));
					    	        	
							        	numCab = numCab +3;
						        		
						        	}else if(c == 6) {
						        		
						        		HSSFCell cellCab = CabRow.createCell(numCab);
							        	cellCab.setCellStyle(style3);
							        	cellCab.setCellValue(obtenerPeriodo(2));
					    	        	
							        	numCab = numCab +3;
						        		
						        	}else if(c == 9) {
						        		
						        		HSSFCell cellCab = CabRow.createCell(numCab);
							        	cellCab.setCellStyle(style3);
							        	cellCab.setCellValue(obtenerPeriodo(3));
					    	        	
							        	numCab = numCab +3;
						        		
						        	}else if(c == 12) {
						        		
						        		HSSFCell cellCab = CabRow.createCell(numCab);
							        	cellCab.setCellStyle(style3);
							        	cellCab.setCellValue(obtenerPeriodo(4));
					    	        	
							        	numCab = numCab +3;
						        		
						        	}
						        		        	
						        }
						        
						        numero++;
						        
						        HSSFRow CabPRow = sheet.createRow(numero);
						        
						        HSSFCell cellCa1 = CabPRow.createCell(0);
						        cellCa1.setCellStyle(style);
						        cellCa1.setCellValue("Indicador");	
						        
						        HSSFCell cellCa2 = CabPRow.createCell(1);
						        cellCa2.setCellStyle(style);
						        cellCa2.setCellValue("Unidad");	
						        
						        int num =2;
						        for(int c= mesI; c<=mesF; c++) {
						        	
						        	if(c == 3) {
						        		
						        		HSSFCell cellCabp1 = CabPRow.createCell(num);
							        	cellCabp1.setCellStyle(style);
							        	cellCabp1.setCellValue("Ejecutado");
							        	num++;
							        	
							        	HSSFCell cellCabp2 = CabPRow.createCell(num);
							        	cellCabp2.setCellStyle(style);
							        	cellCabp2.setCellValue("Programado");
							        	num++;
							        	
							        	HSSFCell cellCabp3 = CabPRow.createCell(num);
							        	cellCabp3.setCellStyle(style);
							        	cellCabp3.setCellValue("% Eficacia");
							        	num++;
						        		
						        	}else if(c == 6) {
						        		
						        		HSSFCell cellCabp1 = CabPRow.createCell(num);
							        	cellCabp1.setCellStyle(style);
							        	cellCabp1.setCellValue("Ejecutado");
							        	num++;
							        	
							        	HSSFCell cellCabp2 = CabPRow.createCell(num);
							        	cellCabp2.setCellStyle(style);
							        	cellCabp2.setCellValue("Programado");
							        	num++;
							        	
							        	HSSFCell cellCabp3 = CabPRow.createCell(num);
							        	cellCabp3.setCellStyle(style);
							        	cellCabp3.setCellValue("% Eficacia");
							        	num++;
						        		
						        	}else if(c == 9) {
						        		
						        		HSSFCell cellCabp1 = CabPRow.createCell(num);
							        	cellCabp1.setCellStyle(style);
							        	cellCabp1.setCellValue("Ejecutado");
							        	num++;
							        	
							        	HSSFCell cellCabp2 = CabPRow.createCell(num);
							        	cellCabp2.setCellStyle(style);
							        	cellCabp2.setCellValue("Programado");
							        	num++;
							        	
							        	HSSFCell cellCabp3 = CabPRow.createCell(num);
							        	cellCabp3.setCellStyle(style);
							        	cellCabp3.setCellValue("% Eficacia");
							        	num++;
						        		
						        	}else if(c == 12) {
						        		
						        		HSSFCell cellCabp1 = CabPRow.createCell(num);
							        	cellCabp1.setCellStyle(style);
							        	cellCabp1.setCellValue("Ejecutado");
							        	num++;
							        	
							        	HSSFCell cellCabp2 = CabPRow.createCell(num);
							        	cellCabp2.setCellStyle(style);
							        	cellCabp2.setCellValue("Programado");
							        	num++;
							        	
							        	HSSFCell cellCabp3 = CabPRow.createCell(num);
							        	cellCabp3.setCellStyle(style);
							        	cellCabp3.setCellValue("% Eficacia");
							        	num++;
						        		
						        	}
						        	
						        	
						        
						        }
						        
						        numero++;
					        	
					        }else if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaMensual())) {
					        	
					        	 //cabeceras del mes
						        HSSFRow CabRow = sheet.createRow(numero);
						        int numCab =2;
						        for(int c= mesI; c<=mesF; c++) {
						        	
						        	HSSFCell cellCab = CabRow.createCell(numCab);
						        	cellCab.setCellStyle(style3);
						        	cellCab.setCellValue(obtenerMes(c));
				    	        	
						        	numCab = numCab +3;		        	
						        }
						        
						        numero++;
						        
						        HSSFRow CabPRow = sheet.createRow(numero);
						        
						        HSSFCell cellCa1 = CabPRow.createCell(0);
						        cellCa1.setCellStyle(style);
						        cellCa1.setCellValue("Indicador");	
						        
						        HSSFCell cellCa2 = CabPRow.createCell(1);
						        cellCa2.setCellStyle(style);
						        cellCa2.setCellValue("Unidad");	
						        
						        int num =2;
						        for(int c= mesI; c<=mesF; c++) {
						        	
						        	HSSFCell cellCabp1 = CabPRow.createCell(num);
						        	cellCabp1.setCellStyle(style);
						        	cellCabp1.setCellValue("Ejecutado");
						        	num++;
						        	
						        	HSSFCell cellCabp2 = CabPRow.createCell(num);
						        	cellCabp2.setCellStyle(style);
						        	cellCabp2.setCellValue("Programado");
						        	num++;
						        	
						        	HSSFCell cellCabp3 = CabPRow.createCell(num);
						        	cellCabp3.setCellStyle(style);
						        	cellCabp3.setCellValue("% Eficacia");
						        	num++;
						        
						        }
					        	
						        numero++;
					        }
					        
					       
					        
					        // datos
					        
					        
					       	
					        
					        //datos indicadores ejecucion fisica
					        numero++;
					        
					        for (Iterator<Long> iter = indicadoresFisico.iterator(); iter.hasNext();) {
					        	
					        	Long indicadorId = iter.next();
					        	int cel = 0;
					        	HSSFRow Row = sheet.createRow(numero); 
					        	
					        	Indicador indicador = (Indicador) iniciativaservice.load(Indicador.class, new Long(indicadorId));
					        	
					        	if(indicador != null) {
					        		
					        		HSSFCell cellInd = Row.createCell(cel);
					        		cellInd.setCellStyle(style);
					        		cellInd.setCellValue(indicador.getNombre());
					        		cel++;
					        		
					        		String unidad ="";
					        		if(indicador.getUnidadId() != null) {
					        			UnidadMedida uni = (UnidadMedida) iniciativaservice.load(UnidadMedida.class, indicador.getUnidadId());
					        			if(uni != null) {
					        				unidad = uni.getNombre();
					        			}
					        		}
					        		
					        		HSSFCell cellInd1 = Row.createCell(cel);
					        		cellInd1.setCellStyle(style1);		        		
					        		cellInd1.setCellValue(unidad);
					        		cel++;
					        		
					        		for(int y= mesI; y<=mesF; y++) {
					        	    	
					        			//obtener mediciones
					        			
					        			Double real=0.0;
					        			Double meta=0.0;
					        			Double porcentaje =0.0;
					        			
					        			
					        			if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaMensual())) {
					        				
					        				real = obtenerMedicion(y, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
						        			
						        			HSSFCell cellInd2 = Row.createCell(cel);
							        		cellInd2.setCellStyle(style1);
							        		cellInd2.setCellValue(real);
							        		cel++;
							        		
							        		meta = obtenerMedicion(y, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
							        		
							        		HSSFCell cellInd3 = Row.createCell(cel);
							        		cellInd3.setCellStyle(style1);
							        		cellInd3.setCellValue(meta);
							        		cel++;
							        		
							        		if(real >0 && meta >0) {
						        				porcentaje = ((real/meta)*100);
						        			}
							        		
							        		HSSFCell cellInd4 = Row.createCell(cel);
							        		cellInd4.setCellStyle(style1);
							        		cellInd4.setCellValue(porcentaje);
							        		cel++;
						        					  
					        				
					        			}else if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaTrimestral())) {
					        				
					        				if(y == 3) {
					        					
					        					real = obtenerMedicion(1, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
							        			
							        			HSSFCell cellInd2 = Row.createCell(cel);
								        		cellInd2.setCellStyle(style1);
								        		cellInd2.setCellValue(real);
								        		cel++;
								        		
								        		meta = obtenerMedicion(1, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
								        		
								        		HSSFCell cellInd3 = Row.createCell(cel);
								        		cellInd3.setCellStyle(style1);
								        		cellInd3.setCellValue(meta);
								        		cel++;
								        		
								        		if(real >0 && meta >0) {
							        				porcentaje = ((real/meta)*100);
							        			}
								        		
								        		HSSFCell cellInd4 = Row.createCell(cel);
								        		cellInd4.setCellStyle(style1);
								        		cellInd4.setCellValue(porcentaje);
								        		cel++;
					        					
					        				}else if(y == 6) {
					        					
					        					real = obtenerMedicion(2, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
							        			
							        			HSSFCell cellInd2 = Row.createCell(cel);
								        		cellInd2.setCellStyle(style1);
								        		cellInd2.setCellValue(real);
								        		cel++;
								        		
								        		meta = obtenerMedicion(2, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
								        		
								        		HSSFCell cellInd3 = Row.createCell(cel);
								        		cellInd3.setCellStyle(style1);
								        		cellInd3.setCellValue(meta);
								        		cel++;
								        		
								        		if(real >0 && meta >0) {
							        				porcentaje = ((real/meta)*100);
							        			}
								        		
								        		HSSFCell cellInd4 = Row.createCell(cel);
								        		cellInd4.setCellStyle(style1);
								        		cellInd4.setCellValue(porcentaje);
								        		cel++;
					        					
					        				}else if(y == 9) {
					        					
					        					real = obtenerMedicion(3, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
							        			
							        			HSSFCell cellInd2 = Row.createCell(cel);
								        		cellInd2.setCellStyle(style1);
								        		cellInd2.setCellValue(real);
								        		cel++;
								        		
								        		meta = obtenerMedicion(3, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
								        		
								        		HSSFCell cellInd3 = Row.createCell(cel);
								        		cellInd3.setCellStyle(style1);
								        		cellInd3.setCellValue(meta);
								        		cel++;
								        		
								        		if(real >0 && meta >0) {
							        				porcentaje = ((real/meta)*100);
							        			}
								        		
								        		HSSFCell cellInd4 = Row.createCell(cel);
								        		cellInd4.setCellStyle(style1);
								        		cellInd4.setCellValue(porcentaje);
								        		cel++;
					        					
					        				}else if(y == 12) {
					        					
					        					real = obtenerMedicion(4, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
							        			
							        			HSSFCell cellInd2 = Row.createCell(cel);
								        		cellInd2.setCellStyle(style1);
								        		cellInd2.setCellValue(real);
								        		cel++;
								        		
								        		meta = obtenerMedicion(4, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
								        		
								        		HSSFCell cellInd3 = Row.createCell(cel);
								        		cellInd3.setCellStyle(style1);
								        		cellInd3.setCellValue(meta);
								        		cel++;
								        		
								        		if(real >0 && meta >0) {
							        				porcentaje = ((real/meta)*100);
							        			}
								        		
								        		HSSFCell cellInd4 = Row.createCell(cel);
								        		cellInd4.setCellStyle(style1);
								        		cellInd4.setCellValue(porcentaje);
								        		cel++;
					        					
					        				}
					        				
					        			}
					        			
					        			
					        			      					        			        	
							        		        	
							        }
					        	}        
						        
					        	numero++;
					        }
					        
					        numero++;
					        HSSFRow IndPreRow = sheet.createRow(numero);      
					        
					        HSSFCell cellIndPre = IndPreRow.createCell(0);
					        cellIndPre.setCellStyle(style2);
					        cellIndPre.setCellValue("Ejecución Financiera");
					        
					        numero++;
					        numero++;
					        
					        
					        if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaTrimestral())) {
					        	
					        	 //cabeceras del mes
						        HSSFRow Cab1Row = sheet.createRow(numero);
						        int numCab1 =2;
						        for(int c= mesI; c<=mesF; c++) {
						        	
						        	if(c == 3) {
						        		
						        		HSSFCell cellCab = Cab1Row.createCell(numCab1);
							        	cellCab.setCellStyle(style3);
							        	cellCab.setCellValue(obtenerPeriodo(1));
					    	        	
							        	numCab1 = numCab1 +3;
						        		
						        	}else if(c == 6) {
						        		
						        		HSSFCell cellCab = Cab1Row.createCell(numCab1);
							        	cellCab.setCellStyle(style3);
							        	cellCab.setCellValue(obtenerPeriodo(2));
					    	        	
							        	numCab1 = numCab1 +3;
						        		
						        	}else if(c == 9) {
						        		
						        		HSSFCell cellCab = Cab1Row.createCell(numCab1);
							        	cellCab.setCellStyle(style3);
							        	cellCab.setCellValue(obtenerPeriodo(3));
					    	        	
							        	numCab1 = numCab1 +3;
						        		
						        	}else if(c == 12) {
						        		
						        		HSSFCell cellCab = Cab1Row.createCell(numCab1);
							        	cellCab.setCellStyle(style3);
							        	cellCab.setCellValue(obtenerPeriodo(4));
					    	        	
							        	numCab1 = numCab1 +3;
						        		
						        	}
						        		        	
						        }
						        
						        numero++;
						        
						        HSSFRow CabP1Row = sheet.createRow(numero);
						        
						        HSSFCell cellCa1 = CabP1Row.createCell(0);
						        cellCa1.setCellStyle(style);
						        cellCa1.setCellValue("Indicador");	
						        
						        HSSFCell cellCa2 = CabP1Row.createCell(1);
						        cellCa2.setCellStyle(style);
						        cellCa2.setCellValue("Unidad");	
						        
						        int num1 =2;
						        for(int c= mesI; c<=mesF; c++) {
						        	
						        	if(c == 3) {
						        		
						        		HSSFCell cellCabp1 = CabP1Row.createCell(num1);
							        	cellCabp1.setCellStyle(style);
							        	cellCabp1.setCellValue("Ejecutado");
							        	num1++;
							        	
							        	HSSFCell cellCabp2 = CabP1Row.createCell(num1);
							        	cellCabp2.setCellStyle(style);
							        	cellCabp2.setCellValue("Programado");
							        	num1++;
							        	
							        	HSSFCell cellCabp3 = CabP1Row.createCell(num1);
							        	cellCabp3.setCellStyle(style);
							        	cellCabp3.setCellValue("% Eficacia");
							        	num1++;
						        		
						        	}else if(c == 6) {
						        		
						        		HSSFCell cellCabp1 = CabP1Row.createCell(num1);
							        	cellCabp1.setCellStyle(style);
							        	cellCabp1.setCellValue("Ejecutado");
							        	num1++;
							        	
							        	HSSFCell cellCabp2 = CabP1Row.createCell(num1);
							        	cellCabp2.setCellStyle(style);
							        	cellCabp2.setCellValue("Programado");
							        	num1++;
							        	
							        	HSSFCell cellCabp3 = CabP1Row.createCell(num1);
							        	cellCabp3.setCellStyle(style);
							        	cellCabp3.setCellValue("% Eficacia");
							        	num1++;
						        		
						        	}else if(c == 9) {
						        		
						        		HSSFCell cellCabp1 = CabP1Row.createCell(num1);
							        	cellCabp1.setCellStyle(style);
							        	cellCabp1.setCellValue("Ejecutado");
							        	num1++;
							        	
							        	HSSFCell cellCabp2 = CabP1Row.createCell(num1);
							        	cellCabp2.setCellStyle(style);
							        	cellCabp2.setCellValue("Programado");
							        	num1++;
							        	
							        	HSSFCell cellCabp3 = CabP1Row.createCell(num1);
							        	cellCabp3.setCellStyle(style);
							        	cellCabp3.setCellValue("% Eficacia");
							        	num1++;
						        		
						        	}else if(c == 12) {
						        		
						        		HSSFCell cellCabp1 = CabP1Row.createCell(num1);
							        	cellCabp1.setCellStyle(style);
							        	cellCabp1.setCellValue("Ejecutado");
							        	num1++;
							        	
							        	HSSFCell cellCabp2 = CabP1Row.createCell(num1);
							        	cellCabp2.setCellStyle(style);
							        	cellCabp2.setCellValue("Programado");
							        	num1++;
							        	
							        	HSSFCell cellCabp3 = CabP1Row.createCell(num1);
							        	cellCabp3.setCellStyle(style);
							        	cellCabp3.setCellValue("% Eficacia");
							        	num1++;
						        		
						        	}
						        	
						        	
						        
						        }
						        
						        numero++;
					        	
					        }else if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaMensual())) {
					        	
					        	 //cabeceras del mes

						        HSSFRow Cab1Row = sheet.createRow(numero);
						        int numCab1 =2;
						        for(int c= mesI; c<=mesF; c++) {
						        	
						        	HSSFCell cellCab = Cab1Row.createCell(numCab1);
						        	cellCab.setCellStyle(style3);
						        	cellCab.setCellValue(obtenerMes(c));
				    	        	
						        	numCab1 = numCab1 +3;		        	
						        }
						        
						        numero++;
						        
						        HSSFRow CabP1Row = sheet.createRow(numero);
						        
						        HSSFCell cell1Ca1 = CabP1Row.createCell(0);
						        cell1Ca1.setCellStyle(style);
						        cell1Ca1.setCellValue("Indicador");	
						        
						        HSSFCell cell1Ca2 = CabP1Row.createCell(1);
						        cell1Ca2.setCellStyle(style);
						        cell1Ca2.setCellValue("Unidad");	
						        
						        int num1 =2;
						        for(int c= mesI; c<=mesF; c++) {
						        	
						        	HSSFCell cellCabp1 = CabP1Row.createCell(num1);
						        	cellCabp1.setCellStyle(style);
						        	cellCabp1.setCellValue("Ejecutado");
						        	num1++;
						        	
						        	HSSFCell cellCabp2 = CabP1Row.createCell(num1);
						        	cellCabp2.setCellStyle(style);
						        	cellCabp2.setCellValue("Programado");
						        	num1++;
						        	
						        	HSSFCell cellCabp3 = CabP1Row.createCell(num1);
						        	cellCabp3.setCellStyle(style);
						        	cellCabp3.setCellValue("% Eficacia");
						        	num1++;
						        
						        }
						        
						        numero++;
					        	
					        }
					        
					       
					        
					      //datos indicadores ejecucion presupuesto
					        
					        for (Iterator<Long> iterP = indicadoresPresupuesto.iterator(); iterP.hasNext();) {
					        	
					        	Long indicadorId = iterP.next();
					        	int cel = 0;
					        	HSSFRow Row = sheet.createRow(numero); 
					        	
					        	Indicador indicador = (Indicador) iniciativaservice.load(Indicador.class, new Long(indicadorId));
					        	
					        	if(indicador != null) {
					        		
					        		HSSFCell cellInd = Row.createCell(cel);
					        		cellInd.setCellStyle(style);
					        		cellInd.setCellValue(indicador.getNombre());
					        		cel++;
					        		
					        		String unidad ="";
					        		if(indicador.getUnidadId() != null) {
					        			UnidadMedida uni = (UnidadMedida) iniciativaservice.load(UnidadMedida.class, indicador.getUnidadId());
					        			if(uni != null) {
					        				unidad = uni.getNombre();
					        			}
					        		}
					        		
					        		HSSFCell cellInd1 = Row.createCell(cel);
					        		cellInd1.setCellStyle(style1);		        		
					        		cellInd1.setCellValue(unidad);
					        		cel++;
					        		
					        		for(int y= mesI; y<=mesF; y++) {
					        	    	
					        			//obtener mediciones
					        			
					        			Double real=0.0;
					        			Double meta=0.0;
					        			Double porcentaje =0.0;
					        			
					        			
					        			if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaMensual())) {
					        				
					        				real = obtenerMedicion(y, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
						        			
						        			HSSFCell cellInd2 = Row.createCell(cel);
							        		cellInd2.setCellStyle(style1);
							        		cellInd2.setCellValue(real);
							        		cel++;
							        		
							        		meta = obtenerMedicion(y, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
							        		
							        		HSSFCell cellInd3 = Row.createCell(cel);
							        		cellInd3.setCellStyle(style1);
							        		cellInd3.setCellValue(meta);
							        		cel++;
							        		
							        		if(real >0 && meta >0) {
						        				porcentaje = ((real/meta)*100);
						        			}
							        		
							        		HSSFCell cellInd4 = Row.createCell(cel);
							        		cellInd4.setCellStyle(style1);
							        		cellInd4.setCellValue(porcentaje);
							        		cel++;
						        					  
					        				
					        			}else  if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaTrimestral())) {
					        				
					        				if(y == 3) {
					        					
					        					real = obtenerMedicion(1, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
							        			
							        			HSSFCell cellInd2 = Row.createCell(cel);
								        		cellInd2.setCellStyle(style1);
								        		cellInd2.setCellValue(real);
								        		cel++;
								        		
								        		meta = obtenerMedicion(1, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
								        		
								        		HSSFCell cellInd3 = Row.createCell(cel);
								        		cellInd3.setCellStyle(style1);
								        		cellInd3.setCellValue(meta);
								        		cel++;
								        		
								        		if(real >0 && meta >0) {
							        				porcentaje = ((real/meta)*100);
							        			}
								        		
								        		HSSFCell cellInd4 = Row.createCell(cel);
								        		cellInd4.setCellStyle(style1);
								        		cellInd4.setCellValue(porcentaje);
								        		cel++;
					        					
					        				}else if(y == 6) {
					        					
					        					real = obtenerMedicion(2, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
							        			
							        			HSSFCell cellInd2 = Row.createCell(cel);
								        		cellInd2.setCellStyle(style1);
								        		cellInd2.setCellValue(real);
								        		cel++;
								        		
								        		meta = obtenerMedicion(2, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
								        		
								        		HSSFCell cellInd3 = Row.createCell(cel);
								        		cellInd3.setCellStyle(style1);
								        		cellInd3.setCellValue(meta);
								        		cel++;
								        		
								        		if(real >0 && meta >0) {
							        				porcentaje = ((real/meta)*100);
							        			}
								        		
								        		HSSFCell cellInd4 = Row.createCell(cel);
								        		cellInd4.setCellStyle(style1);
								        		cellInd4.setCellValue(porcentaje);
								        		cel++;
					        					
					        				}else if(y == 9) {
					        					
					        					real = obtenerMedicion(3, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
							        			
							        			HSSFCell cellInd2 = Row.createCell(cel);
								        		cellInd2.setCellStyle(style1);
								        		cellInd2.setCellValue(real);
								        		cel++;
								        		
								        		meta = obtenerMedicion(3, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
								        		
								        		HSSFCell cellInd3 = Row.createCell(cel);
								        		cellInd3.setCellStyle(style1);
								        		cellInd3.setCellValue(meta);
								        		cel++;
								        		
								        		if(real >0 && meta >0) {
							        				porcentaje = ((real/meta)*100);
							        			}
								        		
								        		HSSFCell cellInd4 = Row.createCell(cel);
								        		cellInd4.setCellStyle(style1);
								        		cellInd4.setCellValue(porcentaje);
								        		cel++;
					        					
					        				}else if(y == 12) {
					        					
					        					real = obtenerMedicion(4, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
							        			
							        			HSSFCell cellInd2 = Row.createCell(cel);
								        		cellInd2.setCellStyle(style1);
								        		cellInd2.setCellValue(real);
								        		cel++;
								        		
								        		meta = obtenerMedicion(4, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
								        		
								        		HSSFCell cellInd3 = Row.createCell(cel);
								        		cellInd3.setCellStyle(style1);
								        		cellInd3.setCellValue(meta);
								        		cel++;
								        		
								        		if(real >0 && meta >0) {
							        				porcentaje = ((real/meta)*100);
							        			}
								        		
								        		HSSFCell cellInd4 = Row.createCell(cel);
								        		cellInd4.setCellStyle(style1);
								        		cellInd4.setCellValue(porcentaje);
								        		cel++;
					        					
					        				}
					        				
					        			}
					        			
					        			
					        			      					        			        	
							        		        	
							        }
					        	}        
						        
					        	numero++;
					        	
					        }
					        
					        if(avance.equals("true")) { // mostrar indicadores de las actividades
					        	
					        	
						        
						        if(iniciativa.getProyectoId() != null) {
						        	
						        	numero++;
							        HSSFRow IndPreARow = sheet.createRow(numero); 
							     
							        HSSFCell cellIndAPre = IndPreARow.createCell(0);
							        cellIndAPre.setCellStyle(style2);
							        cellIndAPre.setCellValue("Avance Actividades");
							        
							        
							        numero++;
							        numero++;
							        
							        
							        if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaTrimestral())) {
							        	
							        	 //cabeceras del mes
								        HSSFRow Cab2Row = sheet.createRow(numero);
								        int numCab2 =2;
								        for(int c= mesI; c<=mesF; c++) {
								        	
								        	if(c == 3) {
								        		
								        		HSSFCell cellCab = Cab2Row.createCell(numCab2);
									        	cellCab.setCellStyle(style3);
									        	cellCab.setCellValue(obtenerPeriodo(1));
							    	        	
									        	numCab2 = numCab2 +3;
								        		
								        	}else if(c == 6) {
								        		
								        		HSSFCell cellCab = Cab2Row.createCell(numCab2);
									        	cellCab.setCellStyle(style3);
									        	cellCab.setCellValue(obtenerPeriodo(2));
							    	        	
									        	numCab2 = numCab2 +3;
								        		
								        	}else if(c == 9) {
								        		
								        		HSSFCell cellCab = Cab2Row.createCell(numCab2);
									        	cellCab.setCellStyle(style3);
									        	cellCab.setCellValue(obtenerPeriodo(3));
							    	        	
									        	numCab2 = numCab2 +3;
								        		
								        	}else if(c == 12) {
								        		
								        		HSSFCell cellCab = Cab2Row.createCell(numCab2);
									        	cellCab.setCellStyle(style3);
									        	cellCab.setCellValue(obtenerPeriodo(4));
							    	        	
									        	numCab2 = numCab2 +3;
								        		
								        	}
								        		        	
								        }
								        
								        numero++;
								        
								        HSSFRow CabP2Row = sheet.createRow(numero);
								        
								        HSSFCell cellCa1 = CabP2Row.createCell(0);
								        cellCa1.setCellStyle(style);
								        cellCa1.setCellValue("Indicador");	
								        
								        HSSFCell cellCa2 = CabP2Row.createCell(1);
								        cellCa2.setCellStyle(style);
								        cellCa2.setCellValue("Unidad");	
								        
								        int num2 =2;
								        for(int c= mesI; c<=mesF; c++) {
								        	
								        	if(c == 3) {
								        		
								        		HSSFCell cellCabp1 = CabP2Row.createCell(num2);
									        	cellCabp1.setCellStyle(style);
									        	cellCabp1.setCellValue("Ejecutado");
									        	num2++;
									        	
									        	HSSFCell cellCabp2 = CabP2Row.createCell(num2);
									        	cellCabp2.setCellStyle(style);
									        	cellCabp2.setCellValue("Programado");
									        	num2++;
									        	
									        	HSSFCell cellCabp3 = CabP2Row.createCell(num2);
									        	cellCabp3.setCellStyle(style);
									        	cellCabp3.setCellValue("% Eficacia");
									        	num2++;
								        		
								        	}else if(c == 6) {
								        		
								        		HSSFCell cellCabp1 = CabP2Row.createCell(num2);
									        	cellCabp1.setCellStyle(style);
									        	cellCabp1.setCellValue("Ejecutado");
									        	num2++;
									        	
									        	HSSFCell cellCabp2 = CabP2Row.createCell(num2);
									        	cellCabp2.setCellStyle(style);
									        	cellCabp2.setCellValue("Programado");
									        	num2++;
									        	
									        	HSSFCell cellCabp3 = CabP2Row.createCell(num2);
									        	cellCabp3.setCellStyle(style);
									        	cellCabp3.setCellValue("% Eficacia");
									        	num2++;
								        		
								        	}else if(c == 9) {
								        		
								        		HSSFCell cellCabp1 = CabP2Row.createCell(num2);
									        	cellCabp1.setCellStyle(style);
									        	cellCabp1.setCellValue("Ejecutado");
									        	num2++;
									        	
									        	HSSFCell cellCabp2 = CabP2Row.createCell(num2);
									        	cellCabp2.setCellStyle(style);
									        	cellCabp2.setCellValue("Programado");
									        	num2++;
									        	
									        	HSSFCell cellCabp3 = CabP2Row.createCell(num2);
									        	cellCabp3.setCellStyle(style);
									        	cellCabp3.setCellValue("% Eficacia");
									        	num2++;
								        		
								        	}else if(c == 12) {
								        		
								        		HSSFCell cellCabp1 = CabP2Row.createCell(num2);
									        	cellCabp1.setCellStyle(style);
									        	cellCabp1.setCellValue("Ejecutado");
									        	num2++;
									        	
									        	HSSFCell cellCabp2 = CabP2Row.createCell(num2);
									        	cellCabp2.setCellStyle(style);
									        	cellCabp2.setCellValue("Programado");
									        	num2++;
									        	
									        	HSSFCell cellCabp3 = CabP2Row.createCell(num2);
									        	cellCabp3.setCellStyle(style);
									        	cellCabp3.setCellValue("% Eficacia");
									        	num2++;
								        		
								        	}
								        	
								        	
								        
								        }
								        
								        numero++;
							        	
							        }else if(iniciativa.getFrecuencia().equals(Frecuencia.getFrecuenciaMensual())) {
							        	
							        	 //cabeceras del mes

							        	HSSFRow Cab2Row = sheet.createRow(numero);
								        int numCab2 =2;
								        for(int c= mesI; c<=mesF; c++) {
								        	
								        	HSSFCell cellCab = Cab2Row.createCell(numCab2);
								        	cellCab.setCellStyle(style);
								        	cellCab.setCellValue(obtenerMes(c));
						    	        	
								        	numCab2 = numCab2 +3;		        	
								        }
								        
								        numero++;
								        
								        HSSFRow CabP2Row = sheet.createRow(numero);
								        
								        HSSFCell cell2Ca1 = CabP2Row.createCell(0);
								        cell2Ca1.setCellStyle(style);
								        cell2Ca1.setCellValue("Indicador");	
								        
								        HSSFCell cell2Ca2 = CabP2Row.createCell(1);
								        cell2Ca2.setCellStyle(style);
								        cell2Ca2.setCellValue("Unidad");	
								        
								        int num2 =2;
								        for(int c= mesI; c<=mesF; c++) {
								        	
								        	HSSFCell cellCabp1 = CabP2Row.createCell(num2);
								        	cellCabp1.setCellStyle(style);
								        	cellCabp1.setCellValue("Ejecutado");
								        	num2++;
								        	
								        	HSSFCell cellCabp2 = CabP2Row.createCell(num2);
								        	cellCabp2.setCellStyle(style);
								        	cellCabp2.setCellValue("Programado");
								        	num2++;
								        	
								        	HSSFCell cellCabp3 = CabP2Row.createCell(num2);
								        	cellCabp3.setCellStyle(style);
								        	cellCabp3.setCellValue("% Eficacia");
								        	num2++;
								        
								        }
							        	
							        }
							        
							        
							        
							        
							        
							        numero++;
						        	
						        	List<PryActividad> actividades =actividadservice.getActividades(iniciativa.getProyectoId());
						        	
						        	 for (Iterator<PryActividad> iterA = actividades.iterator(); iterA.hasNext();) {
						        		 
						        		 PryActividad actividad = iterA.next();
						        		 
						        		 indicadoresAvance.add(actividad.getIndicadorId());
						        		 
						        	 }
						        	 
						        	 //datos avance
						        	 
						        	 for (Iterator<Long> iterI = indicadoresAvance.iterator(); iterI.hasNext();) {
								        	
								        	Long indicadorId = iterI.next();
								        	int cel = 0;
								        	HSSFRow Row = sheet.createRow(numero); 
								        	
								        	Indicador indicador = (Indicador) iniciativaservice.load(Indicador.class, new Long(indicadorId));
								        	
								        	if(indicador != null) {
								        		
								        		HSSFCell cellInd = Row.createCell(cel);
								        		cellInd.setCellStyle(style);
								        		cellInd.setCellValue(indicador.getNombre());
								        		cel++;
								        		
								        		String unidad ="";
								        		if(indicador.getUnidadId() != null) {
								        			UnidadMedida uni = (UnidadMedida) iniciativaservice.load(UnidadMedida.class, indicador.getUnidadId());
								        			if(uni != null) {
								        				unidad = uni.getNombre();
								        			}
								        		}
								        		
								        		HSSFCell cellInd1 = Row.createCell(cel);
								        		cellInd1.setCellStyle(style1);		        		
								        		cellInd1.setCellValue(unidad);
								        		cel++;
								        		
								        		for(int y= mesI; y<=mesF; y++) {
								        	    	
								        			//obtener mediciones
								        			
								        			Double real=0.0;
								        			Double meta=0.0;
								        			Double porcentaje =0.0;
								        			
								        			
								        			real = obtenerMedicion(y, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieRealId());
								        			
								        			HSSFCell cellInd2 = Row.createCell(cel);
									        		cellInd2.setCellStyle(style1);
									        		cellInd2.setCellValue(real);
									        		cel++;
									        		
									        		meta = obtenerMedicion(y, new Integer(anio), indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId());
									        		
									        		HSSFCell cellInd3 = Row.createCell(cel);
									        		cellInd3.setCellStyle(style1);
									        		cellInd3.setCellValue(meta);
									        		cel++;
									        		
									        		if(real >0 && meta >0) {
								        				porcentaje = ((real/meta)*100);
								        			}
									        		
									        		HSSFCell cellInd4 = Row.createCell(cel);
									        		cellInd4.setCellStyle(style1);
									        		cellInd4.setCellValue(porcentaje);
									        		cel++;
								        			        	
										        		        	
										        }
								        	}        
									        
								        	numero++;
								        }
						        	 
						        	 
						        }
						        
						        
					        }
						       
					        
						}//
							
						
					}
					
					
					 // crea el archivo excel
					
					 for(int y=0; y<20; y++) {
				        	
				        	sheet.autoSizeColumn(y);
				     }
					
			        HSSFRow dataRow = sheet.createRow(numero++);

			        Date date = new Date();
			        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");


			        String archivo="IniciativasIndicador_"+hourdateFormat.format(date)+".xls";

			        response.setContentType("application/octet-stream");
			        response.setHeader("Content-Disposition","attachment;filename="+archivo);

			        ServletOutputStream file  = response.getOutputStream();
			        
			       

			        workbook.write(file);
			        file.close();
					
		    	}
		    	 		    	    	 
		    	   	 
			
			
		}
				
		iniciativaservice.close();
		return mapping.findForward(forward);
	}
	
	private String obtenerMes(int periodo) {
		String mes = "";
		
		switch(periodo) {
			case 1:
				mes= "Enero";
				break;
			case 2:
				mes= "Febrero";
				break;
			case 3:
				mes= "Marzo";
				break;
			case 4:
				mes= "Abril";
				break;
			case 5:
				mes= "Mayo";
				break;
			case 6:
				mes= "Junio";
				break;
			case 7:
				mes= "Julio";
				break;
			case 8:
				mes= "Agosto";
				break;
			case 9:
				mes= "Septiembre";
				break;
			case 10:
				mes= "Octubre";
				break;
			case 11:
				mes= "Noviembre";
				break;
			case 12:
				mes= "Diciembre";
				break;
		}
		
		return mes;
	}
	
	private String obtenerPeriodo(int periodo) {
		String mes = "";
		
		switch(periodo) {
			case 1:
				mes= "Periodo 1";
				break;
			case 2:
				mes= "Periodo 2";
				break;
			case 3:
				mes= "Periodo 3";
				break;
			case 4:
				mes= "Periodo 4";
				break;
			
		}
		
		return mes;
	}
	
	private Double obtenerMedicion(int periodo, int anio, Long indicadorId, Long SerieId) {
		Double resultado =0.0;
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		
		List <Medicion> medicion = strategosMedicionesService.getMedicionesPeriodo(indicadorId, SerieId, anio, anio, periodo, periodo);
		
		for (Iterator<Medicion> iter = medicion.iterator(); iter.hasNext();) {
			Medicion med = iter.next();
			resultado = med.getValor();			
		}
		
		strategosMedicionesService.close();
		return resultado;
	}
}
