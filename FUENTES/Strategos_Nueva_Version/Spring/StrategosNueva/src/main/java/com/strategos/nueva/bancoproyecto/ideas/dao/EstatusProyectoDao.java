package com.strategos.nueva.bancoproyecto.ideas.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.nueva.bancoproyecto.ideas.model.CriteriosEvaluacion;
import com.strategos.nueva.bancoproyecto.ideas.model.EstatusProyecto;

public interface EstatusProyectoDao extends JpaRepository<EstatusProyecto, Long>{

}
