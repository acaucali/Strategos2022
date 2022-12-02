package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosRegion;

public interface ProyectosRegionService {

	public List<ProyectosRegion> findAll();	

	List<ProyectosRegion> findAllByDepartamentoId(Long departamentoId);
	
	List<ProyectosRegion> findAllByProyectoId(Long proyectoId);
	
	public ProyectosRegion findById(Long id);
	
	public ProyectosRegion save(ProyectosRegion proyectos);
	
	public void delete(Long id);
}
