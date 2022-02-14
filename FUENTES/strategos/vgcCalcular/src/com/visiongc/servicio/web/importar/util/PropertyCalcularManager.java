/**
 * 
 */
package com.visiongc.servicio.web.importar.util;

import java.util.Properties;

/**
 * @author Kerwin
 *
 */
public class PropertyCalcularManager 
{
	private static Properties defaultProps;
	
	public PropertyCalcularManager()
	{
		defaultProps = new Properties();
	}

	public String getProperty(String key, String def)
	{
		return defaultProps.getProperty(key, def);
	}
	
	public void setProperty(String key, String def)
	{
		defaultProps.put(key, def);
	}
	
	public PropertyCalcularManager Set(String[][] configuracion)
	{
		for(int f = 0; f<configuracion.length; f++)
			defaultProps.put(configuracion[f][0], configuracion[f][1]);
		
		return this;
	}
}
