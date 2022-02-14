package com.visiongc.servicio.strategos.seriestiempo.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.servicio.web.importar.util.VgcResourceManager;

public class SerieTiempo implements Serializable, Comparable<Object>
{
	static final long serialVersionUID = 0L;
	
	private static final long TIPO_SERIE_REAL = 0L;
	private static final long TIPO_SERIE_PROGRAMADO = 1L;
	private static final long TIPO_SERIE_MINIMO = 2L;
	private static final long TIPO_SERIE_MAXIMO = 3L;
	private static final long TIPO_SERIE_META_PLAN = 7L;
	private static final long TIPO_SERIE_VALOR_INICIAL_PLAN = 8L;
	/*private static final String IDENT_SERIE_REAL = "REAL";
	private static final String IDENT_SERIE_MINIMO = "MIN";
	private static final String IDENT_SERIE_MAXIMO = "MAX";
	private static final String IDENT_SERIE_PROGRAMADO = "PROG";
	private static final String IDENT_SERIE_META_PLAN = "Meta";
	private static final String IDENT_SERIE_VALOR_INICIAL_PLAN = "VALIN";
	*/
	private Long serieId;
	private String nombre;
	private Boolean fija;
	private Boolean oculta;
	private String identificador;
	private Boolean preseleccionada;

	public SerieTiempo(Long serieId, String nombre, Boolean fija, Boolean oculta, String identificador)
	{
		this.serieId = serieId;
		this.nombre = nombre;
		this.fija = fija;
		this.oculta = oculta;
		this.identificador = identificador;
	}

	public SerieTiempo()
	{
	}

	public SerieTiempo(Long serieId, String nombre)
	{
		this.serieId = serieId;
		this.nombre = nombre;
	}

	public static Long getSerieRealId() 
	{
		return new Long(TIPO_SERIE_REAL);
	}

	public static Long getSerieProgramadoId() 
	{
		return new Long(TIPO_SERIE_PROGRAMADO);
	}

	public static Long getSerieMinimoId() 
	{
		return new Long(TIPO_SERIE_MINIMO);
	}

	public static Long getSerieMaximoId() 
	{
		return new Long(TIPO_SERIE_MAXIMO);
	}

	public static Long getSerieMetaId() 
	{
		return new Long(TIPO_SERIE_META_PLAN);
	}

	public static Long getSerieValorInicialId() 
	{
		return new Long(TIPO_SERIE_VALOR_INICIAL_PLAN);
	}

	public static SerieTiempo getSerieReal() 
	{
		VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
		return new SerieTiempo(new Long(TIPO_SERIE_REAL), messageResources.getResource("serietiempo.real"), new Boolean(true), new Boolean(false), "REAL");
	}

	public static SerieTiempo getSerieMinimo() 
	{
		VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
		return new SerieTiempo(new Long(TIPO_SERIE_MINIMO), messageResources.getResource("serietiempo.minimo"), new Boolean(true), new Boolean(false), "MIN");
	}

	public static SerieTiempo getSerieMaximo() 
	{
		VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
		return new SerieTiempo(new Long(TIPO_SERIE_MAXIMO), messageResources.getResource("serietiempo.maximo"), new Boolean(true), new Boolean(false), "MAX");
	}

	public static SerieTiempo getSerieProgramado() 
	{
		VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
		return new SerieTiempo(new Long(TIPO_SERIE_PROGRAMADO), messageResources.getResource("serietiempo.programado"), new Boolean(true), new Boolean(false), "PROG");
	}

	public static SerieTiempo getSerieMeta() 
	{
		VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
		return new SerieTiempo(new Long(TIPO_SERIE_META_PLAN), messageResources.getResource("serietiempo.meta"), new Boolean(true), new Boolean(false), "Meta");
	}

	public static SerieTiempo getSerieValorInicial() 
	{
		VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
		return new SerieTiempo(new Long(TIPO_SERIE_VALOR_INICIAL_PLAN), messageResources.getResource("serietiempo.valorinicial"), new Boolean(true), new Boolean(false), "VALIN");
	}

	public Long getSerieId() 
	{
		return this.serieId;
	}

	public void setSerieId(Long serieId) 
	{
		this.serieId = serieId;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public Boolean getFija() 
	{
		return this.fija;
	}

	public void setFija(Boolean fija) 
	{
		this.fija = fija;
	}

	public Boolean getOculta() 
	{
		return this.oculta;
	}

	public void setOculta(Boolean oculta) 
	{
		this.oculta = oculta;
	}

	public String getIdentificador() 
	{
		return this.identificador;
	}

	public void setIdentificador(String identificador) 
	{
		this.identificador = identificador;
	}

	public Boolean getPreseleccionada() 
	{
		return this.preseleccionada;
	}

	public void setPreseleccionada(Boolean preseleccionada) 
	{
		this.preseleccionada = preseleccionada;
	}

	public String toString() 
	{
		return new ToStringBuilder(this).append("serieId", getSerieId()).toString();
	}

	public int compareTo(Object o) 
	{
		SerieTiempo st = (SerieTiempo)o;
		
		return getSerieId().compareTo(st.getSerieId());
	}

	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SerieTiempo other = (SerieTiempo)obj;
		return new EqualsBuilder().append(getSerieId(), other.getSerieId()).isEquals();
	}
}