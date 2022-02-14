package com.strategos.riesgos.models.tablas.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.riesgos.models.tablas.entity.Probabilidad;
import com.strategos.riesgos.models.tablas.entity.TipoImpacto;

public interface IProbabilidadDao extends JpaRepository<Probabilidad, Long>{
	
}
