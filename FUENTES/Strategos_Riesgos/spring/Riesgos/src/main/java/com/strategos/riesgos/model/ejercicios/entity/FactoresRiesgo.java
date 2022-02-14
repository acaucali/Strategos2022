package com.strategos.riesgos.model.ejercicios.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="RI_Factores_Riesgo")
public class FactoresRiesgo implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long factorRiesgoId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ejercicioId", nullable = false)
	@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "factorRiesgo" }, allowSetters = true)
	private EjercicioRiesgo ejercicio;
	
	@Column(nullable = false)
	private Long procesoId;
	
	@Size(max = 100)
	@Column(nullable = false)
	private String factorRiesgo;
	
	@Size(max = 150)
	@Column(nullable = true)
	private String descripcionFactor;
	
	@Size(max = 150)
	@Column(nullable = true)
	private String responsable;
	
	@Column(nullable=false)
	private int tipoRiesgoId;
	
	@Column(nullable=false)
	private int RespuestaId;
	
	@Column(nullable=true)
	private int controlId;
	
	@Column(nullable=true)
	private int historico;
	
	@Column(nullable=true)
	private int probabilidad;
	
	@Column(nullable=true)
	private int impacto;
	
	@Column(nullable=true)
	private int severidad;
	
	@Column(nullable=true)
	private int riesgoResidual;
	
	@Column(nullable=false)
	private int estatus;
	
	@Size(max = 20)
	@Column(nullable=true)
	private String alerta;
	
	@JsonIgnoreProperties(value ={ "hibernateLazyInitializer", "handler", "factor" }, allowSetters = true)
	@OneToMany(cascade= CascadeType.ALL, mappedBy="factor", fetch=FetchType.LAZY)
	private List<CausaRiesgo> causaRiesgo;
	
	@JsonIgnoreProperties(value ={ "hibernateLazyInitializer", "handler", "factor" }, allowSetters = true)
	@OneToMany(cascade= CascadeType.ALL, mappedBy="factor", fetch=FetchType.LAZY)
	private List<EfectoRiesgo> efectoRiesgo;
	
	@JsonIgnoreProperties(value ={ "hibernateLazyInitializer", "handler", "factor" }, allowSetters = true)
	@OneToMany(cascade= CascadeType.ALL, mappedBy="factor", fetch=FetchType.LAZY)
	private List<ControlesRiesgo> controlesRiesgo;
	
	public FactoresRiesgo() {
		this.causaRiesgo= new ArrayList();
		this.controlesRiesgo= new ArrayList();
		this.efectoRiesgo= new ArrayList();
	}
	
	
	
	public Long getProcesoId() {
		return procesoId;
	}



	public void setProcesoId(Long procesoId) {
		this.procesoId = procesoId;
	}



	public String getAlerta() {
		return alerta;
	}



	public void setAlerta(String alerta) {
		this.alerta = alerta;
	}



	public Long getFactorRiesgoId() {
		return factorRiesgoId;
	}

	public void setFactorRiesgoId(Long factorRiesgoId) {
		this.factorRiesgoId = factorRiesgoId;
	}

	public EjercicioRiesgo getEjercicio() {
		return ejercicio;
	}

	public void setEjercicio(EjercicioRiesgo ejercicio) {
		this.ejercicio = ejercicio;
	}

	public String getDescripcionFactor() {
		return descripcionFactor;
	}

	public void setDescripcionFactor(String descripcionFactor) {
		this.descripcionFactor = descripcionFactor;
	}

	public int getTipoRiesgoId() {
		return tipoRiesgoId;
	}

	public void setTipoRiesgoId(int tipoRiesgoId) {
		this.tipoRiesgoId = tipoRiesgoId;
	}

	public int getRespuestaId() {
		return RespuestaId;
	}

	public void setRespuestaId(int respuestaId) {
		RespuestaId = respuestaId;
	}

	public int getControlId() {
		return controlId;
	}

	public void setControlId(int controlId) {
		this.controlId = controlId;
	}

	public int getHistorico() {
		return historico;
	}

	public void setHistorico(int historico) {
		this.historico = historico;
	}

	public int getProbabilidad() {
		return probabilidad;
	}

	public void setProbabilidad(int probabilidad) {
		this.probabilidad = probabilidad;
	}

	public int getImpacto() {
		return impacto;
	}

	public void setImpacto(int impacto) {
		this.impacto = impacto;
	}

	public int getSeveridad() {
		return severidad;
	}

	public void setSeveridad(int severidad) {
		this.severidad = severidad;
	}

	public int getRiesgoResidual() {
		return riesgoResidual;
	}

	public void setRiesgoResidual(int riesgoResidual) {
		this.riesgoResidual = riesgoResidual;
	}

	public List<CausaRiesgo> getCausaRiesgo() {
		return causaRiesgo;
	}

	public void setCausaRiesgo(List<CausaRiesgo> causaRiesgo) {
		this.causaRiesgo = causaRiesgo;
	}

	public List<EfectoRiesgo> getEfectoRiesgo() {
		return efectoRiesgo;
	}

	public void setEfectoRiesgo(List<EfectoRiesgo> efectoRiesgo) {
		this.efectoRiesgo = efectoRiesgo;
	}

	public List<ControlesRiesgo> getControlesRiesgo() {
		return controlesRiesgo;
	}

	public void setControlesRiesgo(List<ControlesRiesgo> controlesRiesgo) {
		this.controlesRiesgo = controlesRiesgo;
	}
	
	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public int getEstatus() {
		return estatus;
	}

	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}

	public String getFactorRiesgo() {
		return factorRiesgo;
	}

	public void setFactorRiesgo(String factorRiesgo) {
		this.factorRiesgo = factorRiesgo;
	}



	private static final long serialVersionUID = 1L;

	
}
