import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { TareasComponent } from './tareas.component';
import { TareasRoutingModule } from './tareasrouting.module';
import { DetalleTareaComponent } from './detalle-tarea/detalle-tarea.component';
import { ConfigTareaComponent } from './medicion-tareas/config-tarea/config-tarea.component';




@NgModule({
  declarations: [TareasComponent, DetalleTareaComponent, ConfigTareaComponent],
  imports: [
    CommonModule, TareasRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule
  ],
  providers: []
})
export class TareasModule { }
