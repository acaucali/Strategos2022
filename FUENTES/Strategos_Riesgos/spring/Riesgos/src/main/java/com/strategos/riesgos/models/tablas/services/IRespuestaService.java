package com.strategos.riesgos.models.tablas.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.riesgos.models.tablas.entity.Respuesta;

public interface IRespuestaService {
public List<Respuesta> findAll();
	
	
	public Page<Respuesta> findAll(Pageable pageable);
	
	
	public Respuesta findById(Long id);

	
	public Respuesta save(Respuesta respuesta);
	
	
	public void delete(Long id);
}
