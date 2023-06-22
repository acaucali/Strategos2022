package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.IniciativaPlanDao;
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;
import com.strategos.nueva.bancoproyecto.ideas.model.IniciativaPlan;

import com.strategos.nueva.bancoproyecto.ideas.service.IniciativaPlanService;

@Service
public class IniciativaPlanServiceImpl implements IniciativaPlanService{

	@Autowired
	private IniciativaPlanDao IniciativaPlanDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<IniciativaPlan> findAll() {
		return (List<IniciativaPlan>) IniciativaPlanDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public IniciativaPlan findById(Long id) {
		
		return IniciativaPlanDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public IniciativaPlan save(IniciativaPlan iniciativaPlan) {
		
		return IniciativaPlanDao.save(iniciativaPlan);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		IniciativaPlanDao.deleteById(id);
	}
	
			
}
