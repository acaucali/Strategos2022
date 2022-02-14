package com.strategos.riesgos.models.ejercicios.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.riesgos.model.ejercicios.entity.ControlesRiesgo;
import com.strategos.riesgos.models.ejercicios.dao.IControlesRiesgoDao;

@Service
public class ControlesRiesgoServiceImpl implements IControlesService{
	
	@Autowired
	private IControlesRiesgoDao controlRiesgoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<ControlesRiesgo> findAll() {
		return (List<ControlesRiesgo>) controlRiesgoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ControlesRiesgo> findAll(Pageable pageable) {
		
		return controlRiesgoDao.findAll(pageable);
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
