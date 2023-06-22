package com.strategos.nueva.riesgos.ejercicios.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.nueva.riesgos.ejercicios.model.FactoresRiesgo;




public interface FactoresRiesgoDao extends JpaRepository<FactoresRiesgo, Long>{

	List<FactoresRiesgo> findAllByProcesoId(Long procesoId);
	
	List<FactoresRiesgo> findAllByProbabilidad(int probabilidad);
	
	List<FactoresRiesgo> findAllByImpacto(int impacto);
	
	List<FactoresRiesgo> findAllBySeveridad(int severidad);
}
