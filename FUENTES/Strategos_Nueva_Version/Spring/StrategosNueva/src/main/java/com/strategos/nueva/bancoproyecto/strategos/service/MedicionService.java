package com.strategos.nueva.bancoproyecto.strategos.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.strategos.model.MedicionStrategos;

public interface MedicionService {

	public List<MedicionStrategos> findAll();	

	
	public MedicionStrategos findById(Long id);

	
	public MedicionStrategos save(MedicionStrategos medicion);
	
	
	public void delete(Long id);
	
	public List<MedicionStrategos> findByPeriodos(Long indicadorId, Long serieId, Integer anio, Integer periodoIni, Integer periodoFin);
	
}
