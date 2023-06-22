import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TiposImpactoComponent } from './tipos-impacto.component';











const routes: Routes = [
  {path: '', component: TiposImpactoComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TipoImpactoRoutingModule { }
