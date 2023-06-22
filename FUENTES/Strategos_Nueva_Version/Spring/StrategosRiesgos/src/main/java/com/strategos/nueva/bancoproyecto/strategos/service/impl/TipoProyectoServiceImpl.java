package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.TipoProyectoStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.model.TipoProyectoStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;
import com.strategos.nueva.bancoproyecto.strategos.service.TipoProyectoService;

@Service
public class TipoProyectoServiceImpl implements TipoProyectoService{

	@Autowired
	private TipoProyectoStrategosDao tipoProyectoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoProyectoStrategos> findAll() {
		return (List<TipoProyectoStrategos>) tipoProyectoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public TipoProyectoStrategos findById(Long id) {
		
		return tipoProyectoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public TipoProyectoStrategos save(TipoProyectoStrategos tipoProyecto) {
		
		return tipoProyectoDao.save(tipoProyecto);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		tipoProyectoDao.deleteById(id);
	}
			
}
