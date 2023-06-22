package com.strategos.nueva.riesgos.procesos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="procesos")
public class Procesos implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(columnDefinition = "serial")
	private Long procesosId;
	
	@Column(nullable=true)
	private int procesoPadreId;
	
	@Size(max=100)
	@Column(nullable=false)
	private String procesoNombre;
	
	@Size(max=20)
	@Column(nullable=true)
	private String procesoCodigo;
	
	@Column(nullable=false)
	private int procesoTipo;
	
	@Size(max=150)
	@Column(nullable=true)
	private String descripcion;
	
	@Size(max=50)
	@Column(nullable=true)
	private String responsable;
	
	@Column(nullable=true)
	private Long procesoDocumento;
	
	@JsonIgnoreProperties(value ={ "hibernateLazyInitializer", "handler", "proceso" }, allowSetters = true)
	@OneToMany(cascade= CascadeType.ALL, mappedBy="proceso", fetch=FetchType.LAZY)
	private List<ProcesoProducto> procesoProducto;
	
	@JsonIgnoreProperties(value ={ "hibernateLazyInitializer", "handler", "proceso" }, allowSetters = true)
	@OneToMany(cascade= CascadeType.ALL, mappedBy="proceso", fetch=FetchType.LAZY)
	private List<ProcesoCaracterizacion> procesoCaracterizacion;

	
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<ProcesoCaracterizacion> getProcesoCaracterizacion() {
		return procesoCaracterizacion;
	}

	public void setProcesoCaracterizacion(List<ProcesoCaracterizacion> procesoCaracterizacion) {
		this.procesoCaracterizacion = procesoCaracterizacion;
	}

	public Procesos() {
		this.procesoProducto = new ArrayList<>();
		this.procesoCaracterizacion = new ArrayList();
	}
	
	public Long getProcesosId() {
		return procesosId;
	}

	public void setProcesosId(Long procesosId) {
		this.procesosId = procesosId;
	}

	public int getProcesoPadreId() {
		return procesoPadreId;
	}

	public void setProcesoPadreId(int procesoPadreId) {
		this.procesoPadreId = procesoPadreId;
	}

	public String getProcesoNombre() {
		return procesoNombre;
	}

	public void setProcesoNombre(String procesoNombre) {
		this.procesoNombre = procesoNombre;
	}

	public String getProcesoCodigo() {
		return procesoCodigo;
	}

	public void setProcesoCodigo(String procesoCodigo) {
		this.procesoCodigo = procesoCodigo;
	}

	public int getProcesoTipo() {
		return procesoTipo;
	}

	public void setProcesoTipo(int procesoTipo) {
		this.procesoTipo = procesoTipo;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public Long getProcesoDocumento() {
		return procesoDocumento;
	}

	public void setProcesoDocumento(Long procesoDocumento) {
		this.procesoDocumento = procesoDocumento;
	}
	
	
	public List<ProcesoProducto> getProcesoProducto() {
		return procesoProducto;
	}

	public void setProcesoProducto(List<ProcesoProducto> procesoProducto) {
		this.procesoProducto = procesoProducto;
	}



	private static final long serialVersionUID = 1L;
	
}
