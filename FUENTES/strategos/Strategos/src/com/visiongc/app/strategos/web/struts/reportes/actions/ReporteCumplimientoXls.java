package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.text.ParseException;
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

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.NavegadorForm;

public class ReporteCumplimientoXls extends VgcAction {
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
		navBar.agregarUrl(url, nombre);
	}

	@SuppressWarnings("unlikely-arg-type")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.execute(mapping, form, request, response);
		String forward = mapping.getParameter();

		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		String alcance = (request.getParameter("alcance"));
		String anio = (request.getParameter("anio"));
		reporte.setAno(Integer.parseInt(anio));

		StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance()
				.openStrategosOrganizacionesService();
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance()
				.openStrategosPlanesService();
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance()
				.openStrategosIniciativasService();

		Map<String, Object> filtros = new HashMap<String, Object>();
		List<OrganizacionStrategos> organizaciones = organizacionservice
				.getOrganizaciones(0, 0, "organizacionId", "ASC", true, filtros).getLista();

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

		CellStyle styleOrg = workbook.createCellStyle();

		styleOrg.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleOrg.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleOrg.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleOrg.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleOrg.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styleOrg.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		styleOrg.setRightBorderColor(IndexedColors.BLACK.getIndex());
		styleOrg.setTopBorderColor(IndexedColors.BLACK.getIndex());
		styleOrg.setFont(font2);
		styleOrg.setFillForegroundColor(IndexedColors.LIGHT_BLUE.index);
		styleOrg.setFillBackgroundColor(IndexedColors.LIGHT_BLUE.index);
		styleOrg.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

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

		CellStyle style2 = workbook.createCellStyle();
		style2.setFont(font);

		HSSFRow headerRow = sheet.createRow(0);

		String header = "Reporte Cumplimiento";
		HSSFCell cell = headerRow.createCell(1);
		cell.setCellStyle(headerStyle);
		cell.setCellValue(header);

		// organizacion seleccionada
		if (request.getParameter("alcance").equals("1")) {

			filtros = new HashMap<String, Object>();
			int row = 6;
			OrganizacionStrategos org = ((OrganizacionStrategos) request.getSession().getAttribute("organizacion"));
			reporte.setOrganizacionNombre(org.getNombre());

			HSSFRow OrgRow = sheet.createRow(3);
			HSSFCell cellOrg = OrgRow.createCell(0);
			cellOrg.setCellStyle(styleOrg);
			cellOrg.setCellValue("Organización");

			String nombre = org.getNombre();
			HSSFCell cellOrg1 = OrgRow.createCell(1);
			cellOrg1.setCellStyle(style1);
			cellOrg1.setCellValue(nombre);

			if ((org.getOrganizacionId() != null))
				filtros.put("organizacionId", org.getOrganizacionId().toString());

			PaginaLista paginaPlanes = strategosPlanesService.getPlanes(1, 30, "nombre", "ASC", true, filtros);
			List<Plan> planes = paginaPlanes.getLista();
			// 1.0 Indicadores Planes
			if (planes.size() > 0) {
				for (Plan plan : planes) {
					if (plan.getActivo()) {
						row = dibujarIndicadoresPlan(row, sheet, style, style1, style2, styleRoja, styleVerde,
								styleAmarillo, plan, strategosPlanesService);
					}
				}
			}
			strategosPlanesService.close();

			filtros.put("anio", anio);
			PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(1, 30, "nombre", "ASC", true,
					filtros);
			List<Iniciativa> iniciativas = paginaIniciativas.getLista();
			// INICIATIVAS
			if (iniciativas.size() > 0) {
				List<Iniciativa> iniAtrasados = new ArrayList();
				iniAtrasados = obtenerIniciativas(iniciativas, row, sheet, style1);

				if (iniAtrasados.size() > 0) {
					row = dibujarIniciativas(row, sheet, style, style1, style2, styleRoja, styleVerde, styleAmarillo,
							iniAtrasados, request);
					// 2.1 Actividades
					row = dibujarActividades(row, sheet, style, style1, style2, styleRoja, styleVerde, styleAmarillo,
							iniAtrasados, request);
				}
			}

			strategosIniciativasService.close();

			// 3.0 Analisis Estrategicos
			Integer periodo = obtenerFecha((byte) 5);
			String fecha = String.valueOf((periodo - 1)) + "/" + String.valueOf(new Date().getYear() + 1900);
			SimpleDateFormat date = new SimpleDateFormat("MM/yyyy");
			Date fechaActualDate = date.parse(fecha);

			row = dibujarEventos(row, sheet, style, style1, date, fechaActualDate, org.getOrganizacionId(), mensajes);

			// 4.0 Informe Cualitativo
			row = dibujarInformes(row, sheet, style, style1, date, fechaActualDate, org.getOrganizacionId(), mensajes);
		}
		// suborganizaciones
		else if (request.getParameter("alcance").equals("4")) {

			filtros = new HashMap<String, Object>();
			int row = 6;

			List<OrganizacionStrategos> organizacionesSub = organizacionservice.getOrganizacionHijas(
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId(),
					true);

			OrganizacionStrategos organizacion = ((OrganizacionStrategos) request.getSession()
					.getAttribute("organizacion"));
			reporte.setOrganizacionNombre(organizacion.getNombre());

			HSSFRow OrgRow = sheet.createRow(3);
			HSSFCell cellOrg = OrgRow.createCell(0);
			cellOrg.setCellStyle(styleOrg);
			cellOrg.setCellValue("Organización");

			String nombre = organizacion.getNombre();
			HSSFCell cellOrg1 = OrgRow.createCell(1);
			cellOrg1.setCellStyle(style1);
			cellOrg1.setCellValue(nombre);

			if ((organizacion.getOrganizacionId() != null))
				filtros.put("organizacionId", organizacion.getOrganizacionId().toString());

			PaginaLista paginaPlanes = strategosPlanesService.getPlanes(1, 30, "nombre", "ASC", true, filtros);
			List<Plan> planes = paginaPlanes.getLista();
			// 1.0 Indicadores Planes
			if (planes.size() > 0) {
				for (Plan plan : planes) {
					if (plan.getActivo()) {
						row = dibujarIndicadoresPlan(row, sheet, style, style1, style2, styleRoja, styleVerde,
								styleAmarillo, plan, strategosPlanesService);
					}
				}
			}

			filtros.put("anio", anio);
			PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(1, 30, "nombre", "ASC", true,
					filtros);
			List<Iniciativa> iniciativas = paginaIniciativas.getLista();
			// INICIATIVAS
			if (iniciativas.size() > 0) {
				List<Iniciativa> iniAtrasados = new ArrayList();
				iniAtrasados = obtenerIniciativas(iniciativas, row, sheet, style1);

				if (iniAtrasados.size() > 0) {
					row = dibujarIniciativas(row, sheet, style, style1, style2, styleRoja, styleVerde, styleAmarillo,
							iniAtrasados, request);
					// 2.1 Actividades
					row = dibujarActividades(row, sheet, style, style1, style2, styleRoja, styleVerde, styleAmarillo,
							iniAtrasados, request);
				}
			}

			// 3.0 Analisis Estrategicos
			Integer periodo = obtenerFecha((byte) 5);
			String fecha = String.valueOf((periodo - 1)) + "/" + String.valueOf(new Date().getYear() + 1900);
			SimpleDateFormat date = new SimpleDateFormat("MM/yyyy");
			Date fechaActualDate = date.parse(fecha);

			row = dibujarEventos(row, sheet, style, style1, date, fechaActualDate, organizacion.getOrganizacionId(),
					mensajes);

			// 4.0 Informe Cualitativo
			row = dibujarInformes(row, sheet, style, style1, date, fechaActualDate, organizacion.getOrganizacionId(),
					mensajes);

			// suborganizaciones
			if (organizacionesSub.size() > 0 || organizacionesSub != null) {
				for (Iterator<OrganizacionStrategos> iter = organizacionesSub.iterator(); iter.hasNext();) {
					OrganizacionStrategos org = iter.next();
					HSSFRow OrgRow1 = sheet.createRow(row);
					HSSFCell cellOrg2 = OrgRow1.createCell(0);
					cellOrg2.setCellStyle(styleOrg);
					cellOrg2.setCellValue("Organización");

					nombre = org.getNombre();
					HSSFCell cellOrg3 = OrgRow1.createCell(1);
					cellOrg3.setCellStyle(style1);
					cellOrg3.setCellValue(nombre);
					row = row + 3;

					filtros.clear();
					if ((org.getOrganizacionId() != null))
						filtros.put("organizacionId", org.getOrganizacionId().toString());

					paginaPlanes = strategosPlanesService.getPlanes(1, 30, "nombre", "ASC", true, filtros);
					planes = paginaPlanes.getLista();
					// 1.0 Indicadores Planes
					if (planes.size() > 0) {
						for (Plan plan : planes) {
							if (plan.getActivo()) {
								row = dibujarIndicadoresPlan(row, sheet, style, style1, style2, styleRoja, styleVerde,
										styleAmarillo, plan, strategosPlanesService);
							}
						}
					}

					filtros.put("anio", anio);
					paginaIniciativas = strategosIniciativasService.getIniciativas(1, 30, "nombre", "ASC", true,
							filtros);
					iniciativas = paginaIniciativas.getLista();
					// INICIATIVAS
					if (iniciativas.size() > 0) {
						List<Iniciativa> iniAtrasados = new ArrayList();
						iniAtrasados = obtenerIniciativas(iniciativas, row, sheet, style1);

						if (iniAtrasados.size() > 0) {
							row = dibujarIniciativas(row, sheet, style, style1, style2, styleRoja, styleVerde,
									styleAmarillo, iniAtrasados, request);
							// 2.1 Actividades
							row = dibujarActividades(row, sheet, style, style1, style2, styleRoja, styleVerde,
									styleAmarillo, iniAtrasados, request);
						}
					}

					// 3.0 Analisis Estrategicos
					row = dibujarEventos(row, sheet, style, style1, date, fechaActualDate, org.getOrganizacionId(),
							mensajes);

					// 4.0 Informe Cualitativo
					row = dibujarInformes(row, sheet, style, style1, date, fechaActualDate, org.getOrganizacionId(),
							mensajes);
				}

			}
			strategosPlanesService.close();
			strategosIniciativasService.close();

		} else {
			// todas las organizaciones
			if (organizaciones.size() > 0) {
				int count = 0;
				int row = 6;
				for (Iterator<OrganizacionStrategos> iter = organizaciones.iterator(); iter.hasNext();) {
					OrganizacionStrategos organizacion = iter.next();
					if (count > 0)
						row++;

					count++;
					filtros = new HashMap<String, Object>();

					HSSFRow OrgRow1 = sheet.createRow(row);
					HSSFCell cellOrg2 = OrgRow1.createCell(0);
					cellOrg2.setCellStyle(styleOrg);
					cellOrg2.setCellValue("Organización");

					String nombre = organizacion.getNombre();
					HSSFCell cellOrg3 = OrgRow1.createCell(1);
					cellOrg3.setCellStyle(style1);
					cellOrg3.setCellValue(nombre);
					row = row + 3;

					if ((organizacion.getOrganizacionId() != null))
						filtros.put("organizacionId", organizacion.getOrganizacionId().toString());

					PaginaLista paginaPlanes = strategosPlanesService.getPlanes(1, 30, "nombre", "ASC", true, filtros);
					List<Plan> planes = paginaPlanes.getLista();
					// 1.0 Indicadores Planes
					if (planes.size() > 0) {
						for (Plan plan : planes) {
							if (plan.getActivo()) {
								row = dibujarIndicadoresPlan(row, sheet, style, style1, style2, styleRoja, styleVerde,
										styleAmarillo, plan, strategosPlanesService);
							}
						}
					}

					filtros.put("anio", anio);
					PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(1, 30, "nombre", "ASC",
							true, filtros);
					List<Iniciativa> iniciativas = paginaIniciativas.getLista();
					// INICIATIVAS
					if (iniciativas.size() > 0) {
						List<Iniciativa> iniAtrasados = new ArrayList();
						iniAtrasados = obtenerIniciativas(iniciativas, row, sheet, style1);

						if (iniAtrasados.size() > 0) {
							row = dibujarIniciativas(row, sheet, style, style1, style2, styleRoja, styleVerde,
									styleAmarillo, iniAtrasados, request);
							// 2.1 Actividades
							row = dibujarActividades(row, sheet, style, style1, style2, styleRoja, styleVerde,
									styleAmarillo, iniAtrasados, request);
						}
					}

					// 3.0 Analisis Estrategicos
					Integer periodo = obtenerFecha((byte) 5);
					String fecha = String.valueOf((periodo - 1)) + "/" + String.valueOf(new Date().getYear() + 1900);
					SimpleDateFormat date = new SimpleDateFormat("MM/yyyy");
					Date fechaActualDate = date.parse(fecha);

					row = dibujarEventos(row, sheet, style, style1, date, fechaActualDate,
							organizacion.getOrganizacionId(), mensajes);

					// 4.0 Informe Cualitativo
					row = dibujarInformes(row, sheet, style, style1, date, fechaActualDate,
							organizacion.getOrganizacionId(), mensajes);
				}
			}
		}

		for (int y = 0; y < 50; y++) {

			sheet.autoSizeColumn(y);
		}

		// crea el archivo excel

		Date dateXls = new Date();
		SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");

		String archivo = "ReporteCumplimiento_" + hourdateFormat.format(dateXls) + ".xls";

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + archivo);

		ServletOutputStream file = response.getOutputStream();

		workbook.write(file);
		file.close();

		forward = "exito";
		organizacionservice.close();

		return mapping.findForward(forward);
	}

	private int dibujarInformes(int row, HSSFSheet sheet, CellStyle style, CellStyle style1, SimpleDateFormat date,
			Date fechaActualDate, Long organizacionId, MessageResources mensajes) throws ParseException {
		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance()
				.openStrategosExplicacionesService();
		Map<String, Object> filtros = new HashMap<String, Object>();

		filtros.put("tipo", "1");
		filtros.put("objetoId", organizacionId.toString());

		PaginaLista paginaExplicaciones = strategosExplicacionesService.getExplicaciones(1, 30, "fecha", "DESC", true,
				filtros);

		List<Explicacion> explicaciones = paginaExplicaciones.getLista();
		boolean flag = false;
		if (explicaciones.size() > 0) {
			Explicacion explicacionUltima = explicaciones.get(0);
			String ultimaCarga = obtenerTrimestre(explicacionUltima.getFecha());
			Date fechaUltimaCarga = date.parse(ultimaCarga);

			if (fechaUltimaCarga.before(fechaActualDate)) {
				flag = true;
				HSSFRow IniRow3 = sheet.createRow(row);
				HSSFCell cellIni3 = IniRow3.createCell(0);
				cellIni3.setCellStyle(style);
				cellIni3.setCellValue(mensajes.getMessage("jsp.extension.informes.cualitativos"));
				row++;

				HSSFRow IniRow4 = sheet.createRow(row);
				HSSFCell cell4 = IniRow4.createCell(0);
				cell4.setCellStyle(style1);
				cell4.setCellValue("No hay informes cualitativos en el periodo anterior");
				row++;

				HSSFRow IniRow5 = sheet.createRow(row);
				HSSFCell cell5 = IniRow5.createCell(0);
				cell5.setCellStyle(style1);
				cell5.setCellValue("Fecha de ultima Carga : " + explicacionUltima.getFechaFormateada());
				row++;

				HSSFRow IniRow6 = sheet.createRow(row);
				HSSFCell cell6 = IniRow6.createCell(0);
				cell6.setCellStyle(style1);
				cell6.setCellValue("Titulo de la explicación : " + explicacionUltima.getTitulo());
				row++;
			}
			if (explicacionUltima.getAdjuntosExplicacion().size() == 0) {
				if (!flag) {
					HSSFRow IniRow2 = sheet.createRow(row);
					HSSFCell cellIni2 = IniRow2.createCell(0);
					cellIni2.setCellStyle(style);
					cellIni2.setCellValue(mensajes.getMessage("jsp.extension.informes.cualitativos"));

					row++;
					HSSFRow IniRow5 = sheet.createRow(row);
					HSSFCell cell5 = IniRow5.createCell(0);
					cell5.setCellStyle(style1);
					cell5.setCellValue("Titulo de la explicación : " + explicacionUltima.getTitulo());
					row++;
				}
				flag = true;
				HSSFRow IniRow6 = sheet.createRow(row);
				HSSFCell cell6 = IniRow6.createCell(0);
				cell6.setCellStyle(style1);
				cell6.setCellValue("No se han cargado anexos");
			}
			if (flag)
				row = row + 3;
		}
		return row;
	}

	private int dibujarEventos(int row, HSSFSheet sheet, CellStyle style, CellStyle style1, SimpleDateFormat date,
			Date fechaActualDate, Long organizacionId, MessageResources mensajes) throws ParseException {

		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance()
				.openStrategosExplicacionesService();
		Map<String, Object> filtros = new HashMap<String, Object>();

		filtros.put("tipo", "3");
		filtros.put("objetoId", organizacionId.toString());

		PaginaLista paginaExplicaciones = strategosExplicacionesService.getExplicaciones(1, 30, "fecha", "DESC", true,
				filtros);

		List<Explicacion> explicaciones = paginaExplicaciones.getLista();
		boolean flag = false;
		if (explicaciones.size() > 0) {
			Explicacion explicacionUltima = explicaciones.get(0);
			String ultimaCarga = obtenerTrimestre(explicacionUltima.getFecha());
			Date fechaUltimaCarga = date.parse(ultimaCarga);

			if (fechaUltimaCarga.before(fechaActualDate)) {
				flag = true;
				HSSFRow IniRow2 = sheet.createRow(row);
				HSSFCell cellIni2 = IniRow2.createCell(0);
				cellIni2.setCellStyle(style);
				cellIni2.setCellValue(mensajes.getMessage("jsp.extension.informes.eventos"));

				row++;

				HSSFRow IniRow4 = sheet.createRow(row);
				HSSFCell cell4 = IniRow4.createCell(0);
				cell4.setCellStyle(style1);
				cell4.setCellValue("No hay eventos en el periodo anterior");
				row++;

				HSSFRow IniRow3 = sheet.createRow(row);
				HSSFCell cell3 = IniRow3.createCell(0);
				cell3.setCellStyle(style1);
				cell3.setCellValue("Fecha de ultima Carga : " + explicacionUltima.getFechaFormateada());
				row++;

				HSSFRow IniRow5 = sheet.createRow(row);
				HSSFCell cell5 = IniRow5.createCell(0);
				cell5.setCellStyle(style1);
				cell5.setCellValue("Titulo de la explicación : " + explicacionUltima.getTitulo());
				row++;

			}
			if (explicacionUltima.getAdjuntosExplicacion().size() == 0) {
				if (!flag) {
					HSSFRow IniRow2 = sheet.createRow(row);
					HSSFCell cellIni2 = IniRow2.createCell(0);
					cellIni2.setCellStyle(style);
					cellIni2.setCellValue(mensajes.getMessage("jsp.extension.informes.eventos"));

					row++;
					HSSFRow IniRow5 = sheet.createRow(row);
					HSSFCell cell5 = IniRow5.createCell(0);
					cell5.setCellStyle(style1);
					cell5.setCellValue("Titulo de la explicación : " + explicacionUltima.getTitulo());
					row++;
				}
				flag = true;
				HSSFRow IniRow6 = sheet.createRow(row);
				HSSFCell cell6 = IniRow6.createCell(0);
				cell6.setCellStyle(style1);
				cell6.setCellValue("No se han cargado anexos");
			}
			if (flag)
				row = row + 3;
		}
		strategosExplicacionesService.close();
		return row;
	}

	private List<PryActividad> obtenerActividades(List<PryActividad> actividades, Iniciativa iniciativa)
			throws ParseException {
		List<PryActividad> actAtrasadas = new ArrayList();
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().
				openStrategosMedicionesService();
		
		for (PryActividad actividad : actividades) {
			Date fechaUltimaMedicion;
			Integer periodo = obtenerFecha(iniciativa.getFrecuencia());
			Integer anio = (new Date().getYear() + 1900);
			String fecha = String.valueOf((periodo - 1)) + "/" + String.valueOf(anio);
			SimpleDateFormat date = new SimpleDateFormat("MM/yyyy");
			Date fechaActualDate = date.parse(fecha);
			String ultimaMedicion = actividad.getFechaUltimaMedicion();
			
			if (actividad.getFechaUltimaMedicion() != null) {
				fechaUltimaMedicion = date.parse(ultimaMedicion);
				if (fechaUltimaMedicion.before(fechaActualDate)) {
					actAtrasadas.add(actividad);
				}
			}else {				
				List<Medicion> medicion = strategosMedicionesService.getMedicionesPeriodo(actividad.getIndicadorId(), 1L, anio, anio, periodo-1, periodo-1);
				if(medicion.size() > 0)
					actAtrasadas.add(actividad);				
			}
		}
		return actAtrasadas;
	}

	private int dibujarActividades(int row, HSSFSheet sheet, CellStyle style, CellStyle style1, CellStyle style2,
			CellStyle styleRoja, CellStyle styleVerde, CellStyle styleAmarillo, List<Iniciativa> iniAtrasados,
			HttpServletRequest request) throws ParseException {

		Map<String, Object> filtros = new HashMap<String, Object>();
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance()
				.openStrategosPryActividadesService();
		for (Iniciativa iniciativa : iniAtrasados) {

			filtros = new HashMap<String, Object>();
			filtros.put("proyectoId", iniciativa.getProyectoId());
			List<PryActividad> actividades = strategosPryActividadesService
					.getActividades(0, 0, "fila", "ASC", true, filtros).getLista();

			if (actividades.size() > 0) {
				List<PryActividad> actAtrasadas = new ArrayList();
				actAtrasadas = obtenerActividades(actividades, iniciativa);

				if (actAtrasadas.size() > 0) {
					HSSFRow IniRow2 = sheet.createRow(row);
					HSSFCell cellIni2 = IniRow2.createCell(0);
					cellIni2.setCellStyle(style);
					cellIni2.setCellValue(((NavegadorForm) request.getSession().getAttribute("activarIniciativa"))
							.getNombreSingular());

					HSSFCell cellAct = IniRow2.createCell(1);
					cellAct.setCellStyle(style1);
					cellAct.setCellValue(iniciativa.getNombre());
					row++;

					HSSFRow cabezaTablaAct = sheet.createRow(row);
					HSSFCell cellTablaAct = cabezaTablaAct.createCell(0);
					cellTablaAct.setCellStyle(style);
					cellTablaAct.setCellValue("Nombre Actividad");

					cellTablaAct = cabezaTablaAct.createCell(1);
					cellTablaAct.setCellStyle(style);
					cellTablaAct.setCellValue("Porcentaje de Avance");

					cellTablaAct = cabezaTablaAct.createCell(2);
					cellTablaAct.setCellStyle(style);
					cellTablaAct.setCellValue("Alerta");

					cellTablaAct = cabezaTablaAct.createCell(3);
					cellTablaAct.setCellStyle(style);
					cellTablaAct.setCellValue("Fecha Ultima Medición");

					cellTablaAct = cabezaTablaAct.createCell(4);
					cellTablaAct.setCellStyle(style);
					cellTablaAct.setCellValue("Responsable de Ejecución");

					for (PryActividad actividad : actAtrasadas) {
						row++;
						HSSFRow Row = sheet.createRow(row);
						int cel = 0;
						HSSFCell cellInfo = Row.createCell(cel);
						cellInfo.setCellStyle(style1);
						cellInfo.setCellValue(actividad.getNombre());
						cel++;

						HSSFCell cellInfo2 = Row.createCell(cel);
						cellInfo2.setCellStyle(style1);
						cellInfo2.setCellValue(iniciativa.getPorcentajeCompletadoFormateado() != null
								? iniciativa.getPorcentajeCompletadoFormateado()
								: "");
						cel++;

						HSSFCell cellInfo3 = Row.createCell(cel);
						cellInfo3.setCellStyle(style1);
						if (actividad.getAlerta() == null) {
							cellInfo3.setCellStyle(style1);
							cellInfo3.setCellValue("");
						} else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
							cellInfo3.setCellStyle(styleRoja);
							cellInfo3.setCellValue("");
						} else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue()) {
							cellInfo3.setCellStyle(styleVerde);
							cellInfo3.setCellValue("");
						} else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla()
								.byteValue()) {
							cellInfo3.setCellStyle(styleAmarillo);
							cellInfo3.setCellValue("");
						}
						cel++;

						HSSFCell cellInfo5 = Row.createCell(cel);
						cellInfo5.setCellStyle(style1);
						cellInfo5.setCellValue(
								actividad.getFechaUltimaMedicion() != null ? actividad.getFechaUltimaMedicion() : "");
						cel++;

						HSSFCell cellInfo6 = Row.createCell(cel);
						cellInfo6.setCellStyle(style1);
						String nombreResp = null;
						if (actividad.getResponsableCargarEjecutado() != null) {
							nombreResp = actividad.getResponsableCargarEjecutado().getNombre();
						}
						cellInfo6.setCellValue(nombreResp != null ? nombreResp : "");
						cel++;
					}
					row = row + 3;
				}
			}
		}
		strategosPryActividadesService.close();
		return row;
	}

	private List<Iniciativa> obtenerIniciativas(List<Iniciativa> iniciativas, int row, HSSFSheet sheet,
			CellStyle style1) throws Exception {
		List<Iniciativa> iniAtrasados = new ArrayList();
		for (Iniciativa iniciativa : iniciativas) {
			if (iniciativa.getEstatusId() == 2) {
				Map<String, Object> filtros = new HashMap<String, Object>();
				StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance()
						.openStrategosPryActividadesService();

				filtros = new HashMap<String, Object>();
				filtros.put("proyectoId", iniciativa.getProyectoId());
				List<PryActividad> actividades = strategosPryActividadesService
						.getActividades(0, 0, "fila", "ASC", true, filtros).getLista();

				if (actividades.size() > 0) {
					List<PryActividad> actAtrasadas = new ArrayList();
					actAtrasadas = obtenerActividades(actividades, iniciativa);										
					if (actAtrasadas.size() > 0) {						
						iniAtrasados.add(iniciativa);
					}
				}
			}
		}
		return iniAtrasados;
	}

	private int dibujarIniciativas(int row, HSSFSheet sheet, CellStyle style, CellStyle style1, CellStyle style2,
			CellStyle styleRoja, CellStyle styleVerde, CellStyle styleAmarillo, List<Iniciativa> iniAtrasados,
			HttpServletRequest request) {

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
				.openStrategosIndicadoresService();

		HSSFRow IniRow = sheet.createRow(row);
		HSSFCell cellIni = IniRow.createCell(0);
		cellIni.setCellStyle(style);
		cellIni.setCellValue(
				((NavegadorForm) request.getSession().getAttribute("activarIniciativa")).getNombrePlural());

		row = row + 2;

		HSSFRow cabezaTablaInd = sheet.createRow(row);
		HSSFCell cellTablaInd = cabezaTablaInd.createCell(0);
		cellTablaInd.setCellStyle(style);
		cellTablaInd.setCellValue("Nombre Iniciativa");

		cellTablaInd = cabezaTablaInd.createCell(1);
		cellTablaInd.setCellStyle(style);
		cellTablaInd.setCellValue("Porcentaje de Avance");

		cellTablaInd = cabezaTablaInd.createCell(2);
		cellTablaInd.setCellStyle(style);
		cellTablaInd.setCellValue("Alerta");

		cellTablaInd = cabezaTablaInd.createCell(3);
		cellTablaInd.setCellStyle(style);
		cellTablaInd.setCellValue("Frecuencia");

		cellTablaInd = cabezaTablaInd.createCell(4);
		cellTablaInd.setCellStyle(style);
		cellTablaInd.setCellValue("Fecha Ultima Medición");

		cellTablaInd = cabezaTablaInd.createCell(5);
		cellTablaInd.setCellStyle(style);
		cellTablaInd.setCellValue("Medición");

		for (Iniciativa iniciativa : iniAtrasados) {
			row++;

			Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
					iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
			String frecuencia = obtenerFrecuencia(iniciativa.getFrecuencia());
			int cel = 0;
			HSSFRow Row = sheet.createRow(row);

			HSSFCell cellInfo = Row.createCell(cel);
			cellInfo.setCellStyle(style1);
			cellInfo.setCellValue(iniciativa.getNombre());
			cel++;

			HSSFCell cellInfo2 = Row.createCell(cel);
			cellInfo2.setCellStyle(style1);
			cellInfo2.setCellValue(iniciativa.getPorcentajeCompletadoFormateado() != null
					? iniciativa.getPorcentajeCompletadoFormateado()
					: "");
			cel++;

			HSSFCell cellInfo3 = Row.createCell(cel);
			cellInfo3.setCellStyle(style1);
			if (iniciativa.getAlerta() == null) {
				cellInfo3.setCellStyle(style1);
				cellInfo3.setCellValue("");
			} else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
				cellInfo3.setCellStyle(styleRoja);
				cellInfo3.setCellValue("");
			} else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue()) {
				cellInfo3.setCellStyle(styleVerde);
				cellInfo3.setCellValue("");
			} else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
				cellInfo3.setCellStyle(styleAmarillo);
				cellInfo3.setCellValue("");
			}
			cel++;

			HSSFCell cellInfo4 = Row.createCell(cel);
			cellInfo4.setCellStyle(style1);
			cellInfo4.setCellValue(frecuencia);
			cel++;

			HSSFCell cellInfo5 = Row.createCell(cel);
			cellInfo5.setCellStyle(style1);
			cellInfo5.setCellValue(
					iniciativa.getFechaUltimaMedicion() != null ? iniciativa.getFechaUltimaMedicion() : "");
			cel++;

			HSSFCell cellInfo6 = Row.createCell(cel);
			cellInfo6.setCellStyle(style1);
			cellInfo6.setCellValue(
					indicador.getUltimaMedicionFormateada() != null ? indicador.getUltimaMedicionFormateada() : "");
			cel++;

		}
		row = row + 3;
		strategosIndicadoresService.close();
		return row;
	}

	private List<Indicador> obtenerIndicadores(Integer nivel, List<Perspectiva> perspectivasPlan, Plan plan,
			StrategosIndicadoresService strategosIndicadoresService,
			StrategosPerspectivasService strategosPerspectivasService, StrategosPlanesService strategosPlanesService)
			throws Exception {

		List<Indicador> indAtrasados = new ArrayList();

		for (Perspectiva perspectiva : perspectivasPlan) {

			Map<String, Object> filtros = new HashMap<String, Object>();

			filtros.put("padreId", perspectiva.getPerspectivaId());
			String[] orden = new String[1];
			String[] tipoOrden = new String[1];
			orden[0] = "nombre";
			tipoOrden[0] = "asc";
			List<Perspectiva> perspectivas = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);

			if (perspectivas.size() > 0) {
				for (Perspectiva perspectivaHija : perspectivas) {
					filtros = new HashMap<String, Object>();
					filtros.put("perspectivaId", perspectivaHija.getPerspectivaId().toString());
					List<Indicador> indicadores = strategosIndicadoresService
							.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, true).getLista();

					for (Indicador indicador : indicadores) {
						if (indicador.getNaturaleza() == 0) {
							Date fechaUltimaMedicion;
							Integer periodo = obtenerFecha(indicador.getFrecuencia());
							String fecha = String.valueOf((periodo - 1)) + "/"
									+ String.valueOf(new Date().getYear() + 1900);
							SimpleDateFormat date = new SimpleDateFormat("MM/yyyy");
							Date fechaActualDate = date.parse(fecha);
							String ultimaMedicion = indicador.getFechaUltimaMedicion();
							if (indicador.getFechaUltimaMedicion() != null) {
								fechaUltimaMedicion = date.parse(ultimaMedicion);
								if (fechaUltimaMedicion.before(fechaActualDate)) {
									indicador.setPerspectivaNombre(perspectivaHija.getNombre());
									indAtrasados.add(indicador);
								}
							}
						}
					}
				}
			}
		}
		return indAtrasados;
	}

	private int dibujarIndicadoresPlan(int row, HSSFSheet sheet, CellStyle style, CellStyle style1, CellStyle style2,
			CellStyle styleRoja, CellStyle styleVerde, CellStyle styleAmarillo, Plan plan,
			StrategosPlanesService strategosPlanesService) throws Exception {

		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance()
				.openStrategosPerspectivasService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
				.openStrategosIndicadoresService();

		Map<String, Object> filtros = new HashMap<String, Object>();
		List<Indicador> indAtrasados = new ArrayList();
		Perspectiva perspectiva = strategosPerspectivasService.getPerspectivaRaiz(plan.getPlanId());
		filtros.clear();
		filtros.put("padreId", perspectiva.getPerspectivaId());
		String[] orden = new String[1];
		String[] tipoOrden = new String[1];
		orden[0] = "nombre";
		tipoOrden[0] = "asc";
		Integer nivel = 0;
		List<Perspectiva> perspectivas = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);

		if (perspectivas.size() > 0) {
			if (perspectiva.getPadreId() == null || perspectiva.getPadreId() == 0L)
				nivel = 0;
			else
				nivel++;
			indAtrasados = obtenerIndicadores(nivel, perspectivas, plan, strategosIndicadoresService,
					strategosPerspectivasService, strategosPlanesService);
			if (indAtrasados.size() > 0) {

				HSSFRow IniRow = sheet.createRow(row);
				HSSFCell cellIni = IniRow.createCell(0);
				cellIni.setCellStyle(style);
				cellIni.setCellValue("Plan");
				HSSFCell cellIni1 = IniRow.createCell(1);
				cellIni1.setCellStyle(style1);
				cellIni1.setCellValue(plan.getNombre());

				row++;
				HSSFRow IndRow = sheet.createRow(row);
				HSSFCell cellIndEj = IndRow.createCell(0);
				cellIndEj.setCellStyle(style2);
				cellIndEj.setCellValue("Indicadores");
				row++;

				HSSFRow cabezaTablaInd = sheet.createRow(row);
				HSSFCell cellTablaInd = cabezaTablaInd.createCell(0);
				cellTablaInd.setCellStyle(style);
				cellTablaInd.setCellValue("Nombre Perspectiva");

				cellTablaInd = cabezaTablaInd.createCell(1);
				cellTablaInd.setCellStyle(style);
				cellTablaInd.setCellValue("Nombre Indicador");

				cellTablaInd = cabezaTablaInd.createCell(2);
				cellTablaInd.setCellStyle(style);
				cellTablaInd.setCellValue("Alerta");

				cellTablaInd = cabezaTablaInd.createCell(3);
				cellTablaInd.setCellStyle(style);
				cellTablaInd.setCellValue("Frecuencia");

				cellTablaInd = cabezaTablaInd.createCell(4);
				cellTablaInd.setCellStyle(style);
				cellTablaInd.setCellValue("Fecha Ultima Medicion");

				cellTablaInd = cabezaTablaInd.createCell(5);
				cellTablaInd.setCellStyle(style);
				cellTablaInd.setCellValue("Medición");

				cellTablaInd = cabezaTablaInd.createCell(6);
				cellTablaInd.setCellStyle(style);
				cellTablaInd.setCellValue("Responsable");
				for (Indicador indicador : indAtrasados) {
					row++;
					int cel = 0;
					HSSFRow Row = sheet.createRow(row);

					HSSFCell cellInfo = Row.createCell(cel);
					cellInfo.setCellStyle(style1);
					cellInfo.setCellValue(indicador.getPerspectivaNombre());
					cel++;

					HSSFCell cellInfo2 = Row.createCell(cel);
					cellInfo2.setCellStyle(style1);
					cellInfo2.setCellValue(indicador.getNombre());
					cel++;

					Byte alerta = strategosPlanesService.getAlertaIndicadorPorPlan(indicador.getIndicadorId(),
							plan.getPlanId());
					String frecuencia = obtenerFrecuencia(indicador.getFrecuencia());

					HSSFCell cellInfo3 = Row.createCell(cel);
					cellInfo3.setCellStyle(style1);
					if (alerta == null) {
						cellInfo3.setCellStyle(style1);
						cellInfo3.setCellValue("");
					} else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
						cellInfo3.setCellStyle(styleRoja);
						cellInfo3.setCellValue("");
					} else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue()) {
						cellInfo3.setCellStyle(styleVerde);
						cellInfo3.setCellValue("");
					} else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
						cellInfo3.setCellStyle(styleAmarillo);
						cellInfo3.setCellValue("");
					}
					cel++;

					HSSFCell cellInfo7 = Row.createCell(cel);
					cellInfo7.setCellStyle(style1);
					cellInfo7.setCellValue(frecuencia != null ? frecuencia : "");
					cel++;

					HSSFCell cellInfo4 = Row.createCell(cel);
					cellInfo4.setCellStyle(style1);
					cellInfo4.setCellValue(
							indicador.getFechaUltimaMedicion() != null ? indicador.getFechaUltimaMedicion() : "");
					cel++;

					HSSFCell cellInfo5 = Row.createCell(cel);
					cellInfo5.setCellStyle(style1);
					cellInfo5.setCellValue(
							indicador.getUltimaMedicionFormateada() != null ? indicador.getUltimaMedicionFormateada()
									: "");
					cel++;

					HSSFCell cellInfo6 = Row.createCell(cel);
					cellInfo6.setCellStyle(style1);
					String nombreResp = null;
					if (indicador.getResponsableCargarEjecutado() != null) {
						nombreResp = indicador.getResponsableCargarEjecutado().getNombre();
					}
					cellInfo6.setCellValue(nombreResp != null ? nombreResp : "");
					cel++;
				}
				row = row + 3;
			}
		}
		strategosPerspectivasService.close();
		strategosIndicadoresService.close();
		return row;
	}

	private Integer obtenerFecha(Byte frecuencia) {
		Integer periodoFinal = 0;

		Integer dia = new Date().getDay();
		int mes = new Date().getMonth() + 1;

		// Diaria - 0
		if (frecuencia == 0)
			periodoFinal = dia;
		// Semanal - 1
		else if (frecuencia == 1)
			periodoFinal = dia / 7;

		// Quincenal - 2
		else if (frecuencia == 2) {
			if (mes > 1) {
				int semana = (mes - 1) * 2;
				if (dia > 15) {
					semana += 2;
				} else if (dia <= 15) {
					semana += 1;
				}
				periodoFinal = Math.round(semana - 1);
			} else {
				if (dia > 15) {
					periodoFinal = 1;
				} else if (dia <= 15) {
					periodoFinal = 0;
				}
			}
		}

		// Mensual - 3
		else if (frecuencia == 3)
			periodoFinal = mes;

		// Bimestral - 4
		else if (frecuencia == 4) {
			if (mes <= 2) {
				periodoFinal = 1;
			} else if (mes > 2 && mes <= 4) {
				periodoFinal = 2;
			} else if (mes > 4 && mes <= 6) {
				periodoFinal = 3;
			} else if (mes > 6 && mes <= 8) {
				periodoFinal = 4;
			} else if (mes > 8 && mes <= 10) {
				periodoFinal = 5;
			} else if (mes > 10) {
				periodoFinal = 6;
			}
		}

		// Trimestral - 5
		else if (frecuencia == 5) {
			if (mes <= 3) {
				periodoFinal = 1;
			} else if (mes > 3 && mes <= 6) {
				periodoFinal = 2;
			} else if (mes > 6 && mes <= 9) {
				periodoFinal = 3;
			} else if (mes > 9) {
				periodoFinal = 4;
			}
		}

		// Cuatrimestral - 6
		else if (frecuencia == 6) {
			if (mes <= 4) {
				periodoFinal = 1;
			} else if (mes > 4 && mes <= 8) {
				periodoFinal = 2;
			} else if (mes > 8) {
				periodoFinal = 3;
			}
		}

		// Semestral - 7
		else if (frecuencia == 7) {
			if (mes <= 6) {
				periodoFinal = 1;
			} else if (mes > 6) {
				periodoFinal = 2;
			}
		}

		// Anual - 8
		else if (frecuencia == 8)
			periodoFinal = 1;

		return periodoFinal;
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

	private String obtenerTrimestre(Date fecha) {
		Calendar calendario = Calendar.getInstance();
		calendario.setTime(fecha);
		int mes = calendario.get(Calendar.MONTH) + 1; // Sumamos 1 porque enero es 0
		int ano = calendario.get(Calendar.YEAR);

		int trimestre;
		if (mes >= 1 && mes <= 3) {
			trimestre = 1;
		} else if (mes >= 4 && mes <= 6) {
			trimestre = 2;
		} else if (mes >= 7 && mes <= 9) {
			trimestre = 3;
		} else {
			trimestre = 4;
		}

		return trimestre + "/" + ano;
	}
}
