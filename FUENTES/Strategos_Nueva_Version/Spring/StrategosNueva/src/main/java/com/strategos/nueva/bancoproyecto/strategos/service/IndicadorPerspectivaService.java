package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorPerspectiva;

public interface IndicadorPerspectivaService {

	public List<IndicadorPerspectiva> findAll();	

	
	public IndicadorPerspectiva findById(Long id);

	
	public IndicadorPerspectiva save(IndicadorPerspectiva indicador);
	
	
	public void delete(Long id);
	
	
	public void deleteIndicadorPerspectiva(Long perspectivaId, Long indicadorId);
	
	public List<IndicadorPerspectiva> findByPerspectiva(Long perspectivaId);
	
	
	
}
