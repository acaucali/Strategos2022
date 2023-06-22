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
@Table(name="efectos_riesgo")
public class EfectosRiesgo implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(columnDefinition = "serial")
	private Long efectoId;
	
	@Size(max=50)
	@Column(nullable=false)
	private String efecto;	
	
	@Size(max=500)
	@Column(nullable=true)
	private String efectoDescripcion;
	
		
	public Long getEfectoId() {
		return efectoId;
	}

	public void setEfectoId(Long efectoId) {
		this.efectoId = efectoId;
	}

	public String getEfecto() {
		return efecto;
	}

	public void setEfecto(String efecto) {
		this.efecto = efecto;
	}

	public String getEfectoDescripcion() {
		return efectoDescripcion;
	}

	public void setEfectoDescripcion(String efectoDescripcion) {
		this.efectoDescripcion = efectoDescripcion;
	}

	private static final long serialVersionUID = 1L;
	
}
