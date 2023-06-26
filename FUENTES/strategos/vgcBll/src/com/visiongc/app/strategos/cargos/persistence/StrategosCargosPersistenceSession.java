package com.visiongc.app.strategos.cargos.persistence;

import java.util.Map;

import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.commons.util.PaginaLista;

public abstract interface StrategosCargosPersistenceSession extends StrategosPersistenceSession{

	public abstract PaginaLista getCargos(int paramInt1, int paramInt2, String paramString1, String paramString2,  boolean paramBoolean, Map paramMap );
}
