package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.strategos.model.OrganizacionesStrategos;

public interface OrganizacionService {

	public List<OrganizacionesStrategos> findAll();	

	
	public OrganizacionesStrategos findById(Long id);

	
	public OrganizacionesStrategos save(OrganizacionesStrategos organizacion);
	
	
	public void delete(Long id);
	
}
