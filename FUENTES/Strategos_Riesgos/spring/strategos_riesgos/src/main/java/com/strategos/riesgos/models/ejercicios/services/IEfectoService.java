package com.strategos.riesgos.models.ejercicios.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.riesgos.model.ejercicios.entity.EfectoRiesgo;

public interface IEfectoService {
	public List<EfectoRiesgo> findAll();
	
	
	public Page<EfectoRiesgo> findAll(Pageable pageable);
	
	
	public EfectoRiesgo findById(Long id);

	
	public EfectoRiesgo save(EfectoRiesgo efecto);
	
	
	public void delete(Long id);
}
