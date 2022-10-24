package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.Iniciativa;

public interface IniciativaService {

	public List<Iniciativa> findAll();	

	
	public Iniciativa findById(Long id);

	
	public Iniciativa save(Iniciativa iniciativa);
	
	
	public void delete(Long id);
	
}
