package com.strategos.nueva.bancoproyecto.strategos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="efectividad_controles")
public class EfectividadControles {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(columnDefinition = "serial")
	private Long efectividadId;
	
	@Size(max=100)
	@Column(nullable=false)
	private String efectividad;
	
	@Column(nullable=false)
	private int efectividadPuntaje;

	public Long getEfectividadId() {
		return efectividadId;
	}

	public void setEfectividadId(Long efectividadId) {
		this.efectividadId = efectividadId;
	}

	public String getEfectividad() {
		return efectividad;
	}

	public void setEfectividad(String efectividad) {
		this.efectividad = efectividad;
	}

	public int getEfectividadPuntaje() {
		return efectividadPuntaje;
	}

	public void setEfectividadPuntaje(int efectividadPuntaje) {
		this.efectividadPuntaje = efectividadPuntaje;
	} 
	
	
}
