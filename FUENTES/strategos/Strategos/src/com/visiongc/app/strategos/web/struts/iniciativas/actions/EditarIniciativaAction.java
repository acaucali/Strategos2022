package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.visiongc.app.strategos.cargos.StrategosCargosService;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosFaseProyectoService;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativaEstatusService;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.ResultadoEspecificoIniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.ConfiguracionIniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.FaseProyecto;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus.EstatusType;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.EditarIniciativaForm;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;

public class EditarIniciativaAction extends VgcAction {

	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarIniciativaForm editarIniciativaForm = (EditarIniciativaForm) form;

		forward = getData(editarIniciativaForm, forward, request);

		if (forward.equals("licencia"))
			return this.getForwardBack(request, 1, false);
		if (forward.equals("noencontrado"))
			return getForwardBack(request, 1, true);

		return mapping.findForward(forward);
	}

	public String getData(EditarIniciativaForm editarIniciativaForm, String forward, HttpServletRequest request) {
		ActionMessages messages = getMessages(request);

		Boolean desdeInstrumento = (request.getParameter("desdeInstrumento") != null
				&& request.getParameter("desdeInstrumento") != "")
						? Boolean.valueOf(request.getParameter("desdeInstrumento"))
						: null;

		String iniciativaId = request.getParameter("iniciativaId");
		String planId = request.getParameter("planId");
		Long portafolioId = (request.getParameter("portafolioId") != null && request.getParameter("portafolioId") != "")
				? Long.parseLong(request.getParameter("portafolioId"))
				: null;
		Long instrumentoId = (request.getParameter("instrumentoId") != null
				&& request.getParameter("instrumentoId") != "") ? Long.parseLong(request.getParameter("instrumentoId"))
						: null;

		boolean verForm = getPermisologiaUsuario(request).tienePermiso("INICIATIVA_VIEWALL")
				|| (portafolioId != null && portafolioId != 0L);
		boolean editarForm = getPermisologiaUsuario(request).tienePermiso("INICIATIVA_EDIT")
				&& (portafolioId == null || (portafolioId != null && portafolioId == 0L));
		boolean editInstrumentos = getPermisologiaUsuario(request).tienePermiso("INICIATIVA_EDIT_COOPERACION");

		boolean bloqueado = false;
		Long estatusId = null;

		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance()
				.openStrategosIniciativasService();
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();

		List<?> anos = null;

		if ((iniciativaId != null) && (!iniciativaId.equals("")) && (!iniciativaId.equals("0"))) {
			bloqueado = !strategosIniciativasService.lockForUpdate(request.getSession().getId(), iniciativaId, null);

			Iniciativa iniciativa = (Iniciativa) strategosIniciativasService.load(Iniciativa.class,
					new Long(iniciativaId));

			if (iniciativa != null) {
				OrganizacionStrategos organizacion = (OrganizacionStrategos) strategosIniciativasService
						.load(OrganizacionStrategos.class, new Long(iniciativa.getOrganizacionId()));
				iniciativa.setOrganizacion(organizacion);
				editarIniciativaForm.setOrganizacionId(organizacion.getOrganizacionId());
				editarIniciativaForm.setOrganizacionNombre(organizacion.getNombre());

				editarIniciativaForm.setBloqueado(iniciativa.getSoloLectura());

				if (editarIniciativaForm.getBloqueado())
					bloqueado = true;

				if (editarIniciativaForm.getBloqueado().booleanValue())
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("action.editarregistro.sololectura"));

				if (bloqueado) {
					editarIniciativaForm.setBloqueado(new Boolean(true));
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("action.editarregistro.bloqueado"));
				}

				editarIniciativaForm.setNombre(iniciativa.getNombre());
				editarIniciativaForm.setNombreLargo(iniciativa.getNombreLargo());
				editarIniciativaForm.setTipoAlerta(iniciativa.getTipoAlerta());
				editarIniciativaForm.setEnteEjecutor(iniciativa.getEnteEjecutor());

				editarIniciativaForm.setAlertaZonaVerde(iniciativa.getAlertaZonaVerde());
				editarIniciativaForm.setAlertaZonaAmarilla(iniciativa.getAlertaZonaAmarilla());
				editarIniciativaForm.setFrecuencia(iniciativa.getFrecuencia());

				editarIniciativaForm.setResponsableFijarMetaId(iniciativa.getResponsableFijarMetaId());
				editarIniciativaForm.setResponsableLograrMetaId(iniciativa.getResponsableLograrMetaId());
				editarIniciativaForm.setResponsableSeguimientoId(iniciativa.getResponsableSeguimientoId());
				editarIniciativaForm.setResponsableCargarMetaId(iniciativa.getResponsableCargarMetaId());
				editarIniciativaForm.setResponsableCargarEjecutadoId(iniciativa.getResponsableCargarEjecutadoId());

				if (iniciativa.getResponsableFijarMeta() != null)
					editarIniciativaForm.setResponsableFijarMeta(iniciativa.getResponsableFijarMeta().getNombreCargo());
				else
					editarIniciativaForm.setResponsableFijarMeta(null);

				if (iniciativa.getResponsableLograrMeta() != null)
					editarIniciativaForm
							.setResponsableLograrMeta(iniciativa.getResponsableLograrMeta().getNombreCargo());
				else
					editarIniciativaForm.setResponsableLograrMeta(null);

				if (iniciativa.getResponsableSeguimiento() != null)
					editarIniciativaForm
							.setResponsableSeguimiento(iniciativa.getResponsableSeguimiento().getNombreCargo());
				else
					editarIniciativaForm.setResponsableSeguimiento(null);

				if (iniciativa.getResponsableCargarMeta() != null)
					editarIniciativaForm
							.setResponsableCargarMeta(iniciativa.getResponsableCargarMeta().getNombreCargo());
				else
					editarIniciativaForm.setResponsableCargarMeta(null);

				if (iniciativa.getResponsableCargarEjecutado() != null)
					editarIniciativaForm
							.setResponsableCargarEjecutado(iniciativa.getResponsableCargarEjecutado().getNombreCargo());
				else
					editarIniciativaForm.setResponsableCargarEjecutado(null);

				Integer ano = new Integer(FechaUtil.getAno(new Date()));
				anos = PeriodoUtil.getListaNumeros(ano, new Byte((byte) 5));

				if (iniciativa.getMemoIniciativa() != null) {
					editarIniciativaForm.setDescripcion(iniciativa.getMemoIniciativa().getDescripcion());
					editarIniciativaForm.setResultado(iniciativa.getMemoIniciativa().getResultado());
				} else {
					editarIniciativaForm.setDescripcion(null);
					editarIniciativaForm.setResultado(null);
				}

				String resultadosEspecificos = "";
				int anoTemp = ano.intValue() - 5;
				int anoFinal = ano.intValue() + 5;
				for (Iterator<?> i = iniciativa.getResultadosEspecificosIniciativa().iterator(); i.hasNext();) {
					ResultadoEspecificoIniciativa resultadoEspecificoIniciativa = (ResultadoEspecificoIniciativa) i
							.next();

					int anoEspecifico = resultadoEspecificoIniciativa.getPk().getAno().intValue();
					while (anoTemp < anoEspecifico) {
						resultadosEspecificos = resultadosEspecificos + editarIniciativaForm.getSEPARADOR();
						anoTemp++;
					}

					resultadosEspecificos = resultadosEspecificos + resultadoEspecificoIniciativa.getResultado()
							+ editarIniciativaForm.getSEPARADOR();
					anoTemp++;
				}

				while (anoTemp < anoFinal) {
					resultadosEspecificos = resultadosEspecificos + editarIniciativaForm.getSEPARADOR();
					anoTemp++;
				}
				editarIniciativaForm.setAno(ano);
				editarIniciativaForm.setResultadoEspecificoIniciativa(resultadosEspecificos);
				editarIniciativaForm.setBloqueado(iniciativa.getOrganizacionId()
						.longValue() != new Long((String) request.getSession().getAttribute("organizacionId"))
								.longValue()
						&& !editInstrumentos);
				editarIniciativaForm.setTipoMedicion(iniciativa.getTipoMedicion());
				editarIniciativaForm.setEliminarMediciones(false);
				editarIniciativaForm.setEstatusId(iniciativa.getEstatusId());
				editarIniciativaForm.setEstatus(iniciativa.getEstatus());
				editarIniciativaForm.setTipoId(iniciativa.getTipoId());
				editarIniciativaForm.setTipoProyecto(iniciativa.getTipoProyecto());
				editarIniciativaForm.setAnioFormulacion(iniciativa.getAnioFormulacion());
				editarIniciativaForm.setPorcentajeCompletado(iniciativa.getPorcentajeCompletado());

				// Campos nuevos
				editarIniciativaForm.setResponsableProyecto(iniciativa.getResponsableProyecto());
				editarIniciativaForm.setCargoResponsable(iniciativa.getCargoResponsable());
				editarIniciativaForm.setOrganizacionesInvolucradas(iniciativa.getOrganizacionesInvolucradas());
				editarIniciativaForm.setObjetivoEstrategico(iniciativa.getObjetivoEstrategico());
				editarIniciativaForm.setFuenteFinanciacion(iniciativa.getFuenteFinanciacion());
				editarIniciativaForm.setMontoFinanciamiento(iniciativa.getMontoFinanciamiento());
				editarIniciativaForm.setIniciativaEstrategica(iniciativa.getIniciativaEstrategica());
				editarIniciativaForm.setLiderIniciativa(iniciativa.getLiderIniciativa());
				editarIniciativaForm.setTipoIniciativa(iniciativa.getTipoIniciativa());
				editarIniciativaForm.setPoblacionBeneficiada(iniciativa.getPoblacionBeneficiada());
				editarIniciativaForm.setContexto(iniciativa.getContexto());
				editarIniciativaForm.setDefinicionProblema(iniciativa.getDefinicionProblema());
				editarIniciativaForm.setAlcance(iniciativa.getAlcance());
				editarIniciativaForm.setObjetivoGeneral(iniciativa.getObjetivoGeneral());
				editarIniciativaForm.setObjetivoEspecificos(iniciativa.getObjetivoEspecificos());
				
				editarIniciativaForm.setCodigoIniciativa(iniciativa.getCodigoIniciativa());
				editarIniciativaForm.setCargoId(iniciativa.getCargoId());
				editarIniciativaForm.setUnidad(iniciativa.getUnidadId());
								
				editarIniciativaForm.setFaseId(iniciativa.getFaseId());
				
				editarIniciativaForm.setJustificacion(iniciativa.getJustificacion());
				editarIniciativaForm.setMontoTotal(iniciativa.getMontoTotal());
				editarIniciativaForm.setMontoMonedaExt(iniciativa.getMontoMonedaExt());
				editarIniciativaForm.setSituacionPresupuestaria(iniciativa.getSituacionPresupuestaria());
				editarIniciativaForm.setHitos(iniciativa.getHitos());
				editarIniciativaForm.setSector(iniciativa.getSector());
				editarIniciativaForm.setGerenciaGeneralesRes(iniciativa.getGerenciaGeneralRes());
				editarIniciativaForm.setCodigoSipe(iniciativa.getCodigoSipe());
				editarIniciativaForm.setProyectoPresupAso(iniciativa.getProyectoPresupAso());
				editarIniciativaForm.setEstado(iniciativa.getEstado());
				editarIniciativaForm.setMunicipio(iniciativa.getMunicipio());
				editarIniciativaForm.setParroquia(iniciativa.getParroquia());
				editarIniciativaForm.setDireccionProyecto(iniciativa.getDireccionProyecto());
				editarIniciativaForm.setObjetivoHistorico(iniciativa.getObjetivoHistorico());
				editarIniciativaForm.setObjetivoNacional(iniciativa.getObjetivoNacional());
				editarIniciativaForm.setObjetivoEstrategicoPV(iniciativa.getObjetivoEstrategicoPV());
				editarIniciativaForm.setObjetivoGeneralPV(iniciativa.getObjetivoGeneralPV());
				editarIniciativaForm.setObjetivoEspecifico(iniciativa.getObjetivoEspecifico());
				editarIniciativaForm.setPrograma(iniciativa.getPrograma());
				editarIniciativaForm.setProblemas(iniciativa.getProblemas());
				editarIniciativaForm.setCausas(iniciativa.getCausas());
				editarIniciativaForm.setLineasEstrategicas(iniciativa.getLineasEstrategicas());
				
				editarIniciativaForm.setGerenteProyectoNombre(iniciativa.getGerenteProyectoNombre());
				editarIniciativaForm.setGerenteProyectoCedula(iniciativa.getGerenteProyectoCedula());
				editarIniciativaForm.setGerenteProyectoEmail(iniciativa.getGerenteProyectoEmail());
				editarIniciativaForm.setGerenteProyectoTelefono(iniciativa.getGerenteProyectoTelefono());
				
				editarIniciativaForm.setResponsableTecnicoNombre(iniciativa.getResponsableTecnicoNombre());
				editarIniciativaForm.setResponsableTecnicoCedula(iniciativa.getResponsableTecnicoCedula());
				editarIniciativaForm.setResponsableTecnicoEmail(iniciativa.getResponsableTecnicoEmail());
				editarIniciativaForm.setResponsableTecnicoTelefono(iniciativa.getResponsableTecnicoTelefono());
				
				editarIniciativaForm.setResponsableRegistradorNombre(iniciativa.getResponsableRegistradorNombre());
				editarIniciativaForm.setResponsableRegistradorCedula(iniciativa.getResponsableRegistradorCedula());
				editarIniciativaForm.setResponsableRegistradorEmail(iniciativa.getResponsableRegistradorEmail());
				editarIniciativaForm.setResponsableRegistradorTelefono(iniciativa.getResponsableRegistradorTelefono());
				
				editarIniciativaForm.setResponsableAdministrativoNombre(iniciativa.getResponsableAdministrativoNombre());
				editarIniciativaForm.setResponsableAdministrativoCedula(iniciativa.getResponsableAdministrativoCedula());
				editarIniciativaForm.setResponsableAdministrativoEmail(iniciativa.getResponsableAdministrativoEmail());
				editarIniciativaForm.setResponsableAdministrativoTelefono(iniciativa.getResponsableAdministrativoTelefono());

				editarIniciativaForm.setResponsableAdminContratosNombre(iniciativa.getResponsableAdminContratosNombre());
				editarIniciativaForm.setResponsableAdminContratosCedula(iniciativa.getResponsableAdminContratosCedula());
				editarIniciativaForm.setResponsableAdminContratosEmail(iniciativa.getResponsableAdminContratosEmail());
				editarIniciativaForm.setResponsableAdminContratosTelefono(iniciativa.getResponsableAdminContratosTelefono());
				
				editarIniciativaForm.setAlineacionODS(iniciativa.getAlineacionODS());
				editarIniciativaForm.setAlineacionPDMP(iniciativa.getAlineacionPDMP());
				editarIniciativaForm.setCoberturaGeografica(iniciativa.getCoberturaGeografica());
				editarIniciativaForm.setImpactoCiudadania(iniciativa.getImpactoCiudadania());
				editarIniciativaForm.setImplementadorRecursos(iniciativa.getImplementadorRecursos());
				editarIniciativaForm.setDependenciaResponsable(iniciativa.getDependenciaResponsable());
				editarIniciativaForm.setSostenibilidad(iniciativa.getSostenibilidad());
				editarIniciativaForm.setDependenciasCompetentes(iniciativa.getDependenciasCompetentes());
				
				ConfiguracionIniciativa configuracionIniciativa = strategosIniciativasService.getConfiguracionIniciativa();
				
				if (configuracionIniciativa != null)
				{									
					editarIniciativaForm.setMostrarAdministracionPublica(configuracionIniciativa.getIniciativaAdministracionPublica());
				}				
				
				if (iniciativa.getFechaInicio() != null)
		        	editarIniciativaForm.setFechaInicio(VgcFormatter.formatearFecha(iniciativa.getFechaInicio(), "formato.fecha.corta"));
				else
					editarIniciativaForm.setFechaInicio(null);
				
				if (iniciativa.getFechaFin() != null)
		        	editarIniciativaForm.setFechaFin(VgcFormatter.formatearFecha(iniciativa.getFechaFin(), "formato.fecha.corta"));
				else
					editarIniciativaForm.setFechaFin(null);
				
				if (iniciativa.getFechaActaInicio() != null)
		        	editarIniciativaForm.setFechaActaInicio(VgcFormatter.formatearFecha(iniciativa.getFechaActaInicio(), "formato.fecha.corta"));
				else
					editarIniciativaForm.setFechaActaInicio(null);

				estatusId = new com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions.CalcularActividadesAction()
						.CalcularEstatus(iniciativa.getPorcentajeCompletado());
			} else {
				strategosIniciativasService.unlockObject(request.getSession().getId(),
						editarIniciativaForm.getIniciativaId());

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("action.editarregistro.noencontrado"));
				forward = "noencontrado";
			}
		}
		// si la iniciativa no es nula
		else {
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
					.openStrategosIndicadoresService();
			if (!strategosIndicadoresService.checkLicencia(request)) {
				strategosIndicadoresService.close();

				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("action.guardarregistro.limite.exedido"));
				this.saveMessages(request, messages);
				forward = "licencia";
			}
			strategosIndicadoresService.close();
			editarIniciativaForm.clear();

			int anoActual = FechaUtil.getAno(new Date());
			anos = PeriodoUtil.getListaNumeros(new Integer(anoActual), new Byte((byte) 5));

			if (editarIniciativaForm.getAnioFormulacion() == null) {
				editarIniciativaForm.setAnioFormulacion(Integer.toString(anoActual));
			}

			if (desdeInstrumento != null) {
				if (!desdeInstrumento) {
					OrganizacionStrategos organizacion = (OrganizacionStrategos) strategosIniciativasService.load(
							OrganizacionStrategos.class,
							new Long((String) request.getSession().getAttribute("organizacionId")).longValue());
					editarIniciativaForm.setOrganizacionId(organizacion.getOrganizacionId());
					editarIniciativaForm.setOrganizacionNombre(organizacion.getNombre());
				}
			}
			
			ConfiguracionIniciativa configuracionIniciativa = strategosIniciativasService.getConfiguracionIniciativa();
			
			if (configuracionIniciativa != null)
			{									
				editarIniciativaForm.setMostrarAdministracionPublica(configuracionIniciativa.getIniciativaAdministracionPublica());
			}
		}

		if ((planId != null) && (!planId.equals(""))) {
			Plan plan = (Plan) strategosIniciativasService.load(Plan.class, new Long(planId));
			if (plan != null)
				editarIniciativaForm.setNombreIniciativaSingular(plan.getMetodologia().getNombreIniciativaSingular());
			editarIniciativaForm.setPlanId(new Long(planId));
		}

		editarIniciativaForm.setFrecuencias(Frecuencia.getFrecuencias());
		editarIniciativaForm.setGrupoAnos(anos);
		
		StrategosCargosService strategosCargosService = StrategosServiceFactory.getInstance().openStrategosCargosService();
		Map<String, String> filtrosCargos = new HashMap<String, String>();
		PaginaLista paginaCargos = strategosCargosService.getCargos(0, 0, "id", "asc", true, filtrosCargos);
		editarIniciativaForm.setCargos(paginaCargos.getLista());
		
		StrategosFaseProyectoService strategosFaseProyectoService = StrategosServiceFactory.getInstance().openStrategosFaseProyectoService();
		Map<String, String> filtrosFases = new HashMap<String, String>();
		PaginaLista paginaFases = strategosFaseProyectoService.getFasesProyecto(0, 0, "id", "asc", true, filtrosFases);							
		editarIniciativaForm.setFases(paginaFases.getLista());
		

		// estatus
		editarIniciativaForm.setEstatuses(new ArrayList<IniciativaEstatus>());
		if (editarIniciativaForm.getEstatusId() != null
				&& editarIniciativaForm.getEstatusId().longValue() != EstatusType.getEstatusCencelado().longValue()
				&& editarIniciativaForm.getEstatusId().longValue() != EstatusType.getEstatusSuspendido().longValue())
			editarIniciativaForm.getEstatuses().add(editarIniciativaForm.getEstatus());
		StrategosIniciativaEstatusService strategosIniciativaEstatusService = StrategosServiceFactory.getInstance()
				.openStrategosIniciativaEstatusService();
		Map<String, String> filtros = new HashMap<String, String>();
		PaginaLista paginaIniciativaEstatus = strategosIniciativaEstatusService.getIniciativaEstatus(0, 0, "id", "asc",
				true, filtros);
		strategosIniciativaEstatusService.close();
		IniciativaEstatus estatusProbable = null;
		for (Iterator<IniciativaEstatus> iter = paginaIniciativaEstatus.getLista().iterator(); iter.hasNext();) {
			IniciativaEstatus iniciativaEstatus = iter.next();
			if (iniciativaEstatus.getId().longValue() == EstatusType.getEstatusCencelado().longValue())
				editarIniciativaForm.getEstatuses().add(iniciativaEstatus);
			if (iniciativaEstatus.getId().longValue() == EstatusType.getEstatusSuspendido().longValue())
				editarIniciativaForm.getEstatuses().add(iniciativaEstatus);
			if (estatusId != null && estatusId.longValue() == iniciativaEstatus.getId().longValue())
				estatusProbable = iniciativaEstatus;
		}
		if (estatusProbable != null && editarIniciativaForm.getEstatusId() != null
				&& (editarIniciativaForm.getEstatusId().longValue() == EstatusType.getEstatusCencelado().longValue()
						|| editarIniciativaForm.getEstatusId().longValue() == EstatusType.getEstatusSuspendido()
								.longValue()))
			editarIniciativaForm.getEstatuses().add(estatusProbable);
		else if (estatusProbable == null)
			editarIniciativaForm.getEstatuses().add(EstatusType.setEstatusIniciado());

		// tipo proyecto
		editarIniciativaForm.setTipos(new ArrayList<TipoProyecto>());
		if (editarIniciativaForm.getTipoId() != null)
			editarIniciativaForm.getTipos().addAll(editarIniciativaForm.getTipos());

		if (desdeInstrumento != null && desdeInstrumento) {
			editarIniciativaForm.setDesdeInstrumento(true);
		}

		StrategosTipoProyectoService strategosTiposProyectoService = StrategosServiceFactory.getInstance()
				.openStrategosTipoProyectoService();
		Map<String, String> filtrosTipo = new HashMap();
		PaginaLista paginaTipos = strategosTiposProyectoService.getTiposProyecto(0, 0, "tipoProyectoId", "asc", true,
				filtros);
		strategosTiposProyectoService.close();

		for (Iterator<TipoProyecto> iter = paginaTipos.getLista().iterator(); iter.hasNext();) {
			TipoProyecto tipoProyecto = iter.next();
			editarIniciativaForm.getTipos().add(tipoProyecto);
		}
		
		StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService();
		
		editarIniciativaForm.setUnidadesMedida(strategosUnidadesService.getUnidadesMedida(0, 0, "nombre", "asc", false, null).getLista());

	    strategosUnidadesService.close();

		strategosIniciativasService.close();

		if (!editInstrumentos) {
			if (!bloqueado && verForm && !editarForm) {
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("action.editarregistro.sololectura"));
				editarIniciativaForm.setBloqueado(true);
			} else if (!bloqueado && !verForm && !editarForm)
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("action.editarregistro.sinpermiso"));
		}
		saveMessages(request, messages);

		return forward;
	}	
}
