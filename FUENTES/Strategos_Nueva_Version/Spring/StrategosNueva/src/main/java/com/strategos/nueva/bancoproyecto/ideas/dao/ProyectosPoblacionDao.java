package com.strategos.nueva.bancoproyecto.ideas.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.nueva.bancoproyecto.ideas.model.CriteriosEvaluacion;
import com.strategos.nueva.bancoproyecto.ideas.model.EstatusIdeas;
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;
import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosPoblacion;

public interface ProyectosPoblacionDao extends JpaRepository<ProyectosPoblacion, Long>{

	List<ProyectosPoblacion> findAllByProyectoId(Long proyectoId);
	
}
