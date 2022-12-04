import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DatoIdea } from 'src/app/paginas/configuracion/model-util/DatoIdea';
import { DatoMedicion } from 'src/app/paginas/configuracion/model-util/DatoMedicion';
import { Indicador } from 'src/app/paginas/configuracion/model/indicador';
import { IndicadorService } from 'src/app/paginas/configuracion/services/indicador.service';
import { MedicionService } from 'src/app/paginas/configuracion/services/medicion.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-medicion-tareas',
  templateUrl: './medicion-tareas.component.html',
  styleUrls: ['./medicion-tareas.component.css']
})
export class MedicionTareasComponent implements OnInit {

  private errores: string[];
  encabezados: DatoIdea[];
  datosReal: DatoMedicion[];
  datosMeta: DatoMedicion[];
  datosMediciones: DatoMedicion[] = new Array<DatoMedicion>();
  vacio: boolean = false;  
  registrado: boolean = false;
  indicadores: Indicador[];
  objetivo: any;
  ids: number[];
  serie: number;

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private medicionService: MedicionService, public indicadorService: IndicadorService) { }

  ngOnInit(): void {
    

    if(localStorage.getItem('objetivoId') != null){
      var objetivo = Number(localStorage.getItem('objetivoId'));
      this.objetivo = localStorage.getItem('nombreObjetivo');
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

    var serie;
    if(real != null && meta != null){
      serie =3;
    }else if(real != null && meta == null){
      serie =1;
    }else if(real == null && meta != null){
      serie =2;
    }

    console.log(serie);
    this.serie = serie;

    this.medicionService.getMedicionesIdsTareaList(Number(localStorage.getItem('actividad')), serie).subscribe(response=>{this.ids = response});
    this.medicionService.getMedicionesEncabezadosTareaList(Number(localStorage.getItem('actividad')), frecuencia,anio, periodoIni, periodoFin).subscribe(response =>{this.encabezados = response});

    if(serie == 3){
      this.medicionService.getMedicionesDatosTareaList(Number(localStorage.getItem('actividad')), anio, periodoIni, periodoFin , 1).subscribe(response =>{this.datosReal = response});
      this.medicionService.getMedicionesDatosTareaList(Number(localStorage.getItem('actividad')), anio, periodoIni, periodoFin , 2).subscribe(response =>{this.datosMeta = response});
    }else{
      if(serie == 1){
        this.medicionService.getMedicionesDatosTareaList(Number(localStorage.getItem('actividad')), anio, periodoIni, periodoFin , 1).subscribe(response =>{this.datosReal = response});
      }
      if(serie == 2){
        this.medicionService.getMedicionesDatosTareaList(Number(localStorage.getItem('actividad')), anio, periodoIni, periodoFin , 2).subscribe(response =>{this.datosMeta = response});
      }

    }

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

      this.medicionService.createTarea(this.datosMediciones, Number(localStorage.getItem('actividad'))).subscribe(
        json => {
          swal.fire('Mediciones guardadas', `${json.mensaje}`, 'success');
        },
        err =>{
          this.errores = err.error.errors as string[];
          console.error('Código error: '+err.status);
          console.error(err.error.errors);
        }
      );
      
    }else{
      if(this.serie ==1 ){
        this.medicionService.createTarea(this.datosReal, Number(localStorage.getItem('actividad'))).subscribe(
          json => {
            swal.fire('Mediciones guardadas', `${json.mensaje}`, 'success');
          },
          err =>{
            this.errores = err.error.errors as string[];
            console.error('Código error: '+err.status);
            console.error(err.error.errors);
          }
        );
      }
      if(this.serie ==2){
        this.medicionService.createTarea(this.datosMeta, Number(localStorage.getItem('objetivoId'))).subscribe(
          json => {
            swal.fire('Mediciones guardadas', `${json.mensaje}`, 'success');
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
    this.router.navigate(['/', 'planes', localStorage.getItem('proyectoId')]);
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
