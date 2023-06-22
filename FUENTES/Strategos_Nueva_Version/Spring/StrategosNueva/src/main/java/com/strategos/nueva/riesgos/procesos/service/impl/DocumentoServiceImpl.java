package com.strategos.nueva.riesgos.procesos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.riesgos.procesos.dao.DocumentoDao;
import com.strategos.nueva.riesgos.procesos.model.Documento;
import com.strategos.nueva.riesgos.procesos.service.DocumentoService;



@Service
public class DocumentoServiceImpl implements DocumentoService{
	@Autowired
	private DocumentoDao procedimientoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Documento> findAll() {
		return (List<Documento>) procedimientoDao.findAll();
	}

		
	@Override
	@Transactional(readOnly = true)
	public Documento findById(Long id) {
		
		return procedimientoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Documento save(Documento documento) {
		
		return procedimientoDao.save(documento);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		procedimientoDao.deleteById(id);
	}
}
