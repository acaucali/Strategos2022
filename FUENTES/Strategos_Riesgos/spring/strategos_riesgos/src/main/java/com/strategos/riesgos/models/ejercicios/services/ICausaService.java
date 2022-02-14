package com.strategos.riesgos.models.ejercicios.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.riesgos.model.ejercicios.entity.RiCausaRiesgo;



public interface ICausaService {
	
	public List<RiCausaRiesgo> findAll();
	
	
	public List<String> findByCausa2();	
	
	
	public Page<RiCausaRiesgo> findAll(Pageable pageable);
	
	
	public RiCausaRiesgo findById(Long id);

	
	public RiCausaRiesgo save(RiCausaRiesgo causa);
	
	
	public void delete(Long id);

}
