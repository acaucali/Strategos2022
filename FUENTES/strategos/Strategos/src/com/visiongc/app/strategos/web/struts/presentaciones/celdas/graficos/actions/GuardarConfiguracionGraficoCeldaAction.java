package com.visiongc.app.strategos.web.struts.presentaciones.celdas.graficos.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.web.struts.graficos.forms.GraficoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

public class GuardarConfiguracionGraficoCeldaAction extends VgcAction
{
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = "exito";

		ActionMessages messages = new ActionMessages();

		GraficoForm graficoForm = (GraficoForm)form;

		saveMessages(request, messages);

		return mapping.findForward(forward);
	}
}