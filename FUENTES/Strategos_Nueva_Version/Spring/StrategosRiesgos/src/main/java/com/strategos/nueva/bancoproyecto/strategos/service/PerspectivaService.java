package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.strategos.model.PerspectivaStrategos;

public interface PerspectivaService {

	public List<PerspectivaStrategos> findAll();	

	
	public PerspectivaStrategos findById(Long id);

	
	public PerspectivaStrategos save(PerspectivaStrategos perspectiva);
	
	
	public void delete(Long id);
	
	public List<PerspectivaStrategos> findAllByPlanId(Long planId);
}
