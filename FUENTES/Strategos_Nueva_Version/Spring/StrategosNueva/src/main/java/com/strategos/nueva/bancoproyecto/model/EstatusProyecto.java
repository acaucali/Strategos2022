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
@Table(name="estatus_proyecto")
public class EstatusProyecto implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long estatusProyectoId;
	
	@Size(max=50)
	@Column(nullable=true)
	private String estatus; 
	
		
	public Long getEstatusProyectoId() {
		return estatusProyectoId;
	}


	public void setEstatusProyectoId(Long estatusProyectoId) {
		this.estatusProyectoId = estatusProyectoId;
	}


	public String getEstatus() {
		return estatus;
	}


	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}


	private static final long serialVersionUID = 1L;
	
}
