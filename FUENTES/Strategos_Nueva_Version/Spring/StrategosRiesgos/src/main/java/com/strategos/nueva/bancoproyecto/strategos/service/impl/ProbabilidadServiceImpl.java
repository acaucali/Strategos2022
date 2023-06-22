package com.strategos.nueva.bancoproyecto.strategos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.strategos.dao.ProbabilidadDao;
import com.strategos.nueva.bancoproyecto.strategos.model.Probabilidad;
import com.strategos.nueva.bancoproyecto.strategos.service.ProbabilidadService;

@Service
public class ProbabilidadServiceImpl implements ProbabilidadService{
	@Autowired
	private ProbabilidadDao probabilidadDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Probabilidad> findAll() {
		return (List<Probabilidad>) probabilidadDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Probabilidad findById(Long id) {
		
		return probabilidadDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Probabilidad save(Probabilidad probabilidad) {
		
		return probabilidadDao.save(probabilidad);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		probabilidadDao.deleteById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Probabilidad> findAllByPuntaje() {
		return (List<Probabilidad>) probabilidadDao.findAll(Sort.by(Sort.Direction.DESC, "probabilidadPuntaje"));
	}
}
