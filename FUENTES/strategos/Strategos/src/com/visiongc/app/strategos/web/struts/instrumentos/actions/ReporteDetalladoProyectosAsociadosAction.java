package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;

public class ReporteDetalladoProyectosAsociadosAction extends VgcAction  {
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre){}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		ReporteForm reporteForm = (ReporteForm)form;
		reporteForm.clear();

		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		Usuario user = getUsuarioConectado(request);
		
		StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();


		boolean isAdmin=false;
		if(user.getIsAdmin()){

			isAdmin=true;
		}	
		request.getSession().setAttribute("isAdmin", isAdmin);

		/* Parametros para el reporte */

		Calendar fecha = Calendar.getInstance();
        int ano = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        int anoIn = ano -20;
        int anoFin = ano +20;

		reporteForm.setAnoFinal(""+anoFin);
		reporteForm.setMesFinal(""+mes);
		reporteForm.setAno(ano);
		reporteForm.setAnoInicial(""+anoIn);
		
		reporteForm.setCooperantes(new ArrayList<Cooperante>());
	    if(reporteForm.getCooperanteId() != null) {
	    	reporteForm.getCooperantes().addAll(reporteForm.getCooperantes());
	    }
		
		Map<String, String> filtrosCooperantes = new HashMap();
	    PaginaLista paginaCooperantes = strategosCooperantesService.getCooperantes(0, 0, "cooperanteId", "asc", true, filtrosCooperantes);

	    for (Iterator<Cooperante> iter = paginaCooperantes.getLista().iterator(); iter.hasNext(); )
		{
				Cooperante cooperante = iter.next();
				reporteForm.getCooperantes().add(cooperante);
		}

		


		return mapping.findForward(forward);
	}
}
