package com.visiongc.app.strategos.web.struts.causas.actions;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.visiongc.app.strategos.causas.model.Causa;
import com.visiongc.commons.report.Tabla;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;

public class ReporteCausasAction extends VgcReporteBasicoAction
{
  @Override
protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes)
    throws Exception
  {
    return mensajes.getMessage("action.reportecausas.titulo");
  }

  @Override
protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
  {
    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

    MessageResources mensajes = getResources(request);

    TablaBasicaPDF tabla = null;
    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[1];
    columnas[0] = 100;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);

    ArbolesService nodosArbolService = FrameworkServiceFactory.getInstance().openArbolesService();

    Causa causaRoot = new Causa();
    causaRoot = (Causa)nodosArbolService.getNodoArbolRaiz(causaRoot);

    List causas = nodosArbolService.crearReporteArbol(causaRoot, new Boolean(true));

    nodosArbolService.close();

    tabla.setFormatoFont(font.style());
    tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);

    tabla.agregarCelda(mensajes.getMessage("action.reportecausas.nombre"));

    documento.add(tabla.getTabla());

    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    tabla.setAmplitudTabla(100);
    tabla.setColorBorde(255, 255, 255);
    tabla.crearTabla(columnas);

    tabla.setDefaultAlineacionHorizontal();
    if ((causas != null) && (causas.size() > 0)) {
      for (Iterator iter = causas.iterator(); iter.hasNext(); ) {
        String causa = (String)iter.next();

        tabla.setDefaultAlineacionHorizontal();
        tabla.agregarCelda(causa);
      }

      documento.add(tabla.getTabla());
    }

    documento.newPage();
  }
}