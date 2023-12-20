package com.visiongc.app.strategos.web.struts.explicaciones.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.Explicacion.ObjetivoKey;
import com.visiongc.app.strategos.explicaciones.model.Explicacion.TipoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.ExplicacionPGN;
import com.visiongc.app.strategos.explicaciones.model.MemoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.util.TipoMemoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.util.TipoObjetoExplicacion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCelda;
import com.visiongc.app.strategos.web.struts.explicaciones.forms.EditarExplicacionForm;
import com.visiongc.app.strategos.web.struts.explicaciones.forms.EditarExplicacionesPGNForm;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.forms.EditarActividadForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

public class EditarExplicacionPGNAction extends VgcAction
{
	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarExplicacionesPGNForm editarExplicacionForm = (EditarExplicacionesPGNForm)form;
		editarExplicacionForm.clear();

		ActionMessages messages = getMessages(request);

		String explicacionId = request.getParameter("explicacionId");
		String objetoId = request.getParameter("objetoId"); 
		
			
		boolean bloqueado = false;

		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		

		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();

		
		if ((explicacionId != null) && (!explicacionId.equals("")) && (!explicacionId.equals("0")))
		{
			bloqueado = !strategosExplicacionesService.lockForUpdate(request.getSession().getId(), explicacionId, null);

			editarExplicacionForm.setBloqueado(new Boolean(bloqueado));

			ExplicacionPGN explicacion = (ExplicacionPGN)strategosExplicacionesService.load(ExplicacionPGN.class, new Long(explicacionId));

			if (explicacion != null)
			{
				if (bloqueado)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));				
				
				editarExplicacionForm.setExplicacionId(explicacion.getExplicacionId());
				editarExplicacionForm.setCreadoId(explicacion.getCreadoId());
				editarExplicacionForm.setObjetoId(explicacion.getObjetoId());
				editarExplicacionForm.setTitulo(explicacion.getTitulo());
				editarExplicacionForm.setNombreUsuarioCreado(explicacion.getUsuarioCreado().getFullName());
				editarExplicacionForm.setCreado(VgcFormatter.formatearFecha(explicacion.getCreado(), "formato.fecha.corta"));
				editarExplicacionForm.setCumpliendoFechas(explicacion.getCumplimiendoFechas());
				editarExplicacionForm.setExplicacionFechas(explicacion.getExplicacionFechas());
				editarExplicacionForm.setRecibido(explicacion.getRecibido());
				editarExplicacionForm.setExplicacionRecibido(explicacion.getExplicacionRecibido());
				
				if (explicacion.getFecha() != null)
					editarExplicacionForm.setFecha(VgcFormatter.formatearFecha(explicacion.getFecha(), "formato.fecha.corta"));
				else
					editarExplicacionForm.setFecha(null);		
				
				StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
				StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
				
				PryActividad pryActividad = (PryActividad) strategosPryActividadesService.load(PryActividad.class, explicacion.getObjetoId());
				editarExplicacionForm.setNombreObjetoKey(pryActividad.getNombre());
				
				OrganizacionStrategos organizacion = ((OrganizacionStrategos)request.getSession().getAttribute("organizacion"));
				editarExplicacionForm.setNombreOrganizacion(organizacion.getNombre());
				
				strategosOrganizacionesService.close();
				strategosPryActividadesService.close();
			}
			else
			{
				strategosExplicacionesService.unlockObject(request.getSession().getId(), new Long(explicacionId));

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
				forward = "noencontrado";
			}
		}
		else
		{
			
			editarExplicacionForm.clear();				
			editarExplicacionForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
			editarExplicacionForm.setNombreUsuarioCreado(((Usuario)request.getSession().getAttribute("usuario")).getFullName());
			editarExplicacionForm.setCreadoId(((Usuario)request.getSession().getAttribute("usuario")).getUsuarioId());
			editarExplicacionForm.setCreado(VgcFormatter.formatearFecha(new Date(), "formato.fecha.corta"));
			editarExplicacionForm.setNombreUsuarioModificado(((Usuario)request.getSession().getAttribute("usuario")).getFullName());
			editarExplicacionForm.setModificadoId(((Usuario)request.getSession().getAttribute("usuario")).getUsuarioId());
			editarExplicacionForm.setFechaModificado(VgcFormatter.formatearFecha(new Date(), "formato.fecha.corta"));
			
			editarExplicacionForm.setFecha(VgcFormatter.formatearFecha(new Date(), "formato.fecha.corta"));
			
			editarExplicacionForm.setNombreObjetoKey(((PryActividad)request.getSession().getAttribute("actividad")).getNombre());			
			editarExplicacionForm.setObjetoId(((PryActividad)request.getSession().getAttribute("actividad")).getActividadId());
			editarExplicacionForm.setExplicacionId(0L);
		}
		
		strategosExplicacionesService.close();
		
		saveMessages(request, messages);

		if (forward.equals("noencontrado"))
			return getForwardBack(request, 1, true);

		return mapping.findForward(forward);
		
		
	}
}