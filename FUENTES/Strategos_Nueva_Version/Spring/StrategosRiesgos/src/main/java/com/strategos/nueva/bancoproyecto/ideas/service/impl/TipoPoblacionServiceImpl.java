package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.TipoPoblacionDao;
import com.strategos.nueva.bancoproyecto.ideas.model.TipoPoblacion;
import com.strategos.nueva.bancoproyecto.ideas.service.TipoPoblacionService;

@Service
public class TipoPoblacionServiceImpl implements TipoPoblacionService{

	@Autowired
	private TipoPoblacionDao tipoPoblacionDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoPoblacion> findAll() {
		return (List<TipoPoblacion>) tipoPoblacionDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public TipoPoblacion findById(Long id) {
		
		return tipoPoblacionDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public TipoPoblacion save(TipoPoblacion poblacion) {
		
		return tipoPoblacionDao.save(poblacion);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		tipoPoblacionDao.deleteById(id);
	}
			
}
