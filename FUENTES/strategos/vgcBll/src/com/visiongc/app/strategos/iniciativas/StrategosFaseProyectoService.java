package com.visiongc.app.strategos.iniciativas;

import java.util.Map;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.iniciativas.model.util.FaseProyecto;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;

public abstract interface StrategosFaseProyectoService extends StrategosService{
	
	public abstract PaginaLista getFasesProyecto(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
	
	public abstract int deleteFaseProyecto(FaseProyecto paramFase, Usuario paramUsuario);
	  
	public abstract int saveFaseProyecto(FaseProyecto paramFase, Usuario paramUsuario);

}
