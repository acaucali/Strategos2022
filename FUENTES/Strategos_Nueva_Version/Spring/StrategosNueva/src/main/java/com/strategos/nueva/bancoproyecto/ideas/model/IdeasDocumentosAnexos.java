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
	
	//campo blob donde se guardara el documento
	@Lob
	@Column(nullable=false)
	private byte[] documento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ideaId", nullable = false)
	@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "documentos" }, allowSetters = true)
	private IdeasProyectos idea;
	
			
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

	public byte[] getDocumento() {
		return documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	public IdeasProyectos getIdea() {
		return idea;
	}

	public void setIdea(IdeasProyectos idea) {
		this.idea = idea;
	}

	private static final long serialVersionUID = 1L;
	
}
