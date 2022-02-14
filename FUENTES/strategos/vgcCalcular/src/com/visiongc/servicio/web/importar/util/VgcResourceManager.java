/**
 * 
 */
package com.visiongc.servicio.web.importar.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * @author Kerwin
 *
 */
public class VgcResourceManager 
{
	/** Mapa de Recursos de Lenguaje */
	private static Map<String, VgcMessageResources> messageResourcesMap = null;

	static 
	{
		try 
		{
			messageResourcesMap = new HashMap<String, VgcMessageResources>();
			getMessageResources("Framework");
		} 
		catch (Exception e) {}
	}

	public static VgcMessageResources getMessageResources() 
	{
		return VgcMessageResources.getVgcMessageResources();
	}

	/**
	 * 
	 * @param sufix
	 * @return
	 * 
	 *  (19/08/2012)
	 */
	public static VgcMessageResources getMessageResources(String sufix) 
	{
		VgcMessageResources vmr = (VgcMessageResources) messageResourcesMap.get(sufix);

		if (vmr == null) 
		{
			vmr = VgcMessageResources.getVgcMessageResources(sufix);
			messageResourcesMap.put(sufix, vmr);
		}
		
		return vmr;
	}

	/**
	 * Función que retorna un objeto de recursos de lenguaje por un nombre y
	 * tipo de nombre
	 * 
	 * Creado por: Kerwin Arias (15/11/2012)
	 * 
	 * @param nombre
	 * @param tipoNombre
	 * @return
	 */
	public static VgcMessageResources getMessageResources(String nombre, int tipoNombre) 
	{
		VgcMessageResources vmr = (VgcMessageResources) messageResourcesMap.get(nombre);

		if (vmr == null) 
		{
			vmr = VgcMessageResources.getVgcMessageResources(nombre, tipoNombre);
			messageResourcesMap.put(nombre, vmr);
		}
		
		return vmr;
	}

	/**
	 * 
	 * @param locale
	 * @param sufix
	 * @return
	 * 
	 *  (19/08/2012)
	 */
	public static VgcMessageResources getMessageResources(Locale locale, String sufix) 
	{
		VgcMessageResources vmr = (VgcMessageResources) messageResourcesMap.get(sufix + "_" + locale.getLanguage() + "_" + locale.getCountry());

		if (vmr == null) 
		{
			vmr = VgcMessageResources.getVgcMessageResources(locale, sufix);
			messageResourcesMap.put(sufix + "_" + locale.getLanguage() + "_" + locale.getCountry(), vmr);
		}
		
		return vmr;
	}

	public static String getResourceApp(String key) 
	{
		String argsReemplazo[] = new String[0];
		return getResourceApp(key, argsReemplazo);
	}

	public static String getResourceApp(String key, Locale locale) 
	{
		String argsReemplazo[] = new String[0];
		return getResourceApp(key, argsReemplazo, locale);
	}

	public static String getResourceApp(String key, String argsReemplazo[]) 
	{
		String recursoLenguaje = null;
		String recursoLenguajeFramework = null;
		String recursoLenguajeFrameworkWeb = null;
		int numParam = 0;

		for (Iterator<String> iter = messageResourcesMap.keySet().iterator(); iter.hasNext();) 
		{
			String clave = (String) iter.next();
			if (clave.indexOf("_") == -1) 
			{
				// Para evitar usar recursos de lenguaje con locale
				VgcMessageResources recursos = (VgcMessageResources) messageResourcesMap.get(clave);
				if (clave.indexOf("FrameworkWeb") > -1) 
				{
					try 
					{
						recursoLenguajeFrameworkWeb = recursos.getResource(key);
					} catch (Exception e) { }
				} 
				else if (clave.indexOf("Framework") > -1) 
				{
					try 
					{
						recursoLenguajeFramework = recursos.getResource(key);
					} catch (Exception e) { }
				} 
				else 
				{
					try 
					{
						recursoLenguaje = recursos.getResource(key);
					} catch (Exception e) { }
					
					if (recursoLenguaje != null) 
						break;
				}
			}
		}

		if (recursoLenguaje == null) 
		{
			if (recursoLenguajeFrameworkWeb != null) 
				recursoLenguaje = recursoLenguajeFrameworkWeb;
			else 
				recursoLenguaje = recursoLenguajeFramework;
		}

		if (recursoLenguaje != null) 
		{
			numParam = argsReemplazo.length;
			if (recursoLenguaje.indexOf("{") > 0) 
			{
				for (int i = 0; i < numParam; i++) 
					recursoLenguaje = recursoLenguaje.replaceAll("\\{" + i + "\\}", argsReemplazo[i]);
			}

		}
		
		return recursoLenguaje;
	}

	public static String getResourceApp(String key, String argsReemplazo[], Locale locale) 
	{
		String recursoLenguaje = null;
		String recursoLenguajeFramework = null;
		String recursoLenguajeFrameworkWeb = null;
		String strLocale = locale.getLanguage() + "_" + locale.getCountry();
		int numParam = 0;

		for (Iterator<String> iter = messageResourcesMap.keySet().iterator(); iter.hasNext();) 
		{
			String clave = (String) iter.next();
			if (clave.indexOf(strLocale) > -1) 
			{
				VgcMessageResources recursos = (VgcMessageResources) messageResourcesMap.get(clave);
				if (clave.indexOf("FrameworkWeb") > -1) 
				{
					try 
					{
						recursoLenguajeFrameworkWeb = recursos.getResource(key);
					} catch (Exception e) { }
				} 
				else if (clave.indexOf("Framework") > -1) 
				{
					try 
					{
						recursoLenguajeFramework = recursos.getResource(key);
					} catch (Exception e) { }
				} 
				else 
				{
					try 
					{
						recursoLenguaje = recursos.getResource(key);
					} catch (Exception e) { }
					
					if (recursoLenguaje != null) 
						break;
				}
			}
		}

		if (recursoLenguaje == null) {
			if (recursoLenguajeFrameworkWeb != null) {
				recursoLenguaje = recursoLenguajeFrameworkWeb;
			} else {
				recursoLenguaje = recursoLenguajeFramework;
			}
		}

		if (recursoLenguaje != null) 
		{
			numParam = argsReemplazo.length;
			if (recursoLenguaje.indexOf("{") > 0) 
			{
				for (int i = 0; i < numParam; i++) 
					recursoLenguaje = recursoLenguaje.replaceAll("\\{" + i + "\\}", argsReemplazo[i]);
			}
		} 
		else 
			recursoLenguaje = getResourceApp(key, argsReemplazo);

		return recursoLenguaje;
	}
}
