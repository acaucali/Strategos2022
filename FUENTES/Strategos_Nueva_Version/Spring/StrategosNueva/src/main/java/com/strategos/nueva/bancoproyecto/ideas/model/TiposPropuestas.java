package com.strategos.nueva.bancoproyecto.ideas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="bp_tipos_propuestas")
public class TiposPropuestas implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tipoPropuestaId;
	
	@Size(max=50)
	@Column(nullable=true)
	private String tipoPropuesta;
	
	
	
	public Long getTipoPropuestaId() {
		return tipoPropuestaId;
	}

	public void setTipoPropuestaId(Long tipoPropuestaId) {
		this.tipoPropuestaId = tipoPropuestaId;
	}

	public String getTipoPropuesta() {
		return tipoPropuesta;
	}

	public void setTipoPropuesta(String tipoPropuesta) {
		this.tipoPropuesta = tipoPropuesta;
	}

	

	private static final long serialVersionUID = 1L;
	
}
