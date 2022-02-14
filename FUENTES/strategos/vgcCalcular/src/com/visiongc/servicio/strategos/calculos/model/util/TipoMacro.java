/**
 * 
 */
package com.visiongc.servicio.strategos.calculos.model.util;

import java.util.ArrayList;
import java.util.List;
import com.visiongc.commons.util.VgcMessageResources;

/**
 * @author Kerwin
 *
 */
public class TipoMacro 
{
	private static final byte TIPO_MACRO_SIN_CAMBIO_PERIODO = 0;
	private static final byte TIPO_MACRO_MISMO_PERIODO_ANO_ANTERIOR = 1;
	private static final byte TIPO_MACRO_PERIODO_CIERRE_ANO_ANTERIOR = 2;
	private static final byte TIPO_MACRO_PRIMER_PERIODO_ANO = 3;
	private static final byte TIPO_MACRO_SUMATORIA = 4;
	private byte tipoMacroId;
	private String nombre;

	public static TipoMacro getTipoMacro(byte tipo, VgcMessageResources messageResources)
	{
		TipoMacro tipoMacro = new TipoMacro();

		if (tipo == TIPO_MACRO_SIN_CAMBIO_PERIODO) 
		{
			tipoMacro.tipoMacroId = TIPO_MACRO_SIN_CAMBIO_PERIODO;
			tipoMacro.nombre = messageResources.getResource("tipomacro.sincambioperiodo");
		}
		if (tipo == TIPO_MACRO_MISMO_PERIODO_ANO_ANTERIOR) 
		{
			tipoMacro.tipoMacroId = TIPO_MACRO_MISMO_PERIODO_ANO_ANTERIOR;
			tipoMacro.nombre = messageResources.getResource("tipomacro.mismoperiodoanoanterior");
		}
		if (tipo == TIPO_MACRO_PERIODO_CIERRE_ANO_ANTERIOR) 
		{
			tipoMacro.tipoMacroId = TIPO_MACRO_PERIODO_CIERRE_ANO_ANTERIOR;
			tipoMacro.nombre = messageResources.getResource("tipomacro.periodocierreanoanterior");
		}
		if (tipo == TIPO_MACRO_PRIMER_PERIODO_ANO) 
		{
			tipoMacro.tipoMacroId = TIPO_MACRO_PRIMER_PERIODO_ANO;
			tipoMacro.nombre = messageResources.getResource("tipomacro.primerperiodoano");
		}
		if (tipo == TIPO_MACRO_SUMATORIA) 
		{
			tipoMacro.tipoMacroId = TIPO_MACRO_SUMATORIA;
			tipoMacro.nombre = messageResources.getResource("tipomacro.sumatoria");
		}

		return tipoMacro;
	}

	public static List<TipoMacro> getTiposMacro(VgcMessageResources messageResources)
	{
		List<TipoMacro> tiposMacro = new ArrayList<TipoMacro>();

		TipoMacro tipoMacro = new TipoMacro();
		tipoMacro.tipoMacroId = TIPO_MACRO_SIN_CAMBIO_PERIODO;
		tipoMacro.nombre = messageResources.getResource("tipomacro.sincambioperiodo");
		tiposMacro.add(tipoMacro);

		tipoMacro = new TipoMacro();
		tipoMacro.tipoMacroId = TIPO_MACRO_MISMO_PERIODO_ANO_ANTERIOR;
		tipoMacro.nombre = messageResources.getResource("tipomacro.mismoperiodoanoanterior");
		tiposMacro.add(tipoMacro);

		tipoMacro = new TipoMacro();
		tipoMacro.tipoMacroId = TIPO_MACRO_PERIODO_CIERRE_ANO_ANTERIOR;
		tipoMacro.nombre = messageResources.getResource("tipomacro.periodocierreanoanterior");
		tiposMacro.add(tipoMacro);

		tipoMacro = new TipoMacro();
		tipoMacro.tipoMacroId = TIPO_MACRO_PRIMER_PERIODO_ANO;
		tipoMacro.nombre = messageResources.getResource("tipomacro.primerperiodoano");
		tiposMacro.add(tipoMacro);
		
		tipoMacro = new TipoMacro();
		tipoMacro.tipoMacroId = TIPO_MACRO_SUMATORIA;
		tipoMacro.nombre = messageResources.getResource("tipomacro.sumatoria");
		tiposMacro.add(tipoMacro);

		return tiposMacro;
	}

	public static Byte getTipoMacroSinCambioPeriodo() 
	{
		return TIPO_MACRO_SIN_CAMBIO_PERIODO;
	}

	public static Byte getTipoMacroMismoPeriodoAnoAnterior() 
	{
		return TIPO_MACRO_MISMO_PERIODO_ANO_ANTERIOR;
	}

	public static Byte getTipoMacroPeriodoCierreAnoAnterior() 
	{
		return TIPO_MACRO_PERIODO_CIERRE_ANO_ANTERIOR;
	}

	public static Byte getTipoMacroPrimerPeriodoAno() 
	{
		return TIPO_MACRO_PRIMER_PERIODO_ANO;
	}

	public static Byte getTipoMacroSumatoria() 
	{
		return TIPO_MACRO_SUMATORIA;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public byte getTipoMacroId() 
	{
		return this.tipoMacroId;
	}

	public void setTipoMacroId(byte tipoMacroId) 
	{
		this.tipoMacroId = tipoMacroId;
	}
}
