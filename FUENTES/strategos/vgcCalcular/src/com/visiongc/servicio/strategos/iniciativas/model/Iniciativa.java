/**
 * 
 */
package com.visiongc.servicio.strategos.iniciativas.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.visiongc.app.strategos.iniciativas.model.MemoIniciativa;
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
	
	private Long organizacionId;
	private String codigoIniciativa;
	private String nombre;
	private String memoIniciativa;
	private Long proyectoId;
	private String anioFormulacion;
	private Byte frecuencia;
	private Byte tipoMedicion;
	private Double alertaZonaVerde;
	private Double alertaZonaAmarilla;
	private Boolean crearCuentas;
	private Long unidadMedida;
	
	

	public Iniciativa()
	{
	}
	
	public Iniciativa(Long iniciativaId, Byte alerta, Double porcentajeCompletado, String fechaUltimaMedicion, Long indicadorId, Indicador indicador, String codigoIniciativa)
	{
		this.iniciativaId = iniciativaId;
		this.alerta = alerta;
		this.porcentajeCompletado = porcentajeCompletado;
		this.fechaUltimaMedicion = fechaUltimaMedicion;
		this.indicadorId = indicadorId;
		this.codigoIniciativa = codigoIniciativa;
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

	public String getCodigoIniciativa() {
		return codigoIniciativa;
	}

	public void setCodigoIniciativa(String codigoIniciativa) {
		this.codigoIniciativa = codigoIniciativa;
	}
	
	public Long getOrganizacionId() {
		return organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMemoIniciativa() {
		return memoIniciativa;
	}

	public void setMemoIniciativa(String memoIniciativa) {
		this.memoIniciativa = memoIniciativa;
	}

	public Long getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(Long proyectoId) {
		this.proyectoId = proyectoId;
	}

	public String getAnioFormulacion() {
		return anioFormulacion;
	}

	public void setAnioFormulacion(String anioFormulacion) {
		this.anioFormulacion = anioFormulacion;
	}

	public Byte getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Byte frecuencia) {
		this.frecuencia = frecuencia;
	}

	public Byte getTipoMedicion() {
		return tipoMedicion;
	}

	public void setTipoMedicion(Byte tipoMedicion) {
		this.tipoMedicion = tipoMedicion;
	}

	public Double getAlertaZonaVerde() {
		return alertaZonaVerde;
	}

	public void setAlertaZonaVerde(Double alertaZonaVerde) {
		this.alertaZonaVerde = alertaZonaVerde;
	}

	public Double getAlertaZonaAmarilla() {
		return alertaZonaAmarilla;
	}

	public void setAlertaZonaAmarilla(Double alertaZonaAmarilla) {
		this.alertaZonaAmarilla = alertaZonaAmarilla;
	}
	
	public Boolean getCrearCuentas() {
		return crearCuentas;
	}

	public void setCrearCuentas(Boolean crearCuentas) {
		this.crearCuentas = crearCuentas;
	}

	public Long getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(Long unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
}
