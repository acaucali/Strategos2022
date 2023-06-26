package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.model.util.LapsoTiempo;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryProyectosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.report.VgcFormatoReporte;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.framework.web.struts.forms.NavegadorForm;

public class ReporteIniciativaMedicionesAtrasadasPdf extends VgcReporteBasicoAction {
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 57;
	private int maxLineasAntesTabla = 4;

	@Override
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception {
		String source = request.getParameter("source");
		return mensajes.getMessage("jsp.reportes.iniciativa.ejecucion.mediciones.atrasadas");
	}

	@Override
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response,
			Document documento) throws Exception {
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();

		// Obtencion del request
		String tipo = (request.getParameter("tipo"));
		String ano = (request.getParameter("ano"));
		String estatus = (request.getParameter("estatus"));
		String todos = (request.getParameter("todos"));

		Font fuente = getConfiguracionPagina(request).getFuente();

		/* Parametros para el reporte */
		String source = request.getParameter("source");
		/*
		 * reporte.setMesInicial(request.getParameter("mesInicial"));
		 * reporte.setMesFinal(request.getParameter("mesFinal"));
		 * reporte.setAnoInicial(request.getParameter("anoInicial"));
		 * reporte.setAnoFinal(request.getParameter("anoFinal"));
		 */
		reporte.setMesInicial("1");
		reporte.setMesFinal("12");
		reporte.setAnoInicial("2023");
		reporte.setAnoFinal("2023");
		reporte.setAlcance(request.getParameter("alcance") != null ? Byte.parseByte(request.getParameter("alcance"))
				: reporte.getAlcancePlan());
		reporte.setAno(Integer.parseInt(ano));
		reporte.setTipo(Long.parseLong(tipo));
		reporte.setEstatus(estatus);

		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());

		// Titulo de filtros aplicados
		font.setSize(VgcFormatoReporte.TAMANO_FUENTE_SUBTITULO);
		font.setStyle(Font.BOLD);
		fontBold.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
		fontBold.setStyle(Font.BOLD);

		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);

		Paragraph texto = new Paragraph("Filtros del reporte: ", font);
		documento.add(texto);
		documento.add(lineaEnBlanco(fuente));
		crearTablaFiltros(tabla, mensajes, documento, request, todos, tipo, ano, fuente);
		documento.add(tabla.getTabla());

		documento.add(lineaEnBlanco(fuente));
		documento.add(lineaEnBlanco(fuente));
		EjecucionIniciativa(reporte, documento, request, mensajes, source, todos, fuente);
	}

	private void EjecucionIniciativa(ReporteForm reporte, Document documento, HttpServletRequest request,
			MessageResources mensajes, String source, String todos, Font fuente) throws Exception {

		Font fontTitulos = new Font(getConfiguracionPagina(request).getCodigoFuente());
		fontTitulos.setSize(14);
		fontTitulos.setStyle(Font.BOLD);
		int nivel = 0;
		inicioTamanoPagina = lineasxPagina(fuente);
		tamanoPagina = inicioTamanoPagina;
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
				.openStrategosIndicadoresService();
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance()
				.openStrategosMedicionesService();
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance()
				.openStrategosIniciativasService();
		StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance()
				.openStrategosOrganizacionesService();

		Map<String, Object> filtros = new HashMap<String, Object>();
		// font.setStyle(Font.NORMAL);

		List<OrganizacionStrategos> organizaciones = organizacionservice
				.getOrganizaciones(0, 0, "organizacionId", "ASC", true, filtros).getLista();

		filtros = new HashMap<String, Object>();

		// Organizacion Seleccionada
		if (request.getParameter("alcance").equals("1")) {
			OrganizacionStrategos org = (OrganizacionStrategos) organizacionservice.load(OrganizacionStrategos.class,
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId());

			if (org != null) {
				// Nombre de la Organizacion, plan y periodo del reporte
				Paragraph textoOrg = new Paragraph("Organización: " + org.getNombre(), fontTitulos);
				textoOrg.setAlignment(Element.ALIGN_LEFT);
				documento.add(textoOrg);

				documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
			}

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

			if (reporte.getAlcance().byteValue() == reporte.getAlcanceObjetivo().byteValue()) {
				filtros.put("organizacionId",((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId());
			}
			PlantillaPlanes plantilla = new PlantillaPlanes();
			plantilla.setNombreActividadSingular(mensajes.getMessage("jsp.modulo.actividad.titulo.singular"));
			plantilla.setNombreIniciativaSingular(
					((NavegadorForm) request.getSession().getAttribute("activarIniciativa")).getNombreSingular());
			plantilla.setNombreIndicadorSingular(mensajes.getMessage("jsp.modulo.indicador.titulo.singular"));
			reporte.setPlantillaPlanes(plantilla);

			if (reporte.getFiltro().getHistorico() != null
					&& reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado()) {
				filtros.put("historicoDate", "IS NULL");
			} else if (reporte.getFiltro().getHistorico() != null
					&& reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado()) {
				filtros.put("historicoDate", "IS NOT NULL");
			}
			if (reporte.getFiltro().getNombre() != null) {
				filtros.put("nombre", reporte.getFiltro().getNombre());
			}
			if (reporte.getTipo().equals("0")) {
				filtros.put("tipoId", reporte.getTipo());
			}
			if (todos.equals("false")) {
				filtros.put("anio", reporte.getAno());
			}
			filtros.put("estatusId", reporte.getEstatus());

			List<Iniciativa> iniciativas = strategosIniciativasService
					.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();

			if (iniciativas.size() > 0) {

				// Inicializacion Encabezado Tabla Iniciativas
				TablaPDF tabla = null;
				tabla = new TablaPDF(getConfiguracionPagina(request), request);

				// Se asigna el header de la tabla
				crearTablaTitulo(tabla, mensajes, 1);

				for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
					Iniciativa iniciativa = iter.next();
					Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
							iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

					LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(
							((Integer) (Integer.parseInt(reporte.getAnoInicial()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getAnoFinal()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getMesInicial()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getMesFinal()))).intValue(),
							indicador.getFrecuencia().byteValue());
					int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
					int periodoFin = periodoInicio;
					if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
						periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
					List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(
							indicador.getIndicadorId(), SerieTiempo.getSerieRealId(),
							new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio,
							periodoFin);
					List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(
							indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(),
							new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio,
							periodoFin);

					// Dibujar Informacion de la Iniciativa
					crearTablaIniciativa(reporte, iniciativa, indicador, medicionesProgramado, medicionesEjecutado,
							fuente, mensajes, documento, request, tabla);
				}
				documento.add(tabla.getTabla());
				documento.add(lineaEnBlanco(fuente));

				for (Iterator<Iniciativa> iter2 = iniciativas.iterator(); iter2.hasNext();) {
					Iniciativa iniciativa = iter2.next();
					Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
							iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

					LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(
							((Integer) (Integer.parseInt(reporte.getAnoInicial()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getAnoFinal()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getMesInicial()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getMesFinal()))).intValue(),
							indicador.getFrecuencia().byteValue());
					int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
					int periodoFin = periodoInicio;
					if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
						periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
					List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(
							indicador.getIndicadorId(), SerieTiempo.getSerieRealId(),
							new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio,
							periodoFin);
					List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(
							indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(),
							new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio,
							periodoFin);

					Paragraph texto = new Paragraph(
							reporte.getPlantillaPlanes().getNombreIniciativaSingular() + " : " + iniciativa.getNombre(),
							fontTitulos);
					texto.setAlignment(Element.ALIGN_LEFT);
					texto.setIndentationLeft(16);
					documento.add(texto);

					dibujarInformacionActividad(reporte, fuente, fontTitulos, iniciativa, documento,
							strategosMedicionesService, strategosIndicadoresService, mensajes, request);
				}
			} else {
				documento.add(lineaEnBlanco(fuente));

				fuente.setColor(0, 0, 255);
				Paragraph texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noiniciativas",
						"INICIATIVAS", "ORGANIZACIÓN"), fuente);
				texto.setIndentationLeft(50);
				documento.add(texto);
				fuente.setColor(0, 0, 0);
				documento.add(lineaEnBlanco(fuente));
			}

		}

		//suborganizaciones
		if (request.getParameter("alcance").equals("4")) {
			Map<String, Object> filtro = new HashMap<String, Object>();

			filtro.put("padreId",
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId());

			List<OrganizacionStrategos> organizacionesSub = organizacionservice.getOrganizacionHijas(
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId(),
					true);
			OrganizacionStrategos org = (OrganizacionStrategos) organizacionservice.load(OrganizacionStrategos.class,
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId());

			if (org != null) {

				Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

				// Nombre de la Organizacion, plan y periodo del reporte
				font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
				font.setStyle(Font.NORMAL);

				Paragraph textoOrg = new Paragraph("Organización: " + org.getNombre(), font);
				textoOrg.setAlignment(Element.ALIGN_LEFT);
				documento.add(textoOrg);

				documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
			}
			filtros.put("organizacionId",
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId());
			if (reporte.getFiltro().getHistorico() != null
					&& reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado()) {
				filtros.put("historicoDate", "IS NULL");
			} else if (reporte.getFiltro().getHistorico() != null
					&& reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado()) {
				filtros.put("historicoDate", "IS NOT NULL");
			}
			if (reporte.getFiltro().getNombre() != null) {
				filtros.put("nombre", reporte.getFiltro().getNombre());
			}
			if (reporte.getTipo().equals("0")) {
				filtros.put("tipoId", reporte.getTipo());
			}
			if (todos.equals("false")) {
				filtros.put("anio", reporte.getAno());
			}
			filtros.put("estatusId", reporte.getEstatus());


			List<Iniciativa> iniciativas = strategosIniciativasService
					.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();

			if (iniciativas.size() > 0) {

				// Inicializacion Encabezado Tabla Iniciativas
				TablaPDF tabla = null;
				tabla = new TablaPDF(getConfiguracionPagina(request), request);

				// Se asigna el header de la tabla
				crearTablaTitulo(tabla, mensajes, 1);

				for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();) {
					Iniciativa iniciativa = iter.next();
					Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
							iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

					LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(
							((Integer) (Integer.parseInt(reporte.getAnoInicial()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getAnoFinal()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getMesInicial()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getMesFinal()))).intValue(),
							indicador.getFrecuencia().byteValue());
					int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
					int periodoFin = periodoInicio;
					if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
						periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
					List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(
							indicador.getIndicadorId(), SerieTiempo.getSerieRealId(),
							new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio,
							periodoFin);
					List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(
							indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(),
							new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio,
							periodoFin);

					// Dibujar Informacion de la Iniciativa
					crearTablaIniciativa(reporte, iniciativa, indicador, medicionesProgramado, medicionesEjecutado,
							fuente, mensajes, documento, request, tabla);
				}
				documento.add(tabla.getTabla());
				documento.add(lineaEnBlanco(fuente));

				for (Iterator<Iniciativa> iter2 = iniciativas.iterator(); iter2.hasNext();) {
					Iniciativa iniciativa = iter2.next();
					Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
							iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

					LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(
							((Integer) (Integer.parseInt(reporte.getAnoInicial()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getAnoFinal()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getMesInicial()))).intValue(),
							((Integer) (Integer.parseInt(reporte.getMesFinal()))).intValue(),
							indicador.getFrecuencia().byteValue());
					int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
					int periodoFin = periodoInicio;
					if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
						periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
					List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(
							indicador.getIndicadorId(), SerieTiempo.getSerieRealId(),
							new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio,
							periodoFin);
					List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(
							indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(),
							new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio,
							periodoFin);

					Paragraph texto = new Paragraph(
							reporte.getPlantillaPlanes().getNombreIniciativaSingular() + " : " + iniciativa.getNombre(),
							fontTitulos);
					texto.setAlignment(Element.ALIGN_LEFT);
					texto.setIndentationLeft(16);
					documento.add(texto);

					dibujarInformacionActividad(reporte, fuente, fontTitulos, iniciativa, documento,
							strategosMedicionesService, strategosIndicadoresService, mensajes, request);
				}
			} else {
				documento.add(lineaEnBlanco(fuente));

				fuente.setColor(0, 0, 255);
				Paragraph texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noiniciativas",
						"INICIATIVAS", "ORGANIZACIÓN"), fuente);
				texto.setIndentationLeft(50);
				documento.add(texto);
				fuente.setColor(0, 0, 0);
				documento.add(lineaEnBlanco(fuente));
			}
			if (organizacionesSub.size() > 0 || organizacionesSub != null) {
				for (Iterator<OrganizacionStrategos> iter = organizacionesSub.iterator(); iter.hasNext();) {

					OrganizacionStrategos organizacion = iter.next();

					if (organizacion != null) {

						Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

						// Nombre de la Organizacion, plan y periodo del reporte
						font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
						font.setStyle(Font.NORMAL);

						Paragraph textoOrg = new Paragraph("Organización: " + organizacion.getNombre(), font);
						textoOrg.setAlignment(Element.ALIGN_LEFT);
						documento.add(textoOrg);

						documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
					}

					filtros.put("organizacionId", organizacion.getOrganizacionId().toString());
					if (reporte.getFiltro().getHistorico() != null
							&& reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado()) {
						filtros.put("historicoDate", "IS NULL");
					} else if (reporte.getFiltro().getHistorico() != null
							&& reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado()) {
						filtros.put("historicoDate", "IS NOT NULL");
					}
					if (reporte.getFiltro().getNombre() != null) {
						filtros.put("nombre", reporte.getFiltro().getNombre());
					}
					if (reporte.getTipo().equals("0")) {
						filtros.put("tipoId", reporte.getTipo());
					}
					if (todos.equals("false")) {
						filtros.put("anio", reporte.getAno());
					}
					filtros.put("estatusId", reporte.getEstatus());



					List<Iniciativa> iniciativasSub = strategosIniciativasService
							.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();


					if (iniciativasSub.size() > 0) {

						// Inicializacion Encabezado Tabla Iniciativas
						TablaPDF tabla = null;
						tabla = new TablaPDF(getConfiguracionPagina(request), request);

						// Se asigna el header de la tabla
						crearTablaTitulo(tabla, mensajes, 1);
						for (Iterator<Iniciativa> iter1 = iniciativasSub.iterator(); iter1.hasNext();) {
							Iniciativa iniciativa = iter1.next();
							Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
									iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

							LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(
									((Integer) (Integer.parseInt(reporte.getAnoInicial()))).intValue(),
									((Integer) (Integer.parseInt(reporte.getAnoFinal()))).intValue(),
									((Integer) (Integer.parseInt(reporte.getMesInicial()))).intValue(),
									((Integer) (Integer.parseInt(reporte.getMesFinal()))).intValue(),
									indicador.getFrecuencia().byteValue());
							int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
							int periodoFin = periodoInicio;
							if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
								periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
							List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(
									indicador.getIndicadorId(), SerieTiempo.getSerieRealId(),
									new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio,
									periodoFin);
							List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(
									indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(),
									new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio,
									periodoFin);

							// Dibujar Informacion de la Iniciativa
							crearTablaIniciativa(reporte, iniciativa, indicador, medicionesProgramado, medicionesEjecutado,
									fuente, mensajes, documento, request, tabla);

						}
						documento.add(tabla.getTabla());
						documento.add(lineaEnBlanco(fuente));
						for (Iterator<Iniciativa> iter2 = iniciativasSub.iterator(); iter2.hasNext();) {
							Iniciativa iniciativa = iter2.next();
							Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
									iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

							LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(
									((Integer) (Integer.parseInt(reporte.getAnoInicial()))).intValue(),
									((Integer) (Integer.parseInt(reporte.getAnoFinal()))).intValue(),
									((Integer) (Integer.parseInt(reporte.getMesInicial()))).intValue(),
									((Integer) (Integer.parseInt(reporte.getMesFinal()))).intValue(),
									indicador.getFrecuencia().byteValue());
							int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
							int periodoFin = periodoInicio;
							if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
								periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();
							List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(
									indicador.getIndicadorId(), SerieTiempo.getSerieRealId(),
									new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio,
									periodoFin);
							List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(
									indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(),
									new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio,
									periodoFin);

							Paragraph texto = new Paragraph(
									"Iniciativa" + " : " + iniciativa.getNombre(),
									fontTitulos);
							texto.setAlignment(Element.ALIGN_LEFT);
							texto.setIndentationLeft(16);
							documento.add(texto);

							dibujarInformacionActividad(reporte, fuente, fontTitulos, iniciativa, documento,
									strategosMedicionesService, strategosIndicadoresService, mensajes, request);
						}
					}else {
						documento.add(lineaEnBlanco(fuente));

						fuente.setColor(0, 0, 255);
						Paragraph texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noiniciativas",
								"INICIATIVAS", "ORGANIZACIÓN"), fuente);
						texto.setIndentationLeft(50);
						documento.add(texto);
						fuente.setColor(0, 0, 0);
						documento.add(lineaEnBlanco(fuente));
					}

				}
			}
		}

	}

	private void crearTablaIniciativa(ReporteForm reporte, Iniciativa iniciativa, Indicador indicador,
			List<Medicion> medicionesProgramado, List<Medicion> medicionesEjecutado, Font font,
			MessageResources mensajes, Document documento, HttpServletRequest request, TablaPDF tabla)
			throws Exception {

		Date fechaUltimaMedicion;
		Integer periodo = obtenerFecha(iniciativa.getFrecuencia());
		String fecha = String.valueOf(periodo) + "/" + String.valueOf(new Date().getYear() + 1900);
		SimpleDateFormat date = new SimpleDateFormat("MM/yyyy");
		Date fechaActualDate = date.parse(fecha);
		String ultimaMedicion = iniciativa.getFechaUltimaMedicion();
		String frecuencia = obtenerFrecuencia(iniciativa.getFrecuencia());

		IniciativaEstatus estatusId = iniciativa.getEstatus();

		if (lineas >= tamanoPagina) {
			lineas = inicioLineas;
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, null, null, request);
		}

		PryProyecto proyecto = null;
		if (iniciativa.getProyectoId() != null) {
			StrategosPryProyectosService strategosPryProyectosService = StrategosServiceFactory.getInstance()
					.openStrategosPryProyectosService();
			proyecto = (PryProyecto) strategosPryProyectosService.load(PryProyecto.class, iniciativa.getProyectoId());
			strategosPryProyectosService.close();
		}

		double programado = 0D;
		double porcentajeEsperado = 0D;

		for (Iterator<Medicion> iterEjecutado = medicionesEjecutado.iterator(); iterEjecutado.hasNext();) {
			Medicion ejecutado = iterEjecutado.next();
			for (Iterator<Medicion> iterMeta = medicionesProgramado.iterator(); iterMeta.hasNext();) {
				Medicion meta = iterMeta.next();
				if (ejecutado.getMedicionId().getAno().intValue() == meta.getMedicionId().getAno().intValue()
						&& ejecutado.getMedicionId().getPeriodo().intValue() == meta.getMedicionId().getPeriodo()
								.intValue()) {
					if (meta.getValor() != null)
						programado = programado + meta.getValor();
					break;
				}
			}
		}
		tabla.agregarCelda("\n" + iniciativa.getNombre());
		if (iniciativa.getPorcentajeCompletadoFormateado() == null) {
			tabla.agregarCelda("");
		} else {
			tabla.agregarCelda("\n" + iniciativa.getPorcentajeCompletadoFormateado());
		}
		if (iniciativa.getFechaUltimaMedicion() == null) {
			tabla.agregarCelda("");
		} else {
			tabla.agregarCelda("\n" + iniciativa.getFechaUltimaMedicion());
		}
		if (iniciativa.getEstatus() == null) {
			tabla.agregarCelda("");
		} else {
			tabla.agregarCelda("\n" + frecuencia);
		}
		if (iniciativa.getTipoProyecto() == null) {
			tabla.agregarCelda("");
		} else {
			tabla.agregarCelda("\n" + iniciativa.getTipoProyecto().getNombre());
		}
		if (iniciativa.getAnioFormulacion() == null) {
			tabla.agregarCelda("");
		} else {
			tabla.agregarCelda("\n" + iniciativa.getAnioFormulacion());
		}
		if (iniciativa.getFechaUltimaMedicion() != null) {
			fechaUltimaMedicion = date.parse(ultimaMedicion);
			if (fechaUltimaMedicion.before(fechaActualDate)) {
				tabla.agregarCelda("\n" + "Sí");
			} else {
				tabla.agregarCelda("");
			}
		} else {
			tabla.agregarCelda("");
		}

	}

	private void dibujarInformacionActividad(ReporteForm reporte, Font font, Font fontTitulos, Iniciativa iniciativa,
			Document documento, StrategosMedicionesService strategosMedicionesService,
			StrategosIndicadoresService strategosIndicadoresService, MessageResources mensajes,
			HttpServletRequest request) throws Exception {

		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance()
				.openStrategosPryActividadesService();

		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;

		filtros = new HashMap<String, Object>();
		filtros.put("proyectoId", iniciativa.getProyectoId());
		List<PryActividad> actividades = strategosPryActividadesService
				.getActividades(0, 0, "fila", "ASC", true, filtros).getLista();

		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		// Se asigna el header de la tabla
		crearTablaTitulo(tabla, mensajes, 2);

		tabla.setAlineacionHorizontal(1);

		if (actividades.size() > 0) {
			for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();) {
				PryActividad actividad = iter.next();
				Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
						actividad.getIndicadorId());

				// Dibujar Informacion de la Iniciativa
				crearTablaActividad(reporte, actividad, indicador, iniciativa, font, mensajes, documento, request,
						tabla);
			}
			documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
			documento.add(tabla.getTabla());
			documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
			documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));

		} else {

			fontTitulos.setColor(0, 0, 255);
			texto = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noactividades",
					reporte.getPlantillaPlanes().getNombreActividadPlural().toLowerCase(),
					reporte.getPlantillaPlanes().getNombreIniciativaSingular().toLowerCase()), fontTitulos);
			texto.setIndentationLeft(50);
			documento.add(texto);
			fontTitulos.setColor(0, 0, 0);
			documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
			documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
		}

		strategosMetasService.close();
		strategosPryActividadesService.close();
	}

	private void crearTablaActividad(ReporteForm reporte, PryActividad actividad, Indicador indicador,
			Iniciativa iniciativa, Font font, MessageResources mensajes, Document documento, HttpServletRequest request,
			TablaPDF tabla) throws Exception {

		Date fechaUltimaMedicion;
		Integer periodo = obtenerFecha(iniciativa.getFrecuencia());
		String fecha = String.valueOf(periodo) + "/" + String.valueOf(new Date().getYear() + 1900);
		SimpleDateFormat date = new SimpleDateFormat("MM/yyyy");
		Date fechaActualDate = date.parse(fecha);
		String ultimaMedicion = actividad.getFechaUltimaMedicion();

		if (lineas >= tamanoPagina) {
			lineas = inicioLineas;
			tamanoPagina = inicioTamanoPagina;
			saltarPagina(documento, false, font, null, null, request);
		}
		tabla.agregarCelda("\n" + actividad.getNombre());
		tabla.agregarCelda("\n" + VgcFormatter.formatearFecha(actividad.getComienzoPlan(), "formato.fecha.corta"));
		tabla.agregarCelda("\n" + VgcFormatter.formatearFecha(actividad.getFinPlan(), "formato.fecha.corta"));
		if (actividad.getFechaUltimaMedicion() != null) {
			tabla.agregarCelda("\n" + actividad.getFechaUltimaMedicion());
		} else
			tabla.agregarCelda("");
		tabla.agregarCelda("\n" + VgcFormatter.formatearNumero(actividad.getDuracionPlan()));
		tabla.agregarCelda(
				"\n" + actividad.getPorcentajeEjecutado() != null ? actividad.getPorcentajeEjecutadoFormateado() : "");
		tabla.agregarCelda(
				"\n" + actividad.getPorcentajeEsperado() != null ? actividad.getPorcentajeEsperadoFormateado() : "");
		if (actividad.getFechaUltimaMedicion() != null) {
			fechaUltimaMedicion = date.parse(ultimaMedicion);
			if (fechaUltimaMedicion.before(fechaActualDate)) {
				tabla.agregarCelda("\n" + "Sí");
			} else {
				tabla.agregarCelda("");
			}
		} else
			tabla.agregarCelda("");

	}

	private TablaPDF crearTablaTitulo(TablaPDF tabla, MessageResources mensajes, Integer tipo) throws Exception {

		if (tipo == 1) {
			int[] columnas = new int[7];
			columnas[0] = 30;
			columnas[1] = 8;
			columnas[2] = 8;
			columnas[3] = 8;
			columnas[4] = 10;
			columnas[5] = 8;
			columnas[6] = 10;

			tabla.setAmplitudTabla(100);
			tabla.crearTabla(columnas);

			tabla.setColorFondo(21, 60, 120);
			tabla.setColorLetra(255, 255, 255);
			tabla.setTamanoFont(12);
			tabla.setFormatoFont(Font.BOLD);
			tabla.setAlineacionHorizontal(1);

			tabla.agregarCelda(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.iniciativa"));
			tabla.agregarCelda(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje"));
			tabla.agregarCelda(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.fecha"));
			tabla.agregarCelda(mensajes.getMessage("jsp.editariniciativa.ficha.frecuencia"));
			tabla.agregarCelda(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.tipo"));
			tabla.agregarCelda(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.anio"));
			tabla.agregarCelda(mensajes.getMessage("action.reporte.estatus.iniciativa.mediciones.atrasadas"));

			tabla.setColorFondo(255, 255, 255);
			tabla.setColorLetra(0, 0, 0);
			tabla.setTamanoFont(10);
			tabla.setFormatoFont(Font.NORMAL);
		}

		if (tipo == 2) {
			int[] columnas = new int[8];
			columnas[0] = 30;
			columnas[1] = 8;
			columnas[2] = 9;
			columnas[3] = 10;
			columnas[4] = 8;
			columnas[5] = 8;
			columnas[6] = 10;
			columnas[7] = 8;

			tabla.setAmplitudTabla(100);
			tabla.crearTabla(columnas);
			tabla.setColorFondo(21, 60, 120);
			tabla.setColorLetra(255, 255, 255);
			tabla.setTamanoFont(12);
			tabla.setFormatoFont(Font.BOLD);
			tabla.setAlineacionHorizontal(1);

			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.actividades"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion"));
			tabla.agregarCelda(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.fecha"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.duracion"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.ejecutado"));
			tabla.agregarCelda(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.programado"));
			tabla.agregarCelda(mensajes.getMessage("action.reporte.estatus.iniciativa.mediciones.atrasadas"));

			tabla.setColorFondo(255, 255, 255);
			tabla.setColorLetra(0, 0, 0);
			tabla.setTamanoFont(10);
			tabla.setFormatoFont(Font.NORMAL);
		}

		return tabla;

	}

	private TablaPDF crearTablaFiltros(TablaPDF tabla, MessageResources mensajes, Document documento,
			HttpServletRequest request, String todos, String tipo, String ano, Font fuente) throws Exception {

		String tipoT = obtenerTipo(Long.parseLong(tipo));
		String estatusT = "En Ejecución";
		String anio = "";
		if (todos.equals("false")) {
			anio = String.valueOf(Integer.parseInt(ano));
		} else {
			anio = "Todos";
		}
		String alcanceT = "";
		if (request.getParameter("alcance").equals("1"))
			alcanceT = "Organización seleccionada";
		else if (request.getParameter("alcance").equals("4"))
			alcanceT = "Organización seleccionada y Sub-Organizaciones";
		else
			alcanceT = "Todas las Organizaciones";

		int[] columnas = new int[4];
		columnas[0] = 20;
		columnas[1] = 15;
		columnas[2] = 10;
		columnas[3] = 15;

		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);
		tabla.setColorFondo(128, 128, 128);
		tabla.setColorLetra(255, 255, 255);
		tabla.setTamanoFont(10);
		tabla.setFormatoFont(Font.BOLD);
		tabla.setAlineacionHorizontal(1);

		tabla.agregarCelda(mensajes.getMessage("jsp.editariniciativa.ficha.alcance") + " del Reporte");
		tabla.agregarCelda(mensajes.getMessage("action.reporte.estatus.iniciativa.nombre.tipo"));
		tabla.agregarCelda(mensajes.getMessage("jsp.editariniciativa.ficha.estatus"));
		tabla.agregarCelda(mensajes.getMessage("jsp.gestionariniciativas.filtro.anio"));

		tabla.setColorFondo(255, 255, 255);
		tabla.setColorLetra(0, 0, 0);
		tabla.setTamanoFont(8);
		tabla.setFormatoFont(Font.NORMAL);

		tabla.agregarCelda(alcanceT);
		tabla.agregarCelda(tipoT);
		tabla.agregarCelda(estatusT);
		tabla.agregarCelda(anio);

		return tabla;
	}

	public String obtenerTipo(Long tipo) {
		String nombre = "";

		if (tipo == 656054) {
			nombre = "Estrategicos Dependencias";
		}
		if (tipo == 656056) {
			nombre = "Bid";
		}
		if (tipo == 656058) {
			nombre = "Cooperación";
		}
		if (tipo == 656060) {
			nombre = "Bpin";
		}
		if (tipo == 1253394) {
			nombre = "Iniciativa Estratégica";
		}
		if (tipo == 1253396) {
			nombre = "Proyecto de Dependencia";
		}
		if (tipo == 1253398) {
			nombre = "Proyecto Preventivo";
		}
		if (tipo == 0) {
			nombre = "Todos";
		}
		return nombre;
	}

	public String obtenerFrecuencia (Byte frecuenciaId) {
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
