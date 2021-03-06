import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { EvaluacionDatosComponent } from './evaluacion-datos.component';
import { EvaluacionDatosRoutingModule } from './evaluaciondatosrouting.module';
import { ConsultaideaComponent } from '../consultaidea/consultaidea.component';
import { GestionideasComponent } from '../gestionideas.component';


@NgModule({
  declarations: [EvaluacionDatosComponent, ConsultaideaComponent],
  imports: [
    CommonModule, EvaluacionDatosRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule
  ],
  providers: []
})
export class EvaluacionDatosModule { }
