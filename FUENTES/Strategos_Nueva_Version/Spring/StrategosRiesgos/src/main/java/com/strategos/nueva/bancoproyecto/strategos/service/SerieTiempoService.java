package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.strategos.model.SerieTiempoStrategos;

public interface SerieTiempoService {

	public List<SerieTiempoStrategos> findAll();	

	
	public SerieTiempoStrategos findById(Long id);

	
	public SerieTiempoStrategos save(SerieTiempoStrategos serie);
	
	
	public void delete(Long id);
	
}
