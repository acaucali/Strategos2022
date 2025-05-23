package com.visiongc.app.strategos.web.struts.indicadores.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.web.struts.indicadores.forms.EditarIndicadorForm;
import com.visiongc.app.strategos.web.struts.indicadores.forms.GestionarIndicadoresForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Modulo;
import com.visiongc.framework.model.Modulo.ModuloType;
import com.visiongc.framework.model.Transaccion;
import com.visiongc.framework.transaccion.TransaccionService;

public class GestionarIndicadoresAction extends VgcAction
{
	private PaginaLista paginaIndicadores = null;

	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarIndicadoresForm gestionarIndicadoresForm = (GestionarIndicadoresForm)form;
		Long claseId = new Long((String)request.getSession().getAttribute("claseId"));
		long organizacionId = Long.parseLong((String)request.getSession().getAttribute("organizacionId"));
		Boolean actualizarForma = request.getSession().getAttribute("actualizarForma") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("actualizarForma")) : false;
		if (!actualizarForma)
		{
			actualizarForma = request.getSession().getAttribute("GuardarIndicador") != null ? Boolean.parseBoolean((String)request.getSession().getAttribute("GuardarIndicador")) : false;
			if (request.getSession().getAttribute("GuardarIndicador") != null)
				request.getSession().removeAttribute("GuardarIndicador");
		}

		
						
		if (claseId != null && claseId != 0L && gestionarIndicadoresForm != null && gestionarIndicadoresForm.getClaseId() != null && claseId.longValue() != gestionarIndicadoresForm.getClaseId().longValue())
			gestionarIndicadoresForm.setPagina(1);
		gestionarIndicadoresForm.setClaseId(claseId);

		String atributoOrden = gestionarIndicadoresForm.getAtributoOrden();
		String tipoOrden = gestionarIndicadoresForm.getTipoOrden();
		int pagina = gestionarIndicadoresForm.getPagina();
		boolean mostrarTodas = getPermisologiaUsuario(request).tienePermiso("INDICADOR_VIEWALL");

		gestionarIndicadoresForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("INDICADOR_VIEWALL"));
		gestionarIndicadoresForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("INDICADOR_EDIT"));

		if (atributoOrden == null)
		{
			atributoOrden = "nombre";
			gestionarIndicadoresForm.setAtributoOrden(atributoOrden);
		}
		if (tipoOrden == null)
		{
			tipoOrden = "ASC";
			gestionarIndicadoresForm.setTipoOrden(tipoOrden);
		}

		if (pagina < 1)
			pagina = 1;

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		ConfiguracionUsuario configuracionUsuarioPanel = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Panel.Indicadores", "Ancho");
		if (configuracionUsuarioPanel == null)
		{
			configuracionUsuarioPanel = new ConfiguracionUsuario();
			ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
			pk.setConfiguracionBase("Strategos.Panel.Indicadores");
			pk.setObjeto("Ancho");
			pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
			configuracionUsuarioPanel.setPk(pk);
			configuracionUsuarioPanel.setData("400");

			frameworkService.saveConfiguracionUsuario(configuracionUsuarioPanel, this.getUsuarioConectado(request));
		}
		gestionarIndicadoresForm.setAnchoPorDefecto(configuracionUsuarioPanel.getData());
		
		Long selectFrecuencia = (request.getParameter("frecuencia") != null && request.getParameter("frecuencia") != "" && !request.getParameter("frecuencia").equals("0")) ? Long.parseLong(request.getParameter("frecuencia")) : null;
		Long selectUnidadMedida = (request.getParameter("unidadMedida") != null && request.getParameter("unidadMedida") != "" && !request.getParameter("unidadMedida").equals("0")) ? Long.parseLong(request.getParameter("unidadMedida")) : null;		
			
		if(selectFrecuencia != null) {
			if(selectFrecuencia == 1000)
				selectFrecuencia = null;
		}
						
		if (selectFrecuencia != null)
			request.getSession().setAttribute("selectFrecuenciaIndicador", selectFrecuencia);
		if (selectUnidadMedida != null)
			request.getSession().setAttribute("selectUnidadMedidaIndicador", selectUnidadMedida);
		
		if (request.getParameter("limpiarFiltros") != null) {	
			gestionarIndicadoresForm.setUnidadId(null);
			request.getSession().setAttribute("selectFrecuenciaIndicador", null);
			request.getSession().setAttribute("selectUnidadMedidaIndicador", null);								
		}
		
		Long selectFrecuenciaAttribute = null;
		Long selectUnidadMedidaAttribute = null;
		
		if (request.getSession().getAttribute("selectFrecuenciaIndicador") != null)
			selectFrecuenciaAttribute = (Long) request.getSession().getAttribute("selectFrecuenciaIndicador");
		else 			
			selectFrecuenciaAttribute = null;		
		if (request.getSession().getAttribute("selectUnidadMedidaIndicador") != null)
			selectUnidadMedidaAttribute = (Long) request.getSession().getAttribute("selectUnidadMedidaIndicador");
		else 			
			selectUnidadMedidaAttribute = null;
						
		gestionarIndicadoresForm.setFrecuencia( selectFrecuenciaAttribute);
		gestionarIndicadoresForm.setUnidadId(selectUnidadMedidaAttribute);
		
		gestionarIndicadoresForm.setFrecuencias(Frecuencia.getFrecuencias());
		setUnidadesMedida(gestionarIndicadoresForm, strategosIndicadoresService);
		
		Map<String, Object> filtros = new HashMap<String, Object>();

		filtros.put("organizacionId", Long.toString(organizacionId));
		filtros.put("claseId", claseId.toString());
		if ((gestionarIndicadoresForm.getFiltroNombre() != null) && (!gestionarIndicadoresForm.getFiltroNombre().equals("")))
			filtros.put("nombre", gestionarIndicadoresForm.getFiltroNombre());
		if (!mostrarTodas)
			filtros.put("visible", true);
		if (gestionarIndicadoresForm.getFrecuencia() != null)
			filtros.put("frecuencia", gestionarIndicadoresForm.getFrecuencia().toString());		
		if (gestionarIndicadoresForm.getUnidadId() != null)
			filtros.put("unidadId", gestionarIndicadoresForm.getUnidadId().toString());
		
		

		Integer totalPaginas = 29;
		if (paginaIndicadores != null && paginaIndicadores.getFiltros() != null)
		{
			if (!paginaIndicadores.samePage(pagina, totalPaginas, atributoOrden, tipoOrden, filtros))
				paginaIndicadores = null;
		}

		if (paginaIndicadores == null || actualizarForma)
		{
			paginaIndicadores = strategosIndicadoresService.getIndicadores(pagina, totalPaginas, atributoOrden, tipoOrden, true, filtros, null, null, true);
			paginaIndicadores.setFiltros(filtros);
			paginaIndicadores.setNroPagina(pagina);
			paginaIndicadores.setTamanoPagina(totalPaginas);
			paginaIndicadores.setOrden(atributoOrden);
			paginaIndicadores.setTipoOrden(tipoOrden);
			if (actualizarForma)
				request.getSession().removeAttribute("actualizarForma");
		}

		paginaIndicadores.setTamanoSetPaginas(5);
		request.setAttribute("paginaIndicadores", paginaIndicadores);

		Modulo modulo = new Modulo().getModuloActivo(ModuloType.Indicador.Reporte.ComiteEjecutivo);
		if (modulo != null)
		{
			gestionarIndicadoresForm.setReporteComiteEjecutivo(modulo.getActivo());
			if (gestionarIndicadoresForm.getReporteComiteEjecutivo() != null && gestionarIndicadoresForm.getReporteComiteEjecutivo())
				gestionarIndicadoresForm.setReporte(true);
		}
		strategosIndicadoresService.close();

		Modulo cliente = (Modulo) request.getSession().getAttribute("MIF");
		if (cliente != null && cliente.getActivo())
		{
			//Revisar si hay transacciones
			TransaccionService transaccionService = FrameworkServiceFactory.getInstance().openTransaccionService();
			List<Transaccion> transacciones = transaccionService.getTransacciones(0, 0, "nombre", "ASC", true, null).getLista();
			gestionarIndicadoresForm.setHayTransacciones(transacciones.size() > 0);
			gestionarIndicadoresForm.setTransacciones(transacciones);
			transaccionService.close();
		}

		return mapping.findForward(forward);
	
	}
	
	 private void setUnidadesMedida(GestionarIndicadoresForm gestionarIndicadoresForm, StrategosIndicadoresService strategosIndicadoresService) {
		    StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService(strategosIndicadoresService);

		    gestionarIndicadoresForm.setUnidadesMedida(strategosUnidadesService.getUnidadesMedida(0, 0, "nombre", "asc", false, null).getLista());

		    strategosUnidadesService.close();
		  }
}