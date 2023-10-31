package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;
import org.hibernate.Session;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.MemoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.util.TipoMemoExplicacion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCelda;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.report.Tabla;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.VgcResourceManager;

public class ReporteExplicacionesPdf extends VgcReporteBasicoAction {
	private static Session sesion;
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 57;
	private int maxLineasAntesTabla = 4;

	@Override
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception {
		return mensajes.getMessage("jsp.reportes.explicaciones.resumido");
	}

	@Override
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response,
			Document documento) throws Exception {
		MessageResources messageResources = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();

		Date fechaDesdeParse =  new Date();
		Date fechaHastaParse =  new Date();
		
		String fechaDesde = (request.getParameter("fechaDesde"));		
		if(fechaDesde != null && !fechaDesde.equals("")) {
			fechaDesdeParse = (FechaUtil.convertirStringToDate(fechaDesde, VgcResourceManager.getResourceApp("formato.fecha.corta")));
		}
		
		String fechaHasta = (request.getParameter("fechaHasta"));
		if(fechaHasta != null && !fechaHasta.equals("")) {			
			fechaHastaParse = (FechaUtil.convertirStringToDate(fechaHasta, VgcResourceManager.getResourceApp("formato.fecha.corta")));
		}
		
		String todos = (request.getParameter("todos"));

		String objetoId = (request.getParameter("objetoId"));
		
		String objetoKey = (request.getParameter("objetoKey"));
		
		

		String atributoOrden = "";

		int pagina = 0;

		if (pagina < 1)
			pagina = 1;

		Map<String, Comparable> filtros = new HashMap<String, Comparable>();

		Paragraph texto;
		int columna = 1;

		documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));

		Font fuente = getConfiguracionPagina(request).getFuente();

		if ((objetoId != null) && (!objetoId.equals("")) && Long.parseLong(objetoId) != 0)
			filtros.put("objetoId", objetoId);

		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance()
				.openStrategosExplicacionesService();
		
		if (objetoKey.equals("Indicador"))
		{
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(objetoId));
			reporte.setSource(indicador.getNombre());
			strategosIndicadoresService.close();
		}
		else if (objetoKey.equals("Celda"))
		{
			StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
			Celda celda = (Celda)strategosCeldasService.load(Celda.class, new Long(objetoId));

			String nombreObjetoKey = "";
			if (celda.getIndicadoresCelda() != null)
			{
				if ((celda.getIndicadoresCelda().size() == 0) || (celda.getIndicadoresCelda().size() > 1))
					nombreObjetoKey = celda.getTitulo();
				else if (celda.getIndicadoresCelda().size() == 1)
				{
					IndicadorCelda indicadorCelda = (IndicadorCelda)celda.getIndicadoresCelda().toArray()[0];
					StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
					Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorCelda.getIndicador().getIndicadorId());
					nombreObjetoKey = indicador.getNombre();
					strategosIndicadoresService.close();
				}
			}
			else nombreObjetoKey = celda.getTitulo();
			reporte.setSource(nombreObjetoKey);

			strategosCeldasService.close();
		}
		else  if (objetoKey.equals("Iniciativa"))
		{
			StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			Iniciativa iniciativa = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, new Long(objetoId));

			reporte.setSource(iniciativa.getNombre());

			strategosIniciativasService.close();
		}
		else if (objetoKey.equals("Instrumento"))
		{
			StrategosInstrumentosService strategosInstrumentoService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
			Instrumentos instrumento = (Instrumentos)strategosInstrumentoService.load(Instrumentos.class, new Long(objetoId));
			
			reporte.setSource(instrumento.getNombreCorto());
			
			strategosInstrumentoService.close();			
		}
		else if (objetoKey.equals("Actividad"))
		{
			StrategosPryActividadesService strategosActividadService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
			
			PryActividad actividad = (PryActividad)strategosActividadService.load(PryActividad.class, new Long(objetoId));
			
			reporte.setSource(actividad.getNombre());
			
			strategosActividadService.close();
		}
		
		OrganizacionStrategos organizacion = ((OrganizacionStrategos)request.getSession().getAttribute("organizacion"));
		reporte.setOrganizacionNombre(organizacion.getNombre());
		

		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());
		
		 //Nombre de la Organizacion, plan y periodo del reporte
		font.setSize(8);
		font.setStyle(Font.NORMAL);
		fontBold.setSize(8);
		fontBold.setStyle(Font.BOLD);

		documento.add(lineaEnBlanco(font));
		lineas = getNumeroLinea(lineas, inicioLineas);
		String subTituloOrg = "Organización" +  " - " +  reporte.getOrganizacionNombre();
		agregarSubTitulo(documento, getConfiguracionPagina(request), subTituloOrg, true, true, 13.0F);
		documento.add(lineaEnBlanco(font));
		
		String subTitulo = messageResources.getMessage("jsp.reporte.explicacion.objeto") +  " - " + objetoKey + " : " + reporte.getSource();
		agregarSubTitulo(documento, getConfiguracionPagina(request), subTitulo, true, true, 13.0F);
		lineas = getNumeroLinea(lineas, inicioLineas);

		documento.add(lineaEnBlanco(font));
		lineas = getNumeroLinea(lineas, inicioLineas);
		
		List<Explicacion> explicaciones = strategosExplicacionesService
				.getExplicaciones(pagina, 30, "fecha", "DESC", true, filtros).getLista();
		List<Explicacion> expReporte = new ArrayList<>();

		if (explicaciones != null && explicaciones.size() > 0) {
			for (Explicacion exp : explicaciones) {
				if(!todos.equals("true")) {
					if(fechaDesdeParse.compareTo(exp.getFecha()) <= 0 && fechaHastaParse.compareTo(exp.getFecha()) >= 0) {
				    	expReporte.add(exp);
					}			
				}else {
					expReporte.add(exp);
				}
			}
		}

		fuente.setSize(12);		
		fuente.setStyle(Font.COURIER);
		TablaPDF tabla = null;
		tabla = new TablaPDF(getConfiguracionPagina(request), request);
		int[] columnas = new int[5];

		columnas[0] = 8;
		columnas[1] = 20;
		columnas[2] = 25;
		columnas[3] = 20;		
		columnas[4] = 20;

		
		
		tabla.crearTabla(columnas);
		tabla.setTamanoFont(12);
		tabla.setFont(Font.TIMES_ROMAN);		
		tabla.setAmplitudTabla(100);

		tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);
		tabla.setColorFondo(21, 60, 120);
		tabla.setColorLetra(255, 255, 255);

		tabla.agregarCelda(messageResources.getMessage("jsp.gestionarexplicaciones.columna.fecha"));
		tabla.agregarCelda(messageResources.getMessage("jsp.gestionarexplicaciones.columna.titulo"));
		tabla.agregarCelda(messageResources.getMessage("jsp.editarexplicacion.ficha.tipomemo.descripcion"));
		tabla.agregarCelda(messageResources.getMessage("jsp.editarexplicacion.ficha.tipomemo.causas"));
		tabla.agregarCelda(messageResources.getMessage("jsp.editarexplicacion.ficha.tipomemo.correctivos"));

		tabla.setAlineacionHorizontal(0);
		tabla.setAlineacionVertical(Tabla.V_ALINEACION_TOP);
		tabla.setTamanoFont(8);
		tabla.setColorFondo(255, 255, 255);
		tabla.setColorLetra(0, 0, 0);
		//
		if (expReporte != null && expReporte.size() > 0) {
			for (Explicacion exp : expReporte) {
				if (exp.getFechaFormateada() != null) {
					tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);
					tabla.agregarCelda(exp.getFechaFormateada());
				} else {
					tabla.agregarCelda("");
				}

				tabla.setAlineacionHorizontal(0);
				tabla.agregarCelda(exp.getTitulo());

				String memoDescripcion = "";
				String memoCausas = "";
				String memoCorrectivos = "";

				for (Iterator<?> i = exp.getMemosExplicacion().iterator(); i.hasNext();) {
					MemoExplicacion memoExplicacion = (MemoExplicacion) i.next();
					Byte tipoMemo = memoExplicacion.getPk().getTipo();
					if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_DESCRIPCION)))
						memoDescripcion = memoExplicacion.getMemo();
					else if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_CAUSAS)))
						memoCausas = memoExplicacion.getMemo();
					else if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_CORRECTIVOS)))
						memoCorrectivos = memoExplicacion.getMemo();
				}

				tabla.agregarCelda(memoDescripcion);
				tabla.agregarCelda(memoCausas);
				tabla.agregarCelda(memoCorrectivos);

				documento.add(lineaEnBlanco(fuente));
			}
		}

		documento.add(tabla.getTabla());
		documento.add(lineaEnBlanco(fuente));

		strategosExplicacionesService.close();
	}

}
