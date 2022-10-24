package com.strategos.nueva.bancoproyecto.ideas.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bp_iniciativa_perspectiva")
public class IniciativaPerspectiva implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long iniciativaPerspectivaId;
	
	private Long perspectivaId;
	private Long iniciativaId;
			
	public Long getIniciativaPerspectivaId() {
		return iniciativaPerspectivaId;
	}

	public void setIniciativaPerspectivaId(Long iniciativaPerspectivaId) {
		this.iniciativaPerspectivaId = iniciativaPerspectivaId;
	}

	public Long getPerspectivaId() {
		return perspectivaId;
	}

	public void setPerspectivaId(Long perspectivaId) {
		this.perspectivaId = perspectivaId;
	}

	public Long getIniciativaId() {
		return iniciativaId;
	}

	public void setIniciativaId(Long iniciativaId) {
		this.iniciativaId = iniciativaId;
	}

	private static final long serialVersionUID = 1L;
	  
}
