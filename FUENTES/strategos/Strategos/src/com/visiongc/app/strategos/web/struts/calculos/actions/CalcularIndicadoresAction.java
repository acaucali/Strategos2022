package com.visiongc.app.strategos.web.struts.calculos.actions;

import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.web.struts.calculos.forms.CalculoIndicadoresForm;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions.CalcularActividadesAction;
import com.visiongc.app.strategos.web.struts.servicio.forms.ServicioForm;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.auditoria.model.util.AuditoriaTipoEvento;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.util.FrameworkConnection;
import com.visiongc.framework.web.struts.forms.servicio.GestionarServiciosForm;
import com.visiongc.servicio.strategos.calculos.CalcularManager;
import com.visiongc.servicio.strategos.servicio.model.Servicio;

public class CalcularIndicadoresAction extends VgcAction {
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.execute(mapping, form, request, response);

		boolean cancel = (request.getParameter("cancel") != null) && (request.getParameter("cancel").equals("1"));
		if (cancel) {
			request.getSession().removeAttribute("calculoIndicadoresForm");
			request.getSession().removeAttribute("verArchivoLog");

			return getForwardBack(request, 1, true);
		}
		String forward = mapping.getParameter();

		CalculoIndicadoresForm calculoIndicadoresForm = (CalculoIndicadoresForm) form;
		if ((calculoIndicadoresForm.getIndicadorId() != null)
				&& (calculoIndicadoresForm.getIndicadorId().longValue() == 0L)) {
			calculoIndicadoresForm.setIndicadorId(null);
		}
		if ((calculoIndicadoresForm.getClaseId() != null) && (calculoIndicadoresForm.getClaseId().longValue() == 0L)) {
			calculoIndicadoresForm.setClaseId(null);
		}
		if ((calculoIndicadoresForm.getOrganizacionId() != null)
				&& (calculoIndicadoresForm.getOrganizacionId().longValue() == 0L)) {
			calculoIndicadoresForm.setOrganizacionId(null);
		}
		if ((calculoIndicadoresForm.getPerspectivaId() != null)
				&& (calculoIndicadoresForm.getPerspectivaId().longValue() == 0L)) {
			calculoIndicadoresForm.setPerspectivaId(null);
		}
		if ((calculoIndicadoresForm.getPlanId() != null) && (calculoIndicadoresForm.getPlanId().longValue() == 0L)) {
			calculoIndicadoresForm.setPlanId(null);
		}
		if ((calculoIndicadoresForm.getIniciativaId() != null)
				&& (calculoIndicadoresForm.getIniciativaId().longValue() == 0L)) {
			calculoIndicadoresForm.setIniciativaId(null);
		}
		Calcular(request, calculoIndicadoresForm);
		calculoIndicadoresForm.setCalculado(new Boolean(true));
		if (request.getSession().getAttribute("actualizarForma") == null) {
			request.getSession().setAttribute("actualizarForma", "true");
		}
		return mapping.findForward(forward);
	}

	private void Calcular(HttpServletRequest request, CalculoIndicadoresForm calculoIndicadoresForm) throws Exception {
		ServicioForm servicioForm = new ServicioForm();
		StringBuffer log = new StringBuffer();

		ActionMessages messages = getMessages(request);
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");

		String showPresentacion = request.getParameter("showPresentacion") != null
				? request.getParameter("showPresentacion").toString()
				: "0";
		ConfiguracionUsuario presentacion = new ConfiguracionUsuario();
		ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
		pk.setConfiguracionBase("Strategos.Wizar.Calcular.ShowPresentacion");
		pk.setObjeto("ShowPresentacion");
		pk.setUsuarioId(getUsuarioConectado(request).getUsuarioId());
		presentacion.setPk(pk);
		presentacion.setData(showPresentacion);
		frameworkService.saveConfiguracionUsuario(presentacion, getUsuarioConectado(request));
		if (configuracion == null) {
			calculoIndicadoresForm.setStatus(CalculoIndicadoresForm.CalcularStatus.getCalcularStatusNoConfigurado());
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
					new ActionMessage("jsp.asistente.importacion.status.configuracion.noexiste"));
			saveMessages(request, messages);
		} else {
			List<PryActividad> actividades = new ArrayList();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("properties");
			Element eElement = (Element) nList.item(0);

			String url = VgcAbstractService.getTagValue("url", eElement);
			String driver = VgcAbstractService.getTagValue("driver", eElement);
			String user = VgcAbstractService.getTagValue("user", eElement);
			String password = VgcAbstractService.getTagValue("password", eElement);
			password = new GestionarServiciosForm().getPasswordConexionDecriptado(password);
			if (!new FrameworkConnection().testConnection(url, driver, user, password)) {
				calculoIndicadoresForm
						.setStatus(CalculoIndicadoresForm.CalcularStatus.getCalcularStatusNoConfigurado());
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.asistente.importacion.status.configuracion.noexiste"));
				saveMessages(request, messages);
			} else {
				Usuario usuario = getUsuarioConectado(request);
				StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance()
						.openStrategosIniciativasService();

				if (calculoIndicadoresForm.getAlcance().byteValue() == (byte) 3) {
					StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
							.openStrategosIndicadoresService();
					StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance()
							.openStrategosMedicionesService();
					StrategosPryActividadesService servicioActividad = StrategosServiceFactory.getInstance()
							.openStrategosPryActividadesService();
					Map<String, String> filtros = new HashMap();
					filtros.put("organizacionId",
							calculoIndicadoresForm.getOrganizacionId() != null
									? calculoIndicadoresForm.getOrganizacionId().toString()
									: "");
					PaginaLista iniciativas = strategosIniciativasService.getIniciativas(1, 100, "id", "asc", true,
							filtros);

					if (iniciativas.getLista().size() > 0) {
												
						Long actividadId = null;
						Long proyectoId = null;
						Long padreId = null;
						int respuesta = 10000;
						for (Iterator<Iniciativa> iter = iniciativas.getLista().iterator(); iter.hasNext();) {
							Iniciativa iniciativa = iter.next();
							if (iniciativa.getProyectoId() != null) {
								filtros = new HashMap();
								filtros.put("proyectoId", iniciativa.getProyectoId().toString());
								PaginaLista actividadesCal = servicioActividad.getActividades(1, 100, "id", "asc", true,
										filtros);													
								if (actividadesCal.getLista().size() > 0) {
									for (Iterator<PryActividad> iter2 = actividadesCal.getLista().iterator(); iter2
											.hasNext();) {
										PryActividad actividad = iter2.next();

										if (actividad != null) {
											if ((proyectoId == null) && (actividad.getProyectoId() != null)) {
												proyectoId = actividad.getProyectoId();
											}
											actividadId = actividad.getActividadId();
											padreId = actividad.getPadreId();
											
											Indicador indicador = (Indicador) strategosIndicadoresService.load(
													Indicador.class, new Long(actividad.getIndicadorId().longValue()));
											if (indicador != null) {												
																								
												Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(
														indicador.getIndicadorId(), indicador.getFrecuencia(),
														indicador.getMesCierre(), SerieTiempo.getSerieRealId(),
														indicador.getValorInicialCero(),
														TipoCorte.getTipoCorteTransversal(),
														indicador.getTipoCargaMedicion());												
												actividad.setAlerta(AlertaIndicador.getAlertaNoDefinible());
												if (medicionReal != null) {
													List<Medicion> medicionesAlertas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieAlerta(), medicionReal.getMedicionId().getAno(), medicionReal.getMedicionId().getAno(), medicionReal.getMedicionId().getPeriodo(), medicionReal.getMedicionId().getPeriodo());
										              if ((medicionesAlertas.size() > 0) && (medicionesAlertas.get(0).getValor() != null)) {
										                actividad.setAlerta(AlertaIndicador.ConvertDoubleToByte(medicionesAlertas.get(0).getValor()));
										              }
												}
												Double totalReal = null;
												Double totalProgramado = null;
												Double ultimaMedicionReal = null;
												String ultimaMedicion =  null;
												if (actividad.getTipoMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())
									            {
									              List<Medicion> medicionesReales = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), new Integer(0), new Integer(9999), new Integer(0), new Integer(999));
									              List<Medicion> medicionesProgramada = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), new Integer(0), new Integer(9999), new Integer(0), new Integer(999));
									              for (Iterator<Medicion> iterMediciones = medicionesReales.iterator(); iterMediciones.hasNext();)
									              {
									                Medicion medicion = iterMediciones.next();
									                if ((medicion.getValor() != null) && (totalReal == null)) {
									                  totalReal = Double.valueOf(0.0D);
									                }
									                totalReal = Double.valueOf(totalReal.doubleValue() + medicion.getValor().doubleValue());
									                ultimaMedicionReal = totalReal;
									                for (Iterator<Medicion> iterMedicionesProgramadas = medicionesProgramada.iterator(); iterMedicionesProgramadas.hasNext();)
									                {
									                  Medicion medicionProgramada = iterMedicionesProgramadas.next();
									                  if ((medicion.getMedicionId().getAno().intValue() == medicionProgramada.getMedicionId().getAno().intValue()) &&
									                    (medicion.getMedicionId().getPeriodo().intValue() == medicionProgramada.getMedicionId().getPeriodo().intValue()))
									                  {
									                    if ((medicionProgramada.getValor() != null) && (totalProgramado == null)) {
									                      totalProgramado = Double.valueOf(0.0D);
									                    }
									                    totalProgramado = Double.valueOf(totalProgramado.doubleValue() + medicionProgramada.getValor().doubleValue());
									                    break;
									                  }
									                }
													
									                ultimaMedicion = medicion.getMedicionId().getPeriodo().toString() + "/" + medicion.getMedicionId().getAno();													
													actividad.setFechaUltimaMedicion(ultimaMedicion);
													indicador.setFechaUltimaMedicion(ultimaMedicion);
									              }
												} else {
													totalReal = medicionReal != null ? medicionReal.getValor() : null;
													if ((medicionReal != null) && (medicionReal.getValor() != null)) {
														ultimaMedicionReal = medicionReal.getValor();
													}
													if (totalReal != null) {
														List<Medicion> medicionesProgramada = strategosMedicionesService
																.getMedicionesPeriodo(indicador.getIndicadorId(),
																		SerieTiempo.getSerieProgramado().getSerieId(),
																		new Integer(0), new Integer(9999),
																		new Integer(0), new Integer(999));
														DecimalFormat nf3 = new DecimalFormat("#000");
														int anoPeriodoBuscar = Integer.parseInt(medicionReal
																.getMedicionId().getAno().toString()
																+ nf3.format(medicionReal.getMedicionId().getPeriodo())
																		.toString());
														for (Iterator<Medicion> iterMedicionesProgramadas = medicionesProgramada
																.iterator(); iterMedicionesProgramadas.hasNext();) {
															Medicion medProgramada = iterMedicionesProgramadas.next();
															int anoPeriodo = Integer.parseInt(medProgramada
																	.getMedicionId().getAno().toString()
																	+ nf3.format(
																			medProgramada.getMedicionId().getPeriodo())
																			.toString());
															if (anoPeriodo <= anoPeriodoBuscar) {
																totalProgramado = medProgramada.getValor();
															}
														}
													}
												}
												actividad.setPorcentajeCompletado(ultimaMedicionReal);
												actividad.setPorcentajeEjecutado(totalReal);
												actividad.setPorcentajeEsperado(totalProgramado);
												
												respuesta = servicioActividad.saveActividad(actividad,
														getUsuarioConectado(request), Boolean.valueOf(false));
												if (respuesta != 10000) {
													break;
												}
											}
										}
									}
								}
								
								
								if (respuesta == 10000 && (actividadId != null)) {									
									PryActividad actividad = (PryActividad) servicioActividad.load(PryActividad.class,
											new Long(actividadId.longValue()));									
									respuesta = new CalcularActividadesAction().CalcularPadre(actividad,
											iniciativa.getIniciativaId(), request);

								}
							}
						}
					}
					servicioActividad.close();
					strategosIndicadoresService.close();
					strategosMedicionesService.close();

				}

				com.visiongc.commons.util.VgcMessageResources messageResources = VgcResourceManager
						.getMessageResources("StrategosWeb");
				log.append(messageResources.getResource("jsp.asistente.calculo.log.titulocalculo") + "\n");

				Calendar ahora = Calendar.getInstance();
				String[] argsReemplazo = new String[2];
				argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
				argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
				log.append(messageResources.getResource("jsp.asistente.calculo.log.fechainiciocalculo", argsReemplazo)
						+ "\n\n");

				servicioForm.setProperty("url", url);
				servicioForm.setProperty("driver", driver);
				servicioForm.setProperty("user", user);
				servicioForm.setProperty("password", password);

				servicioForm.setProperty("logMediciones", calculoIndicadoresForm.getReportarIndicadores().toString());
				servicioForm.setProperty("logErrores", calculoIndicadoresForm.getReportarErrores().toString());
				servicioForm.setProperty("tomarPeriodosSinMedicionConValorCero",
						calculoIndicadoresForm.getPeriodosCero().toString());
				servicioForm.setProperty("planId",
						calculoIndicadoresForm.getPlanId() != null ? calculoIndicadoresForm.getPlanId().toString()
								: "");
				servicioForm.setProperty("organizacionId",
						calculoIndicadoresForm.getOrganizacionId() != null
								? calculoIndicadoresForm.getOrganizacionId().toString()
								: "");
				servicioForm.setProperty("perspectivaId",
						calculoIndicadoresForm.getPerspectivaId() != null
								? calculoIndicadoresForm.getPerspectivaId().toString()
								: "");
				servicioForm.setProperty("frecuencia",
						calculoIndicadoresForm.getFrecuencia() != null
								? calculoIndicadoresForm.getFrecuencia().toString()
								: "");
				if ((calculoIndicadoresForm.getOrigen().byteValue() != calculoIndicadoresForm.getOrigenIniciativas()
						.byteValue()) && (calculoIndicadoresForm.getClaseId() != null)) {
					servicioForm.setProperty("claseId",
							(calculoIndicadoresForm.getAlcance().byteValue() == calculoIndicadoresForm.getAlcanceClase()
									.byteValue()) && (calculoIndicadoresForm.getClaseId() != null)
											? calculoIndicadoresForm.getClaseId().toString()
											: "");
				} else if (calculoIndicadoresForm.getIniciativaId() != null) {
					servicioForm.setProperty("claseId",
							(calculoIndicadoresForm.getAlcance().byteValue() == calculoIndicadoresForm
									.getAlcanceIniciativa().byteValue())
									&& (calculoIndicadoresForm.getIniciativaId() != null)
											? calculoIndicadoresForm.getClaseId().toString()
											: "");
					servicioForm.setProperty("desdeIniciativas", "true");
				} else {
					servicioForm.setProperty("claseId", "");
				}
				servicioForm.setProperty("ano", calculoIndicadoresForm.getAno());
				servicioForm.setProperty("mesInicial", calculoIndicadoresForm.getMesInicial().toString());
				servicioForm.setProperty("mesFinal", calculoIndicadoresForm.getMesFinal().toString());
				servicioForm
						.setProperty("arbolCompletoOrganizacion",
								Boolean.valueOf(calculoIndicadoresForm.getAlcance()
										.byteValue() == calculoIndicadoresForm.getAlcanceOrganizacion().byteValue())
										.toString());
				servicioForm.setProperty("todasOrganizaciones", Boolean.valueOf(calculoIndicadoresForm.getAlcance()
						.byteValue() == calculoIndicadoresForm.getAlcanceOrganizacionTodas().byteValue()).toString());
				servicioForm.setProperty("indicadorId",
						calculoIndicadoresForm.getIndicadorId() != null
								? calculoIndicadoresForm.getIndicadorId().toString()
								: "");
				servicioForm.setProperty("porNombre", calculoIndicadoresForm.getPorNombre().toString());
				servicioForm.setProperty("nombreIndicador",
						calculoIndicadoresForm.getNombreIndicador() != null
								? calculoIndicadoresForm.getNombreIndicador()
								: "");

				servicioForm.setProperty("logConsolaMetodos", Boolean.valueOf(false).toString());
				servicioForm.setProperty("logConsolaDetallado", Boolean.valueOf(false).toString());
				servicioForm.setProperty("usuarioId", usuario.getUsuarioId().toString());

				servicioForm.setProperty("activarSheduler", Boolean.valueOf(true).toString());
				servicioForm.setProperty("unidadTiempo", Integer.valueOf(3).toString());
				servicioForm.setProperty("numeroEjecucion", Integer.valueOf(1).toString());

				StringBuffer logBefore = log;

				// ejecucion calculo

				new CalcularManager(servicioForm.Get(), log, com.visiongc.servicio.web.importar.util.VgcMessageResources
						.getVgcMessageResources("StrategosWeb")).Ejecutar();
				log = logBefore;

				calculoIndicadoresForm.setStatus(CalculoIndicadoresForm.CalcularStatus.getCalcularStatusCalculado());

				ahora = Calendar.getInstance();
				argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
				argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

				log.append("\n\n");
				log.append(messageResources.getResource("jsp.asistente.calculo.log.fechafin.programada", argsReemplazo)
						+ "\n\n");

				request.getSession().setAttribute("verArchivoLog", log);
				if (usuario != null) {
					byte tipoEvento = AuditoriaTipoEvento.getAuditoriaTipoEventoCalculo();
					Servicio servicio = new Servicio();
					servicio.setUsuarioId(usuario.getUsuarioId());
					servicio.setFecha(VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy") + " "
							+ VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a"));
					servicio.setNombre(messageResources.getResource("jsp.asistente.calculo.log.titulocalculo"));
					servicio.setMensaje(messageResources.getResource("jsp.asistente.calculo.log.fechafin.programada",
							argsReemplazo));
					servicio.setEstatus(CalculoIndicadoresForm.CalcularStatus.getCalcularStatusCalculado());

					frameworkService.registrarAuditoriaEvento(servicio, usuario, tipoEvento);
				}
			}
		}
		frameworkService.close();
	}
}