package com.strategos.riesgos.models.tablas.services;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;


import com.strategos.riesgos.models.tablas.entity.CausaRiesgo;


public interface ICausaRiesgoService {
	
	public List<CausaRiesgo> findAll();
	
	
	public Page<CausaRiesgo> findAll(Pageable pageable);
	
	
	public CausaRiesgo findById(Long id);

	
	public CausaRiesgo save(CausaRiesgo causas);
	
	
	public void delete(Long id);
}
