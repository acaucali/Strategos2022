package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.Proyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosPlan;
import com.strategos.nueva.bancoproyectos.model.util.FIltroIdea;

public interface ProyectosPlanService {

	public List<ProyectosPlan> findAll();	

	
	public ProyectosPlan findById(Long id);

	
	public ProyectosPlan save(ProyectosPlan proyectos);
	
	
	public void delete(Long id);
	
	public ProyectosPlan findAllByProyectoId(Long proyectoId);
		
}
