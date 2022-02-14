package com.strategos.riesgos.models.ejercicios.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.riesgos.model.ejercicios.entity.EjercicioRiesgo;

public interface IEjercicioService {
	public List<EjercicioRiesgo> findAll();
	
	
	public Page<EjercicioRiesgo> findAll(Pageable pageable);
	
	
	public EjercicioRiesgo findById(Long id);

	
	public EjercicioRiesgo save(EjercicioRiesgo ejercicio);
	
	
	public void delete(Long id);


	public Page<EjercicioRiesgo> findAllByProcesoPadreId(Long procesoPadreId, Pageable pageable);
}
