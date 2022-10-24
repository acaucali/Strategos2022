package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.PlanStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.model.PlanStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.PlanService;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;

@Service
public class PlanServiceImpl implements PlanService{

	@Autowired
	private PlanStrategosDao planDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<PlanStrategos> findAll() {
		return (List<PlanStrategos>) planDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public PlanStrategos findById(Long id) {
		
		return planDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public PlanStrategos save(PlanStrategos plan) {
		
		return planDao.save(plan);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		planDao.deleteById(id);
	}
			
}
