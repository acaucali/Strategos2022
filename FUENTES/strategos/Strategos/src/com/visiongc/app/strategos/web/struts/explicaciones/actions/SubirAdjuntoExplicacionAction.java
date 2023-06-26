package com.visiongc.app.strategos.web.struts.explicaciones.actions;

import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.web.struts.explicaciones.forms.EditarExplicacionForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

public class SubirAdjuntoExplicacionAction extends VgcAction
{
  @Override
public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  @Override
public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();
    ActionMessages messages = getMessages(request);

    EditarExplicacionForm editarExplicacionForm = (EditarExplicacionForm)request.getSession().getAttribute("editarExplicacionForm");
    AdjuntoExplicacion adjunto = new AdjuntoExplicacion();

    if(editarExplicacionForm.getAdjuntosExplicacion().size() <1)
    {
    	FormFile archivo = (FormFile)editarExplicacionForm.getMultipartRequestHandler().getFileElements().get("adjunto");
        
        if(archivo != null && archivo.getFileName() !=""){
        	
        	

        	
        	adjunto.setTitulo(archivo.getFileName());
        	adjunto.setRuta("");
        	
        	adjunto.setArchivoBytes(archivo.getFileData());
        	adjunto.setArchivo(null);
        	
        	editarExplicacionForm.getAdjuntosExplicacion().add(adjunto);
    	    editarExplicacionForm.setNumeroAdjuntos(new Long(editarExplicacionForm.getAdjuntosExplicacion().size()));
    	    
    	    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("explicacion.subir.exitoso"));
    		saveMessages(request, messages);
        	
        }
    }
    else{
    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("explicacion.subir.limitemaximo"));
		saveMessages(request, messages);
    }


    return mapping.findForward(forward);
  }
}