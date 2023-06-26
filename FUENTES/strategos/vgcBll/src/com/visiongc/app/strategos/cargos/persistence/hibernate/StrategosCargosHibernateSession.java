package com.visiongc.app.strategos.cargos.persistence.hibernate;

import com.visiongc.app.strategos.cargos.model.Cargos;
import com.visiongc.app.strategos.cargos.persistence.StrategosCargosPersistenceSession;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.commons.util.PaginaLista;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

public class StrategosCargosHibernateSession extends StrategosHibernateSession implements StrategosCargosPersistenceSession{
	
	public StrategosCargosHibernateSession(Session session) {
		super(session);
	}
	
	public StrategosCargosHibernateSession(StrategosHibernateSession parentSession){
		super(parentSession);
	}
	
	public PaginaLista getCargos(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal, Map filtros) {
		Criteria consulta = prepareQuery(Cargos.class);
		
		if(filtros != null) {
			Iterator iter = filtros.keySet().iterator();
			String fieldName = null;
			String fieldValue = null;
			
			while(iter.hasNext()) {
				fieldName = (String)iter.next();
				fieldValue = (String)filtros.get(fieldName);
				
				if(fieldName.equals("nombre")) {
					consulta.add(Restrictions.ilike(fieldName, fieldValue, MatchMode.ANYWHERE));
				}
			}
		}
		
		return executeQuery(consulta, pagina, tamanoPagina, orden, tipoOrden, getTotal);
	}
}
