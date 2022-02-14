package com.visiongc.servicio.strategos.indicadores.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.servicio.web.importar.util.VgcResourceManager;

public class TipoMedicion implements Serializable
{
	static final long serialVersionUID = 0L;
	private static final byte TIPO_MEDICION_EN_PERIODO = 0;
	private static final byte TIPO_MEDICION_AL_PERIODO = 1;
	private byte tipoMedicionId;
	private String nombre;

	public byte getTipoMedicionId()
	{
		return this.tipoMedicionId;
	}

	public void setTipoMedicionId(byte tipoMedicionId) 
	{
		this.tipoMedicionId = tipoMedicionId;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public static Byte getTipoMedicionEnPeriodo() 
	{
		return new Byte((byte) 0);
	}

	public static Byte getTipoMedicionAlPeriodo() 
	{
		return new Byte((byte) 1);
	}

	public static List<TipoMedicion> getTipoMediciones() 
	{
		return getTipoMediciones(null);
	}

	public static List<TipoMedicion> getTipoMediciones(VgcMessageResources messageResources)
	{
		if (messageResources == null) 
			messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

		List<TipoMedicion> tipoMediciones = new ArrayList<TipoMedicion>();

		TipoMedicion tipoMedicion = new TipoMedicion();

		tipoMedicion.tipoMedicionId = 0;
		tipoMedicion.nombre = messageResources.getResource("tipomedicion.enelperiodo");
		tipoMediciones.add(tipoMedicion);

		tipoMedicion = new TipoMedicion();
		tipoMedicion.tipoMedicionId = 1;
		tipoMedicion.nombre = messageResources.getResource("tipomedicion.alperiodo");
		tipoMediciones.add(tipoMedicion);

		return tipoMediciones;
	}

	public static String getNombre(byte tipoMedicion) 
	{
		String nombre = "";
		
		VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

		if (tipoMedicion == 0) 
			nombre = messageResources.getResource("tipomedicion.enelperiodo");

		if (tipoMedicion == 1) 
			nombre = messageResources.getResource("tipomedicion.alperiodo");

		return nombre;
	}
}