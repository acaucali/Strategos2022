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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="bp_proyectos")
public class Proyectos implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long proyectoId;
	
	//organizacion Id
	@Column(nullable=false)
	private Long dependenciaId;
	
	@Size(max=2000)
	@Column(nullable=true)
	private String organizacion;
	
	@Size(max=200)
	@Column(nullable=false)
	private String profesionalResponsable;
	
	@Size(max=200)
	@Column(nullable=false)
	private String contactoEmail;
	
	@Size(max=200)
	@Column(nullable=false)
	private String contactoTelefono;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date fechaRadicacion; 
	
	//organizacion Id
	@Column(nullable=false)
	private Long tipoProyectoId;
	
	@Size(max=2000)
	@Column(nullable=true)
	private String tipo;
	
	@Size(max=200)
	@Column(nullable=false)
	private String codigoBdp;
	
	@Size(max=250)
	@Column(nullable=false)
	private String nombreProyecto;
	
		
	@Column(nullable=false)
	private Integer duracion;
	
	@Size(max=150)
	@Column(nullable=false)
	private String costoEstimado;
	
	@Column(nullable=false)
	private Long dependenciaLider;
	
	//alineacion plan
	@Column(nullable=false)
	private Long tipoObjetivoId;
	
	@Size(max=2500)
	@Column(nullable=true)
	private String pertinencia;
	
	@Size(max=2500)
	@Column(nullable=true)
	private String problematica;
	
	@Size(max=500)
	@Column(nullable=false)
	private String dependenciasEstrategicas;
	
	@Size(max=500)
	@Column(nullable=true)
	private String sociosEstrategicos;
	
	@Size(max=2500)
	@Column(nullable=false)
	private String cobertura;
	
	@Size(max=2500)
	@Column(nullable=true)
	private String rolesParticipantes;
	
	@Size(max=2500)
	@Column(nullable=false)
	private String antecedentes;
	
	@Size(max=2500)
	@Column(nullable=false)
	private String justificacion;
	
	@Size(max=2500)
	@Column(nullable=false)
	private String alcance;
	
	@Size(max=2500)
	@Column(nullable=false)
	private String objetivoGeneral;
	
	@Size(max=500)
	@Column(nullable=false)
	private String financiacion;
	
	@Column(nullable=true)
	private Long ideaId;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date fechaFormulacion; 
	
	@Size(max=4)
	@Column(nullable=true)
	private String anioFormulacion;
	
	@Column(nullable=false)
	private Long estatusId;
	
	@Size(max=2000)
	@Column(nullable=true)
	private String estatus;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date fechaEstatus;
	
	@Column(nullable=true)
	private Boolean historico;
	
	@Column(nullable=false)
	private Long metodologiaId;

	@Column(nullable=true)
	private Boolean isPreproyecto;
	
	@Column(nullable=true)
	private Byte frecuencia;
	
	@JsonIgnoreProperties(value ={ "hibernateLazyInitializer", "handler", "proyecto" }, allowSetters = true)
	@OneToMany(cascade= CascadeType.MERGE, mappedBy="proyecto", fetch=FetchType.LAZY)
	private List<TipoPoblacion> poblaciones;
		
	@Size(max=500)
	@Column(nullable=true)
	private String cooperante;
	
	@Size(max=500)
	@Column(nullable=true) 
	private String dependenciasConvenio;		

	@Size(max=100)
	@Column(nullable=true) 
	private String numeroConvenio;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date fechaInicioConvenio; 
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date fechaCulminacionConvenio;
	
	@Size(max=200)
	@Column(nullable=true)
	private String nombreOperador;
	
	@Size(max=200)
	@Column(nullable=true)
	private String contactoEmailOperador;
	
	@Size(max=200)
	@Column(nullable=true)
	private String contactoTelefonoOperador;
	
	@Size(max=500)
	@Column(nullable=true)
	private String recursosAsignados;
	
	@Size(max=500)
	@Column(nullable=true)
	private String prorrogas;
	
	@JsonIgnoreProperties(value ={ "hibernateLazyInitializer", "handler", "proyecto" }, allowSetters = true)
	@OneToMany(cascade= CascadeType.MERGE, mappedBy="proyectoId", fetch=FetchType.LAZY)
	private List<ProyectosRegion> departamentos;
	
	@Column(nullable=true)
	private Long documentoId;
	
	
	public Boolean getIsPreproyecto() {
		return isPreproyecto;
	}

	public void setIsPreproyecto(Boolean isPreproyecto) {
		this.isPreproyecto = isPreproyecto;
	}

	public Long getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(Long proyectoId) {
		this.proyectoId = proyectoId;
	}

	public Long getDependenciaId() {
		return dependenciaId;
	}

	public void setDependenciaId(Long dependenciaId) {
		this.dependenciaId = dependenciaId;
	}

	public String getProfesionalResponsable() {
		return profesionalResponsable;
	}

	public void setProfesionalResponsable(String profesionalResponsable) {
		this.profesionalResponsable = profesionalResponsable;
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

	public Long getTipoProyectoId() {
		return tipoProyectoId;
	}

	public void setTipoProyectoId(Long tipoProyectoId) {
		this.tipoProyectoId = tipoProyectoId;
	}

	public String getCodigoBdp() {
		return codigoBdp;
	}

	public void setCodigoBdp(String codigoBdp) {
		this.codigoBdp = codigoBdp;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public String getCostoEstimado() {
		return costoEstimado;
	}

	public void setCostoEstimado(String costoEstimado) {
		this.costoEstimado = costoEstimado;
	}

	public Long getDependenciaLider() {
		return dependenciaLider;
	}

	public void setDependenciaLider(Long dependenciaLider) {
		this.dependenciaLider = dependenciaLider;
	}

	public Long getTipoObjetivoId() {
		return tipoObjetivoId;
	}

	public void setTipoObjetivoId(Long tipoObjetivoId) {
		this.tipoObjetivoId = tipoObjetivoId;
	}

	public String getPertinencia() {
		return pertinencia;
	}

	public void setPertinencia(String pertinencia) {
		this.pertinencia = pertinencia;
	}

	public String getProblematica() {
		return problematica;
	}

	public void setProblematica(String problematica) {
		this.problematica = problematica;
	}

	public String getDependenciasEstrategicas() {
		return dependenciasEstrategicas;
	}

	public void setDependenciasEstrategicas(String dependenciasEstrategicas) {
		this.dependenciasEstrategicas = dependenciasEstrategicas;
	}

	public String getSociosEstrategicos() {
		return sociosEstrategicos;
	}

	public void setSociosEstrategicos(String sociosEstrategicos) {
		this.sociosEstrategicos = sociosEstrategicos;
	}

	public String getCobertura() {
		return cobertura;
	}

	public void setCobertura(String cobertura) {
		this.cobertura = cobertura;
	}

	public String getRolesParticipantes() {
		return rolesParticipantes;
	}

	public void setRolesParticipantes(String rolesParticipantes) {
		this.rolesParticipantes = rolesParticipantes;
	}

	public String getAntecedentes() {
		return antecedentes;
	}

	public void setAntecedentes(String antecedentes) {
		this.antecedentes = antecedentes;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public String getAlcance() {
		return alcance;
	}

	public void setAlcance(String alcance) {
		this.alcance = alcance;
	}

	public String getObjetivoGeneral() {
		return objetivoGeneral;
	}

	public void setObjetivoGeneral(String objetivoGeneral) {
		this.objetivoGeneral = objetivoGeneral;
	}

	public String getFinanciacion() {
		return financiacion;
	}

	public void setFinanciacion(String financiacion) {
		this.financiacion = financiacion;
	}

	public Long getIdeaId() {
		return ideaId;
	}

	public void setIdeaId(Long ideaId) {
		this.ideaId = ideaId;
	}

	public Date getFechaFormulacion() {
		return fechaFormulacion;
	}

	public void setFechaFormulacion(Date fechaFormulacion) {
		this.fechaFormulacion = fechaFormulacion;
	}

	public String getAnioFormulacion() {
		return anioFormulacion;
	}

	public void setAnioFormulacion(String anioFormulacion) {
		this.anioFormulacion = anioFormulacion;
	}

	public Long getEstatusId() {
		return estatusId;
	}

	public void setEstatusId(Long estatusId) {
		this.estatusId = estatusId;
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

	public Long getMetodologiaId() {
		return metodologiaId;
	}

	public void setMetodologiaId(Long metodologiaId) {
		this.metodologiaId = metodologiaId;
	}

	public List<TipoPoblacion> getPoblaciones() {
		return poblaciones;
	}

	public void setPoblaciones(List<TipoPoblacion> poblaciones) {
		this.poblaciones = poblaciones;
	}
	
	public String getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(String organizacion) {
		this.organizacion = organizacion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {

		this.estatus = estatus;
	}
	
	public Byte getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Byte frecuencia) {
		this.frecuencia = frecuencia;
	}
	
	public String getCooperante() {
		return cooperante;
	}

	public void setCooperante(String cooperante) {
		this.cooperante = cooperante;
	}

	public String getDependenciasConvenio() {
		return dependenciasConvenio;
	}

	public void setDependenciasConvenio(String dependenciasConvenio) {
		this.dependenciasConvenio = dependenciasConvenio;
	}

	public String getNumeroConvenio() {
		return numeroConvenio;
	}

	public void setNumeroConvenio(String numeroConvenio) {
		this.numeroConvenio = numeroConvenio;
	}

	public Date getFechaInicioConvenio() {
		return fechaInicioConvenio;
	}

	public void setFechaInicioConvenio(Date fechaInicioConvenio) {
		this.fechaInicioConvenio = fechaInicioConvenio;
	}

	public Date getFechaCulminacionConvenio() {
		return fechaCulminacionConvenio;
	}

	public void setFechaCulminacionConvenio(Date fechaCulminacionConvenio) {
		this.fechaCulminacionConvenio = fechaCulminacionConvenio;
	}

	public String getNombreOperador() {
		return nombreOperador;
	}

	public void setNombreOperador(String nombreOperador) {
		this.nombreOperador = nombreOperador;
	}

	public String getContactoEmailOperador() {
		return contactoEmailOperador;
	}

	public void setContactoEmailOperador(String contactoEmailOperador) {
		this.contactoEmailOperador = contactoEmailOperador;
	}

	public String getContactoTelefonoOperador() {
		return contactoTelefonoOperador;
	}

	public void setContactoTelefonoOperador(String contactoTelefonoOperador) {
		this.contactoTelefonoOperador = contactoTelefonoOperador;
	}

	public String getRecursosAsignados() {
		return recursosAsignados;
	}

	public void setRecursosAsignados(String recursosAsignados) {
		this.recursosAsignados = recursosAsignados;
	}

	public String getProrrogas() {
		return prorrogas;
	}

	public void setProrrogas(String prorrogas) {
		this.prorrogas = prorrogas;
	}


	public List<ProyectosRegion> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<ProyectosRegion> departamentos) {
		this.departamentos = departamentos;
	}
	
	public Long getDocumentoId() {
		return documentoId;
	}

	public void setDocumentoId(Long documentoId) {
		this.documentoId = documentoId;
	}

	private static final long serialVersionUID = 1L;
}
