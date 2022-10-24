package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.PerspectivaStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.model.PerspectivaStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.PerspectivaService;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;

@Service
public class PerspectivaServiceImpl implements PerspectivaService{

	@Autowired
	private PerspectivaStrategosDao perspectiveDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<PerspectivaStrategos> findAll() {
		return (List<PerspectivaStrategos>) perspectiveDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public PerspectivaStrategos findById(Long id) {
		
		return perspectiveDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public PerspectivaStrategos save(PerspectivaStrategos perspectiva) {
		
		return perspectiveDao.save(perspectiva);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		perspectiveDao.deleteById(id);
	}

	@Override
	public List<PerspectivaStrategos> findAllByPlanId(Long planId) {
		// TODO Auto-generated method stub
		return perspectiveDao.findAllByPlanId(planId);
	}
			
}
