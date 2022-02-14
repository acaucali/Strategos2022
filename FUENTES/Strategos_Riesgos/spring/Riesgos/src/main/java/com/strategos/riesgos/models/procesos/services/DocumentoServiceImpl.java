package com.strategos.riesgos.models.procesos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.riesgos.models.procesos.dao.IDocumentoDao;
import com.strategos.riesgos.models.procesos.entity.Documento;

@Service
public class DocumentoServiceImpl implements IDocumentoService{
	@Autowired
	private IDocumentoDao procedimientoDao;
	
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
