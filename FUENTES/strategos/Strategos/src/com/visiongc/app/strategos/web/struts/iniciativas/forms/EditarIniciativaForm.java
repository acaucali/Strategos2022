package com.visiongc.app.strategos.web.struts.iniciativas.forms;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.visiongc.app.strategos.cargos.model.Cargos;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.model.util.FaseProyecto;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus.EstatusType;
import com.visiongc.app.strategos.iniciativas.model.util.TipoCalculoEstadoIniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.indicadores.forms.EditarIndicadorForm;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarIniciativaForm extends EditarObjetoForm {
	static final long serialVersionUID = 0L;

	private final String SEPARADOR = "|+|";
	private String nombreIniciativaSingular;
	private Long iniciativaId;
	private String nombre;
	private String nombreLargo;
	private Byte frecuencia;
	private List frecuencias;
	private List grupoAnos;
	private String enteEjecutor;
	private Byte tipoAlerta;
	private Double alertaZonaVerde;
	private Double alertaZonaAmarilla;
	private Long organizacionId;
	private Long responsableFijarMetaId;
	private Long responsableLograrMetaId;
	private Long responsableSeguimientoId;
	private Long responsableCargarMetaId;
	private Long responsableCargarEjecutadoId;
	private String descripcion;
	private String resultado;
	private String resultadoEspecificoIniciativa;
	private Integer ano;
	private Boolean visible;
	private String responsableFijarMeta;
	private String responsableLograrMeta;
	private String responsableSeguimiento;
	private String responsableCargarMeta;
	private String responsableCargarEjecutado;
	private Long planId;
	private Long perspectivaId;
	private Long iniciativaAsociadaPlanId;
	private String planNombre;
	private String perspectivaNombre;
	private String nombreObjetoPerspectiva;
	private String nombreObjetoIniciativa;
	private String frecuenciaNombre;
	private String unidadNombre;
	private String seriesIndicador;
	private Boolean hayValorPorcentajeAmarillo;
	private Boolean hayValorPorcentajeVerde;
	private String organizacionNombre;
	private Byte tipoMedicion;
	private Boolean eliminarMediciones;
	private Long estatusId;
	private IniciativaEstatus estatus;
	private List<IniciativaEstatus> estatuses;
	private Double porcentajeCompletado;
	private String anioFormulacion;
	private Long tipoId;
	private TipoProyecto tipoProyecto;
	private List<TipoProyecto> tipos;
	private Boolean desdeInstrumento;
	private Long instrumentoId;

	// Campos nuevos
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

	private String codigoIniciativa;
	private Long cargoId;
	private List<Cargos> cargos;

	private Long faseId;
	private List<FaseProyecto> fases;
	
	private String justificacion;
	private String fechaInicio;
	private String fechaFin;
	private String montoTotal;
	private String montoMonedaExt;
	private String situacionPresupuestaria;
	private String hitos;
	private String sector;
	private String fechaActaInicio;
	private String gerenciaGeneralesRes;
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

	private Boolean mostrarAdministracionPublica;
	
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

	private Byte partidas;
	private List<Cargos> cuentas;

	private Long unidad;
	private List<?> unidadesMedida;

	public Byte getPartidas() {
		return partidas;
	}

	public void setPartidas(Byte partidas) {
		this.partidas = partidas;
	}

	public List<Cargos> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cargos> cuentas) {
		this.cuentas = cuentas;
	}

	public Long getUnidad() {
		return unidad;
	}

	public void setUnidad(Long unidad) {
		this.unidad = unidad;
	}

	public Long getInstrumentoId() {
		return instrumentoId;
	}

	public void setInstrumentoId(Long instrumentoId) {
		this.instrumentoId = instrumentoId;
	}

	public List<TipoProyecto> getTipos() {
		return tipos;
	}

	public void setTipos(List<TipoProyecto> tipos) {
		this.tipos = tipos;
	}

	public Long getTipoId() {
		return tipoId;
	}

	public void setTipoId(Long tipoId) {
		this.tipoId = tipoId;
	}

	public TipoProyecto getTipoProyecto() {
		return tipoProyecto;
	}

	public void setTipoProyecto(TipoProyecto tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
	}

	public String getNombreIniciativaSingular() {
		return this.nombreIniciativaSingular;
	}

	public void setNombreIniciativaSingular(String nombreIniciativaSingular) {
		this.nombreIniciativaSingular = nombreIniciativaSingular;
	}

	public String getResponsableFijarMeta() {
		return this.responsableFijarMeta;
	}

	public void setResponsableFijarMeta(String responsableFijarMeta) {
		this.responsableFijarMeta = responsableFijarMeta;
	}

	public String getResponsableLograrMeta() {
		return this.responsableLograrMeta;
	}

	public void setResponsableLograrMeta(String responsableLograrMeta) {
		this.responsableLograrMeta = responsableLograrMeta;
	}

	public String getResponsableSeguimiento() {
		return this.responsableSeguimiento;
	}

	public void setResponsableSeguimiento(String responsableSeguimiento) {
		this.responsableSeguimiento = responsableSeguimiento;
	}

	public String getResponsableCargarMeta() {
		return this.responsableCargarMeta;
	}

	public void setResponsableCargarMeta(String responsableCargarMeta) {
		this.responsableCargarMeta = responsableCargarMeta;
	}

	public String getResponsableCargarEjecutado() {
		return this.responsableCargarEjecutado;
	}

	public void setResponsableCargarEjecutado(String responsableCargarEjecutado) {
		this.responsableCargarEjecutado = responsableCargarEjecutado;
	}

	public String getSEPARADOR() {
		return "|+|";
	}

	public String getResultadoEspecificoIniciativa() {
		return this.resultadoEspecificoIniciativa;
	}

	public void setResultadoEspecificoIniciativa(String resultadoEspecificoIniciativa) {
		this.resultadoEspecificoIniciativa = resultadoEspecificoIniciativa;
	}

	public String getResultado() {
		return this.resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getIniciativaId() {
		return this.iniciativaId;
	}

	public void setIniciativaId(Long iniciativaId) {
		this.iniciativaId = iniciativaId;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAnioFormulacion() {
		return this.anioFormulacion;
	}

	public void setAnioFormulacion(String anioFormulacion) {
		this.anioFormulacion = anioFormulacion;
	}

	public Byte getTipoAlerta() {
		return this.tipoAlerta;
	}

	public void setTipoAlerta(Byte tipoAlerta) {
		this.tipoAlerta = tipoAlerta;
	}

	public Double getAlertaZonaVerde() {
		return this.alertaZonaVerde;
	}

	public void setAlertaZonaVerde(Double alertaZonaVerde) {
		this.alertaZonaVerde = alertaZonaVerde;
	}

	public Double getAlertaZonaAmarilla() {
		return this.alertaZonaAmarilla;
	}

	public void setAlertaZonaAmarilla(Double alertaZonaAmarilla) {
		this.alertaZonaAmarilla = alertaZonaAmarilla;
	}

	public Long getOrganizacionId() {
		return this.organizacionId;
	}

	public void setOrganizacionId(Long organizacionId) {
		this.organizacionId = organizacionId;
	}

	public Byte getFrecuencia() {
		return this.frecuencia;
	}

	public void setFrecuencia(Byte frecuencia) {
		this.frecuencia = frecuencia;
	}

	public List getFrecuencias() {
		return this.frecuencias;
	}

	public void setFrecuencias(List frecuencias) {
		this.frecuencias = frecuencias;
	}

	public List getGrupoAnos() {
		return this.grupoAnos;
	}

	public void setGrupoAnos(List grupoAnos) {
		this.grupoAnos = grupoAnos;
	}

	public String getNombreLargo() {
		return this.nombreLargo;
	}

	public void setNombreLargo(String nombreLargo) {
		this.nombreLargo = nombreLargo;
	}

	public String getEnteEjecutor() {
		return this.enteEjecutor;
	}

	public void setEnteEjecutor(String enteEjecutor) {
		this.enteEjecutor = enteEjecutor;
	}

	public Long getResponsableFijarMetaId() {
		return this.responsableFijarMetaId;
	}

	public void setResponsableFijarMetaId(Long responsableFijarMetaId) {
		this.responsableFijarMetaId = responsableFijarMetaId;
	}

	public Long getResponsableLograrMetaId() {
		return this.responsableLograrMetaId;
	}

	public void setResponsableLograrMetaId(Long responsableLograrMetaId) {
		this.responsableLograrMetaId = responsableLograrMetaId;
	}

	public Long getResponsableSeguimientoId() {
		return this.responsableSeguimientoId;
	}

	public void setResponsableSeguimientoId(Long responsableSeguimientoId) {
		this.responsableSeguimientoId = responsableSeguimientoId;
	}

	public Long getResponsableCargarMetaId() {
		return this.responsableCargarMetaId;
	}

	public void setResponsableCargarMetaId(Long responsableCargarMetaId) {
		this.responsableCargarMetaId = responsableCargarMetaId;
	}

	public Long getResponsableCargarEjecutadoId() {
		return this.responsableCargarEjecutadoId;
	}

	public void setResponsableCargarEjecutadoId(Long responsableCargarEjecutadoId) {
		this.responsableCargarEjecutadoId = responsableCargarEjecutadoId;
	}

	public Integer getAno() {
		return this.ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Boolean getVisible() {
		return this.visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Long getIniciativaAsociadaPlanId() {
		return this.iniciativaAsociadaPlanId;
	}

	public void setIniciativaAsociadaPlanId(Long iniciativaAsociadaPlanId) {
		this.iniciativaAsociadaPlanId = iniciativaAsociadaPlanId;
	}

	public Long getPlanId() {
		return this.planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public Long getPerspectivaId() {
		return this.perspectivaId;
	}

	public void setPerspectivaId(Long perspectivaId) {
		this.perspectivaId = perspectivaId;
	}

	public String getPerspectivaNombre() {
		return this.perspectivaNombre;
	}

	public void setPerspectivaNombre(String perspectivaNombre) {
		this.perspectivaNombre = perspectivaNombre;
	}

	public String getPlanNombre() {
		return this.planNombre;
	}

	public void setPlanNombre(String planNombre) {
		this.planNombre = planNombre;
	}

	public String getNombreObjetoPerspectiva() {
		return this.nombreObjetoPerspectiva;
	}

	public void setNombreObjetoPerspectiva(String nombreObjetoPerspectiva) {
		this.nombreObjetoPerspectiva = nombreObjetoPerspectiva;
	}

	public String getFrecuenciaNombre() {
		return this.frecuenciaNombre;
	}

	public void setFrecuenciaNombre(String frecuenciaNombre) {
		this.frecuenciaNombre = frecuenciaNombre;
	}

	public String getUnidadNombre() {
		return this.unidadNombre;
	}

	public void setUnidadNombre(String unidadNombre) {
		this.unidadNombre = unidadNombre;
	}

	public String getNombreObjetoIniciativa() {
		return this.nombreObjetoIniciativa;
	}

	public void setNombreObjetoIniciativa(String nombreObjetoIniciativa) {
		this.nombreObjetoIniciativa = nombreObjetoIniciativa;
	}

	public String getSeriesIndicador() {
		return this.seriesIndicador;
	}

	public void setSeriesIndicador(String seriesIndicador) {
		this.seriesIndicador = seriesIndicador;
	}

	public String getSeparadorSeries() {
		return new EditarIndicadorForm().getSeparadorSeries();
	}

	public Boolean getHayValorPorcentajeAmarillo() {
		return this.hayValorPorcentajeAmarillo;
	}

	public void setHayValorPorcentajeAmarillo(Boolean hayValorPorcentajeAmarillo) {
		this.hayValorPorcentajeAmarillo = hayValorPorcentajeAmarillo;
	}

	public Boolean getHayValorPorcentajeVerde() {
		return this.hayValorPorcentajeVerde;
	}

	public void setHayValorPorcentajeVerde(Boolean hayValorPorcentajeVerde) {
		this.hayValorPorcentajeVerde = hayValorPorcentajeVerde;
	}

	public String getOrganizacionNombre() {
		return this.organizacionNombre;
	}

	public void setOrganizacionNombre(String organizacionNombre) {
		this.organizacionNombre = organizacionNombre;
	}

	public Byte getTipoMedicion() {
		return this.tipoMedicion;
	}

	public void setTipoMedicion(Byte tipoMedicion) {
		this.tipoMedicion = tipoMedicion;
	}

	public Boolean getEliminarMediciones() {
		return this.eliminarMediciones;
	}

	public void setEliminarMediciones(Boolean eliminarMediciones) {
		this.eliminarMediciones = eliminarMediciones;
	}

	public Long getEstatusId() {
		return this.estatusId;
	}

	public void setEstatusId(Long estatusId) {
		this.estatusId = estatusId;
	}

	public IniciativaEstatus getEstatus() {
		return this.estatus;
	}

	public void setEstatus(IniciativaEstatus estatus) {
		this.estatus = estatus;
	}

	public List<IniciativaEstatus> getEstatuses() {
		return this.estatuses;
	}

	public void setEstatuses(List<IniciativaEstatus> estatuses) {
		this.estatuses = estatuses;
	}

	public Double getPorcentajeCompletado() {
		return this.porcentajeCompletado;
	}

	public void setPorcentajeCompletado(Double porcentajeCompletado) {
		this.porcentajeCompletado = porcentajeCompletado;
	}

	public Boolean getDesdeInstrumento() {
		return desdeInstrumento;
	}

	public void setDesdeInstrumento(Boolean desdeInstrumento) {
		this.desdeInstrumento = desdeInstrumento;
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

	@Override
	public void clear() {
		Calendar fechaActual = Calendar.getInstance();

		int anoActual = fechaActual.get(1);

		this.nombreIniciativaSingular = null;
		this.iniciativaId = new Long(0L);
		this.nombre = null;
		this.nombreLargo = null;
		this.descripcion = null;
		this.enteEjecutor = null;
		this.frecuencia = Frecuencia.getFrecuenciaTrimestral();
		this.responsableCargarEjecutado = null;
		this.responsableCargarMeta = null;
		this.responsableFijarMeta = null;
		this.responsableLograrMeta = null;
		this.responsableSeguimiento = null;
		this.tipoAlerta = TipoCalculoEstadoIniciativa.getTipoCalculoEstadoIniciativaPorActividades();
		this.alertaZonaAmarilla = null;
		this.alertaZonaVerde = null;
		this.hayValorPorcentajeAmarillo = false;
		this.hayValorPorcentajeVerde = false;
		this.ano = new Integer(anoActual);
		this.resultado = null;
		int anoTemp = anoActual - 5;
		int anoFinal = anoActual + 5;
		String resultadosEspecificos = "";
		while (anoTemp < anoFinal) {
			resultadosEspecificos = resultadosEspecificos + "|+|";
			anoTemp++;
		}
		this.resultadoEspecificoIniciativa = resultadosEspecificos;
		this.planId = null;
		this.visible = new Boolean(true);
		setBloqueado(new Boolean(false));
		this.organizacionNombre = "";
		this.tipoMedicion = TipoMedicion.getTipoMedicionEnPeriodo();
		this.eliminarMediciones = new Boolean(false);
		this.estatusId = EstatusType.getEstatusSinIniciar();
		this.estatus = EstatusType.setEstatusIniciado();
		this.tipoId = Long.valueOf(1L);
		this.tipoProyecto = new TipoProyecto();
		this.porcentajeCompletado = null;
		this.desdeInstrumento = false;

		this.responsableProyecto = null;
		this.cargoResponsable = null;
		this.organizacionesInvolucradas = null;
		this.objetivoEstrategico = null;
		this.fuenteFinanciacion = null;
		this.montoFinanciamiento = null;
		this.iniciativaEstrategica = null;
		this.liderIniciativa = null;
		this.tipoIniciativa = null;
		this.poblacionBeneficiada = null;
		this.contexto = null;
		this.definicionProblema = null;
		this.alcance = null;
		this.objetivoGeneral = null;
		this.objetivoEspecificos = null;
		
		this.justificacion = null;
		this.fechaInicio = null;
		this.fechaFin = null;
		this.montoTotal = null;
		this.montoMonedaExt = null;
		this.situacionPresupuestaria =  null;
		this.hitos = null;
		this.sector =  null;
		this.fechaActaInicio =  null;
		this.gerenciaGeneralesRes = null;
		this.codigoSipe = null;
		this.proyectoPresupAso =  null;
		this.estado = null;
		this.municipio =  null;
		this.parroquia = null;
		this.direccionProyecto = null;
		this.objetivoHistorico = null;
		this.objetivoNacional =  null;
		this.objetivoEstrategicoPV =  null;
		this.objetivoGeneralPV = null;
		this.objetivoEspecifico = null;
		this.programa = null;
		this.problemas = null;
		this.causas = null;
		this.lineasEstrategicas = null;

		this.codigoIniciativa = null;
		this.cargoId = new Long(0L);
		this.cargos = null;
		
		this.faseId = new Long(0L);
		this.fases = null;
		
		this.partidas = 1;
		this.unidadesMedida = null;
		this.unidad = new Long(0L);
		
		this.gerenteProyectoNombre = null;
		this.gerenteProyectoCedula = null;
		this.gerenteProyectoEmail = null;
		this.gerenteProyectoTelefono = null;
		this.responsableTecnicoNombre = null;
		this.responsableTecnicoCedula = null;
		this.responsableTecnicoEmail = null;
		this.responsableTecnicoTelefono = null;
		this.responsableRegistradorNombre = null;
		this.responsableRegistradorCedula = null;
		this.responsableRegistradorEmail = null;
		this.responsableRegistradorTelefono = null;
		this.responsableAdministrativoNombre = null;
		this.responsableAdministrativoCedula = null;
		this.responsableAdministrativoEmail = null;
		this.responsableAdministrativoTelefono = null;
		this.responsableAdminContratosNombre = null;
		this.responsableAdminContratosCedula = null;
		this.responsableAdminContratosEmail = null;
		this.responsableAdminContratosTelefono = null;
		
		this.mostrarAdministracionPublica = null;

		this.seriesIndicador = getSeparadorSeries() + SerieTiempo.getSerieRealId() + getSeparadorSeries()
				+ SerieTiempo.getSerieProgramadoId() + getSeparadorSeries();
	}

	public Long getCargoId() {
		return cargoId;
	}

	public void setCargoId(Long cargoId) {
		this.cargoId = cargoId;
	}

	public List<?> getCargos() {
		return cargos;
	}

	public void setCargos(List<Cargos> cargos) {
		this.cargos = cargos;
	}
	
	public Long getFaseId() {
		return faseId;
	}
	
	public void setFaseId(Long faseId) { 
		this.faseId = faseId;
	}
	
	public List<?> getFases(){
		return fases;	
	}
	
	public void setFases(List<FaseProyecto> fases) {
		this.fases = fases;
	}

	public String getCodigoIniciativa() {
		return codigoIniciativa;
	}

	public void setCodigoIniciativa(String codigoIniciativa) {
		this.codigoIniciativa = codigoIniciativa;
	}

	public List<?> getUnidadesMedida() {
		return unidadesMedida;
	}

	public void setUnidadesMedida(List<?> unidadesMedida) {
		this.unidadesMedida = unidadesMedida;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
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

	public String getFechaActaInicio() {
		return fechaActaInicio;
	}

	public void setFechaActaInicio(String fechaActaInicio) {
		this.fechaActaInicio = fechaActaInicio;
	}

	public String getGerenciaGeneralesRes() {
		return gerenciaGeneralesRes;
	}

	public void setGerenciaGeneralesRes(String gerenciaGeneralesRes) {
		this.gerenciaGeneralesRes = gerenciaGeneralesRes;
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

	public Boolean getMostrarAdministracionPublica() {
		return mostrarAdministracionPublica;
	}

	public void setMostrarAdministracionPublica(Boolean mostrarAdministracionPublica) {
		this.mostrarAdministracionPublica = mostrarAdministracionPublica;
	}
}
