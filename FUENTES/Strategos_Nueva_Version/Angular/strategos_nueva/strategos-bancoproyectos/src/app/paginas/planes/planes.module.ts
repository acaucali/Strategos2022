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

import { PlanesComponent } from './planes.component';
import { PlanesRoutingModule } from './planes-routing.module';
import { IndicadoresComponent } from './indicadores/indicadores.component';
import { IniciativasComponent } from './iniciativas/iniciativas.component';
import { PlanComponent } from './plan/plan.component';
import { TreeViewModule } from '@progress/kendo-angular-treeview';
import { DetallePerspectivaComponent } from './plan/detalle-perspectiva/detalle-perspectiva.component';
import { DetalleIniciativaComponent } from './iniciativas/detalle-iniciativa/detalle-iniciativa.component';
import { DetalleIndicadorComponent } from './indicadores/detalle-indicador/detalle-indicador.component';

import { MedicionIndicadorComponent } from './indicadores/medicion-indicador/medicion-indicador.component';

import { DetalleTareaComponent } from './iniciativas/tareas/detalle-tarea/detalle-tarea.component';
import { MedicionTareasComponent } from './iniciativas/tareas/medicion-tareas/medicion-tareas.component';
import { GraficoComponent } from './indicadores/grafico/grafico.component';




@NgModule({
  declarations: [PlanesComponent, PlanComponent, IniciativasComponent, IndicadoresComponent, DetallePerspectivaComponent, DetalleIniciativaComponent, DetalleIndicadorComponent, MedicionIndicadorComponent],
  imports: [
    CommonModule, PlanesRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule,  NgMultiSelectDropDownModule.forRoot(),
    MatSelectModule, DropDownsModule, InputsModule, TreeViewModule
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'en-GB' }
  ]
})
export class PlanesModule { }
