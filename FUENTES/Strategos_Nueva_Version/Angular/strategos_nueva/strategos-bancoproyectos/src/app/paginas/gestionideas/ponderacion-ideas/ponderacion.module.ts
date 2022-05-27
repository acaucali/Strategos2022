import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { PonderacionRoutingModule } from './ponderacion-routing.module';
import { PonderacionIdeasComponent } from './ponderacion-ideas.component';






@NgModule({
  declarations: [PonderacionIdeasComponent],
  imports: [
    CommonModule, PonderacionRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule
  ],
  providers: []
})
export class PonderacionModule { }
