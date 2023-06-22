package com.strategos.nueva.riesgos.service;

import java.util.List;


import com.strategos.nueva.bancoproyecto.strategos.model.ClaseIndicadoresStrategos;
import com.strategos.nueva.riesgos.model.CalificacionesRiesgo;

public interface CalificacionesRiesgoService {

	public List<CalificacionesRiesgo> findAll();
	
	
	public CalificacionesRiesgo findById(Long id);

	
	public CalificacionesRiesgo save(CalificacionesRiesgo calificacionesRiesgo);
	
	
	public void delete(Long id);
	
}
