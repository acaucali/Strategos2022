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

public class ReporteInstrumentoPdf extends VgcReporteBasicoAction {
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


		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;

		StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
		StrategosTiposConvenioService strategosConveniosService = StrategosServiceFactory.getInstance().openStrategosTiposConvenioService();
		StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();


		String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";

		FiltroForm filtro = new FiltroForm();

		if (filtroNombre.equals(""))
			filtro.setNombre(null);
		else
			filtro.setNombre(filtroNombre);
		reporte.setFiltro(filtro);


    	Font fuente = getConfiguracionPagina(request).getFuente();
        MessageResources messageResources = getResources(request);

        TablaPDF tabla = null;
        tabla = new TablaPDF(getConfiguracionPagina(request), request);
        int[] columnas = new int[6];
        columnas[0] = 21;
        columnas[1] = 20;
        columnas[2] = 20;
        columnas[3] = 10;
        columnas[4] = 20;
        columnas[5] = 12;


        tabla.setAmplitudTabla(100);
        tabla.crearTabla(columnas);

        documento.add(lineaEnBlanco(fuente));

        List<Instrumentos> instrumentos = strategosInstrumentosService.getInstrumentos(0, 0, "nombreCorto", "ASC", true, filtros).getLista();

        tabla.setAlineacionHorizontal(1);
        tabla.setColorFondo(21, 60, 120);
        tabla.setColorLetra(255, 255, 255);
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.nombre"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.tipo"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.cooperante"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.fecha"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.descripcion"));
        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.estatus"));


        tabla.setDefaultAlineacionHorizontal();
        tabla.setColorFondo(255,255,255);
        tabla.setColorLetra(0, 0, 0);
        if (instrumentos.size() > 0)
		{
			for (Iterator<Instrumentos> iter = instrumentos.iterator(); iter.hasNext();)
			{
				Instrumentos instrumento = iter.next();
				tabla.setAlineacionHorizontal(0);
				tabla.agregarCelda(instrumento.getNombreCorto());
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


				if(instrumento.getFechaInicio() != null) {
					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
					tabla.agregarCelda(format.format(instrumento.getFechaInicio()));
				}else {

				}

				tabla.agregarCelda(instrumento.getNombreInstrumento());

				tabla.agregarCelda(obtenerEstatus(instrumento.getEstatus()));

			}
			documento.add(tabla.getTabla());
		}else{
			documento.add(tabla.getTabla());
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
}



