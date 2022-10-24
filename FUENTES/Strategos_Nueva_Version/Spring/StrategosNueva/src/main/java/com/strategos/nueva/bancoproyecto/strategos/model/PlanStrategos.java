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
@Table(name="planes")
public class PlanStrategos implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(columnDefinition = "serial")
	private Long planId;

	@Column(nullable=true)
	private Long organizacionId;
	
	@Column(nullable=true)
	private Long planImpactaId;
	
	@Size(max=50)
	@Column(nullable=true)
	private String nombre;
	
	@Column(nullable=true)
	private Long padreId;
	
	@Column(nullable=true)
	private Integer anoInicial;
	
	@Column(nullable=true)
	private Integer anoFinal;
	
	@Column(nullable=true)
	private Byte tipo;
	
	@Column(nullable=true)
	private Byte activo;
	
	@Column(nullable=true)
	private Byte revision;
	
	@Size(max=50)
	@Column(nullable=true)
	private String esquema;
	
	@Column(nullable=true)
	private Long metodologiaId;
	
	@Column(nullable=true)
	private Long claseId;
	
	@Column(nullable=true)
	private Long claseIdIndicadoresTotales;
	
	@Column(nullable=true, name="ind_total_imputacion_id")
	private Long indTotalImputacionId;
	
	@Column(nullable=true, name="ind_total_iniciativa_id")
	private Long indTotalIniciativaId;
	
	@Column(nullable=true, name="crecimiento")
	private Byte alerta;
	
	@Column(nullable=true)
	private Double ultimaMedicionAnual;
	
	@Column(nullable=true)
	private Double ultimaMedicionParcial;
	
	@Column(nullable=true, name="nl_ano_indicador_id")
	private Long nlAnoIndicadorId;
	
	@Column(nullable=true, name="nl_par_indicador_id")
	private Long nlParIndicadorId;
	
	@Size(max=10)
	@Column(nullable=true)
	private String fechaUltimaMedicion;
	
	
	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public Long getOrganizacionId() {
		return organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
	}

	public Long getPlanImpactaId() {
		return planImpactaId;
	}

	public void setPlanImpactaId(Long planImpactaId) {
		this.planImpactaId = planImpactaId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getPadreId() {
		return padreId;
	}

	public void setPadreId(Long padreId) {
		this.padreId = padreId;
	}

	public Integer getAnoInicial() {
		return anoInicial;
	}

	public void setAnoInicial(Integer anoInicial) {
		this.anoInicial = anoInicial;
	}

	public Integer getAnoFinal() {
		return anoFinal;
	}

	public void setAnoFinal(Integer anoFinal) {
		this.anoFinal = anoFinal;
	}

	public Byte getTipo() {
		return tipo;
	}

	public void setTipo(Byte tipo) {
		this.tipo = tipo;
	}

	public Byte getActivo() {
		return activo;
	}

	public void setActivo(Byte activo) {
		this.activo = activo;
	}

	public Byte getRevision() {
		return revision;
	}

	public void setRevision(Byte revision) {
		this.revision = revision;
	}

	public String getEsquema() {
		return esquema;
	}

	public void setEsquema(String esquema) {
		this.esquema = esquema;
	}

	public Long getMetodologiaId() {
		return metodologiaId;
	}

	public void setMetodologiaId(Long metodologiaId) {
		this.metodologiaId = metodologiaId;
	}

	public Long getClaseId() {
		return claseId;
	}

	public void setClaseId(Long claseId) {
		this.claseId = claseId;
	}

	public Long getClaseIdIndicadoresTotales() {
		return claseIdIndicadoresTotales;
	}

	public void setClaseIdIndicadoresTotales(Long claseIdIndicadoresTotales) {
		this.claseIdIndicadoresTotales = claseIdIndicadoresTotales;
	}

	public Long getIndTotalImputacionId() {
		return indTotalImputacionId;
	}

	public void setIndTotalImputacionId(Long indTotalImputacionId) {
		this.indTotalImputacionId = indTotalImputacionId;
	}

	public Long getIndTotalIniciativaId() {
		return indTotalIniciativaId;
	}

	public void setIndTotalIniciativaId(Long indTotalIniciativaId) {
		this.indTotalIniciativaId = indTotalIniciativaId;
	}

	public Byte getAlerta() {
		return alerta;
	}

	public void setAlerta(Byte alerta) {
		this.alerta = alerta;
	}

	public Double getUltimaMedicionAnual() {
		return ultimaMedicionAnual;
	}

	public void setUltimaMedicionAnual(Double ultimaMedicionAnual) {
		this.ultimaMedicionAnual = ultimaMedicionAnual;
	}

	public Double getUltimaMedicionParcial() {
		return ultimaMedicionParcial;
	}

	public void setUltimaMedicionParcial(Double ultimaMedicionParcial) {
		this.ultimaMedicionParcial = ultimaMedicionParcial;
	}

	public Long getNlAnoIndicadorId() {
		return nlAnoIndicadorId;
	}

	public void setNlAnoIndicadorId(Long nlAnoIndicadorId) {
		this.nlAnoIndicadorId = nlAnoIndicadorId;
	}

	public Long getNlParIndicadorId() {
		return nlParIndicadorId;
	}

	public void setNlParIndicadorId(Long nlParIndicadorId) {
		this.nlParIndicadorId = nlParIndicadorId;
	}

	public String getFechaUltimaMedicion() {
		return fechaUltimaMedicion;
	}

	public void setFechaUltimaMedicion(String fechaUltimaMedicion) {
		this.fechaUltimaMedicion = fechaUltimaMedicion;
	}


	private static final long serialVersionUID = 1L;
}
