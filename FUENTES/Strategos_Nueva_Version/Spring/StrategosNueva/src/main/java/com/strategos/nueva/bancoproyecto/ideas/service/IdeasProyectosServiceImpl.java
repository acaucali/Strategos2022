package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.IdeasProyectosDao;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;

@Service
public class IdeasProyectosServiceImpl implements IdeasProyectosService{

	@Autowired
	private IdeasProyectosDao ideasProyectosDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<IdeasProyectos> findAll() {
		return (List<IdeasProyectos>) ideasProyectosDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public IdeasProyectos findById(Long id) {
		
		return ideasProyectosDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public IdeasProyectos save(IdeasProyectos proyectos) {
		
		return ideasProyectosDao.save(proyectos);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		ideasProyectosDao.deleteById(id);
	}
			
}
