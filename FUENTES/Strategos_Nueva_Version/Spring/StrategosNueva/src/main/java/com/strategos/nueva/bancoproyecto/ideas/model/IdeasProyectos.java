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
	
	private static final long serialVersionUID = 1L;
	
}
