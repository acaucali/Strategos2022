package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyectos.model.util.FIltroIdea;

public interface IdeasProyectosService {

	public List<IdeasProyectos> findAll();	
	
	
	public List<IdeasProyectos> queryFiltros(FIltroIdea filtro);	

	
	public IdeasProyectos findById(Long id);

	
	public IdeasProyectos save(IdeasProyectos proyecto);
	
	
	public void delete(Long id);
	
	List<IdeasProyectos> findAllByDependenciaId(Long dependenciaId);
	
}
