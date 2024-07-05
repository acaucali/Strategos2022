package com.visiongc.app.strategos.web.struts.tipoproyecto.actions;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.commons.report.Tabla;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;

public class ReporteTiposProyectoAction extends VgcReporteBasicoAction
{
  @Override
protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
  {
	  return mensajes.getMessage("action.reportetipoproyecto.titulo");
  }

  @Override
protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
  {
    String atributoOrden = request.getParameter("atributoOrden");
    String tipoOrden = request.getParameter("tipoOrden");

    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

    MessageResources mensajes = getResources(request);


    StrategosTipoProyectoService strategosTipoProyectoService = StrategosServiceFactory.getInstance().openStrategosTipoProyectoService();

    documento.add(lineaEnBlanco(font));
    TablaPDF tabla = null;
    tabla = new TablaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[2];
    columnas[0] = 20;
    columnas[1] = 80;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);

    List<?> proyecto = strategosTipoProyectoService.getTiposProyecto(0, 0, atributoOrden, tipoOrden, false, null).getLista();

    tabla.setFormatoFont(font.style());
    tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);
    tabla.setColorFondo(21, 60, 120);
	tabla.setColorLetra(255, 255, 255);
	tabla.setTamanoFont(10);
	tabla.setFormatoFont(Font.NORMAL);
	
	tabla.agregarCelda(mensajes.getMessage("action.reportetipoproyecto.id"));
    tabla.agregarCelda(mensajes.getMessage("action.reportetipoproyecto.nombre"));
    
    tabla.setColorFondo(255, 255, 255);
	tabla.setColorLetra(0, 0, 0);
	
    if ((proyecto != null) && (proyecto.size() > 0)) {
      for (Iterator<?> iter = proyecto.iterator(); iter.hasNext(); ) {
        TipoProyecto tipo = (TipoProyecto)iter.next();
        tabla.agregarCelda(tipo.getTipoProyectoId().toString());
        tabla.agregarCelda(tipo.getNombre());
      }

      documento.add(tabla.getTabla());
    }
    else
    {
      tabla.setColspan(3);
      font.setStyle(2);
      Paragraph texto = new Paragraph(" " + mensajes.getMessage("action.reportetipoproyecto.nocategorias"), font);
      texto.setAlignment(0);
      documento.add(texto);
    }
    
    strategosTipoProyectoService.close();
  }
}