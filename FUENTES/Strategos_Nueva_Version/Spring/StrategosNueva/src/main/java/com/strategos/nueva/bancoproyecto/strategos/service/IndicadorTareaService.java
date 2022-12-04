package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorPerspectiva;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorTareaStrategos;

public interface IndicadorTareaService {

	public List<IndicadorTareaStrategos> findAll();	

	
	public IndicadorTareaStrategos findById(Long id);

	
	public IndicadorTareaStrategos save(IndicadorTareaStrategos indicador);
	
	
	public void delete(Long id);
	
	public void deleteIndicadorTarea(Long actividadId, Long indicadorId);
	
	public List<IndicadorTareaStrategos> findByTarea(Long actividadId);
	
}
