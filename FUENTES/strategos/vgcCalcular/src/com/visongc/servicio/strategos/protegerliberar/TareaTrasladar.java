package com.visongc.servicio.strategos.protegerliberar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.visiongc.servicio.strategos.calculos.CalcularManager;
import com.visiongc.servicio.strategos.servicio.model.Servicio;
import com.visiongc.servicio.web.importar.dal.servicio.ServicioManager;
import com.visiongc.servicio.web.importar.util.PropertyCalcularManager;
import com.visiongc.servicio.web.importar.util.VgcFormatter;
import com.visiongc.servicio.web.importar.util.VgcMessageResources;

public class TareaTrasladar implements Runnable{
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
	    
	    this.timer = Executors.newSingleThreadScheduledExecutor();
	    if (timeUnit == TimeUnit.DAYS) {
	      this.timer.scheduleAtFixedRate(this, 1L, 24L, TimeUnit.HOURS);
	    } else if (timeUnit == TimeUnit.HOURS) {
	      this.timer.scheduleAtFixedRate(this, 1L, 60L, TimeUnit.MINUTES);
	    } else if (timeUnit == TimeUnit.MINUTES) {
	      this.timer.scheduleAtFixedRate(this, 1L, 60L, TimeUnit.SECONDS);
	    } else {
	      this.timer.scheduleAtFixedRate(this, 1L, 60L, TimeUnit.MILLISECONDS);
	    }
	    this.duracion = duracion;
	  }
	  
	  public void run()
	  {
	    this.counter += 1;
	    int dormir = 1;
	    Calendar now = Calendar.getInstance();
	    now.setTimeInMillis(System.currentTimeMillis());
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	    String[] argsReemplazo = new String[4];
	    
	    Calendar nowNext = Calendar.getInstance();
	    nowNext.setTimeInMillis(this.duracion);
	    if (this.timeUnit == TimeUnit.DAYS)
	    {
	      this.duracion = (86400000L + nowNext.getTimeInMillis());
	    }
	    else if (this.timeUnit == TimeUnit.HOURS)
	    {
	      this.duracion = (3600000L + nowNext.getTimeInMillis());
	    }
	    else if (this.timeUnit == TimeUnit.MINUTES)
	    {
	      this.duracion = (60000L + nowNext.getTimeInMillis());
	      dormir = 4;
	    }
	    else
	    {
	      this.duracion = (1000L + nowNext.getTimeInMillis());
	      dormir = 4;
	    }
	    boolean respuesta = new CalcularManager(this.pm, this.log, this.messageResources, this.servicio).Calcular();
	    if (respuesta) {
	      this.log.append("\n\nEjecuci�n [Thread " + Thread.currentThread().getName() + "] " + this.counter + " Ejecuci�n=" + sdf.format(now.getTime()) + " proxima Ejecuci�n=" + sdf.format(nowNext.getTime()) + "\n");
	    } else {
	      detener();
	    }
	    try
	    {
	      Thread.sleep(dormir * 1000);
	    }
	    catch (InterruptedException e)
	    {
	      argsReemplazo[0] = (e.getMessage() != null ? e.getMessage() : "");
	      argsReemplazo[1] = "";
	      argsReemplazo[2] = "";
	      argsReemplazo[3] = "";
	      
	      this.log.append(this.messageResources.getResource("jsp.asistente.importacion.log.errordormir", argsReemplazo) + "\n\n");
	    }
	    if ((this.counter == this.terminar) && (!this.infinito)) {
	      detener();
	    }
	  }
	  
	  public void detener()
	  {
	    this.timer.shutdown();
	    
	    String[] argsReemplazo = new String[2];
	    Calendar ahora = Calendar.getInstance();
	    
	    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
	    
	    this.log.append("\n\n");
	    this.log.append(this.messageResources.getResource("jsp.asistente.calculo.log.fechainiciocalculo", argsReemplazo) + "\n\n");
	    
	    this.servicio.setLog(this.log.toString());
	    
	    new ServicioManager(this.pm, this.log, this.messageResources).saveServicio(this.servicio, null);
	  }
}
