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
@Table(name="clase")
public class ClaseIndicadoresStrategos implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long claseId;
	
	@Column(nullable=true)
	private Long padreId;
	
	@Column(nullable=true)
	private Long organizacionId;
	
	@Size(max=310)
	@Column(nullable=true)
	private String nombre;
	
	@Size(max=250)
	@Column(nullable=true)
	private String descripcion;
	
	@Size(max=20)
	@Column(nullable=true)
	private String enlaceParcial;
	
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
	
	@Column(nullable=true)
	private Byte tipo;
	
	@Column(nullable=true)
	private Boolean visible;
		
	public Long getClaseId() {
		return claseId;
	}

	public void setClaseId(Long claseId) {
		this.claseId = claseId;
	}

	public Long getPadreId() {
		return padreId;
	}

	public void setPadreId(Long padreId) {
		this.padreId = padreId;
	}

	public Long getOrganizacionId() {
		return organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEnlaceParcial() {
		return enlaceParcial;
	}

	public void setEnlaceParcial(String enlaceParcial) {
		this.enlaceParcial = enlaceParcial;
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

	public Byte getTipo() {
		return tipo;
	}

	public void setTipo(Byte tipo) {
		this.tipo = tipo;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}


	private static final long serialVersionUID = 1L;
}
