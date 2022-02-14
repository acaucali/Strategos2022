package com.strategos.riesgos.models.procesos.utilidades;

import java.util.List;

import com.strategos.riesgos.model.ejercicios.entity.FactoresRiesgo;

public class MapaCalor {
	
	private String color;
	private int probabilidad;
	private int impacto;
	private int severidad;
	private List<FactoresRiesgo> factores;
	private int cantidadId;
	
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public int getProbabilidad() {
		return probabilidad;
	}
	
	public void setProbabilidad(int probabilidad) {
		this.probabilidad = probabilidad;
	}
	
	public int getImpacto() {
		return impacto;
	}
	
	public void setImpacto(int impacto) {
		this.impacto = impacto;
	}
	
	public int getSeveridad() {
		return severidad;
	}
	
	public void setSeveridad(int severidad) {
		this.severidad = severidad;
	}
		
	public List<FactoresRiesgo> getFactores() {
		return factores;
	}

	public void setFactores(List<FactoresRiesgo> factores) {
		this.factores = factores;
	}

	public int getCantidadId() {
		return cantidadId;
	}

	public void setCantidadId(int cantidadId) {
		this.cantidadId = cantidadId;
	}
	
	
}
