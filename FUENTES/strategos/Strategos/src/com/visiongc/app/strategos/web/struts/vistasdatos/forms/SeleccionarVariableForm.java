package com.visiongc.app.strategos.web.struts.vistasdatos.forms;

import java.util.List;

import com.visiongc.framework.web.struts.forms.SelectorListaForm;

public class SeleccionarVariableForm extends SelectorListaForm
{
	static final long serialVersionUID = 0L;

	private String filtroNombre;
	private Byte seleccionMultiple;
	private List listaVariables;

	public Byte getSeleccionMultiple()
	{
		return this.seleccionMultiple;
	}

	public void setSeleccionMultiple(Byte seleccionMultiple)
	{
		this.seleccionMultiple = seleccionMultiple;
	}

	public String getFiltroNombre()
	{
		return this.filtroNombre;
	}

	public void setFiltroNombre(String filtroNombre)
	{
		this.filtroNombre = filtroNombre;
	}

	public List getListaVariables()
	{
		return this.listaVariables;
	}

	public void setListaVariables(List listaVariables)
	{
		this.listaVariables = listaVariables;
	}

	public void clear()
	{
	}
}