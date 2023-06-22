package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.IndicadorStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorService;
import com.strategos.nueva.bancoproyecto.strategos.service.MetodologiaService;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;

@Service
public class IndicadoresServiceImpl implements IndicadorService{

	@Autowired
	private IndicadorStrategosDao indicadorDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<IndicadorStrategos> findAll() {
		return (List<IndicadorStrategos>) indicadorDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public IndicadorStrategos findById(Long id) {
		
		return indicadorDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public IndicadorStrategos save(IndicadorStrategos indicador) {
		
		return indicadorDao.save(indicador);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		indicadorDao.deleteById(id);
	}
			
}
