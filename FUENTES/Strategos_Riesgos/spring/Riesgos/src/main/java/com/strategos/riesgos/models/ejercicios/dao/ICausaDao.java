package com.strategos.riesgos.models.ejercicios.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.riesgos.model.ejercicios.entity.CausaRiesgo;

public interface ICausaDao extends JpaRepository<CausaRiesgo, Long>{

}
