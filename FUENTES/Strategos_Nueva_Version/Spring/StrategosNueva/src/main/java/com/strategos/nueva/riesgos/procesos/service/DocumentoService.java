package com.strategos.nueva.riesgos.procesos.service;

import java.util.List;

import com.strategos.nueva.riesgos.procesos.model.Documento;




public interface DocumentoService {
	
	public List<Documento> findAll();
	
	
	public Documento findById(Long id);

	
	public Documento save(Documento documento);
	
	
	public void delete(Long id);
}
