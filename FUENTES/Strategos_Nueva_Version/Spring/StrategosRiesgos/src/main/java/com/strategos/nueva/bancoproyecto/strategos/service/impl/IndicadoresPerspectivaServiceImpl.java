package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.IndicadorPerspectivaStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorPerspectiva;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorPerspectivaService;
import com.strategos.nueva.bancoproyecto.strategos.service.MetodologiaService;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;

@Service
public class IndicadoresPerspectivaServiceImpl implements IndicadorPerspectivaService{

	@Autowired
	private IndicadorPerspectivaStrategosDao indicadorPerspectivaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<IndicadorPerspectiva> findAll() {
		return (List<IndicadorPerspectiva>) indicadorPerspectivaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public IndicadorPerspectiva findById(Long id) {
		
		return indicadorPerspectivaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public IndicadorPerspectiva save(IndicadorPerspectiva indicador) {
		
		return indicadorPerspectivaDao.save(indicador);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		indicadorPerspectivaDao.deleteById(id);
	}

	@Override
	public List<IndicadorPerspectiva> findByPerspectiva(Long perspectivaId) {
		return indicadorPerspectivaDao.findByPerspectiva(perspectivaId);
	}

	@Override
	public void deleteIndicadorPerspectiva(Long perspectivaId, Long indicadorId) {
		// TODO Auto-generated method stub
		indicadorPerspectivaDao.deleteIndicadorPerspectiva(perspectivaId, indicadorId);
	}
			
}
