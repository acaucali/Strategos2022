import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DatoIdea } from 'src/app/paginas/configuracion/model-util/DatoIdea';
import { DatoMedicion } from 'src/app/paginas/configuracion/model-util/DatoMedicion';
import { MedicionService } from 'src/app/paginas/configuracion/services/medicion.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-presupuesto',
  templateUrl: './presupuesto.component.html',
  styleUrls: ['./presupuesto.component.css']
})
export class PresupuestoComponent implements OnInit {

  private errores: string[];
  
  tarea: any;
  objetivo: any;
  encabezados: DatoIdea[];
  today = new Date();
  anio: number = this.today.getFullYear();
  periodoIni: number;
  periodoFin: number;
  frecuencia: number =1;
  real: Boolean;
  meta: Boolean;
  serie: number;

  periodos: number[] = new Array<number>();
  datosReal: DatoMedicion[];
  datosMeta: DatoMedicion[];
  datosMediciones: DatoMedicion[] = new Array<DatoMedicion>();

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private medicionService: MedicionService) { }

  ngOnInit(): void {
    this.medicionService.getMedicionesEncabezadosPresupuestoList().subscribe(response =>{this.encabezados = response});
    this.tarea = localStorage.getItem('tareaNombre');
  }

  registrar(){
    console.log(this.datosMeta);
    console.log(this.datosReal);

    if(this.serie ==3){
      for(let data of this.datosReal){
        this.datosMediciones.push(data);
      }

      for(let data of this.datosMeta){
        this.datosMediciones.push(data);
      }

      this.medicionService.createPresupuesto(this.datosMediciones, Number(localStorage.getItem('anio'))).subscribe(
        
        json => {
          swal.fire('Presupuestos guardados', `${json.mensaje}`, 'success');
        },
        err =>{
          this.errores = err.error.errors as string[];
          console.error('Código error: '+err.status);
          console.error(err.error.errors);
        }

      );
            
    }else{
      if(this.serie ==1 ){

        this.medicionService.createPresupuesto(this.datosReal, Number(localStorage.getItem('anio'))).subscribe(
        
          json => {
            swal.fire('Presupuestos guardados', `${json.mensaje}`, 'success');
          },
          err =>{
            this.errores = err.error.errors as string[];
            console.error('Código error: '+err.status);
            console.error(err.error.errors);
          }
  
        );

      }
      if(this.serie ==2){

        this.medicionService.createPresupuesto(this.datosMeta, Number(localStorage.getItem('anio'))).subscribe(
        
          json => {
            swal.fire('Presupuestos guardados', `${json.mensaje}`, 'success');
          },
          err =>{
            this.errores = err.error.errors as string[];
            console.error('Código error: '+err.status);
            console.error(err.error.errors);
          }
  
        );

      }
    }



  }

  regresar(){
    this.router.navigate(['/', 'tareas']);
  }

  calcular(){

    this.limpiarVariables();
    this.periodos = new Array<number>();
    

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

    var anio = Number(localStorage.getItem('anio'));
    var periodoFin =Number(localStorage.getItem('periodoFin'));
    var periodoIni =Number(localStorage.getItem('periodoIni'));
    var frecuencia =Number(localStorage.getItem('frecuencia'));

    if(localStorage.getItem('real') != null){
      var real=Number(localStorage.getItem('real'));
    }
    if(localStorage.getItem('meta') != null){
      var meta=Number(localStorage.getItem('meta'));
    }

    var serie =0;
    if(real != null && meta != null){
      serie =3;
    }else if(real != null && meta == null){
      serie =1;
    }else if(real == null && meta != null){
      serie =2;
    }

    for(var i = periodoIni; i <= periodoFin; i++)
    { 
      this.periodos.push(i);
    }

    this.serie =0;
  
    console.log(this.periodos);
    this.serie = serie;

    this.datosMeta = new Array<DatoMedicion>();
    this.datosReal = new Array<DatoMedicion>();

    if(serie == 3){
      this.medicionService.getMedicionesDatosPresupuestosList(Number(localStorage.getItem('tarea')), anio, periodoIni, periodoFin , 1).subscribe(response =>{
        this.datosReal = response;
        console.log(this.datosReal);
      });
      this.medicionService.getMedicionesDatosPresupuestosList(Number(localStorage.getItem('tarea')), anio, periodoIni, periodoFin , 2).subscribe(response =>{
        this.datosMeta = response;
        console.log(this.datosMeta);
      });
      
      
    }else{
      if(serie == 1){
        this.medicionService.getMedicionesDatosPresupuestosList(Number(localStorage.getItem('tarea')), anio, periodoIni, periodoFin , 1).subscribe(response =>{
          this.datosReal = response;
          console.log(this.datosReal);
        });
     
      }
      if(serie == 2){
        this.medicionService.getMedicionesDatosPresupuestosList(Number(localStorage.getItem('tarea')), anio, periodoIni, periodoFin , 2).subscribe(response =>{
          this.datosMeta = response;
          console.log(this.datosMeta);
        });    
  
      }

    }
    console.log(this.serie);
    
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
