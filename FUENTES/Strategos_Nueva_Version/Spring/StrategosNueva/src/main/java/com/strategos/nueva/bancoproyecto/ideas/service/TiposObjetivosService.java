package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.TiposObjetivos;

public interface TiposObjetivosService {

	public List<TiposObjetivos> findAll();	

	
	public TiposObjetivos findById(Long id);

	
	public TiposObjetivos save(TiposObjetivos tipoObjetivo);
	
	
	public void delete(Long id);
	
}
