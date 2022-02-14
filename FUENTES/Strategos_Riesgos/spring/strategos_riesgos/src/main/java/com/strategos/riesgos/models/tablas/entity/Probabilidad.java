package com.strategos.riesgos.models.tablas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="Probabilidad")
public class Probabilidad implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long probabilidadId;
	
	@Size(max=50)
	@Column(nullable=false)
	private String probabilidadNombre;
	
	@Column(nullable=false)
	private int probabilidadPuntaje;
	
	@Size(max=500)
	@Column(nullable=true)
	private String probabilidadDescripcion;
	
	
	public Long getProbabilidadId() {
		return probabilidadId;
	}


	public void setProbabilidadId(Long probabilidadId) {
		this.probabilidadId = probabilidadId;
	}


	public String getProbabilidadNombre() {
		return probabilidadNombre;
	}


	public void setProbabilidadNombre(String probabilidadNombre) {
		this.probabilidadNombre = probabilidadNombre;
	}


	public int getProbabilidadPuntaje() {
		return probabilidadPuntaje;
	}


	public void setProbabilidadPuntaje(int probabilidadPuntaje) {
		this.probabilidadPuntaje = probabilidadPuntaje;
	}


	public String getProbabilidadDescripcion() {
		return probabilidadDescripcion;
	}


	public void setProbabilidadDescripcion(String probabilidadDescripcion) {
		this.probabilidadDescripcion = probabilidadDescripcion;
	}


	private static final long serialVersionUID = 1L;
}
