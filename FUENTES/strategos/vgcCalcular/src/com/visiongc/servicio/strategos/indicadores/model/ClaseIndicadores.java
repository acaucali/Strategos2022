package com.visiongc.servicio.strategos.indicadores.model;

import com.visiongc.servicio.strategos.organizaciones.model.OrganizacionStrategos;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ClaseIndicadores implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long claseId;
  private Long padreId;
  private Long organizacionId;
  private String nombre;
  private String descripcion;
  private String enlaceParcial;
  private Date creado;
  private Date modificado;
  private Long creadoId;
  private Long modificadoId;
  private Byte tipo;
  private Boolean visible;
  private ClaseIndicadores padre;
  private List<ClaseIndicadores> hijos;
  private Boolean hijosCargados;
  private Collection<?> nodoArbolHijos;
  private OrganizacionStrategos organizacion;
  private Set<?> indicadores;

  public ClaseIndicadores(Long claseId, Long padreId, Long organizacionId, String nombre, String descripcion, String enlaceParcial, Long creadoId, Long modificadoId, Integer nivel, Byte tipo, Boolean visible, ClaseIndicadores padre, List<ClaseIndicadores> hijos, Boolean hijosCargados)
  {
    this.claseId = claseId;
    this.padreId = padreId;
    this.organizacionId = organizacionId;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.enlaceParcial = enlaceParcial;
    this.creadoId = creadoId;
    this.modificadoId = modificadoId;
    this.tipo = tipo;
    this.visible = visible;
    this.padre = padre;
    this.hijos = hijos;
    this.hijosCargados = hijosCargados;
  }

  public ClaseIndicadores()
  {
  }

  public Long getClaseId()
  {
    return this.claseId;
  }

  public void setClaseId(Long claseId) {
    this.claseId = claseId;
  }

  public Date getCreado() {
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

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getEnlaceParcial() {
    return this.enlaceParcial;
  }

  public void setEnlaceParcial(String enlaceParcial) {
    this.enlaceParcial = enlaceParcial;
  }

  public List<ClaseIndicadores> getHijos() {
    return this.hijos;
  }

  public void setHijos(List<ClaseIndicadores> hijos) {
    this.hijos = hijos;
  }

  public Boolean getHijosCargados() {
    return this.hijosCargados;
  }

  public void setHijosCargados(Boolean hijosCargados) {
    this.hijosCargados = hijosCargados;
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

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Long getOrganizacionId() {
    return this.organizacionId;
  }

  public void setOrganizacionId(Long organizacionId) {
    this.organizacionId = organizacionId;
  }

  public ClaseIndicadores getPadre() {
    return this.padre;
  }

  public void setPadre(ClaseIndicadores padre) {
    this.padre = padre;
  }

  public Long getPadreId() {
    return this.padreId;
  }

  public void setPadreId(Long padreId) {
    this.padreId = padreId;
  }

  public Byte getTipo() {
    return this.tipo;
  }

  public void setTipo(Byte tipo) {
    this.tipo = tipo;
  }

  public Boolean getVisible() {
    return this.visible;
  }

  public void setVisible(Boolean visible) {
    this.visible = visible;
  }

  public Set<?> getIndicadores() {
    return this.indicadores;
  }

  public void setIndicadores(Set<?> indicadores) {
    this.indicadores = indicadores;
  }

  public OrganizacionStrategos getOrganizacion() {
    return this.organizacion;
  }

  public void setOrganizacion(OrganizacionStrategos organizacion) {
    this.organizacion = organizacion;
  }

  public Collection<?> getNodoArbolHijos() {
    if (this.nodoArbolHijos != null) {
      return this.nodoArbolHijos;
    }
    return this.hijos;
  }

  public boolean getNodoArbolHijosCargados()
  {
    if (this.hijosCargados == null) {
      this.hijosCargados = new Boolean(false);
    }
    return this.hijosCargados.booleanValue();
  }

  public String getNodoArbolId() {
    if (this.claseId != null) {
      return this.claseId.toString();
    }
    return null;
  }

  public String getNodoArbolNombre()
  {
    return this.nombre;
  }

  public String getNodoArbolNombreCampoId() {
    return "claseId";
  }

  public String getNodoArbolNombreCampoNombre() {
    return "nombre";
  }

  public String getNodoArbolNombreCampoPadreId() {
    return "padreId";
  }

  public String getNodoArbolNombreImagen() {
    return null;
  }

  public String getNodoArbolPadreId() {
    if (this.padreId != null) {
      return this.padreId.toString();
    }
    return null;
  }

  public void setNodoArbolHijos(Collection<?> nodoArbolHijos)
  {
    if ((nodoArbolHijos instanceof Set))
      this.hijos = ((List<ClaseIndicadores>)nodoArbolHijos);
    else
      this.nodoArbolHijos = nodoArbolHijos;
  }

  public void setNodoArbolHijosCargados(boolean cargados)
  {
    this.hijosCargados = new Boolean(cargados);
  }

  public void setNodoArbolNombre(String nombre) {
    this.nombre = nombre;
  }

  public int compareTo(Object o) {
    ClaseIndicadores or = (ClaseIndicadores)o;
    return getClaseId().compareTo(or.getClaseId());
  }

  public String toString() {
    return new ToStringBuilder(this).append("claseId", getClaseId()).toString();
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ClaseIndicadores other = (ClaseIndicadores)obj;
    if (this.claseId == null) {
      if (other.claseId != null)
        return false;
    } else if (!this.claseId.equals(other.claseId))
      return false;
    return true;
  }
}