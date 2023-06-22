package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.strategos.model.MetodologiaStrategos;

public interface MetodologiaService {

	public List<MetodologiaStrategos> findAll();	

	
	public MetodologiaStrategos findById(Long id);

	
	public MetodologiaStrategos save(MetodologiaStrategos metodologia);
	
	
	public void delete(Long id);
	
}
