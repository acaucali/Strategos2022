package com.strategos.nueva.bancoproyecto.strategos.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class SerieIndicadorPkStrategos implements Serializable{
		
	private Long serieId;	
	
	private Long indicadorId;
	
	@Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.serieId);
        hash = 59 * hash + Objects.hashCode(this.indicadorId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SerieIndicadorPkStrategos other = (SerieIndicadorPkStrategos) obj;
        if (!Objects.equals(this.serieId, other.serieId)) {
            return false;
        }
        if (!Objects.equals(this.indicadorId, other.indicadorId)) {
            return false;
        }
        return true;
    }

	public Long getSerieId() {
		return serieId;
	}

	public void setSerieId(Long serieId) {
		this.serieId = serieId;
	}

	public Long getIndicadorId() {
		return indicadorId;
	}

	public void setIndicadorId(Long indicadorId) {
		this.indicadorId = indicadorId;
	}
       
	private static final long serialVersionUID = 1L;
}
