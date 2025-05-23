/**
 *
 */
package com.visiongc.app.strategos.web.struts.modulo.codigoEnlace.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.modulo.codigoenlace.StrategosCodigoEnlaceService;
import com.visiongc.app.strategos.modulo.codigoenlace.model.CodigoEnlace;
import com.visiongc.app.strategos.web.struts.modulo.codigoEnlace.forms.EditarCodigoEnlaceForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class EditarCodigoEnlaceAction extends VgcAction
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

	    EditarCodigoEnlaceForm editarCodigoEnlaceForm = (EditarCodigoEnlaceForm)form;

	    ActionMessages messages = getMessages(request);

	    String id = request.getParameter("id");

	    StrategosCodigoEnlaceService strategosCodigoEnlaceService = StrategosServiceFactory.getInstance().openStrategosCodigoEnlaceService();

	    if ((id != null) && (!id.equals("")) && (!id.equals("0")))
	    {
	    	CodigoEnlace codigoEnlace = (CodigoEnlace)strategosCodigoEnlaceService.load(CodigoEnlace.class, new Long(id));

	    	if (codigoEnlace != null)
	    	{
	    		editarCodigoEnlaceForm.setId(codigoEnlace.getId());
	    		editarCodigoEnlaceForm.setCodigo(codigoEnlace.getCodigo());
	    		editarCodigoEnlaceForm.setNombre(codigoEnlace.getNombre());
	    		editarCodigoEnlaceForm.setBi(codigoEnlace.getBi());
	    		editarCodigoEnlaceForm.setCategoria(codigoEnlace.getCategoria());
	    	}
	    	else
	    	{
	    		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
	    		forward = "noencontrado";
	    	}
	    }
	    else
	    	editarCodigoEnlaceForm.clear();

	    strategosCodigoEnlaceService.close();

	    saveMessages(request, messages);

	    if (forward.equals("noencontrado"))
	    	return getForwardBack(request, 1, true);
	    return mapping.findForward(forward);
	}
}