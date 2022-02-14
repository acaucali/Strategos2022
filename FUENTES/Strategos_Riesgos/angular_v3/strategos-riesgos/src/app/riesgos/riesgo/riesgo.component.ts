import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Ejercicios } from '../ejercicios/ejercicios';
import { FactorRiesgo } from '../factorriesgo/factor';
import { EjerciciosService } from '../ejercicios/ejercicios.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ProcesosService } from 'src/app/procesos/procesos.service';
import { FactorService } from '../factorriesgo/factor.service';

@Component({
  selector: 'app-riesgo',
  templateUrl: './riesgo.component.html',
  styleUrls: ['./riesgo.component.css']
})
export class RiesgoComponent implements OnChanges {

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
