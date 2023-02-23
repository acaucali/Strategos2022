import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManinpageComponent } from './maninpage/maninpage.component';
import { Pagina404Component } from './pagina404/pagina404.component';
import { ModulosModule } from '../modulos/modulos.module';
import { AppRoutingModule } from '../app-routing.module';
import { ConfiguracionComponent } from './configuracion/configuracion.component';
import { PreproyectoComponent } from './preproyecto/preproyecto.component';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { PlanesComponent } from './planes/planes.component';
import { IndicadoresComponent } from './planes/indicadores/indicadores.component';
import { IniciativasComponent } from './planes/iniciativas/iniciativas.component';
import { PlanComponent } from './planes/plan/plan.component';
import { ProyectosComponent } from './proyectos/proyectos.component';
import { LoginComponent } from './login/login.component';
import { InicioComponent } from './inicio/inicio.component';
import { FormsModule } from '@angular/forms';
import { UsuariosComponent } from './usuarios/usuarios.component';
import { DetalleUsuariosComponent } from './usuarios/detalle-usuarios/detalle-usuarios.component';


@NgModule({
  declarations: [
    ManinpageComponent,
    Pagina404Component,
    ConfiguracionComponent,
    LoginComponent,
    InicioComponent
  ],
  imports: [
    CommonModule,
    ModulosModule,
    AppRoutingModule, FormsModule
   

  ]
})
export class PaginasModule { }
