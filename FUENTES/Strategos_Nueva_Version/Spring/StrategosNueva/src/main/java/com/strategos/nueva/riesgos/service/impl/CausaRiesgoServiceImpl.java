package com.strategos.nueva.riesgos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.riesgos.dao.CausaRiesgoDao;
import com.strategos.nueva.riesgos.model.CausaRiesgo;
import com.strategos.nueva.riesgos.service.CausaRiesgoService;


@Service
public class CausaRiesgoServiceImpl implements  CausaRiesgoService{
	@Autowired
	private CausaRiesgoDao causasRiesgoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<CausaRiesgo> findAll() {
		return (List<CausaRiesgo>) causasRiesgoDao.findAll();
	}

	
	@Override
	@Transactional(readOnly = true)
	public CausaRiesgo findById(Long id) {
		
		return causasRiesgoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public CausaRiesgo save(CausaRiesgo causaRiesgo) {
		
		return causasRiesgoDao.save(causaRiesgo);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		causasRiesgoDao.deleteById(id);
	}
}
