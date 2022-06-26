package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.strategos.model.IniciativaEstatusStrategos;

public interface IniciativaEstatusService {

	public List<IniciativaEstatusStrategos> findAll();	

	
	public IniciativaEstatusStrategos findById(Long id);

	
	public IniciativaEstatusStrategos save(IniciativaEstatusStrategos iniciativa);
	
	
	public void delete(Long id);
	
}
