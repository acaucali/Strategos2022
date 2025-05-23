package com.visiongc.app.strategos.web.struts.responsables.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.commons.report.Tabla;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;

public class ReporteResponsablesAsociadosAction extends VgcReporteBasicoAction
{
  @Override
protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes)
    throws Exception
  {
    return "B - " + mensajes.getMessage("action.reporteresponsables.asociados.titulo");
  }

  @Override
protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
    String atributoOrden = request.getParameter("atributoOrden");
    String tipoOrden = request.getParameter("tipoOrden");
    String responsableId = request.getParameter("responsableId");

    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

    MessageResources mensajes = getResources(request);

    StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();

    TablaBasicaPDF tabla = null;
    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[5];
    columnas[0] = 20;
    columnas[1] = 20;
    columnas[2] = 20;
    columnas[3] = 20;
    columnas[4] = 20;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);

    Map filtros = new HashMap();
    String organizacionId = (String)request.getSession().getAttribute("organizacionId");
    if ((organizacionId != null) && (!organizacionId.equals(""))) {
      filtros.put("organizacionId", organizacionId);
    }
    if ((responsableId != null) && (!responsableId.equals(""))) {
      filtros.put("responsableId", responsableId);
    }
    filtros.put("tipo", true);

    List responsables = strategosResponsablesService.getResponsables(0, 0, atributoOrden, tipoOrden, false, filtros).getLista();

    tabla.setFormatoFont(font.style());
    tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);

    tabla.agregarCelda(mensajes.getMessage("action.reporteresponsables.cargo"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteresponsables.nombre"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteresponsables.email"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteresponsables.organizacion"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteresponsables.tipo"));
    tabla.setColspan(2);

    tabla.setDefaultAlineacionHorizontal();

    if ((responsables != null) && (responsables.size() > 0)) {
      for (Iterator iter = responsables.iterator(); iter.hasNext(); ) {
        Responsable responsable = (Responsable)iter.next();

        tabla.setDefaultAlineacionHorizontal();

        tabla.agregarCelda(responsable.getCargo());

        tabla.agregarCelda(responsable.getNombre());

        tabla.agregarCelda(responsable.getEmail());

        if (responsable.getOrganizacion() != null)
          tabla.agregarCelda(responsable.getOrganizacion().getNombre());
        else {
          tabla.agregarCelda("");
        }

        tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);
        if ((responsable.getTipo() != null) && (responsable.getTipo().booleanValue())) {
          tabla.agregarCelda(mensajes.getMessage("action.reporteresponsables.tipo.si"));
        }
        else if ((responsable.getTipo() != null) && (!responsable.getTipo().equals("true"))) {
          tabla.agregarCelda(mensajes.getMessage("action.reporteresponsables.tipo.no"));
        }

        for (Iterator i = responsable.getResponsables().iterator(); i.hasNext(); ) {
          Responsable responsableAsociado = (Responsable)i.next();

          tabla.getColspan();

          tabla.agregarCelda(responsableAsociado.getCargo());

          tabla.agregarCelda(responsableAsociado.getNombre());

          tabla.agregarCelda(responsableAsociado.getEmail());

          if (responsableAsociado.getOrganizacion() != null)
            tabla.agregarCelda(responsableAsociado.getOrganizacion().getNombre());
          else {
            tabla.agregarCelda("");
          }

          tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);
          if ((responsableAsociado.getTipo() != null) && (responsableAsociado.getTipo().booleanValue())) {
            tabla.agregarCelda(mensajes.getMessage("action.reporteresponsables.tipo.si"));
          }
          else if ((responsableAsociado.getTipo() != null) && (!responsableAsociado.getTipo().equals("true"))) {
            tabla.agregarCelda(mensajes.getMessage("action.reporteresponsables.tipo.no"));
          }
        }

      }

      documento.add(tabla.getTabla());
    }
    else
    {
      tabla.setColspan(3);
      font.setStyle(2);
      Paragraph texto = new Paragraph(" " + mensajes.getMessage("action.reporteresponsables.noresponsables"), font);
      texto.setAlignment(0);
      documento.add(texto);
    }

    documento.newPage();

    strategosResponsablesService.close();
  }
}