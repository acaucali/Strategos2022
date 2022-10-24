package com.strategos.nueva.bancoproyecto.strategos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="serie_tiempo")
public class SerieTiempoStrategos implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long serieId;
	
	@Size(max=50)
	@Column(nullable=true)
	private String nombre;
	
	@Column(nullable=true)
	private Boolean fija;
	
	@Column(nullable=true)
	private Boolean oculta;
	
	@Size(max=5)
	@Column(nullable=true)
	private String identificador;
	
	@Column(nullable=true)
	private Boolean preseleccionada;
	
		
	public Long getSerieId() {
		return serieId;
	}

	public void setSerieId(Long serieId) {
		this.serieId = serieId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getFija() {
		return fija;
	}

	public void setFija(Boolean fija) {
		this.fija = fija;
	}

	public Boolean getOculta() {
		return oculta;
	}

	public void setOculta(Boolean oculta) {
		this.oculta = oculta;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public Boolean getPreseleccionada() {
		return preseleccionada;
	}

	public void setPreseleccionada(Boolean preseleccionada) {
		this.preseleccionada = preseleccionada;
	}



	private static final long serialVersionUID = 1L;
	
}
