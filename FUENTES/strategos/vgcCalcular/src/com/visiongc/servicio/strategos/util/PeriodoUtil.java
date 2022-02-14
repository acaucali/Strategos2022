/**
 * 
 */
package com.visiongc.servicio.strategos.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import com.visiongc.servicio.strategos.model.util.Frecuencia;
import com.visiongc.servicio.strategos.model.util.Periodo;

/**
 * @author Kerwin
 *
 */
public class PeriodoUtil 
{
	private static final Integer[][][] matrizPeriodos = getMatrizPeriodos();
	
	public static int getNumeroMaximoPeriodosPorFrecuencia(byte frecuencia, int ano)
	{
		switch (frecuencia) 
		{
	    	case 0:
	    		if (ano == -1) 
	    			return 365;
	    		if (ano % 4 == 0) 
	    			return 366;
	    		return 365;
	    	case 1:
	    		return 52;
	    	case 2:
	    		return 24;
	    	case 3:
	    		return 12;
	    	case 4:
	    	return 6;
	    	case 5:
	    		return 4;
	    	case 6:
	    		return 3;
	    	case 7:
	    		return 2;
	    	case 8:
	    		return 1;
    	}

    	return -1;
  	}
	
	public static int getPeriodoDeFecha(Calendar fecha, byte frecuencia)
	{
		int periodoCalculado = 0;
	    boolean mayorIgual = false;
	    boolean menorIgual = false;
	    Periodo periodo = null;

	    List<Periodo> periodos = generarPeriodosFrecuencia(fecha.get(1), frecuencia);

	    int c = 1;
	    for (Iterator<Periodo> ite = periodos.iterator(); ite.hasNext(); ) 
	    {
	    	periodo = (Periodo)ite.next();

	    	mayorIgual = (fecha.after(periodo.getFechaInicio())) || (fecha.equals(periodo.getFechaInicio()));
	    	menorIgual = (fecha.before(periodo.getFechaFinal())) || (fecha.equals(periodo.getFechaFinal()));

	    	if ((mayorIgual) && (menorIgual)) 
	    	{
	    		periodoCalculado = c;
	    		break;
	    	}
	    	c++;
	    }

	    return periodoCalculado;
	}
	
	public static int getPeriodoDate(Date fecha, byte frecuencia)
	{
		Calendar fechaCal = Calendar.getInstance();
		fechaCal.setTime(fecha);		
		
		return getPeriodoDeFecha(fechaCal, frecuencia);
	}
	
  	public static Calendar fechaCalendario(int ano, int mes, int dia, boolean horaMaximaDia)
  	{
  		try
  		{
  			Calendar fecha = Calendar.getInstance();
  			fecha.set(ano, (mes - 1), dia);

  			if (horaMaximaDia)
  				fecha = finDelDia(fecha);
  			else 
  				fecha = inicioDelDia(fecha); 

  			return fecha; 
  		} 
  		catch (Exception e) 
  		{
  		}

  		return null;
  	}
	
  	public static Calendar sumarDias(Calendar fecha, int ndays, boolean horaMaximaDia) 
  	{
  		Calendar fecha2 = Calendar.getInstance();

  		int day = fecha.get(5);
  		int month = fecha.get(2);
  		int year = fecha.get(1);

  		fecha2.set(5, day);
  		fecha2.set(2, month);
  		fecha2.set(1, year);
  		
  		fecha2.add(5, ndays);
  		
  		if (horaMaximaDia)
  			return finDelDia(fecha2);
  		else
  			return inicioDelDia(fecha2);
  	}
  	
	public static Calendar inicioDelDia(Calendar fecha)
  	{
  		Calendar fecha2 = Calendar.getInstance();
  		
  		fecha2.set(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH), fecha.get(Calendar.DATE), 0, 0, 0);
  		fecha2.set(Calendar.MILLISECOND, 0);
  		
  		return fecha2;
  	}

  	public static Calendar finDelDia(Calendar fecha)
  	{
  		Calendar fecha2 = Calendar.getInstance();

  		fecha2.set(fecha.get(Calendar.YEAR), fecha.get(Calendar.MONTH), fecha.get(Calendar.DATE), 23, 59, 59);
  		fecha2.set(Calendar.MILLISECOND, 0);
  		
  		return fecha2;
  	}
	
	public static int ultimoDiaMes(int mes, int ano)
	{
		Calendar fecha = Calendar.getInstance();

	    fecha.set(5, 1);

	    fecha.set(2, mes);
	    fecha.set(1, ano);

	    fecha.add(5, -1);

	    return fecha.get(5);
	}
	
  	public static List<Periodo> generarPeriodosFrecuencia(int ano, byte frecuencia)
  	{
  		Periodo periodo = null;
  		List<Periodo> colPeriodos = new ArrayList<Periodo>();
  		int numeroPeriodo = 0;
	   
  		int diaFinal = -1;
  		int mes = 0;
	   
  		Calendar fechaInicio = fechaCalendario(ano, 1, 1, false);
  		Calendar fechaFinal = fechaCalendario(ano, 12, ultimoDiaMes(12, ano), true);
  		Calendar fechaGuia = null;
  		switch (frecuencia)
  		{
  			case 0:
  				fechaGuia = fechaInicio;	   
  				fechaGuia = sumarDias(fechaGuia, -1, false);
  				do 
  				{
  					++numeroPeriodo;
  					periodo = new Periodo();  					
  					fechaGuia = sumarDias(fechaGuia, 1, false);	   
  					periodo.setFechaInicio(fechaGuia);
  					periodo.setAnoPeriodo(ano);
  					periodo.setNumeroPeriodo(numeroPeriodo);
  					Calendar fechaFinalPeriodo = (Calendar)fechaGuia.clone();
	  				periodo.setFechaFinal(finDelDia(fechaFinalPeriodo));
	  				fechaGuia = periodo.getFechaInicio();	   
	  				colPeriodos.add(periodo);
	   
	  				if (periodo.getFechaInicio().equals(fechaFinal)) return colPeriodos;  
  				}
  				while (!(periodo.getFechaInicio().after(fechaFinal)));	   
  				break;
  			case 1:
  				fechaGuia = fechaInicio;
  				while (fechaGuia.before(fechaFinal)) 
  				{
  					periodo = new Periodo();
	   
  					++numeroPeriodo;
  					periodo.setAnoPeriodo(ano);
  					periodo.setNumeroPeriodo(numeroPeriodo);
  					periodo.setFechaInicio(fechaGuia);
  					Calendar fechaFinalPeriodo = null;
  					if (fechaGuia.get(Calendar.DAY_OF_WEEK) == 1)
  						fechaFinalPeriodo = sumarDias(fechaGuia, 6, true);
  					else
  						fechaFinalPeriodo = sumarDias(fechaGuia, (7 - (fechaGuia.get(Calendar.DAY_OF_WEEK))), true);
  					periodo.setFechaFinal(fechaFinalPeriodo);
  					fechaGuia = sumarDias(fechaFinalPeriodo, 1, false);
	   
  					colPeriodos.add(periodo);
  				}
  				break;
	       case 2:
	    	   for (mes = 1; mes <= 12; ++mes)
	    	   {
	    		   switch (mes)
	    		   {
		    		   case 1:
		    		   case 3:
		    		   case 5:
		    		   case 7:
		    		   case 8:
		    		   case 10:
		    		   case 12:
		    			   diaFinal = 31;
		    			   break;
		    		   case 2:
		    			   diaFinal = ultimoDiaMes(2, ano);
		    			   break;
		    		   case 4:
		    		   case 6:
		    		   case 9:
		    		   case 11:
		    			   diaFinal = 30;
	    		   }	   
	    		   periodo = new Periodo();
	    		   periodo.setAnoPeriodo(ano);
	    		   periodo.setNumeroPeriodo(mes * 2 - 1);
	    		   periodo.setFechaInicio(fechaCalendario(ano, mes, 1, false));
	    		   periodo.setFechaFinal(fechaCalendario(ano, mes, 15, true));
	    		   colPeriodos.add(periodo);
	    		   
	    		   periodo = new Periodo();
	    		   periodo.setAnoPeriodo(ano);
	    		   periodo.setNumeroPeriodo(mes * 2);
	    		   periodo.setFechaInicio(fechaCalendario(ano, mes, 16, false));
	    		   periodo.setFechaFinal(fechaCalendario(ano, mes, diaFinal, true));
	    		   
	    		   colPeriodos.add(periodo);
	    	   }
	    	   break;
	       	case 3:
	       		for (mes = 1; mes <= 12; ++mes)
	       		{
	       			switch (mes)
	       			{
	       			case 1:
	       			case 3:
	       			case 5:
	       			case 7:
	       			case 8:
	       			case 10:
	       			case 12:
	       				diaFinal = 31;
	       				break;
	       			case 2:
	       				diaFinal = ultimoDiaMes(2, ano);
	       				break;
	       			case 4:
	       			case 6:
	       			case 9:
	       			case 11:
	       				diaFinal = 30;
	       			}
	   
	       			periodo = new Periodo();
	       			periodo.setAnoPeriodo(ano);
	       			periodo.setNumeroPeriodo(mes);
	       			periodo.setFechaInicio(fechaCalendario(ano, mes, 1, false));
	       			periodo.setFechaFinal(fechaCalendario(ano, mes, diaFinal, true));
	       			colPeriodos.add(periodo);
	       		}
	       		break;
	       case 4:
	    	   for (mes = 1; mes <= 12; ++mes)
	    	   {
	    		   switch (mes)
	    		   	{
	    		   		case 1:
	    		   		case 3:
	    		   		case 5:
	    		   		case 7:
	    		   		case 8:
	    		   		case 10:
	    		   		case 12:
	    		   			diaFinal = 31;
	    		   			break;
	    		   		case 2:
	    		   			diaFinal = ultimoDiaMes(2, ano);
	    		   			break;
    		   			case 4:
    		   			case 6:
    		   			case 9:
    		   			case 11:
    		   				diaFinal = 30;
	    		   	}
	    		   	
	    		   if (mes % 2 != 0) 
	    		   {
	    			   periodo = new Periodo();
	    			   periodo.setFechaInicio(fechaCalendario(ano, mes, 1, false));
	    		   } 
	    		   else 
	    		   {
	    			   periodo.setAnoPeriodo(ano);
	    			   periodo.setNumeroPeriodo(mes / 2);
	    			   periodo.setFechaFinal(fechaCalendario(ano, mes, diaFinal, true));
	    			   colPeriodos.add(periodo);
	    		   }	   
	    	   }	   
	    	   break;
	       	case 5:
	       		for (mes = 1; mes <= 12; ++mes)
	       		{
	       			switch (mes)
	       			{
	       				case 1:
	       				case 3:
	       				case 5:
	       				case 7:	
	       				case 8:
	       				case 10:
	       				case 12:
	       					diaFinal = 31;
	       					break;
	       				case 2:
	       					diaFinal = ultimoDiaMes(2, ano);
	       					break;
	       				case 4:
	       				case 6:
	       				case 9:
	       				case 11:
	       					diaFinal = 30;
	       			}
	   
	       			if ((mes == 1) || (mes == 4) || (mes == 7) || (mes == 10)) 
	       			{
	       				periodo = new Periodo();
	       				periodo.setFechaInicio(fechaCalendario(ano, mes, 1, false));
	       			}
	       			if ((mes == 3) || (mes == 6) || (mes == 9) || (mes == 12)) 
	       			{
	       				periodo.setFechaFinal(fechaCalendario(ano, mes, diaFinal, true));
	       				periodo.setAnoPeriodo(ano);
	       				periodo.setNumeroPeriodo(mes / 3);
	       				colPeriodos.add(periodo);
	       			}
	       		}	   
	       		break;
	       	case 6:
	       		for (mes = 1; mes <= 12; ++mes)
	       		{
	       			switch (mes)
	       			{
	       				case 1:
	       				case 3:
	       				case 5:
	       				case 7:
	       				case 8:
	       				case 10:
	       				case 12:
	       					diaFinal = 31;
	       					break;
	       				case 2:
	       					diaFinal = ultimoDiaMes(2, ano);
	       					break;
	       				case 4:
	       				case 6:
	       				case 9:
	       				case 11:
	       					diaFinal = 30;
	       			}
	   
	       			if ((mes == 1) || (mes == 5) || (mes == 9)) 
	       			{
	       				periodo = new Periodo();
	       				periodo.setFechaInicio(fechaCalendario(ano, mes, 1, false));
	       			}
	       			if ((mes == 4) || (mes == 8) || (mes == 12)) 
	       			{
	       				periodo.setFechaFinal(fechaCalendario(ano, mes, diaFinal, true));
	       				periodo.setAnoPeriodo(ano);
	       				periodo.setNumeroPeriodo(mes / 4);
	       				colPeriodos.add(periodo);
	       			}
	       		}
	       		break;
	       	case 7:
	       		for (mes = 1; mes <= 12; ++mes) 
	       		{
	       			switch (mes)
	       			{
	       				case 1:
	       				case 3:
	       				case 5:
	       				case 7:
	       				case 8:
	       				case 10:
	       				case 12:
	       					diaFinal = 31;
	       					break;
	       				case 2:
	       					diaFinal = ultimoDiaMes(2, ano);
	       					break;
	       				case 4:
	       				case 6:
	       				case 9:
	       				case 11:
	       					diaFinal = 30;
	       			}
	       			
	       			if ((mes == 1) || (mes == 7)) 
	       			{
	       				periodo = new Periodo();
	       				periodo.setFechaInicio(fechaCalendario(ano, mes, 1, false));
	       			}
	       			if ((mes == 6) || (mes == 12)) 
	       			{
	       				periodo.setFechaFinal(fechaCalendario(ano, mes, diaFinal, true));
	       				periodo.setAnoPeriodo(ano);
	       				periodo.setNumeroPeriodo(mes / 6);
	       				colPeriodos.add(periodo);
	       			}
	       		}
	       		break;
	       	case 8:
	       		periodo = new Periodo();
	       		periodo.setFechaInicio(fechaInicio);
	       		periodo.setFechaFinal(finDelDia(fechaFinal));
	       		periodo.setAnoPeriodo(ano);
	       		periodo.setNumeroPeriodo(1);
	       		
	       		colPeriodos.add(periodo);
  		}
	   
  		return colPeriodos;
  	}
	
  	public static Byte getMesInicio(Byte mesCierre) 
  	{
  		int mesInicio = 1;
  		if (mesCierre.byteValue() < 12) 
  			mesInicio = mesCierre.byteValue() + 1;
  		
  		return new Byte(Integer.toString(mesInicio));
  	}
  	
  	public static Calendar getCalendarFinMes(int mes, int ano) 
  	{
  		Calendar fecha = Calendar.getInstance();

		fecha.set(ano, mes, 1);
		fecha = finDelDia(fecha);
		fecha.add(5, -1);
  		
		return fecha;
  	}
    
    public static int getPeriodoAnterior(int periodo, int ano, byte frecuencia)
    {
    	int periodoAnterior = 0;
    	if (periodo > 1)
    		periodoAnterior = periodo - 1;
    	else 
    		periodoAnterior = getNumeroMaximoPeriodosPorFrecuencia(frecuencia, ano - 1);
      
    	return periodoAnterior;
    }
    
  	public static int getAnoPeriodoAnterior(int periodo, int ano)
  	{
  		int anoPeriodoAnterior = 0;
  		if (periodo > 1)
  			anoPeriodoAnterior = ano;
  		else 
  			anoPeriodoAnterior = ano - 1;
  		
  		return anoPeriodoAnterior;
  	}
  	
    public static Calendar getDateByPeriodo(Byte frecuencia, int ano, int periodo, boolean firstDate)
    {
  	  	Calendar fecha = Calendar.getInstance();

  	  	if (frecuencia.equals(Frecuencia.getFrecuenciaDiaria())) 
  	  	{
  	  		fecha.set(1, ano);
  	  		fecha.set(6, periodo);
  	  	}
  	  	else if (frecuencia.equals(Frecuencia.getFrecuenciaMensual())) 
  	  	{
  	  		fecha.set(1, ano);
  	  		fecha.set(2, periodo - 1);
  	  		if (firstDate)
  	  			fecha.set(5, 1);
  	  		else
  	  			fecha.set(5, 31);
  	  	}
  	  	else if (!frecuencia.equals(Frecuencia.getFrecuenciaAnual())) 
  	  	{
  	  		int factorFrecuencia = 0;
  	  		
  	  		if (frecuencia.equals(Frecuencia.getFrecuenciaBimensual()))
  	  			factorFrecuencia = 2;
  	  		else if (frecuencia.equals(Frecuencia.getFrecuenciaTrimestral()))
  	  			factorFrecuencia = 3;
  	  		else if (frecuencia.equals(Frecuencia.getFrecuenciaCuatrimestral()))
  	  			factorFrecuencia = 4;
  	  		else if (frecuencia.equals(Frecuencia.getFrecuenciaSemestral())) 
  	  			factorFrecuencia = 6;

  	  		fecha.set(1, ano);
  	  		if (firstDate)
  	  		{
  	  			fecha.set(2, factorFrecuencia * periodo - factorFrecuencia);
  	  			fecha.set(5, 1);
  	  		}
  	  		else
  	  		{
  	  			fecha.set(2, factorFrecuencia * periodo -1);
  	  			fecha.set(5, 31);
  	  		}
  	  	}
  	  	else  
  	  	{
  	  		fecha.set(1, ano);
  	  		if (firstDate)
  	  		{
  	  			fecha.set(2, 0);
  	  			fecha.set(5, 1);
  	  		}
  	  		else
  	  		{
  	  			fecha.set(2, 11);
  	  			fecha.set(5, 31);
  	  		}
  	  	}
  	  	
  	  	return fecha;
	}
    
    public static List<LapsoTiempo> getLapsosTiempoEnPeriodosPorFrecuenciaMes(int anoInicio, int anoFinal, int mesInicio, int mesFinal) 
    {
        List<LapsoTiempo> lapsos = new ArrayList<LapsoTiempo>();

        lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaDiaria().byteValue()));
        lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaSemanal().byteValue()));
        lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaQuincenal().byteValue()));
        lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaMensual().byteValue()));
        lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaBimensual().byteValue()));
        lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaTrimestral().byteValue()));
        lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaCuatrimestral().byteValue()));
        lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaSemestral().byteValue()));
        lapsos.add(getLapsoTiempoEnPeriodosPorMes(anoInicio, anoFinal, mesInicio, mesFinal, Frecuencia.getFrecuenciaAnual().byteValue()));

        return lapsos;
    }
    
    public static LapsoTiempo getLapsoTiempoEnPeriodosPorMes(int anoInicio, int anoFinal, int mesInicio, int mesFinal, byte frecuencia)
    {
    	List<Periodo> periodos = new ArrayList<Periodo>();

    	Periodo periodo = null;
    	LapsoTiempo lapso = new LapsoTiempo();

    	Calendar fechaInicio = fechaCalendario(anoInicio, mesInicio, 1, false);
    	Calendar fechaFinal = fechaCalendario(anoFinal, mesFinal, ultimoDiaMes(mesFinal, anoFinal), true);

    	periodos = generarPeriodosFrecuencia(anoInicio, frecuencia);

    	int c = 1;
    	for (Iterator<Periodo> iter = periodos.iterator(); iter.hasNext(); ) 
    	{
    		periodo = (Periodo)iter.next();

    		boolean mayorIgual = fechaInicio.getTimeInMillis() >= periodo.getFechaInicio().getTimeInMillis();
    		boolean menorIgual = fechaInicio.getTimeInMillis() <= periodo.getFechaFinal().getTimeInMillis();

    		if ((mayorIgual) && (menorIgual)) 
    		{
    			lapso.setPeriodoInicio(new Integer(c));
    			break;
    		}
    		c++;
    	}

    	if (anoInicio != anoFinal) 
    	{
    		periodos = generarPeriodosFrecuencia(anoFinal, frecuencia);
    	}

    	c = 1;
    	for (Iterator<Periodo> iter = periodos.iterator(); iter.hasNext(); ) 
    	{
    		periodo = (Periodo)iter.next();

    		boolean mayorIgual = fechaFinal.getTimeInMillis() >= periodo.getFechaInicio().getTimeInMillis();
    		boolean menorIgual = fechaFinal.getTimeInMillis() <= periodo.getFechaFinal().getTimeInMillis();

    		if ((mayorIgual) && (menorIgual)) 
    		{
    			lapso.setPeriodoFin(new Integer(c));
    			break;
    		}
    		c++;
    	}

    	if ((frecuencia == Frecuencia.getFrecuenciaSemanal().byteValue()) && (lapso.getPeriodoFin() == null)) 
    		lapso.setPeriodoFin(new Integer(periodos.size()));

    	return lapso;
    }
    
  	public static int getNumeroPeriodosFrecuenciaDiariaEnFrecuencia(byte frecuencia, byte frecuenciaContenida, int ano, int periodo) 
  	{
  		int numeroPeriodos = 0;
  		if (frecuenciaContenida == Frecuencia.getFrecuenciaDiaria().intValue()) 
  		{
  			if (frecuencia == Frecuencia.getFrecuenciaSemanal().intValue())
  				numeroPeriodos = 7;
  			else if (frecuencia == Frecuencia.getFrecuenciaQuincenal().intValue())
  				numeroPeriodos = 15;
  			else if (frecuencia == Frecuencia.getFrecuenciaMensual().intValue()) 
  			{
  				if ((periodo == 1) || (periodo == 3) || (periodo == 5) || (periodo == 7) || (periodo == 8) || (periodo == 10) || (periodo == 12))
  					numeroPeriodos = 31;
  				else if (periodo == 2) 
  				{
  					if (anoBisiesto(ano))
  						numeroPeriodos = 29;
  					else
  						numeroPeriodos = 28;
  				}
  				else
  					numeroPeriodos = 30;
  			}
  			else if (frecuencia == Frecuencia.getFrecuenciaBimensual().intValue()) 
  			{
  				if (periodo == 1) 
  				{
  					if (anoBisiesto(ano))
  						numeroPeriodos = 60;
  					else
  						numeroPeriodos = 59;
  				}
  				else if (periodo == 4)
  					numeroPeriodos = 62;
  				else
  					numeroPeriodos = 61;
  			}
  			else if (frecuencia == Frecuencia.getFrecuenciaTrimestral().intValue()) 
  			{
  				if (periodo == 1) 
  				{
  					if (anoBisiesto(ano))
  						numeroPeriodos = 91;
  					else
  						numeroPeriodos = 90;
  				}
  				else if (periodo == 3)
  					numeroPeriodos = 91;
  				else
  					numeroPeriodos = 92;
  			}
  			else if (frecuencia == Frecuencia.getFrecuenciaCuatrimestral().intValue()) 
  			{
  				if (periodo == 1) 
  				{
  					if (anoBisiesto(ano))
  						numeroPeriodos = 121;
  					else
  						numeroPeriodos = 120;
  				}
  				else if (periodo == 2)
  					numeroPeriodos = 123;
  				else
  					numeroPeriodos = 122;
  			}
  			else if (frecuencia == Frecuencia.getFrecuenciaSemestral().intValue()) 
  			{
  				if (periodo == 1) 
  				{
  					if (anoBisiesto(ano))
  						numeroPeriodos = 182;
  					else
  						numeroPeriodos = 181;
  				}
  				else
  					numeroPeriodos = 184;
  			}
  			else if (frecuencia == Frecuencia.getFrecuenciaAnual().intValue()) 
  			{
  				if (anoBisiesto(ano))
  					numeroPeriodos = 366;
  				else 
  				{
  					numeroPeriodos = 365;
  				}
  			}
  		}

  		return numeroPeriodos;
  	}
  	
  	private static boolean anoBisiesto(int ano) 
  	{
  		GregorianCalendar calendar = new GregorianCalendar();
  		if (calendar.isLeapYear(ano))
  			return true;
  		else
  			return false;
  	}
  	
    public static Integer getPeriodoCompatible(byte frecuenciaOriginal, int periodoOriginal, byte frecuenciaInsumo) 
    {
    	Integer periodoCompatible = null;

        if ((frecuenciaOriginal < 9) && (periodoOriginal < 13) && (frecuenciaInsumo < 9)) 
        {
        	if (frecuenciaOriginal == frecuenciaInsumo) 
        		return new Integer(periodoOriginal);
          
        	periodoCompatible = matrizPeriodos[frecuenciaOriginal][frecuenciaInsumo][periodoOriginal];
        }

        return periodoCompatible;
    }
    
    public static Integer[][][] getMatrizPeriodos() 
    {
    	Integer[][][] matriz = new Integer[9][9][13];

        matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][2] = new Integer("1");
        matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][4] = new Integer("2");
        matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][6] = new Integer("3");
        matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][8] = new Integer("4");
        matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][10] = new Integer("5");
        matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][12] = new Integer("6");

        matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][3] = new Integer("1");
        matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][6] = new Integer("2");
        matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][9] = new Integer("3");
        matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][12] = new Integer("4");

        matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][4] = new Integer("1");
        matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][8] = new Integer("2");
        matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][12] = new Integer("3");

        matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaSemestral().byteValue()][6] = new Integer("1");
        matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaSemestral().byteValue()][12] = new Integer("2");

        matriz[Frecuencia.getFrecuenciaMensual().byteValue()][Frecuencia.getFrecuenciaAnual().byteValue()][12] = new Integer("1");

        matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][1] = new Integer("2");
        matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][2] = new Integer("4");
        matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][3] = new Integer("6");
        matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][4] = new Integer("8");
        matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][5] = new Integer("10");
        matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][6] = new Integer("12");

        matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][3] = new Integer("2");
        matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][6] = new Integer("4");

        matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][2] = new Integer("1");
        matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][4] = new Integer("2");
        matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][6] = new Integer("3");

        matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaSemestral().byteValue()][3] = new Integer("1");
        matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaSemestral().byteValue()][6] = new Integer("2");

        matriz[Frecuencia.getFrecuenciaBimensual().byteValue()][Frecuencia.getFrecuenciaAnual().byteValue()][6] = new Integer("1");

        matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][1] = new Integer("3");
        matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][2] = new Integer("6");
        matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][3] = new Integer("9");
        matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][4] = new Integer("12");

        matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][2] = new Integer("3");
        matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][4] = new Integer("6");

        matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][4] = new Integer("3");

        matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaSemestral().byteValue()][2] = new Integer("1");
        matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaSemestral().byteValue()][4] = new Integer("2");

        matriz[Frecuencia.getFrecuenciaTrimestral().byteValue()][Frecuencia.getFrecuenciaAnual().byteValue()][4] = new Integer("1");

        matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][1] = new Integer("4");
        matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][2] = new Integer("8");
        matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][3] = new Integer("12");

        matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][1] = new Integer("2");
        matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][2] = new Integer("4");
        matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][3] = new Integer("6");

        matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][3] = new Integer("4");

        matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaSemestral().byteValue()][3] = new Integer("2");

        matriz[Frecuencia.getFrecuenciaCuatrimestral().byteValue()][Frecuencia.getFrecuenciaAnual().byteValue()][3] = new Integer("1");

        matriz[Frecuencia.getFrecuenciaSemestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][1] = new Integer("6");
        matriz[Frecuencia.getFrecuenciaSemestral().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][2] = new Integer("12");

        matriz[Frecuencia.getFrecuenciaSemestral().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][1] = new Integer("3");
        matriz[Frecuencia.getFrecuenciaSemestral().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][2] = new Integer("6");

        matriz[Frecuencia.getFrecuenciaSemestral().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][1] = new Integer("2");
        matriz[Frecuencia.getFrecuenciaSemestral().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][2] = new Integer("4");

        matriz[Frecuencia.getFrecuenciaSemestral().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][2] = new Integer("3");

        matriz[Frecuencia.getFrecuenciaSemestral().byteValue()][Frecuencia.getFrecuenciaAnual().byteValue()][2] = new Integer("1");

        matriz[Frecuencia.getFrecuenciaAnual().byteValue()][Frecuencia.getFrecuenciaMensual().byteValue()][1] = new Integer("12");

        matriz[Frecuencia.getFrecuenciaAnual().byteValue()][Frecuencia.getFrecuenciaBimensual().byteValue()][1] = new Integer("6");

        matriz[Frecuencia.getFrecuenciaAnual().byteValue()][Frecuencia.getFrecuenciaTrimestral().byteValue()][1] = new Integer("4");

        matriz[Frecuencia.getFrecuenciaAnual().byteValue()][Frecuencia.getFrecuenciaCuatrimestral().byteValue()][1] = new Integer("3");

        matriz[Frecuencia.getFrecuenciaAnual().byteValue()][Frecuencia.getFrecuenciaSemestral().byteValue()][1] = new Integer("2");

        return matriz;
    }
}
