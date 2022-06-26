package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.strategos.model.TipoProyectoStrategos;

public interface TipoProyectoService {

	public List<TipoProyectoStrategos> findAll();	

	
	public TipoProyectoStrategos findById(Long id);

	
	public TipoProyectoStrategos save(TipoProyectoStrategos tipo);
	
	
	public void delete(Long id);
	
}
