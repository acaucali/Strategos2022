package com.strategos.nueva.riesgos.ejercicios.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.nueva.riesgos.ejercicios.model.EjercicioRiesgo;



public interface EjercicioService {
	public List<EjercicioRiesgo> findAll();
	

	
	public EjercicioRiesgo findById(Long id);

	
	public EjercicioRiesgo save(EjercicioRiesgo ejercicio);
	
	
	public void delete(Long id);


	public Page<EjercicioRiesgo> findAllByProcesoPadreId(Long procesoPadreId, Pageable pageable);
}
