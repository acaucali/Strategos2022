package com.visiongc.app.strategos.explicaciones.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.model.Usuario;

public class ExplicacionPGN implements Serializable {

	static final long serialVersionUID = 0L;
	private Long explicacionId;
	private Date fecha;
	private Date creado;
	private String fechaFormateadaCreado;
	private Long creadoId;
	private Long objetoId;
	private String titulo;
	private Usuario usuarioCreado;
	private Boolean cumplimiendoFechas;
	private String explicacionFechas;
	private Boolean recibido;
	private String explicacionRecibido;

	public ExplicacionPGN() {
	}

	public Long getExplicacionId() {
		return explicacionId;
	}

	public void setExplicacionId(Long explicacionId) {
		this.explicacionId = explicacionId;
	}

	public Date getFecha() {
		return fecha;
	}

	public String getFechaFormateada() {
		return VgcFormatter.formatearFecha(fecha, "formato.fecha.corta");
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Long getCreadoId() {
		return creadoId;
	}

	public void setCreadoId(Long creadoId) {
		this.creadoId = creadoId;
	}

	public Date getCreado() {
		return creado;
	}

	public void setCreado(Date creado) {
		this.creado = creado;
	}

	public Long getObjetoId() {
		return objetoId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setObjetoId(Long objetoId) {
		this.objetoId = objetoId;
	}

	public Boolean getCumplimiendoFechas() {
		return cumplimiendoFechas;
	}

	public void setCumplimiendoFechas(Boolean cumplimiendoFechas) {
		this.cumplimiendoFechas = cumplimiendoFechas;
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

	public String getFechaFormateadaCreado() {
		fechaFormateadaCreado = VgcFormatter.formatearFecha(creado, "formato.fecha.corta");
		return fechaFormateadaCreado;
	}

	public void setFechaFormateadaCreado(String fechaFormateadaCreado) {
		this.fechaFormateadaCreado = fechaFormateadaCreado;
	}

	public Usuario getUsuarioCreado() {
		return usuarioCreado;
	}

	public void setUsuarioCreado(Usuario usuarioCreado) {
		this.usuarioCreado = usuarioCreado;
	}

	public int compareTo(Object o) {
		ExplicacionPGN or = (ExplicacionPGN) o;
		return getExplicacionId().compareTo(or.getExplicacionId());
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExplicacionPGN other = (ExplicacionPGN) obj;

		if (explicacionId == null) {
			if (explicacionId != null) {
				return false;
			}
		} else if (!explicacionId.equals(explicacionId)) {
			return false;
		}
		return true;
	}

	public String toString() {
		return new ToStringBuilder(this).append("explicacionId", getExplicacionId()).toString();
	}
}
