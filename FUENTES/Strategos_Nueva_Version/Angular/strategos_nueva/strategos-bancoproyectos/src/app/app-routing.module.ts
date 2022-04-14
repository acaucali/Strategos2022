import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PaginasRoutingModule } from './paginas/paginas-routing.module';

const routes: Routes = [
  {path: '', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes), PaginasRoutingModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
