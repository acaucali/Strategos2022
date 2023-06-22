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
@Table(name="calificaciones_riesgo")
public class CalificacionesRiesgo implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(columnDefinition = "serial")
	private Long calificacionesRiesgoId;
	
	@Size(max=50)
	@Column(nullable=false)
	private String calificacionesRiesgo;
	
	
	@Column(nullable=false)
	private int calificacionesRiesgoMinimo;
	
	
	@Column(nullable=false)
	private int calificacionesRiesgoMaximo;
	
	@Size(max=10)
	@Column(nullable=false)
	private String calificacionesRiesgoColor;
	
	@Size(max=500)
	@Column(nullable=true)
	private String calificacionesRiesgoAccion;
	
	
	
	public Long getCalificacionesRiesgoId() {
		return calificacionesRiesgoId;
	}



	public void setCalificacionesRiesgoId(Long calificacionesRiesgoId) {
		this.calificacionesRiesgoId = calificacionesRiesgoId;
	}



	public String getCalificacionesRiesgo() {
		return calificacionesRiesgo;
	}



	public void setCalificacionesRiesgo(String calificacionesRiesgo) {
		this.calificacionesRiesgo = calificacionesRiesgo;
	}



	public int getCalificacionesRiesgoMinimo() {
		return calificacionesRiesgoMinimo;
	}



	public void setCalificacionesRiesgoMinimo(int calificacionesRiesgoMinimo) {
		this.calificacionesRiesgoMinimo = calificacionesRiesgoMinimo;
	}



	public int getCalificacionesRiesgoMaximo() {
		return calificacionesRiesgoMaximo;
	}



	public void setCalificacionesRiesgoMaximo(int calificacionesRiesgoMaximo) {
		this.calificacionesRiesgoMaximo = calificacionesRiesgoMaximo;
	}



	public String getCalificacionesRiesgoColor() {
		return calificacionesRiesgoColor;
	}



	public void setCalificacionesRiesgoColor(String calificacionesRiesgoColor) {
		this.calificacionesRiesgoColor = calificacionesRiesgoColor;
	}



	public String getCalificacionesRiesgoAccion() {
		return calificacionesRiesgoAccion;
	}



	public void setCalificacionesRiesgoAccion(String calificacionesRiesgoAccion) {
		this.calificacionesRiesgoAccion = calificacionesRiesgoAccion;
	}



	private static final long serialVersionUID = 1L;
	
}
