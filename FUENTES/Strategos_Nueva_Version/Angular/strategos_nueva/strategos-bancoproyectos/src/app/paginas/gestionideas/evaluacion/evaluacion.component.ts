import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ORGANIZACION_ID } from 'src/app/config/config';
import { EvaluacionIdeas } from '../../configuracion/model/evaluacionideas';
import { IdeasProyectos } from '../../configuracion/model/ideasproyectos';
import { EvaluacionIdeasService } from '../../configuracion/services/evaluacionidea.service';
import { IdeasProyectosService } from '../../configuracion/services/ideasproyectos.service';
import { ModalService } from './selector-ideas/modal.service';

@Component({
  selector: 'app-evaluacion',
  templateUrl: './evaluacion.component.html',
  styleUrls: ['./evaluacion.component.css']
})
export class EvaluacionComponent implements OnInit {

  public ideas: IdeasProyectos[];
  evaluacion: EvaluacionIdeas;
  
  public organizacionId: any;
  public isAdmin: boolean = false;

  elements: any = [];
  previous: any = [];

  pageEva: number =1;
  paginador: any;

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private evaluacionService: EvaluacionIdeasService, private ideasService: IdeasProyectosService,
    private modalService: ModalService) { }

  ngOnInit(): void {

    this.activatedRoute.params.subscribe(params =>{
      let id = params['id'];
      let evaId = params['evaId'];
      this.ideas= null;

      if(evaId && evaId != 0){
        console.log("existe idea")
        this.evaluacionService.getEvaluacion(evaId).subscribe(response =>{this.evaluacion = response}); //traer evaluacion
        //traer ideas x evaluacion
      }else{
        this.evaluacion = new EvaluacionIdeas();
      }

      
      if(id){

        if(id == ORGANIZACION_ID){
          this.isAdmin = true;   
          this.ideasService.getIdeasList().subscribe(response => {this.ideas = response});   
        }else{
          this.ideasService.getIdeasListId(id).subscribe(response =>{this.ideas = response})
        }

        this.organizacionId = id;
      }

      
    });

  }

  seleccionarIdea(){
    this.modalService.abrirModal();
  }

  eliminarIdea(){
    
  }

  create(){

  }

  update(){

  }

  cerrarModal(){

  }

}
