package com.strategos.riesgos.models.procesos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.strategos.riesgos.models.procesos.entity.ProcesoCaracterizacion;

public interface IProcesoCaracterizacionDao extends JpaRepository<ProcesoCaracterizacion, Long>{
	
}
