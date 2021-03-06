/**
 * 
 */
package com.visiongc.servicio.strategos.planes.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;

/**
 * @author Kerwin
 *
 */
public class TipoIndicadorEstado implements Serializable
{
	static final long serialVersionUID = 0L;
	private static final byte TIPO_INDICADOR_ESTADO_ANUAL = 0;
	private static final byte TIPO_INDICADOR_ESTADO_PARCIAL = 1;
	private static final byte TIPO_INDICADOR_ESTADO_ACTIVIDAD_MEDICION = 2;
	private static final byte TIPO_INDICADOR_ESTADO_ACTIVIDAD_META = 3;
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
	
	public static Byte getTipoIndicadorEstadoParcial() 
	{
		return TIPO_INDICADOR_ESTADO_PARCIAL;
	}
	
	public static Byte getTipoIndicadorEstadoAnual() 
	{
		return TIPO_INDICADOR_ESTADO_ANUAL;
	}
	
	public static Byte getTipoIndicadorEstadoActividadMedicion() 
	{
		return TIPO_INDICADOR_ESTADO_ACTIVIDAD_MEDICION;
	}
	
	public static Byte getTipoIndicadorEstadoActividadMeta() 
	{
		return TIPO_INDICADOR_ESTADO_ACTIVIDAD_META;
	}
	
	public static List<TipoIndicadorEstado> getTipos() 
	{
		return getTipos(null);
	}
	
	public static List<TipoIndicadorEstado> getTipos(VgcMessageResources messageResources) 
	{
		if (messageResources == null) 
			messageResources = VgcResourceManager.getMessageResources("Strategos");
		List<TipoIndicadorEstado> tipos = new ArrayList<TipoIndicadorEstado>();
		TipoIndicadorEstado tipo = new TipoIndicadorEstado();
		
		tipo.tipoId = 0;
		tipo.nombre = messageResources.getResource("indicador.tipo.estado.anual");
		tipos.add(tipo);
		tipo = new TipoIndicadorEstado();
		
		tipo.tipoId = 1;
		tipo.nombre = messageResources.getResource("indicador.tipo.estado.parcial");
		tipos.add(tipo);
		tipo = new TipoIndicadorEstado();
	  
		tipo.tipoId = 2;
		tipo.nombre = messageResources.getResource("indicador.tipo.estado.actividad.medicion");
		tipos.add(tipo);
		tipo = new TipoIndicadorEstado();
	  
		tipo.tipoId = 3;
		tipo.nombre = messageResources.getResource("indicador.tipo.estado.actividad.meta");
		tipos.add(tipo);
	  
		return tipos;
	}
	
	public static String getNombre(byte tipo) 
	{
		String nombre = "";
		VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");
		if (tipo == 0)
			nombre = messageResources.getResource("plan.indicadorestado.tipo.anual");
		else if (tipo == 1)
			nombre = messageResources.getResource("plan.indicadorestado.tipo.parcial");
		else if (tipo == 2)
			nombre = messageResources.getResource("plan.indicadorestado.tipo.actividad.medicion");
		else if (tipo == 3) 
			nombre = messageResources.getResource("plan.indicadorestado.tipo.actividad.meta");
		return nombre;
	}
}