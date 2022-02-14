package com.strategos.riesgos.models.ejercicios.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.riesgos.model.ejercicios.entity.CausaRiesgo;



public interface ICausaService {
	
	public List<CausaRiesgo> findAll();
	
	
	public Page<CausaRiesgo> findAll(Pageable pageable);
	
	
	public CausaRiesgo findById(Long id);

	
	public CausaRiesgo save(CausaRiesgo causa);
	
	
	public void delete(Long id);

}
