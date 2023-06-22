package com.strategos.nueva.riesgos.procesos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.riesgos.procesos.dao.ProcesoDao;
import com.strategos.nueva.riesgos.procesos.model.Procesos;
import com.strategos.nueva.riesgos.procesos.service.ProcesoService;


public class ProcesoServiceImpl implements ProcesoService{
	@Autowired
	private ProcesoDao procesoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Procesos> findAll() {
		return (List<Procesos>) procesoDao.findAll();
	}

		
	@Override
	@Transactional(readOnly = true)
	public Procesos findById(Long id) {
		
		return procesoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Procesos save(Procesos proceso) {
		
		return procesoDao.save(proceso);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		procesoDao.deleteById(id);
	}
}
