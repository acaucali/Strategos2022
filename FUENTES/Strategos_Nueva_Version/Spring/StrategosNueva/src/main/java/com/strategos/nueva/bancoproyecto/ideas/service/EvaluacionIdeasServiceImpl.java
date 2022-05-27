package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.EvaluacionIdeasDao;
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeas;

@Service
public class EvaluacionIdeasServiceImpl implements EvaluacionIdeasService{

	@Autowired
	private EvaluacionIdeasDao evaluacionIdeasDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<EvaluacionIdeas> findAll() {
		return (List<EvaluacionIdeas>) evaluacionIdeasDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public EvaluacionIdeas findById(Long id) {
		
		return evaluacionIdeasDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public EvaluacionIdeas save(EvaluacionIdeas evaluacion) {
		
		return evaluacionIdeasDao.save(evaluacion);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		evaluacionIdeasDao.deleteById(id);
	}
			
}
