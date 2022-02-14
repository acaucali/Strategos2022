/**
 * 
 */
package com.visiongc.servicio.strategos.indicadores.model.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kerwin
 *
 */
public class TipoSuma 
{
	static final long serialVersionUID = 0L;
	
	private static final byte TIPO_SUMAR_MEDICIONES = 0;
	private static final byte TIPO_ULTIMO_PERIODO = 1;
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

	public static Byte getTipoSumaSumarMediciones() 
	{
		return new Byte((byte) 0);
	}

	public static Byte getTipoSumaUltimoPeriodo() 
	{
		return new Byte((byte) 1);
	}
}
