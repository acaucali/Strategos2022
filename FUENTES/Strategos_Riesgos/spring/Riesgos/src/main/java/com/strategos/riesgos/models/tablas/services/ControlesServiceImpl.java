package com.strategos.riesgos.models.tablas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.riesgos.models.tablas.dao.IControlesDao;
import com.strategos.riesgos.models.tablas.entity.Controles;

@Service
public class ControlesServiceImpl implements IControlesService{
	@Autowired
	private IControlesDao controlesDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Controles> findAll() {
		return (List<Controles>) controlesDao.findAll();
	}


	@Override
	@Transactional(readOnly = true)
	public Page<Controles> findAll(Pageable pageable) {
		
		return controlesDao.findAll(pageable);
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
