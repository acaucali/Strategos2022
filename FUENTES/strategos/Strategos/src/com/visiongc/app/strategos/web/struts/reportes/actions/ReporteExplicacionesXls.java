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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.MemoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.util.TipoMemoExplicacion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCelda;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;

public class ReporteExplicacionesXls  extends VgcAction{
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url,
			String nombre) {
		navBar.agregarUrl(url, nombre);
	}
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		/** Se ejecuta el servicio del Action padre (obligatorio!!!) */
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		MessageResources messageResources = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		Date fechaDesdeParse =  new Date();
		Date fechaHastaParse =  new Date();
		
		String fechaDesde = (request.getParameter("fechaDesde"));		
		if(fechaDesde != null && !fechaDesde.equals("")) {
			fechaDesdeParse = (FechaUtil.convertirStringToDate(fechaDesde, VgcResourceManager.getResourceApp("formato.fecha.corta")));
		}
		
		String fechaHasta = (request.getParameter("fechaHasta"));
		if(fechaHasta != null && !fechaHasta.equals("")) {			
			fechaHastaParse = (FechaUtil.convertirStringToDate(fechaHasta, VgcResourceManager.getResourceApp("formato.fecha.corta")));
		}
		
		String todos = (request.getParameter("todos"));
		
		String objetoId = (request.getParameter("objetoId"));
		String objetoKey = (request.getParameter("objetoKey"));
		
		String atributoOrden = "";

		int pagina = 0;

		if (pagina < 1)
			pagina = 1;


		Map<String, Comparable> filtros = new HashMap<String, Comparable>();

		Paragraph texto;
		int columna = 1;

		Calendar fecha = Calendar.getInstance();
        int anoTemp = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        
        
        StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
        StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
        
        if (objetoKey.equals("Indicador"))
		{
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(objetoId));
			reporte.setSource(indicador.getNombre());
			strategosIndicadoresService.close();
		}
		else if (objetoKey.equals("Celda"))
		{
			StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
			Celda celda = (Celda)strategosCeldasService.load(Celda.class, new Long(objetoId));

			String nombreObjetoKey = "";
			if (celda.getIndicadoresCelda() != null)
			{
				if ((celda.getIndicadoresCelda().size() == 0) || (celda.getIndicadoresCelda().size() > 1))
					nombreObjetoKey = celda.getTitulo();
				else if (celda.getIndicadoresCelda().size() == 1)
				{
					IndicadorCelda indicadorCelda = (IndicadorCelda)celda.getIndicadoresCelda().toArray()[0];
					StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
					Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorCelda.getIndicador().getIndicadorId());
					nombreObjetoKey = indicador.getNombre();
					strategosIndicadoresService.close();
				}
			}
			else nombreObjetoKey = celda.getTitulo();
			reporte.setSource(nombreObjetoKey);

			strategosCeldasService.close();
		}
		else  if (objetoKey.equals("Iniciativa"))
		{
			StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			Iniciativa iniciativa = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, new Long(objetoId));

			reporte.setSource(iniciativa.getNombre());

			strategosIniciativasService.close();
		}
		else if (objetoKey.equals("Instrumento"))
		{
			StrategosInstrumentosService strategosInstrumentoService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
			Instrumentos instrumento = (Instrumentos)strategosInstrumentoService.load(Instrumentos.class, new Long(objetoId));
			
			reporte.setSource(instrumento.getNombreCorto());
			
			strategosInstrumentoService.close();			
		}
		else if (objetoKey.equals("Actividad"))
		{
			StrategosPryActividadesService strategosActividadService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
			
			PryActividad actividad = (PryActividad)strategosActividadService.load(PryActividad.class, new Long(objetoId));
			
			reporte.setSource(actividad.getNombre());
			
			strategosActividadService.close();
		}
		
		OrganizacionStrategos organizacion = ((OrganizacionStrategos)request.getSession().getAttribute("organizacion"));
		reporte.setOrganizacionNombre(organizacion.getNombre());
		
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
        style1.setWrapText(true);
                
        
        CellStyle style2 = workbook.createCellStyle();
        style2.setFont(font);

        HSSFRow headerRow = sheet.createRow(columna++);
        
        String header = "Reporte Resumido de Explicaciones";
        HSSFCell cell = headerRow.createCell(1);
        cell.setCellStyle(style);
        cell.setCellValue(header);
        
        HSSFRow OrgRow = sheet.createRow(columna++);
        HSSFCell cellOrg = OrgRow.createCell(0);
        cellOrg.setCellStyle(style);
        cellOrg.setCellValue("Organización");	
        
        String subTituloOrg = reporte.getOrganizacionNombre();
        HSSFCell cellOrgTitulo = OrgRow.createCell(1);
        cellOrgTitulo.setCellStyle(style1);
        cellOrgTitulo.setCellValue(subTituloOrg);	
        
        
        HSSFRow ObjRow = sheet.createRow(columna++);
		String nombre = messageResources.getMessage("jsp.reporte.explicacion.objeto") + " - " + objetoKey ;
	    HSSFCell cellSub = ObjRow.createCell(0);
	    cellSub.setCellStyle(style);
	    cellSub.setCellValue(nombre);
	    
	    String nombreObj = reporte.getSource();
	    HSSFCell cellSubObj = ObjRow.createCell(1);
	    cellSubObj.setCellStyle(style1);
	    cellSubObj.setCellValue(nombreObj);
	    

        
        if ((objetoId != null) && (!objetoId.equals("")) && Long.parseLong(objetoId) != 0)
			filtros.put("objetoId", objetoId);
        
        List<Explicacion> explicaciones = strategosExplicacionesService.getExplicaciones(pagina, 30, "fecha", "DESC", true, filtros).getLista();
        List<Explicacion> expReporte = new ArrayList<>();

		if(explicaciones != null && explicaciones.size() > 0) {
			for(Explicacion exp: explicaciones) {	
				if(!todos.equals("true")) {
					if(fechaDesdeParse.compareTo(exp.getFecha()) <= 0 && fechaHastaParse.compareTo(exp.getFecha()) >= 0) {
				    	expReporte.add(exp);
					}			
				}else {
					expReporte.add(exp);
				}
			}
		}	
		
		if (expReporte != null && expReporte.size() > 0) {
			sheet.createRow(columna++);
			
			HSSFRow CabPRow = sheet.createRow(columna++);
			
			HSSFCell dataRow = CabPRow.createCell(0);
			dataRow.setCellStyle(style);
			dataRow.setCellValue(messageResources.getMessage("jsp.gestionarexplicaciones.columna.fecha"));
			
			HSSFCell dataRow1 = CabPRow.createCell(1);
			dataRow1.setCellStyle(style);
			dataRow1.setCellValue(messageResources.getMessage("jsp.gestionarexplicaciones.columna.titulo"));
			
			HSSFCell dataRow2 = CabPRow.createCell(2);
			dataRow2.setCellStyle(style);
			dataRow2.setCellValue(messageResources.getMessage("jsp.editarexplicacion.ficha.tipomemo.descripcion"));
			
			HSSFCell dataRow3 = CabPRow.createCell(3);
			dataRow3.setCellStyle(style);
			dataRow3.setCellValue(messageResources.getMessage("jsp.editarexplicacion.ficha.tipomemo.causas"));
			
			HSSFCell dataRow4 = CabPRow.createCell(4);
			dataRow4.setCellStyle(style);
			dataRow4.setCellValue(messageResources.getMessage("jsp.editarexplicacion.ficha.tipomemo.correctivos"));								
	        
	        HSSFRow Row = sheet.createRow(columna++);
	        
	        
	        for (Explicacion exp : expReporte) {
	        	int cel = 0;	        	
	        	HSSFCell cellFecha = Row.createCell(cel);
	        	cellFecha.setCellStyle(style1);
	        	if(exp.getFechaFormateada() != null) {
		    		cellFecha.setCellValue(exp.getFechaFormateada());
		        }else {
		        	cellFecha.setCellValue("");
		        }
	        	cel++;
	        	
	        	HSSFCell cell1 = Row.createCell(cel);
	        	cell1.setCellStyle(style1);		        		
	        	cell1.setCellValue(exp.getTitulo());	
	        	cel++;
	        	
	        	String memoDescripcion="";
		    	String memoCausas="";
		    	String memoCorrectivos = "";

		    	for (Iterator<?> i = exp.getMemosExplicacion().iterator(); i.hasNext(); )
				{
					MemoExplicacion memoExplicacion = (MemoExplicacion)i.next();
					Byte tipoMemo = memoExplicacion.getPk().getTipo();
					if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_DESCRIPCION)))
						memoDescripcion=memoExplicacion.getMemo();
					else if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_CAUSAS)))
						memoCausas=memoExplicacion.getMemo();
					else if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_CORRECTIVOS)))
						memoCorrectivos = memoExplicacion.getMemo();

				}
		    	HSSFCell cell2 = Row.createCell(cel);
		    	cell2.setCellStyle(style1);		        		
	        	cell2.setCellValue(memoDescripcion);
	        	cel++;
	        	
	        	HSSFCell cell3 = Row.createCell(cel);
	        	cell3.setCellStyle(style1);		        		
	        	cell3.setCellValue(memoCausas);
	        	cel++;
	        	
	        	HSSFCell cell4 = Row.createCell(cel);
	        	cell4.setCellStyle(style1);		        		
	        	cell4.setCellValue(memoCorrectivos);	        	
	        }
	        
	        for(int y=0; y<5; y++) {
	        	
	        	sheet.autoSizeColumn(y);
	       }
	        
	        Date date = new Date();
	        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");


	        String archivo="ExplicacionesInstrumento_"+hourdateFormat.format(date)+".xls";

	        response.setContentType("application/octet-stream");
	        response.setHeader("Content-Disposition","attachment;filename="+archivo);

	        ServletOutputStream file  = response.getOutputStream();

	        workbook.write(file);
	        file.close();
			
		}
		
		forward="exito";
		strategosExplicacionesService.close();
		
		return mapping.findForward(forward);
	}
}
