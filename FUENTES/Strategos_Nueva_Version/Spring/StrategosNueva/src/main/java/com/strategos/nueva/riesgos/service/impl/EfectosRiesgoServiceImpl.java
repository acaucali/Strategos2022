package com.strategos.nueva.riesgos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.riesgos.dao.EfectosDao;
import com.strategos.nueva.riesgos.model.EfectosRiesgo;
import com.strategos.nueva.riesgos.service.EfectosService;



@Service
public class EfectosRiesgoServiceImpl implements EfectosService{
	@Autowired
	private EfectosDao efectosDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<EfectosRiesgo> findAll() {
		return (List<EfectosRiesgo>) efectosDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public EfectosRiesgo findById(Long id) {
		
		return efectosDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public EfectosRiesgo save(EfectosRiesgo efectos) {
		
		return efectosDao.save(efectos);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		efectosDao.deleteById(id);
	}

}
