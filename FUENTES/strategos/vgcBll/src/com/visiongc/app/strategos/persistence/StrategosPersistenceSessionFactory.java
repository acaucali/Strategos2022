package com.visiongc.app.strategos.persistence;

import com.visiongc.app.strategos.cargos.persistence.StrategosCargosPersistenceSession;
import com.visiongc.app.strategos.categoriasmedicion.persistence.StrategosCategoriasPersistenceSession;
import com.visiongc.app.strategos.causas.persistence.StrategosCausasPersistenceSession;
import com.visiongc.app.strategos.estadosacciones.persistence.StrategosEstadosPersistenceSession;
import com.visiongc.app.strategos.explicaciones.persistence.StrategosExplicacionesPersistenceSession;
import com.visiongc.app.strategos.foros.persistence.StrategosForosPersistenceSession;
import com.visiongc.app.strategos.graficos.persistence.StrategosGraficosPersistenceSession;
import com.visiongc.app.strategos.indicadores.persistence.StrategosClasesIndicadoresPersistenceSession;
import com.visiongc.app.strategos.indicadores.persistence.StrategosIndicadorAsignarInventarioPersistenceSession;
import com.visiongc.app.strategos.indicadores.persistence.StrategosIndicadoresPersistenceSession;
import com.visiongc.app.strategos.indicadores.persistence.StrategosMedicionesPersistenceSession;
import com.visiongc.app.strategos.indicadores.persistence.StrategosMedicionesValoracionPersistenceSession;
import com.visiongc.app.strategos.iniciativas.persistence.StrategosFaseProyectoPersistenceSession;
import com.visiongc.app.strategos.iniciativas.persistence.StrategosIniciativaEstatusPersistenceSession;
import com.visiongc.app.strategos.iniciativas.persistence.StrategosIniciativasPersistenceSession;
import com.visiongc.app.strategos.iniciativas.persistence.StrategosTipoProyectoPersistenceSession;
import com.visiongc.app.strategos.instrumentos.persistence.StrategosCooperantesPersistenceSession;
import com.visiongc.app.strategos.instrumentos.persistence.StrategosInstrumentosPersistenceSession;
import com.visiongc.app.strategos.instrumentos.persistence.StrategosTiposConvenioPersistenceSession;
import com.visiongc.app.strategos.modulo.codigoenlace.persistence.StrategosCodigoEnlacePersistenceSession;
import com.visiongc.app.strategos.organizaciones.persistence.StrategosOrganizacionesPersistenceSession;
import com.visiongc.app.strategos.plancuentas.persistence.StrategosCuentasPersistenceSession;
import com.visiongc.app.strategos.planes.persistence.StrategosMetasPersistenceSession;
import com.visiongc.app.strategos.planes.persistence.StrategosModelosPersistenceSession;
import com.visiongc.app.strategos.planes.persistence.StrategosPerspectivasPersistenceSession;
import com.visiongc.app.strategos.planes.persistence.StrategosPlanesPersistenceSession;
import com.visiongc.app.strategos.planes.persistence.StrategosPlantillasPlanesPersistenceSession;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.StrategosPrdProductosPersistenceSession;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.StrategosPryActividadesPersistenceSession;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.StrategosPryProyectosPersistenceSession;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.StrategosPryVistasPersistenceSession;
import com.visiongc.app.strategos.portafolios.persistence.StrategosPortafoliosPersistenceSession;
import com.visiongc.app.strategos.presentaciones.persistence.StrategosCeldasPersistenceSession;
import com.visiongc.app.strategos.presentaciones.persistence.StrategosPaginasPersistenceSession;
import com.visiongc.app.strategos.presentaciones.persistence.StrategosVistasPersistenceSession;
import com.visiongc.app.strategos.problemas.persistence.StrategosAccionesPersistenceSession;
import com.visiongc.app.strategos.problemas.persistence.StrategosClasesProblemasPersistenceSession;
import com.visiongc.app.strategos.problemas.persistence.StrategosProblemasPersistenceSession;
import com.visiongc.app.strategos.problemas.persistence.StrategosSeguimientosPersistenceSession;
import com.visiongc.app.strategos.reportes.persistence.StrategosReportesGraficoPersistenceSession;
import com.visiongc.app.strategos.reportes.persistence.StrategosReportesPersistenceSession;
import com.visiongc.app.strategos.responsables.persistence.StrategosResponsablesPersistenceSession;
import com.visiongc.app.strategos.seriestiempo.persistence.StrategosSeriesTiempoPersistenceSession;
import com.visiongc.app.strategos.unidadesmedida.persistence.StrategosUnidadesPersistenceSession;
import com.visiongc.app.strategos.vistasdatos.persistence.StrategosVistasDatosPersistenceSession;

public abstract interface StrategosPersistenceSessionFactory
{
  public abstract StrategosUnidadesPersistenceSession openUnidadesPersistenceSession();
  
  public abstract StrategosUnidadesPersistenceSession openUnidadesPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosCategoriasPersistenceSession openCategoriasPersistenceSession();
  
  public abstract StrategosCategoriasPersistenceSession openCategoriasPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosCargosPersistenceSession openCargosPersistenceSession();
  
  public abstract StrategosCargosPersistenceSession openCargosPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosResponsablesPersistenceSession openResponsablesPersistenceSession();
  
  public abstract StrategosResponsablesPersistenceSession openResponsablesPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosCuentasPersistenceSession openCuentasPersistenceSession();
  
  public abstract StrategosCuentasPersistenceSession openCuentasPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosCausasPersistenceSession openCausasPersistenceSession();
  
  public abstract StrategosCausasPersistenceSession openCausasPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosEstadosPersistenceSession openEstadosPersistenceSession();
  
  public abstract StrategosEstadosPersistenceSession openEstadosPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosOrganizacionesPersistenceSession openOrganizacionesPersistenceSession();
  
  public abstract StrategosOrganizacionesPersistenceSession openOrganizacionesPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosClasesIndicadoresPersistenceSession openClasesIndicadoresPersistenceSession();
  
  public abstract StrategosClasesIndicadoresPersistenceSession openClasesIndicadoresPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosIndicadoresPersistenceSession openIndicadoresPersistenceSession();
  
  public abstract StrategosIndicadoresPersistenceSession openIndicadoresPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosIndicadorAsignarInventarioPersistenceSession openIndicadorAsignarInventarioPersistenceSession();
  
  public abstract StrategosIndicadorAsignarInventarioPersistenceSession openIndicadorAsignarInventarioPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosMedicionesPersistenceSession openMedicionesPersistenceSession();
  
  public abstract StrategosMedicionesPersistenceSession openMedicionesPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosMedicionesValoracionPersistenceSession openMedicionesValoracionPersistenceSession();
  
  public abstract StrategosMedicionesValoracionPersistenceSession openMedicionesValoracionPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosPlantillasPlanesPersistenceSession openPlantillasPlanesPersistenceSession();
  
  public abstract StrategosPlantillasPlanesPersistenceSession openPlantillasPlanesPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosIniciativasPersistenceSession openIniciativasPersistenceSession();
  
  public abstract StrategosIniciativasPersistenceSession openIniciativasPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosPryProyectosPersistenceSession openPryProyectosPersistenceSession();
  
  public abstract StrategosPryProyectosPersistenceSession openPryProyectosPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosPryActividadesPersistenceSession openPryActividadesPersistenceSession();
  
  public abstract StrategosPryActividadesPersistenceSession openPryActividadesPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosPryVistasPersistenceSession openPryVistasPersistenceSession();
  
  public abstract StrategosPryVistasPersistenceSession openPryVistasPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosVistasPersistenceSession openVistasPersistenceSession();
  
  public abstract StrategosVistasPersistenceSession openVistasPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosPrdProductosPersistenceSession openPrdProductosPersistenceSession();
  
  public abstract StrategosPrdProductosPersistenceSession openPrdProductosPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosProblemasPersistenceSession openProblemasPersistenceSession();
  
  public abstract StrategosProblemasPersistenceSession openProblemasPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosClasesProblemasPersistenceSession openClasesProblemasPersistenceSession();
  
  public abstract StrategosClasesProblemasPersistenceSession openClasesProblemasPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosSeguimientosPersistenceSession openSeguimientosPersistenceSession();
  
  public abstract StrategosSeguimientosPersistenceSession openSeguimientosPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosAccionesPersistenceSession openAccionesPersistenceSession();
  
  public abstract StrategosAccionesPersistenceSession openAccionesPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosPortafoliosPersistenceSession openPortafoliosPersistenceSession();
  
  public abstract StrategosPortafoliosPersistenceSession openPortafoliosPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosExplicacionesPersistenceSession openExplicacionesPersistenceSession();
  
  public abstract StrategosExplicacionesPersistenceSession openExplicacionesPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosForosPersistenceSession openForosPersistenceSession();
  
  public abstract StrategosForosPersistenceSession openForosPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosSeriesTiempoPersistenceSession openSeriesTiempoPersistenceSession();
  
  public abstract StrategosSeriesTiempoPersistenceSession openSeriesTiempoPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosPaginasPersistenceSession openPaginasPersistenceSession();
  
  public abstract StrategosPaginasPersistenceSession openPaginasPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosCeldasPersistenceSession openCeldasPersistenceSession();
  
  public abstract StrategosCeldasPersistenceSession openCeldasPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosPlanesPersistenceSession openPlanesPersistenceSession();
  
  public abstract StrategosPlanesPersistenceSession openPlanesPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosMetasPersistenceSession openMetasPersistenceSession();
  
  public abstract StrategosMetasPersistenceSession openMetasPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosPerspectivasPersistenceSession openPerspectivasPersistenceSession();
  
  public abstract StrategosPerspectivasPersistenceSession openPerspectivasPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosModelosPersistenceSession openModelosPersistenceSession();
  
  public abstract StrategosModelosPersistenceSession openModelosPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosVistasDatosPersistenceSession openVistasDatosPersistenceSession();
  
  public abstract StrategosVistasDatosPersistenceSession openVistasDatosPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosReportesPersistenceSession openReportesPersistenceSession();
  
  public abstract StrategosReportesPersistenceSession openReportesPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosReportesGraficoPersistenceSession openReportesGraficoPersistenceSession();
  
  public abstract StrategosReportesGraficoPersistenceSession openReportesGraficoPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosGraficosPersistenceSession openGraficosPersistenceSession();
  
  public abstract StrategosGraficosPersistenceSession openGraficosPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosIniciativaEstatusPersistenceSession openIniciativaEstatusPersistenceSession();
  
  public abstract StrategosIniciativaEstatusPersistenceSession openIniciativaEstatusPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosCodigoEnlacePersistenceSession openCodigoEnlacePersistenceSession();
  
  public abstract StrategosCodigoEnlacePersistenceSession openCodigoEnlacePersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);

  public abstract StrategosTipoProyectoPersistenceSession openTipoProyectoPersistenceSession();
  
  public abstract StrategosTipoProyectoPersistenceSession openTipoProyectoPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosFaseProyectoPersistenceSession openFaseProyectoPersistenceSession();
  
  public abstract StrategosFaseProyectoPersistenceSession openFaseProyectoPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosCooperantesPersistenceSession openCooperantesPersistenceSession();
  
  public abstract StrategosCooperantesPersistenceSession openCooperantesPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosTiposConvenioPersistenceSession openTiposConvenioPersistenceSession();
  
  public abstract StrategosTiposConvenioPersistenceSession openTiposConvenioPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
  
  public abstract StrategosInstrumentosPersistenceSession openInstrumentosPersistenceSession();
  
  public abstract StrategosInstrumentosPersistenceSession openInstrumentosPersistenceSession(StrategosPersistenceSession paramStrategosPersistenceSession);
}
