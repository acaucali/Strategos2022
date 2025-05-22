import { Component, OnInit, Input, AfterViewInit, OnChanges, SimpleChanges } from '@angular/core';
import { ProbabilidadService } from 'src/app/tablas/probabilidad/probabilidad.service';
import { TipoimpactoService } from 'src/app/tablas/tipos-impacto/tipoimpacto.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FactorService } from '../factor.service';
import { FactorriesgoComponent } from '../factorriesgo.component';
import { ModalMapaService } from './modalMapa.service';
import { Ejercicios } from '../../ejercicios/ejercicios';
import { Probabilidad } from 'src/app/tablas/probabilidad/probabilidad';
import { TipoImpacto } from 'src/app/tablas/tipos-impacto/tipoimpacto';
import { FactorRiesgo } from '../factor';
import { EjerciciosService } from '../../ejercicios/ejercicios.service';
import { CalificacionesService } from 'src/app/tablas/calificaciones/calificaciones.service';
import { Calificaciones } from 'src/app/tablas/calificaciones/calificaciones';
import { MapaCalor } from './mapaCalor';


@Component({
  selector: 'detalle-mapa',
  templateUrl: './mapa.component.html',
  styleUrls: ['./mapa.component.css']
})
export class MapaComponent implements OnInit, OnChanges {
  
  pageFac: number =1;

  @Input() ejeid: string; 
 

  public probabilidades: Probabilidad[]; 
  public impactos: TipoImpacto[];
  public factores: FactorRiesgo[];
  public calificaciones: Calificaciones[];
  public objetosMapa: MapaCalor[];
  public rojo: number=0;
  public naranja: number=0;
  public amarillo: number=0;
  public verde: number=0;
  public ejercicio: Ejercicios;

  constructor(private ejercicioService: EjerciciosService, private router: Router, 
    private activatedRoute: ActivatedRoute, private probabilidadService: ProbabilidadService,
    private impactoService: TipoimpactoService, public modalMapa: ModalMapaService, private calificacionesService: CalificacionesService,
    private factorService: FactorService
    ) { }

  ngOnInit() {

    this.activatedRoute.params.subscribe(params =>{
      let id = params['id'];
      if(id){
        this.factorService.getMapasCalor(parseInt(id)).subscribe(response => this.objetosMapa = response);
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
  
  cerrarVentana(){
    window.close();
  }
  
}
