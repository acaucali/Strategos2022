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
@Table(name="tipo_proyecto")
public class TipoProyectoStrategos implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long tipoProyectoId;
	
	@Size(max=50)
	@Column(nullable=true, name="nombre_proyecto")
	private String nombre;

	public Long getTipoProyectoId() {
		return tipoProyectoId;
	}

	public void setTipoProyectoId(Long tipoProyectoId) {
		this.tipoProyectoId = tipoProyectoId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	private static final long serialVersionUID = 1L;	
}
