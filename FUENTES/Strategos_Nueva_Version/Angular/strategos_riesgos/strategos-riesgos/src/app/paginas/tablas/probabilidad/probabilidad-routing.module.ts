import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProbabilidadComponent } from './probabilidad.component';










const routes: Routes = [
  {path: '', component: ProbabilidadComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProbabilidadRoutingModule { }
