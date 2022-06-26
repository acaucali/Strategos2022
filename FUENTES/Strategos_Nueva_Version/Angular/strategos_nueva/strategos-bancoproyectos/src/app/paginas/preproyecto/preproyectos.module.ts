import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PreproyectoComponent } from './preproyecto.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { NgxPaginationModule } from 'ngx-pagination';
import { PreproyectoRoutingModule } from './preproyectos-routing.module';
import { DetallepreproyectoComponent } from './detallepreproyecto/detallepreproyecto.component';







@NgModule({
  declarations: [PreproyectoComponent, DetallepreproyectoComponent],
  imports: [
    CommonModule, PreproyectoRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'en-GB' }
  ]
})
export class PreProyectoModule { }
