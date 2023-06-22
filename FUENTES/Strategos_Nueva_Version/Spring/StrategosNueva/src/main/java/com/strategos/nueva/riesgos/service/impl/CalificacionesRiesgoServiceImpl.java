package com.strategos.nueva.riesgos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.riesgos.dao.CalificacionesRiesgoDao;
import com.strategos.nueva.riesgos.model.CalificacionesRiesgo;
import com.strategos.nueva.riesgos.service.CalificacionesRiesgoService;



@Service
public class CalificacionesRiesgoServiceImpl implements CalificacionesRiesgoService{

	@Autowired
	private CalificacionesRiesgoDao calificacionesRiesgoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<CalificacionesRiesgo> findAll() {
		return (List<CalificacionesRiesgo>) calificacionesRiesgoDao.findAll();
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public CalificacionesRiesgo findById(Long id) {
		
		return calificacionesRiesgoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public CalificacionesRiesgo save(CalificacionesRiesgo calificacionesRiesgo) {
		
		return calificacionesRiesgoDao.save(calificacionesRiesgo);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		calificacionesRiesgoDao.deleteById(id);
	}
			
}
