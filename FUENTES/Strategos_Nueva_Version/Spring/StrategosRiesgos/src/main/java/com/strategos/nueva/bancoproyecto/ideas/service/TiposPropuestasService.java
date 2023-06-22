package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.TiposPropuestas;

public interface TiposPropuestasService {

	public List<TiposPropuestas> findAll();	

	
	public TiposPropuestas findById(Long id);

	
	public TiposPropuestas save(TiposPropuestas tipoPropuesta);
	
	
	public void delete(Long id);
	
}
