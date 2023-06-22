package com.strategos.nueva.bancoproyecto.strategos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.strategos.nueva.bancoproyecto.strategos.model.UsuarioStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.ClaseIndicadoresStrategos;

public interface ClaseIndicadoresStrategosDao extends JpaRepository<ClaseIndicadoresStrategos, Long>{

	public List<ClaseIndicadoresStrategos> findByOrganizacionIdAndTipo(Long organizacionId, Byte tipo);
	
	@Query("select c from ClaseIndicadoresStrategos c where c.organizacionId=?1 and c.tipo=?2 and padreId is null")
	public ClaseIndicadoresStrategos findByClaseRaiz(Long organizacionId, Byte tipo);
	
}
