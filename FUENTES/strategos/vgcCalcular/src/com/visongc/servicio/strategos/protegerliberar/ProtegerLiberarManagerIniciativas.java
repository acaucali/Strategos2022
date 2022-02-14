package com.visongc.servicio.strategos.protegerliberar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.framework.model.Usuario;
import com.visiongc.servicio.strategos.servicio.model.Servicio;
import com.visiongc.servicio.web.importar.dal.servicio.ServicioManager;
import com.visiongc.servicio.web.importar.dal.usuario.UsuarioManager;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcFormatter;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;

public class ProtegerLiberarManagerIniciativas {
	  PropertyCalcularManager pm;
	  StringBuffer log;
	  VgcMessageResources messageResources;
	  Boolean logConsolaMetodos = Boolean.valueOf(false);
	  Boolean logConsolaDetallado = Boolean.valueOf(false);
	  Servicio servicio;
	  
	  //
	  public ProtegerLiberarManagerIniciativas(String[][] configuracion, StringBuffer log, VgcMessageResources messageResources)
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
	  
	  public ProtegerLiberarManagerIniciativas(PropertyCalcularManager pm, StringBuffer log, VgcMessageResources messageResources, Servicio servicio)
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
	  public boolean Ejecutar(String datos[][],List<Long> indicadoresId, Calendar fecha1, StrategosIndicadoresService strategosIndicadoresService, byte accion, Usuario usuario, Integer anoIni, Integer mesInicial, Integer mesFinal, byte serieId)
	  {
	      boolean respuesta = false;
	      respuesta = (new ServicioManager(pm, log, messageResources)).saveServicio(servicio, null) == 10000;
	      boolean activarSheduler = Boolean.parseBoolean(pm.getProperty("activarSheduler", "false"));
	      if(!activarSheduler)
	      {
	    	  // 
	          respuesta = ProtegerLiberar(indicadoresId, fecha1, strategosIndicadoresService, accion, usuario, anoIni, mesInicial, mesFinal, serieId);
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
	          TareaMetas t1 = new TareaMetas();
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
	  
	  
	  public boolean ProtegerLiberar(List<Long> indicadores, Calendar fecha1, StrategosIndicadoresService strategosIndicadoresService, byte accion, Usuario usuario, Integer anoIni, Integer mesInicial, Integer mesFinal, byte serieId)
	  {
		
		
		
		  
	    boolean respuestas = false;
	    Date fechaDate = fecha1.getTime();
	    
	    int respuesta = VgcReturnCode.DB_OK;
	    
	    
	    for(Long id: indicadores){
	    	
	    	List<Indicador> indicadoresList = new ArrayList();
	    	Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, id);
	    	if(indicador != null){
	    		
	    		indicadoresList.add(indicador);
	    		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
	    		
	    		List<Medicion> medicionesFinal = new ArrayList();	    		
	    		List<Medicion> mediciones = new ArrayList();
	    		
	    		
	    		
	    		// si la serie es todas
	    		if(serieId == 7) {
	    			mediciones = strategosMedicionesService.getMedicionesIndicadores(indicadoresList, null, anoIni, anoIni);
	    		}else {
	    			mediciones = strategosMedicionesService.getMedicionesIndicadores(indicadoresList, (long) serieId, anoIni, anoIni);
	    		}
	    		
	    		
		    	
	    		if(mediciones != null && mediciones.size() >0){
	    			for (Iterator<Medicion> iter =mediciones.iterator(); iter.hasNext(); )
					{
				    	Medicion medicion = (Medicion)iter.next();
				    	
				    	if(medicion.getMedicionId().getPeriodo() >= mesInicial  && medicion.getMedicionId().getPeriodo() <= mesFinal){
				    		medicionesFinal.add(medicion);
				    		
				    	}		    	
				    	
					}	
	    			strategosMedicionesService.close();
	    			respuestas = protegerLiberarMediciones(medicionesFinal, accion, usuario);
	    		}
	    			    	
		    		
	    	}
	    	
	    	    	
	    	
	    }
	    
	    return respuestas;
	  
	  }
	  
	  private boolean protegerLiberarMediciones(List<Medicion> mediciones, byte accion, Usuario usuario){
		  
		  StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		
		  for (Iterator<Medicion> iter =mediciones.iterator(); iter.hasNext(); )
			{
		    	Medicion medicion = (Medicion)iter.next();
		    	
		    	if (accion == 2)
				{
		    		medicion.setProtegido(false);
				}
		    	if (accion == 1)
				{
		    		medicion.setProtegido(true);
				}		    	
		    	
		    	strategosMedicionesService.saveMedicion(medicion, usuario);
			}
		  
		  strategosMedicionesService.close();
		  
		  return true;
	  }
	  
	  
	  
}
