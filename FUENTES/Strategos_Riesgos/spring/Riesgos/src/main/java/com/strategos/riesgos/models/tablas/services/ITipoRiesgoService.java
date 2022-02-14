package com.strategos.riesgos.models.tablas.services;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;


import com.strategos.riesgos.models.tablas.entity.TipoRiesgo;

public interface ITipoRiesgoService {
	
	public List<TipoRiesgo> findAll();
	
	
	public Page<TipoRiesgo> findAll(Pageable pageable);
	
	
	public TipoRiesgo findById(Long id);

	
	public TipoRiesgo save(TipoRiesgo tipoRiesgo);
	
	
	public void delete(Long id);
}
