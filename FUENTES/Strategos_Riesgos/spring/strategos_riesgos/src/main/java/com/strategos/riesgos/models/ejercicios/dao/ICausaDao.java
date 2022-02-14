package com.strategos.riesgos.models.ejercicios.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.strategos.riesgos.model.ejercicios.entity.RiCausaRiesgo;

public interface ICausaDao extends JpaRepository<RiCausaRiesgo, Long>{
	
	@Query("SELECT DISTINCT r.causa from RiCausaRiesgo r ")
	public List<String> findByCausa2();

}
