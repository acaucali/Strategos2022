package com.visiongc.app.strategos.web.struts.unidadesmedida.actions;

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
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.commons.report.Tabla;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;

public class ReporteUnidadesMedidaAction extends VgcReporteBasicoAction
{
  @Override
protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes)
    throws Exception
  {
    return mensajes.getMessage("action.reporteunidadesmedida.titulo");
  }

  @Override
protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
    String atributoOrden = request.getParameter("atributoOrden");
    String tipoOrden = request.getParameter("tipoOrden");

    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

    MessageResources mensajes = getResources(request);

    StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService();

    documento.add(lineaEnBlanco(font));
    TablaPDF tabla = null;
    tabla = new TablaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[3];
    columnas[0] = 20;
    columnas[1] = 60;
    columnas[2] = 20;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);

    List unidadesMedida = strategosUnidadesService.getUnidadesMedida(0, 0, atributoOrden, tipoOrden, false, null).getLista();

    tabla.setFormatoFont(font.style());
    tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);
    tabla.setColorFondo(21, 60, 120);
	tabla.setColorLetra(255, 255, 255);
	tabla.setTamanoFont(10);
	tabla.setFormatoFont(Font.NORMAL);

	tabla.agregarCelda(mensajes.getMessage("action.reporteunidadesmedida.id"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteunidadesmedida.nombre"));
    tabla.agregarCelda(mensajes.getMessage("action.reporteunidadesmedida.monetaria"));

    tabla.setColorFondo(255, 255, 255);
	tabla.setColorLetra(0, 0, 0);
    
    if ((unidadesMedida != null) && (unidadesMedida.size() > 0)) {
      for (Iterator iter = unidadesMedida.iterator(); iter.hasNext(); ) {
        UnidadMedida unidad = (UnidadMedida)iter.next();

        tabla.agregarCelda(unidad.getUnidadId().toString());
        tabla.setDefaultAlineacionHorizontal();
        tabla.agregarCelda(unidad.getNombre());

        tabla.setAlineacionHorizontal(Tabla.H_ALINEACION_CENTER);
        if ((unidad.getTipo() != null) && (unidad.getTipo().booleanValue()))
          tabla.agregarCelda(mensajes.getMessage("comunes.si"));
        else if ((unidad.getTipo() != null) && (!unidad.getTipo().equals("true"))) {
          tabla.agregarCelda(mensajes.getMessage("comunes.no"));
        }        
      }
            
      documento.add(tabla.getTabla());
    }
    else
    {
      tabla.setColspan(3);
      font.setStyle(2);
      Paragraph texto = new Paragraph(mensajes.getMessage("action.reporteunidadesmedida.nounidades"), font);
      texto.setAlignment(0);
      documento.add(texto);
    }    

    strategosUnidadesService.close();
  }
}