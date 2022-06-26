package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.EstatusIdeaDao;
import com.strategos.nueva.bancoproyecto.ideas.model.EstatusIdeas;
import com.strategos.nueva.bancoproyecto.ideas.service.EstatusIdeaService;

@Service
public class EstatusIdeaServiceImpl implements EstatusIdeaService{

	@Autowired
	private EstatusIdeaDao estatusIdeaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<EstatusIdeas> findAll() {
		return (List<EstatusIdeas>) estatusIdeaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public EstatusIdeas findById(Long id) {
		
		return estatusIdeaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public EstatusIdeas save(EstatusIdeas estatus) {
		
		return estatusIdeaDao.save(estatus);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		estatusIdeaDao.deleteById(id);
	}
			
}
