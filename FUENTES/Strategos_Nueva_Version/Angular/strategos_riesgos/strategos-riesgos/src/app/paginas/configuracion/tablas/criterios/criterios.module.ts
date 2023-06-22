import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';



import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { CriteriosComponent } from './criterios.component';
import { DetalleCriterioComponent } from './detalle-criterio/detalle-criterio.component';
import { CriteriosRoutingModule } from './criterios-routing.module';




@NgModule({
  declarations: [CriteriosComponent, DetalleCriterioComponent],
  imports: [
    CommonModule, CriteriosRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule
  ],
  providers: []
})
export class CriteriosModule { }
