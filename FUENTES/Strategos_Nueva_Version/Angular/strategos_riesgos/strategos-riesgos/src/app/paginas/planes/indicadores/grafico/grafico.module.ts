import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { GraficoComponent } from './grafico.component';
import { GraficoRoutingModule } from './graficorouting.module';
import { ConfigGraficoComponent } from './config-grafico/config-grafico.component';



@NgModule({
  declarations: [GraficoComponent],
  imports: [
    CommonModule, GraficoRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule
  ],
  providers: []
})
export class GraficoModule { }
