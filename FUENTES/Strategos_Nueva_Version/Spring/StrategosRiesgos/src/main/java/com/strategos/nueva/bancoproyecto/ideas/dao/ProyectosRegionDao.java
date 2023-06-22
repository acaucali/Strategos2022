package com.strategos.nueva.bancoproyecto.ideas.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosRegion;

public interface ProyectosRegionDao extends JpaRepository<ProyectosRegion, Long>{
	
	List<ProyectosRegion> findAllByDepartamentoId(Long departamentoId);
	
	List<ProyectosRegion> findAllByProyectoId(Long proyectoId);

}
