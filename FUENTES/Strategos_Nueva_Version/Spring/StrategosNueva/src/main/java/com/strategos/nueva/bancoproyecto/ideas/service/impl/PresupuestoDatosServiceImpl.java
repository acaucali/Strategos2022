package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.PresupuestoDatosDao;
import com.strategos.nueva.bancoproyecto.ideas.model.PresupuestoDatos;
import com.strategos.nueva.bancoproyecto.ideas.service.PresupuestoDatosService;

@Service
public class PresupuestoDatosServiceImpl implements PresupuestoDatosService{

	@Autowired
	private PresupuestoDatosDao presupuestoDatosDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<PresupuestoDatos> findAll() {
		return (List<PresupuestoDatos>) presupuestoDatosDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public PresupuestoDatos findById(Long id) {
		
		return presupuestoDatosDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public PresupuestoDatos save(PresupuestoDatos presupuesto) {
		
		return presupuestoDatosDao.save(presupuesto);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		presupuestoDatosDao.deleteById(id);
	}

	@Override
	public List<PresupuestoDatos> findAllByTareaId(Long tareaId) {
		// TODO Auto-generated method stub
		return presupuestoDatosDao.findAllByTareaId(tareaId);
	}

	@Override
	public List<PresupuestoDatos> findByPeriodos(Long tareaId, Long serieId, Integer anio, Integer periodoIni,
			Integer periodoFin) {
		// TODO Auto-generated method stub
		return presupuestoDatosDao.findByPeriodos(tareaId, serieId, anio, periodoIni, periodoFin);
	}
			
}
