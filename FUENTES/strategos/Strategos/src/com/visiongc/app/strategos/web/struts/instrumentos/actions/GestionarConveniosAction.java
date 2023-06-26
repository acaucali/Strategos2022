/**
 *
 */
package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.instrumentos.StrategosTiposConvenioService;
import com.visiongc.app.strategos.instrumentos.model.TipoConvenio;
import com.visiongc.app.strategos.web.struts.instrumentos.forms.GestionarConveniosForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class GestionarConveniosAction extends VgcAction
{
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrlSinParametros(url, nombre, new Integer(2));
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarConveniosForm gestionarConveniosForm = (GestionarConveniosForm)form;

		String atributoOrden = gestionarConveniosForm.getAtributoOrden();
	    String tipoOrden = gestionarConveniosForm.getTipoOrden();
	    int pagina = gestionarConveniosForm.getPagina();

	    if (atributoOrden == null) {
	      atributoOrden = "descripcion";
	      gestionarConveniosForm.setAtributoOrden(atributoOrden);
	    }
	    if (tipoOrden == null) {
	      tipoOrden = "ASC";
	      gestionarConveniosForm.setTipoOrden(tipoOrden);
	    }

	    if (pagina < 1) {
	      pagina = 1;
	    }

	    StrategosTiposConvenioService strategosConveniosService = StrategosServiceFactory.getInstance().openStrategosTiposConvenioService();

	    Map filtros = new HashMap();

	    /*
	    if ((gestionarTipoProyectoForm.getFiltroNombre() != null) && (!gestionarTipoProyectoForm.getFiltroNombre().equals(""))) {
	        filtros.put("nombre", gestionarTipoProyectoForm.getFiltroNombre());
	      }
		*/

	    PaginaLista paginaConvenios = strategosConveniosService.getTiposConvenio(pagina, 30, atributoOrden, tipoOrden, true, filtros);

	    paginaConvenios.setTamanoSetPaginas(5);

	    request.setAttribute("paginaConvenios", paginaConvenios);

	    strategosConveniosService.close();

	    if (paginaConvenios.getLista().size() > 0) {
	        TipoConvenio convenios = (TipoConvenio)paginaConvenios.getLista().get(0);
	        gestionarConveniosForm.setSeleccionados(convenios.getTiposConvenioId().toString());
	        gestionarConveniosForm.setValoresSeleccionados(convenios.getDescripcion());
	    } else {
	    	gestionarConveniosForm.setSeleccionados(null);
	    }

		return mapping.findForward(forward);
	}
}
