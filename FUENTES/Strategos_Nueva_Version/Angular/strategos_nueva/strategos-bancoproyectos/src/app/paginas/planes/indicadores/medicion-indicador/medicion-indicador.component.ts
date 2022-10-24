import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { IndicadorService } from 'src/app/paginas/configuracion/services/indicador.service';
import { ModalIndicadorService } from './modal.service';

@Component({
  selector: 'medicion-indicador',
  templateUrl: './medicion-indicador.component.html',
  styleUrls: ['./medicion-indicador.component.css']
})
export class MedicionIndicadorComponent implements OnInit {

  private errores: string[];
  
  objetivo: any;
  titulo: string = "Mediciones de Indicadores";
  anio: number;
  periodoIni: number;
  periodoFin: number;
  frecuencia: number;
  real: Boolean;
  meta: Boolean;
  

  constructor(public modalservice: ModalIndicadorService, public indicadorService: IndicadorService, private router: Router) { }

  ngOnInit(): void {
    this.objetivo = localStorage.getItem('nombreObjetivo');
  }

  aceptar(){

    this.limpiarVariables();


    console.log(this.anio, this.periodoFin, this.periodoIni, this.frecuencia, this.real, this.meta);
    localStorage.setItem('anio', this.anio.toString());
    localStorage.setItem('periodoFin', this.periodoFin.toString());
    localStorage.setItem('periodoIni', this.periodoIni.toString());
    localStorage.setItem('frecuencia', this.frecuencia.toString());
    

    if(this.real != undefined && this.real == true){
      localStorage.setItem('real', "0");
    }

    if(this.meta != undefined && this.meta == true){
      localStorage.setItem('meta', "1");
    }

    this.router.navigate(['/', 'medicionindicador']);
  }

  cerrarModal(){
    this.modalservice.cerrarModal();
  }

  limpiarVariables(){
    localStorage.removeItem('anio');
    localStorage.removeItem('periodoFin');
    localStorage.removeItem('periodoIni');
    localStorage.removeItem('frecuencia');
    localStorage.removeItem('real');
    localStorage.removeItem('meta');
  }

}
