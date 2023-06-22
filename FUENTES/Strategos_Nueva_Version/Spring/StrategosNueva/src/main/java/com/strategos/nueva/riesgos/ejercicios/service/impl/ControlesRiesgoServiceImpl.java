package com.strategos.nueva.riesgos.ejercicios.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.riesgos.ejercicios.dao.ControlesRiesgoDao;
import com.strategos.nueva.riesgos.ejercicios.model.ControlesRiesgo;
import com.strategos.nueva.riesgos.ejercicios.service.ControlesService;



@Service
public class ControlesRiesgoServiceImpl implements ControlesService{
	
	@Autowired
	private ControlesRiesgoDao controlRiesgoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<ControlesRiesgo> findAll() {
		return (List<ControlesRiesgo>) controlRiesgoDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public ControlesRiesgo findById(Long id) {
		
		return controlRiesgoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public ControlesRiesgo save(ControlesRiesgo controlRiesgo) {
		
		return controlRiesgoDao.save(controlRiesgo);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		controlRiesgoDao.deleteById(id);
	}

}
