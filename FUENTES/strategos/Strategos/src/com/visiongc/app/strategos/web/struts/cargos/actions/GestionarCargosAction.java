package com.visiongc.app.strategos.web.struts.cargos.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.cargos.StrategosCargosService;
import com.visiongc.app.strategos.cargos.model.Cargos;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.web.struts.cargos.forms.GestionarCargosForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;

public class GestionarCargosAction extends VgcAction {

	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
		navBar.agregarUrl(url, nombre);
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.execute(mapping, form, request, response);

	    String forward = mapping.getParameter();
	    
	    GestionarCargosForm gestionarCargosForm = (GestionarCargosForm)form;
	    
	    String atributoOrden = gestionarCargosForm.getAtributoOrden();
	    String tipoOrden = gestionarCargosForm.getTipoOrden();
	    int pagina = gestionarCargosForm.getPagina();
	    
	    if(atributoOrden == null) {
	    	atributoOrden = "nombre";
	    	gestionarCargosForm.setAtributoOrden(atributoOrden);
	    }
	    if(tipoOrden == null) {
	    	tipoOrden = "ASC";
	    	gestionarCargosForm.setTipoOrden(tipoOrden);
	    }
	    
	    if (pagina < 1) {
	        pagina = 1;
	      }
	    
	    StrategosCargosService strategosCargosService = StrategosServiceFactory.getInstance().openStrategosCargosService();
	    
	    Map filtros = new HashMap();
	    
	    if((gestionarCargosForm.getFiltroNombre() != null) && (!gestionarCargosForm.getFiltroNombre().equals(""))) {
	    	filtros.put("nombre", gestionarCargosForm.getFiltroNombre());
	    }
	    	   
	    PaginaLista paginaCargos = strategosCargosService.getCargos(pagina, 30, atributoOrden, tipoOrden, true, filtros);
	    	    	    	  
	    paginaCargos.setTamanoSetPaginas(5);
	    
	    request.setAttribute("paginaCargos", paginaCargos);
	    
	    strategosCargosService.close();
	    
	    if(paginaCargos.getLista().size() > 0 ) {
	    	Cargos cargos = (Cargos)paginaCargos.getLista().get(0);
	    	gestionarCargosForm.setSeleccionados(cargos.getCargoId().toString());
	    	gestionarCargosForm.setValoresSeleccionados(cargos.getNombre());
	    }else {
	    	gestionarCargosForm.setSeleccionados(null);
	    }
	    
	    return mapping.findForward(forward);
	}
}
