import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManinpageComponent } from './maninpage/maninpage.component';
import { Pagina404Component } from './pagina404/pagina404.component';
import { ModulosModule } from '../modulos/modulos.module';
import { AppRoutingModule } from '../app-routing.module';
import { ConfiguracionComponent } from './configuracion/configuracion.component';


@NgModule({
  declarations: [
    ManinpageComponent,
    Pagina404Component,
    ConfiguracionComponent
  ],
  imports: [
    CommonModule,
    ModulosModule,
    AppRoutingModule,
    

  ]
})
export class PaginasModule { }
