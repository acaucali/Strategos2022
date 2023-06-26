package com.visiongc.app.strategos.web.struts.presentaciones.paginas.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.presentaciones.StrategosPaginasService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

public class CambiarOrdenPaginaAction extends VgcAction
{
  @Override
public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  @Override
public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String paginaId = request.getParameter("paginaId");
    boolean subir = request.getParameter("subir").equals("1");

    StrategosPaginasService strategosPaginasService =
      StrategosServiceFactory.getInstance().openStrategosPaginasService();

    strategosPaginasService.cambiarOrdenPaginas(new Long(paginaId), subir,
      getUsuarioConectado(request));

    strategosPaginasService.close();

    return getForwardBack(request, 1, true);
  }
}