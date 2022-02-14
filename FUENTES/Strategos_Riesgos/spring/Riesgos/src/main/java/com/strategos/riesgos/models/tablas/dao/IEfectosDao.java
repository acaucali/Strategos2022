package com.strategos.riesgos.models.tablas.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.riesgos.models.tablas.entity.EfectosRiesgo;

public interface IEfectosDao extends JpaRepository<EfectosRiesgo, Long>{

}
