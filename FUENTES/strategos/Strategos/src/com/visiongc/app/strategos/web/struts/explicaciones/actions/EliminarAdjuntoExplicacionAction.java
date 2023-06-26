package com.visiongc.app.strategos.web.struts.explicaciones.actions;

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.web.struts.explicaciones.forms.EditarExplicacionForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

import java.io.File;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EliminarAdjuntoExplicacionAction extends VgcAction
{
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();
		Usuario usuario = getUsuarioConectado(request);

		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
		 
		EditarExplicacionForm editarExplicacionForm = (EditarExplicacionForm)request.getSession().getAttribute("editarExplicacionForm");
		AdjuntoExplicacion adjunto = null;
		Long explicacionId = null;
		
		String indiceAdjunto = ((String[])editarExplicacionForm.getMultipartRequestHandler().getTextElements().get("indiceAdjunto"))[0];
		adjunto = (AdjuntoExplicacion)editarExplicacionForm.getAdjuntosExplicacion().get(Integer.parseInt(indiceAdjunto));
		
		adjunto.setArchivo(null);

		editarExplicacionForm.getAdjuntosExplicacion().remove(Integer.parseInt(indiceAdjunto));
		editarExplicacionForm.setNumeroAdjuntos(new Long(editarExplicacionForm.getAdjuntosExplicacion().size()));
		
		return mapping.findForward(forward);
	}
}