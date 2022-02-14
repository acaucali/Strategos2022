import { Component, OnInit, ViewChild, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { EjerciciosService } from '../ejercicios/ejercicios.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Ejercicios } from '../ejercicios/ejercicios';
import { ProcesosService } from 'src/app/procesos/procesos.service';
import { Procesos } from 'src/app/procesos/procesos';
import { FactorService } from './factor.service';
import swal from 'sweetalert2';
import { FactorRiesgo } from './factor';
import { ModalService } from './detalle/modal.service';
import { MdbTablePaginationComponent, MdbTableService } from 'angular-bootstrap-md';
import { ModalMapaService } from './mapa/modalMapa.service';
import { URL_FRONT, URL_BACKEND } from 'src/app/config/config';
import { ModalGraficoService } from './grafico/modalgrafico.service';
import { GraficoComponent } from './grafico/grafico.component';


@Component({
  selector: 'app-factorriesgo',
  templateUrl: './factorriesgo.component.html',
  styleUrls: ['./factorriesgo.component.css']
})
export class FactorriesgoComponent implements OnInit {

  pageFac: number =1;
  

  public ejercicio: Ejercicios= new Ejercicios();
  public proceso: Procesos= new Procesos();
  public factores: FactorRiesgo[];
  public factorSeleccionado: FactorRiesgo;
  public accion: string;
  public colores:  any[] = [];
  public vermapa: boolean=false;
  public ejeid: string;
  public proid: string;

  @ViewChild(GraficoComponent, {static: false}) graficoComponent: GraficoComponent;
 
  constructor( private ejercicioService: EjerciciosService, private router: Router, private activatedRoute: ActivatedRoute,
    private procesosService: ProcesosService, private factorService: FactorService, public modalService: ModalService, private modalMapa: ModalMapaService,
    public modalGrafico: ModalGraficoService
    ) { }

    ngOnInit() {
      
    this.activatedRoute.params.subscribe(params =>{
      let id = params['id'];
      let procesoid= params['procesoid'];

      this.factores= null;

      if(id){  
        this.ejeid = id;
        this.ejercicioService.getEjercicio(id).subscribe((ejercicio) =>{ 
          this.ejercicio= ejercicio;
          this.ejercicioService.getFactores(id).subscribe(response =>{
            this.factores = response;
            
            this.calcularAlerta(); 
          });
        });
        
      }
      if(procesoid){
        this.procesosService.getProceso(procesoid).subscribe(
         (proceso) => this.proceso = proceso
        );
      }
      
    });
    
  }

  delete(factor: FactorRiesgo): void{
    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar el factor de riesgo ?`,
      type: 'warning',
      showCancelButton: true,
      confirmButtonClass: 'btn btn-success',
      cancelButtonClass: 'btn btn-danger',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33'
    }).then((result) => {
      if (result.value) {
        this.factorService.delete(factor.factorRiesgoId).subscribe(
          response =>{
            this.cargarFactores();
            swal.fire(
              'Factor eliminado!',
              'El Factor de Riesgo se ha eliminado con éxito',
              'success'
            )
          }
        )
        
      }
    })
  }

  abrirModal(factor: FactorRiesgo){
    this.accion = "update";
    this.factorSeleccionado= factor;
    this.modalService.abrirModal();
  }

  crearFactor(){
    this.factorSeleccionado = new FactorRiesgo();
    this.modalService.abrirModal();
  }
  
  cargarFactores(){
    this.factores = null;
    this.vermapa = false;
    this.ejercicioService.getFactores(this.ejercicio.ejercicioId).subscribe(response =>{
      this.factores = response;
      
      this.calcularAlerta(); 
    });
  }

  calcularAlerta(){
    this.factores.forEach(fac =>{
      this.colores.push(fac.factorRiesgo);
    });
  }
  
  descargarPDF(){
    
    window.open(URL_BACKEND+`/api/ejercicios/factor/pdf/${this.ejercicio.ejercicioId}`);
  }

  mapa(){
    window.open(URL_FRONT+`mapa/${this.ejeid}`);
  }

  graficos(){
    this.graficoComponent.cargarDatosGrafico();
    this.modalGrafico.abrirModal();
  }

  cargarDatosGrafico(){
    this.graficoComponent.cargarDatosGrafico();
  }
}
