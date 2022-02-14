package com.strategos.riesgos.models.tablas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.riesgos.models.tablas.dao.ITipoRiesgoDao;
import com.strategos.riesgos.models.tablas.entity.TipoRiesgo;

@Service
public class TipoRiesgoServiceImpl implements ITipoRiesgoService{
	@Autowired
	private ITipoRiesgoDao tipoRiesgoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoRiesgo> findAll() {
		return (List<TipoRiesgo>) tipoRiesgoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TipoRiesgo> findAll(Pageable pageable) {
		
		return tipoRiesgoDao.findAll(pageable);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public TipoRiesgo findById(Long id) {
		
		return tipoRiesgoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public TipoRiesgo save(TipoRiesgo tipoRiesgo) {
		
		return tipoRiesgoDao.save(tipoRiesgo);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		tipoRiesgoDao.deleteById(id);
	}
}
