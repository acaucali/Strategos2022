package com.strategos.nueva.riesgos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.riesgos.dao.EfectividadDao;
import com.strategos.nueva.riesgos.model.EfectividadControles;
import com.strategos.nueva.riesgos.service.EfectividadService;


@Service
public class EfectividadServiceImpl implements EfectividadService{
	
	@Autowired
	private EfectividadDao efectividadDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<EfectividadControles> findAll() {
		return (List<EfectividadControles>) efectividadDao.findAll();
	}

	
	@Override
	@Transactional(readOnly = true)
	public EfectividadControles findById(Long id) {
		
		return efectividadDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public EfectividadControles save(EfectividadControles efectividad) {
		
		return efectividadDao.save(efectividad);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		efectividadDao.deleteById(id);
	}

}
