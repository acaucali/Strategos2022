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
@Table(name="metodologia")
public class MetodologiaStrategos implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long metodologiaId;
	
	@Size(max=50)
	@Column(nullable=true, name="nombre")
	private String nombre;
	
	@Size(max=2000)
	@Column(nullable=true)
	private String descripcion;
	
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
	
	@Size(max=50)
	@Column(nullable=true, name="indicador_nombre")
	private String nombreIndicador;
	
	@Size(max=50)
	@Column(nullable=true, name="iniciativa_nombre")
	private String nombreIniciativa;
	
	@Size(max=50)
	@Column(nullable=true, name="actividad_nombre")
	private String nombreActividad;
	
	@Column(nullable=true)
	private Byte tipo;
	
		
	public Long getMetodologiaId() {
		return metodologiaId;
	}

	public void setMetodologiaId(Long metodologiaId) {
		this.metodologiaId = metodologiaId;
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

	public String getNombreIndicador() {
		return nombreIndicador;
	}

	public void setNombreIndicador(String nombreIndicador) {
		this.nombreIndicador = nombreIndicador;
	}

	public String getNombreIniciativa() {
		return nombreIniciativa;
	}

	public void setNombreIniciativa(String nombreIniciativa) {
		this.nombreIniciativa = nombreIniciativa;
	}

	public String getNombreActividad() {
		return nombreActividad;
	}

	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}

	public Byte getTipo() {
		return tipo;
	}

	public void setTipo(Byte tipo) {
		this.tipo = tipo;
	}

	private static final long serialVersionUID = 1L;
}
