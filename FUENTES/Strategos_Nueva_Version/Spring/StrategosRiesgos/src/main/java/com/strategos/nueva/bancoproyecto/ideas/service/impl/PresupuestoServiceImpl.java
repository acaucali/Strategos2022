package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.PresupuestoDao;
import com.strategos.nueva.bancoproyecto.ideas.model.Presupuesto;
import com.strategos.nueva.bancoproyecto.ideas.service.PresupuestoService;

@Service
public class PresupuestoServiceImpl implements PresupuestoService{

	@Autowired
	private PresupuestoDao presupuestoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Presupuesto> findAll() {
		return (List<Presupuesto>) presupuestoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Presupuesto findById(Long id) {
		
		return presupuestoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Presupuesto save(Presupuesto presupuesto) {
		
		return presupuestoDao.save(presupuesto);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		presupuestoDao.deleteById(id);
	}
			
}
