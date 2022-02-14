package com.strategos.riesgos.models.ejercicios.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;


import com.strategos.riesgos.model.ejercicios.entity.FactoresRiesgo;

public interface IFactoresRiesgoDao extends JpaRepository<FactoresRiesgo, Long>{

	List<FactoresRiesgo> findAllByProcesoId(Long procesoId);
	
	List<FactoresRiesgo> findAllByProbabilidad(int probabilidad);
	
	List<FactoresRiesgo> findAllByImpacto(int impacto);
	
	List<FactoresRiesgo> findAllBySeveridad(int severidad);
}
