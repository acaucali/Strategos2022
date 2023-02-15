package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.Usuario;



public interface UsuariosService {

	public Usuario findByUsername(String username);
	
	public List<Usuario> findAll();
	
	
	public Usuario findById(Long id);

	
	public Usuario save(Usuario usuario);
	
	
	public void delete(Long id);
	
}
