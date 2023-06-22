import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ModalConfigService } from './modal.service';


@Component({
  selector: 'configuracion-tarea',
  templateUrl: './config-tarea.component.html',
  styleUrls: ['./config-tarea.component.css']
})
export class ConfigTareaComponent implements OnInit {

  private errores: string[];
  
  today = new Date();
  anio: number = this.today.getFullYear();

  objetivo: any;
  titulo: string = "Mediciones de Tareas";

  periodoIni: number;
  periodoFin: number;
  frecuencia: number=1;
  real: Boolean;
  meta: Boolean;
  

  constructor(public modalservice: ModalConfigService, private router: Router) { }

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

    this.router.navigate(['/', 'mediciontarea']);
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
