package com.strategos.nueva.bancoproyecto.ideas.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.nueva.bancoproyecto.ideas.model.CriteriosEvaluacion;
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;

public interface EvaluacionIdeasDetalleDao extends JpaRepository<EvaluacionIdeasDetalle, Long>{

}
