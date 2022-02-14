package com.visiongc.servicio.strategos.planes.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PlantillaPlanes
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long plantillaPlanesId;
  private String nombre;
  private String descripcion;
  private Date creado;
  private Date modificado;
  private Long creadoId;
  private Long modificadoId;
  private String nombreIndicadorSingular;
  private String nombreIniciativaSingular;
  private String nombreActividadSingular;
  private Byte tipo;
  private Set<?> elementos;

  public PlantillaPlanes(Long plantillaPlanesId, String nombre, String descripcion, Date creado, Date modificado, Long creadoId, Long modificadoId, String nombreIndicadorSingular, String nombreIniciativaSingular, String nombreActividadSingular, String nombreIndicadorPlural, String nombreIniciativaPlural, String nombreActividadPlural, String articuloIndicadorSingular, String articuloIniciativaSingular, String articuloActividadSingular, String articuloIndicadorPlural, String articuloIniciativaPlural, String articuloActividadPlural, Byte tipo)
  {
    this.plantillaPlanesId = plantillaPlanesId;
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.creado = creado;
    this.modificado = modificado;
    this.creadoId = creadoId;
    this.modificadoId = modificadoId;
    this.nombreIndicadorSingular = nombreIndicadorSingular;
    this.nombreIniciativaSingular = nombreIniciativaSingular;
    this.nombreActividadSingular = nombreActividadSingular;
    this.tipo = tipo;
  }

  public PlantillaPlanes()
  {
  }

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

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
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

  public String getNombreActividadSingular() {
    return this.nombreActividadSingular;
  }

  public void setNombreActividadSingular(String nombreActividadSingular) {
    this.nombreActividadSingular = nombreActividadSingular;
  }

  public String getNombreIndicadorSingular() {
    return this.nombreIndicadorSingular;
  }

  public void setNombreIndicadorSingular(String nombreIndicadorSingular) {
    this.nombreIndicadorSingular = nombreIndicadorSingular;
  }

  public String getNombreIniciativaSingular() {
    return this.nombreIniciativaSingular;
  }

  public void setNombreIniciativaSingular(String nombreIniciativaSingular) {
    this.nombreIniciativaSingular = nombreIniciativaSingular;
  }

  public Long getPlantillaPlanesId() {
    return this.plantillaPlanesId;
  }

  public void setPlantillaPlanesId(Long plantillaPlanesId) {
    this.plantillaPlanesId = plantillaPlanesId;
  }

  public Byte getTipo() {
    return this.tipo;
  }

  public void setTipo(Byte tipo) {
    this.tipo = tipo;
  }

  public Set<?> getElementos() {
    return this.elementos;
  }

  public void setElementos(Set<?> elementos) {
    this.elementos = elementos;
  }

  public int compareTo(Object o) {
    PlantillaPlanes or = (PlantillaPlanes)o;
    return getPlantillaPlanesId().compareTo(or.getPlantillaPlanesId());
  }

  public String toString() {
    return new ToStringBuilder(this).append("plantillaPlanesId", getPlantillaPlanesId()).toString();
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PlantillaPlanes other = (PlantillaPlanes)obj;
    if (this.plantillaPlanesId == null) {
      if (other.plantillaPlanesId != null)
        return false;
    } else if (!this.plantillaPlanesId.equals(other.plantillaPlanesId))
      return false;
    return true;
  }
}