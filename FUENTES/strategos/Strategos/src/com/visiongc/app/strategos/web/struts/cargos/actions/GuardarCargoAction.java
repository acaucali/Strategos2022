package com.visiongc.app.strategos.web.struts.cargos.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.cargos.StrategosCargosService;
import com.visiongc.app.strategos.cargos.model.Cargos;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.web.struts.cargos.forms.EditarCargosForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

public class GuardarCargoAction extends VgcAction {

	private static final String ACTION_KEY = "GuardarCargoAction";

	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarCargosForm editarCargosForm = (EditarCargosForm) form;

		ActionMessages messages = getMessages(request);

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		String ts = request.getParameter("ts");
		String ultimoTs = (String) request.getSession().getAttribute("GuardarCargoAction.ultimoTs");

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(ts)))
			cancelar = true;

		StrategosCargosService strategosCargosService = StrategosServiceFactory.getInstance()
				.openStrategosCargosService();

		if (cancelar) {
			strategosCargosService.unlockObject(request.getSession().getId(), editarCargosForm.getCargoId());

			strategosCargosService.close();

			return getForwardBack(request, 1, true);
		}

		Cargos cargo = new Cargos();
		boolean nuevo = false;
		int respuesta = 10000;
		
		cargo.setCargoId(editarCargosForm.getCargoId());
		cargo.setNombre(editarCargosForm.getNombre());

		if ((editarCargosForm.getCargoId() != null) && (!editarCargosForm.getCargoId().equals(Long.valueOf("0")))) {
			cargo = (Cargos) strategosCargosService.load(Cargos.class, editarCargosForm.getCargoId());
		} else {
			nuevo = true;
			cargo = new Cargos();
			cargo.setCargoId(new Long(0L));
		}

		cargo.setNombre(editarCargosForm.getNombre());
		
		respuesta = strategosCargosService.saveCargo(cargo, getUsuarioConectado(request));			

		if (respuesta == 10000) {
			strategosCargosService.unlockObject(request.getSession().getId(), editarCargosForm.getCargoId());
			forward = "exito";

			if (nuevo) {
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("action.guardarregistro.nuevo.ok"));	
				forward = "crearCargos";
			} else {
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("action.guardarregistro.modificar.ok"));
			}
		}
		
		else if(respuesta == 10003) {
			 messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
		}
		
		strategosCargosService.close();

	    saveMessages(request, messages);

	    request.getSession().setAttribute("GuardarCargoAction.ultimoTs", ts);
	    
	    if (forward.equals("exito")) {
	        return getForwardBack(request, 1, true);
	    }
		
		return mapping.findForward(forward);
	}
}
