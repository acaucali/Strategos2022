package com.strategos.nueva.riesgos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.nueva.riesgos.model.Respuesta;




public interface RespuestaService {

	public List<Respuesta> findAll();

	
	
	public Respuesta findById(Long id);

	
	public Respuesta save(Respuesta respuesta);
	
	
	public void delete(Long id);
}
