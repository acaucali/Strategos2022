package com.strategos.nueva.riesgos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.riesgos.dao.ControlesDao;
import com.strategos.nueva.riesgos.model.Controles;
import com.strategos.nueva.riesgos.service.ControlesService;


@Service
public class ControlesServiceImpl implements ControlesService{
	@Autowired
	private ControlesDao controlesDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Controles> findAll() {
		return (List<Controles>) controlesDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Controles findById(Long id) {
		
		return controlesDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Controles save(Controles controles) {
		
		return controlesDao.save(controles);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		controlesDao.deleteById(id);
	}

	
}
