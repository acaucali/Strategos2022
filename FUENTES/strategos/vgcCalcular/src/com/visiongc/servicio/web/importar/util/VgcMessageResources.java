/**
 * 
 */
package com.visiongc.servicio.web.importar.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Kerwin
 *
 */
public class VgcMessageResources 
{
	private ResourceBundle messages;

	public final static int TIPO_NOMBRE_NOMBRE_COMPLETO = 1;
	public final static int TIPO_NOMBRE_PREFIJO = 2;
	public final static int TIPO_NOMBRE_SUFIJO = 3;

	public VgcMessageResources(ResourceBundle resourceBundle) 
	{
		this.messages = resourceBundle;
	}

	public static VgcMessageResources getVgcMessageResources() 
	{
		ResourceBundle resourceBundle = ResourceBundle.getBundle("MessageResources");
		if (resourceBundle != null) 
		{
			VgcMessageResources vmr = new VgcMessageResources(resourceBundle);
			return vmr;
		} 
		else 
			return null;
	}

	public static VgcMessageResources getVgcMessageResources(String sufijo) 
	{
		ResourceBundle resourceBundle = ResourceBundle.getBundle("MessageResources" + sufijo);
		if (resourceBundle != null) 
		{
			VgcMessageResources vmr = new VgcMessageResources(resourceBundle);
			return vmr;
		} 
		else 
			return null;
	}

	/**
	 * Creado por: Kerwin Arias (15/11/2012)
	 * 
	 * @param nombre
	 * @param tipoNombre
	 */
	public static VgcMessageResources getVgcMessageResources(String nombre, int tipoNombre) 
	{
		ResourceBundle resourceBundle = ResourceBundle.getBundle(getNombreArchivoRecursos(nombre, tipoNombre));
		if (resourceBundle != null) 
		{
			VgcMessageResources vmr = new VgcMessageResources(resourceBundle);
			return vmr;
		} 
		else 
			return null;
	}

	public static VgcMessageResources getVgcMessageResources(String nombre, int tipoNombre, Locale locale) 
	{
		ResourceBundle resourceBundle = ResourceBundle.getBundle(getNombreArchivoRecursos(nombre, tipoNombre), locale);
		if ((resourceBundle != null) && (resourceBundle.getLocale().getCountry().equals(locale.getCountry()))) 
		{
			VgcMessageResources vmr = new VgcMessageResources(resourceBundle);
			return vmr;
		} 
		else 
			return null;
	}

	private static String getNombreArchivoRecursos(String nombre, int tipoNombre) 
	{
		String nombreArchivoRecursos = "MessageResources";
		if (tipoNombre == TIPO_NOMBRE_NOMBRE_COMPLETO) 
			nombreArchivoRecursos = nombre;
		else if (tipoNombre == TIPO_NOMBRE_PREFIJO) 
			nombreArchivoRecursos = nombre + "MessageResources";
		else if (tipoNombre == TIPO_NOMBRE_SUFIJO) 
			nombreArchivoRecursos = "MessageResources" + nombre;

		return nombreArchivoRecursos;
	}

	public static VgcMessageResources getVgcMessageResources(Locale locale) 
	{
		ResourceBundle resourceBundle = ResourceBundle.getBundle("MessageResources", locale);
		if (resourceBundle != null) 
		{
			VgcMessageResources vmr = new VgcMessageResources(resourceBundle);
			return vmr;
		} 
		else 
			return null;
	}

	public static VgcMessageResources getVgcMessageResources(Locale locale, String sufix) 
	{
		ResourceBundle resourceBundle = ResourceBundle.getBundle("MessageResources" + sufix, locale);
		if (resourceBundle != null) 
		{
			VgcMessageResources vmr = new VgcMessageResources(resourceBundle);
			return vmr;
		} 
		else 
			return null;
	}

	public String getResource(String key) 
	{
		String argsReemplazo[] = new String[0];
		return getResource(key, argsReemplazo);
	}

	public String getResource(String key, String argsReemplazo[]) 
	{

		String resource = messages.getString(key);
		int numParam = argsReemplazo.length;
		if (resource.indexOf("{") > 0) 
		{
			for (int i = 0; i < numParam; i++) 
				resource = resource.replaceAll("\\{" + i + "\\}", argsReemplazo[i]);
		}

		return resource;
	} // --- fin de la funcion

	/**
	 * 
	 * Creado por: Kerwin Arias (24/04/2012)
	 * 
	 * @return
	 */
	public ResourceBundle getResourceBundle() 
	{
		return messages;
	}
}
