package com.strategos.nueva.bancoproyecto.model;

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
@Table(name="bp_evaluaciones_anexos")
public class EvaluacionAnexos implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long documentoId;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "evaluacionId", nullable = false)
	@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "evaluacionAnexos" }, allowSetters = true)
	private EvaluacionIdeas evaluacion;
	
	@Size(max=150)
	@Column(nullable=true)
	private String documento;
	
		
	public Long getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(Long documentoId) {
		this.documentoId = documentoId;
	}

	public EvaluacionIdeas getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(EvaluacionIdeas evaluacion) {
		this.evaluacion = evaluacion;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	private static final long serialVersionUID = 1L;

}
