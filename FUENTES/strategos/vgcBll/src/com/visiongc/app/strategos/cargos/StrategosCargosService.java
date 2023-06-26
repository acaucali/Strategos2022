package com.visiongc.app.strategos.cargos;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.cargos.model.Cargos;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;
import java.util.Map;

public interface StrategosCargosService extends StrategosService{
	public abstract PaginaLista getCargos(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
	
	public abstract int deleteCargo(Cargos paramCargos, Usuario paramUsuario);
	
	public abstract int saveCargo(Cargos paramCargos, Usuario paramUsuario);
}
