package com.visiongc.app.strategos.web.struts.cargos.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.cargos.StrategosCargosService;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.web.struts.forms.SelectorListaForm;

public class SeleccionarCargosAction extends VgcAction {
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		SelectorListaForm seleccionarCargosForm = (SelectorListaForm) form;

		String atributoOrden = seleccionarCargosForm.getAtributoOrden();
		String tipoOrden = seleccionarCargosForm.getTipoOrden();

		if (atributoOrden == null) {
			atributoOrden = "nombre";
			seleccionarCargosForm.setAtributoOrden(atributoOrden);
		}
		if (tipoOrden == null) {
			tipoOrden = "ASC";
			seleccionarCargosForm.setTipoOrden(tipoOrden);
		}

		StrategosCargosService strategosCargosService = StrategosServiceFactory.getInstance()
				.openStrategosCargosService();

		PaginaLista paginaCargos = strategosCargosService.getCargos(0, 0, atributoOrden, tipoOrden, true, null);

		request.setAttribute("paginaCargos", paginaCargos);

		strategosCargosService.close();

		return mapping.findForward(forward);
	}

}
