package com.strategos.riesgos.model.ejercicios.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="RI_Efecto_Riesgo")
public class EfectoRiesgo implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long efectoRiesgoId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "factorId", nullable = false)
	@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "efectoRiesgo" }, allowSetters = true)
	private FactoresRiesgo factor;
	
	@Size(max=150)
	@Column(nullable=false)
	private String efectoNombre;
	
	@Size(max=500)
	@Column(nullable=true)
	private String descripcionImpacto;
	
	@Column(nullable=false)
	private int tipoImpactoId;
	
	
	public Long getEfectoRiesgoId() {
		return efectoRiesgoId;
	}

	public void setEfectoRiesgoId(Long efectoRiesgoId) {
		this.efectoRiesgoId = efectoRiesgoId;
	}

	public FactoresRiesgo getFactor() {
		return factor;
	}

	public void setFactor(FactoresRiesgo factor) {
		this.factor = factor;
	}

	public String getEfectoNombre() {
		return efectoNombre;
	}

	public void setEfectoNombre(String efectoNombre) {
		this.efectoNombre = efectoNombre;
	}

	public String getDescripcionImpacto() {
		return descripcionImpacto;
	}

	public void setDescripcionImpacto(String descripcionImpacto) {
		this.descripcionImpacto = descripcionImpacto;
	}

	public int getTipoImpactoId() {
		return tipoImpactoId;
	}

	public void setTipoImpactoId(int tipoImpactoId) {
		this.tipoImpactoId = tipoImpactoId;
	}

	private static final long serialVersionUID = 1L;
}
