import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EvaluacionDatosComponent } from './evaluacion-datos.component';





const routes: Routes = [
  {path: '', component: EvaluacionDatosComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EvaluacionDatosRoutingModule { }
