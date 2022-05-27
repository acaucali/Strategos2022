package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.IdeasEvaluadas;

public interface IdeasEvaluadasService {

	public List<IdeasEvaluadas> findAll();	

	
	public IdeasEvaluadas findById(Long id);

	
	public IdeasEvaluadas save(IdeasEvaluadas ideas);
	
	
	public void delete(Long id);
	
}
