package com.strategos.nueva.riesgos.service;

import org.springframework.data.domain.Pageable;

import com.strategos.nueva.riesgos.model.Controles;

import java.util.List;

import org.springframework.data.domain.Page;




public interface ControlesService {
	
	public List<Controles> findAll();
	
	
	public Controles findById(Long id);

	
	public Controles save(Controles controles);
	
	
	public void delete(Long id);


	
}
