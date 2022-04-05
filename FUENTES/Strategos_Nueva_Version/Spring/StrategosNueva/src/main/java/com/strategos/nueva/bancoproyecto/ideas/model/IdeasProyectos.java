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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
	
	//tabla interna propuesta
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "tipoPropuestaId")
	@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "idea" }, allowSetters = true)
    private TiposPropuestas tipoPropuesta;
		
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
	
	//tabla interna tipo objetivos
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "tipoObjetivoId")
	@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "idea" }, allowSetters = true)
    private TiposObjetivos tipoObjetivo;
	
	@Column(nullable=false)
	private Integer alineacionPlan;
	
	@Size(max=500)
	@Column(nullable=false)
	private String financiacion;
	
	@Size(max=50)
	@Column(nullable=false)
	private String personaEncargada;
	
	@Size(max=250)
	@Column(nullable=false)
	private String personaContactoDatos;
	
	//organizacion Id
	@Column(nullable=true)
	private Long dependenciaId;
	
	@Size(max=2000)
	@Column(nullable=false)
	private String proyectosEjecutados;
	
	@Size(max=2000)
	@Column(nullable=false)
	private String capacidadTecnica;
	
	@Column(nullable=false)
	private Date fechaIdea;
	
	@Size(max=4)
	@Column(nullable=true)
	private String anioFormulacion;
		
	//estatus idea
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "estatusIdeaId")
	@JsonIgnoreProperties(value={ "hibernateLazyInitializer", "handler", "idea" }, allowSetters = true)
    private EstatusIdeas estatus;
	
	@Column(nullable=false)
	private Integer estatusIdea;
	
	@Column(nullable=true)
	private Date fechaEstatus;
	
	@Column(nullable=true)
	private Byte historico;
	
	@Column(nullable=true)
	private Double valorUltimaEvaluacion;
	
	@Column(nullable=true)
	private Date fechaUltimaEvaluacion;
	
	@Size(max=2000)
	@Column(nullable=true)
	private String observaciones;
	
	@JsonIgnoreProperties(value ={ "hibernateLazyInitializer", "handler", "idea" }, allowSetters = true)
	@OneToMany(cascade= CascadeType.ALL, mappedBy="idea", fetch=FetchType.LAZY)
	private List<IdeasDocumentosAnexos> documentos;
	
		
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

	public TiposPropuestas getTipoPropuesta() {
		return tipoPropuesta;
	}

	public void setTipoPropuesta(TiposPropuestas tipoPropuesta) {
		this.tipoPropuesta = tipoPropuesta;
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

	public TiposObjetivos getTipoObjetivo() {
		return tipoObjetivo;
	}

	public void setTipoObjetivo(TiposObjetivos tipoObjetivo) {
		this.tipoObjetivo = tipoObjetivo;
	}

	public Integer getAlineacionPlan() {
		return alineacionPlan;
	}

	public void setAlineacionPlan(Integer alineacionPlan) {
		this.alineacionPlan = alineacionPlan;
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

	public String getPersonaContactoDatos() {
		return personaContactoDatos;
	}

	public void setPersonaContactoDatos(String personaContactoDatos) {
		this.personaContactoDatos = personaContactoDatos;
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

	public Date getFechaIdea() {
		return fechaIdea;
	}

	public void setFechaIdea(Date fechaIdea) {
		this.fechaIdea = fechaIdea;
	}

	public String getAnioFormulacion() {
		return anioFormulacion;
	}

	public void setAnioFormulacion(String anioFormulacion) {
		this.anioFormulacion = anioFormulacion;
	}

	public EstatusIdeas getEstatus() {
		return estatus;
	}

	public void setEstatus(EstatusIdeas estatus) {
		this.estatus = estatus;
	}

	public Integer getEstatusIdea() {
		return estatusIdea;
	}

	public void setEstatusIdea(Integer estatusIdea) {
		this.estatusIdea = estatusIdea;
	}

	public Date getFechaEstatus() {
		return fechaEstatus;
	}

	public void setFechaEstatus(Date fechaEstatus) {
		this.fechaEstatus = fechaEstatus;
	}

	public Byte getHistorico() {
		return historico;
	}

	public void setHistorico(Byte historico) {
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

	public List<IdeasDocumentosAnexos> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<IdeasDocumentosAnexos> documentos) {
		this.documentos = documentos;
	}

	private static final long serialVersionUID = 1L;
	
}
