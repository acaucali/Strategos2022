import { Component, OnInit } from '@angular/core';
import { Procesos } from '../procesos/procesos';
import { ProcesosService } from '../procesos/procesos.service';
import { ActivatedRoute } from '@angular/router';
import { EjerciciosComponent } from './ejercicios/ejercicios.component';

@Component({
  selector: 'app-riesgos',
  templateUrl: './riesgos.component.html',
})
export class RiesgosComponent implements OnInit {

  public procesos: Procesos[];
  public procesoId: number;
  public id: number;

  constructor(private procesosService: ProcesosService,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.procesosService.getProcesos().subscribe(response => this.procesos= response);
  }

  seleccionarProceso(){
    if(this.id != undefined){
      
      this.procesoId = this.id;
    }else{
     
      this.procesoId =0;
    }

  }
}
