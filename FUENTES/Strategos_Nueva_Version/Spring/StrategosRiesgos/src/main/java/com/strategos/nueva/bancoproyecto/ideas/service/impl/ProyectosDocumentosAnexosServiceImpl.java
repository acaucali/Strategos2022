package com.strategos.nueva.bancoproyecto.ideas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.ProyectosDocumentosAnexosDao;
import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosDocumentosAnexos;
import com.strategos.nueva.bancoproyecto.ideas.service.ProyectosDocumentosAnexosService;

@Service
public class ProyectosDocumentosAnexosServiceImpl implements ProyectosDocumentosAnexosService{

	@Autowired
	private ProyectosDocumentosAnexosDao proyectosDocumentosAnexosDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<ProyectosDocumentosAnexos> findAll(){
		return (List<ProyectosDocumentosAnexos>) proyectosDocumentosAnexosDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public ProyectosDocumentosAnexos findById(Long id) {
		return proyectosDocumentosAnexosDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional
	public ProyectosDocumentosAnexos save(ProyectosDocumentosAnexos anexos) {
		return proyectosDocumentosAnexosDao.save(anexos);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		
		proyectosDocumentosAnexosDao.deleteById(id);
	}
}
