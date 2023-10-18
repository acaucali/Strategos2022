package com.visiongc.app.strategos.iniciativas.model.util;

import java.io.Serializable;

public class FaseProyecto implements Serializable {
	
	static final long serialVersionUID = 0L;
	
	private Long faseProyectoId;
	private String nombre;

	public Long getFaseProyectoId() {
		return faseProyectoId;
	}
	public void setFaseProyectoId(Long faseProyectoId) {
		this.faseProyectoId = faseProyectoId;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public FaseProyecto() {}
	
	public FaseProyecto(Long faseProyectoId, String nombre) {
		
		super();
		this.faseProyectoId = faseProyectoId;
		this.nombre = nombre;
	}	
}
