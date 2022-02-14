package com.strategos.riesgos.model.ejercicios.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="RI_Ejercicios_Riego")
public class EjercicioRiesgo implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long ejercicioId;
	
	@Column(nullable=false)
	private Long procesoPadreId;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fechaEjercicio;
	
	@Size(max=500)
	@Column(nullable=false)
	private String ejercicioDescripcion;
	
	@Column(nullable=false)
	private int ejercicioEstatus;
	
	@JsonIgnoreProperties(value ={ "hibernateLazyInitializer", "handler", "ejercicio" }, allowSetters = true)
	@OneToMany(cascade= CascadeType.ALL, mappedBy="ejercicio", fetch=FetchType.LAZY)
	private List<FactoresRiesgo> factorRiesgo;
	
	public EjercicioRiesgo() {
		this.factorRiesgo= new ArrayList();
	}
	
	public Long getEjercicioId() {
		return ejercicioId;
	}

	public void setEjercicioId(Long ejercicioId) {
		this.ejercicioId = ejercicioId;
	}

	public Long getProcesoPadreId() {
		return procesoPadreId;
	}

	public void setProcesoPadreId(Long procesoPadreId) {
		this.procesoPadreId = procesoPadreId;
	}

	public Date getFechaEjercicio() {
		return fechaEjercicio;
	}

	public void setFechaEjercicio(Date fechaEjercicio) {
		this.fechaEjercicio = fechaEjercicio;
	}

	public String getEjercicioDescripcion() {
		return ejercicioDescripcion;
	}

	public void setEjercicioDescripcion(String ejercicioDescripcion) {
		this.ejercicioDescripcion = ejercicioDescripcion;
	}

	public int getEjercicioEstatus() {
		return ejercicioEstatus;
	}

	public void setEjercicioEstatus(int ejercicioEstatus) {
		this.ejercicioEstatus = ejercicioEstatus;
	}

	public List<FactoresRiesgo> getFactorRiesgo() {
		return factorRiesgo;
	}
	
	public void setFactorRiesgo(List<FactoresRiesgo> factorRiesgo) {
		this.factorRiesgo = factorRiesgo;
	}

	private static final long serialVersionUID = 1L;

}
