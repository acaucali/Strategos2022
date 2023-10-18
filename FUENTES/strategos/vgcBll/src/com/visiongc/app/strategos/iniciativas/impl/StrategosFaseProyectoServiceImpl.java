package com.visiongc.app.strategos.iniciativas.impl;

import java.util.Map;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.iniciativas.StrategosFaseProyectoService;
import com.visiongc.app.strategos.iniciativas.model.util.FaseProyecto;
import com.visiongc.app.strategos.iniciativas.persistence.StrategosFaseProyectoPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;

public class StrategosFaseProyectoServiceImpl extends StrategosServiceImpl implements StrategosFaseProyectoService {
	private StrategosFaseProyectoPersistenceSession persistenceSession = null;

	public StrategosFaseProyectoServiceImpl(StrategosFaseProyectoPersistenceSession persistenceSession,
			boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources) {
		super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
		this.persistenceSession = persistenceSession;
	}

	public PaginaLista getFasesProyecto(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal,
			Map filtros) {
		return persistenceSession.getFasesProyecto(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);
	}

	public int deleteFaseProyecto(FaseProyecto faseProyecto, Usuario usuario) {
		boolean transActiva = false;
		int resultado = 10000;
		try {
			if (!persistenceSession.isTransactionActive()) {
				persistenceSession.beginTransaction();
				transActiva = true;
			}

			if (faseProyecto.getFaseProyectoId() != null) {
				resultado = persistenceSession.delete(faseProyecto, usuario);
			}

			if (resultado == 10000) {
				if (transActiva) {
					persistenceSession.commitTransaction();
					transActiva = false;
				}

			} else if (transActiva) {
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

	public int saveFaseProyecto(FaseProyecto faseProyecto, Usuario usuario) {
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
			fieldValues[0] = faseProyecto.getNombre();

			if ((faseProyecto.getFaseProyectoId() == null) || (faseProyecto.getFaseProyectoId().longValue() == 0L)) {
				if (persistenceSession.existsObject(faseProyecto, fieldNames, fieldValues)) {
					resultado = 10003;
				} else {
					faseProyecto.setFaseProyectoId(new Long(persistenceSession.getUniqueId()));
					resultado = persistenceSession.insert(faseProyecto, usuario);
				}
			} else {
				String[] idFieldNames = new String[1];
				Object[] idFieldValues = new Object[1];
				idFieldNames[0] = "faseProyectoId";
				idFieldValues[0] = faseProyecto.getFaseProyectoId();

				if (persistenceSession.existsObject(faseProyecto, fieldNames, fieldValues, idFieldNames,
						idFieldValues)) {
					resultado = 10003;
				} else {
					resultado = persistenceSession.update(faseProyecto, usuario);
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
