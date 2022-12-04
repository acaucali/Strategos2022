package com.strategos.nueva.bancoproyecto.ideas.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.strategos.nueva.bancoproyecto.ideas.model.PresupuestoDatos;
import com.strategos.nueva.bancoproyecto.ideas.model.Proyectos;
import com.strategos.nueva.bancoproyecto.strategos.model.MedicionStrategos;

public interface PresupuestoDatosDao extends JpaRepository<PresupuestoDatos, Long>{
	
	public List<PresupuestoDatos> findAllByTareaId(Long tareaId);
	
	@Query("select p from PresupuestoDatos p where p.tareaId=?1 and p.serieId=?2 "
			+ "and p.ano=?3 and p.periodo >=?4 and p.periodo <=?5")
	public List<PresupuestoDatos> findByPeriodos(Long tareaId, Long serieId, Integer anio, Integer periodoIni, Integer periodoFin);

}
