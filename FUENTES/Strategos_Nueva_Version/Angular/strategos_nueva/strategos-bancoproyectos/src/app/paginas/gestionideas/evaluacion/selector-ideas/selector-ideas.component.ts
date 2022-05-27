import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ORGANIZACION_ID } from 'src/app/config/config';
import { IdeasProyectos } from 'src/app/paginas/configuracion/model/ideasproyectos';
import { EvaluacionIdeasService } from 'src/app/paginas/configuracion/services/evaluacionidea.service';
import { IdeasProyectosService } from 'src/app/paginas/configuracion/services/ideasproyectos.service';
import { EvaluacionComponent } from '../evaluacion.component';
import { ModalService } from './modal.service';

@Component({
  selector: 'selector-ideas',
  templateUrl: './selector-ideas.component.html',
  styleUrls: ['./selector-ideas.component.css']
})
export class SelectorIdeasComponent implements OnInit {

  public ideas: IdeasProyectos[];
  private errores: string[];
  @Input() org: number;

  elements: any = [];
  previous: any = [];

  pageSele: number =1;
  paginador: any;
  
  constructor(public modalservice: ModalService, private router: Router, private evaluacionService: EvaluacionIdeasService, private evaluacionComponent: EvaluacionComponent
    , private activatedRoute: ActivatedRoute, private ideasService: IdeasProyectosService) { }

  ngOnInit(): void {
          
      if(this.org == ORGANIZACION_ID){
        this.ideasService.getIdeasList().subscribe(response => {this.ideas = response}); 
      }else{
        this.ideasService.getIdeasListId(this.org).subscribe(response =>{this.ideas = response});
      }
       
  }
   
  cerrarModal(){
    this.modalservice.cerrarModal();
  }

}
