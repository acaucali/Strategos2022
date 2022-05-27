import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { EvaluacionIdeas } from '../../configuracion/model/evaluacionideas';
import { EvaluacionIdeasService } from '../../configuracion/services/evaluacionidea.service';


@Component({
  selector: 'app-ponderacion-ideas',
  templateUrl: './ponderacion-ideas.component.html',
  styleUrls: ['./ponderacion-ideas.component.css']
})
export class PonderacionIdeasComponent implements OnInit {

  pageEva: number =1;
 
  ponderaciones: EvaluacionIdeas[];
  ponderacionSeleccionada: EvaluacionIdeas;
  
  paginador: any;  
  organizacionId: any;

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;

  constructor(private router: Router, private ponderacionService: EvaluacionIdeasService, private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {

    this.activatedRoute.params.subscribe(params =>{

      let id = params['id'];
      this.organizacionId= null;
      
      if(id){ 
        this.organizacionId = id;
      }
      
    });

    this.getEvaluaciones();
  }

  delete(evaluacion: EvaluacionIdeas): void{

    Swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar la evaluación ${evaluacion.observacion} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.ponderacionService.delete(evaluacion.evaluacionId).subscribe(
          response =>{
            this.getEvaluaciones();
            Swal.fire(
              'Evaluación eliminada!',
              'La evaluación ha sido eliminada con éxito',
              'success'
            )
          }
        )
      }
    })

  }
  
  abrirModal(evaluacion: EvaluacionIdeas){
    this.ponderacionSeleccionada= evaluacion;
    //this.modalService.abrirModal();
  }

  crearEvaluacion(){
    this.ponderacionSeleccionada = new EvaluacionIdeas();
    //this.modalService.abrirModal();
  }

  getEvaluaciones(){
    this.ponderaciones = null;
    this.elements = [];
    this.previous = [];
    
    this.ponderacionService.getEvaluacionesList().subscribe(response =>{
        this.ponderaciones = response;
        if(this.ponderaciones.length >0){
          this.ponderaciones.forEach(evaluacion =>{

            this.elements.push({
                                                        
              evaluacionId: evaluacion.evaluacionId, fechaEvaluacion: evaluacion.fechaEvaluacion, observacion: evaluacion.observacion
            });

          });
        }
        
      });

  }
 
  

}
