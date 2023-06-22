package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.nueva.bancoproyecto.strategos.model.MacroProceso;



public interface MacroProcesoService {

	public List<MacroProceso> findAll();

	
	public MacroProceso findById(Long id);

	
	public MacroProceso save(MacroProceso macroProceso);
	
	
	public void delete(Long id);
}
