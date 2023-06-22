package com.strategos.nueva.bancoproyecto.ideas.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bp_proyectos_plan")
public class ProyectosPlan implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long proyectoPlanId;
	
	private Long proyectoId;
	
	private Long planId;
			
	public Long getProyectoPlanId() {
		return proyectoPlanId;
	}

	public void setProyectoPlanId(Long proyectoPlanId) {
		this.proyectoPlanId = proyectoPlanId;
	}

	public Long getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(Long proyectoId) {
		this.proyectoId = proyectoId;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}


	private static final long serialVersionUID = 1L;
}
