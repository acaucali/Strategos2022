package com.visiongc.app.strategos.web.struts.vistasdatos.forms;

import java.util.List;

import com.visiongc.framework.web.struts.forms.SelectorListaForm;

public class SeleccionarPlanForm extends SelectorListaForm
{
  static final long serialVersionUID = 0L;
  private String filtroNombre;
  private Byte seleccionMultiple;
  private List listaPlanes;

  public String getFiltroNombre()
  {
    return this.filtroNombre;
  }

  public void setFiltroNombre(String filtroNombre) {
    this.filtroNombre = filtroNombre;
  }

  public List getListaPlanes() {
    return this.listaPlanes;
  }

  public void setListaPlanes(List listaPlanes) {
    this.listaPlanes = listaPlanes;
  }

  public Byte getSeleccionMultiple() {
    return this.seleccionMultiple;
  }

  public void setSeleccionMultiple(Byte seleccionMultiple) {
    this.seleccionMultiple = seleccionMultiple;
  }

  public void clear()
  {
  }
}