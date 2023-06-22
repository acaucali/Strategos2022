import { Component, OnInit } from '@angular/core';
import { OrganizacionStrategos } from '../configuracion/model-util/organizacionstrategos';
import { OrganizacionStrategosService } from '../configuracion/services-util/organizacionstrategos.service';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioComponent implements OnInit {

  public organizaciones: OrganizacionStrategos[];
  public id: number;

  constructor(private organizacionesService: OrganizacionStrategosService) { }

  ngOnInit(): void {

    this.organizacionesService.getOrganizacionesList().subscribe(response =>{this.organizaciones = response}); // obtiene las organizaciones

  }

  seleccionarProceso(){
    if(this.id != undefined){
      localStorage.setItem('organizacion', this.id.toString());
    }
  }

}
