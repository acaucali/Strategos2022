import { Component, OnInit } from '@angular/core';
import { URL_STRATEGOS } from '../config/config';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  regresar(){
    window.location.href=URL_STRATEGOS;
  }
}
