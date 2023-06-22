package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeas;

public interface EvaluacionIdeasService {

	public List<EvaluacionIdeas> findAll();	

	
	public EvaluacionIdeas findById(Long id);

	
	public EvaluacionIdeas save(EvaluacionIdeas evaluacion);
	
	
	public void delete(Long id);
	
}
