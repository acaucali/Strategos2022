import { Component, OnInit, Input, OnChanges, SimpleChanges, ChangeDetectorRef, ViewChild, AfterViewInit } from '@angular/core';
import { Ejercicios } from './ejercicios';
import { ModalService } from './detalle/modal.service';
import { ActivatedRoute } from '@angular/router';

import swal from 'sweetalert2';
import { EjerciciosService } from './ejercicios.service';
import { MdbTableService, MdbTablePaginationComponent } from 'angular-bootstrap-md';

@Component({
  selector: 'app-ejercicios',
  templateUrl: './ejercicios.component.html',
  styleUrls: ['./ejercicios.css']
})
export class EjerciciosComponent implements OnChanges {

  pageEje: number =1;
  
  ejercicios: Ejercicios[];
  ejerciciosLista: Ejercicios[];
  ejercicioSeleccionado: Ejercicios;
  paginador: any;

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;

  @Input() id: number;

  constructor(
    public modalservice: ModalService, private activatedRoute: ActivatedRoute, private ejercicioService: EjerciciosService) { }

  ngOnInit() {
    
  }

  ngOnChanges(changes: SimpleChanges): void {
    if(changes.id){
      if(this.id>0 || this.id != undefined){
        this.cargarEjercicios();
      }else{
        this.ejercicios=null;
      }
    }
  }
 
  

  /* metodos ejercicios */

  delete(ejercicio: Ejercicios): void{
    swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar el ejercicio ?`,
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
        this.ejercicioService.delete(ejercicio.ejercicioId).subscribe(
          response =>{
            this.cargarEjercicios();
            swal.fire(
              'Ejercicio eliminado!',
              'El Ejercicio se ha eliminado con éxito',
              'success'
            )
          }
        )
        
      }
    })
  }

  abrirModal(ejercicio: Ejercicios){
    this.ejercicioSeleccionado= ejercicio;
    this.modalservice.abrirModal();
  }

  crearEjercicio(){
    this.ejercicioSeleccionado = new Ejercicios();
    this.ejercicioSeleccionado.procesoPadreId= this.id;
    this.modalservice.abrirModal();
  }

  cargarEjercicios(){
    this.ejercicios = null;
    this.elements = [];
    this.previous = [];
    this.ejercicioService.getEjerciciosList(this.id).subscribe(response =>{
      this.ejercicios = response;
      if(this.ejercicios.length >0){
        this.ejercicios.forEach(eje =>{
          this.elements.push({ejercicioId: eje.ejercicioId, procesoPadreId: eje.procesoPadreId, fechaEjercicio: eje.fechaEjercicio,
            ejercicioDescripcion: eje.ejercicioDescripcion, ejercicioEstatus: eje.ejercicioEstatus, factorRiesgo: eje.factorRiesgo
          });
        });
      }
    });
  }


}
