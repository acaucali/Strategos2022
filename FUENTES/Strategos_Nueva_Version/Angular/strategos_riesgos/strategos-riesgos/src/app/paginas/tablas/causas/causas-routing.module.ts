import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CausasComponent } from './causas.component';






const routes: Routes = [
  {path: '', component: CausasComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CausasRoutingModule { }
