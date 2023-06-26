package com.visiongc.app.strategos.web.struts.reportes.grafico.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.reportes.StrategosReportesGraficoService;
import com.visiongc.app.strategos.reportes.model.ReporteGrafico;
import com.visiongc.app.strategos.web.struts.reportes.grafico.forms.GraficoReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

public class GraficarReporteAction extends VgcAction{
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);
		String forward = mapping.getParameter();

		String reporteId = request.getParameter("reporteId");

		StrategosReportesGraficoService reportesGraficoService = StrategosServiceFactory.getInstance().openStrategosReportesGraficoService();

		GraficoReporteForm graficoReporteForm = (GraficoReporteForm)form;
		String indicadores="";
		String indicadoresId="";
		String organizaciones="";
		int ind=0;
		int org=0;

		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");



			if (reporteId != null)
			{
				ReporteGrafico reporte= reportesGraficoService.obtenerReporte(new Long(reporteId));

				if(reporte !=null) {

					if(usuario != null && !usuario.getIsAdmin() && !reporte.getPublico()){
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.reporte.grafico.ejecutar.error.admin"));
					}
					else {

						if(reporte.getIndicadores() != null || reporte.getIndicadores() != "") {
							ind=reporte.getIndicadores().length();

							if(ind >0) {
								indicadores=reporte.getIndicadores().substring(0, ind-1);
							}

						}

						if(reporte.getOrganizaciones() != null || reporte.getOrganizaciones() != "") {
							org=reporte.getOrganizaciones().length();

							if(org >0) {

								organizaciones=reporte.getOrganizaciones().substring(0, org-1);
								String[] cad = organizaciones.split(",");

								for(int x=0; x<cad.length; x++) {

									String detalle= cad[x];
									if(x == 0) {
										indicadoresId=buscarIndicador(indicadores, new Long(detalle));

										if(indicadoresId.length() >0) {
											indicadoresId= indicadoresId.substring(1, indicadoresId.length());
										}else {
											indicadoresId= indicadores;
										}


									}else {
										indicadoresId+=buscarIndicador(indicadores, new Long(detalle));
									}
								}
							}

						}

						graficoReporteForm.setReporteId(reporte.getReporteId());
						graficoReporteForm.setIndicadoresId(indicadoresId);


					}// fin else

				}

			}


		reportesGraficoService.close();

		saveMessages(request, messages);

		//return getForwardBack(request, 1, true);

	  	return mapping.findForward(forward);
	}

	public String buscarIndicador(String indicadores, Long organizacionId) {

		String indicadoresId = "";

		StrategosIndicadoresService indicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

		String[] cad = indicadores.split(",");

		for (String detalle : cad) {

			Indicador indicador = (Indicador)indicadoresService.load(Indicador.class, new Long(detalle));

			Indicador ind= new Indicador();

			ind = indicadoresService.getIndicador(organizacionId, indicador.getNombre());

			if(ind != null) {

				if(ind.getIndicadorId() != null) {
					indicadoresId+= ",";
					indicadoresId+= ind.getIndicadorId();
				}

			}

		}

		indicadoresService.close();

		return indicadoresId;
	}
}
