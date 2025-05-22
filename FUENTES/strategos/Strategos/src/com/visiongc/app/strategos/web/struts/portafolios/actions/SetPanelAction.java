package com.visiongc.app.strategos.web.struts.portafolios.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;

public class SetPanelAction extends VgcAction {
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String panel = request.getParameter("panel");
		String tipo = request.getParameter("tipo");
		String tamano = request.getParameter("tamano");		
		
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), panel, tipo);
		if (configuracionUsuario == null)
		{
			configuracionUsuario = new ConfiguracionUsuario();
			ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
			pk.setConfiguracionBase(panel);
			pk.setObjeto(tipo);
			pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
			configuracionUsuario.setPk(pk);
			configuracionUsuario.setData(tamano);
		}
		else
			configuracionUsuario.setData(tamano);
		frameworkService.saveConfiguracionUsuario(configuracionUsuario, this.getUsuarioConectado(request));
		frameworkService.close();
		
		request.setAttribute("ajaxResponse", "");
	    return mapping.findForward("ajaxResponse");
	}
}