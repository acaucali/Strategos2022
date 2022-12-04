package com.strategos.nueva.bancoproyecto.strategos.model;

import java.io.Serializable;

import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable
public class IndicadorTareaPk implements Serializable{
	
	private Long actividadId;

	private Long indicadorId; 
	
	

	public Long getActividadId() {
		return actividadId;
	}

	public void setActividadId(Long actividadId) {
		this.actividadId = actividadId;
	}

	public Long getIndicadorId() {
		return indicadorId;
	}

	public void setIndicadorId(Long indicadorId) {
		this.indicadorId = indicadorId;
	}

	public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.actividadId);
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
        final IndicadorTareaPk other = (IndicadorTareaPk) obj;
        if (!Objects.equals(this.indicadorId, other.indicadorId)) {
            return false;
        }
        if (!Objects.equals(this.actividadId, other.actividadId)) {
            return false;
        }
        return true;
    }
	
	private static final long serialVersionUID = 1L;
}
