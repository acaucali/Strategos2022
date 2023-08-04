package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
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
import com.visiongc.app.strategos.planes.model.util.TipoPerspectiva;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.report.Tabla;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.report.VgcFormatoReporte;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.VgcFormatter;

public class PlanVisualizarPlanReportePdfAction extends VgcReporteBasicoAction {
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 57;
	private int maxLineasAntesTabla = 4;

	@Override
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception {
		return mensajes.getMessage("jsp.reportes.plan.visualizar.titulo");
	}

	@Override
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response,
			Document documento) throws Exception {
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		reporte.setPlanId(
				request.getParameter("planId") != null ? Long.parseLong(request.getParameter("planId")) : null);

		visualizarPlan(reporte, documento, request, mensajes);
	}

	private void visualizarPlan(ReporteForm reporte, Document documento, HttpServletRequest request,
			MessageResources mensajes) throws Exception {
		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance()
				.openStrategosPerspectivasService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
				.openStrategosIndicadoresService();
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance()
				.openStrategosMedicionesService();
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance()
				.openStrategosIniciativasService();

		Plan plan = (Plan) strategosPerspectivasService.load(Plan.class, reporte.getPlanId());
		reporte.setPlantillaPlanes((PlantillaPlanes) strategosPerspectivasService.load(PlantillaPlanes.class,
				new Long(plan.getMetodologiaId())));

		// Raiz del plan
		lineas = 10;
		tamanoPagina = inicioTamanoPagina;
		Perspectiva perspectiva = null;

		perspectiva = strategosPerspectivasService.getPerspectivaRaiz(reporte.getPlanId());

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance()
				.openStrategosPlanesService();
		ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan();
		strategosPlanesService.close();

		perspectiva.setConfiguracionPlan(configuracionPlan);

		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());

		// Nombre de la Organizacion, plan y periodo del reporte
		font.setSize(12);
		font.setStyle(Font.NORMAL);

		Paragraph texto = new Paragraph(
				mensajes.getMessage("jsp.reportes.plan.meta.organizacion") + " : "
						+ ((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getNombre(),
				font);
		texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea((lineas + 5), inicioLineas);

		texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.meta.titulo.rango") + " : " + reporte.getAno(),
				font);
		texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea((lineas), inicioLineas);

		Integer nivel = 0;

		texto = new Paragraph(
				mensajes.getMessage("jsp.reportes.plan.meta.plan") + " : " + perspectiva.getNombreCompleto(), font);
		texto.setAlignment(Element.ALIGN_CENTER);
		documento.add(texto);
		lineas = getNumeroLinea((lineas + 5), inicioLineas);

		font.setSize(10);
		font.setStyle(Font.NORMAL);
		fontBold.setSize(8);
		fontBold.setStyle(Font.BOLD);
		documento.add(lineaEnBlanco(font));
		lineas = getNumeroLinea(lineas, inicioLineas);

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

				texto = new Paragraph(
						new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction()
								.getNombrePerspectiva(reporte, nivel) + " : " + perspectivaHija.getNombreCompleto(),
						font);
				texto.setAlignment(Element.ALIGN_LEFT);
				documento.add(lineaEnBlanco(font));
				if (count >= 1) {
					documento.add(lineaEnBlanco(font));
					documento.add(lineaEnBlanco(font));
				}
				documento.add(texto);
				count++;

				dibujarInformacionIndicador(nivel, reporte, font, perspectivaHija, documento,
						strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes,
						request);

				dibujarInformacionIniciativa(nivel, reporte, font, perspectivaHija, documento,
						strategosMedicionesService, strategosIniciativasService, strategosIndicadoresService,
						strategosPerspectivasService, mensajes, request);

				buildReporte(nivel, reporte, font, perspectivaHija, documento, configuracionPlan,
						strategosMedicionesService, strategosIniciativasService, strategosIndicadoresService,
						strategosPerspectivasService, mensajes, request);
			}
		}
	}

	private void buildReporte(int nivel, ReporteForm reporte, Font font, Perspectiva perspectiva, Document documento,
			ConfiguracionPlan configuracionPlan, StrategosMedicionesService strategosMedicionesService,
			StrategosIniciativasService strategosIniciativasService,
			StrategosIndicadoresService strategosIndicadoresService,
			StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes,
			HttpServletRequest request) throws Exception {
		// Lista de perspectivas del primer nivel
		Paragraph texto;
		Map<String, Object> filtros = new HashMap<String, Object>();

		filtros.put("padreId", perspectiva.getPerspectivaId());
		String[] orden = new String[1];
		String[] tipoOrden = new String[1];
		orden[0] = "nombre";
		tipoOrden[0] = "asc";
		List<Perspectiva> perspectivas = strategosPerspectivasService.getPerspectivas(orden, tipoOrden, filtros);

		Integer count = 0;
		if (perspectivas.size() > 0) {
			for (Perspectiva perspectivaHija : perspectivas) {
				perspectivaHija.setConfiguracionPlan(configuracionPlan);

				texto = new Paragraph(
						new com.visiongc.app.strategos.web.struts.reportes.actions.PlanEjecucionReporteAction()
								.getNombrePerspectiva(reporte, (nivel + 1)) + " : "
								+ perspectivaHija.getNombreCompleto(),
						font);
				texto.setAlignment(Element.ALIGN_LEFT);
				documento.add(lineaEnBlanco(font));
				if (count >= 1) {
					documento.add(lineaEnBlanco(font));
				}
				documento.add(texto);
				count++;
				dibujarInformacionIndicador(nivel, reporte, font, perspectivaHija, documento,
						strategosMedicionesService, strategosIndicadoresService, strategosPerspectivasService, mensajes,
						request);

				dibujarInformacionIniciativa(nivel, reporte, font, perspectivaHija, documento,
						strategosMedicionesService, strategosIniciativasService, strategosIndicadoresService,
						strategosPerspectivasService, mensajes, request);

				buildReporte((nivel + 1), reporte, font, perspectivaHija, documento, configuracionPlan,
						strategosMedicionesService, strategosIniciativasService, strategosIndicadoresService,
						strategosPerspectivasService, mensajes, request);
			}
		}
	}

	private void dibujarInformacionIndicador(int nivel, ReporteForm reporte, Font font, Perspectiva perspectiva,
			Document documento, StrategosMedicionesService strategosMedicionesService,
			StrategosIndicadoresService strategosIndicadoresService,
			StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes,
			HttpServletRequest request) throws Exception {

		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance()
				.openStrategosPlanesService(strategosMetasService);

		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;

		filtros = new HashMap<String, Object>();
		filtros.put("perspectivaId", perspectiva.getPerspectivaId().toString());
		List<Indicador> indicadores = strategosIndicadoresService
				.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, true).getLista();

		Font fontCol = new Font(getConfiguracionPagina(request).getCodigoFuente());
		fontCol.setSize(9);
		fontCol.setStyle(Font.BOLD);

		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[10];
		if (indicadores.size() > 0) {

			columnas = new int[9];

			columnas[0] = 2;
			columnas[1] = 12;
			columnas[2] = 3;
			columnas[3] = 6;
			columnas[4] = 3;
			columnas[5] = 3;
			columnas[6] = 4;
			columnas[7] = 5;
			columnas[8] = 5;

			tabla.setAmplitudTabla(100);
			tabla.setTamanoFont(16);
			tabla.crearTabla(columnas);
			tabla.setAlineacionHorizontal(1);
			tabla.setFormatoFont(Font.NORMAL);
			tabla.setColorFondo(21, 60, 120);
			tabla.setColorLetra(255, 255, 255);
			tabla.agregarCelda("Alerta");
			tabla.agregarCelda("Indicador");
			tabla.agregarCelda("Real");
			tabla.agregarCelda("Ultimo periodo de medicion");
			tabla.agregarCelda("Meta anual");
			tabla.agregarCelda("Meta parcial");
			tabla.agregarCelda("Unidad");
			tabla.agregarCelda("Frecuencia");
			tabla.agregarCelda("Naturaleza");

			tabla.setColorFondo(255, 255, 255);
			tabla.setColorLetra(0, 0, 0);

			Byte tipo = 1;
			for (Indicador indicador : indicadores) {
				Byte alerta = strategosPlanesService.getAlertaIndicadorPorPlan(indicador.getIndicadorId(),
						reporte.getPlanId());

				texto = new Paragraph("", font);
				String url = obtenerCadenaRecurso(request);

				if (alerta == null) {
					Image image = Image
							.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaBlanca.gif"));
					texto.add(new Chunk(image, 0, 0));
				} else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
					Image image = Image
							.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaRoja.gif"));
					texto.add(new Chunk(image, 0, 0));
				} else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue()) {
					Image image = Image
							.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaVerde.gif"));
					texto.add(new Chunk(image, 0, 0));
				} else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
					Image image = Image
							.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif"));
					texto.add(new Chunk(image, 0, 0));
				}

				tabla.agregarCelda(texto);

				// Nombre
				tabla.agregarCelda(indicador.getNombre());

				// % Cumplimiento Real
				if (indicador.getUltimaMedicionFormateada() != null && indicador.getUltimaMedicionFormateada() != null)
					tabla.agregarCelda(indicador.getUltimaMedicionFormateada());
				else
					tabla.agregarCelda("");

				// Ultima Medicion
				if (indicador.getFechaUltimaMedicion() != null)
					tabla.agregarCelda(indicador.getFechaUltimaMedicion());
				else
					tabla.agregarCelda("");

				// Meta Anual
				if (indicador.getFechaUltimaMedicion() != null) {
					List<?> metas = strategosMetasService.getMetasAnuales(indicador.getIndicadorId(),
							reporte.getPlanId(), indicador.getFechaUltimaMedicionAno(),
							indicador.getFechaUltimaMedicionAno(), false);
					if (metas.size() > 0) {
						indicador.setMetaAnual(((Meta) metas.get(0)).getValor());
						tabla.agregarCelda(indicador.getMetaAnual() != null
								? VgcFormatter.formatearNumero(indicador.getMetaAnual())
								: "");
					}
				} else
					tabla.agregarCelda("");

				// Meta Parcial
				if (indicador.getFechaUltimaMedicion() != null) {
					List<?> metas = strategosMetasService.getMetasParciales(indicador.getIndicadorId(),
							reporte.getPlanId(), indicador.getFrecuencia(), indicador.getOrganizacion().getMesCierre(),
							indicador.getCorte(), indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcial(),
							indicador.getFechaUltimaMedicionAno(), indicador.getFechaUltimaMedicionAno(),
							indicador.getFechaUltimaMedicionPeriodo(), indicador.getFechaUltimaMedicionPeriodo());
					if (metas.size() > 0) {
						Meta metaParcial = (Meta) metas.get(0);
						indicador.setMetaParcial(metaParcial.getValor());

						tabla.agregarCelda(indicador.getMetaParcial() != null
								? VgcFormatter.formatearNumero(indicador.getMetaParcial())
								: "");
					}
				} else
					tabla.agregarCelda("");

				// Unidad
				if (indicador.getUnidadId() != null) {
					UnidadMedida unidad = (UnidadMedida) strategosPlanesService.load(UnidadMedida.class,
							indicador.getUnidadId());
					if (unidad != null)
						tabla.agregarCelda(unidad.getNombre());
				} else
					tabla.agregarCelda("");

				// Frecuencia
				if (indicador.getFrecuenciaNombre() != null)
					tabla.agregarCelda(indicador.getFrecuenciaNombre());
				else
					tabla.agregarCelda("");

				// Naturaleza
				if (indicador.getNaturalezaNombre() != null)
					tabla.agregarCelda(indicador.getNaturalezaNombre());
				else
					tabla.agregarCelda("");

			}
			documento.add(lineaEnBlanco(font));
			documento.add(tabla.getTabla());

		} else {
			documento.add(lineaEnBlanco(font));

			font.setColor(0, 0, 255);
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noindicadores",
					"INDICADORES", "PERSPECTIVA"), font);
			texto.setIndentationLeft(50);
			documento.add(texto);
			font.setColor(0, 0, 0);

			documento.add(lineaEnBlanco(font));
		}

	}

	private void dibujarInformacionIniciativa(int nivel, ReporteForm reporte, Font font, Perspectiva perspectiva,
			Document documento, StrategosMedicionesService strategosMedicionesService,
			StrategosIniciativasService strategosIniciativasService,
			StrategosIndicadoresService strategosIndicadoresService,
			StrategosPerspectivasService strategosPerspectivasService, MessageResources mensajes,
			HttpServletRequest request) throws Exception {

		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance()
				.openStrategosPlanesService();
		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;

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

		Font fontCol = new Font(getConfiguracionPagina(request).getCodigoFuente());
		fontCol.setSize(9);
		fontCol.setStyle(Font.BOLD);

		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[10];

		if (iniciativas.size() > 0) {
			columnas = new int[9];

			columnas[0] = 2;
			columnas[1] = 13;
			columnas[2] = 4;
			columnas[3] = 3;
			columnas[4] = 4;
			columnas[5] = 4;
			columnas[6] = 4;
			columnas[7] = 4;
			columnas[8] = 5;

			tabla.setAmplitudTabla(100);
			tabla.setTamanoFont(16);
			tabla.crearTabla(columnas);
			tabla.setAlineacionHorizontal(1);
			tabla.setFormatoFont(Font.NORMAL);
			tabla.setColorFondo(21, 60, 120);
			tabla.setColorLetra(255, 255, 255);
			tabla.agregarCelda("Alerta");
			tabla.agregarCelda("Iniciativa");
			tabla.agregarCelda("% Completado");
			tabla.agregarCelda("% Esperado");
			tabla.agregarCelda("Fecha Ultima Medicion");
			tabla.agregarCelda("Ultima Medicion");
			tabla.agregarCelda("frecuencia");
			tabla.agregarCelda("Estatus");
			tabla.agregarCelda("Año Formulacion");

			tabla.setColorFondo(255, 255, 255);
			tabla.setColorLetra(0, 0, 0);

			BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			java.awt.Font fontNormal = new java.awt.Font(font.getFamilyname(), font.style(),
					((Float) (font.size())).intValue());
			FontMetrics metrics = img.getGraphics().getFontMetrics(fontNormal);

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

				texto = new Paragraph("", font);
				String url = obtenerCadenaRecurso(request);

				if (iniciativa.getAlerta() == null) {
					Image image = Image
							.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaBlanca.gif"));
					texto.add(new Chunk(image, 0, 0));
				} else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
					Image image = Image
							.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaRoja.gif"));
					texto.add(new Chunk(image, 0, 0));
				} else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue()) {
					Image image = Image
							.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaVerde.gif"));
					texto.add(new Chunk(image, 0, 0));
				} else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
					Image image = Image
							.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif"));
					texto.add(new Chunk(image, 0, 0));
				}

				tabla.agregarCelda(texto);

				tabla.agregarCelda(iniciativa.getNombre());

				if (iniciativa.getPorcentajeCompletadoFormateado() != null)
					tabla.agregarCelda(iniciativa.getPorcentajeCompletadoFormateado());
				else
					tabla.agregarCelda("");

				if (programado != null)
					tabla.agregarCelda(programado);
				else
					tabla.agregarCelda("");

				if (iniciativa.getFechaUltimaMedicion() != null)
					tabla.agregarCelda(iniciativa.getFechaUltimaMedicion());
				else
					tabla.agregarCelda("");

				if (ultimaMedicion != null)
					tabla.agregarCelda(ultimaMedicion.getValorFormateado("0.00"));
				else
					tabla.agregarCelda("");

				if (iniciativa.getFrecuenciaNombre() != null)
					tabla.agregarCelda(iniciativa.getFrecuenciaNombre());
				else
					tabla.agregarCelda("");

				if (iniciativa.getEstatus().getNombre() != null)
					tabla.agregarCelda(iniciativa.getEstatus().getNombre());
				else
					tabla.agregarCelda("");

				tabla.agregarCelda(iniciativa.getAnioFormulacion());

			}

			documento.add(lineaEnBlanco(font));
			documento.add(tabla.getTabla());
		} else {
			documento.add(lineaEnBlanco(font));

			font.setColor(0, 0, 255);
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noiniciativas",
					"INICIATIVAS", "PERSPECTIVA"), font);
			texto.setIndentationLeft(50);
			documento.add(texto);
			font.setColor(0, 0, 0);

			documento.add(lineaEnBlanco(font));
		}
	}

	private String obtenerCadenaRecurso(HttpServletRequest request) {
		String result = null;
		if (request.getServerPort() == 80 && request.getScheme().equals("http")) {

			result = request.getServerName() + "/" + request.getContextPath();
			result = "https" + "://" + result.replaceAll("//", "/");

		} else {
			result = request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath();
			result = request.getScheme() + "://" + result.replaceAll("//", "/");

		}
		return result;

	}
}
