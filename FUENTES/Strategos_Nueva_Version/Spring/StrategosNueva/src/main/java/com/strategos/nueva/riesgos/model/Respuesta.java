package com.strategos.nueva.riesgos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="respuesta_riesgo")
public class Respuesta implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(columnDefinition = "serial")
	private Long respuestaId;
	
	@Size(max=50)
	@Column(nullable=false)
	private String respuesta;
	
	@Size(max=500)
	@Column(nullable=true)
	private String respuestaDescripcion;

	
	public Long getRespuestaId() {
		return respuestaId;
	}


	public void setRespuestaId(Long respuestaId) {
		this.respuestaId = respuestaId;
	}


	public String getRespuesta() {
		return respuesta;
	}


	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}


	public String getRespuestaDescripcion() {
		return respuestaDescripcion;
	}


	public void setRespuestaDescripcion(String respuestaDescripcion) {
		this.respuestaDescripcion = respuestaDescripcion;
	}


	private static final long serialVersionUID = 1L;
}
