import { Component, OnInit, ViewChild, ElementRef, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { Calificaciones } from '../../configuracion/model/calificaciones';
import { CalificacionesService } from '../../configuracion/services/calificaciones.service';
import { ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';


import { URL_BACKEND } from 'src/app/config/config';
import { ModalService } from './detalle-calificaciones/modal.service';


@Component({
  selector: 'app-calificaciones',
  templateUrl: './calificaciones.component.html',
  styleUrls: ['./calificaciones.component.css']
})
export class CalificacionesComponent implements OnInit {

  pageCali: number =1;
 

  calificaciones: Calificaciones[];
  paginador: any;
  calificacionSeleccionada: Calificaciones;

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;
  
  constructor( private calificacionesService: CalificacionesService,
    public modalservice: ModalService, 
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {

    this.getCalificaciones();
  }

  
  /* metodos calificaciones */

  delete(calificacion: Calificaciones): void{

    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar la calificación ${calificacion.calificacionesRiesgo} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33'
    }).then((result) => {
      if (result.value) {
        this.calificacionesService.delete(calificacion.calificacionesRiesgoId).subscribe(
          response =>{
            this.getCalificaciones();
            swal.fire(
              'Calificación eliminada!',
              'La calificación se ha eliminado con éxito',
              'success'
            )
          }
        )
        
      }
    })
  }
  
  abrirModal(calificacion: Calificaciones){
    this.calificacionSeleccionada= calificacion;
    this.modalservice.abrirModal();
  }

  crearCliente(){
    this.calificacionSeleccionada = new Calificaciones();
    this.modalservice.abrirModal();
  }
  
  
  descargarPDF(){
    window.open(URL_BACKEND+"api/tablas/calificacionesriesgo/pdf"); 
  }

  getCalificaciones(){
    this.calificaciones = null;
    this.elements = [];
    this.previous = [];
    this.calificacionesService.getCalificacionesList().subscribe(response =>{
      this.calificaciones = response;
      if(this.calificaciones.length >0){
        this.calificaciones.forEach(cal =>{
          this.elements.push({calificacionesRiesgoId: cal.calificacionesRiesgoId, calificacionesRiesgo: cal.calificacionesRiesgo, calificacionesRiesgoMaximo: cal.calificacionesRiesgoMaximo, 
            calificacionesRiesgoMinimo: cal.calificacionesRiesgoMinimo, calificacionesRiesgoColor: cal.calificacionesRiesgoColor, calificacionesRiesgoAccion: cal.calificacionesRiesgoAccion});
        });
      }
      
    });
  }

}


