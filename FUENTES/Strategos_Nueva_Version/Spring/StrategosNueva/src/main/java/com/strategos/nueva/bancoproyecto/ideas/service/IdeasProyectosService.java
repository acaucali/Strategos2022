package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;

public interface IdeasProyectosService {

	public List<IdeasProyectos> findAll();	

	
	public IdeasProyectos findById(Long id);

	
	public IdeasProyectos save(IdeasProyectos proyecto);
	
	
	public void delete(Long id);
	
}
