package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.MedicionStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.model.MedicionStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.MedicionService;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;

@Service
public class MedicionServiceImpl implements MedicionService{

	@Autowired
	private MedicionStrategosDao medicionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<MedicionStrategos> findAll() {
		return (List<MedicionStrategos>) medicionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public MedicionStrategos findById(Long id) {
		
		return medicionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public MedicionStrategos save(MedicionStrategos medicion) {
		
		return medicionDao.save(medicion);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		medicionDao.deleteById(id);
	}

	@Override
	public List<MedicionStrategos> findByPeriodos(Long indicadorId, Long serieId, Integer anio, Integer periodoIni,
			Integer periodoFin) {
		// TODO Auto-generated method stub
		return medicionDao.findByPeriodos(indicadorId, serieId, anio, periodoIni, periodoFin);
	}
			
}
