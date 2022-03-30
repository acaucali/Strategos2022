package com.strategos.nueva.bancoproyecto.ideas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.bancoproyecto.ideas.dao.IdeasDocumentosAnexosDao;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasDocumentosAnexos;

@Service
public class IdeasDocumentosAnexosServiceImpl implements IdeasDocumentosAnexosService{

	@Autowired
	private IdeasDocumentosAnexosDao ideasDocumentosAnexosDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<IdeasDocumentosAnexos> findAll() {
		return (List<IdeasDocumentosAnexos>) ideasDocumentosAnexosDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public IdeasDocumentosAnexos findById(Long id) {
		
		return ideasDocumentosAnexosDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public IdeasDocumentosAnexos save(IdeasDocumentosAnexos anexos) {
		
		return ideasDocumentosAnexosDao.save(anexos);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
	 ideasDocumentosAnexosDao.deleteById(id);
	}
			
}
