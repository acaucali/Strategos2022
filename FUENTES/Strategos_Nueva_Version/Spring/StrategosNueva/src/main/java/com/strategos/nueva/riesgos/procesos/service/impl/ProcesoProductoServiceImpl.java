package com.strategos.nueva.riesgos.procesos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.riesgos.procesos.dao.ProcesoProductoDao;
import com.strategos.nueva.riesgos.procesos.model.ProcesoProducto;
import com.strategos.nueva.riesgos.procesos.service.ProcesoProductoService;


@Service
public class ProcesoProductoServiceImpl implements ProcesoProductoService{
	
	@Autowired
	private ProcesoProductoDao procesoProductoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<ProcesoProducto> findAll() {
		return (List<ProcesoProducto>) procesoProductoDao.findAll();
	}


	@Override
	@Transactional(readOnly = true)
	public ProcesoProducto findById(Long id) {
		
		return procesoProductoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public ProcesoProducto save(ProcesoProducto procesoProducto) {
		
		return procesoProductoDao.save(procesoProducto);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		procesoProductoDao.deleteById(id);
	}
	

	
}
