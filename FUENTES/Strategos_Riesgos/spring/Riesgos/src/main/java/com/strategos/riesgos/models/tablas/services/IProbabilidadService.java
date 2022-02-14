package com.strategos.riesgos.models.tablas.services;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;


import com.strategos.riesgos.models.tablas.entity.Probabilidad;

public interface IProbabilidadService {
	public List<Probabilidad> findAll();
	
	
	public Page<Probabilidad> findAll(Pageable pageable);
	
	
	public Probabilidad findById(Long id);

	
	public Probabilidad save(Probabilidad probabilidad);
	
	
	public void delete(Long id);


	public List<Probabilidad> findAllByPuntaje();
}
