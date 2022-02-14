package com.visiongc.servicio.strategos.planes.model;

import com.visiongc.servicio.web.importar.util.VgcFormatter;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Perspectiva implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long perspectivaId;
  private Long planId;
  private Long responsableId;
  private String nombre;
  private String nombreObjetoPerspectiva;
  private Long padreId;
  private String descripcion;
  private Integer orden;
  private Byte frecuencia;
  private Long objetivoImpactaId;
  private String titulo;
  private Byte tipo;
  private String fechaUltimaMedicion;
  private Double ultimaMedicionAnual;
  private Double ultimaMedicionParcial;
  private Date creado;
  private Date modificado;
  private Long creadoId;
  private Long modificadoId;
  private Byte alertaParcial;
  private Byte alertaAnual;
  private Integer ano;
  private Long claseId;
  private Long nlAnoIndicadorId;
  private Long nlParIndicadorId;
  private Byte tipoCalculo;
  private Byte nivel;
  private Set<?> indicadoresPerspectiva;
  private List<?> listaIndicadores;
  private List<?> listaIndicadoresGuia;
  private List<?> listaIniciativas;
  private Set<?> indicadoresPlan;
  private Perspectiva padre;
  private List<Perspectiva> hijos;
  private Boolean hijosCargados;
  private Plan plan;
  private Collection<?> nodoArbolHijos;
  private List<String> listaPerspectivasMetod;
  private List<String> listaPadres;

  public Long getPerspectivaId()
  {
    return this.perspectivaId;
  }

  public void setPerspectivaId(Long perspectivaId) {
    this.perspectivaId = perspectivaId;
  }

  public Long getPlanId() {
    return this.planId;
  }

  public void setPlanId(Long planId) {
    this.planId = planId;
  }

  public Long getResponsableId() {
    return this.responsableId;
  }

  public void setResponsableId(Long responsableId) {
    this.responsableId = responsableId;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getNombreObjetoPerspectiva() {
    return this.nombreObjetoPerspectiva;
  }

  public void setNombreObjetoPerspectiva(String nombreObjetoPerspectiva) {
    this.nombreObjetoPerspectiva = nombreObjetoPerspectiva;
  }

  public Long getPadreId() {
    return this.padreId;
  }

  public void setPadreId(Long padreId) {
    this.padreId = padreId;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Integer getOrden() {
    return this.orden;
  }

  public void setOrden(Integer orden) {
    this.orden = orden;
  }

  public Byte getFrecuencia() {
    return this.frecuencia;
  }

  public void setFrecuencia(Byte frecuencia) {
    this.frecuencia = frecuencia;
  }

  public Long getObjetivoImpactaId() {
    return this.objetivoImpactaId;
  }

  public void setObjetivoImpactaId(Long objetivoImpactaId) {
    this.objetivoImpactaId = objetivoImpactaId;
  }

  public String getTitulo() {
    return this.titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Byte getTipo() {
    return this.tipo;
  }

  public void setTipo(Byte tipo) {
    this.tipo = tipo;
  }

  public String getFechaUltimaMedicion() {
    return this.fechaUltimaMedicion;
  }

  public void setFechaUltimaMedicion(String fechaUltimaMedicion) {
    this.fechaUltimaMedicion = fechaUltimaMedicion;
  }

  public Double getUltimaMedicionAnual() {
    return this.ultimaMedicionAnual;
  }

  public String getUltimaMedicionAnualFormateado() {
    return VgcFormatter.formatearNumero(this.ultimaMedicionAnual, 2);
  }

  public void setUltimaMedicionAnual(Double ultimaMedicionAnual) {
    this.ultimaMedicionAnual = ultimaMedicionAnual;
  }

  public Double getUltimaMedicionParcial() {
    return this.ultimaMedicionParcial;
  }

  public String getUltimaMedicionParcialFormateado() {
    return VgcFormatter.formatearNumero(this.ultimaMedicionParcial, 2);
  }

  public void setUltimaMedicionParcial(Double ultimaMedicionParcial) {
    this.ultimaMedicionParcial = ultimaMedicionParcial;
  }

  public Byte getNivel() {
    return this.nivel;
  }

  public void setNivel(Byte nivel) {
    this.nivel = nivel;
  }

  public Date getCreado() {
    return this.creado;
  }

  public void setCreado(Date creado) {
    this.creado = creado;
  }

  public Date getModificado() {
    return this.modificado;
  }

  public void setModificado(Date modificado) {
    this.modificado = modificado;
  }

  public Long getCreadoId() {
    return this.creadoId;
  }

  public void setCreadoId(Long creadoId) {
    this.creadoId = creadoId;
  }

  public Long getModificadoId() {
    return this.modificadoId;
  }

  public void setModificadoId(Long modificadoId) {
    this.modificadoId = modificadoId;
  }

  	public Byte getAlertaParcial() 
  	{
  		return this.alertaParcial;
  	}

  	public void setAlertaParcial(Byte alertaParcial) 
  	{
  		this.alertaParcial = alertaParcial;
  	}

  	public Byte getAlertaAnual() 
  	{
  		return this.alertaAnual;
  	}

  	public void setAlertaAnual(Byte alertaAnual) 
  	{
  		this.alertaAnual = alertaAnual;
  	}
  	
  public Integer getAno() {
    return this.ano;
  }

  public void setAno(Integer ano) {
    this.ano = ano;
  }

  public Long getClaseId() {
    return this.claseId;
  }

  public void setClaseId(Long claseId) {
    this.claseId = claseId;
  }

  public Long getNlAnoIndicadorId() {
    return this.nlAnoIndicadorId;
  }

  public void setNlAnoIndicadorId(Long nlAnoIndicadorId) {
    this.nlAnoIndicadorId = nlAnoIndicadorId;
  }

  public Long getNlParIndicadorId() {
    return this.nlParIndicadorId;
  }

  public void setNlParIndicadorId(Long nlParIndicadorId) {
    this.nlParIndicadorId = nlParIndicadorId;
  }

  public Byte getTipoCalculo() {
    return this.tipoCalculo;
  }

  public void setTipoCalculo(Byte tipoCalculo) {
    this.tipoCalculo = tipoCalculo;
  }

  public Perspectiva getPadre() {
    return this.padre;
  }

  public void setPadre(Perspectiva padre) {
    this.padre = padre;
  }

  public List<Perspectiva> getHijos() {
    return this.hijos;
  }

  public void setHijos(List<Perspectiva> hijos) {
    this.hijos = hijos;
  }

  public Boolean getHijosCargados() {
    return this.hijosCargados;
  }

  public void setHijosCargados(Boolean hijosCargados) {
    this.hijosCargados = hijosCargados;
  }

  public Plan getPlan() {
    return this.plan;
  }

  public void setPlan(Plan plan) {
    this.plan = plan;
  }

  public Set<?> getIndicadoresPerspectiva() {
    return this.indicadoresPerspectiva;
  }

  public void setIndicadoresPerspectiva(Set<?> indicadoresPerspectiva) {
    this.indicadoresPerspectiva = indicadoresPerspectiva;
  }

  public List<?> getListaIndicadores() {
    return this.listaIndicadores;
  }

  public void setListaIndicadores(List<?> listaIndicadores) {
    this.listaIndicadores = listaIndicadores;
  }
  
  public void setListaPerspectivasMetod(List<String> listaPerspectivasMetod) {
    this.listaPerspectivasMetod = listaPerspectivasMetod;
  }

  public List<String> getListaPerspectivasMetod() {
    return this.listaPerspectivasMetod;
  }
  
  public List<String> getListaPadres() {
    return this.listaPadres;
  }

  public void setListaPadres(List<String> listaPadres) {
	  this.listaPadres = listaPadres;
  } 

  public List<?> getListaIndicadoresGuia() {
    return this.listaIndicadoresGuia;
  }

  public void setListaIndicadoresGuia(List<?> listaIndicadoresGuia) {
    this.listaIndicadoresGuia = listaIndicadoresGuia;
  }

  public List<?> getListaIniciativas() {
    return this.listaIniciativas;
  }

  public void setListaIniciativas(List<?> listaIniciativas) {
    this.listaIniciativas = listaIniciativas;
  }

  public Set<?> getIndicadoresPlan() {
    return this.indicadoresPlan;
  }

  public void setIndicadoresPlan(Set<?> indicadoresPlan) {
    this.indicadoresPlan = indicadoresPlan;
  }

  public void setHijosHoja(List<?> hijosHoja) {
  }

  public String getNodoArbolId() {
    if (this.perspectivaId != null) {
      return this.perspectivaId.toString();
    }
    return null;
  }

  public String getNodoArbolNombre()
  {
    return this.nombre;
  }

  public boolean getNodoArbolHijosCargados() {
    if (this.hijosCargados == null) {
      this.hijosCargados = new Boolean(false);
    }
    return this.hijosCargados.booleanValue();
  }

  public String getNodoArbolNombreCampoId() {
    return "perspectivaId";
  }

  public String getNodoArbolNombreCampoNombre() {
    return "nombre";
  }

  public String getNodoArbolNombreCampoPadreId() {
    return "padreId";
  }

  public String getNodoArbolPadreId() {
    if (this.padreId != null) {
      return this.padreId.toString();
    }
    return null;
  }

  public void setNodoArbolHijos(Collection<?> nodoArbolHijos)
  {
    this.nodoArbolHijos = nodoArbolHijos;
  }

  public Collection<?> getNodoArbolHijos() {
    return this.nodoArbolHijos;
  }

  public void setNodoArbolHijosCargados(boolean cargados) {
    this.hijosCargados = new Boolean(cargados);
  }

  public void setNodoArbolNombre(String nombre)
  {
    this.nombre = nombre;
  }

  public int compareTo(Object o) {
    Perspectiva or = (Perspectiva)o;
    return getPerspectivaId().compareTo(or.getPerspectivaId());
  }

  public String toString() {
    return new ToStringBuilder(this).append("perspectivaId", getPerspectivaId()).toString();
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Perspectiva other = (Perspectiva)obj;
    return new EqualsBuilder().append(getPerspectivaId(), other.getPerspectivaId()).isEquals();
  }
}