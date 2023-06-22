package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.EstatusProyectoDao;
import com.strategos.nueva.bancoproyecto.ideas.model.EstatusProyecto;
import com.strategos.nueva.bancoproyecto.ideas.service.EstatusProyectoService;

@Service
public class EstatusProyectoServiceImpl implements EstatusProyectoService{

	@Autowired
	private EstatusProyectoDao estatusProyectoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<EstatusProyecto> findAll() {
		return (List<EstatusProyecto>) estatusProyectoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public EstatusProyecto findById(Long id) {
		
		return estatusProyectoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public EstatusProyecto save(EstatusProyecto estatusProyecto) {
		
		return estatusProyectoDao.save(estatusProyecto);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		estatusProyectoDao.deleteById(id);
	}
			
}
