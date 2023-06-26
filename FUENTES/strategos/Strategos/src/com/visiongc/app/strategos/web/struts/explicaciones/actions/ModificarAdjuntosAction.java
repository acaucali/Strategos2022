package com.visiongc.app.strategos.web.struts.explicaciones.actions;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.web.struts.explicaciones.forms.EditarExplicacionForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

public class ModificarAdjuntosAction  extends VgcAction{

	@Override
	public void updateNavigationBar(NavigationBar arg0, String arg1, String arg2) {	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String forward = mapping.getParameter();
		ActionMessages messages = new ActionMessages();

		
		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
		List<AdjuntoExplicacion> adjuntos = strategosExplicacionesService.getAdjuntos();
		Integer count = 0;
		Integer timer = 0;
		Integer total = adjuntos.size();
		List<String> cambios = (List<String>) new ArrayList();
		
			for (Iterator<AdjuntoExplicacion> iter = adjuntos.iterator(); iter.hasNext(); )
			{
				
				AdjuntoExplicacion adjunto =  (AdjuntoExplicacion)iter.next();
				 try {
					 FileInputStream archivo = new FileInputStream(adjunto.getRuta());
					 if (archivo.available() != 0) {
						 
						 ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				         byte[] buffer = new byte[16384];
				         int len;
				         while ((len = archivo.read(buffer)) != -1) {
				                outputStream.write(buffer, 0, len);
				         }
				         byte[] bytes = outputStream.toByteArray();
				            
				         outputStream.close();
						 archivo.close();
						 
						 adjunto.setArchivoBytes(bytes);
						 adjunto.setArchivo(null);
					 }
					 count++;
					 strategosExplicacionesService.saveAdjuntoExplicacion(adjunto, adjunto.getPk().getExplicacionId(), usuario);
					 cambios.add(adjunto.getTitulo());
					 
			     }catch (IOException ex) {	 
			            System.out.println(ex);		        
			     }
			}	
		
		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.modificacion.adjunto.conteo", count, total));
		for(Iterator<String> iter = cambios.iterator(); iter.hasNext(); ) {
			String titulo =  (String)iter.next();
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.modificacion.adjunto", titulo ));			
		}
		saveMessages(request, messages);
		return  getForwardBack(request, 1, true);
	}

	
	
}
