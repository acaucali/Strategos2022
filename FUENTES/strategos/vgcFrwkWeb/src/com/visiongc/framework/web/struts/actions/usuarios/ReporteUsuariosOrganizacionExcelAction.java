package com.visiongc.framework.web.struts.actions.usuarios;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.usuarios.UsuariosService;


public class ReporteUsuariosOrganizacionExcelAction extends VgcAction{

	public void updateNavigationBar(NavigationBar navBar, String url,
			String nombre) {
		navBar.agregarUrl(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		super.execute(mapping, form, request, response);
		
		int x=1;
		String forward = mapping.getParameter();
		String estatus = (request.getParameter("estatus"));	
		
	    MessageResources messageResources = getResources(request);
	    	    
	    UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService(getLocale(request));
	    
	    List usuarios = usuariosService.getUsuarios(0, estatus, estatus).getLista();
	    
	    Object[][] data = new Object[usuarios.size()+1][7];
		 
	    data[0][0]=messageResources.getMessage("reporte.framework.usuarios.resumido.uid");
    	data[0][1]=messageResources.getMessage("reporte.framework.usuarios.resumido.fullname");
    	data[0][2]=messageResources.getMessage("reporte.framework.usuarios.resumido.administrador");
    	data[0][3]=messageResources.getMessage("reporte.framework.usuarios.resumido.estatus");
    	data[0][4]=messageResources.getMessage("reporte.framework.usuarios.detallado.bloqueado");
    	data[0][5]=messageResources.getMessage("reporte.framework.grupos.listagrupos.grupo");
    	data[0][6]=messageResources.getMessage("reporte.framework.usuarios.organizacion.asociada");
    	
    	if (usuarios.size() > 0)
		{
			for (Iterator<Usuario> iter = usuarios.iterator(); iter.hasNext();)
			{
				Usuario usuario = (Usuario)iter.next();
				
				data[x][0]=usuario.getUId();
				data[x][1]=usuario.getFullName();
				
				if (usuario.getIsAdmin().booleanValue()) {
					data[x][2]=VgcResourceManager.getResourceApp("comunes.si");
		        } else {
		        	data[x][2]=VgcResourceManager.getResourceApp("comunes.no");
		        }
				 
				data[x][3]="4";
		        
				if(usuario.getBloqueado().booleanValue()){
					data[x][4]=VgcResourceManager.getResourceApp("comunes.no");
		        }else {
		        	data[x][4]=VgcResourceManager.getResourceApp("comunes.si");
		        }
								
				data[x][5]="6";
				data[x][6]="7";
				x=x+1;
			}
		}
    	
    	HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, "Hoja excel");
        
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        
        HSSFRow headerRow = sheet.createRow(0);
        
        String header = "Reporte Usuarios por Organizacion";
        HSSFCell cell = headerRow.createCell(1);
        cell.setCellStyle(headerStyle);
        cell.setCellValue(header);
        
        for (int i = 0; i < data.length; ++i) {
            HSSFRow dataRow = sheet.createRow(i + 1);

            Object[] d = data[i];
            String login = (String) d[0];
            String nombreUs = (String) d[1];
            String admin = (String) d[2];
            String estado=(String)d[3];
            String bloqueado = (String) d[4];
            String grupo = (String) d[5];
            String organizacion =(String) d[6];
            

           
            dataRow.createCell(0).setCellValue(login);
            dataRow.createCell(1).setCellValue(nombreUs);
            dataRow.createCell(2).setCellValue(admin);
            dataRow.createCell(3).setCellValue(estado);
            dataRow.createCell(4).setCellValue(bloqueado);
            dataRow.createCell(5).setCellValue(grupo);
            dataRow.createCell(6).setCellValue(organizacion);            
        }
        
        HSSFRow dataRow = sheet.createRow(1 + data.length);   
        
        Date date = new Date();
        SimpleDateFormat hourdateFormat = new SimpleDateFormat("HHmmss_ddMMyyyy");
       
        
        String archivo="UsuariosOrganizacion_"+hourdateFormat.format(date)+".xls"; 
        
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment;filename="+archivo);    
       
        ServletOutputStream file  = response.getOutputStream();
        
        workbook.write(file);
        file.close();
		
        return mapping.findForward(forward);  
		
	}
		
	
}
