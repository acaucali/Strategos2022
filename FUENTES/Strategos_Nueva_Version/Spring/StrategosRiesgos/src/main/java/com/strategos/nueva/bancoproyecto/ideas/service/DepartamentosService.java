package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.Departamentos;

public interface DepartamentosService {
	
	public List<Departamentos> findAll();
	
	public Departamentos findById(Long id);
	
	public Departamentos save(Departamentos departamento);
	
	public void delete(Long id);

}
