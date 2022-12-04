package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.strategos.model.Responsable;

public interface ResponsableService {

	public List<Responsable> findAll();	

	
	public Responsable findById(Long id);

	
	public Responsable save(Responsable responsable);
	
	
	public void delete(Long id);
	
}
