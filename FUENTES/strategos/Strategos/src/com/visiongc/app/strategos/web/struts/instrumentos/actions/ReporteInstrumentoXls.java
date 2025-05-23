package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.StrategosTiposConvenioService;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.instrumentos.model.TipoConvenio;
import com.visiongc.app.strategos.model.util.LapsoTiempo;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryProyectosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.FiltroForm;

public class ReporteInstrumentoXls extends VgcAction{

	@Override
	public void updateNavigationBar(NavigationBar navBar, String url,
			String nombre) {

		/**
		 * Se agrega el url porque este es un nivel de navegaci�n v�lido
		 */

		navBar.agregarUrl(url, nombre);
	}


	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {

		/** Se ejecuta el servicio del Action padre (obligatorio!!!) */
	super.execute(mapping, form, request, response);

			String forward = mapping.getParameter();

			MessageResources mensajes = getResources(request);
			ReporteForm reporte = new ReporteForm();
			reporte.clear();
			String source = request.getParameter("source");

			String anio = request.getParameter("anio") != null ? request.getParameter("anio") : "";
			if(anio != "") {
				reporte.setAno(new Integer(anio));
			}else {
				reporte.setAno(null);
			}

			Calendar fecha = Calendar.getInstance();
	        Integer anoTemp = fecha.get(Calendar.YEAR);
	        int mes = fecha.get(Calendar.MONTH) + 1;

			reporte.setAnoInicial(anoTemp.toString());
			reporte.setAnoFinal(anoTemp.toString());
			reporte.setMesInicial("1");
			reporte.setMesFinal("12");


			reporte.setCooperanteId(request.getParameter("cooperante") != null && !request.getParameter("cooperante").toString().equals("") ? Long.parseLong(request.getParameter("cooperante")) : null);
			reporte.setTipoCooperanteId(request.getParameter("tipoconvenio") != null && !request.getParameter("tipoconvenio").toString().equals("") ? Long.parseLong(request.getParameter("tipoconvenio")) : null);
			reporte.setNombre(request.getParameter("nombre") != null ? request.getParameter("nombre") : "");
			reporte.setId(request.getParameter("instrumentoId") != null && !request.getParameter("instrumentoId").toString().equals("") ? Long.parseLong(request.getParameter("instrumentoId")) : null);

			String alcance = (request.getParameter("alcance"));

			String instrumentoId = (request.getParameter("instrumentoId"));




			Map<String, Object> filtros = new HashMap<String, Object>();
			Paragraph texto;

			StrategosIniciativasService iniciativaservice = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
			StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();

			MessageResources messageResources = getResources(request);

			//instrumento seleccionado
			if(request.getParameter("alcance").equals("1")){

				int columna = 1;

				StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();
				StrategosTiposConvenioService strategosConveniosService = StrategosServiceFactory.getInstance().openStrategosTiposConvenioService();

				String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";
				Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();

				FiltroForm filtro = new FiltroForm();
				filtro.setHistorico(selectHitoricoType);
				if (filtroNombre.equals(""))
					filtro.setNombre(null);
				else
					filtro.setNombre(filtroNombre);
				reporte.setFiltro(filtro);

				Instrumentos instrumento = (Instrumentos)strategosInstrumentosService.load(Instrumentos.class, reporte.getId());
				HSSFWorkbook workbook = new HSSFWorkbook();
		        HSSFSheet sheet = workbook.createSheet();
		        workbook.setSheetName(0, "Hoja excel");


				CellStyle headerStyle = workbook.createCellStyle();
		        Font font = workbook.createFont();
		        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		        headerStyle.setFont(font);

		        CellStyle style = workbook.createCellStyle();
		        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		        style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		        HSSFRow headerRow = sheet.createRow(columna++);

		        String header = "Reporte Instrumentos Detallado";
		        HSSFCell cell = headerRow.createCell(1);
		        cell.setCellStyle(headerStyle);
		        cell.setCellValue(header);

		        sheet.createRow(columna++);


		        HSSFRow dataRow = sheet.createRow(columna++);

		        dataRow.createCell(0).setCellValue(messageResources.getMessage("jsp.reportes.instrumento.titulo"));
		        dataRow.createCell(1).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.tipo"));
		        dataRow.createCell(2).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.cooperante"));
		        dataRow.createCell(3).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.fecha"));
		        dataRow.createCell(4).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.fecha.terminacion"));
		        dataRow.createCell(5).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.descripcion"));
		        dataRow.createCell(6).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.estatus"));


		        HSSFRow datapRow = sheet.createRow(columna++);

		        datapRow.createCell(0).setCellValue(instrumento.getNombreCorto());

		    	if(instrumento.getTiposConvenioId() != null) {
					TipoConvenio tipoConvenio = (TipoConvenio)strategosConveniosService.load(TipoConvenio.class, new Long(instrumento.getTiposConvenioId()));
					if(tipoConvenio != null) {
						datapRow.createCell(1).setCellValue(tipoConvenio.getNombre());
					}

				}else {
					datapRow.createCell(1).setCellValue("");
				}

		        if(instrumento.getCooperanteId() != null) {
					Cooperante cooperante = (Cooperante)strategosCooperantesService.load(Cooperante.class, new Long(instrumento.getCooperanteId()));
					if(cooperante != null) {
						datapRow.createCell(2).setCellValue(cooperante.getNombre());
					}

				}else {
					datapRow.createCell(2).setCellValue("");
				}


		        if(instrumento.getFechaInicio() != null) {
					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
					datapRow.createCell(3).setCellValue(format.format(instrumento.getFechaInicio()));
				}else {
					datapRow.createCell(3).setCellValue("");
				}

		        if(instrumento.getFechaTerminacion() != null) {
					SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
					datapRow.createCell(4).setCellValue(format.format(instrumento.getFechaTerminacion()));
				}else {
					datapRow.createCell(4).setCellValue("");
				}


		        datapRow.createCell(5).setCellValue(instrumento.getNombreInstrumento());
		        datapRow.createCell(6).setCellValue(obtenerEstatus(instrumento.getEstatus()));

		        sheet.createRow(columna++);

		        int pagina = 0;
			    String atributoOrden = null;
			    String tipoOrden = null;

			    if (atributoOrden == null)
			    	atributoOrden = "nombreCorto";
			    if (tipoOrden == null)
			    	tipoOrden = "ASC";
			    if (pagina < 1)
			    	pagina = 1;

		        //iniciativa

		        StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		        StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
				StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();


	    		filtros = new HashMap();

	    		filtros.put("instrumentoId", instrumento.getInstrumentoId().toString());

	    		PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(pagina, 30, null, tipoOrden, true, filtros);

	    		if (paginaIniciativas.getLista().size() > 0)
	    		{
	    			for (Iterator<Iniciativa> iter = paginaIniciativas.getLista().iterator(); iter.hasNext();)
	    			{
	    				Iniciativa iniciativa = iter.next();

	    				//organizacion


	    				Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
	    				List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), 1, 12);
	    				List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), 1, 12);

	    				PryProyecto proyecto = null;
	    				if (iniciativa.getProyectoId() != null)
	    				{
	    					StrategosPryProyectosService strategosPryProyectosService = StrategosServiceFactory.getInstance().openStrategosPryProyectosService();
	    					proyecto = (PryProyecto) strategosPryProyectosService.load(PryProyecto.class, iniciativa.getProyectoId());
	    					strategosPryProyectosService.close();
	    				}


	    				HSSFRow dataIni = sheet.createRow(columna++);

	    				dataIni.createCell(0).setCellValue(messageResources.getMessage("jsp.asignarpesosactividad.iniciativa"));
	    			    dataIni.createCell(1).setCellValue(messageResources.getMessage("jsp.asignarpesosactividad.organizacion"));
	    			    dataIni.createCell(2).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.unidad"));
	    			    dataIni.createCell(3).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio"));
	    			    dataIni.createCell(4).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion"));
	    			    dataIni.createCell(5).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.avance"));
	    			    dataIni.createCell(6).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.programado"));
	    			    dataIni.createCell(7).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.responsable"));

	    			   HSSFRow datapIni = sheet.createRow(columna++);

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

	    				// datos iniciativa
	    				datapIni.createCell(0).setCellValue(iniciativa.getNombre());
	    				datapIni.createCell(1).setCellValue(iniciativa.getOrganizacion().getNombre());

	    				if (indicador.getUnidadId() != null && indicador.getUnidadId() != 0L)
	    					datapIni.createCell(2).setCellValue(indicador.getUnidad().getNombre());
	    			    else
	    			    	datapIni.createCell(2).setCellValue("");

	    				datapIni.createCell(3).setCellValue(proyecto != null && proyecto.getComienzoPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getComienzoPlan(),"formato.fecha.corta")): "");
	    				datapIni.createCell(4).setCellValue(proyecto != null && proyecto.getFinPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getFinPlan(), "formato.fecha.corta")): "");
	    				datapIni.createCell(5).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
	    				datapIni.createCell(6).setCellValue(VgcFormatter.formatearNumero(programado));

	    				if(iniciativa.getResponsableSeguimiento() != null)
	    					datapIni.createCell(7).setCellValue(iniciativa.getResponsableSeguimiento().getNombre() !=null ? iniciativa.getResponsableSeguimiento().getNombre() : "");
	    				else
	    					datapIni.createCell(7).setCellValue("");

	    			   sheet.createRow(columna++);

	    			 //actividades

	    			   StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
	    				StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();


	    			   filtros = new HashMap<String, Object>();
	    				filtros.put("proyectoId", iniciativa.getProyectoId());
	    				List<PryActividad> actividades = strategosPryActividadesService.getActividades(0, 0, "fila", "ASC", true, filtros).getLista();

	    				if (actividades.size() > 0)
	    				{
	    					HSSFRow dataAct = sheet.createRow(columna++);


		    			    dataAct.createCell(0).setCellValue(messageResources.getMessage("jsp.asignarpesosactividad.actividad"));
		    			    dataAct.createCell(1).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio"));
		    			    dataAct.createCell(2).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion"));
		    			    dataAct.createCell(3).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.peso"));
		    			    dataAct.createCell(4).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.duracion"));
		    			    dataAct.createCell(5).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.ejecutado"));
		    			    dataAct.createCell(6).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.programado"));


	    					for (Iterator<PryActividad> itera = actividades.iterator(); itera.hasNext();)
	    					{
	    						PryActividad actividad = itera.next();
	    						Indicador indicadora = (Indicador)strategosIndicadoresService.load(Indicador.class, actividad.getIndicadorId());

	    				    	LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(((Integer)(Integer.parseInt(reporte.getAnoInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getAnoFinal()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesFinal()))).intValue(), indicadora.getFrecuencia().byteValue());
	    				    	int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
	    				    	int periodoFin = periodoInicio;
	    				    	if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
	    				    		periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();

	    						List<Medicion> medicionesEjecutadoa = strategosMedicionesService.getMedicionesPeriodo(indicadora.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);
	    						List<Medicion> medicionesProgramadoa = strategosMedicionesService.getMedicionesPeriodo(indicadora.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);

	    						HSSFRow datapAct = sheet.createRow(columna++);

	    						datapAct.createCell(0).setCellValue(actividad.getNombre() != null ? actividad.getNombre() : "");
	    						datapAct.createCell(1).setCellValue(VgcFormatter.formatearFecha(actividad.getComienzoPlan(),"formato.fecha.corta"));
	    						datapAct.createCell(2).setCellValue(VgcFormatter.formatearFecha(actividad.getFinPlan(), "formato.fecha.corta"));
	    						datapAct.createCell(3).setCellValue(VgcFormatter.formatearNumero(actividad.getPeso()));
	    						datapAct.createCell(4).setCellValue(VgcFormatter.formatearNumero(actividad.getDuracionPlan()));
	    						datapAct.createCell(5).setCellValue(actividad.getPorcentajeEjecutado() != null ? actividad.getPorcentajeEjecutadoFormateado() : "");
	    						datapAct.createCell(6).setCellValue(actividad.getPorcentajeEsperado() != null ? actividad.getPorcentajeEsperadoFormateado() : "");


	    					}

	    					sheet.createRow(columna++);
	    				}


	    			}
	    		}




		        Date date = new Date();
		        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");


		        String archivo="InstrumentoDetallado_"+hourdateFormat.format(date)+".xls";

		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition","attachment;filename="+archivo);

		        ServletOutputStream file  = response.getOutputStream();

		        workbook.write(file);
		        file.close();


				forward="exito";
		        organizacionservice.close();
		        iniciativaservice.close();
		        strategosMedicionesService.close();
		        strategosIndicadoresService.close();
		        /** C�digo de l�gica de Negocio del action	*/

				/** Otherwise, return "success" */
				return mapping.findForward(forward);

			}
			else {

				int columna = 1;

				HSSFWorkbook workbook = new HSSFWorkbook();
		        HSSFSheet sheet = workbook.createSheet();
		        workbook.setSheetName(0, "Hoja excel");


				CellStyle headerStyle = workbook.createCellStyle();
		        Font font = workbook.createFont();
		        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		        headerStyle.setFont(font);

		        CellStyle style = workbook.createCellStyle();
		        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		        style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		        HSSFRow headerRow = sheet.createRow(columna++);

		        String header = "Reporte Instrumentos Detallado";
		        HSSFCell cell = headerRow.createCell(1);
		        cell.setCellStyle(headerStyle);
		        cell.setCellValue(header);

				strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
				StrategosTiposConvenioService strategosConvenioService = StrategosServiceFactory.getInstance().openStrategosTiposConvenioService();
				StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();

				Map<String, String> filtrosInstru = new HashMap<String, String>();
			    int pagina = 0;
			    String atributoOrden = null;
			    String tipoOrden = null;

			    if (atributoOrden == null)
			    	atributoOrden = "nombreCorto";
			    if (tipoOrden == null)
			    	tipoOrden = "ASC";
			    if (pagina < 1)
			    	pagina = 1;

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
						filtros.put("estatus", estatus.toString());
				}
				if ((tiposConvenioIdAttribute != null)
						&& tiposConvenioIdAttribute != 0) {
					filtros.put("tiposConvenioId", tiposConvenioIdAttribute.toString());
				}
				if ((cooperanteIdAttribute != null) && (cooperanteIdAttribute != 0)) {
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


			    PaginaLista paginaInstrumentos = strategosInstrumentosService.getInstrumentos(pagina, 30, atributoOrden, tipoOrden, true, filtrosInstru);

			    if (paginaInstrumentos.getLista().size() > 0)
				{

			    	for (Iterator<Instrumentos> iter = paginaInstrumentos.getLista().iterator(); iter.hasNext(); )
					{
			    		Instrumentos instrumento = iter.next();



				        sheet.createRow(columna++);


				        HSSFRow dataRow = sheet.createRow(columna++);

				        dataRow.createCell(0).setCellValue(messageResources.getMessage("jsp.reportes.instrumento.titulo"));
				        dataRow.createCell(1).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.tipo"));
				        dataRow.createCell(2).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.cooperante"));
				        dataRow.createCell(3).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.fecha"));
				        dataRow.createCell(4).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.fecha.terminacion"));
				        dataRow.createCell(5).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.descripcion"));
				        dataRow.createCell(6).setCellValue(messageResources.getMessage("jsp.pagina.instrumentos.estatus"));


				        HSSFRow datapRow = sheet.createRow(columna++);

				        datapRow.createCell(0).setCellValue(instrumento.getNombreCorto());

				    	if(instrumento.getTiposConvenioId() != null) {
							TipoConvenio tipoConvenio = (TipoConvenio)strategosConvenioService.load(TipoConvenio.class, new Long(instrumento.getTiposConvenioId()));
							if(tipoConvenio != null) {
								datapRow.createCell(1).setCellValue(tipoConvenio.getNombre());
							}

						}else {
							datapRow.createCell(1).setCellValue("");
						}

				        if(instrumento.getCooperanteId() != null) {
							Cooperante cooperante = (Cooperante)strategosCooperantesService.load(Cooperante.class, new Long(instrumento.getCooperanteId()));
							if(cooperante != null) {
								datapRow.createCell(2).setCellValue(cooperante.getNombre());
							}

						}else {
							datapRow.createCell(2).setCellValue("");
						}


				        if(instrumento.getFechaInicio() != null) {
							SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
							datapRow.createCell(3).setCellValue(format.format(instrumento.getFechaInicio()));
						}else {
							datapRow.createCell(3).setCellValue("");
						}

				        if(instrumento.getFechaTerminacion() != null) {
							SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
							datapRow.createCell(4).setCellValue(format.format(instrumento.getFechaTerminacion()));
						}else {
							datapRow.createCell(4).setCellValue("");
						}


				        datapRow.createCell(5).setCellValue(instrumento.getNombreInstrumento());
				        datapRow.createCell(6).setCellValue(obtenerEstatus(instrumento.getEstatus()));

				        sheet.createRow(columna++);

				        pagina = 0;
					    atributoOrden = null;
					    tipoOrden = null;

					    if (atributoOrden == null)
					    	atributoOrden = "nombreCorto";
					    if (tipoOrden == null)
					    	tipoOrden = "ASC";
					    if (pagina < 1)
					    	pagina = 1;

				        //iniciativa

				        StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
				        StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
						StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();


			    		filtros = new HashMap();

			    		filtros.put("instrumentoId", instrumento.getInstrumentoId().toString());

			    		PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(pagina, 30, null, tipoOrden, true, filtros);

			    		if (paginaIniciativas.getLista().size() > 0)
			    		{
			    			for (Iterator<Iniciativa> iteri = paginaIniciativas.getLista().iterator(); iteri.hasNext();)
			    			{
			    				Iniciativa iniciativa = iteri.next();

			    				//organizacion


			    				Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
			    				List<Medicion> medicionesEjecutado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), 1, 12);
			    				List<Medicion> medicionesProgramado = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), 1, 12);

			    				PryProyecto proyecto = null;
			    				if (iniciativa.getProyectoId() != null)
			    				{
			    					StrategosPryProyectosService strategosPryProyectosService = StrategosServiceFactory.getInstance().openStrategosPryProyectosService();
			    					proyecto = (PryProyecto) strategosPryProyectosService.load(PryProyecto.class, iniciativa.getProyectoId());
			    					strategosPryProyectosService.close();
			    				}


			    				HSSFRow dataIni = sheet.createRow(columna++);

			    				dataIni.createCell(0).setCellValue(messageResources.getMessage("jsp.asignarpesosactividad.iniciativa"));
			    			    dataIni.createCell(1).setCellValue(messageResources.getMessage("jsp.asignarpesosactividad.organizacion"));
			    			    dataIni.createCell(2).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.unidad"));
			    			    dataIni.createCell(3).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio"));
			    			    dataIni.createCell(4).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion"));
			    			    dataIni.createCell(5).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.avance"));
			    			    dataIni.createCell(6).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.programado"));
			    			    dataIni.createCell(7).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.responsable"));

			    			   HSSFRow datapIni = sheet.createRow(columna++);

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

			    				// datos iniciativa
			    				datapIni.createCell(0).setCellValue(iniciativa.getNombre());
			    				datapIni.createCell(1).setCellValue(iniciativa.getOrganizacion().getNombre());

			    				if (indicador.getUnidadId() != null && indicador.getUnidadId() != 0L)
			    					datapIni.createCell(2).setCellValue(indicador.getUnidad().getNombre());
			    			    else
			    			    	datapIni.createCell(2).setCellValue("");

			    				datapIni.createCell(3).setCellValue(proyecto != null && proyecto.getComienzoPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getComienzoPlan(),"formato.fecha.corta")): "");
			    				datapIni.createCell(4).setCellValue(proyecto != null && proyecto.getFinPlan() != null ? (VgcFormatter.formatearFecha(proyecto.getFinPlan(), "formato.fecha.corta")): "");
			    				datapIni.createCell(5).setCellValue(iniciativa.getPorcentajeCompletadoFormateado());
			    				datapIni.createCell(6).setCellValue(VgcFormatter.formatearNumero(programado));

			    				if(iniciativa.getResponsableSeguimiento() != null)
			    					datapIni.createCell(7).setCellValue(iniciativa.getResponsableSeguimiento().getNombre() !=null ? iniciativa.getResponsableSeguimiento().getNombre() : "");
			    				else
			    					datapIni.createCell(7).setCellValue("");

			    			   sheet.createRow(columna++);

			    			 //actividades

			    			   StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
			    				StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();


			    			   filtros = new HashMap<String, Object>();
			    				filtros.put("proyectoId", iniciativa.getProyectoId());
			    				List<PryActividad> actividades = strategosPryActividadesService.getActividades(0, 0, "fila", "ASC", true, filtros).getLista();

			    				if (actividades.size() > 0)
			    				{
			    					HSSFRow dataAct = sheet.createRow(columna++);


				    			    dataAct.createCell(0).setCellValue(messageResources.getMessage("jsp.asignarpesosactividad.actividad"));
				    			    dataAct.createCell(1).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.inicio"));
				    			    dataAct.createCell(2).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.culminacion"));
				    			    dataAct.createCell(3).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.peso"));
				    			    dataAct.createCell(4).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.duracion"));
				    			    dataAct.createCell(5).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.ejecutado"));
				    			    dataAct.createCell(6).setCellValue(messageResources.getMessage("jsp.reportes.plan.ejecucion.reporte.titulo.porcentaje.programado"));


			    					for (Iterator<PryActividad> itera = actividades.iterator(); itera.hasNext();)
			    					{
			    						PryActividad actividad = itera.next();
			    						Indicador indicadora = (Indicador)strategosIndicadoresService.load(Indicador.class, actividad.getIndicadorId());

			    				    	LapsoTiempo lapsoTiempoEnPeriodos = PeriodoUtil.getLapsoTiempoEnPeriodosPorMes(((Integer)(Integer.parseInt(reporte.getAnoInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getAnoFinal()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesInicial()))).intValue(), ((Integer)(Integer.parseInt(reporte.getMesFinal()))).intValue(), indicadora.getFrecuencia().byteValue());
			    				    	int periodoInicio = lapsoTiempoEnPeriodos.getPeriodoInicio().intValue();
			    				    	int periodoFin = periodoInicio;
			    				    	if (lapsoTiempoEnPeriodos.getPeriodoFin() != null)
			    				    		periodoFin = lapsoTiempoEnPeriodos.getPeriodoFin().intValue();

			    						List<Medicion> medicionesEjecutadoa = strategosMedicionesService.getMedicionesPeriodo(indicadora.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);
			    						List<Medicion> medicionesProgramadoa = strategosMedicionesService.getMedicionesPeriodo(indicadora.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), new Integer(reporte.getAnoInicial()), new Integer(reporte.getAnoFinal()), periodoInicio, periodoFin);

			    						HSSFRow datapAct = sheet.createRow(columna++);

			    						datapAct.createCell(0).setCellValue(actividad.getNombre() != null ? actividad.getNombre() : "");
			    						datapAct.createCell(1).setCellValue(VgcFormatter.formatearFecha(actividad.getComienzoPlan(),"formato.fecha.corta"));
			    						datapAct.createCell(2).setCellValue(VgcFormatter.formatearFecha(actividad.getFinPlan(), "formato.fecha.corta"));
			    						datapAct.createCell(3).setCellValue(VgcFormatter.formatearNumero(actividad.getPeso()));
			    						datapAct.createCell(4).setCellValue(VgcFormatter.formatearNumero(actividad.getDuracionPlan()));
			    						datapAct.createCell(5).setCellValue(actividad.getPorcentajeEjecutado() != null ? actividad.getPorcentajeEjecutadoFormateado() : "");
			    						datapAct.createCell(6).setCellValue(actividad.getPorcentajeEsperado() != null ? actividad.getPorcentajeEsperadoFormateado() : "");


			    					}

			    					sheet.createRow(columna++);
			    				}


			    			}
			    		}

					}

				}


				Date date = new Date();
		        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");


		        String archivo="InstrumentoDetallado_"+hourdateFormat.format(date)+".xls";

		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition","attachment;filename="+archivo);

		        ServletOutputStream file  = response.getOutputStream();

		        workbook.write(file);
		        file.close();


				forward="exito";
		        organizacionservice.close();
		        iniciativaservice.close();
		        /** C�digo de l�gica de Negocio del action	*/

				/** Otherwise, return "success" */
				return mapping.findForward(forward);


			}

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
