package com.visiongc.app.strategos.web.struts.taglib;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

import com.visiongc.app.strategos.planificacionseguimiento.model.util.AlertaIniciativaProducto;
import com.visiongc.commons.struts.tag.VgcBaseTag;

public class ImagenAlertaIniciativaTag extends VgcBaseTag
{
	static final long serialVersionUID = 0L;
	protected String name = null;

	protected String property = null;

	protected String scope = null;

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getScope()
	{
		return this.scope;
	}

	public void setScope(String scope)
	{
		this.scope = scope;
	}

	public String getProperty()
	{
		return this.property;
	}

	public void setProperty(String property)
	{
		this.property = property;
	}

	@Override
	public int doStartTag() throws JspException
	{
		String nombreImagen = "alertaBlanca.gif";

		Object value = TagUtils.getInstance().lookup(this.pageContext, this.name, this.property, this.scope);

		if (value != null)
		{
			Byte valorAlerta = Byte.valueOf(value.toString());

			if (valorAlerta.equals(AlertaIniciativaProducto.getAlertaRoja()))
				nombreImagen = "alertaRoja.gif";
			else if (valorAlerta.equals(AlertaIniciativaProducto.getAlertaAmarilla()))
				nombreImagen = "alertaAmarilla.gif";
			else if (valorAlerta.equals(AlertaIniciativaProducto.getAlertaVerde()))
				nombreImagen = "alertaVerde.gif";
			else if (valorAlerta.equals(AlertaIniciativaProducto.getAlertaNoDefinible()))
				nombreImagen = "alertaBlanca.gif";
			else if (valorAlerta.equals(AlertaIniciativaProducto.getAlertaEnEsperaComienzo()))
				nombreImagen = "alertaBlanca.gif";
		}

		String resultado = "<img style=\"cursor: pointer\" src=\"" + getUrlAplicacion() + "/paginas/strategos/indicadores/imagenes/" + nombreImagen + "\" border=\"0\" width=\"10\" height=\"10\" title=\"" + getMessageResource(null, null, "boton.alerta.alt") + "\">";

		TagUtils.getInstance().write(this.pageContext, resultado);

		return 0;
	}

	@Override
	public void release()
	{
		super.release();
		this.name = null;
		this.property = null;
		this.scope = null;
	}
}