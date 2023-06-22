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


import { ControlesComponent } from './controles.component';
import { DetalleControlesComponent } from './detalle-controles/detalle-controles.component';
import { ControlesRoutingModule } from './controles-routing.module';

@NgModule({
  declarations: [ControlesComponent, DetalleControlesComponent],
  imports: [
    CommonModule, ControlesRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule,  NgMultiSelectDropDownModule.forRoot(),
    MatSelectModule, DropDownsModule, InputsModule
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'en-GB' }
  ]
})
export class ControlesModule { }
