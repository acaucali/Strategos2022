package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.CalificacionesRiesgoDao;
import com.strategos.nueva.bancoproyecto.strategos.dao.ClaseIndicadoresStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.model.CalificacionesRiesgo;
import com.strategos.nueva.bancoproyecto.strategos.model.ClaseIndicadoresStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.CalificacionesRiesgoService;
import com.strategos.nueva.bancoproyecto.strategos.service.ClaseIndicadorService;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;

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
