import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';
import { EvaluacionRoutingModule } from './evaluacion-routing.module';
import { EvaluacionComponent } from './evaluacion.component';
import { SelectorIdeasComponent } from './selector-ideas/selector-ideas.component';







@NgModule({
  declarations: [EvaluacionComponent, SelectorIdeasComponent],
  imports: [
    CommonModule, EvaluacionRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'en-GB' }
  ]
})
export class EvaluacionModule { }
