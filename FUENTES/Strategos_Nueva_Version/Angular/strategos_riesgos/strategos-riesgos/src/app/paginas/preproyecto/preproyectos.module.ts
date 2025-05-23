import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PreproyectoComponent } from './preproyecto.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { NgxPaginationModule } from 'ngx-pagination';
import { PreproyectoRoutingModule } from './preproyectos-routing.module';
import { DetallepreproyectoComponent } from './detallepreproyecto/detallepreproyecto.component';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import {MatSelectModule} from '@angular/material/select';
import { InputsModule } from '@progress/kendo-angular-inputs';
import { DropDownsModule } from '@progress/kendo-angular-dropdowns';
import { LabelModule } from '@progress/kendo-angular-label';








@NgModule({
  declarations: [PreproyectoComponent, DetallepreproyectoComponent],
  imports: [
    CommonModule, PreproyectoRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule,  NgMultiSelectDropDownModule.forRoot(),
    MatSelectModule, DropDownsModule, InputsModule
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'en-GB' }
  ]
})
export class PreProyectoModule { }
