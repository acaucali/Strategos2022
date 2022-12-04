package com.strategos.nueva.bancoproyecto.ideas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="bp_iniciativas")
public class Iniciativa implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long iniciativaId;
	
	@Size(max=250)
	@Column(nullable=false)
	private String nombreIniciativa;
	
	@Size(max=2000)
	@Column(nullable=false)
	private String descripcion;
	
	@Column(nullable=true)
	private Byte frecuencia;
	
	@Column(nullable=true)
	private Double zonaVerde;
	
	@Column(nullable=true)
	private Double zonaAmarilla;
	
	@Size(max=10)
	@Column(nullable=true)
	private String fechaUltimaMedicion;
	
	@Column(nullable=true)
	private Byte alerta;
	
	@Column(nullable=true)
	private Double ultimaMedicion;
	
	@Column(nullable=true)
	private Double ultimoProgramado;
	
		
	public Long getIniciativaId() {
		return iniciativaId;
	}

	public void setIniciativaId(Long iniciativaId) {
		this.iniciativaId = iniciativaId;
	}

	public String getNombreIniciativa() {
		return nombreIniciativa;
	}

	public void setNombreIniciativa(String nombreIniciativa) {
		this.nombreIniciativa = nombreIniciativa;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Byte getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Byte frecuencia) {
		this.frecuencia = frecuencia;
	}

	public Double getZonaVerde() {
		return zonaVerde;
	}

	public void setZonaVerde(Double zonaVerde) {
		this.zonaVerde = zonaVerde;
	}

	public Double getZonaAmarilla() {
		return zonaAmarilla;
	}

	public void setZonaAmarilla(Double zonaAmarilla) {
		this.zonaAmarilla = zonaAmarilla;
	}
		
	public String getFechaUltimaMedicion() {
		return fechaUltimaMedicion;
	}

	public void setFechaUltimaMedicion(String fechaUltimaMedicion) {
		this.fechaUltimaMedicion = fechaUltimaMedicion;
	}

	public Byte getAlerta() {
		return alerta;
	}

	public void setAlerta(Byte alerta) {
		this.alerta = alerta;
	}

	public Double getUltimaMedicion() {
		return ultimaMedicion;
	}

	public void setUltimaMedicion(Double ultimaMedicion) {
		this.ultimaMedicion = ultimaMedicion;
	}

	public Double getUltimoProgramado() {
		return ultimoProgramado;
	}

	public void setUltimoProgramado(Double ultimoProgramado) {
		this.ultimoProgramado = ultimoProgramado;
	}



	private static final long serialVersionUID = 1L;
}
