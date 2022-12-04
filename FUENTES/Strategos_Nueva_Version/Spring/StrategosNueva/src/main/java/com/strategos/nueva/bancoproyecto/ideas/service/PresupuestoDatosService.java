package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.IniciativaPerspectiva;
import com.strategos.nueva.bancoproyecto.ideas.model.PresupuestoDatos;

public interface PresupuestoDatosService {

	public List<PresupuestoDatos> findAll();	

	
	public PresupuestoDatos findById(Long id);

	
	public PresupuestoDatos save(PresupuestoDatos presupuesto);
	
	
	public void delete(Long id);
	
	
	public List<PresupuestoDatos> findAllByTareaId(Long tareaId);
	
	
	public List<PresupuestoDatos> findByPeriodos(Long tareaId, Long serieId, Integer anio, Integer periodoIni, Integer periodoFin);
	
}
