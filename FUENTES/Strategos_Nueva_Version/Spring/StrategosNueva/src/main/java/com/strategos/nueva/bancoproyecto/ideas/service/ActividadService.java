package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.Actividad;

public interface ActividadService {

	public List<Actividad> findAll();	

	
	public Actividad findById(Long id);

	
	public Actividad save(Actividad actividad);
	
	
	public void delete(Long id);
	
}
