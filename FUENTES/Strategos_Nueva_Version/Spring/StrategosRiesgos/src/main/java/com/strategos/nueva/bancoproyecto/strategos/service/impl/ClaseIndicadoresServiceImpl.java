package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.ClaseIndicadoresStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.model.ClaseIndicadoresStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.ClaseIndicadorService;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;

@Service
public class ClaseIndicadoresServiceImpl implements ClaseIndicadorService{

	@Autowired
	private ClaseIndicadoresStrategosDao claseDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<ClaseIndicadoresStrategos> findAll() {
		return (List<ClaseIndicadoresStrategos>) claseDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public ClaseIndicadoresStrategos findById(Long id) {
		
		return claseDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public ClaseIndicadoresStrategos save(ClaseIndicadoresStrategos clase) {
		
		return claseDao.save(clase);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		claseDao.deleteById(id);
	}

	@Override
	public List<ClaseIndicadoresStrategos> findByOrganizacionIdAndTipo(Long organizacionId, Byte tipo) {
		// TODO Auto-generated method stub
		return claseDao.findByOrganizacionIdAndTipo(organizacionId, tipo);
	}

	@Override
	public ClaseIndicadoresStrategos findByClaseRaiz(Long organizacionId, Byte tipo) {
		// TODO Auto-generated method stub
		return claseDao.findByClaseRaiz(organizacionId, tipo);
	}
			
}
