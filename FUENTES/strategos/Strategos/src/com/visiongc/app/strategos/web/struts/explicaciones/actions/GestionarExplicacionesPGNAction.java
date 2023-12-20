package com.visiongc.app.strategos.web.struts.explicaciones.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.ExplicacionPGN;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryProyectosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.web.struts.explicaciones.forms.GestionarExplicacionesPGNForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;

public class GestionarExplicacionesPGNAction extends VgcAction{
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(url, nombre);
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
		
		String objetoId = request.getParameter("objetoId");
		
		boolean cancelar = false;
		if ((objetoId == null) || (objetoId.equals("")))
		{
			objetoId = (String)request.getSession().getAttribute("objetoId");
			if ((objetoId == null) || (objetoId.equals("")))
				cancelar = true;
		}
		
		if (cancelar)
			return getForwardBack(request, 2, true);
		
		GestionarExplicacionesPGNForm gestionarExplicacionesPGNForm = (GestionarExplicacionesPGNForm)form;
		
		gestionarExplicacionesPGNForm.setObjetoId(Long.parseLong(objetoId));
		
		String atributoOrden = gestionarExplicacionesPGNForm.getAtributoOrden();
		String tipoOrden = gestionarExplicacionesPGNForm.getTipoOrden();
		
		int pagina = gestionarExplicacionesPGNForm.getPagina();
		
		if (atributoOrden == null)
		{
			atributoOrden = "fecha";
			gestionarExplicacionesPGNForm.setAtributoOrden(atributoOrden);
		}
		if (tipoOrden == null)
		{
			tipoOrden = "DESC";
			gestionarExplicacionesPGNForm.setTipoOrden(tipoOrden);
		}

		if (pagina < 1)
			pagina = 1;
		
		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
		
		Map<String, Comparable> filtros = new HashMap<String, Comparable>();		
		if ((gestionarExplicacionesPGNForm.getFiltroTitulo() != null) && (!gestionarExplicacionesPGNForm.getFiltroTitulo().equals("")))
			filtros.put("titulo", gestionarExplicacionesPGNForm.getFiltroTitulo());
		if ((gestionarExplicacionesPGNForm.getFiltroAutor() != null) && (!gestionarExplicacionesPGNForm.getFiltroAutor().equals("")))
			filtros.put("autor", gestionarExplicacionesPGNForm.getFiltroAutor());
		if ((objetoId != null) && (!objetoId.equals("")) && Long.parseLong(objetoId) != 0)
			filtros.put("objetoId", objetoId);
				
		PaginaLista paginaExplicaciones = strategosExplicacionesService.getExplicacionesPGN(pagina, 30, atributoOrden, tipoOrden, true, filtros);
		
		paginaExplicaciones.setTamanoSetPaginas(5);
		
		request.setAttribute("paginaExplicacionesPGN", paginaExplicaciones);
		
		StrategosPryProyectosService strategosPryProyectosService = StrategosServiceFactory.getInstance().openStrategosPryProyectosService();
		PryActividad pryActividad = (PryActividad)strategosPryProyectosService.load(PryActividad.class, new Long(objetoId));
		
		request.getSession().setAttribute("actividad", pryActividad);		
		request.getSession().setAttribute("objetoId", objetoId);
		
		gestionarExplicacionesPGNForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
		gestionarExplicacionesPGNForm.setNombreObjetoKey(pryActividad.getNombre());
		
		strategosExplicacionesService.close();

		if (paginaExplicaciones.getLista().size() > 0)
		{
			ExplicacionPGN explicacion = (ExplicacionPGN)paginaExplicaciones.getLista().get(0);			
			gestionarExplicacionesPGNForm.setSeleccionados(explicacion.getExplicacionId().toString());	
			gestionarExplicacionesPGNForm.setValoresSeleccionados(explicacion.getTitulo());
		}
		else			
			gestionarExplicacionesPGNForm.setSeleccionados(null);
	
		
		return mapping.findForward(forward);
	}
}
