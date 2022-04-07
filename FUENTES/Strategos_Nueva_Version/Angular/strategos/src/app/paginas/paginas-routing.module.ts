import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ManinpageComponent } from './maninpage/maninpage.component';
import { Pagina404Component } from './pagina404/pagina404.component';




const routes: Routes = [
  
  {path: '', 
  component: ManinpageComponent,
  children: [
    { path: '', loadChildren: () => import('./inicio/inicio.module').then(m =>m.InicioModule)},
    //{ path: 'usuarios', loadChildren: () => import('./usuarios/usuarios.module').then(m =>m.UsuariosModule)},
    { path: '**', component: Pagina404Component},
  ]}
];
 
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PaginasRoutingModule { }
