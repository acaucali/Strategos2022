package com.strategos.riesgos.models.tablas.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.riesgos.models.tablas.entity.CausaRiesgo;

public interface ICausaRiesgoDao extends JpaRepository<CausaRiesgo, Long>{

}
