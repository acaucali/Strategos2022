package com.strategos.riesgos.models.procesos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.riesgos.models.procesos.dao.IProcesoCaracterizacionDao;
import com.strategos.riesgos.models.procesos.entity.ProcesoCaracterizacion;

@Service
public class ProcesoCaracterizacionServiceImpl implements IProcesoCaracterizacionService{
	
	@Autowired
	private IProcesoCaracterizacionDao procesoCaracterizacionDao;
	
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
	
	@Override
	@Transactional(readOnly = true)
	public Page<ProcesoCaracterizacion> findAll(Pageable pageable) {
		
		return procesoCaracterizacionDao.findAll(pageable);
	}


	
	

}
