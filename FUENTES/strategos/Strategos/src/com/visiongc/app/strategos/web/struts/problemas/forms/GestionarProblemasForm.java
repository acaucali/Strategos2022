package com.visiongc.app.strategos.web.struts.problemas.forms;

import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class GestionarProblemasForm extends VisorListaForm
{
	static final long serialVersionUID = 0L;

	private String filtroNombre;
	private Integer tipo;

	public String getFiltroNombre()
	{
		return this.filtroNombre;
	}

	public void setFiltroNombre(String filtroNombre)
	{
		this.filtroNombre = filtroNombre;
	}

	public Integer getTipo()
	{
		return this.tipo;
	}

	public void setTipo(Integer tipo)
	{
		this.tipo = tipo;
	}
}