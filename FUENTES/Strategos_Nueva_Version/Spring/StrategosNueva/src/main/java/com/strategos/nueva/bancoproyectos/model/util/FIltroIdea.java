package com.strategos.nueva.bancoproyectos.model.util;

public class FIltroIdea {
	
	private Long organizacionId;
	private Long propuestaId;
	private Long estatusId;
	private String anio;
	private Boolean historico;
	
	public Long getOrganizacionId() {
		return organizacionId;
	}
	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
	}
	public Long getPropuestaId() {
		return propuestaId;
	}
	public void setPropuestaId(Long propuestaId) {
		this.propuestaId = propuestaId;
	}
	public Long getEstatusId() {
		return estatusId;
	}
	public void setEstatusId(Long estatusId) {
		this.estatusId = estatusId;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public Boolean getHistorico() {
		return historico;
	}
	public void setHistorico(Boolean historico) {
		this.historico = historico;
	}	
	
}
