package com.strategos.nueva.bancoproyecto.ideas.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bp_iniciativa_plan")
public class IniciativaPlan implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long iniciativaPlanId;
	
	private Long iniciativaId;
	private Long planId;
			
	public Long getIniciativaPlanId() {
		return iniciativaPlanId;
	}

	public void setIniciativaPlanId(Long iniciativaPlanId) {
		this.iniciativaPlanId = iniciativaPlanId;
	}

	public Long getIniciativaId() {
		return iniciativaId;
	}

	public void setIniciativaId(Long iniciativaId) {
		this.iniciativaId = iniciativaId;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	private static final long serialVersionUID = 1L;
}
