/**
 *
 */
package com.visiongc.app.strategos.web.struts.reportes.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;

/**
 * @author Andres
 *
 */
public class ReportePortafolioDetalle extends VgcAction
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

		ReporteForm reporteForm = (ReporteForm)form;
		reporteForm.clear();

		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		Usuario user = getUsuarioConectado(request);

		boolean isAdmin=false;
		if(user.getIsAdmin()){

			isAdmin=true;
		}

		request.getSession().setAttribute("isAdmin", isAdmin);

		/* Parametros para el reporte */


	    /*Asigna a la Forma que genera reportes, el nombre de la organizacion y plan seleccionados*/
	    reporteForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
	    reporteForm.setPortafolioId(request.getParameter("portafolioId") != null && !request.getParameter("portafolioId").toString().equals("") ? Long.parseLong(request.getParameter("portafolioId")) : null);

		return mapping.findForward(forward);
	}
}
