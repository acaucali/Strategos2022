package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosDocumentosAnexos;

public interface ProyectosDocumentosAnexosService {
	
	public List<ProyectosDocumentosAnexos> findAll();
	
	public ProyectosDocumentosAnexos findById(Long id);
	
	public ProyectosDocumentosAnexos save(ProyectosDocumentosAnexos anexo);
	
	public void delete(Long id);

}
