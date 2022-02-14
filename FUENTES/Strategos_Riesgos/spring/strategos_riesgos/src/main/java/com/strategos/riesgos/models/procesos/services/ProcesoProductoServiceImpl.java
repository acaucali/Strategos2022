package com.strategos.riesgos.models.procesos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.riesgos.models.procesos.dao.IProcesoProductoDao;
import com.strategos.riesgos.models.procesos.entity.ProcesoProducto;



@Service
public class ProcesoProductoServiceImpl implements IProcesoProductoService{
	
	@Autowired
	private IProcesoProductoDao procesoProductoDao;
	
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
	
	@Override
	@Transactional(readOnly = true)
	public Page<ProcesoProducto> findAll(Pageable pageable) {
		
		return procesoProductoDao.findAll(pageable);
	}
	
	
}
