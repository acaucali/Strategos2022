package com.strategos.nueva.bancoproyecto.ideas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="bp_evaluacion_ideas_detalle")
public class EvaluacionIdeasDetalle implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long evaluacionId;
	
	private Long ideaId;
	
	@Size(max=200)
	@Column(nullable=true)
	private String criterio;
	
	@Column(nullable=true)
	private Double peso;
	
	@Column(nullable=true)
	private Double valorEvaluado;
	
		
	public Long getEvaluacionId() {
		return evaluacionId;
	}

	public void setEvaluacionId(Long evaluacionId) {
		this.evaluacionId = evaluacionId;
	}

	public Long getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(Long ideaId) {
		this.ideaId = ideaId;
	}

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getValorEvaluado() {
		return valorEvaluado;
	}

	public void setValorEvaluado(Double valorEvaluado) {
		this.valorEvaluado = valorEvaluado;
	}


	private static final long serialVersionUID = 1L;

}
