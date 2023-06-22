package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.strategos.model.SerieIndicadorStrategos;

public interface SerieIndicadorService {

	public List<SerieIndicadorStrategos> findAll();	

	
	public SerieIndicadorStrategos findById(Long id);

	
	public SerieIndicadorStrategos save(SerieIndicadorStrategos serie);
	
	
	public void delete(Long id);
	
}
