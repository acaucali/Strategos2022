import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { DetalleMedicionComponent } from './detalle-medicion.component';
import { DetalleMedicionRoutingModule } from './detallemedicionrouting.module';



@NgModule({
  declarations: [DetalleMedicionComponent],
  imports: [
    CommonModule, DetalleMedicionRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule
  ],
  providers: []
})
export class DetalleMedicionModule { }
