package com.strategos.riesgos.models.procesos.entity;

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
@Table(name = "Proceso_Caracterizacion")
public class ProcesoCaracterizacion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long procedimientoId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "procesoId", nullable = false)
	@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "procesoCaracterizacion" }, allowSetters = true)
	private Procesos proceso;

	@Size(max = 100)
	@Column(nullable = false)
	private String procedimientoNombre;

	@Size(max = 45)
	@Column(nullable = true)
	private String procedimientoCodigo;
	
	@Size(max = 150)
	@Column(nullable = true)
	private String procedimientoObjetivo;
	
	@Column(nullable=true)
	private Long procedimientoDocumento;
	
	
	
	
	public Long getProcedimientoDocumento() {
		return procedimientoDocumento;
	}


	public void setProcedimientoDocumento(Long procesoDocumento) {
		this.procedimientoDocumento = procesoDocumento;
	}


	public Procesos getProceso() {
		return proceso;
	}


	public void setProceso(Procesos proceso) {
		this.proceso = proceso;
	}


	public String getProcedimientoObjetivo() {
		return procedimientoObjetivo;
	}


	public void setProcedimientoObjetivo(String procedimientoObjetivo) {
		this.procedimientoObjetivo = procedimientoObjetivo;
	}

	public Long getProcedimientoId() {
		return procedimientoId;
	}

	public void setProcedimientoId(Long procedimientoId) {
		this.procedimientoId = procedimientoId;
	}

	public String getProcedimientoNombre() {
		return procedimientoNombre;
	}

	public void setProcedimientoNombre(String procedimientoNombre) {
		this.procedimientoNombre = procedimientoNombre;
	}

	public String getProcedimientoCodigo() {
		return procedimientoCodigo;
	}

	public void setProcedimientoCodigo(String procedimientoCodigo) {
		this.procedimientoCodigo = procedimientoCodigo;
	}

	private static final long serialVersionUID = 1L;
}
