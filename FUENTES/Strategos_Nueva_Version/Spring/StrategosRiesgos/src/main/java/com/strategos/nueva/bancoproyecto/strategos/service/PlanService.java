package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.strategos.model.PlanStrategos;

public interface PlanService {

	public List<PlanStrategos> findAll();	

	
	public PlanStrategos findById(Long id);

	
	public PlanStrategos save(PlanStrategos plan);
	
	
	public void delete(Long id);
	
}
