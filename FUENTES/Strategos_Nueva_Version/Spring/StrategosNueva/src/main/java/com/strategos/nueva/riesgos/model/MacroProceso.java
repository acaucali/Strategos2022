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
@Table(name="macro_procesos")
public class MacroProceso implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(columnDefinition = "serial")
	private Long macroProcesoId;
	
	@Size(max=100)
	@Column(nullable=false)
	private String macroProceso;
	
	@Size(max=150)
	@Column(nullable=true)
	private String macroProcesoDescripcion;
	
	@Size(max=50)
	@Column(nullable=true)
	private String macroProcesoCodigo;
	
	
	public Long getMacroProcesoId() {
		return macroProcesoId;
	}

	public void setMacroProcesoId(Long macroProcesoId) {
		this.macroProcesoId = macroProcesoId;
	}

	public String getMacroProceso() {
		return macroProceso;
	}

	public void setMacroProceso(String macroProceso) {
		this.macroProceso = macroProceso;
	}

	public String getMacroProcesoDescripcion() {
		return macroProcesoDescripcion;
	}

	public void setMacroProcesoDescripcion(String macroProcesoDescripcion) {
		this.macroProcesoDescripcion = macroProcesoDescripcion;
	}

	public String getMacroProcesoCodigo() {
		return macroProcesoCodigo;
	}

	public void setMacroProcesoCodigo(String macroProcesoCodigo) {
		this.macroProcesoCodigo = macroProcesoCodigo;
	}

	private static final long serialVersionUID = 1L;
}
