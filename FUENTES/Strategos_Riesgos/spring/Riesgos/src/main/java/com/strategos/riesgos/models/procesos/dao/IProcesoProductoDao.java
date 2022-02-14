package com.strategos.riesgos.models.procesos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.riesgos.models.procesos.entity.ProcesoProducto;

public interface IProcesoProductoDao extends JpaRepository<ProcesoProducto, Long>{ 

}
