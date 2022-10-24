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
@Table(name="unidad")
public class UnidadStrategos implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long unidadId;
		
	@Column(nullable=true)
	private String nombre;
	
	@Column(nullable=true)
	private Boolean tipo;
	
		
	public Long getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(Long unidadId) {
		this.unidadId = unidadId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getTipo() {
		return tipo;
	}

	public void setTipo(Boolean tipo) {
		this.tipo = tipo;
	}


	private static final long serialVersionUID = 1L;
	
}
