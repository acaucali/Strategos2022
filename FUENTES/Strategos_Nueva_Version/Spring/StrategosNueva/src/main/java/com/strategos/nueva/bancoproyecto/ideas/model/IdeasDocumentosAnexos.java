package com.strategos.nueva.bancoproyecto.ideas.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="bp_ideas_anexos")
public class IdeasDocumentosAnexos implements Serializable{

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
	private Long ideaId;
			
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

	public Long getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(Long ideaId) {
		this.ideaId = ideaId;
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
