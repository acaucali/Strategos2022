/**
 * 
 */
package com.visiongc.servicio.strategos.planes.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.visiongc.commons.util.VgcFormatter;

/**
 * @author Kerwin
 *
 */
public class PerspectivaEstado implements Serializable
{
	static final long serialVersionUID = 0L;
	private PerspectivaEstadoPK pk;
	private Double estado;
	private Integer totalConValor;
	private Integer total;
	
	public PerspectivaEstadoPK getPk()
	{
		return this.pk;
	}
	
	public void setPk(PerspectivaEstadoPK pk) 
	{
		this.pk = pk;
	}
	
	public Double getEstado() 
	{
		return this.estado;
	}
	
	public String getEstadoFormateado() 
	{
		return VgcFormatter.formatearNumero(this.estado, 2);
	}
	
	public void setEstado(Double estado) 
	{
		this.estado = estado;
	}
	
	public Integer getTotalConValor() 
	{
		return this.totalConValor;
	}
	
	public void setTotalConValor(Integer totalConValor) 
	{
		this.totalConValor = totalConValor;
	}
	
	public Integer getTotal() 
	{
		return this.total;
	}
	
	public void setTotal(Integer total) 
	{
		this.total = total;
	}
	
	public boolean equals(Object other) 
	{
		if (this == other)
			return true;
		if (!(other instanceof PerspectivaEstado))
			return false;
		
		PerspectivaEstado castOther = (PerspectivaEstado)other;
	  
		return new EqualsBuilder().append(getPk(), castOther.getPk()).isEquals();
	}
	
	public String toString() 
	{
		return new ToStringBuilder(this).append("pk", getPk()).toString();
	}
}