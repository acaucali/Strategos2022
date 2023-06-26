package com.visiongc.app.strategos.web.struts.indicadores.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.web.struts.indicadores.forms.EditarIndicadorForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

public class VisualizarIndicadorAction extends VgcAction
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

		EditarIndicadorForm editarIndicadorForm = (EditarIndicadorForm)form;

		forward = new com.visiongc.app.strategos.web.struts.indicadores.actions.EditarIndicadorAction().getData(editarIndicadorForm, forward, request);

		String indicadorId = request.getParameter("indicadorId");

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		if ((indicadorId == null) || (indicadorId.equals("")))
			cancelar = true;
		if (cancelar || forward.equals("noencontrado"))
			return getForwardBack(request, 1, true);

		return mapping.findForward(forward);
	}
}