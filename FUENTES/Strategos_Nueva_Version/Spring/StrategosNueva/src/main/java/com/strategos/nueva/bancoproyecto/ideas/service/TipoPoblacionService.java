package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.TipoPoblacion;

public interface TipoPoblacionService {

	public List<TipoPoblacion> findAll();	

	
	public TipoPoblacion findById(Long id);

	
	public TipoPoblacion save(TipoPoblacion poblacion);
	
	
	public void delete(Long id);
	
}
