package com.visiongc.servicio.strategos.indicadores.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.servicio.web.importar.util.VgcResourceManager;

public class Caracteristica
  implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final byte CARACTERISTICA_RETO_AUMENTO = 0;
  private static final byte CARACTERISTICA_RETO_DISMINUCION = 1;
  private static final byte CARACTERISTICA_CONDICION_VALOR_MAXIMO = 2;
  private static final byte CARACTERISTICA_CONDICION_VALOR_MINIMO = 3;
  private static final byte CARACTERISTICA_CONDICION_BANDAS = 4;
  private byte caracteristicaId;
  private String nombre;

  public byte getCaracteristicaId()
  {
    return this.caracteristicaId;
  }

  public void setCaracteristicaId(byte caracteristicaId) {
    this.caracteristicaId = caracteristicaId;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public static Byte getCaracteristicaRetoAumento() {
    return new Byte((byte) CARACTERISTICA_RETO_AUMENTO);
  }

  public static Byte getCaracteristicaRetoDisminucion() {
    return new Byte((byte) CARACTERISTICA_RETO_DISMINUCION);
  }

  public static Byte getCaracteristicaCondicionValorMaximo() {
    return new Byte((byte) CARACTERISTICA_CONDICION_VALOR_MAXIMO);
  }

  public static Byte getCaracteristicaCondicionValorMinimo() {
    return new Byte((byte) CARACTERISTICA_CONDICION_VALOR_MINIMO);
  }

  public static Byte getCaracteristicaCondicionBandas() {
    return new Byte((byte) CARACTERISTICA_CONDICION_BANDAS);
  }

  public static List<Caracteristica> getCaracteristicas() {
    return getCaracteristicas(null);
  }

  public static List<Caracteristica> getCaracteristicas(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
    }

    List<Caracteristica> caracteristicas = new ArrayList<Caracteristica>();

    Caracteristica caracteristica = new Caracteristica();

    caracteristica.caracteristicaId = 0;
    caracteristica.nombre = messageResources.getResource("caracteristica.retoaumento");
    caracteristicas.add(caracteristica);

    caracteristica = new Caracteristica();
    caracteristica.caracteristicaId = 1;
    caracteristica.nombre = messageResources.getResource("caracteristica.retodisminucion");
    caracteristicas.add(caracteristica);

    caracteristica = new Caracteristica();
    caracteristica.caracteristicaId = 2;
    caracteristica.nombre = messageResources.getResource("caracteristica.condicionvaloresmaximos");
    caracteristicas.add(caracteristica);

    caracteristica = new Caracteristica();
    caracteristica.caracteristicaId = 3;
    caracteristica.nombre = messageResources.getResource("caracteristica.condicionvaloresminimos");
    caracteristicas.add(caracteristica);

    caracteristica = new Caracteristica();
    caracteristica.caracteristicaId = 4;
    caracteristica.nombre = messageResources.getResource("caracteristica.condicionbandas");
    caracteristicas.add(caracteristica);

    return caracteristicas;
  }

  public static String getNombre(byte caracteristica) {
    String nombre = "";

    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

    if (caracteristica == 0) {
      nombre = messageResources.getResource("caracteristica.retoaumento");
    }

    if (caracteristica == 1) {
      nombre = messageResources.getResource("caracteristica.retodisminucion");
    }

    if (caracteristica == 2) {
      nombre = messageResources.getResource("caracteristica.condicionvaloresmaximos");
    }

    if (caracteristica == 3) {
      nombre = messageResources.getResource("caracteristica.condicionvaloresminimos");
    }

    if (caracteristica == 4) {
      nombre = messageResources.getResource("caracteristica.condicionbandas");
    }

    return nombre;
  }
}