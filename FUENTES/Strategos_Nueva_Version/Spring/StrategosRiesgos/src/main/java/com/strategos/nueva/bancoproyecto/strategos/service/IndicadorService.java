package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorStrategos;

public interface IndicadorService {

	public List<IndicadorStrategos> findAll();	

	
	public IndicadorStrategos findById(Long id);

	
	public IndicadorStrategos save(IndicadorStrategos indicador);
	
	
	public void delete(Long id);
	
}
