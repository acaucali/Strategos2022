/**
 * 
 */
package com.visiongc.servicio.web.importar.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Kerwin
 *
 */
public class VgcFormatter 
{
	private static DecimalFormat formateadorNumero = iniFormateadorNumero();

	private static int numeroMinimoDecimalesPorDefecto = 2;
	private static int numeroMaximoDecimalesPorDefecto = 2;

	private static SimpleDateFormat formateadorFecha;

	public static String formatearNumero(Integer numero) 
	{
		Double parametro = null;
		if (numero != null) 
			parametro = new Double(numero.intValue());

		return formatearNumero(parametro, null, null, new Integer(0));
	}

	public static String formatearNumero(Long numero) 
	{
		Double parametro = null;
		if (numero != null) 
			parametro = new Double(numero.longValue());

		return formatearNumero(parametro, null, null, new Integer(0));
	}

	public static String formatearNumero(Double numero) 
	{
		return formatearNumero(numero, null, null, null);
	}

	public static String formatearNumero(Double numero, int numeroDecimales) 
	{
		return formatearNumero(numero, null, null, new Integer(numeroDecimales));
	}

	public static String formatearNumeroConPatron(Double numero, String patron) 
	{
		return formatearNumero(numero, patron, null, null);
	}

	public static String formatearNumero(Double numero, String resourceKeyPatron) 
	{
		return formatearNumero(numero, null, resourceKeyPatron, null);
	}

	public static String formatearNumero(Double numero, String patron, String resourceKey, Integer numeroDecimales) 
	{
		String numeroFormateado = null;
		if (numero != null) 
		{
			if (patron != null) 
			{
				DecimalFormat formateadorNumeroInterno = new DecimalFormat(patron);
				return formateadorNumeroInterno.format(numero);
			}
			if (resourceKey != null) 
			{
				DecimalFormat formateadorNumeroInterno = new DecimalFormat(VgcResourceManager.getResourceApp(resourceKey));
				return formateadorNumeroInterno.format(numero);
			}
			if (formateadorNumero == null) 
				inicializarFormateadorNumero();

			if (numeroDecimales != null) 
			{
				formateadorNumero.setMinimumFractionDigits(numeroDecimales.intValue());
				formateadorNumero.setMaximumFractionDigits(numeroDecimales.intValue());
			}

			numeroFormateado = formateadorNumero.format(numero);
			if (numeroDecimales != null) 
			{
				formateadorNumero.setMinimumFractionDigits(numeroMinimoDecimalesPorDefecto);
				formateadorNumero.setMaximumFractionDigits(numeroMaximoDecimalesPorDefecto);
			}
		}

		return numeroFormateado;
	}

	public static Double parsearNumeroFormateado(String numero) throws Exception 
	{
		return parsearNumeroFormateado(numero, null);
	}

	public static Double parsearNumeroFormateado(String numero, Integer numeroDecimales) throws Exception 
	{

		if (formateadorNumero == null) 
			inicializarFormateadorNumero();
		if (numero == null)
			return null;

		Double d = 0.00D;
		try 
		{

			if (numeroDecimales != null) 
			{
				formateadorNumero.setMinimumFractionDigits(numeroDecimales.intValue());
				formateadorNumero.setMaximumFractionDigits(numeroDecimales.intValue());
			}

			d = formateadorNumero.parse(numero).doubleValue();
			
			if (numeroDecimales != null) 
			{
				formateadorNumero.setMinimumFractionDigits(numeroMinimoDecimalesPorDefecto);
				formateadorNumero.setMaximumFractionDigits(numeroMaximoDecimalesPorDefecto);
			}
		} 
		catch (Exception e) 
		{
			throw e;
		}

		return d;
	}

	private static void inicializarFormateadorNumero() 
	{
		DecimalFormatSymbols simbolosFormato = new DecimalFormatSymbols();
		simbolosFormato.setGroupingSeparator(VgcResourceManager.getResourceApp("formato.numero.separadormiles").charAt(0));
		simbolosFormato.setDecimalSeparator(VgcResourceManager.getResourceApp("formato.numero.separadordecimales").charAt(0));
		formateadorNumero = new DecimalFormat(VgcResourceManager.getResourceApp("formato.numero"), simbolosFormato);
		numeroMaximoDecimalesPorDefecto = formateadorNumero.getMaximumFractionDigits();
		numeroMinimoDecimalesPorDefecto = formateadorNumero.getMinimumFractionDigits();
	}
	
	private static DecimalFormat iniFormateadorNumero()
	{
		DecimalFormatSymbols simbolosFormato = new DecimalFormatSymbols();
		simbolosFormato.setGroupingSeparator(VgcResourceManager.getResourceApp("formato.numero.separadormiles").charAt(0));
		simbolosFormato.setDecimalSeparator(VgcResourceManager.getResourceApp("formato.numero.separadordecimales").charAt(0));
		formateadorNumero = new DecimalFormat(VgcResourceManager.getResourceApp("formato.numero"), simbolosFormato);
		numeroMaximoDecimalesPorDefecto = formateadorNumero.getMaximumFractionDigits();
		numeroMinimoDecimalesPorDefecto = formateadorNumero.getMinimumFractionDigits();
		
		return formateadorNumero;
	}

	/**
	 * 
	 * Creado por: Kerwin Arias (24/04/2012)
	 * 
	 * @param fecha
	 * @param patron
	 * @return
	 */
	public static String formatearFecha(Date fecha, String patron) 
	{
		return formatearFecha(fecha, patron, null);
	}

	/**
	 * 
	 * Creado por: Kerwin Arias (26/11/2012)
	 * 
	 * @param fecha
	 * @param patron
	 * @param locale
	 * @return
	 */
	public static String formatearFecha(Date fecha, String patron, Locale locale) 
	{
		if (fecha == null) 
			return null;

		boolean esFechaLarga = false;
		if (patron.equals("formato.fecha.larga")) 
			esFechaLarga = true;

		if (patron.indexOf("formato.") > -1) 
			patron = VgcResourceManager.getResourceApp(patron);

		if (formateadorFecha == null) 
			formateadorFecha = new SimpleDateFormat(patron);
		else 
			formateadorFecha.applyPattern(patron);

		String str = formateadorFecha.format(fecha);

		if (esFechaLarga) 
		{
			String trozos[] = str.split(" ");
			if (trozos.length == 4) 
			{
				String de = VgcResourceManager.getResourceApp("formato.fecha.separador");
				str = trozos[0] + " " + trozos[1] + " " + de + " " + trozos[2] + " " + de + " " + trozos[3];
			}
		}

		return str;
	}
	
	public static String formatDate(Date d, String format)
	{
		String strFinal = "";

	    if (d != null) 
	    {
	    	if (format.compareTo("LONG_DATE") == 0) 
	    	{
	    		DateFormat dateFormatter = DateFormat.getDateInstance(0);
	    		strFinal = dateFormatter.format(d);
	    	}
	    	else if (format.compareTo("SHORT_DATE") == 0) 
	    	{
	    		DateFormat dateFormatter = DateFormat.getDateInstance(3);
	    		strFinal = dateFormatter.format(d);
	    	}
	    	else if (format.compareTo("TIME") == 0) 
	    	{
	    		SimpleDateFormat dateFormatterSimple = new SimpleDateFormat("HH:mm:ss");
	    		strFinal = String.valueOf(dateFormatterSimple.format(d));
	    	}
	    	else
	    	{
	    		SimpleDateFormat dateFormatterSimple = new SimpleDateFormat(format);
	    		strFinal = String.valueOf(dateFormatterSimple.format(d));
	    	}
	    }
	    	return strFinal;
	}
	
	public static boolean isFechaValida(String fechax) 
	{
		try 
		{
			SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		    formatoFecha.setLenient(false);
		    formatoFecha.parse(fechax);
		} 
		catch (Exception e) 
		{
			return false;
		}
		  
		return true;
	}
}