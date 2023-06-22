package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.EstatusIdeas;

public interface EstatusIdeaService {

	public List<EstatusIdeas> findAll();	

	
	public EstatusIdeas findById(Long id);

	
	public EstatusIdeas save(EstatusIdeas estatus);
	
	
	public void delete(Long id);
	
}
