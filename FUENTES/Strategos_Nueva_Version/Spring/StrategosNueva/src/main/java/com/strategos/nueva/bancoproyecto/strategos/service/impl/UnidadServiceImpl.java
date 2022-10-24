package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.UnidadStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.model.UnidadStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.UnidadService;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;

@Service
public class UnidadServiceImpl implements UnidadService{

	@Autowired
	private UnidadStrategosDao unidadDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<UnidadStrategos> findAll() {
		return (List<UnidadStrategos>) unidadDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public UnidadStrategos findById(Long id) {
		
		return unidadDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public UnidadStrategos save(UnidadStrategos unidad) {
		
		return unidadDao.save(unidad);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		unidadDao.deleteById(id);
	}
			
}
