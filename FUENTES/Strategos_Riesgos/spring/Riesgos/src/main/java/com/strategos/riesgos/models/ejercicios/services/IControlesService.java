package com.strategos.riesgos.models.ejercicios.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.riesgos.model.ejercicios.entity.ControlesRiesgo;

public interface IControlesService {
	public List<ControlesRiesgo> findAll();
	
	
	public Page<ControlesRiesgo> findAll(Pageable pageable);
	
	
	public ControlesRiesgo findById(Long id);

	
	public ControlesRiesgo save(ControlesRiesgo control);
	
	
	public void delete(Long id);
}
