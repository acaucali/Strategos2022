package com.strategos.nueva.bancoproyecto.strategos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name="medicion")

public class MedicionStrategos implements Serializable{
	
	@EmbeddedId
	private MedicionPkStrategos medicionPk;
	
	@Column(nullable=true)
	private Double valor;
	
	@Column(nullable=true)
	private Integer protegido;


	public MedicionPkStrategos getMedicionPk() {
		return medicionPk;
	}

	public void setMedicionPk(MedicionPkStrategos medicionPk) {
		this.medicionPk = medicionPk;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getProtegido() {
		return protegido;
	}

	public void setProtegido(Integer protegido) {
		this.protegido = protegido;
	}
	
	private static final long serialVersionUID = 1L;
	
}
