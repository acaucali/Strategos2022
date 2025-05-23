package com.visiongc.app.strategos.iniciativas.model;

import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectiva;
import com.visiongc.app.strategos.planes.model.IniciativaPlan;
import com.visiongc.app.strategos.planes.model.util.AlertaObjetivo;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.arboles.NodoArbol;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Iniciativa implements Serializable, NodoArbol {
	static final long serialVersionUID = 0L;
	private Long iniciativaId;
	private String nombre;
	private Long proyectoId;
	private Byte tipoAlerta;
	private Double alertaZonaVerde;
	private Double alertaZonaAmarilla;
	private Long organizacionId;
	private Byte frecuencia;
	private String nombreLargo;
	private String enteEjecutor;
	private Byte naturaleza;
	private Long responsableFijarMetaId;
	private Long responsableLograrMetaId;
	private Long responsableSeguimientoId;
	private Long responsableCargarMetaId;
	private Long responsableCargarEjecutadoId;
	private OrganizacionStrategos organizacion;
	private Responsable responsableFijarMeta;
	private Responsable responsableLograrMeta;
	private Responsable responsableSeguimiento;
	private Responsable responsableCargarMeta;
	private Responsable responsableCargarEjecutado;
	private MemoIniciativa memoIniciativa;
	private Set<ResultadoEspecificoIniciativa> resultadosEspecificosIniciativa;
	private String descripcion;
	private Boolean soloLectura;
	private Set<IniciativaPerspectiva> iniciativaPerspectivas;
	private Long claseId;
	private Byte alerta;
	private NodoArbol nodoArbolPadre;
	private Collection nodoArbolHijos;
	private boolean nodoArbolHijosCargados;
	private List<PryActividad> listaActividades;
	private Double porcentajeCompletado;
	private String fechaUltimaMedicion;
	private Set<IniciativaPlan> iniciativaPlanes;
	private List<Iniciativa> iniciativasAsociadas;
	private Set<IndicadorIniciativa> iniciativaIndicadores;
	private Double porcentajeEsperado;
	private Double ultimaMedicion;
	private Byte tipoMedicion;
	private Date historicoDate;
	private Byte condicion;
	private Long estatusId;
	private Long tipoId;
	private IniciativaEstatus estatus;
	private String anioFormulacion;
	private TipoProyecto tipoProyecto;

//Campos nuevos
	private String responsableProyecto;
	private String cargoResponsable;
	private String organizacionesInvolucradas;
	private String objetivoEstrategico;
	private String fuenteFinanciacion;
	private String montoFinanciamiento;
	private String iniciativaEstrategica;
	private String liderIniciativa;
	private String tipoIniciativa;
	private String poblacionBeneficiada;
	private String contexto;
	private String definicionProblema;
	private String alcance;
	private String objetivoGeneral;
	private String objetivoEspecificos;

	private String justificacion;
	private Date fechaInicio;
	private Date fechaFin;
	private String montoTotal;
	private String montoMonedaExt;
	private String situacionPresupuestaria;
	private String hitos;
	private String sector;
	private Date fechaActaInicio;
	private String gerenciaGeneralRes;
	private String codigoSipe;
	private String proyectoPresupAso;
	private String estado;
	private String municipio;
	private String parroquia;
	private String direccionProyecto;
	private String objetivoHistorico;
	private String objetivoNacional;
	private String objetivoEstrategicoPV;
	private String objetivoGeneralPV;
	private String objetivoEspecifico;
	private String programa;
	private String problemas;
	private String causas;
	private String lineasEstrategicas;
	
	private String gerenteProyectoNombre;
	private String gerenteProyectoCedula;
	private String gerenteProyectoEmail;
	private String gerenteProyectoTelefono;	
	private String responsableTecnicoNombre;
	private String responsableTecnicoCedula;
	private String responsableTecnicoEmail;
	private String responsableTecnicoTelefono;	
	private String responsableRegistradorNombre;
	private String responsableRegistradorCedula;
	private String responsableRegistradorEmail;
	private String responsableRegistradorTelefono;	
	private String responsableAdministrativoNombre;
	private String responsableAdministrativoCedula;
	private String responsableAdministrativoEmail;
	private String responsableAdministrativoTelefono;	
	private String responsableAdminContratosNombre;
	private String responsableAdminContratosCedula;
	private String responsableAdminContratosEmail;
	private String responsableAdminContratosTelefono;
	
	private Long faseId;
	
	private String alineacionPDMP;
	private String alineacionODS;
	private String coberturaGeografica;
	private String impactoCiudadania;	
	private String implementadorRecursos;
	private String dependenciaResponsable;
	private String sostenibilidad;
	private String dependenciasCompetentes;

	public String getSostenibilidad() {
		return sostenibilidad;
	}

	public void setSostenibilidad(String sostenibilidad) {
		this.sostenibilidad = sostenibilidad;
	}

	public String getDependenciasCompetentes() {
		return dependenciasCompetentes;
	}

	public void setDependenciasCompetentes(String dependenciasCompetentes) {
		this.dependenciasCompetentes = dependenciasCompetentes;
	}

	public String getAlineacionPDMP() {
		return alineacionPDMP;
	}

	public void setAlineacionPDMP(String alineacionPDMP) {
		this.alineacionPDMP = alineacionPDMP;
	}

	public String getAlineacionODS() {
		return alineacionODS;
	}

	public void setAlineacionODS(String alineacionODS) {
		this.alineacionODS = alineacionODS;
	}

	public String getCoberturaGeografica() {
		return coberturaGeografica;
	}

	public void setCoberturaGeografica(String coberturaGeografica) {
		this.coberturaGeografica = coberturaGeografica;
	}

	public String getImpactoCiudadania() {
		return impactoCiudadania;
	}

	public void setImpactoCiudadania(String impactoCiudadania) {
		this.impactoCiudadania = impactoCiudadania;
	}

	public String getImplementadorRecursos() {
		return implementadorRecursos;
	}

	public void setImplementadorRecursos(String implementadorRecursos) {
		this.implementadorRecursos = implementadorRecursos;
	}

	public String getDependenciaResponsable() {
		return dependenciaResponsable;
	}

	public void setDependenciaResponsable(String dependenciaResponsable) {
		this.dependenciaResponsable = dependenciaResponsable;
	}

	public Byte getPartidas() {
		return partidas;
	}

	public void setPartidas(Byte partidas) {
		this.partidas = partidas;
	}

	public Long getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(Long unidadId) {
		this.unidadId = unidadId;
	}

	private String codigoIniciativa;
	private Long cargoId;

	// estos son campos temporales, no tienen mapeo de base de datos
	private String fechaesperado;
	private int dias;
	private String observacion;
	private String organizacionNombre;
	private Byte partidas;
	private Long unidadId;

	public Iniciativa(Long iniciativaId, Long organizacionId, String nombre, Byte naturaleza, Byte frecuencia) {
		this.iniciativaId = iniciativaId;
		this.organizacionId = organizacionId;
		this.nombre = nombre;

		this.naturaleza = naturaleza;
		this.frecuencia = frecuencia;
	}

	public Iniciativa(Long iniciativaId, Long organizacionId, String nombre, Byte naturaleza, Byte frecuencia,
			Double porcentajeCompletado) {
		this.iniciativaId = iniciativaId;
		this.organizacionId = organizacionId;
		this.nombre = nombre;
		this.naturaleza = naturaleza;
		this.frecuencia = frecuencia;
		this.porcentajeCompletado = porcentajeCompletado;
	}

	public Iniciativa(Long iniciativaId) {
		this.iniciativaId = iniciativaId;
	}

	public Iniciativa() {
	}

	public Long getTipoId() {
		return tipoId;
	}

	public void setTipoId(Long tipoId) {
		this.tipoId = tipoId;
	}

	public Long getIniciativaId() {
		return iniciativaId;
	}

	public void setIniciativaId(Long iniciativaId) {
		this.iniciativaId = iniciativaId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getProyectoId() {
		return proyectoId;
	}

	public void setProyectoId(Long proyectoId) {
		this.proyectoId = proyectoId;
	}

	public Byte getTipoAlerta() {
		return tipoAlerta;
	}

	public void setTipoAlerta(Byte tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}

	public Double getAlertaZonaVerde() {
		return alertaZonaVerde;
	}

	public void setAlertaZonaVerde(Double alertaZonaVerde) {
		this.alertaZonaVerde = alertaZonaVerde;
	}

	public Double getAlertaZonaAmarilla() {
		return alertaZonaAmarilla;
	}

	public void setAlertaZonaAmarilla(Double alertaZonaAmarilla) {
		this.alertaZonaAmarilla = alertaZonaAmarilla;
	}

	public Long getOrganizacionId() {
		return organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
	}

	public Byte getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Byte frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getFrecuenciaNombre() {
		if (frecuencia != null) {
			return Frecuencia.getNombre(frecuencia.byteValue());
		}
		return "";
	}

	public String getNombreLargo() {
		return nombreLargo;
	}

	public void setNombreLargo(String nombreLargo) {
		this.nombreLargo = nombreLargo;
	}

	public String getEnteEjecutor() {
		return enteEjecutor;
	}

	public void setEnteEjecutor(String enteEjecutor) {
		this.enteEjecutor = enteEjecutor;
	}

	public Byte getNaturaleza() {
		return naturaleza;
	}

	public void setNaturaleza(Byte naturaleza) {
		this.naturaleza = naturaleza;
	}

	public String getNaturalezaNombre() {
		if (naturaleza != null) {
			return Naturaleza.getNombre(naturaleza.byteValue());
		}
		return "";
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

	public OrganizacionStrategos getOrganizacion() {
		return organizacion;
	}

	public void setOrganizacion(OrganizacionStrategos organizacion) {
		this.organizacion = organizacion;
	}

	public MemoIniciativa getMemoIniciativa() {
		return memoIniciativa;
	}

	public Responsable getResponsableFijarMeta() {
		return responsableFijarMeta;
	}

	public void setResponsableFijarMeta(Responsable responsableFijarMeta) {
		this.responsableFijarMeta = responsableFijarMeta;
	}

	public Responsable getResponsableLograrMeta() {
		return responsableLograrMeta;
	}

	public void setResponsableLograrMeta(Responsable responsableLograrMeta) {
		this.responsableLograrMeta = responsableLograrMeta;
	}

	public Responsable getResponsableSeguimiento() {
		return responsableSeguimiento;
	}

	public void setResponsableSeguimiento(Responsable responsableSeguimiento) {
		this.responsableSeguimiento = responsableSeguimiento;
	}

	public Responsable getResponsableCargarMeta() {
		return responsableCargarMeta;
	}

	public void setResponsableCargarMeta(Responsable responsableCargarMeta) {
		this.responsableCargarMeta = responsableCargarMeta;
	}

	public Responsable getResponsableCargarEjecutado() {
		return responsableCargarEjecutado;
	}

	public void setResponsableCargarEjecutado(Responsable responsableCargarEjecutado) {
		this.responsableCargarEjecutado = responsableCargarEjecutado;
	}

	public void setMemoIniciativa(MemoIniciativa memoIniciativa) {
		this.memoIniciativa = memoIniciativa;
	}

	public Set<ResultadoEspecificoIniciativa> getResultadosEspecificosIniciativa() {
		return resultadosEspecificosIniciativa;
	}

	public void setResultadosEspecificosIniciativa(Set<ResultadoEspecificoIniciativa> resultadosEspecificosIniciativa) {
		this.resultadosEspecificosIniciativa = resultadosEspecificosIniciativa;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<IniciativaPerspectiva> getIniciativaPerspectivas() {
		return iniciativaPerspectivas;
	}

	public void setIniciativaPerspectivas(Set<IniciativaPerspectiva> iniciativaPerspectivas) {
		this.iniciativaPerspectivas = iniciativaPerspectivas;
	}

	public Long getClaseId() {
		return claseId;
	}

	public void setClaseId(Long claseId) {
		this.claseId = claseId;
	}

	public Byte getAlerta() {
		return alerta;
	}

	public void setAlerta(Byte alerta) {
		this.alerta = alerta;
	}

	public Boolean getSoloLectura() {
		return soloLectura;
	}

	public void setSoloLectura(Boolean soloLectura) {
		this.soloLectura = soloLectura;
	}

	public Collection getNodoArbolHijos() {
		return nodoArbolHijos;
	}

	public boolean getNodoArbolHijosCargados() {
		return nodoArbolHijosCargados;
	}

	public String getNodoArbolId() {
		return iniciativaId.toString();
	}

	public String getNodoArbolNombre() {
		return nombre;
	}

	public String getNodoArbolNombreCampoId() {
		return "iniciativaId";
	}

	public String getNodoArbolNombreCampoNombre() {
		return "nombre";
	}

	public String getNodoArbolNombreCampoPadreId() {
		return null;
	}

	public String getNodoArbolNombreImagen(Byte tipo) {
		if (tipo.byteValue() == 1) {
			return "Iniciativa" + AlertaObjetivo.getNombreImagen(alerta);
		}
		return "";
	}

	public String getNodoArbolNombreToolTipImagen(Byte tipo) {
		return "";
	}

	public void setNodoArbolPadre(NodoArbol nodoArbolPadre) {
		this.nodoArbolPadre = nodoArbolPadre;
	}

	public NodoArbol getNodoArbolPadre() {
		return nodoArbolPadre;
	}

	public String getNodoArbolPadreId() {
		return null;
	}

	public void setNodoArbolHijos(Collection nodoArbolHijos) {
		this.nodoArbolHijos = nodoArbolHijos;
	}

	public void setNodoArbolHijosCargados(boolean cargados) {
		nodoArbolHijosCargados = cargados;
	}

	public void setNodoArbolNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPorcentajeCompletado() {
		return porcentajeCompletado;
	}

	public void setPorcentajeCompletado(Double porcentajeCompletado) {
		this.porcentajeCompletado = porcentajeCompletado;
	}

	public String getPorcentajeCompletadoFormateado() {
		return VgcFormatter.formatearNumero(porcentajeCompletado, 2);
	}

	public Double getPorcentajeEsperado() {
		return porcentajeEsperado;
	}

	public String getPorcentajeEsperadoFormateado() {
		return VgcFormatter.formatearNumero(porcentajeEsperado, 2);
	}

	public void setPorcentajeEsperado(Double porcentajeEsperado) {
		this.porcentajeEsperado = porcentajeEsperado;
	}

	public Double getUltimaMedicion() {
		return ultimaMedicion;
	}

	public void setUltimaMedicion(Double ultimaMedicion) {
		this.ultimaMedicion = ultimaMedicion;
	}

	public String getUltimaMedicionFormateado() {
		return VgcFormatter.formatearNumero(ultimaMedicion, 2);
	}

	public List<PryActividad> getListaActividades() {
		return listaActividades;
	}

	public void setListaActividades(List<PryActividad> listaActividades) {
		this.listaActividades = listaActividades;
	}

	public String getFechaUltimaMedicion() {
		return fechaUltimaMedicion;
	}

	public void setFechaUltimaMedicion(String fechaUltimaMedicion) {
		this.fechaUltimaMedicion = fechaUltimaMedicion;
	}

	public Set<IniciativaPlan> getIniciativaPlanes() {
		return iniciativaPlanes;
	}

	public void setIniciativaPlanes(Set<IniciativaPlan> iniciativaPlanes) {
		this.iniciativaPlanes = iniciativaPlanes;
	}

	public List<Iniciativa> getIniciativasAsociadas() {
		return iniciativasAsociadas;
	}

	public void setIniciativasAsociadas(List<Iniciativa> iniciativasAsociadas) {
		this.iniciativasAsociadas = iniciativasAsociadas;
	}

	public Set<IndicadorIniciativa> getIniciativaIndicadores() {
		return iniciativaIndicadores;
	}

	public void setIniciativaIndicadores(Set<IndicadorIniciativa> iniciativaIndicadores) {
		this.iniciativaIndicadores = iniciativaIndicadores;
	}

	public String getFechaesperado() {
		return fechaesperado;
	}

	public void setFechaesperado(String fechaesperado) {
		this.fechaesperado = fechaesperado;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getOrganizacionNombre() {
		return organizacionNombre;
	}

	public void setOrganizacionNombre(String organizacionNombre) {
		this.organizacionNombre = organizacionNombre;
	}

	public Long getIndicadorId(Byte tipo) {
		for (Iterator<IndicadorIniciativa> iter = this.iniciativaIndicadores.iterator(); iter.hasNext();) {
			IndicadorIniciativa iniciativaIndicadores = (IndicadorIniciativa) iter.next();
			if (iniciativaIndicadores.getTipo().byteValue() == tipo.byteValue()) {
				return iniciativaIndicadores.getPk().getIndicadorId();
			}
		}
		return null;
	}
	
	public List<Long> getIndicadoresId(Byte tipo) {
		List<Long> indicadoresId = new ArrayList<Long>();
		for (Iterator<IndicadorIniciativa> iter = this.iniciativaIndicadores.iterator(); iter.hasNext();) {
			IndicadorIniciativa iniciativaIndicadores = (IndicadorIniciativa) iter.next();
			if (iniciativaIndicadores.getTipo().byteValue() == tipo.byteValue()) {
				indicadoresId.add(iniciativaIndicadores.getPk().getIndicadorId());
				
				
			}
		}
		
		return indicadoresId;
	}

	public void setIndicadorId(Long indicadorId, Byte tipo) {
		boolean indicadorExiste = false;

		if (this.iniciativaIndicadores != null) {
			for (Iterator<IndicadorIniciativa> iter = this.iniciativaIndicadores.iterator(); iter.hasNext();) {
				IndicadorIniciativa iniciativaIndicadores = (IndicadorIniciativa) iter.next();
				if (iniciativaIndicadores.getTipo().byteValue() == tipo.byteValue()) {
					indicadorExiste = true;
					iniciativaIndicadores.getPk().setIndicadorId(indicadorId);
					iniciativaIndicadores.getPk().setIniciativaId(iniciativaId);
					break;
				}
			}
		}

		if (!indicadorExiste) {
			IndicadorIniciativaPK pk = new IndicadorIniciativaPK(iniciativaId, indicadorId);
			IndicadorIniciativa indicadorIniciativa = new IndicadorIniciativa();
			indicadorIniciativa.setTipo(tipo);
			indicadorIniciativa.setPk(pk);
			if (this.iniciativaIndicadores == null) {
				this.iniciativaIndicadores = new HashSet();
			}
			this.iniciativaIndicadores.add(indicadorIniciativa);
		}
	}

	public Byte getTipoMedicion() {
		return tipoMedicion;
	}

	public void setTipoMedicion(Byte tipoMedicion) {
		this.tipoMedicion = tipoMedicion;
	}

	public Date getHistoricoDate() {
		return historicoDate;
	}

	public void setHistoricoDate(Date historicoDate) {
		this.historicoDate = historicoDate;
	}

	public Byte getCondicion() {
		return this.condicion = Byte.valueOf(historicoDate != null ? HistoricoType.getFiltroHistoricoMarcado()
				: HistoricoType.getFiltroHistoricoNoMarcado());
	}

	public void setCondicion(Byte condicion) {
		this.condicion = condicion;
	}

	public Long getEstatusId() {
		return estatusId;
	}

	public void setEstatusId(Long estatusId) {
		this.estatusId = estatusId;
	}

	public IniciativaEstatus getEstatus() {
		return estatus;
	}

	public TipoProyecto getTipoProyecto() {
		return tipoProyecto;
	}

	public void setTipoProyecto(TipoProyecto tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
	}

	public void setEstatus(IniciativaEstatus estatus) {
		this.estatus = estatus;
	}

	public String getAnioFormulacion() {
		return anioFormulacion;
	}

	public void setAnioFormulacion(String anio) {
		this.anioFormulacion = anio;
	}

	public String getResponsableProyecto() {
		return responsableProyecto;
	}

	public void setResponsableProyecto(String responsableProyecto) {
		this.responsableProyecto = responsableProyecto;
	}

	public String getCargoResponsable() {
		return cargoResponsable;
	}

	public void setCargoResponsable(String cargoResponsable) {
		this.cargoResponsable = cargoResponsable;
	}

	public String getOrganizacionesInvolucradas() {
		return organizacionesInvolucradas;
	}

	public void setOrganizacionesInvolucradas(String organizacionesInvolucradas) {
		this.organizacionesInvolucradas = organizacionesInvolucradas;
	}

	public String getObjetivoEstrategico() {
		return objetivoEstrategico;
	}

	public void setObjetivoEstrategico(String objetivoEstrategico) {
		this.objetivoEstrategico = objetivoEstrategico;
	}

	public String getFuenteFinanciacion() {
		return fuenteFinanciacion;
	}

	public void setFuenteFinanciacion(String fuenteFinanciacion) {
		this.fuenteFinanciacion = fuenteFinanciacion;
	}

	public String getMontoFinanciamiento() {
		return montoFinanciamiento;
	}

	public void setMontoFinanciamiento(String montoFinanciamiento) {
		this.montoFinanciamiento = montoFinanciamiento;
	}

	public String getIniciativaEstrategica() {
		return iniciativaEstrategica;
	}

	public void setIniciativaEstrategica(String iniciativaEstrategica) {
		this.iniciativaEstrategica = iniciativaEstrategica;
	}

	public String getLiderIniciativa() {
		return liderIniciativa;
	}

	public void setLiderIniciativa(String liderIniciativa) {
		this.liderIniciativa = liderIniciativa;
	}

	public String getTipoIniciativa() {
		return tipoIniciativa;
	}

	public void setTipoIniciativa(String tipoIniciativa) {
		this.tipoIniciativa = tipoIniciativa;
	}

	public String getPoblacionBeneficiada() {
		return poblacionBeneficiada;
	}

	public void setPoblacionBeneficiada(String poblacionBeneficiada) {
		this.poblacionBeneficiada = poblacionBeneficiada;
	}

	public String getContexto() {
		return contexto;
	}

	public void setContexto(String contexto) {
		this.contexto = contexto;
	}

	public String getDefinicionProblema() {
		return definicionProblema;
	}

	public void setDefinicionProblema(String definicionProblema) {
		this.definicionProblema = definicionProblema;
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

	public String getObjetivoEspecificos() {
		return objetivoEspecificos;
	}

	public void setObjetivoEspecificos(String objetivoEspecificos) {
		this.objetivoEspecificos = objetivoEspecificos;
	}

	public int compareTo(Object o) {
		Iniciativa or = (Iniciativa) o;
		return getIniciativaId().compareTo(or.getIniciativaId());
	}

	public Iniciativa copyFrom(Iniciativa iniciativa) {
		Iniciativa iniciativaCopia = new Iniciativa();
		iniciativaCopia.setIniciativaId(new Long(0L));
		iniciativaCopia.setMemoIniciativa(new MemoIniciativa());
		iniciativaCopia.setResultadosEspecificosIniciativa(new HashSet());
		iniciativaCopia.setIniciativaPerspectivas(new HashSet());
		iniciativaCopia.setIniciativaPlanes(new HashSet());

		iniciativaCopia.setNombre(iniciativa.getNombre());
		iniciativaCopia.setProyectoId(iniciativa.getProyectoId());
		iniciativaCopia.setTipoAlerta(iniciativa.getTipoAlerta());
		iniciativaCopia.setAlertaZonaVerde(iniciativa.getAlertaZonaVerde());
		iniciativaCopia.setAlertaZonaAmarilla(iniciativa.getAlertaZonaAmarilla());
		iniciativaCopia.setOrganizacionId(iniciativa.getOrganizacionId());
		iniciativaCopia.setFrecuencia(iniciativa.getFrecuencia());
		iniciativaCopia.setNombreLargo(iniciativa.getNombreLargo());
		iniciativaCopia.setEnteEjecutor(iniciativa.getEnteEjecutor());
		iniciativaCopia.setNaturaleza(iniciativa.getNaturaleza());
		iniciativaCopia.setResponsableFijarMetaId(iniciativa.getResponsableFijarMetaId());
		iniciativaCopia.setResponsableLograrMetaId(iniciativa.getResponsableLograrMetaId());
		iniciativaCopia.setResponsableSeguimientoId(iniciativa.getResponsableSeguimientoId());
		iniciativaCopia.setResponsableCargarMetaId(iniciativa.getResponsableCargarMetaId());
		iniciativaCopia.setResponsableCargarEjecutadoId(iniciativa.getResponsableCargarEjecutadoId());
		iniciativaCopia.setDescripcion(iniciativa.getDescripcion());
		iniciativaCopia.setSoloLectura(iniciativa.getSoloLectura());
		iniciativaCopia.setClaseId(iniciativa.getClaseId());
		iniciativaCopia.setAlerta(iniciativa.getAlerta());
		iniciativaCopia.setPorcentajeCompletado(iniciativa.getPorcentajeCompletado());
		iniciativaCopia.setFechaUltimaMedicion(iniciativa.getFechaUltimaMedicion());
		iniciativaCopia.setPorcentajeEsperado(iniciativa.getPorcentajeEsperado());
		iniciativaCopia.setUltimaMedicion(iniciativa.getUltimaMedicion());
		iniciativaCopia.setTipoMedicion(iniciativa.getTipoMedicion());
		iniciativaCopia.setHistoricoDate(iniciativa.getHistoricoDate());
		iniciativaCopia.setCondicion(iniciativa.getCondicion());
		iniciativaCopia.setEstatusId(iniciativa.getEstatusId());
		iniciativaCopia.setTipoId(iniciativa.getTipoId());
		iniciativaCopia.setAnioFormulacion(iniciativa.getAnioFormulacion());

		iniciativaCopia.setResponsableProyecto(iniciativa.getResponsableProyecto());
		iniciativaCopia.setCargoResponsable(iniciativa.getCargoResponsable());
		iniciativaCopia.setOrganizacionesInvolucradas(iniciativa.getOrganizacionesInvolucradas());
		iniciativaCopia.setObjetivoEstrategico(iniciativa.getObjetivoEstrategico());
		iniciativaCopia.setFuenteFinanciacion(iniciativa.getFuenteFinanciacion());
		iniciativaCopia.setMontoFinanciamiento(iniciativa.getMontoFinanciamiento());
		iniciativaCopia.setIniciativaEstrategica(iniciativa.getIniciativaEstrategica());
		iniciativaCopia.setLiderIniciativa(iniciativa.getLiderIniciativa());
		iniciativaCopia.setTipoIniciativa(iniciativa.getTipoIniciativa());
		iniciativaCopia.setPoblacionBeneficiada(iniciativa.getPoblacionBeneficiada());
		iniciativaCopia.setContexto(iniciativa.getContexto());
		iniciativaCopia.setDefinicionProblema(iniciativa.getDefinicionProblema());
		iniciativaCopia.setAlcance(iniciativa.getAlcance());
		iniciativaCopia.setObjetivoGeneral(iniciativa.getObjetivoGeneral());
		iniciativaCopia.setObjetivoEspecificos(iniciativa.getObjetivoEspecificos());

		iniciativaCopia.setCodigoIniciativa(iniciativa.getCodigoIniciativa());
		iniciativaCopia.setCargoId(iniciativa.getCargoId());
		iniciativaCopia.setUnidadId(iniciativa.getUnidadId());

		iniciativaCopia.setJustificacion(iniciativa.getJustificacion());
		iniciativaCopia.setFechaInicio(iniciativa.getFechaInicio());
		iniciativaCopia.setFechaFin(iniciativa.getFechaFin());
		iniciativaCopia.setMontoTotal(iniciativa.getMontoTotal());
		iniciativaCopia.setMontoMonedaExt(iniciativa.getMontoMonedaExt());
		iniciativaCopia.setSituacionPresupuestaria(iniciativa.getSituacionPresupuestaria());
		iniciativaCopia.setHitos(iniciativa.getHitos());
		iniciativaCopia.setSector(iniciativa.getSector());
		iniciativaCopia.setFechaActaInicio(iniciativa.getFechaActaInicio());
		iniciativaCopia.setGerenciaGeneralRes(iniciativa.getGerenciaGeneralRes());
		iniciativaCopia.setCodigoSipe(iniciativa.getCodigoSipe());
		iniciativaCopia.setProyectoPresupAso(iniciativa.getProyectoPresupAso());
		iniciativaCopia.setEstado(iniciativa.getEstado());
		iniciativaCopia.setMunicipio(iniciativa.getMunicipio());
		iniciativaCopia.setParroquia(iniciativa.getParroquia());
		iniciativaCopia.setDireccionProyecto(iniciativa.getDireccionProyecto());
		iniciativaCopia.setObjetivoHistorico(iniciativa.getObjetivoHistorico());
		iniciativaCopia.setObjetivoNacional(iniciativa.getObjetivoNacional());
		iniciativaCopia.setObjetivoEstrategicoPV(iniciativa.getObjetivoEstrategicoPV());
		iniciativaCopia.setObjetivoGeneralPV(iniciativa.getObjetivoGeneralPV());
		iniciativaCopia.setObjetivoEspecifico(iniciativa.getObjetivoEspecifico());
		iniciativaCopia.setPrograma(iniciativa.getPrograma());
		iniciativaCopia.setProblemas(iniciativa.getProblemas());
		iniciativaCopia.setCausas(iniciativa.getCausas());
		iniciativaCopia.setLineasEstrategicas(iniciativa.getLineasEstrategicas());
		
		iniciativaCopia.setGerenteProyectoNombre(iniciativa.getGerenteProyectoNombre());
		iniciativaCopia.setGerenteProyectoCedula(iniciativa.getGerenteProyectoCedula());
		iniciativaCopia.setGerenteProyectoEmail(iniciativa.getGerenteProyectoEmail());
		iniciativaCopia.setGerenteProyectoTelefono(iniciativa.getGerenteProyectoTelefono());
		
		iniciativaCopia.setResponsableTecnicoNombre(iniciativa.getResponsableTecnicoNombre());
		iniciativaCopia.setResponsableTecnicoCedula(iniciativa.getResponsableTecnicoCedula());
		iniciativaCopia.setResponsableTecnicoEmail(iniciativa.getResponsableTecnicoEmail());
		iniciativaCopia.setResponsableTecnicoTelefono(iniciativa.getResponsableTecnicoTelefono());
		
		iniciativaCopia.setResponsableRegistradorNombre(iniciativa.getResponsableTecnicoNombre());
		iniciativaCopia.setResponsableRegistradorCedula(iniciativa.getResponsableTecnicoCedula());
		iniciativaCopia.setResponsableRegistradorEmail(iniciativa.getResponsableTecnicoEmail());
		iniciativaCopia.setResponsableRegistradorTelefono(iniciativa.getResponsableTecnicoTelefono());
		
		iniciativaCopia.setResponsableAdministrativoNombre(iniciativa.getResponsableAdministrativoNombre());		
		iniciativaCopia.setResponsableAdministrativoCedula(iniciativa.getResponsableAdministrativoCedula());
		iniciativaCopia.setResponsableAdministrativoEmail(iniciativa.getResponsableAdministrativoEmail());
		iniciativaCopia.setResponsableAdministrativoTelefono(iniciativa.getResponsableAdministrativoTelefono());
		
		iniciativaCopia.setResponsableAdminContratosNombre(iniciativa.getResponsableAdminContratosNombre());
		iniciativaCopia.setResponsableAdminContratosCedula(iniciativa.getResponsableAdminContratosCedula());
		iniciativaCopia.setResponsableAdminContratosEmail(iniciativa.getResponsableAdminContratosEmail());
		iniciativaCopia.setResponsableAdminContratosTelefono(iniciativa.getResponsableAdminContratosTelefono());
		
		iniciativaCopia.setFaseId(iniciativa.getFaseId());

		if (iniciativa.getMemoIniciativa() != null) {
			if (iniciativa.getMemoIniciativa().getDescripcion() != null) {
				iniciativaCopia.getMemoIniciativa().setDescripcion(iniciativa.getMemoIniciativa().getDescripcion());
			} else
				iniciativaCopia.getMemoIniciativa().setDescripcion(null);
			if (iniciativa.getMemoIniciativa().getResultado() != null) {
				iniciativaCopia.getMemoIniciativa().setResultado(iniciativa.getMemoIniciativa().getResultado());
			} else {
				iniciativaCopia.getMemoIniciativa().setResultado(null);
			}
		}
		iniciativaCopia.getResultadosEspecificosIniciativa().clear();
		for (Iterator<ResultadoEspecificoIniciativa> iter = iniciativa.getResultadosEspecificosIniciativa()
				.iterator(); iter.hasNext();) {
			ResultadoEspecificoIniciativa resultadoEspecificoIniciativa = (ResultadoEspecificoIniciativa) iter.next();
			iniciativaCopia.getResultadosEspecificosIniciativa().add(resultadoEspecificoIniciativa);
		}

		for (Iterator<IniciativaPerspectiva> iter = iniciativa.getIniciativaPerspectivas().iterator(); iter
				.hasNext();) {
			IniciativaPerspectiva iniciativaPerspectiva = (IniciativaPerspectiva) iter.next();
			iniciativaCopia.getIniciativaPerspectivas().add(iniciativaPerspectiva);
		}

		for (Iterator<IniciativaPlan> iter = iniciativa.getIniciativaPlanes().iterator(); iter.hasNext();) {
			IniciativaPlan iniciativaPlan = (IniciativaPlan) iter.next();
			iniciativaCopia.getIniciativaPlanes().add(iniciativaPlan);
		}

		return iniciativaCopia;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Iniciativa other = (Iniciativa) obj;
		if (iniciativaId == null) {
			if (iniciativaId != null) {
				return false;
			}
		} else if (!iniciativaId.equals(iniciativaId))
			return false;
		return true;
	}

	public String toString() {
		return new ToStringBuilder(this).append("iniciativaId", getIniciativaId()).toString();
	}

	public Long getCargoId() {
		return cargoId;
	}

	public void setCargoId(Long cargoId) {
		this.cargoId = cargoId;
	}

	public String getCodigoIniciativa() {
		return codigoIniciativa;
	}

	public void setCodigoIniciativa(String codigoIniciativa) {
		this.codigoIniciativa = codigoIniciativa;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(String montoTotal) {
		this.montoTotal = montoTotal;
	}

	public String getMontoMonedaExt() {
		return montoMonedaExt;
	}

	public void setMontoMonedaExt(String montoMonedaExt) {
		this.montoMonedaExt = montoMonedaExt;
	}

	public String getSituacionPresupuestaria() {
		return situacionPresupuestaria;
	}

	public void setSituacionPresupuestaria(String situacionPresupuestaria) {
		this.situacionPresupuestaria = situacionPresupuestaria;
	}

	public String getHitos() {
		return hitos;
	}

	public void setHitos(String hitos) {
		this.hitos = hitos;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public Date getFechaActaInicio() {
		return fechaActaInicio;
	}

	public void setFechaActaInicio(Date fechaActaInicio) {
		this.fechaActaInicio = fechaActaInicio;
	}

	public String getGerenciaGeneralRes() {
		return gerenciaGeneralRes;
	}

	public void setGerenciaGeneralRes(String gerenciaGeneralRes) {
		this.gerenciaGeneralRes = gerenciaGeneralRes;
	}

	public String getCodigoSipe() {
		return codigoSipe;
	}

	public void setCodigoSipe(String codigoSipe) {
		this.codigoSipe = codigoSipe;
	}

	public String getProyectoPresupAso() {
		return proyectoPresupAso;
	}

	public void setProyectoPresupAso(String proyectoPresupAso) {
		this.proyectoPresupAso = proyectoPresupAso;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getParroquia() {
		return parroquia;
	}

	public void setParroquia(String parroquia) {
		this.parroquia = parroquia;
	}

	public String getDireccionProyecto() {
		return direccionProyecto;
	}

	public void setDireccionProyecto(String direccionProyecto) {
		this.direccionProyecto = direccionProyecto;
	}

	public String getObjetivoHistorico() {
		return objetivoHistorico;
	}

	public void setObjetivoHistorico(String objetivoHistorico) {
		this.objetivoHistorico = objetivoHistorico;
	}

	public String getObjetivoNacional() {
		return objetivoNacional;
	}

	public void setObjetivoNacional(String objetivoNacional) {
		this.objetivoNacional = objetivoNacional;
	}

	public String getObjetivoEstrategicoPV() {
		return objetivoEstrategicoPV;
	}

	public void setObjetivoEstrategicoPV(String objetivoEstrategicoPV) {
		this.objetivoEstrategicoPV = objetivoEstrategicoPV;
	}

	public String getObjetivoGeneralPV() {
		return objetivoGeneralPV;
	}

	public void setObjetivoGeneralPV(String objetivoGeneralPV) {
		this.objetivoGeneralPV = objetivoGeneralPV;
	}

	public String getObjetivoEspecifico() {
		return objetivoEspecifico;
	}

	public void setObjetivoEspecifico(String objetivoEspecifico) {
		this.objetivoEspecifico = objetivoEspecifico;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getProblemas() {
		return problemas;
	}

	public void setProblemas(String problemas) {
		this.problemas = problemas;
	}

	public String getCausas() {
		return causas;
	}

	public void setCausas(String causas) {
		this.causas = causas;
	}

	public String getLineasEstrategicas() {
		return lineasEstrategicas;
	}

	public void setLineasEstrategicas(String lineasEstrategicas) {
		this.lineasEstrategicas = lineasEstrategicas;
	}

	public String getGerenteProyectoNombre() {
		return gerenteProyectoNombre;
	}

	public void setGerenteProyectoNombre(String gerenteProyectoNombre) {
		this.gerenteProyectoNombre = gerenteProyectoNombre;
	}

	public String getGerenteProyectoCedula() {
		return gerenteProyectoCedula;
	}

	public void setGerenteProyectoCedula(String gerenteProyectoCedula) {
		this.gerenteProyectoCedula = gerenteProyectoCedula;
	}

	public String getGerenteProyectoEmail() {
		return gerenteProyectoEmail;
	}

	public void setGerenteProyectoEmail(String gerenteProyectoEmail) {
		this.gerenteProyectoEmail = gerenteProyectoEmail;
	}

	public String getGerenteProyectoTelefono() {
		return gerenteProyectoTelefono;
	}

	public void setGerenteProyectoTelefono(String gerenteProyectoTelefono) {
		this.gerenteProyectoTelefono = gerenteProyectoTelefono;
	}

	public String getResponsableTecnicoNombre() {
		return responsableTecnicoNombre;
	}

	public void setResponsableTecnicoNombre(String responsableTecnicoNombre) {
		this.responsableTecnicoNombre = responsableTecnicoNombre;
	}

	public String getResponsableTecnicoCedula() {
		return responsableTecnicoCedula;
	}

	public void setResponsableTecnicoCedula(String responsableTecnicoCedula) {
		this.responsableTecnicoCedula = responsableTecnicoCedula;
	}

	public String getResponsableTecnicoEmail() {
		return responsableTecnicoEmail;
	}

	public void setResponsableTecnicoEmail(String responsableTecnicoEmail) {
		this.responsableTecnicoEmail = responsableTecnicoEmail;
	}

	public String getResponsableTecnicoTelefono() {
		return responsableTecnicoTelefono;
	}

	public void setResponsableTecnicoTelefono(String responsableTecnicoTelefono) {
		this.responsableTecnicoTelefono = responsableTecnicoTelefono;
	}

	public String getResponsableRegistradorNombre() {
		return responsableRegistradorNombre;
	}

	public void setResponsableRegistradorNombre(String responsableRegistradorNombre) {
		this.responsableRegistradorNombre = responsableRegistradorNombre;
	}

	public String getResponsableRegistradorCedula() {
		return responsableRegistradorCedula;
	}

	public void setResponsableRegistradorCedula(String responsableRegistradorCedula) {
		this.responsableRegistradorCedula = responsableRegistradorCedula;
	}

	public String getResponsableRegistradorEmail() {
		return responsableRegistradorEmail;
	}

	public void setResponsableRegistradorEmail(String responsableRegistradorEmail) {
		this.responsableRegistradorEmail = responsableRegistradorEmail;
	}

	public String getResponsableRegistradorTelefono() {
		return responsableRegistradorTelefono;
	}

	public void setResponsableRegistradorTelefono(String responsableRegistradorTelefono) {
		this.responsableRegistradorTelefono = responsableRegistradorTelefono;
	}

	public String getResponsableAdministrativoNombre() {
		return responsableAdministrativoNombre;
	}

	public void setResponsableAdministrativoNombre(String responsableAdministrativoNombre) {
		this.responsableAdministrativoNombre = responsableAdministrativoNombre;
	}

	public String getResponsableAdministrativoCedula() {
		return responsableAdministrativoCedula;
	}

	public void setResponsableAdministrativoCedula(String responsableAdministrativoCedula) {
		this.responsableAdministrativoCedula = responsableAdministrativoCedula;
	}

	public String getResponsableAdministrativoEmail() {
		return responsableAdministrativoEmail;
	}

	public void setResponsableAdministrativoEmail(String responsableAdministrativoEmail) {
		this.responsableAdministrativoEmail = responsableAdministrativoEmail;
	}

	public String getResponsableAdministrativoTelefono() {
		return responsableAdministrativoTelefono;
	}

	public void setResponsableAdministrativoTelefono(String responsableAdministrativoTelefono) {
		this.responsableAdministrativoTelefono = responsableAdministrativoTelefono;
	}

	public String getResponsableAdminContratosNombre() {
		return responsableAdminContratosNombre;
	}

	public void setResponsableAdminContratosNombre(String responsableAdminContratosNombre) {
		this.responsableAdminContratosNombre = responsableAdminContratosNombre;
	}

	public String getResponsableAdminContratosCedula() {
		return responsableAdminContratosCedula;
	}

	public void setResponsableAdminContratosCedula(String responsableAdminContratosCedula) {
		this.responsableAdminContratosCedula = responsableAdminContratosCedula;
	}

	public String getResponsableAdminContratosEmail() {
		return responsableAdminContratosEmail;
	}

	public void setResponsableAdminContratosEmail(String responsableAdminContratosEmail) {
		this.responsableAdminContratosEmail = responsableAdminContratosEmail;
	}

	public String getResponsableAdminContratosTelefono() {
		return responsableAdminContratosTelefono;
	}

	public void setResponsableAdminContratosTelefono(String responsableAdminContratosTelefono) {
		this.responsableAdminContratosTelefono = responsableAdminContratosTelefono;
	}

	public Long getFaseId() {
		return faseId;
	}

	public void setFaseId(Long faseId) {
		this.faseId = faseId;
	}

	
}
