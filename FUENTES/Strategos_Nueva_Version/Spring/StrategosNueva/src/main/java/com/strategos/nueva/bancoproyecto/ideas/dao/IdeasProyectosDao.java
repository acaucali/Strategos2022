package com.strategos.nueva.bancoproyecto.ideas.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import com.strategos.nueva.bancoproyecto.ideas.model.CriteriosEvaluacion;
import com.strategos.nueva.bancoproyecto.ideas.model.IdeasProyectos;

public interface IdeasProyectosDao extends JpaRepository<IdeasProyectos, Long>, JpaSpecificationExecutor<IdeasProyectos>{
	
	List<IdeasProyectos> findAllByDependenciaId(Long dependenciaId);
	
}
