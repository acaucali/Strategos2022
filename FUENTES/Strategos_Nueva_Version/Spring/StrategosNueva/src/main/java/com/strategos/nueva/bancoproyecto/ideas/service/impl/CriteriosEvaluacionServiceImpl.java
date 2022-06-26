package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.CriteriosEvaluacionDao;
import com.strategos.nueva.bancoproyecto.ideas.model.CriteriosEvaluacion;
import com.strategos.nueva.bancoproyecto.ideas.service.CriteriosEvaluacionService;

@Service
public class CriteriosEvaluacionServiceImpl implements CriteriosEvaluacionService{

	@Autowired
	private CriteriosEvaluacionDao criteriosEvaluacionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<CriteriosEvaluacion> findAll() {
		return (List<CriteriosEvaluacion>) criteriosEvaluacionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public CriteriosEvaluacion findById(Long id) {
		
		return criteriosEvaluacionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public CriteriosEvaluacion save(CriteriosEvaluacion criterios) {
		
		return criteriosEvaluacionDao.save(criterios);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		criteriosEvaluacionDao.deleteById(id);
	}
			
}
