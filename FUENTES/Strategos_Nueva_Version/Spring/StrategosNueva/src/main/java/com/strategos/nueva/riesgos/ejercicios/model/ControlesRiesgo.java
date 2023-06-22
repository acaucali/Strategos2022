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
@Table(name="ri_controles_riesgo") 
public class ControlesRiesgo implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(columnDefinition = "serial")
	private Long controlRiesgoId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "factorId", nullable = false)
	@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "controlesRiesgo" }, allowSetters = true)
	private FactoresRiesgo factor;
	
	@Size(max=150)
	@Column(nullable=false)
	private String control;
	
	@Size(max=500)
	@Column(nullable=true)
	private String descripcionControl;
	
	@Column(nullable=true)
	private int efectividadId;
	
	
	public Long getControlRiesgoId() {
		return controlRiesgoId;
	}

	public void setControlRiesgoId(Long controlRiesgoId) {
		this.controlRiesgoId = controlRiesgoId;
	}

	public FactoresRiesgo getFactor() {
		return factor;
	}

	public void setFactor(FactoresRiesgo factor) {
		this.factor = factor;
	}
	
	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public String getDescripcionControl() {
		return descripcionControl;
	}

	public void setDescripcionControl(String descripcionControl) {
		this.descripcionControl = descripcionControl;
	}

	public int getEfectividadId() {
		return efectividadId;
	}

	public void setEfectividadId(int efectividadId) {
		this.efectividadId = efectividadId;
	}

	private static final long serialVersionUID = 1L;
}
