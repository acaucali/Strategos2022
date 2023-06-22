import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';
import { Ejercicios } from '../../configuracion/model/ejercicios';
import { EjerciciosService } from '../../configuracion/services/ejercicios.service';
import { ModalService } from './detalle-ejercicios/modal.service';

@Component({
  selector: 'app-ejercicios',
  templateUrl: './ejercicios.component.html',
  styleUrls: ['./ejercicios.component.css']
})
export class EjerciciosComponent implements OnInit {

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
      icon: 'warning',
      showCancelButton: true,
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
