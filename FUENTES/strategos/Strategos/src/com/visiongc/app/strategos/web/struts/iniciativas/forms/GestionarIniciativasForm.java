package com.visiongc.app.strategos.web.struts.iniciativas.forms;

import java.util.List;

import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.iniciativas.model.util.TipoCalculoEstadoIniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.framework.web.struts.forms.VisorListaForm;

public class GestionarIniciativasForm
  extends VisorListaForm
{
  static final long serialVersionUID = 0L;
  private String filtroNombre;
  private String filtroAnio;
  private String seleccionadoId;
  private String nombreIniciativaSeleccionada;
  private Byte tipoAlerta;
  private Long organizacionId;
  private Long iniciativaId;
  private String respuesta;
  private Long planId;
  private String source;
  private List<IniciativaEstatus> tiposEstatus;
  private Long estatus;
  private Long tipo;
  private List<TipoProyecto> tipos;
  private Long portafolioId;
  private Long instrumentoId;
  
  // Permisos Reportes
  private boolean detallado;
  private boolean resumido;
  private boolean resumidoVigentes;
  private boolean datosBasicos;
  private boolean medicionesAtrasadas;
  private boolean detalladoPlanes;
  private boolean indicadores;
  

  public String getFiltroNombre()
  {
    return this.filtroNombre;
  }

  public void setFiltroNombre(String filtroNombre)
  {
    this.filtroNombre = filtroNombre;
  }

  public String getFiltroAnio()
  {
    return this.filtroAnio;
  }

  public void setFiltroAnio(String filtroAnio)
  {
    this.filtroAnio = filtroAnio;
  }

  public String getSeleccionadoId()
  {
    return this.seleccionadoId;
  }

  public void setSeleccionadoId(String seleccionadoId)
  {
    this.seleccionadoId = seleccionadoId;
  }

  public String getNombreIniciativaSeleccionada()
  {
    return this.nombreIniciativaSeleccionada;
  }

  public void setNombreIniciativaSeleccionada(String nombreIniciativaSeleccionada)
  {
    this.nombreIniciativaSeleccionada = nombreIniciativaSeleccionada;
  }

  public Byte getTipoAlerta()
  {
    return this.tipoAlerta;
  }

  public void setTipoAlerta(Byte tipoAlerta)
  {
    this.tipoAlerta = tipoAlerta;
  }

  public Long getOrganizacionId()
  {
    return this.organizacionId;
  }

  public void setOrganizacionId(Long organizacionId)
  {
    this.organizacionId = organizacionId;
  }

  public Byte getTipoCalculoEstadoIniciativaPorSeguimientos()
  {
    return TipoCalculoEstadoIniciativa.getTipoCalculoEstadoIniciativaPorSeguimientos();
  }

  public Byte getTipoCalculoEstadoIniciativaPorActividades()
  {
    return TipoCalculoEstadoIniciativa.getTipoCalculoEstadoIniciativaPorActividades();
  }

  public Long getIniciativaId()
  {
    return this.iniciativaId;
  }

  public void setIniciativaId(Long iniciativaId)
  {
    this.iniciativaId = iniciativaId;
  }

  public String getRespuesta()
  {
    return this.respuesta;
  }

  public void setRespuesta(String respuesta)
  {
    this.respuesta = respuesta;
  }

  public String getSource()
  {
    return this.source;
  }

  public void setSource(String source)
  {
    this.source = source;
  }

  public Long getPlanId()
  {
    return this.planId;
  }

  public void setPlanId(Long planId)
  {
    this.planId = planId;
  }

  public List<IniciativaEstatus> getTiposEstatus()
  {
    return this.tiposEstatus;
  }

  public void setTiposEstatus(List<IniciativaEstatus> tiposEstatus)
  {
    this.tiposEstatus = tiposEstatus;
  }

  public Long getEstatus()
  {
    return this.estatus;
  }

  public void setEstatus(Long estatus)
  {
    this.estatus = estatus;
  }

  public Long getPortafolioId()
  {
    return this.portafolioId;
  }

  public void setPortafolioId(Long portafolioId)
  {
    this.portafolioId = portafolioId;
  }

  public Long getTipo() {
	return tipo;
  }

  public void setTipo(Long tipo) {
	this.tipo = tipo;
  }

  public List<TipoProyecto> getTipos() {
	return tipos;
  }

  public void setTipos(List<TipoProyecto> tipos) {
	this.tipos = tipos;
  }

  public Long getInstrumentoId() {
	return instrumentoId;
  }

  public void setInstrumentoId(Long instrumentoId) {
	this.instrumentoId = instrumentoId;
  }

public void clear()
  {
    this.seleccionadoId = null;
    this.nombreIniciativaSeleccionada = null;
    this.organizacionId = null;
    this.iniciativaId = null;
    this.tipoAlerta = null;
    this.respuesta = "";
    this.planId = null;
    this.filtroNombre = null;
    this.filtroAnio = null;
    this.tiposEstatus = null;
    this.estatus = null;
    this.tipo = null;
    this.tipos = null;
    this.portafolioId = null;
    
    this.detallado = true;
    this.resumido = true;
    this.detalladoPlanes = true;
    this.datosBasicos = true;
    this.medicionesAtrasadas = true;
    this.indicadores = true;
    this.resumidoVigentes = true;

    FiltroForm filtro = new FiltroForm();
    filtro.setHistorico(Byte.valueOf(HistoricoType.getFiltroHistoricoNoMarcado()));
    filtro.setIncluirTodos(Boolean.valueOf(true));
    setFiltro(filtro);
  }

public boolean isDetallado() {
	return detallado;
}

public void setDetallado(boolean detallado) {
	this.detallado = detallado;
}

public boolean isResumido() {
	return resumido;
}

public void setResumido(boolean resumido) {
	this.resumido = resumido;
}

public boolean isResumidoVigentes() {
	return resumidoVigentes;
}

public void setResumidoVigentes(boolean resumidoVigentes) {
	this.resumidoVigentes = resumidoVigentes;
}

public boolean isDatosBasicos() {
	return datosBasicos;
}

public void setDatosBasicos(boolean datosBasicos) {
	this.datosBasicos = datosBasicos;
}

public boolean isMedicionesAtrasadas() {
	return medicionesAtrasadas;
}

public void setMedicionesAtrasadas(boolean medicionesAtrasadas) {
	this.medicionesAtrasadas = medicionesAtrasadas;
}

public boolean isDetalladoPlanes() {
	return detalladoPlanes;
}

public void setDetalladoPlanes(boolean detalladoPlanes) {
	this.detalladoPlanes = detalladoPlanes;
}

public boolean isIndicadores() {
	return indicadores;
}

public void setIndicadores(boolean indicadores) {
	this.indicadores = indicadores;
}
}
