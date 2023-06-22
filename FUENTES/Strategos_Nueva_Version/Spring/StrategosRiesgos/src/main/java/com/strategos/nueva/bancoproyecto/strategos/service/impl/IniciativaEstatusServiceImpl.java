package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.IniciativaEstatusStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.model.IniciativaEstatusStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.IniciativaEstatusService;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;

@Service
public class IniciativaEstatusServiceImpl implements IniciativaEstatusService{

	@Autowired
	private IniciativaEstatusStrategosDao iniciativaEstatusDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<IniciativaEstatusStrategos> findAll() {
		return (List<IniciativaEstatusStrategos>) iniciativaEstatusDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public IniciativaEstatusStrategos findById(Long id) {
		
		return iniciativaEstatusDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public IniciativaEstatusStrategos save(IniciativaEstatusStrategos iniciativa) {
		
		return iniciativaEstatusDao.save(iniciativa);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		iniciativaEstatusDao.deleteById(id);
	}
			
}
