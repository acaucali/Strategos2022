package com.strategos.nueva.bancoproyecto.ideas.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.nueva.bancoproyecto.ideas.model.CriteriosEvaluacion;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.Actividad;

public interface ActividadDao extends JpaRepository<Actividad, Long>{

	List<Actividad> findAllByProyectoId(Long proyectoId);
	
}
