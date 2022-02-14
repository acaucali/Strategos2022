package com.visongc.servicio.strategos.protegerliberar;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;

import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.ObjetoClaveValor;
import com.visiongc.framework.model.Columna;
import com.visiongc.framework.model.Transaccion;
import com.visiongc.servicio.strategos.calculos.impl.CalculoManager;
import com.visongc.servicio.strategos.protegerliberar.Tarea;


import com.visiongc.servicio.strategos.indicadores.model.Medicion;
import com.visiongc.servicio.strategos.indicadores.model.MedicionPK;

import com.visiongc.servicio.strategos.message.model.Message;
import com.visiongc.servicio.strategos.model.util.Frecuencia;
import com.visiongc.servicio.strategos.planes.model.Meta;
import com.visiongc.servicio.strategos.planes.model.MetaPK;
import com.visiongc.servicio.strategos.planes.model.Perspectiva;
import com.visiongc.servicio.strategos.planes.model.util.TipoMeta;
import com.visiongc.servicio.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.servicio.strategos.servicio.model.Servicio;
import com.visiongc.servicio.strategos.util.PeriodoUtil;
import com.visiongc.servicio.web.importar.dal.indicador.IndicadorManager;
import com.visiongc.servicio.web.importar.dal.medicion.MedicionManager;
import com.visiongc.servicio.web.importar.dal.message.MessageManager;
import com.visiongc.servicio.web.importar.dal.meta.MetaManager;
import com.visiongc.servicio.web.importar.dal.perspectiva.PerspectivaManager;
import com.visiongc.servicio.web.importar.dal.servicio.ServicioManager;
import com.visiongc.servicio.web.importar.dal.usuario.UsuarioManager;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcFormatter;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.util.ConnectionManager;

public class ProtegerLiberarManager {
	  PropertyCalcularManager pm;
	  StringBuffer log;
	  VgcMessageResources messageResources;
	  Boolean logConsolaMetodos = Boolean.valueOf(false);
	  Boolean logConsolaDetallado = Boolean.valueOf(false);
	  Servicio servicio;
	  
	  //
	  public ProtegerLiberarManager(String[][] configuracion, StringBuffer log, VgcMessageResources messageResources)
	  {
	    pm = new PropertyCalcularManager().Set(configuracion);
	    this.log = log;
	    this.messageResources = messageResources;
	    logConsolaMetodos = Boolean.valueOf(Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false")));
	    logConsolaDetallado = Boolean.valueOf(Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false")));
	    
	    if (servicio == null)
	    {
	      Long usuarioId = Long.valueOf(pm.getProperty("usuarioId", "") != "" ? Long.parseLong(pm.getProperty("usuarioId", "")) : new UsuarioManager(pm, this.log, this.messageResources).LoadAdmin(null).longValue());
	      servicio = new Servicio(usuarioId, null, this.messageResources.getResource("jsp.servicio.importar.titulo"), null, this.messageResources.getResource("jsp.servicio.inicio"), "");
	    }
	  }
	  
	  public ProtegerLiberarManager(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources, Servicio servicio)
	  {
	    this.pm = pm;
	    this.log = log;
	    this.messageResources = messageResources;
	    logConsolaMetodos = Boolean.valueOf(Boolean.parseBoolean(pm.getProperty("logConsolaMetodos", "false")));
	    logConsolaDetallado = Boolean.valueOf(Boolean.parseBoolean(pm.getProperty("logConsolaDetallado", "false")));
	    this.servicio = servicio;
	    
	    if (this.servicio == null)
	    {
	      Long usuarioId = Long.valueOf(pm.getProperty("usuarioId", "") != "" ? Long.parseLong(pm.getProperty("usuarioId", "")) : new UsuarioManager(this.pm, this.log, this.messageResources).LoadAdmin(null).longValue());
	      this.servicio = new Servicio(usuarioId, null, this.messageResources.getResource("jsp.servicio.importar.titulo"), null, this.messageResources.getResource("jsp.servicio.inicio"), "");
	    }
	  }
	  
	  //  revisar
	  public boolean Ejecutar(String datos[][],List<Indicador> indicadores, Calendar fecha1, StrategosIndicadoresService strategosIndicadoresService, byte accion, Long serieId)
	  {
	      boolean respuesta = false;
	      respuesta = (new ServicioManager(pm, log, messageResources)).saveServicio(servicio, null) == 10000;
	      boolean activarSheduler = Boolean.parseBoolean(pm.getProperty("activarSheduler", "false"));
	      if(!activarSheduler)
	      {
	    	  // 
	          respuesta = ProtegerLiberar(indicadores, fecha1, strategosIndicadoresService, accion, serieId);
	          //
	          String argsReemplazo[] = new String[2];
	          Calendar ahora = Calendar.getInstance();
	          argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	          argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
	          log.append("\n\n");
	          log.append((new StringBuilder(String.valueOf(messageResources.getResource("jsp.asistente.importacion.log.fechafincalculo", argsReemplazo)))).append("\n\n").toString());
	          if(!respuesta)
	          {
	              argsReemplazo[0] = messageResources.getResource("jsp.asistente.importacion.log.error.inesperado");
	              argsReemplazo[1] = "";
	              log.append((new StringBuilder(String.valueOf(messageResources.getResource("jsp.asistente.importacion.log.error", argsReemplazo)))).append("\n\n").toString());
	              servicio.setMensaje(messageResources.getResource("jsp.asistente.importacion.log.error", argsReemplazo));
	          }
	          servicio.setLog(log.toString());
	          (new ServicioManager(pm, log, messageResources)).saveServicio(servicio, null);
	      } else
	      {
	          int terminar = Integer.parseInt(pm.getProperty("numeroEjecucion", "1"));
	          int unidadTiempo = Integer.parseInt(pm.getProperty("unidadTiempo", "3"));
	          Calendar inicio = Calendar.getInstance();
	          String fecha[] = servicio.getFecha().split("-");
	          int dia = Integer.parseInt(fecha[2].substring(0, 2));
	          int mes = Integer.parseInt(fecha[1]);
	          int ano = Integer.parseInt(fecha[0]);
	          if(unidadTiempo != 0)
	              inicio.set(ano, mes - 1, dia);
	          else
	              inicio.set(ano, mes - 1, dia, 8, 0, 0);
	          Tarea t1 = new Tarea();
	          TimeUnit timeUnit;
	          long duracion;
	          if(unidadTiempo == 0)
	          {
	              timeUnit = TimeUnit.DAYS;
	              duracion = 0x5265c00L + inicio.getTimeInMillis();
	          } else
	          if(unidadTiempo == 1)
	          {
	              timeUnit = TimeUnit.HOURS;
	              duracion = 0x36ee80L + inicio.getTimeInMillis();
	          } else
	          if(unidadTiempo == 2)
	          {
	              timeUnit = TimeUnit.MINUTES;
	              duracion = 60000L + inicio.getTimeInMillis();
	          } else
	          {
	              timeUnit = TimeUnit.SECONDS;
	              duracion = 1000L + inicio.getTimeInMillis();
	          }
	          Calendar nowDuracion = Calendar.getInstance();
	          nowDuracion.setTimeInMillis(duracion);
	          //
	          t1.programar(duracion, terminar, terminar == 0, timeUnit, log, messageResources, pm, servicio);
	      }
	      return respuesta;
	  }
	  
	  
	  public boolean ProtegerLiberar(List<Indicador> indicadores, Calendar fecha1, StrategosIndicadoresService strategosIndicadoresService, byte accion, Long serieId)
	  {
	    boolean respuestas = false;
	    Date fechaDate = fecha1.getTime();
	    Set<com.visiongc.app.strategos.indicadores.model.SerieIndicador> seriesIndicador = null;
	    int respuesta = VgcReturnCode.DB_OK;
	    
	    
	    for (Iterator<Indicador> iter = indicadores.iterator(); iter.hasNext(); )
		{
	    	Indicador indicador = (Indicador)iter.next();
			boolean actualizar = false;
			seriesIndicador = indicador.getSeriesIndicador();
		    for (Iterator<?> i = seriesIndicador.iterator(); i.hasNext(); ) 
		    {
		    	SerieIndicador serie = (SerieIndicador)i.next();
		    	
		    	
		    		if(serieId != null) {
		    			
		    			if(serie.getPk().getSerieId().longValue() == serieId ) {
			    				if (serie.getFechaBloqueo() != null)
						    	{
									if (accion == 2)
									{
							    		if (serie.getFechaBloqueo().after(fechaDate))
							    		{
							    			actualizar = true;
							    			serie.setFechaBloqueo(fechaDate);
							    		}
									}
									else
									{
							    		if (serie.getFechaBloqueo().before(fechaDate))
							    		{
							    			actualizar = true;
							    			serie.setFechaBloqueo(fechaDate);
							    		}
									}
						    	}
						    	else if (accion == 1)
						    	{
						    		actualizar = true;
						    		serie.setFechaBloqueo(fechaDate);
						    	}
		    			}
		    			
		    		}else if(serieId == null){
		    			
		    			if (serie.getFechaBloqueo() != null)
				    	{
							if (accion == 2)
							{
					    		if (serie.getFechaBloqueo().after(fechaDate))
					    		{
					    			actualizar = true;
					    			serie.setFechaBloqueo(fechaDate);
					    		}
							}
							else
							{
					    		if (serie.getFechaBloqueo().before(fechaDate))
					    		{
					    			actualizar = true;
					    			serie.setFechaBloqueo(fechaDate);
					    		}
							}
				    	}
				    	else if (accion == 1)
				    	{
				    		actualizar = true;
				    		serie.setFechaBloqueo(fechaDate);
				    	}
		    			
		    		}
		    	
		    	
		    }
		    
		    if (actualizar)
		    	respuesta = strategosIndicadoresService.saveSerieIndicador(indicador.getIndicadorId(), seriesIndicador);
		    if (respuesta != VgcReturnCode.DB_OK)
		    	break;
		}
	    
	    respuestas = true;
	    
	    return respuestas;
	  
	  }
	  
	  
}
