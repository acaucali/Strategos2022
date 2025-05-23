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
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryProyectosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.portafolios.model.PortafolioIniciativa;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.report.Tabla;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.VgcFormatter;

public class ReportePortafolioResumidoPdf extends VgcReporteBasicoAction {
	private static Session sesion;
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 57;
	private int maxLineasAntesTabla = 4;

	@Override
	protected String agregarTitulo(HttpServletRequest request,	MessageResources mensajes) throws Exception
	{
		return mensajes.getMessage("jsp.reportes.portafolio.ejecucion.resumido");
	}



	@Override
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
	{

		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		String alcance = (request.getParameter("alcance"));
		String orgId = (request.getParameter("organizacionId"));


		Map<String, Object> filtros = new HashMap<String, Object>();
		Paragraph texto;
		int columna = 1;

		documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));

		Calendar fecha = Calendar.getInstance();
        int anoTemp = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;


		Font fuente = getConfiguracionPagina(request).getFuente();
        MessageResources messageResources = getResources(request);

		StrategosIniciativasService iniciativaservice = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
		StrategosPortafoliosService portafolioservice = StrategosServiceFactory.getInstance().openStrategosPortafoliosService();


		Map<String, String> filtro = new HashMap<String, String>();


		// organizacion seleccionada
		if(request.getParameter("alcance").equals("1")){

			filtro.put("organizacionId", ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId().toString());

			List<Portafolio> portafolios = portafolioservice.getPortafolios(0, 0, "nombre", "ASC", true, filtro).getLista();

			for(Portafolio por: portafolios) {

				Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

			    //Nombre de la Organizacion, plan y periodo del reporte
				font.setSize(8);
				font.setStyle(Font.BOLD);


				texto = new Paragraph("Portafolio: "+ por.getNombre(), font);
				texto.setAlignment(Element.ALIGN_LEFT);
				texto.setIndentationLeft(16);
				documento.add(texto);


				documento.add(lineaEnBlanco(fuente));

				TablaPDF tabla = null;
		        tabla = new TablaPDF(getConfiguracionPagina(request), request);
		        int[] columnas = new int[5];


		        columnas[0] = 20;
		        columnas[1] = 4;
		        columnas[2] = 3;
		        columnas[3] = 4;
		        columnas[4] = 4;

		        tabla.setAmplitudTabla(100);
		        tabla.crearTabla(columnas);

		        tabla.setAlineacionHorizontal(0);

		        tabla.agregarCelda(messageResources.getMessage("action.reportecomiteejecutivo.organizacion"));
		        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarportafolio.columna.ultimoperiodocalculo"));
		        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarportafolio.columna.porcentajecompletado"));
		        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarportafolio.columna.estatus"));
		        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarportafolio.columna.frecuencia"));



		        tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);
			    tabla.setAlineacionVertical(Tabla.V_ALINEACION_TOP);
				//

		        if(por.getOrganizacion() != null) {
		        	tabla.agregarCelda(por.getOrganizacion().getNombre());
		        }else {
		        	tabla.agregarCelda("");
		        }


		        tabla.agregarCelda(por.getFechaUltimoCalculo());

		        if(por.getPorcentajeCompletadoFormateado() != null) {
		        	tabla.agregarCelda(por.getPorcentajeCompletadoFormateado());
		        }else {
		        	tabla.agregarCelda("");
		        }

		        if(por.getEstatus().getNombre() != null) {
		        	tabla.agregarCelda(por.getEstatus().getNombre());
		        }else {
		        	tabla.agregarCelda("");
		        }

		        if(por.getFrecuenciaNombre() != null) {
		        	tabla.agregarCelda(por.getFrecuenciaNombre());
		        }else {
		        	tabla.agregarCelda("");
		        }


		        documento.add(tabla.getTabla());

		        //

		        font = new Font(getConfiguracionPagina(request).getCodigoFuente());

			    //Nombre de la Organizacion, plan y periodo del reporte
				font.setSize(8);
				font.setStyle(Font.BOLD);


				documento.add(lineaEnBlanco(fuente));

				List<PortafolioIniciativa> iniciativasPortafolio = portafolioservice.getIniciativasPortafolio(por.getId());


				for(PortafolioIniciativa iniX: iniciativasPortafolio) {

					//iniciativa

					Long iniciativaId = iniX.getPk().getIniciativaId();
					PryProyecto proyecto = null;

					Iniciativa iniciativa = (Iniciativa)iniciativaservice.load(Iniciativa.class, iniciativaId);

					StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
					StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();

					Indicador indicador = (Indicador)iniciativaservice.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

					List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
					List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);

					if (iniciativa.getProyectoId() != null)
					{
						StrategosPryProyectosService strategosPryProyectosService = StrategosServiceFactory.getInstance().openStrategosPryProyectosService();
						proyecto = (PryProyecto) strategosPryProyectosService.load(PryProyecto.class, iniciativa.getProyectoId());
						strategosPryProyectosService.close();
					}

					texto = new Paragraph("Iniciativa: "+ iniciativa.getNombre(), font);
					texto.setAlignment(Element.ALIGN_LEFT);
					texto.setIndentationLeft(16);
					documento.add(texto);

					documento.add(lineaEnBlanco(fuente));

					TablaPDF tablaIni = null;
			        tablaIni = new TablaPDF(getConfiguracionPagina(request), request);
			        int[] columnasIni = new int[6];


			        columnasIni[0] = 5;
			        columnasIni[1] = 10;
			        columnasIni[2] = 10;
			        columnasIni[3] = 8;
			        columnasIni[4] = 8;
			        columnasIni[5] = 25;

			        tablaIni.setAmplitudTabla(100);
			        tablaIni.crearTabla(columnasIni);

			        tablaIni.setAlineacionHorizontal(1);

			        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.unidad"));
			        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio"));
			        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion"));
			        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.avance"));
			        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.programado"));
			        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.responsable"));



			        tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);
				    tabla.setAlineacionVertical(Tabla.V_ALINEACION_TOP);

			        Double programado = 0D;
				    double porcentajeEsperado = 0D;
				    for (Iterator<Medicion> iterEjecutado = medicionesEjecutado.iterator(); iterEjecutado.hasNext();)
				    {
				    	Medicion ejecutado = iterEjecutado.next();
						for (Iterator<Medicion> iterMeta = medicionesProgramado.iterator(); iterMeta.hasNext();)
						{
							Medicion meta = iterMeta.next();
							if (ejecutado.getMedicionId().getAno().intValue() == meta.getMedicionId().getAno().intValue() &&
									ejecutado.getMedicionId().getPeriodo().intValue() == meta.getMedicionId().getPeriodo().intValue())
							{
								if (meta.getValor() != null)
									programado = programado + meta.getValor();
								break;
							}
						}
				    }
					if (programado.doubleValue() != 0)
						porcentajeEsperado = (porcentajeEsperado * 100D) / 100D;

					if (indicador.getUnidadId() != null && indicador.getUnidadId() != 0L)
						tablaIni.agregarCelda(indicador.getUnidad().getNombre());
				    else
				    	tablaIni.agregarCelda("");

					tablaIni.agregarCelda(proyecto != null && proyecto.getComienzoPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getComienzoPlan(),"formato.fecha.corta")): "");
					tablaIni.agregarCelda(proyecto != null && proyecto.getFinPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getFinPlan(), "formato.fecha.corta")): "");

					tablaIni.agregarCelda(iniciativa.getPorcentajeCompletadoFormateado());
					tablaIni.agregarCelda(VgcFormatter.formatearNumero(programado));

					if(iniciativa.getResponsableSeguimiento() !=null) {
						tablaIni.agregarCelda(iniciativa.getResponsableSeguimiento().getNombre());
					}else {
						tablaIni.agregarCelda("");
					}


					documento.add(tablaIni.getTabla());

					documento.add(lineaEnBlanco(fuente));



				}



			}


		}
		// todas las organizaciones
		else{

			filtro.put("activo", "1");

			List<Portafolio> portafolios = portafolioservice.getPortafolios(0, 0, "nombre", "ASC", true, filtro).getLista();

			for(Portafolio por: portafolios) {

				Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

			    //Nombre de la Organizacion, plan y periodo del reporte
				font.setSize(8);
				font.setStyle(Font.BOLD);


				texto = new Paragraph("Portafolio: "+ por.getNombre(), font);
				texto.setAlignment(Element.ALIGN_LEFT);
				texto.setIndentationLeft(16);
				documento.add(texto);


				documento.add(lineaEnBlanco(fuente));

				TablaPDF tabla = null;
		        tabla = new TablaPDF(getConfiguracionPagina(request), request);
		        int[] columnas = new int[5];


		        columnas[0] = 20;
		        columnas[1] = 4;
		        columnas[2] = 3;
		        columnas[3] = 4;
		        columnas[4] = 4;

		        tabla.setAmplitudTabla(100);
		        tabla.crearTabla(columnas);

		        tabla.setAlineacionHorizontal(0);

		        tabla.agregarCelda(messageResources.getMessage("action.reportecomiteejecutivo.organizacion"));
		        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarportafolio.columna.ultimoperiodocalculo"));
		        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarportafolio.columna.porcentajecompletado"));
		        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarportafolio.columna.estatus"));
		        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarportafolio.columna.frecuencia"));



		        tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);
			    tabla.setAlineacionVertical(Tabla.V_ALINEACION_TOP);
				//

		        if(por.getOrganizacion() != null) {
		        	tabla.agregarCelda(por.getOrganizacion().getNombre());
		        }else {
		        	tabla.agregarCelda("");
		        }


		        tabla.agregarCelda(por.getFechaUltimoCalculo());

		        if(por.getPorcentajeCompletadoFormateado() != null) {
		        	tabla.agregarCelda(por.getPorcentajeCompletadoFormateado());
		        }else {
		        	tabla.agregarCelda("");
		        }

		        if(por.getEstatus().getNombre() != null) {
		        	tabla.agregarCelda(por.getEstatus().getNombre());
		        }else {
		        	tabla.agregarCelda("");
		        }

		        if(por.getFrecuenciaNombre() != null) {
		        	tabla.agregarCelda(por.getFrecuenciaNombre());
		        }else {
		        	tabla.agregarCelda("");
		        }


		        documento.add(tabla.getTabla());

		        //

		        font = new Font(getConfiguracionPagina(request).getCodigoFuente());

			    //Nombre de la Organizacion, plan y periodo del reporte
				font.setSize(8);
				font.setStyle(Font.BOLD);


				documento.add(lineaEnBlanco(fuente));

				List<PortafolioIniciativa> iniciativasPortafolio = portafolioservice.getIniciativasPortafolio(por.getId());


				for(PortafolioIniciativa iniX: iniciativasPortafolio) {

					//iniciativa

					Long iniciativaId = iniX.getPk().getIniciativaId();
					PryProyecto proyecto = null;

					Iniciativa iniciativa = (Iniciativa)iniciativaservice.load(Iniciativa.class, iniciativaId);

					StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
					StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();

					Indicador indicador = (Indicador)iniciativaservice.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

					List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
					List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);

					if (iniciativa.getProyectoId() != null)
					{
						StrategosPryProyectosService strategosPryProyectosService = StrategosServiceFactory.getInstance().openStrategosPryProyectosService();
						proyecto = (PryProyecto) strategosPryProyectosService.load(PryProyecto.class, iniciativa.getProyectoId());
						strategosPryProyectosService.close();
					}

					texto = new Paragraph("Iniciativa: "+ iniciativa.getNombre(), font);
					texto.setAlignment(Element.ALIGN_LEFT);
					texto.setIndentationLeft(16);
					documento.add(texto);

					documento.add(lineaEnBlanco(fuente));

					TablaPDF tablaIni = null;
			        tablaIni = new TablaPDF(getConfiguracionPagina(request), request);
			        int[] columnasIni = new int[6];


			        columnasIni[0] = 5;
			        columnasIni[1] = 10;
			        columnasIni[2] = 10;
			        columnasIni[3] = 8;
			        columnasIni[4] = 8;
			        columnasIni[5] = 25;

			        tablaIni.setAmplitudTabla(100);
			        tablaIni.crearTabla(columnasIni);

			        tablaIni.setAlineacionHorizontal(1);

			        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.unidad"));
			        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio"));
			        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion"));
			        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.avance"));
			        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.programado"));
			        tablaIni.agregarCelda(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.responsable"));



			        tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);
				    tabla.setAlineacionVertical(Tabla.V_ALINEACION_TOP);

			        Double programado = 0D;
				    double porcentajeEsperado = 0D;
				    for (Iterator<Medicion> iterEjecutado = medicionesEjecutado.iterator(); iterEjecutado.hasNext();)
				    {
				    	Medicion ejecutado = iterEjecutado.next();
						for (Iterator<Medicion> iterMeta = medicionesProgramado.iterator(); iterMeta.hasNext();)
						{
							Medicion meta = iterMeta.next();
							if (ejecutado.getMedicionId().getAno().intValue() == meta.getMedicionId().getAno().intValue() &&
									ejecutado.getMedicionId().getPeriodo().intValue() == meta.getMedicionId().getPeriodo().intValue())
							{
								if (meta.getValor() != null)
									programado = programado + meta.getValor();
								break;
							}
						}
				    }
					if (programado.doubleValue() != 0)
						porcentajeEsperado = (porcentajeEsperado * 100D) / 100D;

					if (indicador.getUnidadId() != null && indicador.getUnidadId() != 0L)
						tablaIni.agregarCelda(indicador.getUnidad().getNombre());
				    else
				    	tablaIni.agregarCelda("");

					tablaIni.agregarCelda(proyecto != null && proyecto.getComienzoPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getComienzoPlan(),"formato.fecha.corta")): "");
					tablaIni.agregarCelda(proyecto != null && proyecto.getFinPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getFinPlan(), "formato.fecha.corta")): "");

					tablaIni.agregarCelda(iniciativa.getPorcentajeCompletadoFormateado());
					tablaIni.agregarCelda(VgcFormatter.formatearNumero(programado));

					if(iniciativa.getResponsableSeguimiento() !=null) {
						tablaIni.agregarCelda(iniciativa.getResponsableSeguimiento().getNombre());
					}else {
						tablaIni.agregarCelda("");
					}


					documento.add(tablaIni.getTabla());

					documento.add(lineaEnBlanco(fuente));



				}

			}

		}


		documento.newPage();
        organizacionservice.close();
        iniciativaservice.close();

	}


}


