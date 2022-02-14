package com.visiongc.servicio.strategos.planes.model;

import com.visiongc.servicio.strategos.indicadores.model.Medicion;
import com.visiongc.servicio.strategos.indicadores.model.MedicionPK;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Meta
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private MetaPK metaId;
  private Double valor;
  private Boolean protegido;
  private Byte tipoCargaMeta;

  public Meta(MetaPK metaId, Double valor, Boolean protegido)
  {
    this.metaId = metaId;
    this.valor = valor;
    this.protegido = protegido;
  }

  public Meta()
  {
  }

  public MetaPK getMetaId() {
    return this.metaId;
  }

  public void setMetaId(MetaPK metaId) {
    this.metaId = metaId;
  }

  public Double getValor() {
    return this.valor;
  }

  public void setValor(Double valor) {
    this.valor = valor;
  }

  public Boolean getProtegido() {
    return this.protegido;
  }

  public void setProtegido(Boolean protegido) {
    this.protegido = protegido;
  }

  public Byte getTipoCargaMeta() {
    return this.tipoCargaMeta;
  }

  public void setTipoCargaMeta(Byte tipoCargaMeta) {
    this.tipoCargaMeta = tipoCargaMeta;
  }

  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof Meta))
      return false;
    Meta castOther = (Meta)other;
    return new EqualsBuilder().append(getMetaId(), castOther.getMetaId()).isEquals();
  }

  public String toString() {
    return new ToStringBuilder(this).append("id", getMetaId()).toString();
  }

  public Meta clonar() {
    Meta meta = new Meta();
    MetaPK metaPk = new MetaPK();
    meta.setMetaId(metaPk);
    meta.getMetaId().setIndicadorId(getMetaId().getIndicadorId());
    meta.getMetaId().setPlanId(getMetaId().getPlanId());
    meta.getMetaId().setSerieId(getMetaId().getSerieId());
    meta.getMetaId().setTipo(getMetaId().getTipo());
    meta.getMetaId().setAno(getMetaId().getAno());
    meta.getMetaId().setPeriodo(getMetaId().getPeriodo());
    meta.setValor(getValor());
    meta.setProtegido(getProtegido());
    return meta;
  }

  public Medicion clonarComoMedicion() {
    Medicion medicion = new Medicion();
    MedicionPK medicionPk = new MedicionPK();
    medicion.setMedicionId(medicionPk);
    medicion.getMedicionId().setIndicadorId(getMetaId().getIndicadorId());
    medicion.getMedicionId().setSerieId(getMetaId().getSerieId());
    medicion.getMedicionId().setAno(getMetaId().getAno());
    medicion.getMedicionId().setPeriodo(getMetaId().getPeriodo());
    medicion.setValor(getValor());
    medicion.setProtegido(getProtegido());
    return medicion;
  }
}