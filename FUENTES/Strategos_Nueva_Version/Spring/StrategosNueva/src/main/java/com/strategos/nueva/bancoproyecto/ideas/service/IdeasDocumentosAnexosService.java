package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.IdeasDocumentosAnexos;

public interface IdeasDocumentosAnexosService {

	public List<IdeasDocumentosAnexos> findAll();	

	
	public IdeasDocumentosAnexos findById(Long id);

	
	public IdeasDocumentosAnexos save(IdeasDocumentosAnexos anexo);
	
	
	public void delete(Long id);
	
}
