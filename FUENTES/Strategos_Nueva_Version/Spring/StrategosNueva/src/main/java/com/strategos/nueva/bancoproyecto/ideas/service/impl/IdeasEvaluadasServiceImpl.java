package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.IdeasEvaluadasDao;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasEvaluadas;
import com.strategos.nueva.bancoproyecto.ideas.service.IdeasEvaluadasService;

@Service
public class IdeasEvaluadasServiceImpl implements IdeasEvaluadasService{

	@Autowired
	private IdeasEvaluadasDao ideasEvaluadasDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<IdeasEvaluadas> findAll() {
		return (List<IdeasEvaluadas>) ideasEvaluadasDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public IdeasEvaluadas findById(Long id) {
		
		return ideasEvaluadasDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public IdeasEvaluadas save(IdeasEvaluadas ideas) {
		
		return ideasEvaluadasDao.save(ideas);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		ideasEvaluadasDao.deleteById(id);
	}

	@Override
	public List<IdeasEvaluadas> findAllByEvaluacionId(Long Id) {
		return (List<IdeasEvaluadas>)ideasEvaluadasDao.findAllByEvaluacionId(Id);
	}
			
}
