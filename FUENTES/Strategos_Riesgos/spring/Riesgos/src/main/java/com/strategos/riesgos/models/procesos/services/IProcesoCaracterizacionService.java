package com.strategos.riesgos.models.procesos.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.riesgos.models.procesos.entity.ProcesoCaracterizacion;

public interface IProcesoCaracterizacionService {
	
	public List<ProcesoCaracterizacion> findAll();
	
	
	public Page<ProcesoCaracterizacion> findAll(Pageable pageable);
	
	
	public ProcesoCaracterizacion findById(Long id);

	
	public ProcesoCaracterizacion save(ProcesoCaracterizacion procesoCaracterizacion);
	
	
	public void delete(Long id);
	
	
}
