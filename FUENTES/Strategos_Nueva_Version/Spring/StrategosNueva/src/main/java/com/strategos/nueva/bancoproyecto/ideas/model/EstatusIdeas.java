package com.strategos.nueva.bancoproyecto.ideas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="estatus_ideas")
public class EstatusIdeas implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long estatusIdeaId;
	
	@Size(max=50)
	@Column(nullable=true)
	private String estatus;
	
	@OneToOne(mappedBy = "estatus", fetch=FetchType.LAZY)
	@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "estatus" }, allowSetters = true)
    private IdeasProyectos idea;
	
		
	public Long getEstatusIdeaId() {
		return estatusIdeaId;
	}

	public void setEstatusIdeaId(Long estatusIdeaId) {
		this.estatusIdeaId = estatusIdeaId;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
		
	public IdeasProyectos getIdea() {
		return idea;
	}

	public void setIdea(IdeasProyectos idea) {
		this.idea = idea;
	}


	private static final long serialVersionUID = 1L;
}
