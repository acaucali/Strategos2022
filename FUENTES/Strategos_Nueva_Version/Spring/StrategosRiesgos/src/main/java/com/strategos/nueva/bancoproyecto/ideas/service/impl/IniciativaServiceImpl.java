package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.IniciativaDao;
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;
import com.strategos.nueva.bancoproyecto.ideas.model.Iniciativa;

import com.strategos.nueva.bancoproyecto.ideas.service.IniciativaService;

@Service
public class IniciativaServiceImpl implements IniciativaService{

	@Autowired
	private IniciativaDao IniciativaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Iniciativa> findAll() {
		return (List<Iniciativa>) IniciativaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Iniciativa findById(Long id) {
		
		return IniciativaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Iniciativa save(Iniciativa iniciativa) {
		
		return IniciativaDao.save(iniciativa);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		IniciativaDao.deleteById(id);
	}
	
			
}
