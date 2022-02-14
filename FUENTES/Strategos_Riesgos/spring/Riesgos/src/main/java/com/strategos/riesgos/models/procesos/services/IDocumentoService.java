package com.strategos.riesgos.models.procesos.services;

import java.util.List;

import com.strategos.riesgos.models.procesos.entity.Documento;


public interface IDocumentoService {
	
	public List<Documento> findAll();
	
	
	public Documento findById(Long id);

	
	public Documento save(Documento documento);
	
	
	public void delete(Long id);
}
