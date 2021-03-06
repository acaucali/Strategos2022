package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;

public interface EvaluacionIdeasDetalleService {

	public List<EvaluacionIdeasDetalle> findAll();	
	
	List<EvaluacionIdeasDetalle> findAllByEvaluacionId(Long evaluacionId);
	
	List<EvaluacionIdeasDetalle> findAllByEvaluacionIdAndIdeaId(Long evaluacionId, Long ideaId);

	
	public EvaluacionIdeasDetalle findById(Long id);

	
	public EvaluacionIdeasDetalle save(EvaluacionIdeasDetalle evaluacion);
	
	
	public void delete(Long id);
	
}
