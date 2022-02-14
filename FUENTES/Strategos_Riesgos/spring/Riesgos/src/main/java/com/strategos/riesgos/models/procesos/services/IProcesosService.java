package com.strategos.riesgos.models.procesos.services;

import java.util.List;

import com.strategos.riesgos.models.procesos.entity.ProcesoProducto;
import com.strategos.riesgos.models.procesos.entity.Procesos;

public interface IProcesosService {
	
	public List<Procesos> findAll();
	
	
	public Procesos findById(Long id);

	
	public Procesos save(Procesos procesos);
	
	
	public void delete(Long id);
	
	
	public ProcesoProducto findProductoById(Long id);

	
	public ProcesoProducto saveProducto(ProcesoProducto procesoProducto);
	
	
	public void deleteProducto(Long id);

}
