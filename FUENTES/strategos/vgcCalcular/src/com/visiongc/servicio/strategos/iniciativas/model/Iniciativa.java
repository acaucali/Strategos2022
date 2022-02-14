/**
 * 
 */
package com.visiongc.servicio.strategos.iniciativas.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.visiongc.servicio.strategos.indicadores.model.Indicador;

/**
 * @author Kerwin
 *
 */
public class Iniciativa implements Serializable
{
	static final long serialVersionUID = 0L;
	
	private Long iniciativaId;
	private Long indicadorId;
	private Byte alerta;
	private Double porcentajeCompletado;
	private String fechaUltimaMedicion;
	private Indicador indicador;
	
	public Iniciativa()
	{
	}
	
	public Iniciativa(Long iniciativaId, Byte alerta, Double porcentajeCompletado, String fechaUltimaMedicion, Long indicadorId, Indicador indicador)
	{
		this.iniciativaId = iniciativaId;
		this.alerta = alerta;
		this.porcentajeCompletado = porcentajeCompletado;
		this.fechaUltimaMedicion = fechaUltimaMedicion;
		this.indicadorId = indicadorId;
	}
	
	public Long getIniciativaId()
	{
		return this.iniciativaId;
	}
	
	public void setIniciativaId(Long iniciativaId) 
	{
		this.iniciativaId = iniciativaId;
	}
	
	public Long getIndicadorId()
	{
		return this.indicadorId;
	}
	
	public void setIndicadorId(Long indicadorId) 
	{
		this.indicadorId = indicadorId;
	}

	public Indicador getIndicador()
	{
		return this.indicador;
	}
	
	public void setIndicador(Indicador indicador) 
	{
		this.indicador = indicador;
	}
	
	public Byte getAlerta() 
	{
		return this.alerta;
	}

	public void setAlerta(Byte alerta) 
	{
		this.alerta = alerta;
	}
	
	public Double getPorcentajeCompletado() 
	{
	    return this.porcentajeCompletado;
	}

	public void setPorcentajeCompletado(Double porcentajeCompletado) 
	{
	    this.porcentajeCompletado = porcentajeCompletado;
	}
	
	public String getFechaUltimaMedicion() 
	{
	    return this.fechaUltimaMedicion;
	}

	public void setFechaUltimaMedicion(String fechaUltimaMedicion) 
	{
		this.fechaUltimaMedicion = fechaUltimaMedicion;
	}
	
	public int compareTo(Object o) 
	{
		Iniciativa or = (Iniciativa)o;
    	return getIniciativaId().compareTo(or.getIniciativaId());
  	}

  	public String toString() 
  	{
	  return new ToStringBuilder(this).append("iniciativaId", getIniciativaId()).toString();
  	}

  	public boolean equals(Object obj) 
  	{
  		if (this == obj)
  			return true;
  		if (obj == null)
  			return false;
  		if (getClass() != obj.getClass())
  			return false;
  		
  		Iniciativa other = (Iniciativa)obj;
  		if (this.iniciativaId == null) 
  		{
  			if (other.iniciativaId != null)
  				return false;
  		} 
  		else if (!this.iniciativaId.equals(other.iniciativaId))
  			return false;
    	return true;
  	}
}
