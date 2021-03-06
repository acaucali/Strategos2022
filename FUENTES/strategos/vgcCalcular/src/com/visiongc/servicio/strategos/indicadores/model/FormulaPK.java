package com.visiongc.servicio.strategos.indicadores.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class FormulaPK
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private Long serieId;
  private Long indicadorId;

  public FormulaPK(Long serieId, Long indicadorId)
  {
    this.serieId = serieId;
    this.indicadorId = indicadorId;
  }

  public FormulaPK()
  {
  }

  public Long getSerieId() {
    return this.serieId;
  }

  public void setSerieId(Long serieId) {
    this.serieId = serieId;
  }

  public Long getIndicadorId() {
    return this.indicadorId;
  }

  public void setIndicadorId(Long indicadorId) {
    this.indicadorId = indicadorId;
  }

  public String toString() {
    return new ToStringBuilder(this)
      .append("serieId", getSerieId())
      .append("indicadorId", getIndicadorId())
      .toString();
  }

  public boolean equals(Object other) {
    if (this == other) return true;
    if (!(other instanceof FormulaPK)) return false;
    FormulaPK castOther = (FormulaPK)other;
    return new EqualsBuilder()
      .append(getSerieId(), castOther.getSerieId())
      .append(getIndicadorId(), castOther.getIndicadorId())
      .isEquals();
  }

  public int hashCode() {
    return new HashCodeBuilder()
      .append(getSerieId())
      .append(getIndicadorId())
      .toHashCode();
  }
}