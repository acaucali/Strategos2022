/**
 * 
 */
package com.visiongc.servicio.strategos.calculos;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.struts.util.MessageResources;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.ConfiguracionNegativo;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.Password;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.ReporteServicioService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.ReporteServicio;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.persistence.ReporteServicioPersistenceSession;
import com.visiongc.framework.usuarios.UsuariosService;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.servicio.strategos.calculos.impl.CalculoManager;
import com.visiongc.servicio.strategos.indicadores.model.Indicador;
import com.visiongc.servicio.strategos.iniciativas.model.Iniciativa;
import com.visiongc.servicio.strategos.message.model.Message;
import com.visiongc.servicio.strategos.message.model.Message.MessageStatus;
import com.visiongc.servicio.strategos.message.model.Message.MessageType;
import com.visiongc.servicio.strategos.servicio.model.Servicio;
import com.visiongc.servicio.strategos.servicio.model.Servicio.ServicioStatus;
import com.visiongc.servicio.strategos.util.Correo;
import com.visiongc.servicio.web.importar.dal.indicador.IndicadorManager;
import com.visiongc.servicio.web.importar.dal.iniciativa.IniciativaManager;
import com.visiongc.servicio.web.importar.dal.message.MessageManager;
import com.visiongc.servicio.web.importar.dal.servicio.ServicioManager;
import com.visiongc.servicio.web.importar.dal.usuario.UsuarioManager;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcFormatter;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.util.ConnectionManager;

/**
 * @author Kerwin
 *
 */
public class CalcularManager2 
{
	PropertyCalcularManager pm;
	StringBuffer log; 
	VgcMessageResources messageResources;
	Boolean logConsolaMetodos = false;
	Boolean logConsolaDetallado = false;
	Servicio servicio;

	public CalcularManager2()
	{
	}
	
	public CalcularManager2(String[][] configuracion, StringBuffer log, VgcMessageResources messageResources)
	{
		this.pm = new PropertyCalcularManager().Set(configuracion);
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));

		if (this.servicio == null)
		{
			Long usuarioId = (pm.getProperty("usuarioId", "") != "" ? Long.parseLong(pm.getProperty("usuarioId", "")) : new UsuarioManager(this.pm, this.log, this.messageResources).LoadAdmin(null));     
			this.servicio = new Servicio(usuarioId, null, this.messageResources.getResource("jsp.servicio.calcular.titulo"), null, this.messageResources.getResource("jsp.servicio.inicio"), "");
		}
	}

	public CalcularManager2(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources, Servicio servicio)
	{
		this.pm = pm;
		this.log = log;
		this.messageResources = messageResources;
		this.logConsolaMetodos = Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false"));
		this.logConsolaDetallado = Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false"));
		this.servicio = servicio;

		if (this.servicio == null)
		{
			Long usuarioId = (pm.getProperty("usuarioId", "") != "" ? Long.parseLong(pm.getProperty("usuarioId", "")) : new UsuarioManager(this.pm, this.log, this.messageResources).LoadAdmin(null));     
			this.servicio = new Servicio(usuarioId, null, this.messageResources.getResource("jsp.servicio.inicio"), null, this.messageResources.getResource("jsp.servicio.inicio"), "");
		}
	}
	
	public boolean Ejecutar(Long serieId, Long usuarioId, int mesv, int anov)
	{
		boolean respuesta = false;
		respuesta = (new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null) == 10000 ? true : false);
		
		boolean activarSheduler = Boolean.parseBoolean(this.pm.getProperty("activarSheduler", "false"));
		if (!activarSheduler)
		{
			respuesta = Calcular(serieId, usuarioId, mesv, anov);
			
	        String[] argsReemplazo = new String[2];
	        Calendar ahora = Calendar.getInstance();
	        
		    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
		    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
		    
		    log.append("\n\n");
		    log.append(messageResources.getResource("jsp.asistente.calculo.log.fechafincalculo", argsReemplazo) + "\n\n");
		    if (!respuesta)
		    {
			    argsReemplazo[0] = messageResources.getResource("jsp.asistente.importacion.log.error.inesperado");
			    argsReemplazo[1] = "";

			    log.append(messageResources.getResource("jsp.asistente.calculo.log.error", argsReemplazo) + "\n\n");
		    	this.servicio.setMensaje(this.messageResources.getResource("jsp.asistente.calculo.log.error", argsReemplazo));
		    }
		    this.servicio.setLog(this.log.toString());

		    new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);
		}
		else
		{
			int terminar = Integer.parseInt(this.pm.getProperty("numeroEjecucion", "1"));
			int unidadTiempo = Integer.parseInt(this.pm.getProperty("unidadTiempo", "3"));

			Calendar inicio = Calendar.getInstance();
			String[] fecha = this.servicio.getFecha().split("-");
			
			int dia = Integer.parseInt(fecha[2].substring(0, 2)); 
			int mes = Integer.parseInt(fecha[1]);
			int ano = Integer.parseInt(fecha[0]);
			
			if (unidadTiempo != 0)
				inicio.set(ano, mes-1, dia);
			else
				inicio.set(ano, mes-1, dia, 8, 0, 0);
			
			TareaValidacion t1 = new TareaValidacion();
			TimeUnit timeUnit;
			long duracion;
			if (unidadTiempo == 0)
			{
				timeUnit = TimeUnit.DAYS;
				duracion = 1000 * 60 * 60 * 24 + (inicio.getTimeInMillis());
			}
			else if (unidadTiempo == 1)
			{
				timeUnit = TimeUnit.HOURS;
				duracion = 1000 * 60 * 60 + (inicio.getTimeInMillis());
			}
			else if (unidadTiempo == 2)
			{
				timeUnit = TimeUnit.MINUTES;
				duracion = 1000 * 60 + (inicio.getTimeInMillis());
			}
			else
			{
				timeUnit = TimeUnit.SECONDS;
				duracion = 1000 + (inicio.getTimeInMillis());
			}
			
			Calendar nowDuracion = Calendar.getInstance();
			nowDuracion.setTimeInMillis(duracion);

			t1.programar(duracion, terminar, (terminar == 0), timeUnit, this.log, this.messageResources, this.pm, this.servicio);
		}
		
		return respuesta;
	}
	
	public boolean Calcular(Long serieId, Long usuarioId, int mes, int anov)
	{
		boolean respuesta = false;
	    boolean logMediciones = Boolean.parseBoolean(pm.getProperty("logMediciones", "false"));
	    boolean logErrores = Boolean.parseBoolean(pm.getProperty("logErrores", "false"));
	    String[] argsReemplazo = new String[5];
	    
		Connection cn = null;
		Statement stm = null;
		boolean transActiva = false;
	    
		try
		{
			List<Indicador> indicadores = new ArrayList<Indicador>();
			List<Iniciativa> iniciativas = new ArrayList<Iniciativa>();
	
			boolean arbolCompletoOrganizacion = Boolean.parseBoolean(pm.getProperty("arbolCompletoOrganizacion", "false"));
			boolean todasOrganizaciones = Boolean.parseBoolean(pm.getProperty("todasOrganizaciones", "false"));
			boolean porNombre = Boolean.parseBoolean(pm.getProperty("porNombre", "false"));
    	    boolean tomarPeriodosNulosComoCero = Boolean.parseBoolean(pm.getProperty("tomarPeriodosSinMedicionConValorCero", "false"));
			Long indicadorId = (pm.getProperty("indicadorId", "0") != "" ? Long.parseLong(pm.getProperty("indicadorId", "0")) : null);
			Long iniciativaId = (pm.getProperty("iniciativaId", "0") != "" ? Long.parseLong(pm.getProperty("iniciativaId", "0")) : null);
			Long claseId = (pm.getProperty("claseId", "0") != "" ? Long.parseLong(pm.getProperty("claseId", "0")) : null);
			Long organizacionId = (pm.getProperty("organizacionId", "0") != "" ? Long.parseLong(pm.getProperty("organizacionId", "0")) : null);
			Long planId = (pm.getProperty("planId", "0") != "" ? Long.parseLong(pm.getProperty("planId", "0")) : null);
			Long perspectivaId = (pm.getProperty("perspectivaId", "0") != "" ? Long.parseLong(pm.getProperty("perspectivaId", "0")) : null);
			String nombreIndicador = pm.getProperty("nombreIndicador", "");
			String nombreIniciativa = pm.getProperty("nombreIniciativa", "");
    	    Integer mesInicial = Integer.parseInt(pm.getProperty("mesInicial", "0"));
    	    Integer mesFinal = Integer.parseInt(pm.getProperty("mesFinal", "0"));
    	    Integer ano = Integer.parseInt(pm.getProperty("ano", "0"));
    	    Byte frecuencia = (pm.getProperty("frecuencia", null) != null && pm.getProperty("frecuencia", null) != "" ? Byte.parseByte(pm.getProperty("frecuencia", null)) : null);
    	    Usuario usuario = (pm.getProperty("usuarioId", "") != "" ? new UsuarioManager(this.pm, this.log, this.messageResources).Load(Long.parseLong(pm.getProperty("usuarioId", "")), stm) : null);
    	    boolean calcularIniciativas = Boolean.parseBoolean(pm.getProperty("calcularIniciativas", "false"));

	    	argsReemplazo[0] = ano + "";
    	    argsReemplazo[1] = ano + "";
    	    argsReemplazo[2] = "";
    	    argsReemplazo[3] = "";
    	    argsReemplazo[4] = "";

			cn = new ConnectionManager(pm).getConnection();
			cn.setAutoCommit(false);
			stm = cn.createStatement();
			transActiva = true;
			
			this.servicio.setEstatus(ServicioStatus.getServicioStatusEnProceso());
			this.servicio.setMensaje(messageResources.getResource("calcularindicadores.calculando.anos", argsReemplazo));
			this.servicio.setLog(log.toString());
			new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, stm);
			
			if (!calcularIniciativas)
				indicadores = new IndicadorManager(pm, log, messageResources).getIndicadoresParaCalcular(indicadorId, porNombre, nombreIndicador, claseId, organizacionId, arbolCompletoOrganizacion, todasOrganizaciones, perspectivaId, planId, frecuencia, stm);
			else
				iniciativas = new IniciativaManager(pm, log, messageResources).getIniciativasParaCalcular(iniciativaId, porNombre, nombreIniciativa, claseId, organizacionId, arbolCompletoOrganizacion, todasOrganizaciones, perspectivaId, planId, frecuencia, stm);
			
			if (!calcularIniciativas)
			{
				if (indicadores != null && indicadores.size() > 0)
				{
					this.servicio.setMensaje(messageResources.getResource("calcularindicadores.calculando.total") + " " + indicadores.size());
					new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, stm);
					
					StringBuffer logCalculo = new CalculoManager(pm, log, messageResources).calcularMedicionesIndicadores(indicadores, planId, organizacionId, perspectivaId, ano, mesInicial.byteValue(), mesFinal.byteValue(), tomarPeriodosNulosComoCero, logMediciones, logErrores, usuario, stm, mesInicial.byteValue(), mesFinal.byteValue());
					log.append(logCalculo.toString());
				}
				else
				{
		  			if (planId != null)
		  			{
						new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, stm);
						StringBuffer logCalculo = new CalculoManager(pm, log, messageResources).calcularEstadoObjetivos(planId, organizacionId, perspectivaId, ano, mesInicial.byteValue(), mesFinal.byteValue(), stm);
						log.append(logCalculo.toString());
		  			}
				}
			}
			else
			{
				if (iniciativas != null && iniciativas.size() > 0)
				{
					this.servicio.setMensaje(messageResources.getResource("calcularindicadores.calculando.total") + " " + iniciativas.size());
					new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, stm);

					StringBuffer logCalculo = new CalculoManager(pm, log, messageResources).calcularIniciativas(iniciativas, planId, organizacionId, perspectivaId, logMediciones, logErrores, usuario, stm);
					log.append(logCalculo.toString());
				}
			}

			Calendar ahora = Calendar.getInstance();
	  		argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	  		argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
	  		argsReemplazo[2] = "";
    	    argsReemplazo[3] = "";
    	    argsReemplazo[4] = "";

	  		log.append(this.messageResources.getResource("calcularindicadores.fechafincalculocompuesto", argsReemplazo) + "\n\n");
			log.append(messageResources.getResource("calcularindicadores.success") + "\n");
			
			
			//log.setLength(0);
			
			
			log.append("\n" +"\n" +"Reporte Valores Negativos" +"\n");
			
			//////////////////
			
			
			Message message = new Message(this.servicio.getUsuarioId(), this.servicio.getFecha(), MessageStatus.getStatusPendiente(), "", MessageType.getTypeAlerta(), this.servicio.getNombre());
  			this.servicio.setEstatus(ServicioStatus.getServicioStatusSuccess());
			this.servicio.setMensaje(messageResources.getResource("calcularindicadores.success"));
			this.servicio.setLog(log.toString());
			new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, stm);

			
			message.setMensaje(messageResources.getResource("calcularindicadores.success"));
			new MessageManager(this.pm, this.log, this.messageResources).saveMessage(message, stm);
		
			
			
			
			cn.commit();
			cn.setAutoCommit(true);
			stm.close();
			cn.close();
			cn = null;
			transActiva = false;
			
			respuesta = true;
			
			
			StringBuffer logNegativo = validarNegativo(new Long(0), ano, mesInicial, organizacionId, usuario.getUsuarioId());
			
			log.append(logNegativo.toString());
			
			
			
			
	    }
	    catch (Exception e) 
		{
	    	argsReemplazo[0] = e.getMessage() != null ? e.getMessage() : "";
    	    argsReemplazo[1] = "";
    	    argsReemplazo[2] = "";
    	    argsReemplazo[3] = "";
    	    argsReemplazo[4] = "";

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.medicion.general.error", argsReemplazo) + "\n\n");
		}
		finally
		{
			try 
			{
				if (transActiva) stm.close();
			} 
			catch (Exception localException8) { }
			      
			try 
			{
				if (transActiva)
				{
					cn.setAutoCommit(true);
			    	cn.close();
			    	cn = null;
				}
			} 
			catch (Exception localException9) { }
		}
		
		return respuesta;
	}
	
	
	
	public int Ejecutar(List<Object> indicadores, byte tipoEjecucion)
	{
		final byte EJECUCIONALERTA = 1;

		int respuesta = 10000;

		Connection cn = null;
		Statement stm = null;
		boolean transActiva = false;
	    
		try
		{
			cn = new ConnectionManager(pm).getConnection();
			cn.setAutoCommit(false);
			stm = cn.createStatement();
			transActiva = true;
			
			if (stm != null)
			{
				for (Iterator<Object> i = indicadores.iterator(); i.hasNext(); )
				{
					Object objeto = (Object)i.next();
					Indicador indicador = null;
					if (objeto.getClass().getName().equals("java.lang.Long"))
						indicador = new IndicadorManager(pm, log, messageResources).Load((Long)objeto,  stm);
					else
						indicador = (Indicador) objeto;
					
					if (tipoEjecucion == EJECUCIONALERTA && indicador != null)
					{
						respuesta = new CalculoManager(pm, log, messageResources).calcularAlertaIndicador(indicador, stm);
						if (respuesta != 10000)
							break;
					}
				}
			}

	    	if (transActiva) 
	    	{
	    		if (respuesta == 10000)
					cn.commit();
	    		else
					cn.rollback();
			    
    			cn.setAutoCommit(true);
	    	}
			
			stm.close();
			cn.close();
			cn = null;
			transActiva = false;
	    }
	    catch (Exception e) 
		{
	    	if (transActiva) 
	    	{
				try 
				{
					cn.rollback();
				} 
				catch (SQLException e1) 
				{
					throw new ChainedRuntimeException(e1.getMessage(), e1);
				}
    			try 
    			{
					cn.setAutoCommit(true);
				} 
    			catch (SQLException e1) 
    			{
    				throw new ChainedRuntimeException(e1.getMessage(), e1);
				}

    			transActiva = false;
	    	}

  			throw new ChainedRuntimeException(e.getMessage(), e);
		}
		finally
		{
			try 
			{
				if (transActiva) stm.close();
			} 
			catch (Exception localException8) { }
			      
			try 
			{
				if (transActiva)
				{
					cn.setAutoCommit(true);
			    	cn.close();
			    	cn = null;
				}
			} 
			catch (Exception localException9) { }
		}
		
		return respuesta;
	}
	
	/// <summary>
	/// Metodo para calcular la alerta de un indicador.
	/// </summary>
	/// <param name="caracteristica">Caracteristicas del indicador.</param>
	/// <param name="zonaVerde">Zona verde del indicador.</param>
	/// <param name="zonaAmarilla">Zona amarilla del indicador.</param>
	/// <param name="ejecutado">Ejecutado.</param>
	/// <param name="meta">meta o programado.</param>
	/// <param name="ejecutadoAnterior">Ejecutado anterior.</param>
	public Byte Ejecutar(Byte caracteristica, Double zonaVerde, Double zonaAmarilla, Double ejecutado, Double meta, Double metaInferior, Double ejecutadoAnterior)
	{
		return new CalculoManager().calcularAlertaIndicador(caracteristica, zonaVerde, zonaAmarilla, ejecutado, meta, metaInferior, ejecutadoAnterior);
	}
	
	private StringBuffer validarNegativo(Long serieId, int anoInicio, int mesInicio, Long organizacionId, Long usuarioId) throws UnsupportedEncodingException, ParserConfigurationException, SAXException, IOException {
		
		StringBuffer logNegativo = new StringBuffer(); 
		
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();	
		StrategosClasesIndicadoresService strategosClasesService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
		UsuariosService usuariosService = FrameworkServiceFactory.getInstance().openUsuariosService();	
		
		List<com.visiongc.app.strategos.indicadores.model.Indicador> indicadores = new ArrayList<com.visiongc.app.strategos.indicadores.model.Indicador>();
		List<Long> indicadoresId = new ArrayList<Long>();
		List<Medicion> mediciones = new ArrayList<Medicion>();
		List<Medicion> medicionesNegativas = new ArrayList<Medicion>();
		
		
		Map<String, Object> filtros = new HashMap<String, Object>();

		Usuario user = (Usuario)usuariosService.load(Usuario.class, usuarioId);
		
		/*
		filtros.put("organizacionId", organizacionId.toString());
		
		
		indicadores=strategosIndicadoresService.getIndicadores(filtros);
		
		// obtiene los id de los indicadores
		
		for (Iterator iter = indicadores.iterator(); iter.hasNext(); ) 
		{
			com.visiongc.app.strategos.indicadores.model.Indicador indicador = (com.visiongc.app.strategos.indicadores.model.Indicador)iter.next();
			indicadoresId.add(indicador.getIndicadorId());
		}
		
		
		
		
		indicadoresId.add((long)233854);
		
		
		mediciones = strategosMedicionesService.getMedicionList(indicadoresId, serieId, anoInicio, mesInicio);
		*/
		
		
		// todas las organizaciones
		
		
		mediciones = strategosMedicionesService.getMedicionList(null, serieId, anoInicio, mesInicio);
		
		
					
		for (Iterator iter = mediciones.iterator(); iter.hasNext(); ) 
		{
  			Medicion medicion = (Medicion)iter.next();
  			
  			
  			if(medicion.getValor() <0) {
  				Long indicadorId= medicion.getMedicionId().getIndicadorId();
  	        	com.visiongc.app.strategos.indicadores.model.Indicador indicador = (com.visiongc.app.strategos.indicadores.model.Indicador)strategosIndicadoresService.load(com.visiongc.app.strategos.indicadores.model.Indicador.class, new Long(indicadorId));
  				Organizacion organizacion = (Organizacion)strategosOrganizacionesService.load(Organizacion.class, new Long(indicador.getOrganizacionId()));
  				ClaseIndicadores clase = (ClaseIndicadores)strategosClasesService.load(ClaseIndicadores.class, new Long(indicador.getClaseId()));
  				
  				logNegativo.append("Nombre Organización: "+organizacion.getNombre() +"\n");
  				logNegativo.append("Clase: "+clase.getNombre() +"\n");
  				logNegativo.append("Nombre Indicador: "+indicador.getNombre() +"\n");
  				logNegativo.append("Valor: "+medicion.getValor() +"\n");
  				logNegativo.append("Año: "+medicion.getMedicionId().getAno() +"\n");
  				logNegativo.append("Periodo: "+medicion.getMedicionId().getPeriodo() +"\n\n");
  				
  				
  				
  				medicionesNegativas.add(medicion);
  				
  				//responsables
  				
  				
  				
  			}
  			
  			

		}
		
		enviarCorreo(medicionesNegativas, user);
	

		strategosMedicionesService.close();
		strategosIndicadoresService.close();
		strategosOrganizacionesService.close();
		strategosIndicadoresService.close();
		strategosClasesService.close();
	
		
		return logNegativo;
	}
	
	
	
	
	// enviar correo
	
	public String enviarCorreo(List<Medicion> medicionesNegativas, Usuario usuario) throws UnsupportedEncodingException, ParserConfigurationException, SAXException, IOException {
		
		String mensaje = "";
		
		List<ReporteServicio> reportes = new ArrayList();
		
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();	
		StrategosClasesIndicadoresService strategosClasesService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
		ReporteServicioService reporteServicio = FrameworkServiceFactory.getInstance().openReporteServicioService();
		
		List<String> configuracion = obtenerConfiguracion(); 
		List<String> responsablesId = new ArrayList();
		
		if(configuracion != null) {
			String texto= configuracion.get(0);
			String titulo= configuracion.get(1);
			Boolean envResCar= Boolean.parseBoolean(configuracion.get(2));
			Boolean envResMetr= Boolean.parseBoolean(configuracion.get(3));
			Boolean envResFij= Boolean.parseBoolean(configuracion.get(4));
			Boolean envResLog= Boolean.parseBoolean(configuracion.get(5));
			Boolean envResNeg= Boolean.parseBoolean(configuracion.get(6));
			Boolean envResSeg= Boolean.parseBoolean(configuracion.get(7));
			String host = configuracion.get(8);
			String port = configuracion.get(9);
			String user = configuracion.get(10);
			String pass = configuracion.get(11);
			
			if(!port.equals("") || !user.equals("") || !pass.equals("") || !host.equals("")) {
				
				
				for (Iterator iter = medicionesNegativas.iterator(); iter.hasNext(); ) 
				{
		  			Medicion medicion = (Medicion)iter.next();
		  			
		  			
		  			
		  				Long indicadorId= medicion.getMedicionId().getIndicadorId();
		  	        	com.visiongc.app.strategos.indicadores.model.Indicador indicador = (com.visiongc.app.strategos.indicadores.model.Indicador)strategosIndicadoresService.load(com.visiongc.app.strategos.indicadores.model.Indicador.class, new Long(indicadorId));
		  				/*
		  	        	Organizacion organizacion = (Organizacion)strategosOrganizacionesService.load(Organizacion.class, new Long(indicador.getOrganizacionId()));
		  				ClaseIndicadores clase = (ClaseIndicadores)strategosClasesService.load(ClaseIndicadores.class, new Long(indicador.getClaseId()));
		  			    */
		  	        	
		  				
		  				
		  				if(indicador.getResponsableCargarEjecutadoId() !=null && envResCar == true) {
		  					ReporteServicio reporte = new ReporteServicio();
		  					reporte.setIndicadorId(indicadorId);
		  					reporte.setMedicion("Valor:"+medicion.getValor()+","+
		  							"Ano:"+medicion.getMedicionId().getAno()+","
		  							+"Periodo:"+medicion.getMedicionId().getPeriodo());
		  					reporte.setResponsableId(indicador.getResponsableCargarEjecutadoId());
		  					reporteServicio.saveReporte(reporte, usuario);
		  					reportes.add(reporte);
		  					
		  					Boolean existe=validarId(indicador.getResponsableCargarEjecutadoId().toString(), responsablesId);
		  					
		  					if(!existe) {
		  						responsablesId.add(indicador.getResponsableCargarEjecutadoId().toString());
		  					}
		  					
		  					
		  				}
		  				if(indicador.getResponsableCargarMetaId() !=null && envResMetr == true) {
		  					ReporteServicio reporte = new ReporteServicio();
		  					reporte.setIndicadorId(indicadorId);
		  					reporte.setMedicion("Valor:"+medicion.getValor()+","+
		  							"Ano:"+medicion.getMedicionId().getAno()+","
		  							+"Periodo:"+medicion.getMedicionId().getPeriodo());
		  					reporte.setResponsableId(indicador.getResponsableCargarMetaId());
		  					reporteServicio.saveReporte(reporte, usuario);
		  					reportes.add(reporte);
		  					
		  					Boolean existe=validarId(indicador.getResponsableCargarMetaId().toString(), responsablesId);
		  					
		  					if(!existe) {
		  						responsablesId.add(indicador.getResponsableCargarMetaId().toString());
		  					}
		  					
		  				}
		  				if(indicador.getResponsableFijarMetaId() !=null && envResFij == true) {
		  					ReporteServicio reporte = new ReporteServicio();
		  					reporte.setIndicadorId(indicadorId);
		  					reporte.setMedicion("Valor:"+medicion.getValor()+","+
		  							"Ano:"+medicion.getMedicionId().getAno()+","
		  							+"Periodo:"+medicion.getMedicionId().getPeriodo());
		  					reporte.setResponsableId(indicador.getResponsableFijarMetaId());
		  					reporteServicio.saveReporte(reporte, usuario);
		  					reportes.add(reporte);
		  					
		  					
		  					Boolean existe=validarId(indicador.getResponsableFijarMetaId().toString(), responsablesId);
		  					
		  					if(!existe) {
		  						responsablesId.add(indicador.getResponsableFijarMetaId().toString());
		  					}
		  					
		  				
		  				}
		  				if(indicador.getResponsableLograrMetaId() != null && envResLog == true) {
		  					ReporteServicio reporte = new ReporteServicio();
		  					reporte.setIndicadorId(indicadorId);
		  					reporte.setMedicion("Valor:"+medicion.getValor()+","+
		  							"Ano:"+medicion.getMedicionId().getAno()+","
		  							+"Periodo:"+medicion.getMedicionId().getPeriodo());
		  					reporte.setResponsableId(indicador.getResponsableLograrMetaId());
		  					reporteServicio.saveReporte(reporte, usuario);
		  					reportes.add(reporte);
		  					
		  					Boolean existe=validarId(indicador.getResponsableLograrMetaId().toString(), responsablesId);
		  					
		  					if(!existe) {
		  						responsablesId.add(indicador.getResponsableLograrMetaId().toString());
		  					}
		  					
		  					
		  				}
		  				if(indicador.getResponsableNotificacionId() != null && envResNeg == true) {
		  					ReporteServicio reporte = new ReporteServicio();
		  					reporte.setIndicadorId(indicadorId);
		  					reporte.setMedicion("Valor:"+medicion.getValor()+","+
		  							"Ano:"+medicion.getMedicionId().getAno()+","
		  							+"Periodo:"+medicion.getMedicionId().getPeriodo());
		  					reporte.setResponsableId(indicador.getResponsableNotificacionId());
		  					reporteServicio.saveReporte(reporte, usuario);
		  					reportes.add(reporte);
		  					
		  					Boolean existe=validarId(indicador.getResponsableNotificacionId().toString(), responsablesId);
		  					
		  					if(!existe) {
		  						responsablesId.add(indicador.getResponsableNotificacionId().toString());
		  					}	
		  						  					
		  					
		  				}
		  				if(indicador.getResponsableSeguimientoId() != null && envResSeg == true) {
		  					ReporteServicio reporte = new ReporteServicio();
		  					reporte.setIndicadorId(indicadorId);
		  					reporte.setMedicion("Valor:"+medicion.getValor()+","+
		  							"Ano:"+medicion.getMedicionId().getAno()+","
		  							+"Periodo:"+medicion.getMedicionId().getPeriodo());
		  					reporte.setResponsableId(indicador.getResponsableSeguimientoId());
		  					reporteServicio.saveReporte(reporte, usuario);
		  					reportes.add(reporte);
		  					
		  					
		  					Boolean existe=validarId(indicador.getResponsableSeguimientoId().toString(), responsablesId);
		  					
		  					if(!existe) {
		  						responsablesId.add(indicador.getResponsableSeguimientoId().toString());
		  					}
		  					
		  				}
		  				
		  				
		  				
		  				
		  			
				}
				
				
				enviarCorreoResponsable(host, port, user, pass, titulo, texto, responsablesId);
				
				
				// eliminar registros
				for(Iterator iter =reportes.iterator(); iter.hasNext();) {
					
					ReporteServicio reporte = (ReporteServicio)iter.next();
					reporteServicio.deleteReporte(reporte, usuario);
				}
				
				
			}else {
				mensaje = "no se puede enviar correo, no existe la configuración de correo completa";
			}
			
			
			
		}else {
			mensaje = "no existe configuración";
		}
		
		reporteServicio.close();
		strategosMedicionesService.close();
		strategosIndicadoresService.close();
		strategosOrganizacionesService.close();
		strategosIndicadoresService.close();
		strategosClasesService.close();
			
		return mensaje;
	}
	
	
	
	public void enviarCorreoResponsable(String host, String port, String user, String pass, String titulo, String texto, List<String> reponsablesId){
		
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();	
		StrategosClasesIndicadoresService strategosClasesService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
		StrategosResponsablesService strategosResponsableService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();
		
		
		ReporteServicioService reporteServicio = FrameworkServiceFactory.getInstance().openReporteServicioService();
		
		
		
		for(String id:reponsablesId) {
			
			Long resId = Long.parseLong(id);
			
			Responsable responsable = (Responsable)strategosResponsableService.load(Responsable.class, resId);
			
			// valida si el responsbale tiene email
			if(responsable.getEmail() != null || !responsable.getEmail().equals("")) {
				
				
				//obtiene mediciones 
				List<ReporteServicio> reportes = reporteServicio.getReporte(resId);
				Correo correo = new Correo();
				String textoIn = texto;
	  			String cuerpo = textoIn;
				
				
				for (Iterator iter = reportes.iterator(); iter.hasNext(); ) 
				{
		  			ReporteServicio reporte = (ReporteServicio)iter.next();
		  			
		  			
	  	        	com.visiongc.app.strategos.indicadores.model.Indicador indicador = (com.visiongc.app.strategos.indicadores.model.Indicador)strategosIndicadoresService.load(com.visiongc.app.strategos.indicadores.model.Indicador.class, new Long(reporte.getIndicadorId()));
	  				Organizacion organizacion = (Organizacion)strategosOrganizacionesService.load(Organizacion.class, new Long(indicador.getOrganizacionId()));
	  				ClaseIndicadores clase = (ClaseIndicadores)strategosClasesService.load(ClaseIndicadores.class, new Long(indicador.getClaseId()));
	  				
	  				cuerpo += "\n \n";
	  				cuerpo += "Medicion: \n";
	  				cuerpo += "Nombre Organización: "+organizacion.getNombre() +"\n";
	  				cuerpo += "Clase: "+clase.getNombre() +"\n";
	  				cuerpo += "Nombre Indicador: "+indicador.getNombre() +"\n";
	  				cuerpo += "Datos Medicion: "+reporte.getMedicion()+"\n";
	  				
	  				cuerpo +="insumos: \n";
	  				
	  				if(indicador.getNaturaleza() == 1) {
	  					
	  					List<InsumoFormula> insumos =strategosIndicadoresService.getInsumosFormula(indicador.getIndicadorId(), (long) 0);
	  					
	  					for(InsumoFormula insumo: insumos) {
	  						Medicion med = strategosMedicionesService.getUltimaMedicion(insumo.getInsumo().getIndicadorId(), (long) 0);
	  						
	  						cuerpo += "Nombre: "+insumo.getInsumo().getNombre() +"\n";
	  						cuerpo += "Valor medición: "+med.getValor()+"\n";
	  						
	  					}
	  					
	  					
	  				}
	  				
	  					  					  				
				}
				
				correo.sendEmail(host, port, user, pass, titulo, cuerpo, responsable.getEmail());
				
			}
						
		}
		
	
		
		strategosResponsableService.close();
		strategosMedicionesService.close();
		strategosIndicadoresService.close();
		strategosOrganizacionesService.close();
		strategosIndicadoresService.close();
		strategosClasesService.close();
		
		reporteServicio.close();
		
	}
	
	
	public Boolean validarId(String busqueda, List<String> ids) {
		
		boolean existe = false;
		
		if(ids !=null || ids.size() >0) {
			
			existe = ids.contains(busqueda);
			if (existe) {
				System.out.println("El elemento SÍ existe en la lista");
			} else {
				System.out.println("El elemento no existe");
			}
			
		}		
		
		return existe;
	}
	
	// configuración
	
	
	public List<String> obtenerConfiguracion() throws ParserConfigurationException, UnsupportedEncodingException, SAXException, IOException{
		
		List<String> lista = new ArrayList();
		
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		
		
		ConfiguracionNegativo configuracionNegativo = strategosMedicionesService.getConfiguracionNegativo();
		String cadena = "";
		
		if(configuracionNegativo != null) {
			
			
			cadena =configuracionNegativo.getTitulo();
			lista.add(cadena);
			cadena =configuracionNegativo.getTexto();
			lista.add(cadena);
			cadena =configuracionNegativo.getEnviarResponsableCargarEjecutado().toString();
			lista.add(cadena);
			cadena =configuracionNegativo.getEnviarResponsableCargarMeta().toString();
			lista.add(cadena);
			cadena =configuracionNegativo.getEnviarResponsableFijarMeta().toString();
			lista.add(cadena);
			cadena =configuracionNegativo.getEnviarResponsableLograrMeta().toString();
			lista.add(cadena);
			cadena =configuracionNegativo.getEnviarResponsableNegativo().toString();
			lista.add(cadena);
			cadena =configuracionNegativo.getEnviarResponsableSeguimiento().toString();
			lista.add(cadena);
			
		}
				
		cadena = "";
		
		Configuracion configuracion = new Configuracion();
		
		configuracion = frameworkService.getConfiguracion("Strategos.Configuracion.Login");
	    if (configuracion != null)
	    {
	    	 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	         DocumentBuilder db = dbf.newDocumentBuilder();
	         Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
	         doc.getDocumentElement().normalize();
	         NodeList nList = doc.getElementsByTagName("Configuracion");
	         Element eElement = (Element)nList.item(0);
	         
	         cadena =VgcAbstractService.getTagValue("conexionMAILHost", eElement);
	         lista.add(cadena);
	         cadena =VgcAbstractService.getTagValue("conexionMAILPort", eElement);
	         lista.add(cadena);
	         cadena =VgcAbstractService.getTagValue("conexionMAILUser", eElement);
	         lista.add(cadena);
	         
	         String password = TextEncoder.uRLDecode(VgcAbstractService.getTagValue("conexionMAILPassword", eElement));
	         if (!password.equals(""))
	           password = Password.desencripta(password);
	         cadena =password;
	         
	         lista.add(cadena);
	    }
		
		return lista;
	}
	
	
	
	
}

	class TareaValidacion implements Runnable
	{
	ScheduledExecutorService timer;
    int counter;
    int terminar;
    long duracion;
    boolean infinito = false;
    StringBuffer log;
    TimeUnit timeUnit;
    VgcMessageResources messageResources;
    PropertyCalcularManager pm;
    Servicio servicio;
    
    public void programar(long duracion, int terminar, boolean infinito, TimeUnit timeUnit, StringBuffer log, VgcMessageResources messageResources, PropertyCalcularManager pm, Servicio servicio)
    {
        this.terminar = terminar;
        this.infinito = infinito;
        this.log = log;
        this.timeUnit = timeUnit;
        this.messageResources = messageResources;
        this.pm = pm;
        this.servicio = servicio;
        
    	timer = Executors.newSingleThreadScheduledExecutor();
	        
        // Ejecutar dentro de 2 milisegundos, repetir cada 24 Horass
    	if (timeUnit == TimeUnit.DAYS)
    		timer.scheduleAtFixedRate(this, 1, 24, TimeUnit.HOURS);
		else if (timeUnit == TimeUnit.HOURS)
			timer.scheduleAtFixedRate(this, 1, 60, TimeUnit.MINUTES);
		else if (timeUnit == TimeUnit.MINUTES)
			timer.scheduleAtFixedRate(this, 1, 60, TimeUnit.SECONDS);
		else
			timer.scheduleAtFixedRate(this, 1, 60, TimeUnit.MILLISECONDS);
    		
        this.duracion = duracion;
    }
	
    public void run()
    {
        counter++;
        int dormir = 1;
		Calendar now = Calendar.getInstance();
		now.setTimeInMillis(System.currentTimeMillis());

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	    String[] argsReemplazo = new String[4];

	    Calendar nowNext = Calendar.getInstance();
		nowNext.setTimeInMillis(duracion);
    	if (timeUnit == TimeUnit.DAYS)
			duracion = 1000 * 60 * 60 * 24 + (nowNext.getTimeInMillis());
		else if (timeUnit == TimeUnit.HOURS)
			duracion = 1000 * 60 * 60 + (nowNext.getTimeInMillis());
		else if (timeUnit == TimeUnit.MINUTES)
		{
			duracion = 1000 * 60 + (nowNext.getTimeInMillis());
			dormir = 4;
		}
		else
		{
			duracion = 1000 + (nowNext.getTimeInMillis());
			dormir = 4;
		}
    	
    	
    	
    	boolean respuesta = new CalcularManager2(this.pm, this.log, this.messageResources, this.servicio).Calcular(new Long(0), new Long(0), 0, 0);
    	
    	if (respuesta)
    		//
    		log.append("");
		
    	else
    		this.detener();
	        
        // Poner a dormir 4 segundos, como si fuera una tarea demasiado larga
        try
        {
            Thread.sleep(dormir * 1000);
        }
        catch (InterruptedException e)
        {
	    	argsReemplazo[0] = e.getMessage() != null ? e.getMessage() : "";
	    	argsReemplazo[1] = "";
	    	argsReemplazo[2] = "";
	    	argsReemplazo[3] = "";

	    	log.append(messageResources.getResource("jsp.asistente.importacion.log.errordormir", argsReemplazo) + "\n\n");
        }
        
        if (counter == terminar && !infinito)
        {
            this.detener();
        }
    }
	    
    void detener()
    {
        timer.shutdown();
        
        String[] argsReemplazo = new String[2];
        Calendar ahora = Calendar.getInstance();
        
	    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
	    
	    log.append("\n\n");
	    log.append(messageResources.getResource("jsp.asistente.calculo.log.fechainiciocalculo", argsReemplazo) + "\n\n");

	    this.servicio.setLog(this.log.toString());

	    new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);
    }
    
    
	
}
	
	
	
   
    /*
    
    correo cor = new correo();
        cor.init();
    
	*/
	
