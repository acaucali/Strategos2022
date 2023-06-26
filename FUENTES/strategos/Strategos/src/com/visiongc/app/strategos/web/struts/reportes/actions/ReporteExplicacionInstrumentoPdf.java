package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.util.Calendar;
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
import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.MemoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.util.TipoMemoExplicacion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.report.Tabla;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;

public class ReporteExplicacionInstrumentoPdf extends VgcReporteBasicoAction {
	private static Session sesion;
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 57;
	private int maxLineasAntesTabla = 4;

	@Override
	protected String agregarTitulo(HttpServletRequest request,	MessageResources mensajes) throws Exception
	{
		return mensajes.getMessage("jsp.reportes.explicacion.instrumento");
	}



	@Override
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
	{

		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();

		String instrumentoId = (request.getParameter("instrumentoId"));


		String atributoOrden = "";

		int pagina = 0;

		if (pagina < 1)
			pagina = 1;


		Map<String, Comparable> filtros = new HashMap<String, Comparable>();

		Paragraph texto;
		int columna = 1;

		documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));

		Calendar fecha = Calendar.getInstance();
        int anoTemp = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;


		Font fuente = getConfiguracionPagina(request).getFuente();
        MessageResources messageResources = getResources(request);

        StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
        StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();


		if ((instrumentoId != null) && (!instrumentoId.equals("")) && Long.parseLong(instrumentoId) != 0)
			filtros.put("objetoId", instrumentoId);

		List<Explicacion> explicaciones = strategosExplicacionesService.getExplicaciones(pagina, 30, "fecha", "DESC", true, filtros).getLista();

		if(explicaciones != null && explicaciones.size() > 0) {

			Instrumentos instrumentos = (Instrumentos)strategosInstrumentosService.load(Instrumentos.class, new Long(instrumentoId));

			Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

		    //Nombre de instrumento
			font.setSize(8);
			font.setStyle(Font.BOLD);


			texto = new Paragraph("Instrumento: "+ instrumentos.getNombreCorto(), font);
			texto.setAlignment(Element.ALIGN_LEFT);
			texto.setIndentationLeft(16);
			documento.add(texto);


			documento.add(lineaEnBlanco(fuente));


			TablaPDF tabla = null;
	        tabla = new TablaPDF(getConfiguracionPagina(request), request);
	        int[] columnas = new int[7];


	        columnas[0] = 15;
	        columnas[1] = 10;
	        columnas[2] = 20;
	        columnas[3] = 7;
	        columnas[4] = 25;
	        columnas[5] = 20;
	        columnas[6] = 20;

	        tabla.setAmplitudTabla(100);
	        tabla.crearTabla(columnas);

	        tabla.setAlineacionHorizontal(0);

	        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarexplicaciones.columna.autor"));
	        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarexplicaciones.columna.fecha"));
	        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarexplicaciones.columna.titulo"));
	        tabla.agregarCelda(messageResources.getMessage("jsp.editarexplicacion.ficha.publicar"));
	        tabla.agregarCelda(messageResources.getMessage("jsp.editarexplicacion.ficha.adjuntos"));
	        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.avance"));
	        tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.observacion"));


	        tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);
		    tabla.setAlineacionVertical(Tabla.V_ALINEACION_TOP);
			//

		    for(Explicacion exp: explicaciones) {

		    	if(exp.getUsuarioCreado() != null) {
		    		tabla.agregarCelda(exp.getUsuarioCreado().getFullName());
		    	}else {
		    		tabla.agregarCelda("");
		    	}


		    	if(exp.getFechaFormateada() != null) {
		        	tabla.agregarCelda(exp.getFechaFormateada());
		        }else {
		        	tabla.agregarCelda("");
		        }

		        tabla.agregarCelda(exp.getTitulo());

		    	if(exp.getPublico()) {
		        	tabla.agregarCelda("Si");
		        }else {
		        	tabla.agregarCelda("No");
		        }

		    	String cadena = "";

		    	if (exp.getAdjuntosExplicacion() != null)
				{

		    		for (Iterator<?> iter = exp.getAdjuntosExplicacion().iterator(); iter.hasNext(); )
					{
						AdjuntoExplicacion adjunto = (AdjuntoExplicacion)iter.next();
						cadena += " " + adjunto.getTitulo() + ",";
					}

		    		cadena = cadena.substring(0, cadena.length()-1);
				}

		    	tabla.agregarCelda(cadena);

		    	String memoDescripcion="";
		    	String memoCausas="";

		    	for (Iterator<?> i = exp.getMemosExplicacion().iterator(); i.hasNext(); )
				{
					MemoExplicacion memoExplicacion = (MemoExplicacion)i.next();
					Byte tipoMemo = memoExplicacion.getPk().getTipo();
					if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_DESCRIPCION)))
						memoDescripcion=memoExplicacion.getMemo();
					else if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_CAUSAS)))
						memoCausas=memoExplicacion.getMemo();

				}

		    	tabla.agregarCelda(memoDescripcion);
		        tabla.agregarCelda(memoCausas);

		        documento.add(lineaEnBlanco(fuente));
		    }

		    documento.add(tabla.getTabla());
			documento.add(lineaEnBlanco(fuente));

		}





		documento.newPage();
		strategosExplicacionesService.close();
        strategosInstrumentosService.close();

	}


}


