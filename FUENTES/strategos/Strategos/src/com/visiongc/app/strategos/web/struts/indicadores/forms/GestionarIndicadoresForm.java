package com.visiongc.app.strategos.web.struts.indicadores.forms;

import java.util.List;

import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.framework.model.Transaccion;
import com.visiongc.framework.web.struts.forms.VisorListaForm;


public class GestionarIndicadoresForm extends VisorListaForm
{
	static final long serialVersionUID = 0L;

	private String filtroNombre;
	private Long reporteSeleccionadoId;
	private Long graficoSeleccionadoId;
	private String respuesta;
	private Boolean reporte;
	private Boolean reporteComiteEjecutivo;
	private Boolean hayTransacciones;
	private List<Transaccion> transacciones;
	private Long claseId;
	private Long frecuencia;
	private List<?> unidadesMedida;
	private List<?> frecuencias;
	private Long unidadId;
	private String anchoPorDefecto;
	private String altoPorDefecto;

	public Long getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(Long frecuencia) {
		this.frecuencia = frecuencia;
	}

	public List<?> getUnidadesMedida() {
		return unidadesMedida;
	}

	public void setUnidadesMedida(List<?> unidadesMedida) {
		this.unidadesMedida = unidadesMedida;
	}

	public List<?> getFrecuencias() {
		return frecuencias;
	}

	public void setFrecuencias(List<?> frecuencias) {
		this.frecuencias = frecuencias;
	}

	public Long getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(Long unidadId) {
		this.unidadId = unidadId;
	}

	public String getFiltroNombre()
	{
		return this.filtroNombre;
	}

	public void setFiltroNombre(String filtroNombre)
	{
		this.filtroNombre = filtroNombre;
	}

	public Long getReporteSeleccionadoId()
	{
		return this.reporteSeleccionadoId;
	}

	public void setReporteSeleccionadoId(Long reporteSeleccionadoId)
	{
		this.reporteSeleccionadoId = reporteSeleccionadoId;
	}

	public Long getGraficoSeleccionadoId()
	{
		return this.graficoSeleccionadoId;
	}

	public void setGraficoSeleccionadoId(Long graficoSeleccionadoId)
	{
		this.graficoSeleccionadoId = graficoSeleccionadoId;
	}

	public Byte getNaturalezaFormula()
	{
		return Naturaleza.getNaturalezaFormula();
	}

	public Byte getNaturalezaCualitativoOrdinal()
	{
		return Naturaleza.getNaturalezaCualitativoOrdinal();
	}

	public Byte getNaturalezaCualitativoNominal()
	{
		return Naturaleza.getNaturalezaCualitativoNominal();
	}

	public String getRespuesta()
	{
		return this.respuesta;
	}

	public void setRespuesta(String respuesta)
	{
		this.respuesta = respuesta;
	}

	public Boolean getReporteComiteEjecutivo()
	{
		return this.reporteComiteEjecutivo;
	}

	public void setReporteComiteEjecutivo(Boolean reporteComiteEjecutivo)
	{
		this.reporteComiteEjecutivo = reporteComiteEjecutivo;
	}

	public Boolean getReporte()
	{
		return this.reporte;
	}

	public void setReporte(Boolean reporte)
	{
		this.reporte = reporte;
	}

	public Boolean getHayTransacciones()
	{
		return this.hayTransacciones;
	}

	public void setHayTransacciones(Boolean hayTransacciones)
	{
		this.hayTransacciones = hayTransacciones;
	}

	public List<Transaccion> getTransacciones()
	{
		return this.transacciones;
	}

	public void setTransacciones(List<Transaccion> transacciones)
	{
		this.transacciones = transacciones;
	}

	public Long getClaseId()
	{
		return this.claseId;
	}

	public void setClaseId(Long claseId)
	{
		this.claseId = claseId;
	}
	
	public String getAnchoPorDefecto()
	{
		return this.anchoPorDefecto;
	}

	public void setAnchoPorDefecto(String anchoPorDefecto)
	{
		this.anchoPorDefecto = anchoPorDefecto;
	}

	public String getAltoPorDefecto()
	{
		return this.altoPorDefecto;
	}

	public void setAltoPorDefecto(String altoPorDefecto)
	{
		this.altoPorDefecto = altoPorDefecto;
	}
}