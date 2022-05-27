package com.strategos.nueva.bancoproyecto.ideas.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="bp_evaluacion_ideas")
public class EvaluacionIdeas implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long evaluacionId;
	
	@Column(nullable=true)
	private Date fechaEvaluacion;
	
	@Size(max=500)
	@Column(nullable=true)
	private String observacion;
	
		
	public Long getEvaluacionId() {
		return evaluacionId;
	}

	public void setEvaluacionId(Long evaluacionId) {
		this.evaluacionId = evaluacionId;
	}

	public Date getFechaEvaluacion() {
		return fechaEvaluacion;
	}

	public void setFechaEvaluacion(Date fechaEvaluacion) {
		this.fechaEvaluacion = fechaEvaluacion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}


	private static final long serialVersionUID = 1L;

}
