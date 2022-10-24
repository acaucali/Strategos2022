package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;


import com.strategos.nueva.bancoproyecto.strategos.model.UsuarioStrategos;

public interface UsuarioService {

	public List<UsuarioStrategos> findAll();	

	
	public UsuarioStrategos findById(Long id);

	
	public UsuarioStrategos save(UsuarioStrategos usuario);
	
	
	public void delete(Long id);
	
	public UsuarioStrategos findByUser(String user);
}
