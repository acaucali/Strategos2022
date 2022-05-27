package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.EvaluacionIdeasDetalleDao;
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;

@Service
public class EvaluacionIdeasDetalleServiceImpl implements EvaluacionIdeasDetalleService{

	@Autowired
	private EvaluacionIdeasDetalleDao evaluacionIdeasDetalleDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<EvaluacionIdeasDetalle> findAll() {
		return (List<EvaluacionIdeasDetalle>) evaluacionIdeasDetalleDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public EvaluacionIdeasDetalle findById(Long id) {
		
		return evaluacionIdeasDetalleDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public EvaluacionIdeasDetalle save(EvaluacionIdeasDetalle evaluacion) {
		
		return evaluacionIdeasDetalleDao.save(evaluacion);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		evaluacionIdeasDetalleDao.deleteById(id);
	}
			
}
