package com.visiongc.app.strategos.web.struts.explicaciones.forms;

import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class GestionarExplicacionesPGNForm extends VisorListaForm{
	static final long serialVersionUID = 0L;
	
	private String filtroTitulo;
	private String filtroAutor;
	private String nombreOrganizacion;
	private Long objetoId;			
	private String nombreObjetoKey;

	public String getNombreOrganizacion()
	{
		return this.nombreOrganizacion;
	}

	public void setNombreOrganizacion(String nombreOrganizacion)
	{
		this.nombreOrganizacion = nombreOrganizacion;
	}
	
	public String getFiltroTitulo()
	{
		return this.filtroTitulo;
	}

	public void setFiltroTitulo(String filtroTitulo)
	{
		this.filtroTitulo = filtroTitulo;
	}
	
	public String getNombreObjetoKey()
	{
		return this.nombreObjetoKey;
	}

	public void setNombreObjetoKey(String nombreObjetoKey)
	{
		this.nombreObjetoKey = nombreObjetoKey;
	}

	public String getFiltroAutor()
	{
		return this.filtroAutor;
	}

	public void setFiltroAutor(String filtroAutor)
	{
		this.filtroAutor = filtroAutor;
	}

	public Long getObjetoId()
	{
		return this.objetoId;
	}

	public void setObjetoId(Long objetoId)
	{
		this.objetoId = objetoId;
	}

	
}
