package com.strategos.nueva.bancoproyecto.strategos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.strategos.nueva.bancoproyecto.ideas.model.CriteriosEvaluacion;
import com.strategos.nueva.bancoproyecto.ideas.model.ProyectosPoblacion;
import com.strategos.nueva.bancoproyecto.strategos.model.OrganizacionesStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.UsuarioStrategos;

public interface UsuarioStrategosDao extends JpaRepository<UsuarioStrategos, Long>{

	
	public UsuarioStrategos findByUser(String user);
	
	@Query("select u from UsuarioStrategos u where u.user=?1")
	public UsuarioStrategos findByUsername2(String username);
	
}
