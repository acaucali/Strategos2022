package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.ProyectosPoblacionDao;
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;
import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosPoblacion;
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosPoblacionService;

@Service
public class ProyectosPoblacionServiceImpl implements ProyectosPoblacionService{

	@Autowired
	private ProyectosPoblacionDao proyectosPoblacionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<ProyectosPoblacion> findAll() {
		return (List<ProyectosPoblacion>) proyectosPoblacionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public ProyectosPoblacion findById(Long id) {
		
		return proyectosPoblacionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public ProyectosPoblacion save(ProyectosPoblacion proyectos) {
		
		return proyectosPoblacionDao.save(proyectos);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		proyectosPoblacionDao.deleteById(id);
	}
	
	@Override
	public List<ProyectosPoblacion> findAllByProyectoId(Long proyectoId) {
		return (List<ProyectosPoblacion>)proyectosPoblacionDao.findAllByProyectoId(proyectoId);
	}
			
}
