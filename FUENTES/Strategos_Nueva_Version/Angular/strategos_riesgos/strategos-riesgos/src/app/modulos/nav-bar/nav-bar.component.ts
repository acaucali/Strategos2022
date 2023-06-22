import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { STRATEGOS } from 'src/app/config/config';
import { AuthService } from 'src/app/paginas/configuracion/services-util/auth.service';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  constructor(public authService:AuthService, private router: Router) { }

  ngOnInit(): void {
  }

  regresar(){
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
