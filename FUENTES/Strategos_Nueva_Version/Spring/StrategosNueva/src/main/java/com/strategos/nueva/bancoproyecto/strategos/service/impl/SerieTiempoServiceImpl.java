package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.SerieTiempoStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.model.SerieTiempoStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.SerieTiempoService;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;

@Service
public class SerieTiempoServiceImpl implements SerieTiempoService{

	@Autowired
	private SerieTiempoStrategosDao serieTiempoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<SerieTiempoStrategos> findAll() {
		return (List<SerieTiempoStrategos>) serieTiempoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public SerieTiempoStrategos findById(Long id) {
		
		return serieTiempoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public SerieTiempoStrategos save(SerieTiempoStrategos serie) {
		
		return serieTiempoDao.save(serie);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		serieTiempoDao.deleteById(id);
	}
			
}
