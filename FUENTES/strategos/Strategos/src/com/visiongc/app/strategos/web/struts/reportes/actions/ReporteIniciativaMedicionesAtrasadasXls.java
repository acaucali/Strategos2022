package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
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
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.FiltroForm;

public class ReporteIniciativaMedicionesAtrasadasXls extends VgcAction {
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
		navBar.agregarUrl(url, nombre);
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/** Se ejecuta el servicio del Action padre (obligatorio!!!) */
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		String alcance = (request.getParameter("alcance"));
		String orgId = (request.getParameter("organizacionId"));
		String tipo = (request.getParameter("tipo"));
		String estatus = (request.getParameter("estatus"));
		String ano = (request.getParameter("ano"));
		int estatusId = Integer.parseInt(estatus);
		String todos = (request.getParameter("todos"));

		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;

		StrategosIniciativasService iniciativaservice = StrategosServiceFactory.getInstance()
				.openStrategosIniciativasService();
		StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance()
				.openStrategosOrganizacionesService();

		List<OrganizacionStrategos> organizaciones = organizacionservice
				.getOrganizaciones(0, 0, "organizacionId", "ASC", true, filtros).getLista();

		// organizacion seleccionada
		if (request.getParameter("alcance").equals("1")) {
			int x = 1;

			String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre")
					: "";
			Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null
					&& request.getParameter("selectHitoricoType") != "")
							? Byte.parseByte(request.getParameter("selectHitoricoType"))
							: HistoricoType.getFiltroHistoricoNoMarcado();

			FiltroForm filtro = new FiltroForm();
			filtro.setHistorico(selectHitoricoType);
			if (filtroNombre.equals(""))
				filtro.setNombre(null);
			else
				filtro.setNombre(filtroNombre);
			reporte.setFiltro(filtro);
			filtro.setAnio("" + ano);
			if (reporte.getAlcance().byteValue() == reporte.getAlcanceObjetivo().byteValue())
				filtros.put("organizacionId",
						((OrganizacionStrategos) request.getSession().getAttribute("organizacion"))
								.getOrganizacionId());
			if (reporte.getFiltro().getHistorico() != null
					&& reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
				filtros.put("historicoDate", "IS NULL");
			else if (reporte.getFiltro().getHistorico() != null
					&& reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
				filtros.put("historicoDate", "IS NOT NULL");
			if (reporte.getFiltro().getNombre() != null)
				filtros.put("nombre", reporte.getFiltro().getNombre());
			if (!tipo.equals("0")) {
				filtros.put("tipoId", tipo);
			}
			if (todos.equals("false")) {
				filtros.put("anio", ano);
			}
			filtros.put("estatusId", estatus);

			List<Iniciativa> iniciativas = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros)
					.getLista();

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();
			workbook.setSheetName(0, "Hoja excel");

			CellStyle headerStyle = workbook.createCellStyle();
			Font font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			headerStyle.setFont(font);

			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);

			HSSFRow headerRow = sheet.createRow(0);

			String header = mensajes.getMessage("jsp.reportes.iniciativa.ejecucion.mediciones.atrasadas");
			HSSFCell cell = headerRow.createCell(1);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(header);

			HSSFRow dataRow = sheet.createRow(1);
			dataRow.createCell(0)
					.setCellValue(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.iniciativa"));
			dataRow.createCell(1)
					.setCellValue(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje"));
			dataRow.createCell(2).setCellValue(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.fecha"));
			dataRow.createCell(3).setCellValue(mensajes.getMessage("jsp.editariniciativa.ficha.frecuencia"));
			dataRow.createCell(4).setCellValue(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.tipo"));
			dataRow.createCell(5).setCellValue(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.anio"));
			dataRow.createCell(6)
					.setCellValue(mensajes.getMessage("action.reporte.estatus.iniciativa.mediciones.atrasadas"));

			if (iniciativas.size() > 0) {
				for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
					Iniciativa iniciativa = iter.next();

					Date fechaUltimaMedicion;
					Integer periodo = obtenerFecha(iniciativa.getFrecuencia());
					String fecha = String.valueOf((periodo-1)) + "/" + String.valueOf(new Date().getYear() + 1900);
					SimpleDateFormat date = new SimpleDateFormat("MM/yyyy");
					Date fechaActualDate = date.parse(fecha);
					String ultimaMedicion = iniciativa.getFechaUltimaMedicion();
					String frecuencia = obtenerFrecuencia(iniciativa.getFrecuencia());

					HSSFRow dataRow1 = sheet.createRow(x + 1);
					dataRow1.createCell(0).setCellValue(iniciativa.getNombre());
					if (iniciativa.getPorcentajeCompletadoFormateado() == null) {
						dataRow1.createCell(1).setCellValue("");
					} else {
						dataRow1.createCell(1).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
					}
					if (iniciativa.getFechaUltimaMedicion() == null) {
						dataRow1.createCell(2).setCellValue("");
					} else {
						dataRow1.createCell(2).setCellValue(iniciativa.getFechaUltimaMedicion());
					}
					if (iniciativa.getEstatus() == null) {
						dataRow1.createCell(3).setCellValue("");
					} else {
						dataRow1.createCell(3).setCellValue(frecuencia);
					}
					if (iniciativa.getTipoProyecto() == null) {
						dataRow1.createCell(4).setCellValue("");
					} else {
						dataRow1.createCell(4).setCellValue(iniciativa.getTipoProyecto().getNombre());
					}
					if (iniciativa.getAnioFormulacion() == null) {
						dataRow1.createCell(5).setCellValue("");
					} else {
						dataRow1.createCell(5).setCellValue(iniciativa.getAnioFormulacion());
					}
					if (iniciativa.getFechaUltimaMedicion() != null) {
						fechaUltimaMedicion = date.parse(ultimaMedicion);
						if (fechaUltimaMedicion.before(fechaActualDate)) {
							dataRow1.createCell(6).setCellValue("Sí");
						} else {
							dataRow1.createCell(6).setCellValue("");
						}
					} else {
						dataRow1.createCell(6).setCellValue("");
					}
					x = x + 1;
				}

				// Actividades
				for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
					Iniciativa iniciativa = iter.next();
					StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance()
							.openStrategosMetasService();
					StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory
							.getInstance().openStrategosPryActividadesService();
					StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
							.openStrategosIndicadoresService();

					filtros = new HashMap();
					filtros.put("proyectoId", iniciativa.getProyectoId());
					List<PryActividad> actividades = strategosPryActividadesService
							.getActividades(0, 0, "fila", "ASC", true, filtros).getLista();
					if (actividades.size() > 0) {
						HSSFRow dataRow2 = sheet.createRow(x = x + 3);
						dataRow2.createCell(0).setCellValue(
								mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.actividades"));
						dataRow2.createCell(1)
								.setCellValue(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio"));
						dataRow2.createCell(2).setCellValue(
								mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion"));
						dataRow2.createCell(3)
								.setCellValue(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.fecha"));
						dataRow2.createCell(4).setCellValue(
								mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.duracion"));
						dataRow2.createCell(5).setCellValue(
								mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.ejecutado"));
						dataRow2.createCell(6).setCellValue(mensajes
								.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.programado"));
						dataRow2.createCell(7).setCellValue(
								mensajes.getMessage("action.reporte.estatus.iniciativa.mediciones.atrasadas"));
						for (Iterator<PryActividad> iter1 = actividades.iterator(); iter1.hasNext();) {
							PryActividad actividad = iter1.next();

							Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
									actividad.getIndicadorId());

							Date fechaUltimaMedicionActividad;
							Integer periodoActividad = obtenerFecha(iniciativa.getFrecuencia());
							String fecha = String.valueOf((periodoActividad-1)) + "/"
									+ String.valueOf(new Date().getYear() + 1900);
							SimpleDateFormat date = new SimpleDateFormat("MM/yyyy");
							Date fechaActualDate = date.parse(fecha);
							String ultimaMedicionActividad = actividad.getFechaUltimaMedicion();

							HSSFRow dataRow3 = sheet.createRow(x + 1);
							dataRow3.createCell(0).setCellValue(actividad.getNombre());
							dataRow3.createCell(1).setCellValue(
									VgcFormatter.formatearFecha(actividad.getComienzoPlan(), "formato.fecha.corta"));
							dataRow3.createCell(2).setCellValue(
									VgcFormatter.formatearFecha(actividad.getFinPlan(), "formato.fecha.corta"));
							if (actividad.getFechaUltimaMedicion() != null) {
								dataRow3.createCell(3).setCellValue(actividad.getFechaUltimaMedicion());
							} else {
								dataRow3.createCell(3).setCellValue("");
							}
							dataRow3.createCell(4)
									.setCellValue(VgcFormatter.formatearNumero(actividad.getDuracionPlan()));
							dataRow3.createCell(5)
									.setCellValue(actividad.getPorcentajeEjecutado() != null
											? actividad.getPorcentajeEjecutadoFormateado()
											: "");
							dataRow3.createCell(6)
									.setCellValue(actividad.getPorcentajeEsperado() != null
											? actividad.getPorcentajeEsperadoFormateado()
											: "");
							if (actividad.getFechaUltimaMedicion() != null) {
								fechaUltimaMedicionActividad = date.parse(ultimaMedicionActividad);
								if (fechaUltimaMedicionActividad.before(fechaActualDate)) {
									dataRow3.createCell(7).setCellValue("Si");
								} else {
									dataRow3.createCell(7).setCellValue("Si");
								}
							} else
								dataRow3.createCell(7).setCellValue("Si");
							x = x + 1;
						}

					}
				}
			}

			Date date = new Date();

			SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
			String archivo = "IniciativasMedicionesAtrasadas_" + hourdateFormat.format(date) + ".xls";
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment;filename=" + archivo);

			ServletOutputStream file = response.getOutputStream();

			workbook.write(file);
			file.close();
		}

		return mapping.findForward(forward);
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
}
