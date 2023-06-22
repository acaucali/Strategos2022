import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PreproyectoComponent } from './preproyecto.component';




const routes: Routes = [
  {path: '', component: PreproyectoComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PreproyectoRoutingModule { }
