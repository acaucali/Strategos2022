package com.strategos.nueva.riesgos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.strategos.nueva.bancoproyecto.strategos.model.UsuarioStrategos;
import com.strategos.nueva.riesgos.model.CalificacionesRiesgo;


public interface CalificacionesRiesgoDao extends JpaRepository<CalificacionesRiesgo, Long>{

	
	
}
