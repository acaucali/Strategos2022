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
@Table(name="bp_proyectos_region")
public class ProyectosRegion implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long proyectoRegionId;
	
	private Long departamentoId;
		
	@Size(max=250)
	@Column(nullable=true)
	private String municipioId;
	
	private Long proyectoId;
	
	@Size(max=250)
	@Column(nullable=true)
	private String municipioNombre;
	
	@Size(max=250)
	@Column(nullable=true)
	private String departamentoNombre;
	
	public void setProyectoRegionId(Long proyectoRegionId){
		this.proyectoRegionId = proyectoRegionId;
	}
	
	public Long getProyectoRegionId() {
		return proyectoRegionId;
	}

	public Long getDepartamentoId() {
		return departamentoId;
	}

	public void setDepartamentoId(Long departamentoId) {
		this.departamentoId = departamentoId;
	}

	public String getMunicipioId() {
		return municipioId;
	}

	public void setMunicipioId(String municipioId) {
		this.municipioId = municipioId;
	}
	
	public Long getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(Long proyectoId) {
		this.proyectoId = proyectoId;
	}
	
	public String getMunicipioNombre() {
		return municipioNombre;
	}

	public void setMunicipioNombre(String municipioNombre) {
		this.municipioNombre = municipioNombre;
	}

	public String getDepartamentoNombre() {
		return departamentoNombre;
	}

	public void setDepartamentoNombre(String departamentoNombre) {
		this.departamentoNombre = departamentoNombre;
	}



	private static final long serialVersionUID = 1L;

}
