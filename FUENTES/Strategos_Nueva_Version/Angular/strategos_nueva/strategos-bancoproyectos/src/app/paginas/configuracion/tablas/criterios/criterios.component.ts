import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CriteriosEvaluacion } from '../../model/criteriosevaluacion';
import { CriteriosEvaluacionService } from '../../services/criteriosevaluacion.service';
import { ModalService } from './detalle-criterio/modal.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-criterios',
  templateUrl: './criterios.component.html',
  styleUrls: ['./criterios.component.css']
})
export class CriteriosComponent implements OnInit {

  pageIdea: number =1;
 

  criterios: CriteriosEvaluacion[];
  paginador: any;
  criterioSeleccionado: CriteriosEvaluacion;

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;

  constructor(private router: Router, private criteriosService: CriteriosEvaluacionService, private modalService: ModalService) { }

  ngOnInit(): void {
    this.getCriterios();
  }

  delete(criterio: CriteriosEvaluacion): void{

    Swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar el criterio ${criterio.control} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.criteriosService.delete(criterio.criterioId).subscribe(
          response =>{
            this.getCriterios();
            Swal.fire(
              'Criterio eliminado!',
              'El criterio ha sido eliminado con éxito',
              'success'
            )
          }
        )
      }
    })

  }
  
  abrirModal(criterio: CriteriosEvaluacion){
    this.criterioSeleccionado= criterio;
    this.modalService.abrirModal();
  }

  crearCriterio(){
    this.criterioSeleccionado = new CriteriosEvaluacion();
    this.modalService.abrirModal();
  }

  getCriterios(){
    this.criterios = null;
    this.elements = [];
    this.previous = [];
    
    this.criteriosService.getCriteriosList().subscribe(response =>{
        this.criterios = response;
        if(this.criterios.length >0){
          this.criterios.forEach(criterio =>{

            this.elements.push({
                                                        
              criterioId: criterio.criterioId, control: criterio.control, peso: criterio.peso
            });

          });
        }
        
      });

  }

  
}
