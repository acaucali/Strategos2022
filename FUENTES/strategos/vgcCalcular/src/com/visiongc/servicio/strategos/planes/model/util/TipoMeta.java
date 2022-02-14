package com.visiongc.servicio.strategos.planes.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.servicio.web.importar.util.VgcResourceManager;

public class TipoMeta implements Serializable
{
	static final long serialVersionUID = 0L;
	private static final byte TIPO_META_PARCIAL = 1;
	private static final byte TIPO_META_ANUAL = 2;
	private static final byte TIPO_META_VALOR_INICIAL = 3;
	private static final byte TIPO_META_PARCIAL_SUPERIOR = 1;
	private static final byte TIPO_META_PARCIAL_INFERIOR = 4;
	
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

	public static Byte getTipoMetaParcial() 
	{
		return new Byte((byte) TIPO_META_PARCIAL);
	}

	public static Byte getTipoMetaAnual() 
	{
		return new Byte((byte) TIPO_META_ANUAL);
	}

	public static Byte getTipoMetaValorInicial() 
	{
		return new Byte((byte) TIPO_META_VALOR_INICIAL);
	}

	public static Byte getTipoMetaParcialSuperior() 
	{
		return new Byte((byte) TIPO_META_PARCIAL_SUPERIOR);
	}

	public static Byte getTipoMetaParcialInferior() 
	{
		return new Byte((byte) TIPO_META_PARCIAL_INFERIOR);
	}

	public static List<TipoMeta> getTipos() 
	{
		return getTipos(null);
	}

	public static List<TipoMeta> getTipos(VgcMessageResources messageResources) 
	{
		if (messageResources == null) 
			messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

		List<TipoMeta> tipos = new ArrayList<TipoMeta>();
		TipoMeta tipo = new TipoMeta();
		tipo.tipoId = 1;
		tipo.nombre = messageResources.getResource("plan.meta.tipo.parcial");
		tipos.add(tipo);
    
		tipo = new TipoMeta();
		tipo.tipoId = 2;
		tipo.nombre = messageResources.getResource("plan.meta.tipo.anual");
		tipos.add(tipo);
    
		tipo = new TipoMeta();
		tipo.tipoId = 3;
		tipo.nombre = messageResources.getResource("plan.meta.tipo.valorinicial");
		tipos.add(tipo);
    
		tipo = new TipoMeta();
		tipo.tipoId = 1;
		tipo.nombre = messageResources.getResource("plan.meta.tipo.superior");
		tipos.add(tipo);
    
		tipo = new TipoMeta();
		tipo.tipoId = 4;
		tipo.nombre = messageResources.getResource("plan.meta.tipo.inferior");
		tipos.add(tipo);
    
		return tipos;
	}

	public static String getNombre(byte tipo) 
	{
		String nombre = "";
		VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
		if (tipo == 1) 
			nombre = messageResources.getResource("plan.meta.tipo.parcial");
		if (tipo == 2) 
			nombre = messageResources.getResource("plan.meta.tipo.anual");
		if (tipo == 3) 
			nombre = messageResources.getResource("plan.meta.tipo.valorinicial");
		if (tipo == 1) 
			nombre = messageResources.getResource("plan.meta.tipo.superior");
		if (tipo == 4) 
			nombre = messageResources.getResource("plan.meta.tipo.inferior");
    
		return nombre;
	}
}