package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.IniciativaPerspectivaDao;
import com.strategos.nueva.bancoproyecto.ideas.model.EvaluacionIdeasDetalle;
import com.strategos.nueva.bancoproyecto.ideas.model.IniciativaPerspectiva;
import com.strategos.nueva.bancoproyecto.ideas.service.IniciativaPerspectivaService;

@Service
public class IniciativaPerspectivaServiceImpl implements IniciativaPerspectivaService{

	@Autowired
	private IniciativaPerspectivaDao IniciativaPerspectivaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<IniciativaPerspectiva> findAll() {
		return (List<IniciativaPerspectiva>) IniciativaPerspectivaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public IniciativaPerspectiva findById(Long id) {
		
		return IniciativaPerspectivaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public IniciativaPerspectiva save(IniciativaPerspectiva indicadorIniciativa) {
		
		return IniciativaPerspectivaDao.save(indicadorIniciativa);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		IniciativaPerspectivaDao.deleteById(id);
	}

	@Override
	public List<IniciativaPerspectiva> findByPerspectiva(Long perspectivaId) {
		// TODO Auto-generated method stub
		return IniciativaPerspectivaDao.findByPerspectiva(perspectivaId);
	}

	@Override
	public IniciativaPerspectiva findByPerspectivaIniciativa(Long perspectivaId, Long iniciativaId) {
		// TODO Auto-generated method stub
		return IniciativaPerspectivaDao.findByPerspectivaIniciativa(perspectivaId, iniciativaId);
	}
	
			
}
