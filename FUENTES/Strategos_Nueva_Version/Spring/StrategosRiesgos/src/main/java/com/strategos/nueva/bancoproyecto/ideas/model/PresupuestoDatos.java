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
@Table(name="bp_presupuesto_datos")
public class PresupuestoDatos implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long presupuestoDatoId;
	
	@Column(nullable=false)
	private Long tareaId;
	
	@Column(nullable=false)
	private Long serieId;	
	
	@Size(max=500)
	@Column(nullable=true)
	private String presupuesto;
	
	@Column(nullable=true)
	private Integer ano;
	
	@Column(nullable=true)
	private Integer periodo;
	
	@Column(nullable=true)
	private Double valor;
		
	public Long getPresupuestoDatoId() {
		return presupuestoDatoId;
	}

	public void setPresupuestoDatoId(Long presupuestoDatoId) {
		this.presupuestoDatoId = presupuestoDatoId;
	}

	public Long getTareaId() {
		return tareaId;
	}

	public void setTareaId(Long tareaId) {
		this.tareaId = tareaId;
	}

	public String getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(String presupuesto) {
		this.presupuesto = presupuesto;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public Long getSerieId() {
		return serieId;
	}

	public void setSerieId(Long serieId) {
		this.serieId = serieId;
	}



	private static final long serialVersionUID = 1L;

}
