package com.strategos.riesgos.models.tablas.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.riesgos.models.tablas.entity.MacroProceso;

public interface IMacroProcesoService {

	public List<MacroProceso> findAll();
	
	
	public Page<MacroProceso> findAll(Pageable pageable);
	
	
	public MacroProceso findById(Long id);

	
	public MacroProceso save(MacroProceso macroProceso);
	
	
	public void delete(Long id);
}
