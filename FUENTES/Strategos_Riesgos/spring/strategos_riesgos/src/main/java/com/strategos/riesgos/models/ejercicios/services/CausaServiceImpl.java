package com.strategos.riesgos.models.ejercicios.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.riesgos.model.ejercicios.entity.RiCausaRiesgo;
import com.strategos.riesgos.models.ejercicios.dao.ICausaDao;

@Service
public class CausaServiceImpl implements ICausaService{
	@Autowired
	private ICausaDao causaRiesgoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<RiCausaRiesgo> findAll() {
		return (List<RiCausaRiesgo>) causaRiesgoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<RiCausaRiesgo> findAll(Pageable pageable) {
		
		return causaRiesgoDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<String> findByCausa2() {
		
		return causaRiesgoDao.findByCausa2();
	}	
	
	@Override
	@Transactional(readOnly = true)
	public RiCausaRiesgo findById(Long id) {
		
		return causaRiesgoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public RiCausaRiesgo save(RiCausaRiesgo causaRiesgo) {
		
		return causaRiesgoDao.save(causaRiesgo);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		causaRiesgoDao.deleteById(id);
	}

	
}
