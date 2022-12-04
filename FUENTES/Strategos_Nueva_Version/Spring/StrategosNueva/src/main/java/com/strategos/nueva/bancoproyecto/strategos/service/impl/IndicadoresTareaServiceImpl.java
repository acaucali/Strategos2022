package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.IndicadorStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.dao.IndicadorTareaStrategosDao;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorTareaStrategos;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorService;
import com.strategos.nueva.bancoproyecto.strategos.service.IndicadorTareaService;
import com.strategos.nueva.bancoproyecto.strategos.service.MetodologiaService;
import com.strategos.nueva.bancoproyecto.strategos.service.OrganizacionService;

@Service
public class IndicadoresTareaServiceImpl implements IndicadorTareaService{

	@Autowired
	private IndicadorTareaStrategosDao indicadorTareaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<IndicadorTareaStrategos> findAll() {
		return (List<IndicadorTareaStrategos>) indicadorTareaDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public IndicadorTareaStrategos findById(Long id) {
		
		return indicadorTareaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public IndicadorTareaStrategos save(IndicadorTareaStrategos indicador) {
		
		return indicadorTareaDao.save(indicador);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		indicadorTareaDao.deleteById(id);
	}

	@Override
	public void deleteIndicadorTarea(Long actividadId, Long indicadorId) {
		indicadorTareaDao.deleteIndicadorTarea(actividadId, indicadorId);
		
	}

	@Override
	public List<IndicadorTareaStrategos> findByTarea(Long actividadId) {
		return indicadorTareaDao.findByTarea(actividadId);
	}
			
}
