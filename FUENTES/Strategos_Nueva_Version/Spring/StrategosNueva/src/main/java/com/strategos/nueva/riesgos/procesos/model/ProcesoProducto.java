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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "proceso_producto")
public class ProcesoProducto implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(columnDefinition = "serial")
	private Long procesoProductoId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "procesoId", nullable = false)
	@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "procesoProducto" }, allowSetters = true)
	private Procesos proceso;

	@Size(max = 50)
	@Column(nullable = false)
	private String procesoProductoServicio;

	@Size(max = 150)
	@Column(nullable = true)
	private String procesoProductoCaracteristica;
	
	public Long getProcesoProductoId() {
		return procesoProductoId;
	}

	public void setProcesoProductoId(Long procesoProductoId) {
		this.procesoProductoId = procesoProductoId;
	}

	public Procesos getProceso() {
		return proceso;
	}

	public void setProceso(Procesos proceso) {
		this.proceso = proceso;
	}

	public String getProcesoProductoServicio() {
		return procesoProductoServicio;
	}

	public void setProcesoProductoServicio(String procesoProductoServicio) {
		this.procesoProductoServicio = procesoProductoServicio;
	}

	public String getProcesoProductoCaracteristica() {
		return procesoProductoCaracteristica;
	}

	public void setProcesoProductoCaracteristica(String procesoProductoCaracteristica) {
		this.procesoProductoCaracteristica = procesoProductoCaracteristica;
	}

	private static final long serialVersionUID = 1L;

}
