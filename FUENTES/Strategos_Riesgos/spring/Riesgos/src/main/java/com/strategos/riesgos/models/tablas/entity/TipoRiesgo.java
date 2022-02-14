package com.strategos.riesgos.models.tablas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="Tipo_Riesgo")
public class TipoRiesgo implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tipoRiesgoId;
	
	@Size(max=50)
	@Column(nullable=false)
	private String tipoRiesgo;

	
	
	public Long getTipoRiesgoId() {
		return tipoRiesgoId;
	}


	public void setTipoRiesgoId(Long tipoRiesgoId) {
		this.tipoRiesgoId = tipoRiesgoId;
	}


	public String getTipoRiesgo() {
		return tipoRiesgo;
	}


	public void setTipoRiesgo(String tipoRiesgo) {
		this.tipoRiesgo = tipoRiesgo;
	}


	private static final long serialVersionUID = 1L;

}
