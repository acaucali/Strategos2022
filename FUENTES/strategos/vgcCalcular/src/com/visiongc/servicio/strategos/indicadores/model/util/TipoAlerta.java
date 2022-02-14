/**
 * 
 */
package com.visiongc.servicio.strategos.indicadores.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kerwin
 *
 */
public class TipoAlerta implements Serializable
{
	static final long serialVersionUID = 0L;
	private static final byte TIPO_ALERTA_PORCENTAJE = 0;
	private static final byte TIPO_ALERTA_VALOR_ABSOLUTO_MAGNITUD = 1;
	private static final byte TIPO_ALERTA_VALOR_ABSOLUTO_INDICADOR = 2;
	private byte tipoAlertaId;
	private String nombre;

	public byte getTipoAlertaId()
	{
		return this.tipoAlertaId;
	}

	public void setTipoAlertaId(byte tipoAlertaId) 
	{
		this.tipoAlertaId = tipoAlertaId;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public static Byte getTipoAlertaPorcentaje() 
	{
		return new Byte((byte) 0);
	}

	public static Byte getTipoAlertaValorAbsolutoMagnitud() 
	{
		return new Byte((byte) 1);
	}

	public static Byte getTipoAlertaValorAbsolutoIndicador() 
	{
		return new Byte((byte) 2);
	}
}