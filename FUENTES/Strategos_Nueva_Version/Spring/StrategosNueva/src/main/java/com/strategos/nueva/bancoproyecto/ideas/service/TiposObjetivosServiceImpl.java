package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.TiposObjetivosDao;
import com.strategos.nueva.bancoproyecto.ideas.model.TiposObjetivos;

@Service
public class TiposObjetivosServiceImpl implements TiposObjetivosService{

	@Autowired
	private TiposObjetivosDao tiposObjetivosDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<TiposObjetivos> findAll() {
		return (List<TiposObjetivos>) tiposObjetivosDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public TiposObjetivos findById(Long id) {
		
		return tiposObjetivosDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public TiposObjetivos save(TiposObjetivos tiposObjetivos) {
		
		return tiposObjetivosDao.save(tiposObjetivos);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		tiposObjetivosDao.deleteById(id);
	}
			
}
