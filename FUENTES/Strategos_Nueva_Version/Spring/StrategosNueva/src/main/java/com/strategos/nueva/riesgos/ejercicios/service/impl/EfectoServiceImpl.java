package com.strategos.nueva.riesgos.ejercicios.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.riesgos.ejercicios.dao.EfectoRiesgoDao;
import com.strategos.nueva.riesgos.ejercicios.model.EfectoRiesgo;
import com.strategos.nueva.riesgos.ejercicios.service.EfectoService;

@Service
public class EfectoServiceImpl implements EfectoService{
	@Autowired
	private EfectoRiesgoDao efectoRiesgoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<EfectoRiesgo> findAll() {
		return (List<EfectoRiesgo>) efectoRiesgoDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public EfectoRiesgo findById(Long id) {
		
		return efectoRiesgoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public EfectoRiesgo save(EfectoRiesgo efectoRiesgo) {
		
		return efectoRiesgoDao.save(efectoRiesgo);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		efectoRiesgoDao.deleteById(id);
	}

}
