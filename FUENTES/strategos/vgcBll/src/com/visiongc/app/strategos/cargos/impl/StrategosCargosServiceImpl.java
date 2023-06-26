package com.visiongc.app.strategos.cargos.impl;

import java.util.Map;

import com.visiongc.app.strategos.cargos.StrategosCargosService;
import com.visiongc.app.strategos.cargos.model.Cargos;
import com.visiongc.app.strategos.cargos.persistence.StrategosCargosPersistenceSession;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;

public class StrategosCargosServiceImpl extends StrategosServiceImpl implements StrategosCargosService {

	private StrategosCargosPersistenceSession persistenceSession = null;

	public StrategosCargosServiceImpl(StrategosCargosPersistenceSession persistenceSession, boolean persistenceOwned,
			StrategosServiceFactory serviceFactory, VgcMessageResources messageResources) {
		super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
		this.persistenceSession = persistenceSession;
	}

	@Override
	public PaginaLista getCargos(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal,
			Map filtros) {
		return persistenceSession.getCargos(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
	}

	@Override
	public int deleteCargo(Cargos cargos, Usuario usuario) {
		boolean transActiva = false;
		int resultado = 10000;
		try {
			if (!persistenceSession.isTransactionActive()) {
				persistenceSession.beginTransaction();
				transActiva = true;
			}

			if (cargos.getCargoId() != null) {
				resultado = persistenceSession.delete(cargos, usuario);
			}

			if (resultado == 10000) {
				if (transActiva) {
					persistenceSession.commitTransaction();
					transActiva = false;
				}
			}

			else if (transActiva) {
				persistenceSession.rollbackTransaction();
				transActiva = false;
			}

		} catch (Throwable t) {
			if (transActiva) {
				persistenceSession.rollbackTransaction();
				throw new ChainedRuntimeException(t.getMessage(), t);
			}
		}

		return resultado;
	}

	@Override
	public int saveCargo(Cargos cargo, Usuario usuario) {
		boolean transActiva = false;
		int resultado = 10000;
		String[] fieldNames = new String[1];
		Object[] fieldValues = new Object[1];
		try {
			
			 if (!persistenceSession.isTransactionActive()) {
			        persistenceSession.beginTransaction();
			        transActiva = true;
			      }
			 
			 fieldNames[0] = "nombre";
		     fieldValues[0] = cargo.getNombre();		     		     
		     
		     if((cargo.getCargoId() == null) || (cargo.getCargoId().longValue() == 0L)) {		    	 		    	 
		    	 if(persistenceSession.existsObject(cargo, fieldNames, fieldValues)) {
		    		 resultado = 10003;		    		 
		    	 }
		    	 else {		    		 
		    		 cargo.setCargoId(new Long(persistenceSession.getUniqueId()));		    		 
		    		 resultado = persistenceSession.insert(cargo, usuario);
		    	 }
		     }else {
		    	 String[] idFieldNames = new String[1];
		         Object[] idFieldValues = new Object[1];
		         
		         idFieldNames[0] = "cargoId";
		         idFieldValues[0] = cargo.getCargoId();
		         if(persistenceSession.existsObject(cargo, idFieldNames, idFieldValues, idFieldNames, idFieldValues)) {
		        	 resultado = 10003;
		         }else {
		        	 resultado = persistenceSession.update(cargo, usuario);
		         }
		     }
		     
		     if (transActiva) {
		         if (resultado == 10000) {
		           persistenceSession.commitTransaction();
		         } else {
		           persistenceSession.rollbackTransaction();
		         }
		         transActiva = false;
		       }
			 
		} catch (Throwable t) {
			if (transActiva) {
				persistenceSession.rollbackTransaction();
			}
			throw new ChainedRuntimeException(t.getMessage(), t);
		}

		return resultado;
	}
}
