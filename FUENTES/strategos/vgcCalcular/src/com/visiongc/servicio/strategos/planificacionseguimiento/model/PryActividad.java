package com.visiongc.servicio.strategos.planificacionseguimiento.model;

import java.io.Serializable;
import java.util.Date;

import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;

public class PryActividad implements Serializable
{
	static final long serialVersionUID = 0L;
	
	private Long actividadId;
	private Long proyectoId;
	private Long indicadorId;
	private String nombre;
	private String descripcion;
	private Date comienzoPlan;
	private Date comienzoReal;
	private Date finPlan;
	private Date finReal;
	private Double duracionPlan;
	private Long unidadId;
	private Integer fila;
	private Integer nivel;
	
	private Boolean compuesta;
	private Date creado;
	private Date modificado;
	private Long creadoId;
	private Long modificadoId;
	private Byte naturaleza;
	private Long claseId;
	private Byte tipoMedicion;
	private Byte crecimiento;
	private Double porcentajeCompletado;
	private Double porcentajeEjecutado;
	private Double porcentajeEsperado;
	
	private String fechaUltimaMedicion;
	private Double alertaZonaAmarilla;
	private Double alertaZonaVerde;
	
	
	 public PryActividad() {}
	

	public PryActividad(Long actividadId, Long proyectoId, Long indicadorId, String nombre, String descripcion,
			Date comienzoPlan, Date comienzoReal, Date finPlan, Date finReal, Double duracionPlan, Long unidadId,
			Integer fila, Integer nivel, Boolean compuesta, Date creado, Date modificado, Long creadoId,
			Long modificadoId, Byte naturaleza, Long claseId, Byte tipoMedicion, Byte crecimiento,
			Double porcentajeCompletado, Double porcentajeEjecutado, Double porcentajeEsperado,
			String fechaUltimaMedicion) {		
		
		this.actividadId = actividadId;
		this.proyectoId = proyectoId;
		this.indicadorId = indicadorId;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.comienzoPlan = comienzoPlan;
		this.comienzoReal = comienzoReal;
		this.finPlan = finPlan;
		this.finReal = finReal;
		this.duracionPlan = duracionPlan;
		this.unidadId = unidadId;
		this.fila = fila;
		this.nivel = nivel;
		this.compuesta = compuesta;
		this.creado = creado;
		this.modificado = modificado;
		this.creadoId = creadoId;
		this.modificadoId = modificadoId;
		this.naturaleza = naturaleza;
		this.claseId = claseId;
		this.tipoMedicion = tipoMedicion;
		this.crecimiento = crecimiento;
		this.porcentajeCompletado = porcentajeCompletado;
		this.porcentajeEjecutado = porcentajeEjecutado;
		this.porcentajeEsperado = porcentajeEsperado;
		this.fechaUltimaMedicion = fechaUltimaMedicion;
	}

	public Long getActividadId() {
		return actividadId;
	}

	public void setActividadId(Long actividadId) {
		this.actividadId = actividadId;
	}

	public Long getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(Long proyectoId) {
		this.proyectoId = proyectoId;
	}

	public Long getIndicadorId() {
		return indicadorId;
	}

	public void setIndicadorId(Long indicadorId) {
		this.indicadorId = indicadorId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getComienzoPlan() {
		return comienzoPlan;
	}

	public void setComienzoPlan(Date comienzoPlan) {
		this.comienzoPlan = comienzoPlan;
	}

	public Date getComienzoReal() {
		return comienzoReal;
	}

	public void setComienzoReal(Date comienzoReal) {
		this.comienzoReal = comienzoReal;
	}

	public Date getFinPlan() {
		return finPlan;
	}

	public void setFinPlan(Date finPlan) {
		this.finPlan = finPlan;
	}

	public Date getFinReal() {
		return finReal;
	}

	public void setFinReal(Date finReal) {
		this.finReal = finReal;
	}

	public Double getDuracionPlan() {
		return duracionPlan;
	}

	public void setDuracionPlan(Double duracionPlan) {
		this.duracionPlan = duracionPlan;
	}

	public Long getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(Long unidadId) {
		this.unidadId = unidadId;
	}

	public Integer getFila() {
		return fila;
	}

	public void setFila(Integer fila) {
		this.fila = fila;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public Boolean getCompuesta() {
		return compuesta;
	}

	public void setCompuesta(Boolean compuesta) {
		this.compuesta = compuesta;
	}

	public Date getCreado() {
		return creado;
	}

	public void setCreado(Date creado) {
		this.creado = creado;
	}

	public Date getModificado() {
		return modificado;
	}

	public void setModificado(Date modificado) {
		this.modificado = modificado;
	}

	public Long getCreadoId() {
		return creadoId;
	}

	public void setCreadoId(Long creadoId) {
		this.creadoId = creadoId;
	}

	public Long getModificadoId() {
		return modificadoId;
	}

	public void setModificadoId(Long modificadoId) {
		this.modificadoId = modificadoId;
	}

	public Byte getNaturaleza() {
		return naturaleza;
	}

	public void setNaturaleza(Byte naturaleza) {
		this.naturaleza = naturaleza;
	}

	public Long getClaseId() {
		return claseId;
	}

	public void setClaseId(Long claseId) {
		this.claseId = claseId;
	}

	public Byte getTipoMedicion() {
		return tipoMedicion;
	}

	public void setTipoMedicion(Byte tipoMedicion) {
		this.tipoMedicion = tipoMedicion;
	}

	public Byte getCrecimiento() {
		return crecimiento;
	}

	public void setCrecimiento(Byte crecimiento) {
		this.crecimiento = crecimiento;
	}

	public Double getPorcentajeCompletado() {
		return porcentajeCompletado;
	}

	public void setPorcentajeCompletado(Double porcentajeCompletado) {
		this.porcentajeCompletado = porcentajeCompletado;
	}

	public Double getPorcentajeEjecutado() {
		return porcentajeEjecutado;
	}

	public void setPorcentajeEjecutado(Double porcentajeEjecutado) {
		this.porcentajeEjecutado = porcentajeEjecutado;
	}

	public Double getPorcentajeEsperado() {
		return porcentajeEsperado;
	}

	public void setPorcentajeEsperado(Double porcentajeEsperado) {
		this.porcentajeEsperado = porcentajeEsperado;
	}

	public String getFechaUltimaMedicion() {
		return fechaUltimaMedicion;
	}

	public void setFechaUltimaMedicion(String fechaUltimaMedicion) {
		this.fechaUltimaMedicion = fechaUltimaMedicion;
	}


	public Double getAlertaZonaAmarilla() {
		return alertaZonaAmarilla;
	}


	public void setAlertaZonaAmarilla(Double alertaZonaAmarilla) {
		this.alertaZonaAmarilla = alertaZonaAmarilla;
	}


	public Double getAlertaZonaVerde() {
		return alertaZonaVerde;
	}


	public void setAlertaZonaVerde(Double alertaZonaVerde) {
		this.alertaZonaVerde = alertaZonaVerde;
	}
	
	
}
