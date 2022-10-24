package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.SerieIndicadorStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.model.SerieIndicadorStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.SerieIndicadorService;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;

@Service
public class SerieIndicadorServiceImpl implements SerieIndicadorService{

	@Autowired
	private SerieIndicadorStrategosDao serieIndicadorDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<SerieIndicadorStrategos> findAll() {
		return (List<SerieIndicadorStrategos>) serieIndicadorDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public SerieIndicadorStrategos findById(Long id) {
		
		return serieIndicadorDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public SerieIndicadorStrategos save(SerieIndicadorStrategos serie) {
		
		return serieIndicadorDao.save(serie);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		serieIndicadorDao.deleteById(id);
	}
			
}
