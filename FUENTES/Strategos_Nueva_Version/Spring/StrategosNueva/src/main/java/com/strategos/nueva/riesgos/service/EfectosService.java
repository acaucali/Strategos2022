package com.strategos.nueva.riesgos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.nueva.riesgos.model.EfectosRiesgo;



public interface EfectosService {
	
	public List<EfectosRiesgo> findAll();
	
	
	public EfectosRiesgo findById(Long id);

	
	public EfectosRiesgo save(EfectosRiesgo efectos);
	
	
	public void delete(Long id);
}
