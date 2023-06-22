package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.strategos.model.UnidadStrategos;

public interface UnidadService {

	public List<UnidadStrategos> findAll();	

	
	public UnidadStrategos findById(Long id);

	
	public UnidadStrategos save(UnidadStrategos unidad);
	
	
	public void delete(Long id);
	
}
