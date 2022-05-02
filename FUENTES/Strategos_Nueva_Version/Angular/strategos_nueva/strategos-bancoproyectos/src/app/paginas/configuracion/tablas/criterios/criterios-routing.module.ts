import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CriteriosComponent } from './criterios.component';




const routes: Routes = [
  {path: '', component: CriteriosComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CriteriosRoutingModule { }
