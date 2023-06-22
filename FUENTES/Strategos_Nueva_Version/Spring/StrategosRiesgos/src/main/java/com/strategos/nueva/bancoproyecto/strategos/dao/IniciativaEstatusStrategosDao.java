package com.strategos.nueva.bancoproyecto.strategos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.strategos.nueva.bancoproyecto.ideas.model.CriteriosEvaluacion;
import com.strategos.nueva.bancoproyecto.strategos.model.IniciativaEstatusStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.OrganizacionesStrategos;

public interface IniciativaEstatusStrategosDao extends JpaRepository<IniciativaEstatusStrategos, Long>{

}
