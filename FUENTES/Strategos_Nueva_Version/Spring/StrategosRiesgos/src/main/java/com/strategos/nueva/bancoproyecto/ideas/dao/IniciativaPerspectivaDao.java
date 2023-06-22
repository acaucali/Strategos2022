package com.strategos.nueva.bancoproyecto.ideas.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.strategos.nueva.bancoproyecto.ideas.model.IniciativaPerspectiva;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorPerspectiva;

public interface IniciativaPerspectivaDao extends JpaRepository<IniciativaPerspectiva, Long>{

	@Query("select i from IniciativaPerspectiva i where i.perspectivaId=?1")
	public List<IniciativaPerspectiva> findByPerspectiva(Long perspectivaId);
	
	@Query("select i from IniciativaPerspectiva i where i.perspectivaId=?1 and i.iniciativaId=?2")
	public IniciativaPerspectiva findByPerspectivaIniciativa(Long perspectivaId, Long iniciativaId);
	
}
