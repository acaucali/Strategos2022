package com.visiongc.servicio.strategos.indicadores.model;

import com.visiongc.servicio.strategos.seriestiempo.model.SerieTiempo;
import java.io.Serializable;
import java.util.Set;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Formula
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private FormulaPK pk;
  private String expresion;
  private SerieIndicador serieIndicador;
  private Set<?> insumos;

  public Formula(FormulaPK pk, String expresion, SerieIndicador serieIndicador, SerieTiempo serieTiempo, Set<?> insumos)
  {
    this.pk = pk;
    this.serieIndicador = serieIndicador;
    this.expresion = expresion;
    this.insumos = insumos;
  }

  public Formula()
  {
  }

  public FormulaPK getPk() {
    return this.pk;
  }

  public void setPk(FormulaPK pk) {
    this.pk = pk;
  }

  public String getExpresion() {
    return this.expresion;
  }

  public void setExpresion(String expresion) {
    this.expresion = expresion;
  }

  public SerieIndicador getSerieIndicador()
  {
    return this.serieIndicador;
  }

  public void setSerieIndicador(SerieIndicador serieIndicador) {
    this.serieIndicador = serieIndicador;
  }

  public Set getInsumos() {
    return this.insumos;
  }

  public void setInsumos(Set<?> insumos) {
    this.insumos = insumos;
  }

  public String toString()
  {
    return new ToStringBuilder(this)
      .append("pk", getPk())
      .toString();
  }
}