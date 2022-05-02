import { Component, OnInit } from '@angular/core';
import { IdeasProyectos } from '../configuracion/model/ideasproyectos';
import { IdeasProyectosService } from '../configuracion/services/ideasproyectos.service';
import { ModalService } from './detallegestion/modal.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

import { OrganizacionStrategosService } from '../configuracion/services-util/organizacionstrategos.service';
import { OrganizacionStrategos } from '../configuracion/model-util/organizacionstrategos';
import { ORGANIZACION_ID, URL_BACKEND } from 'src/app/config/config';
import { EstatusIdeas } from '../configuracion/model/estatusideas';
import { TiposObjetivos } from '../configuracion/model/tiposobjetivos';
import { TiposPropuestas } from '../configuracion/model/tipospropuestas';
import { EstatusIdeaService } from '../configuracion/services/estatusideas.service';
import { TiposPropuestaService } from '../configuracion/services/tipospropuestas.service';
import { TiposObejtivosService } from '../configuracion/services/tiposobjetivos.service';
import { IdeasDocumentosAnexos } from '../configuracion/model/ideasdocumentosanexos';
import { IdeasDocumentosService } from '../configuracion/services/ideasdocumentosanexos.service';


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
  public organizacionFiltroId: any;
  public organizacionId: any;
  public propuestaId: any;
  public estatusId: any;
  public organizacion: OrganizacionStrategos = new OrganizacionStrategos;
  public propuestas: TiposPropuestas[];
  public objetivos: TiposObjetivos[];
  public estatus: EstatusIdeas[];
  public organizaciones: OrganizacionStrategos[];
  public anio: string;
  public historico: number;
  public isAdmin: boolean = false;

  public orgStr: String ="";
  public estStr: String ="";
  public proStr: String ="";


  elements: any = [];
  previous: any = [];

  firstItemIndex;
  lastItemIndex;
  urlTree: any;

  constructor(private ideasService: IdeasProyectosService, private organizacionesService: OrganizacionStrategosService, private estatusService: EstatusIdeaService, private propuestasService: TiposPropuestaService,
    private objetivosService: TiposObejtivosService, public modalservice: ModalService, private router: Router, public documentoService: IdeasDocumentosService) {
      this.urlTree = this.router.parseUrl(this.router.url);
      this.organizacionId = this.urlTree.queryParams['id'];    
  }

  ngOnInit(): void {

    this.estatusService.getEstatusList().subscribe(response =>{this.estatus = response}); // obtiene los estatus
    this.propuestasService.getTiposPropuestaList().subscribe(response =>{this.propuestas = response}); // obtiene las propuestas
    this.objetivosService.getTiposObjetivoList().subscribe(response =>{this.objetivos = response}); // obtiene los objetivos
    this.organizacionesService.getOrganizacionesList().subscribe(response =>{this.organizaciones = response}); // obtiene las organizaciones

    this.organizacionesService.getOrganizacion(this.organizacionId).subscribe((org) =>{ 
      this.organizacion= org;
    });

    if(this.organizacionId == ORGANIZACION_ID){
      this.isAdmin = true;      
    }
    
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
    this.ideaSeleccionada.dependenciaId = this.organizacionId;
    console.log(this.ideaSeleccionada);
    this.modalservice.abrirModal();
  }
  
  getIdeas(){
    this.ideas = null;
    this.elements = [];
    this.previous = [];

    

    if(this.isAdmin == true){
      this.ideasService.getIdeasList().subscribe(response =>{
        this.ideas = response;
        if(this.ideas.length >0){
          this.ideas.forEach(idea =>{

            this.organizacionesService.getOrganizacion(idea.dependenciaId).subscribe((org) =>{ 
              this.orgStr = org.nombre;
            });
        
            this.estatusService.getCriterio(idea.estatusIdeaId).subscribe((est) =>{ 
              this.estStr = est.estatus;
            });
        
            this.propuestasService.getTipoPropuesta(idea.tipoPropuestaId).subscribe((pro) =>{ 
              this.proStr= pro.tipoPropuesta;
            });

            this.elements.push({
                                                        
              ideaId: idea.ideaId, nombreIdea: idea.nombreIdea, descripcionIdea: idea.descripcionIdea, tipoPropuestaId: idea.tipoPropuestaId, impacto: idea.impacto,
              problematica: idea.problematica, poblacion: idea.poblacion, focalizacion: idea.focalizacion, tipoObjetivoId: idea.tipoObjetivoId, 
              financiacion: idea.financiacion, dependenciasParticipantes: idea.dependenciasParticipantes, dependenciaPersona: idea.dependenciaPersona, personaEncargada: idea.personaEncargada, personaContactoDatos: idea.personaContactoDatos, dependenciaId: idea.dependenciaId,
              proyectosEjecutados: idea.proyectosEjecutados, capacidadTecnica: idea.capacidadTecnica, fechaIdea: idea.fechaIdea , anioFormulacion: idea.anioFormulacion,
              estatusIdeaId: idea.estatusIdeaId, fechaEstatus: idea.fechaEstatus, historico: idea.historico, valorUltimaEvaluacion: idea.valorUltimaEvaluacion,
              fechaUltimaEvaluacion: idea.fechaUltimaEvaluacion, observaciones: idea.observaciones, documentoId: idea.documentoId, orgStr: this.orgStr, estStr: this.estStr,
              proStr: this.proStr
            });
          });
        }
        
      });
    }else{
      this.ideasService.getIdeasListId(this.organizacionId).subscribe(response =>{
        this.ideas = response;
        if(this.ideas.length >0){
          this.ideas.forEach(idea =>{
            this.organizacionesService.getOrganizacion(idea.dependenciaId).subscribe((org) =>{ 
              this.orgStr = org.nombre;
              
            });
        
            this.estatusService.getCriterio(idea.estatusIdeaId).subscribe((est) =>{ 
              this.estStr = est.estatus;
             
            });
        
            this.propuestasService.getTipoPropuesta(idea.tipoPropuestaId).subscribe((pro) =>{ 
              this.proStr= pro.tipoPropuesta;
            
            });


            this.elements.push({ideaId: idea.ideaId, nombreIdea: idea.nombreIdea, descripcionIdea: idea.descripcionIdea, tipoPropuestaId: idea.tipoPropuestaId, impacto: idea.impacto,
              problematica: idea.problematica, poblacion: idea.poblacion, focalizacion: idea.focalizacion, tipoObjetivoId: idea.tipoObjetivoId, 
              financiacion: idea.financiacion, dependenciasParticipantes: idea.dependenciasParticipantes, dependenciaPersona: idea.dependenciaPersona, personaEncargada: idea.personaEncargada, personaContactoDatos: idea.personaContactoDatos, dependenciaId: idea.dependenciaId,
              proyectosEjecutados: idea.proyectosEjecutados, capacidadTecnica: idea.capacidadTecnica, fechaIdea: idea.fechaIdea, anioFormulacion: idea.anioFormulacion,
              estatusIdeaId: idea.estatusIdeaId, fechaEstatus: idea.fechaEstatus, historico: idea.historico, valorUltimaEvaluacion: idea.valorUltimaEvaluacion,
              fechaUltimaEvaluacion: idea.fechaUltimaEvaluacion, observaciones: idea.observaciones, documentoId: idea.documentoId, orgStr: this.orgStr, estStr: this.estStr,
              proStr: this.proStr
            });
          });
        }
      });  
    }

  }

  descargarPDF(idea: IdeasProyectos){
    window.open(URL_BACKEND+"/api/strategos/bancoproyectos/idea/pdf/" + idea.ideaId); 
  }

  descargarPDFResumido(){
    if(this.isAdmin == true){
      window.open(URL_BACKEND+"/api/strategos/bancoproyectos/idea/pdf/resumido/"+ 0);
    }else{
      window.open(URL_BACKEND+"/api/strategos/bancoproyectos/idea/pdf/resumido/"+this.organizacionId);
    }
  }

  descargarXls(idea: IdeasProyectos){
    window.open(URL_BACKEND+"/api/strategos/bancoproyectos/idea/excel/" + idea.ideaId); 
  }

  descargarXlsResumido(){
    if(this.isAdmin == true){
      window.open(URL_BACKEND+"/api/strategos/bancoproyectos/idea/excel/resumido/"+ 0);
    }else{
      window.open(URL_BACKEND+"/api/strategos/bancoproyectos/idea/excel/resumido/"+this.organizacionId);
    }
  }


}
