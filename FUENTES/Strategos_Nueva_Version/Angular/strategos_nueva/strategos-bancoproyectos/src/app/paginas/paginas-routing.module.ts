import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ManinpageComponent } from './maninpage/maninpage.component';
import { Pagina404Component } from './pagina404/pagina404.component';




const routes: Routes = [
  
  {path: '', 
  component: ManinpageComponent,
  children: [
    { path: '', loadChildren: () => import('./inicio/inicio.module').then(m =>m.InicioModule)},
    { path: 'gestionideas', loadChildren: () => import('./gestionideas/gestionideas.module').then(m =>m.GestionIdeasModule)},
    { path: 'criterios', loadChildren: () => import('./configuracion/tablas/criterios/criterios.module').then(m =>m.CriteriosModule)},
    { path: 'inicio', loadChildren: () => import('./inicio/inicio.module').then(m =>m.InicioModule)},
    { path: '**', component: Pagina404Component},
  ]}
];
 
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PaginasRoutingModule { }
