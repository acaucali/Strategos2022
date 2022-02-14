package com.visiongc.servicio.strategos.indicadores.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.servicio.web.importar.util.VgcResourceManager;

public class TipoIndicador
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte TIPO_INDICADOR_RESULTADO = 0;
  private static final byte TIPO_INDICADOR_GUIA = 1;
  private byte tipoId;
  private String nombre;

  public byte getTipoId()
  {
    return this.tipoId;
  }

  public void setTipoId(byte tipoId) {
    this.tipoId = tipoId;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public static Byte getTipoIndicadorResultado() {
    return new Byte((byte) 0);
  }

  public static Byte getTipoIndicadorGuia() {
    return new Byte((byte) 1);
  }

  public static List<?> getTipos() {
    return getTipos(null);
  }

  public static List<TipoIndicador> getTipos(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
    }

    List<TipoIndicador> tipos = new ArrayList<TipoIndicador>();

    TipoIndicador tipo = new TipoIndicador();

    tipo.tipoId = 0;
    tipo.nombre = messageResources.getResource("tipoindicador.resultado");
    tipos.add(tipo);

    tipo = new TipoIndicador();
    tipo.tipoId = 1;
    tipo.nombre = messageResources.getResource("tipoindicador.guia");
    tipos.add(tipo);

    return tipos;
  }

  public static String getNombre(byte tipo) {
    String nombre = "";

    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

    if (tipo == 0) {
      nombre = messageResources.getResource("tipoindicador.resultado");
    }

    if (tipo == 1) {
      nombre = messageResources.getResource("tipoindicador.guia");
    }
    return nombre;
  }
}