package com.strategos.nueva.bancoproyecto.ideas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="bp_tipos_poblacion")
public class TipoPoblacion implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tipoPoblacionId;
	
	@Size(max=250)
	@Column(nullable=true)
	private String poblacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proyectoId", nullable = true) 
	@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "poblaciones" }, allowSetters = true)
	private Proyectos proyecto;
			
	public Long getTipoPoblacionId() {
		return tipoPoblacionId;
	}

	public void setTipoPoblacionId(Long tipoPoblacionId) {
		this.tipoPoblacionId = tipoPoblacionId;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}
	
	public Proyectos getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyectos proyecto) {
		this.proyecto = proyecto;
	}


	private static final long serialVersionUID = 1L;
}
