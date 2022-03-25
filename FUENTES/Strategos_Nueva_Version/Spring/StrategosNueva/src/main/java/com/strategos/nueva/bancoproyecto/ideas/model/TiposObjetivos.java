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
@Table(name="bp_tipos_objetivos")
public class TiposObjetivos implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tipoObjetivoId;
	
	@Size(max=250)
	@Column(nullable=true)
	private String descripcionObjetivo;
	
	@OneToOne(mappedBy = "tipoObjetivo", fetch=FetchType.LAZY)
	@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "tipoObjetivo" }, allowSetters = true)
    private IdeasProyectos idea;
	
	
	public Long getTipoObjetivoId() {
		return tipoObjetivoId;
	}

	public void setTipoObjetivoId(Long tipoObjetivoId) {
		this.tipoObjetivoId = tipoObjetivoId;
	}

	public String getDescripcionObjetivo() {
		return descripcionObjetivo;
	}

	public void setDescripcionObjetivo(String descripcionObjetivo) {
		this.descripcionObjetivo = descripcionObjetivo;
	}

	public IdeasProyectos getIdea() {
		return idea;
	}

	public void setIdea(IdeasProyectos idea) {
		this.idea = idea;
	}

	private static final long serialVersionUID = 1L;

}
