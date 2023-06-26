package com.visiongc.app.strategos.web.struts.cargos.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarCargosForm extends EditarObjetoForm{
	
	static final long serialVersionUID = 0L;
	private Long cargoId;
	private String nombre;
	
	public Long getCargoId() {
		return cargoId;
	}
	
	public void setCargoId(Long cargoId) {
		this.cargoId = cargoId;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	@Override 
	public void clear() {
		this.cargoId = new Long(0L);
		this.nombre = null;
		setBloqueado(new Boolean(false));
	}

}
