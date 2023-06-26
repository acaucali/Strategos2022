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

public class EditarCargoAction extends VgcAction {

	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
		
		EditarCargosForm editarCargosForm = (EditarCargosForm)form;
		
		ActionMessages messages = getMessages(request);
		
		String cargoId = request.getParameter("cargoId");
		
		boolean bloqueado = false;
		
		StrategosCargosService strategosCargosService = StrategosServiceFactory.getInstance().openStrategosCargosService();

		if((cargoId != null) && (!cargoId.equals("")) && (!cargoId.equals("0"))) {
			bloqueado = !strategosCargosService.lockForUpdate(request.getSession().getId(), cargoId, null);
			editarCargosForm.setBloqueado(new Boolean(bloqueado));
			
			Cargos cargo = (Cargos)strategosCargosService.load(Cargos.class, new Long(cargoId));
			
			if(cargo != null) {
				if(bloqueado) {
					 messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
				}
				
				editarCargosForm.setCargoId(cargo.getCargoId());
				editarCargosForm.setNombre(cargo.getNombre());
			}else {
				strategosCargosService.unlockObject(request.getSession().getId(), new Long(cargoId));
				
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
		        forward = "noencontrado";
			}
		}else {
			editarCargosForm.clear();
		}
		
		strategosCargosService.close();
		
		saveMessages(request, messages);
		
		if (forward.equals("noencontrado"))
	    {
	      return getForwardBack(request, 1, true);
	    }
		
		return mapping.findForward(forward);
	}
}
