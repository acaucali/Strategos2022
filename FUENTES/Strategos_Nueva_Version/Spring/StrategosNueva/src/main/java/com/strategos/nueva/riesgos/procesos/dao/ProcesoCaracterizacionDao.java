package com.strategos.nueva.riesgos.procesos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.strategos.nueva.riesgos.procesos.model.ProcesoCaracterizacion;



public interface ProcesoCaracterizacionDao extends JpaRepository<ProcesoCaracterizacion, Long>{
	
}