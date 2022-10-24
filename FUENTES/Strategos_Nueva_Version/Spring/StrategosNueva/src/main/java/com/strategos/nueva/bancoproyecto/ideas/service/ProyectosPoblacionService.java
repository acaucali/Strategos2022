package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;
import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosPoblacion;

public interface ProyectosPoblacionService {

	public List<ProyectosPoblacion> findAll();	

	List<ProyectosPoblacion> findAllByProyectoId(Long proyectoId);
	
		
	public ProyectosPoblacion findById(Long id);

	
	public ProyectosPoblacion save(ProyectosPoblacion proyectos);
	
	
	public void delete(Long id);
	
}
