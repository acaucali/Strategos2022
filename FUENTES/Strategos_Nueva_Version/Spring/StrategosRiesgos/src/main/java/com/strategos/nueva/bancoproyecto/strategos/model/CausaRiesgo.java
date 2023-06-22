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
@Table(name="causa_riesgo")
public class CausaRiesgo implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(columnDefinition = "serial")
	private Long causaRiesgoId;
	
	@Size(max=50)
	@Column(nullable=false)
	private String causaRiesgo;
	
	@Size(max=500)
	@Column(nullable=true)
	private String causaRiesgoDescripcion;
	

	public Long getCausaRiesgoId() {
		return causaRiesgoId;
	}


	public void setCausaRiesgoId(Long causaRiesgoId) {
		this.causaRiesgoId = causaRiesgoId;
	}


	public String getCausaRiesgo() {
		return causaRiesgo;
	}


	public void setCausaRiesgo(String causaRiesgo) {
		this.causaRiesgo = causaRiesgo;
	}


	public String getCausaRiesgoDescripcion() {
		return causaRiesgoDescripcion;
	}


	public void setCausaRiesgoDescripcion(String causaRiesgoDescripcion) {
		this.causaRiesgoDescripcion = causaRiesgoDescripcion;
	}

	private static final long serialVersionUID = 1L;
}
