package com.strategos.riesgos.models.ejercicios.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.riesgos.model.ejercicios.entity.EjercicioRiesgo;

public interface IEjercicioRiesgoDao extends JpaRepository<EjercicioRiesgo, Long>{

	Page<EjercicioRiesgo> findAllByProcesoPadreId(Long procesoPadreId, Pageable pageable);
}
