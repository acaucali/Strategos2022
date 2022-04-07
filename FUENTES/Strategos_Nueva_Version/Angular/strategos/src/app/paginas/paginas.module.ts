import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ManinpageComponent } from './maninpage/maninpage.component';
import { InicioComponent } from './inicio/inicio.component';
import { GestionideasComponent } from './gestionideas/gestionideas.component';
import { Pagina404Component } from './pagina404/pagina404.component';
import { ModulosModule } from '../modulos/modulos.module';
import { AppRoutingModule } from '../app-routing.module';



@NgModule({
  declarations: [
    ManinpageComponent,
    InicioComponent,
    GestionideasComponent,
    Pagina404Component
  ],
  imports: [
    CommonModule,
    ModulosModule,
    AppRoutingModule
  ]
})
export class PaginasModule { }
