package com.visiongc.app.strategos.web.struts.planes.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.web.struts.planes.forms.SeleccionarPlanesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;

public final class SeleccionarPlanesPlanesAction extends VgcAction
{
  public static final String ACTION_KEY = "SeleccionarPlanesPlanesAction";

  @Override
public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  @Override
public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    SeleccionarPlanesForm seleccionarPlanesForm = (SeleccionarPlanesForm)form;

    if (seleccionarPlanesForm.getAtributoOrden() == null) {
      seleccionarPlanesForm.setAtributoOrden("nombre");
    }
    if (seleccionarPlanesForm.getTipoOrden() == null) {
      seleccionarPlanesForm.setTipoOrden("ASC");
    }

    String llamadaDesde = request.getParameter("llamadaDesde");
    if ((llamadaDesde != null) &&
      (llamadaDesde.equals("Organizaciones"))) {
      seleccionarPlanesForm.setPanelSeleccionado("panelOrganizaciones");
    }

    StrategosPlanesService planesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();

    Map filtros = new HashMap();

    if ((seleccionarPlanesForm.getOrganizacionSeleccionadaId() != null) && (!seleccionarPlanesForm.getOrganizacionSeleccionadaId().equals(""))) {
      filtros.put("organizacionId", seleccionarPlanesForm.getOrganizacionSeleccionadaId().toString());
    }

    if ((seleccionarPlanesForm.getMostrarSoloPlanesActivos() != null) && (seleccionarPlanesForm.getMostrarSoloPlanesActivos().booleanValue())) {
      filtros.put("activo", seleccionarPlanesForm.getMostrarSoloPlanesActivos().toString());
    }

    PaginaLista paginaPlanes = planesService.getPlanes(0, 0, seleccionarPlanesForm.getAtributoOrden(), seleccionarPlanesForm.getTipoOrden(), true, filtros);

    request.getSession().setAttribute("paginaPlanes", paginaPlanes);

    planesService.close();

    if (paginaPlanes.getLista().size() > 0) {
      Plan plan = (Plan)paginaPlanes.getLista().get(0);
      seleccionarPlanesForm.setSeleccionados(plan.getPlanId().toString());
      seleccionarPlanesForm.setValoresSeleccionados(plan.getNombre());
    } else {
      seleccionarPlanesForm.setSeleccionados(null);
    }

    seleccionarPlanesForm.setCambioOrganizacion(false);
    seleccionarPlanesForm.setIniciado(new Boolean(true));

    return mapping.findForward(forward);
  }
}