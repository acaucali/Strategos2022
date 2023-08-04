package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import org.apache.struts.actions.DownloadAction.FileStreamInfo;
import org.apache.struts.actions.DownloadAction.StreamInfo;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.model.util.LapsoTiempo;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.planes.model.util.TipoMeta;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;

public class PlanVisualizarPlanReporteXlsAction extends VgcAction {

	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
		navBar.agregarUrl(url, nombre);
	}

	@Override
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
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance()
				.openStrategosIniciativasService();
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance()
				.openStrategosMedicionesService();

		Plan plan = (Plan) strategosPerspectivasService.load(Plan.class, reporte.getPlanId());
		reporte.setPlantillaPlanes((PlantillaPlanes) strategosPerspectivasService.load(PlantillaPlanes.class,
				new Long(plan.getMetodologiaId())));

		Perspectiva perspectiva = null;

		perspectiva = strategosPerspectivasService.getPerspectivaRaiz(reporte.getPlanId());

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance()
				.openStrategosPlanesService();
		ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan();
		strategosPlanesService.close();

		perspectiva.setConfiguracionPlan(configuracionPlan);

		Workbook objWB = new XSSFWorkbook();

		Cell celda = null;

		// Creo la hoja
		Sheet hoja1 = objWB.createSheet("Visualizar Plan Detallado");

		int numeroFila = 1;
		int numeroCelda = 1;
		Row fila = hoja1.createRow(numeroFila++);

		celda = fila.createCell(numeroCelda);
		celda.setCellType(Cell.CELL_TYPE_STRING);
		celda.setCellValue(mensajes.getMessage("jsp.reportes.plan.visualizar.titulo"));

		// Subtitulo
		String subTitulo = mensajes.getMessage("jsp.reportes.plan.meta.organizacion") + " : "
				+ ((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getNombre();
		if (subTitulo != null) {
			numeroCelda = 1;
			fila = hoja1.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			celda.setCellValue(subTitulo);
		}

		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");

		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");

		String texto = null;
		Integer nivel = 0;

		texto = mensajes.getMessage("jsp.reportes.plan.meta.plan") + " : " + perspectiva.getNombreCompleto();

		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue(texto);

		numeroCelda = 1;
		fila = hoja1.createRow(numeroFila++);
		celda = fila.createCell(numeroCelda);
		celda.setCellValue("");

		Map<String, Object> filtros = new HashMap<String, Object>();

		filtros.put("padreId", perspectiva.getPerspectivaId());
		String[] orden = new String[1];
		String[] tipoOrden = new String[1];
		orden[0] = "nombre";
		tipoOrden[0] = "asc";
		Integer count = 0;
		List<Perspectiva> perspectivas = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);
		if (perspectivas.size() > 0) {
			if (perspectiva.getPadreId() == null || perspectiva.getPadreId() == 0L)
				nivel = 0;
			else
				nivel++;

			for (Perspectiva perspectivaHija : perspectivas) {
				perspectivaHija.setConfiguracionPlan(configuracionPlan);

				texto = new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction()
						.getNombrePerspectiva(reporte, nivel) + " : " + perspectivaHija.getNombreCompleto();
				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue(texto);

				numeroFila = dibujarInformacionIndicador(numeroFila, nivel, reporte, perspectivaHija, objWB, hoja1,
						strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes,
						request);

				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");

				numeroFila = dibujarInformacionIniciativa(numeroFila, nivel, reporte, perspectivaHija, objWB, hoja1,
						strategosMedicionesService, strategosIniciativasService, strategosIndicadoresService,
						strategosPerspectivasService, mensajes, request);

				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");

				numeroFila = buildReporte(numeroFila, nivel, reporte, perspectivaHija, objWB, hoja1, configuracionPlan,
						strategosMedicionesService, strategosIniciativasService, strategosIndicadoresService,
						strategosPerspectivasService, mensajes, request);

				numeroCelda = 1;
				fila = hoja1.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");
			}
		}

		strategosPerspectivasService.close();
		strategosIndicadoresService.close();
		strategosIniciativasService.close();
		strategosMedicionesService.close();

		Date date = new Date();
		SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");

		String archivo = "reporteConsolidado_" + hourdateFormat.format(date) + ".xlsx";

		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + archivo);

		ServletOutputStream file = response.getOutputStream();

		objWB.write(file);
		file.close();

		return mapping.findForward(forward);

	}

	private int buildReporte(int numeroFila, int nivel, ReporteForm reporte, Perspectiva perspectiva, Workbook objWB,
			Sheet hoja, ConfiguracionPlan configuracionPlan, StrategosMedicionesService strategosMedicionesService,
			StrategosIniciativasService strategosIniciativasService,
			StrategosIndicadoresService strategosIndicadoresService,
			StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes,
			HttpServletRequest request) throws Exception {
		// Lista de perspectivas del primer nivel
		String texto;
		Cell celda = null;
		Row fila;
		int numeroCelda = 1;

		Map<String, Object> filtros = new HashMap<String, Object>();

		filtros.put("padreId", perspectiva.getPerspectivaId());
		String[] orden = new String[1];
		String[] tipoOrden = new String[1];
		orden[0] = "nombre";
		tipoOrden[0] = "asc";
		List<Perspectiva> perspectivas = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);

		if (perspectivas.size() > 0) {
			for (Perspectiva perspectivaHija : perspectivas) {
				perspectivaHija.setConfiguracionPlan(configuracionPlan);

				texto = new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction()
						.getNombrePerspectiva(reporte, (nivel + 1)) + " : " + perspectivaHija.getNombreCompleto();
				numeroCelda = 1;
				fila = hoja.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue(texto);

				numeroFila = dibujarInformacionIndicador(numeroFila, nivel, reporte, perspectivaHija, objWB, hoja,
						strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes,
						request);

				numeroCelda = 1;
				fila = hoja.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");

				numeroFila = dibujarInformacionIniciativa(numeroFila, nivel, reporte, perspectivaHija, objWB, hoja,
						strategosMedicionesService, strategosIniciativasService, strategosIndicadoresService,
						strategosPerspectivasService, mensajes, request);

				numeroCelda = 1;
				fila = hoja.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");

				numeroFila = buildReporte(numeroFila, (nivel + 1), reporte, perspectivaHija, objWB, hoja,
						configuracionPlan, strategosMedicionesService, strategosIniciativasService,
						strategosIndicadoresService, strategosPerspectivasService, mensajes, request);

				numeroCelda = 1;
				fila = hoja.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda);
				celda.setCellValue("");
			}
		}
		return numeroFila;
	}

	private int dibujarInformacionIndicador(int numeroFila, int nivel, ReporteForm reporte, Perspectiva perspectiva,
			Workbook objWB, Sheet hoja, StrategosMedicionesService strategosMedicionesService,
			StrategosIndicadoresService strategosIndicadoresService,
			StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes,
			HttpServletRequest request) throws Exception {
		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance()
				.openStrategosPlanesService(strategosMetasService);
		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance()
				.openStrategosExplicacionesService();
		Map<String, Object> filtros = new HashMap<String, Object>();
		String texto;

		Cell celda = null;
		Row fila;
		int numeroCelda = 1;

		filtros = new HashMap<String, Object>();
		filtros.put("perspectivaId", perspectiva.getPerspectivaId().toString());
		List<Indicador> indicadores = strategosIndicadoresService
				.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, true).getLista();

		if (indicadores.size() > 0) {
			String[][] columnas = new String[9][2];
			int contador = 0;
			columnas[contador][0] = "15";
			columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.alerta");

			contador++;
			columnas[contador][0] = "100";
			columnas[contador][1] = reporte.getPlantillaPlanes().getNombreIndicadorSingular();

			contador++;
			columnas[contador][0] = "30";
			columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.real");

			contador++;
			columnas[contador][0] = "30";
			columnas[contador][1] = "ultimo Periodo Medicion";

			contador++;
			columnas[contador][0] = "35";
			columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.metaanula");

			contador++;
			columnas[contador][0] = "35";
			columnas[contador][1] = "Meta Parcial";

			contador++;
			columnas[contador][0] = "15";
			columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.unidad");

			contador++;
			columnas[contador][0] = "15";
			columnas[contador][1] = "Frecuencia";

			contador++;
			columnas[contador][0] = "15";
			columnas[contador][1] = "Naturaleza";

			numeroFila = crearEncabezado(columnas, numeroFila, reporte, mensajes, strategosMedicionesService,
					strategosIndicadoresService, objWB, hoja, request);

			for (Indicador indicador : indicadores) {
				Byte alerta = strategosPlanesService.getAlertaIndicadorPorPlan(indicador.getIndicadorId(),
						reporte.getPlanId());
				texto = null;
				if (alerta == null)
					texto = "Blanca";
				else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
					texto = "Roja";
				else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
					texto = "Verde";
				else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
					texto = "Amarillo";
				numeroCelda = 1;
				fila = hoja.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(texto);

				// Nombre del indicador
				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(indicador.getNombre());

				// % Cumplimiento Real
				texto = "";
				if (indicador.getUltimaMedicionFormateada() != null && indicador.getUltimaMedicionFormateada() != null)
					texto = indicador.getUltimaMedicionFormateada();
				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(texto);

				// Ultima Medicion
				texto = "";
				if (indicador.getFechaUltimaMedicion() != null)
					texto = indicador.getFechaUltimaMedicion();
				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(texto);

				// % Meta Anual
				texto = "";
				if (indicador.getFechaUltimaMedicion() != null) {
					List<?> metas = strategosMetasService.getMetasAnuales(indicador.getIndicadorId(),
							reporte.getPlanId(), indicador.getFechaUltimaMedicionAno(),
							indicador.getFechaUltimaMedicionAno(), false);
					if (metas.size() > 0) {
						indicador.setMetaAnual(((Meta) metas.get(0)).getValor());
						texto = (indicador.getMetaAnual() != null
								? VgcFormatter.formatearNumero(indicador.getMetaAnual())
								: "");
					}
				}
				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(texto);

				// Meta Parcial
				texto = "";
				if (indicador.getFechaUltimaMedicion() != null) {
					List<?> metas = strategosMetasService.getMetasParciales(indicador.getIndicadorId(),
							reporte.getPlanId(), indicador.getFrecuencia(), indicador.getOrganizacion().getMesCierre(),
							indicador.getCorte(), indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcial(),
							indicador.getFechaUltimaMedicionAno(), indicador.getFechaUltimaMedicionAno(),
							indicador.getFechaUltimaMedicionPeriodo(), indicador.getFechaUltimaMedicionPeriodo());
					if (metas.size() > 0) {
						Meta metaParcial = (Meta) metas.get(0);
						indicador.setMetaParcial(metaParcial.getValor());

						texto = indicador.getMetaParcial() != null
								? VgcFormatter.formatearNumero(indicador.getMetaParcial())
								: "";
					}
				}
				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(texto);

				// Unidad
				texto = "";
				if (indicador.getUnidadId() != null) {
					UnidadMedida unidad = (UnidadMedida) strategosPlanesService.load(UnidadMedida.class,
							indicador.getUnidadId());
					if (unidad != null)
						texto = unidad.getNombre();
				}
				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(texto);

				// frecuencia
				texto = "";
				if (indicador.getFrecuenciaNombre() != null)
					texto = indicador.getFrecuenciaNombre();
				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(texto);

				// Naturaleza
				texto = "";
				if (indicador.getNaturalezaNombre() != null)
					texto = indicador.getNaturalezaNombre();
				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(texto);
			}
		} else {
			texto = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noindicadores",
					reporte.getPlantillaPlanes().getNombreIndicadorPlural().toLowerCase(),
					new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction()
							.getNombrePerspectiva(reporte, (nivel)).toLowerCase());
			numeroCelda = 1;
			fila = hoja.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			// celda.setCellStyle(getEstiloCuerpo(objWB));
			celda.setCellType(Cell.CELL_TYPE_STRING);
			celda.setCellValue(texto);
		}

		strategosPlanesService.close();
		strategosMetasService.close();
		strategosExplicacionesService.close();

		return numeroFila;
	}

	private int dibujarInformacionIniciativa(int numeroFila, int nivel, ReporteForm reporte, Perspectiva perspectiva,
			Workbook objWB, Sheet hoja, StrategosMedicionesService strategosMedicionesService,
			StrategosIniciativasService strategosIniciativasService,
			StrategosIndicadoresService strategosIndicadoresService,
			StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes,
			HttpServletRequest request) throws Exception {
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance()
				.openStrategosPlanesService();
		Map<String, Object> filtros = new HashMap<String, Object>();
		String texto;

		Cell celda = null;
		Row fila;
		int numeroCelda = 1;

		filtros = new HashMap<String, Object>();
		filtros.put("perspectivaId", perspectiva.getPerspectivaId().toString());
		if (reporte.getFiltro().getHistorico() != null
				&& reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
			filtros.put("historicoDate", "IS NULL");
		else if (reporte.getFiltro().getHistorico() != null
				&& reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
			filtros.put("historicoDate", "IS NOT NULL");
		List<Iniciativa> iniciativas = strategosIniciativasService.getIniciativas(0, 0, "nombre", "ASC", true, filtros)
				.getLista();

		if (iniciativas.size() > 0) {
			String[][] columnas = new String[9][2];
			int contador = 0;
			columnas[contador][0] = "15";
			columnas[contador][1] = mensajes.getMessage("jsp.reportes.plan.consolidado.reporte.columna.alerta");

			contador++;
			columnas[contador][0] = "100";
			columnas[contador][1] = reporte.getPlantillaPlanes().getNombreIniciativaPlural();

			contador++;
			columnas[contador][0] = "35";
			columnas[contador][1] = mensajes
					.getMessage("jsp.reportes.plan.consolidado.reporte.columna.porcentaje.ejecutado.acumulado");

			contador++;
			columnas[contador][0] = "30";
			columnas[contador][1] = mensajes
					.getMessage("jsp.reportes.plan.consolidado.reporte.columna.porcentaje.esperado.acumulado");

			contador++;
			columnas[contador][0] = "30";
			columnas[contador][1] = "Fecha Ultima Medicion";

			contador++;
			columnas[contador][0] = "30";
			columnas[contador][1] = "Ultima Medicion";

			contador++;
			columnas[contador][0] = "30";
			columnas[contador][1] = "Frecuencia";

			contador++;
			columnas[contador][0] = "30";
			columnas[contador][1] = "Estatus";

			contador++;
			columnas[contador][0] = "30";
			columnas[contador][1] = "Año Formulacion";

			numeroFila = crearEncabezado(columnas, numeroFila, reporte, mensajes, strategosMedicionesService,
					strategosIndicadoresService, objWB, hoja, request);

			Date fechaActual = new Date();
			Integer anioActual = fechaActual.getYear() + 1900;
			Integer mesActual = fechaActual.getMonth() + 1;

			for (Iniciativa iniciativa : iniciativas) {

				Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
						iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

				LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes((anioActual),
						(anioActual), (1), (mesActual), indicador.getFrecuencia().byteValue());

				int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
				int periodoFin = periodoInicio;
				if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
					periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
				boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue()
						&& indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo()
								.byteValue());
				List<Medicion> mediciones = strategosMedicionesService.getMedicionesPorFrecuencia(
						indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), anioActual, anioActual, periodoInicio,
						periodoFin, indicador.getFrecuencia(), indicador.getFrecuencia(), acumular, acumular);

				Medicion ultimaMedicion = strategosMedicionesService.getUltimaMedicionConValor(mediciones);

				Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(),
						indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(),
						indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
				String programado = null;
				if (medicionReal != null) {
					List<Medicion> mediciones2 = strategosMedicionesService.getMedicionesPeriodo(
							indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), null,
							medicionReal.getMedicionId().getAno(), null, medicionReal.getMedicionId().getPeriodo());

					for (Iterator<Medicion> iter2 = mediciones2.iterator(); iter2.hasNext();) {
						Medicion medicion = iter2.next();
						programado = medicion.getValorFormateado("0.00");
					}
				}
				// Alerta
				Byte alerta = iniciativa.getAlerta();
				texto = null;
				if (alerta == null)
					texto = "Blanca";
				else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
					texto = "Roja";
				else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
					texto = "Verde";
				else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
					texto = "Amarillo";
				numeroCelda = 1;
				fila = hoja.createRow(numeroFila++);
				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(texto);

				// Nombre de la iniciativa
				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(iniciativa.getNombre());

				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(iniciativa.getPorcentajeCompletadoFormateado());

				texto = "";
				if(programado != null)
					texto = programado;
				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(texto);

				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(iniciativa.getFechaUltimaMedicion());

				texto = "";
				if (ultimaMedicion != null)
					texto = ultimaMedicion.getValorFormateado("0.00");
				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(texto);

				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(iniciativa.getFrecuenciaNombre());

				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(iniciativa.getEstatus().getNombre());

				celda = fila.createCell(numeroCelda++);
				// celda.setCellStyle(getEstiloCuerpo(objWB));
				celda.setCellType(Cell.CELL_TYPE_STRING);
				celda.setCellValue(iniciativa.getAnioFormulacion());
			}
		} else {
			texto = mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noindicadores",
					reporte.getPlantillaPlanes().getNombreIniciativaPlural().toLowerCase(),
					new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction()
							.getNombrePerspectiva(reporte, (nivel)).toLowerCase());
			numeroCelda = 1;
			fila = hoja.createRow(numeroFila++);
			celda = fila.createCell(numeroCelda);
			// celda.setCellStyle(getEstiloCuerpo(objWB));
			celda.setCellType(Cell.CELL_TYPE_STRING);
			celda.setCellValue(texto);
		}

		strategosPlanesService.close();

		return numeroFila;
	}

	private CellStyle getEstiloEncabezado(Workbook objWB) {
		// Aunque no es necesario podemos establecer estilos a las celdas.
		// Primero, establecemos el tipo de fuente
		Font fuente = objWB.createFont();
		fuente.setFontHeightInPoints((short) 11);
		fuente.setFontName(HSSFFont.FONT_ARIAL);
		fuente.setBoldweight(Font.BOLDWEIGHT_BOLD);

		// Luego creamos el objeto que se encargar� de aplicar el estilo a la
		// celda
		CellStyle estiloCelda = objWB.createCellStyle();
		estiloCelda.setWrapText(true);
		estiloCelda.setAlignment(CellStyle.ALIGN_JUSTIFY);
		estiloCelda.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		estiloCelda.setFont(fuente);

		// Tambi�n, podemos establecer bordes...
		estiloCelda.setBorderBottom(CellStyle.BORDER_MEDIUM);
		estiloCelda.setBottomBorderColor((short) 8);
		estiloCelda.setBorderLeft(CellStyle.BORDER_MEDIUM);
		estiloCelda.setLeftBorderColor((short) 8);
		estiloCelda.setBorderRight(CellStyle.BORDER_MEDIUM);
		estiloCelda.setRightBorderColor((short) 8);
		estiloCelda.setBorderTop(CellStyle.BORDER_MEDIUM);
		estiloCelda.setTopBorderColor((short) 8);

		// Establecemos el tipo de sombreado de nuestra celda
		estiloCelda.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		estiloCelda.setFillPattern(CellStyle.SOLID_FOREGROUND);

		return estiloCelda;
	}

	private CellStyle getEstiloCuerpo(Workbook objWB) {
		// Aunque no es necesario podemos establecer estilos a las celdas.
		// Primero, establecemos el tipo de fuente
		Font fuente = objWB.createFont();
		fuente.setFontHeightInPoints((short) 11);
		fuente.setFontName(HSSFFont.FONT_ARIAL);
		fuente.setBoldweight(Font.BOLDWEIGHT_NORMAL);

		// Luego creamos el objeto que se encargar� de aplicar el estilo a la
		// celda
		CellStyle estiloCelda = objWB.createCellStyle();
		estiloCelda.setWrapText(true);
		estiloCelda.setAlignment(CellStyle.ALIGN_JUSTIFY);
		estiloCelda.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		estiloCelda.setFont(fuente);

		// Tambi�n, podemos establecer bordes...
		estiloCelda.setBorderBottom(CellStyle.BORDER_MEDIUM);
		estiloCelda.setBottomBorderColor((short) 8);
		estiloCelda.setBorderLeft(CellStyle.BORDER_MEDIUM);
		estiloCelda.setLeftBorderColor((short) 8);
		estiloCelda.setBorderRight(CellStyle.BORDER_MEDIUM);
		estiloCelda.setRightBorderColor((short) 8);
		estiloCelda.setBorderTop(CellStyle.BORDER_MEDIUM);
		estiloCelda.setTopBorderColor((short) 8);

		// Establecemos el tipo de sombreado de nuestra celda
		estiloCelda.setFillForegroundColor(HSSFColor.WHITE.index);
		estiloCelda.setFillPattern(CellStyle.SOLID_FOREGROUND);

		return estiloCelda;
	}

	private int crearEncabezado(String[][] columnas, int numeroFila, ReporteForm reporte, MessageResources mensajes,
			StrategosMedicionesService strategosMedicionesService,
			StrategosIndicadoresService strategosIndicadoresService, Workbook objWB, Sheet hoja,
			HttpServletRequest request) throws Exception {
		int numeroCelda = 1;
		Row fila = hoja.createRow(numeroFila++);
		Cell celda = null;
		for (int k = 0; k < columnas.length; k++) {
			celda = fila.createCell(k + numeroCelda);
			celda.setCellStyle(getEstiloEncabezado(objWB));

			celda.setCellType(Cell.CELL_TYPE_STRING);
			celda.setCellValue(columnas[k][1]);
		}

		return numeroFila;
	}
}
