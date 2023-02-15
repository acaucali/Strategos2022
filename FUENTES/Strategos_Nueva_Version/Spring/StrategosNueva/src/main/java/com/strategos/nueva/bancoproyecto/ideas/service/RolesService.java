package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.Roles;

public interface RolesService {

	public List<Roles> findAll();	

	
	public Roles findById(Long id);

	
	public Roles save(Roles rol);
	
	
	public void delete(Long id);
	
}
