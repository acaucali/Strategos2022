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
@Table(name="bp_presupuesto")
public class Presupuesto implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long presupuestoId;
	
	@Size(max=500)
	@Column(nullable=true)
	private String presupuesto;
	
		
	public Long getPresupuestoId() {
		return presupuestoId;
	}

	public void setPresupuestoId(Long presupuestoId) {
		this.presupuestoId = presupuestoId;
	}

	public String getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(String presupuesto) {
		this.presupuesto = presupuesto;
	}

	private static final long serialVersionUID = 1L;

}
