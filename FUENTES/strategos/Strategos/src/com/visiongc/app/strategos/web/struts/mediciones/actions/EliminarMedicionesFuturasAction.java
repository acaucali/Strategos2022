package com.visiongc.app.strategos.web.struts.mediciones.actions;

import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.model.ElementoPlantillaPlanes;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.mediciones.forms.EditarMedicionesForm;
import com.visiongc.app.strategos.web.struts.mediciones.forms.EditarMedicionesForm.TipoSource;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Usuario;

public class EliminarMedicionesFuturasAction extends VgcAction{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();
		
		EditarMedicionesForm editarMedicionesForm = (EditarMedicionesForm) form;
		
		if (request.getParameter("funcion") != null) {
			String funcion = request.getParameter("funcion");

			if (funcion.equals("eliminar")) {
				eliminar(request, editarMedicionesForm);
			}
		}		
		
		return mapping.findForward(forward);		
	}
	
	public void eliminar(HttpServletRequest request, EditarMedicionesForm editarMedicionesForm  ) throws Exception  {
		ActionMessages messages = getMessages(request);
		
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
				.openStrategosIndicadoresService();
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance()
				.openStrategosMedicionesService();
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		
		Integer anioEliminar = Integer.parseInt(request.getParameter("anio"));
		Integer periodoEliminar = editarMedicionesForm.getPeriodoHasta();
		
		Map<String, String> filtros = new HashMap();
		
		if(editarMedicionesForm.getSoloSeleccionados()) { // Solo seleccionados			
			List<Indicador> indicadores = editarMedicionesForm.getIndicadores();
			if(indicadores.size() > 0 ) {
				for(Iterator<Indicador> iter = indicadores.iterator(); iter.hasNext();) {
					Indicador indicador = (Indicador) iter.next();										
					List<Medicion> medicionesPeriodo = strategosMedicionesService
							.getMedicionesPeriodo(indicador.getIndicadorId(),
									SerieTiempo.getSerieReal().getSerieId().longValue(), anioEliminar, anioEliminar,
									periodoEliminar, periodoEliminar, indicador.getFrecuencia());
					if(medicionesPeriodo.size() > 0) {
						int respuesta = strategosMedicionesService.deleteMedicion(medicionesPeriodo.get(0), getUsuarioConectado(request));						
						if(respuesta == 10000) {
							
							PryActividad act = strategosPryActividadesService.getActividadByIndicador(indicador.getIndicadorId());							
							act.setFechaUltimaMedicion(String.valueOf(periodoEliminar-1)+"/"+String.valueOf(anioEliminar));							
							strategosPryActividadesService.saveActividad(act, getUsuarioConectado(request), true);
							Iniciativa iniciativa = strategosIniciativasService.getIniciativaByProyecto(act.getProyectoId());
							iniciativa.setFechaUltimaMedicion(String.valueOf(periodoEliminar-1)+"/"+String.valueOf(anioEliminar));
							strategosIniciativasService.saveIniciativa(iniciativa, getUsuarioConectado(request), true);
														
							messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(
									"action.eliminarmediciones.mensaje.Eliminar.mediciones.exito"));
							saveMessages(request, messages);
						}else {
							messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(
									"action.eliminarmediciones.mensaje.Eliminar.mediciones.related"));
							saveMessages(request, messages);
						}
					}else {
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(
								"action.eliminarmediciones.mensaje.Eliminar.mediciones.no.mediciones"));
						saveMessages(request, messages);
					}
				}
			}
		}else { // Toda la clase
			ClaseIndicadores clase = (ClaseIndicadores)strategosIndicadoresService.load(ClaseIndicadores.class, editarMedicionesForm.getClaseId());
			
			 
			if(clase.getIndicadores().size() > 0 ) {
				for(Iterator<Indicador> iter = clase.getIndicadores().iterator(); iter.hasNext();) {
					Indicador indicador = (Indicador) iter.next();
					List<Medicion> medicionesPeriodo = strategosMedicionesService
							.getMedicionesPeriodo(indicador.getIndicadorId(),
									SerieTiempo.getSerieReal().getSerieId().longValue(), anioEliminar, anioEliminar,
									periodoEliminar, periodoEliminar);
					if(medicionesPeriodo.size() > 0) {
						int respuesta = strategosMedicionesService.deleteMedicion(medicionesPeriodo.get(0), getUsuarioConectado(request));						
						if(respuesta == 10000) {
							
							PryActividad act = strategosPryActividadesService.getActividadByIndicador(indicador.getIndicadorId());							
							act.setFechaUltimaMedicion(String.valueOf(periodoEliminar-1)+"/"+String.valueOf(anioEliminar));							
							strategosPryActividadesService.saveActividad(act, getUsuarioConectado(request), true);
							Iniciativa iniciativa = strategosIniciativasService.getIniciativaByProyecto(act.getProyectoId());
							iniciativa.setFechaUltimaMedicion(String.valueOf(periodoEliminar-1)+"/"+String.valueOf(anioEliminar));
							strategosIniciativasService.saveIniciativa(iniciativa, getUsuarioConectado(request), true);
							
							messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(
									"action.eliminarmediciones.mensaje.Eliminar.mediciones.exito"));
							saveMessages(request, messages);
						}else {
							messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(
									"action.eliminarmediciones.mensaje.Eliminar.mediciones.related"));
							saveMessages(request, messages);
						}
					}else {
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(
								"action.eliminarmediciones.mensaje.Eliminar.mediciones.no.mediciones"));
						saveMessages(request, messages);
					}
				}
			}
		}
		
	}
}