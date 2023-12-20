package com.visiongc.app.strategos.explicaciones;

import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.ExplicacionPGN;
import com.visiongc.app.strategos.instrumentos.model.InstrumentoIniciativa;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.model.Usuario;

import java.io.File;
import java.util.List;
import java.util.Map;

public abstract interface StrategosExplicacionesService
  extends StrategosService
{
  public abstract PaginaLista getExplicaciones(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract int deleteExplicacion(Explicacion paramExplicacion, Usuario paramUsuario);
  
  public abstract int saveExplicacion(Explicacion paramExplicacion, Usuario paramUsuario);
  
  public abstract Long getNumeroAdjuntos(Long paramLong);
  
  public abstract Long getNumeroExplicaciones(Long paramLong);
  
  public abstract int deleteAdjuntoExplicacion(AdjuntoExplicacion paramAdjunto, Usuario paramUsuario);
  
  public abstract AdjuntoExplicacion getAdjunto(Long paramLong);
  
  public abstract List<AdjuntoExplicacion> getAdjuntos();

  public int saveAdjuntoExplicacion(AdjuntoExplicacion paramAdjunto, Long paramLong, Usuario usuario);
  
  public Explicacion getAdjuntosExplicacion(Explicacion explicacion, Usuario paramUsuario);
  
  // Explicaciones PGN
  
  public abstract PaginaLista getExplicacionesPGN(int paramInt1, int paramInt2, String paramString1, String paramString2, boolean paramBoolean, Map paramMap);
  
  public abstract int deleteExplicacionPGN(ExplicacionPGN paramExplicacion, Usuario paramUsuario);
  
  public abstract int saveExplicacionPGN(ExplicacionPGN paramExplicacion, Usuario paramUsuario);
 
}
