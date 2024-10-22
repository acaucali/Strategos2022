package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.MemoIniciativa;
import com.visiongc.app.strategos.iniciativas.model.ResultadoEspecificoIniciativa;
import com.visiongc.app.strategos.iniciativas.model.ResultadoEspecificoIniciativaPK;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus.EstatusType;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectiva;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectivaPK;
import com.visiongc.app.strategos.planes.model.IniciativaPlan;
import com.visiongc.app.strategos.planes.model.IniciativaPlanPK;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.EditarIniciativaForm;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.StringUtil;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

public class GuardarIniciativaAction extends VgcAction {
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarIniciativaForm editarIniciativaForm = (EditarIniciativaForm) form;

		ActionMessages messages = getMessages(request);

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		String ts = request.getParameter("ts");
		String ultimoTs = (String) request.getSession().getAttribute("GuardarIniciativaAction.ultimoTs");
		String organizacionId = (String) request.getSession().getAttribute("organizacionId");
		Long instrumentoId = (request.getParameter("instrumentoId") != null
				&& request.getParameter("instrumentoId") != "") ? Long.parseLong(request.getParameter("instrumentoId"))
						: null;
		Boolean desdeInstrumento = (request.getParameter("desdeInstrumento") != null
				&& request.getParameter("desdeInstrumento") != "")
						? Boolean.parseBoolean(request.getParameter("desdeInstrumento"))
						: null;

		int fallos = 0;
		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(ts)))
			cancelar = true;

		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance()
				.openStrategosIniciativasService();

		if (cancelar) {
			strategosIniciativasService.unlockObject(request.getSession().getId(),
					editarIniciativaForm.getIniciativaId());

			strategosIniciativasService.close();

			if (desdeInstrumento != null && desdeInstrumento) {
				return getForwardBack(request, 2, true);
			} else {
				return getForwardBack(request, 1, true);
			}

		}

		boolean nuevo = false;
		Iniciativa iniciativa = null;

		int respuesta = VgcReturnCode.DB_OK;

		if ((editarIniciativaForm.getIniciativaId() != null)
				&& (!editarIniciativaForm.getIniciativaId().equals(Long.valueOf("0"))))
			iniciativa = (Iniciativa) strategosIniciativasService.load(Iniciativa.class,
					editarIniciativaForm.getIniciativaId());
		else {
			nuevo = true;
			iniciativa = new Iniciativa();
			iniciativa.setIniciativaId(new Long(0L));
			iniciativa.setMemoIniciativa(new MemoIniciativa());
			iniciativa.setResultadosEspecificosIniciativa(new HashSet<ResultadoEspecificoIniciativa>());
			iniciativa.setIniciativaPerspectivas(new HashSet<IniciativaPerspectiva>());
			iniciativa.setIniciativaPlanes(new HashSet<IniciativaPlan>());
			if (desdeInstrumento != null && desdeInstrumento) {
				iniciativa.setOrganizacionId(editarIniciativaForm.getOrganizacionId());
			} else {
				iniciativa.setOrganizacionId(new Long(organizacionId));
			}

		}

		iniciativa.setNombre(editarIniciativaForm.getNombre());

		if ((editarIniciativaForm.getNombre() != null) && (!editarIniciativaForm.getNombre().equals("")))
			iniciativa.setNombreLargo(editarIniciativaForm.getNombre());
		else
			iniciativa.setNombreLargo(null);

		if ((editarIniciativaForm.getEnteEjecutor() != null) && (!editarIniciativaForm.getEnteEjecutor().equals("")))
			iniciativa.setEnteEjecutor(editarIniciativaForm.getEnteEjecutor());
		else
			iniciativa.setEnteEjecutor(null);

		if ((editarIniciativaForm.getAnioFormulacion() != null)
				&& (!editarIniciativaForm.getAnioFormulacion().equals("")))
			iniciativa.setAnioFormulacion(editarIniciativaForm.getAnioFormulacion());
		else
			iniciativa.setAnioFormulacion(null);

		// Campos nuevos

		if ((editarIniciativaForm.getResponsableProyecto() != null)
				&& (editarIniciativaForm.getResponsableProyecto() != ""))

			if (editarIniciativaForm.getResponsableProyecto().length() > 150) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.reponsableproyecto"));
				iniciativa.setResponsableProyecto(editarIniciativaForm.getResponsableProyecto().substring(0, 150));
			} else
				iniciativa.setResponsableProyecto(editarIniciativaForm.getResponsableProyecto());

		else
			iniciativa.setResponsableProyecto(null);

		if ((editarIniciativaForm.getCargoResponsable() != null)
				&& (!editarIniciativaForm.getCargoResponsable().equals("")))
			iniciativa.setCargoResponsable(editarIniciativaForm.getCargoResponsable());
		else
			iniciativa.setCargoResponsable(null);

		if ((editarIniciativaForm.getOrganizacionesInvolucradas() != null)
				&& (!editarIniciativaForm.getOrganizacionesInvolucradas().equals("")))
			if (editarIniciativaForm.getOrganizacionesInvolucradas().length() > 500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.organizacionesinvolucradas"));
				iniciativa.setOrganizacionesInvolucradas(editarIniciativaForm.getOrganizacionesInvolucradas().substring(0, 500));
			} else
				iniciativa.setOrganizacionesInvolucradas(editarIniciativaForm.getOrganizacionesInvolucradas());

		else
			iniciativa.setOrganizacionesInvolucradas(null);

		if ((editarIniciativaForm.getObjetivoEstrategico() != null)
				&& (!editarIniciativaForm.getObjetivoEstrategico().equals(""))) {

			if (editarIniciativaForm.getObjetivoEstrategico().length() > 250) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.objetivoestrategico"));
				iniciativa.setObjetivoEstrategico(editarIniciativaForm.getObjetivoEstrategico().substring(0, 250));
			} else
				iniciativa.setObjetivoEstrategico(editarIniciativaForm.getObjetivoEstrategico());
		} else
			iniciativa.setObjetivoEstrategico(null);

		if ((editarIniciativaForm.getFuenteFinanciacion() != null)
				&& (!editarIniciativaForm.getFuenteFinanciacion().equals("")))
			if (editarIniciativaForm.getFuenteFinanciacion().length() > 50) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.fuentefinanciacion"));
				iniciativa.setFuenteFinanciacion(editarIniciativaForm.getFuenteFinanciacion().substring(0, 50));
			} else
				iniciativa.setFuenteFinanciacion(editarIniciativaForm.getFuenteFinanciacion());

		else
			iniciativa.setFuenteFinanciacion(null);

		if ((editarIniciativaForm.getMontoFinanciamiento() != null)
				&& (!editarIniciativaForm.getMontoFinanciamiento().equals("")))
			if (editarIniciativaForm.getMontoFinanciamiento().length() > 50) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.monto"));
				iniciativa.setMontoFinanciamiento(editarIniciativaForm.getMontoFinanciamiento().substring(0, 50));
			} else
				iniciativa.setMontoFinanciamiento(editarIniciativaForm.getMontoFinanciamiento());

		else
			iniciativa.setMontoFinanciamiento(null);

		if ((editarIniciativaForm.getIniciativaEstrategica() != null)
				&& (!editarIniciativaForm.getIniciativaEstrategica().equals("")))
			if (editarIniciativaForm.getIniciativaEstrategica().length() > 500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.iniciativaestrategica"));
				iniciativa.setIniciativaEstrategica(editarIniciativaForm.getIniciativaEstrategica().substring(0, 500));
			} else
				iniciativa.setIniciativaEstrategica(editarIniciativaForm.getIniciativaEstrategica());

		else
			iniciativa.setIniciativaEstrategica(null);

		if ((editarIniciativaForm.getLiderIniciativa() != null)
				&& (!editarIniciativaForm.getLiderIniciativa().equals("")))
			if (editarIniciativaForm.getLiderIniciativa().length() > 250) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.lideriniciativa"));
				iniciativa.setLiderIniciativa(editarIniciativaForm.getLiderIniciativa().substring(0, 250));
			} else
				iniciativa.setLiderIniciativa(editarIniciativaForm.getLiderIniciativa());
		else
			iniciativa.setLiderIniciativa(null);

		if ((editarIniciativaForm.getTipoIniciativa() != null)
				&& (!editarIniciativaForm.getTipoIniciativa().equals("")))
			if (editarIniciativaForm.getTipoIniciativa().length() > 250) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.tipoiniciativa"));
				iniciativa.setTipoIniciativa(editarIniciativaForm.getTipoIniciativa().substring(0, 250));
			} else
				iniciativa.setTipoIniciativa(editarIniciativaForm.getTipoIniciativa());

		else
			iniciativa.setTipoIniciativa(null);

		if ((editarIniciativaForm.getPoblacionBeneficiada() != null)
				&& (!editarIniciativaForm.getPoblacionBeneficiada().equals("")))
			if (editarIniciativaForm.getPoblacionBeneficiada().length() > 450) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.poblacionbeneficiada"));
				iniciativa.setPoblacionBeneficiada(editarIniciativaForm.getPoblacionBeneficiada().substring(0, 450));
			} else
				iniciativa.setPoblacionBeneficiada(editarIniciativaForm.getPoblacionBeneficiada());
		else
			iniciativa.setPoblacionBeneficiada(null);

		if ((editarIniciativaForm.getContexto() != null) && (!editarIniciativaForm.getContexto().equals("")))
			if (editarIniciativaForm.getContexto().length() > 450) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.contexto"));
				iniciativa.setContexto(editarIniciativaForm.getContexto().substring(0, 450));
			} else
				iniciativa.setContexto(editarIniciativaForm.getContexto());

		else
			iniciativa.setContexto(null);

		if ((editarIniciativaForm.getDefinicionProblema() != null)
				&& (!editarIniciativaForm.getDefinicionProblema().equals("")))
			if (editarIniciativaForm.getDefinicionProblema().length() > 1500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.definicionproblema"));
				iniciativa.setDefinicionProblema(editarIniciativaForm.getDefinicionProblema().substring(0, 1500));
			} else
				iniciativa.setDefinicionProblema(editarIniciativaForm.getDefinicionProblema());

		else
			iniciativa.setDefinicionProblema(null);

		if ((editarIniciativaForm.getAlcance() != null) && (!editarIniciativaForm.getAlcance().equals("")))
			if (editarIniciativaForm.getAlcance().length() > 1500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.alcance"));
				iniciativa.setAlcance(editarIniciativaForm.getAlcance().substring(0, 1500));
			} else
				iniciativa.setAlcance(editarIniciativaForm.getAlcance());

		else
			iniciativa.setAlcance(null);

		if ((editarIniciativaForm.getObjetivoGeneral() != null)
				&& (!editarIniciativaForm.getObjetivoGeneral().equals("")))
			if (editarIniciativaForm.getObjetivoGeneral().length() > 1500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.objetivogeneral"));
				iniciativa.setObjetivoGeneral(editarIniciativaForm.getObjetivoGeneral().substring(0, 1500));
			} else
				iniciativa.setObjetivoGeneral(editarIniciativaForm.getObjetivoGeneral());

		else
			iniciativa.setObjetivoGeneral(null);

		if ((editarIniciativaForm.getObjetivoEspecificos() != null)
				&& (!editarIniciativaForm.getObjetivoEspecificos().equals("")))
			if(editarIniciativaForm.getObjetivoEspecificos().length() > 1500 ) {		
				if(fallos == 0){
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;
				
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("jsp.editariniciativa.ficha.objetivosespecificos"));
				iniciativa.setObjetivoEspecificos(editarIniciativaForm.getObjetivoEspecificos().substring(0,1500));											
			}
			else
				iniciativa.setObjetivoEspecificos(editarIniciativaForm.getObjetivoEspecificos());
				
		else
			iniciativa.setObjetivoEspecificos(null);

		String selectTipoProyecto = request.getParameter("selectTipoProyecto");

		if (selectTipoProyecto != null && !selectTipoProyecto.equals("") && !selectTipoProyecto.equals("0")) {
			iniciativa.setTipoId(Long.parseLong(selectTipoProyecto));
		} else {
			iniciativa.setTipoId(null);
		}

		String selectCargo = request.getParameter("selectCargo");

		if (selectCargo != null && !selectCargo.equals("") && !selectCargo.equals("0")) {
			iniciativa.setCargoId(Long.parseLong(selectCargo));
		} else {
			iniciativa.setCargoId(null);
		}

		if ((editarIniciativaForm.getCodigoIniciativa() != null)
				&& (!editarIniciativaForm.getCodigoIniciativa().equals("")))
			iniciativa.setCodigoIniciativa(editarIniciativaForm.getCodigoIniciativa());
		else
			iniciativa.setCodigoIniciativa(null);

		if ((editarIniciativaForm.getJustificacion() != null) && (!editarIniciativaForm.getJustificacion().equals("")))
			if (editarIniciativaForm.getJustificacion().length() > 500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.justificacion"));
				iniciativa.setJustificacion(editarIniciativaForm.getJustificacion().substring(0, 500));
			} else
				iniciativa.setJustificacion(editarIniciativaForm.getJustificacion());

		else
			iniciativa.setJustificacion(null);

		if ((editarIniciativaForm.getMontoTotal() != null) && (!editarIniciativaForm.getMontoTotal().equals("")))
			if (editarIniciativaForm.getMontoTotal().length() > 50) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.monto.total"));
				iniciativa.setMontoTotal(editarIniciativaForm.getMontoTotal().substring(0, 50));
			} else
				iniciativa.setMontoTotal(editarIniciativaForm.getMontoTotal());
				
		else
			iniciativa.setMontoTotal(null);

		if ((editarIniciativaForm.getMontoMonedaExt() != null)
				&& (!editarIniciativaForm.getMontoMonedaExt().equals("")))
			if (editarIniciativaForm.getMontoMonedaExt().length() > 50) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.monto.moneda.extranjera"));
				iniciativa.setMontoMonedaExt(editarIniciativaForm.getMontoMonedaExt().substring(0, 50));
			} else
				iniciativa.setMontoMonedaExt(editarIniciativaForm.getMontoMonedaExt());

		else
			iniciativa.setMontoMonedaExt(null);

		if ((editarIniciativaForm.getSituacionPresupuestaria() != null)
				&& (!editarIniciativaForm.getSituacionPresupuestaria().equals("")))
			if (editarIniciativaForm.getSituacionPresupuestaria().length() > 150) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.situacion.presupuestaria"));
				iniciativa.setSituacionPresupuestaria(editarIniciativaForm.getSituacionPresupuestaria().substring(0, 150));
			} else
				iniciativa.setSituacionPresupuestaria(editarIniciativaForm.getSituacionPresupuestaria());				
		else
			iniciativa.setSituacionPresupuestaria(null);

		if ((editarIniciativaForm.getHitos() != null) && (!editarIniciativaForm.getHitos().equals("")))
			if (editarIniciativaForm.getHitos().length() > 500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.hitos"));
				iniciativa.setHitos(editarIniciativaForm.getHitos().substring(0, 500));
			} else
				iniciativa.setHitos(editarIniciativaForm.getHitos());
					
		else
			iniciativa.setHitos(null);

		if ((editarIniciativaForm.getSector() != null) && (!editarIniciativaForm.getSector().equals("")))
			if (editarIniciativaForm.getSector().length() > 150) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.sector"));
				iniciativa.setSector(editarIniciativaForm.getSector().substring(0, 150));
			} else
				iniciativa.setSector(editarIniciativaForm.getSector());
				
		else
			iniciativa.setSector(null);

		if ((editarIniciativaForm.getGerenciaGeneralesRes() != null)
				&& (!editarIniciativaForm.getGerenciaGeneralesRes().equals("")))
			if (editarIniciativaForm.getGerenciaGeneralesRes().length() > 150) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.responsable.gerencia.general"));
				iniciativa.setGerenciaGeneralRes(editarIniciativaForm.getGerenciaGeneralesRes().substring(0, 150));
			} else
				iniciativa.setGerenciaGeneralRes(editarIniciativaForm.getGerenciaGeneralesRes());

		else
			iniciativa.setGerenciaGeneralRes(null);

		if ((editarIniciativaForm.getCodigoSipe() != null) && (!editarIniciativaForm.getCodigoSipe().equals("")))
			if (editarIniciativaForm.getCodigoSipe().length() > 50) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.codigo.sipe"));
				iniciativa.setCodigoSipe(editarIniciativaForm.getCodigoSipe().substring(0, 50));
			} else
				iniciativa.setCodigoSipe(editarIniciativaForm.getCodigoSipe());

		else
			iniciativa.setCodigoSipe(null);

		if ((editarIniciativaForm.getProyectoPresupAso() != null)
				&& (!editarIniciativaForm.getProyectoPresupAso().equals("")))
			if (editarIniciativaForm.getProyectoPresupAso().length() > 300) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.proyecto.presupuestario"));
				iniciativa.setProyectoPresupAso(editarIniciativaForm.getProyectoPresupAso().substring(0, 300));
			} else
				iniciativa.setProyectoPresupAso(editarIniciativaForm.getProyectoPresupAso());
				
		else
			iniciativa.setProyectoPresupAso(null);

		if ((editarIniciativaForm.getEstado() != null) && (!editarIniciativaForm.getEstado().equals("")))
			if (editarIniciativaForm.getEstado().length() > 100) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.estado"));
				iniciativa.setEstado(editarIniciativaForm.getEstado().substring(0, 100));
			} else
				iniciativa.setEstado(editarIniciativaForm.getEstado());

		else
			iniciativa.setEstado(null);

		if ((editarIniciativaForm.getMunicipio() != null) && (!editarIniciativaForm.getMunicipio().equals("")))
			if (editarIniciativaForm.getMunicipio().length() > 300) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.municipio"));
				iniciativa.setMunicipio(editarIniciativaForm.getMunicipio().substring(0, 300));
			} else
				iniciativa.setMunicipio(editarIniciativaForm.getMunicipio());
				
		else
			iniciativa.setMunicipio(null);

		if ((editarIniciativaForm.getParroquia() != null) && (!editarIniciativaForm.getParroquia().equals("")))
			if (editarIniciativaForm.getParroquia().length() > 300) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.parroquia"));
				iniciativa.setParroquia(editarIniciativaForm.getParroquia().substring(0, 300));
			} else
				iniciativa.setParroquia(editarIniciativaForm.getParroquia());

		else
			iniciativa.setParroquia(null);

		if ((editarIniciativaForm.getDireccionProyecto() != null)
				&& (!editarIniciativaForm.getDireccionProyecto().equals("")))
			if (editarIniciativaForm.getDireccionProyecto().length() > 500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.direccion"));
				iniciativa.setDireccionProyecto(editarIniciativaForm.getDireccionProyecto().substring(0, 500));
			} else
				iniciativa.setDireccionProyecto(editarIniciativaForm.getDireccionProyecto());

		else
			iniciativa.setDireccionProyecto(null);

		if ((editarIniciativaForm.getObjetivoHistorico() != null)
				&& (!editarIniciativaForm.getObjetivoHistorico().equals("")))
			if (editarIniciativaForm.getObjetivoHistorico().length() > 500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.objetivohistorico"));
				iniciativa.setObjetivoHistorico(editarIniciativaForm.getObjetivoHistorico().substring(0, 500));
			} else
				iniciativa.setObjetivoHistorico(editarIniciativaForm.getObjetivoHistorico());

		else
			iniciativa.setObjetivoHistorico(null);

		if ((editarIniciativaForm.getObjetivoNacional() != null)
				&& (!editarIniciativaForm.getObjetivoNacional().equals("")))
			if (editarIniciativaForm.getObjetivoNacional().length() > 1500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.objetivonacional"));
				iniciativa.setObjetivoNacional(editarIniciativaForm.getObjetivoNacional().substring(0, 1500));
			} else
				iniciativa.setObjetivoNacional(editarIniciativaForm.getObjetivoNacional());

		else
			iniciativa.setObjetivoNacional(null);

		if ((editarIniciativaForm.getObjetivoEstrategicoPV() != null)
				&& (!editarIniciativaForm.getObjetivoEstrategicoPV().equals("")))
			if (editarIniciativaForm.getObjetivoEstrategicoPV().length() > 1500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.objetivoestrategicopv"));
				iniciativa.setObjetivoEstrategicoPV(editarIniciativaForm.getObjetivoEstrategicoPV().substring(0, 1500));
			} else
				iniciativa.setObjetivoEstrategicoPV(editarIniciativaForm.getObjetivoEstrategicoPV());
				
		else
			iniciativa.setObjetivoEstrategicoPV(null);

		if ((editarIniciativaForm.getObjetivoGeneralPV() != null)
				&& (!editarIniciativaForm.getObjetivoGeneralPV().equals("")))
			if (editarIniciativaForm.getObjetivoGeneralPV().length() > 1500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.objetivogeneralpv"));
				iniciativa.setObjetivoGeneralPV(editarIniciativaForm.getObjetivoGeneralPV().substring(0, 1500));
			} else
				iniciativa.setObjetivoGeneralPV(editarIniciativaForm.getObjetivoGeneralPV());
				
		else
			iniciativa.setObjetivoGeneralPV(null);

		if ((editarIniciativaForm.getObjetivoEspecifico() != null)
				&& (!editarIniciativaForm.getObjetivoEspecifico().equals("")))
			if (editarIniciativaForm.getObjetivoEspecifico().length() > 1500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.objetivoespecifico"));
				iniciativa.setObjetivoEspecifico(editarIniciativaForm.getObjetivoEspecifico().substring(0, 1500));
			} else
				iniciativa.setObjetivoEspecifico(editarIniciativaForm.getObjetivoEspecifico());
				
		else
			iniciativa.setObjetivoEspecifico(null);

		if ((editarIniciativaForm.getPrograma() != null) && (!editarIniciativaForm.getPrograma().equals("")))
			if (editarIniciativaForm.getPrograma().length() > 500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.programa"));
				iniciativa.setPrograma(editarIniciativaForm.getPrograma().substring(0, 500));
			} else
				iniciativa.setPrograma(editarIniciativaForm.getPrograma());

		else
			iniciativa.setPrograma(null);

		if ((editarIniciativaForm.getProblemas() != null) && (!editarIniciativaForm.getProblemas().equals("")))
			if (editarIniciativaForm.getProblemas().length() > 750) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.problemas"));
				iniciativa.setProblemas(editarIniciativaForm.getProblemas().substring(0, 750));
			} else
				iniciativa.setProblemas(editarIniciativaForm.getProblemas());

		else
			iniciativa.setProblemas(null);

		if ((editarIniciativaForm.getCausas() != null) && (!editarIniciativaForm.getCausas().equals("")))
			if (editarIniciativaForm.getCausas().length() > 500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.causas"));
				iniciativa.setCausas(editarIniciativaForm.getCausas().substring(0, 500));
			} else
				iniciativa.setCausas(editarIniciativaForm.getCausas());

		else
			iniciativa.setCausas(null);

		if ((editarIniciativaForm.getLineasEstrategicas() != null)
				&& (!editarIniciativaForm.getLineasEstrategicas().equals("")))
			if (editarIniciativaForm.getLineasEstrategicas().length() > 500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.lineasestrategicas"));
				iniciativa.setLineasEstrategicas(editarIniciativaForm.getLineasEstrategicas().substring(0, 500));
			} else
				iniciativa.setLineasEstrategicas(editarIniciativaForm.getLineasEstrategicas());

		else
			iniciativa.setLineasEstrategicas(null);

		if ((editarIniciativaForm.getGerenteProyectoNombre() != null)
				&& (!editarIniciativaForm.getGerenteProyectoNombre().equals("")))
			iniciativa.setGerenteProyectoNombre(editarIniciativaForm.getGerenteProyectoNombre());
		else
			iniciativa.setGerenteProyectoNombre(null);

		if ((editarIniciativaForm.getGerenteProyectoCedula() != null)
				&& (!editarIniciativaForm.getGerenteProyectoCedula().equals("")))
			iniciativa.setGerenteProyectoCedula(editarIniciativaForm.getGerenteProyectoCedula());
		else
			iniciativa.setGerenteProyectoCedula(null);

		if ((editarIniciativaForm.getGerenteProyectoEmail() != null)
				&& (!editarIniciativaForm.getGerenteProyectoEmail().equals("")))
			iniciativa.setGerenteProyectoEmail(editarIniciativaForm.getGerenteProyectoEmail());
		else
			iniciativa.setGerenteProyectoEmail(null);

		if ((editarIniciativaForm.getGerenteProyectoTelefono() != null)
				&& (!editarIniciativaForm.getGerenteProyectoTelefono().equals("")))
			iniciativa.setGerenteProyectoTelefono(editarIniciativaForm.getGerenteProyectoTelefono());
		else
			iniciativa.setGerenteProyectoTelefono(null);

		if ((editarIniciativaForm.getResponsableTecnicoNombre() != null)
				&& (!editarIniciativaForm.getResponsableTecnicoNombre().equals("")))
			iniciativa.setResponsableTecnicoNombre(editarIniciativaForm.getResponsableTecnicoNombre());
		else
			iniciativa.setResponsableTecnicoNombre(null);

		if ((editarIniciativaForm.getResponsableTecnicoCedula() != null)
				&& (!editarIniciativaForm.getResponsableTecnicoCedula().equals("")))
			iniciativa.setResponsableTecnicoCedula(editarIniciativaForm.getResponsableTecnicoCedula());
		else
			iniciativa.setResponsableTecnicoCedula(null);

		if ((editarIniciativaForm.getResponsableTecnicoEmail() != null)
				&& (!editarIniciativaForm.getResponsableTecnicoEmail().equals("")))
			iniciativa.setResponsableTecnicoEmail(editarIniciativaForm.getResponsableTecnicoEmail());
		else
			iniciativa.setResponsableTecnicoEmail(null);

		if ((editarIniciativaForm.getResponsableTecnicoTelefono() != null)
				&& (!editarIniciativaForm.getResponsableTecnicoTelefono().equals("")))
			iniciativa.setResponsableTecnicoTelefono(editarIniciativaForm.getResponsableTecnicoTelefono());
		else
			iniciativa.setResponsableTecnicoTelefono(null);

		if ((editarIniciativaForm.getResponsableRegistradorNombre() != null)
				&& (!editarIniciativaForm.getResponsableRegistradorNombre().equals("")))
			iniciativa.setResponsableRegistradorNombre(editarIniciativaForm.getResponsableRegistradorNombre());
		else
			iniciativa.setResponsableRegistradorNombre(null);

		if ((editarIniciativaForm.getResponsableRegistradorCedula() != null)
				&& (!editarIniciativaForm.getResponsableRegistradorCedula().equals("")))
			iniciativa.setResponsableRegistradorCedula(editarIniciativaForm.getResponsableRegistradorCedula());
		else
			iniciativa.setResponsableRegistradorCedula(null);

		if ((editarIniciativaForm.getResponsableRegistradorEmail() != null)
				&& (!editarIniciativaForm.getResponsableRegistradorEmail().equals("")))
			iniciativa.setResponsableRegistradorEmail(editarIniciativaForm.getResponsableRegistradorEmail());
		else
			iniciativa.setResponsableRegistradorEmail(null);

		if ((editarIniciativaForm.getResponsableRegistradorTelefono() != null)
				&& (!editarIniciativaForm.getResponsableRegistradorTelefono().equals("")))
			iniciativa.setResponsableRegistradorTelefono(editarIniciativaForm.getResponsableRegistradorTelefono());
		else
			iniciativa.setResponsableRegistradorTelefono(null);

		if ((editarIniciativaForm.getResponsableAdministrativoNombre() != null)
				&& (!editarIniciativaForm.getResponsableAdministrativoNombre().equals("")))
			iniciativa.setResponsableAdministrativoNombre(editarIniciativaForm.getResponsableAdministrativoNombre());
		else
			iniciativa.setResponsableAdministrativoNombre(null);

		if ((editarIniciativaForm.getResponsableAdministrativoCedula() != null)
				&& (!editarIniciativaForm.getResponsableAdministrativoCedula().equals("")))
			iniciativa.setResponsableAdministrativoCedula(editarIniciativaForm.getResponsableAdministrativoCedula());
		else
			iniciativa.setResponsableAdministrativoCedula(null);

		if ((editarIniciativaForm.getResponsableAdministrativoEmail() != null)
				&& (!editarIniciativaForm.getResponsableAdministrativoEmail().equals("")))
			iniciativa.setResponsableAdministrativoEmail(editarIniciativaForm.getResponsableAdministrativoEmail());
		else
			iniciativa.setResponsableAdministrativoEmail(null);

		if ((editarIniciativaForm.getResponsableAdministrativoTelefono() != null)
				&& (!editarIniciativaForm.getResponsableAdministrativoTelefono().equals("")))
			iniciativa
					.setResponsableAdministrativoTelefono(editarIniciativaForm.getResponsableAdministrativoTelefono());
		else
			iniciativa.setResponsableAdministrativoTelefono(null);

		if ((editarIniciativaForm.getResponsableAdminContratosNombre() != null)
				&& (!editarIniciativaForm.getResponsableAdminContratosNombre().equals("")))
			iniciativa.setResponsableAdminContratosNombre(editarIniciativaForm.getResponsableAdminContratosNombre());
		else
			iniciativa.setResponsableAdminContratosNombre(null);

		if ((editarIniciativaForm.getResponsableAdminContratosCedula() != null)
				&& (!editarIniciativaForm.getResponsableAdminContratosCedula().equals("")))
			iniciativa.setResponsableAdminContratosCedula(editarIniciativaForm.getResponsableAdminContratosCedula());
		else
			iniciativa.setResponsableAdminContratosCedula(null);

		if ((editarIniciativaForm.getResponsableAdminContratosEmail() != null)
				&& (!editarIniciativaForm.getResponsableAdminContratosEmail().equals("")))
			iniciativa.setResponsableAdminContratosEmail(editarIniciativaForm.getResponsableAdminContratosEmail());
		else
			iniciativa.setResponsableAdminContratosEmail(null);

		if ((editarIniciativaForm.getResponsableAdminContratosTelefono() != null)
				&& (!editarIniciativaForm.getResponsableAdminContratosTelefono().equals("")))
			iniciativa
					.setResponsableAdminContratosTelefono(editarIniciativaForm.getResponsableAdminContratosTelefono());
		else
			iniciativa.setResponsableAdminContratosTelefono(null);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		if ((editarIniciativaForm.getFechaInicio() != null) && (!editarIniciativaForm.getFechaInicio().equals(""))) {
			Calendar calFechaInicio = Calendar.getInstance();
			calFechaInicio.setTime(simpleDateFormat.parse(editarIniciativaForm.getFechaInicio()));
			calFechaInicio = PeriodoUtil.inicioDelDia(calFechaInicio);
			iniciativa.setFechaInicio(calFechaInicio.getTime());
		} else {
			iniciativa.setFechaInicio(null);
		}

		if ((editarIniciativaForm.getFechaFin() != null) && (!editarIniciativaForm.getFechaFin().equals(""))) {
			Calendar calFechaFin = Calendar.getInstance();
			calFechaFin.setTime(simpleDateFormat.parse(editarIniciativaForm.getFechaFin()));
			calFechaFin = PeriodoUtil.inicioDelDia(calFechaFin);
			iniciativa.setFechaFin(calFechaFin.getTime());
		} else {
			iniciativa.setFechaFin(null);
		}

		if ((editarIniciativaForm.getFechaActaInicio() != null)
				&& (!editarIniciativaForm.getFechaActaInicio().equals(""))) {
			Calendar calFechaActaInicio = Calendar.getInstance();
			calFechaActaInicio.setTime(simpleDateFormat.parse(editarIniciativaForm.getFechaActaInicio()));
			calFechaActaInicio = PeriodoUtil.inicioDelDia(calFechaActaInicio);
			iniciativa.setFechaActaInicio(calFechaActaInicio.getTime());
		} else {
			iniciativa.setFechaActaInicio(null);
		}

		if (editarIniciativaForm.getAlertaZonaAmarilla() != null
				&& editarIniciativaForm.getHayValorPorcentajeAmarillo())
			iniciativa.setAlertaZonaAmarilla(editarIniciativaForm.getAlertaZonaAmarilla());
		else
			iniciativa.setAlertaZonaAmarilla(null);

		if (editarIniciativaForm.getAlertaZonaVerde() != null && editarIniciativaForm.getHayValorPorcentajeVerde())
			iniciativa.setAlertaZonaVerde(editarIniciativaForm.getAlertaZonaVerde());
		else
			iniciativa.setAlertaZonaVerde(null);

		Boolean cambioFrecuencia = (iniciativa != null && iniciativa.getFrecuencia() != null
				&& iniciativa.getFrecuencia().byteValue() != editarIniciativaForm.getFrecuencia().byteValue());
		if (cambioFrecuencia) {
			iniciativa.setFechaUltimaMedicion(null);
			iniciativa.setPorcentajeCompletado(null);
			iniciativa.setAlerta(null);
		}
		iniciativa.setFrecuencia(editarIniciativaForm.getFrecuencia());

		if (editarIniciativaForm.getResponsableFijarMetaId().equals(new Long(0L)))
			iniciativa.setResponsableFijarMetaId(null);
		else
			iniciativa.setResponsableFijarMetaId(editarIniciativaForm.getResponsableFijarMetaId());

		if (editarIniciativaForm.getResponsableLograrMetaId().equals(new Long(0L)))
			iniciativa.setResponsableLograrMetaId(null);
		else
			iniciativa.setResponsableLograrMetaId(editarIniciativaForm.getResponsableLograrMetaId());

		if (editarIniciativaForm.getResponsableSeguimientoId().equals(new Long(0L)))
			iniciativa.setResponsableSeguimientoId(null);
		else
			iniciativa.setResponsableSeguimientoId(editarIniciativaForm.getResponsableSeguimientoId());

		if (editarIniciativaForm.getResponsableCargarMetaId().equals(new Long(0L)))
			iniciativa.setResponsableCargarMetaId(null);
		else
			iniciativa.setResponsableCargarMetaId(editarIniciativaForm.getResponsableCargarMetaId());

		if (editarIniciativaForm.getResponsableCargarEjecutadoId().equals(new Long(0L)))
			iniciativa.setResponsableCargarEjecutadoId(null);
		else
			iniciativa.setResponsableCargarEjecutadoId(editarIniciativaForm.getResponsableCargarEjecutadoId());

		iniciativa.setTipoAlerta((byte) 0); // Calculo por alerta

		if ((editarIniciativaForm.getDescripcion() != null) && (!editarIniciativaForm.getDescripcion().equals("")))
			if (editarIniciativaForm.getDescripcion().length() > 1500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.descripcion"));
				iniciativa.getMemoIniciativa().setDescripcion(editarIniciativaForm.getDescripcion().substring(0, 1500));				
			} else
				iniciativa.getMemoIniciativa().setDescripcion(editarIniciativaForm.getDescripcion());
				
		else if (iniciativa.getMemoIniciativa() != null)
			iniciativa.getMemoIniciativa().setDescripcion(null);

		if ((editarIniciativaForm.getResultado() != null) && (!editarIniciativaForm.getResultado().equals("")))
			if (editarIniciativaForm.getSituacionPresupuestaria().length() > 150) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.situacion.presupuestaria"));
				iniciativa.getMemoIniciativa().setResultado(editarIniciativaForm.getResultado().substring(0, 150));				
			} else
				iniciativa.getMemoIniciativa().setResultado(editarIniciativaForm.getResultado());
	
		else if (iniciativa.getMemoIniciativa() != null)
			iniciativa.getMemoIniciativa().setResultado(null);
		
		
		
		if ((editarIniciativaForm.getAlineacionPDMP() != null)
				&& (!editarIniciativaForm.getAlineacionPDMP().equals("")))
			if (editarIniciativaForm.getAlineacionPDMP().length() > 500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.alineacionpdmp"));
				iniciativa.setAlineacionPDMP(editarIniciativaForm.getAlineacionPDMP().substring(0, 500));
			} else
				iniciativa.setAlineacionPDMP(editarIniciativaForm.getAlineacionPDMP());
		else
			iniciativa.setAlineacionPDMP(null);
		
		
		
		if ((editarIniciativaForm.getAlineacionODS() != null)
				&& (!editarIniciativaForm.getAlineacionODS().equals("")))
			if (editarIniciativaForm.getAlineacionODS().length() > 500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.alineacionods"));
				iniciativa.setAlineacionODS(editarIniciativaForm.getAlineacionODS().substring(0, 500));
			} else
				iniciativa.setAlineacionODS(editarIniciativaForm.getAlineacionODS());
		else
			iniciativa.setAlineacionODS(null);
		
		if ((editarIniciativaForm.getImpactoCiudadania() != null)
				&& (!editarIniciativaForm.getImpactoCiudadania().equals("")))
			if (editarIniciativaForm.getImpactoCiudadania().length() > 500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.impactociudadania"));
				iniciativa.setImpactoCiudadania(editarIniciativaForm.getImpactoCiudadania().substring(0, 500));
			} else
				iniciativa.setImpactoCiudadania(editarIniciativaForm.getImpactoCiudadania());
		else
			iniciativa.setImpactoCiudadania(null);
		
		if ((editarIniciativaForm.getImplementadorRecursos() != null)
				&& (!editarIniciativaForm.getImplementadorRecursos().equals("")))
			if (editarIniciativaForm.getImplementadorRecursos().length() > 500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.implementadorrecursos"));
				iniciativa.setImplementadorRecursos(editarIniciativaForm.getImplementadorRecursos().substring(0, 500));
			} else
				iniciativa.setImplementadorRecursos(editarIniciativaForm.getImplementadorRecursos());
		else
			iniciativa.setImplementadorRecursos(null);
		
		if ((editarIniciativaForm.getCoberturaGeografica() != null)
				&& (!editarIniciativaForm.getCoberturaGeografica().equals("")))
			if (editarIniciativaForm.getCoberturaGeografica().length() > 500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.coberturageografica"));
				iniciativa.setCoberturaGeografica(editarIniciativaForm.getCoberturaGeografica().substring(0, 500));
			} else
				iniciativa.setCoberturaGeografica(editarIniciativaForm.getCoberturaGeografica());
		else
			iniciativa.setCoberturaGeografica(null);
		
		if ((editarIniciativaForm.getDependenciaResponsable() != null)
				&& (!editarIniciativaForm.getDependenciaResponsable().equals("")))
			if (editarIniciativaForm.getDependenciaResponsable().length() > 500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.dependenciaresponsable"));
				iniciativa.setDependenciaResponsable(editarIniciativaForm.getDependenciaResponsable().substring(0, 500));
			} else
				iniciativa.setDependenciaResponsable(editarIniciativaForm.getDependenciaResponsable());
		else
			iniciativa.setDependenciaResponsable(null);
		
		
		if ((editarIniciativaForm.getDependenciasCompetentes() != null)
				&& (!editarIniciativaForm.getDependenciasCompetentes().equals("")))
			if (editarIniciativaForm.getDependenciasCompetentes().length() > 500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.dependenciascompetentes"));
				iniciativa.setDependenciasCompetentes(editarIniciativaForm.getDependenciasCompetentes().substring(0, 500));
			} else
				iniciativa.setDependenciasCompetentes(editarIniciativaForm.getDependenciasCompetentes());
		else
			iniciativa.setDependenciasCompetentes(null);
		
		if ((editarIniciativaForm.getSostenibilidad() != null)
				&& (!editarIniciativaForm.getSostenibilidad().equals("")))
			if (editarIniciativaForm.getSostenibilidad().length() > 500) {
				if (fallos == 0) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("campos.maximo.ajustado"));
				}
				fallos++;

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.editariniciativa.ficha.sostenibilidad"));
				iniciativa.setSostenibilidad(editarIniciativaForm.getSostenibilidad().substring(0, 500));
			} else
				iniciativa.setSostenibilidad(editarIniciativaForm.getSostenibilidad());
		else
			iniciativa.setSostenibilidad(null);
		

		iniciativa.getResultadosEspecificosIniciativa().clear();

		if ((editarIniciativaForm.getResultadoEspecificoIniciativa() != null)
				&& (!editarIniciativaForm.getResultadoEspecificoIniciativa().equals(""))) {
			String[] resultadosEspecificos = StringUtil.split(editarIniciativaForm.getResultadoEspecificoIniciativa(),
					editarIniciativaForm.getSEPARADOR());
			int anoCentral = FechaUtil.getAno(new Date());

			int anoTemp = anoCentral - 5;
			for (String resultadoEspecifico : resultadosEspecificos) {
				if (!resultadoEspecifico.equals("")) {
					ResultadoEspecificoIniciativa resultadoEspecificoIniciativa = new ResultadoEspecificoIniciativa();

					resultadoEspecificoIniciativa.setPk(
							new ResultadoEspecificoIniciativaPK(iniciativa.getIniciativaId(), new Integer(anoTemp)));
					resultadoEspecificoIniciativa.setResultado(resultadoEspecifico);
					iniciativa.getResultadosEspecificosIniciativa().add(resultadoEspecificoIniciativa);
				}
				anoTemp++;
			}
		}

		if ((editarIniciativaForm.getPerspectivaId() != null)
				&& (editarIniciativaForm.getPerspectivaId().longValue() != 0L)) {
			IniciativaPlan iniciativaPlan = new IniciativaPlan();
			iniciativaPlan.setPk(new IniciativaPlanPK());
			iniciativaPlan.getPk().setIniciativaId(iniciativa.getIniciativaId());
			iniciativaPlan.getPk().setPlanId(editarIniciativaForm.getPlanId());
			iniciativa.getIniciativaPlanes().add(iniciativaPlan);

			IniciativaPerspectiva iniciativaPerspectiva = new IniciativaPerspectiva();
			iniciativaPerspectiva.setPk(new IniciativaPerspectivaPK());
			iniciativaPerspectiva.getPk().setIniciativaId(iniciativa.getIniciativaId());
			iniciativaPerspectiva.getPk().setPerspectivaId(editarIniciativaForm.getPerspectivaId());
			iniciativa.getIniciativaPerspectivas().add(iniciativaPerspectiva);
		} else if ((editarIniciativaForm.getPlanId() != null) && (editarIniciativaForm.getPlanId().longValue() != 0L)) {
			IniciativaPlan iniciativaPlan = new IniciativaPlan();
			iniciativaPlan.setPk(new IniciativaPlanPK());
			iniciativaPlan.getPk().setIniciativaId(iniciativa.getIniciativaId());
			iniciativaPlan.getPk().setPlanId(editarIniciativaForm.getPlanId());
			iniciativa.getIniciativaPlanes().add(iniciativaPlan);
		}
		iniciativa.setTipoMedicion(editarIniciativaForm.getTipoMedicion());
		iniciativa.setPartidas(editarIniciativaForm.getPartidas());

		String selectUnidad = request.getParameter("selectUnidad");

		if (selectUnidad != null && !selectUnidad.equals("") && !selectUnidad.equals("0")) {
			iniciativa.setUnidadId(Long.parseLong(selectUnidad));
		} else {
			iniciativa.setUnidadId(editarIniciativaForm.getUnidad());
		}

		String selectFase = request.getParameter("selectFase");

		if (selectFase != null && !selectFase.equals("") && !selectFase.equals("0")) {
			iniciativa.setFaseId(Long.parseLong(selectFase));
		} else {
			iniciativa.setFaseId(editarIniciativaForm.getFaseId());
		}

		String selectEstatusType = request.getParameter("selectEstatusType");

		if (selectEstatusType != null && !selectEstatusType.equals("") && !selectEstatusType.equals("0")
				&& ((new Long(selectEstatusType)).longValue() == EstatusType.getEstatusCencelado().longValue()
						|| (new Long(selectEstatusType)).longValue() == EstatusType.getEstatusSuspendido().longValue()))
			iniciativa.setEstatusId(Long.parseLong(selectEstatusType));
		else if (selectEstatusType != null && !selectEstatusType.equals("") && !selectEstatusType.equals("0")
				&& iniciativa.getEstatusId() != null
				&& (iniciativa.getEstatusId().longValue() == EstatusType.getEstatusCencelado().longValue()
						|| iniciativa.getEstatusId().longValue() == EstatusType.getEstatusSuspendido().longValue()))
			iniciativa.setEstatusId(
					new com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions.CalcularActividadesAction()
							.CalcularEstatus(iniciativa.getPorcentajeCompletado()));

		if (editarIniciativaForm.getEliminarMediciones())
			respuesta = eliminarMediciones(iniciativa, getUsuarioConectado(request));
		if (respuesta == VgcReturnCode.DB_OK) {

			if (iniciativa.getEstatusId() != null
					&& iniciativa.getEstatusId().longValue() != EstatusType.getEstatusCencelado().longValue()
					&& iniciativa.getEstatusId().longValue() != EstatusType.getEstatusSuspendido().longValue()
					&& editarIniciativaForm.getEliminarMediciones())
				iniciativa.setEstatusId(EstatusType.getEstatusSinIniciar());

			try {
				respuesta = strategosIniciativasService.saveIniciativa(iniciativa, getUsuarioConectado(request), true);
			} catch (Throwable t) {
				respuesta = 10007;
			}

		}
		if (respuesta == VgcReturnCode.DB_OK)
			respuesta = actualizarActividades(cambioFrecuencia, iniciativa, getUsuarioConectado(request),
					strategosIniciativasService);

		if (respuesta == VgcReturnCode.DB_OK) {
			editarIniciativaForm.setStatus(StatusUtil.getStatusSuccess());
			strategosIniciativasService.unlockObject(request.getSession().getId(),
					editarIniciativaForm.getIniciativaId());
			if (request.getSession().getAttribute("actualizarForma") == null)
				request.getSession().setAttribute("actualizarForma", "true");

			forward = "exito";
			if (nuevo) {
				StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance()
						.openStrategosInstrumentosService();

				if (instrumentoId != null && instrumentoId != 0L) {
					Instrumentos instrumento = (Instrumentos) strategosInstrumentosService.load(Instrumentos.class,
							instrumentoId);
					if (instrumento != null)
						strategosInstrumentosService.asociarInstrumento(instrumentoId, iniciativa.getIniciativaId(),
								getUsuarioConectado(request));
					else
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
								new ActionMessage("action.editarregistro.noencontrado"));
				}

				strategosInstrumentosService.close();

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("action.guardarregistro.nuevo.ok"));
				forward = "crearIniciativa";
			} else
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("action.guardarregistro.modificar.ok"));
		} else if (respuesta == VgcReturnCode.DB_PK_AK_VIOLATED) {
			editarIniciativaForm.setStatus(StatusUtil.getStatusNoSuccess());
			forward = "exito";
			if (nuevo)
				forward = "crearIniciativa";
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
					new ActionMessage("action.guardarregistro.duplicado"));
		} else if (respuesta == VgcReturnCode.DB_CANCELED) {
			editarIniciativaForm.setStatus(StatusUtil.getStatusNoSuccess());
			forward = "crearIniciativa";
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
					new ActionMessage("action.guardarregistro.modificar.no.ok"));
		}

		strategosIniciativasService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("GuardarIniciativaAction.ultimoTs", ts);

		if (editarIniciativaForm.getFrecuencias() == null)
			editarIniciativaForm.setFrecuencias(Frecuencia.getFrecuencias());
		if (editarIniciativaForm.getGrupoAnos() == null) {
			Integer ano = FechaUtil.getAno(new Date());
			editarIniciativaForm.setGrupoAnos(PeriodoUtil.getListaNumeros(ano, new Byte((byte) 5)));
		}

		if (forward.equals("exito")) {
			if (desdeInstrumento != null && desdeInstrumento) {
				return getForwardBack(request, 2, true);
			} else {
				return getForwardBack(request, 1, true);
			}
		}

		return mapping.findForward(forward);

	}

	public int actualizarActividades(Boolean cambioFrecuencia, Iniciativa iniciativa, Usuario usuario,
			StrategosIniciativasService strategosIniciativasService) {
		int respuesta = VgcReturnCode.DB_OK;

		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance()
				.openStrategosPryActividadesService(strategosIniciativasService);

		Map<String, Object> filtros = new HashMap<String, Object>();
		List<PryActividad> actividades = new ArrayList<PryActividad>();
		Date comienzoPlan = null;
		Date finPlan = null;
		Date comienzoReal = null;
		Date finReal = null;

		if (iniciativa.getProyectoId() != null) {
			filtros = new HashMap<String, Object>();
			filtros.put("proyectoId", iniciativa.getProyectoId().toString());
			filtros.put("padreId", null);

			String atributoOrden = "fila";
			String tipoOrden = "ASC";
			int pagina = 0;
			actividades = strategosPryActividadesService
					.getActividades(pagina, 0, atributoOrden, tipoOrden, false, filtros).getLista();
			for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();) {
				PryActividad actividad = iter.next();
				if (comienzoPlan == null)
					comienzoPlan = actividad.getComienzoPlan();
				if (finPlan == null)
					finPlan = actividad.getFinPlan();
				if (comienzoReal == null)
					comienzoReal = actividad.getComienzoReal();
				if (finReal == null)
					finReal = actividad.getFinReal();

				if (actividad.getComienzoPlan() != null && actividad.getComienzoPlan().before(comienzoPlan))
					comienzoPlan = actividad.getComienzoPlan();
				if (actividad.getFinPlan() != null && actividad.getFinPlan().after(finPlan))
					finPlan = actividad.getFinPlan();
				if (actividad.getComienzoReal() != null && actividad.getComienzoReal().before(comienzoReal))
					comienzoReal = actividad.getComienzoReal();
				if (actividad.getFinReal() != null && actividad.getFinReal().after(finReal))
					finReal = actividad.getFinReal();
			}

			if (cambioFrecuencia) {
				filtros = new HashMap<String, Object>();
				filtros.put("proyectoId", iniciativa.getProyectoId().toString());

				atributoOrden = "fila";
				tipoOrden = "ASC";
				pagina = 0;
				StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
						.openStrategosIndicadoresService();
				actividades = strategosPryActividadesService
						.getActividades(pagina, 0, atributoOrden, tipoOrden, false, filtros).getLista();
				for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();) {
					PryActividad actividad = iter.next();

					actividad.setFechaUltimaMedicion(null);
					actividad.setPorcentajeCompletado(null);
					actividad.setPorcentajeEjecutado(null);
					actividad.setPorcentajeEsperado(null);
					actividad.setAlerta(null);
					if (respuesta == VgcReturnCode.DB_OK)
						respuesta = strategosPryActividadesService.saveActividad(actividad, usuario, false);

					Indicador indicador = null;
					if (actividad.getIndicadorId() != null)
						indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
								new Long(actividad.getIndicadorId()));
					if (respuesta == VgcReturnCode.DB_OK && indicador != null) {
						indicador.setFrecuencia(iniciativa.getFrecuencia());
						respuesta = strategosIndicadoresService.saveIndicador(indicador, usuario);
						if (respuesta == VgcReturnCode.DB_PK_AK_VIOLATED) {
							filtros = new HashMap<String, Object>();

							filtros.put("claseId", indicador.getClaseId());
							filtros.put("nombre", indicador.getNombre());
							List<Indicador> inds = strategosIndicadoresService
									.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, false).getLista();
							if (inds.size() > 0) {
								indicador = inds.get(0);
								respuesta = VgcReturnCode.DB_OK;

							}
						}
					}
					if (respuesta != VgcReturnCode.DB_OK)
						break;
				}
				strategosIndicadoresService.close();
			}
		}

		if (respuesta == VgcReturnCode.DB_OK) {
			filtros = new HashMap<String, Object>();
			filtros.put("objetoAsociadoId", iniciativa.getIniciativaId().toString());

			String atributoOrden = "fila";
			String tipoOrden = "ASC";
			int pagina = 0;
			actividades = strategosPryActividadesService
					.getActividades(pagina, 0, atributoOrden, tipoOrden, false, filtros).getLista();

			for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();) {
				PryActividad actividad = iter.next();

				if (iniciativa.getNombre() != null)
					actividad.setNombre(iniciativa.getNombre());
				else
					actividad.setNombre(null);

				if ((iniciativa.getMemoIniciativa() != null)
						&& (iniciativa.getMemoIniciativa().getDescripcion() != null)
						&& (!iniciativa.getMemoIniciativa().getDescripcion().equals("")))
					actividad.setDescripcion(iniciativa.getMemoIniciativa().getDescripcion());
				else
					actividad.setDescripcion(null);

				if (comienzoPlan != null)
					actividad.setComienzoPlan(comienzoPlan);
				else
					actividad.setComienzoPlan(null);

				if (finPlan != null)
					actividad.setFinPlan(finPlan);
				else
					actividad.setFinPlan(null);

				if (comienzoReal != null)
					actividad.setComienzoReal(comienzoReal);
				else
					actividad.setComienzoReal(null);

				if (finReal != null)
					actividad.setFinReal(finReal);
				else
					actividad.setFinReal(null);

				actividad.setDuracionPlan(null);

				if (iniciativa.getResponsableFijarMetaId() != null
						&& iniciativa.getResponsableFijarMetaId().equals(new Long(0L)))
					actividad.setResponsableFijarMetaId(null);
				else
					actividad.setResponsableFijarMetaId(iniciativa.getResponsableFijarMetaId());

				if (iniciativa.getResponsableLograrMetaId() != null
						&& iniciativa.getResponsableLograrMetaId().equals(new Long(0L)))
					actividad.setResponsableLograrMetaId(null);
				else
					actividad.setResponsableLograrMetaId(iniciativa.getResponsableLograrMetaId());

				if (iniciativa.getResponsableSeguimientoId() != null
						&& iniciativa.getResponsableSeguimientoId().equals(new Long(0L)))
					actividad.setResponsableSeguimientoId(null);
				else
					actividad.setResponsableSeguimientoId(iniciativa.getResponsableSeguimientoId());

				if (iniciativa.getResponsableCargarMetaId() != null
						&& iniciativa.getResponsableCargarMetaId().equals(new Long(0L)))
					actividad.setResponsableCargarMetaId(null);
				else
					actividad.setResponsableCargarMetaId(iniciativa.getResponsableCargarMetaId());

				if (iniciativa.getResponsableCargarEjecutadoId() != null
						&& iniciativa.getResponsableCargarEjecutadoId().equals(new Long(0L)))
					actividad.setResponsableCargarEjecutadoId(null);
				else
					actividad.setResponsableCargarEjecutadoId(iniciativa.getResponsableCargarEjecutadoId());

				if (iniciativa.getAlertaZonaVerde() != null)
					actividad.getPryInformacionActividad().setPorcentajeVerde(iniciativa.getAlertaZonaVerde());
				else
					actividad.getPryInformacionActividad().setPorcentajeVerde(null);

				if (iniciativa.getAlertaZonaAmarilla() != null)
					actividad.getPryInformacionActividad().setPorcentajeAmarillo(iniciativa.getAlertaZonaAmarilla());
				else
					actividad.getPryInformacionActividad().setPorcentajeAmarillo(null);

				if (iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()) != null
						&& iniciativa.getClaseId() != null) {
					actividad.setIndicadorId(
							new Long(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento())));
					actividad.setClaseId(new Long(iniciativa.getClaseId()));
				}

				respuesta = strategosPryActividadesService.saveActividad(actividad, usuario, false);
				if (respuesta != VgcReturnCode.DB_OK)
					break;
			}
		}

		strategosPryActividadesService.close();

		return respuesta;
	}

	public int eliminarMediciones(Iniciativa iniciativa, Usuario usuario) {
		int respuesta = VgcReturnCode.DB_OK;

		iniciativa.setFechaUltimaMedicion(null);
		iniciativa.setPorcentajeCompletado(null);
		iniciativa.setPorcentajeEsperado(null);
		iniciativa.setAlerta(null);

		List<Long> indicadores = new ArrayList<Long>();
		indicadores.add(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
		indicadores.add(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionEficiencia()));
		indicadores.add(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionEficiencia()));

		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance()
				.openStrategosPryActividadesService();

		Map<String, Object> filtros = new HashMap<String, Object>();
		List<PryActividad> actividades = new ArrayList<PryActividad>();

		if (iniciativa.getProyectoId() != null) {
			filtros = new HashMap<String, Object>();
			filtros.put("proyectoId", iniciativa.getProyectoId().toString());
			filtros.put("padreId", null);

			String atributoOrden = "fila";
			String tipoOrden = "ASC";
			int pagina = 0;
			actividades = strategosPryActividadesService
					.getActividades(pagina, 0, atributoOrden, tipoOrden, false, filtros).getLista();
			for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();) {
				PryActividad actividad = iter.next();
				indicadores.add(actividad.getIndicadorId());
				actividad.setFechaUltimaMedicion(null);
				actividad.setPorcentajeCompletado(null);
				actividad.setPorcentajeEjecutado(null);
				actividad.setPorcentajeEsperado(null);
				actividad.setAlerta(null);
				actividad.setTipoMedicion(iniciativa.getTipoMedicion());
				if (respuesta == VgcReturnCode.DB_OK)
					respuesta = strategosPryActividadesService.saveActividad(actividad, usuario, false);
				if (respuesta != VgcReturnCode.DB_OK)
					break;
			}
		}
		strategosPryActividadesService.close();

		if (respuesta == VgcReturnCode.DB_OK) {
			StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance()
					.openStrategosMedicionesService();
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
					.openStrategosIndicadoresService();

			for (Iterator<Long> iter = indicadores.iterator(); iter.hasNext();) {
				Long indicadorId = iter.next();
				if (indicadorId != null) {
					if (respuesta == VgcReturnCode.DB_OK)
						respuesta = strategosMedicionesService.deleteMediciones(indicadorId);
					try {
						if (respuesta == VgcReturnCode.DB_OK)
							respuesta = strategosIndicadoresService.actualizarDatosIndicador(indicadorId, null, null,
									null);
					} catch (Throwable e) {
						respuesta = VgcReturnCode.DB_PK_AK_VIOLATED;
					}
				}
				if (respuesta != VgcReturnCode.DB_OK)
					break;
			}

			strategosMedicionesService.close();
			strategosIndicadoresService.close();
		}

		return respuesta;
	}
}