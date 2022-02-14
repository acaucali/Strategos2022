package com.strategos.riesgos.models.tablas.services;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.strategos.riesgos.models.tablas.entity.CalificacionesRiesgo;

public interface ICalificacionesRiesgoService {
	
	public List<CalificacionesRiesgo> findAll();
	
	
	public Page<CalificacionesRiesgo> findAll(Pageable pageable);
	
	
	public CalificacionesRiesgo findById(Long id);

	
	public CalificacionesRiesgo save(CalificacionesRiesgo calificacionesRiesgo);
	
	
	public void delete(Long id);
}
