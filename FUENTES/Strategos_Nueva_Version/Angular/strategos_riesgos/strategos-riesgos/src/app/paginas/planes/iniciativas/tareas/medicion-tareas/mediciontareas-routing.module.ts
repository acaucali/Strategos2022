import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MedicionTareasComponent } from './medicion-tareas.component';







const routes: Routes = [
  {path: '', component: MedicionTareasComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MedicionTareasRoutingModule { }
