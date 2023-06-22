package com.strategos.nueva.bancoproyecto.ideas.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bp_indicador_iniciativa")
public class IndicadorIniciativa implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long iniciativaIndicadorId;
	
	private Long iniciativaId;
	
	private Long indicadorId;
	
	private Byte tipo;
	
		
	public Long getIniciativaIndicadorId() {
		return iniciativaIndicadorId;
	}

	public void setIniciativaIndicadorId(Long iniciativaIndicadorId) {
		this.iniciativaIndicadorId = iniciativaIndicadorId;
	}

	public Long getIniciativaId() {
		return iniciativaId;
	}

	public void setIniciativaId(Long iniciativaId) {
		this.iniciativaId = iniciativaId;
	}

	public Long getIndicadorId() {
		return indicadorId;
	}

	public void setIndicadorId(Long indicadorId) {
		this.indicadorId = indicadorId;
	}

	public Byte getTipo() {
		return tipo;
	}

	public void setTipo(Byte tipo) {
		this.tipo = tipo;
	}

	private static final long serialVersionUID = 1L;
}
