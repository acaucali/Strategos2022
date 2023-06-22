package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.TiposPropuestasDao;
import com.strategos.nueva.bancoproyecto.ideas.model.TiposPropuestas;
import com.strategos.nueva.bancoproyecto.ideas.service.TiposPropuestasService;

@Service
public class TiposPropuestasServiceImpl implements TiposPropuestasService{

	@Autowired
	private TiposPropuestasDao tiposPropuestasDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<TiposPropuestas> findAll() {
		return (List<TiposPropuestas>) tiposPropuestasDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public TiposPropuestas findById(Long id) {
		
		return tiposPropuestasDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public TiposPropuestas save(TiposPropuestas tiposPropuestas) {
		
		return tiposPropuestasDao.save(tiposPropuestas);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		tiposPropuestasDao.deleteById(id);
	}
			
}
