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
@Table(name="bp_departamentos")
public class Departamentos implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long departamentoId;
	
	@Size(max=200)
	@Column(nullable=true)
	private String departamentoNombre;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proyectoId", nullable = true) 
	@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "departamentos" }, allowSetters = true)
	private Proyectos proyecto;

	public Long getDepartamentosId() {
		return departamentoId;
	}
	
	public void setDepartamentoId(Long departamentoId) {
		this.departamentoId = departamentoId;
	}
	
	public String getDepartamentoNombre() {
		return departamentoNombre;
	}
	
	public void setDepartamentoNombre(String departamentoNombre) {
		this.departamentoNombre = departamentoNombre;
	}
	
	public Proyectos getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyectos proyecto) {
		this.proyecto = proyecto;
	}
	
	private static final long serialVersionUID = 1L;
}
