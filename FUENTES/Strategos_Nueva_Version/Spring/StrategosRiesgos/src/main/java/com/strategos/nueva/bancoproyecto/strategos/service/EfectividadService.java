package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.nueva.bancoproyecto.strategos.model.EfectividadControles;



public interface EfectividadService {
	
	public List<EfectividadControles> findAll();

	
	public EfectividadControles findById(Long id);

	
	public EfectividadControles save(EfectividadControles efectividades);
	
	
	public void delete(Long id);

}
