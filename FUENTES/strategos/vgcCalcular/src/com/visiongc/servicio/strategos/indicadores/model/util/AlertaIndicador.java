package com.visiongc.servicio.strategos.indicadores.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.visiongc.servicio.web.importar.util.VgcMessageResources;
import com.visiongc.servicio.web.importar.util.VgcResourceManager;

public class AlertaIndicador implements Serializable
{
  static final long serialVersionUID = 0L;
  private static final Byte ALERTA_NO_DEFINIBLE = null;
  private static final byte ALERTA_VERDE = 2;
  private static final byte ALERTA_AMARILLA = 3;
  private static final byte ALERTA_ROJA = 0;
  private static final byte ALERTA_INALTERADA = 1;
  private static final byte ALERTA_AZUL = 4;
  private static final byte ALERTA_NEGRA = 5;
  private Byte alertaId;
  private String nombre;

  public Byte getAlertaId()
  {
    return this.alertaId;
  }

  public void setAlertaId(Byte alertaId) {
    this.alertaId = alertaId;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public static Byte getAlertaNoDefinible() {
    return ALERTA_NO_DEFINIBLE;
  }

  public static Byte getAlertaVerde() {
    return new Byte((byte) 2);
  }

  public static Byte getAlertaAmarilla() {
    return new Byte((byte) 3);
  }

  public static Byte getAlertaRoja() {
    return new Byte((byte) 0);
  }

  public static Byte getAlertaInalterada() {
    return new Byte((byte) 1);
  }

  public static Byte getAlertaAzul() {
    return new Byte((byte) 4);
  }

  public static Byte getAlertaNegra() {
    return new Byte((byte) 5);
  }

  public static List<AlertaIndicador> getTipoAlertas() {
    return getTipoAlertas(null);
  }

  public static List<AlertaIndicador> getTipoAlertas(VgcMessageResources messageResources)
  {
    if (messageResources == null) {
      messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
    }

    List<AlertaIndicador> alertas = new ArrayList<AlertaIndicador>();

    AlertaIndicador alerta = new AlertaIndicador();
    alerta.alertaId = ALERTA_NO_DEFINIBLE;
    alerta.nombre = messageResources.getResource("alertaindicador.nodefinible");
    alertas.add(alerta);

    alerta = new AlertaIndicador();
    alerta.alertaId = new Byte((byte) 2);
    alerta.nombre = messageResources.getResource("alertaindicador.verde");
    alertas.add(alerta);

    alerta = new AlertaIndicador();
    alerta.alertaId = new Byte((byte) 3);
    alerta.nombre = messageResources.getResource("alertaindicador.amarilla");
    alertas.add(alerta);

    alerta = new AlertaIndicador();
    alerta.alertaId = new Byte((byte) 0);
    alerta.nombre = messageResources.getResource("alertaindicador.roja");
    alertas.add(alerta);

    alerta = new AlertaIndicador();
    alerta.alertaId = new Byte((byte) 1);
    alerta.nombre = messageResources.getResource("alertaindicador.inalterada");
    alertas.add(alerta);

    alerta = new AlertaIndicador();
    alerta.alertaId = new Byte((byte) 4);
    alerta.nombre = messageResources.getResource("alertaindicador.azul");
    alertas.add(alerta);

    alerta = new AlertaIndicador();
    alerta.alertaId = new Byte((byte) 5);
    alerta.nombre = messageResources.getResource("alertaindicador.negra");
    alertas.add(alerta);

    return alertas;
  }

  public static String getNombre(byte alerta)
  {
    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

    String nombre = messageResources.getResource("alertaindicador.nodefinible");

    if (alerta == 2)
      nombre = messageResources.getResource("alertaindicador.verde");
    else if (alerta == 3)
      nombre = messageResources.getResource("alertaindicador.amarilla");
    else if (alerta == 0)
      nombre = messageResources.getResource("alertaindicador.roja");
    else if (alerta == 1)
      nombre = messageResources.getResource("alertaindicador.inalterada");
    else if (alerta == 4)
      nombre = messageResources.getResource("alertaindicador.azul");
    else if (alerta == 5) {
      nombre = messageResources.getResource("alertaindicador.negra");
    }

    return nombre;
  }
}