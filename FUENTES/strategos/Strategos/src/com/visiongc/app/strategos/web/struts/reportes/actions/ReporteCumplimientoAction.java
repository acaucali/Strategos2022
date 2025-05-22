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

public class ReporteCumplimientoAction extends VgcAction{
	
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();
		
		ReporteForm reporteForm = (ReporteForm)form;
		reporteForm.clear();
		
		Usuario user = getUsuarioConectado(request);

		boolean todasOrganizaciones = getPermisologiaUsuario(request).tienePermiso("REPORTE_TODAS_ORGANIZACIONES");
		if(todasOrganizaciones){			
			reporteForm.setTodasOrganizaciones(true);
		}						
		
		return mapping.findForward(forward);
	}
}
