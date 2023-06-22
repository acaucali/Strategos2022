import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GraficoFactorComponent } from './grafico.component';








const routes: Routes = [
  {path: '', component: GraficoFactorComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GraficoFactorRoutingModule { }
