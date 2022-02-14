package com.visiongc.servicio.strategos.planes.model;

import com.visiongc.servicio.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.servicio.strategos.organizaciones.model.OrganizacionStrategos;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Plan implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long planId;
  private Long organizacionId;
  private Long planImpactaId;
  private String nombre;
  private Long padreId;
  private Integer anoInicial;
  private Integer anoFinal;
  private Byte tipo;
  private Boolean activo;
  private Byte revision;
  private String esquema;
  private Date creado;
  private Long creadoId;
  private Date modificado;
  private Long modificadoId;
  private Long metodologiaId;
  private Long claseId;
  private Long claseIdIndicadoresTotales;
  private Long indTotalImputacionId;
  private Long indTotalIniciativaId;
  private Byte alerta;
  private Double ultimaMedicionAnual;
  private Double ultimaMedicionParcial;
  private Long nlAnoIndicadorId;
  private Long nlParIndicadorId;
  private String fechaUltimaMedicion;
  private Long serieIdVigente;
  private PlantillaPlanes metodologia;
  private OrganizacionStrategos organizacion;
  private ClaseIndicadores clase;
  private Plan planImpacta;
  private Collection<?> nodoArbolHijos;
  private Boolean hijosCargados;
  private Set<?> hijos;
  private Plan padre;

  public Date getCreado()
  {
    return this.creado;
  }

  public void setCreado(Date creado) {
    this.creado = creado;
  }

  public Long getCreadoId() {
    return this.creadoId;
  }

  public void setCreadoId(Long creadoId) {
    this.creadoId = creadoId;
  }

  public Date getModificado() {
    return this.modificado;
  }

  public void setModificado(Date modificado) {
    this.modificado = modificado;
  }

  public Long getModificadoId() {
    return this.modificadoId;
  }

  public void setModificadoId(Long modificadoId) {
    this.modificadoId = modificadoId;
  }

  public Long getPlanId() {
    return this.planId;
  }

  public void setPlanId(Long planId) {
    this.planId = planId;
  }

  public Long getOrganizacionId() {
    return this.organizacionId;
  }

  public void setOrganizacionId(Long organizacionId) {
    this.organizacionId = organizacionId;
  }

  public Long getPlanImpactaId() {
    return this.planImpactaId;
  }

  public void setPlanImpactaId(Long planImpactaId) {
    this.planImpactaId = planImpactaId;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Long getPadreId() {
    return this.padreId;
  }

  public void setPadreId(Long padreId) {
    this.padreId = padreId;
  }

  public Integer getAnoInicial() {
    return this.anoInicial;
  }

  public void setAnoInicial(Integer anoInicial) {
    this.anoInicial = anoInicial;
  }

  public Integer getAnoFinal() {
    return this.anoFinal;
  }

  public void setAnoFinal(Integer anoFinal) {
    this.anoFinal = anoFinal;
  }

  public Boolean getActivo() {
    return this.activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }

  public Byte getRevision() {
    return this.revision;
  }

  public void setRevision(Byte revision) {
    this.revision = revision;
  }

  public String getEsquema()
  {
    return this.esquema;
  }

  public void setEsquema(String esquema) {
    this.esquema = esquema;
  }

  public Long getMetodologiaId() {
    return this.metodologiaId;
  }

  public void setMetodologiaId(Long metodologiaId) {
    this.metodologiaId = metodologiaId;
  }

  public Long getClaseId() {
    return this.claseId;
  }

  public void setClaseId(Long claseId) {
    this.claseId = claseId;
  }

  public Long getClaseIdIndicadoresTotales() {
    return this.claseIdIndicadoresTotales;
  }

  public void setClaseIdIndicadoresTotales(Long claseIdIndicadoresTotales) {
    this.claseIdIndicadoresTotales = claseIdIndicadoresTotales;
  }

  public Long getIndTotalImputacionId() {
    return this.indTotalImputacionId;
  }

  public void setIndTotalImputacionId(Long indTotalImputacionId) {
    this.indTotalImputacionId = indTotalImputacionId;
  }

  public Long getIndTotalIniciativaId() {
    return this.indTotalIniciativaId;
  }

  public void setIndTotalIniciativaId(Long indTotalIniciativaId) {
    this.indTotalIniciativaId = indTotalIniciativaId;
  }

  public Byte getAlerta() {
    return this.alerta;
  }

  public void setAlerta(Byte alerta) {
    this.alerta = alerta;
  }

  public Double getUltimaMedicionAnual() {
    return this.ultimaMedicionAnual;
  }

  public void setUltimaMedicionAnual(Double ultimaMedicionAnual) {
    this.ultimaMedicionAnual = ultimaMedicionAnual;
  }

  public Double getUltimaMedicionParcial() {
    return this.ultimaMedicionParcial;
  }

  public void setUltimaMedicionParcial(Double ultimaMedicionParcial) {
    this.ultimaMedicionParcial = ultimaMedicionParcial;
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

  public String getFechaUltimaMedicion() {
    return this.fechaUltimaMedicion;
  }

  public void setFechaUltimaMedicion(String fechaUltimaMedicion) {
    this.fechaUltimaMedicion = fechaUltimaMedicion;
  }

  public OrganizacionStrategos getOrganizacion() {
    return this.organizacion;
  }

  public void setOrganizacion(OrganizacionStrategos organizacion) {
    this.organizacion = organizacion;
  }

  public PlantillaPlanes getMetodologia() {
    return this.metodologia;
  }

  public void setMetodologia(PlantillaPlanes metodologia) {
    this.metodologia = metodologia;
  }

  public Byte getTipo() {
    return this.tipo;
  }

  public void setTipo(Byte tipo) {
    this.tipo = tipo;
  }

  public ClaseIndicadores getClase() {
    return this.clase;
  }

  public void setClase(ClaseIndicadores clase) {
    this.clase = clase;
  }

  public Plan getPlanImpacta() {
    return this.planImpacta;
  }

  public void setPlanImpacta(Plan planImpacta) {
    this.planImpacta = planImpacta;
  }

  public Long getSerieIdVigente() {
    return this.serieIdVigente;
  }

  public void setSerieIdVigente(Long serieIdVigente) {
    this.serieIdVigente = serieIdVigente;
  }

  public Set<?> getHijos() {
    return this.hijos;
  }

  public void setHijos(Set<?> hijos) {
    this.hijos = hijos;
  }

  public Plan getPadre() {
    return this.padre;
  }

  public void setPadre(Plan padre) {
    this.padre = padre;
  }

  public void setNodoArbolHijos(Collection<?> nodoArbolHijos) {
    this.nodoArbolHijos = nodoArbolHijos;
  }

  public Collection<?> getNodoArbolHijos() {
    return this.nodoArbolHijos;
  }

  public String getNodoArbolId() {
    if (this.planId != null) {
      return this.planId.toString();
    }
    return null;
  }

  public String getNodoArbolNombre()
  {
    return this.nombre;
  }

  public String getNodoArbolNombreImagen() {
    return "Plan";
  }

  public boolean getNodoArbolHijosCargados() {
    if (this.hijosCargados == null) {
      this.hijosCargados = new Boolean(false);
    }
    return this.hijosCargados.booleanValue();
  }

  public String getNodoArbolNombreCampoId() {
    return "planId";
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

  public void setNodoArbolHijosCargados(boolean cargados)
  {
    if (this.hijosCargados == null) {
      this.hijosCargados = new Boolean(false);
    }
    this.hijosCargados = new Boolean(cargados);
  }

  public void setNodoArbolNombre(String nombre)
  {
    this.nombre = nombre;
  }

  public int compareTo(Object o) {
    Plan or = (Plan)o;
    return getPlanId().compareTo(or.getPlanId());
  }

  public String toString() {
    return new ToStringBuilder(this).append("planId", getPlanId()).toString();
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Plan other = (Plan)obj;
    return new EqualsBuilder().append(getPlanId(), other.getPlanId()).isEquals();
  }
}