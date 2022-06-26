package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.EvaluacionIdeasDetalleDao;
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasEvaluadas;
import com.strategos.nueva.bancoproyecto.ideas.service.EvaluacionIdeasDetalleService;

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

	@Override
	public List<EvaluacionIdeasDetalle> findAllByEvaluacionId(Long evaluacionId) {
		return (List<EvaluacionIdeasDetalle>)evaluacionIdeasDetalleDao.findAllByEvaluacionId(evaluacionId);
	}

	@Override
	public List<EvaluacionIdeasDetalle> findAllByEvaluacionIdAndIdeaId(Long evaluacionId, Long ideaId) {
		return (List<EvaluacionIdeasDetalle>)evaluacionIdeasDetalleDao.findAllByEvaluacionIdAndIdeaId(evaluacionId, ideaId);
	}
			
}
