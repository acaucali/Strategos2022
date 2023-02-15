package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.RolesDao;
import com.strategos.nueva.bancoproyecto.ideas.model.Roles;
import com.strategos.nueva.bancoproyecto.ideas.service.EstatusIdeaService;
import com.strategos.nueva.bancoproyecto.ideas.service.RolesService;

@Service
public class RolesServiceImpl implements RolesService{

	@Autowired
	private RolesDao rolesDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Roles> findAll() {
		return (List<Roles>) rolesDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Roles findById(Long id) {
		
		return rolesDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Roles save(Roles roles) {
		
		return rolesDao.save(roles);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		rolesDao.deleteById(id);
	}
			
}
