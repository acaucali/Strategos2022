/**
 * 
 */
package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.StrategosTiposConvenioService;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.instrumentos.model.TipoConvenio;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.GestionarIniciativasForm;
import com.visiongc.app.strategos.web.struts.instrumentos.forms.GestionarConveniosForm;
import com.visiongc.app.strategos.web.struts.instrumentos.forms.GestionarInstrumentosForm;
import com.visiongc.app.strategos.web.struts.portafolios.forms.GestionarPortafoliosForm;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.CondicionType;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.web.controles.SplitControl;
import com.visiongc.framework.web.struts.forms.FiltroForm;

/**
 * @author Kerwin
 *
 */
public class GestionarInstrumentosAction extends VgcAction {
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
		navBar.agregarUrlSinParametros(url, nombre, new Integer(2));
	}

	@SuppressWarnings("unlikely-arg-type")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarInstrumentosForm gestionarInstrumentosForm = (GestionarInstrumentosForm) form;
		String instrumentoSeleccionadoId = gestionarInstrumentosForm != null
				&& gestionarInstrumentosForm.getSeleccionados() != null ? gestionarInstrumentosForm.getSeleccionados()
						: null;
		String iniciativaSeleccionadoId = gestionarInstrumentosForm != null
				&& gestionarInstrumentosForm.getIniciativaId() != null
						? gestionarInstrumentosForm.getIniciativaId().toString()
						: null;

		Long iniciativaId = null;

		if (request.getParameter("iniciativaSeleccionadaId") != null
				&& request.getParameter("iniciativaSeleccionadaId") != ""
				&& !request.getParameter("iniciativaSeleccionadaId").equals("0"))
			iniciativaId = Long.parseLong(request.getParameter("iniciativaSeleccionadaId"));
		else if (iniciativaSeleccionadoId != null && !iniciativaSeleccionadoId.equals(""))
			iniciativaId = Long.parseLong(iniciativaSeleccionadoId);
		
		

		Long instrumentoId = null;
		Long indicadorId = null;
		Long claseId = null;
		Long indicadorAnioId = null;
		if (request.getParameter("instrumentoId") != null && request.getParameter("instrumentoId") != "")
			instrumentoId = Long.parseLong(request.getParameter("instrumentoId"));
		else if (instrumentoSeleccionadoId != null && instrumentoSeleccionadoId != "")
			instrumentoId = Long.parseLong(instrumentoSeleccionadoId);		
		if (instrumentoId != null)
			gestionarInstrumentosForm.setSeleccionados(instrumentoId.toString());
		if (iniciativaId != null)
			gestionarInstrumentosForm.setIniciativaId(iniciativaId);

		FiltroForm filtro = new FiltroForm();

		String nombreCorto = request.getParameter("nombreCorto") != null ? request.getParameter("nombreCorto") : "";
		String anio = request.getParameter("anio") != null ? request.getParameter("anio") : "";
		String estatusSt = request.getParameter("estatus") != null ? request.getParameter("estatus") : "";
		Byte estatus = 0;

		if (estatusSt != null && estatusSt != "") {
			estatus = Byte.valueOf(estatusSt);
		}
			
		
		Long cooperanteId = (request.getParameter("cop") != null) && (request.getParameter("cop") != "")
				&& (!request.getParameter("cop").equals("0"))
						? Long.valueOf(Long.parseLong(request.getParameter("cop")))
						: null;
		Long tiposConvenioId = (request.getParameter("con") != null) && (request.getParameter("con") != "")
				&& (!request.getParameter("con").equals("0"))
						? Long.valueOf(Long.parseLong(request.getParameter("con")))
						: null;

		gestionarInstrumentosForm.setFiltro(filtro);

		gestionarInstrumentosForm.setAnio(anio);
		gestionarInstrumentosForm.setCooperanteId(cooperanteId);
		gestionarInstrumentosForm.setTiposConvenioId(tiposConvenioId);
		gestionarInstrumentosForm.setNombreCorto(nombreCorto);

		StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance()
				.openStrategosInstrumentosService();
		StrategosTiposConvenioService strategosTiposConvenioService = StrategosServiceFactory.getInstance()
				.openStrategosTiposConvenioService();
		StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance()
				.openStrategosCooperantesService();

		gestionarInstrumentosForm.setInstrumentoId(instrumentoId);

		
		
		Map<String, String> filtros = new HashMap<String, String>();
			
		int pagina = gestionarInstrumentosForm.getPagina();
		String atributoOrden = null;
		String tipoOrden = null;		

		if (atributoOrden == null)
			atributoOrden = "nombreCorto";
		if (tipoOrden == null)
			tipoOrden = "ASC";
		if (pagina < 1)
			pagina = 1;				

		if ((gestionarInstrumentosForm.getNombreCorto() != null) && gestionarInstrumentosForm.getNombreCorto() != "") {
			filtros.put("nombreCorto", gestionarInstrumentosForm.getNombreCorto());
		}
		if ((gestionarInstrumentosForm.getAnio() != null) && gestionarInstrumentosForm.getAnio() != "") {
			filtros.put("anio", gestionarInstrumentosForm.getAnio());
		}
		if ((gestionarInstrumentosForm.getEstatus() != null)) {	
			if((gestionarInstrumentosForm.getEstatus() != 0))
				filtros.put("estatus", gestionarInstrumentosForm.getEstatus().toString());
		}
		if ((gestionarInstrumentosForm.getTiposConvenioId() != null)
				&& gestionarInstrumentosForm.getTiposConvenioId() != 0) {
			filtros.put("tiposConvenioId", gestionarInstrumentosForm.getTiposConvenioId().toString());
		}
		if ((gestionarInstrumentosForm.getCooperanteId() != null) && gestionarInstrumentosForm.getCooperanteId() != 0) {
			filtros.put("cooperanteId", gestionarInstrumentosForm.getCooperanteId().toString());
		}
		
		if(request.getParameter("limpiar") != null) {
			if(request.getParameter("limpiar").equals("1")) 
				filtros.clear();;
		}		
									
		PaginaLista paginaInstrumentos = strategosInstrumentosService.getInstrumentos(pagina, 30, atributoOrden,
				tipoOrden, true, filtros);

		if (paginaInstrumentos.getLista().size() > 0) {

			for (Iterator<Instrumentos> iter = paginaInstrumentos.getLista().iterator(); iter.hasNext();) {
				Instrumentos instrumento = (Instrumentos) iter.next();

				SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

				if (instrumento.getFechaInicio() != null)
					instrumento.setFechaInicioTexto(format.format(instrumento.getFechaInicio()));
				if (instrumento.getFechaProrroga() != null)
					instrumento.setFechaProrrogaTexto(format.format(instrumento.getFechaProrroga()));
				if (instrumento.getFechaTerminacion() != null)
					instrumento.setFechaTerminacionTexto(format.format(instrumento.getFechaTerminacion()));

			}

			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();

			ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(
					this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Foco.Instrumento.Lista",
					"InstrumentoId");

			Long instrumentoIdFocus = null;
			boolean instrumentoEnLaLista = false;
			if (configuracionUsuario != null) {
				instrumentoIdFocus = new Long(configuracionUsuario.getData());
				for (Iterator<Instrumentos> iter = paginaInstrumentos.getLista().iterator(); iter.hasNext();) {
					Instrumentos instrumento = (Instrumentos) iter.next();
					if (instrumento.getInstrumentoId().longValue() == instrumentoIdFocus.longValue()) {
						instrumentoEnLaLista = true;
						break;
					}
				}
			}

			if ((gestionarInstrumentosForm.getSeleccionados() == null)
					|| (gestionarInstrumentosForm.getSeleccionados().equals(""))) {
				instrumentoId = ((Instrumentos) paginaInstrumentos.getLista().get(0)).getInstrumentoId();

				if (!instrumentoEnLaLista) {
					instrumentoIdFocus = instrumentoId;
					instrumentoEnLaLista = true;
				} else {
					instrumentoId = instrumentoIdFocus;
					instrumentoEnLaLista = false;
				}
				gestionarInstrumentosForm.setSeleccionados(instrumentoId.toString());
				indicadorId = obtenerIndicadorId(instrumentoId.toString(), paginaInstrumentos,
						gestionarInstrumentosForm);
				claseId = obtenerClaseId(instrumentoId.toString(), paginaInstrumentos, gestionarInstrumentosForm);
				if (indicadorId != null)
					gestionarInstrumentosForm.setIndicadorId(indicadorId.toString());
				indicadorAnioId = obtenerIndicadorAnioId(instrumentoId.toString(), paginaInstrumentos,
						gestionarInstrumentosForm);
				if (indicadorAnioId != null)
					gestionarInstrumentosForm.setIndicadorAnioId(indicadorAnioId.toString());
			} else {
				for (Iterator<Instrumentos> iter = paginaInstrumentos.getLista().iterator(); iter.hasNext();) {
					Instrumentos instru = (Instrumentos) iter.next();
					if (instru.getInstrumentoId().longValue() == new Long(gestionarInstrumentosForm.getSeleccionados())
							.longValue()) {
						instrumentoEnLaLista = true;
						break;
					}
				}

				if (instrumentoEnLaLista) {
					instrumentoIdFocus = new Long(gestionarInstrumentosForm.getSeleccionados());
					indicadorId = obtenerIndicadorId(instrumentoIdFocus.toString(), paginaInstrumentos,
							gestionarInstrumentosForm);
					claseId = obtenerClaseId(instrumentoId.toString(), paginaInstrumentos, gestionarInstrumentosForm);
					if (indicadorId != null)
						gestionarInstrumentosForm.setIndicadorId(indicadorId.toString());
					indicadorAnioId = obtenerIndicadorAnioId(instrumentoId.toString(), paginaInstrumentos,
							gestionarInstrumentosForm);
					if (indicadorAnioId != null)
						gestionarInstrumentosForm.setIndicadorAnioId(indicadorAnioId.toString());
				} else {
					instrumentoId = ((Instrumentos) paginaInstrumentos.getLista().get(0)).getInstrumentoId();
					indicadorId = ((Instrumentos) paginaInstrumentos.getLista().get(0))
							.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento());
					instrumentoIdFocus = instrumentoId;
					instrumentoEnLaLista = true;

					gestionarInstrumentosForm.setSeleccionados(instrumentoId.toString());
					indicadorId = obtenerIndicadorId(instrumentoId.toString(), paginaInstrumentos,
							gestionarInstrumentosForm);
					claseId = obtenerClaseId(instrumentoId.toString(), paginaInstrumentos, gestionarInstrumentosForm);

					if (indicadorId != null)
						gestionarInstrumentosForm.setIndicadorId(indicadorId.toString());
					indicadorAnioId = obtenerIndicadorAnioId(instrumentoId.toString(), paginaInstrumentos,
							gestionarInstrumentosForm);
					if (indicadorAnioId != null)
						gestionarInstrumentosForm.setIndicadorAnioId(indicadorAnioId.toString());
				}
			}

			if (instrumentoEnLaLista) {
				if (configuracionUsuario == null) {
					configuracionUsuario = new ConfiguracionUsuario();
					ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
					pk.setConfiguracionBase("Strategos.Foco.Instrumento.Lista");
					pk.setObjeto("InstrumentoId");
					pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
					configuracionUsuario.setPk(pk);
				}
				configuracionUsuario.setData(instrumentoIdFocus.toString());
				frameworkService.saveConfiguracionUsuario(configuracionUsuario, this.getUsuarioConectado(request));
			}

			Instrumentos instrumentoSeleccionada = (Instrumentos) strategosInstrumentosService.load(Instrumentos.class,
					new Long(gestionarInstrumentosForm.getSeleccionados()));
			if (instrumentoSeleccionada != null)
				gestionarInstrumentosForm.setSeleccionadoNombre(instrumentoSeleccionada.getNombreCorto());
			else {
				instrumentoId = ((Instrumentos) paginaInstrumentos.getLista().get(0)).getInstrumentoId();
				gestionarInstrumentosForm.setSeleccionadoId(instrumentoId.toString());
				instrumentoSeleccionada = (Instrumentos) strategosInstrumentosService.load(Instrumentos.class,
						new Long(gestionarInstrumentosForm.getSeleccionados()));
				if (instrumentoSeleccionada != null)
					gestionarInstrumentosForm.setSeleccionadoNombre(instrumentoSeleccionada.getNombreCorto());

				ActionMessages messages = getMessages(request);
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("action.reporteportafolio.portafolioeliminado"));
				saveMessages(request, messages);
			}
			frameworkService.close();
		} else {
			gestionarInstrumentosForm.setSeleccionados(null);
		}

		gestionarInstrumentosForm.setConvenios(new ArrayList<TipoConvenio>());

		if (gestionarInstrumentosForm.getTiposConvenioId() != null) {
			gestionarInstrumentosForm.getConvenios().addAll(gestionarInstrumentosForm.getConvenios());
		}

		// tipo convenio

		Map<String, String> filtrosTipoConvenio = new HashMap();
		PaginaLista paginaTipos = strategosTiposConvenioService.getTiposConvenio(0, 0, "tiposConvenioId", "asc", true,
				filtrosTipoConvenio);

		for (Iterator<TipoConvenio> iter = paginaTipos.getLista().iterator(); iter.hasNext();) {
			TipoConvenio tipoProyecto = (TipoConvenio) iter.next();
			gestionarInstrumentosForm.getConvenios().add(tipoProyecto);
		}

		gestionarInstrumentosForm.setCooperantes(new ArrayList<Cooperante>());
		if (gestionarInstrumentosForm.getCooperanteId() != null) {
			gestionarInstrumentosForm.getCooperantes().addAll(gestionarInstrumentosForm.getCooperantes());
		}

		Map<String, String> filtrosCooperantes = new HashMap();
		PaginaLista paginaCooperantes = strategosCooperantesService.getCooperantes(0, 0, "cooperanteId", "asc", true,
				filtrosCooperantes);

		for (Iterator<Cooperante> iter = paginaCooperantes.getLista().iterator(); iter.hasNext();) {
			Cooperante cooperante = (Cooperante) iter.next();
			gestionarInstrumentosForm.getCooperantes().add(cooperante);
		}

		paginaInstrumentos.setTamanoSetPaginas(5);

		request.setAttribute("paginaInstrumentos", paginaInstrumentos);

		GestionarIniciativasForm gestionarIniciativasForm = new GestionarIniciativasForm();
		gestionarIniciativasForm.setSource("Instrumentos");

		strategosInstrumentosService.close();

		return mapping.findForward(forward);
	}

	private Long obtenerIndicadorId(String instrumentoId, PaginaLista paginaInstrumentos,
			GestionarInstrumentosForm gestionarInstrumentosForm) {
		Long indicadorId = null;
		Long instru = Long.parseLong(instrumentoId);
		for (Iterator<Instrumentos> iter = paginaInstrumentos.getLista().iterator(); iter.hasNext();) {
			Instrumentos instrumento = (Instrumentos) iter.next();
			if (instrumento.getInstrumentoId().longValue() == instru.longValue()) {
				indicadorId = instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento());
			}
		}
		return indicadorId;
	}

	private Long obtenerClaseId(String instrumentoId, PaginaLista paginaInstrumentos,
			GestionarInstrumentosForm gestionarInstrumentosForm) {
		Long claseId = null;
		Long instru = Long.parseLong(instrumentoId);
		for (Iterator<Instrumentos> iter = paginaInstrumentos.getLista().iterator(); iter.hasNext();) {
			Instrumentos instrumento = (Instrumentos) iter.next();
			if (instrumento.getInstrumentoId().longValue() == instru.longValue()) {
				claseId = instrumento.getClaseId();
			}
		}
		return claseId;
	}

	private Long obtenerIndicadorAnioId(String instrumentoId, PaginaLista paginaInstrumentos,
			GestionarInstrumentosForm gestionarInstrumentosForm) {
		StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance()
				.openStrategosClasesIndicadoresService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
				.openStrategosIndicadoresService();

		Long indicadorAnioId = null;
		Long instru = Long.parseLong(instrumentoId);
		try {
			for (Iterator<Instrumentos> iter = paginaInstrumentos.getLista().iterator(); iter.hasNext();) {
				Instrumentos instrumento = (Instrumentos) iter.next();
				if (instrumento.getInstrumentoId().longValue() == instru.longValue()) {

					ClaseIndicadores claseIns = (ClaseIndicadores) strategosClasesIndicadoresService
							.load(ClaseIndicadores.class, instrumento.getClaseId());
					Indicador indicador = new Indicador();
					Map<String, Object> filtrosAnio = new HashMap();

					filtrosAnio.put("claseId", claseIns.getPadreId());

					List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true,
							filtrosAnio, null, null, Boolean.valueOf(false)).getLista();
					if (inds.size() > 0) {
						indicador = (Indicador) inds.get(0);
						indicadorAnioId = indicador.getIndicadorId();
					}else{
						indicadorAnioId = null;
					}
				}

			}
		} catch (Throwable t) {
			indicadorAnioId = null;
		}

		return indicadorAnioId;
	}
}
