import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GestionIdeasRoutingModule} from './gestionideas-routing.module';
import { GestionideasComponent } from './gestionideas.component';
import { DetallegestionComponent } from './detallegestion/detallegestion.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [GestionideasComponent, DetallegestionComponent],
  imports: [
    CommonModule, GestionIdeasRoutingModule, NgxPaginationModule,  FormsModule, ReactiveFormsModule
  ],
  providers: []
})
export class GestionIdeasModule { }
