package com.strategos.riesgos.models.tablas.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.riesgos.models.tablas.entity.EfectosRiesgo;

public interface IEfectosService {
	
	public List<EfectosRiesgo> findAll();
	
	
	public EfectosRiesgo findById(Long id);

	
	public EfectosRiesgo save(EfectosRiesgo efectos);
	
	
	public void delete(Long id);
}
