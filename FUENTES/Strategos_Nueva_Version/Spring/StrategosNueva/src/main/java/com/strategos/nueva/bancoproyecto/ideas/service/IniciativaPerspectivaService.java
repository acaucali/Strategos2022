package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.IniciativaPerspectiva;

public interface IniciativaPerspectivaService {

	public List<IniciativaPerspectiva> findAll();	

	
	public IniciativaPerspectiva findById(Long id);

	
	public IniciativaPerspectiva save(IniciativaPerspectiva iniciativa);
	
	
	public void delete(Long id);
	
	public List<IniciativaPerspectiva> findByPerspectiva(Long perspectivaId);
	
	
	public IniciativaPerspectiva findByPerspectivaIniciativa(Long perspectivaId, Long iniciativaId);
	
}
