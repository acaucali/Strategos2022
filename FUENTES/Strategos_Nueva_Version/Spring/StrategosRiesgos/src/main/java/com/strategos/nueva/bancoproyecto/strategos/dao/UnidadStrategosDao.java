package com.strategos.nueva.bancoproyecto.strategos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.nueva.bancoproyecto.ideas.model.CriteriosEvaluacion;
import com.strategos.nueva.bancoproyecto.strategos.model.OrganizacionesStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.UnidadStrategos;

public interface UnidadStrategosDao extends JpaRepository<UnidadStrategos, Long>{

}
