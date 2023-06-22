package com.strategos.nueva.riesgos.procesos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.nueva.riesgos.procesos.model.ProcesoProducto;



public interface ProcesoProductoService {

	public List<ProcesoProducto> findAll();
	
	

	
	
	public ProcesoProducto findById(Long id);

	
	public ProcesoProducto save(ProcesoProducto procesoProducto);
	
	
	public void delete(Long id);
}
