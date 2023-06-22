import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GestionideasComponent } from './gestionideas.component';



const routes: Routes = [
  {path: '', component: GestionideasComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GestionIdeasRoutingModule { }
