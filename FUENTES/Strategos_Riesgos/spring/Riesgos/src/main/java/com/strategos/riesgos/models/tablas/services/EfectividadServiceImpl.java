package com.strategos.riesgos.models.tablas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.riesgos.models.tablas.dao.IEfectividadDao;
import com.strategos.riesgos.models.tablas.entity.EfectividadControles;

@Service
public class EfectividadServiceImpl implements IEfectividadService{
	
	@Autowired
	private IEfectividadDao efectividadDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<EfectividadControles> findAll() {
		return (List<EfectividadControles>) efectividadDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<EfectividadControles> findAll(Pageable pageable) {
		
		return efectividadDao.findAll(pageable);
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
