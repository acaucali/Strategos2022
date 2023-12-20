package com.visiongc.app.strategos.web.struts.explicaciones.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.ExplicacionPGN;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.web.struts.explicaciones.forms.EditarExplicacionesPGNForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

public class GuardarExplicacionesPGNAction extends VgcAction{
	
	private static final String ACTION_KEY = "GuardarExplicacionPGNAction";
	
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		super.execute(mapping, form, request, response);

	    String forward = mapping.getParameter();
	    
	    EditarExplicacionesPGNForm editarExplicacionForm = (EditarExplicacionesPGNForm)form;
	    	    
	    ActionMessages messages = getMessages(request);
	    
	    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
	    String ts = request.getParameter("ts");
	    String ultimoTs = (String)request.getSession().getAttribute("GuardarExplicacionPGNAction.ultimoTs");
	    if ((ts == null) || (ts.equals(""))) {
	      cancelar = true;
	    } else if ((ultimoTs != null) && (ultimoTs.equals(ts))) {
	      cancelar = true;
	    }
	    StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
	    if (cancelar)
	    {
	      strategosExplicacionesService.unlockObject(request.getSession().getId(), editarExplicacionForm.getExplicacionId());

	      strategosExplicacionesService.close();

	      return getForwardBack(request, 1, true);
	    }
	    
	    ExplicacionPGN explicacion = new ExplicacionPGN();
	    
	    boolean nuevo = false;
	    int respuesta = 10000;
	    
	    if ((editarExplicacionForm.getExplicacionId() != null) && (!editarExplicacionForm.getExplicacionId().equals(Long.valueOf("0"))))
	    {
	    	explicacion = (ExplicacionPGN)strategosExplicacionesService.load(ExplicacionPGN.class, editarExplicacionForm.getExplicacionId());
	    }else {
	    	nuevo = true;
	    	explicacion = new ExplicacionPGN();
	    	explicacion.setExplicacionId(new Long(0L));
	    	explicacion.setCreado(new Date());
	    	explicacion.setCreadoId(((Usuario)request.getSession().getAttribute("usuario")).getUsuarioId());
	    	explicacion.setObjetoId(editarExplicacionForm.getObjetoId());		    	
	    }
	    explicacion.setTitulo(editarExplicacionForm.getTitulo());
	    explicacion.setFecha(FechaUtil.convertirStringToDate(editarExplicacionForm.getFecha(), VgcResourceManager.getResourceApp("formato.fecha.corta")));	    
	    explicacion.setCumplimiendoFechas(editarExplicacionForm.getCumpliendoFechas());
    	explicacion.setExplicacionFechas(editarExplicacionForm.getExplicacionFechas());
    	explicacion.setRecibido(editarExplicacionForm.getRecibido());
    	explicacion.setExplicacionRecibido(editarExplicacionForm.getExplicacionRecibido());
	    
	    respuesta = strategosExplicacionesService.saveExplicacionPGN(explicacion, getUsuarioConectado(request));
	    if (respuesta == 10000)
	    {
	      strategosExplicacionesService.unlockObject(request.getSession().getId(), editarExplicacionForm.getExplicacionId());
	      forward = "exito";
	      if (nuevo)
	      {
	        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
	        forward = "crearExplicacionPGN";
	      }
	      else
	      {
	        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
	      }
	    }
	    else if (respuesta == 10003)
	    {
	      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
	    }
	    strategosExplicacionesService.close();

	    saveMessages(request, messages);

	    request.getSession().setAttribute("GuardarExplicacionAction.ultimoTs", ts);
	    if (forward.equals("exito")) {
	      return getForwardBack(request, 1, true);
	    }
	    return mapping.findForward(forward);
	}
}
