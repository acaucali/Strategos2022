import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GestionIdeasRoutingModule} from './gestionideas-routing.module';
import { GestionideasComponent } from './gestionideas.component';
import { DetallegestionComponent } from './detallegestion/detallegestion.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DocumentosComponent } from './documentos/documentos.component';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';






@NgModule({
  declarations: [GestionideasComponent, DetallegestionComponent, DocumentosComponent],
  imports: [
    CommonModule, GestionIdeasRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule, MatDatepickerModule, MatNativeDateModule
  ],
  providers: []
})
export class GestionIdeasModule { }
