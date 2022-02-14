package com.strategos.riesgos.models.tablas.services;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;


import com.strategos.riesgos.models.tablas.entity.Controles;

public interface IControlesService {
	public List<Controles> findAll();
	
	
	public Page<Controles> findAll(Pageable pageable);
	
	
	public Controles findById(Long id);

	
	public Controles save(Controles controles);
	
	
	public void delete(Long id);


	
}
