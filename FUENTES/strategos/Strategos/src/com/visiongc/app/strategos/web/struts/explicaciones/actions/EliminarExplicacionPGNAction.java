package com.visiongc.app.strategos.web.struts.explicaciones.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.ExplicacionPGN;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

public class EliminarExplicacionPGNAction extends VgcAction {
	private static final String ACTION_KEY = "EliminarExplicacionAction";

	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);

		String explicacionId = request.getParameter("explicacionId");
		String ts = request.getParameter("ts");
		String ultimoTs = (String) request.getSession().getAttribute("EliminarExplicacionAction.ultimoTs");
		boolean bloqueado = false;
		boolean cancelar = false;

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((explicacionId == null) || (explicacionId.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(explicacionId + "&" + ts))) {
			cancelar = true;
		}

		if (cancelar) {
			return getForwardBack(request, 1, true);
		}
		
	    StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();

	    bloqueado = !strategosExplicacionesService.lockForDelete(request.getSession().getId(), explicacionId);
	    
	    ExplicacionPGN explicacion = (ExplicacionPGN)strategosExplicacionesService.load(ExplicacionPGN.class, new Long(explicacionId));
	    
	    if (explicacion != null)
	    {
	      if (bloqueado)
	      {
	        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", explicacion.getTitulo()));
	      }
	      else
	      {
	        explicacion.setExplicacionId(Long.valueOf(explicacionId));
	        int res = strategosExplicacionesService.deleteExplicacionPGN(explicacion, getUsuarioConectado(request));

	        strategosExplicacionesService.unlockObject(request.getSession().getId(), explicacionId);

	        if (res == 10004)
	        {
	          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", explicacion.getTitulo()));
	        }
	        else
	        {
	          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", explicacion.getTitulo()));
	        }

	      }

	    }
	    else
	    {
	      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
	    }

	    strategosExplicacionesService.close();

	    saveMessages(request, messages);

	    request.getSession().setAttribute("EliminarExplicacionPGNAction.ultimoTs", explicacionId + "&" + ts);

	    return getForwardBack(request, 1, true);
	}
}
