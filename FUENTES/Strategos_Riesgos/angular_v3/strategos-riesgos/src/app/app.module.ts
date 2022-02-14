import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { CalificacionesComponent } from './tablas/calificaciones/calificaciones.component';
import { CausasComponent } from './tablas/causas/causas.component';
import { ControlesComponent } from './tablas/controles/controles.component';
import { ProbabilidadComponent } from './tablas/probabilidad/probabilidad.component';
import { TiposImpactoComponent } from './tablas/tipos-impacto/tipos-impacto.component';
import { TiposRiesgoComponent } from './tablas/tipos-riesgo/tipos-riesgo.component';
import { RespuestaComponent } from './tablas/respuesta/respuesta.component';
import { RouterModule, Routes} from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { CalificacionesService } from './tablas/calificaciones/calificaciones.service';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DetalleCalificacion } from './tablas/calificaciones/detalle/detalle.calificacion';
import { DetalleCausa } from './tablas/causas/detalle/detalle.causa';
import { CausasService } from './tablas/causas/causas.service';
import { ControlesService } from './tablas/controles/controles.service';
import { DetalleControl } from './tablas/controles/detalle/detalle.control';
import { DetalleProbabilidad } from './tablas/probabilidad/detalle/detalleprobabilidad.control';
import { ProbabilidadService } from './tablas/probabilidad/probabilidad.service';
import { DetalleRespuesta } from './tablas/respuesta/detalle/detallerespuesta.control';
import { RespuestaService } from './tablas/respuesta/respuesta.service';
import { DetalleImpacto } from './tablas/tipos-impacto/detalle/detalleimpacto.control';
import { TipoimpactoService } from './tablas/tipos-impacto/tipoimpacto.service';
import { TiporiesgoService } from './tablas/tipos-riesgo/tiporiesgo.service';
import { DetalleRiesgo } from './tablas/tipos-riesgo/detalle/detalleriesgo.control';
import { ProcesosComponent } from './procesos/procesos.component';
import { ProductoComponent } from './procesos/producto/producto.component';
import { CaracterizacionComponent } from './procesos/caracterizacion/caracterizacion.component';
import { FormComponent } from './procesos/form/form.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { RiesgosComponent } from './riesgos/riesgos.component';
import { EjerciciosComponent } from './riesgos/ejercicios/ejercicios.component';
import { RiesgoComponent } from './riesgos/riesgo/riesgo.component';
import { DetalleEjercicio } from './riesgos/ejercicios/detalle/detalleejercicio.control';
import { ProcesosService } from './procesos/procesos.service';
import { TreeViewModule } from '@progress/kendo-angular-treeview';
import {MatTreeModule, MatIconModule, MatButtonModule, MatPaginatorModule, MatNativeDateModule} from '@angular/material';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { DetalleProducto } from './procesos/producto/modal/detalle.producto';
import { ProductoService } from './procesos/producto/producto.service';
import { DetalleCaracterizacion } from './procesos/caracterizacion/modal/detalle.caracterizacion';
import { CaracterizacionService } from './procesos/caracterizacion/caracterizacion.service';
import { MacroprocesosComponent } from './tablas/macroprocesos/macroprocesos.component';
import { DetalleMacro } from './tablas/macroprocesos/detalle/detalle.marcoproceso';
import { MacroprocesosService } from './tablas/macroprocesos/macroprocesos.service';
import { EjerciciosService } from './riesgos/ejercicios/ejercicios.service';

import { FactorriesgoComponent } from './riesgos/factorriesgo/factorriesgo.component';
import { DetalleFactor } from './riesgos/factorriesgo/detalle/detallefactor.control';
import { FactorService } from './riesgos/factorriesgo/factor.service';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import { FormFactorComponent } from './riesgos/factorriesgo/detalle/form/form.factor.component';
import { CausasFactorComponent } from './riesgos/factorriesgo/causas/causasFactor.component';
import { EfectosFactorComponent } from './riesgos/factorriesgo/efectos/efectosFactor.component';
import { ControlesFactorComponent } from './riesgos/factorriesgo/controles/controlesFactor.component';
import { DetalleCausaFactor } from './riesgos/factorriesgo/causas/detalle/detalle.causa.factor';
import { CausaFactorService } from './riesgos/factorriesgo/causas/causa.factor.service';
import { DropDownsModule } from '@progress/kendo-angular-dropdowns';
import { MDBBootstrapModule} from 'angular-bootstrap-md';
import { DetalleEfectoFactor } from './riesgos/factorriesgo/efectos/detalle/detalle.efecto.factor';
import { efectoFactorService } from './riesgos/factorriesgo/efectos/efector.service';
import { EfectividadComponent } from './tablas/efectividad/efectividad.component';
import { DetalleEfectividad } from './tablas/efectividad/detalle/detalle.efectividad';
import { DetalleControlFactor } from './riesgos/factorriesgo/controles/detalle/detalle.control.factor';
import { ControlFactorService } from './riesgos/factorriesgo/controles/controles.factor.service';

import {MatTableModule} from '@angular/material/table';
import {NgxPaginationModule} from 'ngx-pagination';
import { MapaComponent } from './riesgos/factorriesgo/mapa/mapa.component';
import { DetalleEfecto } from './tablas/efectos/detalle/detalle.efecto';
import { EfectosComponent } from './tablas/efectos/efectos.component';
import { EfectosService } from './tablas/efectos/efectos.service';
import { DetalleComponent } from './riesgos/riesgo/detalle/detalle.component';
import { GraficoComponent } from './riesgos/factorriesgo/grafico/grafico.component';
import { ChartsModule } from 'ng2-charts';


const routes: Routes=[
  {path: '', redirectTo: '/procesos', pathMatch: 'full'},
  {path: 'calificaciones', component: CalificacionesComponent},
  {path: 'causas', component: CausasComponent},
  {path: 'controles', component: ControlesComponent},
  {path: 'probabilidad', component: ProbabilidadComponent},
  {path: 'respuesta', component: RespuestaComponent},
  {path: 'tiposimpacto', component: TiposImpactoComponent},
  {path: 'tiporiesgo', component: TiposRiesgoComponent},
  {path: 'ejercicios', component: EjerciciosComponent},
  {path: 'controlesfac', component: ControlesFactorComponent},
  {path: 'macros', component: MacroprocesosComponent},
  {path: 'procesos', component: ProcesosComponent},
  {path: 'producto', component: ProductoComponent},
  {path: 'riesgos', component: RiesgosComponent},
  {path: 'efectos', component: EfectosComponent},
  {path: 'factores/:id/:procesoid', component: FactorriesgoComponent},
  {path: 'efectividad', component: EfectividadComponent},
  {path: 'mapa/:id', component: MapaComponent},
  {path: 'grafico/:id', component: GraficoComponent},
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    CalificacionesComponent,
    CausasComponent,
    ControlesComponent,
    ProbabilidadComponent,
    TiposImpactoComponent,
    TiposRiesgoComponent,
    RespuestaComponent,
    DetalleCalificacion,
    DetalleCausa,
    DetalleControl,
    DetalleProbabilidad,
    DetalleRespuesta,
    DetalleImpacto,
    DetalleProducto,
    DetalleRiesgo,
    DetalleFactor,
    DetalleCaracterizacion,
    DetalleComponent,
    MapaComponent,
    ProcesosComponent, 
    ProductoComponent, CaracterizacionComponent, 
    FormComponent, RiesgosComponent, 
    EjerciciosComponent, RiesgoComponent,  
    DetalleEjercicio, MacroprocesosComponent,
    DetalleMacro,  FactorriesgoComponent, EfectosFactorComponent, 
    FormFactorComponent, CausasFactorComponent, ControlesFactorComponent,
    DetalleCausaFactor, DetalleEfectoFactor, EfectividadComponent,
    DetalleEfectividad, DetalleControlFactor, DetalleEfecto, EfectosComponent, GraficoComponent

  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes),
    MatTreeModule,
    NgbModule,
    TreeViewModule,
    MatIconModule,
    MatButtonModule,
    MatNativeDateModule,
    BrowserAnimationsModule, 
    MatDatepickerModule, 
    MatTableModule, MatPaginatorModule,
    MatAutocompleteModule,
    DropDownsModule,
    MDBBootstrapModule.forRoot(),
    NgxPaginationModule, ChartsModule
  ],
  providers: [CalificacionesService, CausasService, ControlesService, ProbabilidadService, RespuestaService,
    TipoimpactoService, TiporiesgoService, ProcesosService, ProductoService, CaracterizacionService, MacroprocesosService, EjerciciosService,
    FactorService, CausaFactorService, efectoFactorService, ControlFactorService, EfectosService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
