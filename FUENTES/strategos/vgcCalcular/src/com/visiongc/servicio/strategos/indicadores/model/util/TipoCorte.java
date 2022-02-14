package com.visiongc.servicio.strategos.indicadores.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.servicio.web.importar.util.VgcResourceManager;

public class TipoCorte
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_CORTE_LONGITUDINAL = 0;
  private static final byte TIPO_CORTE_TRANSVERSAL = 1;
  private byte tipoCorteId;
  private String nombre;

  public byte getTipoCorteId()
  {
    return this.tipoCorteId;
  }

  public void setTipoCorteId(byte tipoCorteId) {
    this.tipoCorteId = tipoCorteId;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public static Byte getTipoCorteLongitudinal() {
    return new Byte((byte) 0);
  }

  public static Byte getTipoCorteTransversal() {
    return new Byte((byte) 1);
  }

  public static List<TipoCorte> getTipoCortes() {
    return getTipoCortes(null);
  }

  public static List<TipoCorte> getTipoCortes(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
    }

    List<TipoCorte> tipoCortes = new ArrayList<TipoCorte>();

    TipoCorte tipoCorte = new TipoCorte();

    tipoCorte.tipoCorteId = 0;
    tipoCorte.nombre = messageResources.getResource("tipocorte.longitudinal");
    tipoCortes.add(tipoCorte);

    tipoCorte = new TipoCorte();
    tipoCorte.tipoCorteId = 1;
    tipoCorte.nombre = messageResources.getResource("tipocorte.transversal");
    tipoCortes.add(tipoCorte);

    return tipoCortes;
  }

  public static String getNombre(byte tipoCorte) {
    String nombre = "";

    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

    if (tipoCorte == 0) {
      nombre = messageResources.getResource("tipocorte.longitudinal");
    }

    if (tipoCorte == 1) {
      nombre = messageResources.getResource("tipocorte.transversal");
    }
    return nombre;
  }
}