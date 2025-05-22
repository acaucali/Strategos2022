package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;
import org.hibernate.Session;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.ExplicacionPGN;
import com.visiongc.app.strategos.explicaciones.model.MemoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.util.TipoMemoExplicacion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicadorPK;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.StrategosTiposConvenioService;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.model.util.LapsoTiempo;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.report.Tabla;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.report.VgcFormatoReporte;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.web.struts.forms.NavegadorForm;

public class ReporteDetalladoProyectosAsociadosPdf extends VgcReporteBasicoAction  {
	private static Session sesion;
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 57;
	private int maxLineasAntesTabla = 4;
	
	@Override
	protected String agregarTitulo(HttpServletRequest request,	MessageResources mensajes) throws Exception
	{
		String desdeIniciativas = request.getParameter("desdeIniciativas") != null ? request.getParameter("desdeIniciativas") : "";
		if(desdeIniciativas.equals("true"))
			return "Reporte detallado de Proyectos/Planes de Acción";
		else
			return mensajes.getMessage("jsp.pagina.instrumentos.reporte.titulo.detalle");
		
	}
	
	@Override
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
	{
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		
		String anio = request.getParameter("anio") != null ? request.getParameter("anio") : "0";
		String anioInicial = request.getParameter("anioInicial") != null ? request.getParameter("anioInicial") : "";
		String anioFinal = request.getParameter("anioFinal") != null ? request.getParameter("anioFinal") : "";
		String perInicial = request.getParameter("perInicial") != null ? request.getParameter("perInicial") : "";
		String perFinal = request.getParameter("perFinal") != null ? request.getParameter("perFinal") : "";
		String estatus = request.getParameter("estatus") != null ? request.getParameter("estatus") : "";
		String todos = request.getParameter("todos") != null ? request.getParameter("todos") : "";
		String desdeIniciativas = request.getParameter("desdeIniciativas") != null ? request.getParameter("desdeIniciativas") : "";
		String iniciativaId = request.getParameter("iniciativaId") != null ? request.getParameter("iniciativaId") : "";
		String estatusIniciativa = request.getParameter("estatusIniciativa") != null ? request.getParameter("estatusIniciativa") : "";
	
		reporte.setAno(new Integer(anio));
		reporte.setAnoFinal(anioFinal);
		reporte.setMesFinal(perFinal);
		reporte.setAnoInicial(anioInicial);
		reporte.setMesInicial(perInicial);
		reporte.setEstatus2(estatusIniciativa);		
		reporte.setCooperanteId(request.getParameter("cop") != null && !request.getParameter("cop").toString().equals("") ? Long.parseLong(request.getParameter("cop")) : null);
		reporte.setEstatus(estatus);
		
		if(desdeIniciativas.equals("true"))
			ejecucionDesdeIniciativas(reporte,documento,request,mensajes, iniciativaId, null, true);
		else
			ejecucionInstrumento(reporte,documento,request,mensajes,todos);
	}
	
	private void ejecucionInstrumento(ReporteForm reporte, Document documento, HttpServletRequest request, MessageResources mensajes, String todos ) throws Exception {
		//Raiz del plan
	    lineas = 2;
	    
	    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());
	    //Nombre de la Organizacion, plan y periodo del reporte
		font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
		font.setStyle(Font.BOLD);
		fontBold.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
		fontBold.setStyle(Font.BOLD);

		font.setSize(8);
		font.setStyle(Font.NORMAL);
		fontBold.setSize(8);
		fontBold.setStyle(Font.BOLD);
		
		int nivel = 0;
		inicioTamanoPagina = lineasxPagina(font);
	    tamanoPagina = inicioTamanoPagina;
	    
	    StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
	    StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();
	    StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
	    StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
	    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	    StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
	    
		Map<String, String> filtros = new HashMap<String, String>();
	    int pagina = 0;
	    String atributoOrden = null;
	    String tipoOrden = null;

	    if (tipoOrden == null)
	    	tipoOrden = "ASC";
	    if (pagina < 1)
	    	pagina = 1;
	    
	    if(reporte.getEstatus() != null && !reporte.getEstatus().equals("0")) {
	    	filtros.put("estatus", reporte.getEstatus());
	    }if (todos.equals("false")) {
			filtros.put("anio", reporte.getAno().toString());	
	    }if(reporte.getCooperanteId() != null && reporte.getCooperanteId() != 0) {
	    	filtros.put("cooperanteId", reporte.getCooperanteId().toString());
	    }
	    	    
	    int cont = 0 ;
	    
	    PaginaLista paginaInstrumentos = strategosInstrumentosService.getInstrumentos(pagina, 30, atributoOrden, tipoOrden, true, filtros);
	    
	    documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
	    String titulo = "INFORME DEL ESTADO DE AVANCE DE LOS INSTRUMENTOS DE COOPERACION INTERNACIONAL";
	    dibujarEncabezado(mensajes, request, documento, titulo, true);
	    Paragraph texto;
		if (paginaInstrumentos.getLista().size() > 0)
		{
	    	for (Iterator<Instrumentos> iter = paginaInstrumentos.getLista().iterator(); iter.hasNext(); )
			{
	    		Instrumentos instrumento = iter.next();
	    		int cont1 = 0 ;
	    		if(cont != 0)
	    			documento.newPage();
	    		
	    		font.setStyle(Font.NORMAL);
	    		
	    		if(instrumento != null) {
	    			documento.add(lineaEnBlanco(font));
	    			documento.add(lineaEnBlanco(font));
	    			titulo = "INFORMACIÓN INSTRUMENTO DE COOPERACIÓN INTERNACIONAL";
	    			dibujarEncabezado(mensajes, request, documento, titulo, false);
	    			dibujarInstrumento(mensajes, request, documento, instrumento);
	    			
	    			filtros = new HashMap();
		    		filtros.put("instrumentoId", instrumento.getInstrumentoId().toString());
		    		filtros.put("estatusId", reporte.getEstatus2().toString());
		    		
		    		PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(pagina, 30, atributoOrden, tipoOrden, true, filtros);
		    		List<Iniciativa> iniciativas = paginaIniciativas.getLista();
		    		
		    		if(iniciativas.size() > 0 ) {
		    			for(Iniciativa iterIniciativa : iniciativas) {
			    			Iniciativa iniciativa = iterIniciativa;
			    			if(cont1 != 0)
			    				documento.newPage();
			    			documento.add(lineaEnBlanco(font));
			    			ejecucionDesdeIniciativas(reporte,documento,request,mensajes, iniciativa.getIniciativaId().toString(), instrumento, false);		
			    			cont1++;
			    			
			    		}
		    		}else {
						documento.add(lineaEnBlanco(font));

						font.setColor(0, 0, 255);
						Paragraph noIni = new Paragraph(mensajes.getMessage("jsp.reportes.plan.ejecucion.reporte.noiniciativas",
								"INICIATIVAS", "INTRUMENTO"), font);
						noIni.setIndentationLeft(50);
						documento.add(noIni);
						font.setColor(0, 0, 0);
						documento.add(lineaEnBlanco(font));
					}
 	    		}else
	    		{
	    			documento.add(lineaEnBlanco(font));
	    			font.setColor(0, 0, 255);
	    			texto = new Paragraph("No hay datos de los instrumentos para presentar", font);
	    			texto.setIndentationLeft(50);
	    			documento.add(texto);
	    			font.setColor(0, 0, 0);

	    			documento.add(lineaEnBlanco(font));

	    		}
	    		strategosCooperantesService.close();
	    		cont++;
			}
		}else
		{
			documento.add(lineaEnBlanco(font));
			font.setColor(0, 0, 255);
			texto = new Paragraph("No hay datos de los instrumentos para presentar", font);
			texto.setIndentationLeft(50);
			documento.add(texto);
			font.setColor(0, 0, 0);

			documento.add(lineaEnBlanco(font));

		}
	}

	private void ejecucionDesdeIniciativas(ReporteForm reporte, Document documento, HttpServletRequest request, MessageResources mensajes, String iniciativaId, Instrumentos instrumento, Boolean desdeIniciativas) throws Exception {		
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
	    StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
	    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	    StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
	    StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		Iniciativa iniciativa = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, Long.parseLong(iniciativaId));
		//Raiz del plan
	    lineas = 2;
	    
	    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());
	    //Nombre de la Organizacion, plan y periodo del reporte
		font.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
		font.setStyle(Font.BOLD);
		fontBold.setSize(VgcFormatoReporte.TAMANO_FUENTE_TITULO);
		fontBold.setStyle(Font.BOLD);

		font.setSize(8);
		font.setStyle(Font.NORMAL);
		fontBold.setSize(8);
		fontBold.setStyle(Font.BOLD);
		
		int nivel = 0;
		inicioTamanoPagina = lineasxPagina(font);
	    tamanoPagina = inicioTamanoPagina;
	    
	    Map<String, String> filtros = new HashMap<String, String>();
	    int pagina = 0;
	    String atributoOrden = null;
	    String tipoOrden = null;

	    if (tipoOrden == null)
	    	tipoOrden = "ASC";
	    if (pagina < 1)
	    	pagina = 1;	    	    
	    	    	    
	    documento.add(lineaEnBlanco(font));
	    String titulo = "INFORMACIÓN DEL PROYECTO";
		dibujarEncabezado(mensajes, request, documento, titulo, false);		    			
		dibujarIniciativa(mensajes, request, documento, iniciativa, instrumento, desdeIniciativas);
		
		Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
				iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

		
		List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(
				indicador.getIndicadorId(), SerieTiempo.getSerieRealId(),
				new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), new Integer(reporte.getMesInicial()),
				new Integer(reporte.getMesFinal()));		
		
		List<Integer> periodos = new ArrayList<Integer>();
		int periodoInicial = 0;
		int periodoFinal = 0;
		for(Medicion medicion : medicionesEjecutado) {	
			periodos.add(medicion.getMedicionId().getPeriodo());	
		}
		if(medicionesEjecutado.size() > 0 ) {
			periodoInicial = Collections.min(periodos);
			periodoFinal = Collections.max(periodos);
		}
		
		Map<String, Object> filtrosAct = new HashMap<String, Object>();
		
		filtrosAct = new HashMap<String, Object>();
		filtrosAct.put("proyectoId", iniciativa.getProyectoId());
					    					    					    		
		List<PryActividad> actividades = strategosPryActividadesService
				.getActividades(0, 0, "fila", "ASC", true, filtrosAct).getLista();			    					    		

		if(actividades.size() > 0 ) {
			titulo = "PRODUCTOS O ENTREGABLES FINAL(ES)";
			documento.add(lineaEnBlanco(font));
			dibujarEncabezado(mensajes, request, documento, titulo, false);		    			
			dibujarTituloPryActividad(mensajes, request, documento);	
		
			for (PryActividad iter1 : actividades) {
				Indicador indicadorAct = (Indicador)strategosIndicadoresService.load(Indicador.class, iter1.getIndicadorId());			    			
				dibujarPryActividad(mensajes, request, documento, iter1, indicadorAct);
			}
			
			titulo = "Periodo calculados del proyecto";
			documento.add(lineaEnBlanco(font));
			dibujarEncabezado(mensajes, request, documento, titulo, false);
			dibujarPeriodo(mensajes, request, documento, reporte, periodoInicial, periodoFinal);
			
		}else {
			documento.add(lineaEnBlanco(font));

			font.setColor(0, 0, 255);
			Paragraph noIni = new Paragraph("No hay actividades definidas para este proyecto", font);
			noIni.setIndentationLeft(50);
			documento.add(noIni);
			font.setColor(0, 0, 0);
			documento.add(lineaEnBlanco(font));
		}
		
		if(actividades.size() > 0 ) {
			documento.add(lineaEnBlanco(font));
			titulo = "Avance del proyecto (%)\nProductos o resultados final(es)\n(Este campo se diligencia trimestralmente";
			dibujarEncabezado(mensajes, request, documento, titulo, false);		    			
			dibujarTituloAvance(mensajes, request, documento);
			dibujarSubtitulo(mensajes, request, documento);
			for (PryActividad iter1 : actividades) {
				List<Medicion> medicionesEjecutadoAct = strategosMedicionesService.getMedicionesPeriodo(
						iter1.getIndicadorId(), SerieTiempo.getSerieRealId(),
						new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), new Integer(reporte.getMesInicial()),
						new Integer(reporte.getMesFinal()));			
				List<Medicion> medicionesProgramadoAct = strategosMedicionesService.getMedicionesPeriodo(
						iter1.getIndicadorId(), SerieTiempo.getSerieProgramadoId(),
						new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), new Integer(reporte.getMesInicial()),
						new Integer(reporte.getMesFinal()));
								
				dibujarAvances(mensajes, request, documento, iter1, medicionesEjecutadoAct, medicionesProgramadoAct );	
				
			}
			List<ExplicacionPGN> explicacionesPrimer = new ArrayList<ExplicacionPGN>();				    						
			List<ExplicacionPGN> explicacionesSegundo = new ArrayList<ExplicacionPGN>();
			List<ExplicacionPGN> explicacionesTercer = new ArrayList<ExplicacionPGN>();
			List<ExplicacionPGN> explicacionesCuarto = new ArrayList<ExplicacionPGN>();	
			for (PryActividad iter1 : actividades) {				    								    				
				Map<String, Comparable> filtrosExp = new HashMap<String, Comparable>();				    				
				if ((iter1.getActividadId() != null) && (!iter1.getActividadId().equals("")) && iter1.getActividadId() != 0)
					filtros.put("objetoId", iter1.getActividadId().toString());
				
				PaginaLista paginaExplicaciones = strategosExplicacionesService.getExplicacionesPGN(pagina, 30, atributoOrden, tipoOrden, true, filtros);				    				
				if(paginaExplicaciones != null) {
					List<ExplicacionPGN> explicaciones = paginaExplicaciones.getLista();
					if(explicaciones.size() >0) {
						documento.add(lineaEnBlanco(font));
						Integer periodo = 0;
										
						for(ExplicacionPGN explicacion : explicaciones) {															
							periodo = obtenerPeriodo(explicacion.getFecha().getMonth()+1);
							if(periodo == 1)
								explicacionesPrimer.add(explicacion);
							else if(periodo == 2)
								explicacionesSegundo.add(explicacion);
							else if(periodo == 3)
								explicacionesTercer.add(explicacion);
							else if(periodo == 4)
								explicacionesCuarto.add(explicacion);				    											    											    							
						}
						
					}
				}
			}
			if(explicacionesPrimer.size() > 0  && explicacionesPrimer != null ) {
				dibujarTituloExplicaciones(mensajes, request, documento, 1);
				for (ExplicacionPGN explicacion1 : explicacionesPrimer) {
					dibujarSubtituloExplicaciones(mensajes, request, documento, explicacion1);
					dibujarExplicaciones(mensajes, request, documento, explicacion1);					
				}
			}
			if(explicacionesSegundo.size() > 0 && explicacionesSegundo != null  ) {
				dibujarTituloExplicaciones(mensajes, request, documento, 2);
				for (ExplicacionPGN explicacion2 : explicacionesSegundo) {
					dibujarSubtituloExplicaciones(mensajes, request, documento, explicacion2);
					dibujarExplicaciones(mensajes, request, documento, explicacion2);
					
				}
			}
			if(explicacionesTercer.size() > 0 && explicacionesTercer != null ) {
				dibujarTituloExplicaciones(mensajes, request, documento, 3);
				for (ExplicacionPGN explicacion3 : explicacionesTercer) {
					dibujarSubtituloExplicaciones(mensajes, request, documento, explicacion3);					
					dibujarExplicaciones(mensajes, request, documento, explicacion3);
					
				}
			}
			if(explicacionesCuarto.size() > 0 && explicacionesCuarto != null ) {
				dibujarTituloExplicaciones(mensajes, request, documento, 4);
				for (ExplicacionPGN explicacion4 : explicacionesCuarto) {
					dibujarSubtituloExplicaciones(mensajes, request, documento, explicacion4);
					dibujarExplicaciones(mensajes, request, documento, explicacion4);					
				}
			}				
		}else {
			documento.add(lineaEnBlanco(font));

			font.setColor(0, 0, 255);
			Paragraph noIni = new Paragraph("No hay actividades definidas para este proyecto", font);
			noIni.setIndentationLeft(50);
			documento.add(noIni);
			font.setColor(0, 0, 0);
			documento.add(lineaEnBlanco(font));
		}
	}
	
	public void dibujarEncabezado(MessageResources mensajes, HttpServletRequest request, Document documento, String texto, Boolean inicial) throws Exception{
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[2];
		
		columnas = new int[1];

		columnas[0] = 100;

		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);
		tabla.setAlineacionHorizontal(1);
		tabla.setFormatoFont(Font.BOLD);
		tabla.setColorFondo(21, 60, 120);
		tabla.setColorLetra(255, 255, 255);
		tabla.agregarCelda(texto);

		if(inicial == true) {
			tabla.setAmplitudTabla(100);
			tabla.setColorFondo(255, 255, 255);
			tabla.setColorLetra(0, 0, 0);
			tabla.agregarCelda("AÑO " + request.getParameter("anio"));
		}
		documento.add(tabla.getTabla());
	}
	
	public void dibujarInstrumento(MessageResources mensajes, HttpServletRequest request, Document documento, Instrumentos instrumento) throws Exception{
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[3];
		
		columnas = new int[2];

		columnas[0] = 20;
		columnas[1] = 80;
		
		tabla.setAmplitudTabla(100);
		tabla.setTamanoFont(16);
		tabla.crearTabla(columnas);
		
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda("\nNombre del cooperante\n");
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(instrumento.getCooperante().getNombre());
		
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda("Nombre del Instrumento");
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(instrumento.getNombreCorto());
		
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda("Objetivo del instrumento");
		tabla.setFormatoFont(Font.NORMAL);
		if(instrumento.getObjetivoInstrumento() != null || instrumento.getObjetivoInstrumento() != "")
			tabla.agregarCelda(instrumento.getObjetivoInstrumento());
		else
			tabla.agregarCelda("");
		
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda("Vigencia del instrumento");
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(instrumento.getFechaTerminacion());
		documento.add(tabla.getTabla());
	}

	public void dibujarIniciativa(MessageResources mensajes, HttpServletRequest request, Document documento, Iniciativa iniciativa, Instrumentos instrumento,  Boolean desdeIniciativa) throws Exception{
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[3];
		
		columnas = new int[2];

		columnas[0] = 20;
		columnas[1] = 80;
		
		tabla.setAmplitudTabla(100);
		tabla.setTamanoFont(16);
		tabla.crearTabla(columnas);
		
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda("\nNombre del Proyecto\n");
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getNombre());
		
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda("\nObjetivo del proyecto (General)\n");
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getObjetivoGeneral());
		
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda("\nObjetivo Estrategico PEI\n");
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getObjetivoEstrategico());
		
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda("\n" + ((NavegadorForm) request.getSession().getAttribute("activarIniciativa"))
				.getNombreSingular() +" Estrategica\n");
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getIniciativaEstrategica());
		
		if(!desdeIniciativa) {
			tabla.setFormatoFont(Font.BOLD);
			tabla.agregarCelda("\nAlineación con el Plan Decenal del Ministerio Público (PDMP)\n");
			tabla.setFormatoFont(Font.NORMAL);
			tabla.agregarCelda(iniciativa.getAlineacionPDMP());
			
			tabla.setFormatoFont(Font.BOLD);
			tabla.agregarCelda("\nAlineación con los Objetivos de Desarrollo Sostenible (ODS)\n");
			tabla.setFormatoFont(Font.NORMAL);
			tabla.agregarCelda(iniciativa.getAlineacionODS());
		}
		
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda("\nPoblación objetivo\n");
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getPoblacionBeneficiada());		
		
		if(!desdeIniciativa) {
			tabla.setFormatoFont(Font.BOLD);
			tabla.agregarCelda("\nCobertura Geográfica\n");
			tabla.setFormatoFont(Font.NORMAL);
			tabla.agregarCelda(iniciativa.getCoberturaGeografica());	
			
			tabla.setFormatoFont(Font.BOLD);
			tabla.agregarCelda("\nImpacto frente a la ciudadanía\n");
			tabla.setFormatoFont(Font.NORMAL);
			tabla.agregarCelda(iniciativa.getImpactoCiudadania());	
			
			tabla.setFormatoFont(Font.BOLD);
			tabla.agregarCelda("\nSostenibilidad\n");
			tabla.setFormatoFont(Font.NORMAL);
			tabla.agregarCelda(iniciativa.getSostenibilidad());		
		
			tabla.setFormatoFont(Font.BOLD);
			tabla.agregarCelda("\nCooperante\n");
			tabla.setFormatoFont(Font.NORMAL);
			tabla.agregarCelda(instrumento.getCooperante().getNombre());
			
			tabla.setFormatoFont(Font.BOLD);
			tabla.agregarCelda("\nDependencia responsable\n");
			tabla.setFormatoFont(Font.NORMAL);
			tabla.agregarCelda(iniciativa.getDependenciaResponsable());		
		
			tabla.setFormatoFont(Font.BOLD);
			tabla.agregarCelda("\nOtras dependencias competentes\n");
			tabla.setFormatoFont(Font.NORMAL);
			tabla.agregarCelda(iniciativa.getDependenciasCompetentes());
		}
		
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda("\nCoordinador del proyecto\n");
		tabla.setFormatoFont(Font.NORMAL);
		tabla.agregarCelda(iniciativa.getResponsableProyecto());
		
		documento.add(tabla.getTabla());
	}
	
	public void dibujarTituloPryActividad(MessageResources mensajes, HttpServletRequest request, Document documento) throws Exception{
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[7];
		
		columnas = new int[6];

		columnas[0] = 40;
		columnas[1] = 35;
		columnas[2] = 10;
		columnas[3] = 15;
		columnas[4] = 15;
		columnas[5] = 15;
		
		tabla.setAmplitudTabla(100);
		tabla.setTamanoFont(16);
		tabla.crearTabla(columnas);			
		
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda("\nProductos o entregables final(es)\n");
		
		tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);		
		tabla.agregarCelda("\nIndicador de producto\n");
		tabla.agregarCelda("\nMeta\n");
		tabla.agregarCelda("\nFecha de inicio\n");
		tabla.agregarCelda("\nFecha de finalizacion\n");			
		tabla.agregarCelda("\nPeso del producto\n");
		
		documento.add(tabla.getTabla());
	}

	public void dibujarPryActividad(MessageResources mensajes, HttpServletRequest request, Document documento, PryActividad actividad, Indicador indicador) throws Exception{
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[7];
		
		columnas = new int[6];

		columnas[0] = 40;
		columnas[1] = 35;
		columnas[2] = 10;
		columnas[3] = 15;
		columnas[4] = 15;
		columnas[5] = 15;
		
		tabla.setAmplitudTabla(100);
		tabla.setTamanoFont(16);
		tabla.crearTabla(columnas);		
					
		tabla.agregarCelda(actividad.getNombre());
				
		tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);		
		tabla.agregarCelda(indicador.getNombreCorto());				
		tabla.agregarCelda(actividad.getPorcentajeEsperadoFormateado());				
		tabla.agregarCelda(actividad.getComienzoPlanFormateada());		
		tabla.agregarCelda(actividad.getFinPlanFormateada());	
		tabla.agregarCelda(actividad.getPesoFormateado());
		
		documento.add(tabla.getTabla());
	}
	
	public void dibujarPeriodo(MessageResources mensajes, HttpServletRequest request, Document documento, ReporteForm reporte, int periodoInicial, int periodoFinal) throws Exception{
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[5];
		
		columnas = new int[4];

		columnas[0] = 20;
		columnas[1] = 25;
		columnas[2] = 20;
		columnas[3] = 25;
		
		
		tabla.setAmplitudTabla(100);
		tabla.setTamanoFont(16);
		tabla.crearTabla(columnas);			
		tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);
		tabla.setFormatoFont(Font.BOLD);
		
		tabla.agregarCelda("\nI Trimestre\n");					
		tabla.agregarCelda("\nII Trimestre\n");
		tabla.agregarCelda("\nIII Trimestre\n");
		tabla.agregarCelda("\nIV Trimestre\n");
		
		if(periodoInicial != 0 && periodoFinal !=0) {
			if(periodoInicial == 1) {
				for(int i = periodoInicial; i <= periodoFinal; i++) {
					tabla.agregarCelda("\nX\n");
				}
				if(periodoFinal == 1) {
					tabla.agregarCelda("\n\n");
					tabla.agregarCelda("\n\n");
					tabla.agregarCelda("\n\n");
				}
				if(periodoFinal == 2) {
					tabla.agregarCelda("\n\n");
					tabla.agregarCelda("\n\n");
				}
				if(periodoFinal == 3) {
					tabla.agregarCelda("\n\n");				
				}
			}
			
			if(periodoInicial == 2) {
				tabla.agregarCelda("\n\n");
				for(int i = periodoInicial; i <= periodoFinal; i++) {
					tabla.agregarCelda("\nX\n");
				}
				if(periodoFinal == 2) {
					tabla.agregarCelda("\n\n");
					tabla.agregarCelda("\n\n");
				}
				if(periodoFinal == 3) {
					tabla.agregarCelda("\n\n");				
				}
			}
			
			if(periodoInicial == 3) {
				tabla.agregarCelda("\n\n");
				tabla.agregarCelda("\n\n");
				for(int i = periodoInicial; i <= periodoFinal; i++) {
					tabla.agregarCelda("\nX\n");
				}
				if(periodoFinal == 3) {
					tabla.agregarCelda("\n\n");				
				}
			}
			
			if(periodoInicial == 4) {
				tabla.agregarCelda("\n\n");
				tabla.agregarCelda("\n\n");
				tabla.agregarCelda("\n\n");
				for(int i = periodoInicial; i <= periodoFinal; i++) {
					tabla.agregarCelda("\nX\n");
				}
			}
		}else {
			tabla.agregarCelda("\n\n");
			tabla.agregarCelda("\n\n");
			tabla.agregarCelda("\n\n");
			tabla.agregarCelda("\n\n");
		}				
		documento.add(tabla.getTabla());
	}
		
	public void dibujarTituloAvance(MessageResources mensajes, HttpServletRequest request, Document documento) throws Exception{
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[4];
		
		columnas = new int[3];

		columnas[0] = 32;
		columnas[1] = 45;
		columnas[2] = 45;
		
		
		tabla.setAmplitudTabla(100);
		tabla.setTamanoFont(16);
		tabla.crearTabla(columnas);		
		tabla.setColorFondo(180, 198, 231);
		tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);			
		tabla.setFormatoFont(Font.BOLD);
		tabla.agregarCelda("Productos entregables finales");								
		tabla.agregarCelda("Avance programado trimestreal (%)");				
		tabla.agregarCelda("Avance real(%)");				
		
		
		documento.add(tabla.getTabla());
	}
	
	public void dibujarSubtitulo(MessageResources mensajes, HttpServletRequest request, Document documento) throws Exception{
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[10];
		
		columnas = new int[9];

		columnas[0] = 57;
		columnas[1] = 20;
		columnas[2] = 20;
		columnas[3] = 20;
		columnas[4] = 20;
		columnas[5] = 20;
		columnas[6] = 20;
		columnas[7] = 20;
		columnas[8] = 20;
		
		
		tabla.setAmplitudTabla(100);
		tabla.setTamanoFont(16);
		tabla.crearTabla(columnas);	
		tabla.setColorFondo(180, 198, 231);		
		tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);			
		tabla.agregarCelda("");										
		tabla.agregarCelda("I TRIM");				
		tabla.agregarCelda("II TRIM");
		tabla.agregarCelda("III TRIM");
		tabla.agregarCelda("IV TRIM");
		tabla.agregarCelda("I TRIM");				
		tabla.agregarCelda("II TRIM");
		tabla.agregarCelda("III TRIM");
		tabla.agregarCelda("IV TRIM");
		
		
		documento.add(tabla.getTabla());
	}
	
	public void dibujarAvances(MessageResources mensajes, HttpServletRequest request, Document documento, PryActividad actividad,List<Medicion> medicionesEjecutadoAct, List<Medicion> medicionesProgramadoAct) throws Exception{
	
		List<Integer> periodosEjecutadoAct = new ArrayList<Integer>();
		
		int periodoEjecutadoInicial = 0;
		int periodoEjecutadoFinal  = 0;
		int periodoProgramadoInicial = 0;
		int periodoProgramadoFinal = 0;
		for(Medicion medicion : medicionesEjecutadoAct) {	
			periodosEjecutadoAct.add(medicion.getMedicionId().getPeriodo());	
		}
		if(periodosEjecutadoAct.size() > 0 ) {
			periodoEjecutadoInicial = Collections.min(periodosEjecutadoAct);
			periodoEjecutadoFinal = Collections.max(periodosEjecutadoAct);
		}
		List<Integer> periodosProgramadoAct = new ArrayList<Integer>();
		
		for(Medicion medicion : medicionesProgramadoAct) {	
			periodosProgramadoAct.add(medicion.getMedicionId().getPeriodo());	
		}
		if(periodosProgramadoAct.size() > 0 ) {
			periodoProgramadoInicial = Collections.min(periodosProgramadoAct);
			periodoProgramadoFinal = Collections.max(periodosProgramadoAct);
		}
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[10];
		
		columnas = new int[9];

		columnas[0] = 57;
		columnas[1] = 20;
		columnas[2] = 20;
		columnas[3] = 20;
		columnas[4] = 20;
		columnas[5] = 20;
		columnas[6] = 20;
		columnas[7] = 20;
		columnas[8] = 20;
							
		
		tabla.setAmplitudTabla(100);
		tabla.setTamanoFont(16);
		tabla.crearTabla(columnas);		
		tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);			
		tabla.agregarCelda("\n"+actividad.getNombre());								

		if(periodoProgramadoInicial != 0 && periodoProgramadoFinal != 0) {		
			if(periodoProgramadoInicial == 1) {
				for(int i = periodoProgramadoInicial; i <= periodoProgramadoFinal; i++) {
					for(Medicion medicion : medicionesProgramadoAct) {
						if(medicion.getMedicionId().getPeriodo() == i) {						
							tabla.agregarCelda(medicion.getValor().toString());
						}
					}
				}
				if(periodoProgramadoFinal == 1) {
					tabla.agregarCelda("\n\n");
					tabla.agregarCelda("\n\n");
					tabla.agregarCelda("\n\n");
				}
				if(periodoProgramadoFinal == 2) {
					tabla.agregarCelda("\n\n");
					tabla.agregarCelda("\n\n");
				}
				if(periodoProgramadoFinal == 3) {
					tabla.agregarCelda("\n\n");				
				}
			}
			
			if(periodoProgramadoInicial == 2) {
				tabla.agregarCelda("\n\n");
				for(int i = periodoProgramadoInicial; i <= periodoProgramadoFinal; i++) {
					for(Medicion medicion : medicionesProgramadoAct) {
						if(medicion.getMedicionId().getPeriodo() == i) {							
							tabla.agregarCelda(medicion.getValor().toString());
						}
					}
				}
				if(periodoProgramadoFinal == 2) {
					tabla.agregarCelda("\n\n");
					tabla.agregarCelda("\n\n");
				}
				if(periodoProgramadoFinal == 3) {
					tabla.agregarCelda("\n\n");				
				}
			}
			
			if(periodoProgramadoInicial == 3) {
				tabla.agregarCelda("\n\n");
				tabla.agregarCelda("\n\n");
				for(int i = periodoProgramadoInicial; i <= periodoProgramadoFinal; i++) {
					for(Medicion medicion : medicionesProgramadoAct) {
						if(medicion.getMedicionId().getPeriodo() == i) {							
							tabla.agregarCelda(medicion.getValor().toString());
						}
					}
				}
				if(periodoProgramadoFinal == 3) {
					tabla.agregarCelda("\n\n");				
				}
			}
			
			if(periodoProgramadoInicial == 4) {
				tabla.agregarCelda("\n\n");
				tabla.agregarCelda("\n\n");
				tabla.agregarCelda("\n\n");
				for(int i = periodoProgramadoInicial; i <= periodoProgramadoFinal; i++) {
					for(Medicion medicion : medicionesProgramadoAct) {
						if(medicion.getMedicionId().getPeriodo() == i) {							
							tabla.agregarCelda(medicion.getValor().toString());
						}
					}
				}
			}
		
		}else {
			tabla.agregarCelda("\n\n");
			tabla.agregarCelda("\n\n");
			tabla.agregarCelda("\n\n");
			tabla.agregarCelda("\n\n");
		}
		
		
		if(periodoEjecutadoInicial != 0 && periodoEjecutadoFinal != 0) {	
	
			if(periodoEjecutadoInicial == 1) {
				for(int i = periodoEjecutadoInicial; i <= periodoEjecutadoFinal; i++) {
					for(Medicion medicion : medicionesEjecutadoAct) {
						if(medicion.getMedicionId().getPeriodo() == i) {						
							tabla.agregarCelda(medicion.getValor().toString());
						}
					}
				}
				if(periodoEjecutadoFinal == 1) {
					tabla.agregarCelda("\n\n");
					tabla.agregarCelda("\n\n");
					tabla.agregarCelda("\n\n");
				}
				if(periodoEjecutadoFinal == 2) {
					tabla.agregarCelda("\n\n");
					tabla.agregarCelda("\n\n");
				}
				if(periodoEjecutadoFinal == 3) {
					tabla.agregarCelda("\n\n");				
				}
			}
			
			if(periodoEjecutadoInicial == 2) {
				tabla.agregarCelda("\n\n");
				for(int i = periodoEjecutadoInicial; i <= periodoEjecutadoFinal; i++) {
					for(Medicion medicion : medicionesEjecutadoAct) {
						if(medicion.getMedicionId().getPeriodo() == i) {							
							tabla.agregarCelda(medicion.getValor().toString());
						}
					}
				}
				if(periodoEjecutadoFinal == 2) {
					tabla.agregarCelda("\n\n");
					tabla.agregarCelda("\n\n");
				}
				if(periodoEjecutadoFinal == 3) {
					tabla.agregarCelda("\n\n");				
				}
			}
			
			if(periodoEjecutadoInicial == 3) {
				tabla.agregarCelda("\n\n");
				tabla.agregarCelda("\n\n");
				for(int i = periodoEjecutadoInicial; i <= periodoEjecutadoFinal; i++) {
					for(Medicion medicion : medicionesEjecutadoAct) {
						if(medicion.getMedicionId().getPeriodo() == i) {							
							tabla.agregarCelda(medicion.getValor().toString());
						}
					}
				}
				if(periodoEjecutadoFinal == 3) {
					tabla.agregarCelda("\n\n");				
				}
			}
			
			if(periodoEjecutadoInicial == 4) {
				tabla.agregarCelda("\n\n");
				tabla.agregarCelda("\n\n");
				tabla.agregarCelda("\n\n");
				for(int i = periodoEjecutadoInicial; i <= periodoEjecutadoFinal; i++) {
					for(Medicion medicion : medicionesEjecutadoAct) {
						if(medicion.getMedicionId().getPeriodo() == i) {							
							tabla.agregarCelda(medicion.getValor().toString());
						}
					}
				}
			}			
		}else {
			tabla.agregarCelda("\n\n");
			tabla.agregarCelda("\n\n");
			tabla.agregarCelda("\n\n");
			tabla.agregarCelda("\n\n");
		}
		
		documento.add(tabla.getTabla());
	}
	
	public void dibujarTituloExplicaciones(MessageResources mensajes, HttpServletRequest request, Document documento, Integer periodo) throws Exception{
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[2];
		
		columnas = new int[1];

		columnas[0] = 100;

		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);
		tabla.setAlineacionHorizontal(0);
		tabla.setFormatoFont(Font.BOLD);		
		tabla.setColorFondo(180, 198, 231);		
		if(periodo == 1)
			tabla.agregarCelda("\nI Trimestre");				
		else if(periodo ==2) 
			tabla.agregarCelda("\nII Trimestre");
		else if(periodo ==3)
			tabla.agregarCelda("\nIII Trimestre");
		else if(periodo == 4)
			tabla.agregarCelda("\nIV Trimestre");
		
		documento.add(tabla.getTabla());
	}
	
	public void dibujarSubtituloExplicaciones(MessageResources mensajes, HttpServletRequest request, Document documento, ExplicacionPGN explicacion) throws Exception{
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[2];
		
		columnas = new int[1];

		columnas[0] = 100;

		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);
		tabla.setAlineacionHorizontal(0);
		tabla.setFormatoFont(Font.BOLD);			
		PryActividad actividad = (PryActividad)strategosPryActividadesService.load(PryActividad.class, explicacion.getObjetoId());	
		tabla.agregarCelda(actividad.getNombre());		
		
		documento.add(tabla.getTabla());
	}
	
	public void dibujarExplicaciones(MessageResources mensajes, HttpServletRequest request, Document documento, ExplicacionPGN memoExplicacion) throws Exception{
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[2];
		
		String recuadro = "☐";
        String recuadroMarcado = "☑";
        
        String siTexto = memoExplicacion.getCumplimiendoFechas() ? recuadroMarcado : recuadro;
        String noTexto = memoExplicacion.getCumplimiendoFechas() ? recuadroMarcado : recuadro;
		
		columnas = new int[1];

		columnas[0] = 100;

		tabla.setAmplitudTabla(100);
		tabla.crearTabla(columnas);
		tabla.setAlineacionHorizontal(0);		
		tabla.setFormatoFont(Font.NORMAL);				
		if(memoExplicacion.getCumplimiendoFechas())
			tabla.agregarCelda("¿Se estan cumpliendo las fechas en el plan de trabajo?     Si: X     No: "
					+ "\n\nPor favor explique los retos o inconvenientes que se han presentado en el desarrollo del producto durante el trimestre de reporte. \n" 
					+ memoExplicacion.getExplicacionFechas());
		else
			tabla.agregarCelda("¿Se estan cumpliendo las fechas en el plan de trabajo?     Si:       No: X "
					+ "\n\nPor favor explique los retos o inconvenientes que se han presentado en el desarrollo del producto durante el trimestre de reporte. \n" 
					+ memoExplicacion.getExplicacionFechas());
		
		if(memoExplicacion.getRecibido())
			tabla.agregarCelda("¿Se recibio el producto?     Si: X     No: "
					+ "\n\nEn caso de haberlo recibido, explique para que sirve y como va a usar este producto en el desarrollo de sus funciones. \n"
					+ memoExplicacion.getExplicacionRecibido());
		else
			tabla.agregarCelda("¿Se recibio el producto?     Si:       No: X"
					+ "\n\nEn caso de haberlo recibido, explique para que sirve y como va a usar este producto en el desarrollo de sus funciones. \n"
					+ memoExplicacion.getExplicacionRecibido());
		
		documento.add(tabla.getTabla());
	}

	public Integer obtenerPeriodo(Integer mes) {
		Integer periodo = 0;
		
		if(mes >= 1 && mes < 4 ) {
			periodo = 1;
		}else if(mes >= 4 && mes < 7 ) {
			periodo = 2;
		}else if(mes >= 7 && mes < 10 ) {
			periodo = 3;
		}else if(mes >= 10 && mes <= 12 ) {
			periodo = 4;
		}		
		return periodo;
	}
}
