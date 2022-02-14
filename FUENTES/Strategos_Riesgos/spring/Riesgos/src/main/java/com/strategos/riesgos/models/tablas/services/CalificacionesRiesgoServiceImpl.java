package com.strategos.riesgos.models.tablas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.riesgos.models.tablas.dao.ICalificacionesRiesgoDao;
import com.strategos.riesgos.models.tablas.entity.CalificacionesRiesgo;


@Service
public class CalificacionesRiesgoServiceImpl implements ICalificacionesRiesgoService{
	
	@Autowired
	private ICalificacionesRiesgoDao calificacionesRiesgoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<CalificacionesRiesgo> findAll() {
		return (List<CalificacionesRiesgo>) calificacionesRiesgoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<CalificacionesRiesgo> findAll(Pageable pageable) {
		
		return calificacionesRiesgoDao.findAll(pageable);
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
