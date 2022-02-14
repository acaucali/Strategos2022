package com.visiongc.servicio.strategos.indicadores.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.servicio.web.importar.util.VgcResourceManager;

public class TipoFuncionIndicador implements Serializable
{
	static final long serialVersionUID = 0L;
  
	private static final byte TIPO_FUNCION_NORMAL = 0;
	private static final byte TIPO_FUNCION_SEGUIMIENTO = 1;
	private static final byte TIPO_FUNCION_IMPUTACION = 2;
	private static final byte TIPO_FUNCION_PERSPECTIVA = 3;
	private static final byte TIPO_FUNCION_TOTAL_IMPUTACION = 4;
	private static final byte TIPO_FUNCION_TOTAL_INICIATIVA = 5;
	private byte tipoId;
	private String nombre;

	public byte getTipoId()
	{
		return this.tipoId;
	}

	public void setTipoId(byte tipoId) 
	{
		this.tipoId = tipoId;
	}
	
	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public static Byte getTipoFuncionNormal() 
	{
		return TIPO_FUNCION_NORMAL;
	}

	public static Byte getTipoFuncionSeguimiento() 
	{
		return TIPO_FUNCION_SEGUIMIENTO;
	}

	public static Byte getTipoFuncionImputacion() 
	{
		return TIPO_FUNCION_IMPUTACION;
	}

	public static Byte getTipoFuncionPerspectiva() 
	{
		return TIPO_FUNCION_PERSPECTIVA;
	}

	public static Byte getTipoFuncionTotalImputacion() 
	{
		return TIPO_FUNCION_TOTAL_IMPUTACION;
	}

	public static Byte getTipoFuncionTotalIniciativa() 
	{
		return TIPO_FUNCION_TOTAL_INICIATIVA;
	}

	public static List<TipoFuncionIndicador> getTipos() 
	{
		return getTipos(null);
	}

	public static List<TipoFuncionIndicador> getTipos(VgcMessageResources messageResources)
	{
		if (messageResources == null) 
			messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

		List<TipoFuncionIndicador> tipos = new ArrayList<TipoFuncionIndicador>();

		TipoFuncionIndicador tipo = new TipoFuncionIndicador();

		tipo.tipoId = TIPO_FUNCION_NORMAL;
		tipo.nombre = messageResources.getResource("indicador.tipofuncion.normal");
		tipos.add(tipo);
		
		tipo = new TipoFuncionIndicador();
		tipo.tipoId = TIPO_FUNCION_SEGUIMIENTO;
		tipo.nombre = messageResources.getResource("indicador.tipofuncion.seguimiento");
		tipos.add(tipo);
		
		tipo = new TipoFuncionIndicador();
		tipo.tipoId = TIPO_FUNCION_IMPUTACION;
		tipo.nombre = messageResources.getResource("indicador.tipofuncion.imputacion");
		tipos.add(tipo);
		
		tipo = new TipoFuncionIndicador();
		tipo.tipoId = TIPO_FUNCION_PERSPECTIVA;
		tipo.nombre = messageResources.getResource("indicador.tipofuncion.perspectiva");
		tipos.add(tipo);

		tipo = new TipoFuncionIndicador();
		tipo.tipoId = TIPO_FUNCION_TOTAL_IMPUTACION;
		tipo.nombre = messageResources.getResource("indicador.tipofuncion.totalimputacion");
		tipos.add(tipo);
		
		tipo = new TipoFuncionIndicador();
		tipo.tipoId = TIPO_FUNCION_TOTAL_INICIATIVA;
		tipo.nombre = messageResources.getResource("indicador.tipofuncion.totaliniciativa");
		tipos.add(tipo);

		return tipos;
	}

	public static String getNombre(byte tipo) 
	{
		String nombre = "";
		
		VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

		if (tipo == TIPO_FUNCION_NORMAL)
			nombre = messageResources.getResource("indicador.tipofuncion.normal");
		else if (tipo == TIPO_FUNCION_SEGUIMIENTO)
			nombre = messageResources.getResource("indicador.tipofuncion.seguimiento");
		else if (tipo == TIPO_FUNCION_IMPUTACION)
			nombre = messageResources.getResource("indicador.tipofuncion.imputacion");
		else if (tipo == TIPO_FUNCION_PERSPECTIVA)
			nombre = messageResources.getResource("indicador.tipofuncion.perspectiva");
		else if (tipo == TIPO_FUNCION_TOTAL_IMPUTACION)
			nombre = messageResources.getResource("indicador.tipofuncion.totalimputacion");
		else if (tipo == TIPO_FUNCION_TOTAL_INICIATIVA) 
			nombre = messageResources.getResource("indicador.tipofuncion.totaliniciativa");

		return nombre;
	}
}