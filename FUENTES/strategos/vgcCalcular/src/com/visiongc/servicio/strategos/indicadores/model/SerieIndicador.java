package com.visiongc.servicio.strategos.indicadores.model;

import com.visiongc.servicio.strategos.seriestiempo.model.SerieTiempo;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class SerieIndicador implements Serializable
{
	static final long serialVersionUID = 0L;
	private SerieIndicadorPK pk;
	private Byte naturaleza;
	private Date fechaBloqueo;
	private SerieTiempo serieTiempo;
	private Indicador indicador;
	private Set<?> formulas;
	private Set<?> mediciones;
	private Set<?> indicadoresCeldas;
	private String nombre;

	public SerieIndicador(SerieIndicadorPK pk, Byte naturaleza, SerieTiempo serieTiempo, Indicador indicador, Set<?> formulas, Set<?> mediciones, Set<?> indicadoresCeldas)
	{
		this.pk = pk;
		this.naturaleza = naturaleza;
		this.serieTiempo = serieTiempo;
		this.indicador = indicador;
		this.formulas = formulas;
		this.mediciones = mediciones;
		this.indicadoresCeldas = indicadoresCeldas;
	}

	public SerieIndicador()
	{
	}

	public SerieIndicador(SerieIndicadorPK pk)
	{
		this.pk = pk;
	}

	public SerieIndicadorPK getPk() 
	{
		return this.pk;
	}

	public void setPk(SerieIndicadorPK pk) 
	{
		this.pk = pk;
	}

	public Byte getNaturaleza() 
	{
		return this.naturaleza;
	}

	public void setNaturaleza(Byte naturaleza) 
	{
		this.naturaleza = naturaleza;
	}

	public Date getFechaBloqueo() 
	{
		return this.fechaBloqueo;
	}

	public void setFechaBloqueo(Date fechaBloqueo) 
	{
		this.fechaBloqueo = fechaBloqueo;
	}

	public SerieTiempo getSerieTiempo() 
	{
		return this.serieTiempo;
	}

	public void setSerieTiempo(SerieTiempo serieTiempo) 
	{
		this.serieTiempo = serieTiempo;
	}

	public Indicador getIndicador() 
	{
		return this.indicador;
	}

	public void setIndicador(Indicador indicador) 
	{
		this.indicador = indicador;
	}

	public Set getFormulas() 
	{
		return this.formulas;
	}

	public void setFormulas(Set<?> formulas) 
	{
		this.formulas = formulas;
	}

	public Set<?> getMediciones() 
	{
		return this.mediciones;
	}

	public void setMediciones(Set<?> mediciones) 
	{
		this.mediciones = mediciones;
	}

	public Set<?> getIndicadoresCeldas() 
	{
		return this.indicadoresCeldas;
	}

	public void setIndicadoresCeldas(Set<?> indicadoresCeldas) 
	{
		this.indicadoresCeldas = indicadoresCeldas;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public String toString() 
	{
		return new ToStringBuilder(this).append("pk", getPk()).toString();
	}
}