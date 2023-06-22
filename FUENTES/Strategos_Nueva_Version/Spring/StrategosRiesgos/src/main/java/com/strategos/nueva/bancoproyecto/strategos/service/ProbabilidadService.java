package com.strategos.nueva.bancoproyecto.strategos.service;

import org.springframework.data.domain.Pageable;

import com.strategos.nueva.bancoproyecto.strategos.model.Probabilidad;

import java.util.List;

import org.springframework.data.domain.Page;


public interface ProbabilidadService {
	
	public List<Probabilidad> findAll();

	public Probabilidad findById(Long id);

	
	public Probabilidad save(Probabilidad probabilidad);
	
	
	public void delete(Long id);


	public List<Probabilidad> findAllByPuntaje();
}
