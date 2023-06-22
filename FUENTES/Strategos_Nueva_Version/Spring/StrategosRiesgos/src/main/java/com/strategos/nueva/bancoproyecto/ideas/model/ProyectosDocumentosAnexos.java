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
@Table(name="bp_proyectos_anexos")
public class ProyectosDocumentosAnexos implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long documentoId;
	
	@Size(max=250)
	@Column(nullable=false)
	private String tituloDocumento;
	
	@Size(max = 500)
	@Column(nullable = false)
	private String descripcion;
	
	@Size(max = 1000)
	@Column(nullable = false)
	private String documentoRuta;
	
	@Column(nullable=false)
	private Long proyectoId;
	
	private Byte tipo;
	
	public Long getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(Long documentoId) {
		this.documentoId = documentoId;
	}

	public String getTituloDocumento() {
		return tituloDocumento;
	}

	public void setTituloDocumento(String tituloDocumento) {
		this.tituloDocumento = tituloDocumento;
	}
	
	public Long getProyectoId() {
		return proyectoId;
	}
	
	public void setProyectoId(Long proyectoId) {
		this.proyectoId = proyectoId;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDocumentoRuta() {
		return documentoRuta;
	}

	public void setDocumentoRuta(String documentoRuta) {
		this.documentoRuta = documentoRuta;
	}
	
	private static final long serialVersionUID = 1L;
	
}
