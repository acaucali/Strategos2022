package com.strategos.riesgos.models.tablas.services;

import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;


import com.strategos.riesgos.models.tablas.entity.TipoImpacto;

public interface ITipoImpactoService {
	
	public List<TipoImpacto> findAll();
	
	
	public Page<TipoImpacto> findAll(Pageable pageable);
	
	
	public TipoImpacto findById(Long id);

	
	public TipoImpacto save(TipoImpacto tipoImpacto);
	
	
	public void delete(Long id);


	public List<TipoImpacto> findAllByPuntaje();
}
