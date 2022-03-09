package com.strategos.nueva.bancoproyecto.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="bp_evaluaciones_anexos")
public class EvaluacionAnexos implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long documentoId;
	
	@Column(nullable=false)
	private Long evaluacionId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ejercicioId", nullable = false)
	@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "factorRiesgo" }, allowSetters = true)
	private EjercicioRiesgo ejercicio;
	
	@Size(max=150)
	@Column(nullable=true)
	private String documento;
	
	private static final long serialVersionUID = 1L;

}
