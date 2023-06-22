import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FactorriesgoComponent } from './factorriesgo.component';








const routes: Routes = [
  {path: '', component: FactorriesgoComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FactorRoutingModule { }
