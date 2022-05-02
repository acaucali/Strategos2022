import { Component, OnInit } from '@angular/core';
import { STRATEGOS } from 'src/app/config/config';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  regresar(){
    window.location.href=STRATEGOS;
  }

}
