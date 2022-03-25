package com.strategos.nueva.bancoproyecto.ideas.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.nueva.bancoproyecto.ideas.model.CriteriosEvaluacion;
import com.strategos.nueva.bancoproyecto.ideas.model.TiposPropuestas;

public interface TiposPropuestasDao extends JpaRepository<TiposPropuestas, Long>{

}
