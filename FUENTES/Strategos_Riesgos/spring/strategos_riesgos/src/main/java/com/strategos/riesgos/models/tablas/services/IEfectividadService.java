package com.strategos.riesgos.models.tablas.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.riesgos.models.tablas.entity.EfectividadControles;

public interface IEfectividadService {
	
	public List<EfectividadControles> findAll();
	
	
	public Page<EfectividadControles> findAll(Pageable pageable);
	
	
	public EfectividadControles findById(Long id);

	
	public EfectividadControles save(EfectividadControles efectividades);
	
	
	public void delete(Long id);

}
