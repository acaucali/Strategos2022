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
import { ProcesosComponent } from './procesos.component';
import { ProcesosRoutingModule } from './procesos-routing.module';
import { CaracterizacionComponent } from './caracterizacion/caracterizacion.component';
import { DetalleCaracterizacionComponent } from './caracterizacion/detalle-caracterizacion/detalle-caracterizacion.component';
import { FormComponent } from './form/form.component';
import { DetalleProductoComponent } from './producto/detalle-producto/detalle-producto.component';
import { ProductoComponent } from './producto/producto.component';









@NgModule({
  declarations: [ProcesosComponent, CaracterizacionComponent, DetalleCaracterizacionComponent, ProductoComponent, DetalleProductoComponent, FormComponent],
  imports: [
    CommonModule, ProcesosRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule,  NgMultiSelectDropDownModule.forRoot(),
    MatSelectModule, DropDownsModule, InputsModule, TreeViewModule
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'en-GB' }
  ]
})
export class ProcesosModule { }