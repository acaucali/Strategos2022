package com.strategos.nueva.riesgos.ejercicios.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.nueva.riesgos.ejercicios.model.ControlesRiesgo;



public interface ControlesService {
	public List<ControlesRiesgo> findAll();
	
	

	
	
	public ControlesRiesgo findById(Long id);

	
	public ControlesRiesgo save(ControlesRiesgo control);
	
	
	public void delete(Long id);
}
