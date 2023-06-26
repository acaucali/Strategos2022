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
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

public class EliminarCargoAction extends VgcAction {
	private static final String ACTION_KEY = "EliminarCargoAction";

	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);

		String cargoId = request.getParameter("cargoId");
		String ts = request.getParameter("ts");
		String ultimoTs = (String) request.getSession().getAttribute("EliminarCargoAction.ultimoTs");
		boolean bloqueado = false;
		boolean cancelar = false;

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((cargoId == null) || (cargoId.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(cargoId + "&" + ts))) {
			cancelar = true;
		}

		if (cancelar) {
			return getForwardBack(request, 1, true);
		}
		
		StrategosCargosService strategosCargosService = StrategosServiceFactory.getInstance().openStrategosCargosService();
		
		bloqueado = !strategosCargosService.lockForDelete(request.getSession().getId(), cargoId);
		
		Cargos cargo = (Cargos)strategosCargosService.load(Cargos.class, new Long(cargoId));
		
		if(cargo != null) {
			if(bloqueado) {
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.bloqueado", cargo.getNombre()));
			}else {
				cargo.setCargoId(Long.valueOf(cargoId));
				int res = strategosCargosService.deleteCargo(cargo, getUsuarioConectado(request));
				
				strategosCargosService.unlockObject(request.getSession().getId(), cargoId);
				
				if(res == 10004) {
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.relacion", cargo.getNombre()));
				}else{
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.eliminacionok", cargo.getNombre()));
			    }
			}
		}else {
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.eliminarregistro.noencontrado"));
		}
		
		strategosCargosService.close();
		
		saveMessages(request, messages);
		
		request.getSession().setAttribute("EliminarCargoAction.ultimoTs", cargoId + "&" + ts);

		return getForwardBack(request, 1, true);
	}
}
