package com.strategos.nueva.riesgos.procesos.service;

import java.util.List;

import com.strategos.nueva.riesgos.procesos.model.Procesos;




public interface ProcesoService {
	
	public List<Procesos> findAll();
	
	
	public Procesos findById(Long id);

	
	public Procesos save(Procesos procesos);
	
	
	public void delete(Long id);
}
