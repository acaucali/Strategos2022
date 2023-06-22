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
@Table(name="iniciativa_estatus")
public class IniciativaEstatusStrategos implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Size(max=50)
	@Column(nullable=true, name="nombre")
	private String nombre;
	
	@Column(nullable=true)
	private Double porcentajeInicial;
	
	@Column(nullable=true)
	private Double porcentajeFinal;
	
	@Column(nullable=true)
	private Boolean sistema;
	
	@Column(nullable=true)
	private Boolean bloquearMedicion;
	
	@Column(nullable=true)
	private Boolean bloquearIndicadores;
	
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPorcentajeInicial() {
		return porcentajeInicial;
	}

	public void setPorcentajeInicial(Double porcentajeInicial) {
		this.porcentajeInicial = porcentajeInicial;
	}

	public Double getPorcentajeFinal() {
		return porcentajeFinal;
	}

	public void setPorcentajeFinal(Double porcentajeFinal) {
		this.porcentajeFinal = porcentajeFinal;
	}

	public Boolean getSistema() {
		return sistema;
	}

	public void setSistema(Boolean sistema) {
		this.sistema = sistema;
	}

	public Boolean getBloquearMedicion() {
		return bloquearMedicion;
	}

	public void setBloquearMedicion(Boolean bloquearMedicion) {
		this.bloquearMedicion = bloquearMedicion;
	}

	public Boolean getBloquearIndicadores() {
		return bloquearIndicadores;
	}

	public void setBloquearIndicadores(Boolean bloquearIndicadores) {
		this.bloquearIndicadores = bloquearIndicadores;
	}


	private static final long serialVersionUID = 1L;
}
