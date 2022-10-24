package com.strategos.nueva.bancoproyecto.ideas.model;

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
@Table(name="bp_actividad")
public class Actividad implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long actividadId;
	
	@Size(max=250)
	@Column(nullable=false)
	private String nombreActividad;
	
	@Size(max=2000)
	@Column(nullable=false)
	private String descripcion;
	
	@Column(nullable=true)
	private Double peso;
	
	@Column(nullable=true)
	private Double zonaVerde;
	
	@Column(nullable=true)
	private Double zonaAmarilla;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date fechaInicio; 
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date fechaCulminacion; 
	
	@Column(nullable=true)
	private Long responsableId;
	
		
	public Long getActividadId() {
		return actividadId;
	}

	public void setActividadId(Long actividadId) {
		this.actividadId = actividadId;
	}

	public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public Double getZonaVerde() {
		return zonaVerde;
	}

	public void setZonaVerde(Double zonaVerde) {
		this.zonaVerde = zonaVerde;
	}

	public Double getZonaAmarilla() {
		return zonaAmarilla;
	}

	public void setZonaAmarilla(Double zonaAmarilla) {
		this.zonaAmarilla = zonaAmarilla;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaCulminacion() {
		return fechaCulminacion;
	}

	public void setFechaCulminacion(Date fechaCulminacion) {
		this.fechaCulminacion = fechaCulminacion;
	}

	public Long getResponsableId() {
		return responsableId;
	}

	public void setResponsableId(Long responsableId) {
		this.responsableId = responsableId;
	}

	private static final long serialVersionUID = 1L;
}
