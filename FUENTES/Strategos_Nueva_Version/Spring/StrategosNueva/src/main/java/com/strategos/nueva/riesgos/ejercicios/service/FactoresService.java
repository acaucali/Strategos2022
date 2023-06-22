package com.strategos.nueva.riesgos.ejercicios.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.nueva.riesgos.ejercicios.model.FactoresRiesgo;


public interface FactoresService {
	
	public List<FactoresRiesgo> findAll();
	
	
	public FactoresRiesgo findById(Long id);

	
	public FactoresRiesgo save(FactoresRiesgo factor);
	
	
	public void delete(Long id);
	
	
	public List<FactoresRiesgo> findAllByProcesoId(Long procesoId);
	
	
	public List<FactoresRiesgo> findAllByProbabilidad(int probabilidad);
	
	
	public List<FactoresRiesgo> findAllByImpacto(int impacto);
	
	
	public List<FactoresRiesgo> findAllBySeveridad(int severidad);
}
