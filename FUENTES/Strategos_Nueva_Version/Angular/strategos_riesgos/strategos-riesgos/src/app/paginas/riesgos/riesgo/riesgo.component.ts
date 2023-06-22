import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Ejercicios } from '../../configuracion/model/ejercicios';
import { FactorRiesgo } from '../../configuracion/model/factor';
import { EjerciciosService } from '../../configuracion/services/ejercicios.service';
import { FactorService } from '../../configuracion/services/factor.service';
import { ProcesosService } from '../../configuracion/services/procesos.service';

@Component({
  selector: 'app-riesgo',
  templateUrl: './riesgo.component.html',
  styleUrls: ['./riesgo.component.css']
})
export class RiesgoComponent implements OnInit {

  pageRie: number =1;
  
  ejercicios: Ejercicios[];
  public factores: FactorRiesgo[];

  paginador: any;

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;

  @Input() id: number;

  constructor(private ejercicioService: EjerciciosService, private router: Router, private activatedRoute: ActivatedRoute,
    private procesosService: ProcesosService, private factorService: FactorService) { }

  ngOnInit() {
    this.factores = null;
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(changes.id){
      if(this.id>0 || this.id != undefined){
        this.cargarFactores();
      }else{
        this.factores=null;
      }
    }
  }

  cargarFactores(){
    this.factores = null;
    this.factorService.getFactoresProceso(this.id).subscribe(response =>{
      this.factores = response;
    });
  }

}
