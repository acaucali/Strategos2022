package com.strategos.nueva.riesgos.procesos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.riesgos.procesos.dao.ProcesoCaracterizacionDao;
import com.strategos.nueva.riesgos.procesos.model.ProcesoCaracterizacion;
import com.strategos.nueva.riesgos.procesos.service.ProcesoCaracterizacionService;


@Service
public class ProcesoCaracterizacionServiceImpl implements ProcesoCaracterizacionService{
	
	@Autowired
	private ProcesoCaracterizacionDao procesoCaracterizacionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<ProcesoCaracterizacion> findAll() {
		return (List<ProcesoCaracterizacion>) procesoCaracterizacionDao.findAll();
	}


	@Override
	@Transactional(readOnly = true)
	public ProcesoCaracterizacion findById(Long id) {
		
		return procesoCaracterizacionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public ProcesoCaracterizacion save(ProcesoCaracterizacion procesoCaracterizacion) {
		
		return procesoCaracterizacionDao.save(procesoCaracterizacion);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		procesoCaracterizacionDao.deleteById(id);
	}
	

	
	

}
