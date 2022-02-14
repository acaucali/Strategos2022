package com.strategos.riesgos.models.tablas.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.riesgos.models.tablas.entity.TipoImpacto;

public interface ITipoImpactoDao extends JpaRepository<TipoImpacto, Long>{

}
