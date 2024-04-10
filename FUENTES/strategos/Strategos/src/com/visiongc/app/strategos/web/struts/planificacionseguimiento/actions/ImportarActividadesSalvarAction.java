package com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.text.SimpleDateFormat;

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
import com.visiongc.app.strategos.web.struts.iniciativas.forms.ImportarIniciativasForm;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.ImportarIniciativasForm.ImportarStatus;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.forms.ImportarActividadesForm;
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

public class ImportarActividadesSalvarAction extends VgcAction {

	private boolean hayCodigoIniciativa = false;
	private boolean hayNombre = false;
	private boolean hayDescripcion = false;
	private boolean hayFechaInicio = false;
	private boolean hayFechaCulminacion = false;
	private boolean hayAlertaVerde = false;
	private boolean hayAlertaAmarilla = false;
	private boolean hayUnidadMedida = false;
	private boolean hayNumeroActividad = false;
	private boolean hayCodigoEnlace = false;
	private boolean hayPeso = false; 

	@Override
	public void updateNavigationBar(NavigationBar arg0, String arg1, String arg2) {
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();

		ImportarActividadesForm importarActividadesForm = (ImportarActividadesForm) form;

		if (request.getParameter("funcion") != null) {
			String funcion = request.getParameter("funcion");
			if (funcion.equals("verificar")) {

				hayCodigoIniciativa = false;
				hayNombre = false;
				hayDescripcion = false;
				hayFechaInicio = false;
				hayFechaCulminacion = false;
				hayAlertaVerde = false;
				hayAlertaAmarilla = false;
				hayUnidadMedida = false;
				hayNumeroActividad = false;
				hayCodigoEnlace = false;
				hayPeso = false;

				Verificar(request, importarActividadesForm);

				return mapping.findForward(forward);
			} else if (funcion.equals("salvar")) {
				int respuesta = Salvar(request, importarActividadesForm);
				
				if (respuesta == 10000)
					importarActividadesForm.setStatus(ImportarStatus.getImportarStatusSalvado());
				else
					importarActividadesForm.setStatus(ImportarStatus.getImportarStatusNoSalvado());

				return mapping.findForward(forward);
			} else if (funcion.equals("read")) {
				Long id = Long.parseLong(request.getParameter("Id"));

				ImportacionService importarService = FrameworkServiceFactory.getInstance().openImportacionService();
				Importacion importacion = (Importacion) importarService.load(Importacion.class, new Long(id));
				importarService.close();

				if (importacion != null)
					request.setAttribute("ajaxResponse",
							importacion.getId().toString() + "|" + importacion.getNombre());
				else
					request.setAttribute("ajaxResponse", "0");
				return mapping.findForward("ajaxResponse");
			} else if (funcion.equals("readFull")) {
				Long id = Long.parseLong(request.getParameter("Id"));

				ImportacionService importarService = FrameworkServiceFactory.getInstance().openImportacionService();
				Importacion importacion = (Importacion) importarService.load(Importacion.class, new Long(id));
				importarService.close();

				importarActividadesForm.setId(importacion.getId());
				importarActividadesForm.setNombre(importacion.getNombre());
				importarActividadesForm.setPlanSeleccion(importacion.getNombre());
				importarActividadesForm.setTipoFuente(importacion.getTipo());
				importarActividadesForm.setXml(importacion.getConfiguracion());
				importarActividadesForm.setStatus(ImportarStatus.getImportarStatusReadSuccess());

				return mapping.findForward(forward);
			} else if (funcion.equals("importar")) {
				String showPresentacion = request.getParameter("showPresentacion") != null
						? request.getParameter("showPresentacion").toString()
						: "0";
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

				Importar(request, importarActividadesForm);

				return mapping.findForward(forward);
			}
		}

		return mapping.findForward("ajaxResponse");
	}

	private void Importar(HttpServletRequest request, ImportarActividadesForm importarActividadesForm)
			throws Exception {		
		StringBuffer log = new StringBuffer();

		VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
		log.append(messageResources.getResource("jsp.asistente.importacion.log.titulocalculo") + "\n");

		Calendar ahora = Calendar.getInstance();
		String[] argsReemplazo = new String[2];
		argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
		argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
		log.append(messageResources.getResource("jsp.asistente.importacion.log.fechainiciocalculo", argsReemplazo)
				+ "\n\n");

		if (importarActividadesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue()
				&& importarActividadesForm.getExcelTipo().byteValue() == 0)
			BuscarDatosExcel2003(request, log, messageResources, importarActividadesForm);
		else if (importarActividadesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel()
				.byteValue() && importarActividadesForm.getExcelTipo().byteValue() == 1)
			BuscarDatosExcel2010(request, log, messageResources, importarActividadesForm);

		ahora = Calendar.getInstance();
		argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
		argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

		log.append("\n\n");
		log.append(messageResources.getResource("jsp.asistente.importacion.log.fechafin.programada", argsReemplazo)
				+ "\n\n");

		request.getSession().setAttribute("verArchivoLog", log);
	}

	private void BuscarDatosExcel2003(HttpServletRequest request, StringBuffer log,
			VgcMessageResources messageResources, ImportarActividadesForm importarActividadesForm) throws Exception {
				
		String campo = "";
		int posicionCodigoIni = 0;
		int posicionNombre = 0;
		int posicionDescripcion = 0;
		int posicionFechaInicio = 0;
		int posicionFechaCulminacion = 0;
		int posicionAlertaVerde = 0;
		int posicionAlertaAmarilla = 0;
		int posicionUnidadMedida = 0;
		int posicionNumeroActividad = 0;
		int posicionCodigoEnlace = 0;
		int posicionPeso = 0;
		
		int filas = 0;

		String res = "";
		ActionMessages messages = getMessages(request);

		try {
			// Lo primero es leer un workbook que representa todo el documento XLS
			FormFile archivo = importarActividadesForm.getFileForm();
			Workbook workbook = Workbook.getWorkbook(archivo.getInputStream());

			// Elegimos la primera hoja
			Sheet sheet = workbook.getSheet(0);
			Cell celda = null;
			filas = sheet.getRows();
			for (int i = 0, z = sheet.getRows(); i < z; i++) {
				for (int j = 0, k = sheet.getColumns(); j < k; j++) {
					celda = sheet.getCell(j, i);

					// Obtenemos el contenido de la celda
					campo = celda.getContents();
					if (campo.equalsIgnoreCase("CODIGO PROYECTO"))
						posicionCodigoIni = j + 1;
					else if (campo.equalsIgnoreCase("NOMBRE ACTIVIDAD"))
						posicionNombre = j + 1;
					else if (campo.equalsIgnoreCase("DESCRIPCION"))
						posicionDescripcion = j + 1;
					else if (campo.equalsIgnoreCase("FECHA INICIO"))
						posicionFechaInicio = j + 1;
					else if (campo.equalsIgnoreCase("FECHA CULMINACION"))
						posicionFechaCulminacion = j + 1;
					else if (campo.equalsIgnoreCase("ALERTA VERDE"))
						posicionAlertaVerde = j + 1;
					else if (campo.equalsIgnoreCase("ALERTA AMARILLA"))
						posicionAlertaAmarilla = j + 1;
					else if (campo.equalsIgnoreCase("UNIDAD MEDIDA"))
						posicionUnidadMedida = j + 1;
					else if (campo.equalsIgnoreCase("NUMERO ACTIVIDAD"))
						posicionNumeroActividad = j + 1;
					else if (campo.equalsIgnoreCase("CODIGO DE ENLACE"))
						posicionNumeroActividad = j + 1;
					else if (campo.equalsIgnoreCase("PESO"))
						posicionNumeroActividad = j + 1;

					if (posicionCodigoIni != 0 && posicionNombre != 0 && posicionDescripcion != 0
							&& posicionFechaInicio != 0 && posicionFechaCulminacion != 0 && posicionAlertaVerde != 0
							&& posicionAlertaAmarilla != 0 && posicionUnidadMedida != 0 && posicionNumeroActividad != 0 && posicionCodigoEnlace != 0 && posicionPeso != 0)
						break;
				}

				if (posicionCodigoIni != 0 && posicionNombre != 0 && posicionDescripcion != 0
						&& posicionFechaInicio != 0 && posicionFechaCulminacion != 0 && posicionAlertaVerde != 0
						&& posicionAlertaAmarilla != 0 && posicionUnidadMedida != 0 && posicionNumeroActividad != 0 && posicionCodigoEnlace != 0 && posicionPeso != 0)
					break;
			}

			workbook.close();

			if (posicionCodigoIni != 0 && posicionNombre != 0 && posicionDescripcion != 0 && posicionFechaInicio != 0
					&& posicionFechaCulminacion != 0 && posicionAlertaVerde != 0 && posicionAlertaAmarilla != 0 && posicionUnidadMedida != 0 && posicionNumeroActividad != 0) {
				String[][] datos = new String[filas][11];

				String codigoIniArchivo = "";
				String nombreArchivo = "";
				String descripcionArchivo = "";
				String fechaInicioArchivo = "";
				String fechaCulminacionArchivo = "";
				String alertaVerdeArchivo = "";
				String alertaAmarillaArchivo = "";
				String unidadMedidaArchivo = "";
				String numeroActividadArchivo = "";
				String codigoEnlaceArchivo = "" ;
				String pesoArchivo = "";

				// Lo primero es leer un workbook que representa todo el documento XLS
				workbook = Workbook.getWorkbook(archivo.getInputStream());

				// Elegimos la primera hoja
				sheet = workbook.getSheet(0);
				celda = null;

				filas = 0;
				for (int r = 0, z = sheet.getRows(); r < z; r++) {

					codigoIniArchivo = "";
					nombreArchivo = "";
					descripcionArchivo = "";
					fechaInicioArchivo = "";
					fechaCulminacionArchivo = "";
					alertaVerdeArchivo = "";
					alertaAmarillaArchivo = "";
					unidadMedidaArchivo = "";
					numeroActividadArchivo = "";
					codigoEnlaceArchivo = "";
					pesoArchivo = "";

					for (int j = 0, k = sheet.getColumns(); j < k; j++) {
						celda = sheet.getCell(j, r);

						campo = celda.getContents();
						if ((j + 1) == posicionCodigoIni)
							codigoIniArchivo = campo;
						else if ((j + 1) == posicionNombre)
							nombreArchivo = campo;
						else if ((j + 1) == posicionDescripcion)
							descripcionArchivo = getValue(celda);
						else if ((j + 1) == posicionFechaInicio)
							fechaInicioArchivo = campo;
						else if ((j + 1) == posicionFechaCulminacion)
							fechaCulminacionArchivo = campo;
						else if ((j + 1) == posicionAlertaVerde)
							alertaVerdeArchivo = campo;
						else if ((j + 1) == posicionAlertaAmarilla)
							alertaAmarillaArchivo = campo;
						else if ((j + 1) == posicionUnidadMedida)
							unidadMedidaArchivo = campo;
						else if ((j + 1) == posicionNumeroActividad)
							numeroActividadArchivo = campo;		
						else if ((j + 1) == posicionCodigoEnlace)
							codigoEnlaceArchivo = campo;
						else if ((j + 1) == posicionPeso)
							pesoArchivo = campo;

						if (!codigoIniArchivo.equals("") && !nombreArchivo.equals("") && !descripcionArchivo.equals("")
								&& !fechaInicioArchivo.equals("") && !fechaCulminacionArchivo.equals("")
								&& !alertaVerdeArchivo.equals("") && !alertaAmarillaArchivo.equals("") && !unidadMedidaArchivo.equals("") && numeroActividadArchivo.equals("") && codigoEnlaceArchivo.equals("") && pesoArchivo.equals(""))
							break;
					}

					if (!codigoIniArchivo.equals("CODIGO PROYECTO") && !nombreArchivo.equals("")
							&& !descripcionArchivo.equals("") && !fechaInicioArchivo.equals("")
							&& !fechaCulminacionArchivo.equals("") && !alertaVerdeArchivo.equals("")
							&& !alertaAmarillaArchivo.equals("") && !unidadMedidaArchivo.equals("") && numeroActividadArchivo.equals("") && codigoEnlaceArchivo.equals("") && pesoArchivo.equals("")) {

						datos[filas][0] = codigoIniArchivo;
						datos[filas][1] = nombreArchivo;
						datos[filas][2] = descripcionArchivo;
						datos[filas][3] = fechaInicioArchivo;
						datos[filas][4] = fechaCulminacionArchivo;
						datos[filas][5] = alertaVerdeArchivo;
						datos[filas][6] = alertaAmarillaArchivo;
						datos[filas][7] = unidadMedidaArchivo;
						datos[filas][8] = numeroActividadArchivo;
						datos[filas][9] = codigoEnlaceArchivo;
						datos[filas][10] = pesoArchivo;
						
						

						filas++;
					}
				}

				workbook.close();

				if (datos.length > 0)
					Importar(request, log, messageResources, datos, importarActividadesForm);
				else {
					res = "jsp.asistente.importacion.fin.importar.archivo.empthy";
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(res));
					saveMessages(request, messages);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			res = "Error";

			String[] argsReemplazo = new String[1];
			argsReemplazo[0] = e.getMessage();

			log.append(messageResources.getResource("jsp.asistente.importacion.log.error", argsReemplazo) + "\n\n");

			importarActividadesForm.setStatus(ImportarStatus.getImportarStatusImportado());
			importarActividadesForm.setRespuesta(res);
		}

		if (importarActividadesForm.getRespuesta().equals("Error")) {
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
					new ActionMessage("jsp.asistente.importacion.fin.importar.archivo.error"));
			saveMessages(request, messages);
		}
	}
	
	private void BuscarDatosExcel2010(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources, ImportarActividadesForm importarActividadesForm) throws Exception
	{		
	    String campo = "";
	    
	    Integer posicionCodigoIni = null;
	    Integer posicionNombre = null;
	    Integer posicionDescripcion = null;
	    Integer posicionFechaInicio = null;
	    Integer posicionFechaCulminacion = null;
	    Integer posicionAlertaVerde = null;
	    Integer posicionAlertaAmarilla = null;
	    Integer posicionUnidadMedida = null;
	    Integer posicionNumeroActividad = null;
	    Integer posicionCodigoEnlace = null;
	    Integer posicionPeso = null;
	    
	    Integer filas = 0;
	    Integer filaError = null;
	    Integer columnaError = null;

	    String res = "";
    	ActionMessages messages = getMessages(request);

	    try
	    {
	    	// Lo primero es leer un workbook que representa todo el documento XLS
	    	FormFile archivo = importarActividadesForm.getFileForm();
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
					if (campo.equalsIgnoreCase("CODIGO PROYECTO"))
						posicionCodigoIni = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("NOMBRE ACTIVIDAD"))
						posicionNombre = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("DESCRIPCION"))
						posicionDescripcion = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("FECHA INICIO"))
						posicionFechaInicio = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("FECHA CULMINACION"))
						posicionFechaCulminacion = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("ALERTA VERDE"))
						posicionAlertaVerde = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("ALERTA AMARILLA"))
						posicionAlertaAmarilla = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("UNIDAD MEDIDA"))
						posicionUnidadMedida = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("NUMERO ACTIVIDAD"))
						posicionNumeroActividad = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("CODIGO DE ENLACE"))
						posicionCodigoEnlace = hssfCell.getColumnIndex();
					else if (campo.equalsIgnoreCase("PESO"))
						posicionPeso = hssfCell.getColumnIndex();
					

					if ( posicionCodigoIni != null && posicionNombre != null && posicionDescripcion != null && posicionFechaInicio != null && posicionFechaCulminacion != null && posicionAlertaVerde != null && posicionAlertaAmarilla != null && posicionUnidadMedida != null && posicionNumeroActividad != null && posicionCodigoEnlace != null && posicionPeso != null)
						break;
            	}
            	if ( posicionCodigoIni != null && posicionNombre != null && posicionDescripcion != null && posicionFechaInicio != null && posicionFechaCulminacion != null && posicionAlertaVerde != null && posicionAlertaAmarilla != null && posicionUnidadMedida != null && posicionNumeroActividad != null && posicionCodigoEnlace != null && posicionPeso != null)
					break;
            }	    		    
	    	
	    	if ( posicionCodigoIni != null && posicionNombre != null && posicionDescripcion != null && posicionFechaInicio != null && posicionFechaCulminacion != null && posicionAlertaVerde != null && posicionAlertaAmarilla != null && posicionUnidadMedida != null && posicionNumeroActividad != null && posicionCodigoEnlace != null && posicionPeso != null)
	    	{
	    			    		
	    		String[][] datos = new String[filas][11];
		    	
		    	String codigoIniArchivo = "";
		    	String nombreArchivo = "";
		    	String descripcionArchivo = "";
		    	String fechaInicioArchivo = "";
		    	String fechaCulminacionArchivo = "";
		    	String alertaVerdeArchivo = "";
		    	String alertaAmarillaArchivo = "";
		    	String unidadMedidaArchivo = "";
		    	String numeroActividadArchivo = "";
		    	String codigoEnlaceArchivo = "";
		    	String pesoArchivo = "";
		    	

		    	// Lo primero es leer un workbook que representa todo el documento XLS
		    	workBook = new XSSFWorkbook(archivo.getInputStream());

		    	//Elegimos la primera hoja
		    	hssfSheet = workBook.getSheetAt(0);
		    	int fila = 0;
		    	for (Iterator<Row> i = hssfSheet.rowIterator(); i.hasNext(); )
		    	{
		    		XSSFRow hssfRow = (XSSFRow)i.next();
		    		
		    		codigoIniArchivo = "";
		    		nombreArchivo = "";
		    		descripcionArchivo = "";
		    		fechaInicioArchivo = "";
		    		fechaCulminacionArchivo = "";
		    		alertaVerdeArchivo = "";
		    		alertaAmarillaArchivo = "";	
		    		unidadMedidaArchivo = "";
		    		numeroActividadArchivo = "";
		    		codigoEnlaceArchivo = "";
		    		pesoArchivo = "";
		    		
			    	filaError = hssfRow.getRowNum();
			    	for (Iterator<org.apache.poi.ss.usermodel.Cell> j = hssfRow.cellIterator(); j.hasNext(); )
		    		{
			    		XSSFCell hssfCell = (XSSFCell) j.next();
			    		columnaError = hssfCell.getColumnIndex();
						if (hssfCell.getColumnIndex() == posicionCodigoIni)													
							codigoIniArchivo = hssfCell.toString();													
						else if (hssfCell.getColumnIndex() == posicionNombre)
							nombreArchivo = hssfCell.toString();
						else if (hssfCell.getColumnIndex() == posicionDescripcion)
							descripcionArchivo = hssfCell.toString();													
						else if (hssfCell.getColumnIndex() == posicionFechaInicio) {
						    if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING) {
						        fechaInicioArchivo = hssfCell.toString();
						    } else if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) {
						        if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(hssfCell)) {
						            Date fecha = hssfCell.getDateCellValue();
						            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
						            fechaInicioArchivo = formatoFecha.format(fecha);
						        } else {
						        	fechaInicioArchivo = ((Integer) ((int) hssfCell.getNumericCellValue())).toString();
						        }
						    }
						}
						else if (hssfCell.getColumnIndex() == posicionFechaCulminacion) {
						    if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING) {
						    	fechaCulminacionArchivo = hssfCell.toString();
						    } else if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC) {
						        if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(hssfCell)) {
						            Date fecha = hssfCell.getDateCellValue();
						            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
						            fechaCulminacionArchivo = formatoFecha.format(fecha);
						        } else {
						        	fechaCulminacionArchivo = ((Integer) ((int) hssfCell.getNumericCellValue())).toString();
						        }
						    }
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
						else if (hssfCell.getColumnIndex() == posicionUnidadMedida) {
							if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING)
								unidadMedidaArchivo = hssfCell.toString();
							else if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC)
								unidadMedidaArchivo = ((Integer) ((int) hssfCell.getNumericCellValue())).toString();
						}	
						else if (hssfCell.getColumnIndex() == posicionNumeroActividad) {
							if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING)
								numeroActividadArchivo = hssfCell.toString();
							else if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC)
								numeroActividadArchivo = ((Integer) ((int) hssfCell.getNumericCellValue())).toString();
						}	
						else if (hssfCell.getColumnIndex() == posicionCodigoEnlace)													{
							codigoEnlaceArchivo = hssfCell.toString();	
						}
						else if (hssfCell.getColumnIndex() == posicionPeso) {
							if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING)
								pesoArchivo = hssfCell.toString();
							else if (hssfCell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC)
								pesoArchivo = ((Integer) ((int) hssfCell.getNumericCellValue())).toString();
						}
														
						if (!codigoIniArchivo.equals("") && !nombreArchivo.equals("") && !descripcionArchivo.equals("") && !fechaInicioArchivo.equals("") && !fechaCulminacionArchivo.equals("") && !alertaVerdeArchivo.equals("") && !alertaAmarillaArchivo.equals("") && !unidadMedidaArchivo.equals("") && !numeroActividadArchivo.equals("") && !codigoEnlaceArchivo.equals("") && !pesoArchivo.equals(""))
							break;
					}			    	
			    	
			    	if (!codigoIniArchivo.equals("CODIGO PROYECTO") && !nombreArchivo.equals("") && !descripcionArchivo.equals("") && !fechaInicioArchivo.equals("") && !fechaCulminacionArchivo.equals("") && !alertaVerdeArchivo.equals("") && !alertaAmarillaArchivo.equals("") && !unidadMedidaArchivo.equals("") && !numeroActividadArchivo.equals("") && !codigoEnlaceArchivo.equals("") && !pesoArchivo.equals(""))
					{													    	
							
						datos[fila][0] = codigoIniArchivo;
						datos[fila][1] = nombreArchivo;
						datos[fila][2] = descripcionArchivo;
						datos[fila][3] = fechaInicioArchivo;
						datos[fila][4] = fechaCulminacionArchivo;			
						datos[fila][5] = alertaVerdeArchivo;
						datos[fila][6] = alertaAmarillaArchivo;
						datos[fila][7] = unidadMedidaArchivo;
						datos[fila][8] = numeroActividadArchivo;
						datos[fila][9] = codigoEnlaceArchivo;
						datos[fila][10] = pesoArchivo;
												
  						fila++;
					}
		    	}		   
		    			    	
				if (datos.length > 0)
					Importar(request, log, messageResources, datos, importarActividadesForm);
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

	    	importarActividadesForm.setStatus(ImportarStatus.getImportarStatusImportado());
	    	importarActividadesForm.setRespuesta(res);
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

	    	importarActividadesForm.setStatus(ImportarStatus.getImportarStatusFileError());
	    	importarActividadesForm.setRespuesta(res);
	    }

	    if (importarActividadesForm.getRespuesta().equals("Error"))
	    {
		    messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.importacion.fin.importar.archivo.error"));
		    saveMessages(request, messages);
	    }
	}

	private String getValue(Cell celda) {
		
		
		String value = "";

		if (celda.getType() == CellType.LABEL) {
			LabelCell lc = (LabelCell) celda;
			value = lc.getString();
		} else if (celda.getType() == CellType.NUMBER || celda.getType() == CellType.NUMBER_FORMULA) {
			NumberCell nc = (NumberCell) celda;
			Double valor = nc.getValue();
			value = valor.toString();
		} else if (celda.getType() == CellType.DATE) {
			DateCell dc = (DateCell) celda;
			Date valor = dc.getDate();
			value = valor.toString();
		}

		return value;
	}

	private int Salvar(HttpServletRequest request, ImportarActividadesForm importarActividadesForm) throws Exception
	{		
    	int respuesta = 10000;
    	
    	ActionMessages messages = getMessages(request);
		Usuario usuario = getUsuarioConectado(request);

		Importacion importacion = new Importacion();
		boolean nuevo = false;
		
		importarActividadesForm.setBdPassword(request.getParameter("password"));
		ImportacionService importarService = FrameworkServiceFactory.getInstance().openImportacionService();
		if ((importarActividadesForm.getId() != null) && (!importarActividadesForm.getId().equals(Long.valueOf("0"))))
			importacion = (Importacion)importarService.load(Importacion.class, importarActividadesForm.getId());
		else
		{
			importacion = new Importacion();
			importacion.setId(new Long(0L));
			nuevo = true;
		}
		
		importacion.setNombre(importarActividadesForm.getNombre());
		importacion.setTipo(importarActividadesForm.getTipoFuente());
		importacion.setUsuario(usuario);
		importacion.setUsuarioId(usuario.getUsuarioId());
		importacion.setConfiguracion(importarActividadesForm.getXml());
		
		respuesta = importarService.saveImportacion(importacion, usuario);
		if (respuesta == 10000)
		{
			importarService.unlockObject(request.getSession().getId(), importarActividadesForm.getId());
			destruirPoolLocksUsoEdicion(request, importarService);

			if (nuevo)
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
			else
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
		}
		else if (respuesta == 10003)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));

		importarService.close();
				
	    if (importarActividadesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && importarActividadesForm.getExcelTipo().byteValue() == 0)
	    	importarActividadesForm.setRespuesta(importarActividadesForm.getBdStatusTabla());
	    else if (importarActividadesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue() && importarActividadesForm.getExcelTipo().byteValue() == 1)
	    	importarActividadesForm.setRespuesta(importarActividadesForm.getBdStatusTabla());	    
    	
    	saveMessages(request, messages);

		return respuesta;
	}
	
	private void Importar(HttpServletRequest request, StringBuffer log, VgcMessageResources messageResources,
			String[][] datos, ImportarActividadesForm importarActividadesForm) throws Exception {
		
		ActionMessages messages = getMessages(request);
		if (datos.length == 0) {
			String res = "jsp.asistente.importacion.fin.importar.archivo.empthy";
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(res));
			saveMessages(request, messages);
		} else {
			ServicioForm servicioForm = new ServicioForm();

			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
			Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");

			if (configuracion == null) {
				importarActividadesForm.setStatus(ImportarStatus.getImportarStatusNoConfigurado());
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("jsp.asistente.importacion.status.configuracion.noexiste"));
				saveMessages(request, messages);
			} else {
				// XML
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
				password = new com.visiongc.framework.web.struts.forms.servicio.GestionarServiciosForm()
						.getPasswordConexionDecriptado(password);

				if (!new FrameworkConnection().testConnection(url, driver, user, password)) {
					importarActividadesForm.setStatus(ImportarStatus.getImportarStatusNoConfigurado());
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("jsp.asistente.importacion.status.configuracion.noexiste"));
					saveMessages(request, messages);
				} else {
					byte tipoMedicion = request.getParameter("tipoMedicion") != null
							? Byte.parseByte(request.getParameter("tipoMedicion").toString())
							: 0;
					Usuario usuario = getUsuarioConectado(request);
					boolean todosOrganizacion = request.getParameter("todosOrganizacion") != null
							? (request.getParameter("todosOrganizacion").toString().equals("1") ? true : false)
							: false;
					servicioForm.setProperty("url", url);
					servicioForm.setProperty("driver", driver);
					servicioForm.setProperty("user", user);
					servicioForm.setProperty("password", password);
					servicioForm.setProperty("tipoFuente",
							((Integer) (!request.getParameter("tipoFuente").toString().equals("")
									? Integer.parseInt(request.getParameter("tipoFuente").toString())
									: 0)).toString());
					servicioForm.setProperty("separador", request.getParameter("separador").toString());
					servicioForm.setProperty("logMediciones",
							((Boolean) (request.getParameter("logMediciones") != null
									? (request.getParameter("logMediciones").toString().equals("1") ? true : false)
									: false)).toString());
					servicioForm.setProperty("logErrores",
							((Boolean) (request.getParameter("logErrores") != null
									? (request.getParameter("logErrores").toString().equals("1") ? true : false)
									: false)).toString());
					servicioForm.setProperty("todosPlanes",
							((Boolean) (request.getParameter("todosPlanes") != null
									? (request.getParameter("todosPlanes").toString().equals("1") ? true : false)
									: false)).toString());
					servicioForm.setProperty("todosOrganizacion", Boolean.toString(todosOrganizacion));
					servicioForm.setProperty("tipoMedicion", ((Byte) (tipoMedicion)).toString());
					servicioForm.setProperty("tipoImportacion",
							((Byte) (request.getParameter("tipoImportacion") != null
									? Byte.parseByte(request.getParameter("tipoImportacion").toString())
									: 0)).toString());
					servicioForm.setProperty("calcular",
							((Boolean) (request.getParameter("calcularMediciones") != null
									? (request.getParameter("calcularMediciones").toString().equals("1") ? true : false)
									: false)).toString());
					servicioForm.setProperty("logConsolaMetodos", ((Boolean) (false)).toString());
					servicioForm.setProperty("logConsolaDetallado", ((Boolean) (false)).toString());
					servicioForm.setProperty("tomarPeriodosSinMedicionConValorCero", ((Boolean) (false)).toString());
					servicioForm.setProperty("activarSheduler", ((Boolean) (true)).toString());
					servicioForm.setProperty("unidadTiempo", ((Integer) (3)).toString());
					servicioForm.setProperty("numeroEjecucion", ((Integer) (1)).toString());					
					servicioForm.setProperty("usuarioId", usuario.getUsuarioId().toString());
					if (!todosOrganizacion)
						servicioForm.setProperty("organizacionId",
								(String) request.getSession().getAttribute("organizacionId"));

					StringBuffer logBefore = log;
					boolean respuesta = new com.visiongc.servicio.strategos.importar.ImportarManager(servicioForm.Get(),
							log, com.visiongc.servicio.web.importar.util.VgcMessageResources
									.getVgcMessageResources("StrategosWeb"))
							.EjecutarActividad(datos, usuario);
					log = logBefore;
					String res = "";

					res = "Successful";
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
							new ActionMessage("jsp.asistente.importacion.archivo.configuracion.alerta.fin"));
					saveMessages(request, messages);

					if (usuario != null) {
						Calendar ahora = Calendar.getInstance();
						byte tipoEvento = AuditoriaTipoEvento.getAuditoriaTipoEventoImportar();
						Servicio servicio = new Servicio();
						servicio.setUsuarioId(usuario.getUsuarioId());
						servicio.setFecha(VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy") + " "
								+ VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a"));
						servicio.setNombre(messageResources.getResource("jsp.asistente.importacion.titulocalculo"));

						String[] argsReemplazo = new String[2];
						argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
						argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

						servicio.setMensaje(messageResources
								.getResource("jsp.asistente.importacion.log.fechafin.programada", argsReemplazo));
						servicio.setEstatus(CalcularStatus.getCalcularStatusCalculado());

						frameworkService.registrarAuditoriaEvento(servicio, usuario, tipoEvento);
					}

					importarActividadesForm.setStatus(ImportarStatus.getImportarStatusImportado());
					importarActividadesForm.setRespuesta(res);
				}
			}

			frameworkService.close();
		}
	}

	private void Verificar(HttpServletRequest request, ImportarActividadesForm importarActividadesForm) {
		
		if (importarActividadesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel().byteValue()
				&& importarActividadesForm.getExcelTipo().byteValue() == 0)
			VerificarExcel2007(request, importarActividadesForm);
		else if (importarActividadesForm.getTipoFuente().byteValue() == ImportacionType.getImportacionTypeExcel()
				.byteValue() && importarActividadesForm.getExcelTipo().byteValue() == 1)
			VerificarExcel2010(request, importarActividadesForm);
	}

	private void VerificarExcel2007(HttpServletRequest request, ImportarActividadesForm importarActividadesForm) {
		String res;
		String campo;		

		try {
			// Lo primero es leer un workbook que representa todo el documento XLS
			FormFile archivo = (FormFile) importarActividadesForm.getMultipartRequestHandler().getFileElements()
					.get("file_browse");
			importarActividadesForm.setFileForm(archivo);
			Workbook workbook = Workbook.getWorkbook(archivo.getInputStream());

			// Elegimos la primera hoja
			Sheet sheet = workbook.getSheet(0);
			Cell celda = null;

			for (int i = 0, z = sheet.getRows(); i < z; i++) {
				for (int j = 0, k = sheet.getColumns(); j < k; j++) {
					celda = sheet.getCell(j, i);

					// Obtenemos el contenido de la celda
					campo = celda.getContents();

					if (!hayCodigoIniciativa && campo.equalsIgnoreCase("CODIGO PROYECTO"))
						hayCodigoIniciativa = true;
					else if (!hayNombre && campo.equalsIgnoreCase("NOMBRE ACTIVIDAD"))
						hayNombre = true;
					else if (!hayDescripcion && campo.equalsIgnoreCase("DESCRIPCION"))
						hayDescripcion = true;
					else if (!hayFechaInicio && campo.equalsIgnoreCase("FECHA INICIO"))
						hayFechaInicio = true;
					else if (!hayFechaCulminacion && campo.equalsIgnoreCase("FECHA CULMINACION"))
						hayAlertaAmarilla = true;
					else if (!hayAlertaVerde && campo.equalsIgnoreCase("ALERTA VERDE"))
						hayFechaCulminacion = true;
					else if (!hayAlertaAmarilla && campo.equalsIgnoreCase("ALERTA AMARILLA"))
						hayAlertaAmarilla = true;
					else if (!hayUnidadMedida && campo.equalsIgnoreCase("UNIDAD MEDIDA"))
						hayUnidadMedida = true;
					else if (!hayNumeroActividad && campo.equalsIgnoreCase("NUMERO ACTIVIDAD"))
						hayNumeroActividad = true;
					else if (!hayCodigoEnlace && campo.equalsIgnoreCase("CODIGO DE ENLACE"))
						hayCodigoEnlace = true;
					else if (!hayPeso && campo.equalsIgnoreCase("PESO"))
						hayPeso = true;

					if (hayCodigoIniciativa && hayNombre && hayDescripcion && hayFechaInicio && hayFechaCulminacion
							&& hayAlertaVerde && hayAlertaAmarilla && hayUnidadMedida && hayNumeroActividad && hayCodigoEnlace && hayPeso)
						break;
				}

				if (hayCodigoIniciativa && hayNombre && hayDescripcion && hayFechaInicio && hayFechaCulminacion
						&& hayAlertaVerde && hayAlertaAmarilla && hayUnidadMedida && hayNumeroActividad && hayCodigoEnlace && hayPeso)
					break;
			}

			workbook.close();

			res = "CODIGO PROYECTO=" + (hayCodigoIniciativa ? "true" : "false") + ",";
			res = res + "NUMERO ACTIVIDAD=" + (hayNumeroActividad ? "true" : "false") + ",";
			res = res + "NOMBRE ACTIVIDAD=" + (hayNombre ? "true" : "false") + ",";
			res = res + "DESCRIPCION=" + (hayDescripcion ? "true" : "false") + ",";
			res = res + "FECHA INICIO=" + (hayFechaInicio ? "true" : "false") + ",";
			res = res + "FECHA CULMINACION=" + (hayFechaCulminacion ? "true" : "false") + ",";
			res = res + "ALERTA VERDE=" + (hayAlertaVerde ? "true" : "false") + ",";
			res = res + "ALERTA AMARILLA=" + (hayAlertaAmarilla ? "true" : "false") + ",";
			res = res + "UNIDAD MEDIDA=" + (hayUnidadMedida ? "true" : "false") + ",";
			res = res + "CODIGO DE ENLACE=" + (hayCodigoEnlace ? "true" : "false") + ",";
			res = res + "PESO=" + (hayPeso ? "true" : "false") + ",";

		} catch (Exception e) {
			e.printStackTrace();
			res = "Error";
		}

		importarActividadesForm.setStatus(ImportarStatus.getImportarStatusValidado());
		importarActividadesForm.setRespuesta(res);
	}

	private void VerificarExcel2010(HttpServletRequest request, ImportarActividadesForm importarActividadesForm) {
		String res;
		String campo;
		try {
			// Lo primero es leer un workbook que representa todo el documento XLS
			FormFile archivo = (FormFile) importarActividadesForm.getMultipartRequestHandler().getFileElements()
					.get("file_browse");
			importarActividadesForm.setFileForm(archivo);
			XSSFWorkbook workBook = new XSSFWorkbook(archivo.getInputStream());

			// Elegimos la primera hoja
			XSSFSheet hssfSheet = workBook.getSheetAt(0);

			Iterator<Row> rowIterator = hssfSheet.rowIterator();
			while (rowIterator.hasNext()) {
				XSSFRow hssfRow = (XSSFRow) rowIterator.next();
				Iterator<org.apache.poi.ss.usermodel.Cell> iterator = hssfRow.cellIterator();
				while (iterator.hasNext()) {
					XSSFCell hssfCell = (XSSFCell) iterator.next();

					// Obtenemos el contenido de la celda
					campo = hssfCell.toString();

					if (!hayCodigoIniciativa && campo.equalsIgnoreCase("CODIGO PROYECTO"))
						hayCodigoIniciativa = true;
					else if (!hayNombre && campo.equalsIgnoreCase("NOMBRE ACTIVIDAD"))
						hayNombre = true;
					else if (!hayDescripcion && campo.equalsIgnoreCase("DESCRIPCION"))
						hayDescripcion = true;
					else if (!hayFechaInicio && campo.equalsIgnoreCase("FECHA INICIO"))
						hayFechaInicio = true;
					else if (!hayFechaCulminacion && campo.equalsIgnoreCase("FECHA CULMINACION"))
						hayFechaCulminacion = true;
					else if (!hayAlertaVerde && campo.equalsIgnoreCase("ALERTA VERDE"))
						hayAlertaVerde = true;
					else if (!hayAlertaAmarilla && campo.equalsIgnoreCase("ALERTA AMARILLA"))
						hayAlertaAmarilla = true;
					else if (!hayUnidadMedida && campo.equalsIgnoreCase("UNIDAD MEDIDA"))
						hayUnidadMedida = true;
					else if (!hayNumeroActividad && campo.equalsIgnoreCase("NUMERO ACTIVIDAD"))
						hayNumeroActividad = true;
					else if (!hayCodigoEnlace && campo.equalsIgnoreCase("CODIGO DE ENLACE"))
						hayCodigoEnlace = true;
					else if (!hayPeso && campo.equalsIgnoreCase("PESO"))
						hayPeso = true;

					if (hayCodigoIniciativa && hayNombre && hayDescripcion && hayFechaInicio && hayFechaCulminacion
							&& hayAlertaVerde && hayAlertaAmarilla && hayUnidadMedida && hayNumeroActividad && hayCodigoEnlace && hayPeso)
						break;
				}

				if (hayCodigoIniciativa && hayNombre && hayDescripcion && hayFechaInicio && hayFechaCulminacion
						&& hayAlertaVerde && hayAlertaAmarilla && hayUnidadMedida && hayNumeroActividad && hayCodigoEnlace && hayPeso)
					break;
			}

			res = "CODIGO PROYECTO=" + (hayCodigoIniciativa ? "true" : "false") + ",";
			res = res + "NUMERO ACTIVIDAD=" + (hayNumeroActividad ? "true" : "false") + ",";
			res = res + "NOMBRE ACTIVIDAD=" + (hayNombre ? "true" : "false") + ",";
			res = res + "DESCRIPCION=" + (hayDescripcion ? "true" : "false") + ",";
			res = res + "FECHA INICIO=" + (hayFechaInicio ? "true" : "false") + ",";
			res = res + "FECHA CULMINACION=" + (hayFechaCulminacion ? "true" : "false") + ",";
			res = res + "ALERTA VERDE=" + (hayAlertaVerde ? "true" : "false") + ",";
			res = res + "ALERTA AMARILLA=" + (hayAlertaAmarilla ? "true" : "false") + ",";
			res = res + "UNIDAD MEDIDA=" + (hayUnidadMedida ? "true" : "false") + ",";
			res = res + "CODIGO DE ENLACE=" + (hayCodigoEnlace ? "true" : "false") + ",";
			res = res + "PESO=" + (hayPeso ? "true" : "false") + ",";

		} catch (Exception e) {
			e.printStackTrace();
			res = "Error";
		}

		importarActividadesForm.setStatus(ImportarStatus.getImportarStatusValidado());
		importarActividadesForm.setRespuesta(res);
	}

}
