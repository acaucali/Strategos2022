package com.strategos.nueva.bancoproyecto.ideas.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.nueva.bancoproyecto.ideas.model.IdeasEvaluadas;

public interface IdeasEvaluadasDao extends JpaRepository<IdeasEvaluadas, Long>{

	List<IdeasEvaluadas> findAllByEvaluacionId(Long evaluacionId);
	
}
