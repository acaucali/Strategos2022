package com.strategos.nueva.bancoproyecto.strategos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;


@Entity
@Table(name="tipo_impacto")
public class TipoImpacto implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(columnDefinition = "serial")
	private Long tipoImpactolId;
	
	@Size(max=50)
	@Column(nullable=false)
	private String tipoImpacto;
	

	@Column(nullable=false)
	private int tipoImpactoPuntaje;
	
	@Size(max=500)
	@Column(nullable=true)
	private String tipoImpactoDescripcion;
	
	
	
	public Long getTipoImpactolId() {
		return tipoImpactolId;
	}


	public void setTipoImpactolId(Long tipoImpactolId) {
		this.tipoImpactolId = tipoImpactolId;
	}


	public String getTipoImpacto() {
		return tipoImpacto;
	}


	public void setTipoImpacto(String tipoImpacto) {
		this.tipoImpacto = tipoImpacto;
	}


	public int getTipoImpactoPuntaje() {
		return tipoImpactoPuntaje;
	}


	public void setTipoImpactoPuntaje(int tipoImpactoPuntaje) {
		this.tipoImpactoPuntaje = tipoImpactoPuntaje;
	}


	public String getTipoImpactoDescripcion() {
		return tipoImpactoDescripcion;
	}


	public void setTipoImpactoDescripcion(String tipoImpactoDescripcion) {
		this.tipoImpactoDescripcion = tipoImpactoDescripcion;
	}


	private static final long serialVersionUID = 1L;

}
