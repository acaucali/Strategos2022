import { Component, OnInit, Input } from '@angular/core';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';

import { Label, Color } from 'ng2-charts';
import { ModalGraficoService } from './modalgrafico.service';
import { ProbabilidadService } from 'src/app/tablas/probabilidad/probabilidad.service';
import { TipoimpactoService } from 'src/app/tablas/tipos-impacto/tipoimpacto.service';
import { Probabilidad } from 'src/app/tablas/probabilidad/probabilidad';
import { TipoImpacto } from 'src/app/tablas/tipos-impacto/tipoimpacto';
import { FactorRiesgo } from '../factor';
import { FactorService } from '../factor.service';
import { EjerciciosService } from '../../ejercicios/ejercicios.service';
import { CausaFactorService } from '../causas/causa.factor.service';

@Component({
  selector: 'app-grafico',
  templateUrl: './grafico.component.html',
  styleUrls: ['./grafico.component.css']
})
export class GraficoComponent implements OnInit {

  public causas: string[];
  
  @Input() ejeid: string;

  constructor(public modalservice: ModalGraficoService, private causaService: CausaFactorService,
     private factorService: FactorService, private ejercicioService: EjerciciosService) { }

  public barChartOptions = {
    scaleShowVerticalLines: false,
    responsive: true,
    scales: {
      yAxes: [
        {
          ticks: {
            beginAtZero: true
          }
        }
      ]
    }
  };

  public barChartLabels = [];

  public barChartType = 'bar';
  
  public barChartLegend = true;
  
  private datos = [];

  public numero: number =0;

  public barChartData = [{data: this.datos, label: 'factores de riesgo'} ];

  ngOnInit() {
    this.cargarDatosGrafico();
  }

  cerrarModal(){
    this.modalservice.cerrarModal();
  }

  cargarDatosGrafico(){

    this.causas = [];
    this.barChartLabels = [];
    
    this.causaService.getCausasGrafico().subscribe(response =>{
      this.causas = response;
      if(this.causas.length >0){
        this.causas.forEach(cau =>{
          this.barChartLabels.push(cau);
        });

        this.factorService.getNumeroFactoresCausas(parseInt(this.ejeid)).subscribe(response => this.datos = response);
        this.barChartData= [ {data: this.datos, label: 'numero factores de riesgo'} ];

        console.log("numero: "+this.datos);
      }
    });
  }

  public barChartColors: Color[] = [
    { backgroundColor: "#5db4ee" },
    
  ]

}
