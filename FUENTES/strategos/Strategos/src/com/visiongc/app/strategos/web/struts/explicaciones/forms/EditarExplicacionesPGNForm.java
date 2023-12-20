package com.visiongc.app.strategos.web.struts.explicaciones.forms;

import java.util.ArrayList;
import java.util.Date;

import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class EditarExplicacionesPGNForm extends EditarObjetoForm {
	static final long serialVersionUID = 0L;

	private Long explicacionId;
	private String fecha;
	private Long creadoId;
	private Long modificadoId;
	private Long objetoId;
	private String titulo;
	private String nombreUsuarioCreado;
	private String nombreUsuarioModificado;
	private String creado;
	private String fechaModificado;
	private String nombreObjetoKey;
	private String nombreOrganizacion;
	private Boolean cumpliendoFechas;
	private String explicacionFechas;
	private Boolean recibido;
	private String explicacionRecibido;

	public Long getExplicacionId() {
		return explicacionId;
	}

	public void setExplicacionId(Long explicacionId) {
		this.explicacionId = explicacionId;
	}

	public String getFecha() {
		return this.fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public Long getCreadoId() {
		return this.creadoId;
	}

	public void setCreadoId(Long creadoId) {
		this.creadoId = creadoId;
	}

	public Long getModificadoId() {
		return this.modificadoId;
	}

	public void setModificadoId(Long modificadoId) {
		this.modificadoId = modificadoId;
	}

	public String getCreado() {
		return creado;
	}

	public void setCreado(String creado) {
		this.creado = creado;
	}

	public String getFechaModificado() {
		return this.fechaModificado;
	}

	public void setFechaModificado(String fechaModificado) {
		this.fechaModificado = fechaModificado;
	}

	public Long getObjetoId() {
		return objetoId;
	}

	public void setObjetoId(Long objetoId) {
		this.objetoId = objetoId;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNombreUsuarioCreado() {
		return this.nombreUsuarioCreado;
	}

	public void setNombreUsuarioCreado(String nombreUsuarioCreado) {
		this.nombreUsuarioCreado = nombreUsuarioCreado;
	}

	public String getNombreUsuarioModificado() {
		return this.nombreUsuarioModificado;
	}

	public void setNombreUsuarioModificado(String nombreUsuarioModificado) {
		this.nombreUsuarioModificado = nombreUsuarioModificado;
	}

	public Boolean getCumpliendoFechas() {
		return cumpliendoFechas;
	}

	public void setCumpliendoFechas(Boolean cumpliendoFechas) {
		this.cumpliendoFechas = cumpliendoFechas;
	}

	public String getExplicacionFechas() {
		return explicacionFechas;
	}

	public void setExplicacionFechas(String explicacionFechas) {
		this.explicacionFechas = explicacionFechas;
	}

	public Boolean getRecibido() {
		return recibido;
	}

	public void setRecibido(Boolean recibido) {
		this.recibido = recibido;
	}

	public String getExplicacionRecibido() {
		return explicacionRecibido;
	}

	public void setExplicacionRecibido(String explicacionRecibido) {
		this.explicacionRecibido = explicacionRecibido;
	}

	public String getNombreObjetoKey() {
		return this.nombreObjetoKey;
	}

	public void setNombreObjetoKey(String nombreObjetoKey) {
		this.nombreObjetoKey = nombreObjetoKey;
	}

	public String getNombreOrganizacion() {
		return this.nombreOrganizacion;
	}

	public void setNombreOrganizacion(String nombreOrganizacion) {
		this.nombreOrganizacion = nombreOrganizacion;
	}

	@Override
	public void clear() {
		this.explicacionId = new Long(0L);
		this.creado = null;
		this.objetoId = new Long(0L);
		this.cumpliendoFechas = null;
		this.explicacionFechas = null;
		this.recibido = null;
		this.explicacionRecibido = null;
		this.explicacionId = new Long(0L);
		this.fecha = null;
		this.creadoId = new Long(0L);
		this.modificadoId = new Long(0L);
		this.objetoId = new Long(0L);
		this.titulo = null;
		this.nombreUsuarioCreado = null;
		this.nombreUsuarioModificado = null;
		this.fechaModificado = null;
		this.nombreObjetoKey = null;
		this.nombreOrganizacion = null;
		setBloqueado(new Boolean(false));
	}
}
