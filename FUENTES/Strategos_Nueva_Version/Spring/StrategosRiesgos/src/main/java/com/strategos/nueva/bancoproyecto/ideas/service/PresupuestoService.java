package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import com.strategos.nueva.bancoproyecto.ideas.model.IniciativaPerspectiva;
import com.strategos.nueva.bancoproyecto.ideas.model.Presupuesto;

public interface PresupuestoService {

	public List<Presupuesto> findAll();	

	
	public Presupuesto findById(Long id);

	
	public Presupuesto save(Presupuesto presupuesto);
	
	
	public void delete(Long id);
	
	
	
}
