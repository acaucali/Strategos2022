import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { PresupuestoComponent } from './presupuesto.component';
import { PresupuestoRoutingModule } from './presupuestorouting.module';





@NgModule({
  declarations: [PresupuestoComponent],
  imports: [
    CommonModule, PresupuestoRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule
  ],
  providers: []
})
export class PresupuestoModule { }
