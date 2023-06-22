package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.strategos.model.CalificacionesRiesgo;
import com.strategos.nueva.bancoproyecto.strategos.model.ClaseIndicadoresStrategos;

public interface CalificacionesRiesgoService {

	public List<CalificacionesRiesgo> findAll();
	
	
	public CalificacionesRiesgo findById(Long id);

	
	public CalificacionesRiesgo save(CalificacionesRiesgo calificacionesRiesgo);
	
	
	public void delete(Long id);
	
}
