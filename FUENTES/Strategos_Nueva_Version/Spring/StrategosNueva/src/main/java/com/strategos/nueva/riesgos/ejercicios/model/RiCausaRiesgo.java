package com.strategos.nueva.riesgos.ejercicios.model;

import java.io.Serializable;

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
@Table(name="ri_causa_riesgo")
public class RiCausaRiesgo implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(columnDefinition = "serial")
	private Long causaRiesgoId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "factorId", nullable = false)
	@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "causaRiesgo" }, allowSetters = true)
	private FactoresRiesgo factor;
	
	@Size(max=150)
	@Column(nullable=false)
	private String causa;
	
	
	@Size(max=500)
	@Column(nullable=true)
	private String descripcionCausa;
	
	@Column(nullable=false)
	private int probabilidadId;
	
	

	public String getCausa() {
		return causa;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

	public Long getCausaRiesgoId() {
		return causaRiesgoId;
	}

	public void setCausaRiesgoId(Long causaRiesgoId) {
		this.causaRiesgoId = causaRiesgoId;
	}

	public FactoresRiesgo getFactor() {
		return factor;
	}

	public void setFactor(FactoresRiesgo factor) {
		this.factor = factor;
	}

	public String getDescripcionCausa() {
		return descripcionCausa;
	}

	public void setDescripcionCausa(String descripcionCausa) {
		this.descripcionCausa = descripcionCausa;
	}

	public int getProbabilidadId() {
		return probabilidadId;
	}

	public void setProbabilidadId(int probabilidadId) {
		this.probabilidadId = probabilidadId;
	}
	
	private static final long serialVersionUID = 1L;
}
