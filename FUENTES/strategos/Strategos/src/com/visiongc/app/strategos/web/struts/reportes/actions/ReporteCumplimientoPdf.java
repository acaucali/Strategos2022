package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
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
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.report.VgcFormatoReporte;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.web.struts.forms.NavegadorForm;

public class ReporteCumplimientoPdf extends VgcReporteBasicoAction {

	@Override
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception {
		return mensajes.getMessage("menu.evaluacion.reportes.cumplimiento");
	}

	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response,
			Document documento) throws Exception {

		MessageResources messageResources = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();

		String alcance = (request.getParameter("alcance"));
		String anio = (request.getParameter("anio"));
		reporte.setAno(Integer.parseInt(anio));

		Map<String, Object> filtros = new HashMap<String, Object>();

		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontTitulo = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontSubtitulo = new Font(getConfiguracionPagina(request).getCodigoFuente());

		// Nombre de la Organizacion, plan y periodo del reporte
		font.setSize(12);
		font.setStyle(Font.NORMAL);
		fontTitulo.setSize(15);
		fontTitulo.setStyle(Font.BOLD);
		fontSubtitulo.setSize(12);
		fontSubtitulo.setStyle(Font.NORMAL);
		documento.add(lineaEnBlanco(font));

		StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance()
				.openStrategosOrganizacionesService();
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance()
				.openStrategosPlanesService();				
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance()
				.openStrategosIniciativasService();		

		List<OrganizacionStrategos> organizaciones = organizacionservice
				.getOrganizaciones(0, 0, "organizacionId", "ASC", true, filtros).getLista();

		// organizacion seleccionada
		if (request.getParameter("alcance").equals("1")) {

			filtros = new HashMap<String, Object>();

			OrganizacionStrategos organizacion = ((OrganizacionStrategos) request.getSession()
					.getAttribute("organizacion"));
			reporte.setOrganizacionNombre(organizacion.getNombre());

			String subTituloOrg = "Organización" + " - " + reporte.getOrganizacionNombre();
			agregarSubTitulo(documento, getConfiguracionPagina(request), subTituloOrg, true, true, 17.0F);

			if ((organizacion.getOrganizacionId() != null))
				filtros.put("organizacionId", organizacion.getOrganizacionId().toString());			

			Paragraph texto = new Paragraph("", fontTitulo);
			PaginaLista paginaPlanes = strategosPlanesService.getPlanes(1, 30, "nombre", "ASC", true, filtros);
			List<Plan> planes = paginaPlanes.getLista();

			// 1.0 Indicadores Planes
			if (planes.size() > 0) {
				for (Plan plan : planes) {
					if (plan.getActivo()) {
						dibujarIndicadoresPlan(font, fontTitulo, fontSubtitulo, strategosPlanesService, plan, documento, messageResources,
								request);
					}
				}
			}

			strategosPlanesService.close();			
			filtros.put("anio", anio);
			PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(1, 30, "nombre", "ASC", true,
					filtros);
			List<Iniciativa> iniciativas = paginaIniciativas.getLista();
			// 2.0 iniciativas
			if (iniciativas.size() > 0) {
				List<Iniciativa> iniAtrasados = new ArrayList();
				iniAtrasados = obtenerIniciativas(iniciativas, fontSubtitulo, texto, documento);
				if (iniAtrasados.size() > 0) {
					dibujarIniciativas(iniAtrasados, font, fontTitulo, documento, messageResources, request);

					dibujarActividades(iniAtrasados, font, fontSubtitulo, documento, messageResources, request);
				}
			}

			strategosIniciativasService.close();

			Integer periodo = obtenerFecha((byte) 5);
			String fecha = String.valueOf((periodo - 1)) + "/" + String.valueOf(new Date().getYear() + 1900);
			SimpleDateFormat date = new SimpleDateFormat("MM/yyyy");
			Date fechaActualDate = date.parse(fecha);
			
			// 3.0 Analisis Estrategicos
			dibujarEventos(date, fechaActualDate, organizacion.getOrganizacionId(), documento, font, fontTitulo, fontSubtitulo, messageResources);
			
			// 4.0 Informe Cualitativo
			dibujarInformes(date, fechaActualDate, organizacion.getOrganizacionId(), documento, font, fontTitulo, fontSubtitulo, messageResources);									
			documento.add(lineaEnBlanco(font));
		}
		// suborganizaciones
		else if (request.getParameter("alcance").equals("4")) {

			filtros = new HashMap<String, Object>();
			
			List<OrganizacionStrategos> organizacionesSub = organizacionservice.getOrganizacionHijas(
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId(),
					true);

			OrganizacionStrategos organizacion = (OrganizacionStrategos) organizacionservice.load(OrganizacionStrategos.class,
					((OrganizacionStrategos) request.getSession().getAttribute("organizacion")).getOrganizacionId());

			if (organizacion != null) {

				// Nombre de la Organizacion, plan y periodo del reporte
				font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
				font.setStyle(Font.NORMAL);

				Paragraph textoOrg = new Paragraph("Organización: " + organizacion.getNombre(), font);
				textoOrg.setAlignment(Element.ALIGN_LEFT);
				documento.add(textoOrg);				
			}
			
			if ((organizacion.getOrganizacionId() != null))
				filtros.put("organizacionId", organizacion.getOrganizacionId().toString());

			Paragraph texto = new Paragraph("", fontTitulo);
			PaginaLista paginaPlanes = strategosPlanesService.getPlanes(1, 30, "nombre", "ASC", true, filtros);
			List<Plan> planes = paginaPlanes.getLista();

			// 1.0 Indicadores Planes
			if (planes.size() > 0) {
				for (Plan plan : planes) {
					if (plan.getActivo()) {
						dibujarIndicadoresPlan(font, fontTitulo, fontSubtitulo, strategosPlanesService, plan, documento, messageResources,
								request);
					}
				}
			}
			
			filtros.put("anio", anio);
			PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(1, 30, "nombre", "ASC", true,
					filtros);
			List<Iniciativa> iniciativas = paginaIniciativas.getLista();
			// 2.0 iniciativas
			if (iniciativas.size() > 0) {
				List<Iniciativa> iniAtrasados = new ArrayList();
				iniAtrasados = obtenerIniciativas(iniciativas, fontSubtitulo, texto, documento);
				if (iniAtrasados.size() > 0) {
					dibujarIniciativas(iniAtrasados, font, fontTitulo, documento, messageResources, request);

					dibujarActividades(iniAtrasados, font, fontSubtitulo, documento, messageResources, request);
				}
			}
			

			Integer periodo = obtenerFecha((byte) 5);
			String fecha = String.valueOf((periodo - 1)) + "/" + String.valueOf(new Date().getYear() + 1900);
			SimpleDateFormat date = new SimpleDateFormat("MM/yyyy");
			Date fechaActualDate = date.parse(fecha);
			
			// 3.0 Analisis Estrategicos
			dibujarEventos(date, fechaActualDate, organizacion.getOrganizacionId(), documento, font, fontTitulo, fontSubtitulo, messageResources);
			
			// 4.0 Informe Cualitativo
			dibujarInformes(date, fechaActualDate, organizacion.getOrganizacionId(), documento, font, fontTitulo, fontSubtitulo, messageResources);									
			documento.add(lineaEnBlanco(font));			

			// suborganizaciones
			if (organizacionesSub.size() > 0 || organizacionesSub != null) {
				for (Iterator<OrganizacionStrategos> iter = organizacionesSub.iterator(); iter.hasNext();) {
					OrganizacionStrategos org = iter.next();

					if (org != null) {

						// Nombre de la Organizacion, plan y periodo del reporte
						font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
						font.setStyle(Font.NORMAL);

						Paragraph textoOrg = new Paragraph("Organización: " + org.getNombre(), font);
						textoOrg.setAlignment(Element.ALIGN_LEFT);
						documento.add(textoOrg);
						
					}
					filtros.clear();
					if ((org.getOrganizacionId() != null))
						filtros.put("organizacionId", org.getOrganizacionId().toString());

					paginaPlanes = strategosPlanesService.getPlanes(1, 30, "nombre", "ASC", true, filtros);
					planes = paginaPlanes.getLista();

					// 1.0 Indicadores Planes
					if (planes.size() > 0) {
						for (Plan plan : planes) {
							if (plan.getActivo()) {
								dibujarIndicadoresPlan(font, fontTitulo, fontSubtitulo, strategosPlanesService, plan, documento, messageResources,
										request);
							}
						}
					}
					
					filtros.put("anio", anio);
					paginaIniciativas = strategosIniciativasService.getIniciativas(1, 30, "nombre", "ASC", true,
							filtros);
					iniciativas = paginaIniciativas.getLista();
					// 2.0 iniciativas
					if (iniciativas.size() > 0) {
						List<Iniciativa> iniAtrasados = new ArrayList();
						iniAtrasados = obtenerIniciativas(iniciativas, fontSubtitulo, texto, documento);
						if (iniAtrasados.size() > 0) {
							dibujarIniciativas(iniAtrasados, font, fontTitulo, documento, messageResources, request);

							dibujarActividades(iniAtrasados, font, fontSubtitulo, documento, messageResources, request);
						}
					}
												
					// 3.0 Analisis Estrategicos
					dibujarEventos(date, fechaActualDate, org.getOrganizacionId(), documento, font, fontTitulo, fontSubtitulo, messageResources);
					
					// 4.0 Informe Cualitativo
					dibujarInformes(date, fechaActualDate, org.getOrganizacionId(), documento, font, fontTitulo, fontSubtitulo, messageResources);									
					documento.add(lineaEnBlanco(font));
				}
			}
			strategosPlanesService.close();
			strategosIniciativasService.close();
		} else {
			// todas las organizaciones
			Integer contador = 0;
			if (organizaciones.size() > 0) {
				for (Iterator<OrganizacionStrategos> iter = organizaciones.iterator(); iter.hasNext();) {
					OrganizacionStrategos organizacion = iter.next();

					filtros = new HashMap<String, Object>();
					reporte.setOrganizacionNombre(organizacion.getNombre());
					contador++;
					System.out.print("\n" + contador + "   -   " + organizacion.getNombre());

					String subTituloOrg = "Organización" + " - " + reporte.getOrganizacionNombre();
					agregarSubTitulo(documento, getConfiguracionPagina(request), subTituloOrg, true, true, 17.0F);

					if ((organizacion.getOrganizacionId() != null))
						filtros.put("organizacionId", organizacion.getOrganizacionId().toString());

					Paragraph texto = new Paragraph("", fontTitulo);
					PaginaLista paginaPlanes = strategosPlanesService.getPlanes(1, 30, "nombre", "ASC", true, filtros);
					List<Plan> planes = paginaPlanes.getLista();

					// 1.0 Indicadores Planes
					if (planes.size() > 0) {
						for (Plan plan : planes) {
							if (plan.getActivo()) {
								dibujarIndicadoresPlan(font, fontTitulo, fontSubtitulo, strategosPlanesService, plan, documento, messageResources,
										request);
							}
						}
					}				

					filtros.put("anio", anio);
					PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(1, 30, "nombre", "ASC", true,
							filtros);
					List<Iniciativa> iniciativas = paginaIniciativas.getLista();
					// 2.0 iniciativas
					if (iniciativas.size() > 0) {
						List<Iniciativa> iniAtrasados = new ArrayList();
						iniAtrasados = obtenerIniciativas(iniciativas, fontSubtitulo, texto, documento);
						if (iniAtrasados.size() > 0) {
							dibujarIniciativas(iniAtrasados, font, fontTitulo, documento, messageResources, request);

							dibujarActividades(iniAtrasados, font, fontSubtitulo, documento, messageResources, request);
						}
					}

					

					Integer periodo = obtenerFecha((byte) 5);
					String fecha = String.valueOf((periodo - 1)) + "/" + String.valueOf(new Date().getYear() + 1900);
					SimpleDateFormat date = new SimpleDateFormat("MM/yyyy");
					Date fechaActualDate = date.parse(fecha);
					
					// 3.0 Analisis Estrategicos
					dibujarEventos(date, fechaActualDate, organizacion.getOrganizacionId(), documento, font, fontTitulo, fontSubtitulo, messageResources);
					
					// 4.0 Informe Cualitativo
					dibujarInformes(date, fechaActualDate, organizacion.getOrganizacionId(), documento, font, fontTitulo, fontSubtitulo, messageResources);									
					documento.add(lineaEnBlanco(font));
				}
			}
			
			strategosPlanesService.close();
			strategosIniciativasService.close();
		}

	}
	
	private void dibujarInformes(SimpleDateFormat date, Date fechaActualDate, Long organizacionId, Document documento, Font font, Font fontTitulo, Font fontSubtitulo, MessageResources messageResources) throws ParseException, DocumentException {
		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance()
				.openStrategosExplicacionesService();
		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto = new Paragraph("", font);

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
				documento.add(lineaEnBlanco(font));
				texto = new Paragraph(messageResources.getMessage("jsp.extension.informes.cualitativos"), fontTitulo);
				texto.setAlignment(Element.ALIGN_LEFT);
				documento.add(texto);
				fontSubtitulo.setColor(0, 0, 255);
				texto = new Paragraph("No hay informes cualitativos en el periodo anterior", fontSubtitulo);
				texto.setIndentationLeft(50);
				documento.add(texto);
				fontSubtitulo.setColor(0, 0, 0);							
				texto = new Paragraph("Fecha de ultima Carga : " + explicacionUltima.getFechaFormateada(),
						fontSubtitulo);
				documento.add(texto);
				texto = new Paragraph("Titulo de la explicación : " + explicacionUltima.getTitulo(), fontSubtitulo);
				documento.add(texto);
			}
			if (explicacionUltima.getAdjuntosExplicacion().size() == 0) {
				if(!flag) {
					documento.add(lineaEnBlanco(font));
					texto = new Paragraph(messageResources.getMessage("jsp.extension.informes.cualitativos"), fontTitulo);
					texto.setAlignment(Element.ALIGN_LEFT);
					documento.add(texto);					
					texto = new Paragraph("Titulo de la explicación : " + explicacionUltima.getTitulo(), fontSubtitulo);
					documento.add(texto);
				}
				fontSubtitulo.setColor(0, 0, 255);
				texto = new Paragraph("No se han cargado anexos", fontSubtitulo);
				texto.setIndentationLeft(50);
				documento.add(texto);
				fontSubtitulo.setColor(0, 0, 0);				
			}
		}
	}
	
	private void dibujarEventos(SimpleDateFormat date, Date fechaActualDate, Long organizacionId, Document documento, Font font, Font fontTitulo, Font fontSubtitulo, MessageResources messageResources) throws ParseException, DocumentException {
		
		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance()
				.openStrategosExplicacionesService();
		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto = new Paragraph("", font);
		
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
				documento.add(lineaEnBlanco(font));
				texto = new Paragraph(messageResources.getMessage("jsp.extension.informes.eventos"), fontTitulo);
				texto.setAlignment(Element.ALIGN_LEFT);
				documento.add(texto);
				
				fontSubtitulo.setColor(0, 0, 255);
				texto = new Paragraph("No hay acta de reunion de analisis estrategico en el periodo anterior",
						fontSubtitulo);
				texto.setIndentationLeft(50);
				documento.add(texto);
				fontSubtitulo.setColor(0, 0, 0);			
				texto = new Paragraph("Fecha de ultima Carga : " + explicacionUltima.getFechaFormateada(),
						fontSubtitulo);
				documento.add(texto);
				texto = new Paragraph("Titulo de la explicación : " + explicacionUltima.getTitulo(), fontSubtitulo);
				documento.add(texto);				
			}
			if (explicacionUltima.getAdjuntosExplicacion().size() == 0) {
				if(!flag) {
					documento.add(lineaEnBlanco(font));
					texto = new Paragraph(messageResources.getMessage("jsp.extension.informes.eventos"), fontTitulo);
					texto.setAlignment(Element.ALIGN_LEFT);
					documento.add(texto);					
					texto = new Paragraph("Titulo de la explicación : " + explicacionUltima.getTitulo(), fontSubtitulo);
					documento.add(texto);
				}
				fontSubtitulo.setColor(0, 0, 255);
				texto = new Paragraph("No se han cargado anexos", fontSubtitulo);
				texto.setIndentationLeft(50);
				documento.add(texto);
				fontSubtitulo.setColor(0, 0, 0);				
			}
		}		
		strategosExplicacionesService.close();
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

	private void dibujarActividades(List<Iniciativa> iniAtrasados, Font font, Font fontSubtitulo, Document documento,
			MessageResources messageResources, HttpServletRequest request) throws Exception {
		Map<String, Object> filtros = new HashMap<String, Object>();
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance()
				.openStrategosPryActividadesService();
		Paragraph texto = new Paragraph("", font);
		for (Iniciativa iniciativa : iniAtrasados) {
			filtros = new HashMap<String, Object>();
			filtros.put("proyectoId", iniciativa.getProyectoId());
			List<PryActividad> actividades = strategosPryActividadesService
					.getActividades(0, 0, "fila", "ASC", true, filtros).getLista();
			if (actividades.size() > 0) {
				List<PryActividad> actAtrasadas = new ArrayList();
				actAtrasadas = obtenerActividades(actividades, iniciativa);
				if (actAtrasadas.size() > 0) {
					documento.add(lineaEnBlanco(font));
					texto = new Paragraph(
							((NavegadorForm) request.getSession().getAttribute("activarIniciativa")).getNombreSingular()
									+ " : " + iniciativa.getNombre(),
							fontSubtitulo);
					texto.setAlignment(Element.ALIGN_LEFT);
					documento.add(texto);

					TablaPDF tabla = null;
					tabla = new TablaPDF(getConfiguracionPagina(request), request);
					int[] columnas = new int[6];

					columnas = new int[5];

					columnas[0] = 15;
					columnas[1] = 3;
					columnas[2] = 5;
					columnas[3] = 5;
					columnas[4] = 10;

					tabla.setAmplitudTabla(100);
					tabla.crearTabla(columnas);

					tabla.setAlineacionHorizontal(1);
					tabla.setColorFondo(21, 60, 120);
					tabla.setColorLetra(255, 255, 255);
					tabla.setTamanoFont(10);
					tabla.setFormatoFont(Font.NORMAL);
					tabla.agregarCelda("Nombre Actividad");
					tabla.agregarCelda("Alerta");
					tabla.agregarCelda("Porcentaje de Avance");
					tabla.agregarCelda("Fecha Ultima Medicion");
					tabla.agregarCelda("Responsable de Ejecucion");

					tabla.setColorFondo(255, 255, 255);
					tabla.setColorLetra(0, 0, 0);
					for (PryActividad actividad : actAtrasadas) {
						tabla.agregarCelda(actividad.getNombre());

						String url = obtenerCadenaRecurso(request);
						if (actividad.getAlerta() == null)
							tabla.agregarCelda("");
						else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
							tabla.agregarCelda(Image.getInstance(
									new URL(url + "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
						else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
							tabla.agregarCelda(Image.getInstance(
									new URL(url + "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
						else if (actividad.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
							tabla.agregarCelda(Image.getInstance(
									new URL(url + "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif")));

						tabla.agregarCelda(actividad.getPorcentajeEjecutado() != null
								? actividad.getPorcentajeEjecutadoFormateado()
								: "");

						tabla.agregarCelda(
								actividad.getFechaUltimaMedicion() != null ? actividad.getFechaUltimaMedicion() : "");
						
						String nombreResp = null;
						if (actividad.getResponsableCargarEjecutado() != null) {
							nombreResp = actividad.getResponsableCargarEjecutado().getNombre();
						}
						tabla.agregarCelda(nombreResp != null ? nombreResp : "");
					}
					documento.add(lineaEnBlanco(font));
					documento.add(tabla.getTabla());
				}
				actAtrasadas.clear();
			}

		}
		strategosPryActividadesService.close();
	}
		
	private List<Iniciativa> obtenerIniciativas(List<Iniciativa> iniciativas, Font fontSubtitulo, Paragraph texto,
			Document documento) throws Exception {
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

	private void dibujarIniciativas(List<Iniciativa> iniAtrasados, Font font, Font fontTitulo, Document documento,
			MessageResources messageResources, HttpServletRequest request) throws Exception {

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
				.openStrategosIndicadoresService();

		documento.add(lineaEnBlanco(font));
		Paragraph texto = new Paragraph("", font);
		texto = new Paragraph(
				((NavegadorForm) request.getSession().getAttribute("activarIniciativa")).getNombrePlural(), fontTitulo);
		texto.setAlignment(Element.ALIGN_LEFT);
		documento.add(texto);

		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[7];

		columnas = new int[6];

		columnas[0] = 15;
		columnas[1] = 3;
		columnas[2] = 5;
		columnas[3] = 5;
		columnas[4] = 5;
		columnas[5] = 5;

		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);
		tabla.setAlineacionHorizontal(1);
		tabla.setColorFondo(21, 60, 120);
		tabla.setColorLetra(255, 255, 255);
		tabla.setTamanoFont(10);
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda("Nombre Iniciativa");
		tabla.agregarCelda("Alerta");
		tabla.agregarCelda("Porcentaje de Avance");
		tabla.agregarCelda("Frecuencia");
		tabla.agregarCelda("Fecha Ultima Medicion");
		tabla.agregarCelda("Medicion");

		tabla.setColorFondo(255, 255, 255);
		tabla.setColorLetra(0, 0, 0);

		for (Iniciativa iniciativa : iniAtrasados) {

			Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
					iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

			String frecuencia = obtenerFrecuencia(iniciativa.getFrecuencia());

			tabla.agregarCelda(iniciativa.getNombre());

			String url = obtenerCadenaRecurso(request);
			if (iniciativa.getAlerta() == null)
				tabla.agregarCelda("");
			else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
				tabla.agregarCelda(
						Image.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
			else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
				tabla.agregarCelda(
						Image.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
			else if (iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
				tabla.agregarCelda(
						Image.getInstance(new URL(url + "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif")));

			tabla.agregarCelda(iniciativa.getPorcentajeCompletadoFormateado() != null
					? iniciativa.getPorcentajeCompletadoFormateado()
					: "");
			tabla.agregarCelda(frecuencia);
			tabla.agregarCelda(iniciativa.getFechaUltimaMedicion() != null ? iniciativa.getFechaUltimaMedicion() : "");
			tabla.agregarCelda(
					indicador.getUltimaMedicionFormateada() != null ? indicador.getUltimaMedicionFormateada() : "");
		}
		documento.add(lineaEnBlanco(font));
		strategosIndicadoresService.close();
		documento.add(tabla.getTabla());
	}

	private List<Indicador> obtenerIndicadores(Integer nivel, Font font, List<Perspectiva> perspectivasPlan, Plan plan,
			Document documento, StrategosIndicadoresService strategosIndicadoresService,
			StrategosPerspectivasService strategosPerspectivasService, StrategosPlanesService strategosPlanesService,
			MessageResources messageResources, HttpServletRequest request) throws Exception {

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

	private void dibujarIndicadoresPlan(Font font, Font fontTitulo, Font fontSubTitulo, StrategosPlanesService strategosPlanesService, Plan plan,
			Document documento, MessageResources messageResources, HttpServletRequest request) throws Exception {
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
		TablaPDF tabla = null;
		if (perspectivas.size() > 0) {
			if (perspectiva.getPadreId() == null || perspectiva.getPadreId() == 0L)
				nivel = 0;
			else
				nivel++;
			indAtrasados = obtenerIndicadores(nivel, font, perspectivas, plan, documento, strategosIndicadoresService,
					strategosPerspectivasService, strategosPlanesService, messageResources, request);

			tabla = new TablaPDF(getConfiguracionPagina(request), request);
			int[] columnas = new int[7];

			columnas = new int[6];

			columnas[0] = 10;
			columnas[1] = 10;
			columnas[2] = 3;
			columnas[3] = 4;
			columnas[4] = 3;
			columnas[5] = 7;

			tabla.setAmplitudTabla(100);
			tabla.crearTabla(columnas);

			Paragraph texto = new Paragraph("", font);
			if (indAtrasados.size() > 0) {
				
				documento.add(lineaEnBlanco(font));				 
				texto = new Paragraph("Plan - " + plan.getNombre(), fontTitulo);
				texto.setAlignment(Element.ALIGN_LEFT);
				documento.add(texto);						
				texto = new Paragraph("- Indicadores", fontSubTitulo);
				texto.setAlignment(Element.ALIGN_LEFT);
				documento.add(texto);
				
				tabla.setAlineacionHorizontal(1);
				tabla.setColorFondo(21, 60, 120);
				tabla.setColorLetra(255, 255, 255);
				tabla.setTamanoFont(10);
				tabla.setFormatoFont(Font.NORMAL);
				tabla.agregarCelda("Nombre Perspectiva");
				tabla.agregarCelda("Nombre Indicador");
				tabla.agregarCelda("Alerta");
				tabla.agregarCelda("Fecha Ultima Medicion");
				tabla.agregarCelda("Medicion");
				tabla.agregarCelda("Responsable");

				tabla.setColorFondo(255, 255, 255);
				tabla.setColorLetra(0, 0, 0);

				for (Indicador indicador : indAtrasados) {
					tabla.agregarCelda(indicador.getPerspectivaNombre());
					// Nombre
					tabla.agregarCelda(indicador.getNombre());

					Byte alerta = strategosPlanesService.getAlertaIndicadorPorPlan(indicador.getIndicadorId(),
							plan.getPlanId());

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
						Image image = Image.getInstance(
								new URL(url + "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif"));
						texto.add(new Chunk(image, 0, 0));
					}

					tabla.agregarCelda(texto);
					
					tabla.agregarCelda(indicador.getFechaUltimaMedicion() != null ? indicador.getFechaUltimaMedicion() : "");
									
					tabla.agregarCelda(indicador.getUltimaMedicionFormateada() != null ? indicador.getUltimaMedicionFormateada() : "");					
					
					String nombreResp = null;
					if (indicador.getResponsableCargarEjecutado() != null) {
						nombreResp = indicador.getResponsableCargarEjecutado().getNombre();
					}
					tabla.agregarCelda(nombreResp != null ? nombreResp : "");					
				}
				documento.add(lineaEnBlanco(font));
			}
		}
		indAtrasados.clear();

		documento.add(tabla.getTabla());
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
