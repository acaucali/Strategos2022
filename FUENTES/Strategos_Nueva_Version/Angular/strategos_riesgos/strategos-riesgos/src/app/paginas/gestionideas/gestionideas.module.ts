import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GestionIdeasRoutingModule} from './gestionideas-routing.module';
import { GestionideasComponent } from './gestionideas.component';
import { DetallegestionComponent } from './detallegestion/detallegestion.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DocumentosComponent } from './documentos/documentos.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';
import { ConsultaideaComponent } from './consultaidea/consultaidea.component';






@NgModule({
  declarations: [GestionideasComponent, DetallegestionComponent, DocumentosComponent],
  imports: [
    CommonModule, GestionIdeasRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'en-GB' }
  ]
})
export class GestionIdeasModule { }
