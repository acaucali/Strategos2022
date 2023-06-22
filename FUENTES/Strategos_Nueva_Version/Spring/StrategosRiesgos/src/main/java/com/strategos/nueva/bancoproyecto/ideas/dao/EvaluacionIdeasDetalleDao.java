package com.strategos.nueva.bancoproyecto.ideas.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.nueva.bancoproyecto.ideas.model.CriteriosEvaluacion;
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;

public interface EvaluacionIdeasDetalleDao extends JpaRepository<EvaluacionIdeasDetalle, Long>{

	List<EvaluacionIdeasDetalle> findAllByEvaluacionId(Long evaluacionId);
	
	List<EvaluacionIdeasDetalle> findAllByEvaluacionIdAndIdeaId(Long evaluacionId, Long ideaId);
	
}
