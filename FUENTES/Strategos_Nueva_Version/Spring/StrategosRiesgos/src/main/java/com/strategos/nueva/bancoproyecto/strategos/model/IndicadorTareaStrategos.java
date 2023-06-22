package com.strategos.nueva.bancoproyecto.strategos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="indicador_por_actividad")

public class IndicadorTareaStrategos implements Serializable{

	@EmbeddedId
	private IndicadorTareaPk pk;
	
	@Column(nullable=true)
	private Double peso;
			
	
	public IndicadorTareaPk getPk() {
		return pk;
	}

	public void setPk(IndicadorTareaPk pk) {
		this.pk = pk;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	private static final long serialVersionUID = 1L;
	
}
