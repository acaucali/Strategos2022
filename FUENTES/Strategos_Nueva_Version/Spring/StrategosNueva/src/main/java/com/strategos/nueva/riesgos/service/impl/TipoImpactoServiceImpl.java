package com.strategos.nueva.riesgos.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.riesgos.dao.TipoImpactoDao;
import com.strategos.nueva.riesgos.model.TipoImpacto;
import com.strategos.nueva.riesgos.service.TipoImpactoService;



@Service
public class TipoImpactoServiceImpl implements TipoImpactoService{
	@Autowired
	private TipoImpactoDao tipoImpactoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoImpacto> findAll() {
		return (List<TipoImpacto>) tipoImpactoDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public TipoImpacto findById(Long id) {
		
		return tipoImpactoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public TipoImpacto save(TipoImpacto tipoImpacto) {
		
		return tipoImpactoDao.save(tipoImpacto);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		tipoImpactoDao.deleteById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TipoImpacto> findAllByPuntaje() {
		return (List<TipoImpacto>) tipoImpactoDao.findAll(Sort.by(Sort.Direction.ASC, "tipoImpactoPuntaje"));
	}
	
	
}
