package com.visiongc.app.strategos.web.struts.indicadores.util;

import java.util.Collection;

import com.visiongc.framework.arboles.NodoArbol;

public class SeleccionarIndicadoresPlanesNodoRoot
  implements NodoArbol
{
  private Collection nodoArbolHijos;

  @Override
public String getNodoArbolId()
  {
    return "0";
  }

  @Override
public String getNodoArbolNombre() {
    return "planes";
  }

  	@Override
	public String getNodoArbolNombreImagen(Byte tipo)
  	{
  		if (tipo == 1)
  			return "PlanNodoRoot";
  		else
  			return "";
  	}

  	@Override
	public String getNodoArbolNombreToolTipImagen(Byte tipo)
  	{
  		return "";
  	}

  @Override
public Collection getNodoArbolHijos() {
    return this.nodoArbolHijos;
  }

  @Override
public void setNodoArbolHijos(Collection nodoArbolHijos) {
    this.nodoArbolHijos = nodoArbolHijos;
  }

  @Override
public boolean getNodoArbolHijosCargados() {
    return true;
  }

  @Override
public String getNodoArbolNombreCampoId() {
    return "nodoArbolId";
  }

  @Override
public String getNodoArbolNombreCampoNombre() {
    return "nodoArbolNombre";
  }

  @Override
public String getNodoArbolNombreCampoPadreId() {
    return null;
  }

  @Override
public void setNodoArbolPadre(NodoArbol nodoArbol)
  {
  }

  @Override
public NodoArbol getNodoArbolPadre() {
    return null;
  }

  @Override
public String getNodoArbolPadreId() {
    return null;
  }

  @Override
public void setNodoArbolHijosCargados(boolean cargados)
  {
  }

  @Override
public void setNodoArbolNombre(String nombre)
  {
  }
}