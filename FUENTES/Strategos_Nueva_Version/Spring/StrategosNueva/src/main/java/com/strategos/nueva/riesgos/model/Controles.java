package com.strategos.nueva.riesgos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="controles")
public class Controles implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(columnDefinition = "serial")
	private Long controlId;
	
	@Size(max=50)
	@Column(nullable=false)
	private String control;
	
	
	@Size(max=500)
	@Column(nullable=true)
	private String controlDescripcion;
	
	@Column(nullable=false)
	private int procesoId;
	
	
	
	public int getProcesoId() {
		return procesoId;
	}


	public void setProcesoId(int procesoId) {
		this.procesoId = procesoId;
	}


	public Long getControlId() {
		return controlId;
	}


	public void setControlId(Long controlId) {
		this.controlId = controlId;
	}


	public String getControl() {
		return control;
	}


	public void setControl(String control) {
		this.control = control;
	}


	public String getControlDescripcion() {
		return controlDescripcion;
	}


	public void setControlDescripcion(String controlDescripcion) {
		this.controlDescripcion = controlDescripcion;
	}


	private static final long serialVersionUID = 1L;
	
}	
