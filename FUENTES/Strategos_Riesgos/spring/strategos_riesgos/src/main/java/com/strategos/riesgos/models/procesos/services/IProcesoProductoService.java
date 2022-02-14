package com.strategos.riesgos.models.procesos.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.strategos.riesgos.models.procesos.entity.ProcesoProducto;

public interface IProcesoProductoService {

	public List<ProcesoProducto> findAll();
	
	
	public Page<ProcesoProducto> findAll(Pageable pageable);
	
	
	public ProcesoProducto findById(Long id);

	
	public ProcesoProducto save(ProcesoProducto procesoProducto);
	
	
	public void delete(Long id);
}
