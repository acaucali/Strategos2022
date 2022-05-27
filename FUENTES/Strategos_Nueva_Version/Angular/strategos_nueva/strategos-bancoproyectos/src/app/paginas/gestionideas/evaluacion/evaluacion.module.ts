import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { EvaluacionRoutingModule } from './evaluacion-routing.module';
import { EvaluacionComponent } from './evaluacion.component';
import { SelectorIdeasComponent } from './selector-ideas/selector-ideas.component';







@NgModule({
  declarations: [EvaluacionComponent, SelectorIdeasComponent],
  imports: [
    CommonModule, EvaluacionRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule
  ],
  providers: []
})
export class EvaluacionModule { }
