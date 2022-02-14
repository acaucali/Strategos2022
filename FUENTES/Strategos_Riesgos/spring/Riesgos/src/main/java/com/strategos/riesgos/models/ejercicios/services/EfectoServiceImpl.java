package com.strategos.riesgos.models.ejercicios.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.riesgos.model.ejercicios.entity.EfectoRiesgo;
import com.strategos.riesgos.models.ejercicios.dao.IEfectoRiesgoDao;

@Service
public class EfectoServiceImpl implements IEfectoService{
	@Autowired
	private IEfectoRiesgoDao efectoRiesgoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<EfectoRiesgo> findAll() {
		return (List<EfectoRiesgo>) efectoRiesgoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<EfectoRiesgo> findAll(Pageable pageable) {
		
		return efectoRiesgoDao.findAll(pageable);
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
