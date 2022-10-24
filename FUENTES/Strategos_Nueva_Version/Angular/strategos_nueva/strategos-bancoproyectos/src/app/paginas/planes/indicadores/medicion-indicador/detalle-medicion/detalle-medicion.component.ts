import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DatoIdea } from 'src/app/paginas/configuracion/model-util/DatoIdea';
import { DatoMedicion } from 'src/app/paginas/configuracion/model-util/DatoMedicion';
import { MedicionService } from 'src/app/paginas/configuracion/services/medicion.service';
import { Indicador } from '../../../../configuracion/model/indicador';
import { IndicadorService } from '../../../../configuracion/services/indicador.service';

@Component({
  selector: 'app-detalle-medicion',
  templateUrl: './detalle-medicion.component.html',
  styleUrls: ['./detalle-medicion.component.css']
})
export class DetalleMedicionComponent implements OnInit {

  encabezados: DatoIdea[];
  datos: DatoMedicion[];
  vacio: boolean = false;  
  registrado: boolean = false;
  indicadores: Indicador[];
  objetivo: any;

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

    this.indicadorService.getIndicadoresPerspectiva(Number(localStorage.getItem('objetivoId'))).subscribe(response =>{
      this.indicadores = response;
    });

    this.medicionService.getMedicionesEncabezadosList(objetivo, frecuencia,anio, periodoIni, periodoFin).subscribe(response =>{this.encabezados = response});
    this.medicionService.getMedicionesDatosList(objetivo, anio, periodoIni, periodoFin , serie).subscribe(response =>{this.datos = response});

  }

  registrar(){

  }

  regresar(){

  }
  
}
