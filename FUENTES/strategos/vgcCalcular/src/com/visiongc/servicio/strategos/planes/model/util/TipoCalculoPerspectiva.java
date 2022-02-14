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
public class TipoCalculoPerspectiva implements Serializable
{
	static final long serialVersionUID = 0L;
	private static final byte TIPO_CALCULO_PERSPECTIVA_MANUAL = 0;
	private static final byte TIPO_CALCULO_PERSPECTIVA_AUTOMATICO = 1;
	private byte tipoCalculoId;
	private String nombre;

	public byte getTipoCalculoId()
	{
		return this.tipoCalculoId;
	}

	public void setTipoCalculoId(byte tipoCalculoId) 
	{
		this.tipoCalculoId = tipoCalculoId;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public static Byte getTipoCalculoPerspectivaManual() 
	{
		return TIPO_CALCULO_PERSPECTIVA_MANUAL;
	}

	public static Byte getTipoCalculoPerspectivaAutomatico() 
	{
		return TIPO_CALCULO_PERSPECTIVA_AUTOMATICO;
	}

	public static List<TipoCalculoPerspectiva> getTiposCalculo() 
	{
		return getTiposCalculos(null);
	}

	public static List<TipoCalculoPerspectiva> getTiposCalculos(VgcMessageResources messageResources)
	{
		if (messageResources == null) 
			messageResources = VgcResourceManager.getMessageResources("Strategos");

		List<TipoCalculoPerspectiva> tiposCalculo = new ArrayList<TipoCalculoPerspectiva>();

		TipoCalculoPerspectiva tipoCalculoPerspectiva = new TipoCalculoPerspectiva();
		tipoCalculoPerspectiva.tipoCalculoId = 0;
		tipoCalculoPerspectiva.nombre = messageResources.getResource("tipocalculo.perspectiva.manual");
		tiposCalculo.add(tipoCalculoPerspectiva);
		
		tipoCalculoPerspectiva = new TipoCalculoPerspectiva();
		tipoCalculoPerspectiva.tipoCalculoId = 1;
		tipoCalculoPerspectiva.nombre = messageResources.getResource("tipocalculo.perspectiva.automatico");
		tiposCalculo.add(tipoCalculoPerspectiva);

		return tiposCalculo;
	}

	public static String getNombre(byte tipo) 
	{
		String nombre = "";

		VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");

		if (tipo == 0)
			nombre = messageResources.getResource("tipocalculo.perspectiva.manual");
		else if (tipo == 1) 
			nombre = messageResources.getResource("tipocalculo.perspectiva.automatico");

		return nombre;
	}
}