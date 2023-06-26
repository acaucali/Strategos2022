package com.visiongc.app.strategos.web.configuracion;

import com.visiongc.commons.util.xmldata.XmlNodo;
import com.visiongc.framework.web.configuracion.VgcConfiguracionesBaseWeb;

public class StrategosWebConfiguracionesBase extends VgcConfiguracionesBaseWeb
{
	@Override
	public XmlNodo getConfiguracionBase(String nombreObjeto)
	{
		XmlNodo configuracion = null;
		return configuracion;
	}

	@Override
	public Object getObjetoConfiguracionBase(String nombreObjeto)
	{
		return null;
	}
}