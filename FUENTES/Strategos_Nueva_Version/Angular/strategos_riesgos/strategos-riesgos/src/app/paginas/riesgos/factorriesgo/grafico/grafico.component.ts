import { Component, Input, OnInit } from '@angular/core';

import { CausaFactorService } from 'src/app/paginas/configuracion/services/causa.factor.service';
import { EjerciciosService } from 'src/app/paginas/configuracion/services/ejercicios.service';
import { FactorService } from 'src/app/paginas/configuracion/services/factor.service';

import Chart from 'chart.js/auto';
import { ActivatedRoute } from '@angular/router';
import { Ejercicios } from 'src/app/paginas/configuracion/model/ejercicios';
import { Procesos } from 'src/app/paginas/configuracion/model/procesos';
import { ProcesosService } from 'src/app/paginas/configuracion/services/procesos.service';

@Component({
  selector: 'app-grafico-factor',
  templateUrl: './grafico.component.html',
  styleUrls: ['./grafico.component.css']
})
export class GraficoFactorComponent implements OnInit {

  public chart: any;
  public canvas: any;
  public ctx: any;
  public causas: string[];
  public datos: number[];
  public ejercicio: Ejercicios = new Ejercicios();
  public proceso: Procesos = new Procesos();
   
  @Input() ejeid: string;
  @Input() proid: string; 

  constructor(private causaService: CausaFactorService, private activatedRoute: ActivatedRoute, private procesosService: ProcesosService, 
     private factorService: FactorService, private ejercicioService: EjerciciosService) {}

  ngOnInit() {

    this.activatedRoute.params.subscribe(params =>{
      let id = params['id'];
      let procid = params['proid'];
      if(id){
        this.ejercicioService.getEjercicio(id).subscribe((ejercicio) =>{this.ejercicio= ejercicio;});
      }
      if(procid){
        this.procesosService.getProceso(procid).subscribe((proceso) => this.proceso = proceso);
      }

    });
    this.createChart();
  
  }

  createChart() {

    this.chart = new Chart('MyChart2', {
      type: 'bar',//this denotes tha type of chart

      data: {
        // values on X-Axis

        labels: JSON.parse(localStorage.getItem('causas')),
        datasets: [
          {
            label: '',
            data: JSON.parse(localStorage.getItem('datos')),
            backgroundColor: [
              'rgb(255, 99, 132)',
              'rgb(75, 192, 192)',
              'rgb(255, 205, 86)',
              'rgb(201, 203, 207)',
              'rgb(54, 162, 235)',
              'rgb(64, 214, 81)'
            ],
            borderColor: 'rgb(75, 192, 192)',
          },
        ],
      },
      options: {
        aspectRatio: 2.5,
        indexAxis: 'y',
        responsive: true,
              plugins: {  
                // plugin: [ChartDataLabels] , 
                legend: {
                  position: 'top',
                },
                title: {
                  display: true,
                  text: "Riesgos Por Causa",
                  font: {
                    size: 20
                  }
                },
              },
              scales: {
                x:  {
                  display: true,
                  
                  ticks: {},
                  title: {
                    display: true,
                    text: 'Numero de Causas',
                    font: {
                      size: 16
                    }
                  }
                },
                               
                
              },
               
             
      },
    });
  }

  descargar(){
   
      var imageLink = document.createElement('a');
      const canvas = <HTMLCanvasElement> document.getElementById("MyChart2");
      console.log(canvas);
      imageLink.download = 'grafico.png';
      imageLink.href = canvas.toDataURL('image/png', 1);
      imageLink.click();
      console.log(imageLink.href);
  
  }

  regresar(){
    
    window.close();

  }


}
