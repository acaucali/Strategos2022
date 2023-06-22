import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RespuestaComponent } from './respuesta.component';










const routes: Routes = [
  {path: '', component: RespuestaComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RespuestaRoutingModule { }
