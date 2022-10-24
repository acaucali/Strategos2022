package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.IndicadorIniciativaDao;
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;
import com.strategos.nueva.bancoproyecto.ideas.model.IndicadorIniciativa;
import com.strategos.nueva.bancoproyecto.ideas.service.IndicadorIniciativaService;

@Service
public class IndicadorIniciativaServiceImpl implements IndicadorIniciativaService{

	@Autowired
	private IndicadorIniciativaDao indicadorIniciativaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<IndicadorIniciativa> findAll() {
		return (List<IndicadorIniciativa>) indicadorIniciativaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public IndicadorIniciativa findById(Long id) {
		
		return indicadorIniciativaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public IndicadorIniciativa save(IndicadorIniciativa indicadorIniciativa) {
		
		return indicadorIniciativaDao.save(indicadorIniciativa);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		indicadorIniciativaDao.deleteById(id);
	}
	
			
}
