package com.strategos.nueva.riesgos.ejercicios.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.strategos.nueva.riesgos.ejercicios.model.RiCausaRiesgo;



public interface CausaDao extends JpaRepository<RiCausaRiesgo, Long>{
	
	@Query("SELECT DISTINCT r.causa from RiCausaRiesgo r ")
	public List<String> findByCausa2();

}
