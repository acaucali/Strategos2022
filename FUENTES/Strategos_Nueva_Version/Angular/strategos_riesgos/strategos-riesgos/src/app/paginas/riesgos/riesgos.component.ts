import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Procesos } from '../configuracion/model/procesos';
import { ProcesosService } from '../configuracion/services/procesos.service';

@Component({
  selector: 'app-riesgos',
  templateUrl: './riesgos.component.html',
  styleUrls: ['./riesgos.component.css']
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

  regresar(){
    
  }

}
