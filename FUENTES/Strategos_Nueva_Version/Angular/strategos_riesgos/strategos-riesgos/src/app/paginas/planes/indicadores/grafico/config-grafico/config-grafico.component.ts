import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MedicionService } from 'src/app/paginas/configuracion/services/medicion.service';
import { ModalGraficoService } from './modal.service';
import { GraficoComponent } from '../grafico.component';

@Component({
  selector: 'config-grafico',
  templateUrl: './config-grafico.component.html',
  styleUrls: ['./config-grafico.component.css']
})
export class ConfigGraficoComponent implements OnInit {

  anio: number;
  periodoIni: number = 1;
  periodoFin: number = 12;

  titulo: String ="Graficar Indicador";

  @ViewChild(GraficoComponent) graficoComponent: GraficoComponent;

  constructor(public modalservice: ModalGraficoService, private router: Router, private medicionService: MedicionService
    ) { }

  ngOnInit(): void {

    var today = new Date();
    var year = today.getFullYear();

    this.anio = year;

    
  }

  graficar(){
    this.medicionService.getMedicionesPeriodosGraficoList(this.anio, this.periodoIni, this.periodoFin).subscribe(response =>{
      localStorage.setItem("periodos", JSON.stringify(response));
    });

    this.medicionService.getMedicionesRealGraficoList(Number(localStorage.getItem('indicador')), this.anio, this.periodoIni, this.periodoFin).subscribe(response =>{
      localStorage.setItem("reales", JSON.stringify(response));
    });

    this.medicionService.getMedicionesMetaGraficoList(Number(localStorage.getItem('indicador')), this.anio, this.periodoIni, this.periodoFin).subscribe(response =>{
      localStorage.setItem("metas", JSON.stringify(response));
    });

    this.router.navigate(['/', 'grafico']);

   
  }

  cerrarModal(){
    this.modalservice.cerrarModal();
  }

}
