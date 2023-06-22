package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.OrganizacionesStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.model.OrganizacionesStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;

@Service
public class OrganizacionServiceImpl implements OrganizacionService{

	@Autowired
	private OrganizacionesStrategosDao organizacionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<OrganizacionesStrategos> findAll() {
		return (List<OrganizacionesStrategos>) organizacionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public OrganizacionesStrategos findById(Long id) {
		
		return organizacionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public OrganizacionesStrategos save(OrganizacionesStrategos organizacion) {
		
		return organizacionDao.save(organizacion);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		organizacionDao.deleteById(id);
	}
			
}
