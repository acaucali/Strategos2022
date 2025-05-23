package com.visiongc.app.strategos.web.struts.indicadores.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.web.struts.indicadores.forms.EditarIndicadorForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

public class GuardarIndicadorSoloLecturaAction extends VgcAction
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

    EditarIndicadorForm editarIndicadorForm = (EditarIndicadorForm)form;

    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

    Indicador indicador = new Indicador();

    String[] fieldNames = new String[1];
    Object[] fieldValues = new Object[1];

    fieldNames[0] = "indicadorId";
    fieldValues[0] = editarIndicadorForm.getIndicadorId();

    strategosIndicadoresService.saveSoloLectura(indicador, editarIndicadorForm.getSoloLectura().booleanValue(), fieldNames, fieldValues);

    strategosIndicadoresService.close();

    return mapping.findForward("ajaxResponse");
  }
}