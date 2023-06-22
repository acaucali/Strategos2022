import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ManinpageComponent } from './maninpage/maninpage.component';
import { Pagina404Component } from './pagina404/pagina404.component';
import { LoginComponent } from './login/login.component';

import { InicioComponent } from './inicio/inicio.component';
import { UsuarioModule } from './usuarios/usuarios.module';
import { GraficoComponent } from './planes/indicadores/grafico/grafico.component';
import { MapaComponent } from './riesgos/factorriesgo/mapa/mapa.component';




const routes: Routes = [
  
  {path: 'banco', 
  component: ManinpageComponent,
  children: [
    { path: 'inicio', component: InicioComponent},
    { path: 'gestionideas', loadChildren: () => import('./gestionideas/gestionideas.module').then(m =>m.GestionIdeasModule)},
    { path: 'criterios', loadChildren: () => import('./configuracion/tablas/criterios/criterios.module').then(m =>m.CriteriosModule)},
    { path: 'ponderacion', loadChildren: () => import('./gestionideas/ponderacion-ideas/ponderacion.module').then(m =>m.PonderacionModule)},
    { path: 'evaluacion/:evaId', loadChildren: () => import('./gestionideas/evaluacion/evaluacion.module').then(m =>m.EvaluacionModule)},
    { path: 'evaluaciondatos/:id', loadChildren: () => import('./gestionideas/evaluacion-datos/evaluaciondatos.module').then(m =>m.EvaluacionDatosModule)},
    { path: 'preproyecto', loadChildren: () => import('./preproyecto/preproyectos.module').then(m =>m.PreProyectoModule)},
    { path: 'proyecto', loadChildren: () => import('./proyectos/proyectos.module').then(m =>m.ProyectoModule)},
    { path: 'planes/:id', loadChildren: () => import('./planes/planes.module').then(m =>m.PlanesModule)},
    { path: 'medicionindicador', loadChildren: () => import('./planes/indicadores/medicion-indicador/detalle-medicion/detalle-medicion.module').then(m =>m.DetalleMedicionModule)},
    { path: 'tareas', loadChildren: () => import('./planes/iniciativas/tareas/tareas.module').then(m =>m.TareasModule)},
    { path: 'mediciontarea', loadChildren: () => import('./planes/iniciativas/tareas/medicion-tareas/mediciontareas.module').then(m =>m.MedicionTareaModule)},
    { path: 'presupuesto', loadChildren: () => import('./planes/iniciativas/tareas/presupuesto/presupuesto.module').then(m =>m.PresupuestoModule)},    
    { path: 'grafico', loadChildren: () => import('./planes/indicadores/grafico/grafico.module').then(m =>m.GraficoModule)},
    { path: 'usuarios', loadChildren: () => import('./usuarios/usuarios.module').then(m =>m.UsuarioModule)},
    { path: 'calificaciones', loadChildren: () => import('./tablas/calificaciones/calificaciones.module').then(m =>m.CalificacionesModule)},
    { path: 'causas', loadChildren: () => import('./tablas/causas/causas.module').then(m =>m.CausasModule)},
    { path: 'controles', loadChildren: () => import('./tablas/controles/controles.module').then(m =>m.ControlesModule)},
    { path: 'efectividad', loadChildren: () => import('./tablas/efectividad/efectividad.module').then(m =>m.EfectividadModule)},
    { path: 'efectos', loadChildren: () => import('./tablas/efectos/efectos.module').then(m =>m.EfectosModule)},
    { path: 'macroprocesos', loadChildren: () => import('./tablas/macroprocesos/macro.module').then(m =>m.MacroModule)},
    { path: 'probabilidad', loadChildren: () => import('./tablas/probabilidad/probabilidad.module').then(m =>m.ProbabilidadModule)},
    { path: 'respuesta', loadChildren: () => import('./tablas/respuesta/respuesta.module').then(m =>m.RespuestaModule)},
    { path: 'tiposimpacto', loadChildren: () => import('./tablas/tipos-impacto/tipoimpacto.module').then(m =>m.TipoImpactoModule)},
    { path: 'tiposriesgo', loadChildren: () => import('./tablas/tipos-riesgo/tiporiesgo.module').then(m =>m.TiposRiesgoModule)},
    { path: 'procesos', loadChildren: () => import('./procesos/procesos.module').then(m =>m.ProcesosModule)},
    { path: 'riesgos', loadChildren: () => import('./riesgos/riesgos.module').then(m =>m.RiesgosModule)},
    { path: 'factores/:id/:procesoid', loadChildren: () => import('./riesgos/factorriesgo/factor.module').then(m =>m.FactorModule)},
    { path: 'mapa/:id/:proid', loadChildren: () => import('./riesgos/factorriesgo/mapa/mapa.module').then(m =>m.MapaModule)},
    { path: 'graficos/:id/:proid', loadChildren: () => import('./riesgos/factorriesgo/grafico/grafico.module').then(m =>m.GraficoFactorModule)},


    { path: '**', component: Pagina404Component},
  ]}
 
];
 
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PaginasRoutingModule { }
