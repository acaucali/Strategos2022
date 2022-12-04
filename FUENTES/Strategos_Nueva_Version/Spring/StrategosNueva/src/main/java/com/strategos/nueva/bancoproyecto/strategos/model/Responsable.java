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
@Table(name="responsable")
public class Responsable implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(columnDefinition = "serial")
	private Long responsableId;
	
	@Column(nullable=true)
	private Long usuarioId;
	
	@Column(nullable=true)
	private Long organizacionId;
	
	@Size(max=2000)
	@Column(nullable=true)
	private String cargo;
	
	@Size(max=2000)
	@Column(nullable=true)
	private String nombre;
	
	@Size(max=2000)
	@Column(nullable=true)
	private String email;
	
	@Size(max=2000)
	@Column(nullable=true)
	private String ubicacion;
	
	@Size(max=2000)
	@Column(nullable=true)
	private String notas;
	
	@Column(nullable=true, name="tipo")
	private Byte tipo;
	
	@Column(nullable=true, name="grupo")
	private Byte esGrupo;

	public Long getResponsableId() {
		return responsableId;
	}

	public void setResponsableId(Long responsableId) {
		this.responsableId = responsableId;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Long getOrganizacionId() {
		return organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public Byte getTipo() {
		return tipo;
	}

	public void setTipo(Byte tipo) {
		this.tipo = tipo;
	}

	public Byte getEsGrupo() {
		return esGrupo;
	}

	public void setEsGrupo(Byte esGrupo) {
		this.esGrupo = esGrupo;
	}
	
	private static final long serialVersionUID = 1L;
	
}
