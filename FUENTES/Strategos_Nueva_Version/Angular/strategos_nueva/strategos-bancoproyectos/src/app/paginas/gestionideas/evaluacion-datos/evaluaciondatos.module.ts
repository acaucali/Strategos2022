import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { EvaluacionDatosComponent } from './evaluacion-datos.component';
import { EvaluacionDatosRoutingModule } from './evaluaciondatosrouting.module';


@NgModule({
  declarations: [EvaluacionDatosComponent],
  imports: [
    CommonModule, EvaluacionDatosRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule
  ],
  providers: []
})
export class EvaluacionDatosModule { }
