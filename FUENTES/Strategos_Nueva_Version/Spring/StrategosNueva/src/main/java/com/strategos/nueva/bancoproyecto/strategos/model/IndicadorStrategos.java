package com.strategos.nueva.bancoproyecto.strategos.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name="indicador")
public class IndicadorStrategos implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Column(columnDefinition = "serial")
	private Long indicadorId;	
	
	@Column(nullable=true)
	private Long claseId;
	
	@Column(nullable=true)
	private Long organizacionId;
	
	@Size(max=150)
	@Column(nullable=true)
	private String nombre;
	
	@Column(nullable=true)
	private String descripcion;
	
	@Column(nullable=true)
	private Byte naturaleza;
	
	@Column(nullable=true)
	private Byte frecuencia;
	
	@Size(max=50)
	@Column(nullable=true, name="guia")
	private String comportamiento;
	
	@Column(nullable=true)
	private Long unidadId;
	
	@Size(max=100)
	@Column(nullable=true)
	private String codigoEnlace;
	
	@Size(max=150)
	@Column(nullable=true)
	private String nombreCorto;
	
	@Column(nullable=true)
	private Byte prioridad;
	
	@Column(nullable=true, name="constante")
	private Byte mostrarEnArbol;
	
	@Column(nullable=true)
	private String fuente;
	
	@Size(max=10)
	@Column(nullable=true)
	private String orden;
	
	@Column(nullable=true, name="tipo_asociado")
	private Byte indicadorAsociadoTipo;
	
	@Column(nullable=true, name="asociado_id")
	private Long indicadorAsociadoId;
	
	@Column(nullable=true, name="revision")
	private Byte indicadorAsociadoRevision;
	
	@Column(nullable=true, name="read_only")
	private Byte soloLectura;
	
	@Column(nullable=true)
	private Byte caracteristica;
	
	@Column(nullable=true, name="crecimiento_ind")
	private Byte alerta;
	
	@Size(max=10)
	@Column(nullable=true)
	private String fechaUltimaMedicion;
	
	@Column(nullable=true)
	private Double ultimaMedicion;
	
	@Column(nullable=true)
	private Double ultimoProgramado;
	
	@Column(nullable=true, name="tipo")
	private Byte tipoFuncion;
	
	@Column(nullable=true, name="lag_lead")
	private Byte guia;
	
	@Column(nullable=true)
	private Byte corte;
	
	@Size(max=20)
	@Column(nullable=true)
	private String enlaceParcial;
	
	@Column(nullable=true, name="alerta_min_max")
	private Integer alertaMinMax;
	
	@Column(nullable=true, name="alerta_meta_n1")
	private Double alertaMetaZonaVerde;
	
	@Column(nullable=true, name="alerta_meta_n2")
	private Double alertaMetaZonaAmarilla;
	
	@Column(nullable=true, name="alerta_n1_tipo")
	private Byte alertaTipoZonaVerde;
	
	@Column(nullable=true, name="alerta_n2_tipo")
	private Byte alertaTipoZonaAmarilla;
	
	@Column(nullable=true, name="alerta_n1_fv")
	private Byte alertaValorVariableZonaVerde;
	
	@Column(nullable=true, name="alerta_n2_fv")
	private Byte alertaValorVariableZonaAmarilla;
	
	@Column(nullable=true, name="alerta_n1_ind_id")
	private Long alertaIndicadorIdZonaVerde;
	
	@Column(nullable=true, name="alerta_n2_ind_id")
	private Long alertaIndicadorIdZonaAmarilla;
	
	@Column(nullable=true, name="valor_inicial_cero")
	private Byte valorInicialCero;
	
	@Column(nullable=true, name="mascara_decimales")
	private Byte numeroDecimales;
	
	@Column(nullable=true, name="tipo_medicion")
	private Byte tipoCargaMedicion;
	
	@Column(nullable=true, name="psuperior_id")
	private Long parametroSuperiorIndicadorId;
	
	@Column(nullable=true, name="pinferior_id")
	private Long parametroInferiorIndicadorId;
	
	@Column(nullable=true, name="psuperior_vf")
	private Double parametroSuperiorValorFijo;
	
	@Column(nullable=true, name="pinferior_vf")
	private Double parametroInferiorValorFijo;
	
	@Column(nullable=true)
	private String url;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date fechaBloqueoEjecutado;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date fechaBloqueoMeta;
	
	@Column(nullable=true)
	private Byte multidimensional;
	
	@Column(nullable=true, name="resp_notificacion_id")
	private Long responsableNotificacionId;
	
	@Column(nullable=true, name="resp_fijar_meta_id")
	private Long responsableFijarMetaId;
	
	@Column(nullable=true, name="resp_lograr_meta_id")
	private Long responsableLograrMetaId;
	
	@Column(nullable=true, name="resp_seguimiento_id")
	private Long responsableSeguimientoId;
	
	@Column(nullable=true, name="resp_cargar_meta_id")
	private Long responsableCargarMetaId;
	
	@Column(nullable=true, name="resp_cargar_ejecutado_id")
	private Long responsableCargarEjecutadoId;
	
	@Column(nullable=true, name="tipo_suma")
	private Byte tipoSumaMedicion;

	@Column(nullable=true, name="asignar_Inventario")
	private Byte asignarInventario;
	
	@Column(nullable=true)
	private String nombreUnidad;
	
	@Column(nullable=true)
	private Double porcentajeCumplimiento;

	public Long getIndicadorId() {
		return indicadorId;
	}

	public void setIndicadorId(Long indicadorId) {
		this.indicadorId = indicadorId;
	}

	public Long getClaseId() {
		return claseId;
	}

	public void setClaseId(Long claseId) {
		this.claseId = claseId;
	}

	public Long getOrganizacionId() {
		return organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
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

	public Byte getNaturaleza() {
		return naturaleza;
	}

	public void setNaturaleza(Byte naturaleza) {
		this.naturaleza = naturaleza;
	}

	public Byte getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Byte frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getComportamiento() {
		return comportamiento;
	}

	public void setComportamiento(String comportamiento) {
		this.comportamiento = comportamiento;
	}

	public Long getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(Long unidadId) {
		this.unidadId = unidadId;
	}

	public String getCodigoEnlace() {
		return codigoEnlace;
	}

	public void setCodigoEnlace(String codigoEnlace) {
		this.codigoEnlace = codigoEnlace;
	}

	public String getNombreCorto() {
		return nombreCorto;
	}

	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}

	public Byte getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Byte prioridad) {
		this.prioridad = prioridad;
	}

	

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public Byte getIndicadorAsociadoTipo() {
		return indicadorAsociadoTipo;
	}

	public void setIndicadorAsociadoTipo(Byte indicadorAsociadoTipo) {
		this.indicadorAsociadoTipo = indicadorAsociadoTipo;
	}

	public Long getIndicadorAsociadoId() {
		return indicadorAsociadoId;
	}

	public void setIndicadorAsociadoId(Long indicadorAsociadoId) {
		this.indicadorAsociadoId = indicadorAsociadoId;
	}

	public Byte getIndicadorAsociadoRevision() {
		return indicadorAsociadoRevision;
	}

	public void setIndicadorAsociadoRevision(Byte indicadorAsociadoRevision) {
		this.indicadorAsociadoRevision = indicadorAsociadoRevision;
	}

	

	public Byte getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(Byte caracteristica) {
		this.caracteristica = caracteristica;
	}

	public Byte getAlerta() {
		return alerta;
	}

	public void setAlerta(Byte alerta) {
		this.alerta = alerta;
	}

	public String getFechaUltimaMedicion() {
		return fechaUltimaMedicion;
	}

	public void setFechaUltimaMedicion(String fechaUltimaMedicion) {
		this.fechaUltimaMedicion = fechaUltimaMedicion;
	}

	public Double getUltimaMedicion() {
		return ultimaMedicion;
	}

	public void setUltimaMedicion(Double ultimaMedicion) {
		this.ultimaMedicion = ultimaMedicion;
	}

	public Double getUltimoProgramado() {
		return ultimoProgramado;
	}

	public void setUltimoProgramado(Double ultimoProgramado) {
		this.ultimoProgramado = ultimoProgramado;
	}

	public Byte getTipoFuncion() {
		return tipoFuncion;
	}

	public void setTipoFuncion(Byte tipoFuncion) {
		this.tipoFuncion = tipoFuncion;
	}

	

	public Byte getCorte() {
		return corte;
	}

	public void setCorte(Byte corte) {
		this.corte = corte;
	}

	public String getEnlaceParcial() {
		return enlaceParcial;
	}

	public void setEnlaceParcial(String enlaceParcial) {
		this.enlaceParcial = enlaceParcial;
	}

	public Integer getAlertaMinMax() {
		return alertaMinMax;
	}

	public void setAlertaMinMax(Integer alertaMinMax) {
		this.alertaMinMax = alertaMinMax;
	}

	public Double getAlertaMetaZonaVerde() {
		return alertaMetaZonaVerde;
	}

	public void setAlertaMetaZonaVerde(Double alertaMetaZonaVerde) {
		this.alertaMetaZonaVerde = alertaMetaZonaVerde;
	}

	public Double getAlertaMetaZonaAmarilla() {
		return alertaMetaZonaAmarilla;
	}

	public void setAlertaMetaZonaAmarilla(Double alertaMetaZonaAmarilla) {
		this.alertaMetaZonaAmarilla = alertaMetaZonaAmarilla;
	}

	public Byte getAlertaTipoZonaVerde() {
		return alertaTipoZonaVerde;
	}

	public void setAlertaTipoZonaVerde(Byte alertaTipoZonaVerde) {
		this.alertaTipoZonaVerde = alertaTipoZonaVerde;
	}

	public Byte getAlertaTipoZonaAmarilla() {
		return alertaTipoZonaAmarilla;
	}

	public void setAlertaTipoZonaAmarilla(Byte alertaTipoZonaAmarilla) {
		this.alertaTipoZonaAmarilla = alertaTipoZonaAmarilla;
	}

	
	public Byte getAlertaValorVariableZonaVerde() {
		return alertaValorVariableZonaVerde;
	}

	public void setAlertaValorVariableZonaVerde(Byte alertaValorVariableZonaVerde) {
		this.alertaValorVariableZonaVerde = alertaValorVariableZonaVerde;
	}

	public Byte getAlertaValorVariableZonaAmarilla() {
		return alertaValorVariableZonaAmarilla;
	}

	public void setAlertaValorVariableZonaAmarilla(Byte alertaValorVariableZonaAmarilla) {
		this.alertaValorVariableZonaAmarilla = alertaValorVariableZonaAmarilla;
	}

	public Long getAlertaIndicadorIdZonaVerde() {
		return alertaIndicadorIdZonaVerde;
	}

	public void setAlertaIndicadorIdZonaVerde(Long alertaIndicadorIdZonaVerde) {
		this.alertaIndicadorIdZonaVerde = alertaIndicadorIdZonaVerde;
	}

	public Long getAlertaIndicadorIdZonaAmarilla() {
		return alertaIndicadorIdZonaAmarilla;
	}

	public void setAlertaIndicadorIdZonaAmarilla(Long alertaIndicadorIdZonaAmarilla) {
		this.alertaIndicadorIdZonaAmarilla = alertaIndicadorIdZonaAmarilla;
	}

	
	public Byte getNumeroDecimales() {
		return numeroDecimales;
	}

	public void setNumeroDecimales(Byte numeroDecimales) {
		this.numeroDecimales = numeroDecimales;
	}

	public Byte getTipoCargaMedicion() {
		return tipoCargaMedicion;
	}

	public void setTipoCargaMedicion(Byte tipoCargaMedicion) {
		this.tipoCargaMedicion = tipoCargaMedicion;
	}

	public Long getParametroSuperiorIndicadorId() {
		return parametroSuperiorIndicadorId;
	}

	public void setParametroSuperiorIndicadorId(Long parametroSuperiorIndicadorId) {
		this.parametroSuperiorIndicadorId = parametroSuperiorIndicadorId;
	}

	public Long getParametroInferiorIndicadorId() {
		return parametroInferiorIndicadorId;
	}

	public void setParametroInferiorIndicadorId(Long parametroInferiorIndicadorId) {
		this.parametroInferiorIndicadorId = parametroInferiorIndicadorId;
	}

	public Double getParametroSuperiorValorFijo() {
		return parametroSuperiorValorFijo;
	}

	public void setParametroSuperiorValorFijo(Double parametroSuperiorValorFijo) {
		this.parametroSuperiorValorFijo = parametroSuperiorValorFijo;
	}

	public Double getParametroInferiorValorFijo() {
		return parametroInferiorValorFijo;
	}

	public void setParametroInferiorValorFijo(Double parametroInferiorValorFijo) {
		this.parametroInferiorValorFijo = parametroInferiorValorFijo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getFechaBloqueoEjecutado() {
		return fechaBloqueoEjecutado;
	}

	public void setFechaBloqueoEjecutado(Date fechaBloqueoEjecutado) {
		this.fechaBloqueoEjecutado = fechaBloqueoEjecutado;
	}

	public Date getFechaBloqueoMeta() {
		return fechaBloqueoMeta;
	}

	public void setFechaBloqueoMeta(Date fechaBloqueoMeta) {
		this.fechaBloqueoMeta = fechaBloqueoMeta;
	}

	

	public Long getResponsableNotificacionId() {
		return responsableNotificacionId;
	}

	public void setResponsableNotificacionId(Long responsableNotificacionId) {
		this.responsableNotificacionId = responsableNotificacionId;
	}

	public Long getResponsableFijarMetaId() {
		return responsableFijarMetaId;
	}

	public void setResponsableFijarMetaId(Long responsableFijarMetaId) {
		this.responsableFijarMetaId = responsableFijarMetaId;
	}

	public Long getResponsableLograrMetaId() {
		return responsableLograrMetaId;
	}

	public void setResponsableLograrMetaId(Long responsableLograrMetaId) {
		this.responsableLograrMetaId = responsableLograrMetaId;
	}

	public Long getResponsableSeguimientoId() {
		return responsableSeguimientoId;
	}

	public void setResponsableSeguimientoId(Long responsableSeguimientoId) {
		this.responsableSeguimientoId = responsableSeguimientoId;
	}

	public Long getResponsableCargarMetaId() {
		return responsableCargarMetaId;
	}

	public void setResponsableCargarMetaId(Long responsableCargarMetaId) {
		this.responsableCargarMetaId = responsableCargarMetaId;
	}

	public Long getResponsableCargarEjecutadoId() {
		return responsableCargarEjecutadoId;
	}

	public void setResponsableCargarEjecutadoId(Long responsableCargarEjecutadoId) {
		this.responsableCargarEjecutadoId = responsableCargarEjecutadoId;
	}

	public Byte getTipoSumaMedicion() {
		return tipoSumaMedicion;
	}

	public void setTipoSumaMedicion(Byte tipoSumaMedicion) {
		this.tipoSumaMedicion = tipoSumaMedicion;
	}
	
	public Byte getMostrarEnArbol() {
		return mostrarEnArbol;
	}

	public void setMostrarEnArbol(Byte mostrarEnArbol) {
		this.mostrarEnArbol = mostrarEnArbol;
	}

	public Byte getSoloLectura() {
		return soloLectura;
	}

	public void setSoloLectura(Byte soloLectura) {
		this.soloLectura = soloLectura;
	}

	public Byte getGuia() {
		return guia;
	}

	public void setGuia(Byte guia) {
		this.guia = guia;
	}

	public Byte getValorInicialCero() {
		return valorInicialCero;
	}

	public void setValorInicialCero(Byte valorInicialCero) {
		this.valorInicialCero = valorInicialCero;
	}

	public Byte getMultidimensional() {
		return multidimensional;
	}

	public void setMultidimensional(Byte multidimensional) {
		this.multidimensional = multidimensional;
	}

	public Byte getAsignarInventario() {
		return asignarInventario;
	}

	public void setAsignarInventario(Byte asignarInventario) {
		this.asignarInventario = asignarInventario;
	}

	public String getNombreUnidad() {
		return nombreUnidad;
	}

	public void setNombreUnidad(String nombreUnidad) {
		this.nombreUnidad = nombreUnidad;
	}

	public Double getPorcentajeCumplimiento() {
		return porcentajeCumplimiento;
	}

	public void setPorcentajeCumplimiento(Double porcentajeCumplimiento) {
		this.porcentajeCumplimiento = porcentajeCumplimiento;
	}




	private static final long serialVersionUID = 1L;
}
