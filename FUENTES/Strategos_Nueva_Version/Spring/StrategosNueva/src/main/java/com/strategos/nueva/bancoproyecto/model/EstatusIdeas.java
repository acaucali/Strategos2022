package com.strategos.nueva.bancoproyecto.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="estatus_ideas")
public class EstatusIdeas implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long estatusIdeaId;
	
	@Size(max=50)
	@Column(nullable=false)
	private String estatus;
	
		
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

	private static final long serialVersionUID = 1L;
}
