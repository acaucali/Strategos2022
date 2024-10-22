package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.IndicadorEstado;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectiva;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectivaPK;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.servicio.Servicio.EjecutarTipo;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.app.strategos.planes.model.util.TipoMeta;
import com.visiongc.app.strategos.indicadores.model.Formula;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Organizacion;

public class PlanIndicadoresXlsAction extends VgcAction {
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
		navBar.agregarUrl(url, nombre);
	}

	private List<Indicador> indFinales = new ArrayList<Indicador>();
	int row2 = 3;
	
	@SuppressWarnings("unlikely-arg-type")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.execute(mapping, form, request, response);
		String forward = mapping.getParameter();

		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();

		reporte.clear();

		reporte.setPlanId(
				request.getParameter("planId") != null ? Long.parseLong(request.getParameter("planId")) : null);

		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance()
				.openStrategosPerspectivasService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
				.openStrategosIndicadoresService();
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance()
				.openStrategosPlanesService();
		StrategosOrganizacionesService strategosOrganizacionService = StrategosServiceFactory.getInstance().
				openStrategosOrganizacionesService();
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().
				openStrategosMedicionesService();

		Plan plan = (Plan) strategosPerspectivasService.load(Plan.class, reporte.getPlanId());
		reporte.setPlantillaPlanes((PlantillaPlanes) strategosPerspectivasService.load(PlantillaPlanes.class,
				new Long(plan.getMetodologiaId())));

		Perspectiva perspectiva = null;
		perspectiva = strategosPerspectivasService.getPerspectivaRaiz(reporte.getPlanId());

		ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan();

		perspectiva.setConfiguracionPlan(configuracionPlan);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet hoja1 = workbook.createSheet();
		workbook.setSheetName(0, "Hoja 1");
		HSSFSheet hoja2 = workbook.createSheet();
		workbook.setSheetName(1, "Hoja 2");

		CellStyle headerStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerStyle.setFont(font);

		Font font2 = workbook.createFont();
		font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font2.setColor(IndexedColors.WHITE.getIndex());

		CellStyle styleTitulo = workbook.createCellStyle();
		styleTitulo.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleTitulo.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleTitulo.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleTitulo.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleTitulo.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleTitulo.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleTitulo.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styleTitulo.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		styleTitulo.setRightBorderColor(IndexedColors.BLACK.getIndex());
		styleTitulo.setTopBorderColor(IndexedColors.BLACK.getIndex());
		styleTitulo.setFont(font2);
		styleTitulo.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
		styleTitulo.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.index);
		styleTitulo.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

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

		CellStyle style2 = workbook.createCellStyle();
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style2.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style2.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style2.setFont(font);
		style2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		style2.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle styleDep = workbook.createCellStyle();
		styleDep.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleDep.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleDep.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleDep.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleDep.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleDep.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleDep.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styleDep.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		styleDep.setRightBorderColor(IndexedColors.BLACK.getIndex());
		styleDep.setTopBorderColor(IndexedColors.BLACK.getIndex());
		styleDep.setFont(font);
		styleDep.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		styleDep.setFillBackgroundColor(IndexedColors.PALE_BLUE.index);
		styleDep.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle styleRoja = workbook.createCellStyle();
		styleRoja.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleRoja.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleRoja.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleRoja.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleRoja.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styleRoja.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		styleRoja.setRightBorderColor(IndexedColors.BLACK.getIndex());
		styleRoja.setTopBorderColor(IndexedColors.BLACK.getIndex());
		styleRoja.setFillBackgroundColor(IndexedColors.RED.index);
		styleRoja.setFillForegroundColor(IndexedColors.RED.index);
		styleRoja.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle styleVerde = workbook.createCellStyle();
		styleVerde.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleVerde.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleVerde.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleVerde.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleVerde.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styleVerde.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		styleVerde.setRightBorderColor(IndexedColors.BLACK.getIndex());
		styleVerde.setTopBorderColor(IndexedColors.BLACK.getIndex());
		styleVerde.setFillBackgroundColor(IndexedColors.GREEN.index);
		styleVerde.setFillForegroundColor(IndexedColors.GREEN.index);
		styleVerde.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle styleAmarillo = workbook.createCellStyle();
		styleAmarillo.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleAmarillo.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleAmarillo.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleAmarillo.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleAmarillo.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styleAmarillo.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		styleAmarillo.setRightBorderColor(IndexedColors.BLACK.getIndex());
		styleAmarillo.setTopBorderColor(IndexedColors.BLACK.getIndex());
		styleAmarillo.setFillBackgroundColor(IndexedColors.YELLOW.index);
		styleAmarillo.setFillForegroundColor(IndexedColors.YELLOW.index);
		styleAmarillo.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		HSSFRow headerRow = hoja1.createRow(0);
		String header = "Reporte de Dependencias Omisivas";
		HSSFCell cell = headerRow.createCell(0);
		cell.setCellStyle(headerStyle);
		cell.setCellValue(header);

		HSSFRow triRow = hoja1.createRow(2);
		HSSFCell cellTri = triRow.createCell(0);
		cellTri.setCellStyle(styleDep);
		cellTri.setCellValue(mensajes.getMessage("jsp.reportes.plan.meta.plan"));

		HSSFCell cellTri1 = triRow.createCell(1);
		cellTri1.setCellStyle(style1);
		cellTri1.setCellValue(perspectiva.getNombreCompleto());

		HSSFRow nivelRow = hoja1.createRow(4);
		HSSFCell cellOrg = nivelRow.createCell(0);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("Indicador PEI");

		cellOrg = nivelRow.createCell(1);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("Objetivo Estratégico");

		cellOrg = nivelRow.createCell(2);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("Logro Anual Objetivo");

		cellOrg = nivelRow.createCell(3);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("Logro Parcial Objetivo");

		cellOrg = nivelRow.createCell(4);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("Meta Anual Indicador");

		cellOrg = nivelRow.createCell(5);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("Real (Ejecutado)");

		cellOrg = nivelRow.createCell(6);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("Estado Parcial");

		cellOrg = nivelRow.createCell(7);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("Estado Anual");

		cellOrg = nivelRow.createCell(8);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("Fecha Ultima Medición");

		cellOrg = nivelRow.createCell(9);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("Aporte Anual al Objetivo");

		cellOrg = nivelRow.createCell(10);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("Aporte Parcial al Objetivo");

		cellOrg = nivelRow.createCell(11);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("Peso");

		cellOrg = nivelRow.createCell(12);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("Frecuencia");

		cellOrg = nivelRow.createCell(13);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("Alerta");
		
		// HOJA 2 TITULOS
		
		headerRow = hoja2.createRow(0);
		header = "Indicador Insumos Formulas PEI";
		cell = headerRow.createCell(0);		
		cell.setCellValue(header);
		
		triRow = hoja2.createRow(2);
		cellTri = triRow.createCell(0);
		cellTri.setCellStyle(styleTitulo);
		cellTri.setCellValue("Indicador PEI");
				
		cellTri = triRow.createCell(1);
		cellTri.setCellStyle(styleTitulo);
		cellTri.setCellValue("Nombre Dependencia");
				
		cellTri = triRow.createCell(2);
		cellTri.setCellStyle(styleTitulo);
		cellTri.setCellValue("Indicador Dependencia");
			
		cellTri = triRow.createCell(3);
		cellTri.setCellStyle(styleTitulo);
		cellTri.setCellValue("Ejecutado");
				
		cellTri = triRow.createCell(4);
		cellTri.setCellStyle(styleTitulo);
		cellTri.setCellValue("Ultima Medicion");

		Map<String, Object> filtros = new HashMap<String, Object>();

		filtros.put("padreId", perspectiva.getPerspectivaId());
		String[] orden = new String[1];
		String[] tipoOrden = new String[1];
		orden[0] = "nombre";
		tipoOrden[0] = "asc";
		Integer count = 0;
		Integer nivel = 0;
		int row = 5;
				
		this.row2 = 3;
		Integer anioActual = (new Date().getYear())+1900;		
		
		List<Indicador> indFinales = null; 

		List<Perspectiva> perspectivas = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);
		if (perspectivas.size() > 0) {
			for (Perspectiva perspectivaHija : perspectivas) {

				filtros.put("padreId", perspectivaHija.getPerspectivaId());
				List<Perspectiva> subPerspectivas = strategosPerspectivasService.getPerspectivas(orden, tipoOrden,
						filtros);
				if (subPerspectivas.size() > 0) {
					for (Perspectiva perspectivaSubHija : subPerspectivas) {

						filtros = new HashMap<String, Object>();
						filtros.put("perspectivaId", perspectivaSubHija.getPerspectivaId().toString());
						List<Indicador> indicadores = strategosIndicadoresService
								.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, true).getLista();

						if (indicadores.size() > 0) {
							for (Indicador ind : indicadores) {																

								List<?> metas = strategosMetasService.getMetasAnuales(ind.getIndicadorId(),
										plan.getPlanId(), anioActual,
										anioActual, false);
								if (metas.size() > 0)
									ind.setMetaAnual(((Meta) metas.get(0)).getValor());
								metas = strategosMetasService.getMetasParciales(ind.getIndicadorId(), plan.getPlanId(), ind.getFrecuencia(), ind.getOrganizacion().getMesCierre(), ind.getCorte(), ind.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcial(), ind.getFechaUltimaMedicionAno(), ind.getFechaUltimaMedicionAno(), ind.getFechaUltimaMedicionPeriodo(), ind.getFechaUltimaMedicionPeriodo());
								if (metas.size() > 0)
								{
									Meta metaParcial = (Meta)metas.get(0);
									ind.setMetaParcial(metaParcial.getValor());
								}

								List<?> estados = strategosPlanesService.getIndicadorEstados(ind.getIndicadorId(),
										plan.getPlanId(), ind.getFrecuencia(),
										TipoIndicadorEstado.getTipoIndicadorEstadoParcial(),
										ind.getFechaUltimaMedicionAno(), ind.getFechaUltimaMedicionAno(),
										ind.getFechaUltimaMedicionPeriodo(), ind.getFechaUltimaMedicionPeriodo());
								if (estados.size() > 0) {
									IndicadorEstado indEstado = (IndicadorEstado) estados.get(0);									
									ind.setEstadoParcial(indEstado.getEstado());
								}
								estados = strategosPlanesService.getIndicadorEstados(ind.getIndicadorId(),
										plan.getPlanId(), ind.getFrecuencia(),
										TipoIndicadorEstado.getTipoIndicadorEstadoAnual(),
										ind.getFechaUltimaMedicionAno(), ind.getFechaUltimaMedicionAno(),
										ind.getFechaUltimaMedicionPeriodo(), ind.getFechaUltimaMedicionPeriodo());
								if (estados.size() > 0) {
									IndicadorEstado indEstado = (IndicadorEstado) estados.get(0);									
									ind.setEstadoAnual(indEstado.getEstado());
								}
								
								IndicadorPerspectivaPK indicadorPerspectivaPk = new IndicadorPerspectivaPK();
								indicadorPerspectivaPk.setIndicadorId(ind.getIndicadorId());
								indicadorPerspectivaPk.setPerspectivaId(perspectivaSubHija.getPerspectivaId());
								IndicadorPerspectiva indicadorPerspectiva = (IndicadorPerspectiva)strategosIndicadoresService.load(IndicadorPerspectiva.class, indicadorPerspectivaPk);
								if (indicadorPerspectiva != null)
									ind.setPeso(indicadorPerspectiva.getPeso());
								
								if (ind.getUltimaMedicion() != null && (ind.getMetaParcial() != null || ind.getUltimaMedicionAnoAnterior() != null))
								{
									Double zonaVerde = strategosIndicadoresService.zonaVerde(ind, ind.getFechaUltimaMedicionAno(), ind.getFechaUltimaMedicionPeriodo(), ( ind.getMetaParcial() != null ? ind.getMetaParcial() : ind.getUltimaMedicionAnoAnterior()), strategosMedicionesService);
				  					Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(ind, ind.getFechaUltimaMedicionAno(), ind.getFechaUltimaMedicionPeriodo(), (ind.getMetaParcial() != null ? ind.getMetaParcial() : ind.getUltimaMedicionAnoAnterior()), zonaVerde, strategosMedicionesService);
				  					Byte alerta = new Servicio().calcularAlertaXPeriodos(EjecutarTipo.getEjecucionAlertaXPeriodos(), ind.getCaracteristica(), zonaVerde, zonaAmarilla, ind.getUltimaMedicion(), ind.getMetaParcial(), null, ind.getUltimaMedicionAnoAnterior());
				  					ind.setAlerta(alerta);				  					
								}

								HSSFRow OrgRow1 = hoja1.createRow(row);
								HSSFCell cellOrg1 = OrgRow1.createCell(0);
								cellOrg1.setCellStyle(style1);
								cellOrg1.setCellValue(ind.getNombre());

								cellOrg1 = OrgRow1.createCell(1);
								cellOrg1.setCellStyle(style1);
								cellOrg1.setCellValue(perspectivaSubHija.getNombre());

								cellOrg1 = OrgRow1.createCell(2);
								cellOrg1.setCellStyle(style1);
								cellOrg1.setCellValue(perspectivaSubHija.getUltimaMedicionAnualFormateado());

								cellOrg1 = OrgRow1.createCell(3);
								cellOrg1.setCellStyle(style1);
								cellOrg1.setCellValue(perspectivaSubHija.getUltimaMedicionParcialFormateado());

								cellOrg1 = OrgRow1.createCell(4);
								cellOrg1.setCellStyle(style1);
								cellOrg1.setCellValue(ind.getMetaAnualFormateada());

								cellOrg1 = OrgRow1.createCell(5);
								cellOrg1.setCellStyle(style1);
								cellOrg1.setCellValue(ind.getUltimaMedicionFormateada());

								cellOrg1 = OrgRow1.createCell(6);
								cellOrg1.setCellStyle(style1);
								if(ind.getEstadoParcial() != null)
									cellOrg1.setCellValue(ind.getEstadoParcialFormateado());
								else
									cellOrg1.setCellValue("");

								cellOrg1 = OrgRow1.createCell(7);
								cellOrg1.setCellStyle(style1);
								if(ind.getEstadoAnual() != null)
									cellOrg1.setCellValue(ind.getEstadoAnualFormateado());
								else
									cellOrg1.setCellValue("");

								cellOrg1 = OrgRow1.createCell(8);
								cellOrg1.setCellStyle(style1);
								cellOrg1.setCellValue(ind.getFechaUltimaMedicion());

								cellOrg1 = OrgRow1.createCell(9);
								cellOrg1.setCellStyle(style1);
								if ((ind.getPeso() != null ) && ind.getEstadoAnual() != null)
									cellOrg1.setCellValue(((ind.getPeso() / 100) * ind.getEstadoAnual()));
								else
									cellOrg1.setCellValue(ind.getEstadoAnualFormateado());

								cellOrg1 = OrgRow1.createCell(10);
								cellOrg1.setCellStyle(style1);
								if ((ind.getPeso() != null) && ind.getEstadoParcial() != null)
									cellOrg1.setCellValue(((ind.getPeso() / 100) * ind.getEstadoParcial()));
								else
									cellOrg1.setCellValue(ind.getEstadoParcialFormateado());

								cellOrg1 = OrgRow1.createCell(11);
								cellOrg1.setCellStyle(style1);
								if (ind.getPeso() == null)
									cellOrg1.setCellValue("");
								else
									cellOrg1.setCellValue(ind.getPeso());

								String frecuencia = obtenerFrecuencia(ind.getFrecuencia());
								cellOrg1 = OrgRow1.createCell(12);
								cellOrg1.setCellStyle(style1);
								cellOrg1.setCellValue(frecuencia);

								Byte alerta = ind.getAlerta();
								cellOrg1 = OrgRow1.createCell(13);
								if (alerta == null) {
									cellOrg1.setCellStyle(style1);
									cellOrg1.setCellValue("");
								} else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
									cellOrg1.setCellStyle(styleRoja);
									cellOrg1.setCellValue("");
								} else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue()) {
									cellOrg1.setCellStyle(styleVerde);
									cellOrg1.setCellValue("");
								} else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
									cellOrg1.setCellStyle(styleAmarillo);
									cellOrg1.setCellValue("");
								}

								row++;
								if(ind.getNaturaleza().equals((byte) 1)) {
									Set<Formula> formulasSet = ind.getFormulas();		
									if (!formulasSet.isEmpty()) {
									    for(Formula formula : formulasSet) {    
										    if(formula.getInsumos() != null) {		    	
										    	Set<InsumoFormula> insumos = formula.getInsumos();			    	
										    	if (!insumos.isEmpty()) {	
										    		for(InsumoFormula insumo : insumos) {		    			
										    			Indicador hijo = (Indicador) strategosIndicadoresService.load(Indicador.class, insumo.getInsumo().getIndicadorId());			    			
										    			if(hijo.getNaturaleza().equals((byte) 1)) 
										    				verificarInsumos(ind, hijo , hoja2, style1);
										    			else {
										    				HSSFRow OrgRow2 = hoja2.createRow(this.row2);										    				
										    				Organizacion OrganizacionHijo = (Organizacion) strategosOrganizacionService.load(Organizacion.class, hijo.getOrganizacionId());										    				
															HSSFCell cellOrg2 = OrgRow2.createCell(0);
															cellOrg2.setCellStyle(style1);
															cellOrg2.setCellValue(ind.getNombre());
															
															cellOrg2 = OrgRow2.createCell(1);
															cellOrg2.setCellStyle(style1);
															cellOrg2.setCellValue(OrganizacionHijo.getNombre());
															
															cellOrg2 = OrgRow2.createCell(2);
															cellOrg2.setCellStyle(style1);
															cellOrg2.setCellValue(hijo.getNombre());
															
															cellOrg2 = OrgRow2.createCell(3);
															cellOrg2.setCellStyle(style1);
															cellOrg2.setCellValue(hijo.getUltimaMedicionFormateada());
															
															cellOrg2 = OrgRow2.createCell(4);
															cellOrg2.setCellStyle(style1);
															cellOrg2.setCellValue(hijo.getFechaUltimaMedicion());
															this.row2++;										    						
										    			}
										    		}			    		
										    	}		    			    	
										    }
									    }
									}									
								}
							}
						}
					}
				}
			}
		}
				

		strategosPlanesService.close();
		strategosPerspectivasService.close();
		strategosIndicadoresService.close();
		strategosMetasService.close();
		strategosOrganizacionService.close();
		strategosMedicionesService.close();
		
		
		for (int y = 0; y < 50; y++) {
			hoja1.autoSizeColumn(y);
		}
		
		for (int y = 0; y < 50; y++) {
			hoja2.autoSizeColumn(y);
		}

		// crea el archivo excel

		Date dateXls = new Date();
		SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");

		String archivo = "ReporteIndicadoresPlan_" + hourdateFormat.format(dateXls) + ".xls";

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + archivo);

		ServletOutputStream file = response.getOutputStream();

		workbook.write(file);
		file.close();

		forward = "exito";

		return mapping.findForward(forward);
	}
	
	public void verificarInsumos(Indicador indicador, Indicador subIndicador, HSSFSheet hoja2, CellStyle style1) {				
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
				.openStrategosIndicadoresService();		
		StrategosOrganizacionesService strategosOrganizacionService = StrategosServiceFactory.getInstance().
				openStrategosOrganizacionesService();
		Set<Formula> formulasSet = subIndicador.getFormulas();		
		if (!formulasSet.isEmpty()) {
		    for(Formula formula : formulasSet) {    
			    if(formula.getInsumos() != null) {		    	
			    	Set<InsumoFormula> insumos = formula.getInsumos();			    	
			    	if (!insumos.isEmpty()) {	
			    		for(InsumoFormula insumo : insumos) {		    			
			    			Indicador hijo = (Indicador) strategosIndicadoresService.load(Indicador.class, insumo.getInsumo().getIndicadorId());			    			
			    			if(hijo.getNaturaleza().equals((byte) 1)) 
			    				verificarInsumos(indicador , hijo, hoja2, style1);
			    			else {			    				
			    				HSSFRow OrgRow2 = hoja2.createRow(this.row2);
			    				Organizacion OrganizacionHijo = (Organizacion) strategosOrganizacionService.load(Organizacion.class, hijo.getOrganizacionId());										    				
								HSSFCell cellOrg2 = OrgRow2.createCell(0);
								cellOrg2.setCellStyle(style1);
								cellOrg2.setCellValue(indicador.getNombre());
								
								cellOrg2 = OrgRow2.createCell(1);
								cellOrg2.setCellStyle(style1);
								cellOrg2.setCellValue(OrganizacionHijo.getNombre());
								
								cellOrg2 = OrgRow2.createCell(2);
								cellOrg2.setCellStyle(style1);
								cellOrg2.setCellValue(hijo.getNombre());
								
								cellOrg2 = OrgRow2.createCell(3);
								cellOrg2.setCellStyle(style1);
								cellOrg2.setCellValue(hijo.getUltimaMedicionFormateada());
								
								cellOrg2 = OrgRow2.createCell(4);
								cellOrg2.setCellStyle(style1);
								cellOrg2.setCellValue(hijo.getFechaUltimaMedicion());
								this.row2++;		    				
			    			}
			    		}			    		
			    	}		    			    	
			    }
		    }
		}
	}	
 
	public String obtenerFrecuencia(Byte frecuenciaId) {
		String frecuencia = "";

		if (frecuenciaId == 0) {
			frecuencia = "Diaria";
		}
		if (frecuenciaId == 1) {
			frecuencia = "Semanal";
		}
		if (frecuenciaId == 2) {
			frecuencia = "Quincenal";
		}
		if (frecuenciaId == 3) {
			frecuencia = "Mensual";
		}
		if (frecuenciaId == 4) {
			frecuencia = "Bimestral";
		}
		if (frecuenciaId == 5) {
			frecuencia = "Trimestral";
		}
		if (frecuenciaId == 6) {
			frecuencia = "Cuatrimestral";
		}
		if (frecuenciaId == 7) {
			frecuencia = "Semestral";
		}
		if (frecuenciaId == 8) {
			frecuencia = "Anual";
		}
		return frecuencia;
	}
}
