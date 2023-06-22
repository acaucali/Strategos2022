package com.strategos.nueva.riesgos.procesos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.nueva.riesgos.procesos.dao.ProcesoProductoDao;
import com.strategos.nueva.riesgos.procesos.dao.ProcesosDao;
import com.strategos.nueva.riesgos.procesos.model.ProcesoProducto;
import com.strategos.nueva.riesgos.procesos.model.Procesos;
import com.strategos.nueva.riesgos.procesos.service.ProcesosService;



@Service
public class ProcesosServiceImpl implements ProcesosService{
	@Autowired
	private ProcesosDao procesosDao;
	
	@Autowired
	private ProcesoProductoDao procesoProductoDao;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Procesos> findAll() {
		return (List<Procesos>) procesosDao.findAll();
	}


	@Override
	@Transactional(readOnly = true)
	public Procesos findById(Long id) {
		
		return procesosDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Procesos save(Procesos procesos) {
		
		return procesosDao.save(procesos);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		procesosDao.deleteById(id);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public ProcesoProducto findProductoById(Long id) {
		
		return procesoProductoDao.findById(id).orElse(null);
	}


	@Override
	public ProcesoProducto saveProducto(ProcesoProducto procesoProducto) {

		return procesoProductoDao.save(procesoProducto);
	}


	@Override
	public void deleteProducto(Long id) {
		
		procesoProductoDao.deleteById(id);
	}


	


	
}
