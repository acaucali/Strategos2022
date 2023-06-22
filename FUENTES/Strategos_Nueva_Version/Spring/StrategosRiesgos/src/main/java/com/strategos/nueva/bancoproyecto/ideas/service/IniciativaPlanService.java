package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.IniciativaPlan;

public interface IniciativaPlanService {

	public List<IniciativaPlan> findAll();	

	
	public IniciativaPlan findById(Long id);

	
	public IniciativaPlan save(IniciativaPlan iniciativa);
	
	
	public void delete(Long id);
	
}
