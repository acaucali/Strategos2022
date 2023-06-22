import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { NgxPaginationModule } from 'ngx-pagination';

import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import {MatSelectModule} from '@angular/material/select';
import { InputsModule } from '@progress/kendo-angular-inputs';
import { DropDownsModule } from '@progress/kendo-angular-dropdowns';

import { TreeViewModule } from '@progress/kendo-angular-treeview';

import { DetalleEjerciciosComponent } from './ejercicios/detalle-ejercicios/detalle-ejercicios.component';
import { RiesgosRoutingModule } from './riesgos-routing.module';
import { RiesgoComponent } from './riesgo/riesgo.component';
import { EjerciciosComponent } from './ejercicios/ejercicios.component';

import { RiesgosComponent } from './riesgos.component';









@NgModule({
  declarations: [RiesgosComponent,  RiesgoComponent, EjerciciosComponent, DetalleEjerciciosComponent],
  imports: [
    CommonModule, RiesgosRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule,  NgMultiSelectDropDownModule.forRoot(),
    MatSelectModule, DropDownsModule, InputsModule, TreeViewModule
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'en-GB' }
  ]
})
export class RiesgosModule { }
