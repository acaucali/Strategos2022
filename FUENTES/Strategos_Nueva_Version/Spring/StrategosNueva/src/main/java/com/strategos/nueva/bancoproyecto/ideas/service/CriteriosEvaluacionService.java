package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.CriteriosEvaluacion;

public interface CriteriosEvaluacionService {

	public List<CriteriosEvaluacion> findAll();	

	
	public CriteriosEvaluacion findById(Long id);

	
	public CriteriosEvaluacion save(CriteriosEvaluacion criterio);
	
	
	public void delete(Long id);
	
}
