package com.strategos.nueva.bancoproyecto.strategos.model;

import java.io.Serializable;

import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.Id;

@Embeddable
public class IndicadorPerspectivaPk implements Serializable{
	
	private Long perspectivaId;

	private Long indicadorId;
	
	public Long getPerspectivaId() {
		return perspectivaId;
	}

	public void setPerspectivaId(Long perspectivaId) {
		this.perspectivaId = perspectivaId;
	}

	public Long getIndicadorId() {
		return indicadorId;
	}

	public void setIndicadorId(Long indicadorId) {
		this.indicadorId = indicadorId;
	}

	public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.perspectivaId);
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
        final IndicadorPerspectivaPk other = (IndicadorPerspectivaPk) obj;
        if (!Objects.equals(this.indicadorId, other.indicadorId)) {
            return false;
        }
        if (!Objects.equals(this.perspectivaId, other.perspectivaId)) {
            return false;
        }
        return true;
    }
	
	private static final long serialVersionUID = 1L;
}
