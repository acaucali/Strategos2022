package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;
import org.hibernate.Session;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.StrategosTiposConvenioService;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.instrumentos.model.TipoConvenio;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.framework.web.struts.forms.FiltroForm;

public class ReporteInstrumentoDetallePdf extends VgcReporteBasicoAction {
	private static Session sesion;
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 57;
	private int maxLineasAntesTabla = 4;

	@Override
	protected String agregarTitulo(HttpServletRequest request,	MessageResources mensajes) throws Exception
	{
		return mensajes.getMessage("jsp.pagina.instrumentos.reporte.titulo");
	}

	@Override
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
	{
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();

		String alcance = (request.getParameter("alcance"));
		String instrumentoId = (request.getParameter("instrumentoId"));

		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;

		Font fuente = getConfiguracionPagina(request).getFuente();
        MessageResources messageResources = getResources(request);

		String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";

		FiltroForm filtro = new FiltroForm();

		if (filtroNombre.equals(""))
			filtro.setNombre(null);
		else
			filtro.setNombre(filtroNombre);
		reporte.setFiltro(filtro);

		StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();


		if(request.getParameter("alcance").equals("1")){
			//instrumento seleccionado
			Instrumentos instrumento = (Instrumentos)strategosInstrumentosService.load(Instrumentos.class, new Long(instrumentoId));
			if(instrumento != null) {

				documento.add(lineaEnBlanco(fuente));

				Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

			    //Nombre de la Organizacion, plan y periodo del reporte
				font.setSize(10);
				font.setStyle(Font.BOLD);

				texto = new Paragraph("Instrumento: "+ instrumento.getNombreCorto(), font);
				texto.setAlignment(Element.ALIGN_LEFT);
				texto.setIndentationLeft(16);
				documento.add(texto);


				generarTabla1(request, documento, instrumento);
				generarTabla2(request, documento, instrumento);
				generarTabla3(request, documento, instrumento);
				generarTabla4(request, documento, instrumento);
			}

		}else {
			//todos los instrumentos

			String nombreAttribute = "";
			String anioAttribute = "";
			String estatusStAttribute = "";
			Long cooperanteIdAttribute = null;
			Long tiposConvenioIdAttribute = null;
			String historicoAttribute = "";
			Boolean isHistorico = false;
			Byte estatus = 0;

			if (request.getSession().getAttribute("nombreInstrumento") != null)
				nombreAttribute = (String) request.getSession().getAttribute("nombreInstrumento");
			else
				nombreAttribute = "";

			if (request.getSession().getAttribute("anioInstrumento") != null)
				anioAttribute = (String) request.getSession().getAttribute("anioInstrumento");
			else
				anioAttribute = "";

			if (request.getSession().getAttribute("estatusStInstrumento") != null)
				estatusStAttribute = (String) request.getSession().getAttribute("estatusStInstrumento");
			else
				estatusStAttribute = "";

			if (request.getSession().getAttribute("cooperanteIdInstrumento") != null)
				cooperanteIdAttribute = (Long) request.getSession().getAttribute("cooperanteIdInstrumento");
			else
				cooperanteIdAttribute = null;

			if (request.getSession().getAttribute("tiposConvenioIdInstrumento") != null)
				tiposConvenioIdAttribute = (Long) request.getSession().getAttribute("tiposConvenioIdInstrumento");
			else
				tiposConvenioIdAttribute = null;
			
			if (request.getSession().getAttribute("historicoInstrumento") != null)
				historicoAttribute = (String) request.getSession().getAttribute("historicoInstrumento");
				if(historicoAttribute.equals("true"))
					isHistorico = true;
			else
				historicoAttribute = null;								

			if (estatusStAttribute != null && !estatusStAttribute.equals("")) {
				estatus = Byte.valueOf(estatusStAttribute);
			}
																
			if ((nombreAttribute != null) && nombreAttribute != "") {
				filtros.put("nombreCorto", nombreAttribute);
			}
			if ((anioAttribute != null) && anioAttribute != "") {
				filtros.put("anio", anioAttribute);
			}
			if ((estatus != null)) {
				if ((estatus !=0))
					filtros.put("estatus", estatus);
			}
			if ((tiposConvenioIdAttribute != null)
					&& !tiposConvenioIdAttribute.equals("0")) {
				filtros.put("tiposConvenioId", tiposConvenioIdAttribute.toString());
			}
			if ((cooperanteIdAttribute != null) && (!cooperanteIdAttribute.equals("0"))) {
				filtros.put("cooperanteId", cooperanteIdAttribute.toString());
			}
			if((isHistorico != null) && isHistorico == false) {
				filtros.put("isHistorico", "0");
			}else if((isHistorico != null) && isHistorico == true)
				filtros.put("isHistorico", "1");
			
			if (request.getParameter("limpiar") != null) {
				if (request.getParameter("limpiar").equals("1"))
					filtros.clear();
			}
						
			List<Instrumentos> instrumentos = strategosInstrumentosService.getInstrumentos(0, 0, "nombreCorto", "ASC", true, filtros).getLista();

			if (instrumentos.size() > 0)
			{
				for (Iterator<Instrumentos> iter = instrumentos.iterator(); iter.hasNext();)
				{
					Instrumentos instrumento = iter.next();

					documento.add(lineaEnBlanco(fuente));

					Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

				    //Nombre de la Organizacion, plan y periodo del reporte
					font.setSize(10);
					font.setStyle(Font.BOLD);

					texto = new Paragraph("Instrumento: "+ instrumento.getNombreCorto(), font);
					texto.setAlignment(Element.ALIGN_LEFT);
					texto.setIndentationLeft(16);
					documento.add(texto);

					generarTabla1(request, documento, instrumento);
					generarTabla2(request, documento, instrumento);
					generarTabla3(request, documento, instrumento);
					generarTabla4(request, documento, instrumento);
				}
			}
		}


		documento.newPage();

		strategosInstrumentosService.close();

	}

	public String obtenerEstatus(Byte estatus) {

		String nombre = "";

		switch(estatus) {
			case 1:
				nombre="Sin Iniciar";
				break;
			case 2:
				nombre="En Ejecucion";
				break;
			case 3:
				nombre="Cancelado";
				break;
			case 4:
				nombre="Suspendido";
				break;
			case 5:
				nombre="Culminado";
				break;
		}

		return nombre;
	}

	public void generarTabla1(HttpServletRequest request, Document documento, Instrumentos instrumento) throws Exception {

		Font fuente = getConfiguracionPagina(request).getFuente();
        MessageResources messageResources = getResources(request);

        TablaPDF tabla = null;
        tabla = new TablaPDF(getConfiguracionPagina(request), request);
        int[] columnas = new int[4];

        columnas[0] = 20;
        columnas[1] = 20;
        columnas[2] = 20;
        columnas[3] = 20;


        tabla.setAmplitudTabla(100);
        tabla.crearTabla(columnas);

        documento.add(lineaEnBlanco(fuente));


        tabla.setAlineacionHorizontal(1);

        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.descripcion"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.objetivo"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.productos"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.marco"));

        tabla.setDefaultAlineacionHorizontal();

        tabla.setAlineacionHorizontal(0);

        tabla.agregarCelda(instrumento.getNombreInstrumento());
        tabla.agregarCelda(instrumento.getObjetivoInstrumento());
        tabla.agregarCelda(instrumento.getProductos());
        tabla.agregarCelda(instrumento.getInstrumentoMarco());

        documento.add(tabla.getTabla());

	}

	public void generarTabla2(HttpServletRequest request, Document documento, Instrumentos instrumento) throws Exception {

		StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();
		StrategosTiposConvenioService strategosConveniosService = StrategosServiceFactory.getInstance().openStrategosTiposConvenioService();

		Font fuente = getConfiguracionPagina(request).getFuente();
        MessageResources messageResources = getResources(request);

        TablaPDF tabla = null;
        tabla = new TablaPDF(getConfiguracionPagina(request), request);
        int[] columnas = new int[6];
        columnas[0] = 25;
        columnas[1] = 25;
        columnas[2] = 10;
        columnas[3] = 15;
        columnas[4] = 15;
        columnas[5] = 15;

        tabla.setAmplitudTabla(100);
        tabla.crearTabla(columnas);

        documento.add(lineaEnBlanco(fuente));



        tabla.setAlineacionHorizontal(1);

        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.tipo"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.cooperante"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.anio"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.fecha"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.fecha.terminacion"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.fecha.prorroga"));

        tabla.setDefaultAlineacionHorizontal();

        tabla.setAlineacionHorizontal(0);

        if(instrumento.getTiposConvenioId() != null) {
			TipoConvenio tipoConvenio = (TipoConvenio)strategosConveniosService.load(TipoConvenio.class, new Long(instrumento.getTiposConvenioId()));
			if(tipoConvenio != null) {
				tabla.agregarCelda(tipoConvenio.getDescripcion());
			}

		}else {
			tabla.agregarCelda("");
		}

        if(instrumento.getCooperanteId() != null) {
			Cooperante cooperante = (Cooperante)strategosCooperantesService.load(Cooperante.class, new Long(instrumento.getCooperanteId()));
			if(cooperante != null) {
				tabla.agregarCelda(cooperante.getNombre());
			}

		}else {
			tabla.agregarCelda("");
		}

        tabla.agregarCelda(instrumento.getAnio());

        if(instrumento.getFechaInicio() != null) {
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			tabla.agregarCelda(format.format(instrumento.getFechaInicio()));
		}else {
			tabla.agregarCelda("");
		}

        if(instrumento.getFechaTerminacion() != null) {
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			tabla.agregarCelda(format.format(instrumento.getFechaTerminacion()));
		}else {
			tabla.agregarCelda("");
		}

        if(instrumento.getFechaProrroga() != null) {
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			tabla.agregarCelda(format.format(instrumento.getFechaProrroga()));
		}else {
			tabla.agregarCelda("");
		}

        documento.add(tabla.getTabla());
	}

	public void generarTabla3(HttpServletRequest request, Document documento, Instrumentos instrumento) throws Exception {

		Font fuente = getConfiguracionPagina(request).getFuente();
        MessageResources messageResources = getResources(request);

        TablaPDF tabla = null;
        tabla = new TablaPDF(getConfiguracionPagina(request), request);
        int[] columnas = new int[4];
        columnas[0] = 15;
        columnas[1] = 15;
        columnas[2] = 15;
        columnas[3] = 30;



        tabla.setAmplitudTabla(100);
        tabla.crearTabla(columnas);

        documento.add(lineaEnBlanco(fuente));



        tabla.setAlineacionHorizontal(1);

        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.estatus"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.recursos.pesos"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.recursos.dolares"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.nombre.ejecutante"));


        tabla.setDefaultAlineacionHorizontal();

        tabla.agregarCelda(obtenerEstatus(instrumento.getEstatus()));
        tabla.agregarCelda(instrumento.getRecursosPesos().toString());
        tabla.agregarCelda(instrumento.getRecursosDolares().toString());
        tabla.agregarCelda(instrumento.getNombreEjecutante());

        documento.add(tabla.getTabla());
	}

	public void generarTabla4(HttpServletRequest request, Document documento, Instrumentos instrumento) throws Exception {

		Font fuente = getConfiguracionPagina(request).getFuente();
        MessageResources messageResources = getResources(request);

        TablaPDF tabla = null;
        tabla = new TablaPDF(getConfiguracionPagina(request), request);
        int[] columnas = new int[4];
        columnas[0] = 25;
        columnas[1] = 25;
        columnas[2] = 25;
        columnas[3] = 25;



        tabla.setAmplitudTabla(100);
        tabla.crearTabla(columnas);

        documento.add(lineaEnBlanco(fuente));



        tabla.setAlineacionHorizontal(1);

        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.responsable.pgn"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.responsable.cgi"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.unidad"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.observaciones"));


        tabla.setDefaultAlineacionHorizontal();

        tabla.agregarCelda(instrumento.getNombreReposnsableAreas());
        tabla.agregarCelda(instrumento.getResponsableCgi());
        tabla.agregarCelda(instrumento.getAreasCargo());
        tabla.agregarCelda(instrumento.getObservaciones());

        documento.add(tabla.getTabla());
	}
}



