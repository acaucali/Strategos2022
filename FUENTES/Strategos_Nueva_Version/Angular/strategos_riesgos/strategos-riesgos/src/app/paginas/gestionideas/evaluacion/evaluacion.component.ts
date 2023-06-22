import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ORGANIZACION_ID } from 'src/app/config/config';
import { EvaluacionIdeas } from '../../configuracion/model/evaluacionideas';
import { IdeasProyectos } from '../../configuracion/model/ideasproyectos';
import { EvaluacionIdeasService } from '../../configuracion/services/evaluacionidea.service';
import { IdeasProyectosService } from '../../configuracion/services/ideasproyectos.service';
import { ModalService } from './selector-ideas/modal.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-evaluacion',
  templateUrl: './evaluacion.component.html',
  styleUrls: ['./evaluacion.component.css']
})
export class EvaluacionComponent implements OnInit {
  [x: string]: any;

  private errores: string[];
  public ideas: IdeasProyectos[];
  evaluacion: EvaluacionIdeas =new EvaluacionIdeas();
  
  public organizacionId: any;
  public isAdmin: boolean = false;

  elements: any = [];
  previous: any = [];

  pageEva: number =1;
  paginador: any;

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private evaluacionService: EvaluacionIdeasService, private ideasService: IdeasProyectosService,
    private modalService: ModalService) { }

  ngOnInit(): void {

    if(this.organizacionId == undefined){
      this.organizacionId = localStorage.getItem('organizacion');
    }

    this.activatedRoute.params.subscribe(params =>{
      let evaId = params['evaId'];
      this.ideas= null;

      if(evaId && evaId != 0){
        this.evaluacionService.getEvaluacion(evaId).subscribe(response =>{this.evaluacion = response}); //traer evaluacion
        this.evaluacionService.getIdeasList(evaId).subscribe(response =>{this.ideas = response});
        //traer ideas x evaluacion
      }else{
        this.evaluacion = new EvaluacionIdeas();
      }

      
    });

  }

  seleccionarIdea(){
    this.modalService.abrirModal();
  }


  create(){
    this.evaluacion.ideas = this.ideas;
    this.evaluacionService.create(this.evaluacion).subscribe(
      json => {
      swal.fire('Nueva Evaluación', `${json.mensaje}`, 'success');
      this.cerrarModal();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
    this.router.navigateByUrl('/ponderacion');
    this.ponderacionComponent.ngOnInit();
    
    
  }

  update(){
    this.evaluacion.ideas = this.ideas;
    this.evaluacionService.update(this.evaluacion).subscribe(json =>{
      swal.fire('Evaluación Actualizado',  `${json.mensaje}`, 'success')
      this.cerrarModal();
    },
    err =>{
      this.errores = err.error.errors as string[];
      console.error('Código error: '+err.status);
      console.error(err.error.errors);
    }
    );
    this.router.navigateByUrl('/ponderacion');
    this.ponderacionComponent.ngOnInit();

  }


}
