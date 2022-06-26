package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.Proyectos;
import com.strategos.nueva.bancoproyectos.model.util.FIltroIdea;

public interface ProyectosService {

	public List<Proyectos> findAll();	

	
	public Proyectos findById(Long id);

	
	public Proyectos save(Proyectos proyectos);
	
	
	public void delete(Long id);
	
	List<Proyectos> findAllByDependenciaId(Long dependenciaId);
	
	
	public List<Proyectos> queryFiltros(FIltroIdea filtro);	
	
}
