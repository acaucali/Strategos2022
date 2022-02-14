/**
 * 
 */
package com.visiongc.servicio.web.importar.dal.util;

import java.util.Iterator;
import java.util.List;

/**
 * @author Kerwin
 *
 */
public class DalUtil 
{
	public String getCondicionConsulta(Object fieldValue, String operador) 
	{
		String valor = getValorCondicionConsulta(fieldValue);
		String condicion = null;
		if (operador.equals("like")) 
			condicion = " " + operador + " '" + valor.toLowerCase() + "'";
		else if (operador.equals("=")) 
		{
			if (fieldValue instanceof List) 
				operador = " in ";
			condicion = " " + operador + " " + valor;
		} 
		else if (operador.equals("!=")) 
		{
			if (fieldValue instanceof List) 
				operador = " not in ";
			condicion = " " + operador + " " + valor;
		}
		
		return condicion;
	}

	public String getValorCondicionConsulta(Object fieldValue) 
	{
		String valor = "";
		if (fieldValue instanceof String) 
			valor = (String) fieldValue;
		else if (fieldValue instanceof Byte) 
			valor = ((Byte) fieldValue).toString();
		else if (fieldValue instanceof Boolean) 
			valor = ((Boolean) fieldValue) ? "1" : "0"; //valor = ((Boolean) fieldValue).toString();
		else if (fieldValue instanceof Long) 
			valor = ((Long) fieldValue).toString();
		else if (fieldValue instanceof Double) 
			valor = ((Double) fieldValue).toString();
		else if (fieldValue instanceof List) 
		{
			for (Iterator<?> iter = ((List<?>) fieldValue).iterator(); iter.hasNext();) 
			{
				Object valorLista = iter.next();
				valor = valor + ", " + getValorCondicionConsulta(valorLista);
			}
			
			if (valor.length() > 1) 
				valor = "(" + valor.substring(2) + ")";
		}
		
		return valor;
	}
}
