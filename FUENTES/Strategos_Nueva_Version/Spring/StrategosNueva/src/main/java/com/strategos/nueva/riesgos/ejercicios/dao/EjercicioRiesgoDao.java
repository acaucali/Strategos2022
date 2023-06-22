package com.strategos.nueva.riesgos.ejercicios.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.nueva.riesgos.ejercicios.model.EjercicioRiesgo;



public interface EjercicioRiesgoDao extends JpaRepository<EjercicioRiesgo, Long>{

	Page<EjercicioRiesgo> findAllByProcesoPadreId(Long procesoPadreId, Pageable pageable);
}
