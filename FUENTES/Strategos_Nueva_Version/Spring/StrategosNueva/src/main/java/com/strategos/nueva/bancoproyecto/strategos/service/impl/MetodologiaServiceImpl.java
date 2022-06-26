package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.MetodologiaStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.model.MetodologiaStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.MetodologiaService;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;

@Service
public class MetodologiaServiceImpl implements MetodologiaService{

	@Autowired
	private MetodologiaStrategosDao metodologiaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<MetodologiaStrategos> findAll() {
		return (List<MetodologiaStrategos>) metodologiaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public MetodologiaStrategos findById(Long id) {
		
		return metodologiaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public MetodologiaStrategos save(MetodologiaStrategos metodologia) {
		
		return metodologiaDao.save(metodologia);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		metodologiaDao.deleteById(id);
	}
			
}
