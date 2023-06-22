package com.strategos.nueva.riesgos.ejercicios.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.riesgos.ejercicios.dao.EjercicioRiesgoDao;
import com.strategos.nueva.riesgos.ejercicios.model.EjercicioRiesgo;
import com.strategos.nueva.riesgos.ejercicios.service.EjercicioService;



@Service
public class EjercicioServiceImpl implements EjercicioService{

	@Autowired
	private EjercicioRiesgoDao ejercicioRiesgoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<EjercicioRiesgo> findAll() {
		return (List<EjercicioRiesgo>) ejercicioRiesgoDao.findAll();
	}


	@Override
	@Transactional(readOnly = true)
	public Page<EjercicioRiesgo> findAllByProcesoPadreId(Long procesoPadreId, Pageable pageable) {
		return ejercicioRiesgoDao.findAllByProcesoPadreId(procesoPadreId, pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public EjercicioRiesgo findById(Long id) {
		
		return ejercicioRiesgoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public EjercicioRiesgo save(EjercicioRiesgo causaRiesgo) {
		
		return ejercicioRiesgoDao.save(causaRiesgo);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		ejercicioRiesgoDao.deleteById(id);
	}

}
