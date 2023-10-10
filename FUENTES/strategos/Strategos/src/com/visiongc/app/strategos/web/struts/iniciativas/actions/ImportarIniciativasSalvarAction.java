package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.visiongc.app.strategos.web.struts.calculos.forms.CalculoIndicadoresForm.CalcularStatus;
import com.visiongc.app.strategos.web.struts.indicadores.forms.ImportarMedicionesForm;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.ImportarIniciativasForm;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.ImportarIniciativasForm.ImportarStatus;
import com.visiongc.app.strategos.web.struts.servicio.forms.ServicioForm;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.auditoria.model.util.AuditoriaTipoEvento;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.importacion.ImportacionService;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Importacion;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.model.Importacion.ImportacionType;
import com.visiongc.framework.util.FrameworkConnection;
import com.visiongc.servicio.strategos.servicio.model.Servicio;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.LabelCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;

public class ImportarIniciativasSalvarAction extends VgcAction{
	private boolean hayCodigoOrganizacion = false;
	private boolean hayCodigoIniciativa = false;
	private boolean hayNombre = false;
    private boolean hayDescripcion = false;
    private boolean hayTipoIniciativa = false;
    private boolean hayAnio = false;
    private boolean hayFrecuencia = false;
    private boolean hayTipoMedicion = false;
    private boolean hayAlertaVerde = false;
    private boolean hayAlertaAmarilla = false; 
    private boolean hayCrearCuentas = false; 
    private boolean hayUnidadMedida = false; 
    
    @Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
    
    @Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
    	super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
		
		/** Se obtiene el FormBean haciendo el casting respectivo */
		ImportarIniciativasForm importarIniciativasForm = (ImportarIniciativasForm) form;
		
		if (request.getParameter("funcion") != null)
		{
			String funcion = request.getParameter("funcion");
			if (funcion.equals("verificar"))
	    	{
				hayCodigoOrganizacion = false;
				hayCodigoIniciativa = false;
				hayNombre = false;
				hayDescripcion = false;
				hayTipoIniciativa = false;
				hayAnio = false;		
				hayFrecuencia = false;
				hayTipoMedicion = false;
				hayAlertaVerde = false;
				hayAlertaAmarilla = false;
				hayCrearCuentas = false;
				hayUnidadMedida = false;

	    		Verificar(request, importarIniciativasForm);

	    		return mapping.findForward(forward);
	    	}
			else if (funcion.equals("salvar"))
	    	{
	    		int respuesta = Salvar(request, importarIniciativasForm);				
	    		if (respuesta == 10000)
	    			importarIniciativasForm.setStatus(ImportarStatus.getImportarStatusSalvado());
	    		else
	    			importarIniciativasForm.setStatus(ImportarStatus.getImportarStatusNoSalvado());

	    		return mapping.findForward(forward);
	    	}
			else if (funcion.equals("eliminar"))
	    	{
	    		Long id = Long.parseLong(request.getParameter("Id"));
	    		//int respuesta = Eliminar(id, request);
	    		int respuesta = 0;
	    		request.setAttribute("ajaxResponse", respuesta);
	    		return mapping.findForward("ajaxResponse");
	    	}
			else if (funcion.equals("read"))
	    	{
	    		Long id = Long.parseLong(request.getParameter("Id"));

	    		ImportacionService importarService = FrameworkServiceFactory.getInstance().openImportacionService();
	    	    Importacion importacion = (Importacion)importarService.load(Importacion.class, new Long(id));
	    	    importarService.close();

	    	    if (importacion != null)
	    	    	request.setAttribute("ajaxResponse", importacion.getId().toString() + "|" + importacion.getNombre());
	    	    else
	    	    	request.setAttribute("ajaxResponse", "0");
	    		return mapping.findForward("ajaxResponse");
	    	}
			else if (funcion.equals("readFull"))
	    	{
	    		Long id = Long.parseLong(request.getParameter("Id"));

	    		ImportacionService importarService = FrameworkServiceFactory.getInstance().openImportacionService();
	    	    Importacion importacion = (Importacion)importarService.load(Importacion.class, new Long(id));
	    	    importarService.close();

	    	    importarIniciativasForm.setId(importacion.getId());
	    	    importarIniciativasForm.setNombre(importacion.getNombre());
	    	    importarIniciativasForm.setPlanSeleccion(importacion.getNombre());
	    	    importarIniciativasForm.setTipoFuente(importacion.getTipo());
	    	    importarIniciativasForm.setXml(importacion.getConfiguracion());
	    	    importarIniciativasForm.setStatus(ImportarStatus.getImportarStatusReadSuccess());

	    	    return mapping.findForward(forward);
	    	}
			else if (funcion.equals("importar"))
	    	{
	    		String showPresentacion = request.getParameter("showPresentacion") != null ? request.getParameter("showPresentacion").toString() : "0";
	    		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
	    		ConfiguracionUsuario presentacion = new ConfiguracionUsuario();
				ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
				pk.setConfiguracionBase("Strategos.Wizar.Importar.ShowPresentacion");
				pk.setObjeto("ShowPresentacion");
				pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());
				presentacion.setPk(pk);
				presentacion.setData(showPresentacion);
				frameworkService.saveConfiguracionUsuario(presentacion, this.getUsuarioConectado(request));
				frameworkService.close();

	    		Importar(request, importarIniciativasForm);

	    		return mapping.findForward(forward);
	    	}
		}
		
		return mapping.findForward("ajaxResponse");
	}
    
    private void Importar(HttpServletRequest request, ImportarIniciativasForm importarIniciativasForm) throws Exception
	{
    	StringBuffer log = new StringBuffer();

	    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
	    log.append(messageResources.getResource("jsp.asistente.importacion.log.titulocalculo") + "\n");
	    
	    Calendar ahora = Calendar.getInstance();
	    String[] argsReemplazo = new String[2];
	    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
	    log.append(messageResources.getResource("jsp.asistente.importacion.log.fechainiciocalculo", argsReemplazo) + "\n\n");
	    
	    if (importarIniciativasForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && importarIniciativasForm.getExcelTipo().byteValue() == 0)
	    	BuscarDatosExcel2003(request, log, messageResources, importarIniciativasForm);
	    else if (importarIniciativasForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && importarIniciativasForm.getExcelTipo().byteValue() == 1)
	    	BuscarDatosExcel2010(request, log, messageResources, importarIniciativasForm);
	   
	    ahora = Calendar.getInstance();
	    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
	    
	    log.append("\n\n");
	    log.append(messageResources.getResource("jsp.asistente.importacion.log.fechafin.programada", argsReemplazo) + "\n\n");
	    
	    request.getSession().setAttribute("verArchivoLog", log);
	}
    
    private int Salvar(HttpServletRequest request, ImportarIniciativasForm importarIniciativasForm) throws Exception
	{
    	int respuesta = 10000;
    	
    	ActionMessages messages = getMessages(request);
		Usuario usuario = getUsuarioConectado(request);

		Importacion importacion = new Importacion();
		boolean nuevo = false;
		
		importarIniciativasForm.setBdPassword(request.getParameter("password"));
		ImportacionService importarService = FrameworkServiceFactory.getInstance().openImportacionService();
		if ((importarIniciativasForm.getId() != null) && (!importarIniciativasForm.getId().equals(Long.valueOf("0"))))
			importacion = (Importacion)importarService.load(Importacion.class, importarIniciativasForm.getId());
		else
		{
			importacion = new Importacion();
			importacion.setId(new Long(0L));
			nuevo = true;
		}
		
		importacion.setNombre(importarIniciativasForm.getNombre());
		importacion.setTipo(importarIniciativasForm.getTipoFuente());
		importacion.setUsuario(usuario);
		importacion.setUsuarioId(usuario.getUsuarioId());
		importacion.setConfiguracion(importarIniciativasForm.getXml());
		
		respuesta = importarService.saveImportacion(importacion, usuario);
		if (respuesta == 10000)
		{
			importarService.unlockObject(request.getSession().getId(), importarIniciativasForm.getId());
			destruirPoolLocksUsoEdicion(request, importarService);

			if (nuevo)
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
			else
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
		}
		else if (respuesta == 10003)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));

		importarService.close();
				
	    if (importarIniciativasForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && importarIniciativasForm.getExcelTipo().byteValue() == 0)
	    	importarIniciativasForm.setRespuesta(importarIniciativasForm.getBdStatusTabla());
	    else if (importarIniciativasForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && importarIniciativasForm.getExcelTipo().byteValue() == 1)
	    	importarIniciativasForm.setRespuesta(importarIniciativasForm.getBdStatusTabla());	    
    	
    	saveMessages(request, messages);

		return respuesta;
	}
    
    private void BuscarDatosExcel2003(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources, ImportarIniciativasForm importarIniciativasForm) throws Exception
	{
	    String campo = "";
	    int posicionCodigoOrg = 0;
	    int posicionCodigoIni = 0;
	    int posicionNombre = 0;
	    int posicionDescripcion = 0;
	    int posicionTipoIniciativa = 0;
	    int posicionAnio = 0;
	    int posicionFrecuencia = 0;
	    int posicionTipoMedicion = 0;
	    int posicionAlertaVerde = 0;
	    int posicionAlertaAmarilla = 0;
	    int posicionCrearCuentas =  0;
	    int posicionUnidadMedida = 0;
	    int filas = 0;

	    String res = "";
    	ActionMessages messages = getMessages(request);

	    try
	    {
	    	// Lo primero es leer un workbook que representa todo el documento XLS
	    	FormFile archivo = importarIniciativasForm.getFileForm();
	    	Workbook workbook = Workbook.getWorkbook(archivo.getInputStream());

	    	//Elegimos la primera hoja
	    	Sheet sheet = workbook.getSheet(0);
	    	Cell celda = null;
	    	filas = sheet.getRows();
	    	for ( int i=0, z=sheet.getRows(); i<z; i++ )
	    	{
	    		for ( int j=0, k=sheet.getColumns(); j<k; j++ )
	    		{
	    			celda = sheet.getCell(j, i);

	    			// Obtenemos el contenido de la celda
	    			campo = celda.getContents();					
					if (campo.equalsIgnoreCase("CODIGO ORGANIZACION"))
						posicionCodigoOrg = j + 1;
					else if (campo.equalsIgnoreCase("CODIGO PROYECTO"))
						posicionCodigoIni = j + 1;
					else if (campo.equalsIgnoreCase("NOMBRE CORTO"))
						posicionNombre = j + 1;
					else if (campo.equalsIgnoreCase("DESCRIPCION"))
						posicionDescripcion = j + 1;
					else if (campo.equalsIgnoreCase("TIPO PROYECTO"))
						posicionTipoIniciativa = j + 1;
					else if (campo.equalsIgnoreCase("ANIO"))
						posicionAnio = j + 1;
					else if (campo.equalsIgnoreCase("FRECUENCIA"))
						posicionFrecuencia = j + 1;
					else if (campo.equalsIgnoreCase("TIPO MEDICION"))
						posicionTipoMedicion = j + 1;
					else if (campo.equalsIgnoreCase("ALERTA VERDE"))
						posicionAlertaVerde = j + 1;
					else if (campo.equalsIgnoreCase("ALERTA AMARILLA"))
						posicionAlertaAmarilla = j + 1;
					else if (campo.equalsIgnoreCase("CREAR CUENTAS PRESUPUESTO"))
						posicionCrearCuentas = j + 1;
					else if (campo.equalsIgnoreCase("UNIDAD MEDIDA PRESUPUESTO"))
						posicionUnidadMedida = j + 1;

					if (posicionCodigoOrg != 0 && posicionCodigoIni != 0 && posicionNombre != 0 && posicionDescripcion != 0 && posicionTipoIniciativa != 0 && posicionAnio != 0 && posicionFrecuencia != 0 && posicionTipoMedicion != 0 && posicionAlertaVerde != 0 && posicionAlertaAmarilla != 0 && posicionCrearCuentas != 0 && posicionUnidadMedida != 0)					
						break;
	    		}

	    		if (posicionCodigoOrg != 0 && posicionCodigoIni != 0 && posicionNombre != 0 && posicionDescripcion != 0 && posicionTipoIniciativa != 0 && posicionAnio != 0 && posicionFrecuencia != 0 && posicionTipoMedicion != 0 && posicionAlertaVerde != 0 && posicionAlertaAmarilla != 0 && posicionCrearCuentas != 0 && posicionUnidadMedida != 0)
					break;
	    	}

	    	workbook.close();

	    	if (posicionCodigoOrg != 0 && posicionCodigoIni != 0 && posicionNombre != 0 && posicionDescripcion != 0 && posicionTipoIniciativa != 0 && posicionAnio != 0 && posicionFrecuencia != 0 && posicionTipoMedicion != 0 && posicionAlertaVerde != 0 && posicionAlertaAmarilla != 0 && posicionCrearCuentas != 0 && posicionUnidadMedida != 0)
	    	{
	    		String[][] datos = new String[filas][12];

	    		String codigoOrgArchivo = "";
		    	String codigoIniArchivo = "";
		    	String nombreArchivo = "";
		    	String descripcionArchivo = "";
		    	String tipoIniciativaArchivo = "";
		    	String anioArchivo = "";
		    	String frecuenciaArchivo = "";
		    	String tipoMedicionArchivo = "";
		    	String alertaVerdeArchivo = "";
		    	String alertaAmarillaArchivo = "";
		    	String crearCuentasArchivo = "";
		    	String unidadMedidaArchivo = "";

		    	// Lo primero es leer un workbook que representa todo el documento XLS
		    	workbook = Workbook.getWorkbook(archivo.getInputStream());

		    	//Elegimos la primera hoja
		    	sheet = workbook.getSheet(0);
		    	celda = null;

		    	filas = 0;
		    	for ( int r=0, z=sheet.getRows(); r<z; r++ )
		    	{
		    		codigoOrgArchivo = "";
		    		codigoIniArchivo = "";
		    		nombreArchivo = "";
		    		descripcionArchivo = "";
		    		tipoIniciativaArchivo = "";
		    		anioArchivo = "";
		    		frecuenciaArchivo = "";
		    		tipoMedicionArchivo = "";
		    		alertaVerdeArchivo = "";
		    		alertaAmarillaArchivo = "";	
		    		crearCuentasArchivo = "";
			    	unidadMedidaArchivo = "";
		    		for ( int j=0, k=sheet.getColumns(); j<k; j++ )
		    		{
		    			celda = sheet.getCell(j, r);

		    			campo = celda.getContents();
						if ((j + 1) == posicionCodigoOrg)
							codigoOrgArchivo = campo;
						else if ((j + 1) == posicionCodigoIni)
							codigoIniArchivo = campo;
						else if ((j + 1) == posicionNombre)
							nombreArchivo = campo;
						else if ((j + 1) == posicionDescripcion)
							descripcionArchivo = getValue(celda);
						else if ((j + 1) == posicionTipoIniciativa)
							tipoIniciativaArchivo = campo;
						else if ((j + 1) == posicionAnio)
							anioArchivo = campo;
						else if ((j + 1) == posicionFrecuencia)
							frecuenciaArchivo = campo;
						else if ((j + 1) == posicionTipoMedicion)
							tipoMedicionArchivo = campo;
						else if ((j + 1) == posicionAlertaVerde)
							alertaVerdeArchivo = campo;
						else if ((j + 1) == posicionAlertaAmarilla)
							alertaAmarillaArchivo = campo;
						else if ((j + 1) == posicionCrearCuentas)
							crearCuentasArchivo = campo;
						else if ((j + 1) == posicionUnidadMedida)
							unidadMedidaArchivo = campo;

						if (!codigoOrgArchivo.equals("") && !codigoIniArchivo.equals("") && !nombreArchivo.equals("") && !descripcionArchivo.equals("") && !tipoIniciativaArchivo.equals("") && !anioArchivo.equals("") && !frecuenciaArchivo.equals("") && !tipoMedicionArchivo.equals("") && !alertaVerdeArchivo.equals("") && !alertaAmarillaArchivo.equals("") && !crearCuentasArchivo.equals("") && !unidadMedidaArchivo.equals(""))
							break;
					}

		    		if (!codigoOrgArchivo.equals("CODIGO ORGANIZACION") && !codigoIniArchivo.equals("") && !nombreArchivo.equals("") && !descripcionArchivo.equals("") && !tipoIniciativaArchivo.equals("") && !anioArchivo.equals("") && !frecuenciaArchivo.equals("") && !tipoMedicionArchivo.equals("") && !alertaVerdeArchivo.equals("") && !alertaAmarillaArchivo.equals("") && !crearCuentasArchivo.equals("") && !unidadMedidaArchivo.equals(""))
					{											
						datos[filas][0] = codigoOrgArchivo;
						datos[filas][1] = codigoIniArchivo;
						datos[filas][2] = nombreArchivo;
						datos[filas][3] = descripcionArchivo;
						datos[filas][4] = tipoIniciativaArchivo;
						datos[filas][5] = anioArchivo;
						datos[filas][6] = frecuenciaArchivo;
						datos[filas][7] = tipoMedicionArchivo;
						datos[filas][8] = alertaVerdeArchivo;
						datos[filas][9] = alertaAmarillaArchivo;
						datos[filas][10] = crearCuentasArchivo;
						datos[filas][11] = unidadMedidaArchivo;
  						filas++;
					}
		    	}

		    	workbook.close();

				if (datos.length > 0)
					Importar(request, log, messageResources, datos, importarIniciativasForm);
				else
				{
					res = "jsp.asistente.importacion.fin.importar.archivo.empthy";
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(res));
				    saveMessages(request, messages);
				}
	    	}
	    }
	    catch (IOException e)
	    {
	    	e.printStackTrace();
	    	res = "Error";

	    	String[] argsReemplazo = new String[1];
	    	argsReemplazo[0] = e.getMessage();

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.error", argsReemplazo) + "\n\n");

		    importarIniciativasForm.setStatus(ImportarStatus.getImportarStatusImportado());
		    importarIniciativasForm.setRespuesta(res);
	    }

	    if (importarIniciativasForm.getRespuesta().equals("Error"))
	    {
		    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.fin.importar.archivo.error"));
		    saveMessages(request, messages);
	    }
	}
    
    private void BuscarDatosExcel2010(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources, ImportarIniciativasForm importarIniciativasForm) throws Exception
	{
    	    	
	    String campo = "";
	    Integer posicionCodigoOrg = null;
	    Integer posicionCodigoIni = null;
	    Integer posicionNombre = null;
	    Integer posicionDescripcion = null;
	    Integer posicionTipoIniciativa = null;
	    Integer posicionAnio = null;
	    Integer posicionFrecuencia = null;
	    Integer posicionTipoMedicion = null;
	    Integer posicionAlertaVerde = null;
	    Integer posicionAlertaAmarilla = null;
	    Integer posicionCrearCuentas =  null;
	    Integer posicionUnidadMedida = null;
	    Integer filas = 0;
	    Integer filaError = null;
	    Integer columnaError = null;

	    String res = "";
    	ActionMessages messages = getMessages(request);

	    try
	    {
	    	// Lo primero es leer un workbook que representa todo el documento XLS
	    	FormFile archivo = importarIniciativasForm.getFileForm();
	    	XSSFWorkbook workBook = new XSSFWorkbook(archivo.getInputStream());

	    	//Elegimos la primera hoja
	    	XSSFSheet hssfSheet = workBook.getSheetAt(0);
	    	filas = hssfSheet.getPhysicalNumberOfRows();
	    	for (Iterator<Row> i = hssfSheet.rowIterator(); i.hasNext(); )
            {	    		
            	XSSFRow hssfRow = (XSSFRow)i.next();
            	for (Iterator<org.apache.poi.ss.usermodel.Cell> j = hssfRow.cellIterator(); j.hasNext(); )
            	{            
            		XSSFCell hssfCell = (XSSFCell) j.next();
            		campo = hssfCell.toString();            		
					if (campo.equalsIgnoreCase("CODIGO ORGANIZACION"))
						posicionCodigoOrg = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("CODIGO PROYECTO"))
						posicionCodigoIni = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("NOMBRE CORTO"))
						posicionNombre = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("DESCRIPCION"))
						posicionDescripcion = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("TIPO PROYECTO"))
						posicionTipoIniciativa = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("ANIO"))
						posicionAnio = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("FRECUENCIA"))
						posicionFrecuencia = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("TIPO MEDICION"))
						posicionTipoMedicion = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("ALERTA VERDE"))
						posicionAlertaVerde = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("ALERTA AMARILLA"))
						posicionAlertaAmarilla = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("CREAR CUENTAS PRESUPUESTO"))
						posicionCrearCuentas = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("UNIDAD MEDIDA PRESUPUESTO"))
						posicionUnidadMedida = hssfCell.getColumnIndex();

					if (posicionCodigoOrg != null && posicionCodigoIni != null && posicionNombre != null && posicionDescripcion != null && posicionTipoIniciativa != null && posicionAnio != null && posicionFrecuencia != null && posicionTipoMedicion != null && posicionAlertaVerde != null && posicionAlertaAmarilla != null && posicionCrearCuentas != null && posicionUnidadMedida != null)
						break;
            	}
            	if (posicionCodigoOrg != null && posicionCodigoIni != null && posicionNombre != null && posicionDescripcion != null && posicionTipoIniciativa != null && posicionAnio != null && posicionFrecuencia != null && posicionTipoMedicion != null && posicionAlertaVerde != null && posicionAlertaAmarilla != null && posicionCrearCuentas != null && posicionUnidadMedida != null)
					break;
            }	    		    
	    	
	    	if (posicionCodigoOrg != null && posicionCodigoIni != null && posicionNombre != null && posicionDescripcion != null && posicionTipoIniciativa != null && posicionAnio != null && posicionFrecuencia != null && posicionTipoMedicion != null && posicionAlertaVerde != null && posicionAlertaAmarilla != null && posicionCrearCuentas != null && posicionUnidadMedida != null)
	    	{
	    			    		
	    		String[][] datos = new String[filas][12];

		    	String codigoOrgArchivo = "";
		    	String codigoIniArchivo = "";
		    	String nombreArchivo = "";
		    	String descripcionArchivo = "";
		    	String tipoIniciativaArchivo = "";
		    	String anioArchivo = "";
		    	String frecuenciaArchivo = "";
		    	String tipoMedicionArchivo = "";
		    	String alertaVerdeArchivo = "";
		    	String alertaAmarillaArchivo = "";
		    	String crearCuentasArchivo = "";
		    	String unidadMedidaArchivo = "";

		    	// Lo primero es leer un workbook que representa todo el documento XLS
		    	workBook = new XSSFWorkbook(archivo.getInputStream());

		    	//Elegimos la primera hoja
		    	hssfSheet = workBook.getSheetAt(0);
		    	int fila = 0;
		    	for (Iterator<Row> i = hssfSheet.rowIterator(); i.hasNext(); )
		    	{
		    		XSSFRow hssfRow = (XSSFRow)i.next();
		    		codigoOrgArchivo = "";
		    		codigoIniArchivo = "";
		    		nombreArchivo = "";
		    		descripcionArchivo = "";
		    		tipoIniciativaArchivo = "";
		    		anioArchivo = "";
		    		frecuenciaArchivo = "";
		    		tipoMedicionArchivo = "";
		    		alertaVerdeArchivo = "";
		    		alertaAmarillaArchivo = "";	
		    		crearCuentasArchivo = "";
			    	unidadMedidaArchivo = "";
			    	filaError = hssfRow.getRowNum();
			    	for (Iterator<org.apache.poi.ss.usermodel.Cell> j = hssfRow.cellIterator(); j.hasNext(); )
		    		{
			    		XSSFCell hssfCell = (XSSFCell) j.next();
			    		columnaError = hssfCell.getColumnIndex();
						if (hssfCell.getColumnIndex() == posicionCodigoOrg)
							codigoOrgArchivo = hssfCell.toString();
						else if (hssfCell.getColumnIndex() == posicionCodigoIni)													
							codigoIniArchivo = hssfCell.toString();													
						else if (hssfCell.getColumnIndex() == posicionNombre)
							nombreArchivo = hssfCell.toString();
						else if (hssfCell.getColumnIndex() == posicionDescripcion)
							descripcionArchivo = hssfCell.toString();
						else if (hssfCell.getColumnIndex() == posicionTipoIniciativa) {
							if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING)
								tipoIniciativaArchivo = hssfCell.toString();
							else if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC)
								tipoIniciativaArchivo = ((Integer) ((int) hssfCell.getNumericCellValue())).toString();
						}
						else if (hssfCell.getColumnIndex() == posicionAnio) {
							if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING)
								anioArchivo = hssfCell.toString();
							else if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC)
								anioArchivo = ((Integer) ((int) hssfCell.getNumericCellValue())).toString();
						}
						else if (hssfCell.getColumnIndex() == posicionFrecuencia)
							frecuenciaArchivo = hssfCell.toString();
						else if (hssfCell.getColumnIndex() == posicionTipoMedicion) {
							if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING)
								tipoMedicionArchivo = hssfCell.toString();
							else if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC)
								tipoMedicionArchivo = ((Integer) ((int) hssfCell.getNumericCellValue())).toString();
						}
						else if (hssfCell.getColumnIndex() == posicionAlertaVerde) {
							if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING)
								alertaVerdeArchivo = hssfCell.toString();
							else if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC)
								alertaVerdeArchivo = ((Integer) ((int) hssfCell.getNumericCellValue())).toString();
						}
						else if (hssfCell.getColumnIndex() == posicionAlertaAmarilla) {
							if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING)
								alertaAmarillaArchivo = hssfCell.toString();
							else if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC)
								alertaAmarillaArchivo = ((Integer) ((int) hssfCell.getNumericCellValue())).toString();
						}			
						else if (hssfCell.getColumnIndex() == posicionCrearCuentas)
							crearCuentasArchivo = hssfCell.toString();
						else if (hssfCell.getColumnIndex() == posicionUnidadMedida) {
							if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING)
								unidadMedidaArchivo = hssfCell.toString();
							else if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC)
								unidadMedidaArchivo = ((Integer) ((int) hssfCell.getNumericCellValue())).toString();
						}			
						if (!codigoOrgArchivo.equals("") && !codigoIniArchivo.equals("") && !nombreArchivo.equals("") && !descripcionArchivo.equals("") && !tipoIniciativaArchivo.equals("") && !anioArchivo.equals("") && !frecuenciaArchivo.equals("") && !tipoMedicionArchivo.equals("") && !alertaVerdeArchivo.equals("") && !alertaAmarillaArchivo.equals("") && !crearCuentasArchivo.equals("") && !unidadMedidaArchivo.equals(""))
							break;
					}
			    	
					if (!codigoOrgArchivo.equals("CODIGO ORGANIZACION") && !codigoIniArchivo.equals("") && !nombreArchivo.equals("") && !descripcionArchivo.equals("") && !tipoIniciativaArchivo.equals("") && !anioArchivo.equals("") && !frecuenciaArchivo.equals("") && !tipoMedicionArchivo.equals("") && !alertaVerdeArchivo.equals("") && !alertaAmarillaArchivo.equals("") && !crearCuentasArchivo.equals("") && !unidadMedidaArchivo.equals(""))
					{										
						datos[fila][0] = codigoOrgArchivo;
						datos[fila][1] = codigoIniArchivo;
						datos[fila][2] = nombreArchivo;
						datos[fila][3] = descripcionArchivo;
						datos[fila][4] = tipoIniciativaArchivo;
						datos[fila][5] = anioArchivo;
						datos[fila][6] = frecuenciaArchivo;
						datos[fila][7] = tipoMedicionArchivo;
						datos[fila][8] = alertaVerdeArchivo;
						datos[fila][9] = alertaAmarillaArchivo;
						datos[fila][10] = crearCuentasArchivo;
						datos[fila][11] = unidadMedidaArchivo;
  						fila++;
					}
		    	}		   
		    			    	
				if (datos.length > 0)
					Importar(request, log, messageResources, datos, importarIniciativasForm);
				else
				{
					res = "jsp.asistente.importacion.fin.importar.archivo.empthy";
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(res));
				    saveMessages(request, messages);
				}
	    	}
	    }
	    catch (IOException e)
	    {
	    	e.printStackTrace();
	    	res = "Error";

	    	String[] argsReemplazo = new String[1];
	    	argsReemplazo[0] = e.getMessage();

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.error", argsReemplazo) + "\n\n");

		    importarIniciativasForm.setStatus(ImportarStatus.getImportarStatusImportado());
		    importarIniciativasForm.setRespuesta(res);
	    }
	    catch (Throwable e)
	    {
	    	e.printStackTrace();
	    	filaError++;
	    	columnaError++;
	    	res = "FileError|" + filaError.toString() + "|" + columnaError.toString();

	    	String[] argsReemplazo = new String[1];
	    	argsReemplazo[0] = e.getMessage();

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.error", argsReemplazo) + "\n\n");

		    importarIniciativasForm.setStatus(ImportarStatus.getImportarStatusFileError());
		    importarIniciativasForm.setRespuesta(res);
	    }

	    if (importarIniciativasForm.getRespuesta().equals("Error"))
	    {
		    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.fin.importar.archivo.error"));
		    saveMessages(request, messages);
	    }
	}
    
    private String getValue(Cell celda)
	{
		String value = "";

		if (celda.getType() == CellType.LABEL)
		{
			LabelCell lc = (LabelCell) celda;
			value = lc.getString();
		}
		else if (celda.getType() == CellType.NUMBER || celda.getType() == CellType.NUMBER_FORMULA)
		{
			NumberCell nc = (NumberCell) celda;
			Double valor = nc.getValue();
			value = valor.toString();
		}
		else if (celda.getType() == CellType.DATE)
		{
			DateCell dc = (DateCell) celda;
			Date valor = dc.getDate();
			value = valor.toString();
		}

		return value;
	}
    
    private void Importar(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources, String[][] datos, ImportarIniciativasForm importarIniciativasForm) throws Exception
	{
    	    	
    	ActionMessages messages = getMessages(request);
		if (datos.length == 0)
		{
			String res = "jsp.asistente.importacion.fin.importar.archivo.empthy";
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(res));
		    saveMessages(request, messages);
		}
		else
		{
			ServicioForm servicioForm = new ServicioForm();

			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
			Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");

			if (configuracion == null)
			{
				importarIniciativasForm.setStatus(ImportarStatus.getImportarStatusNoConfigurado());
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.status.configuracion.noexiste"));
				saveMessages(request, messages);
			}
			else
			{
				//XML
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		        DocumentBuilder db = dbf.newDocumentBuilder();
		        Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
		        doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("properties");
				Element eElement = (Element) nList.item(0);
				/** Se obtiene el FormBean haciendo el casting respectivo */
				String url = VgcAbstractService.getTagValue("url", eElement);
				String driver = VgcAbstractService.getTagValue("driver", eElement);
				String user = VgcAbstractService.getTagValue("user", eElement);
				String password = VgcAbstractService.getTagValue("password", eElement);
				password = new com.visiongc.framework.web.struts.forms.servicio.GestionarServiciosForm().getPasswordConexionDecriptado(password);

				if (!new FrameworkConnection().testConnection(url, driver, user, password))
				{
					importarIniciativasForm.setStatus(ImportarStatus.getImportarStatusNoConfigurado());
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.status.configuracion.noexiste"));
					saveMessages(request, messages);
				}
				else
				{
					byte tipoMedicion = request.getParameter("tipoMedicion") != null ? Byte.parseByte(request.getParameter("tipoMedicion").toString()) : 0;
					Usuario usuario = getUsuarioConectado(request);
					boolean todosOrganizacion = request.getParameter("todosOrganizacion") != null ? (request.getParameter("todosOrganizacion").toString().equals("1") ? true : false) : false;
					servicioForm.setProperty("url", url);
					servicioForm.setProperty("driver", driver);
					servicioForm.setProperty("user", user);
					servicioForm.setProperty("password", password);
					servicioForm.setProperty("tipoFuente", ((Integer)(!request.getParameter("tipoFuente").toString().equals("") ? Integer.parseInt(request.getParameter("tipoFuente").toString()) : 0)).toString());
					servicioForm.setProperty("separador", request.getParameter("separador").toString());
					servicioForm.setProperty("logMediciones", ((Boolean)(request.getParameter("logMediciones") != null ? (request.getParameter("logMediciones").toString().equals("1") ? true : false) : false)).toString());
					servicioForm.setProperty("logErrores", ((Boolean)(request.getParameter("logErrores") != null ? (request.getParameter("logErrores").toString().equals("1") ? true : false) : false)).toString());
					servicioForm.setProperty("todosPlanes", ((Boolean)(request.getParameter("todosPlanes") != null ? (request.getParameter("todosPlanes").toString().equals("1") ? true : false) : false)).toString());
					servicioForm.setProperty("todosOrganizacion", Boolean.toString(todosOrganizacion));
					servicioForm.setProperty("tipoMedicion", ((Byte)(tipoMedicion)).toString());
					servicioForm.setProperty("tipoImportacion", ((Byte)(request.getParameter("tipoImportacion") != null ? Byte.parseByte(request.getParameter("tipoImportacion").toString()) : 0)).toString());
					servicioForm.setProperty("calcular", ((Boolean)(request.getParameter("calcularMediciones") != null ? (request.getParameter("calcularMediciones").toString().equals("1") ? true : false) : false)).toString());
					servicioForm.setProperty("logConsolaMetodos", ((Boolean)(false)).toString());
					servicioForm.setProperty("logConsolaDetallado", ((Boolean)(false)).toString());
					servicioForm.setProperty("tomarPeriodosSinMedicionConValorCero", ((Boolean)(false)).toString());
					servicioForm.setProperty("activarSheduler", ((Boolean)(true)).toString());
					servicioForm.setProperty("unidadTiempo", ((Integer)(3)).toString());
					servicioForm.setProperty("numeroEjecucion", ((Integer)(1)).toString());					
					servicioForm.setProperty("usuarioId", usuario.getUsuarioId().toString());
					if (!todosOrganizacion)
						servicioForm.setProperty("organizacionId", (String)request.getSession().getAttribute("organizacionId"));

					StringBuffer logBefore = log;
					boolean respuesta = new com.visiongc.servicio.strategos.importar.ImportarManager(servicioForm.Get(), log, com.visiongc.servicio.web.importar.util.VgcMessageResources.getVgcMessageResources("StrategosWeb")).EjecutarIniciativa(datos, usuario);
					log = logBefore;
					String res = "";
					
						res = "Successful";
					    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.archivo.configuracion.alerta.fin"));
					    saveMessages(request, messages);

						if (usuario != null)
						{
							Calendar ahora = Calendar.getInstance();
							byte tipoEvento = AuditoriaTipoEvento.getAuditoriaTipoEventoImportar();
							Servicio servicio = new Servicio();
							servicio.setUsuarioId(usuario.getUsuarioId());
							servicio.setFecha(VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy") + " " + VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a"));
							servicio.setNombre(messageResources.getResource("jsp.asistente.importacion.titulocalculo"));

							String[] argsReemplazo = new String[2];
						    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
						    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

							servicio.setMensaje(messageResources.getResource("jsp.asistente.importacion.log.fechafin.programada", argsReemplazo));
							servicio.setEstatus(CalcularStatus.getCalcularStatusCalculado());

							frameworkService.registrarAuditoriaEvento(servicio, usuario, tipoEvento);
						}
					

					importarIniciativasForm.setStatus(ImportarStatus.getImportarStatusImportado());
					importarIniciativasForm.setRespuesta(res);
				}
			}

			frameworkService.close();
		}
	}
    
    private void Verificar(HttpServletRequest request, ImportarIniciativasForm importarIniciativasForm)
	{
	    
	    if (importarIniciativasForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && importarIniciativasForm.getExcelTipo().byteValue() == 0)
	    	VerificarExcel2007(request, importarIniciativasForm);
	    else if (importarIniciativasForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && importarIniciativasForm.getExcelTipo().byteValue() == 1)
	    	VerificarExcel2010(request, importarIniciativasForm);	    
	}
    
    private void VerificarExcel2007(HttpServletRequest request, ImportarIniciativasForm importarIniciativasForm)
	{
	    String res;
	    String campo;
	    	   
	    try
	    {
	    	// Lo primero es leer un workbook que representa todo el documento XLS
	    	FormFile archivo = (FormFile) importarIniciativasForm.getMultipartRequestHandler().getFileElements().get("file_browse");
	    	importarIniciativasForm.setFileForm(archivo);
	    	Workbook workbook = Workbook.getWorkbook(archivo.getInputStream());

	    	//Elegimos la primera hoja
	    	Sheet sheet = workbook.getSheet(0);
	    	Cell celda = null;

	    	for ( int i=0, z=sheet.getRows(); i<z; i++ )
	    	{
	    		for ( int j=0, k=sheet.getColumns(); j<k; j++ )
	    		{
	    			celda = sheet.getCell(j, i);

	    			// Obtenemos el contenido de la celda
	    			campo = celda.getContents();

					if (!hayCodigoOrganizacion && campo.equalsIgnoreCase("CODIGO ORGANIZACION"))
						hayCodigoOrganizacion = true;
					else if (!hayCodigoIniciativa && campo.equalsIgnoreCase("CODIGO PROYECTO"))
						hayCodigoIniciativa = true;
					else if (!hayNombre && campo.equalsIgnoreCase("NOMBRE CORTO"))
						hayNombre = true;
					else if (!hayDescripcion && campo.equalsIgnoreCase("DESCRIPCION"))
						hayDescripcion = true;
					else if (!hayTipoIniciativa && campo.equalsIgnoreCase("TIPO PROYECTO"))
						hayTipoIniciativa = true;
					else if (!hayAnio && campo.equalsIgnoreCase("ANIO"))
						hayAnio = true;
					else if (!hayFrecuencia && campo.equalsIgnoreCase("FRECUENCIA"))
						hayFrecuencia = true;
					else if (!hayTipoMedicion && campo.equalsIgnoreCase("TIPO MEDICION"))
						hayTipoMedicion = true;
					else if (!hayAlertaVerde && campo.equalsIgnoreCase("ALERTA VERDE"))
						hayAlertaVerde = true;
					else if (!hayAlertaAmarilla && campo.equalsIgnoreCase("ALERTA AMARILLA"))
						hayAlertaAmarilla = true;
					else if (!hayCrearCuentas && campo.equalsIgnoreCase("CREAR CUENTAS PRESUPUESTO"))
						hayCrearCuentas = true;
					else if (!hayUnidadMedida && campo.equalsIgnoreCase("UNIDAD MEDIDA PRESUPUESTO"))
						hayUnidadMedida = true;

					if (hayCodigoOrganizacion && hayCodigoIniciativa && hayNombre && hayDescripcion && hayTipoIniciativa && hayAnio && hayFrecuencia && hayTipoMedicion && hayAlertaVerde && hayAlertaAmarilla && hayCrearCuentas && hayUnidadMedida)
						break;
	    		}

	    		if (hayCodigoOrganizacion && hayCodigoIniciativa && hayNombre && hayDescripcion && hayTipoIniciativa && hayAnio && hayFrecuencia && hayTipoMedicion && hayAlertaVerde && hayAlertaAmarilla && hayCrearCuentas && hayUnidadMedida)
					break;
	    	}

	    	workbook.close();

	    	res = "CODIGO ORGANIZACION=" + (hayCodigoOrganizacion ? "true" : "false") + ",";
		    res = res + "CODIGO PROYECTO=" + (hayCodigoIniciativa ? "true" : "false") + ",";
		    res = res + "NOMBRE CORTO=" + (hayNombre ? "true" : "false") + ",";
		    res = res + "DESCRIPCION=" + (hayDescripcion ? "true" : "false")+ ",";
		    res = res + "TIPO PROYECTO=" + (hayTipoIniciativa ? "true" : "false")+ ",";
		    res = res + "ANIO=" + (hayAnio ? "true" : "false")+ ",";
		    res = res + "FRECUENCIA=" + (hayFrecuencia ? "true" : "false")+ ",";
		    res = res + "TIPO MEDICION=" + (hayTipoMedicion ? "true" : "false")+ ",";
		    res = res + "ALERTA VERDE=" + (hayAlertaVerde ? "true" : "false")+ ",";
		    res = res + "ALERTA AMARILLA=" + (hayAlertaAmarilla ? "true" : "false")+ ",";
		    res = res + "CREAR CUENTAS PRESUPUESTO=" + (hayCrearCuentas ? "true" : "false")+ ",";
		    res = res + "UNIDAD MEDIDA PRESUPUESTO=" + (hayUnidadMedida ? "true" : "false");
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    	res = "Error";
	    }

	    importarIniciativasForm.setStatus(ImportarStatus.getImportarStatusValidado());
	    importarIniciativasForm.setRespuesta(res);	    
	}
    
    private void VerificarExcel2010(HttpServletRequest request, ImportarIniciativasForm importarIniciativasForm)
	{
	    String res;
	    String campo;

	    try
	    {
	    	// Lo primero es leer un workbook que representa todo el documento XLS
	    	FormFile archivo = (FormFile) importarIniciativasForm.getMultipartRequestHandler().getFileElements().get("file_browse");
	    	importarIniciativasForm.setFileForm(archivo);
	    	XSSFWorkbook workBook = new XSSFWorkbook(archivo.getInputStream());

	    	//Elegimos la primera hoja
	    	XSSFSheet hssfSheet = workBook.getSheetAt(0);

	    	Iterator<Row> rowIterator = hssfSheet.rowIterator();
	    	while (rowIterator.hasNext())
	    	{
	    		XSSFRow hssfRow = (XSSFRow) rowIterator.next();
		    	Iterator<org.apache.poi.ss.usermodel.Cell> iterator = hssfRow.cellIterator();
		    	while (iterator.hasNext())
		    	{
		    		XSSFCell hssfCell = (XSSFCell) iterator.next();

	    			// Obtenemos el contenido de la celda
	    			campo = hssfCell.toString();
	    				    			
	    			if (!hayCodigoOrganizacion && campo.equalsIgnoreCase("CODIGO ORGANIZACION")) 	    			
						hayCodigoOrganizacion = true;	    			
					else if (!hayCodigoIniciativa && campo.equalsIgnoreCase("CODIGO PROYECTO")) 						
						hayCodigoIniciativa = true;					
					else if (!hayNombre && campo.equalsIgnoreCase("NOMBRE CORTO"))
						hayNombre = true;
					else if (!hayDescripcion && campo.equalsIgnoreCase("DESCRIPCION"))
						hayDescripcion = true;
					else if (!hayTipoIniciativa && campo.equalsIgnoreCase("TIPO PROYECTO"))
						hayTipoIniciativa = true;
					else if (!hayAnio && campo.equalsIgnoreCase("ANIO"))
						hayAnio = true;
					else if (!hayFrecuencia && campo.equalsIgnoreCase("FRECUENCIA"))
						hayFrecuencia = true;
					else if (!hayTipoMedicion && campo.equalsIgnoreCase("TIPO MEDICION"))
						hayTipoMedicion = true;
					else if (!hayAlertaVerde && campo.equalsIgnoreCase("ALERTA VERDE"))
						hayAlertaVerde = true;
					else if (!hayAlertaAmarilla && campo.equalsIgnoreCase("ALERTA AMARILLA"))
						hayAlertaAmarilla = true;
					else if (!hayCrearCuentas && campo.equalsIgnoreCase("CREAR CUENTAS PRESUPUESTO"))
						hayCrearCuentas = true;
					else if (!hayUnidadMedida && campo.equalsIgnoreCase("UNIDAD MEDIDA PRESUPUESTO"))
						hayUnidadMedida = true;

					if (hayCodigoOrganizacion && hayCodigoIniciativa && hayNombre && hayDescripcion && hayTipoIniciativa && hayAnio && hayFrecuencia && hayTipoMedicion && hayAlertaVerde && hayAlertaAmarilla && hayCrearCuentas && hayUnidadMedida)
						break;
	    		}

	    		if (hayCodigoOrganizacion && hayCodigoIniciativa && hayNombre && hayDescripcion && hayTipoIniciativa && hayAnio && hayFrecuencia && hayTipoMedicion && hayAlertaVerde && hayAlertaAmarilla && hayCrearCuentas && hayUnidadMedida)
					break;
	    	}
	    	
	    	res = "CODIGO ORGANIZACION=" + (hayCodigoOrganizacion ? "true" : "false") + ",";
		    res = res + "CODIGO PROYECTO=" + (hayCodigoIniciativa ? "true" : "false") + ",";
		    res = res + "NOMBRE CORTO=" + (hayNombre ? "true" : "false") + ",";
		    res = res + "DESCRIPCION=" + (hayDescripcion ? "true" : "false")+ ",";
		    res = res + "TIPO PROYECTO=" + (hayTipoIniciativa ? "true" : "false")+ ",";
		    res = res + "ANIO=" + (hayAnio ? "true" : "false")+ ",";
		    res = res + "FRECUENCIA=" + (hayFrecuencia ? "true" : "false")+ ",";
		    res = res + "TIPO MEDICION=" + (hayTipoMedicion ? "true" : "false")+ ",";
		    res = res + "ALERTA VERDE=" + (hayAlertaVerde ? "true" : "false")+ ",";
		    res = res + "ALERTA AMARILLA=" + (hayAlertaAmarilla ? "true" : "false")+ ",";
		    res = res + "CREAR CUENTAS PRESUPUESTO=" + (hayCrearCuentas ? "true" : "false")+ ",";
		    res = res + "UNIDAD MEDIDA PRESUPUESTO=" + (hayUnidadMedida ? "true" : "false");
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    	res = "Error";
	    }

	    importarIniciativasForm.setStatus(ImportarStatus.getImportarStatusValidado());
	    importarIniciativasForm.setRespuesta(res);	    
	}
}
