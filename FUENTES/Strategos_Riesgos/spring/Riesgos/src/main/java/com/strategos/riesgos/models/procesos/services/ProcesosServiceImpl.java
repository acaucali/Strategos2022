package com.strategos.riesgos.models.procesos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.strategos.riesgos.models.procesos.dao.IProcesoProductoDao;
import com.strategos.riesgos.models.procesos.dao.IProcesosDao;
import com.strategos.riesgos.models.procesos.entity.Procesos;
import com.strategos.riesgos.models.procesos.entity.ProcesoProducto;

@Service
public class ProcesosServiceImpl implements IProcesosService{
	@Autowired
	private IProcesosDao procesosDao;
	
	@Autowired
	private IProcesoProductoDao procesoProductoDao;
	
	
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
