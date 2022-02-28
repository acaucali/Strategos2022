package com.visiongc.framework.web.struts.actions.usuarios;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Document;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.web.struts.forms.usuarios.ReporteUsuariosForm;

public class ReporteUsuariosOrganizacionPdfAction extends VgcReporteBasicoAction{
	
	@Override
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception {
		return VgcResourceManager.getResourceApp("reporte.framework.usuarios.organizacion.titulo");
	}

	@Override
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) 
			throws Exception {
		
		MessageResources mensajes = getResources(request);
		
		ReporteUsuariosForm reporte = new ReporteUsuariosForm();
		reporte.clear();		
				
		String estatus = (request.getParameter("estatus"));		
		UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService(getLocale(request));			
		
		TablaPDF tabla = null;
	    tabla = new TablaPDF(getConfiguracionPagina(request), request);
	    int[] columnas = new int[7];
	    columnas[0] = 21;
	    columnas[1] = 27;
	    columnas[2] = 10;
	    columnas[3] = 15;
	    columnas[4] = 21;
	    columnas[5] = 21;
	    columnas[6] = 21;
	    tabla.setAmplitudTabla(100);
	    tabla.crearTabla(columnas);			   

	    tabla.setAlineacionHorizontal(1);
	    
	    tabla.agregarCelda(mensajes.getMessage("reporte.framework.usuarios.resumido.uid"));
	    tabla.agregarCelda(mensajes.getMessage("reporte.framework.usuarios.resumido.fullname"));
	    tabla.agregarCelda(mensajes.getMessage("reporte.framework.usuarios.resumido.administrador"));
	    tabla.agregarCelda(mensajes.getMessage("reporte.framework.usuarios.resumido.estatus"));
	    tabla.agregarCelda(mensajes.getMessage("reporte.framework.usuarios.detallado.bloqueado"));	    
	    tabla.agregarCelda(mensajes.getMessage("reporte.framework.grupos.listagrupos.grupo"));
	    tabla.agregarCelda(mensajes.getMessage("reporte.framework.usuarios.organizacion.asociada"));
	    
	    List usuarios = usuariosService.getUsuarios(0, estatus, estatus).getLista();
	    	    
	    
	    if (usuarios.size() > 0) {
	        for (Iterator iter = usuarios.iterator(); iter.hasNext();) {
	          Usuario usuario = (Usuario)iter.next();
	          

	          tabla.setAlineacionHorizontal(0);
	          tabla.agregarCelda(usuario.getUId());
	          tabla.agregarCelda(usuario.getFullName());
	          tabla.setAlineacionHorizontal(1);
	          
	          if (usuario.getIsAdmin().booleanValue()) {
	              tabla.agregarCelda(VgcResourceManager.getResourceApp("comunes.si"));
	            } else {
	              tabla.agregarCelda(VgcResourceManager.getResourceApp("comunes.no"));
	            }
	          tabla.agregarCelda("4");
	          if(usuario.getBloqueado().booleanValue()){
	        	  tabla.agregarCelda(VgcResourceManager.getResourceApp("comunes.si"));
	          }else {
	        	  tabla.agregarCelda(VgcResourceManager.getResourceApp("comunes.no"));
	          }
	          tabla.agregarCelda("6");
	          tabla.agregarCelda("7");
	          System.out.print("Grupo usuario: "+usuario.getUsuarioGrupos().toString());	          	      	        
	          
	        }
	        documento.add(tabla.getTabla());
	        
	      }   
	    usuariosService.close();
	}
	
}
