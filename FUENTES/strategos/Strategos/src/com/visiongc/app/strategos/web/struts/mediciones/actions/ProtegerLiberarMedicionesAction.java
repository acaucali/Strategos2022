/**
 *
 */
package com.visiongc.app.strategos.web.struts.mediciones.actions;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.web.struts.mediciones.forms.ProtegerLiberarMedicionesForm;
import com.visiongc.app.strategos.web.struts.mediciones.forms.ProtegerLiberarMedicionesForm.ProtegerLiberarAccion;
import com.visiongc.app.strategos.web.struts.mediciones.forms.ProtegerLiberarMedicionesForm.ProtegerLiberarOrigen;
import com.visiongc.app.strategos.web.struts.mediciones.forms.ProtegerLiberarMedicionesForm.ProtegerLiberarSeleccion;
import com.visiongc.app.strategos.web.struts.mediciones.forms.ProtegerLiberarMedicionesForm.ProtegerLiberarStatus;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.util.FrameworkConnection;

/**
 * @author Kerwin
 *
 */
public class ProtegerLiberarMedicionesAction extends VgcAction
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

		ProtegerLiberarMedicionesForm protegerLiberarMedicionesForm = (ProtegerLiberarMedicionesForm)form;

		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Servicios.Configuracion");
		ActionMessages messages = getMessages(request);

		// status

		if (configuracion == null)
		{
			protegerLiberarMedicionesForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoConfigurado());
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

			if (!new FrameworkConnection().testConnection(url, driver, user, new com.visiongc.framework.web.struts.forms.servicio.GestionarServiciosForm().getPasswordConexionDecriptado(password)))
			{
				protegerLiberarMedicionesForm.setStatus(ProtegerLiberarStatus.getImportarStatusNoConfigurado());
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.protegerliberar.mediciones.status.configuracion.noexiste"));
				saveMessages(request, messages);
			}
			else{
				protegerLiberarMedicionesForm.setStatus(ProtegerLiberarStatus.getImportarStatusSuccess());
			}
		}

		// logica de la accion

		Byte origen = request.getParameter("origen") != null ? Byte.parseByte(request.getParameter("origen")) : null;
		Boolean proteger = request.getParameter("proteger") != null ? Boolean.parseBoolean(request.getParameter("proteger")) : null;
		String indicadores = request.getParameter("indicadorId");
		Long claseId = request.getParameter("claseId") != null ? Long.parseLong(request.getParameter("claseId")) : null;
		Long organizacionId = request.getParameter("organizacionId") != null ? Long.parseLong(request.getParameter("organizacionId")) : null;
		String actividadId = request.getParameter("actividadId");
		request.getSession().removeAttribute("actualizarFormaProteger");

		Calendar ahora = Calendar.getInstance();
		protegerLiberarMedicionesForm.setAno(ahora.get(1));
		protegerLiberarMedicionesForm.setMesInicial(1);
		protegerLiberarMedicionesForm.setMesFinal(12);
		protegerLiberarMedicionesForm.setSerieId((byte) 7);
		protegerLiberarMedicionesForm.setOrigen(origen);
		if (proteger)
			protegerLiberarMedicionesForm.setAccion(ProtegerLiberarAccion.getAccionBloquear());
		else if (!proteger)
			protegerLiberarMedicionesForm.setAccion(ProtegerLiberarAccion.getAccionLiberar());
		protegerLiberarMedicionesForm.setClaseId(claseId);
		protegerLiberarMedicionesForm.setOrganizacionId(organizacionId);
		protegerLiberarMedicionesForm.setAltoForma("470px");
		if (protegerLiberarMedicionesForm.getOrigen().byteValue() == ProtegerLiberarOrigen.getOrigenOrganizaciones())
		{
			protegerLiberarMedicionesForm.setAltoForma("550px");
			protegerLiberarMedicionesForm.setTipoSeleccion(ProtegerLiberarSeleccion.getSeleccionOrganizacion());
		}
		else if (protegerLiberarMedicionesForm.getOrigen().byteValue() == ProtegerLiberarOrigen.getOrigenIndicadores())
		{
			protegerLiberarMedicionesForm.setAltoForma("650px");
			protegerLiberarMedicionesForm.setTipoSeleccion(ProtegerLiberarSeleccion.getSeleccionClase());
		}
		else if (protegerLiberarMedicionesForm.getOrigen().byteValue() == ProtegerLiberarOrigen.getOrigenPlanes())
		{
			protegerLiberarMedicionesForm.setAltoForma("650px");
			protegerLiberarMedicionesForm.setTipoSeleccion(ProtegerLiberarSeleccion.getSeleccionPerspectiva());
		}
		else if (protegerLiberarMedicionesForm.getOrigen().byteValue() == ProtegerLiberarOrigen.getOrigenIniciativas())
		{
			protegerLiberarMedicionesForm.setAltoForma("650px");
			protegerLiberarMedicionesForm.setTipoSeleccion(ProtegerLiberarSeleccion.getSeleccionIniciativa());
		}
		else if (protegerLiberarMedicionesForm.getOrigen().byteValue() == ProtegerLiberarOrigen.getOrigenActividades())
		{
			protegerLiberarMedicionesForm.setAltoForma("425px");
			protegerLiberarMedicionesForm.setTipoSeleccion(ProtegerLiberarSeleccion.getSeleccionIndicador());
			StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();

			String cadenaId = "";
			Long id =null;
			int cont =1;
			String[] ids = actividadId.split(",");
			if (ids.length > 0)
			{
				for (String id2 : ids) {

					id=Long.parseLong(id2);
					PryActividad pryActividad = (PryActividad)strategosPryActividadesService.load(PryActividad.class, new Long(id));
					cadenaId += pryActividad.getIndicadorId().toString();
					if(cont < ids.length){
						cadenaId += ",";
					}
					cont ++;
				}
			}

			indicadores = cadenaId;

			strategosPryActividadesService.close();


		}

		if (indicadores != null && !indicadores.equals(""))
		{
			String[] ids = indicadores.split(",");
			if (ids.length > 0)
			{
				if (ids.length == 1)
				{
					protegerLiberarMedicionesForm.setIndicadorId(new Long(ids[0]));
					protegerLiberarMedicionesForm.setTipoSeleccion(ProtegerLiberarSeleccion.getSeleccionIndicador());
				}
				else
				{
					protegerLiberarMedicionesForm.setIndicadorId(null);
					protegerLiberarMedicionesForm.setTipoSeleccion(ProtegerLiberarSeleccion.getSeleccionIndicadoresSeleccionados());
					protegerLiberarMedicionesForm.setIndicadores(new ArrayList<Long>());
					for (String id : ids)
						protegerLiberarMedicionesForm.getIndicadores().add(new Long(id));
				}
			}
		}
		else
		{
			protegerLiberarMedicionesForm.setIndicadorId(null);
			protegerLiberarMedicionesForm.setIndicadores(null);
		}

	  	return mapping.findForward(forward);
	}
}