package com.strategos.nueva.bancoproyecto.ideas.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bp_proyectos_poblacion")
public class ProyectosPoblacion implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long proyectoPoblacionId;
	
	private Long proyectoId;
	
	private Long poblacionId;
	
	public Long getProyectoPoblacionId() {
		return proyectoPoblacionId;
	}

	public void setProyectoPoblacionId(Long proyectoPoblacionId) {
		this.proyectoPoblacionId = proyectoPoblacionId;
	}

	public Long getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(Long proyectoId) {
		this.proyectoId = proyectoId;
	}

	public Long getPoblacionId() {
		return poblacionId;
	}

	public void setPoblacionId(Long poblacionId) {
		this.poblacionId = poblacionId;
	}

	private static final long serialVersionUID = 1L;
}
