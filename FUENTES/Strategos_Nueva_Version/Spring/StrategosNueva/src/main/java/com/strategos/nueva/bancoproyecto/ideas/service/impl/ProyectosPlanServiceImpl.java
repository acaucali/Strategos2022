package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.ProyectosDao;
import com.strategos.nueva.bancoproyecto.ideas.dao.ProyectosPlanDao;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.Proyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosPlan;
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosPlanService;
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosService;
import com.strategos.nueva.bancoproyectos.model.util.FIltroIdea;

@Service
public class ProyectosPlanServiceImpl implements ProyectosPlanService{

	@Autowired
	private ProyectosPlanDao proyectosDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<ProyectosPlan> findAll() {
		return (List<ProyectosPlan>) proyectosDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public ProyectosPlan findById(Long id) {
		
		return proyectosDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public ProyectosPlan save(ProyectosPlan proyectos) {
		
		return proyectosDao.save(proyectos);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		proyectosDao.deleteById(id);
	}

	@Override
	public ProyectosPlan findAllByProyectoId(Long proyectoId) {		
		return proyectosDao.findAllByProyectoId(proyectoId);
	}
	
	
			
}
