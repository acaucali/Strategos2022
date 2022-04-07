import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './footer/footer.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { SideBarComponent } from './side-bar/side-bar.component';
import { RouterModule } from '@angular/router';



@NgModule({
  declarations: [
    FooterComponent,
    NavBarComponent,
    SideBarComponent
  ],
  exports:[
    FooterComponent,
    NavBarComponent,
    SideBarComponent  
  ],
  imports: [
    CommonModule,
    RouterModule
  ]
})
export class ModulosModule { }
