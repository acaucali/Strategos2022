package com.strategos.riesgos.models.procesos.services;

import java.util.List;


import com.strategos.riesgos.models.procesos.entity.Procesos;

public interface IProcesoService {
	
	public List<Procesos> findAll();
	
	
	public Procesos findById(Long id);

	
	public Procesos save(Procesos procesos);
	
	
	public void delete(Long id);
}
