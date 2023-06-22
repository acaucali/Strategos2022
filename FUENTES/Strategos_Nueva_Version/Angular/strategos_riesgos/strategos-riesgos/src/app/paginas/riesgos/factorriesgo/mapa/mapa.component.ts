import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MapaCalor } from 'src/app/paginas/configuracion/model-util/mapaCalor';
import { Calificaciones } from 'src/app/paginas/configuracion/model/calificaciones';
import { Ejercicios } from 'src/app/paginas/configuracion/model/ejercicios';
import { FactorRiesgo } from 'src/app/paginas/configuracion/model/factor';
import { Probabilidad } from 'src/app/paginas/configuracion/model/probabilidad';
import { TipoImpacto } from 'src/app/paginas/configuracion/model/tipoimpacto';
import { CalificacionesService } from 'src/app/paginas/configuracion/services/calificaciones.service';
import { EjerciciosService } from 'src/app/paginas/configuracion/services/ejercicios.service';
import { FactorService } from 'src/app/paginas/configuracion/services/factor.service';
import { ProbabilidadService } from 'src/app/paginas/configuracion/services/probabilidad.service';
import { TipoimpactoService } from 'src/app/paginas/configuracion/services/tipoimpacto.service';
import { ModalMapaService } from './modal.service';
import { Procesos } from 'src/app/paginas/configuracion/model/procesos';
import { ProcesosService } from 'src/app/paginas/configuracion/services/procesos.service';
import { URL_FRONT } from 'src/app/config/config';


@Component({
  selector: 'detalle-mapa',
  templateUrl: './mapa.component.html',
  styleUrls: ['./mapa.component.css']
})
export class MapaComponent implements OnInit, OnChanges {

  pageFac: number =1;

  @Input() ejeid: string;
  @Input() proid: string; 
 

  public probabilidades: Probabilidad[]; 
  public impactos: TipoImpacto[];
  public factores: FactorRiesgo[];
  public calificaciones: Calificaciones[];
  public objetosMapa: MapaCalor[];
  public rojo: number=0;
  public naranja: number=0;
  public amarillo: number=0;
  public verde: number=0;
  public ejercicio: Ejercicios = new Ejercicios();
  public proceso: Procesos = new Procesos();

  constructor(private ejercicioService: EjerciciosService, private router: Router, 
    private activatedRoute: ActivatedRoute, private probabilidadService: ProbabilidadService,
    private impactoService: TipoimpactoService, public modalMapa: ModalMapaService, private calificacionesService: CalificacionesService,
    private factorService: FactorService, private procesosService: ProcesosService
    ) { }

  ngOnInit() {

    this.activatedRoute.params.subscribe(params =>{
      let id = params['id'];
      let procid = params['proid'];
      if(id){
        this.factorService.getMapasCalor(parseInt(id)).subscribe(response => this.objetosMapa = response);
        this.ejercicioService.getEjercicio(id).subscribe((ejercicio) =>{this.ejercicio= ejercicio;});
      }
      if(procid){
        this.procesosService.getProceso(procid).subscribe((proceso) => this.proceso = proceso);
      }

    });
    this.impactoService.getImpactosListMapa().subscribe(response => this.impactos=response );
    this.probabilidadService.getProbabilidadesListMapa().subscribe(response => this.probabilidades = response);
    
    this.calificacionesService.getCalificacionesList().subscribe(response =>{
        this.calificaciones=response;
    });
  
  } 

  ngOnChanges(changes: SimpleChanges){
    alert('cambio'); 
  }

  cerrarModal(){
    this.modalMapa.cerrarModal();

  }

  obtenerDetalle(obj: MapaCalor){
    this.factores = obj.factores;
  }

  regresar(){
    window.close();
  }
}
