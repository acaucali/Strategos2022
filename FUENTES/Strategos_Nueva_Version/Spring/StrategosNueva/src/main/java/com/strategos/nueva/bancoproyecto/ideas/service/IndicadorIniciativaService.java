package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.IndicadorIniciativa;

public interface IndicadorIniciativaService {

	public List<IndicadorIniciativa> findAll();	

	
	public IndicadorIniciativa findById(Long id);

	
	public IndicadorIniciativa save(IndicadorIniciativa indicador);
	
	
	public void delete(Long id);
	
}
