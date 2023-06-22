package com.strategos.nueva.riesgos.ejercicios.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.nueva.riesgos.ejercicios.model.RiCausaRiesgo;





public interface CausaService {
	
	public List<RiCausaRiesgo> findAll();
	
	
	public List<String> findByCausa2();	
	

	
	
	public RiCausaRiesgo findById(Long id);

	
	public RiCausaRiesgo save(RiCausaRiesgo causa);
	
	
	public void delete(Long id);

}
