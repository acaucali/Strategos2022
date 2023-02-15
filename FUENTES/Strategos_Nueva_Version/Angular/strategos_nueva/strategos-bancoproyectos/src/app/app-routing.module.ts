import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './paginas/login/login.component';
import { PaginasRoutingModule } from './paginas/paginas-routing.module';

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes), PaginasRoutingModule],
  exports: [RouterModule]
})
export class AppRoutingModule { }
