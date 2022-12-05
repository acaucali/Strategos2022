import { Component, OnInit, AfterViewInit, AfterContentInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Chart from 'chart.js/auto';
import { MedicionService } from 'src/app/paginas/configuracion/services/medicion.service';



@Component({
  selector: 'app-grafico',
  templateUrl: './grafico.component.html',
  styleUrls: ['./grafico.component.css']
})
export class GraficoComponent implements OnInit {

  indicador: any;
  public chart: any;
  canvas: any;
  ctx: any;
  anio: number;
  periodoIni: number = 1;
  periodoFin: number = 12;

  

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private medicionService: MedicionService) { }

  ngOnInit(): void {

    var today = new Date();
    var year = today.getFullYear();

    this.indicador = localStorage.getItem('indicadorNombre');

    this.createChart();
    
  }

  createChart(){
    
    this.chart = new Chart("MyChart", {
      type: 'line', //this denotes tha type of chart

      data: {// values on X-Axis
        labels: JSON.parse(localStorage.getItem("periodos")), 
	       datasets: [
          {
            label: "Real",
            data: JSON.parse(localStorage.getItem("reales")),
            backgroundColor: 'blue',
            borderColor: 'rgb(75, 192, 192)',
          },
          {
            label: "Meta",
            data: JSON.parse(localStorage.getItem("metas")),
            backgroundColor: 'limegreen',
            borderColor: 'rgb(126, 229, 52)',
          }  
        ]
      },
      options: {
        aspectRatio:2.5
      }
      
    });

    
  }

  reload(){
    window.location.reload();
  }


  regresar(){
    this.router.navigate(['/', 'planes', localStorage.getItem('proyectoId')]);
  }
  

}
