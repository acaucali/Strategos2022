package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.strategos.model.ClaseIndicadoresStrategos;

public interface ClaseIndicadorService {

	public List<ClaseIndicadoresStrategos> findAll();	

	
	public ClaseIndicadoresStrategos findById(Long id);

	
	public ClaseIndicadoresStrategos save(ClaseIndicadoresStrategos clase);
	
	
	public void delete(Long id);
	
	public List<ClaseIndicadoresStrategos> findByOrganizacionIdAndTipo(Long organizacionId, Byte tipo);
	
	public ClaseIndicadoresStrategos findByClaseRaiz(Long organizacionId, Byte tipo);
	
}
