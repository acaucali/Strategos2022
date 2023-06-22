package com.strategos.nueva.riesgos.procesos.service;

import java.util.List;

import com.strategos.nueva.riesgos.procesos.model.ProcesoProducto;
import com.strategos.nueva.riesgos.procesos.model.Procesos;


public interface ProcesosService {
	
	public List<Procesos> findAll();
	
	
	public Procesos findById(Long id);

	
	public Procesos save(Procesos procesos);
	
	
	public void delete(Long id);
	
	
	public ProcesoProducto findProductoById(Long id);

	
	public ProcesoProducto saveProducto(ProcesoProducto procesoProducto);
	
	
	public void deleteProducto(Long id);

}
