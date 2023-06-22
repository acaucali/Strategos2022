package com.strategos.nueva.riesgos.service;

import org.springframework.data.domain.Pageable;

import com.strategos.nueva.riesgos.model.TipoImpacto;

import java.util.List;

import org.springframework.data.domain.Page;

public interface TipoImpactoService {
	
	public List<TipoImpacto> findAll();

	
	public TipoImpacto findById(Long id);

	
	public TipoImpacto save(TipoImpacto tipoImpacto);
	
	
	public void delete(Long id);


	public List<TipoImpacto> findAllByPuntaje();
}
