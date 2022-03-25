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
@Table(name="bp_criterios_evaluacion")
public class CriteriosEvaluacion implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long criterioId;
	
	@Size(max=50)
	@Column(nullable=true)
	private String control;
	
	@Column(nullable=true)
	private Double peso;
	
		
	public Long getCriterioId() {
		return criterioId;
	}

	public void setCriterioId(Long criterioId) {
		this.criterioId = criterioId;
	}

	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	private static final long serialVersionUID = 1L;

}
