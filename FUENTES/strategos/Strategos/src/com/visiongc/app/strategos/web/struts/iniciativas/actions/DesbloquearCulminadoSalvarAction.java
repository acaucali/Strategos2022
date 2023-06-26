package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

public class DesbloquearCulminadoSalvarAction extends VgcAction {

	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);
		String forward = mapping.getParameter();

		Long iniciativaId = request.getParameter("iniciativaId") != null ? Long.parseLong(request.getParameter("iniciativaId")) : null;

		MessageResources mensajes = getResources(request);

		Usuario usuario = getUsuarioConectado(request);

		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();

		if(iniciativaId != null){
			Iniciativa iniciativa = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, new Long(iniciativaId));
			if(iniciativa != null){
				if(iniciativa.getEstatusId() == 5){
					if(iniciativa.getPorcentajeCompletado() == 100 || iniciativa.getPorcentajeCompletado() >100){
						iniciativa.setEstatusId(new Long(2));
						iniciativa.setPorcentajeCompletado(99.0);
						strategosIniciativasService.saveIniciativa(iniciativa, usuario, false);
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.transformar.convertir.sucess"));

					}
					else{
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.desbloquear.culminado.error.porcentaje"));
					}
				}else{
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.desbloquear.culminado.error"));
				}
			}
		}else{
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.desbloquear.culminado.error.iniciativa"));
		}

		saveMessages(request, messages);

		strategosIniciativasService.close();


		return getForwardBack(request, 1, true);
	}

}
