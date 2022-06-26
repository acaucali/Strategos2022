package com.strategos.nueva.bancoproyecto.ideas.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="bp_evaluacion_ideas")
public class EvaluacionIdeas implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long evaluacionId;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date fechaEvaluacion;
	
	@Size(max=500)
	@Column(nullable=true)
	private String observacion;
	
	@JsonIgnoreProperties(value ={ "hibernateLazyInitializer", "handler", "evaluacion" }, allowSetters = true)
	@OneToMany(cascade= CascadeType.MERGE, mappedBy="evaluacion", fetch=FetchType.LAZY)
	private List<IdeasProyectos> ideas;	
	
		
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
	
	public List<IdeasProyectos> getIdeas() {
		return ideas;
	}

	public void setIdeas(List<IdeasProyectos> ideas) {
		this.ideas = ideas;
	}



	private static final long serialVersionUID = 1L;

}
