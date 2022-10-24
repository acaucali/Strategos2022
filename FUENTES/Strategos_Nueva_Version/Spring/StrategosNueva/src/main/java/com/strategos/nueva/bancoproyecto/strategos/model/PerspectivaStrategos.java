package com.strategos.nueva.bancoproyecto.strategos.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name="perspectiva")
public class PerspectivaStrategos implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(columnDefinition = "serial")
	private Long perspectivaId;
	
	@Column(nullable=true)
	private Long planId;
	
	@Column(nullable=true)
	private Long responsableId;
	
	@Size(max=250)
	@Column(nullable=true)
	private String nombre;
	
	@Column(nullable=true)
	private Long padreId;
	
	@Size(max=1000)
	@Column(nullable=true)
	private String descripcion;
	
	@Column(nullable=true)
	private Integer orden;
	
	@Column(nullable=true)
	private Byte frecuencia;
	
	@Size(max=50)
	@Column(nullable=true)
	private String titulo;
	
	@Column(nullable=true)
	private Byte tipo;
	
	@Size(max=10)
	@Column(nullable=true)
	private String fechaUltimaMedicion;
	
	@Column(nullable=true)
	private Double ultimaMedicionAnual;
	
	@Column(nullable=true)
	private Double ultimaMedicionParcial;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date creado;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date modificado;
	
	@Column(nullable=true)
	private Long creadoId;
	
	@Column(nullable=true)
	private Long modificadoId;
	
	@Column(nullable=true, name="crecimiento_parcial")
	private Byte alertaParcial;
	
	@Column(nullable=true, name="crecimiento_anual")
	private Byte alertaAnual;
	
	@Column(nullable=true)
	private Integer ano;
	
	@Column(nullable=true)
	private Long claseId;
	
	@Column(nullable=true, name="nl_ano_indicador_id")
	private Long nlAnoIndicadorId;
	
	@Column(nullable=true, name="nl_par_indicador_id")
	private Long nlParIndicadorId;
	
	@Column(nullable=true)
	private Byte tipoCalculo;
	
	
	public Long getPerspectivaId() {
		return perspectivaId;
	}

	public void setPerspectivaId(Long perspectivaId) {
		this.perspectivaId = perspectivaId;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public Long getResponsableId() {
		return responsableId;
	}

	public void setResponsableId(Long responsableId) {
		this.responsableId = responsableId;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Byte getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Byte frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Byte getTipo() {
		return tipo;
	}

	public void setTipo(Byte tipo) {
		this.tipo = tipo;
	}

	public String getFechaUltimaMedicion() {
		return fechaUltimaMedicion;
	}

	public void setFechaUltimaMedicion(String fechaUltimaMedicion) {
		this.fechaUltimaMedicion = fechaUltimaMedicion;
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

	public Date getCreado() {
		return creado;
	}

	public void setCreado(Date creado) {
		this.creado = creado;
	}

	public Date getModificado() {
		return modificado;
	}

	public void setModificado(Date modificado) {
		this.modificado = modificado;
	}

	public Long getCreadoId() {
		return creadoId;
	}

	public void setCreadoId(Long creadoId) {
		this.creadoId = creadoId;
	}

	public Long getModificadoId() {
		return modificadoId;
	}

	public void setModificadoId(Long modificadoId) {
		this.modificadoId = modificadoId;
	}

	public Byte getAlertaParcial() {
		return alertaParcial;
	}

	public void setAlertaParcial(Byte alertaParcial) {
		this.alertaParcial = alertaParcial;
	}

	public Byte getAlertaAnual() {
		return alertaAnual;
	}

	public void setAlertaAnual(Byte alertaAnual) {
		this.alertaAnual = alertaAnual;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Long getClaseId() {
		return claseId;
	}

	public void setClaseId(Long claseId) {
		this.claseId = claseId;
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

	public Byte getTipoCalculo() {
		return tipoCalculo;
	}

	public void setTipoCalculo(Byte tipoCalculo) {
		this.tipoCalculo = tipoCalculo;
	}


	private static final long serialVersionUID = 1L;
}
