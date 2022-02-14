/**
 * 
 */
package com.visiongc.servicio.strategos.indicadores.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Kerwin
 *
 */
public class Medicion implements Serializable
{
	static final long serialVersionUID = 0L;

	private MedicionPK medicionId;
	private Double valor;
	private String valorString;
	private Boolean protegido;
	private SerieIndicador serieIndicador;

	public Medicion(MedicionPK medicionId, Double valor, Boolean protegido, SerieIndicador serieIndicador)
	{
		this.medicionId = medicionId;
		this.valor = valor;
		this.protegido = protegido;
		this.serieIndicador = serieIndicador;
	}

	public Medicion()
	{
	}

	public Medicion(MedicionPK medicionId, Double valor)
	{
		this.medicionId = medicionId;
		this.valor = valor;
	}

	public Medicion(MedicionPK medicionId, Double valor, Boolean protegido) 
	{
		this.medicionId = medicionId;
		this.valor = valor;
		this.protegido = protegido;
	}

	public MedicionPK getMedicionId() 
	{
		return this.medicionId;
	}

	public void setMedicionId(MedicionPK medicionId) 
	{
		this.medicionId = medicionId;
	}

	public String getId() 
	{
		String id = "";

		if (this.medicionId != null) 
		{
			id = this.medicionId.toString();
			id = id.substring(id.indexOf("[indicadorId"));
		}
		
		return id;
	}

	public Double getValor() 
	{
		return this.valor;
	}
	
	public Long getValorCualitativo() 
	{
		if (this.valor != null) 
		{
			return new Long(this.valor.intValue());
		}
		
		return null;
	}

	public void setValorCualitativo(Long valor)
	{
		if (this.valor != null)
			this.valor = new Double(valor.intValue());
		else
			this.valor = null;
	}

	public void setValor(Double valor)
	{
		this.valor = valor;
	}

	public Boolean getProtegido() 
	{
		return this.protegido;
	}

	public void setProtegido(Boolean protegido) 
	{
		this.protegido = protegido;
	}

	public String getValorString() 
	{
		return this.valorString;
	}

	public void setValorString(String valorString) 
	{
		this.valorString = valorString;
	}
	
	public SerieIndicador getSerieIndicador() 
	{
		return this.serieIndicador;
	}

	public void setSerieIndicador(SerieIndicador serieIndicador) 
	{
		this.serieIndicador = serieIndicador;
	}

	public boolean equals(Object other) 
	{
		if (this == other) return true;
		if (!(other instanceof Medicion)) return false;
		Medicion castOther = (Medicion)other;
		return new EqualsBuilder().append(getMedicionId(), castOther.getMedicionId()).isEquals();
	}

	public String toString() 
	{
		return new ToStringBuilder(this).append("id", getMedicionId()).toString();
	}
}