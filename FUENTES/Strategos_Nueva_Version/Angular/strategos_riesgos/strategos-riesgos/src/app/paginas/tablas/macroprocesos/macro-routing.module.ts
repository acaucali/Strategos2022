import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MacroprocesosComponent } from './macroprocesos.component';










const routes: Routes = [
  {path: '', component: MacroprocesosComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MacroProcesosRoutingModule { }
