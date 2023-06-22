import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TiposRiesgoComponent } from './tipos-riesgo.component';











const routes: Routes = [
  {path: '', component: TiposRiesgoComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TiposRiesgoRoutingModule { }
