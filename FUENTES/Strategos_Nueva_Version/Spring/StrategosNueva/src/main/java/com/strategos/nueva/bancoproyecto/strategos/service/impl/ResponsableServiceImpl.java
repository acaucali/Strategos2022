package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.strategos.nueva.bancoproyecto.strategos.dao.ResponsableStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.model.Responsable;
import com.strategos.nueva.bancoproyecto.strategos.service.ResponsableService;

@Service
public class ResponsableServiceImpl implements ResponsableService{

	@Autowired
	private ResponsableStrategosDao responsableDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Responsable> findAll() {
		return (List<Responsable>) responsableDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Responsable findById(Long id) {
		
		return responsableDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Responsable save(Responsable responsable) {
		
		return responsableDao.save(responsable);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		responsableDao.deleteById(id);
	}
			
}
