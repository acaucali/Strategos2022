package com.strategos.nueva.bancoproyecto.ideas.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="bp_ideas_proyectos")
public class IdeasProyectos implements Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long ideaId;
	
	@Size(max=250)
	@Column(nullable=false)
	private String nombreIdea;
	
	@Size(max=2000)
	@Column(nullable=false)
	private String descripcionIdea;
	
	@Column(nullable=false)
	private Long tipoPropuestaId;
	
	@Size(max=2000)
	@Column(nullable=true)
	private String propuesta;
	
	@Size(max=2000)
	@Column(nullable=false)
	private String impacto;
	
	@Size(max=2000)
	@Column(nullable=false)
	private String problematica;
	
	@Size(max=2000)
	@Column(nullable=false)
	private String poblacion;
	
	@Size(max=2000)
	@Column(nullable=true)
	private String focalizacion;

	@Column(nullable=false)
	private Long tipoObjetivoId;	
		
	@Size(max=500)
	@Column(nullable=false)
	private String financiacion;
	
	@Size(max=500)
	@Column(nullable=false)
	private String dependenciasParticipantes;

	
	@Size(max=50)
	@Column(nullable=false)
	private String personaEncargada;
	
	@Size(max=50)
	@Column(nullable=false)
	private String contactoEmail;
	
	@Size(max=50)
	@Column(nullable=false)
	private String contactoTelefono;
		
	//organizacion Id
	@Column(nullable=true)
	private Long dependenciaId;
	
	@Size(max=2000)
	@Column(nullable=true)
	private String organizacion;
	
	@Size(max=2000)
	@Column(nullable=false)
	private String proyectosEjecutados;
	
	@Size(max=2000)
	@Column(nullable=false)
	private String capacidadTecnica;
		

	@Size(max=4)
	@Column(nullable=true)
	private String anioFormulacion;
	
	@Column(nullable=true)
	private Long estatusIdeaId;
	
	@Size(max=2000)
	@Column(nullable=true)
	private String estatus;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date fechaEstatus; 
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date fechaRadicacion; 
	
	@Column(nullable=true)
	private Boolean historico;
	
	@Column(nullable=true)
	private Double valorUltimaEvaluacion;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date fechaUltimaEvaluacion;
	
	@Size(max=2000)
	@Column(nullable=true)
	private String observaciones;
	
	@Size(max=2000)
	@Column(nullable=true)
	private String objetivoGeneral;
	
	@Size(max=500)
	@Column(nullable=true)
	private String duracionTotal;
	
	@Size(max=2000)
	@Column(nullable=true)
	private String informacionAdicional;
	
	@Column(nullable=true)
	private Long documentoId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "evaluacionId", nullable = true)
	@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "ideas" }, allowSetters = true)
	private EvaluacionIdeas evaluacion;
		
	public Long getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(Long ideaId) {
		this.ideaId = ideaId;
	}

	public String getNombreIdea() {
		return nombreIdea;
	}

	public void setNombreIdea(String nombreIdea) {
		this.nombreIdea = nombreIdea;
	}

	public String getDescripcionIdea() {
		return descripcionIdea;
	}

	public void setDescripcionIdea(String descripcionIdea) {
		this.descripcionIdea = descripcionIdea;
	}


	public String getImpacto() {
		return impacto;
	}

	public void setImpacto(String impacto) {
		this.impacto = impacto;
	}

	public String getProblematica() {
		return problematica;
	}

	public void setProblematica(String problematica) {
		this.problematica = problematica;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getFocalizacion() {
		return focalizacion;
	}

	public void setFocalizacion(String focalizacion) {
		this.focalizacion = focalizacion;
	}

	public String getFinanciacion() {
		return financiacion;
	}

	public void setFinanciacion(String financiacion) {
		this.financiacion = financiacion;
	}

	public String getPersonaEncargada() {
		return personaEncargada;
	}

	public void setPersonaEncargada(String personaEncargada) {
		this.personaEncargada = personaEncargada;
	}

	public Long getDependenciaId() {
		return dependenciaId;
	}

	public void setDependenciaId(Long dependenciaId) {
		this.dependenciaId = dependenciaId;
	}

	public String getProyectosEjecutados() {
		return proyectosEjecutados;
	}

	public void setProyectosEjecutados(String proyectosEjecutados) {
		this.proyectosEjecutados = proyectosEjecutados;
	}

	public String getCapacidadTecnica() {
		return capacidadTecnica;
	}

	public void setCapacidadTecnica(String capacidadTecnica) {
		this.capacidadTecnica = capacidadTecnica;
	}

	public String getAnioFormulacion() {
		return anioFormulacion;
	}

	public void setAnioFormulacion(String anioFormulacion) {
		this.anioFormulacion = anioFormulacion;
	}
	
	public Date getFechaEstatus() {
		return fechaEstatus;
	}

	public void setFechaEstatus(Date fechaEstatus) {
		this.fechaEstatus = fechaEstatus;
	}

	public Boolean getHistorico() {
		return historico;
	}

	public void setHistorico(Boolean historico) {
		this.historico = historico;
	}

	public Double getValorUltimaEvaluacion() {
		return valorUltimaEvaluacion;
	}

	public void setValorUltimaEvaluacion(Double valorUltimaEvaluacion) {
		this.valorUltimaEvaluacion = valorUltimaEvaluacion;
	}

	public Date getFechaUltimaEvaluacion() {
		return fechaUltimaEvaluacion;
	}

	public void setFechaUltimaEvaluacion(Date fechaUltimaEvaluacion) {
		this.fechaUltimaEvaluacion = fechaUltimaEvaluacion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	public String getDependenciasParticipantes() {
		return dependenciasParticipantes;
	}

	public void setDependenciasParticipantes(String dependenciasParticipantes) {
		this.dependenciasParticipantes = dependenciasParticipantes;
	}

	public Long getTipoPropuestaId() {
		return tipoPropuestaId;
	}

	public void setTipoPropuestaId(Long tipoPropuestaId) {
		this.tipoPropuestaId = tipoPropuestaId;
	}

	public Long getTipoObjetivoId() {
		return tipoObjetivoId;
	}

	public void setTipoObjetivoId(Long tipoObjetivoId) {
		this.tipoObjetivoId = tipoObjetivoId;
	}

	public Long getEstatusIdeaId() {
		return estatusIdeaId;
	}

	public void setEstatusIdeaId(Long estatusIdeaId) {
		this.estatusIdeaId = estatusIdeaId;
	}

	public Long getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(Long documentoId) {
		this.documentoId = documentoId;
	}
		
	public String getPropuesta() {
		return propuesta;
	}

	public void setPropuesta(String propuesta) {
		this.propuesta = propuesta;
	}

	public String getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(String organizacion) {
		this.organizacion = organizacion;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getContactoEmail() {
		return contactoEmail;
	}

	public void setContactoEmail(String contactoEmail) {
		this.contactoEmail = contactoEmail;
	}

	public String getContactoTelefono() {
		return contactoTelefono;
	}

	public void setContactoTelefono(String contactoTelefono) {
		this.contactoTelefono = contactoTelefono;
	}

	public Date getFechaRadicacion() {
		return fechaRadicacion;
	}

	public void setFechaRadicacion(Date fechaRadicacion) {
		this.fechaRadicacion = fechaRadicacion;
	}

	public String getObjetivoGeneral() {
		return objetivoGeneral;
	}

	public void setObjetivoGeneral(String objetivoGeneral) {
		this.objetivoGeneral = objetivoGeneral;
	}

	public String getDuracionTotal() {
		return duracionTotal;
	}

	public void setDuracionTotal(String duracionTotal) {
		this.duracionTotal = duracionTotal;
	}

	public EvaluacionIdeas getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(EvaluacionIdeas evaluacion) {
		this.evaluacion = evaluacion;
	}
	
	public String getInformacionAdicional() {
		return informacionAdicional;
	}

	public void setInformacionAdicional(String informacionAdicional) {
		this.informacionAdicional = informacionAdicional;
	}

	private static final long serialVersionUID = 1L;
	
}
