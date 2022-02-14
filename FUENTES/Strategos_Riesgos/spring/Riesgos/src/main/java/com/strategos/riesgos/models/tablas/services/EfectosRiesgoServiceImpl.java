package com.strategos.riesgos.models.tablas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.riesgos.models.tablas.dao.IEfectosDao;
import com.strategos.riesgos.models.tablas.entity.EfectosRiesgo;

@Service
public class EfectosRiesgoServiceImpl implements IEfectosService{
	@Autowired
	private IEfectosDao efectosDao;
	
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
