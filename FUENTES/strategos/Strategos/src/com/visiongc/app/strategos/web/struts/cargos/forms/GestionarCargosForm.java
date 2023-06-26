package com.visiongc.app.strategos.web.struts.cargos.forms;

import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class GestionarCargosForm extends VisorListaForm {

	static final long serialVersionUID = 0L;
	private String filtroNombre;

	public String getFiltroNombre() {
		return this.filtroNombre;
	}

	public void setFiltroNombre(String filtroNombre) {
		this.filtroNombre = filtroNombre;
	}
}
