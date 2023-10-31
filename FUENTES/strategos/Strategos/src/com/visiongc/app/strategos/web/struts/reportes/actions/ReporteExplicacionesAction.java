package com.visiongc.app.strategos.web.struts.reportes.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

public class ReporteExplicacionesAction extends VgcAction{

	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		String objetoId = (request.getParameter("objetoId"));
		String objetoKey = (request.getParameter("objetoKey"));
		
		System.out.print(objetoKey);
		
		ReporteForm reporteForm = (ReporteForm)form;
		reporteForm.clear();
		
		reporteForm.setObjetoSeleccionadoId(new Long(objetoId));
		reporteForm.setObjetoKey(new String (objetoKey));
		
		System.out.print(reporteForm.getObjetoKey());
		
		Usuario user = getUsuarioConectado(request);

		boolean isAdmin=false;
		if(user.getIsAdmin()){

			isAdmin=true;
		}
		request.getSession().setAttribute("isAdmin", isAdmin);
			
		return mapping.findForward(forward);
	}
}
