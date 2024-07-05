package com.visiongc.app.strategos.web.struts.mediciones.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.mediciones.forms.EditarMedicionesForm;
import com.visiongc.app.strategos.web.struts.mediciones.forms.EditarMedicionesForm.TipoSource;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

public class EliminarMedicionesFuturasAction extends VgcAction {
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarMedicionesForm editarMedicionesForm = (EditarMedicionesForm) form;

		if (request.getParameter("funcion") != null) {
			String funcion = request.getParameter("funcion");

			if (funcion.equals("eliminar")) {
				try {
					eliminar(request, editarMedicionesForm);
				} catch (Throwable e) {

					e.printStackTrace();
				}
			}
		}

		return mapping.findForward(forward);
	}

	public void eliminar(HttpServletRequest request, EditarMedicionesForm editarMedicionesForm) throws Throwable {
		ActionMessages messages = getMessages(request);

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
				.openStrategosIndicadoresService();
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance()
				.openStrategosMedicionesService();
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance()
				.openStrategosPryActividadesService();
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance()
				.openStrategosIniciativasService();

		Integer anioEliminar = Integer.parseInt(request.getParameter("anio"));
		Integer periodoEliminar = editarMedicionesForm.getPeriodoHasta();

		Byte source = Byte.parseByte(request.getParameter("source"));
		if (source.byteValue() == TipoSource.SOURCE_CLASE)
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_CLASE);
		else if (source.byteValue() == TipoSource.SOURCE_PLAN)
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_PLAN);
		else if (source.byteValue() == TipoSource.SOURCE_INICIATIVA)
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_INICIATIVA);
		else
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_ACTIVIDAD);

		Map<String, String> filtros = new HashMap();
		int respuesta = 0;

		if (editarMedicionesForm.getSoloSeleccionados()) { // Solo seleccionados
			for (Iterator<Indicador> iter = editarMedicionesForm.getIndicadores().iterator(); iter.hasNext();) {
				Indicador indicador = iter.next();
				Medicion ultimaMedicion = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(),
						SerieTiempo.getSerieReal().getSerieId().longValue());
				List<Medicion> medicionesPeriodo = strategosMedicionesService.getMedicionesPeriodo(
						indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId().longValue(), anioEliminar,
						anioEliminar, periodoEliminar, periodoEliminar);
				if (medicionesPeriodo.size() > 0) {
					respuesta = strategosMedicionesService.deleteMedicion(medicionesPeriodo.get(0),
							getUsuarioConectado(request));
					Medicion ultimaMedicionPost = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(),
							SerieTiempo.getSerieReal().getSerieId().longValue());
					if (respuesta == 10000) {
						if (editarMedicionesForm.getSourceScreen() == TipoSource.SOURCE_CLASE) {
							if (ultimaMedicion.getMedicionId().getPeriodo().equals(periodoEliminar)
									&& ultimaMedicion.getMedicionId().getAno().equals(anioEliminar)) {
								indicador.setFechaUltimaMedicion(
										String.valueOf(ultimaMedicionPost.getMedicionId().getPeriodo()) + "/" + String.valueOf(ultimaMedicionPost.getMedicionId().getAno()));
								strategosIndicadoresService.saveIndicador(indicador, getUsuarioConectado(request));
							}

						}

						if (editarMedicionesForm.getSourceScreen() == TipoSource.SOURCE_ACTIVIDAD) {
							if (ultimaMedicion.getMedicionId().getPeriodo().equals(periodoEliminar)
									&& ultimaMedicion.getMedicionId().getAno().equals(anioEliminar)) {
								PryActividad act = strategosPryActividadesService
										.getActividadByIndicador(indicador.getIndicadorId());
								Iniciativa iniciativa = strategosIniciativasService
										.getIniciativaByProyecto(act.getProyectoId());
								act.setFechaUltimaMedicion(
										String.valueOf(ultimaMedicionPost.getMedicionId().getPeriodo()) + "/" + String.valueOf(ultimaMedicionPost.getMedicionId().getAno()));
								strategosPryActividadesService.saveActividad(act, getUsuarioConectado(request), true);

								iniciativa.setFechaUltimaMedicion(
										String.valueOf(ultimaMedicionPost.getMedicionId().getPeriodo()) + "/" + String.valueOf(ultimaMedicionPost.getMedicionId().getAno()));
								strategosIniciativasService.saveIniciativa(iniciativa, getUsuarioConectado(request),
										true);
								Indicador indicadorIni = (Indicador) strategosIndicadoresService.load(Indicador.class,
										iniciativa.getIndicadorId((byte) 1));
								List<Medicion> medicionesPeriodoIni = strategosMedicionesService.getMedicionesPeriodo(
										indicadorIni.getIndicadorId(),
										SerieTiempo.getSerieReal().getSerieId().longValue(), anioEliminar, anioEliminar,
										periodoEliminar, periodoEliminar);

								if(medicionesPeriodoIni.size() > 0 ) {
									strategosMedicionesService.deleteMedicion(medicionesPeriodoIni.get(0),
											getUsuarioConectado(request));
									indicadorIni.setFechaUltimaMedicion(
											String.valueOf(ultimaMedicionPost.getMedicionId().getPeriodo()) + "/" + String.valueOf(ultimaMedicionPost.getMedicionId().getAno()));
									strategosIndicadoresService.saveIndicador(indicadorIni,
											getUsuarioConectado(request));
								}
							}
						}
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
								new ActionMessage("action.eliminarmediciones.mensaje.Eliminar.mediciones.exito"));
						saveMessages(request, messages);
					} else {
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
								new ActionMessage("action.eliminarmediciones.mensaje.Eliminar.mediciones.related"));
						saveMessages(request, messages);
					}
					Medicion ultimaMedicionDespues = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(),
							SerieTiempo.getSerieReal().getSerieId().longValue());
					
				}
			}
		} else { // Toda la clase
			ClaseIndicadores clase = (ClaseIndicadores) strategosIndicadoresService.load(ClaseIndicadores.class,
					editarMedicionesForm.getClaseId());

			if (clase.getIndicadores().size() > 0) {
				for (Iterator<Indicador> iter = clase.getIndicadores().iterator(); iter.hasNext();) {
					Indicador indicador = iter.next();
					Medicion ultimaMedicion = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(),
							SerieTiempo.getSerieReal().getSerieId().longValue());
					List<Medicion> medicionesPeriodo = strategosMedicionesService.getMedicionesPeriodo(
							indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId().longValue(),
							anioEliminar, anioEliminar, periodoEliminar, periodoEliminar);
					if (medicionesPeriodo.size() > 0) {
						respuesta = strategosMedicionesService.deleteMedicion(medicionesPeriodo.get(0),
								getUsuarioConectado(request));
						Medicion ultimaMedicionPost = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(),
								SerieTiempo.getSerieReal().getSerieId().longValue());
						if (respuesta == 10000) {
							if (editarMedicionesForm.getSourceScreen() == TipoSource.SOURCE_CLASE) {
								if (ultimaMedicion.getMedicionId().getPeriodo().equals(periodoEliminar)
										&& ultimaMedicion.getMedicionId().getAno().equals(anioEliminar)) {
									indicador.setFechaUltimaMedicion(
											String.valueOf(ultimaMedicionPost.getMedicionId().getPeriodo()) + "/" + String.valueOf(ultimaMedicionPost.getMedicionId().getAno()));
									strategosIndicadoresService.saveIndicador(indicador, getUsuarioConectado(request));
								}
							}
							if (editarMedicionesForm.getSourceScreen() == TipoSource.SOURCE_ACTIVIDAD) {
								if (ultimaMedicion.getMedicionId().getPeriodo().equals(periodoEliminar)
										&& ultimaMedicion.getMedicionId().getAno().equals(anioEliminar)) {
									PryActividad act = strategosPryActividadesService
											.getActividadByIndicador(indicador.getIndicadorId());
									act.setFechaUltimaMedicion(
											String.valueOf(ultimaMedicionPost.getMedicionId().getPeriodo()) + "/" + String.valueOf(ultimaMedicionPost.getMedicionId().getAno()));
									strategosPryActividadesService.saveActividad(act, getUsuarioConectado(request),
											true);
									Iniciativa iniciativa = strategosIniciativasService
											.getIniciativaByProyecto(act.getProyectoId());
									iniciativa.setFechaUltimaMedicion(
											String.valueOf(ultimaMedicionPost.getMedicionId().getPeriodo()) + "/" + String.valueOf(ultimaMedicionPost.getMedicionId().getAno()));
									strategosIniciativasService.saveIniciativa(iniciativa, getUsuarioConectado(request),
											true);
								}
							}
							messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
									new ActionMessage("action.eliminarmediciones.mensaje.Eliminar.mediciones.exito"));
							saveMessages(request, messages);
						} else {
							messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
									new ActionMessage("action.eliminarmediciones.mensaje.Eliminar.mediciones.related"));
							saveMessages(request, messages);
						}
					}
				}
			}
		}

		strategosIndicadoresService.close();
		strategosMedicionesService.close();
		strategosPryActividadesService.close();
		strategosPryActividadesService.close();
	}
}