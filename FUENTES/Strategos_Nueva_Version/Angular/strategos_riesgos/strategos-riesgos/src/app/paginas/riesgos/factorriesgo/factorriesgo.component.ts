import { Component, OnInit, ViewChild } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { URL_BACKEND, URL_FRONT } from 'src/app/config/config';
import swal from 'sweetalert2';
import { Ejercicios } from '../../configuracion/model/ejercicios';
import { FactorRiesgo } from '../../configuracion/model/factor';
import { Procesos } from '../../configuracion/model/procesos';
import { EjerciciosService } from '../../configuracion/services/ejercicios.service';
import { FactorService } from '../../configuracion/services/factor.service';
import { ProcesosService } from '../../configuracion/services/procesos.service';




import { ModalMapaService } from './mapa/modal.service';
import { ModalService } from './detalle/modal.service';
import { CausaFactorService } from '../../configuracion/services/causa.factor.service';


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


 
  constructor( private ejercicioService: EjerciciosService, private router: Router, private activatedRoute: ActivatedRoute, private causaService: CausaFactorService,
    private procesosService: ProcesosService, private factorService: FactorService, public modalService: ModalService, private modalMapa: ModalMapaService,

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
        this.proid = procesoid;
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
      icon: 'warning',
      showCancelButton: true,
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

    window.open(URL_FRONT+`banco/mapa/${this.ejeid}/${this.proid}`);
  }

  graficos(){
    this.causaService.getCausasGrafico().subscribe(response =>{
      localStorage.setItem("causas", JSON.stringify(response));
    });
    this.factorService.getNumeroFactoresCausas(parseInt(this.ejeid)).subscribe(response => {
      localStorage.setItem("datos", JSON.stringify(response));
    });
    window.open(URL_FRONT+`banco/graficos/${this.ejeid}/${this.proid}`);
    
  }

}
