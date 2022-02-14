package com.strategos.riesgos.models.ejercicios.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.riesgos.model.ejercicios.entity.EjercicioRiesgo;
import com.strategos.riesgos.model.ejercicios.entity.FactoresRiesgo;

public interface IFactoresService {
	public List<FactoresRiesgo> findAll();
	
	
	public Page<FactoresRiesgo> findAll(Pageable pageable);
	
	
	public FactoresRiesgo findById(Long id);

	
	public FactoresRiesgo save(FactoresRiesgo factor);
	
	
	public void delete(Long id);
	
	
}
