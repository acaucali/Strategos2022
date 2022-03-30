package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.EstatusProyecto;

public interface EstatusProyectoService {

	public List<EstatusProyecto> findAll();	

	
	public EstatusProyecto findById(Long id);

	
	public EstatusProyecto save(EstatusProyecto estatus);
	
	
	public void delete(Long id);
	
}
