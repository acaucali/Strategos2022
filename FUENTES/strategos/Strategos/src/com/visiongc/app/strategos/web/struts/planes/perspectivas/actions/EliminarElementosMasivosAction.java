package com.visiongc.app.strategos.web.struts.planes.perspectivas.actions;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.StrategosPlantillasPlanesService;
import com.visiongc.app.strategos.planes.model.ElementoPlantillaPlanes;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.reportes.StrategosReportesService;
import com.visiongc.app.strategos.web.struts.planes.forms.GestionarPlanForm;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.EditarPerspectivaForm;
import com.visiongc.app.strategos.web.struts.planes.perspectivas.forms.GestionarPerspectivasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EliminarElementosMasivosAction extends VgcAction{
	
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre){}
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String forward = mapping.getParameter();

		ActionMessages messages = getMessages(request);

		String openPerspectivaId = request.getParameter("openPerspectivaId");
		String closePerspectivaId = request.getParameter("closePerspectivaId");
		String seleccionados = request.getParameter("seleccionados");

		boolean cancelar = Boolean.parseBoolean(request.getParameter("cancelar"));
		boolean eliminar = Boolean.parseBoolean(request.getParameter("eliminar"));

	    if (cancelar)
	    {
	      return getForwardBack(request, 1, true);
	    }
	    
	    
		
		EditarPerspectivaForm gestionarPerspectivasForm = (EditarPerspectivaForm)form;
		GestionarPlanForm gestionarPlanForm = (GestionarPlanForm)request.getSession().getAttribute("gestionarPlanForm");

		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Panel.Plan", "Ancho");
		
		if(seleccionados != null) {			
			gestionarPerspectivasForm.setPerspectivas(seleccionados);
			if(eliminar)
				eliminar(seleccionados, request, messages);
		}
			
		if (configuracionUsuario == null)
		{
			configuracionUsuario = new ConfiguracionUsuario();
			ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
			pk.setConfiguracionBase("Strategos.Panel.Plan");
			pk.setObjeto("Ancho");
			pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
			configuracionUsuario.setPk(pk);
			configuracionUsuario.setData("400");

			frameworkService.saveConfiguracionUsuario(configuracionUsuario, this.getUsuarioConectado(request));
		}
		gestionarPlanForm.setAnchoPorDefecto(configuracionUsuario.getData());

		configuracionUsuario = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Panel.Plan", "Alto");
		if (configuracionUsuario == null)
		{
			configuracionUsuario = new ConfiguracionUsuario();
			ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
			pk.setConfiguracionBase("Strategos.Panel.Plan");
			pk.setObjeto("Alto");
			pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
			configuracionUsuario.setPk(pk);
			configuracionUsuario.setData("400");

			frameworkService.saveConfiguracionUsuario(configuracionUsuario, this.getUsuarioConectado(request));
		}
		gestionarPlanForm.setAltoPorDefecto(configuracionUsuario.getData());

		boolean mostrarTodas = getPermisologiaUsuario(request).tienePermiso("PERSPECTIVA_VIEWALL");

		ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();

		Perspectiva perspectivaSeleccionada = null;
		StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
		ConfiguracionPlan configuracionPlan = strategosPlanesService.getConfiguracionPlan();
		strategosPlanesService.close();

		ConfiguracionUsuario foco = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Foco.Perspectiva.Arbol", "PerspectivaId");
		
		if (request.getAttribute("GestionarPerspectivasAction.reloadId") != null)
			perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, (Long)request.getAttribute("GestionarPerspectivasAction.reloadId"));
		else if ((openPerspectivaId != null) && (!openPerspectivaId.equals("")))
		{
			TreeviewWeb.publishTreeOpen("arbolPerspectivas", openPerspectivaId, "session", request);
			perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, new Long(openPerspectivaId));
		}
		else if ((closePerspectivaId != null) && (!closePerspectivaId.equals("")))
		{
			TreeviewWeb.publishTreeClose("arbolPerspectivas", closePerspectivaId, "session", request);
			perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, new Long(closePerspectivaId));
		}
		else
			perspectivaSeleccionada = (Perspectiva)arbolesService.load(Perspectiva.class, new Long((String)request.getSession().getAttribute("perspectivaId")));
		Long reloadId;
		if (perspectivaSeleccionada == null)
		{
			perspectivaSeleccionada = (Perspectiva)request.getSession().getAttribute("perspectivaRoot");
			reloadId = perspectivaSeleccionada.getPerspectivaId();
			TreeviewWeb.publishTree("arbolPerspectivas", perspectivaSeleccionada.getPerspectivaId().toString(), "session", request, true);
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
		}
		else
		{
			reloadId = perspectivaSeleccionada.getPerspectivaId();
			if (closePerspectivaId == null)
				TreeviewWeb.publishTreeOpen("arbolPerspectivas", reloadId.toString(), "session", request);
		}
		perspectivaSeleccionada.setConfiguracionPlan(configuracionPlan);

		if (foco == null)
		{
			foco = new ConfiguracionUsuario();
			ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
			pk.setConfiguracionBase("Strategos.Foco.Perspectiva.Arbol");
			pk.setObjeto("PerspectivaId");
			pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
			foco.setPk(pk);
		}
		foco.setData(reloadId.toString());
		frameworkService.saveConfiguracionUsuario(foco, this.getUsuarioConectado(request));

		arbolesService.refreshNodosArbol((Perspectiva)request.getSession().getAttribute("perspectivaRoot"), (String)request.getSession().getAttribute("arbolPerspectivas"), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas), reloadId);
		

		frameworkService.close();

		gestionarPlanForm.setPerspectivaId(perspectivaSeleccionada.getPerspectivaId());
	
		getPadres(perspectivaSeleccionada, arbolesService);

		
		
		saveMessages(request, messages);
		arbolesService.close();
	
		return mapping.findForward(forward);
	}
	
	private void getPadres(Perspectiva perspectiva, ArbolesService arbolesService)
	{
		Perspectiva perspectivaPadre = null;
		if (perspectiva.getPadreId() != null)
		{
			perspectivaPadre = (Perspectiva)arbolesService.load(Perspectiva.class, new Long(perspectiva.getPadreId()));
			perspectiva.setPadre(perspectivaPadre);
		}

		if (perspectivaPadre != null && perspectivaPadre.getPadreId() != null)
			getPadres(perspectivaPadre, arbolesService);
	}	
	
	private ActionForward eliminar(String seleccionados,  HttpServletRequest request, ActionMessages messages) {				
		String[] seleccionadosArray = seleccionados.split(",");
		boolean bloqueado = false;
		StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();

		for(String sel : seleccionadosArray) {
			strategosPerspectivasService.unlockObject(request.getSession().getId(), sel);
			bloqueado = !strategosPerspectivasService.lockForDelete(request.getSession().getId(), sel);
			Perspectiva perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, new Long(sel));
			if (perspectiva != null)
			{
				if ((perspectiva.getHijos() == null) || ((perspectiva.getHijos() != null) && (perspectiva.getHijos().size() == 0)))
				{
					if (perspectiva.getPadre() != null)
					{
						if (bloqueado)
							messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", perspectiva.getNombre()));
						else
						{
							strategosPerspectivasService.unlockObject(request.getSession().getId(), sel);

							int res = strategosPerspectivasService.deletePerspectiva(perspectiva, getUsuarioConectado(request));

							if (res == 10004)
								messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", perspectiva.getNombre()));
							else
							{
								messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", perspectiva.getNombre()));
								request.setAttribute("GestionarPerspectivasAction.reloadId", perspectiva.getPadreId());
							}
						}
					}
					else
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.nodoraiz", perspectiva.getNombre()));
				}
				else
				{
					System.out.print("\n\nHijooos :  " + perspectiva.getHijos());		
					for(Perspectiva hijo : perspectiva.getHijos()) {
						if (hijo.getPadre() != null)
						{
							if (bloqueado)
								messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", hijo.getNombre()));
							else
							{
								strategosPerspectivasService.unlockObject(request.getSession().getId(), hijo.getPerspectivaId());

								int res = strategosPerspectivasService.deletePerspectiva(hijo, getUsuarioConectado(request));

								if (res == 10004)
									messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", hijo.getNombre()));
								else
								{
									messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", hijo.getNombre()));
									request.setAttribute("GestionarPerspectivasAction.reloadId", hijo.getPadreId());
								}
							}
						}
						else
							messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.nodoraiz", hijo.getNombre()));
					}
					
					strategosPerspectivasService.unlockObject(request.getSession().getId(), sel);

					int res = strategosPerspectivasService.deletePerspectiva(perspectiva, getUsuarioConectado(request));

					if (res == 10004)
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", perspectiva.getNombre()));
					else
					{
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", perspectiva.getNombre()));
						request.setAttribute("GestionarPerspectivasAction.reloadId", perspectiva.getPadreId());
					}
					
				}
			}
			else
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));

			
		}	
		strategosPerspectivasService.close();

		return getForwardBack(request, 1, true);
	}
}
