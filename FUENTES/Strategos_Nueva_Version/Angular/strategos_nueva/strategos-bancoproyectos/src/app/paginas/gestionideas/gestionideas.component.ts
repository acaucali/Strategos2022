import { Component, OnInit } from '@angular/core';
import { IdeasProyectos } from '../configuracion/model/ideasproyectos';
import { IdeasProyectosService } from '../configuracion/services/ideasproyectos.service';
import { ModalService } from './detallegestion/modal.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-gestionideas',
  templateUrl: './gestionideas.component.html',
  styleUrls: ['./gestionideas.component.css']
})
export class GestionideasComponent implements OnInit {

  
  pageIdea: number =1;
 

  ideas: IdeasProyectos[];
  paginador: any;
  ideaSeleccionada: IdeasProyectos;

  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;

  constructor(private ideasService: IdeasProyectosService,
    public modalservice: ModalService) { }

  ngOnInit(): void {
    this.getIdeas();
  }

  delete(idea: IdeasProyectos): void{

    Swal.fire({
      title: 'Está seguro?',
      text:  `¿Seguro que desea eliminar la idea ${idea.nombreIdea} ?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!',
      cancelButtonText: 'No, cancelar!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.ideasService.delete(idea.ideaId).subscribe(
          response =>{
            this.getIdeas();
            Swal.fire(
              'Idea eliminada!',
              'La idea ha sido eliminada con éxito',
              'success'
            )
          }
        )
      }
    })

  }
  
  abrirModal(idea: IdeasProyectos){
    this.ideaSeleccionada= idea;
    this.modalservice.abrirModal();
  }

  crearIdea(){
    this.ideaSeleccionada = new IdeasProyectos();
    this.modalservice.abrirModal();
  }
  
  getIdeas(){
    this.ideas = null;
    this.elements = [];
    this.previous = [];
    this.ideasService.getIdeasList().subscribe(response =>{
      this.ideas = response;
      if(this.ideas.length >0){
        this.ideas.forEach(idea =>{
          this.elements.push({ideaId: idea.ideaId, nombreIdea: idea.nombreIdea, descripcionIdea: idea.descripcionIdea, tiposPropuestas: idea.tiposPropuestas, impacto: idea.impacto,
            problematica: idea.problematica, poblacion: idea.poblacion, focalizacion: idea.focalizacion, tipoObjetivo: idea.tipoObjetivo, alineacionPlan: idea.alineacionPlan,
            financiacion: idea.financiacion, personaEncargada: idea.personaEncargada, personaContactoDatos: idea.personaContactoDatos, dependenciaId: idea.dependenciaId,
            proyectosEjecutados: idea.proyectosEjecutados, capacidadTecnica: idea.capacidadTecnica, fechaIdea: idea.fechaIdea, anioFormulacion: idea.anioFormulacion,
            estatus: idea.estatus, estatusIdea: idea.estatusIdea, fechaEstatus: idea.fechaEstatus, historico: idea.historico, valorUltimaEvaluacion: idea.valorUltimaEvaluacion,
            fechaUltimaEvaluacion: idea.fechaUltimaEvaluacion, observaciones: idea.observaciones, documentos: idea.documentos
          });
        });
      }
      
    });
  }


}
