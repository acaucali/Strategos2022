import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MedicionTareasComponent } from './medicion-tareas.component';
import { MedicionTareasRoutingModule } from './mediciontareas-routing.module';
import { ConfigTareaComponent } from './config-tarea/config-tarea.component';




@NgModule({
  declarations: [MedicionTareasComponent],
  imports: [
    CommonModule, MedicionTareasRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule
  ],
  providers: []
})
export class MedicionTareaModule { }
