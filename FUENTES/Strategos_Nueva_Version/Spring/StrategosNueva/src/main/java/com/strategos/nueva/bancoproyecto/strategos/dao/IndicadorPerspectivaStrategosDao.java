package com.strategos.nueva.bancoproyecto.strategos.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.strategos.nueva.bancoproyecto.ideas.model.CriteriosEvaluacion;
import com.strategos.nueva.bancoproyecto.ideas.model.IniciativaPerspectiva;
import com.strategos.nueva.bancoproyecto.strategos.model.OrganizacionesStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.PerspectivaStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.UsuarioStrategos;
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorPerspectiva;


public interface IndicadorPerspectivaStrategosDao extends JpaRepository<IndicadorPerspectiva, Long>{

	@Query("select i from IndicadorPerspectiva i where i.pk.perspectivaId=?1")
	public List<IndicadorPerspectiva> findByPerspectiva(Long perspectivaId);
		
	
	@Query("delete from IndicadorPerspectiva where pk.perspectivaId=?1 and pk.indicadorId=?2")
	public void deleteIndicadorPerspectiva(Long perspectivaId, Long indicadorId);
}
