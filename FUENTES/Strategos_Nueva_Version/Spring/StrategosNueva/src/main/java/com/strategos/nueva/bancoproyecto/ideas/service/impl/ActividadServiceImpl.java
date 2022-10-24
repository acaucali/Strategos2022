package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.ActividadDao;
import com.strategos.nueva.bancoproyecto.ideas.model.Actividad;
import com.strategos.nueva.bancoproyecto.ideas.service.ActividadService;

@Service
public class ActividadServiceImpl implements ActividadService{

	@Autowired
	private ActividadDao actividadDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Actividad> findAll() {
		return (List<Actividad>) actividadDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Actividad findById(Long id) {
		
		return actividadDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Actividad save(Actividad actividad) {
		
		return actividadDao.save(actividad);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		actividadDao.deleteById(id);
	}
			
}
