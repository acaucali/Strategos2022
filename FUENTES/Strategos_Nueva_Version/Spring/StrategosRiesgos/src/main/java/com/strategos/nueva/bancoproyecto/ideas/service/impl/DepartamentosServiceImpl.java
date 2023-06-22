package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.DepartamentosDao;
import com.strategos.nueva.bancoproyecto.ideas.model.Departamentos;
import com.strategos.nueva.bancoproyecto.ideas.service.DepartamentosService;

@Service
public class DepartamentosServiceImpl implements DepartamentosService{
	
	@Autowired
	private DepartamentosDao departamentosDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Departamentos> findAll(){
		return  (List<Departamentos>) departamentosDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Departamentos findById(Long id) {
		return departamentosDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public Departamentos save(Departamentos departamento) {
		return departamentosDao.save(departamento);		
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		departamentosDao.deleteById(id);
	}

}
