package com.strategos.nueva.bancoproyecto.ideas.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.strategos.nueva.bancoproyecto.ideas.model.CriteriosEvaluacion;
import com.strategos.nueva.bancoproyecto.ideas.model.EstatusIdeas;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;
import com.strategos.nueva.bancoproyecto.ideas.model.Proyectos;

public interface ProyectosDao extends JpaRepository<Proyectos, Long>, JpaSpecificationExecutor<Proyectos>{

	List<Proyectos> findAllByDependenciaId(Long dependenciaId);
	
	List<Proyectos> findAllByDependenciaIdAndEstatusId(Long dependenciaId, Long estatusId);
	
	List<Proyectos> findAllByEstatusId(Long estatusId);
	
	List<Proyectos> findAllByDependenciaIdAndIsPreproyecto(Long dependenciaId, Boolean isPreproyecto);
	
	List<Proyectos> findAllByIsPreproyecto(Boolean isPreproyecto);
}
