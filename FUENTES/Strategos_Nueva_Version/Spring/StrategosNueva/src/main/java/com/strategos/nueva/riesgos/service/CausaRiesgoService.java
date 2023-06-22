package com.strategos.nueva.riesgos.service;

import org.springframework.data.domain.Pageable;

import com.strategos.nueva.riesgos.model.CausaRiesgo;

import java.util.List;

import org.springframework.data.domain.Page;





public interface CausaRiesgoService {
	
	public List<CausaRiesgo> findAll();
	
	
	public CausaRiesgo findById(Long id);

	
	public CausaRiesgo save(CausaRiesgo causas);
	
	
	public void delete(Long id);
}
