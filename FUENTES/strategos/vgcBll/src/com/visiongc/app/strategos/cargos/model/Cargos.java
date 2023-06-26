package com.visiongc.app.strategos.cargos.model;

import org.apache.commons.lang.builder.ToStringBuilder;

public class Cargos {
	
	private Long cargoId;
	private String nombre;
	
	public Cargos(Long cargoId, String nombre) {
		this.cargoId = cargoId;
		this.setNombre(nombre);
	}
	
	public Cargos() {}
	
	public Long getCargoId() {
		return cargoId;
	}
	
	public void setCargoId(Long cargoId) {
		this.cargoId = cargoId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public int compareTo(Object o) {
		Cargos or = (Cargos)o;
		return getCargoId().compareTo(or.getCargoId());
	}
	
	public String toString() {
		return new ToStringBuilder(this).append("cargoId", getCargoId()).toString();
	}
	
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass()) {
			return false;
		}
		Cargos other = (Cargos)obj;
		if(cargoId == null) {
			if(cargoId != null)
				return false;
		}else if(!cargoId.equals(cargoId))
			return false;
		return true;
			
	}
}