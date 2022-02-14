/**
 * 
 */
package com.visiongc.servicio.strategos.planes.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.visiongc.servicio.strategos.planes.model.util.TipoPerspectivaEstado;

/**
 * @author Kerwin
 *
 */
public class PerspectivaEstadoPK implements Serializable
{
	static final long serialVersionUID = 0L;
	private Long perspectivaId;
	private Byte tipo;
	private Integer ano;
	private Integer periodo;

	public Long getPerspectivaId()
	{
		return this.perspectivaId;
	}

	public void setPerspectivaId(Long perspectivaId) 
	{
		this.perspectivaId = perspectivaId;
	}
	
	public Byte getTipo() 
	{
		return this.tipo;
	}
	
	public String getTipoNombre() 
	{
		if (this.tipo == null) 
			return "";
		
		return TipoPerspectivaEstado.getNombre(this.tipo.byteValue());
	}
	
	public void setTipo(Byte tipo)
	{
		this.tipo = tipo;
	}
	
	public Integer getAno() 
	{
		return this.ano;
	}
	
	public void setAno(Integer ano) 
	{
		this.ano = ano;
	}
	
	public Integer getPeriodo() 
	{
		return this.periodo;
	}
	
	public void setPeriodo(Integer periodo) 
	{
		this.periodo = periodo;
	}
	
	public String toString() 
	{
		return new ToStringBuilder(this).append("perspectivaId", getPerspectivaId()).append("tipo", getTipo()).append("ano", getAno()).append("periodo", getPeriodo()).toString();
	}
	
	public boolean equals(Object other) 
	{
		if (this == other)
			return true;
	  
		if (!(other instanceof PerspectivaEstadoPK))
			return false;
	  
		PerspectivaEstadoPK castOther = (PerspectivaEstadoPK)other;
		
		return new EqualsBuilder().append(getPerspectivaId(), castOther.getPerspectivaId()).append(getTipo(), castOther.getTipo()).append(getAno(), castOther.getAno()).append(getPeriodo(), castOther.getPeriodo()).isEquals();
	}
	
	public int hashCode() 
	{
		return new HashCodeBuilder().append(getPerspectivaId()).append(getTipo()).append(getAno()).append(getPeriodo()).toHashCode();
	}
}