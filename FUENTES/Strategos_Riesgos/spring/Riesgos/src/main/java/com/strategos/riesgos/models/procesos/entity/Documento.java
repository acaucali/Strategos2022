package com.strategos.riesgos.models.procesos.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Documentos")
public class Documento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long documentoId;

	@Size(max = 150)
	@Column(nullable = true)
	private String documentoNombre;

	@Column(nullable=false)
	private byte documentoTipo;
	
	@Column(nullable=false)
	private Long documentoOrigenId;
	
	@Size(max = 500)
	@Column(nullable = false)
	private String documentoRuta;

	public Long getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(Long documentoId) {
		this.documentoId = documentoId;
	}

	public String getDocumentoNombre() {
		return documentoNombre;
	}

	public void setDocumentoNombre(String documentoNombre) {
		this.documentoNombre = documentoNombre;
	}

	public byte getDocumentoTipo() {
		return documentoTipo;
	}

	public void setDocumentoTipo(byte documentoTipo) {
		this.documentoTipo = documentoTipo;
	}

	public String getDocumentoRuta() {
		return documentoRuta;
	}

	public void setDocumentoRuta(String documentoRuta) {
		this.documentoRuta = documentoRuta;
	}

	public Long getDocumentoOrigenId() {
		return documentoOrigenId;
	}

	public void setDocumentoOrigenId(Long documentoOrigenId) {
		this.documentoOrigenId = documentoOrigenId;
	}



	private static final long serialVersionUID = 1L;
}
