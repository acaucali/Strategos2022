import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { GraficoFactorComponent } from './grafico.component';
import { GraficoFactorRoutingModule } from './graficorouting.module';





@NgModule({
  declarations: [GraficoFactorComponent],
  imports: [
    CommonModule, GraficoFactorRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule
  ],
  providers: []
})
export class GraficoFactorModule { }
