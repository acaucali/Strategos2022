package com.strategos.nueva.riesgos.procesos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.nueva.riesgos.procesos.model.ProcesoCaracterizacion;


public interface ProcesoCaracterizacionService {
	
	public List<ProcesoCaracterizacion> findAll();
	
	
	public ProcesoCaracterizacion findById(Long id);

	
	public ProcesoCaracterizacion save(ProcesoCaracterizacion procesoCaracterizacion);
	
	
	public void delete(Long id);
	
	
}
