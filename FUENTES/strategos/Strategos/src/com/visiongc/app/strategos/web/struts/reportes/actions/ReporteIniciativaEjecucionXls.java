package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
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
import com.visiongc.app.strategos.planes.model.IniciativaPerspectiva;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.FiltroForm;

public class ReporteIniciativaEjecucionXls extends VgcAction{

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
			String alcance = (request.getParameter("alcance"));
			String orgId = (request.getParameter("organizacionId"));
			String tipo = (request.getParameter("tipo"));
			String estatus = (request.getParameter("estatus"));
			String ano = (request.getParameter("ano"));
			int estatusId = Integer.parseInt(estatus);
			String todos = (request.getParameter("todos"));

			Calendar fecha = Calendar.getInstance();
	        int anoTemp = fecha.get(Calendar.YEAR);
	        int mes = fecha.get(Calendar.MONTH) + 1;

			Map<String, Object> filtros = new HashMap<String, Object>();
			Paragraph texto;

			StrategosIniciativasService iniciativaservice = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			StrategosOrganizacionesService organizacionservice = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();

			List<OrganizacionStrategos> organizaciones = organizacionservice.getOrganizaciones(0, 0, "organizacionId", "ASC", true, filtros).getLista();


			//
			if(request.getParameter("alcance").equals("1")){

				int x=1;
				int numeroCelda=3;


				String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";
				Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();

				FiltroForm filtro = new FiltroForm();
				filtro.setHistorico(selectHitoricoType);
				if (filtroNombre.equals(""))
					filtro.setNombre(null);
				else
					filtro.setNombre(filtroNombre);
				reporte.setFiltro(filtro);
				filtro.setAnio(""+ano);
			    if (reporte.getAlcance().byteValue() == reporte.getAlcanceObjetivo().byteValue())
			    	filtros.put("organizacionId", ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
				if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
					filtros.put("historicoDate", "IS NULL");
				else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
					filtros.put("historicoDate", "IS NOT NULL");
				if (reporte.getFiltro().getNombre() != null)
					filtros.put("nombre", reporte.getFiltro().getNombre());
				if(!tipo.equals("0")) {
					filtros.put("tipoId", tipo);
				}
				if(todos.equals("false")) {
					filtros.put("anio", ano);
				}

				List<Iniciativa> iniciativas = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();

		        MessageResources messageResources = getResources(request);

		        Object[][] data = new Object[iniciativas.size()+1][13];

		        

		        data[0][0]=messageResources.getMessage("jsp.mostrargestioniniciativa.alerta");
		    	data[0][1]=messageResources.getMessage("action.reporte.estatus.iniciativa.nombre");
		    	data[0][2]=messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.frecuencia");
		    	data[0][3]=messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje.ejecutado");
		    	data[0][4]=messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje.programado");
		    	data[0][5]=messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.fecha.actualizacion");
		    	data[0][6]=messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.fecha.actualizacion.esperada");
		    	data[0][7]=messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.dias");
		    	data[0][8]=messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.estatus");
		    	data[0][9]=messageResources.getMessage("jsp.editariniciativa.ficha.tipoproyecto");
		    	data[0][10]=messageResources.getMessage("jsp.editariniciativa.ficha.anioformulacion");
		    	data[0][11]=messageResources.getMessage("action.reporte.estatus.iniciativa.responsable");
		    	data[0][12]=messageResources.getMessage("action.reporte.estatus.iniciativa.responsable.lograr");



		    	if (iniciativas.size() > 0)
				{
					for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();)
					{
						Iniciativa iniciativa = iter.next();

						StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
						StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
						StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();

						Indicador indicador = (Indicador)strategosIniciativasService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

						List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
						List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);

						List<PryActividad> actividades = new ArrayList<PryActividad>();


						if(iniciativa.getProyectoId() != null){
							actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
						}

						if(estatusId == 8) {
							
							Byte alerta = null;
							
								String url = obtenerCadenaRecurso(request);
								alerta = iniciativa.getAlerta();
								if (alerta == null)
									data[x][0]="";
								else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
									data[x][0]="Roja";
								else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
									data[x][0]="Verde";
								else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
									data[x][0]= "Amarilla";	
								
							data[x][1]=iniciativa.getNombre();

							if(iniciativa.getFrecuenciaNombre() != null) {
								data[x][2]=iniciativa.getFrecuenciaNombre();
							}else {
								data[x][2]=("");
							}

							if (iniciativa.getPorcentajeCompletado() != null) {								
								if (indicador != null) {
									boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal()
											.byteValue()
											&& indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo()
													.byteValue());

									Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(),
											indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(),
											indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
									if (medicionReal != null) {
										data[x][3]=medicionReal.getValor().toString();

										List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(
												indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), null,
												medicionReal.getMedicionId().getAno(), null, medicionReal.getMedicionId().getPeriodo());
										Double programado = null;
										for (Iterator<Medicion> iter2 = mediciones.iterator(); iter2.hasNext();) {
											Medicion medicion = iter2.next();
											if (medicion.getValor() != null && programado == null)
												programado = medicion.getValor();
											else if (medicion.getValor() != null && programado != null && acumular)
												programado = programado + medicion.getValor();
											else if (medicion.getValor() != null && programado != null && !acumular)
												programado = medicion.getValor();
										}

										if (programado != null)
											data[x][4]= programado.toString();
										else 
											data[x][4]=("");
									}
									else
										data[x][3]=("");
								}
							}

							if(iniciativa.getFechaUltimaMedicion() != null){
								data[x][5]=iniciativa.getFechaUltimaMedicion();
							}
							else{
								data[x][5]=("");
							}


							Date fechaAh = new Date();
							Date fechaAc = new Date();


							fechaAh = obtenerFechaFinal(actividades);

							if(fechaAh != null) {

								SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy");

								data[x][6]=objSDF.format(fechaAh);

								int milisecondsByDay = 86400000;

								int dias = (int) ((fechaAh.getTime()-fechaAc.getTime()) / milisecondsByDay);

								int diasposi = dias * -1;

								data[x][7]=""+diasposi;

							}else {
								data[x][6]="";
								data[x][7]="";
							}



							//estatus
							if (medicionesProgramadas.size() == 0) {
								//EstatusIniciar
								data[x][8]=messageResources.getMessage("estado.sin.iniciar");
							}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
								//EstatusIniciardesfasado
								data[x][8]=messageResources.getMessage("estado.sin.iniciar.desfasada");
							}
							else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
								//EnEjecucionSinRetrasos
								data[x][8]=messageResources.getMessage("estado.en.ejecucion.sin.retrasos");
							}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
								//EnEjecucionConRetrasosSuperables
								data[x][8]=messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables");
							}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
								//EnEjecucionConRetrasosSignificativos
								data[x][8]=messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos");
							}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
								//EstatusConcluidas
								data[x][8]=messageResources.getMessage("estado.concluidas");
							}
							else if(iniciativa.getEstatusId() == 3) {
								//EstatusCancelado
								data[x][8]="Cancelado";
							}
							else if(iniciativa.getEstatusId() == 4) {
								//EstatusSuspendido
								data[x][8]="Suspendido";
							}else {
								//EstatusIniciar
								data[x][8]=messageResources.getMessage("estado.sin.iniciar");
							}

							if(iniciativa.getTipoProyecto()==null){
								data[x][9]="";
							}else {
								data[x][9]=iniciativa.getTipoProyecto().getNombre();
							}

							if(iniciativa.getAnioFormulacion() == null) {
								data[x][10]="";
							}else {
								data[x][10]=iniciativa.getAnioFormulacion();
							}


							//responsable ejecutar

							if(iniciativa.getResponsableCargarEjecutado() ==null){
								data[x][11]="";
							}
							else{
								data[x][11]=iniciativa.getResponsableCargarEjecutado().getNombre();
							}

							//responsable lograr meta

							if(iniciativa.getResponsableLograrMeta() ==null){
								data[x][12]="";
							}
							else{
								data[x][12]=iniciativa.getResponsableLograrMeta().getNombre();
							}

							x=x+1;
						}else {
							Boolean est= tieneEstatus(iniciativa, medicionesEjecutadas, medicionesProgramadas, estatusId);

							if(est) {

								Byte alerta = null;
								
								String url = obtenerCadenaRecurso(request);
								alerta = iniciativa.getAlerta();
								if (alerta == null)
									data[x][0]="";
								else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue())
									data[x][0]="Roja";
								else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue())
									data[x][0]="Verde";
								else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue())
									data[x][0]= "Amarilla";
								
								data[x][1]=iniciativa.getNombre();

								if(iniciativa.getFrecuenciaNombre() != null) {
									data[x][2]=iniciativa.getFrecuenciaNombre();
								}else {
									data[x][2]=("");
								}

								if (iniciativa.getPorcentajeCompletado() != null) {								
									if (indicador != null) {
										boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal()
												.byteValue()
												&& indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo()
														.byteValue());

										Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(),
												indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(),
												indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
										if (medicionReal != null) {
											data[x][3]=medicionReal.getValor().toString();

											List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(
													indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), null,
													medicionReal.getMedicionId().getAno(), null, medicionReal.getMedicionId().getPeriodo());
											Double programado = null;
											for (Iterator<Medicion> iter2 = mediciones.iterator(); iter2.hasNext();) {
												Medicion medicion = iter2.next();
												if (medicion.getValor() != null && programado == null)
													programado = medicion.getValor();
												else if (medicion.getValor() != null && programado != null && acumular)
													programado = programado + medicion.getValor();
												else if (medicion.getValor() != null && programado != null && !acumular)
													programado = medicion.getValor();
											}

											if (programado != null)
												data[x][4]= programado.toString();
											else 
												data[x][4]=("");
										}
										else
											data[x][3]=("");
									}
								}

								if(iniciativa.getFechaUltimaMedicion() != null){
									data[x][5]=iniciativa.getFechaUltimaMedicion();
								}
								else{
									data[x][5]=("");
								}


								Date fechaAh = new Date();
								Date fechaAc = new Date();


								fechaAh = obtenerFechaFinal(actividades);

								if(fechaAh != null) {

									SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy");

									data[x][6]=objSDF.format(fechaAh);

									int milisecondsByDay = 86400000;

									int dias = (int) ((fechaAh.getTime()-fechaAc.getTime()) / milisecondsByDay);

									int diasposi = dias * -1;

									data[x][7]=""+diasposi;

								}else {
									data[x][6]="";
									data[x][7]="";
								}



								//estatus
								if (medicionesProgramadas.size() == 0) {
									//EstatusIniciar
									data[x][8]=messageResources.getMessage("estado.sin.iniciar");
								}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
									//EstatusIniciardesfasado
									data[x][8]=messageResources.getMessage("estado.sin.iniciar.desfasada");
								}
								else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
									//EnEjecucionSinRetrasos
									data[x][8]=messageResources.getMessage("estado.en.ejecucion.sin.retrasos");
								}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
									//EnEjecucionConRetrasosSuperables
									data[x][8]=messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables");
								}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
									//EnEjecucionConRetrasosSignificativos
									data[x][8]=messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos");
								}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
									//EstatusConcluidas
									data[x][8]=messageResources.getMessage("estado.concluidas");
								}
								else if(iniciativa.getEstatusId() == 3) {
									//EstatusCancelado
									data[x][8]="Cancelado";
								}
								else if(iniciativa.getEstatusId() == 4) {
									//EstatusSuspendido
									data[x][8]="Suspendido";
								}else {
									//EstatusIniciar
									data[x][8]=messageResources.getMessage("estado.sin.iniciar");
								}

								if(iniciativa.getTipoProyecto()==null){
									data[x][9]="";
								}else {
									data[x][9]=iniciativa.getTipoProyecto().getNombre();
								}

								if(iniciativa.getAnioFormulacion() == null) {
									data[x][10]="";
								}else {
									data[x][10]=iniciativa.getAnioFormulacion();
								}


								//responsable ejecutar

								if(iniciativa.getResponsableCargarEjecutado() ==null){
									data[x][11]="";
								}
								else{
									data[x][11]=iniciativa.getResponsableCargarEjecutado().getNombre();
								}

								//responsable lograr meta

								if(iniciativa.getResponsableLograrMeta() ==null){
									data[x][12]="";
								}
								else{
									data[x][12]=iniciativa.getResponsableLograrMeta().getNombre();
								}

								x=x+1;
							}
						}



					}
				}

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
		        
		        CellStyle styleRoja = workbook.createCellStyle();
		        styleRoja.setFillBackgroundColor(IndexedColors.RED.index);
		        styleRoja.setFillForegroundColor(IndexedColors.RED.index);
		        styleRoja.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		        
		        CellStyle styleVerde = workbook.createCellStyle();
		        styleVerde.setFillBackgroundColor(IndexedColors.GREEN.index);
		        styleVerde.setFillForegroundColor(IndexedColors.GREEN.index);
		        styleVerde.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		        
		        CellStyle styleAmarillo = workbook.createCellStyle();
		        styleAmarillo. setFillBackgroundColor(IndexedColors.YELLOW.index);
		        styleAmarillo.setFillForegroundColor(IndexedColors.YELLOW.index);
		        styleAmarillo.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		        HSSFRow headerRow = sheet.createRow(0);

		        String header = "Reporte Iniciativas Resumido";
		        HSSFCell cell = headerRow.createCell(1);
		        cell.setCellStyle(headerStyle);
		        cell.setCellValue(header);


		        HSSFRow OrgRow = sheet.createRow(2);


		        OrganizacionStrategos org = (OrganizacionStrategos)organizacionservice.load(OrganizacionStrategos.class,  ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());

				if(org != null) {
					 String nombre = "Organizaci�n: "+org.getNombre();
				     HSSFCell cellOrg = OrgRow.createCell(0);
				     cellOrg.setCellStyle(headerStyle);
				     cellOrg.setCellValue(nombre);
				}


		        for (Object[] d : data) {
		            HSSFRow dataRow = sheet.createRow(numeroCelda +1);

		            String alerta = (String) d[0];
		            String iniciativaName = (String) d[1];
		            String frecuencia = (String) d[2];
		            String ejecutado=(String)d[3];
		            String programado = (String) d[4];
		            String actualizacion = (String) d[5];
		            String actesperada =(String) d[6];
		            String dias = (String) d[7];
		            String estatusIn = (String) d[8];
		            String tipoIn = (String) d[9];
		            String anio = (String) d[10];
		            String responsable = (String) d[11];
		            String resplograr = (String) d[12];

		            
		            if(alerta.equals("Alerta"))
						dataRow.createCell(0).setCellValue(alerta);
		            else if(alerta.equals("Roja")) {
		            	dataRow.createCell(0).setCellValue("");
		            	dataRow.createCell(0).setCellStyle(styleRoja);
		            }else if(alerta.equals("Verde")) {
		            	dataRow.createCell(0).setCellValue("");
		            	dataRow.createCell(0).setCellStyle(styleVerde);
		            }else if(alerta.equals("Amarilla")) {
		            	dataRow.createCell(0).setCellValue("");
		            	dataRow.createCell(0).setCellStyle(styleAmarillo);
		            }
		            	
		            dataRow.createCell(1).setCellValue(iniciativaName);
		            dataRow.createCell(2).setCellValue(frecuencia);
		            dataRow.createCell(3).setCellValue(ejecutado);
		            dataRow.createCell(4).setCellValue(programado);
		            dataRow.createCell(5).setCellValue(actualizacion);
		            dataRow.createCell(6).setCellValue(actesperada);
		            dataRow.createCell(7).setCellValue(dias);
		            dataRow.createCell(8).setCellValue(estatusIn);
		            dataRow.createCell(9).setCellValue(tipoIn);
		            dataRow.createCell(10).setCellValue(anio);
		            dataRow.createCell(11).setCellValue(responsable);
		            dataRow.createCell(12).setCellValue(resplograr);

		            numeroCelda=numeroCelda+1;
		        }

		        HSSFRow dataRow = sheet.createRow(numeroCelda+1);

		        Date date = new Date();
		        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");


		        String archivo="IniciativasResumido_"+hourdateFormat.format(date)+".xls";

		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition","attachment;filename="+archivo);

		        ServletOutputStream file  = response.getOutputStream();

		        workbook.write(file);
		        file.close();

			}

			else if(request.getParameter("alcance").equals("4")) {

				Map<String, Object> filtro = new HashMap<String, Object>();

				filtro.put("padreId", ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());

				List<OrganizacionStrategos> organizacionesSub = organizacionservice.getOrganizacionHijas(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId(), true);


				String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";
				Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();

				FiltroForm filtrou = new FiltroForm();
				filtrou.setHistorico(selectHitoricoType);
				if (filtroNombre.equals(""))
					filtrou.setNombre(null);
				else
					filtrou.setNombre(filtroNombre);
				reporte.setFiltro(filtrou);




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
		        
		        CellStyle styleRoja = workbook.createCellStyle();
		        styleRoja.setFillBackgroundColor(IndexedColors.RED.index);
		        styleRoja.setFillForegroundColor(IndexedColors.RED.index);
		        styleRoja.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		        
		        CellStyle styleVerde = workbook.createCellStyle();
		        styleVerde.setFillBackgroundColor(IndexedColors.GREEN.index);
		        styleVerde.setFillForegroundColor(IndexedColors.GREEN.index);
		        styleVerde.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		        
		        CellStyle styleAmarillo = workbook.createCellStyle();
		        styleAmarillo. setFillBackgroundColor(IndexedColors.YELLOW.index);
		        styleAmarillo.setFillForegroundColor(IndexedColors.YELLOW.index);
		        styleAmarillo.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		        
		        HSSFRow headerRow = sheet.createRow(0);

		        String header = "Reporte Iniciativas Resumido";
		        HSSFCell cell = headerRow.createCell(1);
		        cell.setCellStyle(headerStyle);
		        cell.setCellValue(header);



		        filtros.put("organizacionId", ((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
				if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
					filtros.put("historicoDate", "IS NULL");
				else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
					filtros.put("historicoDate", "IS NOT NULL");
				if (reporte.getFiltro().getNombre() != null)
					filtros.put("nombre", reporte.getFiltro().getNombre());
				if (reporte.getFiltro().getNombre() != null)
					filtros.put("nombre", reporte.getFiltro().getNombre());
				if(!tipo.equals("0")) {
					filtros.put("tipoId", tipo);
				}
				if(todos.equals("false")) {
					filtros.put("anio", ano);
				}

		        List<Iniciativa> iniciativas = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();

		        int index = iniciativas.size();

		        MessageResources messageResources = getResources(request);

		        HSSFRow dataRow = sheet.createRow(1);
				dataRow.createCell(0).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.entidad"));
				dataRow.createCell(1).setCellValue(messageResources.getMessage("jsp.mostrargestioniniciativa.alerta"));
	            dataRow.createCell(2).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre"));
	            dataRow.createCell(3).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.frecuencia"));
	            dataRow.createCell(4).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje.ejecutado"));
	            dataRow.createCell(5).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje.programado"));
	            dataRow.createCell(6).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.fecha.actualizacion"));
	            dataRow.createCell(7).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.fecha.actualizacion.esperada"));
	            dataRow.createCell(8).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.dias"));
	            dataRow.createCell(9).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.estatus"));
	            dataRow.createCell(10).setCellValue(messageResources.getMessage("jsp.editariniciativa.ficha.tipoproyecto"));
	            dataRow.createCell(11).setCellValue(messageResources.getMessage("jsp.editariniciativa.ficha.anioformulacion"));
	            dataRow.createCell(12).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.responsable"));
	            dataRow.createCell(13).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.responsable.lograr"));
	            int x=1;

			        if (iniciativas.size() > 0)
					{
						for (Iterator<Iniciativa> iter = iniciativas.iterator(); iter.hasNext();)
						{
							Iniciativa iniciativa = iter.next();

							StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
							StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
							StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();

							Indicador indicador = (Indicador)strategosIniciativasService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

							List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
							List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);

							List<PryActividad> actividades = new ArrayList<PryActividad>();


							if(iniciativa.getProyectoId() != null){
								actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
							}


							if(estatusId == 8) {


								HSSFRow dataRow1 = sheet.createRow(x+1);


								dataRow1.createCell(0).setCellValue(iniciativa.getOrganizacion().getNombre());
								Byte alerta = null;
																
								alerta = iniciativa.getAlerta();
								if (alerta == null)
									dataRow1.createCell(1).setCellValue("");
								else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
									dataRow1.createCell(1).setCellValue("");
									dataRow1.createCell(1).setCellStyle(styleRoja);
								}
								else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue()) {
									dataRow1.createCell(1).setCellValue("");
									dataRow1.createCell(1).setCellStyle(styleVerde);
								}
								else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()){
									dataRow1.createCell(1).setCellValue("");
									dataRow1.createCell(1).setCellStyle(styleAmarillo);
								}
									
								
					            dataRow1.createCell(2).setCellValue(iniciativa.getNombre());

					            if(iniciativa.getFrecuenciaNombre() != null) {
					            	dataRow1.createCell(3).setCellValue(iniciativa.getFrecuenciaNombre());
								}else {
									dataRow1.createCell(3).setCellValue("");
								}



					            if (iniciativa.getPorcentajeCompletado() != null) {								
									if (indicador != null) {
										boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal()
												.byteValue()
												&& indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo()
														.byteValue());

										Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(),
												indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(),
												indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
										if (medicionReal != null) {
											dataRow1.createCell(4).setCellValue(medicionReal.getValor().toString());

											List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(
													indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), null,
													medicionReal.getMedicionId().getAno(), null, medicionReal.getMedicionId().getPeriodo());
											Double programado = null;
											for (Iterator<Medicion> iter2 = mediciones.iterator(); iter2.hasNext();) {
												Medicion medicion = iter2.next();
												if (medicion.getValor() != null && programado == null)
													programado = medicion.getValor();
												else if (medicion.getValor() != null && programado != null && acumular)
													programado = programado + medicion.getValor();
												else if (medicion.getValor() != null && programado != null && !acumular)
													programado = medicion.getValor();
											}

											if (programado != null)
												dataRow1.createCell(5).setCellValue(programado.toString());
											else 
												dataRow1.createCell(5).setCellValue("");
										}
										else
											dataRow1.createCell(4).setCellValue("");
									}
								}



								if(iniciativa.getFechaUltimaMedicion() != null){
									dataRow1.createCell(6).setCellValue(iniciativa.getFechaUltimaMedicion());
								}
								else{
									dataRow1.createCell(6).setCellValue("");
								}


								Date fechaAh = new Date();
								Date fechaAc = new Date();

								fechaAh = obtenerFechaFinal(actividades);

								if(fechaAh != null) {

									SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy");

									String fechaTemp =objSDF.format(fechaAh);


									dataRow1.createCell(7).setCellValue(fechaTemp);

									int milisecondsByDay = 86400000;

									int dias = (int) ((fechaAh.getTime()-fechaAc.getTime()) / milisecondsByDay);

									int diasposi = dias * -1;

									dataRow1.createCell(8).setCellValue(""+diasposi);
								}else {
									dataRow1.createCell(7).setCellValue("");
									dataRow1.createCell(8).setCellValue("");
								}



								//estatus
								if (medicionesProgramadas.size() == 0) {
									//EstatusIniciar
									dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
								}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
									//EstatusIniciardesfasado
									dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar.desfasada"));
								}
								else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
									//EnEjecucionSinRetrasos
									dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.sin.retrasos"));
								}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
									//EnEjecucionConRetrasosSuperables
									dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables"));
								}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
									//EnEjecucionConRetrasosSignificativos
									dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos"));
								}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
									//EstatusConcluidas
									dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.concluidas"));
								}
								else if(iniciativa.getEstatusId() == 3) {
									//EstatusCancelado
									dataRow1.createCell(9).setCellValue("Cancelado");
								}
								else if(iniciativa.getEstatusId() == 4) {
									//EstatusSuspendido
									dataRow1.createCell(9).setCellValue("Suspendido");
								}else {
									//EstatusIniciar
									dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
								}

								if(iniciativa.getTipoProyecto()==null){
									dataRow1.createCell(10).setCellValue("");
								}else {
									dataRow1.createCell(10).setCellValue(iniciativa.getTipoProyecto().getNombre());
								}

								if(iniciativa.getAnioFormulacion() == null) {
									dataRow1.createCell(11).setCellValue("");
								}else {
									dataRow1.createCell(11).setCellValue(iniciativa.getAnioFormulacion());
								}

								//responsable ejecutar

								if(iniciativa.getResponsableCargarEjecutado() ==null){
									dataRow1.createCell(12).setCellValue("");
								}
								else{
									dataRow1.createCell(12).setCellValue(iniciativa.getResponsableCargarEjecutado().getNombre().toString());
								}

								//responsable lograr meta

								if(iniciativa.getResponsableLograrMeta() ==null){
									dataRow1.createCell(13).setCellValue("");
								}
								else{
									dataRow1.createCell(13).setCellValue(iniciativa.getResponsableLograrMeta().getNombre().toString());
								}


								x=x+1;

							}else {

								Boolean est= tieneEstatus(iniciativa, medicionesEjecutadas, medicionesProgramadas, estatusId);

								if(est) {

									String ruta=null;
									HSSFRow dataRow1 = sheet.createRow(x+1);


									dataRow1.createCell(0).setCellValue(iniciativa.getOrganizacion().getNombre());
									
									Byte alerta = null;
									
									alerta = iniciativa.getAlerta();
									if (alerta == null)
										dataRow1.createCell(1).setCellValue("");
									else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
										dataRow1.createCell(1).setCellValue("");
										dataRow1.createCell(1).setCellStyle(styleRoja);
									}
									else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue()) {
										dataRow1.createCell(1).setCellValue("");
										dataRow1.createCell(1).setCellStyle(styleVerde);
									}
									else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()){
										dataRow1.createCell(1).setCellValue("");
										dataRow1.createCell(1).setCellStyle(styleAmarillo);
									}
									
						            dataRow1.createCell(2).setCellValue(iniciativa.getNombre());

						            if(iniciativa.getFrecuenciaNombre() != null) {
						            	dataRow1.createCell(3).setCellValue(iniciativa.getFrecuenciaNombre());
									}else {
										dataRow1.createCell(3).setCellValue("");
									}

						            if (iniciativa.getPorcentajeCompletado() != null) {								
										if (indicador != null) {
											boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal()
													.byteValue()
													&& indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo()
															.byteValue());

											Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(),
													indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(),
													indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
											if (medicionReal != null) {
												dataRow1.createCell(4).setCellValue(medicionReal.getValor().toString());

												List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(
														indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), null,
														medicionReal.getMedicionId().getAno(), null, medicionReal.getMedicionId().getPeriodo());
												Double programado = null;
												for (Iterator<Medicion> iter2 = mediciones.iterator(); iter2.hasNext();) {
													Medicion medicion = iter2.next();
													if (medicion.getValor() != null && programado == null)
														programado = medicion.getValor();
													else if (medicion.getValor() != null && programado != null && acumular)
														programado = programado + medicion.getValor();
													else if (medicion.getValor() != null && programado != null && !acumular)
														programado = medicion.getValor();
												}

												if (programado != null)
													dataRow1.createCell(5).setCellValue(programado.toString());
												else 
													dataRow1.createCell(5).setCellValue("");
											}
											else
												dataRow1.createCell(4).setCellValue("");
										}
									}



									if(iniciativa.getFechaUltimaMedicion() != null){
										dataRow1.createCell(6).setCellValue(iniciativa.getFechaUltimaMedicion());
									}
									else{
										dataRow1.createCell(6).setCellValue("");
									}


									Date fechaAh = new Date();
									Date fechaAc = new Date();

									fechaAh = obtenerFechaFinal(actividades);

									if(fechaAh != null) {

										SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy");

										String fechaTemp =objSDF.format(fechaAh);


										dataRow1.createCell(7).setCellValue(fechaTemp);

										int milisecondsByDay = 86400000;

										int dias = (int) ((fechaAh.getTime()-fechaAc.getTime()) / milisecondsByDay);

										int diasposi = dias * -1;

										dataRow1.createCell(8).setCellValue(""+diasposi);
									}else {
										dataRow1.createCell(7).setCellValue("");
										dataRow1.createCell(8).setCellValue("");
									}



									//estatus
									if (medicionesProgramadas.size() == 0) {
										//EstatusIniciar
										dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
									}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
										//EstatusIniciardesfasado
										dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar.desfasada"));
									}
									else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
										//EnEjecucionSinRetrasos
										dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.sin.retrasos"));
									}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
										//EnEjecucionConRetrasosSuperables
										dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables"));
									}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
										//EnEjecucionConRetrasosSignificativos
										dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos"));
									}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
										//EstatusConcluidas
										dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.concluidas"));
									}
									else if(iniciativa.getEstatusId() == 3) {
										//EstatusCancelado
										dataRow1.createCell(9).setCellValue("Cancelado");
									}
									else if(iniciativa.getEstatusId() == 4) {
										//EstatusSuspendido
										dataRow1.createCell(9).setCellValue("Suspendido");
									}else {
										//EstatusIniciar
										dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
									}

									if(iniciativa.getTipoProyecto()==null){
										dataRow1.createCell(10).setCellValue("");
									}else {
										dataRow1.createCell(10).setCellValue(iniciativa.getTipoProyecto().getNombre());
									}

									if(iniciativa.getAnioFormulacion() == null) {
										dataRow1.createCell(11).setCellValue("");
									}else {
										dataRow1.createCell(11).setCellValue(iniciativa.getAnioFormulacion());
									}

									//responsable ejecutar

									if(iniciativa.getResponsableCargarEjecutado() ==null){
										dataRow1.createCell(12).setCellValue("");
									}
									else{
										dataRow1.createCell(12).setCellValue(iniciativa.getResponsableCargarEjecutado().getNombre().toString());
									}

									//responsable lograr meta

									if(iniciativa.getResponsableLograrMeta() ==null){
										dataRow1.createCell(13).setCellValue("");
									}
									else{
										dataRow1.createCell(13).setCellValue(iniciativa.getResponsableLograrMeta().getNombre().toString());
									}


									x=x+1;

								}


							}


						}
					}


		        if(organizacionesSub.size() > 0 || organizacionesSub != null) {


			    	 for (Iterator<OrganizacionStrategos> iter = organizacionesSub.iterator(); iter.hasNext();)
						{

							OrganizacionStrategos organizacion = iter.next();

						    filtros.put("organizacionId", organizacion.getOrganizacionId().toString());
							if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
								filtros.put("historicoDate", "IS NULL");
							else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
								filtros.put("historicoDate", "IS NOT NULL");
							if (reporte.getFiltro().getNombre() != null)
								filtros.put("nombre", reporte.getFiltro().getNombre());
							if(!tipo.equals("0")) {
								filtros.put("tipoId", tipo);
							}
							if(todos.equals("false")) {
								filtros.put("anio", ano);
							}


					        List<Iniciativa> iniciativasSub = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();




					    	 if (iniciativasSub.size() > 0)
								{


									for (Iterator<Iniciativa> iter1 = iniciativasSub.iterator(); iter1.hasNext();)
									{
										Iniciativa iniciativa = iter1.next();

										StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
										StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
										StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();

										Indicador indicador = (Indicador)strategosIniciativasService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

										List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
										List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);
										List<PryActividad> actividades = new ArrayList<PryActividad>();


										if(iniciativa.getProyectoId() != null){
											actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
										}

										if(estatusId == 8) {

											String ruta=null;
											HSSFRow dataRow1 = sheet.createRow(x+1);
											OrganizacionStrategos org= new OrganizacionStrategos();
											ruta=organizacion.getNombre()+"/";
											org=organizacion.getPadre();
											while(org !=null){
												ruta=org.getNombre()+"/"+ruta;
												if(org.getPadre()==null){
													org = null;
												}
												else{
													org=org.getPadre();
												}
											}


											dataRow1.createCell(0).setCellValue(ruta);
											Byte alerta = null;
											
											alerta = iniciativa.getAlerta();
											if (alerta == null)
												dataRow1.createCell(1).setCellValue("");
											else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
												dataRow1.createCell(1).setCellValue("");
												dataRow1.createCell(1).setCellStyle(styleRoja);
											}
											else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue()) {
												dataRow1.createCell(1).setCellValue("");
												dataRow1.createCell(1).setCellStyle(styleVerde);
											}
											else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()){
												dataRow1.createCell(1).setCellValue("");
												dataRow1.createCell(1).setCellStyle(styleAmarillo);
											}

								            if(iniciativa.getFrecuenciaNombre() != null) {
								            	dataRow1.createCell(3).setCellValue(iniciativa.getFrecuenciaNombre());
											}else {
												dataRow1.createCell(3).setCellValue("");
											}

								            if (iniciativa.getPorcentajeCompletado() != null) {								
												if (indicador != null) {
													boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal()
															.byteValue()
															&& indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo()
																	.byteValue());

													Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(),
															indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(),
															indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
													if (medicionReal != null) {
														dataRow1.createCell(4).setCellValue(medicionReal.getValor().toString());

														List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(
																indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), null,
																medicionReal.getMedicionId().getAno(), null, medicionReal.getMedicionId().getPeriodo());
														Double programado = null;
														for (Iterator<Medicion> iter2 = mediciones.iterator(); iter2.hasNext();) {
															Medicion medicion = iter2.next();
															if (medicion.getValor() != null && programado == null)
																programado = medicion.getValor();
															else if (medicion.getValor() != null && programado != null && acumular)
																programado = programado + medicion.getValor();
															else if (medicion.getValor() != null && programado != null && !acumular)
																programado = medicion.getValor();
														}

														if (programado != null)
															dataRow1.createCell(5).setCellValue(programado.toString());
														else 
															dataRow1.createCell(5).setCellValue("");
													}
													else
														dataRow1.createCell(4).setCellValue("");
												}
											}




											if(iniciativa.getFechaUltimaMedicion() != null){
												dataRow1.createCell(6).setCellValue(iniciativa.getFechaUltimaMedicion());
											}
											else{
												dataRow1.createCell(6).setCellValue("");
											}


											Date fechaAh = new Date();
											Date fechaAc = new Date();

											fechaAh = obtenerFechaFinal(actividades);

											if(fechaAh != null) {

												SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy");

												String fechaTemp =objSDF.format(fechaAh);


												dataRow1.createCell(7).setCellValue(fechaTemp);

												int milisecondsByDay = 86400000;

												int dias = (int) ((fechaAh.getTime()-fechaAc.getTime()) / milisecondsByDay);

												int diasposi = dias * -1;

												dataRow1.createCell(8).setCellValue(""+diasposi);
											}else {
												dataRow1.createCell(7).setCellValue("");
												dataRow1.createCell(8).setCellValue("");
											}



											//estatus
											if (medicionesProgramadas.size() == 0) {
												//EstatusIniciar
												dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
											}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
												//EstatusIniciardesfasado
												dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar.desfasada"));
											}
											else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
												//EnEjecucionSinRetrasos
												dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.sin.retrasos"));
											}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
												//EnEjecucionConRetrasosSuperables
												dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables"));
											}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
												//EnEjecucionConRetrasosSignificativos
												dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos"));
											}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
												//EstatusConcluidas
												dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.concluidas"));
											}
											else if(iniciativa.getEstatusId() == 3) {
												//EstatusCancelado
												dataRow1.createCell(9).setCellValue("Cancelado");
											}
											else if(iniciativa.getEstatusId() == 4) {
												//EstatusSuspendido
												dataRow1.createCell(9).setCellValue("Suspendido");
											}else {
												//EstatusIniciar
												dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
											}

											if(iniciativa.getTipoProyecto()==null){
												dataRow1.createCell(10).setCellValue("");
											}else {
												dataRow1.createCell(10).setCellValue(iniciativa.getTipoProyecto().getNombre());
											}

											if(iniciativa.getAnioFormulacion() == null) {
												dataRow1.createCell(11).setCellValue("");
											}else {
												dataRow1.createCell(11).setCellValue(iniciativa.getAnioFormulacion());
											}

											//responsable ejecutar

											if(iniciativa.getResponsableCargarEjecutado() ==null){
												dataRow1.createCell(12).setCellValue("");
											}
											else{
												dataRow1.createCell(12).setCellValue(iniciativa.getResponsableCargarEjecutado().getNombre().toString());
											}

											//responsable lograr meta

											if(iniciativa.getResponsableLograrMeta() ==null){
												dataRow1.createCell(13).setCellValue("");
											}
											else{
												dataRow1.createCell(13).setCellValue(iniciativa.getResponsableLograrMeta().getNombre().toString());
											}


											x=x+1;

										}else {
											Boolean est= tieneEstatus(iniciativa, medicionesEjecutadas, medicionesProgramadas, estatusId);

											if(est) {

												String ruta=null;
												HSSFRow dataRow1 = sheet.createRow(x+1);
												OrganizacionStrategos org= new OrganizacionStrategos();
												ruta=organizacion.getNombre()+"/";
												org=organizacion.getPadre();
												while(org !=null){
													ruta=org.getNombre()+"/"+ruta;
													if(org.getPadre()==null){
														org = null;
													}
													else{
														org=org.getPadre();
													}
												}


												dataRow1.createCell(0).setCellValue(ruta);
												Byte alerta = null;
												
												alerta = iniciativa.getAlerta();
												if (alerta == null)
													dataRow1.createCell(1).setCellValue("");
												else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
													dataRow1.createCell(1).setCellValue("");
													dataRow1.createCell(1).setCellStyle(styleRoja);
												}
												else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue()) {
													dataRow1.createCell(1).setCellValue("");
													dataRow1.createCell(1).setCellStyle(styleVerde);
												}
												else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()){
													dataRow1.createCell(1).setCellValue("");
													dataRow1.createCell(1).setCellStyle(styleAmarillo);
												}
												
									            dataRow1.createCell(2).setCellValue(iniciativa.getNombre());

									            if(iniciativa.getFrecuenciaNombre() != null) {
									            	dataRow1.createCell(3).setCellValue(iniciativa.getFrecuenciaNombre());
												}else {
													dataRow1.createCell(3).setCellValue("");
												}

									            if (iniciativa.getPorcentajeCompletado() != null) {								
													if (indicador != null) {
														boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal()
																.byteValue()
																&& indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo()
																		.byteValue());

														Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(),
																indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(),
																indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
														if (medicionReal != null) {
															dataRow1.createCell(4).setCellValue(medicionReal.getValor().toString());

															List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(
																	indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), null,
																	medicionReal.getMedicionId().getAno(), null, medicionReal.getMedicionId().getPeriodo());
															Double programado = null;
															for (Iterator<Medicion> iter2 = mediciones.iterator(); iter2.hasNext();) {
																Medicion medicion = iter2.next();
																if (medicion.getValor() != null && programado == null)
																	programado = medicion.getValor();
																else if (medicion.getValor() != null && programado != null && acumular)
																	programado = programado + medicion.getValor();
																else if (medicion.getValor() != null && programado != null && !acumular)
																	programado = medicion.getValor();
															}

															if (programado != null)
																dataRow1.createCell(5).setCellValue(programado.toString());
															else 
																dataRow1.createCell(5).setCellValue("");
														}
														else
															dataRow1.createCell(4).setCellValue("");
													}
												}




												if(iniciativa.getFechaUltimaMedicion() != null){
													dataRow1.createCell(6).setCellValue(iniciativa.getFechaUltimaMedicion());
												}
												else{
													dataRow1.createCell(6).setCellValue("");
												}


												Date fechaAh = new Date();
												Date fechaAc = new Date();

												fechaAh = obtenerFechaFinal(actividades);

												if(fechaAh != null) {

													SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy");

													String fechaTemp =objSDF.format(fechaAh);


													dataRow1.createCell(7).setCellValue(fechaTemp);

													int milisecondsByDay = 86400000;

													int dias = (int) ((fechaAh.getTime()-fechaAc.getTime()) / milisecondsByDay);

													int diasposi = dias * -1;

													dataRow1.createCell(8).setCellValue(""+diasposi);
												}else {
													dataRow1.createCell(7).setCellValue("");
													dataRow1.createCell(8).setCellValue("");
												}



												//estatus
												if (medicionesProgramadas.size() == 0) {
													//EstatusIniciar
													dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
												}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
													//EstatusIniciardesfasado
													dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar.desfasada"));
												}
												else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
													//EnEjecucionSinRetrasos
													dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.sin.retrasos"));
												}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
													//EnEjecucionConRetrasosSuperables
													dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables"));
												}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
													//EnEjecucionConRetrasosSignificativos
													dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos"));
												}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
													//EstatusConcluidas
													dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.concluidas"));
												}
												else if(iniciativa.getEstatusId() == 3) {
													//EstatusCancelado
													dataRow1.createCell(9).setCellValue("Cancelado");
												}
												else if(iniciativa.getEstatusId() == 4) {
													//EstatusSuspendido
													dataRow1.createCell(9).setCellValue("Suspendido");
												}else {
													//EstatusIniciar
													dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
												}

												if(iniciativa.getTipoProyecto()==null){
													dataRow1.createCell(10).setCellValue("");
												}else {
													dataRow1.createCell(10).setCellValue(iniciativa.getTipoProyecto().getNombre());
												}

												if(iniciativa.getAnioFormulacion() == null) {
													dataRow1.createCell(11).setCellValue("");
												}else {
													dataRow1.createCell(11).setCellValue(iniciativa.getAnioFormulacion());
												}

												//responsable ejecutar

												if(iniciativa.getResponsableCargarEjecutado() ==null){
													dataRow1.createCell(12).setCellValue("");
												}
												else{
													dataRow1.createCell(12).setCellValue(iniciativa.getResponsableCargarEjecutado().getNombre().toString());
												}

												//responsable lograr meta

												if(iniciativa.getResponsableLograrMeta() ==null){
													dataRow1.createCell(13).setCellValue("");
												}
												else{
													dataRow1.createCell(13).setCellValue(iniciativa.getResponsableLograrMeta().getNombre().toString());
												}


												x=x+1;

											}
										}


									}


								}

						}
				}


			    Date date = new Date();
		        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");


		        String archivo="IniciativasResumido_"+hourdateFormat.format(date)+".xls";

		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition","attachment;filename="+archivo);

		        ServletOutputStream file  = response.getOutputStream();

		        workbook.write(file);
		        file.close();




			}

			// organizacion seleccionada
			else{
				String filtroNombre = (request.getParameter("filtroNombre") != null) ? request.getParameter("filtroNombre") : "";
				Byte selectHitoricoType = (request.getParameter("selectHitoricoType") != null && request.getParameter("selectHitoricoType") != "") ? Byte.parseByte(request.getParameter("selectHitoricoType")) : HistoricoType.getFiltroHistoricoNoMarcado();

				FiltroForm filtro = new FiltroForm();
				filtro.setHistorico(selectHitoricoType);
				if (filtroNombre.equals(""))
					filtro.setNombre(null);
				else
					filtro.setNombre(filtroNombre);
				reporte.setFiltro(filtro);




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
		        
		        CellStyle styleRoja = workbook.createCellStyle();
		        styleRoja.setFillBackgroundColor(IndexedColors.RED.index);
		        styleRoja.setFillForegroundColor(IndexedColors.RED.index);
		        styleRoja.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		        
		        CellStyle styleVerde = workbook.createCellStyle();
		        styleVerde.setFillBackgroundColor(IndexedColors.GREEN.index);
		        styleVerde.setFillForegroundColor(IndexedColors.GREEN.index);
		        styleVerde.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		        
		        CellStyle styleAmarillo = workbook.createCellStyle();
		        styleAmarillo. setFillBackgroundColor(IndexedColors.YELLOW.index);
		        styleAmarillo.setFillForegroundColor(IndexedColors.YELLOW.index);
		        styleAmarillo.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		        HSSFRow headerRow = sheet.createRow(0);

		        String header = "Reporte Iniciativas Resumido";
		        HSSFCell cell = headerRow.createCell(1);
		        cell.setCellStyle(headerStyle);
		        cell.setCellValue(header);



				if (organizaciones.size() > 0)
				{

					 MessageResources messageResources = getResources(request);


				        HSSFRow dataRow = sheet.createRow(1);
						dataRow.createCell(0).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.entidad"));
						dataRow.createCell(1).setCellValue(messageResources.getMessage("jsp.mostrargestioniniciativa.alerta"));
			            dataRow.createCell(2).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre"));
			            dataRow.createCell(3).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.frecuencia"));
			            dataRow.createCell(4).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje.ejecutado"));
			            dataRow.createCell(5).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.porcentaje.programado"));
			            dataRow.createCell(6).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.fecha.actualizacion"));
			            dataRow.createCell(7).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.fecha.actualizacion.esperada"));
			            dataRow.createCell(8).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.dias"));
			            dataRow.createCell(9).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.nombre.estatus"));
			            dataRow.createCell(10).setCellValue(messageResources.getMessage("jsp.editariniciativa.ficha.tipoproyecto"));
			            dataRow.createCell(11).setCellValue(messageResources.getMessage("jsp.editariniciativa.ficha.anioformulacion"));
			            dataRow.createCell(12).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.responsable"));
			            dataRow.createCell(13).setCellValue(messageResources.getMessage("action.reporte.estatus.iniciativa.responsable.lograr"));
			            int x=1;

			    	 for (Iterator<OrganizacionStrategos> iter = organizaciones.iterator(); iter.hasNext();)
						{

							OrganizacionStrategos organizacion = iter.next();

						    filtros.put("organizacionId", organizacion.getOrganizacionId().toString());
							if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoNoMarcado())
								filtros.put("historicoDate", "IS NULL");
							else if (reporte.getFiltro().getHistorico() != null && reporte.getFiltro().getHistorico().byteValue() == HistoricoType.getFiltroHistoricoMarcado())
								filtros.put("historicoDate", "IS NOT NULL");
							if (reporte.getFiltro().getNombre() != null)
								filtros.put("nombre", reporte.getFiltro().getNombre());
							if(!tipo.equals("0")) {
								filtros.put("tipoId", tipo);
							}
							if(todos.equals("false")) {
								filtros.put("anio", ano);
							}


					        List<Iniciativa> iniciativas = iniciativaservice.getIniciativas(0, 0, "nombre", "ASC", true, filtros).getLista();




					    	 if (iniciativas.size() > 0)
								{


									for (Iterator<Iniciativa> iter1 = iniciativas.iterator(); iter1.hasNext();)
									{
										Iniciativa iniciativa = iter1.next();

										StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
										StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
										StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();

										Indicador indicador = (Indicador)strategosIniciativasService.load(Indicador.class, iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));

										List<Medicion> medicionesEjecutadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), 0000, anoTemp, 000, mes);
										List<Medicion> medicionesProgramadas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), 0000, anoTemp, 000, mes);
										List<PryActividad> actividades = new ArrayList<PryActividad>();


										if(iniciativa.getProyectoId() != null){
											actividades = strategosPryActividadesService.getActividades(iniciativa.getProyectoId());
										}

										if(estatusId == 8) {

											String ruta=null;
											HSSFRow dataRow1 = sheet.createRow(x+1);
											OrganizacionStrategos org= new OrganizacionStrategos();
											ruta=organizacion.getNombre()+"/";
											org=organizacion.getPadre();
											while(org !=null){
												ruta=org.getNombre()+"/"+ruta;
												if(org.getPadre()==null){
													org = null;
												}
												else{
													org=org.getPadre();
												}
											}


											dataRow1.createCell(0).setCellValue(ruta);
											Byte alerta = null;
											
											alerta = iniciativa.getAlerta();
											if (alerta == null)
												dataRow1.createCell(1).setCellValue("");
											else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
												dataRow1.createCell(1).setCellValue("");
												dataRow1.createCell(1).setCellStyle(styleRoja);
											}
											else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue()) {
												dataRow1.createCell(1).setCellValue("");
												dataRow1.createCell(1).setCellStyle(styleVerde);
											}
											else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()){
												dataRow1.createCell(1).setCellValue("");
												dataRow1.createCell(1).setCellStyle(styleAmarillo);
											}
											
								            dataRow1.createCell(2).setCellValue(iniciativa.getNombre());

								            if(iniciativa.getFrecuenciaNombre() != null) {
								            	dataRow1.createCell(3).setCellValue(iniciativa.getFrecuenciaNombre());
											}else {
												dataRow1.createCell(3).setCellValue("");
											}

								            if (iniciativa.getPorcentajeCompletado() != null) {								
												if (indicador != null) {
													boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal()
															.byteValue()
															&& indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo()
																	.byteValue());

													Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(),
															indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(),
															indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
													if (medicionReal != null) {
														dataRow1.createCell(4).setCellValue(medicionReal.getValor().toString());

														List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(
																indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), null,
																medicionReal.getMedicionId().getAno(), null, medicionReal.getMedicionId().getPeriodo());
														Double programado = null;
														for (Iterator<Medicion> iter2 = mediciones.iterator(); iter2.hasNext();) {
															Medicion medicion = iter2.next();
															if (medicion.getValor() != null && programado == null)
																programado = medicion.getValor();
															else if (medicion.getValor() != null && programado != null && acumular)
																programado = programado + medicion.getValor();
															else if (medicion.getValor() != null && programado != null && !acumular)
																programado = medicion.getValor();
														}

														if (programado != null)
															dataRow1.createCell(5).setCellValue(programado.toString());
														else 
															dataRow1.createCell(5).setCellValue("");
													}
													else
														dataRow1.createCell(4).setCellValue("");
												}
											}




											if(iniciativa.getFechaUltimaMedicion() != null){
												dataRow1.createCell(6).setCellValue(iniciativa.getFechaUltimaMedicion());
											}
											else{
												dataRow1.createCell(6).setCellValue("");
											}


											Date fechaAh = new Date();
											Date fechaAc = new Date();

											fechaAh = obtenerFechaFinal(actividades);

											if(fechaAh != null) {

												SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy");

												String fechaTemp =objSDF.format(fechaAh);


												dataRow1.createCell(7).setCellValue(fechaTemp);

												int milisecondsByDay = 86400000;

												int dias = (int) ((fechaAh.getTime()-fechaAc.getTime()) / milisecondsByDay);

												int diasposi = dias * -1;

												dataRow1.createCell(8).setCellValue(""+diasposi);
											}else {
												dataRow1.createCell(7).setCellValue("");
												dataRow1.createCell(8).setCellValue("");
											}



											//estatus
											if (medicionesProgramadas.size() == 0) {
												//EstatusIniciar
												dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
											}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
												//EstatusIniciardesfasado
												dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar.desfasada"));
											}
											else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
												//EnEjecucionSinRetrasos
												dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.sin.retrasos"));
											}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
												//EnEjecucionConRetrasosSuperables
												dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables"));
											}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
												//EnEjecucionConRetrasosSignificativos
												dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos"));
											}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
												//EstatusConcluidas
												dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.concluidas"));
											}
											else if(iniciativa.getEstatusId() == 3) {
												//EstatusCancelado
												dataRow1.createCell(9).setCellValue("Cancelado");
											}
											else if(iniciativa.getEstatusId() == 4) {
												//EstatusSuspendido
												dataRow1.createCell(9).setCellValue("Suspendido");
											}else {
												//EstatusIniciar
												dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
											}

											if(iniciativa.getTipoProyecto()==null){
												dataRow1.createCell(10).setCellValue("");
											}else {
												dataRow1.createCell(10).setCellValue(iniciativa.getTipoProyecto().getNombre());
											}

											if(iniciativa.getAnioFormulacion() == null) {
												dataRow1.createCell(11).setCellValue("");
											}else {
												dataRow1.createCell(11).setCellValue(iniciativa.getAnioFormulacion());
											}

											//responsable ejecutar

											if(iniciativa.getResponsableCargarEjecutado() ==null){
												dataRow1.createCell(12).setCellValue("");
											}
											else{
												dataRow1.createCell(12).setCellValue(iniciativa.getResponsableCargarEjecutado().getNombre().toString());
											}

											//responsable lograr meta

											if(iniciativa.getResponsableLograrMeta() ==null){
												dataRow1.createCell(13).setCellValue("");
											}
											else{
												dataRow1.createCell(13).setCellValue(iniciativa.getResponsableLograrMeta().getNombre().toString());
											}


											x=x+1;

										}else {
											Boolean est= tieneEstatus(iniciativa, medicionesEjecutadas, medicionesProgramadas, estatusId);

											if(est) {

												String ruta=null;
												HSSFRow dataRow1 = sheet.createRow(x+1);
												OrganizacionStrategos org= new OrganizacionStrategos();
												ruta=organizacion.getNombre()+"/";
												org=organizacion.getPadre();
												while(org !=null){
													ruta=org.getNombre()+"/"+ruta;
													if(org.getPadre()==null){
														org = null;
													}
													else{
														org=org.getPadre();
													}
												}


												dataRow1.createCell(0).setCellValue(ruta);
												Byte alerta = null;
												
												alerta = iniciativa.getAlerta();
												if (alerta == null)
													dataRow1.createCell(1).setCellValue("");
												else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
													dataRow1.createCell(1).setCellValue("");
													dataRow1.createCell(1).setCellStyle(styleRoja);
												}
												else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue()) {
													dataRow1.createCell(1).setCellValue("");
													dataRow1.createCell(1).setCellStyle(styleVerde);
												}
												else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()){
													dataRow1.createCell(1).setCellValue("");
													dataRow1.createCell(1).setCellStyle(styleAmarillo);
												}
												
									            dataRow1.createCell(2).setCellValue(iniciativa.getNombre());

									            if(iniciativa.getFrecuenciaNombre() != null) {
									            	dataRow1.createCell(3).setCellValue(iniciativa.getFrecuenciaNombre());
												}else {
													dataRow1.createCell(3).setCellValue("");
												}

									            if (iniciativa.getPorcentajeCompletado() != null) {								
													if (indicador != null) {
														boolean acumular = (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal()
																.byteValue()
																&& indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo()
																		.byteValue());

														Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(),
																indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(),
																indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
														if (medicionReal != null) {
															dataRow1.createCell(4).setCellValue(medicionReal.getValor().toString());

															List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(
																	indicador.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), null,
																	medicionReal.getMedicionId().getAno(), null, medicionReal.getMedicionId().getPeriodo());
															Double programado = null;
															for (Iterator<Medicion> iter2 = mediciones.iterator(); iter2.hasNext();) {
																Medicion medicion = iter2.next();
																if (medicion.getValor() != null && programado == null)
																	programado = medicion.getValor();
																else if (medicion.getValor() != null && programado != null && acumular)
																	programado = programado + medicion.getValor();
																else if (medicion.getValor() != null && programado != null && !acumular)
																	programado = medicion.getValor();
															}

															if (programado != null)
																dataRow1.createCell(5).setCellValue(programado.toString());
															else 
																dataRow1.createCell(5).setCellValue("");
														}
														else
															dataRow1.createCell(4).setCellValue("");
													}
												}




												if(iniciativa.getFechaUltimaMedicion() != null){
													dataRow1.createCell(6).setCellValue(iniciativa.getFechaUltimaMedicion());
												}
												else{
													dataRow1.createCell(6).setCellValue("");
												}


												Date fechaAh = new Date();
												Date fechaAc = new Date();

												fechaAh = obtenerFechaFinal(actividades);

												if(fechaAh != null) {

													SimpleDateFormat objSDF = new SimpleDateFormat("MM/yyyy");

													String fechaTemp =objSDF.format(fechaAh);


													dataRow1.createCell(7).setCellValue(fechaTemp);

													int milisecondsByDay = 86400000;

													int dias = (int) ((fechaAh.getTime()-fechaAc.getTime()) / milisecondsByDay);

													int diasposi = dias * -1;

													dataRow1.createCell(8).setCellValue(""+diasposi);
												}else {
													dataRow1.createCell(7).setCellValue("");
													dataRow1.createCell(8).setCellValue("");
												}



												//estatus
												if (medicionesProgramadas.size() == 0) {
													//EstatusIniciar
													dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
												}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0) {
													//EstatusIniciardesfasado
													dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar.desfasada"));
												}
												else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D) {
													//EnEjecucionSinRetrasos
													dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.sin.retrasos"));
												}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
													//EnEjecucionConRetrasosSuperables
													dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.superables"));
												}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
													//EnEjecucionConRetrasosSignificativos
													dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.en.ejecucion.con.retrasos.significativos"));
												}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D) {
													//EstatusConcluidas
													dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.concluidas"));
												}
												else if(iniciativa.getEstatusId() == 3) {
													//EstatusCancelado
													dataRow1.createCell(9).setCellValue("Cancelado");
												}
												else if(iniciativa.getEstatusId() == 4) {
													//EstatusSuspendido
													dataRow1.createCell(9).setCellValue("Suspendido");
												}else {
													//EstatusIniciar
													dataRow1.createCell(9).setCellValue(messageResources.getMessage("estado.sin.iniciar"));
												}

												if(iniciativa.getTipoProyecto()==null){
													dataRow1.createCell(10).setCellValue("");
												}else {
													dataRow1.createCell(10).setCellValue(iniciativa.getTipoProyecto().getNombre());
												}

												if(iniciativa.getAnioFormulacion() == null) {
													dataRow1.createCell(11).setCellValue("");
												}else {
													dataRow1.createCell(11).setCellValue(iniciativa.getAnioFormulacion());
												}

												//responsable ejecutar

												if(iniciativa.getResponsableCargarEjecutado() ==null){
													dataRow1.createCell(12).setCellValue("");
												}
												else{
													dataRow1.createCell(12).setCellValue(iniciativa.getResponsableCargarEjecutado().getNombre().toString());
												}

												//responsable lograr meta

												if(iniciativa.getResponsableLograrMeta() ==null){
													dataRow1.createCell(13).setCellValue("");
												}
												else{
													dataRow1.createCell(13).setCellValue(iniciativa.getResponsableLograrMeta().getNombre().toString());
												}


												x=x+1;

											}
										}


									}


								}

						}
				}


			    Date date = new Date();
		        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");


		        String archivo="IniciativasResumido_"+hourdateFormat.format(date)+".xls";

		        response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition","attachment;filename="+archivo);

		        ServletOutputStream file  = response.getOutputStream();

		        workbook.write(file);
		        file.close();
			}
			forward="exito";
	        organizacionservice.close();
	        iniciativaservice.close();
	        /** C�digo de l�gica de Negocio del action	*/

			/** Otherwise, return "success" */
			return mapping.findForward(forward);

	}

	public String obtenerObjetivo(Iniciativa iniciativa) throws SQLException{
		String objetivo=null;
		Long id=iniciativa.getIniciativaId();

		Map<String, Object> filtros = new HashMap<String, Object>();

		if((iniciativa.getIniciativaPerspectivas() != null) && (iniciativa.getIniciativaPerspectivas().size() > 0)){

			IniciativaPerspectiva iniciativaPerspectiva = (IniciativaPerspectiva)iniciativa.getIniciativaPerspectivas().toArray()[0];
            StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();
            Perspectiva perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, iniciativaPerspectiva.getPk().getPerspectivaId());

            objetivo= perspectiva.getNombre();

		}
		return objetivo;
	}

	public Date obtenerFechaFinal(List<PryActividad> actividades) {

		Date fecha = null;

		for(PryActividad act: actividades) {

			fecha = act.getFinPlan();

		}

		return fecha;
	}

	public Boolean tieneEstatus(Iniciativa iniciativa, List<Medicion> medicionesEjecutadas, List<Medicion> medicionesProgramadas, int estatus){

		boolean tiene=false;
		//estatus
		if (medicionesProgramadas.size() == 0 && estatus == 0) {
			//EstatusIniciar
			tiene = true;
		}else if(medicionesProgramadas.size() > 0 && medicionesEjecutadas.size() == 0 && estatus == 1) {
			//EstatusIniciardesfasado
			tiene = true;
		}
		else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta() != null && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaVerde().byteValue() && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() < 100D && estatus == 2) {
			//EnEjecucionSinRetrasos
			tiene = true;
		}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue() && estatus == 3) {
			//EnEjecucionConRetrasosSuperables
			tiene = true;
		}else if(iniciativa.getEstatusId() == 2 && iniciativa.getAlerta().byteValue() == AlertaIndicador.getAlertaRoja().byteValue() && estatus == 4) {
			//EnEjecucionConRetrasosSignificativos
			tiene = true;
		}else if(iniciativa.getEstatusId() == 5 && iniciativa.getPorcentajeCompletado() != null && iniciativa.getPorcentajeCompletado().doubleValue() >= 100D && estatus == 5) {
			//EstatusConcluidas
			tiene = true;
		}
		else if(iniciativa.getEstatusId() == 3 && estatus == 6) {
			//EstatusCancelado
			tiene = true;
		}
		else if(iniciativa.getEstatusId() == 4 && estatus == 7) {
			//EstatusSuspendido
			tiene = true;
		}else if(iniciativa.getEstatusId() == 1  && estatus == 0) {
			//EstatusSuspendido
			tiene = true;
		}

		return tiene;
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
