import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EfectosComponent } from './efectos.component';









const routes: Routes = [
  {path: '', component: EfectosComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EfectosRoutingModule { }
