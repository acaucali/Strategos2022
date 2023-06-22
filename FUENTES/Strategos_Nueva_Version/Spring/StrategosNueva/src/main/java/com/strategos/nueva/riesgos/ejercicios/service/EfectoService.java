package com.strategos.nueva.riesgos.ejercicios.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.nueva.riesgos.ejercicios.model.EfectoRiesgo;


public interface EfectoService {
	public List<EfectoRiesgo> findAll();
	

	
	
	public EfectoRiesgo findById(Long id);

	
	public EfectoRiesgo save(EfectoRiesgo efecto);
	
	
	public void delete(Long id);
}
