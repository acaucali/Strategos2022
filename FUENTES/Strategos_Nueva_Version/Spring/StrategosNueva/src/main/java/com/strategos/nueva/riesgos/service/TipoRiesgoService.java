package com.strategos.nueva.riesgos.service;

import org.springframework.data.domain.Pageable;

import com.strategos.nueva.riesgos.model.TipoRiesgo;

import java.util.List;

import org.springframework.data.domain.Page;



public interface TipoRiesgoService {
	
	public List<TipoRiesgo> findAll();
	
	
	public TipoRiesgo findById(Long id);

	
	public TipoRiesgo save(TipoRiesgo tipoRiesgo);
	
	
	public void delete(Long id);
}