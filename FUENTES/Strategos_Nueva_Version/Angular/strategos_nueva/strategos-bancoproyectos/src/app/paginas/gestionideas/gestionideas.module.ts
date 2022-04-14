import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GestionIdeasRoutingModule} from './gestionideas-routing.module';
import { GestionideasComponent } from './gestionideas.component';



@NgModule({
  declarations: [GestionideasComponent],
  imports: [
    CommonModule, GestionIdeasRoutingModule
  ]
})
export class GestionIdeasModule { }
