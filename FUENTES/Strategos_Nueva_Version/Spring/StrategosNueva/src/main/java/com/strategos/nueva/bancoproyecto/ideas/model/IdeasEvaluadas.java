package com.strategos.nueva.bancoproyecto.ideas.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bp_ideas_evaluadas")
public class IdeasEvaluadas implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long ideaEvaluadaId;
	
	private Long evaluacionId;

	private Long ideaId;
	
	
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
		
	public Long getIdeaEvaluadaId() {
		return ideaEvaluadaId;
	}

	public void setIdeaEvaluadaId(Long ideaEvaluadaId) {
		this.ideaEvaluadaId = ideaEvaluadaId;
	}

	private static final long serialVersionUID = 1L;

}
