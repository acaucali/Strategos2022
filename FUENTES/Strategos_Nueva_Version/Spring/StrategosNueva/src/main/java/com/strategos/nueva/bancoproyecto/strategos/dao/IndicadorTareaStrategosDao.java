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
import com.strategos.nueva.bancoproyecto.strategos.model.IndicadorTareaStrategos;


public interface IndicadorTareaStrategosDao extends JpaRepository<IndicadorTareaStrategos, Long>{

	@Query("select i from IndicadorTareaStrategos i where i.pk.actividadId=?1")
	public List<IndicadorTareaStrategos> findByTarea(Long actividadId);
		
	
	@Query("delete from IndicadorTareaStrategos where pk.actividadId=?1 and pk.indicadorId=?2")
	public void deleteIndicadorTarea(Long actividadId, Long indicadorId);
}
