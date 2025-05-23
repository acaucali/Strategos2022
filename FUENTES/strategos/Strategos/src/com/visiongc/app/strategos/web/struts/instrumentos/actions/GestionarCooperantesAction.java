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
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.web.struts.instrumentos.forms.GestionarCooperantesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class GestionarCooperantesAction extends VgcAction
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

		GestionarCooperantesForm gestionarCooperantesForm = (GestionarCooperantesForm)form;

		String atributoOrden = gestionarCooperantesForm.getAtributoOrden();
	    String tipoOrden = gestionarCooperantesForm.getTipoOrden();
	    int pagina = gestionarCooperantesForm.getPagina();

	    if (atributoOrden == null) {
	      atributoOrden = "nombre";
	      gestionarCooperantesForm.setAtributoOrden(atributoOrden);
	    }
	    if (tipoOrden == null) {
	      tipoOrden = "ASC";
	      gestionarCooperantesForm.setTipoOrden(tipoOrden);
	    }

	    if (pagina < 1) {
	      pagina = 1;
	    }

	    StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();

	    Map filtros = new HashMap();

	    /*
	    if ((gestionarTipoProyectoForm.getFiltroNombre() != null) && (!gestionarTipoProyectoForm.getFiltroNombre().equals(""))) {
	        filtros.put("nombre", gestionarTipoProyectoForm.getFiltroNombre());
	      }
		*/

	    PaginaLista paginaCooperantes = strategosCooperantesService.getCooperantes(pagina, 30, atributoOrden, tipoOrden, true, filtros);

	    paginaCooperantes.setTamanoSetPaginas(5);

	    request.setAttribute("paginaCooperantes", paginaCooperantes);

	    strategosCooperantesService.close();

	    if (paginaCooperantes.getLista().size() > 0) {
	        Cooperante cooperante = (Cooperante)paginaCooperantes.getLista().get(0);
	        gestionarCooperantesForm.setSeleccionados(cooperante.getCooperanteId().toString());
	        gestionarCooperantesForm.setValoresSeleccionados(cooperante.getNombre());
	    } else {
	    	gestionarCooperantesForm.setSeleccionados(null);
	    }

		return mapping.findForward(forward);
	}
}
