import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PonderacionIdeasComponent } from './ponderacion-ideas.component';




const routes: Routes = [
  {path: '', component: PonderacionIdeasComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PonderacionRoutingModule { }
