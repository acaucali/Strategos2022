package com.strategos.nueva.bancoproyecto.strategos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.strategos.nueva.bancoproyecto.strategos.model.MedicionStrategos;

public interface MedicionStrategosDao extends JpaRepository<MedicionStrategos, Long>{

	@Query("select m from MedicionStrategos m where m.medicionPk.indicadorId=?1 and m.medicionPk.serieId=?2 "
			+ "and m.medicionPk.ano=?3 and m.medicionPk.periodo >=?4 and m.medicionPk.periodo <=?5")
	public List<MedicionStrategos> findByPeriodos(Long indicadorId, Long serieId, Integer anio, Integer periodoIni, Integer periodoFin);
	
	
}
