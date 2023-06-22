package com.strategos.nueva.bancoproyecto.strategos.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="serie_indicador")

public class SerieIndicadorStrategos implements Serializable{
	

	@EmbeddedId
	private SerieIndicadorPkStrategos seriePk;
	
	@Column(nullable=true)
	private Byte naturaleza;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date fechaBloqueo;
		
	public SerieIndicadorPkStrategos getSeriePk() {
		return seriePk;
	}

	public void setSeriePk(SerieIndicadorPkStrategos seriePk) {
		this.seriePk = seriePk;
	}

	public Byte getNaturaleza() {
		return naturaleza;
	}

	public void setNaturaleza(Byte naturaleza) {
		this.naturaleza = naturaleza;
	}

	public Date getFechaBloqueo() {
		return fechaBloqueo;
	}

	public void setFechaBloqueo(Date fechaBloqueo) {
		this.fechaBloqueo = fechaBloqueo;
	}

	private static final long serialVersionUID = 1L;
}
