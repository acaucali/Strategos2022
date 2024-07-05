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
import org.apache.poi.ss.util.CellRangeAddress;
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
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.usuarios.UsuariosService;

public class ReporteDependenciasOmisivasXls extends VgcAction {
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
		Font fontOk = workbook.createFont();
		fontOk.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerStyle.setFont(fontOk);
		Font fontNotOk = workbook.createFont();
		fontNotOk.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fontNotOk.setColor(IndexedColors.RED.getIndex());
		headerStyle.setFont(fontOk);

		Font font2 = workbook.createFont();
		font2.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font2.setColor(IndexedColors.WHITE.getIndex());

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
		styleDep.setFont(font2);
		styleDep.setFillForegroundColor(IndexedColors.BROWN.index);
		styleDep.setFillBackgroundColor(IndexedColors.LIGHT_BLUE.index);
		styleDep.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle styleOrg = workbook.createCellStyle();
		styleOrg.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleOrg.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
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
		styleTitulo.setFillForegroundColor(IndexedColors.DARK_BLUE.index);
		styleTitulo.setFillBackgroundColor(IndexedColors.DARK_BLUE.index);
		styleTitulo.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle styleOk = workbook.createCellStyle();
		styleOk.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleOk.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleOk.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleOk.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleOk.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleOk.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleOk.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styleOk.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		styleOk.setRightBorderColor(IndexedColors.BLACK.getIndex());
		styleOk.setTopBorderColor(IndexedColors.BLACK.getIndex());
		styleOk.setFont(fontOk);
		styleOk.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		styleOk.setFillBackgroundColor(IndexedColors.PALE_BLUE.index);
		styleOk.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		CellStyle styleNotOk = workbook.createCellStyle();
		styleNotOk.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styleNotOk.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleNotOk.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleNotOk.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleNotOk.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleNotOk.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleNotOk.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		styleNotOk.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		styleNotOk.setRightBorderColor(IndexedColors.BLACK.getIndex());
		styleNotOk.setTopBorderColor(IndexedColors.BLACK.getIndex());
		styleNotOk.setFont(fontNotOk);
		styleNotOk.setFillForegroundColor(IndexedColors.PALE_BLUE.index);
		styleNotOk.setFillBackgroundColor(IndexedColors.PALE_BLUE.index);
		styleNotOk.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

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

		HSSFRow headerRow = sheet.createRow(0);
		String header = "Reporte de Dependencias Omisivas";
		HSSFCell cell = headerRow.createCell(0);
		cell.setCellStyle(headerStyle);
		cell.setCellValue(header);
		Date fechaActual = new Date();
		String trimestre = obtenerTrimestre(fechaActual);
		
		HSSFRow triRow = sheet.createRow(2);
		HSSFCell cellTri = triRow.createCell(0);
		cellTri.setCellStyle(styleOrg);
		cellTri.setCellValue("Trimestre");
		
		HSSFCell cellTri1 = triRow.createCell(1);
		cellTri1.setCellStyle(style1);
		cellTri1.setCellValue(trimestre);
		
		HSSFRow nivelRow = sheet.createRow(4);
		HSSFCell cellNivel = nivelRow.createCell(0);
		cellNivel.setCellStyle(styleDep);
		cellNivel.setCellValue("DEPENDENCIAS");

		HSSFRow nivelRow1 = sheet.createRow(5);
		HSSFCell cellNivel1 = nivelRow1.createCell(0);
		cellNivel1.setCellStyle(styleOrg);
		cellNivel1.setCellValue("NIVEL CENTRAL - DELEGADAS");

		sheet.addMergedRegion(new CellRangeAddress(4, 5, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(4, 5, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(4, 5, 3, 3));
		sheet.addMergedRegion(new CellRangeAddress(4, 5, 4, 4));
		sheet.addMergedRegion(new CellRangeAddress(4, 5, 5, 5));

		
		HSSFCell cellOrg = nivelRow.createCell(1);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("AVANCE INICIATIVAS");

		cellOrg = nivelRow.createCell(2);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("RAE");

		cellOrg = nivelRow.createCell(3);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("CUALITATIVO");

		cellOrg = nivelRow.createCell(4);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("INDICADORES");

		cellOrg = nivelRow.createCell(5);
		cellOrg.setCellStyle(styleTitulo);
		cellOrg.setCellValue("ADMINISTRADOR");
		
		// suborganizaciones
		if (request.getParameter("alcance").equals("4")) {
			filtros = new HashMap<String, Object>();
			int row = 6;
			List<OrganizacionStrategos> organizacionesSub = organizacionservice.getOrganizacionHijas(
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId(),
					true);

			UsuariosService usuarioService = FrameworkServiceFactory.getInstance().openUsuariosService();
			
			 
			OrganizacionStrategos organizacion = ((OrganizacionStrategos) request.getSession()
					.getAttribute("organizacion"));		
			Map<String, Object> filtrosAdmin = new HashMap<String, Object>();
			filtrosAdmin.put("grupoId", 4776);
			filtrosAdmin.put("estatus", 0);
			filtrosAdmin.put("organizacionId", organizacion.getOrganizacionId());
			PaginaLista paginaUsuarios = usuarioService.getUsuarios(3, "fullName", "ASC", true, filtrosAdmin);			
			List<Usuario> uss = paginaUsuarios.getLista();
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < uss.size(); i++) {
			    Usuario us = uss.get(i);
			    sb.append(us.getFullName()); // Asumiendo que el método toString() de Usuario devuelve la representación deseada
			    
			    if (i < uss.size() - 1) {
			        sb.append(" - ");
			    }
			}
			
			String administradores = sb.toString();			
			reporte.setOrganizacionNombre(organizacion.getNombre());
			
			filtros.clear();

			HSSFRow OrgRow1 = sheet.createRow(row);
			String nombre = organizacion.getNombre();
			HSSFCell cellOrg1 = OrgRow1.createCell(0);
			cellOrg1.setCellStyle(style1);
			cellOrg1.setCellValue(nombre);
			
			if ((organizacion.getOrganizacionId() != null))
				filtros.put("organizacionId", organizacion.getOrganizacionId().toString());
			filtros.put("anio", anio);
			PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(1, 30, "nombre", "ASC",
					true, filtros);
			List<Iniciativa> iniciativas = paginaIniciativas.getLista();
			if (iniciativas.size() > 0) {
				List<Iniciativa> iniAtrasados = new ArrayList();
				iniAtrasados = obtenerIniciativas(iniciativas);

				if (iniAtrasados.size() > 0) {
					cellOrg1 = OrgRow1.createCell(1);
					cellOrg1.setCellStyle(styleNotOk);
					cellOrg1.setCellValue("X");
				}else {
					cellOrg1 = OrgRow1.createCell(1);
					cellOrg1.setCellStyle(styleOk);
					cellOrg1.setCellValue("ok");
				}
				
			}else {
				cellOrg1 = OrgRow1.createCell(1);
				cellOrg1.setCellStyle(styleOk);
				cellOrg1.setCellValue("No tiene Iniciativas");
			}

			Integer periodo = obtenerFecha((byte) 5);
			String fecha = String.valueOf((periodo - 1)) + "/" + String.valueOf(new Date().getYear() + 1900);
			SimpleDateFormat date = new SimpleDateFormat("MM/yyyy");
			Date fechaActualDate = date.parse(fecha);					

			verificarEventos(row, sheet, OrgRow1, styleNotOk, styleOk, date, fechaActualDate,
					organizacion.getOrganizacionId());
			verificarInformes(row, sheet, OrgRow1, styleNotOk, styleOk, date, fechaActualDate,
					organizacion.getOrganizacionId());

			filtros = new HashMap<String, Object>();
			if ((organizacion.getOrganizacionId() != null))
				filtros.put("organizacionId", organizacion.getOrganizacionId().toString());
			PaginaLista paginaPlanes = strategosPlanesService.getPlanes(1, 30, "nombre", "ASC", true, filtros);
			List<Plan> planes = paginaPlanes.getLista();
			// 1.0 Indicadores Planes
			if (planes.size() > 0) {
				for (Plan plan : planes) {
					if (plan.getActivo()) {

						dibujarIndicadores(row, sheet, OrgRow1, styleNotOk, styleOk, plan, strategosPlanesService);
					} else {
						cellOrg1 = OrgRow1.createCell(4);
						cellOrg1.setCellStyle(styleOk);
						cellOrg1.setCellValue("No tiene planes activos");
					}
				}
			} else {
				cellOrg1 = OrgRow1.createCell(4);
				cellOrg1.setCellStyle(styleOk);
				cellOrg1.setCellValue("No tiene planes");
			}

			cellOrg1 = OrgRow1.createCell(5);
			cellOrg1.setCellStyle(style1);
			cellOrg1.setCellValue(administradores);
			

			// suborganizaciones
			if (organizacionesSub.size() > 0 || organizacionesSub != null) {
				for (Iterator<OrganizacionStrategos> iter = organizacionesSub.iterator(); iter.hasNext();) {
					OrganizacionStrategos org = iter.next();
					row++;
					HSSFRow OrgRow2 = sheet.createRow(row);
					HSSFCell cellOrg2 = OrgRow2.createCell(0);
					cellOrg2.setCellStyle(style1);
					nombre = org.getNombre();
					cellOrg2.setCellValue(nombre);
										
					filtrosAdmin.put("organizacionId", org.getOrganizacionId().toString());
					
					paginaUsuarios = usuarioService.getUsuarios(1, "fullName", "ASC", true, filtrosAdmin);
					uss = paginaUsuarios.getLista();
					sb = new StringBuilder();
					for (int i = 0; i < uss.size(); i++) {
					    Usuario us = uss.get(i);
					    sb.append(us.getFullName()); 
					    
					    if (i < uss.size() - 1) {
					        sb.append(" - ");
					    }
					}
					
					administradores = sb.toString();		
					
					filtros = new HashMap<String, Object>();
					if ((org.getOrganizacionId() != null))
						filtros.put("organizacionId", org.getOrganizacionId().toString());
					filtros.put("anio", anio);
					paginaIniciativas = strategosIniciativasService.getIniciativas(1, 30, "nombre", "ASC",
							true, filtros);
					iniciativas = paginaIniciativas.getLista();
					if (iniciativas.size() > 0) {
						List<Iniciativa> iniAtrasados = new ArrayList();
						iniAtrasados = obtenerIniciativas(iniciativas);

						if (iniAtrasados.size() > 0) {
							cellOrg2 = OrgRow2.createCell(1);
							cellOrg2.setCellStyle(styleNotOk);
							cellOrg2.setCellValue("X");
						}else {
							cellOrg2 = OrgRow2.createCell(1);
							cellOrg2.setCellStyle(styleOk);
							cellOrg2.setCellValue("ok");
						}
						
					}else {
						cellOrg2 = OrgRow2.createCell(1);
						cellOrg2.setCellStyle(styleNotOk);
						cellOrg2.setCellValue("No tiene Iniciativas Formuladas");
					}

					verificarEventos(row, sheet, OrgRow2, styleNotOk, styleOk, date, fechaActualDate,
							org.getOrganizacionId());
					verificarInformes(row, sheet, OrgRow2, styleNotOk, styleOk, date, fechaActualDate,
							org.getOrganizacionId());

					filtros = new HashMap<String, Object>();
					if ((organizacion.getOrganizacionId() != null))
						filtros.put("organizacionId", org.getOrganizacionId().toString());
					paginaPlanes = strategosPlanesService.getPlanes(1, 30, "nombre", "ASC", true, filtros);
					planes = paginaPlanes.getLista();
					// 1.0 Indicadores Planes
					if (planes.size() > 0) {
						for (Plan plan : planes) {
							if (plan.getActivo()) {

								dibujarIndicadores(row, sheet, OrgRow2, styleNotOk, styleOk, plan,
										strategosPlanesService);
							} else {
								cellOrg2 = OrgRow2.createCell(4);
								cellOrg2.setCellStyle(styleOk);
								cellOrg2.setCellValue("No tiene planes activos");
							}
						}
					} else {
						cellOrg2 = OrgRow2.createCell(4);
						cellOrg2.setCellStyle(styleOk);
						cellOrg2.setCellValue("No tiene planes");
					}

					cellOrg2 = OrgRow2.createCell(5);
					cellOrg2.setCellStyle(style1);
					cellOrg2.setCellValue(administradores);					
				}
			}

		} else {
			// todas las organizaciones
			if (organizaciones.size() > 0) {
				int row = 6;
				for (Iterator<OrganizacionStrategos> iter = organizaciones.iterator(); iter.hasNext();) {
					OrganizacionStrategos organizacion = iter.next();
					HSSFRow OrgRow1 = sheet.createRow(row);
					String nombre = organizacion.getNombre();
					HSSFCell cellOrg1 = OrgRow1.createCell(0);
					cellOrg1.setCellStyle(style1);
					cellOrg1.setCellValue(nombre);
					UsuariosService usuarioService = FrameworkServiceFactory.getInstance().openUsuariosService();
					Map<String, Object> filtrosAdmin = new HashMap<String, Object>();
					filtrosAdmin.put("grupoId", 4776);
					filtrosAdmin.put("estatus", 0);
					filtrosAdmin.put("organizacionId", organizacion.getOrganizacionId());
					PaginaLista paginaUsuarios = usuarioService.getUsuarios(3, "fullName", "ASC", true, filtrosAdmin);			
					List<Usuario> uss = paginaUsuarios.getLista();
					
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < uss.size(); i++) {
					    Usuario us = uss.get(i);
					    sb.append(us.getFullName()); // Asumiendo que el método toString() de Usuario devuelve la representación deseada
					    
					    if (i < uss.size() - 1) {
					        sb.append(" - ");
					    }
					}
					
					String administradores = sb.toString();		
					
					if ((organizacion.getOrganizacionId() != null))
						filtros.put("organizacionId", organizacion.getOrganizacionId().toString());
					filtros.put("anio", anio);
					PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(1, 30, "nombre", "ASC",
							true, filtros);
					List<Iniciativa> iniciativas = paginaIniciativas.getLista();
					if (iniciativas.size() > 0) {
						List<Iniciativa> iniAtrasados = new ArrayList();
						iniAtrasados = obtenerIniciativas(iniciativas);

						if (iniAtrasados.size() > 0) {
							cellOrg1 = OrgRow1.createCell(1);
							cellOrg1.setCellStyle(styleNotOk);
							cellOrg1.setCellValue("X");
						}else {
							cellOrg1 = OrgRow1.createCell(1);
							cellOrg1.setCellStyle(styleOk);
							cellOrg1.setCellValue("ok");
						}
						
					}else {
						cellOrg1 = OrgRow1.createCell(1);
						cellOrg1.setCellStyle(styleOk);
						cellOrg1.setCellValue("No tiene Iniciativas");
					}

					Integer periodo = obtenerFecha((byte) 5);
					String fecha = String.valueOf((periodo - 1)) + "/" + String.valueOf(new Date().getYear() + 1900);
					SimpleDateFormat date = new SimpleDateFormat("MM/yyyy");
					Date fechaActualDate = date.parse(fecha);					

					verificarEventos(row, sheet, OrgRow1, styleNotOk, styleOk, date, fechaActualDate,
							organizacion.getOrganizacionId());
					verificarInformes(row, sheet, OrgRow1, styleNotOk, styleOk, date, fechaActualDate,
							organizacion.getOrganizacionId());

					filtros = new HashMap<String, Object>();
					if ((organizacion.getOrganizacionId() != null))
						filtros.put("organizacionId", organizacion.getOrganizacionId().toString());
					PaginaLista paginaPlanes = strategosPlanesService.getPlanes(1, 30, "nombre", "ASC", true, filtros);
					List<Plan> planes = paginaPlanes.getLista();
					// 1.0 Indicadores Planes
					if (planes.size() > 0) {
						for (Plan plan : planes) {
							if (plan.getActivo()) {

								dibujarIndicadores(row, sheet, OrgRow1, styleNotOk, styleOk, plan, strategosPlanesService);
							} else {
								cellOrg1 = OrgRow1.createCell(4);
								cellOrg1.setCellStyle(styleOk);
								cellOrg1.setCellValue("No tiene planes activos");
							}
						}
					} else {
						cellOrg1 = OrgRow1.createCell(4);
						cellOrg1.setCellStyle(styleOk);
						cellOrg1.setCellValue("No tiene planes");
					}

					cellOrg1 = OrgRow1.createCell(5);
					cellOrg1.setCellStyle(style1);
					cellOrg1.setCellValue(administradores);
					row++;
				}
			}
		}

		for (int y = 0; y < 50; y++) {
			sheet.autoSizeColumn(y);
		}

		// crea el archivo excel

		Date dateXls = new Date();
		SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");

		String archivo = "ReporteDependenciasOmisivas_" + hourdateFormat.format(dateXls) + ".xls";

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + archivo);

		ServletOutputStream file = response.getOutputStream();

		workbook.write(file);
		file.close();

		forward = "exito";
		organizacionservice.close();

		return mapping.findForward(forward);
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
	
	private List<Iniciativa> obtenerIniciativas(List<Iniciativa> iniciativas) throws Exception {
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

	private void verificarInformes(int row, HSSFSheet sheet, HSSFRow OrgRow1, CellStyle styleNotOk, CellStyle styleOk,
			SimpleDateFormat date, Date fechaActualDate, Long organizacionId) throws ParseException {
		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance()
				.openStrategosExplicacionesService();
		Map<String, Object> filtros = new HashMap<String, Object>();

		filtros.put("tipo", "1");
		filtros.put("objetoId", organizacionId.toString());

		PaginaLista paginaExplicaciones = strategosExplicacionesService.getExplicaciones(1, 30, "fecha", "DESC", true,
				filtros);

		List<Explicacion> explicaciones = paginaExplicaciones.getLista();
		if (explicaciones.size() > 0) {
			Explicacion explicacionUltima = explicaciones.get(0);
			String ultimaCarga = obtenerTrimestre(explicacionUltima.getFecha());
			Date fechaUltimaCarga = date.parse(ultimaCarga);
			if (fechaUltimaCarga.before(fechaActualDate) || explicacionUltima.getAdjuntosExplicacion().size() == 0) {
				HSSFCell cellOrg1 = OrgRow1.createCell(3);
				cellOrg1.setCellStyle(styleNotOk);
				cellOrg1.setCellValue("X");
			} else {
				HSSFCell cellOrg1 = OrgRow1.createCell(3);
				cellOrg1.setCellStyle(styleOk);
				cellOrg1.setCellValue("ok");
			}
		} else {
			HSSFCell cellOrg1 = OrgRow1.createCell(3);
			cellOrg1.setCellStyle(styleNotOk);
			cellOrg1.setCellValue("X");
		}
	}

	private void verificarEventos(int row, HSSFSheet sheet, HSSFRow OrgRow1, CellStyle styleNotOk, CellStyle styleOk,
			SimpleDateFormat date, Date fechaActualDate, Long organizacionId) throws ParseException {
		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance()
				.openStrategosExplicacionesService();
		Map<String, Object> filtros = new HashMap<String, Object>();

		filtros.put("tipo", "3");
		filtros.put("objetoId", organizacionId.toString());
		PaginaLista paginaExplicaciones = strategosExplicacionesService.getExplicaciones(1, 30, "fecha", "DESC", true,
				filtros);

		List<Explicacion> explicaciones = paginaExplicaciones.getLista();
		if (explicaciones.size() > 0) {
			Explicacion explicacionUltima = explicaciones.get(0);
			String ultimaCarga = obtenerTrimestre(explicacionUltima.getFecha());
			Date fechaUltimaCarga = date.parse(ultimaCarga);
			if (fechaUltimaCarga.before(fechaActualDate) || explicacionUltima.getAdjuntosExplicacion().size() == 0) {
				HSSFCell cellOrg1 = OrgRow1.createCell(2);
				cellOrg1.setCellStyle(styleNotOk);
				cellOrg1.setCellValue("X");
			} else {
				HSSFCell cellOrg1 = OrgRow1.createCell(2);
				cellOrg1.setCellStyle(styleOk);
				cellOrg1.setCellValue("ok");
			}
		} else {
			HSSFCell cellOrg1 = OrgRow1.createCell(2);
			cellOrg1.setCellStyle(styleNotOk);
			cellOrg1.setCellValue("X");
		}
	}

	private void dibujarIndicadores(int row, HSSFSheet sheet, HSSFRow OrgRow2, CellStyle styleNotOk, CellStyle styleOk,
			Plan plan, StrategosPlanesService strategosPlanesService) throws Exception {
		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance()
				.openStrategosPerspectivasService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
				.openStrategosIndicadoresService();

		Map<String, Object> filtros = new HashMap<String, Object>();
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
			boolean indAtrasados = obtenerIndicadores(nivel, perspectivas, plan, strategosIndicadoresService,
					strategosPerspectivasService, strategosPlanesService);

			if (indAtrasados) {
				HSSFCell cellOrg2 = OrgRow2.createCell(4);
				cellOrg2.setCellStyle(styleNotOk);
				cellOrg2.setCellValue("x");
			} else {
				HSSFCell cellOrg2 = OrgRow2.createCell(4);
				cellOrg2.setCellStyle(styleOk);
				cellOrg2.setCellValue("ok");
			}
		}
	}

	private boolean obtenerIndicadores(Integer nivel, List<Perspectiva> perspectivasPlan, Plan plan,
			StrategosIndicadoresService strategosIndicadoresService,
			StrategosPerspectivasService strategosPerspectivasService, StrategosPlanesService strategosPlanesService)
			throws Exception {

		boolean indAtrasados = false;

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
									indAtrasados = true;
									break;
								}
							}
						}
					}
				}
			}
		}
		return indAtrasados;
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

		return trimestre-1 + "/" + ano;
	}
}
